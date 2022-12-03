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
import java.util.Date;
import java.util.Locale;

public class Singleton {
    String url = "https://ohjelmistoprojekti-production.up.railway.app/pricejson/";
    Double[] pricesArray = new Double[24];
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private static Context context;
    private static Singleton instance;

    //private static final String TAG = getActivity().getName();

    private Singleton(Context context) {
        this.context = context;
        mRequestQueue = Volley.newRequestQueue(context);
    }

    public interface VolleyResponseListener {
        void onError(String message);
        void onResponse(Double[] pricesArray);
    }

    public static Singleton getInstance(Context context) {
        if (instance == null) {
            instance = new Singleton(context);
        }
        return instance;
    }

    public void getPriceData(Context context, VolleyResponseListener volleyResponseListener) {
        // Haetaan hintatiedot palvelimelta (JSON)
        String url = "https://ohjelmistoprojekti-production.up.railway.app/pricejson/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    //Toast.makeText(context, "RESPONSE: "+response, Toast.LENGTH_LONG).show();
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());                    parseJsonAndUpdateUI(response, context, "2022-12-03");
                    parseJsonAndUpdateUI(response, context, "2022-12-03");
                    volleyResponseListener.onResponse(pricesArray);
                },
                error -> {
                    // virhe verkosta hakemisessa
                    Toast.makeText(context, "Verkkovirhe", Toast.LENGTH_SHORT).show();
                    volleyResponseListener.onError("volleyResponseListener error");
                }
        );
        // Add the request to the RequestQueue.
        mRequestQueue.add(stringRequest);
    }

    private void parseJsonAndUpdateUI(String response, Context context, String time) {
        try {
            JSONObject obj = new JSONObject(response);
            JSONArray arr = obj.getJSONArray("Prices"); // notice that `"posts": [...]`
            for (int i = 0; i < arr.length(); i++) {

                String date = arr.getJSONObject(i).getString("Date");

                if (date.equals(time)) {
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
                    //Toast.makeText(context, "Parcing: "+arr.getJSONObject(i).getString("H00"), Toast.LENGTH_LONG).show();

                }

            }


            // Parsitaan JSONia eli kaivetaan haluttu data
            /*String id = rootObject.getJSONArray("Prices").getJSONObject(8).getString("id");
            Double H23 = rootObject.getJSONArray("Prices").getJSONObject(8).getDouble("H23");
            String sH23 = String.valueOf(H23);*/

            /*
            Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
            Toast.makeText(context, sH23, Toast.LENGTH_SHORT).show();*/
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Virhe JSON parsinnassa", Toast.LENGTH_SHORT).show();
        }


    }


    private void toastPrices(Double[] pricesArray) {
        StringBuilder builder = new StringBuilder();
        for (Double k : pricesArray) {
            builder.append("").append(k).append(" ");
        }
        Toast.makeText(context, "builder: " + builder, Toast.LENGTH_LONG).show();

    }
}