package com.example.priyanktanna.pgfinderapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;

public class PGActivity extends AppCompatActivity implements View.OnClickListener {


    String feedcome;
    float ratingcome;

    ViewPager viewPager;
    MyCustomPagerAdapter myCustomPagerAdapter;
    TextView tvpgName, tvpgAdd, tvsingleRent, tvmultiRent, tvRating;
    EditText editfeedback;
    Button btnfeedback;
    RatingBar ratingbar;
    private DatabaseReference myRef;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String uid;
    private String tid;
    private SharedPreferences sharedPreferences;
    LikeButton likeButton;

    boolean flag = false;
    private String thisId;
    private String thisTId;
    private String wishlistid;
    private DatabaseReference myRef1;
    private DatabaseReference myref2;

    private String strrating;
    int r;
    private String getUID;
    private String getPgId;
    private String strKey;
    //TextView tvRatings;
    private int e = 0;
    ListView list_Review;
    ArrayList<ReviewModel> reviewModelArrayList;
    Feedback feedback;
    ReviewModel reviewModel;

    Button btnReview;
    private String keyfeedback;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg);
        // myRef = database.getReference("Tenent_Register");

        //tvRatings = (TextView) findViewById(R.id.tv_ratingss);
        myref2 = FirebaseDatabase.getInstance().getReference("Register");
        list_Review = (ListView) findViewById(R.id.list_review);
        reviewModelArrayList = new ArrayList<>();


        btnReview = (Button) findViewById(R.id.btn_review);


        myRef = database.getReference("Wishlist");
        tvpgName = (TextView) findViewById(R.id.tv_pgname);
        tvpgAdd = (TextView) findViewById(R.id.tv_address);
        tvsingleRent = (TextView) findViewById(R.id.single_rent);
        tvmultiRent = (TextView) findViewById(R.id.Multi_rent);

        likeButton = (LikeButton) findViewById(R.id.btn_wishlist);
        myRef1 = database.getReference("Feedback");


        btnfeedback = (Button) findViewById(R.id.btn_feedback);
        btnfeedback.setOnClickListener(this);

        ratingbar = (RatingBar) findViewById(R.id.ratingBar2);
        tvRating = (TextView) findViewById(R.id.tv_rating);

        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean fromUser) {
                //tvRating.setText(String.valueOf(v));
            }
        });

        btnReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PGActivity.this, ReviewActivity.class);
                intent.putExtra("tid", tid);
                startActivity(intent);
            }
        });


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        Intent intent = getIntent();
        ArrayList<String> facilityList;
        ArrayList<String> url;
        String pgName = intent.getStringExtra("pgName");
        String pgAdd = intent.getStringExtra("pgAdd");
        String pgFor = intent.getStringExtra("pgFor");
        String singleSpin = intent.getStringExtra("singleSpin");
        String multiSpin = intent.getStringExtra("multiSpin");
        String avgPerSpi = intent.getStringExtra("avgPerSpi");
        String multiRent = intent.getStringExtra("multiRent");
        String singleRent = intent.getStringExtra("singleRent");
        facilityList = intent.getStringArrayListExtra("facilityList");
        url = intent.getStringArrayListExtra("url");

        uid = intent.getStringExtra("uid");
        tid = intent.getStringExtra("tid");


        sharedPreferences = getSharedPreferences("PGFINDER", Context.MODE_PRIVATE);
        strKey = sharedPreferences.getString("USER_KEY", "");


        for (int i = 0; i < url.size(); i++) {
            Log.e("LIST_URL", url.get(i).toString());
        }
        myCustomPagerAdapter = new MyCustomPagerAdapter(PGActivity.this, url);
        viewPager.setAdapter(myCustomPagerAdapter);

        tvpgName.setText(pgName);
        tvpgAdd.setText(pgAdd);
        tvsingleRent.setText(singleRent);
        tvmultiRent.setText(multiRent);

        ratingbar.setEnabled(false);

        /*myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/
        //CORRECT
     /*   Query query = FirebaseDatabase.getInstance().getReference("Feedback")
                .orderByChild("pg_Id")
                .equalTo(tid);
        query.addListenerForSingleValueEvent(valueEventListener);
*/


        //review
        /*myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    reviewModelArrayList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Feedback feedback = snapshot.getValue(Feedback.class);
                        if (tid.equals(feedback.getPg_Id())) {
                                final ReviewModel reviewModel = new ReviewModel();
                                reviewModel.setRating(feedback.getFeed_rating());
                                reviewModel.setReview(feedback.getFeed_des());
                                myref2.child(feedback.getUserid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                       Log.e("Review","datasnapshot "+dataSnapshot.getKey());
                                        RegistrationModel registrationModel = dataSnapshot.getValue(RegistrationModel.class);
                                       *//* for(DataSnapshot snapshot1 : dataSnapshot.getChildren())
                                        {
                                            String abc = snapshot1.getValue(String.class);
                                            //RegistrationModel registrationModel = snapshot1.getValue(RegistrationModel.class);
                                            //reviewModel.setName(registrationModel.getFirstName());
                                            Log.e("Review","Str "+abc);
                                        }*//*
                                        reviewModel.setName(registrationModel.getFirstName());
                                        Log.e("Review","Name "+reviewModel.getName());
                                        Log.e("Review","Desc "+reviewModel.getReview());

                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            reviewModelArrayList.add(reviewModel);
                        }

                    }
                    MyReviewAdapter myReviewAdapter = new MyReviewAdapter(PGActivity.this,reviewModelArrayList);
                    list_Review.setAdapter(myReviewAdapter);

                    Log.e("Review","Size is "+reviewModelArrayList.size());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/

        //review
        /*myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        feedback = snapshot.getValue(Feedback.class);
                        if (tid.equals(feedback.getPg_Id())) {
                            reviewModel = new ReviewModel();

                            register.child(feedback.getUserid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    RegistrationModel registrationModel = dataSnapshot.getValue(RegistrationModel.class);
                                    reviewModel.setName(registrationModel.getFirstName() + "  " + registrationModel.getLastName());
                                    reviewModel.setRating(feedback.getFeed_rating());
                                    reviewModel.setReview(feedback.getFeed_des());

                                    Log.e("Review","Name is "+reviewModel.getName());
                                    Log.e("Review","Rating "+reviewModel.getRating());
                                    Log.e("Review","Desc is "+reviewModel.getReview());
                                    reviewModelArrayList.add(reviewModel);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });

                        }

                    }
                    Log.e("Review","Size is "+reviewModelArrayList.size());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/

      /*  myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                float pr = 0;
                int n = 0;
                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Feedback feedback = snapshot.getValue(Feedback.class);
                        if (tid.equals(feedback.getPg_Id())) {

                            pr = pr + Float.parseFloat(feedback.getFeed_rating());
                            n++;
                        }

                    }

                }
                pr = pr / n;

                String prating = String.valueOf(pr);
                //tvRatings.setText(prating+"*" + "Number "+n);
                tvRating.setText(n + " Reviews");
                ratingbar.setRating(pr);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                float pr = 0;
                int n = 0;
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Feedback feedback = snapshot.getValue(Feedback.class);
                        getUID = feedback.getUserid();
                        getPgId = feedback.getPg_Id();

                        if (strKey.equals(getUID) && tid.equals(getPgId)) {

                            e = feedback.getR();
                            keyfeedback = feedback.getFeedbackId();
                            //Re
                            feedcome = feedback.getFeed_des();
                            ratingcome = Float.parseFloat(feedback.getFeed_rating());

                        }
                        if (tid.equals(getPgId)) {
                            pr = pr + Float.parseFloat(feedback.getFeed_rating());
                            n++;

                        }


                    }
                    pr = pr / n;

                    String prating = String.valueOf(pr);
                    //tvRatings.setText(prating+"*" + "Number "+n);
                    tvRating.setText(n + " Reviews");
                    ratingbar.setRating(pr);


                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        myRef.child(strKey).child(tid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    WishlistModel wishlistModel = dataSnapshot1.getValue(WishlistModel.class);
                    if (strKey.equals(wishlistModel.getUid()) && tid.equals(wishlistModel.getTid())) {

                        wishlistid = wishlistModel.getId();
                        if (wishlistModel.getFav() == 1) {

                            likeButton.setLiked(true);
                        }

                    }
                }


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        likeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {


                WishlistModel wishlistModel1 = new WishlistModel();
                String id = myRef.push().getKey();
                wishlistModel1.setId(id);
                wishlistModel1.setUid(strKey);
                wishlistModel1.setTid(tid);
                wishlistModel1.setFav(1);
                myRef.child(strKey).child(tid).child(id).setValue(wishlistModel1);
                Toast.makeText(PGActivity.this, "Add to wishlist", Toast.LENGTH_SHORT).show();
                   /*SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("WISHLIST_KEY",id);
                    editor.commit();*/
                // flag = true;


            }

            @Override
            public void unLiked(LikeButton likeButton) {


                //String wishlistkey = sharedPreferences.getString("WISHLIST_KEY", "");
                myRef.child(strKey).child(tid).child(wishlistid).removeValue();

            }
        });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btn_feedback:

                AlertDialog.Builder builder1 = new AlertDialog.Builder(PGActivity.this);
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view1 = layoutInflater.inflate(R.layout.raw_custom_feedback, null);
                builder1.setView(view1);
                final AlertDialog dialog = builder1.create();
                final EditText edtfeedback = (EditText) view1.findViewById(R.id.edt_feedback);
                ratingbar = (RatingBar) view1.findViewById(R.id.ratingBar2);


                ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                        strrating = String.valueOf(rating);

                    }
                });


                if(tid.equals(getPgId))
                {
                    edtfeedback.setText(feedcome);
                    ratingbar.setRating(ratingcome);
                }




                Button btnsend = (Button) view1.findViewById(R.id.btn_submit);
                btnsend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        if (e == 1) {

                            Toast.makeText(PGActivity.this, "Data already set", Toast.LENGTH_SHORT).show();
                            /*update feedback*/

                            String strfeeback = edtfeedback.getText().toString();
                            Feedback feedback = new Feedback();
                            //  String feedbackkey = myRef1.push().getKey();
                            feedback.setFeedbackId(keyfeedback);
                            feedback.setUserid(strKey);
                            feedback.setPg_Id(tid);
                            feedback.setFeed_des(strfeeback);
                            feedback.setFeed_rating(strrating);
                            feedback.setR(1);
                            myRef1.child(keyfeedback).setValue(feedback);
                            /*over feedback*/

                        } else {

                            String strfeeback = edtfeedback.getText().toString();
                            Feedback feedback = new Feedback();
                            String feedbackkey = myRef1.push().getKey();
                            feedback.setFeedbackId(feedbackkey);
                            feedback.setUserid(strKey);
                            feedback.setPg_Id(tid);
                            feedback.setFeed_des(strfeeback);
                            feedback.setFeed_rating(strrating);
                            feedback.setR(1);
                            myRef1.child(feedbackkey).setValue(feedback);

                        }


                    }


                });
                dialog.show();


                break;

        }

    }
}
