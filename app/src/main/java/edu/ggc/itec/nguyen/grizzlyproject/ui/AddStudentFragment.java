package edu.ggc.itec.nguyen.grizzlyproject.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.ggc.itec.nguyen.grizzlyproject.R;
import edu.ggc.itec.nguyen.grizzlyproject.Student;
import edu.ggc.itec.nguyen.grizzlyproject.StudentsViewModel;

public class AddStudentFragment extends Fragment {

    private StudentsViewModel viewModel;

    public AddStudentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_student, container, false);

        EditText input = view.findViewById(R.id.etInput);
        Button addStudent = view.findViewById(R.id.addButton);

        viewModel = new ViewModelProvider(requireActivity()).get(StudentsViewModel.class);

        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = input.getText().toString();
                viewModel.getStudents().add(new Student(name, false));
            }
        });

        return view;

    }
}