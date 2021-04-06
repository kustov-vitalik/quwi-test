package com.example.quwitest.ui.projectlist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quwitest.R;
import com.example.quwitest.data.local.Project;
import com.example.quwitest.databinding.FragmentProjectsBinding;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProjectListRecyclerViewAdapter extends RecyclerView.Adapter<ProjectListRecyclerViewAdapter.ViewHolder> {

    private final List<Project> projects = new ArrayList<>();
    private final Transformation imageTransformation;
    private OnProjectClickListener onProjectClickListener;

    @Inject
    public ProjectListRecyclerViewAdapter(@NonNull Transformation transformation) {
        this.imageTransformation = Objects.requireNonNull(transformation);
    }

    public void setOnProjectClickListener(@NotNull OnProjectClickListener onProjectClickListener) {
        this.onProjectClickListener = Objects.requireNonNull(onProjectClickListener);
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        FragmentProjectsBinding binding = FragmentProjectsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding, this::onItemClick, imageTransformation);
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
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new ProjectListDiffCallback(projects, items));
        projects.clear();
        projects.addAll(items);
        diffResult.dispatchUpdatesTo(this);
    }

    public void onItemClick(int adapterPosition) {
        if (onProjectClickListener != null) {
            onProjectClickListener.onProjectClick(projects.get(adapterPosition));
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final FragmentProjectsBinding binding;
        private final Transformation transformation;

        public ViewHolder(FragmentProjectsBinding binding, OnItemClickListener onItemClickListener, Transformation transformation) {
            super(binding.getRoot());
            this.transformation = transformation;
            this.binding = binding;
            binding.getRoot().setOnClickListener(v -> onItemClickListener.onItemClick(getAdapterPosition()));
        }

        public void bind(Project project) {
            binding.content.setText(project.getName());
            Picasso.get().load(project.getLogoUrl())
                    .transform(transformation)
                    .placeholder(R.drawable.project_logo_placeholder)
                    .error(R.drawable.project_logo_placeholder)
                    .into(binding.itemLogo);
            binding.itemLogo.setVisibility(View.VISIBLE);
        }
    }

    public interface OnProjectClickListener {
        void onProjectClick(Project project);
    }

    public interface OnItemClickListener {
        void onItemClick(int adapterPosition);
    }

    private static class ProjectListDiffCallback extends DiffUtil.Callback {

        private final List<Project> oldProjectList;
        private final List<Project> newProjectList;

        public ProjectListDiffCallback(List<Project> oldProjectList, List<Project> newProjectList) {
            this.oldProjectList = oldProjectList;
            this.newProjectList = newProjectList;
        }

        @Override
        public int getOldListSize() {
            return oldProjectList.size();
        }

        @Override
        public int getNewListSize() {
            return newProjectList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldProjectList.get(oldItemPosition).getId() == newProjectList.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldProjectList.get(oldItemPosition).equals(newProjectList.get(newItemPosition));
        }
    }
}