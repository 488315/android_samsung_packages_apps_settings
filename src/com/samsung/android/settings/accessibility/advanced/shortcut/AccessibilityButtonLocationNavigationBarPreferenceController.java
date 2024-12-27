package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.accessibility.AccessibilityUtil;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.logging.A11yStatusLoggingProvider;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.widget.SecRadioButtonPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccessibilityButtonLocationNavigationBarPreferenceController
        extends BasePreferenceController
        implements Preference.OnPreferenceClickListener,
                LifecycleObserver,
                OnResume,
                OnPause,
                A11yStatusLoggingProvider {
    static final String FLOATING_BUTTON_KEY = "floating_button";
    static final String FLOATING_SETTINGS_CATEGORY_KEY = "floating_button_settings";
    private SecRadioButtonPreference floatingRadioButtonPreference;
    private PreferenceCategory floatingSettingsCategory;
    private final ContentObserver mContentObserver;
    private final ContentResolver mContentResolver;
    private SecRadioButtonPreference navigationBarRadioButtonPreference;

    public AccessibilityButtonLocationNavigationBarPreferenceController(
            Context context, String str) {
        super(context, str);
        this.mContentResolver = context.getContentResolver();
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityButtonLocationNavigationBarPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        AccessibilityButtonLocationNavigationBarPreferenceController.this.refresh();
                    }
                };
    }

    private int getTitleResId() {
        int ordinal = AccessibilityButtonGestureUtil.getSupportedType(this.mContext).ordinal();
        return ordinal != 1
                ? ordinal != 2
                        ? R.string.accessibility_button_display_options_navigation_bar
                        : R.string.accessibility_button_display_options_gesture_three_fingers
                : R.string.accessibility_button_display_options_gesture_two_fingers;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refresh() {
        boolean isFloatingMenuEnabled = AccessibilityUtil.isFloatingMenuEnabled(this.mContext);
        PreferenceCategory preferenceCategory = this.floatingSettingsCategory;
        if (preferenceCategory != null) {
            preferenceCategory.setEnabled(
                    !SecAccessibilityUtils.isAccessibilityButtonEmpty(this.mContext));
            this.floatingSettingsCategory.setVisible(isFloatingMenuEnabled);
        }
        SecRadioButtonPreference secRadioButtonPreference = this.navigationBarRadioButtonPreference;
        if (secRadioButtonPreference != null) {
            secRadioButtonPreference.setTitle(getTitleResId());
            this.navigationBarRadioButtonPreference.setChecked(!isFloatingMenuEnabled);
            this.floatingRadioButtonPreference.setChecked(isFloatingMenuEnabled);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        this.floatingRadioButtonPreference =
                (SecRadioButtonPreference) preferenceScreen.findPreference(FLOATING_BUTTON_KEY);
        this.floatingSettingsCategory =
                (PreferenceCategory)
                        preferenceScreen.findPreference(FLOATING_SETTINGS_CATEGORY_KEY);
        setRadioButtonPreference(findPreference);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        int ordinal = AccessibilityButtonGestureUtil.getSupportedType(this.mContext).ordinal();
        if (ordinal == 0 || ordinal == 1 || ordinal == 2) {
            return 0;
        }
        return ordinal != 4 ? 3 : 5;
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
        String str;
        if (getAvailabilityStatus() == 3
                || SecAccessibilityUtils.isAccessibilityButtonEmpty(this.mContext)) {
            return Map.of();
        }
        int ordinal = AccessibilityButtonGestureUtil.getMode(this.mContext).ordinal();
        if (ordinal == 0) {
            str = "NavigationBar";
        } else if (ordinal == 1) {
            str = "2fingerGesture";
        } else if (ordinal == 2) {
            str = "3fingerGesture";
        } else {
            if (ordinal != 3) {
                return Map.of();
            }
            str = "FloatingOverOtherApps";
        }
        return Map.of("A11YS6004", str);
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

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mContentResolver.unregisterContentObserver(this.mContentObserver);
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public boolean onPreferenceClick(Preference preference) {
        Settings.Secure.putInt(this.mContext.getContentResolver(), "accessibility_button_mode", 0);
        refresh();
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public void onResume() {
        this.mContentResolver.registerContentObserver(
                Settings.Secure.getUriFor("accessibility_button_mode"),
                false,
                this.mContentObserver);
        this.mContentResolver.registerContentObserver(
                Settings.Secure.getUriFor("enabled_accessibility_services"),
                false,
                this.mContentObserver);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setRadioButtonPreference(Preference preference) {
        if (preference instanceof SecRadioButtonPreference) {
            SecRadioButtonPreference secRadioButtonPreference =
                    (SecRadioButtonPreference) preference;
            this.navigationBarRadioButtonPreference = secRadioButtonPreference;
            secRadioButtonPreference.setOnPreferenceClickListener(this);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        refresh();
        preference.seslSetDividerStartOffset(
                (int)
                        this.mContext
                                .getResources()
                                .getDimension(R.dimen.sec_widget_app_list_divider_margin_start));
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
