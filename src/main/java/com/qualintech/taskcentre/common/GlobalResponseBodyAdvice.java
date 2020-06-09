package com.qualintech.taskcentre.common;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.squirrelframework.foundation.exception.TransitionException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

/**
 * @title 全局统一响应处理
 * @author Mart
 * @createDate 2020-05-22
 */
@RestControllerAdvice
@Slf4j
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object>{
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        // 下面处理统一返回结果（统一code、msg、sign 加密等）
        if (selectedConverterType == MappingJackson2HttpMessageConverter.class
                && (selectedContentType.equals(MediaType.APPLICATION_JSON))) {
            if (body == null) {
                return GlobalResponse.NULL;
            }
            else if(body instanceof GlobalResponse) {
                return body;
            }
            else{
                return new GlobalResponse<>(body);
            }
        }
        return body;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (returnType.hasMethodAnnotation(GLOBAL_RESPONSE_IGNORE.class))
            return false;
        return true;
    }

    /**
     * Validator 参数校验异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = { BindException.class })
    public GlobalResponse handleBindException(BindException ex) {
        FieldError err = ex.getFieldError();
        String message = "参数{".concat(err.getField()).concat("}").concat(err.getDefaultMessage());
        log.info("{} -> {}", err.getObjectName(), message);
        return new GlobalResponse(ResultCode.PARAM_INVALID, message);
    }

    @ExceptionHandler(value = { HttpMessageNotReadableException.class })
    public GlobalResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new GlobalResponse(ResultCode.PARAM_NOT_JSON, ex.getMessage());
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    public GlobalResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        return new GlobalResponse(ResultCode.PARAM_INVALID, formatError(errors));
    }
    /**
     * Validator 参数校验异常处理
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public GlobalResponse handleConstraintViolationException(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            PathImpl pathImpl = (PathImpl) constraintViolation.getPropertyPath();
            // 读取参数字段，constraintViolation.getMessage() 读取验证注解中的message值
            String paramName = pathImpl.getLeafNode().getName();
            String message = "参数{".concat(paramName).concat("}").concat(constraintViolation.getMessage());
            log.info("{} -> {} -> {}", constraintViolation.getRootBeanClass().getName(), pathImpl.toString(), message);
            return new GlobalResponse(GlobalResponse.PARAM_INVALID.getCode(), ex.getMessage());
        }
        return new GlobalResponse(GlobalResponse.PARAM_INVALID.getCode(), ex.getMessage());
    }

    /**
     * 业务转换失败的处理
     * @param ex
     * @return
     */
    @ExceptionHandler(TransitionException.class)
    public GlobalResponse handleTransitionException(TransitionException ex) {
        return new GlobalResponse(GlobalResponse.TRANSITION_ERR.getCode(), ex.getMessage());
    }

    /**
     *
     * @param errors
     * @return
     */
    private String formatError(List<ObjectError> errors) {
        StringBuilder builder = new StringBuilder();
        if(errors == null){
            return null;
        }
        if(errors.size()>1){
            errors.forEach(error -> builder.append(error.getDefaultMessage()).append(";"));
            return builder.toString();
        }
        if(errors.size()==1){
            return errors.get(0).getDefaultMessage();
        }
        return null;
    }
}
