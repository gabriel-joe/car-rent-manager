package br.com.evaluation.car.rent.domain;

import java.io.Serializable;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class CarRentSearch implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1134766752302408052L;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;
	private boolean loyalty;
	
	public LocalDate getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}
	public LocalDate getDateTo() {
		return dateTo;
	}
	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}
	public boolean isLoyalty() {
		return loyalty;
	}
	public void setLoyalty(boolean loyalty) {
		this.loyalty = loyalty;
	}
	
}
