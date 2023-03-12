package com.azj.anzj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    private Integer id;//id
    private String title;//标题
    private String content;//内容
    private String firstPicture;//首图
    private String flag;//原创转载翻译标志
    private Integer viewCount;//浏览次数
    private Integer typeId; //分类id
    private boolean appreciation;//是否开启赞赏
    private boolean shareStatement;//是否开启转载
    private boolean commentabled;//是否开启评论
    private boolean published;//是否发布
    private boolean recommend;//是否推荐
    private Date createTime;//创建时间
    private Date updateTime;//更新时间
    private Type type;//分类
    private String description;//博客摘要描述
    private List<Tag> tags;

    private User user;//作者

    private List<Comment> comments;//评论
    //标签ids
    @Transient
    private String tagIds;
    //将从数据库内查到的标签集合的id转换为字符串
    public void init(){
        this.tagIds = tagsToIds(this.getTags());
    }

    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
           StringBuffer ids = new StringBuffer();
           boolean flag = false;
           for(Tag tag : tags) {
                if(flag){
                    ids.append(",");
                }else{
                    flag = true;
                }
                ids.append(tag.getId());
           }
           return ids.toString();
        }else{
            return tagIds;
        }
    }


}
