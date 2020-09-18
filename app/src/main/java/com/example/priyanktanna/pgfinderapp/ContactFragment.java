package com.example.priyanktanna.pgfinderapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ContactFragment extends Fragment {

    TextView textView,textView1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_contactus,container,false);

        textView=(TextView)rootview.findViewById(R.id.tvContact);
        textView.setText("Conteact us");

        textView1=(TextView)rootview.findViewById(R.id.tvContact);
        textView1.setText("we Love Hearing From You !! if you have any questions,contact Us." );




        return rootview;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("CONTACT US");



    }
}
