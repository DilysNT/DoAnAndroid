package com.example.login;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import java.text.SimpleDateFormat;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourUserAdapter extends ArrayAdapter<Tour> {
    public TourUserAdapter(@NonNull Context context, @NonNull List<Tour> objects) {
        super(context,0, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView,@NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_tour_ui, parent, false);
        }
        // Bind views
        Tour tour = (Tour) getItem(position);
        ImageView imageView = convertView.findViewById(R.id.imageView3);
        TextView tvPrice = convertView.findViewById(R.id.txt_price);
        TextView tvLocation = convertView.findViewById(R.id.txtlocation);
        TextView tvStartDate = convertView.findViewById(R.id.txtstart_date);
        TextView tvTime = convertView.findViewById(R.id.txtTime);
        TextView tvDescription = convertView.findViewById(R.id.txtSL);
        TextView tvPT = convertView.findViewById(R.id.txtPT);


        if (tour.getImages() != null && !tour.getImages().isEmpty()) {
            // Lấy hình ảnh đầu tiên
            TourImage tourImage = tour.getImages().get(0);

            // Nếu imageData là một chuỗi Base64
            String base64Image = tourImage.getImageData();

            // Sử dụng Glide để tải hình ảnh từ Base64
            Glide.with(this.getContext())
                    .load("data:image/jpeg;base64," + base64Image)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.bell); // Hình ảnh mặc định nếu không có
        }

        tvPrice.setText("Giá: " + tour.getPrice().toString() + " VND");
        tvLocation.setText(tour.getDestination());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        tvStartDate.setText("Ngày bắt đầu: " + dateFormat.format(tour.getStartDate()));
        tvPT.setText("Ngày kết thúc: " + dateFormat.format(tour.getEndDate()));
        tvDescription.setText(tour.getDescription());
        return convertView;
    }

    // Phương thức giải mã Base64 thành Bitmap
}
