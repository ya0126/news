package com.heima.file.service.impl;

import com.heima.file.config.MinIOConfig;
import com.heima.file.config.MinIOConfigProperties;
import com.heima.file.service.FileStorageService;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * TODO
 *
 * @author yaoh
 */
@Slf4j
@EnableConfigurationProperties(MinIOConfigProperties.class)
@Import(MinIOConfig.class)
public class MinIOFileStorageService implements FileStorageService {

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinIOConfigProperties minIOConfigProperties;

    private final static String separator = "/";

    /**
     * 构建文件路径
     *
     * @param dirPath
     * @param fileName yyyy/mm/dd/file.jpg
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
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType("image/jpg")
                    .bucket(minIOConfigProperties.getBucket())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            StringBuilder stringBuilder = new StringBuilder(minIOConfigProperties.getReadPath());

            minioClient.putObject(putObjectArgs);

            stringBuilder.append(separator + minIOConfigProperties.getBucket());
            stringBuilder.append(separator);
            stringBuilder.append(filePath);
            return stringBuilder.toString();
        } catch (Exception e) {
            log.error("minio put file error.", e);
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
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object(filePath)
                    .contentType("text/html")
                    .bucket(minIOConfigProperties.getBucket())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();

            minioClient.putObject(putObjectArgs);

            StringBuilder stringBuilder = new StringBuilder(minIOConfigProperties.getReadPath());
            stringBuilder.append(separator + minIOConfigProperties.getBucket());
            stringBuilder.append(separator);
            stringBuilder.append(filePath);

            return stringBuilder.toString();
        } catch (Exception e) {
            log.error("minio put file error.", e);
            e.printStackTrace();
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
        String filePath = key.substring(index + 1);

        RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                .bucket(bucket)
                .object(filePath)
                .build();
        try {
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            log.error("minio remove file error.  pathUrl:{}", pathUrl);
            e.printStackTrace();
        }
    }

    @Override
    public byte[] downLoadFile(String pathUrl) {
        String key = pathUrl.replace(minIOConfigProperties.getEndpoint() + "/", "");
        int index = key.indexOf(separator);
        String bucket = key.substring(0, index);
        String filePath = key.substring(index + 1);

        InputStream inputStream = null;
        try {
            inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket).object(filePath).build());
        } catch (Exception e) {
            log.error("minio down file error.  pathUrl:{}", pathUrl);
            e.printStackTrace();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;

        while (true) {
            try {
                if ((rc = inputStream.read(buff, 0, 100)) > 0) break;
            } catch (Exception e) {
                e.printStackTrace();
            }

            outputStream.write(buff, 0, rc);
        }
        return outputStream.toByteArray();
    }
}
