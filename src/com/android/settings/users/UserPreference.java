package com.android.settings.users;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.AttributeSet;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.samsung.android.settings.widget.SecRestrictedPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UserPreference extends SecRestrictedPreference {
    public static final UserPreference$$ExternalSyntheticLambda0 SERIAL_NUMBER_COMPARATOR =
            new UserPreference$$ExternalSyntheticLambda0();
    public int mSerialNumber;
    public final int mUserId;

    public UserPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, -10);
    }

    public final int getSerialNumber() {
        if (this.mUserId == UserHandle.myUserId()) {
            return Integer.MIN_VALUE;
        }
        if (this.mSerialNumber < 0) {
            if (this.mUserId == -10) {
                return Preference.DEFAULT_ORDER;
            }
            int userSerialNumber =
                    ((UserManager) getContext().getSystemService("user"))
                            .getUserSerialNumber(this.mUserId);
            this.mSerialNumber = userSerialNumber;
            if (userSerialNumber < 0) {
                return this.mUserId;
            }
        }
        return this.mSerialNumber;
    }

    @Override // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        boolean z = this.mHelper.mDisabledByAdmin;
        Drawable icon = getIcon();
        if (icon != null) {
            icon.mutate().setAlpha(z ? 102 : 255);
            setIcon(icon);
        }
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference
    public final boolean shouldHideSecondTarget() {
        return true;
    }

    public UserPreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        this.mSerialNumber = -1;
        this.mUserId = i;
        this.mHelper.mDisabledSummary = true;
    }
}
