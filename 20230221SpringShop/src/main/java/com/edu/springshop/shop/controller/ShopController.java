package com.edu.springshop.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.edu.springshop.domain.Product;
import com.edu.springshop.model.product.ProductService;

// 상품 리스트부터 구매단계까지의 쇼핑 전반적인 요청을 처리할 하위 컨트롤러
@Controller
public class ShopController {

	// @Autowired 지워서 AOP되는지 확인(CategoryAdvice)
	// private CategoryService categoryService;
	
	@Autowired
	private ProductService productService; 
	
	// 상품 리스트 페이지 요청
	@GetMapping("/shop/list")
	public ModelAndView getList() {
		// 3단계
		// List categoryList = categoryService.selectAll();
		List productList = productService.selectAll();
		
		// 4단계 : JSP로 가져가야 한다
		ModelAndView mav = new ModelAndView();
		// mav.addObject("categoryList", categoryList);
		mav.addObject("productList", productList);
		mav.setViewName("shop/shop");
	
		return mav;
	}
	
	// 컨트롤러 메서드의 반환형은 오직 ModelAndView만 지원되는 것이 아니다
	// String형도 지원한다
	@GetMapping("/shop/test")
	public String test() {
		
		return "shop/test_result"; // ModelAndView에서 Model을 제외한  View
	}
	
	// 상품 상세보기 요청처리
	@GetMapping("/shop/detail")
	public ModelAndView getDetail(int product_idx) {
		// 3단계 : 일 시키기 
		
		Product product = productService.select(product_idx);
		
		// 4단계 : 저장
		ModelAndView mav = new ModelAndView("/shop/shop_detail");
		mav.addObject("product", product);
		
		return mav;
	}
}
