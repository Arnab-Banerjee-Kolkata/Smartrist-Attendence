package android.arnab.qrattendence;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TeacherHome extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener
{
    static Calendar myCalendar;
    static EditText subjectVal;
    static TextView dateVal;
    static Button attendenceBtn;
    ImageView bgImg;
    RelativeLayout homeWait;
    int stPos=0,etPos=0;
    static boolean resp1=true;
    static int INDICATOR=0,CLASS_VALIDITY_PERIOD=10000;
    int grade,group;
    String department,classSubject,classDate,startTime,endTime;
    DatePickerDialog.OnDateSetListener date;
    TimePickerDialog.OnTimeSetListener time,time2;
    Spinner gradeSpinner,deptSpinner,groupSpinner, startSpinner, endSpinner;
    boolean gradeFlag=false,deptFlag=false, groupFlag=false, startFlag=false, endFlag=false;
    String[] sTime={" ","10:00:00","10:50:00","11:40:00","12:30:00","14:00:00","14:50:00"};
    String[] eTime={" ","10:50:00","11:40:00","12:30:00","13:20:00","14:50:00","15:40:00"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Smartrist: Attendance");
        dateVal=findViewById(R.id.dateVal);
        subjectVal=findViewById(R.id.subjectVal);
        attendenceBtn=findViewById(R.id.attendenceBtn);
        bgImg=findViewById(R.id.bgImg);
        homeWait=findViewById(R.id.homeWait);
        Glide.with(getApplicationContext()).load(this.getResources().getDrawable(R.drawable.attbg)).into(bgImg);
        Button bcred=findViewById(R.id.creditb);
        bcred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Credits.class);
                startActivity(intent);
            }
        });

        homeWait.setVisibility(View.GONE);

        dateVal.setOnClickListener(this);
        attendenceBtn.setOnClickListener(this);

        myCalendar = Calendar.getInstance();

        date= new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDateLabel();
            }

        };

         gradeSpinner= (Spinner) findViewById(R.id.grade);
        String[] itemgrade=new String[]{" ","1","2","3","4"};
        ArrayAdapter<String> myadapter1 = new ArrayAdapter<String>(this,
                R.layout.spinnertext, itemgrade);
        myadapter1.setDropDownViewResource(R.layout.spinnertext);
        gradeSpinner.setAdapter(myadapter1);
        gradeSpinner.setSelection(0,false);
        gradeSpinner.setOnItemSelectedListener(this);

        deptSpinner = (Spinner) findViewById(R.id.department);
        String[] itemdepartment=new String[]{" ","CSE","IT","ECE","BT"};
        ArrayAdapter<String> myadapter2 = new ArrayAdapter<String>(this,
                R.layout.spinnertext, itemdepartment);
        myadapter2.setDropDownViewResource(R.layout.spinnertext);
        deptSpinner.setAdapter(myadapter2);
        deptSpinner.setSelection(0,false);
        deptSpinner.setOnItemSelectedListener(this);

        groupSpinner = (Spinner) findViewById(R.id.group);
        String[] itemgroup=new String[]{" ","1","2","Whole"};
        ArrayAdapter<String> myadapter3 = new ArrayAdapter<String>(this,
                R.layout.spinnertext, itemgroup);
        myadapter3.setDropDownViewResource(R.layout.spinnertext);
        groupSpinner.setAdapter(myadapter3);
        groupSpinner.setSelection(0,false);
        groupSpinner.setOnItemSelectedListener(this);

        startSpinner = (Spinner) findViewById(R.id.startperiod);
        String[] itemstart=new String[]{" ","1st","2nd","3rd","4th","5th","6th"};
        ArrayAdapter<String> myadapter4 = new ArrayAdapter<String>(this,
                R.layout.spinnertext, itemstart);
        myadapter4.setDropDownViewResource(R.layout.spinnertext);
        startSpinner.setAdapter(myadapter4);
        startSpinner.setSelection(0,false);
        startSpinner.setOnItemSelectedListener(this);

        endSpinner = (Spinner) findViewById(R.id.endperiod);
        String[] itemend=new String[]{" ","1st","2nd","3rd","4th","5th","6th"};
        ArrayAdapter<String> myadapter5 = new ArrayAdapter<String>(this,
                R.layout.spinnertext, itemend);
        myadapter5.setDropDownViewResource(R.layout.spinnertext);
        endSpinner.setAdapter(myadapter5);
        endSpinner.setSelection(0,false);
        endSpinner.setOnItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        attendenceBtn.setText("Start Scan");
    }

    @Override
    public void onClick(View v)
    {
        if(v.equals(dateVal))
        {
            new DatePickerDialog(TeacherHome.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
        else if(v.equals(attendenceBtn))
        {
            DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");



            if(TextUtils.isEmpty(dateVal.getText()) || TextUtils.isEmpty(subjectVal.getText()) || !gradeFlag || !deptFlag ||
                    !groupFlag || !startFlag || !endFlag)
            {
                Toast.makeText(TeacherHome.this,"Please fill in all fields",Toast.LENGTH_SHORT).show();
            }
            else if(etPos<stPos)
            {
                Toast.makeText(getApplicationContext(),"End period cannot be before start period.",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                classSubject=subjectVal.getText().toString();
                classDate=dateVal.getText().toString();

                homeWait.setVisibility(View.VISIBLE);

                addClass(TeacherHome.this,getApplicationContext(),grade,department,classSubject,classDate,startTime,endTime,group);

            }
        }

    }
    private void updateDateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateVal.setText(sdf.format(myCalendar.getTime()));
    }


    void addClass(final Activity mActivity, final Context mContext, final int grade, final String department, final String classSubject, final String classDate,
                  final String startTime, final String endTime, final int group)
    {
        Response.Listener<String> classResponseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Map<String, String> params = new HashMap<>();
                JSONObject jsonResponse = null;
                homeWait.setVisibility(View.GONE);

                if (response != null)
                {
                    //Toast.makeText(mContext,response,Toast.LENGTH_LONG).show();
                    try
                    {
                        jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success)
                        {
                            Toast.makeText(mContext,"Class Added",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(mContext, TakeAttendence.class);
                            intent.putExtra("classDate", dateVal.getText().toString());
                            intent.putExtra("classSubject", subjectVal.getText().toString().toLowerCase());
                            intent.putExtra("startTime", startTime);
                            intent.putExtra("endTime", endTime);
                            intent.putExtra("department",department);
                            intent.putExtra("grade",grade);
                            intent.putExtra("group",group);
                            mActivity.startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(mContext, "Could not Add class.", Toast.LENGTH_SHORT).show();
                            params.put("message", "Failed");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, "3 Some error occured", Toast.LENGTH_SHORT).show();
                        params.put("message", "Failed");
                    }

                }
            }
        };

        RequestAddClass requestAddClass=new RequestAddClass(grade,department.toLowerCase(),classSubject.toLowerCase(),
                classDate,startTime,endTime,group,classResponseListener);

        RequestQueue queue=Volley.newRequestQueue(mContext);
        queue.add(requestAddClass);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        if(parent.equals(gradeSpinner))
        {
            if(position!=0)
            {
                gradeFlag=true;
                grade=position;
            }
            else
            {
                gradeFlag=false;
            }
        }
        else if(parent.equals(deptSpinner))
        {
            if(position!=0)
            {
                deptFlag=true;
                department=deptSpinner.getSelectedItem().toString();
            }
            else
            {
                deptFlag=false;
            }
        }
        else if(parent.equals(groupSpinner))
        {
            if(position!=0)
            {
                groupFlag=true;
                group=position;
            }
            else
            {
                groupFlag=false;
            }
        }
        else if(parent.equals(startSpinner))
        {
            if(position!=0)
            {
                startFlag=true;
                startTime=sTime[position];
                stPos=position;
            }
            else
            {
                startFlag=false;
            }
        }
        else if(parent.equals(endSpinner))
        {
            if(position!=0)
            {
                endFlag=true;
                endTime=eTime[position];
                etPos=position;
            }
            else
            {
                endFlag=false;
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
