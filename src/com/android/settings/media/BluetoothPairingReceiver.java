package com.android.settings.media;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.settings.bluetooth.BluetoothPairingDetail;
import com.android.settings.core.SubSettingLauncher;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BluetoothPairingReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(
                "com.android.settings.action.LAUNCH_BLUETOOTH_PAIRING", intent.getAction())) {
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context);
            String name = BluetoothPairingDetail.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            subSettingLauncher.setTitleRes(R.string.bluetooth_pairing_page_title, null);
            launchRequest.mSourceMetricsCategory = 1851;
            subSettingLauncher.addFlags(268468224);
            context.startActivity(subSettingLauncher.toIntent());
        }
    }
}
