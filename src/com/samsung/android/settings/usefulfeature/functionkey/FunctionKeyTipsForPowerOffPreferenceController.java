package com.samsung.android.settings.usefulfeature.functionkey;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.knox.KnoxUtils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000@\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0002\b\u0006\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0000\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u000eH\u0016J\u000e\u0010\u001c\u001a\u00020\u00142\u0006\u0010\u001d\u001a\u00020\fJ\u0012\u0010\u001e\u001a\u00020\u00142\b\u0010\u001b\u001a\u0004\u0018\u00010\u000eH\u0016R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0082D¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\n"
                + "\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0002\n"
                + "\u0000R\u001c\u0010\r"
                + "\u001a\u0004\u0018\u00010\u000eX\u0086\u000e¢\u0006\u000e\n"
                + "\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u001f"
        },
        d2 = {
            "Lcom/samsung/android/settings/usefulfeature/functionkey/FunctionKeyTipsForPowerOffPreferenceController;",
            "Lcom/android/settings/core/BasePreferenceController;",
            "context",
            "Landroid/content/Context;",
            "preferenceKey",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/lang/String;)V",
            "DEFAULT",
            "EXTRA_TYPE",
            "SETTING",
            "mActivityHistory",
            "mParentFragment",
            "Landroidx/fragment/app/Fragment;",
            "mPreference",
            "Landroidx/preference/Preference;",
            "getMPreference",
            "()Landroidx/preference/Preference;",
            "setMPreference",
            "(Landroidx/preference/Preference;)V",
            "displayPreference",
            ApnSettings.MVNO_NONE,
            "screen",
            "Landroidx/preference/PreferenceScreen;",
            "getAvailabilityStatus",
            ApnSettings.MVNO_NONE,
            "handlePreferenceTreeClick",
            ApnSettings.MVNO_NONE,
            "preference",
            "init",
            "parentFragment",
            "updateState",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes3.dex */
public final class FunctionKeyTipsForPowerOffPreferenceController extends BasePreferenceController {
    public static final int $stable = 8;
    private final String DEFAULT;
    private final String EXTRA_TYPE;
    private final String SETTING;
    private String mActivityHistory;
    private Fragment mParentFragment;
    private Preference mPreference;

    public FunctionKeyTipsForPowerOffPreferenceController(Context context, String str) {
        super(context, str);
        this.EXTRA_TYPE = "type";
        this.DEFAULT = "default";
        this.SETTING = "setting";
        this.mActivityHistory = "default";
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        this.mPreference = screen != null ? screen.findPreference(getPreferenceKey()) : null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!Rune.supportFunctionKey() || Rune.isMaintenanceMode() || !Rune.supportBixbyClient()) {
            return 3;
        }
        if (!Utils.isGuestUser(this.mContext) && !Utils.isGuestMode(this.mContext)) {
            if (KnoxUtils.isApplicationRestricted(this.mContext, getPreferenceKey(), "hide")) {
                return 3;
            }
            if (!KnoxUtils.isApplicationRestricted(this.mContext, getPreferenceKey(), "grayout")) {
                return 0;
            }
        }
        return 5;
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

    public final Preference getMPreference() {
        return this.mPreference;
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
        FragmentActivity activity;
        Intrinsics.checkNotNullParameter(preference, "preference");
        if (!TextUtils.equals(preference.getKey(), getPreferenceKey())) {
            return super.handlePreferenceTreeClick(preference);
        }
        if (TextUtils.isEmpty(this.mActivityHistory)
                || !Intrinsics.areEqual(this.mActivityHistory, this.SETTING)) {
            Bundle bundle = new Bundle();
            bundle.putString(this.EXTRA_TYPE, this.SETTING);
            SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
            String name = FunctionKeyTips.class.getName();
            SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
            launchRequest.mDestinationName = name;
            subSettingLauncher.setTitleRes(R.string.sec_function_key_tips_title, null);
            launchRequest.mSourceMetricsCategory = getMetricsCategory();
            launchRequest.mArguments = bundle;
            subSettingLauncher.launch();
        } else {
            Fragment fragment = this.mParentFragment;
            if (fragment != null && (activity = fragment.getActivity()) != null) {
                activity.finish();
            }
        }
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public final void init(Fragment parentFragment) {
        Intrinsics.checkNotNullParameter(parentFragment, "parentFragment");
        this.mParentFragment = parentFragment;
        FragmentActivity activity = parentFragment.getActivity();
        Intent intent = activity != null ? activity.getIntent() : null;
        String valueOf =
                String.valueOf(intent != null ? intent.getStringExtra(this.EXTRA_TYPE) : null);
        this.mActivityHistory = valueOf;
        if (TextUtils.isEmpty(valueOf)) {
            Fragment fragment = this.mParentFragment;
            Bundle arguments = fragment != null ? fragment.getArguments() : null;
            this.mActivityHistory =
                    String.valueOf(
                            arguments != null
                                    ? arguments.getString(this.EXTRA_TYPE, this.DEFAULT)
                                    : null);
        }
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

    public final void setMPreference(Preference preference) {
        this.mPreference = preference;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        if (Rune.supportBixbyClient()) {
            boolean z =
                    Settings.System.getInt(
                                    this.mContext.getContentResolver(),
                                    "dedicated_app_side_switch",
                                    0)
                            != 0;
            Preference preference2 = this.mPreference;
            if (preference2 == null) {
                return;
            }
            preference2.setVisible(!z);
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
