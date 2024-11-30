package com.example.login;
import android.util.Base64;

public class TourImage {

    private Long imageId;
    private Tour tour; // Tham chiếu đến đối tượng Tour
    private String imageData;

    public TourImage(String imageData) {
        this.imageData = imageData;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public Tour getTour() {
        return tour;
    }

    public void setTour(Tour tour) {
        this.tour = tour;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
