package com.example.login;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTour_User extends AppCompatActivity {
    TourUserAdapter tourUserAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_tour_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lv =findViewById(R.id.lvTour);
        tourUserAdapter=new TourUserAdapter(this,new ArrayList<>());
        lv.setAdapter(tourUserAdapter);

        TourService tourService = RetrofitClinet.getRetrofitInstance().create(TourService.class);
//        Call<List<Tour>> test=RetrofitClinet.getRetrofitInstance().create(TourService.class).getAllTours();
//        test.enqueue(new Callback<List<Tour>>() {
//            @Override
//            public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
//                List<Tour> a=response.body();
//                tourUserAdapter.addAll(a);
//            }
//
//            @Override
//            public void onFailure(Call<List<Tour>> call, Throwable t) {
//
//            }
//        });
    }
}