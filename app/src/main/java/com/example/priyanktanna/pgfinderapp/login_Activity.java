package com.example.priyanktanna.pgfinderapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login_Activity extends AppCompatActivity implements View.OnClickListener {


    Button btnLogin;
    Button btnSignup;
    EditText edtUserName, edtPassword;

    TextView tvVisitor, tvForgotPassword;
    private FirebaseAuth auth;
    private DatabaseReference myRef;
    private String stringkey;
    private String strRoletype;
    private String strfn;
    private String strln;
    private String gender;
    SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        auth = FirebaseAuth.getInstance();
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);

        btnSignup = (Button) findViewById(R.id.btn_registration);
        btnSignup.setOnClickListener(this);

        tvVisitor = (TextView) findViewById(R.id.edt_visitor);
        tvVisitor.setOnClickListener(this);

        edtUserName = (EditText) findViewById(R.id.edt_email);
        edtUserName.setOnClickListener(this);

        edtPassword = (EditText) findViewById(R.id.edt_password);
        edtUserName.setOnClickListener(this);

        tvForgotPassword = (TextView) findViewById(R.id.tv_forgotpass);
        tvForgotPassword.setOnClickListener(this);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Register");

        sharedPreferences = getSharedPreferences("PGFINDER", Context.MODE_PRIVATE);



    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_login:

                //String email = edtUserName.getText().toString();
                final String password = edtPassword.getText().toString();


                final String strEmail = edtUserName.getText().toString();
                if (strEmail.isEmpty() && strEmail.equals("")) {
                    edtUserName.setError("Enter Your Email");
                } else if (!Validation.isValidEmail(strEmail)) {
                    edtUserName.setError("Enter valid Email Address");
                } else if (password.isEmpty() && password.equals("")) {
                    edtPassword.setError("Enter password");
                    //Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                }else
                {
                    //authenticate user
                    auth.signInWithEmailAndPassword(strEmail, password)
                            .addOnCompleteListener(login_Activity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.


                                    if(strEmail.equals("admin@gmail.com") && password.equals("iamadmin"))
                                    {
                                        Intent intent = new Intent(login_Activity.this,AdminActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    if (!task.isSuccessful()) {
                                        // there was an error
                                        if (password.length() < 6) {
                                            edtPassword.setError("minimum_password");
                                        } else {
                                            Toast.makeText(login_Activity.this, "auth_failed", Toast.LENGTH_LONG).show();
                                        }
                                    } else {



                                        FirebaseUser user = auth.getCurrentUser();

                                        final String userkey = user.getUid();

                                        Log.e("login_Activity", "keyyyyy" + userkey);
                                        myRef.child(userkey).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                RegistrationModel registrationModel = dataSnapshot.getValue(RegistrationModel.class);
                                                stringkey = registrationModel.getKey();
                                                strRoletype = registrationModel.getRollType();
                                                strfn = registrationModel.getFirstName();
                                                strln = registrationModel.getLastName();
                                                gender = registrationModel.getGender();
                                                Log.e("login_Activity", stringkey);
                                                Log.e("login_Activity", strRoletype);
                                                if (userkey.equals(stringkey)) {


                                                    if (strRoletype.equals("User")) {

                                                        Log.e("login_Activity", "k===" + userkey);
                                                        editor = sharedPreferences.edit();
                                                        editor.putString("USER_KEY", stringkey);
                                                        editor.putString("USER_ROLE", strRoletype);
                                                        editor.putString("USER_FN", strfn);
                                                        editor.putString("USER_LN", strln);
                                                        editor.putString("Gender",gender);
                                                        Log.e("Genderis","Gender is "+gender);
                                                        editor.commit();

                                                        Intent intent = new Intent(login_Activity.this, MainActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                    if (strRoletype.equals("Tenent")) {

                                                        editor = sharedPreferences.edit();
                                                        editor.putString("USER_KEY", stringkey);
                                                        editor.putString("USER_ROLE", strRoletype);
                                                        editor.putString("USER_FN", strfn);
                                                        editor.putString("USER_LN", strln);
                                                        editor.commit();
                                                        Intent intent = new Intent(login_Activity.this, TabActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                        finish();
                                                    }


                                                } else {

                                                    //  Toast.makeText(login_Activity.this, "Login failed", Toast.LENGTH_SHORT).show();
                                                }


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });


                                    }
                                }
                            });
                }



                break;

            case R.id.btn_registration:
                Intent i1 = new Intent(this, registrationActivity.class);
                startActivity(i1);
                finish();

                break;
            case R.id.edt_visitor:
                Intent i3 = new Intent(this, Visitor2Activity.class);
                startActivity(i3);
                finish();


                break;

            case R.id.tv_forgotpass:

                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view1 = layoutInflater.inflate(R.layout.raw_custom_forgotpassword, null);
                builder1.setView(view1);
                final AlertDialog dialog = builder1.create();
                final EditText edtEmail = (EditText) view1.findViewById(R.id.edt_email);
                Button btnsend = (Button) view1.findViewById(R.id.btn_send);

                btnsend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // dialog.dismiss();
                        String strEmail = edtEmail.getText().toString();
                        if (strEmail.isEmpty() && strEmail.equals("")) {
                            edtEmail.setError("Enter Your Email");
                        } else if (!Validation.isValidEmail(strEmail)) {
                            edtEmail.setError("Enter valid Email Address");
                        } else {
                            forgotPassword(strEmail);
                            dialog.cancel();
                        }

                    }
                });
                dialog.show();
                break;

        }

    }

    private void forgotPassword(String strEmail) {
        auth.sendPasswordResetEmail(strEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(login_Activity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(login_Activity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    // @Override
   /* public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (Integer.parseInt(Build.VERSION.SDK)< 5 && keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){

            onBackPressed();
        }


        return super.onKeyDown(keyCode, event);
    }*/


    @Override
    public void onBackPressed() {

        Intent setIntent = new Intent(Intent.ACTION_MAIN);
        setIntent.addCategory(Intent.CATEGORY_HOME);
        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(setIntent);
        return;
    }
}
