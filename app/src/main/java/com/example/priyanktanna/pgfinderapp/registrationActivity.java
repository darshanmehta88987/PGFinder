package com.example.priyanktanna.pgfinderapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registrationActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtFirstName, edtLastName, edtEmail, edtPhone, edtPass, edtConfPass, edtAddress;
    RadioGroup rgGender, rgUserType;
    Button btnRegistration;
    private DatabaseReference myRef;
    private RadioButton radioButton, radioRollType;
    private FirebaseAuth mAuth;
    public String strGender;
    public String strRollType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        edtFirstName = (EditText) findViewById(R.id.edt_first);

        edtLastName = (EditText) findViewById(R.id.edt_last);

        edtEmail = (EditText) findViewById(R.id.reg_email);

        edtPass = (EditText) findViewById(R.id.reg_pass);

        edtConfPass = (EditText) findViewById(R.id.edt_cnf_pass);

        edtPhone = (EditText) findViewById(R.id.edt_phone);

        edtAddress = (EditText) findViewById(R.id.edt_address);

        rgGender = (RadioGroup) findViewById(R.id.rg_gender);

        rgUserType = (RadioGroup) findViewById(R.id.rg_usertype);

        btnRegistration = (Button) findViewById(R.id.btn_register);
        btnRegistration.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Register");


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_register:

                int gender = rgGender.getCheckedRadioButtonId();
                int rollType = rgUserType.getCheckedRadioButtonId();

                final String password = edtPass.getText().toString();
                final String confPassword = edtConfPass.getText().toString();

                final String email = edtEmail.getText().toString().trim();
                final String strFirst = edtFirstName.getText().toString().trim();
                final String strLast = edtLastName.getText().toString().trim();
                final String strPhone = edtPhone.getText().toString().trim();
                final String strAddress = edtAddress.getText().toString().trim();

                if (strFirst.equals("") && strFirst.isEmpty()) {
                    edtFirstName.setError("Enter the first name");
                } else if (strFirst.length() < 2) {
                    edtFirstName.setError("valid name");
                } else if (strLast.length() == 0) {
                    edtLastName.setError("Enter Last Name");
                } else if (gender == -1) {
                    Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show();
                } else if (!Validation.isValidEmail(email)) {
                    edtEmail.setError("Enter valid Email Address");
                } else if (password.length() < 6) {
                    edtPass.setError("Enter password of atleast 6 characters");
                    /* !*/
                } else if (!password.equals(confPassword)) {
                    edtConfPass.setError("Password does not match");
                } else if (strPhone.length() != 10) {
                    edtPhone.setError("Enter 10 digit phone number");
                } else if (strAddress.length() == 0) {
                    edtAddress.setError("Enter Address");
                } else if (rollType == -1) {
                    Toast.makeText(this, "Select Roll type", Toast.LENGTH_SHORT).show();
                } else {
                    radioButton = (RadioButton) findViewById(gender);
                    strGender = radioButton.getText().toString();
                    radioRollType = (RadioButton) findViewById(rollType);
                    strRollType = radioRollType.getText().toString();

                    if (rollType == R.id.radiotenet) {

                        SharedPreferences sharedPreferences = getSharedPreferences("TENENT_REGISTER", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("T_FN", strFirst);
                        editor.putString("T_LN", strLast);
                        editor.putString("T_EMAIL", email);
                        editor.putString("T_PASSWORD", password);
                        editor.putString("T_ADDRES", strAddress);
                        editor.putString("T_GENDER", strGender);
                        editor.putString("T_ROLLETYPE", strRollType);
                        editor.putString("T_PH_NUMBER", strPhone);
                        editor.commit();

                        Intent i = new Intent(registrationActivity.this, TenentAddPgLayOut.class);
                        startActivity(i);
                        finish();

                    } else if (rollType == R.id.radiouser) {

                        mAuth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(registrationActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        Toast.makeText(registrationActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                        if (!task.isSuccessful()) {
                                            Toast.makeText(registrationActivity.this, "Authentication failed." + task.getException(),
                                                    Toast.LENGTH_SHORT).show();
                                        } else {

                                            FirebaseUser user = mAuth.getCurrentUser();
                                            String userkey = user.getUid();
                                            RegistrationModel registrationModel = new RegistrationModel();
                                            registrationModel.setFirstName(strFirst);
                                            registrationModel.setLastName(strLast);
                                            registrationModel.setEmail(email);
                                            registrationModel.setPhoneNo(strPhone);
                                            registrationModel.setPassword(password);
                                            registrationModel.setGender(strGender);
                                            registrationModel.setAddress(strAddress);
                                            registrationModel.setRollType(strRollType);
                                            registrationModel.setKey(userkey);

                                            myRef.child(userkey).setValue(registrationModel);
                                            startActivity(new Intent(registrationActivity.this, login_Activity.class));
                                            finish();
                                            //String key = myRef.push().getKey();
                                        }
                                    }
                                });

                    }

                }

        }

    }

}
