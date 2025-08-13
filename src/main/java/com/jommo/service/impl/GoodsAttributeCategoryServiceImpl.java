package com.jommo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jommo.domain.GoodsAttribute;
import com.jommo.domain.GoodsAttributeCategory;
import com.jommo.dto.GoodsAttributeCategoryDTO;
import com.jommo.mapper.GoodsAttributeMapper;
import com.jommo.service.GoodsAttributeCategoryService;
import com.jommo.mapper.GoodsAttributeCategoryMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author 不会开发的小虾米
* @description 针对表【goods_attribute_category(产品属性分类表)】的数据库操作Service实现
* @createDate 2025-04-02 15:19:32
*/
@Service
public class GoodsAttributeCategoryServiceImpl extends ServiceImpl<GoodsAttributeCategoryMapper, GoodsAttributeCategory>
    implements GoodsAttributeCategoryService{

    @Autowired
    private GoodsAttributeCategoryMapper goodsAttributeCategoryMapper;

    @Autowired
    private GoodsAttributeMapper goodsAttributeMapper;

    @Override
    public void add(GoodsAttributeCategoryDTO categoryDTO) {
        GoodsAttributeCategory goodsAttributeCategory = new GoodsAttributeCategory();
        BeanUtils.copyProperties(categoryDTO, goodsAttributeCategory);
        goodsAttributeCategoryMapper.insert(goodsAttributeCategory);
    }

    @Override
    public Page<GoodsAttributeCategory> getList(Integer current, Integer size) {
        Page<GoodsAttributeCategory> page = new Page<>(current, size);
        return goodsAttributeCategoryMapper.selectPage(page, null);
    }

    @Override
    public void update(GoodsAttributeCategoryDTO categoryDTO) {
        GoodsAttributeCategory goodsAttributeCategory = new GoodsAttributeCategory();
        BeanUtils.copyProperties(categoryDTO, goodsAttributeCategory);
        goodsAttributeCategoryMapper.updateById(goodsAttributeCategory);
    }

    @Override
    public GoodsAttributeCategory findByName(String name) {
        LambdaQueryWrapper<GoodsAttributeCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsAttributeCategory::getName, name);
        return goodsAttributeCategoryMapper.selectOne(queryWrapper);
    }

    @Override
    public void delete(Long id) {
        LambdaQueryWrapper<GoodsAttribute> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsAttribute::getGoodsAttributeCategoryId, id);
        goodsAttributeMapper.delete(queryWrapper);
        goodsAttributeCategoryMapper.deleteById(id);
    }
}




