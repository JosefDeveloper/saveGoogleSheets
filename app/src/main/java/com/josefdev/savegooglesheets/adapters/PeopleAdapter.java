package com.josefdev.savegooglesheets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.josefdev.savegooglesheets.R;
import com.josefdev.savegooglesheets.models.People;

import java.util.List;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder> {
    Context context;
    List<People> peopleList;

    public PeopleAdapter(Context context, List<People> peopleList) {
        this.context = context;
        this.peopleList = peopleList;
    }

    @NonNull
    @Override
    public PeopleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PeopleViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_person, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleViewHolder holder, int position) {
        People pos = peopleList.get(holder.getAdapterPosition());

        String full_name = pos.getName() + " " + pos.getSurname();
        holder.tId.setText(pos.getId());
        holder.tFullName.setText(full_name);
        holder.tAge.setText(pos.getAge());

    }

    @Override
    public int getItemCount() {
        return peopleList.size();
    }

    public static class PeopleViewHolder extends RecyclerView.ViewHolder {
        TextView tId, tFullName, tAge;

        public PeopleViewHolder(@NonNull View itemView) {
            super(itemView);
            tId = itemView.findViewById(R.id.tvId);
            tFullName = itemView.findViewById(R.id.tvFullName);
            tAge = itemView.findViewById(R.id.tvAge);
        }
    }
}
