package com.samsung.android.settings.asbase.widget;

import android.media.AudioManager;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class SecVolumeValues {
    public static final int EAR_PROTECT_LIMIT_LEVEL_FOR_ROUTINE =
            AudioManager.semGetEarProtectLimit() * 10;

    public static final int getAudioStream(int i) {
        switch (i) {
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
                return 3;
            default:
                return i;
        }
    }

    public static final boolean isMusic(int i) {
        return i == 3 || i == 101 || i == 102 || i == 103 || i == 104 || i == 105;
    }

    public static final boolean isSpeakerType(int i) {
        return i == 2 || i == 0 || i == 101;
    }

    public static String rowStreamTypeToString(int i) {
        if (i == 8) {
            return "STREAM_DTMF";
        }
        if (i == 105) {
            return "STREAM_MUSIC_BLUETOOTH";
        }
        if (i == 10) {
            return "STREAM_ACCESSIBILITY";
        }
        if (i == 11) {
            return "STREAM_BIXBY_VOICE";
        }
        if (i == 101) {
            return "STREAM_MUSIC_SPEAKER";
        }
        if (i == 102) {
            return "STREAM_MUSIC_USB_HEADSET";
        }
        switch (i) {
            case 0:
                return "STREAM_VOICE_CALL";
            case 1:
                return "STREAM_SYSTEM";
            case 2:
                return "STREAM_RING";
            case 3:
                return "STREAM_MUSIC";
            case 4:
                return "STREAM_ALARM";
            case 5:
                return "STREAM_NOTIFICATION";
            case 6:
                return "STREAM_BLUETOOTH_SCO";
            default:
                return ApnSettings.MVNO_NONE;
        }
    }
}
