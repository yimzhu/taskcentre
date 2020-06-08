package com.qualintech.taskcentre.common;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @title 全局统一响应处理
 * @author Mart
 * @createDate 2020-05-22
 */
@ControllerAdvice
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice {
    /**
     *  这个方法表示对于哪些请求要执行beforeBodyWrite，返回true执行，返回false不执行
     */
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    /**
     * 处理响应的具体业务方法
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body == null) {
            return GenericResponse.Success();
        }
        if (body instanceof GenericResponse) {
            return body;
        } else {
            return GenericResponse.Success(body);
        }
    }
}
