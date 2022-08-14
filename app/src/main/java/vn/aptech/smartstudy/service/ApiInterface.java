package vn.aptech.smartstudy.service;

import static vn.aptech.smartstudy.service.ValuesClass.CONTENT_TYPE;
import static vn.aptech.smartstudy.service.ValuesClass.SERVER_KEY;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
    @Headers({"Authorization: Key="+SERVER_KEY,"Content-Type:"+CONTENT_TYPE})
    @POST("fcm/send")
    Call<PushNotification> sendNotification(@Body PushNotification pushNotification);


}
