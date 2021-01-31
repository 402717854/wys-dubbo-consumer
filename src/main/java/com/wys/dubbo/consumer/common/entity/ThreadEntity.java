package com.wys.dubbo.consumer.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname ThreadEntity
 * @Description 线程存储变量类
 * @Date 2021/01/27
 * @Created by 86133
 */
@Data
public class ThreadEntity implements Serializable {
    private static final long serialVersionUID = -8635470610686685067L;
    /**用户Id*/
    private Integer userId;
    /**用户token*/
    private String accessToken;


}
