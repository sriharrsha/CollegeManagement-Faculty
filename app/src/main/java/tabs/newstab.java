package tabs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import app.management.college.com.collegemanagement.R;
import app.management.college.com.collegemanagement.model.ExternalExamItem;
import app.management.college.com.collegemanagement.model.GlobalData;
import app.management.college.com.collegemanagement.util.CredentialManager;

public class newstab extends Fragment {
    private static final String DEBUG_TAG = "newstab";
    private CredentialManager credentialManager;
    public newstab() {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String newsCacheString = credentialManager.getNewsCache();
        if(newsCacheString != null && newsCacheString != "") {
            Log.d(DEBUG_TAG, "newsCacheString: " + newsCacheString);
            Log.d(DEBUG_TAG, "newsCacheString: " + "cache exists");
            setTheNewsScreen(newsCacheString);
        } else {
            Log.d(DEBUG_TAG, "onActivityCreated: " + "no cache");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        String profileUrl = "/StaffAttendanceService.svc/GetNoticeBoard";
        credentialManager = new CredentialManager(getActivity());
        String loginURL = "/AuthenticationService.svc/AuthenticateRequest?username="+ credentialManager.getUserName() +"&Password="+ credentialManager.getPassword();
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

//        Toast.makeText(getContext(), "Saved Univ URL: " + credentialManager.getUniversityUrl(), Toast.LENGTH_LONG).show();
        if (networkInfo != null && networkInfo.isConnected()) {
            new NewsTask().execute(credentialManager.getUniversityUrl() + loginURL,
                    credentialManager.getUniversityUrl() +profileUrl);
        } else {
            //TODO: offline compatible
            Log.d(DEBUG_TAG, "cache: " + credentialManager.getProfileCache());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false);
    }





    // Network code
    private class NewsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            // params comes from the execute() call: params[0] is the url.
            try {
                GlobalData globalData = new GlobalData();
                downloadUrl(urls[0]);
                return downloadUrl(urls[1]);
            } catch (Exception e) {
                Log.d(DEBUG_TAG, "The response is: " + e.toString());
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {


//            credentialManager = new CredentialManager(this);
            try {
                JSONObject resultJSON = new JSONObject(result);
                Log.e(DEBUG_TAG, "resultJSON: " + result + "");
                if(resultJSON.getInt("ServiceResult") == 0) {
                    // success
                    credentialManager.setNewsCache(result);
                    // Set the token and time for future use.
                    setTheNewsScreen(result);
                    Log.d(DEBUG_TAG, "onPostExecute: from network" );
                } else {

                }
            } catch (Exception t) {
                Log.e("JSON error", t + " Could not parse malformed JSON: \"" + result + "\"");
            }
            /*Intent i = new Intent(LoginActivity.this, Home.class);
            startActivity(i);*/
        }

    }
    // Given a URL, establishes an HttpUrlConnection and retrieves
// the web page content as a InputStream, which it returns as
// a string.
    private String downloadUrl(String myurl) throws IOException {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 50000;

        try {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            if(myurl.contains("AuthenticateRequest")) conn.setRequestMethod("POST");
            CredentialManager credentialManager = new CredentialManager(getActivity());
//            GlobalData globalData = new GlobalData();
            conn.setRequestProperty("TOKEN", credentialManager.getToken());
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } catch (Exception e) {
            Log.d(DEBUG_TAG, "error is --: " + e.toString());
        }
        finally {
            if (is != null) {
                is.close();
            }
        }
        return "";
    }
    // Reads an InputStream and converts it to a String.
    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        /*char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);*/
        BufferedReader r = new BufferedReader(reader);
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        return  total.toString();
    }

    private void setTheNewsScreen(String result) {
        LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.navigationItemsCont);
        linearLayout.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        try {
            JSONObject resultJSON = new JSONObject(result);
            JSONArray dataList = resultJSON.getJSONArray("DataList");
            for (int i = 0; i < dataList.length(); i++) {
                JSONObject data = (JSONObject) dataList.get(i);
                View view  = inflater.inflate(R.layout.row_news_item, linearLayout, false);
                ((TextView) view.findViewById(R.id.newsTitle)).setText(data.getString("NotificationTitle"));
                ((TextView) view.findViewById(R.id.newsBody)).setText(data.getString("NotificationDescription"));
                // set item content in view
                linearLayout.addView(view);
                ((TextView)getActivity().findViewById(R.id.newsStatus)).setText("yes News to show.");
            }
            if(dataList.length() == 0){
                ((TextView)getActivity().findViewById(R.id.newsStatus)).setText("No News to show.");
            }
        } catch (Exception ex) {

        }

    }

}
