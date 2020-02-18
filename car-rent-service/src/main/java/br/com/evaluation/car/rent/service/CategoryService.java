package br.com.evaluation.car.rent.service;

import br.com.evaluation.car.rent.entity.Category;

public interface CategoryService {

	public Iterable<Category> findAll();
	
	public Category findById(Long id);
	
	public void initData();
}
