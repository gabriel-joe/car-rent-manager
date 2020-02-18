package br.com.evaluation.car.rent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.evaluation.car.rent.entity.Category;
import br.com.evaluation.car.rent.repository.CategoryRepository;
import br.com.evaluation.car.rent.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Iterable<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Category findById(Long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public void initData() {
		loadInitValues(1L, "Compact Hatch");
		loadInitValues(2L, "Medium Hatch");
		loadInitValues(3L, "Sedan");
		loadInitValues(4L, "Van");
		loadInitValues(5L, "Pickup");
	}

	/**
	 * Method responsible to load category data if missing,
	 * useful to run tests and when active profile is different mysql
	 * @param cp
	 * @param id
	 * @param name
	 */
	private void loadInitValues(Long id, String name) {
		Category c = new Category();
		c.setId(id);
		c.setName(name);
		categoryRepository.save(c);
	}
}
