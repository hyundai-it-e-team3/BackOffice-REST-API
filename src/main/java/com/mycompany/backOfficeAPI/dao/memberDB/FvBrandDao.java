package com.mycompany.backOfficeAPI.dao.memberDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.backOfficeAPI.dto.member.FvBrand;

@Mapper
public interface FvBrandDao {
	public List<FvBrand> getFvBrand(String memberId);
	public void insertBrand(FvBrand fvBrand);
	public void deleteBrand(FvBrand fvBrand);
}
