package com.example.login.fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewpagerAdapter extends FragmentStateAdapter {
    public ViewpagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                    return new  HouseFragment();
            case 1:
                return new TicketFragment();
            case 2:
                return new ThongbaoFragment();
            case 3:
                return new AdminFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
