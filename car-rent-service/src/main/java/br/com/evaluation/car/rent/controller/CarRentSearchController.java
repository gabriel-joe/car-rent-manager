package br.com.evaluation.car.rent.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.evaluation.car.rent.domain.CarRent;
import br.com.evaluation.car.rent.domain.CarRentSearch;
import br.com.evaluation.car.rent.domain.SESEmail;
import br.com.evaluation.car.rent.service.AwsFeaturesService;
import br.com.evaluation.car.rent.service.CarRentService;
import br.com.evaluation.car.rent.validator.CarRentSearchValidator;
/**
 * 
 * @author gabriel.santos
 *
 */
@RestController
@RequestMapping("/rent")
@CrossOrigin
public class CarRentSearchController {

	@Autowired
	private CarRentService carRentService;
	
	@Autowired
	private AwsFeaturesService awsFeaturesService;
	
	@Autowired
	private CarRentSearchValidator carRentSearchValidator;
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(carRentSearchValidator);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/search")
	public CarRent search(@RequestBody @Valid  CarRentSearch carRentSearch) {
		return carRentService.searchChepeastRent(carRentSearch);
	}
	
	
	@RequestMapping(method = RequestMethod.POST, path = "/sendEmail")
	public void sendEmail(@RequestBody @Valid SESEmail sesEmail) {
		awsFeaturesService.sendSESEmail(sesEmail);
	}

	
}
