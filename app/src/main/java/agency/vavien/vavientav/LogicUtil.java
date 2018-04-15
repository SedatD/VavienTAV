package agency.vavien.vavientav;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Sedat
 * on 15.04.2018.
 */

public class LogicUtil {
    private int flightNumber = -1;
    private Context context;

    private static final String TAG = "LogicUtil";
    private static final String baseUrl = "http://35.159.15.121:8080/";
    private String url = "aodbFlights/afmsFlights";
    private String startDate = "15/04/2018 18:00", endDate = "15/04/2018 18:10";
    public Airport airport = new Airport();
    public ArrayList<Flight> flightArrayList = new ArrayList<>();

    public LogicUtil(Context context) {
        this.context = context;
    }

    public LogicUtil(Context context, int flightNumber) {
        this.context = context;
        this.flightNumber = flightNumber;
    }

    public LogicUtil(Context context, String startDate, String endDate) {
        this.context = context;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LogicUtil(Context context, String startDate, String endDate, int flightNumber) {
        this.context = context;
        this.startDate = startDate;
        this.endDate = endDate;
        this.flightNumber = flightNumber;
    }

    public void urlMetre() {
        url = baseUrl + url;

        Uri.Builder builder = Uri.parse(url).buildUpon();
        builder.appendQueryParameter("startDate", startDate);
        builder.appendQueryParameter("endDate", endDate);
        if (flightNumber != -1)
            builder.appendQueryParameter("endDate", flightNumber + "");

        url = builder.toString();

        getRequest();
    }

    private void getRequest() {
        RequestQueue queue = Volley.newRequestQueue(context);
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
