package com.edu.springshop.model.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.edu.springshop.domain.Category;
import com.edu.springshop.exception.CategoryException;

@Service
public class CategoryServiceImpl implements CategoryService{
	
	@Autowired
	// @Qualifier("mybatisCategoryDAO")
	private CategoryDAO categoryDAO; //느슨하게 보유...
	
	public List selectAll() {
		return categoryDAO.selectAll();
	}

	public Category select(int category_idx) {
		return categoryDAO.select(category_idx);
	}

	public void insert(Category category)  throws CategoryException{
		categoryDAO.insert(category);
	}

	public void update(Category category)  throws CategoryException{
		categoryDAO.update(category);
	}

	public void delete(int category_idx) throws CategoryException{
		categoryDAO.delete(category_idx);
	}
}