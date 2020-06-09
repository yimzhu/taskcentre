package com.qualintech.taskcentre.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode implements ICode {
    /** 操作成功 */
    SUCCESS(1, "成功"),

    /** 参数缺失或不正确 */
    PARAM_INVALID(-1, "参数缺失或不正确"),

    /** 请求体格式不正确 */
    PARAM_NOT_JSON(-2, "请求体非JSON"),

    /** 业务数据不存在 */
    NULL(2001, "数据不存在"),
    /** 业务数据重复 */
    DUPLICATE(2002, "数据重复"),
    /**状态转变失败 */
    TRANSITION_ERR(2003, "状态转变失败"),

    /** 没有权限 */
    FORBIDDEN(9001, "没有权限"),
    /** 系统发生异常 */
    EXCEPTION(9999, "系统异常");

    public Integer code;
    private String name;
}
