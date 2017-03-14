package com.sajalnarang.loginregisterdemo;

import android.accounts.AccountManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent() != null) {
            Intent intent = getIntent();
            Bundle bundle = intent.getBundleExtra("User Credentials");
            String accountName = bundle.getString(AccountManager.KEY_ACCOUNT_NAME);
            String accountType = bundle.getString(AccountManager.KEY_ACCOUNT_TYPE);
            String authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);

            TextView accountNameTextView = (TextView) findViewById(R.id.account_name_main);
            TextView accountTypeTextView = (TextView) findViewById(R.id.account_type_main);
            TextView authTokenTextView = (TextView) findViewById(R.id.auth_token_main);

            accountNameTextView.setText(accountName);
            accountTypeTextView.setText(accountType);
            authTokenTextView.setText(authToken);
        }
    }
}
