package com.samsung.android.settings.usefulfeature.labs.rotateapps;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.account.controller.SecAccountPreferenceController$$ExternalSyntheticOutline0;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.widget.SecDisabledAppearancePreference;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000D\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010"
                + "\t\u001a\u00020\n"
                + "2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r"
                + "\u001a\u00020\u000eH\u0016J\n"
                + "\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J\b\u0010\u0015\u001a\u00020\u0012H\u0016J\u0010\u0010\u0016\u001a\u00020\n"
                + "2\u0006\u0010\u0017\u001a\u00020\u0012H\u0002J\u0012\u0010\u0018\u001a\u00020\n"
                + "2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000¨\u0006\u0019"
        },
        d2 = {
            "Lcom/samsung/android/settings/usefulfeature/labs/rotateapps/RotateAllAppsPreferenceController;",
            "Lcom/android/settings/core/BasePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "mPreference",
            "Lcom/samsung/android/settings/widget/SecDisabledAppearancePreference;",
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "getLaunchIntent",
            "Landroid/content/Intent;",
            "handlePreferenceTreeClick",
            ApnSettings.MVNO_NONE,
            "preference",
            "Landroidx/preference/Preference;",
            "isControllable",
            "updatePreferenceState",
            "isSubDisplay",
            "updateState",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes3.dex */
public final class RotateAllAppsPreferenceController extends BasePreferenceController {
    public static final int $stable = 8;
    private SecDisabledAppearancePreference mPreference;

    public RotateAllAppsPreferenceController(Context context, String str) {
        super(context, str);
    }

    private final void updatePreferenceState(boolean isSubDisplay) {
        if (isSubDisplay) {
            SecDisabledAppearancePreference secDisabledAppearancePreference = this.mPreference;
            Intrinsics.checkNotNull(secDisabledAppearancePreference);
            secDisabledAppearancePreference.mMsg =
                    this.mContext.getString(R.string.sec_set_aspect_ratio_for_each_app_popup_text);
            SecDisabledAppearancePreference secDisabledAppearancePreference2 = this.mPreference;
            Intrinsics.checkNotNull(secDisabledAppearancePreference2);
            secDisabledAppearancePreference2.setEnabledAppearance(false);
        } else {
            SecDisabledAppearancePreference secDisabledAppearancePreference3 = this.mPreference;
            Intrinsics.checkNotNull(secDisabledAppearancePreference3);
            secDisabledAppearancePreference3.setEnabledAppearance(true);
        }
        SecDisabledAppearancePreference secDisabledAppearancePreference4 = this.mPreference;
        Intrinsics.checkNotNull(secDisabledAppearancePreference4);
        secDisabledAppearancePreference4.setVisible(isAvailable());
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        Intrinsics.checkNotNullParameter(screen, "screen");
        super.displayPreference(screen);
        this.mPreference =
                (SecDisabledAppearancePreference) screen.findPreference(getPreferenceKey());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        String str = Rune.COMMON_CONFIG_PACKAGE_NAME_SMART_MANAGER;
        return Utils.isTablet() ? 0 : 3;
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
    public Intent getLaunchIntent() {
        Bundle bundle = new Bundle();
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = RotateAppsSettings.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 68210;
        launchRequest.mArguments = bundle;
        return SecAccountPreferenceController$$ExternalSyntheticOutline0.m(
                subSettingLauncher, null, R.string.sec_rotate_all_apps_title, 268468224);
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
        Intrinsics.checkNotNullParameter(preference, "preference");
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return false;
        }
        SecDisabledAppearancePreference secDisabledAppearancePreference = this.mPreference;
        if (secDisabledAppearancePreference == null || secDisabledAppearancePreference.mIsEnabled) {
            return super.handlePreferenceTreeClick(preference);
        }
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public boolean isControllable() {
        return true;
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

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        updatePreferenceState(
                this.mContext.getResources().getConfiguration().semDisplayDeviceType == 5);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
