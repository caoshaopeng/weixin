package com.njry.model.button;

import lombok.Data;

@Data
public class Button {
	private String name;//菜单标题
	
	private String type;//菜单的响应动作类型
	
	private Button[] sub_button;

}
