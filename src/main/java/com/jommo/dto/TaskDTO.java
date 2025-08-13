package com.jommo.dto;


import lombok.Data;

/**
 * @author 不会开发的小虾米
 */
@Data
public class TaskDTO {
    private Integer unpaidCount;
    private Integer undeliveredCount;
    private Integer deliveredCount;
    private Integer completedCount;
    private Integer advertisementCount;

    @Override
    public String toString() {
        return "TaskDTO{" +
                "unpaidCount=" + unpaidCount +
                ", undeliveredCount=" + undeliveredCount +
                ", deliveredCount=" + deliveredCount +
                ", completedCount=" + completedCount +
                ", advertisementCount=" + advertisementCount +
                '}';
    }
}
