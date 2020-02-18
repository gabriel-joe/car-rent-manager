package br.com.evaluation.car.rent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.evaluation.car.rent.entity.Category;
import br.com.evaluation.car.rent.service.CategoryService;
/**
 * 
 * @author gabriel.santos
 *
 */
@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	@Autowired

	@RequestMapping(method = RequestMethod.GET, path = "/findAll")
	public Iterable<Category> findAll() {
		return categoryService.findAll();
	}

	
}
