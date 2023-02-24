package com.edu.springshop.model.product;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edu.springshop.domain.Cart;
import com.edu.springshop.exception.CartException;

@Repository
public class MybatisCartDAO implements CartDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List selectAll() {
		return sqlSessionTemplate.selectList("Cart.selectAll");
	}

	public void insert(Cart cart) throws CartException {
		int result = sqlSessionTemplate.insert("Cart.insert", cart);
		if(result < 1) {
			throw new CartException("등록실패");
		}
	}
	public int selectCount(Cart cart) throws CartException {
		int count = sqlSessionTemplate.selectOne("Cart.selectCount", cart);
		
		return count;
	}

	public void updateEa(Cart cart) throws CartException {
		int result = sqlSessionTemplate.update("Cart.updateEa", cart);
		if(result < 1) {
			throw new CartException("장바구니 업데이트 실패");
		}
	}
}
