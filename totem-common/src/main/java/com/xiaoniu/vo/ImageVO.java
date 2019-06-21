package com.xiaoniu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
public class ImageVO implements Serializable{
	
	private static final long serialVersionUID = -8019017074323385599L;
	
	private Integer error;
	private String url;
	private Integer width;
	private Integer height;

}
