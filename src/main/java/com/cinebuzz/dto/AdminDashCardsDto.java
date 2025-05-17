package com.cinebuzz.dto;

import java.util.List;

public class AdminDashCardsDto {

    private Double totalRevenue;
    private Long totalTicketsSold;
    private String mostWatchedMovie;
    private String leastWatchedMovie;
    private List<MovieRevenueReportDto> movieRevenueReport;

    public AdminDashCardsDto() {}

    public AdminDashCardsDto(Double totalRevenue, Long totalTicketsSold, String mostWatchedMovie, String leastWatchedMovie) {
        this.totalRevenue = totalRevenue;
        this.totalTicketsSold = totalTicketsSold;
        this.mostWatchedMovie = mostWatchedMovie;
        this.leastWatchedMovie = leastWatchedMovie;
    }

    public Double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public Long getTotalTicketsSold() {
        return totalTicketsSold;
    }

    public void setTotalTicketsSold(Long totalTicketsSold) {
        this.totalTicketsSold = totalTicketsSold;
    }

    public String getMostWatchedMovie() {
        return mostWatchedMovie;
    }

    public void setMostWatchedMovie(String mostWatchedMovie) {
        this.mostWatchedMovie = mostWatchedMovie;
    }

    public String getLeastWatchedMovie() {
        return leastWatchedMovie;
    }

    public void setLeastWatchedMovie(String leastWatchedMovie) {
        this.leastWatchedMovie = leastWatchedMovie;
    }

    public List<MovieRevenueReportDto> getMovieRevenueReport() {
        return movieRevenueReport;
    }

    public void setMovieRevenueReport(List<MovieRevenueReportDto> movieRevenueReport) {
        this.movieRevenueReport = movieRevenueReport;
    }

    @Override
    public String toString() {
        return "AdminDashCardsDto [totalRevenue=" + totalRevenue + ", totalTicketsSold=" + totalTicketsSold
                + ", mostWatchedMovie=" + mostWatchedMovie + ", leastWatchedMovie=" + leastWatchedMovie + "]";
   
	}

}
