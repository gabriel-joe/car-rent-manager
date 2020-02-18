package br.com.evaluation.car.rent.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import br.com.evaluation.car.rent.domain.CarRentSearch;
import br.com.evaluation.car.rent.domain.SESEmail;
import br.com.evaluation.car.rent.service.AwsFeaturesService;
import br.com.evaluation.car.rent.util.OperationUtil;

@Component
public class CarRentSearchValidator implements Validator {
	
	@Autowired
	private AwsFeaturesService awsFeaturesServices;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return CarRentSearch.class.isAssignableFrom(clazz) || SESEmail.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		if(target instanceof CarRentSearch) {
			validateCarRentSearch(target, errors); 
		} else if(target instanceof SESEmail) {
			validateSESEmail(target, errors);
		}
		
	}

	/**
	 * Validate CarRentSearch Object
	 * @param target
	 * @param errors
	 */
	private void validateCarRentSearch(Object target, Errors errors) {
		CarRentSearch carRentSearch = (CarRentSearch) target;
		
		if(OperationUtil.checkValue(carRentSearch.getDateFrom())) {
			errors.rejectValue("dateFrom", "carRentSearch.dateFrom.invalid", "DateFrom must be informed!");
		} else if(OperationUtil.checkValue(carRentSearch.getDateTo())) { 
			errors.rejectValue("dateTo", "carRentSearch.dateTo.invalid", "DateTo must be informed!");
		} else if(carRentSearch.getDateTo().compareTo(carRentSearch.getDateFrom()) <= 0) { 
			errors.rejectValue("dateFrom", "carRentSearch.dateTo.invalid", "DateTo must be after Start date (DateFrom)!");
		}
	}
	
	/**
	 * Validate SESEmail Object
	 * @param target
	 * @param errors
	 */
	private void validateSESEmail(Object target, Errors errors) {
		SESEmail sesEmail = (SESEmail) target;
		
		if(OperationUtil.checkValue(sesEmail.getEmail())) {
			errors.rejectValue("email", "sesEmail.email.invalid", "Email must be informed!");
		} else if(OperationUtil.checkValue(sesEmail.getSubject())) { 
			errors.rejectValue("subject", "sesEmail.subject.invalid", "Subject must be informed!");
		} else if(OperationUtil.checkValue(sesEmail.getContent())) { 
			errors.rejectValue("content", "sesEmail.content.invalid", "Content must be informed!");
		} else if(!awsFeaturesServices.checkEmailVerified(sesEmail.getEmail())) {
			awsFeaturesServices.sesVerifyEmail(sesEmail.getEmail());
			errors.rejectValue("content", "sesEmail.content.invalid", "Check your e-mail to verify your account to receive an AWS SES Email!");
		}
	}
    
}
