package top.lidh.blog.Dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.lidh.blog.Entity.visitor;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class visitorDaoTest {
    @Autowired
    private visitorDao vd;

    @Test
    public void get(){
        List<visitor> re=vd.getData();
        for(int i=0;i<re.size();i++){
            System.out.println(re.get(i));
        }
    }
}