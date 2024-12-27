package com.samsung.android.settings.deviceinfo.aboutphone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.net.Uri;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.deviceinfo.SecDeviceInfoUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DeviceNameSAReceiver extends BroadcastReceiver {
    public static final Uri SINGLE_URI =
            Uri.parse(
                    "content://com.samsung.android.mobileservice.profileProvider/new_profile_single");

    static {
        Uri.parse("content://com.samsung.android.mobileservice.profileProvider/new_profile_multi");
    }

    public static String getSimplifiedDefaultDeviceName(Context context) {
        return SecDeviceInfoUtils.getDefaultDeviceName(context)
                .replace("Galaxy", ApnSettings.MVNO_NONE)
                .replace("5G", ApnSettings.MVNO_NONE)
                .replace("(LTE)", ApnSettings.MVNO_NONE)
                .replace("LTE", ApnSettings.MVNO_NONE)
                .trim();
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x007e, code lost:

       if (r3 != null) goto L22;
    */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0080, code lost:

       r3.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0093, code lost:

       if (android.text.TextUtils.isEmpty(r12) == false) goto L29;
    */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0095, code lost:

       android.util.Log.i("DeviceNameSAReceiver", "SAN is null");
    */
    /* JADX WARN: Code restructure failed: missing block: B:21:?, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x009c, code lost:

       r14 = getSimplifiedDefaultDeviceName(r13);
       android.util.Log.d("DeviceNameSAReceiver", "[Length] SAN: " + r12.length() + " Reduced DN: " + r14.length());
       r14 = r13.getResources().getString(com.android.settings.R.string.device_info_sync_name, r12, r14);
    */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00d3, code lost:

       if (r14.length() <= 32) goto L32;
    */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x00d7, code lost:

       r0 = r13.getContentResolver();
       android.provider.Settings.Global.putString(r0, "device_name", r14);
       android.provider.Settings.Global.putString(r0, "synced_account_name", r12);
       r13.sendBroadcast(new android.content.Intent("com.android.settings.DEVICE_NAME_CHANGED"));
    */
    /* JADX WARN: Code restructure failed: missing block: B:26:?, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:27:?, code lost:

       return;
    */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x008c, code lost:

       if (0 == 0) goto L26;
    */
    @Override // android.content.BroadcastReceiver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onReceive(android.content.Context r13, android.content.Intent r14) {
        /*
            Method dump skipped, instructions count: 333
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.aboutphone.DeviceNameSAReceiver.onReceive(android.content.Context,"
                    + " android.content.Intent):void");
    }
}
