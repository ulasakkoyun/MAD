package com.ulasakkoyun.week6;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull; // It's good practice to add @NonNull
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ulasakkoyun.week6.placeholder.PlaceholderContent;
import com.ulasakkoyun.week6.placeholder.PlaceholderContent.Movie; // Import the Movie class

/**
 * A fragment representing a list of Items.
 */
public class MovieFragment extends Fragment {

    // --- FIX #1: THE INTERFACE IS NOW DEFINED INSIDE THE FRAGMENT CLASS ---
    public interface OnMovieSelected {
        void onMovieSelected(Movie movie);
    }

    private OnMovieSelected listener;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieFragment() {
    }

    @SuppressWarnings("unused")
    public static MovieFragment newInstance(int columnCount) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            // Pass the listener to the adapter
            recyclerView.setAdapter(new MyMovieRecyclerViewAdapter(PlaceholderContent.ITEMS, listener));
        }
        return view;
    }

    // --- FIX #2: ONATTACH AND ONDETACH ARE NOW INSIDE THE FRAGMENT CLASS ---
    // Also, use the @NonNull annotation for better code safety.
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnMovieSelected) {
            listener = (OnMovieSelected) context;
        } else {
            // Throw an error to ensure the hosting activity implements the interface.
            throw new RuntimeException(context.toString()
                    + " must implement OnMovieSelected");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
} // <-- This is the final closing brace for the MovieFragment class
