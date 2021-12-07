package com.mycompany.backOfficeAPI.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.backOfficeAPI.dao.memberDB.FvBrandDao;
import com.mycompany.backOfficeAPI.dto.member.FvBrand;

@Service
public class FvBrandService {
	@Resource
	private FvBrandDao fvBrandDao;
	
	public List<FvBrand> getFvBrand(String memberId) {
		return fvBrandDao.getFvBrand(memberId);
	}
	
	public void insertBrand(FvBrand fvBrand) {
		fvBrandDao.insertBrand(fvBrand);
	}
	
	public void deleteBrand(FvBrand fvBrand) {
		fvBrandDao.deleteBrand(fvBrand);
	}
}
