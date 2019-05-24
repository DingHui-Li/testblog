package top.lidh.blog.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.lidh.blog.Entity.blog;

import java.util.List;

@Repository
@Mapper
public interface blogDao {
    blog getById(String id);
    List<blog> getAll(int offset,int limit);
    int addBlog(blog b);
    int getNum();
}
