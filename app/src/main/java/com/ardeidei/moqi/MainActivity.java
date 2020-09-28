package com.ardeidei.moqi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.ardeidei.moqi.adapter.CompanyAdapter;
import com.ardeidei.moqi.connection.VolleySingleton;
import com.ardeidei.moqi.connection.WebsiteCon;
import com.ardeidei.moqi.model.CompanyModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    WebsiteCon websiteCon = new WebsiteCon();
    CircleImageView civUPic;
    FloatingActionButton fabScan;
    TextView tvUName, tvUCom;
    RecyclerView rvCompany;


    List<CompanyModel> companyList;
    CompanyAdapter companyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        civUPic = findViewById(R.id.am_civ_upic);
        tvUName = findViewById(R.id.am_tv_uname);
        tvUCom = findViewById(R.id.am_tv_ucompany);

        rvCompany = findViewById(R.id.am_rv_company);
        fabScan = findViewById(R.id.am_fab_scan);


        fabScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scan();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //show newest post first, for this load from last
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        //set layout to recycler view
        rvCompany.setLayoutManager(layoutManager);

        //init company list
        companyList = new ArrayList<>();
        loadCompany();
    }

    private void scan() {
        d
    }


    private void loadCompany() {
        String requestUrl = websiteCon.getURL() + "login";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, requestUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting response to json object
                            JSONArray comlist = new JSONArray(response);
                            for(int i=0; i<comlist.length(); i++){
                                JSONObject companyObject = comlist.getJSONObject(i);

                                String cname = companyObject.getString("firstname");
                                String caddress = companyObject.getString("lastname");
                                String id = companyObject.getString("id");

                                CompanyModel companyModel = new CompanyModel(id, cname, caddress);
                                companyList.add(companyModel);
                            }


                            //adapter
                            companyAdapter = new CompanyAdapter(MainActivity.this, companyList);
                            companyAdapter.notifyDataSetChanged();
                            //set adapter to recyclerview
                            rvCompany.setAdapter(companyAdapter);

                            //if no error in response

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = null;
                        if (volleyError instanceof NetworkError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                        } else if (volleyError instanceof AuthFailureError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                        } else if (volleyError instanceof NoConnectionError) {
                            message = "Cannot connect to Internet...Please check your connection!";
                        } else if (volleyError instanceof TimeoutError) {
                            message = "Connection TimeOut! Please check your internet connection.";
                        }
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });

        VolleySingleton.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
    }


}

