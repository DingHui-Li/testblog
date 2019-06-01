package top.lidh.blog.Dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.lidh.blog.Entity.blog;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class blogDaoTest {
    @Autowired
    private blogDao bd;

    @Test
    public void getbyid(){
       blog re=bd.getById("1");
       if(re!=null)
       System.out.println(re.toString());
    }

    @Test
    public void getall(){
        List<blog> re=bd.getAll(0,10,"");
        System.out.println(re);
    }

    @Test
    public void add(){
        blog b=new blog();
        b.setTitle("addTest");
        int re=bd.addBlog(b);
        System.out.println(re);
    }

}