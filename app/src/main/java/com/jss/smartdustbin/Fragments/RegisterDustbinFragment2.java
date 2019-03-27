package com.jss.smartdustbin.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.jss.smartdustbin.Activities.MapsActivity;
import com.jss.smartdustbin.Models.DustbinRegistrationData;
import com.jss.smartdustbin.Utils.CustomOnItemSelectedListener;
import com.jss.smartdustbin.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RegisterDustbinFragment2 extends Fragment {

    public static final String LOG_TAG = RegisterDustbinFragment2.class.getSimpleName();

    Spinner spinnerStates, spinnerCities;
    Button btProceed2;
    List<String> statesList;
    List<String> citiesList;
    DustbinRegistrationData dustbinRegistrationData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment2_register_dustbin, container, false);
        dustbinRegistrationData = (DustbinRegistrationData) getArguments().getSerializable("registrationDataObject");

        spinnerStates = (Spinner) view.findViewById(R.id.spinner_states);
        spinnerCities = (Spinner) view.findViewById(R.id.spinner_cities);
        btProceed2 = (Button) view.findViewById(R.id.bt_proceed2);


        statesList = new ArrayList<>();
        statesList.add("Uttar Pradesh");
        statesList.add("Madhya  Pradesh");
        statesList.add("Gujarat");

        citiesList = new ArrayList<>();
        citiesList.add("Noida");
        citiesList.add("Varanasi");
        citiesList.add("Allahabad");


        ArrayAdapter<String> statesDataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, statesList);
        statesDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStates.setAdapter(statesDataAdapter);

        ArrayAdapter<String> citiesDataAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, citiesList);
        citiesDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCities.setAdapter(citiesDataAdapter);

        spinnerStates.setOnItemSelectedListener(new CustomOnItemSelectedListener());

        Log.e(LOG_TAG, " Dustbin Registration data " + dustbinRegistrationData.toString());

        btProceed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dustbinRegistrationData.setState(spinnerStates.getSelectedItem().toString());
                dustbinRegistrationData.setCity(spinnerCities.getSelectedItem().toString());
                /*Fragment fragment = new RegisterDustbinFragment3();
                Bundle bundle = new Bundle();
                bundle.putSerializable("registrationDataObject", (Serializable) dustbinRegistrationData);
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fl_home_activity, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();*/
                Intent mapIntent = new Intent(getActivity(), MapsActivity.class);
                startActivity(mapIntent);

               // Toast.makeText(getActivity(), dustbinRegistrationData.getId().toString(), Toast.LENGTH_SHORT).show();

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
