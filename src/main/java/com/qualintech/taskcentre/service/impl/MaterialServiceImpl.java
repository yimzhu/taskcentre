package com.qualintech.taskcentre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.enums.DelegateState;
import com.qualintech.taskcentre.enums.MaterialState;
import com.qualintech.taskcentre.enums.ReviewState;
import com.qualintech.taskcentre.mapper.MaterialMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author yimzhu
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> {
    @Autowired
    private MaterialMapper materialMapper;

    public Material create(Long userId){
        Material material = new Material();
        material.setOwnerId(userId);
        material.setState(MaterialState.INIT);
        material.setInsertTime(LocalDateTime.now());

        /** 初始化 delegate flag 0-关闭 1-开启 */
        material.setDelegateFlag(0);
        material.setDelegateState(DelegateState.CLOSE);

        /** 初始化 review flag 0-关闭 1-开启 */
        material.setReviewFlag(0);
        material.setReviewState(ReviewState.CLOSE);

        materialMapper.insert(material);

        return material;
    }
}
