package agency.vavien.vavientav;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class LogicActivity extends AppCompatActivity {
    private static final String TAG = "LogicAct";
    private static final String baseUrl = "http://35.159.15.121:8080/";
    private String url = "aodbFlights/afmsFlights";
    private String startDate = "15/04/2018 18:00", endDate = "15/04/2018 18:10";
    private Airport airport = new Airport();
    private ArrayList<Flight> flightArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logic);

        url = baseUrl + url;

        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("startDate", startDate);
        builder.appendQueryParameter("endDate", endDate);

        url = builder.toString();

        Log.wtf(TAG, "url : " + url);

        getRequest();
    }

    private void getRequest() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.wtf(TAG, "Response : " + response);
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Flight flight = new Flight(jsonArray.getJSONObject(i));
                                flight.setStartDate(startDate);
                                flight.setEndDate(endDate);
                                calculatePassengerCount(flight);
                                flightArrayList.add(flight);
                            }

                            int totalPassenger = airport.getTotalArrivalCount() + airport.getTotalDepartureCount();
                            airport.setIntensity(((totalPassenger * 100) / 6000));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "*/*"));
                                Log.wtf("try", "res : " + res + " / response : " + response);
                                JSONObject obj = new JSONObject(res);
                                Log.wtf("try", "obj : " + obj);
                            } catch (UnsupportedEncodingException | JSONException e1) {
                                e1.printStackTrace();
                            }
                        }
                        Log.wtf(TAG, "onErrorResponse : " + error + " / volleyError.getMessage() : " + error.getMessage());
                        error.printStackTrace();
                    }
                });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(50000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    private void calculatePassengerCount(Flight flight) {
        int tempCapacity = 0;
        if (flight.getLeg() == 'A') {
            if (flight.getCapacity() != -1) {
                tempCapacity = flight.getCapacity();
            } else {
                Random rand = new Random();
                int randomNum = rand.nextInt((300 - 100) + 1) + 100;
                tempCapacity = randomNum;
            }

            boolean isRepeated = false;
            for (int i = 0; i < flightArrayList.size(); i++) {
                if (flightArrayList.get(i).getRegistrationNo().equals(flight.getRegistrationNo())) {
                    isRepeated = true;
                }
            }

            if (!isRepeated)
                airport.setTotalArrivalCount(tempCapacity + airport.getTotalArrivalCount());

        } else if (flight.getLeg() == 'D') {
            if (flight.getCapacity() != -1) {
                tempCapacity = flight.getCapacity();
            } else {
                Random rand = new Random();
                int randomNum = rand.nextInt((450 - 150) + 1) + 150;
                tempCapacity = randomNum;
            }

            boolean isRepeated = false;
            for (int i = 0; i < flightArrayList.size(); i++) {
                if (flightArrayList.get(i).getRegistrationNo().equals(flight.getRegistrationNo())) {
                    isRepeated = true;
                }
            }

            if (!isRepeated)
                airport.setTotalDepartureCount(tempCapacity + airport.getTotalDepartureCount());

            String temp = "BURGER";
            switch (flight.getDestinationRegion()) {
                case "EUROPE":
                    temp = "PIZZA";
                    break;
                case "ASIA":
                    temp = "SUSHI";
                    break;
                case "AFRICA":
                    temp = "FISH";
                    break;
                case "AMERICA":
                    temp = "BURGER";
                    break;
                case "AUSTRALIA":
                    temp = "FISH";
                    break;
            }
            flight.setFoodSuggest(temp);
        }
    }

}
