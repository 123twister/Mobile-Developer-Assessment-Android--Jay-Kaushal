package com.example.githubusers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.TokenWatcher;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FollowActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<Follow> followList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        followList = new ArrayList<>();

        fetchFollow();
    }

    private void fetchFollow()
    {
        Intent intent = getIntent();
        String follwerURL = intent.getStringExtra("followerURL");
        String followingURL = intent.getStringExtra("followingURL");
        String userName = intent.getStringExtra("newusername");

        String url = "https://api.github.com/users/" + userName + "/" + follwerURL + followingURL;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++)
                {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String userName = jsonObject.getString("login");
                        String imageURL = jsonObject.getString("avatar_url");


                        Follow follow = new Follow(imageURL, userName);
                        followList.add(follow);

                    } catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                    FollowAdapter followAdapter = new FollowAdapter(FollowActivity.this, followList);
                    recyclerView.setAdapter(followAdapter);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FollowActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}