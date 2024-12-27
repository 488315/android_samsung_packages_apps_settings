package com.samsung.android.settings.bluetooth.lebroadcast;

import android.bluetooth.BluetoothDump;
import android.content.DialogInterface;
import android.provider.Settings;
import android.util.Log;

import com.android.settings.R;

import com.samsung.android.settings.logging.SALogging;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class SecBluetoothLeBroadcastSourceSetting$$ExternalSyntheticLambda0
        implements DialogInterface.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SecBluetoothLeBroadcastSourceSetting f$0;

    public /* synthetic */ SecBluetoothLeBroadcastSourceSetting$$ExternalSyntheticLambda0(
            SecBluetoothLeBroadcastSourceSetting secBluetoothLeBroadcastSourceSetting, int i) {
        this.$r8$classId = i;
        this.f$0 = secBluetoothLeBroadcastSourceSetting;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        int i2 = this.$r8$classId;
        SecBluetoothLeBroadcastSourceSetting secBluetoothLeBroadcastSourceSetting = this.f$0;
        switch (i2) {
            case 0:
                SALogging.insertSALog(
                        secBluetoothLeBroadcastSourceSetting.mScreenId,
                        secBluetoothLeBroadcastSourceSetting
                                .getResources()
                                .getString(R.string.event_broadcast_source_info_about_auracast_ok));
                break;
            default:
                boolean z = SecBluetoothLeBroadcastSourceSetting.DEBUG;
                secBluetoothLeBroadcastSourceSetting.getClass();
                Log.d("SecBluetoothLeBroadcastSourceSetting", "Allowbroadcast By user action");
                BluetoothDump.BtLog("BRST-Allow BR by user");
                SALogging.insertSALog(
                        secBluetoothLeBroadcastSourceSetting.mScreenId,
                        secBluetoothLeBroadcastSourceSetting
                                .getResources()
                                .getString(R.string.event_broadcast_start_popup_ok_button));
                Settings.Secure.putInt(
                        secBluetoothLeBroadcastSourceSetting.getContentResolver(),
                        "need_auracast_start_popup",
                        1);
                secBluetoothLeBroadcastSourceSetting.startLeBroadcast();
                break;
        }
    }
}
