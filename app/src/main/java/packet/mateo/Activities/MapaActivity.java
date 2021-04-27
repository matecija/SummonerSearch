package packet.mateo.Activities;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import packet.mateo.R;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng grietainvocador = new LatLng(40.02766903933305, -3.5904345159629694);
        mMap.addMarker(new MarkerOptions().position(grietainvocador).title("Tienda: La Grieta del Invocador"));

        LatLng riot1 = new LatLng(48.871201121938526, 2.3463796752180275);
        mMap.addMarker(new MarkerOptions().position(riot1).title("Riot Games París"));


        LatLng riot2 = new LatLng(34.032449846083, -118.45754378562063);
        mMap.addMarker(new MarkerOptions().position(riot2).title("Riot Games USA"));


        LatLng riot3 = new LatLng(52.430685562763045, 13.540404781735752);
        mMap.addMarker(new MarkerOptions().position(riot3).title("Riot Games Berlín"));

        LatLng riot4 = new LatLng( 37.50912127454891, 127.06089198830061);
        mMap.addMarker(new MarkerOptions().position(riot4).title("Riot Games Korea"));


        mMap.moveCamera(CameraUpdateFactory.newLatLng(grietainvocador));
    }
}