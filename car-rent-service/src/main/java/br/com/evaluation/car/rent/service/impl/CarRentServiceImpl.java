package br.com.evaluation.car.rent.service.impl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.evaluation.car.rent.domain.CarRent;
import br.com.evaluation.car.rent.domain.CarRentSearch;
import br.com.evaluation.car.rent.entity.Car;
import br.com.evaluation.car.rent.repository.CarRepository;
import br.com.evaluation.car.rent.service.CarRentService;

@Service
public class CarRentServiceImpl implements CarRentService {
	
	
	@Autowired
	private CarRepository carRepository;
	
	@Override
	public CarRent searchChepeastRent(CarRentSearch carRentSearch) {
		long days = ChronoUnit.DAYS.between(carRentSearch.getDateFrom(), carRentSearch.getDateTo());
		long weekDays = calcWeekDays(carRentSearch.getDateFrom(), carRentSearch.getDateTo());
		long weekendDays = days - weekDays;
		Iterable<Car> listCar = carRepository.findAll();
		CarRent chepeast = null;
		if(listCar.iterator().hasNext()) {
			chepeast = getTheCheapestCarRent(carRentSearch, weekDays, weekendDays, listCar);
		}
		return chepeast;
	}

	/**
	 * Method responsible to count weekdays between dates
	 * @param start
	 * @param end
	 * @return
	 */
	private long calcWeekDays(LocalDate start, LocalDate end) {
	    DayOfWeek startW = start.getDayOfWeek();
	    DayOfWeek endW = end.getDayOfWeek();
	    long days = ChronoUnit.DAYS.between(start, end);
	    long daysWithoutWeekends = days - 2 * ((days + startW.getValue())/7);
	    //adjust for starting and ending on a Sunday:
	    return daysWithoutWeekends + (startW == DayOfWeek.SUNDAY ? 1 : 0) + (endW == DayOfWeek.SUNDAY ? 1 : 0);
	}
	
	/**
	 * Method responsible to load {@link CarRent} objects
	 * calculating the sum of prices by weekdays and weekends
	 * @param carRentSearch
	 * @param weekDays
	 * @param weekendDays
	 * @param listCar
	 * @return
	 */
	private Set<CarRent> loadCarRentList(CarRentSearch carRentSearch, long weekDays, long weekendDays, Iterable<Car> listCar) {
		Set<CarRent> listCarRent =  new HashSet<CarRent>();
		listCar.forEach(car -> {
			CarRent carRent = new CarRent();
			carRent.setId(car.getId());
			if(carRentSearch.isLoyalty()) {
				carRent.setSumPrice((car.getWeekdayLoyaltyPrice() * weekDays) + (car.getWeekendLoyaltyPrice() * weekendDays));
			} else {
				carRent.setSumPrice((car.getWeekdayPrice() * weekDays) + (car.getWeekendPrice() * weekendDays));
			}
			carRent.setCategoryId(car.getCategory().getId());
			carRent.setModelComplete(new StringBuilder().append(car.getManufacturer()).append(" ").append(car.getModelName()).append(" ").append(car.getModelYear()).toString());
			listCarRent.add(carRent);
		});
		return listCarRent;
	}
	
	/**
	 * Method responsible to get the cheapest Car
	 * by SumPrice and CategoryId
	 * @param carRentSearch
	 * @param weekDays
	 * @param weekendDays
	 * @param listCar
	 * @return
	 */
	private CarRent getTheCheapestCarRent(CarRentSearch carRentSearch, long weekDays, long weekendDays,
			Iterable<Car> listCar) {
		Set<CarRent> listCarRent = loadCarRentList(carRentSearch, weekDays, weekendDays, listCar);
		Comparator<CarRent> byCategoryId = (p1, p2) -> {
			return p2.getCategoryId().compareTo(p1.getCategoryId());
		};
		CarRent chepeast = listCarRent.stream().min(Comparator.comparing(CarRent::getSumPrice).thenComparing(byCategoryId)).get();
		return chepeast;
	}
}
