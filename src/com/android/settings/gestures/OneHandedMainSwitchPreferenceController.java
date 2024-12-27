package com.android.settings.gestures;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.provider.Settings;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.widget.SettingsMainSwitchPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;
import com.android.settingslib.widget.MainSwitchPreference;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class OneHandedMainSwitchPreferenceController extends SettingsMainSwitchPreferenceController
        implements OneHandedSettingsUtils.TogglesCallback, LifecycleObserver, OnStart, OnStop {
    private MainSwitchPreference mPreference;
    private final OneHandedSettingsUtils mUtils;

    public OneHandedMainSwitchPreferenceController(Context context, String str) {
        super(context, str);
        this.mUtils = new OneHandedSettingsUtils(context);
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (MainSwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return (!OneHandedSettingsUtils.isSupportOneHandedMode()
                        || Settings.Secure.getIntForUser(
                                        this.mContext.getContentResolver(),
                                        "navigation_mode",
                                        2,
                                        OneHandedSettingsUtils.sCurrentUserId)
                                == 0)
                ? 5
                : 0;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return R.string.menu_key_system;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    /* renamed from: isChecked */
    public boolean getThreadEnabled() {
        return OneHandedSettingsUtils.isOneHandedModeEnabled(this.mContext);
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.gestures.OneHandedSettingsUtils.TogglesCallback
    public void onChange(Uri uri) {
        if (this.mPreference != null
                && uri.equals(OneHandedSettingsUtils.ONE_HANDED_MODE_ENABLED_URI)) {
            this.mPreference.setChecked(getThreadEnabled());
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

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (z) {
            Context context = this.mContext;
            String str = OneHandedSettingsUtils.ONE_HANDED_MODE_TARGET_NAME;
            Settings.Secure.putIntForUser(
                    context.getContentResolver(),
                    "taps_app_to_exit",
                    1,
                    OneHandedSettingsUtils.sCurrentUserId);
            Context context2 = this.mContext;
            Settings.Secure.putIntForUser(
                    context2.getContentResolver(),
                    "one_handed_mode_timeout",
                    OneHandedSettingsUtils.OneHandedTimeout.MEDIUM.getValue(),
                    OneHandedSettingsUtils.sCurrentUserId);
        }
        Context context3 = this.mContext;
        String str2 = OneHandedSettingsUtils.ONE_HANDED_MODE_TARGET_NAME;
        Settings.Secure.putIntForUser(
                context3.getContentResolver(),
                "one_handed_mode_enabled",
                z ? 1 : 0,
                OneHandedSettingsUtils.sCurrentUserId);
        return true;
    }

    @Override // com.android.settings.widget.SettingsMainSwitchPreferenceController,
              // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
