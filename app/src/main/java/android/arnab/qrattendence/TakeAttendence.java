package android.arnab.qrattendence;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tempos21.t21crypt.exception.CrypterException;
import com.tempos21.t21crypt.exception.DecrypterException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/*Steps:
1. get candidate details
2. match the otp
3. add attendence*/

public class TakeAttendence extends AppCompatActivity
{
    String classSubject,classDate,startTime,endTime,department;
    int grade;
    boolean readyToClose=false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendence);

        Bundle intent=getIntent().getExtras();
        classDate=intent.getString("classDate");
        classSubject=intent.getString("classSubject");
        startTime=intent.getString("startTime");
        endTime=intent.getString("endTime");
        department=intent.getString("department");
        grade=intent.getInt("grade");

        IntentIntegrator integrator=new IntentIntegrator(TakeAttendence.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setCameraId(0);
        integrator.setOrientationLocked(false);
        integrator.setPrompt("Scanning");
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        //Toast.makeText(getApplicationContext(),""+requestCode+"   "+resultCode,Toast.LENGTH_SHORT).show();

        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null && result.getContents()!=null)
        {
            String encodedInfo=result.getContents();
            QR qr=new QR(getApplicationContext(),this.getResources().getString(R.string.KEY_TOKEN));
            try {
                String information=qr.getDecryptedString(encodedInfo);
                ServerRequests serverRequests=new ServerRequests();
                serverRequests.addToAttendenceQueue(getApplicationContext(), information, grade, department, classSubject, classDate, startTime,endTime);



            } catch (CrypterException e) {
                e.printStackTrace();
            } catch (DecrypterException e) {
                e.printStackTrace();
            }

        }

        if(resultCode!=0)
        {
            IntentIntegrator integrator = new IntentIntegrator(TakeAttendence.this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setCameraId(0);
            integrator.setOrientationLocked(false);
            integrator.setPrompt("Scanning");
            integrator.setBeepEnabled(true);
            integrator.setBarcodeImageEnabled(true);
            integrator.initiateScan();
        }
        else
        {
            onBackPressed();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();

        Intent intent=new Intent(getApplicationContext(),TeacherHome.class);
        startActivity(intent);
        finish();
    }
}
