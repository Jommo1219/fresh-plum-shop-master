package com.jommo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jommo.domain.GoodsCategory;
import com.jommo.dto.GoodsCategoryDTO;
import com.jommo.dto.GoodsCategoryWithChildrenItem;
import com.jommo.service.GoodsCategoryService;
import com.jommo.mapper.CategoryMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 不会开发的小虾米
 * @description 针对表【category】的数据库操作Service实现
 * @createDate 2025-03-31 12:12:02
 */
@Service
public class GoodsCategoryServiceImpl extends ServiceImpl<CategoryMapper, GoodsCategory>
        implements GoodsCategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(GoodsCategoryDTO goodsCategoryDTO) {
        //封装
        GoodsCategory c = new GoodsCategory();
        c.setName(goodsCategoryDTO.getName());
        c.setParentId(goodsCategoryDTO.getParentId());
        if (c.getParentId() == 0) {
            c.setLevel(1);
        } else {
            c.setLevel(2);
        }
        c.setSort(goodsCategoryDTO.getSort());
        categoryMapper.insert(c);
    }

    @Override
    public GoodsCategory findByName(String name) {
        LambdaQueryWrapper<GoodsCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsCategory::getName, name);
        return categoryMapper.selectOne(queryWrapper);
    }

    @Override
    public Page<GoodsCategory> getList(Long parentId, Integer current, Integer size) {
        LambdaQueryWrapper<GoodsCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsCategory::getParentId, parentId).orderByDesc(GoodsCategory::getSort);
        Page<GoodsCategory> page = categoryMapper.selectPage(new Page<>(current, size), queryWrapper);
        return page;
    }

    @Override
    public void update(GoodsCategoryDTO goodsCategoryDTO) {
        //封装
        GoodsCategory c = new GoodsCategory();
        BeanUtils.copyProperties(goodsCategoryDTO, c);
        if (c.getParentId() == 0) {
            c.setLevel(1);
        } else {
            c.setLevel(2);
        }
        //更新分类
        categoryMapper.updateById(c);
    }

    @Override
    public void delete(@RequestParam Long id) {
        LambdaQueryWrapper<GoodsCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsCategory::getId, id).or().eq(GoodsCategory::getParentId, id);
        categoryMapper.delete(queryWrapper);
    }

    @Override
    public List<GoodsCategoryWithChildrenItem> listWithChildren() {
        return categoryMapper.listWithChildren();
    }
}




