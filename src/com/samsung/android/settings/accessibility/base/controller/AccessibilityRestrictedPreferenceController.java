package com.samsung.android.settings.accessibility.base.controller;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.accessibility.RestrictedPreferenceHelper;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.RestrictedPreference;

import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AccessibilityRestrictedPreferenceController extends BasePreferenceController {
    protected RestrictedPreference restrictedPreference;
    protected final RestrictedPreferenceHelper restrictedPreferenceHelper;

    public AccessibilityRestrictedPreferenceController(Context context, String str) {
        super(context, str);
        this.restrictedPreferenceHelper = new RestrictedPreferenceHelper(context);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        RestrictedPreference restrictedPreference = getRestrictedPreference(getComponentName());
        this.restrictedPreference = restrictedPreference;
        if (findPreference == null || restrictedPreference == null) {
            return;
        }
        PreferenceGroup parent = findPreference.getParent();
        if (parent != null) {
            if (!WizardManagerHelper.isUserSetupComplete(findPreference.getContext())) {
                Bundle extras = this.restrictedPreference.getExtras();
                extras.putString("settings_title", null);
                extras.putString("settings_component_name", null);
            }
            if (TextUtils.isEmpty(this.restrictedPreference.getKey())) {
                this.restrictedPreference.setKey(findPreference.getKey());
            }
            this.restrictedPreference.setOrder(findPreference.getOrder());
            this.restrictedPreference.setVisible(findPreference.isVisible());
            parent.addPreference(this.restrictedPreference);
            parent.removePreference(findPreference);
        }
        if (this instanceof GoogleAppPreferenceController) {
            this.restrictedPreference.setSummary((CharSequence) null);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public abstract ComponentName getComponentName();

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public abstract RestrictedPreference getRestrictedPreference(ComponentName componentName);

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
        if (getPreferenceKey().equals(preference.getKey())
                && (this instanceof GoogleAppPreferenceController)
                && this.restrictedPreference == null) {
            try {
                this.mContext.startActivity(
                        new Intent("android.intent.action.VIEW")
                                .setData(
                                        Uri.parse(
                                                "https://play.google.com/store/apps/details?id="
                                                        + getComponentName().getPackageName()))
                                .setPackage("com.android.vending"));
            } catch (ActivityNotFoundException e) {
                Log.w(getClass().getSimpleName(), "Failed to open Google Play Store.", e);
            }
        }
        return super.handlePreferenceTreeClick(preference);
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
