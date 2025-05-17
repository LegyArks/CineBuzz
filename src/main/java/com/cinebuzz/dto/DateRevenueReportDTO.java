package com.cinebuzz.dto;

import java.time.LocalDate;

public class DateRevenueReportDTO {
    private LocalDate date;
    private double totalRevenue;
    private int totalTickets;

    public DateRevenueReportDTO(LocalDate date, double totalRevenue, int totalTickets) {
        this.date = date;
        this.totalRevenue = totalRevenue;
        this.totalTickets = totalTickets;
    }

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public int getTotalTickets() {
		return totalTickets;
	}

	public void setTotalTickets(int totalTickets) {
		this.totalTickets = totalTickets;
	}
    

    // getters & setters
}
