package com.example.myapplication.ui.authentication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapters.AuthAdapter;
import com.example.myapplication.databinding.FragmentAuthBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

public class AuthFragment extends Fragment {
    private final FirebaseAuth firebaseAuth;
    private FragmentAuthBinding fragmentAuthBinding;
    private LayoutInflater layoutInflater;
    private AuthAdapter authAdapter;

    public AuthFragment()
    {
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        layoutInflater = LayoutInflater.from(requireContext());
        fragmentAuthBinding = FragmentAuthBinding.inflate(layoutInflater);
        authAdapter = new AuthAdapter(requireActivity());

        return fragmentAuthBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        TabLayout tabLayout = fragmentAuthBinding.tab;
        ViewPager2 viewPager2 = fragmentAuthBinding.pager;
        viewPager2.setAdapter(authAdapter);

        new TabLayoutMediator(tabLayout, fragmentAuthBinding.pager, (tab, position) -> {
             if (position == 0) {
                 tab.setText(R.string.login);
             } else {
                 tab.setText(R.string.sign_up);
             }
        }).attach();
    }
}