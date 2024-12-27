package com.android.settings.security.screenlock;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.preference.Preference;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.display.TimeoutListPreference;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.security.trustagent.TrustAgentManager;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.ArrayList;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class LockAfterTimeoutPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {
    public final DevicePolicyManager mDPM;
    public final LockPatternUtils mLockPatternUtils;
    public final TrustAgentManager mTrustAgentManager;
    public final int mUserId;

    public LockAfterTimeoutPreferenceController(
            int i, Context context, LockPatternUtils lockPatternUtils) {
        super(context);
        this.mUserId = i;
        this.mLockPatternUtils = lockPatternUtils;
        this.mDPM = (DevicePolicyManager) context.getSystemService("device_policy");
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mTrustAgentManager =
                featureFactoryImpl.getSecurityFeatureProvider().getTrustAgentManager();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "lock_after_timeout";
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
        try {
            Settings.Secure.putInt(
                    this.mContext.getContentResolver(),
                    "lock_screen_lock_after_timeout",
                    Integer.parseInt((String) obj));
            updateState(preference);
            return true;
        } catch (NumberFormatException e) {
            Log.e("PrefControllerMixin", "could not persist lockAfter timeout setting", e);
            return true;
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        String string;
        TimeoutListPreference timeoutListPreference = (TimeoutListPreference) preference;
        timeoutListPreference.setValue(
                String.valueOf(
                        Settings.Secure.getLong(
                                this.mContext.getContentResolver(),
                                "lock_screen_lock_after_timeout",
                                5000L)));
        if (this.mDPM != null) {
            RestrictedLockUtils.EnforcedAdmin checkIfMaximumTimeToLockIsSet =
                    RestrictedLockUtilsInternal.checkIfMaximumTimeToLockIsSet(this.mContext);
            long max =
                    Math.max(
                            0L,
                            this.mDPM.getMaximumTimeToLock(null, UserHandle.myUserId())
                                    - Math.max(
                                            0,
                                            Settings.System.getInt(
                                                    this.mContext.getContentResolver(),
                                                    "screen_off_timeout",
                                                    0)));
            if (((DevicePolicyManager)
                                    timeoutListPreference
                                            .getContext()
                                            .getSystemService("device_policy"))
                            != null
                    && (checkIfMaximumTimeToLockIsSet != null
                            || timeoutListPreference.mAdmin != null
                            || timeoutListPreference.mHelper.mDisabledByAdmin)) {
                if (checkIfMaximumTimeToLockIsSet == null) {
                    max = Long.MAX_VALUE;
                }
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                int i = 0;
                while (true) {
                    CharSequence[] charSequenceArr = timeoutListPreference.mInitialValues;
                    if (i >= charSequenceArr.length) {
                        break;
                    }
                    if (Long.parseLong(charSequenceArr[i].toString()) <= max) {
                        arrayList.add(timeoutListPreference.mInitialEntries[i]);
                        arrayList2.add(timeoutListPreference.mInitialValues[i]);
                    }
                    i++;
                }
                if (arrayList2.size() != 0) {
                    if (timeoutListPreference.mHelper.setDisabledByAdmin(null)) {
                        timeoutListPreference.notifyChanged();
                    }
                    if (arrayList.size() != timeoutListPreference.mEntries.length) {
                        int parseInt = Integer.parseInt(timeoutListPreference.mValue);
                        timeoutListPreference.mEntries =
                                (CharSequence[]) arrayList.toArray(new CharSequence[0]);
                        timeoutListPreference.mEntryValues =
                                (CharSequence[]) arrayList2.toArray(new CharSequence[0]);
                        timeoutListPreference.mAdmin = checkIfMaximumTimeToLockIsSet;
                        if (parseInt <= max) {
                            timeoutListPreference.setValue(String.valueOf(parseInt));
                        } else if (arrayList2.size() <= 0
                                || Long.parseLong(
                                                ((CharSequence)
                                                                AlertController$$ExternalSyntheticOutline0
                                                                        .m(1, arrayList2))
                                                        .toString())
                                        != max) {
                            Log.w(
                                    "TimeoutListPreference",
                                    "Default to longest timeout. Value disabled by admin:"
                                            + parseInt);
                            timeoutListPreference.setValue(
                                    ((CharSequence) arrayList2.get(arrayList2.size() - 1))
                                            .toString());
                        } else {
                            timeoutListPreference.setValue(String.valueOf(max));
                        }
                    }
                } else if (timeoutListPreference.mHelper.setDisabledByAdmin(
                        checkIfMaximumTimeToLockIsSet)) {
                    timeoutListPreference.notifyChanged();
                }
            }
        }
        if (timeoutListPreference.mHelper.mDisabledByAdmin) {
            string =
                    this.mDPM
                            .getResources()
                            .getString(
                                    "Settings.DISABLED_BY_IT_ADMIN_TITLE",
                                    new Supplier() { // from class:
                                                     // com.android.settings.security.screenlock.LockAfterTimeoutPreferenceController$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Supplier
                                        public final Object get() {
                                            String string2;
                                            string2 =
                                                    LockAfterTimeoutPreferenceController.this
                                                            .mContext.getString(
                                                            R.string.disabled_by_policy_title);
                                            return string2;
                                        }
                                    });
        } else {
            long j =
                    Settings.Secure.getLong(
                            this.mContext.getContentResolver(),
                            "lock_screen_lock_after_timeout",
                            5000L);
            CharSequence[] charSequenceArr2 = timeoutListPreference.mEntries;
            CharSequence[] charSequenceArr3 = timeoutListPreference.mEntryValues;
            int i2 = 0;
            for (int i3 = 0; i3 < charSequenceArr3.length; i3++) {
                if (j >= Long.valueOf(charSequenceArr3[i3].toString()).longValue()) {
                    i2 = i3;
                }
            }
            Context context = this.mContext;
            LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
            this.mTrustAgentManager.getClass();
            ArrayList arrayList3 =
                    (ArrayList)
                            TrustAgentManager.getActiveTrustAgents(context, lockPatternUtils, true);
            String str =
                    arrayList3.isEmpty()
                            ? null
                            : ((TrustAgentManager.TrustAgentComponentInfo) arrayList3.get(0)).title;
            string =
                    !TextUtils.isEmpty(str)
                            ? Long.valueOf(charSequenceArr3[i2].toString()).longValue() == 0
                                    ? this.mContext.getString(
                                            R.string.lock_immediately_summary_with_exception, str)
                                    : this.mContext.getString(
                                            R.string.lock_after_timeout_summary_with_exception,
                                            charSequenceArr2[i2],
                                            str)
                            : this.mContext.getString(
                                    R.string.lock_after_timeout_summary, charSequenceArr2[i2]);
        }
        timeoutListPreference.setSummary(string);
    }
}
