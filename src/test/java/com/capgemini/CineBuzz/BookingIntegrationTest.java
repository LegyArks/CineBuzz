package com.capgemini.CineBuzz;

import com.capgemini.CineBuzz.entities.Booking;
import com.capgemini.CineBuzz.entities.User;
import com.capgemini.CineBuzz.repositories.BookingRepository;
import com.capgemini.CineBuzz.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    public void setUp() {
        bookingRepository.deleteAll();
        userRepository.deleteAll();

        user = new User();
        user.setName("Test User");
        user.setPassword("Test123"); 
        user.setPhoneNumber("9234567890"); 
        user.setUserType("Admin");
        user.setEmail("test@example.com"); 
        user = userRepository.save(user);
    }
    
    @Test
    public void testCreateBookingAndGetById() throws Exception {
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSeatsBooked(3);
        booking.setAmount(750L);  
        booking.setBookingDate(LocalDate.now());

        String response = mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(booking)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Booking createdBooking = objectMapper.readValue(response, Booking.class);

        mockMvc.perform(get("/api/bookings/" + createdBooking.getBookingId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seatsBooked").value(3))
                .andExpect(jsonPath("$.amount").value(750));
    }

    @Test
    public void testGetAllBookings() throws Exception {
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSeatsBooked(2);
        booking.setAmount(500L);
        booking.setBookingDate(LocalDate.now());
        bookingRepository.save(booking);

        mockMvc.perform(get("/api/bookings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void testDeleteBooking() throws Exception {
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSeatsBooked(1);
        booking.setAmount(300L);
        booking.setBookingDate(LocalDate.now());
        booking = bookingRepository.save(booking);

        mockMvc.perform(delete("/api/bookings/" + booking.getBookingId()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/bookings/" + booking.getBookingId()))
                .andExpect(status().isNotFound());
    }
}
