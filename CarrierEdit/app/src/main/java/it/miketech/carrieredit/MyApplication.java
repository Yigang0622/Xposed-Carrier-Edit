package it.miketech.carrieredit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by Mike on 8/22/2016.
 */
public class MyApplication extends Application {

    private static MyApplication instance;
    private SharedPreferences sharedPreferences;

    public void setContext(Context mContext) {
        this.mContext = mContext;
        sharedPreferences =mContext.getSharedPreferences("configuration", Activity.MODE_WORLD_READABLE);
    }

    private Context mContext;


    public void saveCarrierText(String text){
        SharedPreferences.Editor  editor  =  sharedPreferences.edit();
        editor.putString("carrierText",text);
        editor.commit();
        Toast.makeText(mContext,"Carrier label modified!",Toast.LENGTH_LONG).show();
    }

    public void setHookEnable(Boolean hookEnable){
        SharedPreferences.Editor  editor  =  sharedPreferences.edit();
        editor.putBoolean("enable",hookEnable);

        if (!hookEnable){
            editor.remove("carrierText");
        }

        editor.commit();
    }

    public Boolean isHookEnable(){
        return sharedPreferences.getBoolean("enable",false);
    }

    public String readCarrierText(){
        sharedPreferences =mContext.getSharedPreferences("configuration", 0);
        return sharedPreferences.getString("carrierText","");
    }

    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
