package com.example.login;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class Tour implements Serializable {
    private Long tourId;
    private String tourName;
    private String destination;
    private BigDecimal price;
    private Date startDate;
    private Date endDate;
    private String description;
    private List<TourImage> images;

    public Tour(String tourName, String destination, BigDecimal price, Date startDate, Date endDate, String description, List<TourImage> images) {
        this.tourName = tourName;
        this.destination = destination;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.images = images;
    }

    public Tour(Long tourId, String tourName, String destination, BigDecimal price, Date startDate, Date endDate, String description, List<TourImage> images) {
        this.tourId = tourId;
        this.tourName = tourName;
        this.destination = destination;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.images = images;
    }
    public Tour() {}

    public long getTourId() {
        return this.tourId;
    }

    public void setTourId(Long tourId) {
        this.tourId = tourId;
    }

    public String getTourName() {
        return tourName;
    }

    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TourImage> getImages() {
        return images;
    }

    public void setImages(List<TourImage> images) {
        this.images = images;
    }
}
