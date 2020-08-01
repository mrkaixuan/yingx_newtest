package com.hkx.test;

import com.hkx.dao.VideoMapper;
import com.hkx.entity.VideoDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestInterface {
    @Resource
    VideoMapper videoMapper ;
    @Test
    public void test(){
        List<VideoDTO> videoDTOS = videoMapper.queryByReleaseTime();
        for (VideoDTO videoDTO : videoDTOS) {
            System.out.println(videoDTO);
        }
    }
}
