package com.ulasakkoyun.week6;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// FIX 1: Import the correct data model 'Movie', not 'PlaceholderItem'.
import com.ulasakkoyun.week6.placeholder.PlaceholderContent.Movie;
import com.ulasakkoyun.week6.databinding.FragmentMovieBinding;

import java.util.List;

public class MyMovieRecyclerViewAdapter extends RecyclerView.Adapter<MyMovieRecyclerViewAdapter.ViewHolder> {

    private final List<Movie> mValues;

    // FIX 2: Correctly declare the listener interface type. 'OnMovieSelected' starts with a capital 'O'.
    private final MovieFragment.OnMovieSelected mListener;

    // FIX 3: Correct the constructor signature. It should accept a List and a listener.
    public MyMovieRecyclerViewAdapter(List<Movie> items, MovieFragment.OnMovieSelected listener) {
        mValues = items;
        mListener = listener; // Assign the passed listener to the member variable.
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        // FIX 4: Bind data from the 'Movie' object.
        holder.mItem = mValues.get(position);
        // Assuming your Movie class has getYear() and getName() methods.
        holder.mIdView.setText(String.valueOf(holder.mItem.getYear()));
        holder.mContentView.setText(holder.mItem.getName());

        // FIX 5: Set an OnClickListener to handle item clicks and notify the listener.
        holder.itemView.setOnClickListener(v -> {
            if (null != mListener) {
                // Call the onMovieSelected method defined in the fragment/activity.
                mListener.onMovieSelected(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        // FIX 6: The item type in the ViewHolder must also be 'Movie'.
        public Movie mItem;

        public ViewHolder(FragmentMovieBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @NonNull
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
