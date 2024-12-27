package com.samsung.android.settings.bixby.actionhandler;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;

import com.samsung.android.settings.bixby.control.Control;
import com.samsung.android.settings.bixby.control.ControlFactory;
import com.samsung.android.settings.bixby.control.actionparam.BaseActionParam;
import com.samsung.android.settings.bixby.control.actionparam.Parameter;
import com.samsung.android.settings.bixby.control.actionparam.SettingsActionParam;
import com.samsung.android.settings.bixby.control.commands.BaseCommand;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SettingsActionHandler extends BaseActionHandler {
    public static final String[] ACTIONS = {
        "bixby.settingsApp.GetOnOff_AdaptiveColorTone",
        "bixby.settingsApp.SetOnOff_AdaptiveColorTone",
        "bixby.settingsApp.Goto_AdaptiveColorToneSetting",
        "bixby.settingsApp.GetOnOff_NoDisturb",
        "bixby.settingsApp.SetOnOff_NoDisturb",
        "bixby.settingsApp.Goto_NoDisturbSetting",
        "bixby.settingsApp.GetOnOff_MobileData",
        "bixby.settingsApp.SetOnOff_MobileData",
        "bixby.settingsApp.Goto_MobileDataSetting",
        "bixby.settingsApp.Enable_MobileData",
        "bixby.settingsApp.Disable_MobileData",
        "bixby.settingsApp.Goto_DataUsageSetting",
        "bixby.settingsApp.GetOnOff_Airplane",
        "bixby.settingsApp.SetOnOff_Airplane",
        "bixby.settingsApp.Goto_AirplaneSetting",
        "bixby.settingsApp.Enable_Airplane",
        "bixby.settingsApp.Disable_Airplane",
        "bixby.settingsApp.GetOnOff_BlueLight",
        "bixby.settingsApp.SetOnOff_BlueLight",
        "bixby.settingsApp.Goto_BlueLightSetting",
        "bixby.settingsApp.GetOnOff_AutoBrightness",
        "bixby.settingsApp.SetOnOff_AutoBrightness",
        "bixby.settingsApp.Goto_AutoBrightnessSetting",
        "bixby.settingsApp.GetOnOff_NightTheme",
        "bixby.settingsApp.SetOnOff_NightTheme",
        "bixby.settingsApp.Goto_NightThemeSetting",
        "bixby.settingsApp.GetOnOff_Location",
        "bixby.settingsApp.SetOnOff_Location",
        "bixby.settingsApp.Goto_LocationSetting",
        "bixby.settingsApp.Get_JumpToAppResult",
        "bixby.settingsApp.Goto_JumpToAppMenu",
        "bixby.settingsApp.GetOnOff_Synchronize",
        "bixby.settingsApp.Goto_SynchronizeSetting",
        "bixby.settingsApp.SetOn_Synchronize",
        "bixby.settingsApp.SetOff_Synchronize",
        "bixby.settingsApp.GetOnOff_PinWindows",
        "bixby.settingsApp.SetOnOff_PinWindows",
        "bixby.settingsApp.Goto_PinWindowsSetting",
        "bixby.settingsApp.GetOnOff_ScreenSaver",
        "bixby.settingsApp.SetOnOff_ScreenSaver",
        "bixby.settingsApp.Goto_ScreenSaverSetting",
        "bixby.settingsApp.Get_FontSize",
        "bixby.settingsApp.ChangeFontSize",
        "bixby.settingsApp.Goto_FontSetting",
        "bixby.settingsApp.Get_WhiteBalance",
        "bixby.settingsApp.ChangeWhiteBalance",
        "bixby.settingsApp.Goto_WhiteBalanceSetting",
        "bixby.settingsApp.Get_ScreenZoom",
        "bixby.settingsApp.ChangeScreenZoom",
        "bixby.settingsApp.Goto_ScreenZoomSetting",
        "bixby.settingsApp.Get_SoundMode",
        "bixby.settingsApp.Set_SoundMode",
        "bixby.settingsApp.Goto_SoundModeSetting",
        "bixby.settingsApp.Get_ScreenTimeOut",
        "bixby.settingsApp.Set_ScreenTimeOut",
        "bixby.settingsApp.Goto_ScreenTimeOutSetting",
        "bixby.settingsApp.Goto_LockScreenSetting",
        "bixby.settingsApp.Goto_RingtoneScreenSetting",
        "bixby.settingsApp.Goto_MainSetting",
        "bixby.settingsApp.Goto_SoundVibrateSetting",
        "bixby.settingsApp.Goto_VibratePatternSetting",
        "bixby.settingsApp.Goto_DisplaySetting",
        "bixby.settingsApp.Goto_ScreenModeSetting",
        "bixby.settingsApp.Goto_FullScreenAppsSetting",
        "bixby.settingsApp.Goto_StatusBarSetting",
        "bixby.settingsApp.Goto_NavigationBarSetting",
        "bixby.settingsApp.Goto_AdvancedFeaturesSetting",
        "bixby.settingsApp.Goto_OneHandModeSetting",
        "bixby.settingsApp.Goto_FingerSensorGesturesSetting",
        "bixby.settingsApp.Goto_SwipeToCaptureSetting",
        "bixby.settingsApp.Goto_SmartAlertSetting",
        "bixby.settingsApp.Goto_SmartCaptureSetting",
        "bixby.settingsApp.Goto_EasyMuteSetting",
        "bixby.settingsApp.Goto_DualMessengerSetting",
        "bixby.settingsApp.Goto_SwipeToCallOrSendMessagesSetting",
        "bixby.settingsApp.Goto_SendSOSMessagesSetting",
        "bixby.settingsApp.Goto_DirectShareSetting",
        "bixby.settingsApp.Goto_VideoEnhancerSetting",
        "bixby.settingsApp.Goto_ApplicationMenuSetting",
        "bixby.settingsApp.Goto_CloudAndAccountsSetting",
        "bixby.settingsApp.Goto_AccountSetting",
        "bixby.settingsApp.Goto_GeneralManagementSetting",
        "bixby.settingsApp.Goto_LanguageSetting",
        "bixby.settingsApp.Goto_ChangeLanguage",
        "bixby.settingsApp.Goto_ScreenKeyboardSetting",
        "bixby.settingsApp.Goto_ResetSetting",
        "bixby.settingsApp.Goto_AboutDeviceSetting",
        "bixby.settingsApp.Goto_BiometricsAndSecuritySetting",
        "bixby.settingsApp.Goto_StatusSetting",
        "bixby.settingsApp.Goto_FingerprintsSetting",
        "bixby.settingsApp.Goto_SimStatusSetting",
        "bixby.settingsApp.Goto_FaceRecognitionSetting",
        "bixby.settingsApp.Goto_IrisSetting",
        "bixby.settingsApp.Goto_FactoryDataResetSetting",
        "bixby.settingsApp.Goto_ResetAllSettingsToDefaultSetting",
        "bixby.settingsApp.Goto_HardPressSetting",
        "bixby.settingsApp.Goto_NotificationSoundsSetting",
        "bixby.settingsApp.Goto_FontStyleSetting",
        "bixby.settingsApp.Goto_SideKeySetting",
        "bixby.settingsApp.Goto_SoftwareUpdateSetting",
        "bixby.settingsApp.Goto_SamsungKeyboardSetting",
        "bixby.settingsApp.Goto_VolumeSetting",
        "bixby.settingsApp.Goto_ScreenResolutionSetting",
        "bixby.settingsApp.Set_ScreenResolution",
        "bixby.settingsApp.Get_MuteDuration",
        "bixby.settingsApp.Set_MuteDuration",
        "bixby.settingsApp.Goto_MuteDurationSetting",
        "bixby.settingsApp.Open_UserManual",
        "bixby.settingsApp.CheckColorSettings",
        "bixby.settingsApp.RecoverColorSettings",
        "bixby.settingsApp.GetOnOff_SmartStay",
        "bixby.settingsApp.SetOnOff_SmartStay",
        "bixby.settingsApp.GOTO_SmartStaySetting"
    };

    @Override // com.samsung.android.settings.bixby.actionhandler.BaseActionHandler
    public final String executeActionInternal(BaseActionParam baseActionParam) {
        long j;
        Bundle execute;
        SettingsActionParam settingsActionParam =
                new SettingsActionParam(
                        baseActionParam.mContext,
                        baseActionParam.mActionName,
                        baseActionParam.mExtra);
        String str = baseActionParam.mActionName;
        if (TextUtils.equals(str, "bixby.settingsApp.SetOnOff_NightTheme")) {
            Iterator it = ((ArrayList) settingsActionParam.mParamList).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Parameter parameter = (Parameter) it.next();
                if (TextUtils.equals("value", parameter.key)) {
                    parameter.value =
                            TextUtils.equals(parameter.value, "true")
                                    ? "1"
                                    : DATA.DM_FIELD_INDEX.PCSCF_DOMAIN;
                }
            }
        } else if (TextUtils.equals(str, "bixby.settingsApp.Set_ScreenTimeOut")) {
            Iterator it2 = ((ArrayList) settingsActionParam.mParamList).iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                Parameter parameter2 = (Parameter) it2.next();
                if (TextUtils.equals("value", parameter2.key)) {
                    String str2 = parameter2.value;
                    try {
                        j = Long.parseLong(str2) * 1000;
                    } catch (NumberFormatException unused) {
                        MotionLayout$$ExternalSyntheticOutline0.m(
                                "fail to convert value : ", str2, "SettingsActionParam");
                        j = 0;
                    }
                    parameter2.value = String.valueOf(j);
                    break;
                }
            }
        }
        Control createControl =
                ControlFactory.LazyHolder.sControlFactory.createControl(settingsActionParam);
        new Bundle();
        if (createControl instanceof BaseCommand) {
            execute =
                    createControl.execute(
                            settingsActionParam.mActionName.equals(
                                            "bixby.settingsApp.Goto_SendSOSMessagesSetting")
                                    ? "com.sec.android.app.safetyassurance.command"
                                    : "com.android.settings.command");
        } else {
            execute = createControl.execute(null);
        }
        return this.mResultConverter.convert(execute, settingsActionParam.mActionName);
    }
}
