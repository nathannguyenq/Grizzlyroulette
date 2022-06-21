package edu.ggc.itec.nguyen.grizzlyproject;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class StudentsViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Student>> mutableData = new MutableLiveData<>();
    private final MutableLiveData<String> mutableLastPicked = new MutableLiveData<>();

    public StudentsViewModel() {
        setMutableData(new ArrayList<>());
    }

    public MutableLiveData<String> getMutableLastPicked() {
        return mutableLastPicked;
    }

    public void setMutableData(ArrayList<Student> list) {
        this.mutableData.setValue(list);
    }

    public void addStudent(Student student) {
        this.mutableData.getValue().add(student);
        setMutableData(getStudents());
    }

    public ArrayList<Student> getStudents() {
        return this.mutableData.getValue();
    }

    public MutableLiveData<ArrayList<Student>> getMutableData() {
        return mutableData;
    }

    public void sort() {
        Collections.sort(getStudents());
        setMutableData(getStudents()); // need to re-assert so that observers are notified
    }

    public void shuffle() {
        Collections.shuffle(getStudents());
        setMutableData(getStudents());
    }

    public void clearStudents() {
        getStudents().clear();
        setMutableData(getStudents());
        mutableLastPicked.setValue("");
    }

    public void pickRandomStudent()
    {
        if (getStudents().size() > 0) { //if the arraylist has students
            if (!checkAllHidden()) // and all the students aren't hidden
            {
                Student lastPicked;
                Random random = new Random(); //creates a random generator
                int pick = random.nextInt(getStudents().size()); // where a student is randomly chosen within the list size
                lastPicked = getStudents().get(pick); // instance of picked student is saved
                if (!lastPicked.isHidden()) {  //  if the picked student is visible
                    mutableLastPicked.setValue(lastPicked.getName()); // saves the name of the picked student which is then displayed
                    Log.v("picked", "picked student = " + lastPicked.getName());
                } else   //  if the picked student is hidden
                    pickRandomStudent(); // runs again until a non hidden student is selected
            } else // if all the students are hidden
                mutableLastPicked.setValue("All Students are Hidden"); //displays that all the students are hidden
        }
        else { //if there are no students
            mutableLastPicked.setValue("There are no students"); //displays that there are no students
        }

    }

    public boolean checkAllHidden()
    {
        for (int i = 0;i < getStudents().size();i++) // iterates through the list of students
            if (!getStudents().get(i).isHidden()) //if even a single student isn't hidden
                return false;
        return true; // if none of the students are visible, then all are hidden
    }

    public void toggleHidden(int position) {
        Student s = getStudents().get(position);
        s.toggleHidden();
    }
}