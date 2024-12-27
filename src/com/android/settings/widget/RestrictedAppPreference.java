package com.android.settings.widget;

import android.os.UserHandle;
import android.text.TextUtils;

import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;

import com.android.settingslib.RestrictedPreferenceHelper;
import com.android.settingslib.widget.AppPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RestrictedAppPreference extends AppPreference {
    public RestrictedPreferenceHelper mHelper;
    public String userRestriction;

    public final void checkRestrictionAndSetDisabled() {
        if (TextUtils.isEmpty(this.userRestriction)) {
            return;
        }
        this.mHelper.checkRestrictionAndSetDisabled(UserHandle.myUserId(), this.userRestriction);
    }

    @Override // androidx.preference.Preference
    public final void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        this.mHelper.onAttachedToHierarchy();
        super.onAttachedToHierarchy(preferenceManager);
    }

    @Override // com.android.settingslib.widget.AppPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mHelper.onBindViewHolder(preferenceViewHolder);
    }

    @Override // androidx.preference.Preference
    public final void performClick() {
        if (this.mHelper.performClick()) {
            return;
        }
        super.performClick();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x001f  */
    /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0012  */
    @Override // androidx.preference.Preference
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setEnabled(boolean r6) {
        /*
            r5 = this;
            r0 = 1
            r1 = 0
            if (r6 == 0) goto Lf
            com.android.settingslib.RestrictedPreferenceHelper r2 = r5.mHelper
            boolean r3 = r2.mDisabledByAdmin
            if (r3 == 0) goto Lf
            r2.setDisabledByAdmin(r1)
            r2 = r0
            goto L10
        Lf:
            r2 = 0
        L10:
            if (r6 == 0) goto L1c
            com.android.settingslib.RestrictedPreferenceHelper r3 = r5.mHelper
            boolean r4 = r3.mDisabledByEcm
            if (r4 == 0) goto L1c
            r3.setDisabledByEcm(r1)
            goto L1d
        L1c:
            r0 = r2
        L1d:
            if (r0 != 0) goto L22
            super.setEnabled(r6)
        L22:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.widget.RestrictedAppPreference.setEnabled(boolean):void");
    }
}
