package nkosi.roger.manutdcom;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Roger on 12/8/2016.
 */

public class APIController {
    private final String TAG = APIController.class.getSimpleName();
    private RestApiManager restApiManager;
    private HeadlinesCallBackListener headlinesCallBackListener;

    public APIController(HeadlinesCallBackListener headlinesCallBackListener) {
        this.headlinesCallBackListener = headlinesCallBackListener;
        this.restApiManager = new RestApiManager();
    }

    public interface  HeadlinesCallBackListener{
        void onFetchStart();
        void onFetchProgress(HeadlinesModel model);
        void onFetchProgress(List<HeadlinesModel> modelList);
        void onFetchComplete();
        void onFetchFailed();
    }

    public void fetchHeadlines(){
        HashMap<String, String> map = new HashMap<>();
        map.put("method", "headlines");

        restApiManager.getHomeAPi().getHeadlines(map, new Callback<String>() {

            @Override
            public void success(String s, Response response) {
                try{
                    JSONArray array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++){
                        JSONObject object = array.getJSONObject(i);

                        HeadlinesModel model = new HeadlinesModel.HeadlineBuilder()
                                .setDetails(object.getString(Constants.DETAILS))
                                .setHeadline(object.getString(Constants.HEADLINE))
                                .setHID(object.getString(Constants.NEWSID))
                                .setSource(object.getString(Constants.SOURCE))
                                .setImgUri(object.getString(Constants.HEADLINE_PHOTO))
                                .buildHeadLines();
                        headlinesCallBackListener.onFetchProgress(model);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                headlinesCallBackListener.onFetchComplete();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Error :: " + error.getMessage());
                headlinesCallBackListener.onFetchComplete();
            }
        });
    }
}
