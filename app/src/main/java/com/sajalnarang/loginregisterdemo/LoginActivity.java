package com.sajalnarang.loginregisterdemo;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button registerButton;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerButton = (Button) findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginButton = (Button) findViewById(R.id.login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEditText = (EditText) findViewById(R.id.email);
                EditText passwordEditText = (EditText) findViewById(R.id.password);

                final String email = emailEditText.getText().toString();
                final String password = passwordEditText.getText().toString();

                RetrofitInterface retrofitInterface = ServiceGenerator.createService(RetrofitInterface.class);
                retrofitInterface.login(email, password).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            String authToken = response.body().getToken();
                            createAccount(email, password, authToken);
                            Bundle bundle = new Bundle();
                            bundle.putString(AccountManager.KEY_ACCOUNT_NAME, email);
                            bundle.putString(AccountManager.KEY_ACCOUNT_TYPE, getResources().getString(R.string.account_type));
                            bundle.putString(AccountManager.KEY_AUTHTOKEN, authToken);
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("User Credentials", bundle);
                            startActivity(intent);
                        }
                        else {
                            Log.d("TAG", "onResponse: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("TAG", "onFailure: " + t.toString());
                    }
                });
            }
        });
    }

    public void createAccount(String email, String password, String authToken) {
        Account account = new Account(email, getResources().getString(R.string.account_type));
        AccountManager am = AccountManager.get(this);
        am.addAccountExplicitly(account, password, null);
        am.setAuthToken(account, getResources().getString(R.string.authTokenType), authToken);
    }
}
