package com.project.admuc.qrgossip;

import java.util.ArrayList;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class SMSActivity extends AppCompatActivity {
    private static final String INBOX_URI = "content://sms/inbox";
    private static SMSActivity activity;
    private ArrayList<String> smsList = new ArrayList<String>();
    private ListView mListView;
    private ArrayAdapter<String> adapter;
    private CollapsingToolbarLayout collapsingToolbarLayout = null;

    public static SMSActivity instance() {
        return activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String s = getIntent().getStringExtra("MENU_ITEM");
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(s);

        ImageView imageView = (ImageView)findViewById(R.id.profile_id);
        imageView.setImageResource(R.drawable.menu_messages);
        toolbarTextAppearance();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        mListView = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this, R.layout.activity_sms_list,R.id.text1, smsList);

        mListView.setAdapter(adapter);



        mListView.setOnItemClickListener(MyItemClickListener);
        readSMS();
        Helper.getListViewSize(mListView);
    }
    @Override
    public void onStart() {
        super.onStart();
        activity = this;
    }
    public void readSMS() {
        ContentResolver contentResolver = getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse(INBOX_URI), null, null, null, null);
        int senderIndex = smsInboxCursor.getColumnIndex("address");
        int messageIndex = smsInboxCursor.getColumnIndex("body");
        if (messageIndex < 0 || !smsInboxCursor.moveToFirst()) return;
        adapter.clear();
        do {
            String sender = smsInboxCursor.getString(senderIndex);
            String message = smsInboxCursor.getString(messageIndex);
            //String formattedText = String.format(getResources().getString(R.string.sms_message), sender, message);
            //adapter.add(Html.fromHtml(formattedText).toString());
            adapter.add(sender+"\n"+message);
        } while (smsInboxCursor.moveToNext());
    }

    private OnItemClickListener MyItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
            try {
                //Toast.makeText(getApplicationContext(), adapter.getItem(pos), Toast.LENGTH_SHORT).show();

                Intent newActivity = new Intent(getApplicationContext(), SMSDetailActivity.class);
                newActivity.putExtra("Messages", adapter.getItem(pos));
                startActivity(newActivity);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    private void toolbarTextAppearance() {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsedappbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.expandedappbar);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}