package com.example.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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

public class Loginactivity extends AppCompatActivity {
    EditText edittk, editmk;
    Button btnlogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loginactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edittk = findViewById(R.id.editUserDN);
        editmk = findViewById(R.id.editUserDNMK);
        btnlogin = findViewById(R.id.btnlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edittk.getText().toString();
                String password = editmk.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Loginactivity.this, "Vui lòng nhập tài khoản và mật khẩu", Toast.LENGTH_LONG).show();
                } else {
                    Login(username, password);
                }
            }
        });
    }

    private void Login(String username, String password) {
        User user = new User(username, password);
        UserService userService = RetrofitClinet.getRetrofitInstance().create(UserService.class);
        Call<User> calluser = userService.Login(user);

        calluser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null) {
                    User logginuser = response.body();
                    Toast.makeText(Loginactivity.this, "Xin chào " + response.body().getUserName(), Toast.LENGTH_LONG).show();
                    if (logginuser.getIsAdmin()==1){
                        Intent adminintent = new Intent(Loginactivity.this,AdminMainMenu.class);
                        startActivity(adminintent);
                    }
                    else{
                        Intent userintent = new Intent(Loginactivity.this,HomeUser.class);
                        startActivity(userintent);
                    }
                } else {
                    Toast.makeText(Loginactivity.this, "Đăng nhập không thành công", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(Loginactivity.this, "Có lỗi: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}