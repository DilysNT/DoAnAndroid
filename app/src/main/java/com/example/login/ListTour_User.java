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

        String searchLocation = getIntent().getStringExtra("test");
        loadtimkiem(searchLocation);
        
    }

    private void loadtimkiem(String location) {
        TourService tourService  =RetrofitClinet.getRetrofitInstance().create(TourService.class);
        Call<List<Tour>> call = tourService.searchTours(location);
        call.enqueue(new Callback<List<Tour>>() {
            @Override
            public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Cập nhật dữ liệu cho Adapter
                    tourUserAdapter.clear();
                    tourUserAdapter.addAll(response.body());
                    tourUserAdapter.notifyDataSetChanged();
                } else {
                    // Xử lý trường hợp không có dữ liệu
                }
            }

            @Override
            public void onFailure(Call<List<Tour>> call, Throwable t) {
                // Xử lý lỗi
            }
        });
    }
}