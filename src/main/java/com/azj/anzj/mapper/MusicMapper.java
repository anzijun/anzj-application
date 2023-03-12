package com.azj.anzj.mapper;

import com.azj.anzj.pojo.Music;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author anzj
 * @date 2021/12/12 22:56
 */
@Mapper
@Repository
public interface MusicMapper {

    List<Music> findMusicList();
    Music getCurrMusic();
    Music getMusicById(Integer id);
    Integer getMaxId();
}
