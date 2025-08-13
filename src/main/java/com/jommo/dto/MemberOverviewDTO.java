package com.jommo.dto;


import lombok.Data;

/**
 * @author 不会开发的小虾米
 */
@Data
public class MemberOverviewDTO {
    private long todayNew;
    private long yesterdayNew;
    private long monthNew;
    private long totalMember;
}
