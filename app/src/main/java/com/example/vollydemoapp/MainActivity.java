package com.example.vollydemoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Data> dataArrayList;
    MyAdapter myAdapter;
    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private String url="https://picsum.photos/v2/list";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recycler_view);
        linearLayoutManager = new LinearLayoutManager(this);


        // use a linear layout manager
        dataArrayList=new ArrayList<>();


        getData();

    }

    private void getData() {



        ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait ...");
        dialog.setCancelable(true);
        dialog.show();




        //Initialize String Request
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response!=null){
                    dialog.dismiss();
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        parseArray(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                myAdapter=new MyAdapter(getApplicationContext(),dataArrayList);


                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.addItemDecoration(dividerItemDecoration);
                recyclerView.setAdapter(myAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage().toString()
                    , Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);

    }

    private void parseArray(JSONArray jsonArray) {
        for (int i=0;i<jsonArray.length();i++){
            try {
                JSONObject object=jsonArray.getJSONObject(i);
                Data data=new Data();
                //set name
                data.setName(object.getString("author").toString());
                //set image
                data.setImage(object.getString("download_url"));
                //Add data in array list
                dataArrayList.add(data);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}