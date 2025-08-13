package com.jommo.mapper;

import com.jommo.domain.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jommo.dto.GoodsResult;
import org.apache.ibatis.annotations.Select;

/**
* @author 不会开发的小虾米
* @description 针对表【goods】的数据库操作Mapper
* @createDate 2025-04-16 09:38:56
* @Entity com.jommo.domain.Goods
*/
public interface GoodsMapper extends BaseMapper<Goods> {

    GoodsResult getUpdateInfo(Long id);

    void updateSale(Long goodsId, Integer goodsQuantity);

    @Select("SELECT COUNT(*) FROM goods WHERE publish_status = #{publishStatus} And is_deleted = 0")
    Integer getCountOfGoodsByPublishStatus(Integer publishStatus);
}




