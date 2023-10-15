package com.heima.test;

import com.heima.common.aliyun.ImageScan;
import com.heima.common.aliyun.TextScan;
import com.heima.file.service.FileStorageService;
import com.heima.model.common.enums.AliImageServiceCode;
import com.heima.model.common.enums.AliTextServiceCode;
import com.heima.wemedia.WemediaApplication;
import com.heima.wemedia.service.WmNewsAutoScanService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * TODO
 *
 * @author yaoh
 */
@SpringBootTest(classes = WemediaApplication.class)
@RunWith(SpringRunner.class)
public class MediaTest {

    @Autowired
    private ImageScan imageScan;

    @Autowired
    private TextScan textScan;

    @Test
    public void textScan() throws Exception {
        Map result = textScan.scan("可乐,冰毒", AliTextServiceCode.COMMENT_DETECTION.getServiceCode());
        System.err.println(result);
    }

    @Autowired
    private WmNewsAutoScanService wmNewsAutoScanService;

    @Test
    public void autoScanWmNews() {
        wmNewsAutoScanService.autoScanWmNews(6241);
    }

    @Test
    public void imageScan() throws Exception {
        Map result = imageScan.scan("test", new FileInputStream(new File("/Users/yaoh/test.jpg")), AliImageServiceCode.BASELINE_CHECK.getServiceCode());
        System.out.println(result);
    }

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void download() {
        InputStream inputStream = fileStorageService.downLoadFile("http://101.34.245.48:9000/hmtt/2023/10/15/5846048d08474d10b2eea6259be5b622.jpg", InputStream.class);

        try (FileOutputStream outputStream = new FileOutputStream(new File("/Users/yaoh/download.jpg"))) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead=inputStream.read(buffer))!=-1){
                outputStream.write(buffer,0,bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
