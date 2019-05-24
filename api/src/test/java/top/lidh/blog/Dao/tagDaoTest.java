package top.lidh.blog.Dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class tagDaoTest {
    @Autowired
    private tagDao td;

    @Test
    public void get(){
        int id=td.getTag("标签1");
    }
}