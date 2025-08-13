package com.jommo.common;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author 不会开发的小虾米
 */
@Data
@AllArgsConstructor
public class Page<T> {
    private Integer total;
    private List<T> records;
}
