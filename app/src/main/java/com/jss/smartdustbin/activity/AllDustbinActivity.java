package com.jss.smartdustbin.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jss.smartdustbin.activity.DustbinDetailsActivity;
import com.jss.smartdustbin.model.Dustbin;
import com.jss.smartdustbin.R;

import java.util.ArrayList;
import java.util.HashMap;

public class AllDustbinActivity extends AppCompatActivity implements GoogleMap.OnMarkerClickListener {

    TextView dustbinState;
    TextView dustbinCity;
    TextView dustbinLocality;
    LinearLayout content;
    ProgressBar loader;
    ProgressBar dustbinLevelPb;
    Button dustbinMoreDetails;
    private HashMap<Marker, Integer> mHashMap;
    ArrayList<Dustbin> dustbinArrayList;
    HashMap<String, Dustbin> markerDustbinHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_dustbin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("All Dustbin");
        mHashMap = new HashMap<Marker, Integer>();

        dustbinArrayList = new ArrayList<>();
        dustbinState = findViewById(R.id.state_text_view);
        dustbinCity = findViewById(R.id.city_text_view);
        dustbinLocality = findViewById(R.id.locality_text_view);
        content = findViewById(R.id.comtent_all_dustbin_activity);
        loader = findViewById(R.id.loader_all_dustbin_map);
        dustbinLevelPb = findViewById(R.id.dustbin_progressbar);
        dustbinMoreDetails = findViewById(R.id.bt_more_details);
        markerDustbinHashMap = new HashMap<String, Dustbin>();
        ArrayList<Marker> markers = new ArrayList<Marker>();

        //dustbinLevelPb.setProgress(70);

        dustbinMoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dustbinDetailsActivityIntent = new Intent(AllDustbinActivity.this, DustbinDetailsActivity.class);
                startActivity(dustbinDetailsActivityIntent);
            }
        });
        Dustbin dustbin1 = new Dustbin();
        dustbin1.setState("UP");
        dustbin1.setCity("Noida");
        dustbin1.setId("102");
        dustbin1.setLocality("Sector 66");

        Dustbin dustbin2 = new Dustbin();
        dustbin2.setState("UP");
        dustbin2.setCity("Noida");
        dustbin2.setId("106");
        dustbin2.setLocality("Sector 15");

        Dustbin dustbin3 = new Dustbin();
        dustbin3.setState("UP");
        dustbin3.setCity("Noida");
        dustbin3.setId("112");
        dustbin3.setLocality("Sector 34");

        Dustbin dustbin4 = new Dustbin();
        dustbin4.setCity("Noida");
        dustbin4.setId("108");
        dustbin4.setLocality("Sector 22");

        dustbinArrayList.add(dustbin1);
        dustbinArrayList.add(dustbin2);
        dustbinArrayList.add(dustbin3);
        dustbinArrayList.add(dustbin4);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.all_dustbins_map);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                // mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                mMap.clear(); //clear old markers

                /*CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(28.605223,77.376407))
                        .zoom(12)
                        .bearing(0)
                        .tilt(45)
                        .build();


                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 1000, null);*/
                /*mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.4219999,-122.0862462)));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(16));


*/              for (int i = 0; i < dustbinArrayList.size(); i++){

                }



                Marker marker1 =  mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(28.584881,77.309219))
                        .title("50% full")
                        .snippet("click here for more details")
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("marker_red", 100, 125))));
                markerDustbinHashMap.put(marker1.getId(), dustbinArrayList.get(1));
                markers.add(marker1);

                Marker marker2 = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(28.583724,77.360467))
                        .title("50% full")
                        .snippet("click here for more details")
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("marker_orange", 75, 115))));
                markerDustbinHashMap.put(marker2.getId(), dustbinArrayList.get(2));
                markers.add(marker2);

                Marker marker3 = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(28.595203,77.347282))
                        .title("50% full")
                        .snippet("click here for more details")
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("marker_red", 100, 125))));
                markerDustbinHashMap.put(marker3.getId(), dustbinArrayList.get(3));
                markers.add(marker3);

                Marker marker4 = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(28.605223, 77.376407))
                        .title("50% full")
                        .snippet("click here for more details")
                        .icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("marker_green", 75, 115))));
                markerDustbinHashMap.put(marker4.getId(), dustbinArrayList.get(0));
                markers.add(marker4);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                for (Marker marker : markers) {
                    builder.include(marker.getPosition());
                }
                LatLngBounds bounds = builder.build();
                int width = getResources().getDisplayMetrics().widthPixels;
                //int height = Utilities.dp(309.50f);
                int height = getResources().getDisplayMetrics().heightPixels;
                int padding = 60; // offset from edges of the map in pixels
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,width, height, padding);
                mMap.animateCamera(cu);


                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {

                        // dustbinState.setText(markerDustbinHashMap.get(marker.getId()).getState());
                        marker.getId();
                        //dustbinCity.setText(markerDustbinHashMap.get(marker.getId()).getCity());
                        //dustbinLocality.setText(markerDustbinHashMap.get(marker.getId()).getLocality());
                        Toast.makeText(getApplicationContext(),"Marker is clicked" ,Toast.LENGTH_LONG).show();
                        marker.showInfoWindow();
                        mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
                        mMap.getUiSettings().setMapToolbarEnabled(true);
                        return true;
                    }
                });

                mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        mMap.getUiSettings().setMapToolbarEnabled(true);
                        Intent dustbinDetailsActivityIntent = new Intent(AllDustbinActivity.this, DustbinDetailsActivity.class);
                        startActivity(dustbinDetailsActivityIntent);
                    }
                });

            }
        });



    }

    private void updateMap(){

    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        Toast.makeText(this,"Marker is clicked" ,Toast.LENGTH_LONG).show();

       /* if (marker.equals,(myMarker))
        {
            //handle click here
        }*/
        return true;
    }

    public Bitmap resizeMapIcons(String iconName,int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {


            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
