package com.example.login;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.login.fragment.ViewpagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class AdminMainMenu extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_main_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
            viewPager = findViewById(R.id.viewpager);
            bottomNavigationView = findViewById(R.id.bottomnative);
            ViewpagerAdapter adapter = new ViewpagerAdapter(this);

            viewPager.setAdapter(adapter);

            bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                if (item.getItemId() == R.id.itemhome) {
                    viewPager.setCurrentItem(0);
                    return true;
                }
                else if (item.getItemId() == R.id.itemticket){
                    viewPager.setCurrentItem(1);
                    return true;
                } else if (item.getItemId() == R.id.itemthongbao) {
                    viewPager.setCurrentItem(2);
                    return true;
                } else if (item.getItemId() == R.id.itemdangxuat) {
                    viewPager.setCurrentItem(3);
                    return true;

                }
                return false;
            });
            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0:
                            bottomNavigationView.setSelectedItemId(R.id.itemhome);
                            break;
                        case 1:
                            bottomNavigationView.setSelectedItemId(R.id.itemticket);
                        case 2:
                            bottomNavigationView.setSelectedItemId(R.id.itemthongbao);
                        case 3:
                            bottomNavigationView.setSelectedItemId(R.id.itemdangxuat);
                    }

                }
            });

    }
}