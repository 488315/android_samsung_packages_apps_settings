package com.android.settings.accessibility;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Looper;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnStart;
import com.android.settingslib.core.lifecycle.events.OnStop;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CaptioningCustomController extends BasePreferenceController
        implements LifecycleObserver, OnStart, OnStop {
    static final List<String> CAPTIONING_FEATURE_KEYS =
            Arrays.asList("accessibility_captioning_preset");
    private final CaptionHelper mCaptionHelper;
    private final ContentResolver mContentResolver;
    private Preference mCustom;
    AccessibilitySettingsContentObserver mSettingsContentObserver;

    public CaptioningCustomController(Context context, String str) {
        super(context, str);
        this.mCaptionHelper = new CaptionHelper(context);
        this.mContentResolver = context.getContentResolver();
        AccessibilitySettingsContentObserver accessibilitySettingsContentObserver =
                new AccessibilitySettingsContentObserver(new Handler(Looper.getMainLooper()));
        this.mSettingsContentObserver = accessibilitySettingsContentObserver;
        accessibilitySettingsContentObserver.registerKeysToObserverCallback(
                CAPTIONING_FEATURE_KEYS,
                new AccessibilitySettingsContentObserver
                        .ContentObserverCallback() { // from class:
                                                     // com.android.settings.accessibility.CaptioningCustomController$$ExternalSyntheticLambda0
                    @Override // com.android.settings.accessibility.AccessibilitySettingsContentObserver.ContentObserverCallback
                    public final void onChange(String str2) {
                        CaptioningCustomController.this.lambda$new$0(str2);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(String str) {
        refreshShowingCustom();
    }

    private void refreshShowingCustom() {
        this.mCustom.setVisible(this.mCaptionHelper.mCaptioningManager.getRawUserStyle() == -1);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mCustom = preferenceScreen.findPreference(getPreferenceKey());
        refreshShowingCustom();
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

    @Override // com.android.settingslib.core.lifecycle.events.OnStart
    public void onStart() {
        this.mSettingsContentObserver.register(this.mContentResolver);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnStop
    public void onStop() {
        AccessibilitySettingsContentObserver accessibilitySettingsContentObserver =
                this.mSettingsContentObserver;
        ContentResolver contentResolver = this.mContentResolver;
        accessibilitySettingsContentObserver.getClass();
        contentResolver.unregisterContentObserver(accessibilitySettingsContentObserver);
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
        refreshShowingCustom();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public CaptioningCustomController(
            Context context,
            String str,
            AccessibilitySettingsContentObserver accessibilitySettingsContentObserver) {
        this(context, str);
        this.mSettingsContentObserver = accessibilitySettingsContentObserver;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
