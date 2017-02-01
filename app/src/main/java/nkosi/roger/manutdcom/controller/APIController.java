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
import nkosi.roger.manutdcom.model.CalendarModel;
import nkosi.roger.manutdcom.model.FeaturedMatchModel;
import nkosi.roger.manutdcom.model.HeadlinesModel;
import nkosi.roger.manutdcom.model.History;
import nkosi.roger.manutdcom.model.LiveEventsModel;
import nkosi.roger.manutdcom.model.LiveMatchModel;
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
    private LiveEventCallBackListener liveEventCallBackListener;
    private ProgressDialog dialog;
    private BlogCallBackListener blogCallBackListener;
    private CalendarCallBackListener calendarCallBackListener;

    public APIController(HeadlinesCallBackListener headlinesCallBackListener) {
        this.headlinesCallBackListener = headlinesCallBackListener;
        this.restApiManager = new RestApiManager();

    }

    public APIController(LiveEventCallBackListener liveEventCallBackListener) {
        this.restApiManager = new RestApiManager();
        this.liveEventCallBackListener = liveEventCallBackListener;

    }

    public APIController(BlogCallBackListener blogCallBackListener) {
        this.restApiManager = new RestApiManager();
        this.blogCallBackListener = blogCallBackListener;

    }

    public APIController() {
        this.restApiManager = new RestApiManager();
    }

    public APIController(CalendarCallBackListener listener) {
        this.restApiManager = new RestApiManager();
        this.calendarCallBackListener = listener;

    }


    public interface HeadlinesCallBackListener {
        void onFetchStart();

        void onFetchProgress(HeadlinesModel model);

        void onFetchProgress(List<HeadlinesModel> modelList);

        void onFetchComplete();

        void onFetchFailed();
    }

    public interface CalendarCallBackListener {
        void onFetchStart();

        void onFetchProgress(CalendarModel model);

        void onFetchProgress(List<CalendarModel> modelList);

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

                JSONArray array = null;

                try {
                    Log.e(TAG, s);
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

                JSONArray array = null;

                try {
                    Log.e(TAG, s);
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

                JSONArray array = null;
                try {
                    Log.e(TAG, s);
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
                } catch (Exception e) {
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

                JSONArray array = null;
                try {
                    Log.e(TAG, s);
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
                } catch (Exception e) {
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
                } catch (Exception e) {
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

    public void fetchCalendar(final Context context) {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.show();

        HashMap<String, String> map = new HashMap<>();
        map.put("method", "calendar");

        restApiManager.getHomeAPi().getCalendar(map, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                JSONArray array = null;

                try {
                    Log.e(TAG, s);
                    array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        CalendarModel model = new CalendarModel.BuildCalender()
                                .setHomeAway(object.getString("homeAway"))
                                .setComp(object.getString("competition"))
                                .setDatePlayed(object.getString("datePlayed"))
                                .setAgainst(object.getString("against"))
                                .setScore(object.getString("score"))
                                .buildCalender();
                        calendarCallBackListener.onFetchProgress(model);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                calendarCallBackListener.onFetchComplete();

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
                calendarCallBackListener.onFetchComplete();
            }
        });
    }

    public void refetchCalendar(final Context context) {

        HashMap<String, String> map = new HashMap<>();
        map.put("method", "calendar");

        restApiManager.getHomeAPi().getCalendar(map, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                JSONArray array = null;

                try {
                    Log.e(TAG, s);
                    array = new JSONArray(s);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        CalendarModel model = new CalendarModel.BuildCalender()
                                .setHomeAway(object.getString("homeAway"))
                                .setComp(object.getString("competition"))
                                .setDatePlayed(object.getString("datePlayed"))
                                .setAgainst(object.getString("against"))
                                .setScore(object.getString("score"))
                                .buildCalender();
                        calendarCallBackListener.onFetchProgress(model);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                calendarCallBackListener.onFetchComplete();

                if (array == null) {
                    nkosi.roger.manutdcom.utils.aUtils.showDialog(context);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                nkosi.roger.manutdcom.utils.aUtils.showDialog(context);
                Log.e(TAG, "Error :: " + error.getMessage());
                calendarCallBackListener.onFetchComplete();
            }
        });
    }

    public void refetchEvents(final Context context) {


        HashMap<String, String> map = new HashMap<>();
        map.put("method", "liveEvent");

        restApiManager.getHomeAPi().getLiveEvents(map, new Callback<String>() {

            @Override
            public void success(String s, Response response) {

                JSONArray array = null;
                try {
                    Log.e(TAG, s);
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
                } catch (Exception e) {
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
                    Log.e(TAG, s);
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

                try {
                    Log.e(TAG, s);
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

                    Log.e("images", Constants.BASE_URL + "images/" + model.getFeatureImg());

                    Picasso.with(context).load(Constants.BASE_URL + "images/" + model.getFeatureImg()).into(featuredImg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                try {
                    Log.e(TAG, error.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                    if(model.getDuration() != null){
                        duration.setText(model.getDuration());
                    }else {
                        duration.setText("N/A");
                    }

                    if(model.getMatchdate() != null){
                        matchdate.setText(model.getMatchdate());
                    }else {
                        matchdate.setText("N/A");
                    }

                    if(model.getCompetition() != null){
                        competition.setText(model.getCompetition());
                    }else {
                        competition.setText("N/A");
                    }

                    if(model.getMatchscore() != null){
                        score.setText(model.getMatchscore());
                    }else {
                        score.setText("N/A");
                    }

                    if(model.getAwayteamname() != null){
                        awayteam.setText(model.getAwayteamname());
                    }else {
                        awayteam.setText("N/A");
                    }

                    if(model.getHometeamname() != null){
                        hometeam.setText(model.getHometeamname());
                    }else {
                        hometeam.setText("N/A");
                    }

                    if (model.getLocation() !=null){
                        matchlocation.setText(model.getLocation());
                    }else{
                        matchlocation.setText("N/A");
                    }

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

    public void sendContact(final Context context, String name, String mail, String comment) {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.show();

        HashMap<String, String> map = new HashMap<>();
        map.put("method", "contact");
        map.put("name", name);
        map.put("email", mail);
        map.put("comment", comment);

        restApiManager.getHomeAPi().sendComment(map, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                JSONObject object = null;
                String message = null;
                try {
                    object = new JSONObject(s);
                    message = object.getString("message");

                } catch (Exception e) {

                }

                if (dialog.isShowing())
                    dialog.dismiss();

                if (object != null) {
                    nkosi.roger.manutdcom.utils.aUtils.showAlertDialog(context,
                            "Contact us", message);
                } else {
                    nkosi.roger.manutdcom.utils.aUtils.showAlertDialog(context,
                            "Contact us", "Failed, please try again");
                }

            }

            @Override
            public void failure(RetrofitError error) {
                if (dialog.isShowing())
                    dialog.dismiss();
                nkosi.roger.manutdcom.utils.aUtils.showAlertDialog(context,
                        "Contact us", "Failed, please try again");
            }
        });
    }

    public List<String> getHistory(final Context context, final TextView history) {

        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);
        dialog.show();

        HashMap<String, String> map = new HashMap<>();
        map.put("method", "history");


        restApiManager.getHomeAPi().getHistory(map, new Callback<String>() {
            @Override
            public void success(String s, Response response) {

                JSONObject object = null;


                try {
                    Log.e(TAG, s);
                    History history1 = new History();
                    object = new JSONObject(s);
                    history1.setContent(object.getString("content"));

                    history.setText(history1.getContent());

                } catch (Exception e) {

                }


                if (dialog.isShowing())
                    dialog.dismiss();

                if (object == null) {
                    nkosi.roger.manutdcom.utils.aUtils.showAlertDialog(context,
                            "Try Again", "Please check you internet connection");
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                if (dialog.isShowing())
                    dialog.dismiss();
                nkosi.roger.manutdcom.utils.aUtils.showAlertDialog(context,
                        "Try Again", "Please check you internet connection");
            }
        });


        return null;
    }
}
