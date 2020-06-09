package com.qualintech.taskcentre.common;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Controller默认进行ResultVO包装，对于特殊不需要的，使用该注解可以忽略包装
 *
 * @author 单红宇
 * @date 2019年6月26日
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GLOBAL_RESPONSE_IGNORE {

}