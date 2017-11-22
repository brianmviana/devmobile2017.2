package br.ufc.qx.locationapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private LocationManager locationManager;
    private TextView latitude, longitude, provedor;
    private String urlBase = "http://maps.googleapis.com/maps/api/staticmap" +
            "?size=400x400&sensor=true&markers=color:red|%s,%s";

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        provedor = findViewById(R.id.provedor);
        mWebView = findViewById(R.id.mapa);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(false);
        getLocationManager();

    }

    private void getLocationManager() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.INTERNET},
                    1);
            return;
        }

        Listener listener = new Listener();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        long tempoAtualizacao = 0;
        float distancia = 0;
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, tempoAtualizacao, distancia, listener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, tempoAtualizacao, distancia, listener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    getLocationManager();
                } else {
                    Toast.makeText(this, "Sem permissão para uso de gps ou rede.", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private class Listener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            String latitudeStr = String.valueOf(location.getLatitude());
            String longitudeStr = String.valueOf(location.getLongitude());

            provedor.setText(location.getProvider());
            latitude.setText(latitudeStr);
            longitude.setText(longitudeStr);

            String url = String.format(urlBase, latitudeStr, longitudeStr);
            mWebView.loadUrl(url);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {    }
        @Override
        public void onProviderEnabled(String provider) {  }
        @Override
        public void onProviderDisabled(String provider) { }
    }
}
