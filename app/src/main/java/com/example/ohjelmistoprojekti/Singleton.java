package com.example.ohjelmistoprojekti;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Singleton {
    String url = "https://ohjelmistoprojekti-production.up.railway.app/pricejson/";
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private static Context context;
    private static Singleton instance;

    // Getting context for singleton
    private Singleton(Context context) {
        this.context = context;
        mRequestQueue = Volley.newRequestQueue(context);
    }

    // Creating Volley Response Listener to get aSync data to fragments
    public interface VolleyResponseListener {
        // If error, send message
        void onError(String message);
        // If response, Send data
        void onResponse(double[] pricesToday, double[] pricesTomorrow,String dateToday, String dateTomorrow);
    }

    // Get instance function
    public static Singleton getInstance(Context context) {
        // Theres no instance yet, Create one
        if (instance == null) {
            instance = new Singleton(context);
        }
        // return instance
        return instance;
    }

    public void getPriceData(Context context, VolleyResponseListener volleyResponseListener) {
        // Defining json url
        String url = "https://ohjelmistoprojekti-production.up.railway.app/pricejson/";
        // Requesting a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // If got response
                    // Creating time parameters for parsing responce
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar cal = Calendar.getInstance();
                    String dateToday = sdf.format(cal.getTime());
                    cal.add(Calendar.DAY_OF_MONTH, 1);
                    String dateTomorrow = sdf.format(cal.getTime());
                    // Creating arrays for data
                    double[] pricesToday = new double[24];
                    double[] pricesTomorrow = new double[24];

                    // Parse response
                    parseJsonAndUpdateUI(response, context, dateToday, pricesToday);
                    parseJsonAndUpdateUI(response, context, dateTomorrow, pricesTomorrow);
                    volleyResponseListener.onResponse(pricesToday, pricesTomorrow,dateToday,dateTomorrow);
                },
                error -> {
                    // If theres a error getting response
                    Toast.makeText(context, "Network error at fetching from api", Toast.LENGTH_SHORT).show();
                    volleyResponseListener.onError("volleyResponseListener error");
                }
        );
        // Adding the request to the RequestQueue.
        mRequestQueue.add(stringRequest);
    }

    // Function for parcing data
    private void parseJsonAndUpdateUI(String response, Context context, String time, double[] pricesArray) {
        try {
            // Creating JSONObject from response
            JSONObject obj = new JSONObject(response);
            // Creating JSONArray from JSONObject
            JSONArray arr = obj.getJSONArray("Prices"); // notice that `"posts": [...]`
            // Looping through objects at Prices in json
            for (int i = 0; i < arr.length(); i++) {
                // Getting Date field from object
                String date = arr.getJSONObject(i).getString("Date");
                if (date.equals(time)) {
                    // If Date field equals to searched parameter put prices data to price array
                    pricesArray[0]  = arr.getJSONObject(i).getDouble("H00");
                    pricesArray[1] = arr.getJSONObject(i).getDouble("H01");
                    pricesArray[2] = arr.getJSONObject(i).getDouble("H02");
                    pricesArray[3] = arr.getJSONObject(i).getDouble("H03");
                    pricesArray[4] = arr.getJSONObject(i).getDouble("H04");
                    pricesArray[5] = arr.getJSONObject(i).getDouble("H05");
                    pricesArray[6] = arr.getJSONObject(i).getDouble("H06");
                    pricesArray[7] = arr.getJSONObject(i).getDouble("H07");
                    pricesArray[8] = arr.getJSONObject(i).getDouble("H08");
                    pricesArray[9] = arr.getJSONObject(i).getDouble("H09");
                    pricesArray[10] = arr.getJSONObject(i).getDouble("H10");
                    pricesArray[11] = arr.getJSONObject(i).getDouble("H11");
                    pricesArray[12] = arr.getJSONObject(i).getDouble("H12");
                    pricesArray[13] = arr.getJSONObject(i).getDouble("H13");
                    pricesArray[14] = arr.getJSONObject(i).getDouble("H14");
                    pricesArray[15] = arr.getJSONObject(i).getDouble("H15");
                    pricesArray[16] = arr.getJSONObject(i).getDouble("H16");
                    pricesArray[17] = arr.getJSONObject(i).getDouble("H17");
                    pricesArray[18] = arr.getJSONObject(i).getDouble("H18");
                    pricesArray[19] = arr.getJSONObject(i).getDouble("H19");
                    pricesArray[20] = arr.getJSONObject(i).getDouble("H20");
                    pricesArray[21] = arr.getJSONObject(i).getDouble("H21");
                    pricesArray[22] = arr.getJSONObject(i).getDouble("H22");
                    pricesArray[23] = arr.getJSONObject(i).getDouble("H23");
                }
            }
        } catch (JSONException e) {
            // If theres an error parsing JSON
            e.printStackTrace();
            Toast.makeText(context, "Error at parsing JSON", Toast.LENGTH_SHORT).show();
        }
    }

    // Function for toasting prices from pricesArray
    private void toastPrices(double[] pricesArray) {
        // Create StringBuilder
        StringBuilder builder = new StringBuilder();
        // Append data from array to StringBuilder
        for (double k : pricesArray) {
            builder.append("").append(k).append(" ");
        }
        // Toasting string builder
        Toast.makeText(context, "builder: " + builder, Toast.LENGTH_LONG).show();
    }
}