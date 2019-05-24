package top.lidh.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.lidh.blog.Dao.visitorDao;
import top.lidh.blog.Entity.visitor;

import java.awt.print.PrinterException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/visit")
public class visitorController {
    @Autowired
    private visitorDao vd;

    @PostMapping("/add")
    public Map<String,Object> addVisitor(@RequestBody visitor visit){
        Map<String,Object> result=new HashMap<>();
        try{
            vd.addVisitor(visit);
            result.put("code","200");
        }catch (Exception e){
            result.put("code","-1");
            System.out.println(e.getMessage());
        }
        return result;
    }

    @GetMapping("/getnum")
    public Map<String,Object> getnum(){
        Map<String,Object> result=new HashMap<>();
        try{
            int num=vd.getNum();
            result.put("num",num);
            result.put("code","200");
        }catch (Exception e){
            System.out.println(e.getMessage());
            result.put("code","-1");
        }
        return result;
    }
}
