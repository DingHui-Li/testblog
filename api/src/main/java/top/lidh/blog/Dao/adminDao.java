package top.lidh.blog.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.lidh.blog.Entity.admin;

@Repository
@Mapper
public interface adminDao {
    int login(admin ad);
}
