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
    String[] pricesArray = new String[24];
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

        void onResponse(String[] pricesArray);
    }




    public static Singleton getInstance(Context context) {
        if (instance == null) {
            instance = new Singleton(context);
        }
        return instance;
    }


    public void getPriceData(Context context, VolleyResponseListener volleyResponseListener) {
        // Toast.makeText(context, "getPriceData -funktion sisällä!", Toast.LENGTH_SHORT).show();
        // Haetaan hintatiedot palvelimelta (JSON)
        String url = "https://ohjelmistoprojekti-production.up.railway.app/pricejson/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Näytetään response toastina ruudulla
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

                // pricesToday[0] = arr.getJSONObject(i).getString("H00");
                String date = arr.getJSONObject(i).getString("Date");
                //Toast.makeText(context, "Data" + date, Toast.LENGTH_LONG).show();
                //Toast.makeText(context, "Parameter" +time, Toast.LENGTH_LONG).show();

                // Toast.makeText(this, arr.getJSONObject(i).getString("H00"), Toast.LENGTH_LONG).show();

                if (date.equals(time)) {

                    //Toast.makeText(context, "Inside IF", Toast.LENGTH_LONG).show();
                    pricesArray[0] = arr.getJSONObject(i).getString("H00");
                    pricesArray[1] = arr.getJSONObject(i).getString("H01");
                    pricesArray[2] = arr.getJSONObject(i).getString("H02");
                    pricesArray[3] = arr.getJSONObject(i).getString("H03");
                    pricesArray[4] = arr.getJSONObject(i).getString("H04");
                    pricesArray[5] = arr.getJSONObject(i).getString("H05");
                    pricesArray[6] = arr.getJSONObject(i).getString("H06");
                    pricesArray[7] = arr.getJSONObject(i).getString("H07");
                    pricesArray[8] = arr.getJSONObject(i).getString("H08");
                    pricesArray[9] = arr.getJSONObject(i).getString("H09");
                    pricesArray[10] = arr.getJSONObject(i).getString("H10");
                    pricesArray[11] = arr.getJSONObject(i).getString("H11");
                    pricesArray[12] = arr.getJSONObject(i).getString("H12");
                    pricesArray[13] = arr.getJSONObject(i).getString("H13");
                    pricesArray[14] = arr.getJSONObject(i).getString("H14");
                    pricesArray[15] = arr.getJSONObject(i).getString("H15");
                    pricesArray[16] = arr.getJSONObject(i).getString("H16");
                    pricesArray[17] = arr.getJSONObject(i).getString("H17");
                    pricesArray[18] = arr.getJSONObject(i).getString("H18");
                    pricesArray[19] = arr.getJSONObject(i).getString("H19");
                    pricesArray[20] = arr.getJSONObject(i).getString("H20");
                    pricesArray[21] = arr.getJSONObject(i).getString("H21");
                    pricesArray[22] = arr.getJSONObject(i).getString("H22");
                    pricesArray[23] = arr.getJSONObject(i).getString("H23");
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


    private void toastPrices(String[] pricesArray) {
        StringBuilder builder = new StringBuilder();
        for (String k : pricesArray) {
            builder.append("").append(k).append(" ");
        }
        Toast.makeText(context, "builder: " + builder, Toast.LENGTH_LONG).show();

    }
}