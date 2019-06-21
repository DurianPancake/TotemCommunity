package com.xiaoniu.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * POJO 基类
 */
@Data
@Accessors(chain=true)
public class BasePojo implements Serializable{

}
