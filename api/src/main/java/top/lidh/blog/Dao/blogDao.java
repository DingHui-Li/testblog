package top.lidh.blog.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.lidh.blog.Entity.blog;

import java.util.List;

@Repository
@Mapper
public interface blogDao {
    blog getById(String id);//根据id获取一个blog
    List<blog> getAll(int offset,int limit);//获取一定数量的blog信息/不包括content字段
    int addBlog(blog b);//添加一个blog
    int getNum();//获取blog总数
    int deleteBlog(String[] id);//根据id删除若干blog
    int update(blog b);//更新一个blog
    List<blog> getbyTag(String likeStr);//根据标签获取blog
}
