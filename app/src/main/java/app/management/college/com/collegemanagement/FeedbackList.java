package app.management.college.com.collegemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import app.management.college.com.collegemanagement.api.CollegeManagementApiService;
import app.management.college.com.collegemanagement.api.FeedbackList.DataList;

public class FeedbackList extends AppCompatActivity implements FeedbackFragment.OnListFragmentInteractionListener {

    private CollegeManagementApiService collegeApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);
        FeedbackFragment feedbackFragment=FeedbackFragment.newInstance(1);
        android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.list_container, feedbackFragment).commit();


    }


    @Override
    public void onListFragmentInteraction(DataList item) {
        Intent gotoFeedbackReply=new Intent(this,FeedbackReply.class);
      //  gotoFeedbackReply.putExtra("data", (Parcelable) item);
        startActivity(gotoFeedbackReply);
    }
}
