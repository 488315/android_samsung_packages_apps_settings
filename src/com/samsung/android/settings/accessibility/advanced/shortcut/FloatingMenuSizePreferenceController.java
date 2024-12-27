package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;

import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityUtil;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FloatingMenuSizePreferenceController extends BasePreferenceController
        implements Preference.OnPreferenceChangeListener, A11yStatusLoggingProvider {
    private DropDownPreference dropDownPreference;

    public FloatingMenuSizePreferenceController(Context context, String str) {
        super(context, str);
    }

    private void setDropDownPreference(Preference preference) {
        if (preference instanceof DropDownPreference) {
            DropDownPreference dropDownPreference = (DropDownPreference) preference;
            this.dropDownPreference = dropDownPreference;
            dropDownPreference.seslSetSummaryColor(
                    this.mContext.getColorStateList(R.color.text_color_primary_dark));
        }
    }

    private void updateDropDownPreference() {
        DropDownPreference dropDownPreference = this.dropDownPreference;
        if (dropDownPreference == null) {
            return;
        }
        dropDownPreference.setValue(
                Integer.toString(
                        Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "accessibility_floating_menu_size",
                                9)));
        DropDownPreference dropDownPreference2 = this.dropDownPreference;
        dropDownPreference2.setSummary(dropDownPreference2.getEntry());
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        setDropDownPreference(preferenceScreen.findPreference(getPreferenceKey()));
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

    @Override // com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider
    public Map<String, String> getStatusLoggingData(Context context) {
        if (SecAccessibilityUtils.isAccessibilityButtonEmpty(this.mContext)
                || !AccessibilityUtil.isFloatingMenuEnabled(context)) {
            return Map.of();
        }
        int i =
                Settings.Secure.getInt(
                        context.getContentResolver(), "accessibility_floating_menu_size", 9);
        return Map.of("A11YS6005", i == 0 ? "Small" : i == 1 ? "Large" : "Medium");
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

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public boolean onPreferenceChange(Preference preference, Object obj) {
        Settings.Secure.putInt(
                this.mContext.getContentResolver(),
                "accessibility_floating_menu_size",
                Integer.parseInt((String) obj));
        updateDropDownPreference();
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
        updateDropDownPreference();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
