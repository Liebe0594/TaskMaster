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
    private OnTaskClickListener listener;

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
        void onTaskCompleted(Task task, boolean isCompleted);
        void onTaskDelete(Task task);
    }

    public TaskAdapter(List<Task> taskList, OnTaskClickListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.tvTaskTitle.setText(task.getTitle());
        holder.tvTaskDueDate.setText("Fecha límite: " + task.getDueDate());

        holder.checkBoxTask.setOnCheckedChangeListener(null);
        holder.checkBoxTask.setChecked(task.isCompleted());

        holder.checkBoxTask.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (listener != null) {
                listener.onTaskCompleted(task, isChecked);
            }
        });

        int priorityIcon;
        // Aquí se corrige el mapeo de la prioridad a los iconos
        switch (task.getPriority()) {
            case 0:
                priorityIcon = R.drawable.ic_priority_low;
                break;
            case 1:
                priorityIcon = R.drawable.ic_priority_medium;
                break;
            case 2:
                priorityIcon = R.drawable.ic_priority_high;
                break;
            default:
                priorityIcon = R.drawable.ic_priority_low;
                break;
        }
        holder.ivPriority.setImageResource(priorityIcon);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTaskClick(task);
            }
        });

        holder.ivDeleteTask.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTaskDelete(task);
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvTaskTitle;
        TextView tvTaskDueDate;
        CheckBox checkBoxTask;
        ImageView ivPriority;
        ImageView ivDeleteTask;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            tvTaskDueDate = itemView.findViewById(R.id.tvTaskDueDate);
            checkBoxTask = itemView.findViewById(R.id.checkBoxTask);
            ivPriority = itemView.findViewById(R.id.ivPriority);
            ivDeleteTask = itemView.findViewById(R.id.ivDeleteTask);
        }
    }
}