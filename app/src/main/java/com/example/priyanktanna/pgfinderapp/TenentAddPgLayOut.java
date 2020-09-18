package com.example.priyanktanna.pgfinderapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.io.IOException;

public class TenentAddPgLayOut extends AppCompatActivity implements View.OnClickListener {

    EditText edtPgName, edtPgAddress, edtSingleRent, edtMultiRent;
    RadioButton rdMale, rdFemale;
    Spinner spSingle, spMulti, spAvgPerson;
    Button btnNext;
    RadioGroup radioGroup;
    private String strMulti;
    private String strSingle;
    private String strAvgPersion;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenent_addpglayout);


        edtPgName = (EditText) findViewById(R.id.edt_pgName);

        edtPgAddress = (EditText) findViewById(R.id.edt_pgAddress);

        rdMale = (RadioButton) findViewById(R.id.rg_male);

        rdFemale = (RadioButton) findViewById(R.id.rg_female);

        spSingle = (Spinner) findViewById(R.id.spinnner_single);

        spMulti = (Spinner) findViewById(R.id.spinnner_multi);

        spAvgPerson = (Spinner) findViewById(R.id.spinnner_avgsharing);
        radioGroup = (RadioGroup) findViewById(R.id.rg_gender);
        String[] avgpersion = getResources().getStringArray(R.array.avg_persone);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, avgpersion);
        spAvgPerson.setAdapter(adapter1);
        spAvgPerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {
                } else {
                    strAvgPersion = adapterView.getItemAtPosition(i).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        String[] countries = getResources().getStringArray(R.array.array_room);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        spMulti.setAdapter(adapter);
        spSingle.setAdapter(adapter);

        spSingle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {

                } else {
                    strSingle = adapterView.getItemAtPosition(i).toString();

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spMulti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0) {

                    spAvgPerson.setVisibility(View.GONE);
                    edtMultiRent.setVisibility(View.GONE);
                } else {
                    strMulti = adapterView.getItemAtPosition(i).toString();

                    spAvgPerson.setVisibility(View.VISIBLE);
                    edtMultiRent.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        edtSingleRent = (EditText) findViewById(R.id.edt_rnt_single);

        edtMultiRent = (EditText) findViewById(R.id.edt_rnt_multi);


        btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_next:

                flag = 0;
                int radio = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) findViewById(radio);


                String strgender = radioButton.getText().toString();
                String pgname = edtPgName.getText().toString();
                String address = edtPgAddress.getText().toString();
                String singleRENT = edtSingleRent.getText().toString();
                String MULTIRENT = edtMultiRent.getText().toString();


                if(pgname.isEmpty() || pgname.equals(""))
                {
                    edtPgName.setError("Enter PG Name");
                }
                else if(address.isEmpty() || address.equals(""))
                {
                    edtPgAddress.setError("Enter PG Address");
                }

                /*if (pgname.trim().length()==0) {
                    flag = 1;
                    edtPgName.setError("Enter PG Name");
                }
                if (address.trim().length()==0) {
                    flag = 1;
                    edtPgAddress.setError("Enter PG Address");
                }*/
                /*if(singleRENT.trim().length()==0)
                {
                    flag=1;
                    edtSingleRent.setError("enter single sharing rent");
                }
                if(MULTIRENT.trim().length()==0)
                {
                    flag=1;
                    edtMultiRent.setError("enter multi sharing rent");
                }
*/

                Intent intent = new Intent(TenentAddPgLayOut.this, TenentAddPgLayOut1.class);

                if (!(strgender.equals(""))) {
                    intent.putExtra("GENDER", strgender);

                }
                if (!pgname.equals("")) {
                    intent.putExtra("PGNAME", pgname);

                }
                if (!address.equals("")) {
                    intent.putExtra("PGADDRESS", address);

                }
                if (!singleRENT.equals("")) {
                    intent.putExtra("SINGLE_RENT", singleRENT);

                }
                if (!MULTIRENT.equals("")) {
                    intent.putExtra("MULTI_RENT", MULTIRENT);

                }

                if (!strSingle.equals("")) {
                    intent.putExtra("SINGLE_SPI", strSingle);

                }
                if (!strMulti.equals("")) {
                    intent.putExtra("SINGLE_Mlti", strMulti);

                }
                if (!strAvgPersion.equals("")) {
                    intent.putExtra("SINGLE_Avg", strAvgPersion);

                }
                if (flag != 1) {
                    startActivity(intent);
                    finish();

                }

        }

    }
}
