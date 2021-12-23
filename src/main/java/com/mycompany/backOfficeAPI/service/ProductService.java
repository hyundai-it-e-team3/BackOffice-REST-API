package com.mycompany.backOfficeAPI.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycompany.backOfficeAPI.dao.productDB.BrandDAO;
import com.mycompany.backOfficeAPI.dao.productDB.CategoryDAO;
import com.mycompany.backOfficeAPI.dao.productDB.ProductDAO;
import com.mycompany.backOfficeAPI.dao.productDB.ProductDetailDAO;
import com.mycompany.backOfficeAPI.dao.productDB.ProductImgDAO;
import com.mycompany.backOfficeAPI.dao.productDB.StockDAO;
import com.mycompany.backOfficeAPI.dto.product.BrandDTO;
import com.mycompany.backOfficeAPI.dto.product.CategoryDTO;
import com.mycompany.backOfficeAPI.dto.product.ColorDTO;
import com.mycompany.backOfficeAPI.dto.product.ProductDTO;
import com.mycompany.backOfficeAPI.dto.product.ProductDetailDTO;
import com.mycompany.backOfficeAPI.dto.product.ProductImgDTO;
import com.mycompany.backOfficeAPI.dto.product.ProductSearchDTO;
import com.mycompany.backOfficeAPI.dto.product.StockDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {
	
	@Resource
	private ProductDAO productDAO;
	
	@Resource
	private ProductDetailDAO productDetailDAO;
	
	@Resource
	private StockDAO stockDAO;
	
	@Resource
	private CategoryDAO categoryDAO;
	
	@Resource
	private ProductImgDAO productImgDAO;
	
	@Resource
	private BrandDAO brandDAO;
	
	public ProductDTO getProduct(String productId) {
		
		productDAO.updateHitCount(productId);
		
		ProductDTO productDTO = productDAO.selectByProductId(productId);
		log.info(productId);
		
		
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setParentCategoryId(productDTO.getParentCategoryId());
		productDTO.setCategoryList(new ArrayList<>());
		for(int i = productDTO.getClevel(); i>1; i--) {
				categoryDTO = categoryDAO.selectCategoryById(categoryDTO.getParentCategoryId());
				productDTO.getCategoryList().add(0,categoryDTO.getName());
		}
		productDTO.getCategoryList().add(productDTO.getCategoryName());
		productDTO.setProductDetailList(productDetailDAO.selectAllByProductId(productId));
		
		for(ProductDetailDTO productDetail: productDTO.getProductDetailList()) {
			productDetail.setStockList(stockDAO.selectByProductDetailId(productDetail.getProductDetailId()));
			productDetail.setImgList(productImgDAO.selectByProductDetailId(productDetail.getProductDetailId()));
			productDetail.setWithImgList(productImgDAO.selectByProductDetailId(productDetail.getWithProduct()));
		}		
		return productDTO;
	}

	public int getRowCount(String brandName, String categoryId) {
		Map<String,String> mp = new HashMap<>();
		mp.put("brandName",brandName);
		mp.put("categoryId",categoryId);
		return productDAO.selectCountByBrandName(mp);
	}

	public List<ProductDTO> getBrandProductList(String brandName,String categoryId ,int startRow, int endRow, int sortId) {
		Map<String,Object> mp = new HashMap<>();
		mp.put("brandName",brandName);
		mp.put("startRow",startRow);
		mp.put("endRow",endRow);
		mp.put("categoryId",categoryId);
		
		if(sortId==0) {
			mp.put("sortId","reg_date");
			mp.put("sortWay","asc");
		}else if(sortId==1){
			mp.put("sortId","price");
			mp.put("sortWay","asc");
		}else if(sortId==2){
			mp.put("sortId","price");
			mp.put("sortWay","desc");
		}
		return productDAO.selectProductListByBrandName(mp);
	
	}

	public int getRowCountByCategory(String categoryId) {	
		if(categoryId.equals("0"))
			return productDAO.selectAllCount();
		return productDAO.selectCountByCategoryId(categoryId);
	}

	public List<ProductDTO> getCategoryProductList(String categoryId, int startRow, int endRow, int sortId) {
		
		Map<String,Object> mp = new HashMap<>();
		
		mp.put("categoryId",categoryId);
		mp.put("startRow",startRow);
		mp.put("endRow",endRow);
		
		if(sortId==0) {
			mp.put("sortId","reg_date");
			mp.put("sortWay","desc");
		}else if(sortId==1){
			mp.put("sortId","price");
			mp.put("sortWay","asc");
		}else if(sortId==2){
			mp.put("sortId","price");
			mp.put("sortWay","desc");
		}else{
			mp.put("sortId","hit_count");
			mp.put("sortWay","desc");
		}
		
		
		log.info(categoryId+" "+startRow+" "+endRow+" "+sortId);
		if(categoryId.equals("0"))
			return productDAO.selectAllProductList(mp);
		return productDAO.selectProductListByCategoryId(mp);
	}
	
	public List<ProductDTO> getProductListByWish(String idStr) {
		List<String> list = Arrays.asList(idStr.split(","));
		log.info(list.toString());
		return productDAO.selectProductListByWish(list);
	}

	public List<ProductDTO> getProductByText(String text,int startRow, int endRow, int sortId) {
		Map<String,Object> mp = new HashMap<>();		
		mp.put("text",text);
		mp.put("startRow",startRow);
		mp.put("endRow",endRow);
		
		if(sortId==0) {
			mp.put("sortId","reg_date");
			mp.put("sortWay","desc");
		}else if(sortId==1){
			mp.put("sortId","price");
			mp.put("sortWay","asc");
		}else if(sortId==2){
			mp.put("sortId","price");
			mp.put("sortWay","desc");
		}else{
			mp.put("sortId","hit_count");
			mp.put("sortWay","desc");
		}
		
		
		log.info(text+" "+startRow+" "+endRow+" "+sortId);
		return productDAO.SelectProductByText(mp);
	}

	public int getRowCountByText(String text) {
		return productDAO.selectCountByText(text);
	}


	public List<ProductDTO> getProductList(ProductSearchDTO productSearchDTO) {
		
		log.info(productSearchDTO.toString());
		
		List<ProductDTO> productList = productDAO.selectProductListBySearch(productSearchDTO);
		
		log.info(productList.toString());
		
		for(ProductDTO productDTO: productList) {
			CategoryDTO categoryDTO = new CategoryDTO();
			categoryDTO.setParentCategoryId(productDTO.getParentCategoryId());
			productDTO.setCategoryList(new ArrayList<>());
			for(int i = productDTO.getClevel(); i>1; i--) {
				categoryDTO = categoryDAO.selectCategoryById(categoryDTO.getParentCategoryId());
				log.info(categoryDTO.toString());
				productDTO.getCategoryList().add(0,categoryDTO.getName());
			}
			productDTO.getCategoryList().add(productDTO.getCategoryName());
		}
		return productList;
	}

	public int getTotalProductNum(ProductSearchDTO productSearchDTO) {
		return productDAO.selectProductListCountBySearch(productSearchDTO);
	}

	@Transactional
	public void registProduct(ProductDTO productDTO) {
		log.info("1--------");
		productDAO.insertProduct(productDTO);
		log.info("2--------");
		categoryDAO.insertCategory(productDTO);
		
		for(ProductDetailDTO productDetailDTO: productDTO.getProductDetailList()) {
			log.info("3--------");
			productDetailDAO.insertProductDetail(productDetailDTO);
			
			for(ProductImgDTO productImgDTO: productDetailDTO.getImgList()) {
				log.info("4--------");
				productImgDAO.insertImg(productImgDTO);
			}
			
			for(StockDTO stockDTO: productDetailDTO.getStockList()) {
				log.info("5--------");
				stockDTO.setProductDetailId(productDetailDTO.getProductDetailId());
				stockDAO.insertStock(stockDTO);
			}
		}
		
		log.info("6--------");
	}

	public int getTotalWith(String productDetailId) {
		return productDetailDAO.selectTotalProductDetailById(productDetailId);
	}

	public List<String> getAllSize() {
		return stockDAO.selectAllSize();
	}

	@Transactional
	public void regMdProduct(ProductDTO productDTO) {
		
		productDTO.setMdStatus(1);
		productDAO.insertMdProduct(productDTO);
		productDAO.updateMdStatus(productDTO);
	}

	public void delMdProduct(ProductDTO productDTO) {
		productDTO.setMdStatus(0);
		productDAO.deleteMdProduct(productDTO);
		productDAO.updateMdStatus(productDTO);
		
	}

	public void changeStatus(List<String> productIdList) {
		
		
		for(String productId: productIdList) {
			int statusTemp = productDAO.selectStatusById(productId);
		
			if(statusTemp==1) {
				productDAO.updateStatus(productId,0);
			}else{
				productDAO.updateStatus(productId,1);
			}
			
		}
	}

}
