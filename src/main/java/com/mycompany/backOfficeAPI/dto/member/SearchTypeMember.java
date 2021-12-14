package com.mycompany.backOfficeAPI.dto.member;

import java.util.List;

import lombok.Data;

@Data
public class SearchTypeMember {
	private String searchMemberId;
	private String searchName;
	private List<String> memberLevelList;
}
