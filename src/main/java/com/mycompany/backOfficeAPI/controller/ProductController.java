package com.mycompany.backOfficeAPI.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.backOfficeAPI.dto.product.BrandDTO;
import com.mycompany.backOfficeAPI.dto.product.ColorDTO;
import com.mycompany.backOfficeAPI.dto.product.PagerDTO;
import com.mycompany.backOfficeAPI.dto.product.ProductDTO;
import com.mycompany.backOfficeAPI.dto.product.ProductDetailDTO;
import com.mycompany.backOfficeAPI.dto.product.ProductSearchDTO;
import com.mycompany.backOfficeAPI.dto.product.StockDetailDTO;
import com.mycompany.backOfficeAPI.service.ProductDetailService;
import com.mycompany.backOfficeAPI.service.ProductService;
import com.mycompany.backOfficeAPI.service.S3UploaderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductController {
	
	@Resource
	private ProductService productService;
	
	@Resource
	private ProductDetailService productDetailService;
	
	@Resource
	private S3UploaderService s3UploaderService;
	
	@GetMapping("/{productId}")
	public ProductDTO getProduct(@PathVariable String productId){
		return productService.getProduct(productId);
	}
	
	@RequestMapping("/brandProductList/{brandName}/{categoryId}/{startRow}/{rowCount}/{sortId}")
	public List<ProductDTO> getBrandProductList(@PathVariable("brandName") String brandName,@PathVariable("startRow") int startRow,@PathVariable("rowCount") int rowCount,@PathVariable("sortId") int sortId,@PathVariable("categoryId") String categoryId){
		if(categoryId.equals("null"))
			categoryId = "";
		int totalRows = productService.getRowCount(brandName,categoryId);
		int endRow = startRow+rowCount;
		if(endRow>totalRows) {
			endRow = totalRows;
		}
		
		return productService.getBrandProductList(brandName,categoryId,startRow,endRow,sortId);  
	}
	
	@RequestMapping("/list/{categoryId}/{startRow}/{rowCount}/{sortId}")
	public List<ProductDTO> getCategoryProductList(@PathVariable("categoryId") String categoryId,@PathVariable("startRow") int startRow,@PathVariable("rowCount") int rowCount,@PathVariable("sortId") int sortId){
		if(categoryId.equals("null"))
			categoryId = "";
		int totalRows = productService.getRowCountByCategory(categoryId);
		log.info("categoryId "+categoryId);
		log.info(" "+totalRows);
		
		
		int endRow = startRow+rowCount;
		if(endRow>totalRows) {
			endRow = totalRows;
		}
		List <ProductDTO> list = productService.getCategoryProductList(categoryId,startRow,endRow, sortId);
		for(ProductDTO product : list) {
			log.info(product.getProductId());
		}
		return list;
	}
	
	@RequestMapping("/list/{idStr}")
	public List<ProductDTO> getWishProductList(@PathVariable String idStr){
		log.info(idStr);
		return productService.getProductListByWish(idStr);
	}
	
	@RequestMapping("/list/text/{text}/{startRow}/{rowCount}/{sortId}")
	public List<ProductDTO> getProductByText(@PathVariable String text,@PathVariable int startRow,@PathVariable int rowCount,@PathVariable int sortId){
		if(text.equals("0"))
			text = "";
		int totalRows = productService.getRowCountByText(text);
		
		int endRow = startRow+rowCount;
		if(endRow>totalRows) {
			endRow = totalRows;
		}
		
		return productService.getProductByText(text,startRow,endRow,sortId);
	}
	
	@GetMapping("/withProductList")
	public Map<String,Object> getWithProductList(@RequestParam String productDetailId, @RequestParam String pageNo){
		
		Map<String,Object> resultMap = new HashMap<>();
		int totalRows = productService.getTotalWith(productDetailId);

		PagerDTO pagerDTO = new PagerDTO(10, 5, totalRows,  Integer.parseInt(pageNo));
		
		List<ProductDetailDTO> list = productDetailService.getWithProductList(productDetailId,pagerDTO.getStartRowNo(),pagerDTO.getEndRowNo());
		
		log.info("총합: "+totalRows);
		log.info(list.toString());
		
		
		
		resultMap.put("pager",pagerDTO);
		resultMap.put("productDetailList",list);
		
		return resultMap;
	}
	
	@GetMapping("/sizeList")
	public List<String> getSizetList(){
		
		List<String> sizeList = productService.getAllSize();
		
		return sizeList;
	}
	
	@PostMapping("/regist")
	public Map<String,String> registProduct(@RequestBody ProductDTO productDTO){
		
		log.info(productDTO.toString());
		productService.registProduct(productDTO);
		
		Map<String,String> map = new HashMap<>();
		map.put("result","success");
		
		
		return map;
	}
	
	@PostMapping("/changeStatus")
	public Map<String,String> changeStatus(@RequestBody List<String> productIdList){
		Map<String,String> map = new HashMap<>();
		map.put("result","success");
		
		productService.changeStatus(productIdList);
		
		return map;
	}
	
	@PostMapping("/productList")
	public Map<String,Object> getProductList(@RequestBody ProductSearchDTO productSearchDTO){		
		
		String searchType = productSearchDTO.getSearchType();
		String keyWord = productSearchDTO.getKeyWord();
		String categoryId = productSearchDTO.getCategoryId();
		String regStart =  productSearchDTO.getRegStart();
		String regEnd = productSearchDTO.getRegEnd();
		String status = productSearchDTO.getStatus();
		
		Map<String,Object> resultMap = new HashMap<>();
		
		
		
		if(categoryId.equals("None")) {
			productSearchDTO.setCategoryId("");
		}
		
		int totalRows = productService.getTotalProductNum(productSearchDTO);
		PagerDTO pagerDTO = new PagerDTO(10, 5, totalRows,  Integer.parseInt(productSearchDTO.getPageNo()));
		
		productSearchDTO.setStartRow(pagerDTO.getStartRowNo());
		productSearchDTO.setEndRow(pagerDTO.getEndRowNo());
		
		
		resultMap.put("pager", pagerDTO);
		
		log.info("총 RO개수: "+totalRows);
		
		log.info(searchType);
		log.info(keyWord);
		log.info(categoryId);
		log.info("regStart: "+regStart);
		log.info("regEnd: "+regEnd);
		log.info(status);
		
		List<ProductDTO> productList = productService.getProductList(productSearchDTO);
		log.info(productList.toString());
		resultMap.put("productList", productList);

		return resultMap;
	}
	
	
	
	@PostMapping("/stockList")
	public Map<String,Object> getProductList(@RequestBody Map<String,String> map){		
		
		Map<String,Object> resultMap = new HashMap<>();
		
		String searchType = map.get("searchType");
		String keyWord = map.get("keyWord");
		String sortId = map.get("sortId");
		int pageNo = Integer.parseInt(map.get("pageNo"));
		
		int totalRows = productDetailService.getTotalProductDetail(searchType,keyWord);
		PagerDTO pagerDTO = new PagerDTO(10, 5, totalRows, pageNo);
		List<StockDetailDTO> list = productDetailService.getProductDetailList(searchType,keyWord,pagerDTO.getStartRowNo(),pagerDTO.getEndRowNo(),sortId);
		
		resultMap.put("pager",pagerDTO);
		resultMap.put("stockList",list);

		return resultMap;
	}
	
	@PostMapping("/regMdProduct")
	public Map<String,Object> regMdProduct(@RequestBody ProductDTO productDTO){		
		
		Map<String,Object> resultMap = new HashMap<>();
		
		productService.regMdProduct(productDTO);

		return resultMap;
	}
	
	@PostMapping("/delMdProduct")
	public Map<String,Object> delMdProduct(@RequestBody ProductDTO productDTO){		
		
		Map<String,Object> resultMap = new HashMap<>();
		
		productService.delMdProduct(productDTO);

		return resultMap;
	}
}
