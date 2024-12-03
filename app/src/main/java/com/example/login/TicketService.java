package com.example.login;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface TicketService {
    @GET("/api/ticket/tour/{tourId}")
    Call<List<Ticket>> getTicketbyTour(@Path("tourId") Long tourId);
}
