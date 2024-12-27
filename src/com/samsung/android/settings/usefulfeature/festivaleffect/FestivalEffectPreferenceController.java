package com.samsung.android.settings.usefulfeature.festivaleffect;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.util.secutil.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.SecPreferenceScreen;
import androidx.preference.SecPreferenceUtils;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.TogglePreferenceController;

import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.usefulfeature.UsefulfeatureUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class FestivalEffectPreferenceController extends TogglePreferenceController {
    private static final String KEY_FESTIVAL_EFFECT = "key_festival_effect";
    private static final String TAG = "FestivalEffectPreferenceController";
    private SecPreferenceScreen mPreference;

    public FestivalEffectPreferenceController(Context context) {
        this(context, KEY_FESTIVAL_EFFECT);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        SecPreferenceScreen secPreferenceScreen =
                (SecPreferenceScreen) preferenceScreen.findPreference(KEY_FESTIVAL_EFFECT);
        this.mPreference = secPreferenceScreen;
        secPreferenceScreen.getClass();
        SecPreferenceUtils.applySummaryColor(secPreferenceScreen, true);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!Utils.hasPackage(this.mContext, "com.samsung.android.myeventcenter")) {
            return 3;
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean z = UsefulfeatureUtils.mAccessibilityEnabled;
        int i = Settings.System.getInt(contentResolver, "easy_mode_switch", 1);
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i, "isEasyModeStatus ", "UsefulfeatureUtils");
        return i == 0 ? 5 : 0;
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
        return R.string.menu_key_advanced_features;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public CharSequence getSummary() {
        return this.mContext.getString(
                getThreadEnabled() ? R.string.switch_on_text : R.string.switch_off_text);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        SecPreferenceScreen secPreferenceScreen = this.mPreference;
        if (secPreferenceScreen != null && preference.equals(secPreferenceScreen)) {
            try {
                this.mContext.startActivity(
                        new Intent().setAction("com.samsung.android.myeventcenter.MY_EVENT"));
                return true;
            } catch (ActivityNotFoundException e) {
                Log.secD(TAG, "ActivityNotFoundException in mFestivalEffect");
                e.printStackTrace();
            }
        }
        return super.handlePreferenceTreeClick(preference);
    }

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
        return Settings.System.getInt(
                        this.mContext.getContentResolver(),
                        "current_sec_theme_package_festival_enabled",
                        0)
                != 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return isAvailable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public boolean setChecked(boolean z) {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public FestivalEffectPreferenceController(Context context, String str) {
        super(context, str);
    }
}
