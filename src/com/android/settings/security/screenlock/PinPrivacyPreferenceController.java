package com.android.settings.security.screenlock;

import android.content.Context;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PinPrivacyPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public final LockPatternUtils lockPatternUtils;
    public final int userId;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PinPrivacyPreferenceController(
            int i, Context context, LockPatternUtils lockPatternUtils) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        this.userId = i;
        this.lockPatternUtils = lockPatternUtils;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "enhancedPinPrivacy";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return this.lockPatternUtils.getCredentialTypeForUser(this.userId) == 3;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object value) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        Intrinsics.checkNotNullParameter(value, "value");
        this.lockPatternUtils.setPinEnhancedPrivacyEnabled(
                ((Boolean) value).booleanValue(), this.userId);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        Intrinsics.checkNotNullParameter(preference, "preference");
        ((TwoStatePreference) preference)
                .setChecked(this.lockPatternUtils.isPinEnhancedPrivacyEnabled(this.userId));
    }
}
