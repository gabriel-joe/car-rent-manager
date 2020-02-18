package br.com.evaluation.car.rent.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Car {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String manufacturer;
	private String modelName;
	private Integer modelYear;
	
	@ManyToOne
    @JoinColumn(name = "category_id")
	private Category category;
	
	private double weekdayPrice;
	private double weekendPrice;
	private double weekdayLoyaltyPrice;
	private double weekendLoyaltyPrice;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacter) {
		this.manufacturer = manufacter;
	}
	
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public double getWeekdayPrice() {
		return weekdayPrice;
	}
	public void setWeekdayPrice(double weekdayPrice) {
		this.weekdayPrice = weekdayPrice;
	}
	public double getWeekendPrice() {
		return weekendPrice;
	}
	public void setWeekendPrice(double weekendPrice) {
		this.weekendPrice = weekendPrice;
	}
	public double getWeekdayLoyaltyPrice() {
		return weekdayLoyaltyPrice;
	}
	public void setWeekdayLoyaltyPrice(double weekdayLoyaltyPrice) {
		this.weekdayLoyaltyPrice = weekdayLoyaltyPrice;
	}
	public double getWeekendLoyaltyPrice() {
		return weekendLoyaltyPrice;
	}
	public void setWeekendLoyaltyPrice(double weekendLoyaltyPrice) {
		this.weekendLoyaltyPrice = weekendLoyaltyPrice;
	}
	
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public Integer getModelYear() {
		return modelYear;
	}
	public void setModelYear(Integer modelYear) {
		this.modelYear = modelYear;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((manufacturer == null) ? 0 : manufacturer.hashCode());
		result = prime * result + ((modelName == null) ? 0 : modelName.hashCode());
		result = prime * result + ((modelYear == null) ? 0 : modelYear.hashCode());
		long temp;
		temp = Double.doubleToLongBits(weekdayLoyaltyPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(weekdayPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(weekendLoyaltyPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(weekendPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (manufacturer == null) {
			if (other.manufacturer != null)
				return false;
		} else if (!manufacturer.equals(other.manufacturer))
			return false;
		if (modelName == null) {
			if (other.modelName != null)
				return false;
		} else if (!modelName.equals(other.modelName))
			return false;
		if (modelYear == null) {
			if (other.modelYear != null)
				return false;
		} else if (!modelYear.equals(other.modelYear))
			return false;
		if (Double.doubleToLongBits(weekdayLoyaltyPrice) != Double.doubleToLongBits(other.weekdayLoyaltyPrice))
			return false;
		if (Double.doubleToLongBits(weekdayPrice) != Double.doubleToLongBits(other.weekdayPrice))
			return false;
		if (Double.doubleToLongBits(weekendLoyaltyPrice) != Double.doubleToLongBits(other.weekendLoyaltyPrice))
			return false;
		if (Double.doubleToLongBits(weekendPrice) != Double.doubleToLongBits(other.weekendPrice))
			return false;
		return true;
	}
	
}
