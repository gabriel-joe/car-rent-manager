package br.com.evaluation.car.rent.service;

import br.com.evaluation.car.rent.entity.Car;

public interface CarService {

	public Car save(Car car);
	
	public Iterable<Car> findByCategoryId(Long categoryId);
	
	public void delete(Long id);
	
	public Car findById(Long id);
	
	public Iterable<Car> findAll();
	
	public Car findByManufacturerAndModelNameAndModelYear(String manufacter, String modelName, Integer modelYear);
	
	
}
