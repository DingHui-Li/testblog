package top.lidh.blog.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.lidh.blog.Entity.tag;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface tagDao {
     List<tag> getAll();
     int addTag(tag t);
     int getTag(String name);
     int addTagMap(String[] tagid,String blogid);
     int getNum();
}
