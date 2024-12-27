package com.android.settingslib.search;

import com.android.settings.DisplaySettings;

import com.samsung.android.settings.asbase.audio.SecSoundSystemSoundSettings;
import com.samsung.android.settings.asbase.audio.SecVolumeSettings;
import com.samsung.android.settings.asbase.audio.SoundSettings;
import com.samsung.android.settings.asbase.vibration.SecVibrationIntensitySettings;
import com.samsung.android.settings.asbase.vibration.VibrationSystemIntensitySettings;
import com.samsung.android.settings.general.GeneralDeviceSettings;
import com.samsung.android.settings.inputmethod.MouseAndTrackpadSettings;
import com.samsung.android.settings.inputmethod.MousePointerSettingsFragment;
import com.samsung.android.settings.notification.BadgeAppIconSettings;
import com.samsung.android.settings.notification.StatusBarNotificationSettings;
import com.samsung.android.settings.notification.brief.BriefPopUpSettings;
import com.samsung.android.settings.usefulfeature.Usefulfeature;
import com.samsung.android.settings.usefulfeature.functionkey.FunctionKeySettings;
import com.samsung.android.settings.usefulfeature.motionandgestures.MotionAndGestureSettings;
import com.samsung.android.settings.usefulfeature.onehandedmode.NewOneHandOperationSettings;

import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class GtsIndexableResources {
    public final Set mProviders = new HashSet();

    public GtsIndexableResources() {
        addIndex(new GtsIndexableData(DisplaySettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(new GtsIndexableData(SecSoundSystemSoundSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(new GtsIndexableData(SecVolumeSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(new GtsIndexableData(SoundSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(new GtsIndexableData(SecVibrationIntensitySettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(new GtsIndexableData(VibrationSystemIntensitySettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(new GtsIndexableData(GeneralDeviceSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(new GtsIndexableData(MouseAndTrackpadSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(new GtsIndexableData(MousePointerSettingsFragment.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(new GtsIndexableData(BadgeAppIconSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(new GtsIndexableData(StatusBarNotificationSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(new GtsIndexableData(BriefPopUpSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(new GtsIndexableData(Usefulfeature.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(new GtsIndexableData(FunctionKeySettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(new GtsIndexableData(MotionAndGestureSettings.SEARCH_INDEX_DATA_PROVIDER));
        addIndex(new GtsIndexableData(NewOneHandOperationSettings.SEARCH_INDEX_DATA_PROVIDER));
    }

    public final void addIndex(GtsIndexableData gtsIndexableData) {
        ((HashSet) this.mProviders).add(gtsIndexableData);
    }
}
