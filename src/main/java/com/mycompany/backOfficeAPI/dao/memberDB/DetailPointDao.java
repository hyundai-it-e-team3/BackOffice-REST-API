package com.mycompany.backOfficeAPI.dao.memberDB;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.backOfficeAPI.dto.member.DetailPoint;
import com.mycompany.backOfficeAPI.dto.member.Point;

@Mapper
public interface DetailPointDao {
	public void insertSaveDetailPoint(DetailPoint saveDetailPoint);
	public DetailPoint getAvailableOlderPoint(String memberId);
	public void insertUseDetailPoint(DetailPoint useDetailPoint);
	public void updateBalance(@Param("detailPointSeq") int detailPointSeq, @Param("balance") int balance);
	public void updateUsedStatus(int detailPointSeq);
	public List<DetailPoint> selectRefundDetailPoint(Point refundPointSeq);
	public void updateUsePointBalanceAndStatus(DetailPoint detailPoint);
	public void updateRefundDetailPoint(Point refundPointSeq);
  
	//만료 포인트 소멸
	public List<DetailPoint> getExpiryDetailPoint();
	public void updateExpiryDetailPoint(DetailPoint detailPoint);
}
