package com.example.githubusers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.util.List;

public class FollowAdapter extends RecyclerView.Adapter<FollowAdapter.FollowHolder> {

    private Context context;
    private List<Follow> followList;

    public FollowAdapter(Context context, List<Follow> follows){
        this.context = context;
        followList = follows;
    }

    @NonNull
    @Override
    public FollowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new FollowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowHolder holder, int position) {

        Follow follow = followList.get(position);
        holder.userName.setText(follow.getUserName());

        Glide.with(context).load(follow.getImageURL()).into(holder.imageView);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, ProfileActivity.class);

                String url = "https://api.github.com/users/" + follow.getUserName();

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

                            Intent intent = new Intent(context, ProfileActivity.class);

                            Bundle bundle = new Bundle();

                            bundle.putString("USERNAME", userName);
                            bundle.putString("NAME", name);
                            bundle.putInt("FOLLOWER", followers);
                            bundle.putInt("FOLLOWING", following);
                            bundle.putString("IMAGEURL", imageUrl);
                            bundle.putString("TWITTER", twitter);
                            bundle.putInt("PUBLICREPOS", publicrepos);

                            intent.putExtras(bundle);
                            context.startActivity(intent);

                        } catch (Exception e){

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                Volley.newRequestQueue(context).add(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return followList.size();
    }

    public class FollowHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView userName;
        LinearLayout linearLayout;

        public FollowHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.profileImg);
            userName = itemView.findViewById(R.id.userName);
            linearLayout = itemView.findViewById(R.id.mainlayout);
        }
    }
}
