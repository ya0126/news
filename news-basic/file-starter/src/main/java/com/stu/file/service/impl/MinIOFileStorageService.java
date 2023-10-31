package com.stu.file.service.impl;

import com.stu.file.config.MinIOConfig;
import com.stu.file.config.MinIOConfigProperties;
import com.stu.file.service.FileStorageService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件存储服务实现类
 *
 * @author yaoh
 */
@EnableConfigurationProperties(MinIOConfigProperties.class)
@Import(MinIOConfig.class)
@Slf4j
public class MinIOFileStorageService implements FileStorageService {

    private final static String separator = "/";
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinIOConfigProperties minIOConfigProperties;

    /**
     * 构建文件路径
     *
     * @param dirPath
     * @param fileName eg：2023/9/10/sky.jpg
     * @return String
     */
    public String builderFilePath(String dirPath, String fileName) {
        StringBuilder stringBuilder = new StringBuilder(50);
        if (!StringUtils.isEmpty(dirPath)) {
            stringBuilder.append(dirPath).append(separator);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String todayStr = sdf.format(new Date());
        stringBuilder.append(todayStr).append(separator);
        stringBuilder.append(fileName);
        return stringBuilder.toString();
    }

    /**
     * 上传图片文件
     *
     * @param prefix      文件前缀
     * @param filename    文件名
     * @param inputStream 文件流
     * @return
     */
    @Override
    public String uploadImgFile(String prefix, String filename, InputStream inputStream) {
        String filePath = builderFilePath(prefix, filename);

        try {
            // 构建上传参数
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType("image/jpg")
                    .bucket(minIOConfigProperties.getBucket())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            // 上传文件
            minioClient.putObject(putObjectArgs);

            StringBuilder stringBuilder = new StringBuilder(minIOConfigProperties.getReadPath());
            stringBuilder.append(separator + minIOConfigProperties.getBucket());
            stringBuilder.append(separator);
            stringBuilder.append(filePath);
            // 返回上传文件在文件服务器的位置
            return stringBuilder.toString();
        } catch (Exception e) {
            log.error("minio上传图片文件失败", e);
            throw new RuntimeException("上传文件失败");
        }
    }

    /**
     * 上传html文件
     *
     * @param prefix      文件前缀
     * @param filename    文件名
     * @param inputStream 文件流
     * @return
     */
    @Override
    public String uploadHtmlFile(String prefix, String filename, InputStream inputStream) {
        String filePath = builderFilePath(prefix, filename);
        try {
            // 构建上传参数
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType("text/html")
                    .bucket(minIOConfigProperties.getBucket())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            // 上传文件
            minioClient.putObject(putObjectArgs);

            StringBuilder stringBuilder = new StringBuilder(minIOConfigProperties.getReadPath());
            stringBuilder.append(separator + minIOConfigProperties.getBucket());
            stringBuilder.append(separator);
            stringBuilder.append(filePath);

            // 返回上传文件在文件服务器的位置
            return stringBuilder.toString();
        } catch (Exception e) {
            log.error("minio上传html文件失败", e);
            throw new RuntimeException("上传文件失败");
        }
    }

    /**
     * 删除文件
     *
     * @param pathUrl 文件全路径
     */
    @Override
    public void delete(String pathUrl) {
        // 获取bucket 以及 object
        String key = pathUrl.replace(minIOConfigProperties.getEndpoint() + "/", "");
        int index = key.indexOf(separator);
        String bucket = key.substring(0, index);
        String filePath = key.substring(index + 1);

        // 构建文件删除参数
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(filePath)
                .build();
        try {
            // 删除文件
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            log.error("minio删除文件失败，pathUrl:{}", pathUrl);
            throw new RuntimeException("文件删除失败");
        }
    }

    /**
     * 下载文件
     *
     * @param pathUrl    文件全路径
     * @param returnType 返回类型（字节数组类型，输入流类型）
     * @param <T>
     * @return
     */
    @Override
    public <T> T downLoadFile(String pathUrl, Class<T> returnType) {
        // 获取bucket 以及 object
        String key = pathUrl.replace(minIOConfigProperties.getEndpoint() + "/", "");
        int index = key.indexOf(separator);
        String bucket = key.substring(0, index);
        String filePath = key.substring(index + 1);

        try {
            // 构建文件下载参数
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(filePath)
                    .build();
            // 下载文件
            InputStream inputStream = minioClient.getObject(getObjectArgs);

            // 如果需要流返回结果，直接返回
            if (returnType == InputStream.class) {
                return returnType.cast(inputStream);
            }

            // 如果需要字节数组类型返回，转换为字节数组
            if (returnType == byte[].class) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[8192];
                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                return returnType.cast(outputStream.toByteArray());
            }
        } catch (Exception e) {
            log.error("minio文件下载失败,pathUrl:{}", pathUrl);
            throw new RuntimeException("文件下载失败");
        }
        return null;
    }

}
