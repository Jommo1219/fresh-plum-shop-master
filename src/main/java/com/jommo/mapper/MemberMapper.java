package com.jommo.mapper;

import com.jommo.domain.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
* @author 不会开发的小虾米
* @description 针对表【member】的数据库操作Mapper
* @createDate 2025-04-26 17:39:48
* @Entity com.jommo.domain.Member
*/
public interface MemberMapper extends BaseMapper<Member> {
    @Select("SELECT COUNT(*) FROM member WHERE DATE(create_time) = CURDATE() AND is_deleted = 0")
    Integer getCountOfTodayNewMembers();

    @Select("SELECT COUNT(*) FROM member WHERE DATE(create_time) = DATE_SUB(CURDATE(),INTERVAL 1 DAY) AND is_deleted = 0")
    Integer getCountOfYesterdayNewMembers();

    @Select("SELECT COUNT(*) FROM member WHERE DATE_FORMAT(create_time, '%Y-%m') = DATE_FORMAT(CURDATE(), '%Y-%m') AND is_deleted = 0")
    Integer getCountOfMonthNewMembers();

    @Select("SELECT COUNT(*) FROM member WHERE is_deleted = 0")
    Integer getCountOfMember();
}




