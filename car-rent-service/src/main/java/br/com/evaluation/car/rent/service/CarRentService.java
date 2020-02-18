package br.com.evaluation.car.rent.service;

import br.com.evaluation.car.rent.domain.CarRent;
import br.com.evaluation.car.rent.domain.CarRentSearch;

public interface CarRentService {
	
	public CarRent searchChepeastRent(CarRentSearch carRentSearch);
	
}
