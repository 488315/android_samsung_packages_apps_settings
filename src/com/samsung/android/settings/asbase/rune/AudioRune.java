package com.samsung.android.settings.asbase.rune;

import android.media.AudioManager;
import android.os.Build;
import android.text.TextUtils;

import com.android.settings.Utils;

import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AudioRune {
    public static final boolean SEC_AUDIO_SUPPORT_VOIP_ANTI_HOWLING;
    public static final boolean SEC_AUDIO_SUPPORT_VOIP_SOUND_LOUDER;
    public static final boolean SUPPORT_ADD_BUTTON;
    public static final boolean SUPPORT_HAPTIC_PLAYBACK_WITH_RINGTONE;
    public static final boolean SUPPORT_HIDE_SYSTEM_SOUND_SETTING;
    public static final boolean SUPPORT_KT_RINGTONE;
    public static final boolean SUPPORT_LGU_RINGTONE;
    public static final boolean SUPPORT_MULTI_SOUND_SELECT_MULTIPLE_APPLICATIONS;
    public static final boolean SUPPORT_SITUATION_EXTENSION;
    public static final boolean SUPPORT_SKT_RINGTONE;
    public static final boolean SUPPORT_SOUND_THEME;
    public static final boolean SUPPORT_SOUND_THEME_ONEUI31_EXTENSION;
    public static final boolean SUPPORT_SPEAKER;
    public static final boolean SUPPORT_V_COLORING;

    static {
        boolean equals = TextUtils.equals("sep_basic", "sep_basic");
        SUPPORT_SOUND_THEME = equals;
        SUPPORT_SOUND_THEME_ONEUI31_EXTENSION = equals && Build.VERSION.SEM_PLATFORM_INT >= 120100;
        SUPPORT_SPEAKER = !TextUtils.equals("NONE", "ONEUI_6_1");
        SUPPORT_ADD_BUTTON =
                !SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_DISABLED_MENU_K05");
        SUPPORT_SITUATION_EXTENSION =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_SITUATION_EXTENSION");
        SEC_AUDIO_SUPPORT_VOIP_SOUND_LOUDER =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_VOIP_SOUND_LOUDER");
        SEC_AUDIO_SUPPORT_VOIP_ANTI_HOWLING =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_ANTI_HOWLING");
        SUPPORT_SKT_RINGTONE = Rune.isDomesticSKTModel();
        SUPPORT_KT_RINGTONE = Rune.isDomesticKTTModel();
        SUPPORT_LGU_RINGTONE = Rune.isDomesticLGTModel();
        SUPPORT_V_COLORING =
                SemCscFeature.getInstance()
                                .getBoolean("CscFeature_VoiceCall_SupportCallerRingBackTone")
                        && Rune.isDomesticLGTModel()
                        && !Rune.isLDUModel();
        String readCountryCode = Utils.readCountryCode();
        String salesCode = Utils.getSalesCode();
        SUPPORT_HIDE_SYSTEM_SOUND_SETTING =
                "DZ".equals(readCountryCode)
                        && ("TKD".equals(salesCode)
                                || "TMC".equals(salesCode)
                                || "WTL".equals(salesCode));
        SUPPORT_HAPTIC_PLAYBACK_WITH_RINGTONE = AudioManager.isHapticPlaybackSupported();
        SUPPORT_MULTI_SOUND_SELECT_MULTIPLE_APPLICATIONS = Build.VERSION.SEM_PLATFORM_INT >= 130000;
    }
}
