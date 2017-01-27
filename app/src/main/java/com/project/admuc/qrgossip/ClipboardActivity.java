package com.project.admuc.qrgossip;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import static android.R.id.edit;


public class ClipboardActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout = null;
    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clipboard);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        String s = getIntent().getStringExtra("MENU_ITEM");
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(s);

        ImageView imageView = (ImageView)findViewById(R.id.profile_id);
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

        editText=(EditText) findViewById(R.id.edit_clipboard);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //dynamicToolbarColor();

        toolbarTextAppearance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    BitMatrix bitMatrix = multiFormatWriter.encode(
                            "Clipboard"+" "+
                                    editText.getText(),
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


}
