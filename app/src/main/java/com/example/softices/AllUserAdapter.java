package com.example.softices;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.ContactViewHolder> {
    private List<UserModel> userModelList;
    private Context mContext;

    AllUserAdapter(List<UserModel> userModelList, Context mContext) {
        this.userModelList = userModelList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(mContext).inflate(R.layout.user_record, null);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, final int position) {
        final UserModel userModel = userModelList.get(position);
        holder.txtFirstName.setText(userModel.getFirstname());
        holder.txtEmail.setText(userModel.getEmail());
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(mContext);
                String email = ProfileActivity.RetriveEmailFromShared(mContext);
                if (email.equalsIgnoreCase(userModel.getEmail())) {
                    Toast.makeText(mContext, "You can not delete login user", Toast.LENGTH_SHORT).show();
                } else {
                    databaseHelper.deleteuser(userModel);
                    Toast.makeText(view.getContext(), "Delete Succesfully....", Toast.LENGTH_SHORT).show();
                    userModelList.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override

    public int getItemCount() {
        return userModelList.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder {

        TextView txtFirstName;
        TextView txtEmail;
        Button btndelete;

        ContactViewHolder(View itemView) {
            super(itemView);
            txtFirstName = itemView.findViewById(R.id.txt_firstname);
            txtEmail = itemView.findViewById(R.id.txt_email);
            btndelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
