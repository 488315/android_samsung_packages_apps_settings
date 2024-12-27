package com.samsung.android.settings.asbase.routine.action;

import android.content.Context;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

import androidx.apppickerview.features.applabel.AbstractAppLabelMapFactory$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ComposerKt$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.accessibility.PreferredShortcuts$$ExternalSyntheticOutline0;
import com.android.settings.notification.zen.lifestyle.LifeStyleDND$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.sdk.routines.v3.data.ErrorContents;
import com.samsung.android.sdk.routines.v3.data.ParameterValues;
import com.samsung.android.sdk.routines.v3.data.SupportStatus;
import com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda6;
import com.samsung.android.sdk.routines.v3.internal.ActionDispatcher$$ExternalSyntheticLambda8;
import com.samsung.android.sdk.routines.v3.template.ToggleTemplate;
import com.samsung.android.sdk.routines.v3.template.UiTemplate;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.asbase.routine.action.util.SoundActionUtil;
import com.samsung.android.settings.asbase.utils.SimUtils;
import com.samsung.android.settings.asbase.utils.SoundUtils;
import com.samsung.android.settings.asbase.utils.VibUtils;
import com.samsung.android.settings.asbase.widget.SecVolumeValues;
import com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor;
import com.samsung.android.util.SemLog;
import com.sec.ims.presence.ServiceTuple;

import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SoundSettingsActionInteractor implements ActionInteractor {
    public static String getLevelKeyValues(int i) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                "volume_level", "_".concat(SecVolumeValues.rowStreamTypeToString(i)));
    }

    public static String getLevelKeyValuesFromDevice(int i, String str) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                "volume_level",
                ComposerKt$$ExternalSyntheticOutline0.m(
                        "_", SecVolumeValues.rowStreamTypeToString(i), str));
    }

    public static String getVolumeLabel(float f, int i, Context context) {
        Object systemService = context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        Intrinsics.checkNotNull(
                systemService, "null cannot be cast to non-null type android.media.AudioManager");
        int streamMaxVolume = ((AudioManager) systemService).getStreamMaxVolume(i);
        if (i == 3) {
            streamMaxVolume *= 10;
        }
        return ((int) ((f / streamMaxVolume) * 100.0f)) + "%";
    }

    public static boolean isSystemOptionOn(Context context, String str) {
        return Settings.System.getInt(context.getContentResolver(), str, 1) == 1;
    }

    public static void performMediaVolumeAction(Context context, ParameterValues parameterValues) {
        performVolumeAction(context, parameterValues, getLevelKeyValues(101), 3, 2);
        performVolumeAction(context, parameterValues, getLevelKeyValues(102), 3, 22);
        performVolumeAction(context, parameterValues, getLevelKeyValues(102), 3, 3);
        performVolumeAction(context, parameterValues, getLevelKeyValues(102), 3, 4);
        performVolumeAction(context, parameterValues, getLevelKeyValues(105), 3, 8);
    }

    public static void performSoundModeAction(Context context, ParameterValues parameterValues) {
        Boolean bool = parameterValues.getBoolean("as_do_not_change_volume", Boolean.FALSE);
        Intrinsics.checkNotNullExpressionValue(bool, "getBoolean(...)");
        boolean booleanValue = bool.booleanValue();
        Object systemService = context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        Intrinsics.checkNotNull(
                systemService, "null cannot be cast to non-null type android.media.AudioManager");
        int semGetRingerModeInternal = ((AudioManager) systemService).semGetRingerModeInternal();
        int m =
                (int)
                        LifeStyleDND$$ExternalSyntheticOutline0.m(
                                semGetRingerModeInternal, parameterValues, "as_sound_mode");
        if ((m == 2 || semGetRingerModeInternal == 2) && !booleanValue) {
            if (((int)
                            LifeStyleDND$$ExternalSyntheticOutline0.m(
                                    SoundActionUtil.getStreamVolumeFromRoutine(context, 2, 2),
                                    parameterValues,
                                    getLevelKeyValues(2)))
                    != 0) {
                performVolumeAction(context, parameterValues, getLevelKeyValues(2), 2, 2);
            }
            performVolumeAction(context, parameterValues, getLevelKeyValues(5), 5, 2);
            performVolumeAction(context, parameterValues, getLevelKeyValues(5), 5, 8);
            performVolumeAction(context, parameterValues, getLevelKeyValues(1), 1, 2);
        }
        Object systemService2 = context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        Intrinsics.checkNotNull(
                systemService2, "null cannot be cast to non-null type android.media.AudioManager");
        ((AudioManager) systemService2).setRingerModeInternal(m);
    }

    public static void performVolumeAction(
            Context context, ParameterValues parameterValues, String str, int i, int i2) {
        Float number =
                parameterValues.getNumber(
                        str,
                        Float.valueOf(SoundActionUtil.getStreamVolumeFromRoutine(context, i, i2)));
        Intrinsics.checkNotNullExpressionValue(number, "getNumber(...)");
        int floatValue = (int) number.floatValue();
        StringBuilder m =
                PreferredShortcuts$$ExternalSyntheticOutline0.m(
                        i2,
                        "performVolumeAction stream ",
                        SecVolumeValues.rowStreamTypeToString(i),
                        ", device ",
                        ", index ");
        m.append(floatValue);
        SemLog.d("SoundSettingsActionInteractor", m.toString());
        if (floatValue == -1) {
            return;
        }
        Object systemService = context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
        Intrinsics.checkNotNull(
                systemService, "null cannot be cast to non-null type android.media.AudioManager");
        AudioManager audioManager = (AudioManager) systemService;
        if (SecVolumeValues.isMusic(i)) {
            audioManager.semSetFineVolume(i, floatValue, 0, i2);
            return;
        }
        audioManager.semSetStreamVolume(i, floatValue, 0, i2);
        if (i == 5) {
            audioManager.semSetStreamVolume(i, floatValue, 0, 8);
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor
    public final void getCurrentParameterValues(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ActionDispatcher$$ExternalSyntheticLambda6 actionDispatcher$$ExternalSyntheticLambda6) {
        Intrinsics.checkNotNullParameter(context, "context");
        ParameterValues parameterValues2 = new ParameterValues();
        int i = 1;
        switch (str.hashCode()) {
            case -2001614626:
                if (str.equals("routines_set_bixby_volume")) {
                    parameterValues2.put(
                            "volume_level",
                            Float.valueOf(
                                    SoundActionUtil.getStreamVolumeFromRoutine(context, 11, 0)));
                    actionDispatcher$$ExternalSyntheticLambda6.setResponse(parameterValues2);
                    break;
                }
                break;
            case -1385003673:
                if (str.equals("as_keyboard_vibration")) {
                    parameterValues2.put(
                            "toggle_value",
                            Boolean.valueOf(
                                    isSystemOptionOn(context, "sip_key_feedback_vibration")));
                    actionDispatcher$$ExternalSyntheticLambda6.setResponse(parameterValues2);
                    break;
                }
                break;
            case -774894674:
                if (str.equals("routines_set_call_volume")) {
                    parameterValues2.put(
                            "volume_level",
                            Float.valueOf(
                                    SoundActionUtil.getStreamVolumeFromRoutine(context, 0, 0)));
                    parameterValues2.put(
                            getLevelKeyValuesFromDevice(0, "_EARPIECE"),
                            Float.valueOf(
                                    SoundActionUtil.getStreamVolumeFromRoutine(context, 0, 1)));
                    parameterValues2.put(
                            getLevelKeyValuesFromDevice(0, "_SPEAKER"),
                            Float.valueOf(
                                    SoundActionUtil.getStreamVolumeFromRoutine(context, 0, 2)));
                    actionDispatcher$$ExternalSyntheticLambda6.setResponse(parameterValues2);
                    break;
                }
                break;
            case 230185392:
                if (str.equals("as_dialing_keypad_tones")) {
                    parameterValues2.put(
                            "toggle_value",
                            Boolean.valueOf(isSystemOptionOn(context, "dtmf_tone")));
                    actionDispatcher$$ExternalSyntheticLambda6.setResponse(parameterValues2);
                    break;
                }
                break;
            case 265883236:
                if (str.equals("as_keyboard_sound")) {
                    parameterValues2.put(
                            "toggle_value",
                            Boolean.valueOf(isSystemOptionOn(context, "sip_key_feedback_sound")));
                    actionDispatcher$$ExternalSyntheticLambda6.setResponse(parameterValues2);
                    break;
                }
                break;
            case 342936258:
                if (str.equals("as_media_volume")) {
                    parameterValues2.put(
                            getLevelKeyValues(101),
                            Float.valueOf(
                                    SoundActionUtil.getStreamVolumeFromRoutine(context, 3, 2)));
                    parameterValues2.put(
                            getLevelKeyValues(102),
                            Float.valueOf(
                                    SoundActionUtil.getStreamVolumeFromRoutine(context, 3, 22)));
                    parameterValues2.put(
                            getLevelKeyValuesFromDevice(102, "_WIRED_HEADPHONES"),
                            Float.valueOf(
                                    SoundActionUtil.getStreamVolumeFromRoutine(context, 3, 4)));
                    parameterValues2.put(
                            getLevelKeyValuesFromDevice(102, "_WIRED_HEADSET"),
                            Float.valueOf(
                                    SoundActionUtil.getStreamVolumeFromRoutine(context, 3, 3)));
                    actionDispatcher$$ExternalSyntheticLambda6.setResponse(parameterValues2);
                    break;
                }
                break;
            case 728577263:
                if (str.equals("routines_set_ringtone")) {
                    if (SimUtils.isMultiSimModel() && SimUtils.isEnabledSIM2Only()) {
                        i = 128;
                    }
                    Uri actualDefaultRingtoneUri =
                            RingtoneManager.getActualDefaultRingtoneUri(context, i);
                    if (actualDefaultRingtoneUri != null) {
                        String uri = actualDefaultRingtoneUri.toString();
                        Intrinsics.checkNotNullExpressionValue(uri, "toString(...)");
                        if (uri.length() <= 0) {
                            actualDefaultRingtoneUri = null;
                        }
                        if (actualDefaultRingtoneUri != null) {
                            parameterValues2.put(
                                    "routines_ringtone_uri", actualDefaultRingtoneUri.toString());
                        }
                    }
                    actionDispatcher$$ExternalSyntheticLambda6.setResponse(parameterValues2);
                    break;
                }
                break;
            case 1227889607:
                if (str.equals("as_volume")) {
                    Intrinsics.checkNotNull(
                            context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO),
                            "null cannot be cast to non-null type android.media.AudioManager");
                    parameterValues2.put(
                            "as_sound_mode",
                            Float.valueOf(((AudioManager) r6).semGetRingerModeInternal()));
                    Boolean bool =
                            parameterValues.getBoolean("as_do_not_change_volume", Boolean.FALSE);
                    Intrinsics.checkNotNullExpressionValue(bool, "getBoolean(...)");
                    boolean booleanValue = bool.booleanValue();
                    parameterValues2.put("as_do_not_change_volume", bool);
                    if (!booleanValue) {
                        parameterValues2.put(
                                getLevelKeyValues(2),
                                Float.valueOf(
                                        SoundActionUtil.getStreamVolumeFromRoutine(context, 2, 2)));
                        parameterValues2.put(
                                getLevelKeyValues(5),
                                Float.valueOf(
                                        SoundActionUtil.getStreamVolumeFromRoutine(context, 5, 2)));
                        parameterValues2.put(
                                getLevelKeyValues(1),
                                Float.valueOf(
                                        SoundActionUtil.getStreamVolumeFromRoutine(context, 1, 2)));
                    }
                    actionDispatcher$$ExternalSyntheticLambda6.setResponse(parameterValues2);
                    break;
                }
                break;
            case 1329808775:
                if (str.equals("as_vibrate_while_ringing")) {
                    parameterValues2.put(
                            "toggle_value",
                            Boolean.valueOf(isSystemOptionOn(context, "vibrate_when_ringing")));
                    actionDispatcher$$ExternalSyntheticLambda6.setResponse(parameterValues2);
                    break;
                }
                break;
        }
    }

    @Override // com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor
    public final void getParameterLabel(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ActionDispatcher$$ExternalSyntheticLambda6 actionDispatcher$$ExternalSyntheticLambda6) {
        Intrinsics.checkNotNullParameter(context, "context");
        int hashCode = str.hashCode();
        String str2 = ApnSettings.MVNO_NONE;
        switch (hashCode) {
            case -2001614626:
                if (str.equals("routines_set_bixby_volume")) {
                    Float number =
                            parameterValues.getNumber(
                                    "volume_level",
                                    Float.valueOf(
                                            SoundActionUtil.getStreamVolumeFromRoutine(
                                                    context, 11, 0)));
                    Intrinsics.checkNotNullExpressionValue(number, "getNumber(...)");
                    actionDispatcher$$ExternalSyntheticLambda6.setResponse(
                            getVolumeLabel(number.floatValue(), 11, context));
                    return;
                }
                return;
            case -1385003673:
                if (!str.equals("as_keyboard_vibration")) {
                    return;
                }
                break;
            case -774894674:
                if (str.equals("routines_set_call_volume")) {
                    Float number2 =
                            parameterValues.getNumber(
                                    "volume_level",
                                    Float.valueOf(
                                            SoundActionUtil.getStreamVolumeFromRoutine(
                                                    context, 0, 0)));
                    Intrinsics.checkNotNullExpressionValue(number2, "getNumber(...)");
                    actionDispatcher$$ExternalSyntheticLambda6.setResponse(
                            getVolumeLabel(number2.floatValue(), 0, context));
                    return;
                }
                return;
            case 230185392:
                if (!str.equals("as_dialing_keypad_tones")) {
                    return;
                }
                break;
            case 265883236:
                if (!str.equals("as_keyboard_sound")) {
                    return;
                }
                break;
            case 342936258:
                if (str.equals("as_media_volume")) {
                    String string =
                            context.getString(
                                    Utils.isTablet()
                                            ? R.string.audio_device_type_builtin_speaker_tablet
                                            : R.string.audio_device_type_builtin_speaker);
                    Float number3 =
                            parameterValues.getNumber(
                                    getLevelKeyValues(101),
                                    Float.valueOf(
                                            SoundActionUtil.getStreamVolumeFromRoutine(
                                                    context, 3, 2)));
                    Intrinsics.checkNotNullExpressionValue(number3, "getNumber(...)");
                    String m =
                            AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                                    string, ": ", getVolumeLabel(number3.floatValue(), 3, context));
                    String string2 = context.getString(R.string.audio_device_type_headset);
                    Float number4 =
                            parameterValues.getNumber(
                                    getLevelKeyValues(102),
                                    Float.valueOf(
                                            SoundActionUtil.getStreamVolumeFromRoutine(
                                                    context, 3, 22)));
                    Intrinsics.checkNotNullExpressionValue(number4, "getNumber(...)");
                    String m2 =
                            AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                                    string2,
                                    ": ",
                                    getVolumeLabel(number4.floatValue(), 3, context));
                    String string3 = context.getString(R.string.audio_device_type_bt_a2dp);
                    Float number5 =
                            parameterValues.getNumber(
                                    getLevelKeyValues(105),
                                    Float.valueOf(
                                            SoundActionUtil.getStreamVolumeFromRoutine(
                                                    context, 3, 8)));
                    Intrinsics.checkNotNullExpressionValue(number5, "getNumber(...)");
                    actionDispatcher$$ExternalSyntheticLambda6.setResponse(
                            m
                                    + "\n"
                                    + m2
                                    + "\n"
                                    + AbstractAppLabelMapFactory$$ExternalSyntheticOutline0.m(
                                            string3,
                                            ": ",
                                            getVolumeLabel(number5.floatValue(), 3, context)));
                    return;
                }
                return;
            case 728577263:
                if (str.equals("routines_set_ringtone")) {
                    String string4 =
                            parameterValues.getString(
                                    "routines_ringtone_title", ApnSettings.MVNO_NONE);
                    Intrinsics.checkNotNull(string4);
                    if (string4.length() == 0 || string4.equals("silent")) {
                        string4 = context.getString(R.string.sec_notification_sound_silent);
                    }
                    Intrinsics.checkNotNullExpressionValue(string4, "let(...)");
                    actionDispatcher$$ExternalSyntheticLambda6.setResponse(string4);
                    return;
                }
                return;
            case 1227889607:
                if (str.equals("as_volume")) {
                    int m3 =
                            (int)
                                    LifeStyleDND$$ExternalSyntheticOutline0.m(
                                            2.0f, parameterValues, "as_sound_mode");
                    Boolean bool =
                            parameterValues.getBoolean("as_do_not_change_volume", Boolean.FALSE);
                    Intrinsics.checkNotNullExpressionValue(bool, "getBoolean(...)");
                    boolean booleanValue = bool.booleanValue();
                    String m4 =
                            AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                    context.getString(R.string.sec_ringtone_title), ": ");
                    String m5 =
                            AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                    context.getString(R.string.sec_vibration_notification), ": ");
                    String m6 =
                            AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                    context.getString(R.string.sec_vibration_system), ": ");
                    StringBuilder sb = new StringBuilder();
                    if (m3 == 0) {
                        str2 = context.getString(R.string.sec_sound_mode_mute);
                        Intrinsics.checkNotNullExpressionValue(str2, "getString(...)");
                    } else if (m3 == 1) {
                        str2 = context.getString(R.string.sec_sound_mode_vibrate);
                        Intrinsics.checkNotNullExpressionValue(str2, "getString(...)");
                    } else if (m3 == 2) {
                        str2 = context.getString(R.string.sec_sound_mode_sound);
                        Intrinsics.checkNotNullExpressionValue(str2, "getString(...)");
                    }
                    sb.append(str2);
                    if (m3 == 2) {
                        if (booleanValue) {
                            actionDispatcher$$ExternalSyntheticLambda6.setResponse(sb.toString());
                            return;
                        }
                        Float number6 =
                                parameterValues.getNumber(
                                        getLevelKeyValues(2),
                                        Float.valueOf(
                                                SoundActionUtil.getStreamVolumeFromRoutine(
                                                        context, 2, 2)));
                        Intrinsics.checkNotNullExpressionValue(number6, "getNumber(...)");
                        float floatValue = number6.floatValue();
                        Float number7 =
                                parameterValues.getNumber(
                                        getLevelKeyValues(5),
                                        Float.valueOf(
                                                SoundActionUtil.getStreamVolumeFromRoutine(
                                                        context, 5, 2)));
                        Intrinsics.checkNotNullExpressionValue(number7, "getNumber(...)");
                        float floatValue2 = number7.floatValue();
                        Float number8 =
                                parameterValues.getNumber(
                                        getLevelKeyValues(1),
                                        Float.valueOf(
                                                SoundActionUtil.getStreamVolumeFromRoutine(
                                                        context, 1, 2)));
                        Intrinsics.checkNotNullExpressionValue(number8, "getNumber(...)");
                        float floatValue3 = number8.floatValue();
                        if (floatValue < 0.0f || floatValue2 < 0.0f || floatValue3 < 0.0f) {
                            actionDispatcher$$ExternalSyntheticLambda6.setResponse(sb.toString());
                            return;
                        }
                        String m7 =
                                AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                        m4, getVolumeLabel(floatValue, 2, context));
                        String m8 =
                                AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                        m5, getVolumeLabel(floatValue2, 5, context));
                        String str3 = m6 + getVolumeLabel(floatValue3, 1, context);
                        sb.append("\n");
                        sb.append(m7);
                        ConstraintWidget$$ExternalSyntheticOutline0.m(sb, "\n", m8, "\n", str3);
                    }
                    actionDispatcher$$ExternalSyntheticLambda6.setResponse(sb.toString());
                    return;
                }
                return;
            case 1329808775:
                if (!str.equals("as_vibrate_while_ringing")) {
                    return;
                }
                break;
            default:
                return;
        }
        Boolean bool2 = parameterValues.getBoolean("toggle_value", Boolean.FALSE);
        Intrinsics.checkNotNullExpressionValue(bool2, "getBoolean(...)");
        actionDispatcher$$ExternalSyntheticLambda6.setResponse(
                bool2.booleanValue()
                        ? context.getString(R.string.routine_switch_on_text)
                        : context.getString(R.string.routine_switch_off_text));
    }

    @Override // com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor
    public final List getSupportTagList() {
        return CollectionsKt__CollectionsKt.listOf(
                (Object[])
                        new String[] {
                            "routines_set_ringtone",
                            "routines_set_bixby_volume",
                            "routines_set_call_volume",
                            "as_volume",
                            "as_media_volume",
                            "as_dialing_keypad_tones",
                            "as_keyboard_sound",
                            "as_keyboard_vibration",
                            "as_vibrate_while_ringing"
                        });
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor
    public final SupportStatus isSupported(Context context, String tag) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(tag, "tag");
        int hashCode = tag.hashCode();
        SupportStatus supportStatus = SupportStatus.NOT_SUPPORTED;
        switch (hashCode) {
            case -2001614626:
                if (tag.equals("routines_set_bixby_volume") && !Rune.supportBixbyClient()) {
                    return supportStatus;
                }
                break;
            case -1385003673:
                if (tag.equals("as_keyboard_vibration")
                        && !VibUtils.hasSystemVibrationMenu(context)) {
                    return supportStatus;
                }
                break;
            case 230185392:
                if (tag.equals("as_dialing_keypad_tones")
                        && !SoundUtils.isRingtoneMenuSupported(context)) {
                    return supportStatus;
                }
                break;
            case 728577263:
                if (tag.equals("routines_set_ringtone")) {
                    Object systemService = context.getSystemService(ServiceTuple.MEDIA_CAP_AUDIO);
                    Intrinsics.checkNotNull(
                            systemService,
                            "null cannot be cast to non-null type android.media.AudioManager");
                    if (!((AudioManager) systemService).shouldShowRingtoneVolume()
                            && !SimUtils.isVoiceCapable(context)) {
                        return supportStatus;
                    }
                }
                break;
        }
        return SupportStatus.SUPPORTED;
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x0109, code lost:

       if (new java.io.File(r7).exists() != false) goto L45;
    */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onPerformAction(
            android.content.Context r5,
            java.lang.String r6,
            com.samsung.android.sdk.routines.v3.data.ParameterValues r7,
            long r8,
            com.samsung.android.sdk.routines.v3.interfaces.ActionResultCallback r10) {
        /*
            Method dump skipped, instructions count: 560
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.asbase.routine.action.SoundSettingsActionInteractor.onPerformAction(android.content.Context,"
                    + " java.lang.String, com.samsung.android.sdk.routines.v3.data.ParameterValues,"
                    + " long,"
                    + " com.samsung.android.sdk.routines.v3.interfaces.ActionResultCallback):void");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor
    public final void onPerformReverseAction(
            Context context,
            String str,
            ParameterValues parameterValues,
            long j,
            ActionDispatcher$$ExternalSyntheticLambda8 actionResultCallback) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(actionResultCallback, "actionResultCallback");
        int i = 1;
        switch (str.hashCode()) {
            case -2001614626:
                if (str.equals("routines_set_bixby_volume")) {
                    performVolumeAction(context, parameterValues, "volume_level", 11, 0);
                    break;
                }
                break;
            case -1385003673:
                if (str.equals("as_keyboard_vibration")) {
                    Boolean bool = parameterValues.getBoolean("toggle_value", Boolean.FALSE);
                    Intrinsics.checkNotNullExpressionValue(bool, "getBoolean(...)");
                    Settings.System.putInt(
                            context.getContentResolver(),
                            "sip_key_feedback_vibration",
                            bool.booleanValue() ? 1 : 0);
                    break;
                }
                break;
            case -774894674:
                if (str.equals("routines_set_call_volume")) {
                    performVolumeAction(context, parameterValues, "volume_level", 0, 0);
                    performVolumeAction(
                            context,
                            parameterValues,
                            getLevelKeyValuesFromDevice(0, "_EARPIECE"),
                            0,
                            1);
                    performVolumeAction(
                            context,
                            parameterValues,
                            getLevelKeyValuesFromDevice(0, "_SPEAKER"),
                            0,
                            2);
                    break;
                }
                break;
            case 230185392:
                if (str.equals("as_dialing_keypad_tones")) {
                    Boolean bool2 = parameterValues.getBoolean("toggle_value", Boolean.FALSE);
                    Intrinsics.checkNotNullExpressionValue(bool2, "getBoolean(...)");
                    Settings.System.putInt(
                            context.getContentResolver(),
                            "dtmf_tone",
                            bool2.booleanValue() ? 1 : 0);
                    break;
                }
                break;
            case 265883236:
                if (str.equals("as_keyboard_sound")) {
                    Boolean bool3 = parameterValues.getBoolean("toggle_value", Boolean.FALSE);
                    Intrinsics.checkNotNullExpressionValue(bool3, "getBoolean(...)");
                    Settings.System.putInt(
                            context.getContentResolver(),
                            "sip_key_feedback_sound",
                            bool3.booleanValue() ? 1 : 0);
                    break;
                }
                break;
            case 342936258:
                if (str.equals("as_media_volume")) {
                    performMediaVolumeAction(context, parameterValues);
                    break;
                }
                break;
            case 728577263:
                if (str.equals("routines_set_ringtone")) {
                    if (SimUtils.isMultiSimModel() && SimUtils.isEnabledSIM2Only()) {
                        i = 128;
                    }
                    String string =
                            parameterValues.getString(
                                    "routines_ringtone_uri", ApnSettings.MVNO_NONE);
                    Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                    RingtoneManager.setActualDefaultRingtoneUri(
                            context, i, string.length() > 0 ? Uri.parse(string) : null);
                    break;
                }
                break;
            case 1227889607:
                if (str.equals("as_volume")) {
                    performSoundModeAction(context, parameterValues);
                    break;
                }
                break;
            case 1329808775:
                if (str.equals("as_vibrate_while_ringing")) {
                    Boolean bool4 = parameterValues.getBoolean("toggle_value", Boolean.FALSE);
                    Intrinsics.checkNotNullExpressionValue(bool4, "getBoolean(...)");
                    Settings.System.putInt(
                            context.getContentResolver(),
                            "vibrate_when_ringing",
                            bool4.booleanValue() ? 1 : 0);
                    break;
                }
                break;
        }
    }

    @Override // com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor
    public final ErrorContents onRequestErrorDialogContents(
            Context context, String str, int i, long j) {
        Intrinsics.checkNotNullParameter(context, "context");
        return new ErrorContents(null, "wrong in " + str + " - " + i, null);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterative(DepthRegionTraversal.java:31)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visit(SwitchOverStringVisitor.java:60)
     */
    @Override // com.samsung.android.settings.bixbyroutinehandler.interactor.ActionInteractor
    public final UiTemplate onRequestTemplateContents(Context context, String tag) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(tag, "tag");
        Bundle bundle = new Bundle();
        bundle.putString("label_on", context.getString(R.string.routine_switch_on_text));
        bundle.putString("label_off", context.getString(R.string.routine_switch_off_text));
        switch (tag.hashCode()) {
            case -1385003673:
                if (tag.equals("as_keyboard_vibration")) {
                    bundle.putString(
                            UniversalCredentialUtil.AGENT_TITLE,
                            context.getString(R.string.sec_keyboard_vibration_action_label));
                    bundle.putBoolean(
                            "default_selection",
                            isSystemOptionOn(context, "sip_key_feedback_vibration"));
                    return new ToggleTemplate(bundle);
                }
                break;
            case 230185392:
                if (tag.equals("as_dialing_keypad_tones")) {
                    bundle.putString(
                            UniversalCredentialUtil.AGENT_TITLE,
                            context.getString(R.string.sec_dial_keypad_sound_action_label));
                    bundle.putBoolean("default_selection", isSystemOptionOn(context, "dtmf_tone"));
                    return new ToggleTemplate(bundle);
                }
                break;
            case 265883236:
                if (tag.equals("as_keyboard_sound")) {
                    bundle.putString(
                            UniversalCredentialUtil.AGENT_TITLE,
                            context.getString(R.string.sec_keyboard_sound_action_label));
                    bundle.putBoolean(
                            "default_selection",
                            isSystemOptionOn(context, "sip_key_feedback_sound"));
                    return new ToggleTemplate(bundle);
                }
                break;
            case 1329808775:
                if (tag.equals("as_vibrate_while_ringing")) {
                    bundle.putString(
                            UniversalCredentialUtil.AGENT_TITLE,
                            context.getString(R.string.sec_vibrate_on_ring_title));
                    bundle.putBoolean(
                            "default_selection", isSystemOptionOn(context, "vibrate_when_ringing"));
                    return new ToggleTemplate(bundle);
                }
                break;
        }
        return new UiTemplate(new Bundle());
    }
}
