package com.example.login;
import android.util.Base64;

import java.io.Serializable;

public class TourImage implements Serializable {

    private Long image_id;
    private Tour tour; // Tham chiếu đến đối tượng Tour
    private String imageData;

    public TourImage(String imageData) {
        this.imageData = imageData;
    }

    public Long getImage_id() {
        return image_id;
    }

    public void setImage_id(Long image_id) {
        this.image_id = image_id;
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
