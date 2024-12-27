package com.android.settings.display;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Process;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedPreference;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ScreenTimeoutPreferenceController extends BasePreferenceController {
    public static String PREF_NAME = "screen_timeout";
    private final CharSequence[] mTimeoutEntries;
    private final CharSequence[] mTimeoutValues;

    public ScreenTimeoutPreferenceController(Context context, String str) {
        super(context, str);
        this.mTimeoutEntries =
                context.getResources().getStringArray(R.array.screen_timeout_entries);
        this.mTimeoutValues = context.getResources().getStringArray(R.array.screen_timeout_values);
    }

    private long getCurrentScreenTimeout() {
        return Settings.System.getLong(
                this.mContext.getContentResolver(), "screen_off_timeout", 30000L);
    }

    private CharSequence getCurrentTimeout(long j) {
        int i = 0;
        while (true) {
            CharSequence[] charSequenceArr = this.mTimeoutValues;
            if (i >= charSequenceArr.length) {
                return null;
            }
            if (j == Long.parseLong(charSequenceArr[i].toString())) {
                return this.mTimeoutEntries[i];
            }
            i++;
        }
    }

    private CharSequence getLargestTimeout(long j) {
        CharSequence charSequence = null;
        int i = 0;
        while (true) {
            CharSequence[] charSequenceArr = this.mTimeoutValues;
            if (i >= charSequenceArr.length) {
                return charSequence;
            }
            if (Long.parseLong(charSequenceArr[i].toString()) <= j) {
                charSequence = this.mTimeoutEntries[i];
            }
            i++;
        }
    }

    private Long getMaxScreenTimeout() {
        DevicePolicyManager devicePolicyManager;
        if (RestrictedLockUtilsInternal.checkIfMaximumTimeToLockIsSet(this.mContext) == null
                || (devicePolicyManager =
                                (DevicePolicyManager)
                                        this.mContext.getSystemService(DevicePolicyManager.class))
                        == null) {
            return Long.MAX_VALUE;
        }
        return Long.valueOf(devicePolicyManager.getMaximumTimeToLock(null, UserHandle.myUserId()));
    }

    private RestrictedLockUtils.EnforcedAdmin getPreferenceDisablingAdmin(long j) {
        if (((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class))
                == null) {
            return null;
        }
        RestrictedLockUtils.EnforcedAdmin checkIfRestrictionEnforced =
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(
                        this.mContext, UserHandle.myUserId(), "no_config_screen_timeout");
        return (checkIfRestrictionEnforced == null && getLargestTimeout(j) == null)
                ? RestrictedLockUtilsInternal.checkIfMaximumTimeToLockIsSet(this.mContext)
                : checkIfRestrictionEnforced;
    }

    private CharSequence getTimeoutDescription(long j, long j2) {
        CharSequence[] charSequenceArr;
        CharSequence[] charSequenceArr2;
        if (j < 0
                || (charSequenceArr = this.mTimeoutEntries) == null
                || (charSequenceArr2 = this.mTimeoutValues) == null
                || charSequenceArr2.length != charSequenceArr.length) {
            return null;
        }
        return j > j2 ? getLargestTimeout(j2) : getCurrentTimeout(j);
    }

    private CharSequence getTimeoutSummary(long j) {
        CharSequence timeoutDescription = getTimeoutDescription(getCurrentScreenTimeout(), j);
        return timeoutDescription == null
                ? this.mContext.getString(R.string.screen_timeout_summary_not_set)
                : this.mContext.getString(R.string.screen_timeout_summary, timeoutDescription);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ String lambda$updateState$0() {
        return this.mContext.getString(R.string.disabled_by_policy_title);
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
        long longValue = getMaxScreenTimeout().longValue();
        RestrictedLockUtils.EnforcedAdmin preferenceDisablingAdmin =
                getPreferenceDisablingAdmin(longValue);
        if (preferenceDisablingAdmin != null) {
            preference.setEnabled(false);
            preference.setSummary(
                    ((DevicePolicyManager)
                                    this.mContext.getSystemService(DevicePolicyManager.class))
                            .getResources()
                            .getString(
                                    "Settings.DISABLED_BY_IT_ADMIN_TITLE",
                                    new Supplier() { // from class:
                                                     // com.android.settings.display.ScreenTimeoutPreferenceController$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            String lambda$updateState$0;
                                            lambda$updateState$0 =
                                                    ScreenTimeoutPreferenceController.this
                                                            .lambda$updateState$0();
                                            return lambda$updateState$0;
                                        }
                                    }));
            ((RestrictedPreference) preference).setDisabledByAdmin(preferenceDisablingAdmin);
        } else {
            if (UserManager.get(this.mContext)
                    .hasBaseUserRestriction("no_config_screen_timeout", Process.myUserHandle())) {
                preference.setEnabled(false);
            }
            preference.setSummary(getTimeoutSummary(longValue));
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
