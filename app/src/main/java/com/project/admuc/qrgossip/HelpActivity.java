package com.project.admuc.qrgossip;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class HelpActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String s = getIntent().getStringExtra("MENU_ITEM");
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(s);

        ImageView imageView = (ImageView)findViewById(R.id.profile_id);
        imageView.setImageResource(R.drawable.man);

        toolbarTextAppearance();

        if (s.equals("Profile")) {
            imageView.setImageResource(R.drawable.menu_profile);
        } else if (s.equals("Clipboard")) {
            imageView.setImageResource(R.drawable.menu_clipboard);
        } else if (s.equals("Contacts")) {
            imageView.setImageResource(R.drawable.menu_contacts);
        } else if (s.equals("Web Links")) {
            imageView.setImageResource(R.drawable.menu_link);
        } else if (s.equals("Messages")) {
            imageView.setImageResource(R.drawable.menu_messages);
        } else if (s.equals("Help")) {
            imageView.setImageResource(R.drawable.menu_help);
        } else if (s.equals("About")) {
            imageView.setImageResource(R.drawable.menu_about);
        } else {
            imageView.setImageResource(R.drawable.menu_help);
        }


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


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
