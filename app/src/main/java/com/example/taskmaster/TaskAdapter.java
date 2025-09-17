package com.example.taskmaster;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDeleteClick(int position);
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
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.tvTaskTitle.setText(task.getTitle());
        holder.cbTaskCompleted.setChecked(task.isCompleted());

        // Adaptador para el Spinner de prioridades
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                holder.itemView.getContext(),
                R.array.priorities_array,
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spPriority.setAdapter(spinnerAdapter);
        holder.spPriority.setSelection(task.getPriority());

        // Asignar el listener al botón de eliminar
        if (listener != null) {
            holder.btnDelete.setOnClickListener(v -> {
                listener.onDeleteClick(holder.getAdapterPosition());
            });
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTaskTitle;
        public CheckBox cbTaskCompleted;
        public Spinner spPriority;
        public ImageButton btnDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskTitle = itemView.findViewById(R.id.tvTaskTitle);
            cbTaskCompleted = itemView.findViewById(R.id.cbTaskCompleted);
            spPriority = itemView.findViewById(R.id.spPriority);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}