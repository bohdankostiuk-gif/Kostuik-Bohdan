package com.ua.gamestore.domain;

import java.math.BigDecimal;

public class Game {

    private Long id;
    private String title;
    private String studio;
    private String platform;
    private String genre;
    private Integer releaseYear;
    private BigDecimal price;
    private String posterUrl;
    private String description;
    private String ageRating;

    public Game() {
    }

    public Game(Long id, String title, String studio, String platform, String genre,
                Integer releaseYear, BigDecimal price, String posterUrl,
                String description, String ageRating) {
        this.id = id;
        this.title = title;
        this.studio = studio;
        this.platform = platform;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.price = price;
        this.posterUrl = posterUrl;
        this.description = description;
        this.ageRating = ageRating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudio() {
        return studio;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }
}
