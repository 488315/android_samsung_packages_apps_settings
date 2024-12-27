package com.android.settings.wifi;

import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.android.settings.R;

import com.samsung.android.settings.wifi.intelligent.TurnOnWifiAutomaticallySettings;
import com.samsung.android.widget.SemTipPopup;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiSettings$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiSettings f$0;

    public /* synthetic */ WifiSettings$$ExternalSyntheticLambda3(
            WifiSettings wifiSettings, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiSettings;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        final WifiSettings wifiSettings = this.f$0;
        switch (i) {
            case 0:
                boolean z = WifiSettings.mWifiSettingsForeground;
                wifiSettings.checkConnectViaQr();
                break;
            case 1:
                boolean z2 = WifiSettings.mWifiSettingsForeground;
                wifiSettings.setProgressBarVisible(false);
                break;
            default:
                boolean z3 = WifiSettings.mWifiSettingsForeground;
                if (wifiSettings.isResumed()) {
                    View findViewById =
                            wifiSettings
                                    .getActivity()
                                    .getWindow()
                                    .getDecorView()
                                    .findViewById(R.id.app_bar);
                    wifiSettings.mAppBarView = findViewById;
                    if (findViewById == null) {
                        Log.d("WifiSettings", "parentView is null");
                        break;
                    } else if (wifiSettings.mHasShownBubbleTip) {
                        Log.d("WifiSettings", "Cannot show tip popup");
                        break;
                    } else {
                        try {
                            SemTipPopup semTipPopup = new SemTipPopup(wifiSettings.mAppBarView);
                            wifiSettings.mTipsDescriptionPopup = semTipPopup;
                            semTipPopup.setExpanded(true);
                            wifiSettings.mTipsDescriptionPopup.setMessage(
                                    wifiSettings.mContext.getString(
                                            R.string.sec_wifi_bubble_tips_text));
                            wifiSettings.mTipsDescriptionPopup.setAction(
                                    wifiSettings.mContext.getString(R.string.launch_wifi_text),
                                    new View
                                            .OnClickListener() { // from class:
                                                                 // com.android.settings.wifi.WifiSettings.4
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view) {
                                            Settings.Global.putInt(
                                                    WifiSettings.this.mContext.getContentResolver(),
                                                    "sem_auto_wifi_bubbletip_do_not_show_again",
                                                    1);
                                            WifiSettings.this.launchFragment(
                                                    R.string.wifi_autowifi_title,
                                                    TurnOnWifiAutomaticallySettings.class
                                                            .getCanonicalName());
                                        }
                                    });
                            wifiSettings.mHasShownBubbleTip = true;
                            int[] iArr = new int[2];
                            wifiSettings.mAppBarView.getLocationInWindow(iArr);
                            int i2 =
                                    (int)
                                            ((wifiSettings
                                                                    .getContext()
                                                                    .getResources()
                                                                    .getDisplayMetrics()
                                                                    .densityDpi
                                                            / 160.0f)
                                                    * 35.0f);
                            wifiSettings.mTipsDescriptionPopup.setTargetPosition(
                                    wifiSettings.mAppBarView.getLayoutDirection() == 0
                                            ? (wifiSettings.mAppBarView.getWidth() + iArr[0]) - i2
                                            : iArr[0] + i2,
                                    (wifiSettings.mAppBarView.getHeight() + iArr[1])
                                            - ((int)
                                                    ((wifiSettings
                                                                            .getContext()
                                                                            .getResources()
                                                                            .getDisplayMetrics()
                                                                            .densityDpi
                                                                    / 160.0f)
                                                            * 18.0f)));
                            wifiSettings.mTipsDescriptionPopup.show(
                                    wifiSettings.mAppBarView.getLayoutDirection() != 0 ? 3 : 2);
                            break;
                        } catch (WindowManager.BadTokenException unused) {
                            Log.i(
                                    "WifiSettings",
                                    "BadTokenException occurs. The showAutoWifiTipPopup show will"
                                        + " be ignored.");
                        }
                    }
                }
                break;
        }
    }
}
