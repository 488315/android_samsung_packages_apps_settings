package com.android.settingslib.bluetooth;

import android.media.AudioDeviceAttributes;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class HearingAidAudioRoutingConstants {
    public static final int[] CALL_ROUTING_ATTRIBUTES = {2};
    public static final int[] MEDIA_ROUTING_ATTRIBUTES = {1, 11, 3};
    public static final int[] RINGTONE_ROUTING_ATTRIBUTES = {6};
    public static final int[] NOTIFICATION_ROUTING_ATTRIBUTES = {5};
    public static final AudioDeviceAttributes DEVICE_SPEAKER_OUT =
            new AudioDeviceAttributes(2, 2, ApnSettings.MVNO_NONE);
}
