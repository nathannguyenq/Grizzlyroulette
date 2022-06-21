package edu.ggc.itec.nguyen.grizzlyproject.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import android.widget.TextView;


import java.util.ArrayList;

import edu.ggc.itec.nguyen.grizzlyproject.R;
import edu.ggc.itec.nguyen.grizzlyproject.Student;
import edu.ggc.itec.nguyen.grizzlyproject.StudentAdapter;
import edu.ggc.itec.nguyen.grizzlyproject.StudentsViewModel;

public class HomeFragment extends Fragment {

    private ListView lvStudents;
    private TextView tvResult;

    private StudentsViewModel viewModel;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        lvStudents = view.findViewById(R.id.lvStudents);
        tvResult = view.findViewById(R.id.tvPicked);

        viewModel = new ViewModelProvider(requireActivity()).get(StudentsViewModel.class);
        ArrayList<Student> s = viewModel.getStudents();

        ArrayAdapter<Student> studentsAdapter = new StudentAdapter(view.getContext(), s);
        lvStudents.setAdapter(studentsAdapter);

        viewModel.getMutableData().observe(getViewLifecycleOwner(), item -> {
            // Perform an action with the latest item data
            studentsAdapter.notifyDataSetChanged();
        });

        viewModel.getMutableLastPicked().observe(getViewLifecycleOwner(), item -> {
            tvResult.setText(item + "");
        });

        lvStudents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                viewModel.toggleHidden(position);
                studentsAdapter.notifyDataSetChanged();
            }
        });

        lvStudents.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                studentsAdapter.remove(viewModel.getStudents().get(position));
                studentsAdapter.notifyDataSetChanged();
                return false;
            }

        });

        return view;

    }

}