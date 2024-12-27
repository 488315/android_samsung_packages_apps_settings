package com.android.settings.security.screenlock;

import android.content.Context;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PatternVisiblePreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public final LockPatternUtils mLockPatternUtils;
    public final int mUserId;

    public PatternVisiblePreferenceController(
            int i, Context context, LockPatternUtils lockPatternUtils) {
        super(context);
        this.mUserId = i;
        this.mLockPatternUtils = lockPatternUtils;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "visiblepattern";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.mLockPatternUtils.getCredentialTypeForUser(this.mUserId) == 1;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        this.mLockPatternUtils.setVisiblePatternEnabled(
                ((Boolean) obj).booleanValue(), this.mUserId);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((TwoStatePreference) preference)
                .setChecked(this.mLockPatternUtils.isVisiblePatternEnabled(this.mUserId));
    }
}
