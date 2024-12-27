package com.android.settings.search;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import com.android.settings.DisplaySettings;
import com.android.settings.R;
import com.android.settings.accessibility.CaptioningAppearanceFragment;
import com.android.settings.accessibility.CaptioningMoreOptionsFragment;
import com.android.settings.accessibility.CaptioningPropertiesFragment;
import com.android.settings.accessibility.ToggleColorInversionPreferenceFragment;
import com.android.settings.accessibility.ToggleDaltonizerPreferenceFragment;
import com.android.settings.accessibility.ToggleReduceBrightColorsPreferenceFragment;
import com.android.settings.accessibility.VibrationIntensitySettingsFragment;
import com.android.settings.connecteddevice.PreviouslyConnectedDeviceDashboardFragment;
import com.android.settings.connecteddevice.fastpair.FastPairDeviceDashboardFragment;
import com.android.settings.connecteddevice.threadnetwork.ThreadNetworkFragment;
import com.android.settings.development.DevelopmentSettingsDashboardFragment;
import com.android.settings.display.ScreenTimeoutSettings;
import com.android.settings.fuelgauge.SmartBatterySettings;
import com.android.settings.gestures.OneHandedSettings;
import com.android.settings.homepage.TopLevelSettings;
import com.android.settings.location.LocationServices;
import com.android.settings.notification.zen.ZenModeRestrictNotificationsSettings;
import com.android.settings.system.ResetDashboardFragment;
import com.android.settings.wfd.WifiDisplaySettings;
import com.android.settingslib.search.SearchIndexableData;
import com.android.settingslib.search.SearchIndexableResources;
import com.android.settingslib.search.SearchIndexableResourcesMobile;

import com.samsung.android.settings.accessibility.advanced.AdvancedSettingsFragment;
import com.samsung.android.settings.accessibility.advanced.SecAccessibilityControlTimeoutPreferenceFragment;
import com.samsung.android.settings.accessibility.advanced.flashnotification.SecFlashNotificationsPreferenceFragment;
import com.samsung.android.settings.accessibility.dexterity.InteractionAndDexterityFragment;
import com.samsung.android.settings.accessibility.dexterity.autoaction.AutoActionPreferenceFragment;
import com.samsung.android.settings.accessibility.hearing.AmplifyAmbientSoundPreferenceFragment;
import com.samsung.android.settings.accessibility.hearing.HearingAidPreferenceFragment;
import com.samsung.android.settings.accessibility.hearing.HearingEnhancementsFragment;
import com.samsung.android.settings.accessibility.hearing.MonoAudioFragment;
import com.samsung.android.settings.accessibility.hearing.MuteAllSoundFragment;
import com.samsung.android.settings.accessibility.home.SecAccessibilitySettings;
import com.samsung.android.settings.accessibility.vision.HighContrastFontFragment;
import com.samsung.android.settings.accessibility.vision.MagnifierCameraFragment;
import com.samsung.android.settings.accessibility.vision.VisibilityEnhancementsFragment;
import com.samsung.android.settings.accessibility.vision.color.ColorAdjustmentMainFragment;
import com.samsung.android.settings.accessibility.vision.color.ColorLensFragment;
import com.samsung.android.settings.accessibility.vision.color.ReluminoFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SearchFeatureProviderImpl {
    public SearchIndexableResourcesMobile mSearchIndexableResources;

    public final Intent buildSearchIntent(Context context, int i) {
        return new Intent("android.settings.APP_SEARCH_SETTINGS")
                .setPackage(context.getString(R.string.config_settingsintelligence_package_name))
                .putExtra(
                        "android.intent.extra.REFERRER",
                        new Uri.Builder()
                                .scheme("android-app")
                                .authority(context.getPackageName())
                                .path(String.valueOf(i))
                                .build());
    }

    public final SearchIndexableResources getSearchIndexableResources() {
        if (this.mSearchIndexableResources == null) {
            SearchIndexableResourcesMobile searchIndexableResourcesMobile =
                    new SearchIndexableResourcesMobile();
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            DisplaySettings.class, DisplaySettings.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            CaptioningAppearanceFragment.class,
                            CaptioningAppearanceFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            CaptioningMoreOptionsFragment.class,
                            CaptioningMoreOptionsFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            CaptioningPropertiesFragment.class,
                            CaptioningPropertiesFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            ToggleColorInversionPreferenceFragment.class,
                            ToggleColorInversionPreferenceFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            ToggleDaltonizerPreferenceFragment.class,
                            ToggleDaltonizerPreferenceFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            ToggleReduceBrightColorsPreferenceFragment.class,
                            ToggleReduceBrightColorsPreferenceFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            VibrationIntensitySettingsFragment.class,
                            VibrationIntensitySettingsFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            PreviouslyConnectedDeviceDashboardFragment.class,
                            PreviouslyConnectedDeviceDashboardFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            FastPairDeviceDashboardFragment.class,
                            FastPairDeviceDashboardFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            DevelopmentSettingsDashboardFragment.class,
                            DevelopmentSettingsDashboardFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            ScreenTimeoutSettings.class,
                            ScreenTimeoutSettings.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            SmartBatterySettings.class,
                            SmartBatterySettings.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            OneHandedSettings.class, OneHandedSettings.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            TopLevelSettings.class, TopLevelSettings.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            LocationServices.class, LocationServices.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            ZenModeRestrictNotificationsSettings.class,
                            ZenModeRestrictNotificationsSettings.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            ResetDashboardFragment.class,
                            ResetDashboardFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            WifiDisplaySettings.class,
                            WifiDisplaySettings.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            AdvancedSettingsFragment.class,
                            AdvancedSettingsFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            SecAccessibilityControlTimeoutPreferenceFragment.class,
                            SecAccessibilityControlTimeoutPreferenceFragment
                                    .SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            SecFlashNotificationsPreferenceFragment.class,
                            SecFlashNotificationsPreferenceFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            InteractionAndDexterityFragment.class,
                            InteractionAndDexterityFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            AutoActionPreferenceFragment.class,
                            AutoActionPreferenceFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            AmplifyAmbientSoundPreferenceFragment.class,
                            AmplifyAmbientSoundPreferenceFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            HearingAidPreferenceFragment.class,
                            HearingAidPreferenceFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            HearingEnhancementsFragment.class,
                            HearingEnhancementsFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            MonoAudioFragment.class, MonoAudioFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            MuteAllSoundFragment.class,
                            MuteAllSoundFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            SecAccessibilitySettings.class,
                            SecAccessibilitySettings.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            HighContrastFontFragment.class,
                            HighContrastFontFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            MagnifierCameraFragment.class,
                            MagnifierCameraFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            VisibilityEnhancementsFragment.class,
                            VisibilityEnhancementsFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            ColorAdjustmentMainFragment.class,
                            ColorAdjustmentMainFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            ColorLensFragment.class, ColorLensFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            ReluminoFragment.class, ReluminoFragment.SEARCH_INDEX_DATA_PROVIDER));
            searchIndexableResourcesMobile.addIndex(
                    new SearchIndexableData(
                            ThreadNetworkFragment.class,
                            ThreadNetworkFragment.SEARCH_INDEX_DATA_PROVIDER));
            this.mSearchIndexableResources = searchIndexableResourcesMobile;
        }
        return this.mSearchIndexableResources;
    }

    public final void verifyLaunchSearchResultPageCaller(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException(
                    "ExternalSettingsTrampoline intents must be called with"
                        + " startActivityForResult");
        }
        if (!(TextUtils.equals(str, context.getPackageName())
                || TextUtils.equals(
                        context.getString(R.string.config_settingsintelligence_package_name),
                        str))) {
            throw new SecurityException(
                    "Search result intents must be called with from an allowlisted package.");
        }
    }
}
