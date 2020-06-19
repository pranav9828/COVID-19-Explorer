package com.corona.covid_19explorer.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.corona.covid_19explorer.Adapter.CountryAdapter;
import com.corona.covid_19explorer.Classes.Country;
import com.corona.covid_19explorer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StatesFragment extends Fragment {

    RecyclerView recyclerView;
    List<Country> countryList;
    Country country;
    CountryAdapter countryAdapter;
    JsonObjectRequest jsonObjectRequest;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_states, container, false);
        recyclerView = view.findViewById(R.id.country_list);
        countryList = new ArrayList<>();
        fetchData();
        return view;
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
                countryAdapter = new CountryAdapter(getContext(), countryList);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(countryAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(jsonObjectRequest);
    }
}
