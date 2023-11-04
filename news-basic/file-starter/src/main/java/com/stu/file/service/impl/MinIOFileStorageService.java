package com.stu.file.service.impl;

import com.stu.file.config.MinIOConfig;
import com.stu.file.config.MinIOConfigProperties;
import com.stu.file.service.FileStorageService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
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
    private final MinioClient minioClient;
    private final MinIOConfigProperties minIOConfigProperties;

    public MinIOFileStorageService(MinioClient minioClient, MinIOConfigProperties minIOConfigProperties) {
        this.minioClient = minioClient;
        this.minIOConfigProperties = minIOConfigProperties;
    }

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
            return getUploadFilePath(filePath);
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
            return getUploadFilePath(filePath);
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
        String key = pathUrl.replace(minIOConfigProperties.getEndpoint() + "/", "");
        int index = key.indexOf(separator);
        String bucket = key.substring(0, index);
        String object = key.substring(index + 1);
        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .build();
        try {
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
        String key = pathUrl.replace(minIOConfigProperties.getEndpoint() + "/", "");
        int index = key.indexOf(separator);
        String bucket = key.substring(0, index);
        String filePath = key.substring(index + 1);
        try {
            GetObjectArgs getObjectArgs = GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(filePath)
                    .build();
            InputStream inputStream = minioClient.getObject(getObjectArgs);
            if (returnType == InputStream.class) {
                return returnType.cast(inputStream);
            }
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

    /**
     * 获取上传后的文件路径
     *
     * @param filePath
     * @return String
     */
    protected String getUploadFilePath(String filePath) {
        return new StringBuilder(minIOConfigProperties.getReadPath())
                .append(separator)
                .append(minIOConfigProperties.getBucket())
                .append(separator)
                .append(filePath)
                .toString();
    }
}
