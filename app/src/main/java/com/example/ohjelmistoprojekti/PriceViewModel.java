/*package com.example.ohjelmistoprojekti;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyViewModel extends ViewModel {





    public static String[] sendAndRequestResponse() {

        // Toast.makeText(this, builder, Toast.LENGTH_LONG).show();
        // RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this);

        //String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                // Toast.makeText(getApplicationContext(),"Response :" + response.toString(), Toast.LENGTH_LONG).show();//display the response on screen
                // Kutsutaan JSON-parsintafunktiota
                // String[] pricesArray = new String[24];
                String[] pricesArray = parseJsonAndUpdateUI(response, "2022-11-28");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i(TAG,"Error :" + error.toString());
            }
        });

        mRequestQueue.add(mStringRequest);
    }


            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Virhe JSON parsinnassa", Toast.LENGTH_SHORT).show();
        }

        return
    }
}
*/