package com.example.daisy_mobile.ui.kitchenorder;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class kitchenorderviewmodel extends ViewModel {
    private MutableLiveData<String> mText;

    public kitchenorderviewmodel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
