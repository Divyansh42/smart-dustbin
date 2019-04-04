package com.jss.smartdustbin.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.jss.smartdustbin.Activities.HomeActivity;
import com.jss.smartdustbin.Activities.LoginActivity;
import com.jss.smartdustbin.Models.DustbinRegistrationData;
import com.jss.smartdustbin.R;
import com.jss.smartdustbin.Utils.NetworkReceiver;

import java.io.Serializable;


public class HomeFragment extends Fragment {

    public static final String LOG_TAG = LoginActivity.class.getSimpleName();
    LinearLayout loginLinearLayout;
    LinearLayout registerDustbinLinearLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        ((HomeActivity) getActivity()).setActionBarTitle("Home");

        loginLinearLayout = view.findViewById(R.id.ll_login);
        registerDustbinLinearLayout = view.findViewById(R.id.ll_register_dustbin);

        loginLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivityIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginActivityIntent);

            }
        });

        registerDustbinLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment registerDustbinFragment = new RegisterDustbinFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fl_home_activity, registerDustbinFragment);
                fragmentTransaction.commit();
            }


        });
        return view;

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}

