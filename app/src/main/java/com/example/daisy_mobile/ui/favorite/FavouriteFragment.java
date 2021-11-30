package com.example.daisy_mobile.ui.favorite;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daisy_mobile.R;
import com.example.daisy_mobile.databinding.FavouriteFragmentBinding;
import com.example.daisy_mobile.databinding.FragmentHomeBinding;
import com.example.daisy_mobile.databinding.FragmentOrderBinding;
import com.example.daisy_mobile.ui.home.HomeViewModel;
import com.example.daisy_mobile.ui.order.OrderViewModel;

public class FavouriteFragment extends Fragment {

    private FavouriteViewModel mViewModel;
    private FavouriteFragmentBinding binding;
    public static FavouriteFragment newInstance() {
        return new FavouriteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                new ViewModelProvider(this).get(FavouriteViewModel.class);

        binding = FavouriteFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textFavorite;
        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }
}