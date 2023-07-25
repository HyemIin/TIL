package org.example;

import java.util.ArrayList;

public class Student {

    private int studentID;
    private String studentName;
    ArrayList<Subject> subjectList = new ArrayList<>();

    public Student(int studentID, String studentName) {
        this.studentID = studentID;
        this.studentName = studentName;
    }

    public void addSubject(String subject,int score) {
        subjectList.add(new Subject(subject,score));
    }

    public void showStudentInfo() {
        int total = 0;

        for(Subject s : subjectList){
            total += s.getScorePoint();

            System.out.println("학생 " + studentName + "의 " + s.getName() + " 과목 성적은 " +
                    s.getScorePoint() + "입니다.");
        }

        System.out.println("학생 " + studentName + "의 총점은 " + total + " 입니다.");

    }
}
