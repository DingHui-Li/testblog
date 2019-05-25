package top.lidh.blog.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.lidh.blog.Entity.comment;

import java.util.List;

@Repository
@Mapper
public interface commentDao {
    int add(comment c);//添加一条评论
    List<comment> get(String id,int offset,int limit);//根据blogid获取评论
    List<comment> getLately();//获取前6条评论
}
