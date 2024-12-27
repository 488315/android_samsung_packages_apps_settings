package com.android.settings.deviceinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.storage.StorageManager;
import android.os.storage.VolumeInfo;
import android.util.Log;

import com.android.settings.deviceinfo.storage.StorageUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class StorageUnmountReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        context.sendBroadcast(new Intent("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
        StorageManager storageManager =
                (StorageManager) context.getSystemService(StorageManager.class);
        String stringExtra = intent.getStringExtra("android.os.storage.extra.VOLUME_ID");
        if (stringExtra == null) {
            Log.w("StorageUnmountReceiver", "VolumeInfo ID is null");
            return;
        }
        VolumeInfo findVolumeById = storageManager.findVolumeById(stringExtra);
        if (findVolumeById != null) {
            StorageUtils.checkEncryptionAndUnmount(context, findVolumeById, true);
        } else {
            Log.w("StorageUnmountReceiver", "Missing volume ".concat(stringExtra));
        }
    }
}
