package br.com.evaluation.car.rent.repository;

import br.com.evaluation.car.rent.entity.Category;

public interface CategoryRepository extends org.springframework.data.repository.Repository<Category, Long> {

	public void save(Category c);
	
	public Category findById(Long id);
	
	public Iterable<Category> findAll();
	
}
