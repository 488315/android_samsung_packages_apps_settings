package com.samsung.android.settings.accessibility.hearing.controllers;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.accessibility.AccessibilityRune;
import com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AdaptSoundController extends TogglePreferenceController
        implements AccessibilityObservableController, A11yStatusLoggingProvider {
    private static final String ADAPT_SOUND_PKG_NAME = "com.sec.hearingadjust";
    private static final String BUDS_ENABLED = "buds_enable";
    private static final String BUDS_PLUGIN_PACKAGE_NAME = "buds_plugin_package_name";
    private static final String BUDS_SOUND_EFFECT_ACTIVITY =
            "com.samsung.accessory.hearablemgr.module.hearingadjust.activity.AdaptSoundMainActivity";
    private static final String KEY_RESULT = "Result";
    private static final String TAG = "AdaptSoundController";
    private final PackageManager mPackageManager;
    private static final Uri ADAPT_SOUND_URI_CONTENT_PROVIDER =
            Uri.parse("content://com.sec.hearingadjust.compatibility.AdaptSoundProvider");
    private static final Uri GW_ADAPT_SOUND_URI_CONTENT_PROVIDER =
            Uri.parse("content://com.samsung.accessory.paranmgr");
    private static final String KEY_MAIN_SWITCH_STATUS = "ADAPT_SOUND_SWITCH";
    private static final String[] PROJECTION_MAIN_SWITCH_STATUS = {KEY_MAIN_SWITCH_STATUS};
    private static final String[] PROJECTION_SELECTED_PRESET_NAME = {"ADAPT_SOUND_PRESET_NAME"};
    private static final String[] PROJECTION_GW_SELECTED_PRESET_NAME = {
        "GW_ADAPT_SOUND_SELECTED_PROFILE"
    };
    private static final String KEY_GW_MAIN_SWITCH_STATUS = "GW_ADAPT_SOUND_SWITCH";
    private static final String[] PROJECTION_GW_MAIN_SWITCH_STATUS = {KEY_GW_MAIN_SWITCH_STATUS};

    public AdaptSoundController(Context context, String str) {
        super(context, str);
        this.mPackageManager = context.getPackageManager();
    }

    private String getBudsPluginPackageName() {
        return Settings.System.getString(
                this.mContext.getContentResolver(), BUDS_PLUGIN_PACKAGE_NAME);
    }

    private Intent getBudsSoundEffectActivityIntent() {
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.setComponent(
                new ComponentName(getBudsPluginPackageName(), BUDS_SOUND_EFFECT_ACTIVITY));
        return intent;
    }

    private String getValueFromAdaptSoundProvider(String[] strArr) {
        try {
            Cursor query =
                    this.mContext
                            .getContentResolver()
                            .query(
                                    isBudsConnected()
                                            ? GW_ADAPT_SOUND_URI_CONTENT_PROVIDER
                                            : ADAPT_SOUND_URI_CONTENT_PROVIDER,
                                    strArr,
                                    null,
                                    null,
                                    null);
            if (query != null) {
                while (query.moveToNext()) {
                    try {
                        int columnIndex = query.getColumnIndex(KEY_RESULT);
                        if (columnIndex != -1) {
                            String string = query.getString(columnIndex);
                            query.close();
                            return string;
                        }
                    } finally {
                    }
                }
            }
            if (query == null) {
                return null;
            }
            query.close();
            return null;
        } catch (Exception e) {
            Log.w(TAG, "failed to get projection (" + strArr[0] + ") value from provider.", e);
            return null;
        }
    }

    private boolean isBudsConnected() {
        return Settings.System.getInt(this.mContext.getContentResolver(), BUDS_ENABLED, 0) != 0
                && isSupportBudsSettings();
    }

    private boolean isSupportBudsSettings() {
        List<ResolveInfo> queryIntentActivities;
        return (getBudsPluginPackageName() == null
                        || (queryIntentActivities =
                                        this.mPackageManager.queryIntentActivities(
                                                getBudsSoundEffectActivityIntent(), 0))
                                == null
                        || queryIntentActivities.isEmpty())
                ? false
                : true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        if (findPreference != null) {
            findPreference.seslSetSummaryColor(
                    this.mContext.getColorStateList(R.color.text_color_primary_dark));
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (AccessibilityRune.getFloatingFeatureBooleanValue(
                        "SEC_FLOATING_FEATURE_COMMON_SUPPORT_DISABLED_MENU_K05")
                || !Utils.hasPackage(this.mContext, ADAPT_SOUND_PKG_NAME)) {
            return 3;
        }
        if (UserHandle.myUserId() != 0) {
            return 4;
        }
        return Utils.isDesktopModeEnabled(this.mContext) ? 5 : 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider
    public Map<String, String> getStatusLoggingData(Context context) {
        String valueFromAdaptSoundProvider;
        if (getAvailabilityStatus() == 3) {
            return Map.of();
        }
        String str = "Off";
        if (getThreadEnabled()
                && (valueFromAdaptSoundProvider =
                                getValueFromAdaptSoundProvider(PROJECTION_SELECTED_PRESET_NAME))
                        != null) {
            str =
                    valueFromAdaptSoundProvider.equals("Under 30 years old")
                            ? "Under30"
                            : valueFromAdaptSoundProvider.equals("30 to 60 years old")
                                    ? "30to60"
                                    : valueFromAdaptSoundProvider.equals("Over 60 years old")
                                            ? "Over60"
                                            : "Personalized";
        }
        return Map.of("A11YS4009", str);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        String valueFromAdaptSoundProvider;
        String valueFromAdaptSoundProvider2;
        return isBudsConnected()
                ? (!getThreadEnabled()
                                || (valueFromAdaptSoundProvider2 =
                                                getValueFromAdaptSoundProvider(
                                                        PROJECTION_GW_SELECTED_PRESET_NAME))
                                        == null)
                        ? ApnSettings.MVNO_NONE
                        : this.mContext.getString(
                                R.string.adapt_sound_gw_selected_preset_name,
                                valueFromAdaptSoundProvider2)
                : (!getThreadEnabled()
                                || (valueFromAdaptSoundProvider =
                                                getValueFromAdaptSoundProvider(
                                                        PROJECTION_SELECTED_PRESET_NAME))
                                        == null)
                        ? ApnSettings.MVNO_NONE
                        : valueFromAdaptSoundProvider;
    }

    @Override // com.samsung.android.settings.accessibility.base.controller.AccessibilityObservableController
    public List<Uri> getUriList() {
        int availabilityStatus = getAvailabilityStatus();
        return (availabilityStatus == 3 || availabilityStatus == 4)
                ? List.of()
                : List.of(
                        ADAPT_SOUND_URI_CONTENT_PROVIDER, Settings.System.getUriFor(BUDS_ENABLED));
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!isBudsConnected() || !getPreferenceKey().equals(preference.getKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        this.mContext.startActivity(getBudsSoundEffectActivityIntent());
        return true;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return isBudsConnected()
                ? Boolean.parseBoolean(
                        getValueFromAdaptSoundProvider(PROJECTION_GW_MAIN_SWITCH_STATUS))
                : Boolean.parseBoolean(
                        getValueFromAdaptSoundProvider(PROJECTION_MAIN_SWITCH_STATUS));
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(
                isBudsConnected() ? KEY_GW_MAIN_SWITCH_STATUS : KEY_MAIN_SWITCH_STATUS,
                Boolean.valueOf(z));
        try {
            return this.mContext
                            .getContentResolver()
                            .insert(
                                    isBudsConnected()
                                            ? GW_ADAPT_SOUND_URI_CONTENT_PROVIDER
                                            : ADAPT_SOUND_URI_CONTENT_PROVIDER,
                                    contentValues)
                    != null;
        } catch (Exception e) {
            Log.w(TAG, "setChecked - insert AdaptSound main switch value failed.", e);
            return false;
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        int availabilityStatus = getAvailabilityStatus();
        if (availabilityStatus == 3 || availabilityStatus == 4) {
            preference.setVisible(false);
            return;
        }
        preference.setVisible(true);
        preference.setEnabled(availabilityStatus == 0);
        refreshSummary(preference);
        super.updateState(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
