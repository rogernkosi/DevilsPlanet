package nkosi.roger.manutdcom;

import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by lulu on 10/24/2016.
 */
public class RestApiManager {
    private API homeApi;
    public API getHomeAPi(){
        if (homeApi == null){
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(String.class, new StringDesirializer());
            homeApi = new RestAdapter.Builder()
                    .setEndpoint(Constants.BASE_URL)
                    .setConverter(new GsonConverter(builder.create()))
                    .build()
                    .create(API.class);
        }

        return homeApi;
    }
}
