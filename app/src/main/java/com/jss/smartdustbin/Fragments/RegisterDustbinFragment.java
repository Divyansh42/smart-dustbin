package com.jss.smartdustbin.Fragments;

import android.content.Context;
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

import com.jss.smartdustbin.DustbinRegistrationData;
import com.jss.smartdustbin.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterDustbinFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterDustbinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterDustbinFragment extends Fragment {

    EditText editTextDustbinRegistrationId;
    Button btProceed1;
    String dustbinRegistrationId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_dustbin, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Register Dustbin");

       editTextDustbinRegistrationId = (EditText) view.findViewById(R.id.et_dustbin_registration_id);
       btProceed1 = (Button) view.findViewById(R.id.bt_proceed1);


        DustbinRegistrationData dustbinRegistrationData = new DustbinRegistrationData();
        dustbinRegistrationData.setId(editTextDustbinRegistrationId.getText().toString());

        btProceed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new RegisterDustbinFragment2();
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fl_home_activity, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });

    }


}
