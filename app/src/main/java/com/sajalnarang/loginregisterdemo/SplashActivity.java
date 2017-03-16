package com.sajalnarang.loginregisterdemo;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class SplashActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_GET_ACCOUNTS = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AccountManager accountManager = AccountManager.get(this);
        while (ActivityCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.GET_ACCOUNTS}, MY_PERMISSIONS_GET_ACCOUNTS);
            return;
        }
        Account[] accounts = accountManager.getAccountsByType(getResources().getString(R.string.account_type));
        Log.d("TAG", "onCreate: " + accounts.length);

        if (accounts.length == 0) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (accounts.length == 1) {
            accountManager.getAuthToken(accounts[0], getResources().getString(R.string.authTokenType), null, this, new AccountManagerCallback<Bundle>() {
                @Override
                public void run(AccountManagerFuture<Bundle> future) {
                    try {
                        Bundle bundle = future.getResult();
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        intent.putExtra("User Credentials", bundle);
                        startActivity(intent);
                        finish();
                    } catch (OperationCanceledException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (AuthenticatorException e) {
                        e.printStackTrace();
                    }
                }
            }, null);
        } else {
            Toast.makeText(this, "Something really bad just happened.", Toast.LENGTH_SHORT).show();
        }
    }
}
