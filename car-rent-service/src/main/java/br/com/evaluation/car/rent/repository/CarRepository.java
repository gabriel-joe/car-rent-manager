package br.com.evaluation.car.rent.repository;

import org.springframework.data.repository.Repository;

import br.com.evaluation.car.rent.entity.Car;

public interface CarRepository extends Repository<Car, Long>  {

	public Iterable<Car> findByCategoryId(Long categoryId);
	
	public Iterable<Car> findAll();
	
	public Car findById(Long id);
	
	public Car findByManufacturerAndModelNameAndModelYear(String manufacturer, String modelName, Integer modelYear);
	
	public Car findByManufacturer(String manufacturer);
	
    public Car save(Car car);
    
    public void deleteById(Long id);
    
    // @Query("SELECT c from (SELECT c.id, (c.weekdayPrice * :weekDays) + (c.weekendPrice * :weekendDays) price from Car c) as car inner join Car c where car.id = c.id order by price desc, c.category.id desc")
    // Car findChepeastRent(@Param("weekDays") long weekDays, @Param("weekendDays") long weekendDays);
}
