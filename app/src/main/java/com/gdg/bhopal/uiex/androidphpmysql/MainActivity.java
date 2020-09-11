package com.gdg.bhopal.uiex.androidphpmysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

//***DOUTS***
///*** what is Context???

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextUsername, editTextPassword,editTextName, editTextEmail,editTextPhone,editTextGroupname;
    private Button buttonRegister;
    private ProgressDialog progressDialog; // ****as we are using network operation so we use it to show loading status
    private TextView textViewLogin;


    @Override   //***@Override is a Java annotation.
    // It tells the compiler that the following method overrides a method of its superclass.
    // For instance, say you implement a Person class. ...
    // The equals method is already defined in Person's superclass Object
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(this,ProfileActivity.class));
            return;
        }

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextGroupname = (EditText) findViewById(R.id.editTextGroupname);

        textViewLogin=(TextView)findViewById(R.id.textViewLogin);

        buttonRegister =(Button)findViewById(R.id.buttonRegister);
        progressDialog =new ProgressDialog(this);
        buttonRegister.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);
    }
    private void registerUser(){
        final String email = editTextEmail.getText().toString().trim();//*** trim- faltu ki space hatata he
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String name = editTextName.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();
        final String groupname = editTextGroupname.getText().toString().trim();

        progressDialog.setMessage("Registering user....");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);//*** getApplicationContext()-> return -
                            // -the context of the process of all activity running inside it
                            Toast.makeText(getApplicationContext(),jsonObject.getString("message"),Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {//***response nahi jaa paya toh ye execute hoga or error andro studio wala dega
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "Cann't able to connect to Database\n"+error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }){//*** saare parameteres database me bhejne ke liye ye banaya he...
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                params.put("name",name);
                params.put("email",email);
                params.put("phone",phone);
                params.put("groupname",groupname);
                return params;
            }
        };

        //RequestQueue requestQueue = Volley.newRequestQueue(this);
        //requestQueue.add(stringRequest);
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }
    @Override
    public void onClick(View view)
    {
        if(view == buttonRegister)
                registerUser();
               // startActivity(new Intent(this,LoginActivity.class));
        if(view==textViewLogin)
            startActivity(new Intent(this,LoginActivity.class));
    }
}
