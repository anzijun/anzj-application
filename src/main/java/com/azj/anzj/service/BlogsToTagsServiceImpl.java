package com.azj.anzj.service;

import com.azj.anzj.mapper.BlogsToTagsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BlogsToTagsServiceImpl implements BlogsToTagsService {

    @Autowired
    private BlogsToTagsMapper blogsToTagsMapper;

    @Transactional
    @Override
    public int deleteRel(Integer blogId){
        return blogsToTagsMapper.deleteRel(blogId);
    }

    @Transactional
    @Override
    public int addRel(Integer blogId, List<Integer> tagIds) {
        return blogsToTagsMapper.addRel(blogId,tagIds);
    }

}
