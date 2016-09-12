package tabs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

import app.management.college.com.collegemanagement.R;
import app.management.college.com.collegemanagement.model.GlobalData;
import app.management.college.com.collegemanagement.util.CredentialManager;

public class profiletab extends Fragment {

    private static final String DEBUG_TAG = "profiletab tab";
    private JSONObject address;
    private Iterator<String> keys;
    private CredentialManager credentialManager;
    public profiletab() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String profileUrl = "/FacultyProfileService.svc/GetFacultyProfile";
        credentialManager = new CredentialManager(getActivity());
        String loginURL = "/AuthenticationService.svc/AuthenticateRequest?username="+ credentialManager.getUserName() +"&Password="+ credentialManager.getPassword();
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

//        Toast.makeText(getContext(), "Saved Univ URL: " + credentialManager.getUniversityUrl(), Toast.LENGTH_LONG).show();
        if (networkInfo != null && networkInfo.isConnected()) {
            new ProfileTask().execute(credentialManager.getUniversityUrl() + loginURL,
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
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String profileCacheString = credentialManager.getProfileCache();
        if(profileCacheString != null && profileCacheString != "") {
            Log.d(DEBUG_TAG, "profileCacheString: " + profileCacheString);
            Log.d(DEBUG_TAG, "profileCacheString: " + "cache exists");
            setTheProfileScreen(profileCacheString);
        } else {
            Log.d(DEBUG_TAG, "onActivityCreated: " + "no cache");
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
    public String readIt(InputStream stream, int len) throws IOException {
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

    public void setTheProfileScreen(String result){
        Log.d(DEBUG_TAG, "setTheProfileScreen: " + result);
        try {

            JSONObject resultJSON = new JSONObject(result);
            JSONArray dataListArry = resultJSON.getJSONArray("DataList");
            JSONObject dataList = (JSONObject) dataListArry.get(0);
            Log.e("emoveeee", dataList.toString());
            address = dataList.getJSONObject("Address");
            String addressString = "";
            keys = address.keys();
            Log.d(DEBUG_TAG, "keys " + keys.toString());
            /*while(keys.hasNext()){
                String key = (String)keys.next();
                Log.d(DEBUG_TAG, "for " + key + address.get(key));
                if ( address.get(key) != null ) {
                    addressString += address.get(key) + ", ";
                }
            }*/


            if(address.get("Address") != "null") addressString += address.get("Address") + ", ";
            if(address.get("State") != "null") addressString += address.get("State") + ", ";
            if(address.get("Country") != "null") addressString += address.get("Country") + ". ";
            TextView addressHolder = (TextView) getActivity().findViewById(R.id.addressHolder);

            addressHolder.setText(addressString);
            LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.aboutValuesCont);
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            linearLayout.removeAllViews();
            if(dataList.getString("FirstName") != "null"){
                View FirstName  = inflater.inflate(R.layout.row_profile_about_us, linearLayout, false);
                // set item content in view
                linearLayout.addView(FirstName);
                ((TextView) FirstName.findViewById(R.id.description_1)).setText(dataList.getString("FirstName"));
                ((ImageView) FirstName.findViewById(R.id.profileRowIcon)).setImageResource(R.drawable.ic_name);
            }
            if(dataList.getString("Designation") != "null"){
                View Designation  = inflater.inflate(R.layout.row_profile_about_us, linearLayout, false);
                // set item content in view
                linearLayout.addView(Designation);
                ((TextView) Designation.findViewById(R.id.description_1)).setText(dataList.getString("Designation") + dataList.getString("Department"));
                ((ImageView) Designation.findViewById(R.id.profileRowIcon)).setImageResource(R.drawable.ic_bookmark_outline);
            }
            if(dataList.getString("Email") != "null"){
                View Email  = inflater.inflate(R.layout.row_profile_about_us, linearLayout, false);
                // set item content in view
                linearLayout.addView(Email);
                ((TextView) Email.findViewById(R.id.description_1)).setText(dataList.getString("Email"));
                ((ImageView) Email.findViewById(R.id.profileRowIcon)).setImageResource(R.drawable.ic_mail_outline_black);
            }
            if(dataList.getString("Phone") != "null"){
                View Phone  = inflater.inflate(R.layout.row_profile_about_us, linearLayout, false);
                // set item content in view
                linearLayout.addView(Phone);
                ((TextView) Phone.findViewById(R.id.description_1)).setText(dataList.getString("Phone"));
                ((ImageView) Phone.findViewById(R.id.profileRowIcon)).setImageResource(R.drawable.ic_call);
            }
            if(dataList.getString("DateOfBirth") != "null"){
                View DateOfBirth  = inflater.inflate(R.layout.row_profile_about_us, linearLayout, false);
                // set item content in view
                linearLayout.addView(DateOfBirth);
                ((TextView) DateOfBirth.findViewById(R.id.description_1)).setText("05/04/1988");//dataList.getString("DateOfBirth")
                ((ImageView) DateOfBirth.findViewById(R.id.profileRowIcon)).setImageResource(R.drawable.ic_dob);
            }
            if(dataList.getString("DOJ") != "null"){
                View DOJ  = inflater.inflate(R.layout.row_profile_about_us, linearLayout, false);
                // set item content in view
                linearLayout.addView(DOJ);
                ((TextView) DOJ.findViewById(R.id.description_1)).setText("16/12/2012");//dataList.getString("DOJ")
                ((ImageView) DOJ.findViewById(R.id.profileRowIcon)).setImageResource(R.drawable.ic_doj);
            }



        } catch (Exception e){
            Log.e(DEBUG_TAG, "setTheProfileScreen--: " +  e.toString() );
        }


    }

    // Network code
    private class ProfileTask extends AsyncTask<String, Void, String> {
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
                Log.e("resultJSON ", result + "");
                if (resultJSON.getInt("ServiceResult") == 0) {
                    credentialManager.setProfileCache(result);
                    // success
                    // Set the token and time for future use.
                    setTheProfileScreen(result);
                    Log.d(DEBUG_TAG, "onPostExecute: from network");
                } else {

                }
            } catch (Exception t) {
                Log.e("JSON error", t + " Could not parse malformed JSON: \"" + result + "\"");
            }
            /*Intent i = new Intent(LoginActivity.this, Home.class);
            startActivity(i);*/
        }
    }
}
