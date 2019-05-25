package top.lidh.blog.Util;

import org.springframework.beans.factory.annotation.Autowired;
import top.lidh.blog.Dao.tagDao;
import top.lidh.blog.Entity.tag;

import java.util.List;

public class util {
    public String[] getTagArray(String tagStr, List<tag> tags,tagDao td){
        String[] tagList = tagStr.split(",");
        boolean exist = false;
        for (int i = 0; i < tagList.length; i++) {
            exist = false;
            for (int j = 0; j < tags.size(); j++) {
                if (tagList[i].equals(tags.get(j).getName())) {
                    exist = true;
                    break;
                }
            }
            if (exist) {//如果标签已存在，则替换为标签id
                tagList[i] = td.getTag(tagList[i]) + "";
            } else {//若不存在，则添加后再替换为标签id
                tag t = new tag();
                t.setName(tagList[i]);
                td.addTag(t);
                tagList[i] = t.getId();
            }
        }
        return tagList;
    }
}
