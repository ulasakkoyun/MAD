package com.ulasakkoyun.week6;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String name;
    private String director;
    private int year;
    private List<String> stars = new ArrayList<>();

    public Movie(String name, String director, int year, List<String> stars) {
        this.name = name;
        this.director = director;
        this.year = year;
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<String> getStars() {
        return stars;
    }

    public void setStars(List<String> stars) {
        this.stars = stars;
    }
}
