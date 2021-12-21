package com.mycompany.backOfficeAPI.dto.member;

import lombok.Data;

@Data
public class Manager {
	private String id;
	private String password;
	private String managerRole;
}
