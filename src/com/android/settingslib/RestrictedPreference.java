package com.android.settingslib;

import android.content.Context;
import android.os.UserHandle;
import android.util.AttributeSet;

import androidx.core.content.res.TypedArrayUtils;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settingslib.widget.TwoTargetPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RestrictedPreference extends TwoTargetPreference {
    public final RestrictedPreferenceHelper mHelper;

    public RestrictedPreference(
            Context context, AttributeSet attributeSet, int i, int i2, String str, int i3) {
        super(context, attributeSet, i, i2);
        this.mHelper = new RestrictedPreferenceHelper(context, this, attributeSet, str, i3);
    }

    public final void checkRestrictionAndSetDisabled(String str) {
        this.mHelper.checkRestrictionAndSetDisabled(UserHandle.myUserId(), str);
    }

    public String getPackageName() {
        RestrictedPreferenceHelper restrictedPreferenceHelper = this.mHelper;
        if (restrictedPreferenceHelper != null) {
            return restrictedPreferenceHelper.packageName;
        }
        return null;
    }

    @Override // androidx.preference.Preference
    public final void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        this.mHelper.onAttachedToHierarchy();
        super.onAttachedToHierarchy(preferenceManager);
    }

    @Override // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        this.mHelper.onBindViewHolder(preferenceViewHolder);
    }

    @Override // androidx.preference.Preference
    public void performClick() {
        if (this.mHelper.performClick()) {
            return;
        }
        super.performClick();
    }

    public void setDisabledByAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        if (this.mHelper.setDisabledByAdmin(enforcedAdmin)) {
            notifyChanged();
        }
    }

    @Override // androidx.preference.Preference
    public final void setEnabled(boolean z) {
        if (z) {
            RestrictedPreferenceHelper restrictedPreferenceHelper = this.mHelper;
            if (restrictedPreferenceHelper.mDisabledByAdmin) {
                restrictedPreferenceHelper.setDisabledByAdmin(null);
                return;
            }
        }
        if (z) {
            RestrictedPreferenceHelper restrictedPreferenceHelper2 = this.mHelper;
            if (restrictedPreferenceHelper2.mDisabledByEcm) {
                restrictedPreferenceHelper2.setDisabledByEcm(null);
                return;
            }
        }
        super.setEnabled(z);
    }

    public RestrictedPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        this(context, attributeSet, i, i2, null, -1);
    }

    public RestrictedPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RestrictedPreference(Context context, AttributeSet attributeSet) {
        this(
                context,
                attributeSet,
                TypedArrayUtils.getAttr(
                        context, R.attr.preferenceStyle, android.R.attr.preferenceStyle));
    }

    public RestrictedPreference(Context context) {
        this(context, null);
    }

    public RestrictedPreference(Context context, String str, int i) {
        this(
                context,
                null,
                TypedArrayUtils.getAttr(
                        context, R.attr.preferenceStyle, android.R.attr.preferenceStyle),
                0,
                str,
                i);
    }
}
