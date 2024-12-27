package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.accessibility.AccessibilityUtil;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.widget.SecRadioButtonPreference;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccessibilityButtonLocationFloatingPreferenceController
        extends BasePreferenceController implements Preference.OnPreferenceClickListener {
    static final String FLOATING_SETTINGS_CATEGORY_KEY = "floating_button_settings";
    static final String LOCATION_CATEGORY_KEY = "accessibility_button_location";
    static final String NAVIGATION_BAR_KEY = "navigation_bar";
    private SecRadioButtonPreference floatingRadioButtonPreference;
    private PreferenceCategory floatingSettingsCategory;
    private PreferenceCategory locationCategory;
    private SecRadioButtonPreference navigationBarRadioButtonPreference;

    public AccessibilityButtonLocationFloatingPreferenceController(Context context, String str) {
        super(context, str);
    }

    private boolean getButtonLocationCategoryVisibility() {
        return (Settings.Global.getInt(
                                        this.mContext.getContentResolver(),
                                        "navigation_bar_gesture_while_hidden",
                                        0)
                                > 0
                        || AccessibilityButtonGestureUtil.hasNavigationBar(this.mContext))
                && !Rune.supportNavigationBarForHardKey();
    }

    private void refresh() {
        boolean isAccessibilityButtonEmpty =
                SecAccessibilityUtils.isAccessibilityButtonEmpty(this.mContext);
        boolean isFloatingMenuEnabled = AccessibilityUtil.isFloatingMenuEnabled(this.mContext);
        PreferenceCategory preferenceCategory = this.locationCategory;
        boolean z = false;
        if (preferenceCategory != null) {
            preferenceCategory.setEnabled(
                    (isAccessibilityButtonEmpty || Rune.isSamsungDexMode(this.mContext))
                            ? false
                            : true);
            this.locationCategory.setVisible(getButtonLocationCategoryVisibility());
        }
        PreferenceCategory preferenceCategory2 = this.floatingSettingsCategory;
        if (preferenceCategory2 != null) {
            if (!isAccessibilityButtonEmpty && !Rune.isSamsungDexMode(this.mContext)) {
                z = true;
            }
            preferenceCategory2.setEnabled(z);
            this.floatingSettingsCategory.setVisible(isFloatingMenuEnabled);
        }
        SecRadioButtonPreference secRadioButtonPreference = this.floatingRadioButtonPreference;
        if (secRadioButtonPreference != null) {
            secRadioButtonPreference.setChecked(isFloatingMenuEnabled);
            this.navigationBarRadioButtonPreference.setChecked(!isFloatingMenuEnabled);
        }
    }

    private void setRadioButtonPreference(Preference preference) {
        if (preference instanceof SecRadioButtonPreference) {
            SecRadioButtonPreference secRadioButtonPreference =
                    (SecRadioButtonPreference) preference;
            this.floatingRadioButtonPreference = secRadioButtonPreference;
            secRadioButtonPreference.setOnPreferenceClickListener(this);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.floatingSettingsCategory =
                (PreferenceCategory)
                        preferenceScreen.findPreference(FLOATING_SETTINGS_CATEGORY_KEY);
        this.locationCategory =
                (PreferenceCategory) preferenceScreen.findPreference(LOCATION_CATEGORY_KEY);
        this.navigationBarRadioButtonPreference =
                (SecRadioButtonPreference) preferenceScreen.findPreference(NAVIGATION_BAR_KEY);
        setRadioButtonPreference(findPreference);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        Settings.Secure.putInt(this.mContext.getContentResolver(), "accessibility_button_mode", 1);
        refresh();
        return true;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        refresh();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
