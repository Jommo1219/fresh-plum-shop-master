package com.jommo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应结果
 *
 * @author 不会开发的小虾米
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    //业务状态码  0-成功  1-失败
    private Integer code;
    //提示信息
    private String message;
    //响应数据
    private T data;

    //快速返回操作成功响应结果(带响应数据)
    public static <E> Result<E> success(E data) {
        return new Result<>(ResultCode.SUCCESS, "操作成功", data);
    }

    //快速返回操作成功响应结果
    public static Result success() {
        return new Result(ResultCode.SUCCESS, "操作成功", null);
    }

    public static Result error(String message) {
        return new Result(ResultCode.ERROR, message, null);
    }
}
