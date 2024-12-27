package com.android.settings.password;

import android.app.admin.PasswordMetrics;
import android.content.Context;
import android.os.UserManager;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ChooseLockGenericController {
    public final int mAppRequestedMinComplexity;
    public final Context mContext;
    public final boolean mDevicePasswordRequirementOnly;
    public final boolean mHideInsecureScreenLockTypes;
    public final LockPatternUtils mLockPatternUtils;
    public final ManagedLockPasswordProvider mManagedPasswordProvider;
    public final int mUnificationProfileId;
    public final int mUserId;

    public ChooseLockGenericController(
            Context context,
            int i,
            ManagedLockPasswordProvider managedLockPasswordProvider,
            LockPatternUtils lockPatternUtils,
            boolean z,
            int i2,
            boolean z2,
            int i3) {
        this.mContext = context;
        this.mUserId = i;
        this.mManagedPasswordProvider = managedLockPasswordProvider;
        this.mLockPatternUtils = lockPatternUtils;
        this.mHideInsecureScreenLockTypes = z;
        this.mAppRequestedMinComplexity = i2;
        this.mDevicePasswordRequirementOnly = z2;
        this.mUnificationProfileId = i3;
    }

    public final int getAggregatedPasswordComplexity() {
        int max =
                Math.max(
                        this.mAppRequestedMinComplexity,
                        this.mLockPatternUtils.getRequestedPasswordComplexity(
                                this.mUserId, this.mDevicePasswordRequirementOnly));
        int i = this.mUnificationProfileId;
        return i != -10000
                ? Math.max(max, this.mLockPatternUtils.getRequestedPasswordComplexity(i))
                : max;
    }

    public final CharSequence getTitle(ScreenLockType screenLockType) {
        switch (screenLockType) {
            case NONE:
                return this.mContext.getText(R.string.sec_unlock_set_unlock_off_title);
            case SWIPE:
                return this.mContext.getText(R.string.sec_unlock_set_unlock_none_title);
            case PATTERN:
                return this.mContext.getText(R.string.sec_unlock_set_unlock_pattern_title);
            case PIN:
                return this.mContext.getText(R.string.sec_unlock_set_unlock_pin_title);
            case PASSWORD:
                return this.mContext.getText(R.string.sec_unlock_set_unlock_password_title);
            case MANAGED:
                this.mManagedPasswordProvider.getClass();
                return ApnSettings.MVNO_NONE;
            case EF109:
                return this.mContext.getText(R.string.unlock_set_unlock_cac_pin_title);
            default:
                return null;
        }
    }

    public final List getVisibleAndEnabledScreenLockTypes() {
        ArrayList arrayList = new ArrayList();
        for (ScreenLockType screenLockType : ScreenLockType.values()) {
            if (isScreenLockVisible(screenLockType)) {
                LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
                int i = this.mUserId;
                if (!lockPatternUtils.isCredentialsDisabledForUser(i)) {
                    int i2 = screenLockType.maxQuality;
                    PasswordMetrics requestedPasswordMetrics =
                            this.mLockPatternUtils.getRequestedPasswordMetrics(
                                    i, this.mDevicePasswordRequirementOnly);
                    int i3 = this.mUnificationProfileId;
                    if (i3 != -10000) {
                        requestedPasswordMetrics.maxWith(
                                this.mLockPatternUtils.getRequestedPasswordMetrics(i3));
                    }
                    if (i2
                            >= Math.max(
                                    0,
                                    Math.max(
                                            LockPatternUtils.credentialTypeToPasswordQuality(
                                                    requestedPasswordMetrics.credType),
                                            PasswordMetrics.complexityLevelToMinQuality(
                                                    getAggregatedPasswordComplexity())))) {
                        arrayList.add(screenLockType);
                    }
                }
            }
        }
        return arrayList;
    }

    public final boolean isScreenLockVisible(ScreenLockType screenLockType) {
        boolean isManagedProfile =
                ((UserManager) this.mContext.getSystemService(UserManager.class))
                        .isManagedProfile(this.mUserId);
        int ordinal = screenLockType.ordinal();
        boolean z = this.mHideInsecureScreenLockTypes;
        if (ordinal == 0) {
            return (z
                            || this.mContext
                                    .getResources()
                                    .getBoolean(R.bool.config_hide_none_security_option)
                            || isManagedProfile)
                    ? false
                    : true;
        }
        if (ordinal == 1) {
            return (z
                            || this.mContext
                                    .getResources()
                                    .getBoolean(R.bool.config_hide_swipe_security_option)
                            || isManagedProfile)
                    ? false
                    : true;
        }
        if (ordinal == 2 || ordinal == 3 || ordinal == 4) {
            return this.mLockPatternUtils.hasSecureLockScreen();
        }
        if (ordinal != 5) {
            return true;
        }
        this.mManagedPasswordProvider.getClass();
        return false;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public int mAppRequestedMinComplexity;
        public final Context mContext;
        public boolean mDevicePasswordRequirementOnly;
        public boolean mHideInsecureScreenLockTypes;
        public final LockPatternUtils mLockPatternUtils;
        public final ManagedLockPasswordProvider mManagedPasswordProvider;
        public int mUnificationProfileId;
        public final int mUserId;

        public Builder(int i, Context context, LockPatternUtils lockPatternUtils) {
            this(context, i, new ManagedLockPasswordProvider(), lockPatternUtils);
        }

        public final ChooseLockGenericController build() {
            return new ChooseLockGenericController(
                    this.mContext,
                    this.mUserId,
                    this.mManagedPasswordProvider,
                    this.mLockPatternUtils,
                    this.mHideInsecureScreenLockTypes,
                    this.mAppRequestedMinComplexity,
                    this.mDevicePasswordRequirementOnly,
                    this.mUnificationProfileId);
        }

        public Builder(Context context, int i) {
            this(i, context, new LockPatternUtils(context));
        }

        public Builder(
                Context context,
                int i,
                ManagedLockPasswordProvider managedLockPasswordProvider,
                LockPatternUtils lockPatternUtils) {
            this.mHideInsecureScreenLockTypes = false;
            this.mAppRequestedMinComplexity = 0;
            this.mDevicePasswordRequirementOnly = false;
            this.mUnificationProfileId = -10000;
            this.mContext = context;
            this.mUserId = i;
            this.mManagedPasswordProvider = managedLockPasswordProvider;
            this.mLockPatternUtils = lockPatternUtils;
        }
    }
}
