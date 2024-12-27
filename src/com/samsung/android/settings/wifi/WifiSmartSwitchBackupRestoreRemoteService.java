package com.samsung.android.settings.wifi;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

import androidx.preference.Preference$$ExternalSyntheticOutline0;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiSmartSwitchBackupRestoreRemoteService extends Service {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Messenger mMessenger = new Messenger(new WifiBnrHandler(this));

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiBnrHandler extends Handler {
        public final WeakReference mService;

        public WifiBnrHandler(
                WifiSmartSwitchBackupRestoreRemoteService
                        wifiSmartSwitchBackupRestoreRemoteService) {
            this.mService = new WeakReference(wifiSmartSwitchBackupRestoreRemoteService);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            WifiSmartSwitchBackupRestoreRemoteService wifiSmartSwitchBackupRestoreRemoteService =
                    (WifiSmartSwitchBackupRestoreRemoteService) this.mService.get();
            if (wifiSmartSwitchBackupRestoreRemoteService != null) {
                int i = WifiSmartSwitchBackupRestoreRemoteService.$r8$clinit;
                if (message == null) {
                    Log.e(
                            "WifiSmartSwitchBackupRestoreRemoteService",
                            "handleMessage() - msg is null");
                    return;
                }
                Preference$$ExternalSyntheticOutline0.m(
                        new StringBuilder("handleMessage() - "),
                        message.what,
                        "WifiSmartSwitchBackupRestoreRemoteService");
                if (message.what != 12288) {
                    Log.e(
                            "WifiSmartSwitchBackupRestoreRemoteService",
                            "handleMessage() - Unsupported cmd : " + message.what);
                    return;
                }
                Bundle data = message.getData();
                if (data == null) {
                    Log.e(
                            "WifiSmartSwitchBackupRestoreRemoteService",
                            "handleMessage() - bundle is null");
                    return;
                }
                ArrayList<String> stringArrayList = data.getStringArrayList("SAVE_PATH_URIS");
                String string = data.getString("SESSION_KEY");
                int i2 = data.getInt("SECURITY_LEVEL");
                String string2 = data.getString("SOURCE");
                Intent intent =
                        new Intent("com.sec.android.intent.action.REQUEST_RESTORE_WIFIWPACONF");
                intent.putStringArrayListExtra("SAVE_PATH_URIS", stringArrayList);
                intent.putExtra("SESSION_KEY", string);
                intent.putExtra("SECURITY_LEVEL", i2);
                intent.putExtra("SOURCE", string2);
                intent.putExtra("FAST_TRACK", true);
                new WifiSmartSwitchBackupRestore()
                        .onReceive(
                                wifiSmartSwitchBackupRestoreRemoteService.getApplicationContext(),
                                intent);
            }
        }
    }

    @Override // android.app.Service
    public final IBinder onBind(Intent intent) {
        return this.mMessenger.getBinder();
    }
}
