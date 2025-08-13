package com.jommo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jommo.common.Page;
import com.jommo.domain.GoodsAttribute;
import com.jommo.domain.GoodsAttributeCategory;
import com.jommo.dto.GoodsAttributeDTO;
import com.jommo.mapper.GoodsAttributeCategoryMapper;
import com.jommo.service.GoodsAttributeService;
import com.jommo.mapper.GoodsAttributeMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 不会开发的小虾米
 * @description 针对表【goods_attribute(商品属性表)】的数据库操作Service实现
 * @createDate 2025-04-02 15:59:48
 */
@Service
public class GoodsAttributeServiceImpl extends ServiceImpl<GoodsAttributeMapper, GoodsAttribute>
        implements GoodsAttributeService {

    @Autowired
    private GoodsAttributeMapper goodsAttributeMapper;
    @Autowired
    private GoodsAttributeCategoryMapper goodsAttributeCategoryMapper;

    @Override
    public GoodsAttribute findByName(String name, @NotNull Long goodsAttributeCategoryId) {
        LambdaQueryWrapper<GoodsAttribute> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsAttribute::getName, name).eq(GoodsAttribute::getGoodsAttributeCategoryId, goodsAttributeCategoryId);
        return goodsAttributeMapper.selectOne(queryWrapper);
    }

    @Override
    public void add(GoodsAttributeDTO attributeDTO) {
        GoodsAttribute attribute = new GoodsAttribute();
        BeanUtils.copyProperties(attributeDTO, attribute);
        goodsAttributeMapper.insert(attribute);
        GoodsAttributeCategory category = goodsAttributeCategoryMapper.selectById(attributeDTO.getGoodsAttributeCategoryId());
        category.setAttributeCount(category.getAttributeCount() + 1);
        goodsAttributeCategoryMapper.updateById(category);
    }

    @Override
    public Page<GoodsAttribute> getList(Long goodsAttributeCategoryId, Integer current, Integer size) {
        Map<String, Object> map = new HashMap<>();
        map.put("goodsAttributeCategoryId", goodsAttributeCategoryId);
        map.put("beginNo", (current - 1) * size);
        map.put("size", size);
        List<GoodsAttribute> list = goodsAttributeMapper.selectByGoodsAttributeCategoryIdForPage(map);
        Integer total = goodsAttributeMapper.selectCountByGoodsAttributeCategoryId(goodsAttributeCategoryId);
        Page<GoodsAttribute> p = new Page<>(total, list);
        return p;
    }

    @Override
    public void update(GoodsAttributeDTO attributeDTO) {
        GoodsAttribute attribute = new GoodsAttribute();
        BeanUtils.copyProperties(attributeDTO, attribute);
        goodsAttributeMapper.updateById(attribute);
    }

    @Override
    public void delete(Long id) {
        goodsAttributeMapper.deleteById(id);
    }
}




