package com.azj.anzj.service;

import com.azj.anzj.pojo.Music;

import java.util.List;

/**
 * @author anzj
 * @date 2021/12/12 22:50
 */
public interface MusicService {
    List<Music> findMusicList();
    Music getCurrMusic();
    Music getMusicById(Integer id);
    Integer getMaxId();
}
