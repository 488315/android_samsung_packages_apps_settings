package com.android.settings.accessibility;

import android.R;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.hardware.display.ColorDisplayManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.server.display.feature.flags.Flags;
import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ReduceBrightColorsPreferenceController
        extends AccessibilityQuickSettingsPrimarySwitchPreferenceController
        implements OnStart, OnStop {
    private final ColorDisplayManager mColorDisplayManager;
    private final Context mContext;
    private PrimarySwitchPreference mPreference;
    private ContentObserver mSettingsContentObserver;

    public ReduceBrightColorsPreferenceController(Context context, String str) {
        super(context, str);
        this.mContext = context;
        this.mSettingsContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.android.settings.accessibility.ReduceBrightColorsPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z, Uri uri) {
                        if (TextUtils.equals(
                                uri == null ? null : uri.getLastPathSegment(),
                                "reduce_bright_colors_activated")) {
                            ReduceBrightColorsPreferenceController
                                    reduceBrightColorsPreferenceController =
                                            ReduceBrightColorsPreferenceController.this;
                            reduceBrightColorsPreferenceController.updateState(
                                    reduceBrightColorsPreferenceController.mPreference);
                        }
                    }
                };
        this.mColorDisplayManager =
                (ColorDisplayManager) context.getSystemService(ColorDisplayManager.class);
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController, com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (PrimarySwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController, com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!(Flags.evenDimmer()
                                && this.mContext
                                        .getResources()
                                        .getBoolean(
                                                R.bool.config_forceWindowDrawsStatusBarBackground))
                        && ColorDisplayManager.isReduceBrightColorsAvailable(this.mContext))
                ? 0
                : 3;
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController, com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return com.android.settings.R.string.menu_key_accessibility;
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mContext.getText(
                com.android.settings.R.string.reduce_bright_colors_preference_summary);
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController
    public ComponentName getTileComponentName() {
        return AccessibilityShortcutController.REDUCE_BRIGHT_COLORS_TILE_SERVICE_COMPONENT_NAME;
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController
    public CharSequence getTileTooltipContent() {
        return this.mContext.getText(
                com.android.settings.R.string
                        .accessibility_reduce_bright_colors_auto_added_qs_tooltip_content);
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController, com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController, com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return this.mColorDisplayManager.isReduceBrightColorsActivated();
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("reduce_bright_colors_activated"),
                        false,
                        this.mSettingsContentObserver,
                        -2);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsContentObserver);
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController, com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        super.setChecked(z);
        return this.mColorDisplayManager.setReduceBrightColorsActivated(z);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        refreshSummary(preference);
    }

    @Override // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController, com.android.settings.core.TogglePreferenceController, com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
