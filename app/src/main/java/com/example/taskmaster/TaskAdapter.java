package com.example.taskmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onTaskCompletedClick(int position, boolean isChecked);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.tvTaskTitle.setText(task.getTitle());
        holder.checkBoxTask.setChecked(task.isCompleted());
        holder.tvTaskDueDate.setText("Fecha límite: " + task.getDueDate()); // Establece la fecha límite

        int priorityIcon = R.drawable.ic_priority_low;
        switch (task.getPriority()) {
            case 1:
                priorityIcon = R.drawable.ic_priority_medium;
                break;
            case 2:
                priorityIcon = R.drawable.ic_priority_high;
                break;
        }
        holder.ivPriority.setImageResource(priorityIcon);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTaskTitle;
        public CheckBox checkBoxTask;
        public ImageView ivPriority;
        public TextView tvTaskDueDate;

        public TaskViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            checkBoxTask = itemView.findViewById(R.id.checkBoxTask);
            ivPriority = itemView.findViewById(R.id.ivPriority);
            tvTaskDueDate = itemView.findViewById(R.id.tvTaskDueDate);

            checkBoxTask.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onTaskCompletedClick(position, ((CheckBox) v).isChecked());
                    }
                }
            });
        }
    }
}