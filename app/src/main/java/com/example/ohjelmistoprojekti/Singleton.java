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

public class Singleton{
    String url = "https://ohjelmistoprojekti-production.up.railway.app/pricejson/";
    String[] pricesArray = new String[24];
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;
    private static Context context;
    private static Singleton instance;

    //private static final String TAG = getActivity().getName();

    private Singleton(Context context){
/*        for(int i = 0; i <= 23; i++){
            String s=String.valueOf(i*2);
            mPricesToday[i] = s;
        }*/

        mRequestQueue = Volley.newRequestQueue(context);
        getPriceData(context);
    }

    public String[] getPrices(){
        StringBuilder builder = new StringBuilder();
        return pricesArray;
    }
    private static Singleton mSingleton;


    public static Singleton getInstance(Context context) {
        if( instance == null ) {
            instance = new Singleton(context);
        }
        return instance;
    }




    public void getPriceData(Context context) {
        Toast.makeText(context, "getPriceData -funktion sisällä!", Toast.LENGTH_SHORT).show();
        // Haetaan hintatiedot palvelimelta (JSON)
        String url = "https://ohjelmistoprojekti-production.up.railway.app/pricejson/";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    // Näytetään response toastina ruudulla
                    Toast.makeText(context, response, Toast.LENGTH_LONG).show();
                    parseJsonAndUpdateUI(response, context, "2022-11-28");
                },
                error -> {
                    // virhe verkosta hakemisessa
                    Toast.makeText(context, "Verkkovirhe", Toast.LENGTH_SHORT).show();
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
                // Toast.makeText(this, date, Toast.LENGTH_LONG).show();
                // Toast.makeText(this, arr.getJSONObject(i).getString("H00"), Toast.LENGTH_LONG).show();

                if (date.equals(time)) {

                    // Toast.makeText(this, "Inside IF", Toast.LENGTH_LONG).show();
                    this.pricesArray[0] = arr.getJSONObject(i).getString("H00");
                    this.pricesArray[1] = arr.getJSONObject(i).getString("H01");
                    this.pricesArray[2] = arr.getJSONObject(i).getString("H02");
                    this.pricesArray[3] = arr.getJSONObject(i).getString("H03");
                    this.pricesArray[4] = arr.getJSONObject(i).getString("H04");
                    this.pricesArray[5] = arr.getJSONObject(i).getString("H05");
                    this.pricesArray[6] = arr.getJSONObject(i).getString("H06");
                    this.pricesArray[7] = arr.getJSONObject(i).getString("H07");
                    this.pricesArray[8] = arr.getJSONObject(i).getString("H08");
                    this.pricesArray[9] = arr.getJSONObject(i).getString("H09");
                    this.pricesArray[10] = arr.getJSONObject(i).getString("H10");
                    this.pricesArray[11] = arr.getJSONObject(i).getString("H11");
                    this.pricesArray[12] = arr.getJSONObject(i).getString("H12");
                    this.pricesArray[13] = arr.getJSONObject(i).getString("H13");
                    this.pricesArray[14] = arr.getJSONObject(i).getString("H14");
                    this.pricesArray[15] = arr.getJSONObject(i).getString("H15");
                    this.pricesArray[16] = arr.getJSONObject(i).getString("H16");
                    this.pricesArray[17] = arr.getJSONObject(i).getString("H17");
                    this.pricesArray[18] = arr.getJSONObject(i).getString("H18");
                    this.pricesArray[19] = arr.getJSONObject(i).getString("H19");
                    this.pricesArray[20] = arr.getJSONObject(i).getString("H20");
                    this.pricesArray[21] = arr.getJSONObject(i).getString("H21");
                    this.pricesArray[22] = arr.getJSONObject(i).getString("H22");
                    this.pricesArray[23] = arr.getJSONObject(i).getString("H23");
                }

            }
            StringBuilder builder = new StringBuilder();

            for(String k : this.pricesArray) {

                builder.append("").append(k).append(" ");
            }

            Toast.makeText(context, builder, Toast.LENGTH_LONG).show();
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
}



//        Toast.makeText(this, mPricesToday[3].toString(), Toast.LENGTH_LONG).show();