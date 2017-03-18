package com.sajalnarang.loginregisterdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sajalnarang.loginregisterdemo.R.id.register;

public class RegisterActivity extends AppCompatActivity {

    ProgressDialog registerProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText nameEditText = (EditText) findViewById(R.id.name);
        final EditText phoneEditText = (EditText) findViewById(R.id.phone);
        final EditText emailEditText = (EditText) findViewById(R.id.email);
        final EditText passwordEditText = (EditText) findViewById(R.id.password);
        final EditText passwordEditText2 = (EditText) findViewById(R.id.password2);
        final EditText ageEditText = (EditText) findViewById(R.id.age);
        final Spinner genderSpinner = (Spinner) findViewById(R.id.gender);
        String[] genders = new String[]{"Male", "Female"};
        final ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genders);
        genderSpinner.setAdapter(genderAdapter);

        final Button registerButton = (Button) findViewById(register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEditText.getText().toString().trim();
                if (name.equals("")) {
                    displayNullFieldError(nameEditText);
                    return;
                }

                String phone = phoneEditText.getText().toString().trim();
                if (phone.equals("")) {
                    displayNullFieldError(phoneEditText);
                    return;
                }
                if (!Pattern.matches("\\d{10}", phone)) {
                    phoneEditText.setError("Phone number must be 10 digits");
                    phoneEditText.requestFocus();
                    return;
                }

                String email = emailEditText.getText().toString().trim();
                if (email.equals("")) {
                    displayNullFieldError(emailEditText);
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailEditText.setError("Invalid email address");
                    emailEditText.requestFocus();
                    return;
                }

                String password = passwordEditText.getText().toString().trim();
                if (password.equals("")) {
                    displayNullFieldError(passwordEditText);
                    return;
                }

                String password2 = passwordEditText2.getText().toString().trim();
                if (password2.equals("")) {
                    displayNullFieldError(passwordEditText2);
                    return;
                }
                if (!password.equals(password2)) {
                    passwordEditText2.setError("Passwords do not match");
                    passwordEditText2.requestFocus();
                    return;
                }

                String ageStr = ageEditText.getText().toString().trim();
                if (ageStr.equals("")) {
                    displayNullFieldError(ageEditText);
                    return;
                }
                int age = Integer.parseInt(ageStr);
                int gender = genderSpinner.getSelectedItem().equals("Male") ? 1 : 2;

                registerProgressDialog = new ProgressDialog(RegisterActivity.this);
                registerProgressDialog.setIndeterminate(true);
                registerProgressDialog.setCancelable(false);
                registerProgressDialog.setMessage("Registering User");
                registerProgressDialog.show();

                RetrofitInterface retrofitInterface = ServiceGenerator.createService(RetrofitInterface.class);
                retrofitInterface.registerUser(name, phone, email, password, age, gender).enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        registerProgressDialog.dismiss();
                        if (response.isSuccessful()) {
                            RegisterResponse registerResponse = response.body();
                            String status = registerResponse.getStatus();
                            String data = registerResponse.getData();
                            String message = registerResponse.getMessage();
                            if (status.equals("success")) {
                                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.activity_register);
                                Snackbar.make(linearLayout, message, Snackbar.LENGTH_LONG)
                                        .setAction("OPEN", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent emailLauncherIntent = new Intent(Intent.ACTION_VIEW);
                                                emailLauncherIntent.setType("message/rfc822");
                                                startActivity(emailLauncherIntent);
                                            }
                                        }).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        registerProgressDialog.dismiss();
                    }
                });

            }
        });
    }

    private void displayNullFieldError(EditText editText) {
        editText.setError("This is a required field");
        editText.requestFocus();
    }
}
