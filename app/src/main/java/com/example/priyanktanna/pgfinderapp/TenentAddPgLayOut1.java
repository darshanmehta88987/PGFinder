package com.example.priyanktanna.pgfinderapp;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class TenentAddPgLayOut1 extends AppCompatActivity implements View.OnClickListener {

    CheckBox chTv, chAc, chWifi, chClean, chFood, chLaundry;
    Button btnDone;

    RadioButton r5min,r7min,r10min;
    RadioGroup rgpackage;

    private String strCtv;
    private String strCAC;
    private String strCWifi;
    private String strCClean;
    private String strCfood;
    private String strClaundry;

    ArrayList<String> tenent1List;
    private String tFName;
    private String tLanme;
    private String tEmail;
    private String tPass;
    private String tAdd;
    private String tGender;
    private String tRolle;
    private String tPhNu;
    private String strGender;
    private String strPGname;
    private String strPGAddr;
    private String strSingRent;
    private String strMultRent;
    private String strSpinSingle;
    private String strMultiSpinner;
    private String strAvgSpinner;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private DatabaseReference myRef1;


    private final int PICK_IMAGE_REQUEST = 71;
    private final int PICK_IMAGE_REQUEST2 = 72;
    private final int PICK_IMAGE_REQUEST3 = 73;

    private Uri fileUri;

    private ProgressDialog progressDialog;
    private StorageReference fileRef;
    private StorageReference imageReference;
    private DatabaseReference mDataReference;
    //private String urls;
    ArrayList<String> URL;
    ImageView img1,img2,img3;
    int flag=1;
    private DatabaseReference myRef3;
    private DateTime dateTime;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                UploadImageFileToFirebaseStorage(fileUri);
                img1.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
         if (requestCode == PICK_IMAGE_REQUEST2 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                UploadImageFileToFirebaseStorage(fileUri);
                img2.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        if (requestCode == PICK_IMAGE_REQUEST3 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                UploadImageFileToFirebaseStorage(fileUri);
                img3.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tenent_addpglayout1);

       // r5min=(RadioButton)findViewById(R.id.rg_5min);


        URL = new ArrayList<>();
        img1 = (ImageView) findViewById(R.id.img1);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        img2 = (ImageView)findViewById(R.id.img2);
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST2);
            }
        });

        img3 = (ImageView)findViewById(R.id.img3);
        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST3);
            }
        });

        chTv = (CheckBox) findViewById(R.id.ch_tv);

        chAc = (CheckBox) findViewById(R.id.ch_ac);

        chWifi = (CheckBox) findViewById(R.id.ch_wifi);

        chClean = (CheckBox) findViewById(R.id.ch_clean);

        chFood = (CheckBox) findViewById(R.id.ch_food);

        chLaundry = (CheckBox) findViewById(R.id.ch_laundry);

        btnDone = (Button) findViewById(R.id.btn_done);
        btnDone.setOnClickListener(this);
        tenent1List = new ArrayList<String>();

        r7min=(RadioButton)findViewById(R.id.rg_7min);
        r10min=(RadioButton)findViewById(R.id.rg_10min);

        rgpackage=(RadioGroup)findViewById(R.id.rg_package);

        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Register");
        myRef1 = database.getReference("Tenent_Register");
        myRef3 = database.getReference("Tenent_Timer");

        imageReference = FirebaseStorage.getInstance().getReference().child("images");
        fileRef = null;
        progressDialog = new ProgressDialog(this);


        Intent i = getIntent();

        strGender = i.getStringExtra("GENDER");
        strPGname = i.getStringExtra("PGNAME");
        strPGAddr = i.getStringExtra("PGADDRESS");
        strSingRent = i.getStringExtra("SINGLE_RENT");
        strMultRent = i.getStringExtra("MULTI_RENT");
        strSpinSingle = i.getStringExtra("SINGLE_SPI");
        strMultiSpinner = i.getStringExtra("SINGLE_Mlti");
        strAvgSpinner = i.getStringExtra("SINGLE_Avg");

        SharedPreferences sharedPreferences = getSharedPreferences("TENENT_REGISTER", Context.MODE_PRIVATE);

        tFName = sharedPreferences.getString("T_FN", "");
        tLanme = sharedPreferences.getString("T_LN", "");
        tEmail = sharedPreferences.getString("T_EMAIL", "");
        tPass = sharedPreferences.getString("T_PASSWORD", "");
        tAdd = sharedPreferences.getString("T_ADDRES", "");
        tGender = sharedPreferences.getString("T_GENDER", "");
        tRolle = sharedPreferences.getString("T_ROLLETYPE", "");
        tPhNu = sharedPreferences.getString("T_PH_NUMBER", "");

         dateTime = new DateTime();


    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.btn_done:
                String result = "";

                if (chTv.isChecked()) {

                    strCtv = chTv.getText().toString();
                    tenent1List.add(strCAC);

                }
                if (chAc.isChecked()) {
                    strCAC = chAc.getText().toString();
                    tenent1List.add(strCAC);
                }
                if (chWifi.isChecked()) {
                    strCWifi = chWifi.getText().toString();
                    tenent1List.add(strCWifi);

                }
                if (chClean.isChecked()) {
                    strCClean = chClean.getText().toString();
                    tenent1List.add(strCClean);


                }
                if (chFood.isChecked()) {
                    strCfood = chFood.getText().toString();
                    tenent1List.add(strCfood);


                }
                if (chLaundry.isChecked()) {
                    strClaundry = chLaundry.getText().toString();
                    tenent1List.add(strClaundry);


                }
                int min;
                min = rgpackage.getCheckedRadioButtonId();
                r5min = (RadioButton)findViewById(min);


               /* for (int i = 0; i < tenent1List.size(); i++) {

                    Log.e("Tenent", "tenent" + tenent1List.get(i).toString());
                    Toast.makeText(getApplicationContext(), "" + tenent1List.get(i).toString(), Toast.LENGTH_SHORT).show();

                }
*/
                mAuth.createUserWithEmailAndPassword(tEmail, tPass)
                        .addOnCompleteListener(TenentAddPgLayOut1.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(TenentAddPgLayOut1.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();


                                if (!task.isSuccessful()) {
                                    Toast.makeText(TenentAddPgLayOut1.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String userkey = user.getUid();
                                    RegistrationModel registrationModel = new RegistrationModel();
                                    registrationModel.setFirstName(tFName);
                                    registrationModel.setLastName(tLanme);
                                    registrationModel.setEmail(tEmail);
                                    registrationModel.setPhoneNo(tPhNu);
                                    registrationModel.setPassword(tPass);
                                    registrationModel.setGender(tGender);
                                    registrationModel.setAddress(tAdd);
                                    registrationModel.setRollType(tRolle);
                                    registrationModel.setKey(userkey);

                                    myRef.child(userkey).setValue(registrationModel);

                                    Tenent1Model tenent1Model = new Tenent1Model();
                                    String strTenentId = myRef1.push().getKey();
                                    tenent1Model.setPgName(strPGname);
                                    tenent1Model.setPgAdd(strPGAddr);
                                    tenent1Model.setPgFor(strGender);
                                    tenent1Model.setSingleSpin(strSpinSingle);
                                    tenent1Model.setMultiSpin(strMultiSpinner);
                                    tenent1Model.setAvgPerSpi(strAvgSpinner);
                                    tenent1Model.setSingleRent(strSingRent);
                                    tenent1Model.setMultiRent(strMultRent);
                                    tenent1Model.setFacilityList(tenent1List);
                                    tenent1Model.setUid(userkey);
                                    tenent1Model.settId(strTenentId);
                                    // UploadImageFileToFirebaseStorage();
                                    tenent1Model.setUrl(URL);
                                    // Toast.makeText(TenentAddPgLayOut1.this, "Hello "+urls+" hello" , Toast.LENGTH_SHORT).show();
                                    myRef1.child(userkey).child(strTenentId).setValue(tenent1Model);
                                    String strdate = dateTime.Time();
                                    String strDateTime = String.valueOf(dateTime.dateTimeYearinDateFormat());

                                    String strTimerId = myRef3.push().getKey();
                                    TimerModel timerModel = new TimerModel();
                                    timerModel.setTenentId(userkey);
                                   // timerModel.setTimeSet(strdate);
                                    timerModel.setTimeSet("7");
                                    timerModel.setStrdatetime(strDateTime);
                                    timerModel.setTimerId(strTimerId);
                                    timerModel.setTenentId(strTenentId);
                                    myRef3.child(strTimerId).setValue(timerModel);



                                  //  timerModel.setTimeSet();



                                    startActivity(new Intent(TenentAddPgLayOut1.this, login_Activity.class));
                                    finish();


                                    //String key = myRef.push().getKey();


                                }
                            }
                        });


              /*  Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(TenentAddPgLayOut1.this, login_Activity.class);
                startActivity(intent);
                finish();*/
                break;
        }

    }

    private void UploadImageFileToFirebaseStorage(Uri fileUri) {
        // store image at "images/filename.extension" on Firebase Cloud Storage

        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        fileRef = imageReference.child(UUID.randomUUID().toString() + "." + getFileExtension(fileUri));

        fileRef.putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        String name = taskSnapshot.getMetadata().getName();
                        final String url = taskSnapshot.getDownloadUrl().toString();
                        Log.e("image", "image  " + url);
                        //urls = url;
                        URL.add(url);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // ...
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        // progress percentage
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        // percentage in progress dialog
                        progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                    }
                })
                .addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                        // ...
                    }
                });
    }

    /*private void ImageUploadInfo1(String name, String url) {
        ImageUploadInfo info = new ImageUploadInfo(url);

        String key = mDataReference.push().getKey();
        mDataReference.child(key).setValue(info);
    }*/
    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();

        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

}
