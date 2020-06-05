package com.qualintech.taskcentre.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qualintech.taskcentre.entity.Material;
import com.qualintech.taskcentre.mapper.MaterialMapper;
import com.qualintech.taskcentre.service.IMaterialService;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements IMaterialService {

}
