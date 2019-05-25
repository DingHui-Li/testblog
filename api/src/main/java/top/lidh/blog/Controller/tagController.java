package top.lidh.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.lidh.blog.Dao.tagDao;
import top.lidh.blog.Entity.tag;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tag")
public class tagController {
    @Autowired
    private tagDao td;

    @GetMapping("get_all_tag_num")
    public Map<String,Object> getAllTagNum(){
        Map<String,Object> result=new HashMap<>();
        try{
            List<tag> re=td.getAllAndNum();
            for(int i=0;i<re.size();i++){
                if(re.get(i).getIsnull().equals("-1")){
                    re.get(i).setNum("0");
                }
            }
            result.put("data",re);
            result.put("code","200");
        }catch (Exception e){
            result.put("code","-1");
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("update")
    public Map<String,Object> update(@RequestBody tag t){
        Map<String,Object> result=new HashMap<>();
        try{
            td.update(t);
            result.put("code","200");
        }catch (Exception e){
            result.put("code","-1");
            e.printStackTrace();
        }
        return result;
    }

    @DeleteMapping("delete")
    public Map<String,Object> delete(@RequestBody Map<String,Object> ids){
        Map<String,Object> result=new HashMap<>();
        try{
            td.delete((List)ids.get("ids"));
            result.put("code","200");
        }catch (Exception e){
            result.put("code","-1");
            e.printStackTrace();
        }
        return result;
    }
}
