package br.com.evaluation.car.rent.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.evaluation.car.rent.entity.Car;
import br.com.evaluation.car.rent.service.CarService;
import br.com.evaluation.car.rent.service.CategoryService;
import br.com.evaluation.car.rent.util.OperationUtil;

@Component
public class CarValidation implements Validator {
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Car.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Car car = (Car) target;
		
		if(OperationUtil.checkValue(car.getManufacturer())) {
			errors.rejectValue("manufacturer", "car.manufacturer.invalid", "Manufacturer must be informed!");
		} else if(OperationUtil.checkValue(car.getModelName())) { 
		    errors.rejectValue("modelName","car.modelName.invalid", "Model name must be informed!"); 
		} else if(OperationUtil.checkValue(car.getModelYear())) { 
		    errors.rejectValue("modelYear","car.modelYear.invalid", "Model name must be informed!"); 
		} else if(OperationUtil.checkValue(car.getCategory()) || OperationUtil.checkValue(car.getCategory().getId())) { 
			errors.rejectValue("category","car.category.invalid", "Category must be informed!");
		} else if(OperationUtil.checkValue(car.getWeekdayPrice())) {
			errors.rejectValue("weekdayPrice", "car.weekdayPrice.invalid", "Weekday Price must be informed!");
		} else if(OperationUtil.checkValue(car.getWeekendPrice())) {
			errors.rejectValue("weekendPrice", "car.weekendPrice.invalid", "Weekend Price must be informed!");
		} else if(OperationUtil.checkValue(car.getWeekdayLoyaltyPrice())) {
			errors.rejectValue("weekdayLoyaltyPrice", "car.weekdayLoyaltyPrice.invalid", "Weekday Loyalty Price must be informed!");
		} else if(OperationUtil.checkValue(car.getWeekendLoyaltyPrice())) {
			errors.rejectValue("weekendLoyaltyPrice", "car.weekendLoyaltyPrice.invalid", "Weekend Loyalty Price must be informed!");
		} else if(carService.findByManufacturerAndModelNameAndModelYear(car.getManufacturer(), car.getModelName(), car.getModelYear()) != null) {
			errors.rejectValue("modelName", "car.modelName.invalid", "A registry with this manufacter, modelName and modelYear already exists in database!");
		} else if(categoryService.findById(car.getCategory().getId()) == null) {
			errors.rejectValue("category", "car.category.invalid", "Category informed doesn't exists in database!");
		}
		
	}
	
    
}
