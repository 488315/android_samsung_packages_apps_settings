package com.samsung.android.settings.backup.controller;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.UserHandle;

import androidx.core.text.BidiFormatter;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceCategory;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SamsungServiceCategoryPreferenceController extends BasePreferenceController
        implements LifecycleObserver {
    private static final String KEY_CATEGORY_SAMSUNG_SERVICES = "category_samsungservices";
    private SecPreferenceCategory mPreference;

    public SamsungServiceCategoryPreferenceController(Context context) {
        this(context, KEY_CATEGORY_SAMSUNG_SERVICES);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreference =
                (SecPreferenceCategory)
                        preferenceScreen.findPreference(KEY_CATEGORY_SAMSUNG_SERVICES);
        this.mPreference.setTitle(
                BidiFormatter.getInstance()
                        .unicodeWrap(this.mContext.getResources().getString(R.string.cloud_title)));
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!Rune.SUPPORT_DISABLE_ACCOUNTS_SETTINGS
                && UserHandle.myUserId() == 0
                && AccountUtils.checkSamsungBackupAvailble(this.mContext)) {
            return KnoxUtils.checkKnoxCustomSettingsHiddenItem(2048) ? 2 : 0;
        }
        return 3;
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return KEY_CATEGORY_SAMSUNG_SERVICES;
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
    public boolean isSliceable() {
        return isAvailable();
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

    public SamsungServiceCategoryPreferenceController(Context context, String str) {
        super(context, str);
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
