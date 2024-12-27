package com.samsung.android.settings.actions;

import com.android.settings.location.LocationSwitchBarController;
import com.android.settings.network.telephony.MobileDataPreferenceController;
import com.android.settings.system.FactoryResetPreferenceController;

import com.samsung.android.settings.account.controller.AutoSyncBaseController;
import com.samsung.android.settings.display.controller.CameraCutoutPreferenceController;
import com.samsung.android.settings.display.controller.FrontScreenAppsPreferenceController;
import com.samsung.android.settings.display.controller.FullScreenAppsPreferenceController;
import com.samsung.android.settings.display.controller.ScreenModePreferenceController;
import com.samsung.android.settings.display.controller.ScreenZoomLevelPreferenceController;
import com.samsung.android.settings.display.controller.SecFontSizePreferenceController;
import com.samsung.android.settings.display.controller.SecFontStylePreferenceController;
import com.samsung.android.settings.display.controller.SecScreenResolutionSingleChoiceController;
import com.samsung.android.settings.display.gtscontroller.BrightnessPreferenceController;
import com.samsung.android.settings.display.gtscontroller.DarkModePreferenceController;
import com.samsung.android.settings.display.gtscontroller.ScreenTimeoutPreferenceController;
import com.samsung.android.settings.display.gtscontroller.SmoothMotionPreferenceController;
import com.samsung.android.settings.softwareupdate.SoftwareUpdateBixbyController;

import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ActionIndexableResources {
    public static ActionIndexableResources mInstance;
    public final Set mProviders = new HashSet();

    public ActionIndexableResources() {
        addIndex(AutoSyncBaseController.class);
        addIndex(ScreenZoomLevelPreferenceController.class);
        addIndex(LocationSwitchBarController.class);
        addIndex(MobileDataPreferenceController.class);
        addIndex(FactoryResetPreferenceController.class);
        addIndex(SecFontSizePreferenceController.class);
        addIndex(SecFontStylePreferenceController.class);
        addIndex(SmoothMotionPreferenceController.class);
        addIndex(BrightnessPreferenceController.class);
        addIndex(CameraCutoutPreferenceController.class);
        addIndex(FullScreenAppsPreferenceController.class);
        addIndex(FrontScreenAppsPreferenceController.class);
        addIndex(ScreenModePreferenceController.class);
        addIndex(SecScreenResolutionSingleChoiceController.class);
        addIndex(ScreenTimeoutPreferenceController.class);
        addIndex(DarkModePreferenceController.class);
        addIndex(SoftwareUpdateBixbyController.class);
    }

    public final void addIndex(Class cls) {
        ((HashSet) this.mProviders).add(cls);
    }
}
