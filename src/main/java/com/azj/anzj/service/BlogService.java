package com.azj.anzj.service;

import com.azj.anzj.pojo.Blog;
import com.azj.anzj.vo.BlogQuery;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface BlogService {

    //增加一个博客
    int addBlog(Blog blog);
    //删除一个博客
    int deleteBlog(int id);
    //修改一篇博客
    int updateBlog(Blog blog);

    //根据id查询一篇博客
    Blog getBlogById(int id);
    //用户前台显示只读博客
    Blog getReadOnlyBlogById(int id);
    //通过博客标题获取博客
    Blog getBlogByTitle(String title);
    //按条件查询出一条或多条博客
    PageInfo<Blog> queryBlogs(Integer pageNum, Integer pageSize, BlogQuery blog);
    PageInfo<Blog> queryBlogs(Integer pageNum, Integer pageSize,String query);
    PageInfo<Blog> queryTypesBlogs(Integer pageNum, Integer pageSize,Integer typeId);

    PageInfo<Blog> queryTagsBlogs(Integer pageNum, Integer pageSize,Integer tagId);

    //展示所有博客
    PageInfo<Blog> allBlogs(Integer pageNum, Integer pageSize);

    //归档展示博客
    Map<String,List<Blog>> archiveBlog();

    //前八篇推荐博客
    List<Blog> tuiJianList(Integer limit);

    Integer countBlogs();

}
