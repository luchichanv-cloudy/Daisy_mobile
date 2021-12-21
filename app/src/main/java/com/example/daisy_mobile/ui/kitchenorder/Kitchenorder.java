package com.example.daisy_mobile.ui.kitchenorder;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.daisy_mobile.R;
import com.example.daisy_mobile.databinding.FragmentKitchenmenuBinding;
import com.example.daisy_mobile.databinding.FragmentKitchenorderBinding;
import com.example.daisy_mobile.databinding.FragmentOrderBinding;
import com.example.daisy_mobile.ui.menu.KitchenmenuViewModel;
import com.example.daisy_mobile.ui.order.OrderViewModel;


public class Kitchenorder extends Fragment {
private FragmentKitchenorderBinding binding;
private kitchenorderviewmodel viewmodel;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_kitchenorder, container, false);

        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}