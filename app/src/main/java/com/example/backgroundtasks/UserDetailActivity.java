package com.example.backgroundtasks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailActivity extends AppCompatActivity {
private TextView txtView;
private TextView txtName,txtEmail,txtGender,txtStatus;
private Button btnDelete,btnUpdate,btnSave;
private EditText editName,editEmail,editGender,editStatus;

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

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editGender = findViewById(R.id.editGender);
        editStatus = findViewById(R.id.editStatus);


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
        //DELETE USER
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
                Intent intent1 = new Intent(UserDetailActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });

        //UPDATE USER
        if(id != 0){
            String name = intent.getStringExtra("Name");
            String email = intent.getStringExtra("Email");
            String gender = intent.getStringExtra("Gender");
            String status = intent.getStringExtra("Status");
            User user = new User(id,name,email,gender,status);
            updateUser(user);
        }
        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            User user = getUser();
            @Override
            public void onClick(View view) {
                ApiClient.getAPI().updateUser(user.id,user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                       if(response.body() != null){
                           updateUser(response.body());
                       }
                        Toast.makeText(UserDetailActivity.this, "Updated User successful", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(UserDetailActivity.this, "Updated User Failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        // POST
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            User user = getUser();
            @Override
            public void onClick(View view) {
                ApiClient.getAPI().addUser(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.body() != null){
                            updateUser(response.body());
                        }
                        if(response.isSuccessful()){
                            Toast.makeText(UserDetailActivity.this, "Created User successful", Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(UserDetailActivity.this, "Created User Failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
    private void updateUser(User user) {
        txtView.setText(String.valueOf(user.id));
        editName.setText(user.name);
        editEmail.setText(user.email);
        editGender.setText(user.gender);
        editStatus.setText(user.status);
    }
    private User getUser() {
        int id = Integer.parseInt(txtView.getText().toString());
        String name = editName.getText().toString();
        String email = editEmail.getText().toString();
        String gender = editGender.getText().toString();
        String status = editStatus.getText().toString();
        return new User(id, name, email, gender, status);
    }
}