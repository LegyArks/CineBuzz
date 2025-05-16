package com.capgemini.CineBuzz.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.CineBuzz.dto.AdminDashCardsDto;
import com.capgemini.CineBuzz.services.AdminDashCardsDtoService;


@RestController
@RequestMapping("/api/adminDashCards")
public class AdminDashCardsDtoController {

    private AdminDashCardsDtoService adminDashCardsDtoService;

    @Autowired
    public AdminDashCardsDtoController(AdminDashCardsDtoService adminDashCardsDtoService) {
        this.adminDashCardsDtoService = adminDashCardsDtoService;
    }

    @GetMapping("dashboard")
    public ResponseEntity<AdminDashCardsDto> getDashboardStats() {
    	AdminDashCardsDto card = adminDashCardsDtoService.getDashboardCards();
        return new ResponseEntity<>(card, HttpStatus.OK);
    }
}