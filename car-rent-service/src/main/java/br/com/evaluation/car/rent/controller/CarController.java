package br.com.evaluation.car.rent.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.evaluation.car.rent.domain.S3File;
import br.com.evaluation.car.rent.entity.Car;
import br.com.evaluation.car.rent.service.AwsFeaturesService;
import br.com.evaluation.car.rent.service.CarService;
import br.com.evaluation.car.rent.validator.CarValidation;
/**
 * 
 * @author gabriel.santos
 *
 */
@RestController
@RequestMapping("/car")
@CrossOrigin
public class CarController {

	@Autowired
	private CarService service;
	
	@Autowired
	private CarValidation validator;
	
	@Autowired
	private AwsFeaturesService awsFeaturesService;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/save")
	public Car save(@RequestBody @Valid  Car car) {
		return service.save(car);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/findByCategory/{categoryId}")
	public Iterable<Car> findByCategoryId(@PathVariable(value = "categoryId") Long categoryId) {
		return service.findByCategoryId(categoryId);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/findById/{id}")
	public Car findById(@PathVariable(value = "findById") Long id) {
		return service.findById(id);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/findAll")
	public Iterable<Car> findAll() {
		return service.findAll();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/delete/{id}")
	public void remove(@PathVariable(value = "id") Long id) {
		service.delete(id);
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/uploadImage")
	public S3File uploadFile(@RequestParam("file") MultipartFile file) {
		return awsFeaturesService.storageImageS3(file);
	}
	
}
