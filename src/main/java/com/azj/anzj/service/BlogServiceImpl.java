package com.azj.anzj.service;

import com.azj.anzj.NotFoundException;
import com.azj.anzj.mapper.BlogMapper;
import com.azj.anzj.pojo.Blog;
import com.azj.anzj.utils.MarkdowmUtils;
import com.azj.anzj.vo.BlogQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogMapper blogMapper;

    @Transactional
    @Override
    public int addBlog(Blog blog) {
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViewCount(0);
        return blogMapper.addBlog(blog);
    }

    @Override
    public Blog getBlogByTitle(String title) {
        return blogMapper.getBlogByTitle(title);
    }

    @Transactional
    @Override
    public int deleteBlog(int id) {
        return blogMapper.deleteBlog(id);
    }

    @Transactional
    @Override
    public int updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        blog.setViewCount(0);
        return blogMapper.updateBlog(blog);
    }

    @Override
    public Blog getBlogById(int id) {
        return blogMapper.getBlogTagsById(id);
    }

    //index页面查询一篇笔记
    @Transactional
    @Override
    public Blog getReadOnlyBlogById(int id) {

        Blog blog = blogMapper.getBlogTagsById(id);
        if(blog == null)
        {
            throw new NotFoundException("查找的文章不存在");
        }
        String content = blog.getContent();
        blog.setContent(MarkdowmUtils.markdownToHtmlExtensions(content));
        blogMapper.updateViewCount(id);

        return blog;
    }

    @Override
    public PageInfo<Blog> queryBlogs(Integer pageNum, Integer pageSize, BlogQuery blog) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.queryBlogs(blog);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs,pageSize);
        return blogPageInfo;
    }

    @Override
    public PageInfo<Blog> queryBlogs(Integer pageNum, Integer pageSize,String query) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.queryIndexBlogs(query);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs,pageSize);
        return blogPageInfo;
    }

    @Override
    public PageInfo<Blog> queryTypesBlogs(Integer pageNum, Integer pageSize,Integer typeId) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.queryTypesBlogs(typeId);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs,pageSize);
        return blogPageInfo;
    }

    @Override
    public PageInfo<Blog> queryTagsBlogs(Integer pageNum, Integer pageSize, Integer tagId) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.getTagBlogByTagId(tagId);
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs,pageSize);
        return blogPageInfo;
    }

    @Override
    public PageInfo<Blog> allBlogs(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogs = blogMapper.allBlogs();
        PageInfo<Blog> blogPageInfo = new PageInfo<>(blogs,pageSize);
        return blogPageInfo;
    }
    @Override
    public List<Blog> tuiJianList(Integer limit){
        return blogMapper.tuiJianList(limit);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {

        Map<String,List<Blog>> map = new HashMap<>();

        List<String> years = blogMapper.queryYear();
        for(String year : years){
            map.put(year,blogMapper.queryBlogByYear(year));
        }

        return map;
    }

    @Override
    public Integer countBlogs() {
        return blogMapper.countBlogs();
    }
}
