package com.android.settings.accessibility;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.TogglePreferenceController;
import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import com.android.settingslib.core.lifecycle.events.OnSaveInstanceState;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AccessibilityQuickSettingsPrimarySwitchPreferenceController
        extends TogglePreferenceController
        implements LifecycleObserver, OnCreate, OnDestroy, OnSaveInstanceState {
    private static final String KEY_SAVED_QS_TOOLTIP_RESHOW = "qs_tooltip_reshow";
    private final Handler mHandler;
    private boolean mNeedsQSTooltipReshow;
    private PrimarySwitchPreference mPreference;
    private AccessibilityQuickSettingsTooltipWindow mTooltipWindow;

    public AccessibilityQuickSettingsPrimarySwitchPreferenceController(
            Context context, String str) {
        super(context, str);
        this.mNeedsQSTooltipReshow = false;
        this.mHandler = new Handler(context.getMainLooper());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showQuickSettingsTooltipIfNeeded() {
        ComponentName tileComponentName;
        if (this.mPreference == null || (tileComponentName = getTileComponentName()) == null) {
            return;
        }
        if (!this.mNeedsQSTooltipReshow) {
            TextUtils.SimpleStringSplitter simpleStringSplitter =
                    AccessibilityQuickSettingUtils.sStringColonSplitter;
            return;
        }
        if (this.mPreference.mSwitch != null) {
            AccessibilityQuickSettingsTooltipWindow accessibilityQuickSettingsTooltipWindow =
                    new AccessibilityQuickSettingsTooltipWindow(this.mContext);
            this.mTooltipWindow = accessibilityQuickSettingsTooltipWindow;
            accessibilityQuickSettingsTooltipWindow.setup(getTileTooltipContent());
            this.mTooltipWindow.showAtLocation(this.mPreference.mSwitch, 49, 0, 0);
        }
        AccessibilityQuickSettingUtils.optInValueToSharedPreferences(
                this.mContext, tileComponentName);
        this.mNeedsQSTooltipReshow = false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (PrimarySwitchPreference) preferenceScreen.findPreference(getPreferenceKey());
        if (this.mNeedsQSTooltipReshow) {
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.settings.accessibility.AccessibilityQuickSettingsPrimarySwitchPreferenceController$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AccessibilityQuickSettingsPrimarySwitchPreferenceController.this
                                    .showQuickSettingsTooltipIfNeeded();
                        }
                    });
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
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
        return R.string.menu_key_accessibility;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    public abstract ComponentName getTileComponentName();

    public abstract CharSequence getTileTooltipContent();

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
        return false;
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

    @Override // com.android.settingslib.core.lifecycle.events.OnCreate
    public void onCreate(Bundle bundle) {
        if (bundle == null || !bundle.containsKey(KEY_SAVED_QS_TOOLTIP_RESHOW)) {
            return;
        }
        this.mNeedsQSTooltipReshow = bundle.getBoolean(KEY_SAVED_QS_TOOLTIP_RESHOW);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnDestroy
    public void onDestroy() {
        this.mHandler.removeCallbacksAndMessages(null);
        AccessibilityQuickSettingsTooltipWindow accessibilityQuickSettingsTooltipWindow =
                this.mTooltipWindow;
        if (accessibilityQuickSettingsTooltipWindow == null
                || !accessibilityQuickSettingsTooltipWindow.isShowing()) {
            return;
        }
        this.mTooltipWindow.dismiss();
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnSaveInstanceState
    public void onSaveInstanceState(Bundle bundle) {
        AccessibilityQuickSettingsTooltipWindow accessibilityQuickSettingsTooltipWindow =
                this.mTooltipWindow;
        boolean z =
                accessibilityQuickSettingsTooltipWindow != null
                        && accessibilityQuickSettingsTooltipWindow.isShowing();
        if (this.mNeedsQSTooltipReshow || z) {
            bundle.putBoolean(KEY_SAVED_QS_TOOLTIP_RESHOW, true);
        }
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        if (z) {
            showQuickSettingsTooltipIfNeeded();
        }
        return z;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
