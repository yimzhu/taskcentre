package com.qualintech.taskcentre.common;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class GlobalResponse<T> implements IResponse{
    public static final GlobalResponse SUCCESS = new GlobalResponse(ResultCode.SUCCESS);
    public static final GlobalResponse NULL = new GlobalResponse(ResultCode.NULL);
    public static final GlobalResponse FORBIDDEN = new GlobalResponse(ResultCode.FORBIDDEN);
    public static final GlobalResponse EXCEPTION = new GlobalResponse(ResultCode.EXCEPTION);
    public static final GlobalResponse PARAM_INVALID = new GlobalResponse(ResultCode.PARAM_INVALID);
    /**
     * 返回代码
     */
    private Integer code;

    /**
     * 返回信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 默认构造，返回操作正确的返回代码和信息
     */
    public GlobalResponse() {
        this.setCode(ResultCode.SUCCESS.getCode());
        this.setMessage(ResultCode.SUCCESS.name());
    }

    /**
     * 构造一个返回特定代码的GenericResponse对象
     *
     * @param code
     */
    public GlobalResponse(ResultCode code) {
        this.setCode(code.getCode());
        this.setMessage(code.getName());
    }

    /**
     * 自定义返回Code以及Message
     * @param code
     * @param message
     */
    public GlobalResponse(Integer code, String message) {
        super();
        this.setCode(code);
        this.setMessage(message);
    }

    /**
     * 默认值返回，默认返回正确的code和message
     *
     * @param data
     */
    public GlobalResponse(T data) {
        ResultCode rc = data == null ? ResultCode.NULL : ResultCode.SUCCESS;
        this.setCode(rc.getCode());
        this.setMessage(rc.getName());
        this.setData(data);
    }

    /**
     * 构造返回代码，以及自定义的错误信息
     *
     * @param code
     * @param message
     */
    public GlobalResponse(ResultCode code, String message) {
        this.setCode(code.getCode());
        this.setMessage(message);
    }

    /**
     * 构造自定义的code，message，以及data
     *
     * @param code
     * @param message
     * @param data
     */
    public GlobalResponse(ResultCode code, String message, T data) {
        this.setCode(code.getCode());
        this.setMessage(message);
        this.setData(data);
    }
}
