package com.android.settings.nfc;

import android.content.Context;
import android.os.UserManager;

import androidx.preference.TwoStatePreference;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SecureNfcEnabler extends BaseNfcEnabler {
    public final TwoStatePreference mPreference;
    public final UserManager mUserManager;

    public SecureNfcEnabler(Context context, TwoStatePreference twoStatePreference) {
        super(context);
        this.mPreference = twoStatePreference;
        this.mUserManager = (UserManager) context.getSystemService(UserManager.class);
    }

    @Override // com.android.settings.nfc.BaseNfcEnabler
    public final void handleNfcStateChanged(int i) {
        TwoStatePreference twoStatePreference = this.mPreference;
        if (i == 1) {
            twoStatePreference.setSummary(R.string.nfc_disabled_summary);
            twoStatePreference.setEnabled(false);
            return;
        }
        if (i == 2) {
            twoStatePreference.setEnabled(false);
            return;
        }
        if (i != 3) {
            if (i != 4) {
                return;
            }
            twoStatePreference.setEnabled(false);
        } else {
            twoStatePreference.setSummary(R.string.nfc_secure_toggle_summary);
            twoStatePreference.setChecked(twoStatePreference.isChecked());
            twoStatePreference.setEnabled(this.mUserManager.isPrimaryUser());
        }
    }
}
