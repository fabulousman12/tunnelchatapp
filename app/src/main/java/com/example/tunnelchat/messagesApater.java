package com.example.tunnelchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.example.tunnelchat.chatWin.senderImg;
import static com.example.tunnelchat.chatWin.reciverIImg;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
public class messagesApater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM_SEND = 1;
    private static final int ITEM_RECEIVE = 2;

    private Context context;
    private ArrayList<msgModelclass> messagesAdapterArrayList;

    public messagesApater(Context context, ArrayList<msgModelclass> messagesAdapterArrayList) {
        this.context = context;
        this.messagesAdapterArrayList = messagesAdapterArrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == ITEM_SEND) {
            View view = inflater.inflate(R.layout.sender_layout, parent, false);
            return new SenderViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.reciver_layout, parent, false);
            return new ReceiverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        msgModelclass message = messagesAdapterArrayList.get(position);

        if (holder instanceof SenderViewHolder) {
            SenderViewHolder viewHolder = (SenderViewHolder) holder;
            viewHolder.msgTxt.setText(message.getMessage());
            // Load sender image using Picasso with message.getSenderImage()
            Picasso.get().load(senderImg).into(viewHolder.circleImageView);
        } else if (holder instanceof ReceiverViewHolder) {
            ReceiverViewHolder viewHolder = (ReceiverViewHolder) holder;
            viewHolder.msgTxt.setText(message.getMessage());
            // Load receiver image using Picasso with message.getReceiverImage()
            Picasso.get().load(reciverIImg).into(viewHolder.circleImageView);
        }
    }

    @Override
    public int getItemCount() {
        return messagesAdapterArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        msgModelclass messages = messagesAdapterArrayList.get(position);
        if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(messages.getSenderid())) {
            return ITEM_SEND;
        } else {
            return ITEM_RECEIVE;
        }
    }

    static class SenderViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView msgTxt;

        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.profilerggg);
            msgTxt = itemView.findViewById(R.id.msgsendertyp);
        }
    }

    static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView msgTxt;

        public ReceiverViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.pro);
            msgTxt = itemView.findViewById(R.id.recivertextset);
        }
    }
}

