package com.jommo.mapper;

import com.jommo.domain.HomeAdvertise;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author 不会开发的小虾米
* @description 针对表【home_advertise(首页广告表)】的数据库操作Mapper
* @createDate 2025-04-23 12:11:31
* @Entity com.jommo.domain.HomeAdvertise
*/
public interface HomeAdvertiseMapper extends BaseMapper<HomeAdvertise> {
    @Select("SELECT COUNT(*) from home_advertise")
    Integer getCountOfHomeAdvertise();
}




