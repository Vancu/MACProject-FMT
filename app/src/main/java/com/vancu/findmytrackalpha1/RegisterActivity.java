package com.vancu.findmytrackalpha1;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    Button register, log_in;
    EditText Email, Password, ConfirmEmail, ConfirmPass;
    String EmailHolder, PasswordHolder, ConEmailHolder, ConPassHolder, firstHolder, secondHolder;
    String finalResult;
    String HttpURL = "http://73.220.191.198:13379/UserRegistration.php";
    Boolean CheckEditText;
    ProgressDialog progressDialog;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Assign Id's
        Email = findViewById(R.id.etRegisterEmailAddress);
        Password = findViewById(R.id.etRegisterPassword);
        ConfirmEmail = findViewById(R.id.etConfirmEmail);
        ConfirmPass = findViewById(R.id.etRegisterConfirmPassword);
        firstHolder = "Derp";
        secondHolder = "Darp";

        //Adding Click Listener on button.
        /*register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();

                if (CheckEditText) {

                    // If EditText is not empty and CheckEditText = True then this block will execute.

                    UserRegisterFunction(EmailHolder, PasswordHolder);

                } else {

                    // If EditText is empty then this block will execute .
                    Toast.makeText(RegisterActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }


            }
        });*/
    }

    public void CheckEditTextIsEmptyOrNot(){
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();


        if (!TextUtils.isEmpty(EmailHolder) && !TextUtils.isEmpty(PasswordHolder))
            CheckEditText = true;
        else CheckEditText = false;

    }

    public void UserRegisterFunction(final String firstHolder, final String secondHolder, final String email, final String password){

        @SuppressLint("StaticFieldLeak")
        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(RegisterActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(RegisterActivity.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("firstHolder",params[0]);

                hashMap.put("secondHolder",params[1]);

                hashMap.put("email",params[2]);

                hashMap.put("password",params[3]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(firstHolder, secondHolder, email,password);
    }

    public void bRegisterDummy (View view)
        {
            ConEmailHolder = ConfirmEmail.getText().toString();
            ConPassHolder = ConfirmPass.getText().toString();
            CheckEditTextIsEmptyOrNot();

            if (CheckEditText) {

                // If EditText is not empty and CheckEditText = True then this block will execute.

                if (EmailHolder.equals(ConEmailHolder) && PasswordHolder.equals(ConPassHolder)) {

                    UserRegisterFunction(firstHolder, secondHolder, EmailHolder, PasswordHolder);
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                }

                else {
                    Toast.makeText(RegisterActivity.this, "Email or Password do not match.", Toast.LENGTH_LONG).show();
                }

            } else {

                // If EditText is empty then this block will execute .
                Toast.makeText(RegisterActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

            }
        }
    }
