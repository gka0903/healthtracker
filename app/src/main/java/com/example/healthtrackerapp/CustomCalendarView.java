package com.example.healthtrackerapp;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

public class CustomCalendarView extends RecyclerView {
    private CalendarAdapter adapter;
    private HashSet<String> eventDays;
    private OnDateChangeListener listener;

    public CustomCalendarView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public CustomCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomCalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        eventDays = new HashSet<>();
        adapter = new CalendarAdapter(context, eventDays);
        setLayoutManager(new GridLayoutManager(context, 7)); // 7 columns for 7 days of the week
        setAdapter(adapter);
    }

    public void setEventDays(HashSet<String> eventDays) {
        this.eventDays = eventDays;
        adapter.setEventDays(eventDays);
        adapter.notifyDataSetChanged();
    }

    public void setOnDateChangeListener(OnDateChangeListener listener) {
        this.listener = listener;
        adapter.setOnDateChangeListener(listener);
    }

    public interface OnDateChangeListener {
        void onSelectedDayChange(CustomCalendarView view, int year, int month, int dayOfMonth);
    }

    class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {
        private Context context;
        private ArrayList<String> days;
        private HashSet<String> eventDays;
        private OnDateChangeListener listener;

        CalendarAdapter(Context context, HashSet<String> eventDays) {
            this.context = context;
            this.eventDays = eventDays;
            days = new ArrayList<>();
            initCalendar();
        }

        private void initCalendar() {
            days.clear();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            int monthStartCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = 0; i < monthStartCell; i++) {
                days.add("");
            }
            for (int i = 1; i <= maxDays; i++) {
                days.add(String.valueOf(i));
            }
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.calendar_day, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String day = days.get(position);
            holder.tvDay.setText(day);
            if (!day.isEmpty()) {
                String date = getCurrentMonthYear() + "-" + (day.length() == 1 ? "0" + day : day);
                if (eventDays.contains(date)) {
                    holder.tvDay.setBackgroundColor(Color.RED);
                } else {
                    holder.tvDay.setBackgroundColor(Color.TRANSPARENT);
                }
                holder.itemView.setOnClickListener(v -> {
                    if (listener != null) {
                        int dayInt = Integer.parseInt(day);
                        Calendar calendar = Calendar.getInstance();
                        listener.onSelectedDayChange(CustomCalendarView.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), dayInt);
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return days.size();
        }

        private String getCurrentMonthYear() {
            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH) + 1;
            int year = calendar.get(Calendar.YEAR);
            return year + "-" + (month < 10 ? "0" + month : month);
        }

        void setEventDays(HashSet<String> eventDays) {
            this.eventDays = eventDays;
        }

        void setOnDateChangeListener(OnDateChangeListener listener) {
            this.listener = listener;
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvDay;

            ViewHolder(View itemView) {
                super(itemView);
                tvDay = itemView.findViewById(R.id.tvDay);
            }
        }
    }
}
