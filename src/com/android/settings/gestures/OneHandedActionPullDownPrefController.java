package com.android.settings.gestures;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class OneHandedActionPullDownPrefController extends BasePreferenceController
        implements OneHandedSettingsUtils.TogglesCallback, LifecycleObserver, OnStart, OnStop {
    private Preference mPreference;
    private final OneHandedSettingsUtils mUtils;

    public OneHandedActionPullDownPrefController(Context context, String str) {
        super(context, str);
        this.mUtils = new OneHandedSettingsUtils(context);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference = preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (OneHandedSettingsUtils.isSupportOneHandedMode()
                        && OneHandedSettingsUtils.canEnableController(this.mContext))
                ? 0
                : 5;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!getPreferenceKey().equals(preference.getKey())) {
            return false;
        }
        Context context = this.mContext;
        String str = OneHandedSettingsUtils.ONE_HANDED_MODE_TARGET_NAME;
        Settings.Secure.putIntForUser(
                context.getContentResolver(),
                "swipe_bottom_to_notification_enabled",
                0,
                OneHandedSettingsUtils.sCurrentUserId);
        if (preference instanceof SelectorWithWidgetPreference) {
            ((SelectorWithWidgetPreference) preference).setChecked(true);
        }
        return true;
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

    @Override // com.android.settings.gestures.OneHandedSettingsUtils.TogglesCallback
    public void onChange(Uri uri) {
        if (this.mPreference == null) {
            return;
        }
        if (uri.equals(OneHandedSettingsUtils.ONE_HANDED_MODE_ENABLED_URI)
                || uri.equals(OneHandedSettingsUtils.SOFTWARE_SHORTCUT_ENABLED_URI)
                || uri.equals(OneHandedSettingsUtils.HARDWARE_SHORTCUT_ENABLED_URI)) {
            this.mPreference.setEnabled(OneHandedSettingsUtils.canEnableController(this.mContext));
        } else if (uri.equals(OneHandedSettingsUtils.SHOW_NOTIFICATION_ENABLED_URI)) {
            updateState(this.mPreference);
        }
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mUtils.registerToggleAwareObserver(this);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        this.mUtils.unregisterToggleAwareObserver();
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
        if (preference instanceof SelectorWithWidgetPreference) {
            ((SelectorWithWidgetPreference) preference)
                    .setChecked(
                            !OneHandedSettingsUtils.isSwipeDownNotificationEnabled(this.mContext));
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
