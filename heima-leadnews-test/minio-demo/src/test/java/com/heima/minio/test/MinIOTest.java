package com.heima.minio.test;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;

/**
 * TODO
 *
 * @author yaoh
 */
@SpringBootTest
@RunWith(Runner.class)
public class MinIOTest {

    public void upload() {
        try {
            FileInputStream inputStream = new FileInputStream("/Users/yaoh/list.html");

            MinioClient client = MinioClient
                    .builder()
                    .credentials("minioadmin", "minioadmin")
                    .endpoint("http://101.34.245.48:9000")
                    .build();

            PutObjectArgs
                    .builder()
                    .object("list.html")
                    .contentType("text/html")
                    .bucket("hmtt")
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
