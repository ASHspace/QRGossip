package com.project.admuc.qrgossip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ReaderActivity extends AppCompatActivity {
    private Button scan_btn;
    //private TextView mText;
    private EditText text1;

    private AudioManager myAudioManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);
        scan_btn = (Button) findViewById(R.id.scan_btn);
        text1 = (EditText) findViewById(R.id.text1);


        //mText = (TextView) findViewById( R.id.text );

        final Activity activity = this;
        myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.mhshafi.qrcode", Context.MODE_PRIVATE);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }

            else{
                //sharedPreferences.edit().putString("Data", result.getContents()).apply();
                //String Data = sharedPreferences.getString("Data", "");
                //mText.setText(Data.toString());
                //text1.setText(Data.toString());

                String scan = result.getContents().toString();
                String scanArray[] = scan.split("[\\r\\n]+");

                int foo = Integer.parseInt(scanArray[0]);
                int foo1 = Integer.parseInt(scanArray[1]);
                int foo2 = Integer.parseInt(scanArray[2]);

                if(foo == 0){
                    myAudioManager.setStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, 0);
                }else if(foo == 15){
                    int target = myAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
                    myAudioManager.setStreamVolume(AudioManager.STREAM_RING, target, 0);
                }else{
                    myAudioManager.setStreamVolume(AudioManager.STREAM_RING, foo, 0);
                }
                /*else if(foo == 8){
                    int target = myAudioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
                    float target1 = target / 8;
                    int target2 = foo * (int)target1;
                    myAudioManager.setStreamVolume(AudioManager.STREAM_RING, target2, 0);
                }*/

                if(foo1 == 0){
                    myAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, 0);
                }else if(foo1 == 15){
                    int target = myAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                    myAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, target, 0);
                } else{
                    myAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, foo1, 0);
                }

                if(foo2 == 0){
                    myAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, 0);
                }else if(foo2 == 15){
                    int target = myAudioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION);
                    myAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, target, 0);
                } else{
                    myAudioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, foo2, 0);
                }

            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
