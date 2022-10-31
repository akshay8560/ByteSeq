package com.example.byteseq;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.byteseq.databinding.ActivityMainBinding;
import com.example.byteseq.databinding.ActivitySecondBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    ActivitySecondBinding binding;
    Button fetchdata,logout;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fetchdata=findViewById(R.id.fatchdata);
        logout=findViewById(R.id.Logout);
         listView=findViewById(R.id.listview1);
      RequestQueue requestQueue= Volley.newRequestQueue(this);

        fetchdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url="https://byteseq.com/temp/api.php";
                ArrayList<String> namearraylist=new ArrayList<>();

                JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            for(int i=0;i<jsonArray.length();i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                int idx = jsonObject.getInt("idx");
                                String pkey = jsonObject.getString("pkey");
                                int adate = jsonObject.getInt("adate");
                                String sold = jsonObject.getString("sold");
                                namearraylist.add(pkey);
                                namearraylist.add(sold);



                            }
                            ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(SecondActivity.this,
                                    android.R.layout.simple_expandable_list_item_1,namearraylist);
                                    binding.listview1.setAdapter(arrayAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SecondActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                requestQueue.add(jsonObjectRequest);


            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SecondActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}