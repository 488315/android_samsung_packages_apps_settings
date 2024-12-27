package com.android.settings.security;

import android.content.Context;
import android.os.UserHandle;
import android.text.TextUtils;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.users.OwnerInfoSettings;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.ObservablePreferenceFragment;
import com.android.settingslib.core.lifecycle.events.OnResume;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class OwnerInfoPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, LifecycleObserver, OnResume {
    static final String KEY_OWNER_INFO = "owner_info_settings";
    public static final int MY_USER_ID = UserHandle.myUserId();
    public final LockPatternUtils mLockPatternUtils;
    public RestrictedPreference mOwnerInfoPref;
    public final ObservablePreferenceFragment mParent;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OwnerInfoCallback {
        void onOwnerInfoUpdated();
    }

    public OwnerInfoPreferenceController(
            Context context, ObservablePreferenceFragment observablePreferenceFragment) {
        super(context);
        this.mParent = observablePreferenceFragment;
        this.mLockPatternUtils = new LockPatternUtils(context);
        if (observablePreferenceFragment != null) {
            observablePreferenceFragment.getSettingsLifecycle().addObserver(this);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        this.mOwnerInfoPref =
                (RestrictedPreference) preferenceScreen.findPreference(KEY_OWNER_INFO);
    }

    public RestrictedLockUtils.EnforcedAdmin getDeviceOwner() {
        return RestrictedLockUtilsInternal.getDeviceOwner(this.mContext);
    }

    public String getDeviceOwnerInfo() {
        return this.mLockPatternUtils.getDeviceOwnerInfo();
    }

    public String getOwnerInfo() {
        return this.mLockPatternUtils.getOwnerInfo(MY_USER_ID);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return KEY_OWNER_INFO;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(KEY_OWNER_INFO, preference.getKey())) {
            return false;
        }
        RestrictedPreference restrictedPreference = this.mOwnerInfoPref;
        ObservablePreferenceFragment observablePreferenceFragment = this.mParent;
        if (!observablePreferenceFragment.isAdded()) {
            return true;
        }
        OwnerInfoSettings ownerInfoSettings = new OwnerInfoSettings();
        ownerInfoSettings.setTargetFragment(observablePreferenceFragment, 0);
        ownerInfoSettings.mPreference = restrictedPreference;
        ownerInfoSettings.show(observablePreferenceFragment.getFragmentManager(), "ownerInfo");
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    public boolean isDeviceOwnerInfoEnabled() {
        return this.mLockPatternUtils.isDeviceOwnerInfoEnabled();
    }

    public boolean isOwnerInfoEnabled() {
        return this.mLockPatternUtils.isOwnerInfoEnabled(MY_USER_ID);
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        if (this.mOwnerInfoPref != null) {
            if (isDeviceOwnerInfoEnabled()) {
                this.mOwnerInfoPref.setDisabledByAdmin(getDeviceOwner());
            } else {
                this.mOwnerInfoPref.setDisabledByAdmin(null);
                this.mOwnerInfoPref.setEnabled(
                        !this.mLockPatternUtils.isLockScreenDisabled(MY_USER_ID));
            }
        }
        updateSummary();
    }

    public final void updateSummary() {
        if (this.mOwnerInfoPref != null) {
            if (isDeviceOwnerInfoEnabled()) {
                this.mOwnerInfoPref.setSummary(getDeviceOwnerInfo());
            } else {
                this.mOwnerInfoPref.setSummary(
                        isOwnerInfoEnabled()
                                ? getOwnerInfo()
                                : this.mContext.getString(R.string.owner_info_settings_summary));
            }
        }
    }
}
