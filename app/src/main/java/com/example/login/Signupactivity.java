package com.example.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signupactivity extends AppCompatActivity {

    EditText editsdt,editemail,edituser,editpassword,editrepassword;
    Button btnsignup;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signupactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        editemail = findViewById(R.id.editTextMail);
        editsdt = findViewById(R.id.editTextPhoneNumber);
        edituser = findViewById(R.id.editTextUser);
        editpassword = findViewById(R.id.editTextPassword);
        editrepassword = findViewById(R.id.editTextXNPassword);
        btnsignup = findViewById(R.id.btnDKe);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editemail.getText().toString().trim();
                String phone = editsdt.getText().toString().trim();
                String userName = edituser.getText().toString().trim();
                String password = editpassword.getText().toString();
                String rePassword = editrepassword.getText().toString();

                // Kiểm tra thông tin đầu vào
                if (email.isEmpty() || phone.isEmpty() || userName.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng điền đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userName.length() <= 3) {
                    Toast.makeText(getApplicationContext(), "Tên người dùng phải lớn hơn 3 ký tự!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
                    Toast.makeText(getApplicationContext(), "Email không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 8) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu phải có ít nhất 8 ký tự!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.matches("^(?=.*[a-zA-Z])(?=.*\\d).+$")) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu phải chứa cả chữ và số!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(rePassword)) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo đối tượng User
                User newUser = new User();
                newUser.setUserName(userName);
                newUser.setPassword(password);
                newUser.setEmail(email);
                newUser.setPhone(phone);

                // Gửi yêu cầu đăng ký
                SignupUser(newUser);
            }
        });
    }
    private void SignupUser(User user) {
        UserService userApi = RetrofitClinet.getRetrofitInstance().create(UserService.class);

        Call<Void> call = userApi.signupUser(user);
        call.enqueue(new retrofit2.Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, retrofit2.Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Đăng ký thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }




}