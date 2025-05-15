package com.capgemini.CineBuzz.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.capgemini.CineBuzz.entities.Booking;


public interface BookingRepository extends JpaRepository<Booking, Long> {

	List<Booking> findByUser_Name(String name);


}