package com.example.login;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourAdapter extends ArrayAdapter<Tour> {
    private Context context;
    private List<Tour> tourList;

    public TourAdapter(@NonNull Context context, @NonNull List<Tour> tourList) {
        super(context, 0, tourList);
        this.context = context;
        this.tourList = tourList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_listview_home, parent, false);
        }

        Tour tour = getItem(position);
        ImageView imgview = convertView.findViewById(R.id.image_tour);
        TextView txtprice = convertView.findViewById(R.id.txt_price);
        TextView txtlocation = convertView.findViewById(R.id.txtlocation);
        TextView txtTime = convertView.findViewById(R.id.txtDay);
        TextView txtStartDate = convertView.findViewById(R.id.txtstart_date);
        TextView txttanspot = convertView.findViewById(R.id.txt_transport);

        if (tour.getImages() != null && !tour.getImages().isEmpty()) {
            // Lấy hình ảnh đầu tiên
            TourImage tourImage = tour.getImages().get(0);

            // Nếu imageData là một chuỗi Base64
            String base64Image = tourImage.getImageData();

            // Sử dụng Glide để tải hình ảnh từ Base64
            Glide.with(context)
                    .load("data:image/jpeg;base64," + base64Image)
                    .into(imgview);
        } else {
            imgview.setImageResource(R.drawable.bell); // Hình ảnh mặc định nếu không có
        }

        txtprice.setText("Giá: " + tour.getPrice());
        txtlocation.setText("Điểm đến: " + tour.getDestination());
        txtTime.setText("Thời gian: " + tour.getStartDate() + " - " + tour.getEndDate());
        txtStartDate.setText("Ngày bắt đầu: " + tour.getStartDate());
        txttanspot.setText("Mô tả: " + tour.getDescription());

        return convertView;
    }

}
