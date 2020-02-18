package br.com.evaluation.car.rent.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.evaluation.car.rent.entity.Car;
import br.com.evaluation.car.rent.repository.CarRepository;
import br.com.evaluation.car.rent.service.CarService;

@Service
public class CarServiceImpl implements CarService {

	@Autowired
	private CarRepository carRepository;
	
	@Override
	public Car save(Car p) {
		return carRepository.save(p);
	}

	@Override
	public Iterable<Car> findByCategoryId(Long categoryId) {
		return carRepository.findByCategoryId(categoryId);
	}

	@Override
	public void delete(Long id) {
		carRepository.deleteById(id);
	}

	@Override
	public Car findById(Long id) {
		return carRepository.findById(id);
	}
	
	
	@Override
	public Car findByManufacturerAndModelNameAndModelYear(String manufacter, String modelName, Integer modelYear) {
		return carRepository.findByManufacturerAndModelNameAndModelYear(manufacter, modelName, modelYear);
	}

	@Override
	public Iterable<Car> findAll() {
		return carRepository.findAll();
	}
	
}
