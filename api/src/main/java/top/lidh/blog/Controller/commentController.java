package top.lidh.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.lidh.blog.Dao.commentDao;
import top.lidh.blog.Entity.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class commentController {
    @Autowired
    private commentDao cd;

    @PostMapping("/add")
    public Map<String,Object> add(@RequestBody comment c){
        Map<String,Object>result=new HashMap<>();
        try{
            cd.add(c);
            result.put("code","200");
        }catch(Exception e){
            result.put("code","-1");
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/get")
    public Map<String,Object> get(String id,int offset,int limit){
        Map<String,Object>result=new HashMap<>();
        try{
           List<comment>re=cd.get(id,offset,limit);
           result.put("data",re);
            result.put("code","200");
        }catch(Exception e){
            result.put("code","-1");
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/getLately")
    public Map<String,Object> getLately(){
        Map<String,Object>result=new HashMap<>();
        try{
            List<comment>re=cd.getLately();
            result.put("data",re);
            result.put("code","200");
        }catch(Exception e){
            result.put("code","-1");
            e.printStackTrace();
        }
        return result;
    }
}
