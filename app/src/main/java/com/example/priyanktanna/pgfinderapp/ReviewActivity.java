package com.example.priyanktanna.pgfinderapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {


    private DatabaseReference myRef1;
    private DatabaseReference myref2;
    ListView list_Review;
    ArrayList<ReviewModel> reviewModelArrayList;
    private RegistrationModel registrationModel;
    private String re;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);


        list_Review = (ListView) findViewById(R.id.list_review);
        myRef1 = FirebaseDatabase.getInstance().getReference("Feedback");
        myref2 = FirebaseDatabase.getInstance().getReference("Register");
        reviewModelArrayList = new ArrayList<>();
        Intent intent = getIntent();
        final String tid = intent.getStringExtra("tid");


        //review
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    reviewModelArrayList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        final Feedback feedback = snapshot.getValue(Feedback.class);
                        if (tid.equals(feedback.getPg_Id())) {
                            /*final ReviewModel reviewModel = new ReviewModel();
                            reviewModel.setRating(feedback.getFeed_rating());
                            reviewModel.setReview(feedback.getFeed_des());*/
                            myref2.child(feedback.getUserid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    Log.e("Review", "datasnapshot " + dataSnapshot.getKey());

                                   RegistrationModel registrationModel = dataSnapshot.getValue(RegistrationModel.class);

                                    String regi = registrationModel.getFirstName();
                                    String review = feedback.getFeed_des();
                                    String reviewRating = feedback.getFeed_rating();

                                    nameset(regi, review, reviewRating);

                                    /*//reviewModel.setName(registrationModel.getFirstName());
                                    Log.e("Review", "Name " + reviewModel.getName());
                                    Log.e("Review", "Desc " + reviewModel.getReview());
                                    Log.e("Review", "Rating is " + reviewModel.getRating());
*/

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }

                            });
                            Log.e("++++", "NAME IS " + re);

  /*                          reviewModelArrayList.add(reviewModel);
                            Log.e("ReviewName", "Name " + reviewModel.getName());
*/
                        }

                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void nameset(String s, String review, String regi) {

        final ReviewModel reviewModel = new ReviewModel();
        reviewModel.setName(s);
        reviewModel.setRating(regi);
        reviewModel.setReview(review);
        reviewModelArrayList.add(reviewModel);
        MyReviewAdapter myReviewAdapter = new MyReviewAdapter(ReviewActivity.this, reviewModelArrayList);
        list_Review.setAdapter(myReviewAdapter);

        Log.e("Review", "Size is " + reviewModelArrayList.size());

    }
}
