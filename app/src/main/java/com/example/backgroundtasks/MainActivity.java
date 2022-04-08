package com.example.backgroundtasks;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
 private TextView mTextView;
 private Handler mHandler;
 private ProgressBar progressbar;
 private Button button;

    private RecyclerView mRecyclerView;
    private UserListAdapter mAdapter;
    ArrayList<User> mWordList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerview);
        mAdapter = new UserListAdapter(this, mWordList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ApiClient.getAPI().getAllUsers().enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                mWordList.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Log.e("ERROR: ",t.getMessage());
            }
        });
    }
//   OnClick
//    public void startTask(View view){
//        mTextView.setText(R.string.loading);
//        progressbar.setVisibility(View.VISIBLE);
//        button.setVisibility(View.INVISIBLE);

//        new Thread(()->{
//Call API
//            try {
//                URL url = new URL("https://gorest.co.in/public/v2/users");
//                HttpURLConnection httpsConnection = (HttpURLConnection) url.openConnection();
//                BufferedReader in = new BufferedReader(new InputStreamReader(httpsConnection.getInputStream()));
//                String inputLine;
//                StringBuffer response = new StringBuffer();
//                while ((inputLine = in.readLine()) != null){
//                    response.append(inputLine);
//                }
//                in.close();
//                mHandler.post(()->{
//                    button.setVisibility(View.VISIBLE);
//                    progressbar.setVisibility(View.INVISIBLE);
//                    mTextView.setText(response);
//                });
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//          }
//        ).start();

// getAllUsers

//    ApiClient.getAPI().getAllUsers().enqueue(new Callback<ArrayList<User>>() {
//        @Override
//        public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
//            ArrayList<User> userList = response.body();
//            mTextView.setText("Number of Users: " + userList.size());
//            progressbar.setVisibility(View.INVISIBLE);
//        }
//        @Override
//        public void onFailure(Call<ArrayList<User>> call, Throwable t) {
//            mTextView.setText("Error: " + t.getMessage());
//            progressbar.setVisibility(View.VISIBLE);
//        }
//    })

// Get User

//        ApiClient.getAPI().getUsersByID(3021).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                User user = response.body();
//                mTextView.setText(user.name);
//                progressbar.setVisibility(View.INVISIBLE);
//            }
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                mTextView.setText("Error:" + t.getMessage());
//            }
//        });

// Add user

//        User user = new User();
//        user.gender = "male";
//        user.name = "Nguyen Anh";
//        user.email="anhn@test123.com";
//        user.status = "active";
//        ApiClient.getAPI().addUser(user).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                User user1 = response.body();
//                mTextView.setText("UserID" + user1.id);
//            }
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//
//            }
//        });
//    }
}