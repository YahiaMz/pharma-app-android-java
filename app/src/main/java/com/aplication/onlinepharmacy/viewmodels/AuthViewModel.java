package com.aplication.onlinepharmacy.viewmodels;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.aplication.onlinepharmacy.repositories.AuthRepository;

public class AuthViewModel extends ViewModel {

    private AuthRepository authRepository;

    public MutableLiveData<Boolean> login(Context context , String email , String password) {
        this.authRepository = AuthRepository.getInstance(context);
        return authRepository.login(email ,password);
    }




}
