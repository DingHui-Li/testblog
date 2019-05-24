package top.lidh.blog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.lidh.blog.Dao.blogDao;
import top.lidh.blog.Dao.tagDao;
import top.lidh.blog.Entity.blog;
import top.lidh.blog.Entity.tag;

import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/blog")
public class blogController {
    @Autowired
    private blogDao bd;
    @Autowired
    private tagDao td;

    @GetMapping("/getbyid")
    public Map<String,Object> getById(String id){
        Map<String,Object> result=new HashMap<>();
        try{
            blog b=bd.getById(id);
            result.put("code","200");
            result.put("data",b);
        }catch(Exception e){
            result.put("code","-1");
        }
        return result;
    }

    @GetMapping("/getall")
    public Map<String,Object> getall(int offset,int limit){
        Map<String,Object> result=new HashMap<>();
        try{
            List<blog>  list=bd.getAll(offset,limit);
            result.put("code","200");
            result.put("data",list);
        }catch (Exception e){
            result.put("code","-1");
        }
        return  result;
    }
    @GetMapping("/getalltag")
    public Map<String,Object> getalltag(){
        Map<String,Object> result=new HashMap<>();
        try{
            List<tag>  list=td.getAll();
            result.put("code","200");
            result.put("data",list);
        }catch (Exception e){
            result.put("code","-1");
        }
        return  result;
    }

    @PostMapping("/add")
    public Map<String,Object> add(@RequestBody blog b){
        Map<String,Object> result=new HashMap<>();
        System.out.println(b.getType());
        try{
            if("new".equals(b.getType())) {
                bd.addBlog(b);
            }else if("update".equals(b.getType())){
                bd.update(b);
            }
                List<tag> tags = td.getAll();
                String tagStr = b.getTag();
                String[] tagList = tagStr.split(",");
                boolean exist = false;
                for (int i = 0; i < tagList.length; i++) {
                    exist = false;
                    for (int j = 0; j < tags.size(); j++) {
                        if (tagList[i].equals(tags.get(j).getName())) {
                            exist = true;
                            break;
                        }
                    }
                    if (exist) {//如果标签已存在，则替换为标签id
                        tagList[i] = td.getTag(tagList[i]) + "";
                    } else {//若不存在，则添加后再替换为标签id
                        tag t = new tag();
                        t.setName(tagList[i]);
                        td.addTag(t);
                        tagList[i] = t.getId();
                    }
                }
                td.addTagMap(tagList, b.getId());
                result.put("code", "200");

        }catch (Exception e){
            result.put("code","-1");
            System.out.println(e);
        }
        return result;
    }

    @PostMapping("/delete")
    public Map<String,Object>deleteBlog(String ids){
        Map<String,Object> result=new HashMap<>();
        try{
            ids=ids.replaceAll("[\\[\\]\"]","");
            String[] id=ids.split(",");
            bd.deleteBlog(id);
            result.put("code","200");
        }catch (Exception e){
            result.put("code","-1");
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/upload")
    public  Map<String,Object>uploadImg(@RequestParam("file") MultipartFile file,String type){
        Map<String,Object> result=new HashMap<>();
        try{
            String fileName=file.getOriginalFilename();
            String imgtype=fileName.indexOf(".")!=-1?fileName.substring(fileName.lastIndexOf(".")+1, fileName.length()):null;
            String name= new Date().getTime()+"."+imgtype;
            String realPAth= ClassUtils.getDefaultClassLoader().getResource("").getPath();
            String path="";
            if("img".equals(type)){
                path=realPAth+"static/blogImg/"+name;
                result.put("path","/blogImg/"+name);
            }else {
                path=realPAth+"static/cover/"+name;
                result.put("path","/cover/"+name);
            }
            file.transferTo(new File(path));
            result.put("code","200");
        } catch (IOException e) {
            result.put("code","-1");
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("getnum")
    public Map<String,Object> getnum(){
        Map<String,Object> result=new HashMap<>();
        try{
            int blognum=bd.getNum();
            int tagnum=td.getNum();
            result.put("blognum",blognum);
            result.put("tagnum",tagnum);
            result.put("code","200");
        }catch (Exception e){
            result.put("code","-1");
            e.printStackTrace();
        }
        return result;
    }
}
