package com.example.zika.contactapp;

import android.content.Context;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.List;

public class ContactsRecyclerAdapter extends RecyclerView.Adapter<ContactsRecyclerAdapter.ContactViewHolder>{

    private List<ContactPeople> mList;
    private Context mContext;


    public ContactsRecyclerAdapter(List<ContactPeople> list, Context mContext){
        this.mList = list;
        this.mContext = mContext;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.simple_recyclerview_item, null);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);
        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, final int position) {
        ContactPeople item = mList.get(position);
        holder.txtDisplay.setText(item.name);
        holder.txtDisplay.setContentDescription(item.name);

        holder.mLayout.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, SingleContactActivity.class);
                intent.putExtra("item_position", position);

                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public static class ContactViewHolder extends RecyclerView.ViewHolder{

        View mLayout;
        TextView txtDisplay;

        public ContactViewHolder(View itemView) {
            super(itemView);

            mLayout = itemView;
            txtDisplay = itemView.findViewById(R.id.nameInList);
        }
    }
}