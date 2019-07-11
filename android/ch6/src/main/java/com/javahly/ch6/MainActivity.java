package com.javahly.ch6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText txtTeacherId,txtTeacherName,txtSex,txtSalary;
    Button btnAdd,btnDelete,btnUpdate,btnQuery;
    ListView listViewTeacher;
    String currentTeacherId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTeacherId=(EditText)findViewById(R.id.txtTeacherId);
        txtTeacherName=(EditText)findViewById(R.id.txtTeacherName);
        txtSex=(EditText)findViewById(R.id.txtSex);
        txtSalary=(EditText)findViewById(R.id.txtSalary);
        btnUpdate=(Button)findViewById(R.id.btnUpdate);
        listViewTeacher=(ListView)findViewById(R.id.listViewTeacher);
        View view= LayoutInflater.from(this).inflate(R.layout.teacher_item_header,null);
        listViewTeacher.addHeaderView(view);
        listViewTeacher.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0) {
                    txtTeacherId.setText("");
                    txtTeacherName.setText("");
                    txtSex.setText("");
                    txtSalary.setText("");
                    return;
                }
                TextView teacherId=(TextView)view.findViewById(R.id.teacherId);
                TextView teacherName=(TextView)view.findViewById(R.id.teacherName);
                TextView sex=(TextView)view.findViewById(R.id.sex);
                TextView salary=(TextView)view.findViewById(R.id.salary);
                txtTeacherId.setText(teacherId.getText().toString());
                txtTeacherName.setText(teacherName.getText().toString());
                txtSex.setText(sex.getText().toString());
                txtSalary.setText(salary.getText().toString());
                currentTeacherId=teacherId.getText().toString();
            }
        });
    }
    public void btnAddClick(View view){
        Teacher teacher=new Teacher(txtTeacherId.getText().toString(),txtTeacherName.getText().toString(),txtSex.getText().toString(),Integer.parseInt(txtSalary.getText().toString()));
        try {
            StudentBpo.insert(MainActivity.this, teacher);
            btnQueryClick(view);
        }catch (Exception e){
            Toast.makeText(this,"添加教师信息出错"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    public void btnDeleteClick(View view){
        StudentBpo.delete(MainActivity.this,txtTeacherId.getText().toString());
        btnQueryClick(view);
    }
    public void btnUpdateClick(View view){
        Teacher teacher=new Teacher(txtTeacherId.getText().toString(),txtTeacherName.getText().toString(),txtSex.getText().toString(),Integer.parseInt(txtSalary.getText().toString()));
        StudentBpo.update(MainActivity.this,teacher,currentTeacherId);
        btnQueryClick(view);
    }
    public void btnQueryClick(View view){
        System.out.println("a1");
        List<Map<String,String>> studentList=StudentBpo.getAllTeacher(MainActivity.this,txtTeacherId.getText().toString(),txtTeacherName.getText().toString(),txtSex.getText().toString());
        System.out.println("a2");
        SimpleAdapter adapter=new SimpleAdapter(MainActivity.this,studentList,R.layout.teacher_item,new String[]{"teacherId","teacherName","sex","salary"},new int[]{R.id.teacherId,R.id.teacherName,R.id.sex,R.id.salary});
        System.out.println("a3");
        listViewTeacher.setAdapter(adapter);
        System.out.println("a4");
    }
}
