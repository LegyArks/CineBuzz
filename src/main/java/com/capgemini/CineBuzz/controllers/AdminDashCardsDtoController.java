package com.capgemini.CineBuzz.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capgemini.CineBuzz.services.AdminDashCardsDtoService;
import com.capgemini.CineBuzz.dto.AdminDashCardsDto;
@RestController
@RequestMapping("/api/adminDashboard")
public class AdminDashCardsDtoController {
	
    private AdminDashCardsDtoService dashboardService;

    @GetMapping("/Cards")
    public ResponseEntity<AdminDashCardsDto> getDashboardCards() {
    	AdminDashCardsDto dto = dashboardService.getDashboardCards();
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}

