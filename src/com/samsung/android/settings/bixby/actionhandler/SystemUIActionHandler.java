package com.samsung.android.settings.bixby.actionhandler;

import android.os.Bundle;

import com.samsung.android.sdk.command.template.CommandTemplate;
import com.samsung.android.sdk.command.template.UnformattedTemplate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SystemUIActionHandler extends BaseActionHandler {
    public static final String[] ACTIONS = {
        "SetVolumeLevel",
        "SetVolumeLevelRingtones",
        "SetVolumeLevelMedia",
        "SetVolumeLevelSystem",
        "SetVolumeLevelNoti",
        "SetVolumeLevelBixby",
        "SetVolumeLevelBluetooth",
        "GetVolumeLevel",
        "GetVolumeLevelRingtones",
        "GetVolumeLevelMedia",
        "GetVolumeLevelSystem",
        "GetVolumeLevelNoti",
        "GetVolumeLevelBixby",
        "GetVolumeLevelBluetooth",
        "MuteVolume",
        "MuteVolumeAll",
        "MuteVolumeRingtones",
        "MuteVolumeMedia",
        "MuteVolumeNoti",
        "MuteVolumeSystem",
        "MuteVolumeBixby",
        "MuteVolumeBluetooth",
        "UnMuteVolume",
        "UnMuteVolumeAll",
        "UnMuteVolumeRingtones",
        "UnMuteVolumeMedia",
        "UnMuteVolumeNoti",
        "UnMuteVolumeSystem",
        "UnMuteVolumeBixby",
        "UnMuteVolumeBluetooth",
        "CaptureScreen",
        "ShareScreenshot",
        "ShareScreenshotUri",
        "GoToHomeScreen",
        "Back",
        "IncreaseBrightness",
        "DecreaseBrightness",
        "SetBrightness",
        "bixby.settingsApp.GetOnOff_AutoBrightnessCover",
        "bixby.settingsApp.SetOnOff_AutoBrightnessCover",
        "bixby.settingsApp.Goto_AutoBrightnessCover",
        "ScrollUp",
        "ScrollDown",
        "ScrollLeft",
        "ScrollRight",
        "Goto_EdgeLighting",
        "DummySystem",
        "PowerOff",
        "Reboot",
        "TurnOnFlashlight",
        "TurnOffFlashlight",
        "SetFlashLightLevel",
        "GoToFlashLightSetting",
        "TurnOffScreen",
        "TurnOnAutoRotate",
        "TurnOffAutoRotate",
        "SetLandscapeMode",
        "SetPortraitMode",
        "CoverFlexDoubleTab",
        "DeleteNotification",
        "OpenNotificationPanel",
        "CloseQuickPanelScreen",
        "ReadNotificationWithID",
        "ReplyNotification",
        "AppNotificationPermissionSetting",
        "AppNotificationPermissionSettingOff",
        "AppNotificationSoundSetting",
        "DiagnoseTroubleShooting",
        "LaunchApplication",
        "CloseApplication",
        "CloseForegroundApplication",
        "CloseAllApplications",
        "CloseAllBackgroundApps",
        "CloseAllAppsExceptSpecificOnes",
        "StartMultiWindow",
        "OpenRecentsApp",
        "AppResizable",
        "LaunchMostRecentApplication",
        "CloseMultipleApplications",
        "StartAppSplitPosition",
        "ExchangePositionOfSplitScreen",
        "ChangeLayoutOfSplitScreen",
        "ReplaceAppOfSplitScreen",
        "MaximizeApp",
        "CheckOrientation",
        "CheckSplitType",
        "CheckSplitState",
        "CheckLauncherVisible",
        "GetPackageInSplit",
        "PlayMusic",
        "StopMusic",
        "SkipSong",
        "PreviousSong",
        "ReplayCurrentSong",
        "MoveFromCurrentPosition",
        "SeekTo",
        "FastForward",
        "Rewind",
        "FindAppInfo"
    };

    public static String getValueAsJSON(Bundle bundle) {
        if (!bundle.containsKey("template")) {
            return "fail";
        }
        CommandTemplate createTemplateFromBundle =
                CommandTemplate.createTemplateFromBundle(bundle.getBundle("template"));
        return createTemplateFromBundle.getTemplateType() == 6
                ? ((UnformattedTemplate) createTemplateFromBundle).mJSONString
                : "fail";
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:14:0x019a  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x01a5  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x010f  */
    /* JADX WARN: Type inference failed for: r9v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r9v14 */
    /* JADX WARN: Type inference failed for: r9v16 */
    /* JADX WARN: Type inference failed for: r9v6 */
    @Override // com.samsung.android.settings.bixby.actionhandler.BaseActionHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String executeActionInternal(
            com.samsung.android.settings.bixby.control.actionparam.BaseActionParam r17) {
        /*
            Method dump skipped, instructions count: 566
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.bixby.actionhandler.SystemUIActionHandler.executeActionInternal(com.samsung.android.settings.bixby.control.actionparam.BaseActionParam):java.lang.String");
    }

    @Override // com.samsung.android.settings.bixby.actionhandler.BaseActionHandler
    public final boolean isAffectedByKnoxPolicy() {
        return false;
    }
}
