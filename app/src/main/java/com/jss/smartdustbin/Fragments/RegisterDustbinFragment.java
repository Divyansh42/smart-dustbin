package com.jss.smartdustbin.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.jss.smartdustbin.Models.DustbinRegistrationData;
import com.jss.smartdustbin.R;

import java.io.Serializable;

public class RegisterDustbinFragment extends Fragment {

    EditText editTextDustbinRegistrationId;
    Button btProceed1;
    String dustbinRegistrationId;
    DustbinRegistrationData dustbinRegistrationData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_register_dustbin, container, false);
        editTextDustbinRegistrationId = (EditText) view.findViewById(R.id.et_dustbin_registration_id);
        btProceed1 = (Button) view.findViewById(R.id.bt_proceed1);


        dustbinRegistrationData = new DustbinRegistrationData();

        btProceed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RegisterDustbinFragment2();
                dustbinRegistrationData.setId(editTextDustbinRegistrationId.getText().toString());
                Bundle bundle = new Bundle();
                bundle.putSerializable("registrationDataObject", (Serializable) dustbinRegistrationData);
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fl_home_activity, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Register Dustbin");

    }


}
