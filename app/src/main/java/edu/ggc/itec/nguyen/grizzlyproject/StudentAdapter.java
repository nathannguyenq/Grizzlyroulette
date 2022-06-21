package edu.ggc.itec.nguyen.grizzlyproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentAdapter extends ArrayAdapter<Student> {

    public StudentAdapter(Context context, ArrayList<Student> students) {
        super(context, 0, students);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Student student = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_list_item, parent, false);
        }
        // Lookup view for data population
        TextView tvName = convertView.findViewById(R.id.tvName);
        ImageView ivHidden = convertView.findViewById(R.id.ivHidden);
        // Populate the data into the template view using the data object
        tvName.setText(student.getName());
        // Return the completed view to render on screen
        if (student.isHidden()) {
            ivHidden.setImageResource(R.drawable.eye_off);
            ivHidden.setVisibility(View.VISIBLE);
        } else {
            ivHidden.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

}
