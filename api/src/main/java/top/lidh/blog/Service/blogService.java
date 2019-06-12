package top.lidh.blog.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import top.lidh.blog.Dao.blogDao;
import top.lidh.blog.Dao.tagDao;
import top.lidh.blog.Entity.blog;
import top.lidh.blog.Entity.tag;
import top.lidh.blog.Util.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class blogService {
    @Autowired
    private blogDao bd;
    @Autowired
    private tagDao td;

    @Cacheable("getbyid")
    public Map<String,Object> getByid(String id){
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

    @Cacheable("getAll")
    public Map<String,Object> getAll(int offset,int limit,String key){
        System.out.println("没有缓存");
        Map<String,Object> result=new HashMap<>();
        try{
            key="%"+key+"%";
            List<blog> list=bd.getAll(offset,limit,key);
            result.put("code","200");
            result.put("data",list);
        }catch (Exception e){
            result.put("code","-1");
            e.printStackTrace();
        }
        return  result;
    }

    @Cacheable("getalltag")
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

    public Map<String,Object> add(@RequestBody blog b){
        Map<String,Object> result=new HashMap<>();
        System.out.println(b.getType());
        try{
            if("new".equals(b.getType())) {
                bd.addBlog(b);
            }else if("update".equals(b.getType())){
                bd.update(b);
                td.deletebyblog(b.getId());
            }
            List<tag> tags = td.getAll();
            String tagStr = b.getTag();
            String[] tagList=new util().getTagArray(tagStr,tags,td);
            td.addTagMap(tagList, b.getId());
            result.put("code", "200");

        }catch (Exception e){
            result.put("code","-1");
            e.printStackTrace();
        }
        return result;
    }

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

    @Cacheable("getnum")
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

    @Cacheable("getbytag")
    public Map<String,Object> getbytag(@RequestBody Map ids){
        Map<String,Object> result=new HashMap<>();
        try{
            List<Integer> idlist=(List) ids.get("ids");
            Collections.sort(idlist);
            String likeStr="";
            for(int i=0;i<idlist.size();i++){
                likeStr+="%"+idlist.get(i);
            }
            likeStr+="%";
            List<blog> re=bd.getbyTag(likeStr);
            result.put("code","200");
            result.put("data",re);
        }catch (Exception e){
            result.put("code","-1");
            e.printStackTrace();
        }
        return result;
    }
}
