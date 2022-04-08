package com.example.backgroundtasks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    private final ArrayList<User> mUserList;
    private Context context;

    public UserListAdapter(Context context, ArrayList<User> userList) {
        this.context = context;
        this.mUserList = userList;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View mItemView = mInflater.inflate(R.layout.userlist_item, parent, false);
        return new UserViewHolder(mItemView, this);
    }
    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        String mCurrent = mUserList.get(position).name;
        holder.tvUserName.setText(mCurrent);
        holder.tvUserName.setOnClickListener(view ->{
            Intent intent = new Intent(context,UserDetailActivity.class);
            // GET NAME
            //intent.putExtra("name",mUserList.get(position).name);
            //GET ID
            intent.putExtra("id",mUserList.get(position).id);
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount(){
        return mUserList.size();
    }
    public class UserViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvUserName;
        final UserListAdapter mAdapter;

        public UserViewHolder(View itemView, UserListAdapter adapter) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.userName);
            this.mAdapter = adapter;
        }
    }
}
