package android.arnab.qrattendence;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class requestCandidateDetails extends StringRequest
{
    int MY_SOCKET_TIMEOUT_MS=60000;

    public  requestCandidateDetails(String URL, Response.Listener<String> listener)
    {
        super(Method.GET,URL,listener,null);

        this.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }
}