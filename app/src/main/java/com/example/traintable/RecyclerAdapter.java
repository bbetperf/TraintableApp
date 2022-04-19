package com.example.traintable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE = 1;
    private final List<Object> listRecyclerItem;

    public RecyclerAdapter(Context context, List<Object> listRecyclerItem) {
        this.listRecyclerItem = listRecyclerItem;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView timeStart;
        private final TextView timeEnd;
        private final TextView timeDuration;
        private final TextView stationStart;
        private final TextView stationEnd;
        private final TextView price;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            timeStart = itemView.findViewById(R.id.timeStart);
            timeEnd = itemView.findViewById(R.id.timeEnd);
            timeDuration = itemView.findViewById(R.id.timeDuration);
            stationStart = itemView.findViewById(R.id.stationStart);
            stationEnd = itemView.findViewById(R.id.stationEnd);
            price = itemView.findViewById(R.id.price);

        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.card, viewGroup, false);
        return new ItemViewHolder((layoutView));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        Timetable timetable = (Timetable) listRecyclerItem.get(i);
        itemViewHolder.timeStart.setText(timetable.getTimeStart());
        itemViewHolder.timeEnd.setText(timetable.getTimeEnd());
        itemViewHolder.timeDuration.setText(timetable.getTimeDuration());
        itemViewHolder.stationStart.setText(timetable.getStationStart());
        itemViewHolder.stationEnd.setText(timetable.getStationEnd());
        itemViewHolder.price.setText(timetable.getPrice());
    }

    @Override
    public int getItemCount() {
        return listRecyclerItem.size();
    }
}