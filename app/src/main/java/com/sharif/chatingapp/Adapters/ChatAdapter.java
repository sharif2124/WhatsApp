package com.sharif.chatingapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.sharif.chatingapp.Models.MessageModel;
import com.sharif.chatingapp.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter{
 ArrayList<MessageModel> messageModels;
 Context context;
 String recId;
 int SENDER_VIEW_TYPE = 1;
 int RECIVER_VIEW_TYPE = 2;

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public ChatAdapter(ArrayList<MessageModel> messageModels, Context context, String receverId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = receverId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE){
            View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
            return new SenderViewHolder(view);
        }
    else {
            View view = LayoutInflater.from(context).inflate(R.layout.sample_reciver,parent,false);
            return new ReciverViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messageModels.get(position).getUid().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else {
             return RECIVER_VIEW_TYPE;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
           MessageModel messageModel =messageModels.get(position);
           holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
               @Override
               public boolean onLongClick(View v) {
                   new AlertDialog.Builder(context)
                           .setTitle("Delete")
                           .setMessage("Are you sure you want to delete this message")
                           .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                               @Override
                               public void onClick(DialogInterface dialog, int which) {
                                   FirebaseDatabase database = FirebaseDatabase.getInstance();
                                   String senderRoom = FirebaseAuth.getInstance().getUid() + recId;
                                   database.getReference().child("chats").child(senderRoom)
                                           .child(messageModel.getMessageId())
                                           .setValue(null);
                               }
                           }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                       }
                   }).show();


                   return false;
               }
           });

           if(holder.getClass()==SenderViewHolder.class){
               ((SenderViewHolder)holder).senderMsg.setText(messageModel.getMessage());

           }else
           {
               ((ReciverViewHolder)holder).reciverMsg.setText(messageModel.getMessage());
           }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    public class ReciverViewHolder extends RecyclerView.ViewHolder {

        TextView reciverMsg,reciverTime;
        public ReciverViewHolder(@NonNull View itemView) {
            super(itemView);
            reciverMsg = itemView.findViewById(R.id.recivertext);
            reciverTime = itemView.findViewById(R.id.recivertime);
        }
    }
    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView senderMsg, senderTime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            senderMsg = itemView.findViewById(R.id.sendertext);
            senderTime = itemView.findViewById(R.id.sendertime);
        }
    }
}
