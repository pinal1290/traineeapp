package com.example.softices.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softices.model.ContactModel;
import com.example.softices.R;

import java.util.List;

public class AllContactsAdapter extends RecyclerView.Adapter<AllContactsAdapter.ContactViewHolder> {
    private List<ContactModel> contactVOList;
    private Context mContext;

    public AllContactsAdapter(List<ContactModel> contactVOList, Context mContext) {
        this.contactVOList = contactVOList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.single_contact, null);
        return new ContactViewHolder(view);
    }

    /**
     * onBindViewHolder are fetch all contact in connected devices
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        ContactModel contactVO = contactVOList.get(position);
        holder.tvContactName.setText(contactVO.getContactName());
        holder.tvPhoneNumber.setText(contactVO.getContactNumber());
    }

    @Override
    public int getItemCount() {
        return contactVOList.size();
    }

    /**
     * ContactViewHolder dispaly all contact in recyclerview
     */
    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView tvContactName;
        TextView tvPhoneNumber;

        @SuppressLint("CutPasteId")
        public ContactViewHolder(View itemView) {
            super(itemView);
            tvContactName = itemView.findViewById(R.id.txt_name);
            tvPhoneNumber = itemView.findViewById(R.id.txt_number);
        }
    }
}