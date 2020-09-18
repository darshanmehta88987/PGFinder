package com.example.priyanktanna.pgfinderapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Visitor2Activity extends AppCompatActivity {

    Button btnVisit;
    RadioGroup rgGender;
    RadioButton vMale, vFemale;
    EditText edtArea;
    int gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor2);

        rgGender = (RadioGroup) findViewById(R.id.rg_genderV);
        edtArea = (EditText) findViewById(R.id.edt_area);


        btnVisit = (Button) findViewById(R.id.btn_visit);
        btnVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String area = edtArea.getText().toString();
                gender = rgGender.getCheckedRadioButtonId();
                if (area.isEmpty() || area.equals("")) {
                    edtArea.setError("Please enter area");
                } else if (gender == -1) {
                    Toast.makeText(Visitor2Activity.this, "Please select Gender", Toast.LENGTH_SHORT).show();
                } else {

                    vMale = (RadioButton)findViewById(gender);
                    String strGender = vMale.getText().toString();
                    Intent i = new Intent(Visitor2Activity.this, VisitorActivity.class);
                    i.putExtra("GENDER",strGender);
                    startActivity(i);
                    finish();
                }


            }
        });

    }
}
