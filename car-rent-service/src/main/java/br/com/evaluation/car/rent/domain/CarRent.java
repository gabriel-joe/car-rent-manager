package br.com.evaluation.car.rent.domain;

import java.io.Serializable;

public class CarRent implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2607671521303721792L;
	private Long id;
	private Double sumPrice;
	private Long categoryId;
	private String modelComplete;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Double getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}
	
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		CarRent other = (CarRent) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	public String getModelComplete() {
		return modelComplete;
	}
	public void setModelComplete(String modelComplete) {
		this.modelComplete = modelComplete;
	}
	
	
}
