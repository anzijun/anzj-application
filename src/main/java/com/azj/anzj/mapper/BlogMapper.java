package com.azj.anzj.mapper;

import com.azj.anzj.pojo.Blog;
import com.azj.anzj.vo.BlogQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BlogMapper {
    //新增博客
    int addBlog(Blog blog);
    //删除博客
    int deleteBlog(int id);
    //修改博客
    int updateBlog(Blog blog);
    //通过id查询博客
    Blog getBlogById(int id);
    //根据标题查询博客
    Blog getBlogByTitle(String title);
    //查询全部博客
    List<Blog> tuiJianList(@Param("limit") Integer limit);
    //条件查询博客
    List<Blog> queryBlogs(BlogQuery blog);
    //条件查询博客
    List<Blog> queryIndexBlogs(@Param("query") String query);
    //根据分类ID查询id下的所有博客
    List<Blog> queryTypesBlogs(@Param("typeId") Integer typeId);
    List<Blog> allBlogs();

    //根据博客id查询所有标签
    Blog getBlogTagsById(int id);

    List<Blog> getTagBlogByTagId(@Param("tagId") Integer id);
    //根据index中请求blog/{id}提交次数更新浏览此数
    int updateViewCount(int id);

    List<String> queryYear();
    List<Blog> queryBlogByYear(@Param("year") String year);

    int countBlogs();
}
