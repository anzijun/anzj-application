package com.azj.anzj.service;

import com.azj.anzj.mapper.MusicMapper;
import com.azj.anzj.pojo.Music;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author anzj
 * @date 2021/12/12 22:56
 */
@Service
public class MusicServiceImpl implements MusicService{

    @Autowired
    private MusicMapper musicMapper;

    @Override
    public List<Music> findMusicList() {
        return musicMapper.findMusicList();
    }

    @Override
    public Music getCurrMusic() {
        return musicMapper.getCurrMusic();
    }

    @Override
    public Music getMusicById(Integer id) {
        return musicMapper.getMusicById(id);
    }

    @Override
    public Integer getMaxId() {
        return musicMapper.getMaxId();
    }
}
