package com.capgemini.CineBuzz.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capgemini.CineBuzz.entities.Booking;


public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findByUser_Name(String name);

}