package vn.aptech.smartstudy.service;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FcmNotifySender {
    private String topic;
    private String title;
    private String body;
    private Context mContext;
    private Activity activity;

    private RequestQueue requestQueue;
    private final String BASE_URL="https://fcm.googleapis.com/fcm/send";
    public static final String SERVER_KEY="AAAANXm4h4Y:APA91bGWvEvJD4zDl4BCpr-MiATOParxqh_6KaucvNUpIxPrMvXAIt_kZAWUK9DFO4eAZYSh3d4FpzadtdxFNwrwwf8FzmgYRP2T8CSh492OlwOfRASDywMret1RLwtjI-TgqROuHu8F";

    public FcmNotifySender(String topic, String title, String body, Context mContext, Activity activity) {
        this.topic = topic;
        this.title = title;
        this.body = body;
        this.mContext = mContext;
        this.activity = activity;
    }

    public FcmNotifySender() {
    }
    public void sendNotifycation(){
        requestQueue = Volley.newRequestQueue(activity);
        JSONObject mainObj = new JSONObject();
        try{
            mainObj.put("to", topic);
            JSONObject notiObject = new JSONObject();
            notiObject.put("title", title);
            notiObject.put("body", body);

            mainObj.put("notification", notiObject);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, BASE_URL, mainObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key="+SERVER_KEY);
                    return header;
                }
            };
            requestQueue.add(request);
        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public void setRequestQueue(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    public String getBASE_URL() {
        return BASE_URL;
    }
}
