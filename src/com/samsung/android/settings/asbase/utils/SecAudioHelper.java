package com.samsung.android.settings.asbase.utils;

import android.content.Context;
import android.media.AudioManager;

import com.android.settings.notification.AudioHelper;

import com.samsung.android.settings.asbase.widget.SecVolumeValues;
import com.sec.ims.presence.ServiceTuple;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SecAudioHelper extends AudioHelper {
    public final AudioManager mAudioManager;

    public SecAudioHelper(Context context) {
        super(context);
        this.mAudioManager = (AudioManager) context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
    }

    public static int getEarProtectLevel() {
        int i = SecVolumeValues.EAR_PROTECT_LIMIT_LEVEL_FOR_ROUTINE;
        return ((AudioManager.semGetEarProtectLimit() - 1) * 100) + 9;
    }

    public final boolean isEarDeviceStatusOn() {
        return (this.mAudioManager.isWiredHeadsetOn()
                        || this.mAudioManager.semIsSafeMediaVolumeDeviceOn())
                && !(this.mAudioManager.semIsFmRadioActive()
                        && (this.mAudioManager.semGetRadioOutputPath() == 2
                                || this.mAudioManager.semGetRadioOutputPath() == 8
                                || this.mAudioManager.semGetRadioOutputPath() == 3));
    }
}
