package top.lidh.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.lidh.blog.Dao.adminDao;
import top.lidh.blog.Dao.websiteDao;
import top.lidh.blog.Entity.admin;
import top.lidh.blog.Entity.website;
import top.lidh.blog.Util.jwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class adminController {
    @Autowired
    private adminDao ad;
    @Autowired
    private websiteDao wd;

    @PostMapping("/login")
    public Map<String,Object> login(@RequestBody admin a){
        Map<String,Object> result=new HashMap<>();
        try{
            if(ad.login(a)==1){
                String token=new jwtUtil().createToken(a);
                result.put("token",token);
                result.put("code","200");
            }else{
                result.put("code","-1");
            }
        }catch (Exception e){
            result.put("code","-1");
            result.put("msg",e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/islogin")
    public Map<String,Object> isLogin(String token){
        Map<String,Object> result =new HashMap<>();
        if(token!=null&&token!="") {
            jwtUtil jwt = new jwtUtil();
            boolean re = jwt.verify(token);
            if (re) {
                result.put("code", "200");
            } else result.put("code", "-1");
        }else result.put("code", "-1");
        return result;
    }
    @PostMapping("/set")
    public Map<String,Object> set(@RequestBody website w){
        Map<String,Object> result=new HashMap<>();
        try{
            wd.set(w);
            result.put("code","200");
        }catch (Exception e){
            result.put("code", "-1");
            e.printStackTrace();
        }
        return result;
    }
    @GetMapping("/get")
    public  Map<String,Object> get(){
        Map<String,Object> result=new HashMap<>();
        try{
            website re=wd.get();
            result.put("data",re);
            result.put("code","200");
        }catch (Exception e){
            result.put("code","-1");
            e.printStackTrace();
        }
        return result;
    }
}
