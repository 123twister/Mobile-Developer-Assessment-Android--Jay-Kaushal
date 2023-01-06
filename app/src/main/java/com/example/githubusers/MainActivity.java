package com.example.githubusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText searchText;
    Button searchBtn;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchText = findViewById(R.id.searchEdit);
        searchBtn = findViewById(R.id.searchBtn);

//        searchBtn.setBackgroundColor(Color.GREEN);





        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchName = searchText.getText().toString();
                url = "https://api.github.com/users/" + searchName;

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String name = response.getString("name");
                            String userName = response.getString("login");
                            String imageUrl = response.getString("avatar_url");
                            Integer followers = response.getInt("followers");
                            Integer following = response.getInt("following");
                            String twitter = response.getString("twitter_username");
                            Integer publicrepos = response.getInt("public_repos");

                            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);

                            Bundle bundle = new Bundle();

                            bundle.putString("USERNAME", userName);
                            bundle.putString("NAME", name);
                            bundle.putInt("FOLLOWER", followers);
                            bundle.putInt("FOLLOWING", following);
                            bundle.putString("IMAGEURL", imageUrl);
                            bundle.putString("TWITTER", twitter);
                            bundle.putInt("PUBLICREPOS", publicrepos);

                            intent.putExtras(bundle);
                            startActivity(intent);

                        } catch (Exception e){

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                Volley.newRequestQueue(MainActivity.this).add(request);
            }
        });



    }
}