package com.gdg.bhopal.uiex.androidphpmysql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<UserInfo> userInfos;
    private Context context;

    public MyAdapter(List<UserInfo> userInfos, Context context) {
        this.userInfos= userInfos;
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //this method will be called whenever our viewholder is created
        //that means when instance of the class ViewHolder (iss page ke end me he jo) is created
        //this method will call..

        //with the help of LayoutInflater we will get list_item.xml
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);


        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //this method called after onCreateViewHolder wil be called
        //this metod will bind the data to the ViewHolder...

        UserInfo userInfo=userInfos.get(position);
        holder.textViewName.setText(userInfo.getName());
        holder.textViewEmail.setText(userInfo.getEmail());
        holder.textViewPhone.setText(userInfo.getPhone());
        holder.textViewGroupname.setText(userInfo.getGroupname());

    }

    @Override
    public int getItemCount() {
        return userInfos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public TextView textViewEmail;
        public TextView textViewPhone;
        public TextView textViewGroupname;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName=(TextView)itemView.findViewById(R.id.textviewName);
            textViewEmail=(TextView)itemView.findViewById(R.id.textviewEmail);
            textViewPhone=(TextView)itemView.findViewById(R.id.textviewPhone);
            textViewGroupname=(TextView)itemView.findViewById(R.id.textviewGroupname);


        }
    }
}
