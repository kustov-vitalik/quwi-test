package com.example.quwitest.ui.projectlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.quwitest.R;
import com.example.quwitest.data.network.dto.Project;
import com.example.quwitest.databinding.FragmentProjectsBinding;
import com.example.quwitest.utils.CircleImageTransformation;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

public class ProjectListRecyclerViewAdapter extends RecyclerView.Adapter<ProjectListRecyclerViewAdapter.ViewHolder> {

    private final List<Project> projects = new ArrayList<>();
    private final CircleImageTransformation imageTransformation = new CircleImageTransformation(40, 8);

    @Setter
    private OnProjectClickListener onProjectClickListener;

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_projects, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bind(projects.get(position));
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }

    public void setItems(List<Project> items) {
        projects.clear();
        projects.addAll(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final FragmentProjectsBinding binding;

        public ViewHolder(View view) {
            super(view);
            binding = FragmentProjectsBinding.bind(view);
        }

        public void bind(Project project) {
            binding.content.setText(project.getName());
            Picasso.get().load(project.getLogoUrl())
                    .transform(imageTransformation)
                    .placeholder(R.drawable.project_logo_placeholder)
                    .error(R.drawable.project_logo_placeholder)
                    .into(binding.itemLogo);
            binding.itemLogo.setVisibility(View.VISIBLE);
            binding.getRoot().setOnClickListener(v -> {
                if (onProjectClickListener != null) {
                    onProjectClickListener.onProjectClick(project);
                }
            });
        }
    }

    public interface OnProjectClickListener {
        void onProjectClick(Project project);
    }
}