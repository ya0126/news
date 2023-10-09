package com.heima.test;

import com.heima.common.aliyun.NewGreenImageScan;
import com.heima.common.aliyun.NewGreenTextScan;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AliImageServiceCode;
import com.heima.model.common.enums.AliTextServiceCode;
import com.heima.wemedia.WemediaApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * TODO
 *
 * @author yaoh
 */
@SpringBootTest(classes = WemediaApplication.class)
@RunWith(SpringRunner.class)
public class AliyunTest {

    @Autowired
    private NewGreenTextScan newGreenTextScan;


    @Autowired
    private NewGreenImageScan newGreenImageScan;

    @Test
    public void testTextScan() throws Exception {
        ResponseResult responseResult = newGreenTextScan.textScan("我是一个好人，冰毒", AliTextServiceCode.AD_COMPLIANCE_DETECTION.getServiceCode());
        System.out.println(responseResult);
    }

    @Test
    public void testImageScan() throws Exception {
        ResponseResult responseResult = newGreenImageScan.imageScan("http://101.34.245.48:9090/hmtt/2023/10/08/18f4efb635c94f8fbed11c5ba0db13cb.jpg", AliImageServiceCode.ADVERTISING_CHECK.getServiceCode());
    }
}
