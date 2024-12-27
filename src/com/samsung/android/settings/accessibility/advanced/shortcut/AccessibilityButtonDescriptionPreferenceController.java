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

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnPause;
import com.android.settingslib.core.lifecycle.events.OnResume;

import com.samsung.android.settings.accessibility.base.widget.DescriptionPreference;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccessibilityButtonDescriptionPreferenceController extends BasePreferenceController
        implements LifecycleObserver, OnResume, OnPause {
    final ContentObserver mContentObserver;
    private final ContentResolver mContentResolver;
    private DescriptionPreference mDescriptionPreference;

    public AccessibilityButtonDescriptionPreferenceController(Context context, String str) {
        super(context, str);
        this.mContentResolver = context.getContentResolver();
        this.mContentObserver =
                new ContentObserver(
                        new Handler(
                                Looper
                                        .getMainLooper())) { // from class:
                                                             // com.samsung.android.settings.accessibility.advanced.shortcut.AccessibilityButtonDescriptionPreferenceController.1
                    @Override // android.database.ContentObserver
                    public final void onChange(boolean z) {
                        AccessibilityButtonDescriptionPreferenceController
                                accessibilityButtonDescriptionPreferenceController =
                                        AccessibilityButtonDescriptionPreferenceController.this;
                        accessibilityButtonDescriptionPreferenceController
                                .refreshDescriptionPreference(
                                        accessibilityButtonDescriptionPreferenceController
                                                .mDescriptionPreference,
                                        AccessibilityButtonGestureUtil.getMode(
                                                ((AbstractPreferenceController)
                                                                AccessibilityButtonDescriptionPreferenceController
                                                                        .this)
                                                        .mContext));
                    }
                };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void refreshDescriptionPreference(
            DescriptionPreference descriptionPreference,
            AccessibilityButtonMode accessibilityButtonMode) {
        int ordinal = accessibilityButtonMode.ordinal();
        if (ordinal == 1) {
            descriptionPreference.setIcon(R.drawable.use_accessibility_gesture_2);
            return;
        }
        if (ordinal == 2) {
            descriptionPreference.setIcon(R.drawable.use_accessibility_gesture_3);
            return;
        }
        if (ordinal != 3) {
            descriptionPreference.setIcon(R.drawable.use_accessibility_button);
            String string =
                    this.mContext.getString(
                            R.string.accessibility_button_navigation_content_description);
            if (Objects.equals(descriptionPreference.imageContentDescription, string)) {
                return;
            }
            descriptionPreference.imageContentDescription = string;
            descriptionPreference.notifyChanged();
            return;
        }
        descriptionPreference.setIcon(R.drawable.use_accessibility_button_floating);
        String string2 =
                this.mContext.getString(R.string.accessibility_button_floating_content_description);
        if (Objects.equals(descriptionPreference.imageContentDescription, string2)) {
            return;
        }
        descriptionPreference.imageContentDescription = string2;
        descriptionPreference.notifyChanged();
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

    @Override // com.android.settingslib.core.lifecycle.events.OnPause
    public void onPause() {
        this.mContentResolver.unregisterContentObserver(this.mContentObserver);
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (preference instanceof DescriptionPreference) {
            DescriptionPreference descriptionPreference = (DescriptionPreference) preference;
            this.mDescriptionPreference = descriptionPreference;
            refreshDescriptionPreference(
                    descriptionPreference, AccessibilityButtonGestureUtil.getMode(this.mContext));
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
