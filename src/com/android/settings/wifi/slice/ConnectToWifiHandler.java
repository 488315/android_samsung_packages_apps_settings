package com.android.settings.wifi.slice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.settings.R;
import com.android.settings.slices.SliceBackgroundWorker;
import com.android.settings.wifi.WifiDialogActivity;
import com.android.wifitrackerlib.WifiEntry;

import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Deprecated(forRemoval = true)
/* loaded from: classes2.dex */
public class ConnectToWifiHandler extends BroadcastReceiver {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class WifiEntryConnectCallback implements WifiEntry.ConnectCallback {
        public final Context mContext;
        public final WifiEntry mWifiEntry;

        public WifiEntryConnectCallback(Context context, WifiEntry wifiEntry) {
            this.mContext = context;
            this.mWifiEntry = wifiEntry;
        }

        @Override // com.android.wifitrackerlib.WifiEntry.ConnectCallback
        public final void onConnectResult(int i) {
            if (i == 1) {
                Intent putExtra =
                        new Intent(this.mContext, (Class<?>) WifiDialogActivity.class)
                                .putExtra("key_chosen_wifientry_key", this.mWifiEntry.getKey());
                putExtra.addFlags(268435456);
                this.mContext.startActivity(putExtra);
            } else if (i == 2) {
                Toast.makeText(this.mContext, R.string.wifi_failed_connect_message, 0).show();
            }
        }
    }

    public WifiScanWorker getWifiScanWorker(Intent intent) {
        return (WifiScanWorker)
                SliceBackgroundWorker.getInstance(
                        (Uri) intent.getParcelableExtra("key_wifi_slice_uri"));
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        WifiScanWorker wifiScanWorker;
        if (context == null || intent == null) {
            return;
        }
        String stringExtra = intent.getStringExtra("key_chosen_wifientry_key");
        if (TextUtils.isEmpty(stringExtra)
                || intent.getParcelableExtra("key_wifi_slice_uri") == null
                || (wifiScanWorker = getWifiScanWorker(intent)) == null) {
            return;
        }
        WifiEntry wifiEntry = wifiScanWorker.mWifiPickerTracker.mConnectedWifiEntry;
        if (wifiEntry == null || !TextUtils.equals(stringExtra, wifiEntry.getKey())) {
            Iterator it = wifiScanWorker.mWifiPickerTracker.getWifiEntries().iterator();
            while (true) {
                if (!it.hasNext()) {
                    wifiEntry = null;
                    break;
                } else {
                    wifiEntry = (WifiEntry) it.next();
                    if (TextUtils.equals(stringExtra, wifiEntry.getKey())) {
                        break;
                    }
                }
            }
        }
        if (wifiEntry == null) {
            return;
        }
        wifiEntry.connect(new WifiEntryConnectCallback(context, wifiEntry));
    }
}
