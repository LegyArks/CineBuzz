package com.capgemini.CineBuzz.dto;

public class MovieRevenueReportDto {
 private String movieTitle;
 private Long totalTicketsSold;
 private Double totalRevenue;

 public MovieRevenueReportDto(String movieTitle, Long totalTicketsSold, Double totalRevenue) {
     this.movieTitle = movieTitle;
     this.totalTicketsSold = totalTicketsSold;
     this.totalRevenue = totalRevenue;
 }

 public String getMovieTitle() {
     return movieTitle;
 }

 public void setMovieTitle(String movieTitle) {
     this.movieTitle = movieTitle;
 }

 public Long getTotalTicketsSold() {
     return totalTicketsSold;
 }

 public void setTotalTicketsSold(Long totalTicketsSold) {
     this.totalTicketsSold = totalTicketsSold;
 }

 public Double getTotalRevenue() {
     return totalRevenue;
 }

 public void setTotalRevenue(Double totalRevenue) {
     this.totalRevenue = totalRevenue;
 }
}
