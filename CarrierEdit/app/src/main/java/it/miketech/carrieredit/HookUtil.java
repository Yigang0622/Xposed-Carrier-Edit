package it.miketech.carrieredit;
import android.widget.TextView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by Mike on 8/22/2016.
 */
public class HookUtil implements IXposedHookLoadPackage {

    private Boolean isEnabled = false;
    private String label = "";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {


        if (!loadPackageParam.packageName.equals("com.android.systemui"))
            return;



        XposedHelpers.findAndHookMethod("com.android.keyguard.CarrierText", loadPackageParam.classLoader, "updateCarrierText", new XC_MethodHook() {


            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
                XSharedPreferences xSharedPreferences = new XSharedPreferences("miketech.it.carrieredit","configuration");
                isEnabled = xSharedPreferences.getBoolean("enable",false);
                label = xSharedPreferences.getString("carrierText","");

                XposedBridge.log("Enable? "+ isEnabled);
                XposedBridge.log("carrierText: "+ label);


            }

            @Override
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam param) throws Throwable {

                if (!isEnabled)
                    return;

                TextView mLabel = (TextView) param.thisObject;
                mLabel.setText(label);

                XposedBridge.log(loadPackageParam.packageName);
                XposedBridge.log("Changing Carrier Done");
            }
        });

    }
}
