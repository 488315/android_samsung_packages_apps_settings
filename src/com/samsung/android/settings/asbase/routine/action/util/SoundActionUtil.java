package com.samsung.android.settings.asbase.routine.action.util;

import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioManager;
import android.provider.Settings;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.asbase.widget.SecVolumeValues;
import com.sec.ims.presence.ServiceTuple;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SoundActionUtil {
    public static int getStreamVolumeFromRoutine(Context context, int i, int i2) {
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        Intrinsics.checkNotNull(
                systemService, "null cannot be cast to non-null type android.media.AudioManager");
        AudioManager audioManager = (AudioManager) systemService;
        if (SecVolumeValues.isMusic(i)) {
            return audioManager.semGetFineVolume(i, i2);
        }
        if ((i != 2 && i != 5 && i != 1) || audioManager.getStreamVolume(2) != 0) {
            return audioManager.semGetStreamVolume(i, i2);
        }
        ContentResolver contentResolver = context.getContentResolver();
        String str = ApnSettings.MVNO_NONE;
        int i3 =
                Settings.System.getInt(
                        contentResolver,
                        i != 1
                                ? i != 2
                                        ? i != 5 ? ApnSettings.MVNO_NONE : "volume_notification"
                                        : "volume_ring"
                                : "volume_system",
                        0);
        ContentResolver contentResolver2 = context.getContentResolver();
        if (i == 1) {
            str = "volume_system_speaker";
        } else if (i == 2) {
            str = "volume_ring_speaker";
        } else if (i == 5) {
            str = "volume_notification_speaker";
        }
        return Settings.System.getInt(contentResolver2, str, i3);
    }
}
