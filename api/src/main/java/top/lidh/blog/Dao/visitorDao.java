package top.lidh.blog.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.lidh.blog.Entity.visitor;

import java.util.List;

@Repository
@Mapper
public interface visitorDao {
    int addVisitor(visitor visit);
    int getNum();
    List<visitor> getData();
}
