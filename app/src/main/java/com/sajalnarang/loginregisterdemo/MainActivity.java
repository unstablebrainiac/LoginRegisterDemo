package com.sajalnarang.loginregisterdemo;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent() != null) {
            final Intent intent = getIntent();
            Bundle bundle = intent.getBundleExtra("User Credentials");
            String accountName = bundle.getString(AccountManager.KEY_ACCOUNT_NAME);
            final String accountType = bundle.getString(AccountManager.KEY_ACCOUNT_TYPE);
            final String authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);

            TextView accountNameTextView = (TextView) findViewById(R.id.account_name_main);
            TextView accountTypeTextView = (TextView) findViewById(R.id.account_type_main);
            TextView authTokenTextView = (TextView) findViewById(R.id.auth_token_main);

            accountNameTextView.setText(accountName);
            accountTypeTextView.setText(accountType);
            authTokenTextView.setText(authToken);

            Button signOutButton = (Button) findViewById(R.id.sign_out_button);
            signOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AccountManager accountManager = AccountManager.get(MainActivity.this);
                    accountManager.invalidateAuthToken(accountType, authToken);
                    Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent1);
                }
            });
        }
    }
}
