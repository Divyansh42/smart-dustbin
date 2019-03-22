package com.jss.smartdustbin.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.jss.smartdustbin.DustbinRegistrationData;
import com.jss.smartdustbin.R;

import java.net.SocketImpl;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterDustbinFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterDustbinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterDustbinFragment2 extends Fragment {

    Spinner spinnerStates, spinnerCities;
    Button btProceed2;
    List<String> statesList;
    List<String> citiesList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment2_register_dustbin, null, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Register Dustbin");

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

        btProceed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


}
