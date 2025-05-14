package com.capgemini.CineBuzz.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;
    private String title;
    private String genre;
    private int duration;
    private String language;
    private double price;
    private boolean isUpcoming;

    private String image;      
    private String description;  
    private String trailer; 
    
    @OneToMany(mappedBy = "movie")
    private List<Showtime> showTime;

    public Movie() {}

    public Movie(Long movieId, String title, String genre, int duration, String language, double price, boolean isUpcoming, String image, String description, String trailer) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.language = language;
        this.price = price;
        this.isUpcoming = isUpcoming;
        this.image = image;
        this.description = description;
        this.trailer = trailer;
    }

    // Getters and Setters

    public Long getId() { return movieId; }
    public void setId(Long movieId) { this.movieId = movieId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public boolean isUpcoming() { return isUpcoming; }
    public void setUpcoming(boolean upcoming) { isUpcoming = upcoming; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTrailer() { return trailer; }
    public void setTrailer(String trailer) { this.trailer = trailer; }

    @Override
    public String toString() {
        return "Movie [movieId=" + movieId + ", title=" + title + ", genre=" + genre + ", duration=" + duration + ", language=" + language + ", price=" + price + ", isUpcoming=" + isUpcoming + ", image=" + image + ", description=" + description + ", trailer=" + trailer + "]";
    }
}
