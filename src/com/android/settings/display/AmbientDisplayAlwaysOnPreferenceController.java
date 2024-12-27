package com.android.settings.display;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.display.AmbientDisplayConfiguration;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;

import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AmbientDisplayAlwaysOnPreferenceController extends TogglePreferenceController {
    private static final String AOD_SUPPRESSED_TOKEN = "winddown";
    private static final int MY_USER = UserHandle.myUserId();
    private static final String PROP_AWARE_AVAILABLE = "ro.vendor.aware_available";
    private final int OFF;
    private final int ON;
    private AmbientDisplayConfiguration mConfig;

    public AmbientDisplayAlwaysOnPreferenceController(Context context, String str) {
        super(context, str);
        this.ON = 1;
        this.OFF = 0;
    }

    private AmbientDisplayConfiguration getConfig() {
        if (this.mConfig == null) {
            this.mConfig = new AmbientDisplayConfiguration(this.mContext);
        }
        return this.mConfig;
    }

    public static boolean isAodSuppressedByBedtime(Context context) {
        try {
            return ((PowerManager) context.getSystemService(PowerManager.class))
                    .isAmbientDisplaySuppressedForTokenByApp(
                            AOD_SUPPRESSED_TOKEN,
                            context.getPackageManager()
                                    .getApplicationInfo(
                                            context.getString(R.string.config_systemWellbeing), 0)
                                    .uid);
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    public static boolean isAvailable(AmbientDisplayConfiguration ambientDisplayConfiguration) {
        return ambientDisplayConfiguration.alwaysOnAvailableForUser(MY_USER);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!isAvailable(getConfig())
                        || SystemProperties.getBoolean(PROP_AWARE_AVAILABLE, false))
                ? 3
                : 0;
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

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return com.android.settings.R.string.menu_key_display;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        Context context = this.mContext;
        return context.getText(
                isAodSuppressedByBedtime(context)
                        ? com.android.settings.R.string.aware_summary_when_bedtime_on
                        : com.android.settings.R.string.doze_always_on_summary);
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
        return getConfig().alwaysOnEnabled(MY_USER);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isPublicSlice() {
        return TextUtils.equals(getPreferenceKey(), "ambient_display_always_on");
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return true;
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
        Settings.Secure.putInt(this.mContext.getContentResolver(), "doze_always_on", z ? 1 : 0);
        return true;
    }

    public AmbientDisplayAlwaysOnPreferenceController setConfig(
            AmbientDisplayConfiguration ambientDisplayConfiguration) {
        this.mConfig = ambientDisplayConfiguration;
        return this;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        refreshSummary(preference);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
