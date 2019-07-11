package com.javahly.ch6;

public class Teacher {
    private String teacherId;
    private String teacherName;
    private String sex;
    private int salary;
    public Teacher(){
    }
    public Teacher(String teacherId,String teacherName,String sex,int salary){
        this.teacherId=teacherId;
        this.teacherName=teacherName;
        this.sex=sex;
        this.salary=salary;
    }
    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
    public String toString() {
        return "Teacher{" +
                "teacherId='" + teacherId + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", sex='" + sex + '\'' +
                ", salary=" + salary +
                '}';
    }

}
