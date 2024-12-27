package com.samsung.android.settings.bixby.control.actionparam;

import android.text.TextUtils;

import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.media.SemSoundAssistantManager;
import com.samsung.android.sdk.bixby2.util.BixbyContextInfo;
import com.samsung.android.sdk.bixby2.util.BixbyUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SystemUIActionParam extends BaseActionParam {
    public final String mVolumeType;

    public SystemUIActionParam(BaseActionParam baseActionParam) {
        super(baseActionParam.mContext, baseActionParam.mActionName, baseActionParam.mExtra);
        List asList =
                Arrays.asList("GetVolumeLevel", "SetVolumeLevel", "MuteVolume", "UnMuteVolume");
        this.mVolumeType = ApnSettings.MVNO_NONE;
        if (asList.contains(this.mActionName)) {
            if (isMusicActive()
                    || isMediaControlActive()
                    || new SemSoundAssistantManager(this.mContext).getVolumeMode(1)) {
                this.mVolumeType = "Media";
            } else {
                this.mVolumeType = "Ringtones";
            }
        }
    }

    @Override // com.samsung.android.settings.bixby.control.actionparam.BaseActionParam
    public final void createParamIfNeeded() {
        HashMap hashMap = new HashMap();
        this.mSpecificParamsMap = hashMap;
        hashMap.put("PlayMusic", new Parameter("mode", String.valueOf(0)));
        this.mSpecificParamsMap.put("StopMusic", new Parameter("mode", String.valueOf(2)));
        this.mSpecificParamsMap.put("SkipSong", new Parameter("mode", String.valueOf(4)));
        this.mSpecificParamsMap.put("PreviousSong", new Parameter("mode", String.valueOf(5)));
        this.mSpecificParamsMap.put("ReplayCurrentSong", new Parameter("mode", String.valueOf(3)));
        this.mSpecificParamsMap.put(
                "MoveFromCurrentPosition", new Parameter("mode", String.valueOf(8)));
        this.mSpecificParamsMap.put("SeekTo", new Parameter("mode", String.valueOf(9)));
        this.mSpecificParamsMap.put("FastForward", new Parameter("mode", String.valueOf(6)));
        this.mSpecificParamsMap.put("Rewind", new Parameter("mode", String.valueOf(7)));
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "true", this.mSpecificParamsMap, "TurnOnAutoRotate");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "true", this.mSpecificParamsMap, "TurnOnFlashlight");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "false", this.mSpecificParamsMap, "TurnOffAutoRotate");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "false", this.mSpecificParamsMap, "TurnOffFlashlight");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "false", this.mSpecificParamsMap, "TurnOffScreen");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "true", this.mSpecificParamsMap, "MuteVolume");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "true", this.mSpecificParamsMap, "MuteVolumeAll");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "true", this.mSpecificParamsMap, "MuteVolumeRingtones");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "true", this.mSpecificParamsMap, "MuteVolumeMedia");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "true", this.mSpecificParamsMap, "MuteVolumeNoti");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "true", this.mSpecificParamsMap, "MuteVolumeSystem");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "true", this.mSpecificParamsMap, "MuteVolumeBixby");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "true", this.mSpecificParamsMap, "MuteVolumeBluetooth");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "false", this.mSpecificParamsMap, "UnMuteVolume");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "false", this.mSpecificParamsMap, "UnMuteVolumeAll");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "false", this.mSpecificParamsMap, "UnMuteVolumeRingtones");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "false", this.mSpecificParamsMap, "UnMuteVolumeMedia");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "false", this.mSpecificParamsMap, "UnMuteVolumeNoti");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "false", this.mSpecificParamsMap, "UnMuteVolumeSystem");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "false", this.mSpecificParamsMap, "UnMuteVolumeBixby");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "value", "false", this.mSpecificParamsMap, "UnMuteVolumeBluetooth");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "direction", "up", this.mSpecificParamsMap, "ScrollUp");
        NotificationParam$$ExternalSyntheticOutline0.m(
                "direction", "down", this.mSpecificParamsMap, "ScrollDown");
        Parameter parameter = (Parameter) this.mSpecificParamsMap.get(this.mActionName);
        if (parameter != null) {
            this.mParamList.add(parameter);
        }
        if (isMediaControlActive()) {
            this.mParamList.add(new Parameter("music_active", "true"));
        }
        if (isMusicActive()) {
            this.mParamList.add(new Parameter("media_control", "true"));
        }
    }

    @Override // com.samsung.android.settings.bixby.control.actionparam.BaseActionParam
    public final String getValue() {
        String parameterValue = getParameterValue("value");
        return TextUtils.isEmpty(parameterValue) ? getParameterValue("level") : parameterValue;
    }

    public final boolean isMediaControlActive() {
        BixbyContextInfo bixbyContextInfo = BixbyUtils.getBixbyContextInfo(this.mExtra);
        boolean z = bixbyContextInfo != null ? bixbyContextInfo.isMediaControlActive : false;
        AbsAdapter$$ExternalSyntheticOutline0.m("bMediaControlActive : ", "SystemUIActionParam", z);
        return z;
    }

    public final boolean isMusicActive() {
        BixbyContextInfo bixbyContextInfo = BixbyUtils.getBixbyContextInfo(this.mExtra);
        boolean z = bixbyContextInfo != null ? bixbyContextInfo.isMusicActive : false;
        AbsAdapter$$ExternalSyntheticOutline0.m("bMusicActive : ", "SystemUIActionParam", z);
        return z;
    }

    @Override // com.samsung.android.settings.bixby.control.actionparam.BaseActionParam
    public final void parsingBundle() {
        super.parsingBundle();
        if ("IncreaseBrightness".equals(this.mActionName)) {
            if (getParameterValue("level") == null) {
                this.mParamList.add(new Parameter("level", "+1"));
            }
        } else if ("DecreaseBrightness".equals(this.mActionName)) {
            if (getParameterValue("level") == null) {
                this.mParamList.add(new Parameter("level", "-1"));
            } else if ("MAX".equalsIgnoreCase(getParameterValue("level"))) {
                this.mParamList.add(new Parameter("level", "MIN"));
            }
        }
    }
}
