package com.ar.qr.adhaarcard.adhaarcardqr;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.NativeExpressAdView;
import com.google.android.gms.vision.barcode.Barcode;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

public class MainActivity extends AppCompatActivity  {
    private AdView mAdView;
    CardView cardView,c2;
    boolean flag=false;
    DataBAse dataBAse;
    LinearLayout linearLayout,linearLayoutc,linearLayoutbr;
    TextView uidtext,nametext,dobtext,addtext,gntext,result,posttext,statetxt,empty;
    String uid,name,dob,vtc,gn,post,state,district, posttextq,co;
    public static final int REQUEST_CODE = 100;
    public static final int PERMISSION_REQUEST = 200;
    AnimatorSet animationSet;
    XmlPullParserFactory pullParserFactory;

    Handler handler = new Handler();
    RelativeLayout relativeLayout;
     ImageView imageView6;
    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        relativeLayout=(RelativeLayout)findViewById(R.id.r1l);
        linearLayout=(LinearLayout)findViewById(R.id.click1_2);
        linearLayoutc=(LinearLayout)findViewById(R.id.card_save);
        linearLayoutbr=(LinearLayout)findViewById(R.id.click1_1);
        uidtext=(TextView)findViewById(R.id.textView1);
        imageView6=(ImageView)findViewById(R.id.imageView3);
        nametext=(TextView)findViewById(R.id.textView2);
        dobtext=(TextView)findViewById(R.id.textView3);
        gntext=(TextView)findViewById(R.id.textView4);
        dataBAse=new DataBAse(MainActivity.this);
        animationSet = new AnimatorSet();
        statetxt=(TextView)findViewById(R.id.textView14);
        result=(TextView)findViewById(R.id.result);
        cardView = (CardView) findViewById(R.id.cardView2);
        c2 = (CardView) findViewById(R.id.cardView);
        c2.setVisibility(View.GONE);

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        NativeExpressAdView adView = (NativeExpressAdView)findViewById(R.id.adView1);

        AdRequest request = new AdRequest.Builder().build();
        adView.loadAd(request);
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                imageView6.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                imageView6.setVisibility(View.GONE);

            }
        });


        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA}, PERMISSION_REQUEST);


        }else {
            cardView.post(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                    startActivityForResult(intent, REQUEST_CODE);
                }
            });


        }


        linearLayoutbr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transitionName = getString(R.string.transition_string);
                Intent intent=new Intent(MainActivity.this,ScanActivity.class);

                ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this
                        ,relativeLayout,transitionName);
                ActivityCompat.startActivity(MainActivity.this,intent, activityOptionsCompat.toBundle());
            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transitionName = getString(R.string.transition_string);
                Intent intent=new Intent(MainActivity.this,Main2Activity.class);

                ActivityOptionsCompat activityOptionsCompat=ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this
                ,relativeLayout,transitionName);
                ActivityCompat.startActivity(MainActivity.this,intent, activityOptionsCompat.toBundle());
            }
        });


    /*    mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-4855672100917117/9769702189");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });*/


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });





        if(flag){

        }


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                final Barcode barcode = data.getParcelableExtra("barcode");

                result.post(new Runnable() {
                    @Override
                    public void run() {
                        String strings = barcode.displayValue;
                        try {
                            pullParserFactory = XmlPullParserFactory.newInstance();
                            XmlPullParser parser = pullParserFactory.newPullParser();
                            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                            parser.setInput(new StringReader(strings));

                            int eventType = parser.getEventType();
                            while (eventType != XmlPullParser.END_DOCUMENT) {
                                if (eventType == XmlPullParser.START_DOCUMENT) {


                                } else if (eventType == XmlPullParser.START_TAG && CardData.AADHAAR_DATA_TAG.equals(parser.getName())) {
                                    // extract data from tag
                                    //uid
                                    uid = parser.getAttributeValue(null, CardData.AADHAR_UID_ATTR);
                                    //name

                                    name = parser.getAttributeValue(null,CardData.AADHAR_NAME_ATTR);
                                    //gender
                                    gn = parser.getAttributeValue(null,CardData.AADHAR_GENDER_ATTR);
                                    // year of birth
                                    dob= parser.getAttributeValue(null,CardData.AADHAR_YOB_ATTR);
                                    // care of
                                    co = parser.getAttributeValue(null,CardData.AADHAR_CO_ATTR);
                                    // village Tehsil
                                    vtc = parser.getAttributeValue(null,CardData.AADHAR_VTC_ATTR);
                                    // Post Office
                                    posttextq = parser.getAttributeValue(null,CardData.AADHAR_PO_ATTR);
                                    // district
                                    district = parser.getAttributeValue(null,CardData.AADHAR_DIST_ATTR);                                    // state
                                    state = parser.getAttributeValue(null,CardData.AADHAR_STATE_ATTR);
                                    // Post Code
                                    post = parser.getAttributeValue(null,CardData.AADHAR_PC_ATTR);

                                } else if (eventType == XmlPullParser.END_TAG) {


                                } else if (eventType == XmlPullParser.TEXT) {


                                }
                                // update eventType
                                eventType = parser.next();
                            }

                            c2.setVisibility(View.VISIBLE);

                            ObjectAnimator card_y = ObjectAnimator.ofFloat(c2, View.TRANSLATION_X, 70);
                            card_y.setDuration(2500);
                            card_y.setRepeatMode(ValueAnimator.REVERSE);
                            card_y.setRepeatCount(ValueAnimator.INFINITE);
                            card_y.setInterpolator(new LinearInterpolator());
                            animationSet.playTogether(card_y);
                            animationSet.start();
                           final String gnfull;
                            if(gn.equals("M"))
                            {
                                gntext.setText("GANDER:_MALE");
                                gnfull="MALE";
                            }else {
                                gntext.setText("GANDER:_FEMALE");
                                gnfull="FEMALE";
                            }

                            uidtext.setText("UID:_"+uid);
                            nametext.setText("NAME:_"+name);
                            dobtext.setText("DOB:_"+dob);

                            statetxt.setText("ADDRESS:_"+state+"  "+vtc+"  "+post);
                         final String addco=state+"  "+vtc+"  "+post;
/*
                            handler.postDelayed(runnable,3*1000);*/
                            linearLayoutc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dataBAse.addContact(new MsqliteSting(uid,name,gnfull,dob,addco,vtc,posttextq,district,state,post));
                                    Toast.makeText(MainActivity.this,"ADHAAR SAVED",Toast.LENGTH_LONG).show();


                                }
                            });


                        }


                        catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }


    }
       /*Runnable runnable=new Runnable() {
           @Override
           public void run() {
               if (mInterstitialAd.isLoaded()) {
                   mInterstitialAd.show();
               } else {
                   Log.d("TAG", "The interstitial wasn't loaded yet.");
               }

           }
       };*/

}