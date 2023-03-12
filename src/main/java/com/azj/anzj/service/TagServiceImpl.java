package com.azj.anzj.service;

import com.azj.anzj.mapper.TagMapper;
import com.azj.anzj.pojo.Tag;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService{

    @Autowired
    private TagMapper tagMapper;

    @Transactional
    @Override
    public int addTag(Tag tag) {
        return tagMapper.addTag(tag);
    }
    @Transactional
    @Override
    public int deleteTag(Integer id) {
        return tagMapper.deleteTag(id);
    }
    @Transactional
    @Override
    public int updateTag(Tag tag) {
        return tagMapper.updateTag(tag);
    }

    @Override
    public Tag getTagById(Integer id) {
        return tagMapper.getTagById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    @Override
    public PageInfo<Tag> queryTagPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Tag> tags = tagMapper.queryAllTag();
        PageInfo<Tag> tagPageInfo = new PageInfo<>(tags,pageSize);
        return tagPageInfo;
    }

    @Override
    public List<Tag> listTag() {
        return tagMapper.queryAllTag();
    }



    @Override
    public List<Tag> listTag(String ids) {
        return tagMapper.listTag(convertToList(ids));
    }

    @Override
    public List<Tag> listTopTag(Integer limit) {

        return tagMapper.listTopTag(limit);
    }

    private List<Integer> convertToList(String ids){
        List<Integer> list = new ArrayList<>();
        if(!"".equals(ids) && ids !=null){
            String[] idarr = ids.split(",");
            for(int i=0; i<idarr.length; i++){
                list.add(new Integer(idarr[i]));
            }
        }
        return list;
    }
}
