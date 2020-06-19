package com.corona.covid_19explorer.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.corona.covid_19explorer.Adapter.CountryAdapter;
import com.corona.covid_19explorer.Classes.Country;
import com.corona.covid_19explorer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Country_List extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Country> countryList;
    CountryAdapter countryAdapter;
    JsonObjectRequest jsonObjectRequest;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country__list);
        recyclerView = findViewById(R.id.country_list);
        countryList = new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        fetchData();
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void fetchData() {
        String url = "http://covid19-india-adhikansh.herokuapp.com/states";

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.optJSONArray("state");
                    for (int i=0;i<jsonArray.length(); i++){
                        Country country = new Country();
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        country.setName(jsonObject.getString("name"));
                        country.setTotal(jsonObject.getString("total"));
                        country.setDeath(jsonObject.getString("death"));
                        country.setCured(jsonObject.getString("cured"));
                        country.setActive(jsonObject.getString("active"));
                        countryList.add(country);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
                countryAdapter = new CountryAdapter(Country_List.this, countryList);
                recyclerView.setLayoutManager(new LinearLayoutManager(Country_List.this));
                recyclerView.setAdapter(countryAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Country_List.this);
        requestQueue.add(jsonObjectRequest);
    }

}
