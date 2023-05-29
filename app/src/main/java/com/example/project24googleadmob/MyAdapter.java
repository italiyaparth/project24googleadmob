package com.example.project24googleadmob;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project24googleadmob.databinding.ActivityMain2ItemsBinding;
import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.nativead.NativeAd;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyClass> {

    MainActivity2 mainActivity2;

    public MyAdapter(MainActivity2 mainActivity2) { this.mainActivity2 = mainActivity2; }


    class MyClass extends RecyclerView.ViewHolder {

        TextView textViewName,textViewNumber;
        TemplateView templateView;
        public MyClass(@NonNull View itemView) {

            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewNumber = itemView.findViewById(R.id.textViewNumber);
            templateView=itemView.findViewById(R.id.my_template);
        }
    }

    @NonNull
    @Override
    public MyClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View convertView = LayoutInflater.from(mainActivity2).inflate(R.layout.activity_main2_items,parent,false);
        MyClass m = new MyClass(convertView);

        return m;

    }

    @Override
    public void onBindViewHolder(@NonNull MyClass holder, int position) {

        holder.textViewName.setText(Config.arrayName[position]);
        holder.textViewNumber.setText(Config.arrayNumber[position]);

        if( position % 4 == 0 ) {   // at specific position ad is visible else visibility is gone

            loadAdMethod(holder.templateView);   // custom method

            holder.templateView.setVisibility(View.VISIBLE);

        } else {

            holder.templateView.setVisibility(View.GONE);
        }

    }


//////////----------------------------  Native Ad ( admob )  -----------------------------//////////

    private void loadAdMethod(TemplateView templateView) {

        MobileAds.initialize(mainActivity2);

        AdLoader adLoader = new AdLoader.Builder(mainActivity2,"ca-app-pub-3940256099942544/2247696110")
                                        .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                                            @Override
                                            public void onNativeAdLoaded(NativeAd nativeAd) {

                                                NativeTemplateStyle styles = new NativeTemplateStyle.Builder().build();
                                                templateView.setStyles(styles);
                                                templateView.setNativeAd(nativeAd);
                                            }
                                        })
                                        .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public int getItemCount() { return Config.arrayName.length; }
}
