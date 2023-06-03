package com.aplication.onlinepharmacy.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.aplication.onlinepharmacy.R;
import com.aplication.onlinepharmacy.databinding.ActivityLoginBinding;
import com.aplication.onlinepharmacy.viewmodels.AuthViewModel;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding activityLoginBinding ;
    SharedPreferences sharedPreferences;
    private AuthViewModel authViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.activityLoginBinding = DataBindingUtil.setContentView(this , R.layout.activity_login );
        this.sharedPreferences = getApplicationContext().getSharedPreferences(String.valueOf(R.string.sharedP_file_key), MODE_PRIVATE);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        sharedPreferences = getApplicationContext().getSharedPreferences(String.valueOf(R.string.sharedP_file_key), MODE_PRIVATE);
        if(sharedPreferences.getBoolean("isLogin" , false)) {
            goToMainScreen();
        }else

        this.activityLoginBinding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = activityLoginBinding.loginEmail.getText().toString();
                String password = activityLoginBinding.loginPassword.getText().toString();

               authViewModel.login(getApplicationContext(), email , password ).observe(LoginActivity.this , new Observer<Boolean>() {
                   @Override
                   public void onChanged(Boolean aBoolean) {
                       if ( aBoolean ) {

                        goToMainScreen();
                        finish();
                       }
                   }
               });
            }
        });

    }

    public void goToMainScreen( ) {
        Intent toMainScreen = new Intent(this , MainActivity.class);
        startActivity(toMainScreen);
    }
}