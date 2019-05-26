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
    List<comment> getAll(int offset,int limit);//获取若干条评论
    int getNum();//获取评论总数
    int delete(List ids);
}
