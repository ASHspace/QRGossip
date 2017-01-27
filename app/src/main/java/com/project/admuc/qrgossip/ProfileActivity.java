package com.project.admuc.qrgossip;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;



public class ProfileActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    private TextView textView1,textView2,textView3,textView4,textView5,textView6;
    private SeekBar seekBar1,seekBar2,seekBar3,seekBar4,seekBar5,seekBar6;
    private int ring=0, notification=0, media=0, alarm=0, system=0, voice=0;
    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String s = getIntent().getStringExtra("MENU_ITEM");
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(s);

        final ImageView imageView = (ImageView)findViewById(R.id.profile_id);
        imageView.setImageResource(R.drawable.man);

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


        //dynamicToolbarColor();

        //textView.setText("Ring Volume: ");
        //textView.append("5/10");

        seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
        seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
        seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        seekBar4 = (SeekBar) findViewById(R.id.seekBar4);
        seekBar5 = (SeekBar) findViewById(R.id.seekBar5);
        seekBar6 = (SeekBar) findViewById(R.id.seekBar6);

        textView1 = (TextView) findViewById(R.id.label1);
        textView2 = (TextView) findViewById(R.id.label2);
        textView3 = (TextView) findViewById(R.id.label3);
        textView4 = (TextView) findViewById(R.id.label4);
        textView5 = (TextView) findViewById(R.id.label5);
        textView6 = (TextView) findViewById(R.id.label6);

        textView1.setText("Ringtone: "+seekBar1.getProgress() + "/" + seekBar1.getMax());
        textView2.setText("Notification: "+seekBar2.getProgress() + "/" + seekBar2.getMax());
        textView3.setText("Media: "+seekBar3.getProgress() + "/" + seekBar3.getMax());
        textView4.setText("Alarm: "+seekBar4.getProgress() + "/" + seekBar4.getMax());
        textView5.setText("System: "+seekBar5.getProgress() + "/" + seekBar5.getMax());
        textView6.setText("Voice: "+seekBar6.getProgress() + "/" + seekBar6.getMax());

        ring=seekBar1.getProgress();
        notification=seekBar2.getProgress();
        media=seekBar3.getProgress();
        alarm=seekBar4.getProgress();
        system=seekBar5.getProgress();
        voice=seekBar6.getProgress();

        seekBar1.setOnSeekBarChangeListener(this);
        seekBar2.setOnSeekBarChangeListener(this);
        seekBar3.setOnSeekBarChangeListener(this);
        seekBar4.setOnSeekBarChangeListener(this);
        seekBar5.setOnSeekBarChangeListener(this);
        seekBar6.setOnSeekBarChangeListener(this);




        //textView = (TextView) findViewById(R.id.value);
        //textView.setText("1/20");
        //seekBar.setProgress(10);

/*
        textView.setText(seekBar.getProgress() + "/" + seekBar.getMax());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            //int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
               // progress = progresValue;
                textView.setText(String.valueOf(progresValue) + "/" + seekBar.getMax());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do something here, if you want to do anything at the start of
                // touching the seekbar
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Display the value in textview
                //textView.setText(progress + "/" + seekBar.getMax());
            }
        });
*/


        toolbarTextAppearance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action"+ ring +"/"+notification +"/"+ media, Snackbar.LENGTH_LONG)
                       // .setAction("Action", null).show();
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(
                            "Profile"+" "+
                            Integer.toString(ring) +" "+
                            Integer.toString(notification) +" "+
                            Integer.toString(media)+ " "+
                            Integer.toString(alarm) +" "+
                            Integer.toString(system) +" "+
                            Integer.toString(voice),
                            BarcodeFormat.QR_CODE,300,300);

                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                    Intent intent = new Intent(view.getContext(), QRActivity.class);
                    intent.putExtra("bitmapImage", bitmap);
                    startActivity(intent);
                }
                catch (WriterException e){
                    e.printStackTrace();
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    /*private void dynamicToolbarColor() {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.instagram);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(R.attr.colorPrimary));
                collapsingToolbarLayout.setStatusBarScrimColor(palette.getMutedColor(R.attr.colorPrimaryDark));
            }
        });
    }*/


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

    public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2)
    {

        switch (arg0.getId())
        {
            case R.id.seekBar1:
                textView1.setText("Ringtone: "+String.valueOf(arg1) + "/" + seekBar1.getMax());
                ring=arg1;
                break;
            case R.id.seekBar2:
                textView2.setText("Notification: "+String.valueOf(arg1) + "/" + seekBar2.getMax());
                notification=arg1;
                break;
            case R.id.seekBar3:
                textView3.setText("Media: "+String.valueOf(arg1) + "/" + seekBar3.getMax());
                media=arg1;
                break;
            case R.id.seekBar4:
                textView4.setText("Alarm: "+String.valueOf(arg1) + "/" + seekBar4.getMax());
                alarm=arg1;
                break;
            case R.id.seekBar5:
                textView5.setText("System: "+String.valueOf(arg1) + "/" + seekBar5.getMax());
                system=arg1;
                break;
            case R.id.seekBar6:
                textView6.setText("Voice: "+String.valueOf(arg1) + "/" + seekBar6.getMax());
                voice=arg1;
                break;
            default:
                //Nothing
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // Do something here, if you want to do anything at the start of
        // touching the seekbar
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // Display the value in textview
        //textView.setText(progress + "/" + seekBar.getMax());
    }


}
