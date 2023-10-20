package com.heima.minio;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.FileInputStream;

public class MinIOTest {

    public static void main(String[] args) {

        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream("/Users/yaoh/list.html");

            MinioClient client = MinioClient
                    .builder()
                    .credentials("minioadmin", "minioadmin")
                    .endpoint("http://101.34.245.48:9000")
                    .build();

            PutObjectArgs putObjectArgs = PutObjectArgs
                    .builder()
                    .object("list.html")
                    .contentType("text/html")
                    .bucket("hmtt")
                    .stream(fileInputStream, fileInputStream.available(), -1)
                    .build();

            client.putObject(putObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
