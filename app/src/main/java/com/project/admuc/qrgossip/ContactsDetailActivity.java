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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class ContactsDetailActivity extends AppCompatActivity {

	private CollapsingToolbarLayout collapsingToolbarLayout = null;
	MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
	String s;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_contacts_detail);


		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		s = getIntent().getStringExtra("Contacts");
		collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
		collapsingToolbarLayout.setTitle("Details");

		ImageView imageView = (ImageView)findViewById(R.id.profile_id);
		imageView.setImageResource(R.drawable.menu_contacts);



		TextView textView1 = (TextView)findViewById(R.id.label1);
		textView1.setText("Name: "+s.split("\n")[0]);

		TextView textView2 = (TextView)findViewById(R.id.label2);
		textView2.setText("Phone: "+s.split("\n")[1]);

		toolbarTextAppearance();

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_main);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				try{
					BitMatrix bitMatrix = multiFormatWriter.encode(
							"Contacts"+" "+ s,
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
