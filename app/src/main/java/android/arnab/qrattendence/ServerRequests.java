package android.arnab.qrattendence;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ServerRequests
{
    //Step:1
    void addToAttendenceQueue(final Context mContext, String information, final int grade, final String department, final String classSubject,
                              final String classDate, final String startTime, final String endTime)
    {
        final String fields[]=information.split("@");
        final long id=Long.parseLong(fields[0]);
        Response.Listener<String> detailsResponseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Map<String, String> params = new HashMap<>();
                JSONObject jsonResponse = null;

                if (response != null)
                {
                    try
                    {
                        jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success)
                        {
                            int activeState = jsonResponse.getInt("activeState");
                            if (activeState == 1) {
                                //$Id, $personName, $grade, $department, $orgWalletVal, $activeState, $OTP


                                if (Integer.parseInt(fields[6]) == jsonResponse.getInt("OTP") &&
                                        Integer.parseInt(fields[2])==grade &&
                                        fields[3].equalsIgnoreCase(department))  //Step:2
                                {

                                    ServerRequests.this.addAttendence(mContext,id,classSubject.toUpperCase(),classDate,startTime,endTime,grade,
                                            department.toLowerCase());    //Step:3

                                }
                                else
                                {
                                    Toast.makeText(mContext, "Invalid card", Toast.LENGTH_SHORT).show();
                                }


                            } else {
                                if (activeState == 0) {
                                    Toast.makeText(mContext, "Card not active. Contact office", Toast.LENGTH_SHORT).show();
                                    params.put("message", "Failed");

                                }
                            }
                        } else {
                            Toast.makeText(mContext, "Invalid Id. Contact office", Toast.LENGTH_SHORT).show();
                            params.put("message", "Failed");

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(mContext, "1 Some error occured", Toast.LENGTH_SHORT).show();
                        params.put("message", "Failed");
                    }

                }
            }
        };



        String CANDIDATE_DETAILS_URL= String.format("http://arnabbanerjee.dx.am/requestCandidateDetails.php?id=%1$d",id);

        requestCandidateDetails requestDetails=new requestCandidateDetails(CANDIDATE_DETAILS_URL,detailsResponseListener);
        RequestQueue queue=Volley.newRequestQueue(mContext);
        queue.add(requestDetails);
    }


    //Step:3
    void addAttendence(final Context mContext,final long id,final String classSubject, final String classDate,
                               final String startTime, final String endTime, final int grade, final String department)
    {

        Response.Listener<String> attendenceResponseListener=new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Map<String, String> params = new HashMap<>();
                JSONObject jsonResponse = null;

                if (response != null)
                {
                    try
                    {
                        jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success)
                        {
                            Toast.makeText(mContext,"Added",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(mContext, "Invalid Id. Contact office", Toast.LENGTH_SHORT).show();
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

        RequestAddAttendence requestAddAttendence=new RequestAddAttendence(id,classDate,
                classSubject.toLowerCase(), startTime,endTime, grade, department,attendenceResponseListener);
        RequestQueue queue=Volley.newRequestQueue(mContext);
        queue.add(requestAddAttendence);


    }
}
