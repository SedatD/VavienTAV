package agency.vavien.vavientav;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

/**
 * Created by Sedat
 * on 15.04.2018.
 */

public class GeofenceRegistrationService extends IntentService {
    private static final String TAG = "GeofenceService";

    public void onCreate() {
        super.onCreate();
        Log.wtf(TAG, "onCreate");
    }

    public GeofenceRegistrationService() {
        super(TAG);
    }

    public GeofenceRegistrationService(String aq) {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            Log.wtf(TAG, "GeofencingEvent error " + geofencingEvent.getErrorCode());
        } else {
            int transaction = geofencingEvent.getGeofenceTransition();
            List<Geofence> geofences = geofencingEvent.getTriggeringGeofences();
            Geofence geofence = geofences.get(0);
            if (transaction == Geofence.GEOFENCE_TRANSITION_ENTER && geofence.getRequestId().equals(Constants.GEOFENCE_ID_STAN_UNI)) {
                Log.wtf(TAG, "You are inside Stanford University");
                Toast.makeText(this, "inside", Toast.LENGTH_LONG).show();
            } else {
                Log.wtf(TAG, "You are outside Stanford University");
                Toast.makeText(this, "outside", Toast.LENGTH_LONG).show();
            }
        }
    }

}
