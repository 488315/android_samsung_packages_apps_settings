package com.android.settings.security.screenlock;

import android.content.Context;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.TwoStatePreference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.security.trustagent.TrustAgentManager;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class PowerButtonInstantLockPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public final LockPatternUtils mLockPatternUtils;
    public final TrustAgentManager mTrustAgentManager;
    public final int mUserId;

    public PowerButtonInstantLockPreferenceController(
            int i, Context context, LockPatternUtils lockPatternUtils) {
        super(context);
        this.mUserId = i;
        this.mLockPatternUtils = lockPatternUtils;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mTrustAgentManager =
                featureFactoryImpl.getSecurityFeatureProvider().getTrustAgentManager();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "power_button_instantly_locks";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        int i = this.mUserId;
        if (!lockPatternUtils.isSecure(i)) {
            return false;
        }
        int keyguardStoredPasswordQuality =
                this.mLockPatternUtils.getKeyguardStoredPasswordQuality(i);
        return keyguardStoredPasswordQuality == 65536
                || keyguardStoredPasswordQuality == 131072
                || keyguardStoredPasswordQuality == 196608
                || keyguardStoredPasswordQuality == 262144
                || keyguardStoredPasswordQuality == 327680
                || keyguardStoredPasswordQuality == 393216
                || keyguardStoredPasswordQuality == 524288;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        this.mLockPatternUtils.setPowerButtonInstantlyLocks(
                ((Boolean) obj).booleanValue(), this.mUserId);
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        ((TwoStatePreference) preference)
                .setChecked(this.mLockPatternUtils.getPowerButtonInstantlyLocks(this.mUserId));
        Context context = this.mContext;
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        this.mTrustAgentManager.getClass();
        ArrayList arrayList =
                (ArrayList) TrustAgentManager.getActiveTrustAgents(context, lockPatternUtils, true);
        String str =
                arrayList.isEmpty()
                        ? null
                        : ((TrustAgentManager.TrustAgentComponentInfo) arrayList.get(0)).title;
        if (TextUtils.isEmpty(str)) {
            preference.setSummary(R.string.summary_empty);
        } else {
            preference.setSummary(
                    this.mContext.getString(
                            R.string.lockpattern_settings_power_button_instantly_locks_summary,
                            str));
        }
    }
}
