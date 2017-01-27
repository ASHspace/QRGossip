package com.project.admuc.qrgossip;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.project.admuc.qrgossip.adaptor.MobileArrayAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ListView listItemView;
    FloatingActionButton fab;

    static final String[] MENU_ITEM =
            new String[] { "Profile", "Clipboard", "Contacts", "Messages", "Web Links", "Help", "About"};
    private AudioManager audioManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setVolumeControlStream(AudioManager.STREAM_RING);
        setContentView(R.layout.activity_main);

        checkAndRequestPermissions();

        listItemView = (ListView)findViewById(R.id.listView_main);
        fab = (FloatingActionButton)findViewById(R.id.fab_main);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2, android.R.id.text1, listItemsValue);

        //listItemView.setAdapter(adapter);
        listItemView.setAdapter(new MobileArrayAdapter(this, MENU_ITEM));
        final Activity activity = this;


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(MainActivity.this, "Fab Clicked", Toast.LENGTH_LONG).show();
                //Intent readActivity = new Intent(view.getContext(), ReaderActivity.class);
                //scanActivity.putExtra("MENU_ITEM", v.getText());
                //startActivity(readActivity);

                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.setOrientationLocked(true);
                integrator.initiateScan();


            }
        });


        listItemView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView < ? > adapter, View view,int position, long arg){
            // TODO Auto-generated method stub
            TextView v = (TextView) view.findViewById(R.id.label);
           // Toast.makeText(getApplicationContext(), "selected Item Name is " + v.getText(), Toast.LENGTH_SHORT).show();

                switch (position) {
                    case 0:
                        Intent newActivity0 = new Intent(view.getContext(), ProfileActivity.class);
                        newActivity0.putExtra("MENU_ITEM", v.getText());
                        startActivity(newActivity0);
                        break;
                    case 1:
                        Intent newActivity1 = new Intent(view.getContext(), ClipboardActivity.class);
                        newActivity1.putExtra("MENU_ITEM", v.getText());
                        startActivity(newActivity1);
                        break;
                    case 2:
                        Intent newActivity2 = new Intent(view.getContext(), ContactsActivity.class);
                        newActivity2.putExtra("MENU_ITEM", v.getText());
                        startActivity(newActivity2);
                        break;
                    case 3:
                        Intent newActivity3 = new Intent(view.getContext(), SMSActivity.class);
                        newActivity3.putExtra("MENU_ITEM", v.getText());
                        startActivity(newActivity3);
                        break;
                    case 4:
                        Intent newActivity4 = new Intent(view.getContext(), WebActivity.class);
                        newActivity4.putExtra("MENU_ITEM", v.getText());
                        startActivity(newActivity4);
                        break;
                    case 5:
                        Intent newActivity5 = new Intent(view.getContext(), HelpActivity.class);
                        newActivity5.putExtra("MENU_ITEM", v.getText());
                        startActivity(newActivity5);
                        break;
                    case 6:
                        Intent newActivity6 = new Intent(view.getContext(), AboutActivity.class);
                        newActivity6.putExtra("MENU_ITEM", v.getText());
                        startActivity(newActivity6);
                        break;
                    default:
                        // Nothing do!
                }

            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.mhshafi.qrcode", Context.MODE_PRIVATE);

        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Scanning Canceled", Toast.LENGTH_SHORT).show();
            } else {
                    String dataContents=result.getContents();
                    String dataCode[] = dataContents.split(" ");

                    if(dataCode[0].equals("Profile")) {
                        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                        int val=(int)((Float.valueOf(dataCode[4])/20)*audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM));

                        audioManager.setStreamVolume(AudioManager.STREAM_RING,(int)((Float.valueOf(dataCode[1])/20)*audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)), 0);
                        audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION,(int)((Float.valueOf(dataCode[2])/20)*audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)), 0);
                        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,(int)((Float.valueOf(dataCode[3])/20)*audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)), 0);
                        audioManager.setStreamVolume(AudioManager.STREAM_ALARM,(int)((Float.valueOf(dataCode[4])/20)*audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)), 0);
                        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM,(int)((Float.valueOf(dataCode[5])/20)*audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM)), 0);
                        audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,(int)((Float.valueOf(dataCode[6])/20)*audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL)), 0);

                        Toast.makeText(this, "Sound Profile Applied Successfully", Toast.LENGTH_LONG).show();

                    } else if(dataCode[0].equals("Clipboard")) {
                        String dataClipboard=result.getContents();
                        String dataSpitClipboard[]=dataClipboard.split(" ",2);

                        Intent newClipboard = new Intent(this, QRTextActivity.class);
                        newClipboard.putExtra("Clipboard", dataSpitClipboard[1]);
                        startActivity(newClipboard);

                    } else if(dataCode[0].equals("Contacts")) {
                        Intent intent = new Intent(Intent.ACTION_INSERT);
                        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

                        Intent addContactIntent = new Intent(Intent.ACTION_INSERT);
                        addContactIntent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                        addContactIntent.putExtra(ContactsContract.Intents.Insert.NAME, result.getContents().split("\n")[0].replace("Contacts ",""));
                        addContactIntent.putExtra(ContactsContract.Intents.Insert.PHONE,result.getContents().split("\n")[1]);
                        startActivity(addContactIntent);

                    } else if(dataCode[0].equals("Messages")) {
                        String dataClipboard=result.getContents();
                        String dataSpitClipboard[]=dataClipboard.split(" ",2);

                        Intent newClipboard = new Intent(this, QRTextActivity.class);
                        newClipboard.putExtra("Clipboard", dataSpitClipboard[1]);
                        startActivity(newClipboard);

                    } else if(dataCode[0].equals("Web")) {
                        String dataClipboard=result.getContents();
                        String dataSpitClipboard[]=dataClipboard.split(" ",2);

                        Intent newClipboard = new Intent(this, WebTextActivity.class);
                        newClipboard.putExtra("Web", dataSpitClipboard[1]);
                        startActivity(newClipboard);

                    } else {
                        Toast.makeText(this, "Invalid Data Found", Toast.LENGTH_LONG).show();

                    }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private  void checkAndRequestPermissions() {
        String [] permissions=new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_SMS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String permission:permissions) {
            if (ContextCompat.checkSelfPermission(this,permission )!= PackageManager.PERMISSION_GRANTED){
                listPermissionsNeeded.add(permission);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
        }
    }

}
