package com.samsung.android.settings.wifi.mobileap.autohotspot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;

import com.samsung.android.settings.WifiApUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApAutoHotspotEnablingActivity extends AppCompatActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context mContext;
    public final AnonymousClass1 mOnAutoHotspotStateChangeListener =
            new WifiApAutoHotspotSwitchEnabler.OnStateChangeListener() { // from class:
                // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApAutoHotspotEnablingActivity.1
                @Override // com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApAutoHotspotSwitchEnabler.OnStateChangeListener, com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApFamilySharingSwitchEnabler.OnStateChangeListener
                public final void onStateChanged(int i) {
                    int i2 = WifiApAutoHotspotEnablingActivity.$r8$clinit;
                    MainClearConfirm$$ExternalSyntheticOutline0.m(
                            i,
                            "AutoHotspot onStateChanged() - resultCode: ",
                            "WifiApAutoHotspotEnablingActivity");
                    WifiApAutoHotspotEnablingActivity wifiApAutoHotspotEnablingActivity =
                            WifiApAutoHotspotEnablingActivity.this;
                    WifiApFrameworkUtils.isAutoHotspotSetOn(
                            wifiApAutoHotspotEnablingActivity.mContext);
                    String string =
                            wifiApAutoHotspotEnablingActivity
                                    .mContext
                                    .getResources()
                                    .getString(
                                            R.string
                                                    .wifi_ap_can_not_turn_on_auto_hotspot_for_suggestion_tip_card,
                                            WifiApUtils.getString(
                                                    wifiApAutoHotspotEnablingActivity.mContext,
                                                    R.string.wifi_ap_smart_tethering_title));
                    if (i == 5) {
                        Log.d(
                                "WifiApAutoHotspotEnablingActivity",
                                "AutoHotspot feature can`t be used:"
                                    + " RESULT_ERROR_TETHERING_RESTRICTED");
                        Toast.makeText(wifiApAutoHotspotEnablingActivity.mContext, string, 1)
                                .show();
                        WifiApAutoHotspotEnablingActivity.m1363$$Nest$msendResult(
                                wifiApAutoHotspotEnablingActivity, 5);
                        return;
                    }
                    if (i == 1) {
                        Log.d(
                                "WifiApAutoHotspotEnablingActivity",
                                "AutoHotspot feature can`t be used: RESULT_ERROR_AIRPLANE_MODE_ON");
                        Toast.makeText(wifiApAutoHotspotEnablingActivity.mContext, string, 1)
                                .show();
                        WifiApAutoHotspotEnablingActivity.m1363$$Nest$msendResult(
                                wifiApAutoHotspotEnablingActivity, 1);
                        return;
                    }
                    if (i == 2) {
                        Log.d(
                                "WifiApAutoHotspotEnablingActivity",
                                "AutoHotspot feature can`t be used: RESULT_ERROR_NO_SIM");
                        Toast.makeText(wifiApAutoHotspotEnablingActivity.mContext, string, 1)
                                .show();
                        WifiApAutoHotspotEnablingActivity.m1363$$Nest$msendResult(
                                wifiApAutoHotspotEnablingActivity, 2);
                        return;
                    }
                    if (i == 3) {
                        Log.d(
                                "WifiApAutoHotspotEnablingActivity",
                                "AutoHotspot feature can`t be used:"
                                    + " RESULT_ERROR_NO_ACTIVE_NETWORK");
                        Toast.makeText(wifiApAutoHotspotEnablingActivity.mContext, string, 1)
                                .show();
                        WifiApAutoHotspotEnablingActivity.m1363$$Nest$msendResult(
                                wifiApAutoHotspotEnablingActivity, 3);
                        return;
                    }
                    if (i == 6) {
                        Log.d(
                                "WifiApAutoHotspotEnablingActivity",
                                "AutoHotspot feature can`t be used:"
                                    + " RESULT_ERROR_DISABLED_NEARBY_SCANNING");
                        Toast.makeText(wifiApAutoHotspotEnablingActivity.mContext, string, 1)
                                .show();
                        WifiApAutoHotspotEnablingActivity.m1363$$Nest$msendResult(
                                wifiApAutoHotspotEnablingActivity, 6);
                        return;
                    }
                    if (i == 4) {
                        Log.d(
                                "WifiApAutoHotspotEnablingActivity",
                                "AutoHotspot feature can`t be used: RESULT_ERROR_NO_ACCOUNT");
                        Toast.makeText(wifiApAutoHotspotEnablingActivity.mContext, string, 1)
                                .show();
                        WifiApAutoHotspotEnablingActivity.m1363$$Nest$msendResult(
                                wifiApAutoHotspotEnablingActivity, 4);
                    } else if (i == 7) {
                        Log.d(
                                "WifiApAutoHotspotEnablingActivity",
                                "AutoHotspot feature can`t be used: RESULT_ERROR_DEFAULT_PASSWORD");
                        Toast.makeText(wifiApAutoHotspotEnablingActivity.mContext, string, 1)
                                .show();
                        WifiApAutoHotspotEnablingActivity.m1363$$Nest$msendResult(
                                wifiApAutoHotspotEnablingActivity, 7);
                    } else if (i == -1) {
                        Log.d(
                                "WifiApAutoHotspotEnablingActivity",
                                "AutoHotspot feature can be used: RESULT_OK");
                        WifiApAutoHotspotEnablingActivity.m1363$$Nest$msendResult(
                                wifiApAutoHotspotEnablingActivity, -1);
                        WifiApFrameworkUtils.setAutoHotspotDB(
                                wifiApAutoHotspotEnablingActivity.mContext, true);
                    } else {
                        Log.d(
                                "WifiApAutoHotspotEnablingActivity",
                                "AutoHotspot feature can`t be used: NO MATCHING RESULT");
                        Toast.makeText(wifiApAutoHotspotEnablingActivity.mContext, string, 1)
                                .show();
                        WifiApAutoHotspotEnablingActivity.m1363$$Nest$msendResult(
                                wifiApAutoHotspotEnablingActivity, 0);
                    }
                }
            };
    public WifiApAutoHotspotSwitchEnabler mWifiApAutoHotspotSwitchEnabler;

    /* renamed from: -$$Nest$msendResult, reason: not valid java name */
    public static void m1363$$Nest$msendResult(
            WifiApAutoHotspotEnablingActivity wifiApAutoHotspotEnablingActivity, int i) {
        wifiApAutoHotspotEnablingActivity.getClass();
        Log.i("WifiApAutoHotspotEnablingActivity", "sendResult: " + i);
        Intent intent = new Intent();
        intent.putExtra("result_message", "TODO MSG");
        wifiApAutoHotspotEnablingActivity.setResult(i != -1 ? 0 : -1, intent);
        wifiApAutoHotspotEnablingActivity.finish();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        Log.i("WifiApAutoHotspotEnablingActivity", "onCreate");
        super.onCreate(bundle);
        setContentView(R.layout.sec_wifi_ap_dummy_layout);
        this.mContext = getApplicationContext();
        WifiApAutoHotspotSwitchEnabler wifiApAutoHotspotSwitchEnabler =
                new WifiApAutoHotspotSwitchEnabler(this.mContext);
        this.mWifiApAutoHotspotSwitchEnabler = wifiApAutoHotspotSwitchEnabler;
        wifiApAutoHotspotSwitchEnabler.mOnStateChangeListener =
                this.mOnAutoHotspotStateChangeListener;
        Intent intent = getIntent();
        Log.i(
                "WifiApAutoHotspotEnablingActivity",
                "Intent Name: " + intent.getComponent().getClassName());
        Log.i("WifiApAutoHotspotEnablingActivity", "Intent : " + intent);
        this.mWifiApAutoHotspotSwitchEnabler.updateSwitchState(true);
    }
}
