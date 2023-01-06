package com.example.githubusers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class ProfileActivity extends AppCompatActivity {

    TextView userNameText, profileNameText, followerCountText, followingCountText, descriptionText;
    ImageView profileImg;
    Button followerBtn, followingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userNameText = findViewById(R.id.userNameText);
        profileNameText = findViewById(R.id.profileName);
        followerCountText = findViewById(R.id.followerCount);
        followingCountText = findViewById(R.id.followingCount);
        profileImg = findViewById(R.id.profileImg);
        descriptionText = findViewById(R.id.profileDescription);
        followerBtn = findViewById(R.id.followerBtn);
        followingBtn = findViewById(R.id.followingBtn);

        Intent intent = getIntent();

        Bundle bundle = getIntent().getExtras();

        String name = bundle.getString("NAME");
        String userName = bundle.getString("USERNAME");
        String imageURL = bundle.getString("IMAGEURL");
        Integer following = bundle.getInt("FOLLOWING");
        Integer follower = bundle.getInt("FOLLOWER");
        String twitter = bundle.getString("TWITTER");
        Integer publicRepos = bundle.getInt("PUBLICREPOS");
//        String description = intent.getStringExtra("");

        userNameText.setText(userName);
        profileNameText.setText(name);
        followingCountText.setText(following.toString());
        followerCountText.setText(follower.toString());
        descriptionText.setText("Twitter name: " + twitter + " and the public repos in total is " + publicRepos.toString() + ".");

        Glide.with(this).load(imageURL).into(profileImg);

//        Toast.makeText(ProfileActivity.this, userName, Toast.LENGTH_SHORT).show();
        followerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (followerCountText.getText().toString() == "0") {
                    Toast.makeText(ProfileActivity.this, "User has no followers.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent followIntent = new Intent(ProfileActivity.this, FollowActivity.class);
                    followIntent.putExtra("followerURL", "followers");
                    followIntent.putExtra("followingURL", "");
                    followIntent.putExtra("newusername", userName);

                    startActivity(followIntent);
                }
            }
        });

        followingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (followingCountText.getText().toString() == "0") {
                    Toast.makeText(ProfileActivity.this, "User is not following currently.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent followIntent = new Intent(ProfileActivity.this, FollowActivity.class);
                    followIntent.putExtra("followingURL", "following");
                    followIntent.putExtra("followerURL", "");
                    followIntent.putExtra("newusername", userName);
                    startActivity(followIntent);
                }
            }
        });

    }
}