package com.example.proyecto.ui.ubicacion;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.example.proyecto.Activities.VerAviso;
import com.example.proyecto.AvisosAdapter;
import com.example.proyecto.CustomInfoWindowAdapter;
import com.example.proyecto.MainActivity;
import com.example.proyecto.Model.Aviso;
import com.example.proyecto.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UbicacionFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
    private LocationManager ubicacion;

    private RecyclerView mRecyclerView;
    private AvisosAdapter mAdapter;

    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseRef;
    private List<Aviso> mAvisos;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_ubicacion, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);

        localizacionActual();
        return root;
    }

    private void localizacionActual() {

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    1000);
            return;
        }

        ubicacion = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);

        Location location = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        int zoom = 17;
        // Add a marker in Sydney and move the camera
        // TODO: Before enabling the My Location layer, you must request
        // location permission from the user. This sample does not include
        // a request for location permission.
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {



                // mProgressCircle = findViewById(R.id.progress_circle);

                mAvisos = new ArrayList<>();

                mDatabaseRef = FirebaseDatabase.getInstance().getReference("datos_aviso");

                mDatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Aviso a = postSnapshot.getValue(Aviso.class);
                            mAvisos.add(a);
                        }

                        if (dataSnapshot.exists()) {
                            final LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            for (Aviso aviso : mAvisos) {

                                Log.e("DATO BASe DE DATOS", aviso.getApellido());
                                LatLng customMarkerLocation = new LatLng(-16.406839, -71.52239);
                                mMap.addMarker(new MarkerOptions().position(customMarkerLocation).
                                        icon(BitmapDescriptorFactory.fromBitmap(
                                                createCustomMarker(getContext(),R.drawable.persona_desconocida,aviso.getNombre(),aviso.getmImageUrl())))).setTitle(aviso.getDescripcion());
                                builder.include(customMarkerLocation); //Taking Point A (First LatLng)
                                mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(LayoutInflater.from(getActivity()),getContext(), aviso));

                            }
                            LatLngBounds bounds = builder.build();
                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 200);
                            mMap.moveCamera(cu);
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });




            }
        });



        // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, zoom));

    }
    public static Bitmap createCustomMarker(Context context,@DrawableRes int resource, String _name, String URL) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);


        CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);
        /*
        Glide.with(context)
                .load(URL)
                .transform(new CircularTransformation())
                .into(markerImage);


         */
        markerImage.setImageResource(resource);

        TextView txt_name = (TextView)marker.findViewById(R.id.name);
        txt_name.setText(_name);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }
    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this.getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this.getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        int zoom = 17;
        LatLng ubicacion = new LatLng(-16.406839 , -71.52239);
        MarkerOptions positionMarker = new MarkerOptions().position(ubicacion).title("Mi Ubicacion").snippet("Descripcion Breve");
        mMap.addMarker(positionMarker);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, zoom));


        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(ubicacion)      // Sets the center of the map to location user
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        return true;
    }
}