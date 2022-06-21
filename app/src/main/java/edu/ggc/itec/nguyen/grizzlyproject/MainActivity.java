package edu.ggc.itec.nguyen.grizzlyproject;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import edu.ggc.itec.nguyen.grizzlyproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "GrizzlyRoulette";
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private ImageView bBuilding;

    private StudentsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(StudentsViewModel.class);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fabRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.pickRandomStudent();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_add_student, R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        bBuilding = findViewById(R.id.ivBBuilding);
        bBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.addStudent(new Student("Navaz", false));
                viewModel.addStudent(new Student("Marina", false));
                viewModel.addStudent(new Student("Sterlin", false));
                viewModel.addStudent(new Student("Nathan", false));
                viewModel.addStudent(new Student("Jessie", false));
                viewModel.addStudent(new Student("Ti", false));
                viewModel.addStudent(new Student("Mark", false));

                viewModel.getMutableLastPicked().setValue("Pick a random student");
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_sort) {
            viewModel.sort();
        } else if (item.getItemId() == R.id.action_shuffle) {
            viewModel.shuffle();
        } else if (item.getItemId() == R.id.action_clear) {
            viewModel.clearStudents();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            FileInputStream in = openFileInput("students.txt");
            Scanner scanner = new Scanner(in);
            while (scanner.hasNext()) {
                String s = scanner.nextLine();
                String[] tokens = s.split(",");
                viewModel.addStudent(new Student(tokens[0], Boolean.parseBoolean(tokens[1])));
                Log.v(TAG, "line = " + s);
            }
            scanner.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            FileOutputStream out = openFileOutput("students.txt", Context.MODE_PRIVATE);
            PrintWriter writer = new PrintWriter(out);

            for (Student student : viewModel.getStudents()) {
                writer.println(student.getName() + "," + student.isHidden());
            }
            writer.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}