package com.example.mysqlconn;

import androidx.appcompat.app.AppCompatActivity;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button bt1,bt2;
    TextView tv1;
    EditText edt1,edt2;
    RequestQueue requestQueue ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1= findViewById(R.id.button);
        bt2= findViewById(R.id.button2);
        tv1= findViewById(R.id.tv);
        edt1= findViewById(R.id.edt);
        edt2=findViewById(R.id.edt2);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv1.setText("");

                String showUrl = "http://192.168.0.12/showUsers.php"; //your hosting adress or xampp localadres
                requestQueue = Volley.newRequestQueue(getApplicationContext());


                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        showUrl, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(response.toString());
                        try {

                            URL url = new URL(showUrl);
                            HttpURLConnection http = (HttpURLConnection) url.openConnection();
                            http.setRequestMethod("POST");
                            JSONArray jsonArray = response.getJSONArray("showUser"); //showUser is return by php codes. The parameter(sql) is defined in the php code.
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String id= jsonObject.getString("id");      // MySql datebase kolums names
                                String name= jsonObject.getString("name");
                                String lastname= jsonObject.getString("lastname");
                                tv1.append(id+"."+"Name :"+name+"Lastname :"+lastname+"\n");


                            }


                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.append(error.getMessage());

                    }
                });
                requestQueue.add(jsonObjectRequest);


            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                requestQueue = Volley.newRequestQueue(getApplicationContext());
                String insertUrl = "http://192.168.0.12/insertDb.php"; //your hosting adress or xampp localadres

                StringRequest update = new StringRequest(Request.Method.POST,insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        System.out.println(response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parameters  = new HashMap<String, String>();
                        String sql="INSERT INTO `users` (`id`, `name`, `lastname`) VALUES (NULL, '"+edt1.getText().toString()+"', '"+edt2.getText().toString()+"');";

                        parameters.put("sql", sql); // Php codes waiting sql parameters, The parameter(sql) is defined in the php code.  


                        return parameters;
                    }
                };
                requestQueue.add(update);




            }
        });


    }
}
