package edu.ggc.itec.nguyen.grizzlyproject;

public class Student implements Comparable<Student> {

    private String name;
    private Boolean hidden;

    public Student(String name, Boolean hidden) {
        this.name = name;
        this.hidden = hidden;
    }

    public String getName() {
        return name;
    }

    public Boolean isHidden() {
        return hidden;
    }

    @Override
    public int compareTo(Student other) {
        return this.name.compareTo(other.name);
    }

    public void toggleHidden() {
        hidden = !hidden;
    }
}
