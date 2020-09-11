package com.gdg.bhopal.uiex.androidphpmysql;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private static final String URL_DATA ="http://192.168.1.2/Android/v1/userLogin.php";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private List<UserInfo> userInfos;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            Toast.makeText(this,"Login nahi hua bro!!!", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
        //Toast.makeText(this,"Profile Activity mein pada h apun!!!", Toast.LENGTH_SHORT).show();
       /* textViewUsername=(TextView)findViewById(R.id.textViewUsername);
        textViewUserEmail=(TextView)findViewById(R.id.textViewUseremail);

        textViewUserEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());*/


        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //https://www.internetfaqs.net/superheroes.php

       userInfos=new ArrayList<>();
       //UserInfo ui = new UserInfo("kunjesh butani","bkunjesh@gmail.com","1234567890","gdg1");
       //userInfos.add(ui);
        adapter=new MyAdapter(userInfos,getApplicationContext());
        recyclerView.setAdapter(adapter);

        loadRecyclerViewData();


    }

    private void loadRecyclerViewData(){
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loding Data...");
        progressDialog.show();
        //Toast.makeText(this,"loadrecyclerview me pahoch gaye!!!", Toast.LENGTH_SHORT).show();

        StringRequest request = new StringRequest(Request.Method.POST,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();

                        try {

                            JSONObject jsonObject=new JSONObject(s);
                            JSONArray array=jsonObject.getJSONArray("userInfo");
                            for(int i=0;i<array.length();i++){
                                JSONObject o=array.getJSONObject(i);
                                //Toast.makeText(ProfileActivity.this,o.getString("name") , Toast.LENGTH_SHORT).show();
                                UserInfo item=new UserInfo(o.getString("name"),
                                        o.getString("email"),
                                        o.getString("phone"),
                                        o.getString("groupname"));
                                userInfos.add(item);
                            }
                            Toast.makeText(ProfileActivity.this,array.length()+" Users detected!!!", Toast.LENGTH_SHORT).show();
                            adapter=new MyAdapter(userInfos,getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });

        //RequestQueue requestQueue= Volley.newRequestQueue(this);
        //requestQueue.add(stringRequest);

        RequestHandler.getInstance(this).addToRequestQueue(request);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       switch(item.getItemId()){
           case R.id.menuLogout:
               SharedPrefManager.getInstance(this).logout();
           finish();
           startActivity(new Intent(this,LoginActivity.class));
           break;
       }
       return true;
    }
}
