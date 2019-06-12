package top.lidh.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.lidh.blog.Dao.blogDao;
import top.lidh.blog.Dao.tagDao;
import top.lidh.blog.Dao.websiteDao;
import top.lidh.blog.Entity.blog;
import top.lidh.blog.Entity.tag;
import top.lidh.blog.Entity.website;
import top.lidh.blog.Service.blogService;
import top.lidh.blog.Util.util;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

@RestController
@RequestMapping("/blog")
public class blogController {
    @Autowired
    private blogService bs;

    @GetMapping("/getbyid")
    public Map<String,Object> getById(String id){
        return bs.getByid(id);
    }

    @GetMapping("/getall")
    public Map<String,Object> getall(int offset,int limit,String key){
       return bs.getAll(offset,limit,key);
    }
    @GetMapping("/getalltag")
    public Map<String,Object> getalltag(){
       return bs.getalltag();
    }

    @PostMapping("/add")//add or update
    public Map<String,Object> add(@RequestBody blog b){
       return bs.add(b);
    }

    @PostMapping("/delete")
    public Map<String,Object>deleteBlog(String ids){
        return bs.deleteBlog(ids);
    }
    @GetMapping("getnum")
    public Map<String,Object> getnum(){
       return bs.getnum();
    }

    @PostMapping("/getbytag")
    public Map<String,Object> getbytag(@RequestBody Map ids){
        return bs.getbytag(ids);
    }
}
