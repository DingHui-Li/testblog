package top.lidh.blog.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.lidh.blog.Entity.website;

@Repository
@Mapper
public interface websiteDao {
    int set(website w);
    website get();
}
