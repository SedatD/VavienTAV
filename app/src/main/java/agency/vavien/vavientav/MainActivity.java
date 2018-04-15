package agency.vavien.vavientav;

import android.app.PendingIntent;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = "MainAct";
    private GoogleApiClient googleApiClient;
    private PendingIntent pendingIntent;
    private int i = 0;
    private Location l;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build();

        LogicUtil logicUtil = new LogicUtil(this);
        logicUtil.urlMetre();
        if (logicUtil.airport.getIntensity() != 0)
        logicUtil.airport.getIntensity();

        if (logicUtil.flightArrayList.size() != 0)
        logicUtil.flightArrayList.get(0).getFoodSuggest();



        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 100);

    }

    private PendingIntent getGeofencePendingIntent() {
        if (pendingIntent != null) {
            return pendingIntent;
        }
        Intent intent = new Intent(this, GeofenceRegistrationService.class);
        return PendingIntent.getService(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void startGeofencing() {
        Log.wtf(TAG, "Start geofencing monitoring call");
        pendingIntent = getGeofencePendingIntent();
        if (!googleApiClient.isConnected()) {
            Log.wtf(TAG, "Google API client not connected");
        } else {
            try {
                GeofencingRequest geofencingRequest = new GeofencingRequest.Builder().setInitialTrigger(Geofence.GEOFENCE_TRANSITION_ENTER).addGeofence(getGeofence()).build();
                LocationServices.GeofencingApi.addGeofences(
                        googleApiClient, geofencingRequest,
                        pendingIntent).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess()) {
                            Log.wtf(TAG, "Successfully Geofencing Connected");
                        } else {
                            Log.wtf(TAG, "Failed to add Geofencing " + status.getStatus());
                        }
                    }
                });
            } catch (SecurityException e) {
                Log.wtf(TAG, e.getMessage());
            }
        }
    }

    private void stopGeoFencing() {
        pendingIntent = getGeofencePendingIntent();
        LocationServices.GeofencingApi.removeGeofences
                (googleApiClient, pendingIntent)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess())
                            Log.wtf(TAG, "Stop geofencing");
                        else
                            Log.wtf(TAG, "Not stop geofencing");
                    }
                });
    }

    private void startLocationMonitor() {
        Log.wtf(TAG, "start location monitor");
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(2000)
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates
                    (googleApiClient, locationRequest, this);
        } catch (SecurityException e) {
            Log.wtf(TAG, "SecurityException : " + e.getMessage());
        }

        startGeofencing();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    @NonNull
    private Geofence getGeofence() {
        LatLng latLng = Constants.AREA_LANDMARKS.get(Constants.GEOFENCE_ID_STAN_UNI);
        return new Geofence.Builder()
                .setRequestId(Constants.GEOFENCE_ID_STAN_UNI)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setCircularRegion(latLng.latitude, latLng.longitude, Constants.GEOFENCE_RADIUS_IN_METERS)
                .setNotificationResponsiveness(1000)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                .build();
    }

    /*private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(getGeofence());
        return builder.build();
    }*/

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.wtf(TAG, "onConnected");
        startLocationMonitor();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.wtf(TAG, "onConnectionSuspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.wtf(TAG, "onConnectionFailed : " + connectionResult.getErrorMessage());
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.reconnect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        //Log.wtf(TAG, "onLocationChanged Lat / Lng " + location.getLatitude() + " / " + location.getLongitude());
        //Toast.makeText(this, "Lat / Lng " + location.getLatitude() + " / " + location.getLongitude(), Toast.LENGTH_SHORT).show();

        if(i == 0){
        l = new Location("");
        l.setLatitude(40.9784614);
        l.setLongitude(28.8275138);
        if (location.distanceTo(l) < 5) {
            Log.wtf(TAG, "IN - 1 / distance : " + location.distanceTo(l));
            //Toast.makeText(this, "IN - 1 / distance : " + location.distanceTo(l), Toast.LENGTH_SHORT).show();
            try {
                OneSignal.postNotification(new JSONObject("{'contents': {'en':'TAV Havaalanı İşletmelerine Hoşgeldiniz'},'data':{'chatId':" + "Güvenlikten hızlı geçiş için lütfen tıklayınız" + "}, 'include_player_ids': ['" + OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId() + "']}"), null);
                i = 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            stopGeoFencing();
            stopLocationUpdates(); }
        } else if (i ==1){
            l = new Location("");
            l.setLatitude(40.9785614);
            l.setLongitude(28.8276138);
            if(location.distanceTo(l) < 5) {
                Log.wtf(TAG, "IN - 1 / distance : " + location.distanceTo(l));
                //Toast.makeText(this, "IN - 1 / distance : " + location.distanceTo(l), Toast.LENGTH_SHORT).show();
                try {
                    OneSignal.postNotification(new JSONObject("{'contents': {'en':'TAV'},'data':{'chatId':" + "Lütfen buraya tıklayıp uçuşunuzu seçiniz " + "}, 'include_player_ids': ['" + OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId() + "']}"), null);
                    i =2;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                stopGeoFencing();
                stopLocationUpdates();
            }

        }  else if ( i == 2) {
            l = new Location("");
            l.setLatitude(40.9785614);
            l.setLongitude(28.8276138);

            if(  location.distanceTo(l) < 5) {
                Log.wtf(TAG, "IN - 1 / distance : " + location.distanceTo(l));
                //Toast.makeText(this, "IN - 1 / distance : " + location.distanceTo(l), Toast.LENGTH_SHORT).show();
                try {
                    OneSignal.postNotification(new JSONObject("{'contents': {'en':'TAV'},'data':{'chatId':" + "Check-in işlemleri ve gate günvelik bilgileri için tıklayınız" + "}, 'include_player_ids': ['" + OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId() + "']}"), null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                stopGeoFencing();
                stopLocationUpdates();
            }
        }




        /*float[] results = new float[1];
        Location.distanceBetween(40.9784614, 28.8275138, location.getLatitude(), location.getLongitude(), results);
        if (results[0] < 5) {
            Log.wtf(TAG, "IN - 2 / distance : " + results[0]);
            Toast.makeText(this, "IN - 2 / distance : " + results[0], Toast.LENGTH_SHORT).show();
        } else {
            Log.wtf(TAG, "OUT - 2 / distance : " + results[0]);
            Toast.makeText(this, "OUT - 2 / distance : " + results[0], Toast.LENGTH_SHORT).show();
        }*/
    }

}
