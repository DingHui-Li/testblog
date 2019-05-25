package top.lidh.blog.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.lidh.blog.Entity.tag;

import java.util.List;

@Repository
@Mapper
public interface tagDao {
     List<tag> getAll();//获取所有标签名
     int addTag(tag t);//添加一个标签
     int getTag(String name);//根据标签名获取标签id
     int addTagMap(String[] tagid,String blogid);//添加blog与标签映射
     int getNum();//获取标签总数

     List<tag> getAllAndNum();//获取每一个标签的信息和对应的blog数量
     int update(tag t);//更新一个tag
     int delete(List ids);//删除若干个tag
     int deletebyblog(String blogid);//根据blogid删除tag_map===>用于更新blog标签
}
