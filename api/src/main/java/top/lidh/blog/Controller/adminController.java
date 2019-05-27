package top.lidh.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lidh.blog.Dao.adminDao;
import top.lidh.blog.Entity.admin;
import top.lidh.blog.Util.jwtUtil;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class adminController {
    @Autowired
    private adminDao ad;

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
}
