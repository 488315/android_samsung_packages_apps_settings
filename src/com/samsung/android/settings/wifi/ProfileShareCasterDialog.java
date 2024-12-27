package com.samsung.android.settings.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;

import com.android.internal.app.AlertActivity;

import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.wifi.SemWifiManager;
import com.sec.ims.extensions.WiFiManagerExt;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ProfileShareCasterDialog extends AlertActivity
        implements DialogInterface.OnClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public Context mContext;
    public boolean mIsConfirmed;
    public boolean mIsHotspot;
    public boolean mIsNotifyNeeded = true;
    public final AnonymousClass1 mReceiver =
            new BroadcastReceiver() { // from class:
                                      // com.samsung.android.settings.wifi.ProfileShareCasterDialog.1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    String stringExtra = intent.getStringExtra("userData");
                    if ("com.samsung.android.net.wifi.DISMISS_REQ_PASSWORD_DIALOG".equals(action)) {
                        if (stringExtra != null
                                && !stringExtra.equals(ProfileShareCasterDialog.this.mUserData)) {
                            Log.i("ProfileShareCasterDialog", "Cancel request might be others");
                            return;
                        }
                        Log.i("ProfileShareCasterDialog", "Subscriber canceled request");
                        ProfileShareCasterDialog profileShareCasterDialog =
                                ProfileShareCasterDialog.this;
                        profileShareCasterDialog.mIsNotifyNeeded = false;
                        profileShareCasterDialog.finish();
                    }
                }
            };
    public TimeoutHandler mTimeoutHandler;
    public String mUserData;
    public WifiManager mWifiManager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class TimeoutHandler extends Handler {
        public TimeoutHandler() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 0) {
                return;
            }
            Log.d("ProfileShareCasterDialog", "Time out! 20000");
            int i = ProfileShareCasterDialog.$r8$clinit;
            ProfileShareCasterDialog.this.finish();
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -2) {
            Log.d("ProfileShareCasterDialog", "User declined to share password");
            finish();
        } else {
            if (i != -1) {
                return;
            }
            Log.d("ProfileShareCasterDialog", "User allowed to share password");
            SALogging.insertSALog("WIFI_111", "0127");
            this.mIsConfirmed = true;
            finish();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0216  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01bd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onCreate(android.os.Bundle r24) {
        /*
            Method dump skipped, instructions count: 891
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.ProfileShareCasterDialog.onCreate(android.os.Bundle):void");
    }

    public final void onDestroy() {
        if (this.mIsNotifyNeeded) {
            ActionBarContextView$$ExternalSyntheticOutline0.m(
                    new StringBuilder("onDestroy accepted? "),
                    this.mIsConfirmed,
                    "ProfileShareCasterDialog");
            if (!this.mIsConfirmed) {
                SALogging.insertSALog("WIFI_111", "0126");
            }
            ((SemWifiManager) this.mContext.getSystemService(WiFiManagerExt.SEM_WIFI_SERVICE))
                    .setUserConfirmForSharingPassword(this.mIsConfirmed, this.mUserData);
        }
        super.onDestroy();
    }

    public final void onStart() {
        super.onStart();
        SALogging.insertSALog("WIFI_111");
        this.mContext.registerReceiver(
                this.mReceiver,
                new IntentFilter("com.samsung.android.net.wifi.DISMISS_REQ_PASSWORD_DIALOG"),
                "android.permission.NETWORK_SETTINGS",
                null,
                2);
        TimeoutHandler timeoutHandler = this.mTimeoutHandler;
        if (timeoutHandler.hasMessages(0)) {
            return;
        }
        Log.d("ProfileShareCasterDialog", "timer start");
        timeoutHandler.sendEmptyMessageDelayed(0, 20000L);
    }

    public final void onStop() {
        this.mContext.unregisterReceiver(this.mReceiver);
        Log.d("ProfileShareCasterDialog", "onStop");
        finish();
        super.onStop();
    }
}
