package com.example.eventmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ItemViewHolder>{

    Context context;
    QuerySnapshot list;
    private LayoutInflater mInflater;

    public EventsAdapter(QuerySnapshot list,
                         Context context)
    {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.event_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        DocumentSnapshot snapshot = list.getDocuments().get(position);
        holder.getDate.setText((CharSequence) snapshot.get("date"));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView getDate;
        TextView getVenue;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            getDate = itemView.findViewById(R.id.showDate);
            getVenue = itemView.findViewById(R.id.showVenue);
        }
    }
}

