package com.example.backgroundtasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends AppCompatActivity {
private TextView txtView;
private TextView txtName,txtEmail,txtGender,txtStatus;
private Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Intent intent = getIntent();
        //get ID
       int id  = intent.getIntExtra("id",0);
        txtView = findViewById(R.id.txtId);
        txtView.setText("ID" + id);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtGender = findViewById(R.id.txtGender);
        txtStatus = findViewById(R.id.txtStatus);

        // Call API
        ApiClient.getAPI().getUsersByID(id).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
               User user = response.body();
                txtName.setText("Name: " + user.name);
                txtEmail.setText("Email: " + user.email);
                txtGender.setText("Gender: " + user.gender);
                txtStatus.setText("Status: " + user.status);
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
               Log.e("ERROR: ",t.getMessage());
            }
        });
        btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiClient.getAPI().deleteUser(id).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(UserDetailActivity.this, "User Deleted successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });
    }






}