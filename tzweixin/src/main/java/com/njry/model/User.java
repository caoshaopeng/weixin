package com.njry.model;

import lombok.Data;

@Data
public class User {
	private int id;
	private String user_name;
	private String password;
	private String rec_date;
}
