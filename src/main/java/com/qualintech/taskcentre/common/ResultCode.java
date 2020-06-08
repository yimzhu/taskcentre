package com.qualintech.taskcentre.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode implements ICode {
    /*成功状态码*/
    SUCCESS(00000, "成功"),
    /*参数错误*/
    PARAM_IS_INVAILD(10000, "参数无效"),
    PARAM_IS_BLANK(10001, "参数为空"),
    PARAM_TYPE_BIND_ERROR(10002, "参数类型错误"),
    PARAM_NOT_COMPLETE(10003, "参数缺失"),
    /*业务错误*/
    RESPONSE_IS_BLANK(20000, "业务返回为空");
//    RESPONSE_IS_BLANK(20001, "业务错误");

    public int code;
    private String name;
}
