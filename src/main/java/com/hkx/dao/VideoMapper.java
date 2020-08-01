package com.hkx.dao;

import com.hkx.entity.Video;
import com.hkx.entity.VideoDTO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface VideoMapper extends Mapper<Video> {
    List<VideoDTO> queryByReleaseTime();
}