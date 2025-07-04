package com.echovale.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 30531
 * @version 1.0
 * @description: TODO
 * @date 2025/6/10 13:36
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private int code;
    private String msg;
    private Object data;
    public static Result success() {
        return new Result(200, "success", null);
    }
    public static Result success(Object data) {
        return new Result(200, "success", data);
    }
    public static Result error() {
        return new Result(500, "undefined exception", null);
    }
    public static Result serverError(String msg) {
        return new Result(500, msg, null);
    }
    public static Result clientError(String msg) {
        return new Result(400, msg, null);
    }
}

