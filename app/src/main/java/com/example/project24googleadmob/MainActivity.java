package com.example.project24googleadmob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.project24googleadmob.databinding.ActivityMainBinding;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private InterstitialAd mInterstitialAd; // Interstitial Ad ( admob )

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


//////////----------------------------  Banner Ad ( admob )  -----------------------------//////////

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        // AdUnitId = admob AD ID

        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

        binding.adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                super.onAdLoaded();
                binding.adView.setVisibility(View.VISIBLE);
            }
        });


//////////-------------------------  Interstitial Ad ( admob )  --------------------------//////////

        showInterstitialAdMethod();   // custom method // ad storing in object before showing ad

        binding.buttonShowAd.setOnClickListener(v -> {

            Intent intent=new Intent(MainActivity.this, MainActivity2.class);
            startActivity(intent);

            if (mInterstitialAd != null) { mInterstitialAd.show(MainActivity.this); }
            else { Log.d("TAG", "The interstitial ad wasn't ready yet."); }

            finish();
        });

    }


//////////-------------------------  Interstitial Ad ( admob )  --------------------------//////////
/////////--------------------  ad storing in object before showing ad (method)  -------------------//////////

    private void showInterstitialAdMethod() {

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712", adRequest,

            new InterstitialAdLoadCallback() {

                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {

                    // The mInterstitialAd reference will be null until an ad is loaded.
                    mInterstitialAd = interstitialAd;
                    Log.i("onLoad", "onAdLoaded");
                }

                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {

                    // Handle the error
                    Log.d("OnError", loadAdError.toString());
                    mInterstitialAd = null;
                }

            }
        );

    }

}