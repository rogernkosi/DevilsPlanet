package nkosi.roger.manutdcom.api;

import java.util.Map;

import retrofit.Callback;
import retrofit.http.FieldMap;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

/**
 * Created by ROGER on 10/24/2016.
 */

public interface API {

    @FormUrlEncoded
    @POST("/index.php")
    void getHeadlines(@FieldMap Map<String, String> map, Callback<String> callback);

    @FormUrlEncoded
    @POST("/index.php")
    void getLiveEvents(@FieldMap Map<String, String> map, Callback<String> callback);

    @FormUrlEncoded
    @POST("/index.php")
    void getLiveMatch(@FieldMap Map<String, String> map, Callback<String> callback);

    @FormUrlEncoded
    @POST("/index.php")
    void getFeaturedMatch(@FieldMap Map<String, String> map, Callback<String> callback);

    @FormUrlEncoded
    @POST("/index.php")
    void getMathBlog(@FieldMap Map<String, String> map, Callback<String> callback);
}
