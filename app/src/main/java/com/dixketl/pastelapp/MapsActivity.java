package com.dixketl.pastelapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnMarkerClickListener,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        PlaceSelectionListener,
        GoogleMap.CancelableCallback{

    private GoogleMap mMap;

    private LocationManager lm;
    private String provider;

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    private CameraPosition mCameraPosition;
    Place myPlace;

    SupportMapFragment mapFragment;


    // The entry points to the Places API.
    private GoogleApiClient mGoogleApiClient;
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    PlaceAutocompleteFragment autocompleteFragment;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;


    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";


    private double Longitud;
    private double Latitud;

    //Marcador
    Marker mark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        provider = lm.getBestProvider(criteria, true);
        setContentView(R.layout.activity_maps);

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();


        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Obteniendo el fragmento de autocompletado que se usará con google places
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

        mMap.setBuildingsEnabled(true);


    }
    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
            /*Task<Location> mTL = mFusedLocationProviderClient.getLastLocation();
            Longitud = mTL.getResult().getLongitude();
            Latitud = mTL.getResult().getLatitude();*/
            autocompleteFragment.setOnPlaceSelectedListener(this);
            View mapView = mapFragment.getView();

            if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
                //Get the button view
                View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
                // and next place it, on bottom right (as Google Maps app)
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(130,130);
                // size of button in dp
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                params.setMargins(0, 0, 20, 100);
                locationButton.setLayoutParams(params);
            }

            // Getting Current Location From GPS
            Location location = lm.getLastKnownLocation(provider);
            if(location!=null){
                onLocationChanged(location);
            }

            lm.requestLocationUpdates(provider, 20000, 0, this);

            onMyLocationButtonClick();

        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        mMap.clear();

        LatLng myLatLang = new LatLng(Latitud,Longitud);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLatLang));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15),1500,this);


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
           try{
               Task<PlaceLikelihoodBufferResponse> placeResult =
                       mPlaceDetectionClient.getCurrentPlace(null);
               placeResult.addOnCompleteListener(new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
                   @Override
                   public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
                       if (task.isSuccessful() && task.getResult() != null) {
                           PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
                           for (PlaceLikelihood placeLikelihood : likelyPlaces) {
                               myPlace = placeLikelihood.getPlace();
                           }
                           mark = mMap.addMarker(new MarkerOptions().position(myPlace.getLatLng())
                                   .title(myPlace.getName().toString()).snippet(myPlace.getAddress().toString())
                                   .draggable(true));

                           likelyPlaces.release();
                       }
                   }
               });

               //myPlace = mPlaceDetectionClient.getCurrentPlace(null).getResult().get(0).getPlace();

           }catch (Exception e){
               e.printStackTrace();
           }

        }
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE){
            return;
        }
        if (PermissionUtils.isPermissionGranted(permissions,grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)){
            // Habilita la capa de my location si el permiso es otorgado
            enableMyLocation();
        }else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied){
            //El permiso no se otorgó, despliega el dialogo de error
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }
    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }


    //Métodos para elegir que hacer al dar click o arrastrar un marcador
    @Override
    public boolean onMarkerClick(Marker marker) {

        return false;
    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            //Todo
            mark = mMap.addMarker(new MarkerOptions().position(myPlace.getLatLng())
                    .title(myPlace.getName().toString()).snippet(myPlace.getAddress().toString())
                    .draggable(true));


        }
    }

    //Para manejar las falla de conexión del API de Google places
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    //Manejador de eventos para la selección y autoCompletado de lugares,
    // util con Google Places

    @Override
    public void onPlaceSelected(Place place) {

        myPlace = place;
        mMap.clear();
        mark = mMap.addMarker(new MarkerOptions().position(place.getLatLng())
                .title(place.getName().toString()).snippet(place.getAddress().toString())
                .draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15),1500,this);
    }



    @Override
    public void onError(Status status) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onCancel() {

    }

    //Administrar las peticiones de localización
    @Override
    public void onLocationChanged(Location location) {
        Longitud = location.getLongitude();
        Latitud = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            try {
                lm.requestLocationUpdates(provider, 20000, 0, this);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            lm.removeUpdates(this);
        }
    }

}
