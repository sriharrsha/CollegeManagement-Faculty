package app.management.college.com.collegemanagement;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static app.management.college.com.collegemanagement.ActivitySplash.APPLIED_LEAVES;
import static app.management.college.com.collegemanagement.ActivitySplash.APPLY_LEAVE;
import static app.management.college.com.collegemanagement.ActivitySplash.EXTERNAL_EXAMS;
import static app.management.college.com.collegemanagement.ActivitySplash.FEEDBACK;
import static app.management.college.com.collegemanagement.ActivitySplash.INTERNAL_EXAMS;
import static app.management.college.com.collegemanagement.ActivitySplash.INVIGILATION;
import static app.management.college.com.collegemanagement.ActivitySplash.TIME_TABLE;

/**
 * Created by Sri Harrsha on 05-Aug-16.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {


    private static final String TAG = "Messaging Service";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
//        Toast.makeText(getApplicationContext(), remoteMessage.getMessageId().trim(), Toast.LENGTH_SHORT).show();
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), remoteMessage.getData().get("module").toLowerCase());

        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(), "");

        }


    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(String title, String messageBody, String module) {
        Intent i;
        switch (module) {
            case TIME_TABLE:
                i = new Intent(getApplicationContext(), TimeTableV3.class);
                break;
            case INVIGILATION:
                i = new Intent(getApplicationContext(), InvigilationDetails.class);

                break;
            case APPLY_LEAVE:
                i = new Intent(getApplicationContext(), ApplyLeave.class);
                break;
            case APPLIED_LEAVES:
                i = new Intent(getApplicationContext(), AppliedLeaves.class);

                break;
            case EXTERNAL_EXAMS:
                i = new Intent(getApplicationContext(), ExternalExams.class);
                break;
            case INTERNAL_EXAMS:
                i = new Intent(getApplicationContext(), InternalExams.class);
                break;
            case FEEDBACK:
                i = new Intent(getApplicationContext(), FeedbackList.class);
                break;
            default:
                i = new Intent(this, Home.class);
        }

        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
