package com.prismos.settings;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
    private LinearLayout root, body;
    private SharedPreferences prefs;
    private boolean dark;
    private int bg, card, fg, secondary;
    private final int purple=Color.rgb(126,107,239);
    private final int blue=Color.rgb(83,169,246);

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        prefs=getSharedPreferences("prism_preferences",MODE_PRIVATE);
        showHome();
    }
    private int dp(float value) { return (int)(value*getResources().getDisplayMetrics().density+0.5f); }
    private GradientDrawable shape(int color,int radius) {
        GradientDrawable g=new GradientDrawable(); g.setColor(color);g.setCornerRadius(dp(radius));return g;
    }
    private TextView text(String value,int size,boolean bold,int color) {
        TextView t=new TextView(this);t.setText(value);t.setTextSize(size);t.setTextColor(color);
        t.setTypeface(Typeface.create("sans-serif",bold?Typeface.BOLD:Typeface.NORMAL));
        t.setGravity(Gravity.CENTER_VERTICAL);return t;
    }
    private void init(String title,boolean home) {
        dark=prefs.getBoolean("dark",true);
        bg=Color.parseColor(dark?"#111528":"#F3F4FA");
        card=Color.parseColor(dark?"#252B46":"#FFFFFF");
        fg=Color.parseColor(dark?"#FFFFFF":"#20243B");
        secondary=Color.parseColor(dark?"#B9BED4":"#656C83");
        ScrollView scroll=new ScrollView(this);scroll.setFillViewport(true);scroll.setBackgroundColor(bg);
        root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(18),dp(26),dp(18),dp(28));
        scroll.addView(root);setContentView(scroll);
        if(!home){TextView back=text("‹   Prism Settings",16,false,blue);back.setPadding(0,0,0,dp(18));back.setOnClickListener(v->showHome());root.addView(back);}
        TextView heading=text(title,32,true,fg);heading.setPadding(dp(4),dp(8),0,dp(20));root.addView(heading);
        body=new LinearLayout(this);body.setOrientation(LinearLayout.VERTICAL);root.addView(body);
    }
    private LinearLayout panel() {
        LinearLayout p=new LinearLayout(this);p.setOrientation(LinearLayout.VERTICAL);p.setPadding(dp(15),dp(8),dp(15),dp(8));p.setBackground(shape(card,24));
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2);lp.bottomMargin=dp(14);body.addView(p,lp);return p;
    }
    private void row(LinearLayout panel,String emoji,String title,String detail,Runnable action) {
        LinearLayout r=new LinearLayout(this);r.setOrientation(LinearLayout.HORIZONTAL);r.setGravity(Gravity.CENTER_VERTICAL);r.setPadding(dp(2),dp(13),dp(2),dp(13));
        TextView icon=text(emoji,23,false,fg);icon.setGravity(Gravity.CENTER);r.addView(icon,new LinearLayout.LayoutParams(dp(44),dp(44)));
        LinearLayout labels=new LinearLayout(this);labels.setOrientation(LinearLayout.VERTICAL);labels.setPadding(dp(8),0,0,0);
        labels.addView(text(title,17,true,fg));TextView sub=text(detail,12,false,secondary);sub.setPadding(0,dp(4),0,0);labels.addView(sub);
        r.addView(labels,new LinearLayout.LayoutParams(0,-2,1));r.addView(text("›",25,false,secondary));
        r.setOnClickListener(v->action.run());panel.addView(r);
    }
    private void open(String action) {
        try { startActivity(new Intent(action)); }
        catch(Exception e) { Toast.makeText(this,"This setting isn't available on this device",Toast.LENGTH_LONG).show(); }
    }
    private void showHome(){
        init("Settings",true);
        LinearLayout welcome=panel();row(welcome,"✦","PrismOS Samsung Edition","Android 11 · Standalone preview",()->showAbout());
        LinearLayout p=panel();
        row(p,"◉","Connections","Wi-Fi, Bluetooth, mobile network",()->showConnections());
        row(p,"◈","Display & Font Studio","Brightness, Inter typography, appearance",()->showDisplay());
        row(p,"✿","Wallpaper & style","Themes, color and Balanced Glass",()->showStyle());
        row(p,"♫","Sounds & vibration","Volume, ringtones and feedback",()->open(Settings.ACTION_SOUND_SETTINGS));
        row(p,"♧","Notifications","Manage application alerts",()->open(Settings.ACTION_APP_NOTIFICATION_SETTINGS));
        LinearLayout q=panel();
        row(q,"◇","Privacy & security","Permissions and device protection",()->open(Settings.ACTION_SECURITY_SETTINGS));
        row(q,"▣","Apps","Installed apps and permissions",()->open(Settings.ACTION_APPLICATION_SETTINGS));
        row(q,"⚡","Battery","Battery usage and optimization",()->open(Settings.ACTION_BATTERY_SAVER_SETTINGS));
        row(q,"⚙","Accessibility","Accessibility features",()->open(Settings.ACTION_ACCESSIBILITY_SETTINGS));
        LinearLayout x=panel();
        row(x,"✧","Prism features","Island, Edge, Modes and Game Booster",()->showFeatures());
        row(x,"ⓘ","About PrismOS","Version and development status",()->showAbout());
    }
    private void showConnections(){init("Connections",false);LinearLayout p=panel();
        row(p,"◉","Wi-Fi","Manage wireless networks",()->open(Settings.ACTION_WIFI_SETTINGS));
        row(p,"ᛒ","Bluetooth","Pair and manage devices",()->open(Settings.ACTION_BLUETOOTH_SETTINGS));
        row(p,"▥","SIM & mobile network","Carrier and data settings",()->open(Settings.ACTION_WIRELESS_SETTINGS));
        row(p,"⌁","Hotspot & tethering","Share your connection",()->open(Settings.ACTION_WIRELESS_SETTINGS));
    }
    private void showDisplay(){init("Display & Font Studio",false);LinearLayout p=panel();
        row(p,"☼","Brightness & display","Open Android display controls",()->open(Settings.ACTION_DISPLAY_SETTINGS));
        row(p,"Aa","Font preview","Inter · 17px · Medium (500)",()->showFont());
        Switch sw=new Switch(this);sw.setText("Dark appearance");sw.setTextColor(fg);sw.setTextSize(17);sw.setChecked(dark);
        sw.setPadding(dp(10),dp(15),dp(10),dp(15));sw.setOnCheckedChangeListener((button,on)->{prefs.edit().putBoolean("dark",on).apply();showDisplay();});p.addView(sw);
    }
    private void showFont(){init("Font Studio",false);LinearLayout p=panel();
        TextView sample=text("PrismOS\nBeautiful design. Smooth experience.",20,true,fg);sample.setPadding(dp(12),dp(24),dp(12),dp(24));p.addView(sample);
        row(p,"Aa","Preferred font","Inter · Medium (500) · 17px",()->{});
        TextView note=text("Inter is a planned ROM font. This prototype uses Android's installed sans-serif font until Inter is bundled and licensed font assets are integrated.",14,false,secondary);note.setPadding(dp(8),dp(16),dp(8),dp(18));p.addView(note);
        row(p,"⚙","Android font size","Open system display settings",()->open(Settings.ACTION_DISPLAY_SETTINGS));
    }
    private void showStyle(){init("Wallpaper & style",false);LinearLayout p=panel();
        row(p,"◈","Balanced Glass","Default design mode · concept preview",()->Toast.makeText(this,"Balanced Glass is the selected design mode",Toast.LENGTH_SHORT).show());
        row(p,"✿","Wallpapers","Open Android wallpaper picker",()->open(Intent.ACTION_SET_WALLPAPER));
        row(p,"◉","Blue + purple","PrismOS accent palette",()->{});
    }
    private void showFeatures(){init("Prism features",false);LinearLayout p=panel();
        for(String name:new String[]{"Prism Island","Prism Edge","Prism Themes","Prism Modes","Game Booster","Privacy Hub","App Lock","Smart Widgets"}) {
            row(p,"✧",name,"Planned for ROM integration",()->Toast.makeText(this,"Not implemented in this prototype",Toast.LENGTH_SHORT).show());
        }
    }
    private void showAbout(){init("About PrismOS",false);LinearLayout p=panel();
        row(p,"✦","Prism Settings","Prototype 0.1 · Android 11+",()->{});
        row(p,"▣","Device information","Open Android device details",()->open(Settings.ACTION_DEVICE_INFO_SETTINGS));
        TextView note=text("This app does not replace system Settings. Protected controls are delegated to Android. No system partitions are modified.",14,false,secondary);
        note.setPadding(dp(8),dp(18),dp(8),dp(18));p.addView(note);
    }
    @Override public void onBackPressed(){showHome();}
}
