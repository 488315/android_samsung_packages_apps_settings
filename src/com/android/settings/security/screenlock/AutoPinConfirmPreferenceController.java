package com.android.settings.security.screenlock;

import android.content.Context;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.password.ChooseLockSettingsHelper;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.ObservablePreferenceFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AutoPinConfirmPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public final LockPatternUtils mLockPatternUtils;
    public final ObservablePreferenceFragment mParentFragment;
    public final int mUserId;

    public AutoPinConfirmPreferenceController(
            Context context,
            int i,
            LockPatternUtils lockPatternUtils,
            ObservablePreferenceFragment observablePreferenceFragment) {
        super(context);
        this.mUserId = i;
        this.mLockPatternUtils = lockPatternUtils;
        this.mParentFragment = observablePreferenceFragment;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "auto_pin_confirm";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        if (LockPatternUtils.isAutoPinConfirmFeatureAvailable()) {
            LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
            int i = this.mUserId;
            if (lockPatternUtils.getCredentialTypeForUser(i) == 3
                    && this.mLockPatternUtils.getPinLength(i) >= 6) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        boolean booleanValue = ((Boolean) obj).booleanValue();
        ObservablePreferenceFragment observablePreferenceFragment = this.mParentFragment;
        ChooseLockSettingsHelper.Builder builder =
                new ChooseLockSettingsHelper.Builder(
                        observablePreferenceFragment.getActivity(), observablePreferenceFragment);
        builder.mUserId = this.mUserId;
        builder.mRequestCode = booleanValue ? 111 : 112;
        builder.mTitle = this.mContext.getString(R.string.lock_screen_auto_pin_confirm_title);
        builder.mDescription =
                booleanValue
                        ? this.mContext.getString(R.string.auto_confirm_on_pin_verify_description)
                        : this.mContext.getString(R.string.auto_confirm_off_pin_verify_description);
        builder.mReturnCredentials = true;
        builder.show();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((TwoStatePreference) preference)
                .setChecked(this.mLockPatternUtils.isAutoPinConfirmEnabled(this.mUserId));
    }
}
