package com.project.admuc.qrgossip;

import java.util.ArrayList;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import static android.R.attr.bitmap;


public class ContactsActivity extends AppCompatActivity {
	private CollapsingToolbarLayout collapsingToolbarLayout = null;
	private ListView mListView;

	private Handler updateBarHandler;
	ArrayList<String> contactList;
	Cursor cursor;
	int counter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
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


		toolbarTextAppearance();
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});


		mListView = (ListView) findViewById(R.id.list);

		updateBarHandler =new Handler();
		// Since reading contacts takes more time, let's run it on a separate thread.
		new Thread(new Runnable() {
			@Override
			public void run() {
				getContacts();
			}
		}).start();
		// Set onclicklistener to the list item.
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				//TODO Do whatever you want with the list data
				//Toast.makeText(getApplicationContext(), "item clicked : \n"+contactList.get(position), Toast.LENGTH_SHORT).show();

				Intent newActivity = new Intent(getApplicationContext(), ContactsDetailActivity.class);
				newActivity.putExtra("Contacts", contactList.get(position));
				startActivity(newActivity);


			}
		});
	}
	public void getContacts() {
		contactList = new ArrayList<String>();
		String phoneNumber = null;
		String email = null;
		Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
		String _ID = ContactsContract.Contacts._ID;
		String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
		String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
		Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
		Uri EmailCONTENT_URI =  ContactsContract.CommonDataKinds.Email.CONTENT_URI;
		String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;

		StringBuffer output;
		ContentResolver contentResolver = getContentResolver();

		cursor = contentResolver.query(CONTENT_URI, null,null, null, null);


		// Iterate every contact in the phone
		if (cursor.getCount() > 0) {
			counter = 0;
			while (cursor.moveToNext()) {
				output = new StringBuffer();


				String contact_id = cursor.getString(cursor.getColumnIndex( _ID ));
				String name = cursor.getString(cursor.getColumnIndex( DISPLAY_NAME ));
				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex( HAS_PHONE_NUMBER )));

				if (hasPhoneNumber > 0) {
					output.append(name);
					//This is to read multiple phone numbers associated with the same contact
					Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[] { contact_id }, null);

					while (phoneCursor.moveToNext()) {
						phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
						output.append("\n"+phoneNumber);
					}
					phoneCursor.close();
				}


				// Add the contact to the ArrayList
				if(output.toString()!=" ")
				contactList.add(output.toString());
			}
			// ListView has to be updated using a ui thread
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_contacts_list_item, R.id.text1, contactList);
					mListView.setAdapter(adapter);
					Helper.getListViewSize(mListView);
				}
			});

		}
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
