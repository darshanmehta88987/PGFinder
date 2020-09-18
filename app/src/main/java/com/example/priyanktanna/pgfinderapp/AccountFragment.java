package com.example.priyanktanna.pgfinderapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {


    EditText edtFirstName, edtLastName, edtEmail, edtPhone, edtAddress;
    RadioGroup rgGender;
    RadioButton male, female;
    Button btnUpdate;
    SharedPreferences sharedPreferences;
    DatabaseReference myref;
    RegistrationModel registrationModel;
    FirebaseDatabase database = FirebaseDatabase.getInstance();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.fragment_account, container, false);

        male = (RadioButton) rootview.findViewById(R.id.upradiomale);
        female = (RadioButton) rootview.findViewById(R.id.upradiofemale);
        edtFirstName = (EditText) rootview.findViewById(R.id.upedt_first);

        edtLastName = (EditText) rootview.findViewById(R.id.upedt_last);

        edtEmail = (EditText) rootview.findViewById(R.id.upreg_email);

        edtPhone = (EditText) rootview.findViewById(R.id.upedt_phone);

        edtAddress = (EditText) rootview.findViewById(R.id.upedt_address);

        rgGender = (RadioGroup) rootview.findViewById(R.id.uprg_gender);

        btnUpdate = (Button) rootview.findViewById(R.id.upbtn_register);

        sharedPreferences = this.getContext().getSharedPreferences("PGFINDER", Context.MODE_PRIVATE);

        final String strKey = sharedPreferences.getString("USER_KEY", "");
        Log.e("AccountIs", "Strkey " + strKey);

        myref = database.getReference("Register");

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("AccountIs", "Account is " + dataSnapshot.getChildrenCount());
                //registrationModel = dataSnapshot.getValue(RegistrationModel.class);
                Log.e("AccountIs", "Inside datasnapshot");

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    registrationModel = snapshot.getValue(RegistrationModel.class);

                    String k = registrationModel.getKey();
                    if (strKey.equals(k)) {
                        edtFirstName.setText(registrationModel.getFirstName());
                        edtLastName.setText(registrationModel.getLastName());
                        edtEmail.setText(registrationModel.getEmail());
                        edtEmail.setEnabled(false);
                        edtPhone.setText(registrationModel.getPhoneNo());
                        edtAddress.setText(registrationModel.getAddress());
                        if (registrationModel.getGender().equals("Male")) {
                            male.setChecked(true);
                        }
                        if (registrationModel.getGender().equals("Female")) {
                            female.setChecked(true);
                        }

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.e("AccountIs", "Outside datasnapshot");

    /*    if ik(registrationModel.getGender().equals("Male")) {
            male.setSelected(true);
        }
        if (registrationModel.getGender().equals("Female")) {
            female.setSelected(true);
        }
    */

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strFirst, strLast, strPhone, strAddress;

                strFirst = edtFirstName.getText().toString();
                strLast = edtLastName.getText().toString();
                strPhone = edtPhone.getText().toString();
                strAddress = edtAddress.getText().toString();

                final String finalStrFirst = strFirst;
                final String finalStrLast = strLast;
                final String finalStrPhone = strPhone;
                final String finalStrAddress = strAddress;
                final int gender = rgGender.getCheckedRadioButtonId();

                if (finalStrFirst.equals("") && finalStrFirst.isEmpty()) {
                    edtFirstName.setError("Enter the first name");
                } else if (finalStrFirst.length() < 2) {
                    edtFirstName.setError("valid name");
                } else if (finalStrLast.length() == 0) {
                    edtLastName.setError("Enter Last Name");
                } else if (gender == -1) {
                    Toast.makeText(getContext(), "Select Gender", Toast.LENGTH_SHORT).show();
                } else if (finalStrPhone.length() != 10) {
                    edtPhone.setError("Enter 10 digit phone number");
                } else if (finalStrAddress.length() == 0) {
                    edtAddress.setError("Enter Address");
                } else {
                    RadioButton radioButton;
                    radioButton = (RadioButton) rootview.findViewById(gender);
                    String strGender = radioButton.getText().toString();
                    myref.child(strKey).child("firstName").setValue(finalStrFirst);
                    myref.child(strKey).child("lastName").setValue(finalStrLast);
                    myref.child(strKey).child("gender").setValue(strGender);
                    myref.child(strKey).child("phoneNo").setValue(finalStrPhone);
                    myref.child(strKey).child("address").setValue(finalStrAddress);
                    Intent intent = new Intent(getActivity(),MainActivity.class);
                    startActivity(intent);

                }
            }
        });


        return rootview;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("ACCOUNT");

    }
}
