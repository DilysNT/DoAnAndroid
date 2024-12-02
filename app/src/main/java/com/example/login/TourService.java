package com.example.login;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TourService {
    @GET("/api/tours")
    Call<List<Tour>> getAllTours();

    @Multipart
    @POST("/api/tours")
    Call<Tour> createTour(@Part("tour") RequestBody tour, @Part List<MultipartBody.Part> images);


    @Multipart
    @PUT("/api/tours/{id}")
    Call<Tour> updateTour(
            @Path("id") Long id,
            @Part("tour") RequestBody tourJson,
            @Part List<MultipartBody.Part> images
    );


    @DELETE("/api/tours/{id}")
    Call<Void> deleteTour(@Path("id") Long id);

    @GET("/api/tours/search")
    Call<List<Tour>> searchTours(@Query("tourName") String tourName);
}
