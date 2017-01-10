package nkosi.roger.manutdcom.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import nkosi.roger.manutdcom.constants.Constants;
import nkosi.roger.manutdcom.manager.RestApiManager;
import nkosi.roger.manutdcom.model.BlogModel;
import nkosi.roger.manutdcom.model.FeaturedMatchModel;
import nkosi.roger.manutdcom.model.HeadlinesModel;
import nkosi.roger.manutdcom.model.LiveEventsModel;
import nkosi.roger.manutdcom.model.LiveMatchModel;
import nkosi.roger.manutdcom.utils.aUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Roger on 12/8/2016.
 */

public class APIController {

    private nkosi.roger.manutdcom.utils.aUtils aUtils;
    private final String TAG = APIController.class.getSimpleName();
    private RestApiManager restApiManager;
    private HeadlinesCallBackListener headlinesCallBackListener;
    private LiveEventCallBackListener liveEventCallBackListener;
    private ProgressDialog dialog;
    private BlogCallBackListener blogCallBackListener;

    public APIController(HeadlinesCallBackListener headlinesCallBackListener) {
        this.headlinesCallBackListener = headlinesCallBackListener;
        this.restApiManager = new RestApiManager();
        this.aUtils = new aUtils();
    }

    public APIController(LiveEventCallBackListener liveEventCallBackListener) {
        this.restApiManager = new RestApiManager();
        this.liveEventCallBackListener = liveEventCallBackListener;
        this.aUtils = new aUtils();
    }

    public APIController(BlogCallBackListener blogCallBackListener) {
        this.restApiManager = new RestApiManager();
        this.blogCallBackListener = blogCallBackListener;
        this.aUtils = new aUtils();
    }

    public APIController() {
        this.restApiManager = new RestApiManager();
    }

    public interface HeadlinesCallBackListener {
        void onFetchStart();

        void onFetchProgress(HeadlinesModel model);

        void onFetchProgress(List<HeadlinesModel> modelList);

        void onFetchComplete();

        void onFetchFailed();
    }

    public interface BlogCallBackListener {
        void onFetchStart();

        void onFetchProgress(BlogModel model);

        void onFetchProgress(List<BlogModel> blogModels);

        void onFetchComplete();

        void onFetchFailed();
    }

    public interface LiveEventCallBackListener {
        void onFetchStart();

        void onFetchProgress(LiveEventsModel model);

        void onFetchProgress(List<LiveEventsModel> modelList);

        void onFetchComplete();

        void onFetchFailed();
    }

    public void fetchBlog(final Context context) {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.show();

        HashMap<String, String> map = new HashMap<>();
        map.put("method", "blog");

        restApiManager.getHomeAPi().getMathBlog(map, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.e(TAG, s);
                JSONArray array = null;

                try {
                    array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        BlogModel model = new BlogModel.BuildBlog()
                                .setContent(object.getString("blogContent"))
                                .setTime(object.getString("dateAdded"))
                                .setTitle(object.getString("blogTitle"))
                                .buildBlog();
                        blogCallBackListener.onFetchProgress(model);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                blogCallBackListener.onFetchComplete();
                if (dialog.isShowing())
                    dialog.dismiss();

                if (array == null) {
                    nkosi.roger.manutdcom.utils.aUtils.showDialog(context);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (dialog.isShowing())
                    dialog.dismiss();

                nkosi.roger.manutdcom.utils.aUtils.showDialog(context);

                blogCallBackListener.onFetchComplete();
            }
        });
    }

    public void refetchBlog(final Context context) {

        HashMap<String, String> map = new HashMap<>();
        map.put("method", "blog");

        restApiManager.getHomeAPi().getMathBlog(map, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.e(TAG, s);
                JSONArray array = null;

                try {
                    array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        BlogModel model = new BlogModel.BuildBlog()
                                .setContent(object.getString("blogContent"))
                                .setTime(object.getString("dateAdded"))
                                .setTitle(object.getString("blogTitle"))
                                .buildBlog();
                        blogCallBackListener.onFetchProgress(model);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                blogCallBackListener.onFetchComplete();

                if (array == null) {
                    nkosi.roger.manutdcom.utils.aUtils.showDialog(context);
                }
            }

            @Override
            public void failure(RetrofitError error) {

                nkosi.roger.manutdcom.utils.aUtils.showDialog(context);

                blogCallBackListener.onFetchComplete();
            }
        });
    }


    public void fetchHeadlines(final Context context) {

        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.show();

        HashMap<String, String> map = new HashMap<>();
        map.put("method", "headlines");

        restApiManager.getHomeAPi().getHeadlines(map, new Callback<String>() {

            @Override
            public void success(String s, Response response) {
                Log.e(TAG, s);
                JSONArray array = null;
                try {
                    array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++) {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                headlinesCallBackListener.onFetchComplete();

                if (dialog.isShowing())
                    dialog.dismiss();

                if (array == null) {
                    nkosi.roger.manutdcom.utils.aUtils.showDialog(context);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (dialog.isShowing())
                    dialog.dismiss();

                nkosi.roger.manutdcom.utils.aUtils.showDialog(context);

                Log.e(TAG, "Error :: " + error.getMessage());
                headlinesCallBackListener.onFetchComplete();

            }
        });
    }

    public void refetchHeadlines(final Context context) {

        HashMap<String, String> map = new HashMap<>();
        map.put("method", "headlines");

        restApiManager.getHomeAPi().getHeadlines(map, new Callback<String>() {

            @Override
            public void success(String s, Response response) {
                Log.e(TAG, s);
                JSONArray array = null;
                try {
                    array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++) {
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
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                headlinesCallBackListener.onFetchComplete();


                if (array == null) {
                    nkosi.roger.manutdcom.utils.aUtils.showDialog(context);
                }
            }

            @Override
            public void failure(RetrofitError error) {

                nkosi.roger.manutdcom.utils.aUtils.showDialog(context);

                Log.e(TAG, "Error :: " + error.getMessage());
                headlinesCallBackListener.onFetchComplete();

            }
        });
    }

    public void fetchEvents(final Context context) {

        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.show();

        HashMap<String, String> map = new HashMap<>();
        map.put("method", "liveEvent");

        restApiManager.getHomeAPi().getLiveEvents(map, new Callback<String>() {

            @Override
            public void success(String s, Response response) {
                Log.e(TAG, s);
                JSONArray array = null;
                try {
                    array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        LiveEventsModel model = new LiveEventsModel.LiveEventBuilder()
                                .setEventDescription(object.getString("eventDescription"))
                                .setEventIcon(object.getString("eventIcon"))
                                .setEventTime(object.getString("eventTime"))
                                .setEventTitle(object.getString("eventTitle"))
                                .buildHeadlines();
                        liveEventCallBackListener.onFetchProgress(model);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                liveEventCallBackListener.onFetchComplete();

                if (dialog.isShowing())
                    dialog.dismiss();
                if (array == null) {
                    nkosi.roger.manutdcom.utils.aUtils.showDialog(context);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (dialog.isShowing())
                    dialog.dismiss();
                nkosi.roger.manutdcom.utils.aUtils.showDialog(context);
                Log.e(TAG, "Error :: " + error.getMessage());
                liveEventCallBackListener.onFetchComplete();
            }
        });
    }

    public void refetchEvents(final Context context) {


        HashMap<String, String> map = new HashMap<>();
        map.put("method", "liveEvent");

        restApiManager.getHomeAPi().getLiveEvents(map, new Callback<String>() {

            @Override
            public void success(String s, Response response) {
                Log.e(TAG, s);
                JSONArray array = null;
                try {
                    array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        LiveEventsModel model = new LiveEventsModel.LiveEventBuilder()
                                .setEventDescription(object.getString("eventDescription"))
                                .setEventIcon(object.getString("eventIcon"))
                                .setEventTime(object.getString("eventTime"))
                                .setEventTitle(object.getString("eventTitle"))
                                .buildHeadlines();
                        liveEventCallBackListener.onFetchProgress(model);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                liveEventCallBackListener.onFetchComplete();

                if (array == null) {
                    nkosi.roger.manutdcom.utils.aUtils.showDialog(context);
                }
            }

            @Override
            public void failure(RetrofitError error) {

                nkosi.roger.manutdcom.utils.aUtils.showDialog(context);
                Log.e(TAG, "Error :: " + error.getMessage());
                liveEventCallBackListener.onFetchComplete();
            }
        });
    }

    public void fetchLiveMatch(final TextView duration, final TextView hometeam,
                               final TextView awayteam, final TextView score, final TextView matchdate,
                               final TextView matchlocation, final TextView competition) {


        HashMap<String, String> map = new HashMap<>();
        map.put("method", "liveMatch");

        restApiManager.getHomeAPi().getLiveMatch(map, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                try {
                    JSONObject object = new JSONObject(s);
                    final LiveMatchModel model = new LiveMatchModel();
                    model.setAwayteamname(object.getString("awayteamname"));
                    model.setCompetition(object.getString("competition"));
                    model.setDuration("Duration: " + object.getString("duration") + " min");
                    model.setHometeamname(object.getString("hometeamname"));
                    model.setLocation(object.getString("location"));
                    model.setMatchdate(object.getString("matchdate"));
                    model.setMatchscore(object.getString("matchscore"));

                    // set textviews
                    duration.setText(model.getDuration());
                    hometeam.setText(model.getHometeamname());
                    awayteam.setText(model.getAwayteamname());
                    score.setText(model.getMatchscore());
                    matchdate.setText(model.getMatchdate());
                    competition.setText(model.getCompetition());
                    matchlocation.setText(model.getLocation());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }

    public void fetchFeaturedMatch(final TextView teamsAndScore,
                                   final TextView matchDate, final ImageView featuredImg,
                                   final TextView featuredText, final Context context) {
        final HashMap<String, String> map = new HashMap<>();
        map.put("method", "getFeaturedMatch");

        restApiManager.getHomeAPi().getFeaturedMatch(map, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.e(TAG, s);
                try {
                    JSONObject object = new JSONObject(s);
                    final FeaturedMatchModel model = new FeaturedMatchModel();
                    model.setMatchdate(object.getString("matchdate"));
                    model.setAwayTeam(object.getString("awayTeam"));
                    model.setFeatureImg(object.getString("featureImg"));
                    model.setFeatureText(object.getString("featureText"));
                    model.setHomeTeam(object.getString("homeTeam"));
                    model.setScore(object.getString("score"));

                    teamsAndScore.setText(model.getHomeTeam() + " " + model.getScore() + " " + model.getAwayTeam());
                    matchDate.setText(model.getMatchdate());
                    featuredText.setText(model.getFeatureText());

                    Picasso.with(context).load(Constants.BASE_URL + "images/" + model.getFeatureImg()).into(featuredImg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, error.getMessage());
            }
        });
    }

    public void refetchLiveMatch(final TextView duration, final TextView hometeam,
                                 final TextView awayteam, final TextView score, final TextView matchdate,
                                 final TextView matchlocation, final TextView competition, final Context context) {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.show();

        HashMap<String, String> map = new HashMap<>();
        map.put("method", "liveMatch");

        restApiManager.getHomeAPi().getLiveMatch(map, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                JSONObject object = null;
                try {
                    object = new JSONObject(s);
                    final LiveMatchModel model = new LiveMatchModel();
                    model.setAwayteamname(object.getString("awayteamname"));
                    model.setCompetition(object.getString("competition"));
                    model.setDuration("Duration: " + object.getString("duration") + " min");
                    model.setHometeamname(object.getString("hometeamname"));
                    model.setLocation(object.getString("location"));
                    model.setMatchdate(object.getString("matchdate"));
                    model.setMatchscore(object.getString("matchscore"));

                    // set textviews
                    duration.setText(model.getDuration());
                    hometeam.setText(model.getHometeamname());
                    awayteam.setText(model.getAwayteamname());
                    score.setText(model.getMatchscore());
                    matchdate.setText(model.getMatchdate());
                    competition.setText(model.getCompetition());
                    matchlocation.setText(model.getLocation());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (dialog.isShowing())
                    dialog.dismiss();
                if (object == null) {
                    nkosi.roger.manutdcom.utils.aUtils.showDialog(context);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (dialog.isShowing())
                    dialog.dismiss();
                nkosi.roger.manutdcom.utils.aUtils.showDialog(context);
            }
        });
    }
}
