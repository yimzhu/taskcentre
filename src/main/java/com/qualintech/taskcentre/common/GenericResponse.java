package com.qualintech.taskcentre.common;

import lombok.Data;

@Data
public class GenericResponse<T> implements Result {
    private static final long serialVersionUID = 874200365941306385L;

    private int code;
    private String msg;
    private T Data;

    public static GenericResponse Success () {
        GenericResponse genericResponse =  new GenericResponse();
        genericResponse.setCode(ResultCode.SUCCESS.getCode());
        genericResponse.setMsg(ResultCode.SUCCESS.getName());
        return genericResponse;
    }

    public static GenericResponse Success (Object data) {
        GenericResponse genericResponse =  new GenericResponse();
        genericResponse.setCode(ResultCode.SUCCESS.getCode());
        genericResponse.setMsg(ResultCode.SUCCESS.getName());
        genericResponse.setData(data);

        return genericResponse;
    }

    public static GenericResponse Success (String data) {
//        System.out.print(data.toString());
        GenericResponse genericResponse =  new GenericResponse();
        genericResponse.setCode(ResultCode.SUCCESS.getCode());
        genericResponse.setMsg(ResultCode.SUCCESS.getName());
        genericResponse.setData(data);

        return genericResponse;
    }

    public static<T> GenericResponse Success (ResultCode resultCode, T data) {
        GenericResponse genericResponse =  new GenericResponse();
        genericResponse.setCode(ResultCode.SUCCESS.getCode());
        genericResponse.setMsg(ResultCode.SUCCESS.getName());
        genericResponse.setData(data);
        return genericResponse;
    }

    public static<T> GenericResponse Failure () {
        GenericResponse genericResponse =  new GenericResponse();
        genericResponse.setCode(ResultCode.RESPONSE_IS_BLANK.getCode());
        genericResponse.setMsg(ResultCode.RESPONSE_IS_BLANK.getName());
        return genericResponse;
    }

    public static<T> GenericResponse Failure (ResultCode resultCode, T data) {
        GenericResponse genericResponse =  new GenericResponse();
//        genericResponse.setCode(ResultCode.FAILURE.getCode());
        genericResponse.setResultCode(resultCode);
        genericResponse.setData(data);
        return genericResponse;
    }

//    public static GenericResponse failure(String message) {
//        GenericResponse result = new GenericResponse();
//        result.setCode(ResultCode.PARAM_IS_INVALID.code());
//        result.setMsg(message);
//        return result;
//    }

    public void setResultCode(ResultCode code) {
        this.code = code.getCode();
        this.msg = code.getName();
    }
}
