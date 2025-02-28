package com.example.login;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeUser extends AppCompatActivity {
    EditText etLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etLocation = findViewById(R.id.et_location);
        Button Search = findViewById(R.id.btn_search);
        Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent timkiem = new Intent(HomeUser.this, ListTour_User.class);
               String lcs = etLocation.getText().toString();
               timkiem.putExtra("test",lcs);
               startActivity(timkiem);
            }
        });
        ImageButton Imagebtn = findViewById(R.id.btnFilter);
        Imagebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });

    }
    private void showBottomSheetDialog() {
        // Tạo BottomSheetDialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(HomeUser.this);

        // Inflate layout cho Bottom Sheet
        View bottomSheetView = LayoutInflater.from(this).inflate(
                R.layout.filter_bottom_sheet, // Layout của Bottom Sheet
                null // Không gắn trực tiếp vào root
        );

        // Truy cập các thành phần bên trong Bottom Sheet
        Spinner spinnerDestination = bottomSheetView.findViewById(R.id.spinnerDestination);
        EditText etQuantity = bottomSheetView.findViewById(R.id.etQuantity);
        Button btnApplyFilters = bottomSheetView.findViewById(R.id.btnApplyFilters);

        // Xử lý sự kiện cho nút Tìm kiếm
        btnApplyFilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thêm logic tại đây (ví dụ: xử lý dữ liệu)
                bottomSheetDialog.dismiss(); // Đóng Bottom Sheet sau khi xử lý
            }
        });

        // Gắn view vào BottomSheetDialog
        bottomSheetDialog.setContentView(bottomSheetView);

        // Hiển thị BottomSheetDialog
        bottomSheetDialog.show();
    }

}
