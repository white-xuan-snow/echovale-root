package com.echovale.shared.infrastructure.presistence;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "GMT+8")
    private Date timestamp;
    public static Result success() {
//        return new Result(200, "success", null);
        return Result.builder()
                .code(200)
                .msg("success")
                .timestamp(new Date())
                .build();
    }
    public static Result success(Object data) {
//        return new Result(200, "success", data);
        return Result.builder()
                .code(200)
                .msg("success")
                .data(data)
                .timestamp(new Date())
                .build();
    }
    public static Result error() {
//        return new Result(500, "undefined exception", null);
        return Result.builder()
                .code(500)
                .msg("undefined exception")
                .timestamp(new Date())
                .build();
    }
    public static Result fail(Integer code, String msg) {
//        return new Result(code, msg, null);
        return Result.builder()
                .code(code)
                .msg(msg)
                .timestamp(new Date())
                .build();
    }
    public static Result serverError(String msg) {
//        return new Result(500, msg, null);
        return Result.builder()
                .code(500)
                .msg(msg)
                .timestamp(new Date())
                .build();
    }
    public static Result clientError(String msg) {
//        return new Result(400, msg, null);
        return Result.builder()
                .code(400)
                .msg(msg)
                .timestamp(new Date())
                .build();
    }
}

