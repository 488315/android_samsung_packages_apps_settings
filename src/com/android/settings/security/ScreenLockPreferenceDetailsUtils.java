package com.android.settings.security;

import android.content.Context;
import android.content.Intent;
import android.os.UserHandle;
import android.os.UserManager;
import android.os.storage.StorageManager;

import com.android.internal.app.UnlaunchableAppActivity;
import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.overlay.FeatureFactoryImpl;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ScreenLockPreferenceDetailsUtils {
    public final Context mContext;
    public final LockPatternUtils mLockPatternUtils;
    public final int mProfileChallengeUserId;
    public final UserManager mUm;
    public final int mUserId;

    public ScreenLockPreferenceDetailsUtils(Context context) {
        int myUserId = UserHandle.myUserId();
        this.mUserId = myUserId;
        this.mContext = context;
        UserManager userManager = (UserManager) context.getSystemService(UserManager.class);
        this.mUm = userManager;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mLockPatternUtils =
                featureFactoryImpl.getSecurityFeatureProvider().getLockPatternUtils(context);
        this.mProfileChallengeUserId = Utils.getManagedProfileId(userManager, myUserId);
    }

    public final Intent getQuietModeDialogIntent() {
        int i = this.mProfileChallengeUserId;
        if (i == -10000
                || this.mLockPatternUtils.isSeparateProfileChallengeEnabled(i)
                || !StorageManager.isFileEncrypted()
                || !this.mUm.isQuietModeEnabled(UserHandle.of(i))) {
            return null;
        }
        return UnlaunchableAppActivity.createInQuietModeDialogIntent(i);
    }

    public final String getSummary(int i) {
        Integer valueOf;
        if (this.mLockPatternUtils.isSecure(i)) {
            int keyguardStoredPasswordQuality =
                    this.mLockPatternUtils.getKeyguardStoredPasswordQuality(i);
            if (keyguardStoredPasswordQuality != 65536) {
                if (keyguardStoredPasswordQuality != 131072
                        && keyguardStoredPasswordQuality != 196608) {
                    if (keyguardStoredPasswordQuality != 262144
                            && keyguardStoredPasswordQuality != 327680
                            && keyguardStoredPasswordQuality != 393216) {
                        if (keyguardStoredPasswordQuality != 458752) {
                            if (keyguardStoredPasswordQuality != 524288) {
                                valueOf = null;
                            }
                        }
                    }
                    valueOf = Integer.valueOf(R.string.sec_unlock_set_unlock_password_title);
                }
                valueOf = Integer.valueOf(R.string.sec_unlock_set_unlock_pin_title);
            } else {
                valueOf = Integer.valueOf(R.string.sec_unlock_set_unlock_pattern_title);
            }
        } else {
            valueOf =
                    (i == this.mProfileChallengeUserId
                                    || this.mLockPatternUtils.isLockScreenDisabled(i))
                            ? Integer.valueOf(R.string.sec_unlock_set_unlock_off_title)
                            : Integer.valueOf(R.string.sec_unlock_set_unlock_none_title);
        }
        if (valueOf != null) {
            return this.mContext.getResources().getString(valueOf.intValue());
        }
        return null;
    }
}
