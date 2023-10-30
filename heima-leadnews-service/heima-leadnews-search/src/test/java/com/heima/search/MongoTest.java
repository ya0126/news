package com.heima.search;

import com.heima.search.pojos.ApUserSearch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest(classes = SearchApplication.class)
@RunWith(SpringRunner.class)
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void save() {
        ApUserSearch apUserSearch = new ApUserSearch();
        apUserSearch.setUserId(1);
        apUserSearch.setKeyword("你好");
        mongoTemplate.save(apUserSearch);
    }
}
