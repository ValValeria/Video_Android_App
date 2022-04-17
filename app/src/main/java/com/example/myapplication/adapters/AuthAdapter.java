package com.example.myapplication.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.ui.login.LoginFragment;
import com.example.myapplication.ui.signup.SignupFragment;

public class AuthAdapter extends FragmentStateAdapter {
    public AuthAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;

        if (position == 1) {
            fragment = new SignupFragment();
        } else {
            fragment = new LoginFragment();
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
