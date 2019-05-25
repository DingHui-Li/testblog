package top.lidh.blog.Dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import top.lidh.blog.Entity.visitor;

import java.util.List;

@Repository
@Mapper
public interface visitorDao {
    int addVisitor(visitor visit);//添加一个访问记录
    int getNum();//获取访问总数
    List<visitor> getData();//获取每一天的访问数量/30天
}
