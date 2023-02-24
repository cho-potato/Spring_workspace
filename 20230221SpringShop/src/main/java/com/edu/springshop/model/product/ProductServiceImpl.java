package com.edu.springshop.model.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.edu.springshop.domain.Pimg;
import com.edu.springshop.domain.Product;
import com.edu.springshop.exception.PimgException;
import com.edu.springshop.exception.ProductException;
import com.edu.springshop.exception.UploadException;
import com.edu.springshop.util.FileManager;

@Service
public class ProductServiceImpl implements ProductService{
	
	//DAO 모델
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private PimgDAO pimgDAO;
	
	//FileManager 모델
	@Autowired
	private FileManager fileManager;
	
	public List selectAll() {
		return productDAO.selectAll();
	}

	public Product select(int product_idx) {
		return productDAO.select(product_idx);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void regist(Product product, String dir) throws ProductException, UploadException, PimgException {
		//상품저장 (부모 Product)
		productDAO.insert(product);//select-key에 의해 pk존재하게 됨
		
		//파일저장  //  트랜잭션 상황은 아니지만 개념적으로는 트랜잭션에 해당
		fileManager.save(product, dir);
		
		List<Pimg> pImgList=product.getPimgList();
		
		for(Pimg pimg : pImgList) {
			pimgDAO.insert(pimg);
		}		
	}

	public void update(Product product) {
		productDAO.update(product);
	}

	public void delete(int product_idx) {
		productDAO.delete(product_idx);
	}
}