package agency.vavien.vavientav;

import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;

/**
 * Created by Sedat
 * on 15.04.2018.
 */

class Constants {

    static final String GEOFENCE_ID_STAN_UNI = "TAV";
    static final float GEOFENCE_RADIUS_IN_METERS = 3;
    static final HashMap<String, LatLng> AREA_LANDMARKS = new HashMap<String, LatLng>();

    static {
        AREA_LANDMARKS.put(GEOFENCE_ID_STAN_UNI, new LatLng(40.9784614, 28.8275138));
    }
}
