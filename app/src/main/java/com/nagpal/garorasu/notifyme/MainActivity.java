package com.nagpal.garorasu.notifyme;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button mNotifyButton,mCancelButton,mUpdateButton;
    private NotificationManager mNotifyManager;
    private	static	final	int	NOTIFICATION_ID	=	0;
    private static final String NOTIFICATION_GUIDE_URL = "https://developer.android.com/design/patterns/notifications.html";
    private	static	final	String	ACTION_UPDATE_NOTIFICATION	=
            "com.example.android.notifyme.ACTION_UPDATE_NOTIFICATION";
    private static final String ACTION_CANCEL_NOTIFICATION =
            "com.example.android.notifyme.ACTION_CANCEL_NOTIFICATION";
    private NotificationReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mReceiver = new NotificationReceiver();
        //Initialize and register the notification receiver
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_UPDATE_NOTIFICATION);
        intentFilter.addAction(ACTION_CANCEL_NOTIFICATION);
        registerReceiver(mReceiver, intentFilter);

        mNotifyButton	=	(Button)	findViewById(R.id.notify);
        mNotifyButton.setOnClickListener(new	View.OnClickListener()	{
            @Override
            public	void	onClick(View	view)	{
                    sendNotification();
            }
        });
        mCancelButton = (Button) findViewById(R.id.cancel);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelNotification();
            }
        });
        mUpdateButton = (Button) findViewById(R.id.update);
        mUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNotification();
            }
        });
        mNotifyButton.setEnabled(true);
        mUpdateButton.setEnabled(false);
        mCancelButton.setEnabled(false);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        if(id == R.id.action_settings){
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(menuItem);
    }
    public void sendNotification(){
        Intent	notificationIntent	=	new Intent(this,	MainActivity.class);

        PendingIntent	notificationPendingIntent	=	PendingIntent.getActivity(this,
                NOTIFICATION_ID,	notificationIntent,	PendingIntent.FLAG_UPDATE_CURRENT);

        Intent	learnMoreIntent	=	new	Intent(Intent.ACTION_VIEW,	Uri
                .parse(NOTIFICATION_GUIDE_URL));
        PendingIntent	learnMorePendingIntent	=	PendingIntent.getActivity
                (this,NOTIFICATION_ID,learnMoreIntent,PendingIntent.FLAG_ONE_SHOT);

        // Sets up the pending intent to cancel the notification,
        // delivered when the user dismisses the notification
        Intent cancelIntent = new Intent(ACTION_CANCEL_NOTIFICATION);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, cancelIntent, PendingIntent.FLAG_ONE_SHOT);

        Intent	updateIntent	=	new	Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent	updatePendingIntent	=	PendingIntent.getBroadcast
                (this,	NOTIFICATION_ID,	updateIntent,	PendingIntent.FLAG_ONE_SHOT);

        mNotifyManager	=	(NotificationManager)	getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder	notifyBuilder	=	new	NotificationCompat.Builder(this)
                .setContentTitle("You've	been	notified!")
                .setContentText("This	is	your	notification	text.")
                .setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_android)
                .addAction(R.drawable.ic_update,	"Update",	updatePendingIntent)
                .setDeleteIntent(cancelPendingIntent);
        Notification myNotification	=	notifyBuilder.build();
        mNotifyManager.notify(NOTIFICATION_ID,	myNotification);
        mNotifyButton.setEnabled(false);
        mUpdateButton.setEnabled(true);
        mCancelButton.setEnabled(true);
    }
    public	void	cancelNotification(){
        mNotifyManager.cancel(NOTIFICATION_ID);
        mNotifyButton.setEnabled(true);
        mUpdateButton.setEnabled(false);
        mCancelButton.setEnabled(false);
    }
    public void updateNotification(){
        Bitmap androidImage	=	BitmapFactory
                .decodeResource(getResources(),R.drawable.mascot_1);
        Intent	notificationIntent	=	new Intent(this,	MainActivity.class);

        PendingIntent	notificationPendingIntent	=	PendingIntent.getActivity(this,
                NOTIFICATION_ID,	notificationIntent,	PendingIntent.FLAG_UPDATE_CURRENT);

        Intent	learnMoreIntent	=	new	Intent(Intent.ACTION_VIEW,	Uri
                .parse(NOTIFICATION_GUIDE_URL));
        PendingIntent	learnMorePendingIntent	=	PendingIntent.getActivity
                (this,NOTIFICATION_ID,learnMoreIntent,PendingIntent.FLAG_ONE_SHOT);

        Intent	updateIntent	=	new	Intent(ACTION_UPDATE_NOTIFICATION);
        PendingIntent	updatePendingIntent	=	PendingIntent.getBroadcast
                (this,	NOTIFICATION_ID,	updateIntent,	PendingIntent.FLAG_ONE_SHOT);

        // Sets up the pending intent to cancel the notification,
        // delivered when the user dismisses the notification
        Intent cancelIntent = new Intent(ACTION_CANCEL_NOTIFICATION);
        PendingIntent cancelPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, cancelIntent, PendingIntent.FLAG_ONE_SHOT);

        mNotifyManager	=	(NotificationManager)	getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder	notifyBuilder	=	new	NotificationCompat.Builder(this)
                .setContentTitle("You've	been	notified!")
                .setAutoCancel(true)
                .setContentText("This	is	your	notification	text.")
                .setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_android)
                .setStyle(new	NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle("Notification	Updated!"))
                .addAction(R.drawable.ic_learn_more,"Learn More",learnMorePendingIntent)
                .setDeleteIntent(cancelPendingIntent);
        Notification myNotification	=	notifyBuilder.build();
        mNotifyManager.notify(NOTIFICATION_ID,	myNotification);
        mNotifyButton.setEnabled(false);
        mUpdateButton.setEnabled(false);
        mCancelButton.setEnabled(true);
    }
    @Override
    protected	void	onDestroy()	{
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
    private class NotificationReceiver extends BroadcastReceiver {

        public	NotificationReceiver()	{
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            switch (action){
                case ACTION_CANCEL_NOTIFICATION:
                    cancelNotification();
                    break;
                case ACTION_UPDATE_NOTIFICATION:
                    updateNotification();
                    break;
            }
        }
    }
}
