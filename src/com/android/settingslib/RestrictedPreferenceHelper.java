package com.android.settingslib;

import android.R;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class RestrictedPreferenceHelper {
    public final String mAttrUserRestriction;
    public final Context mContext;
    public boolean mDisabledByAdmin;
    public boolean mDisabledByEcm;
    public Intent mDisabledByEcmIntent;
    public boolean mDisabledSummary;
    RestrictedLockUtils.EnforcedAdmin mEnforcedAdmin;
    public final Preference mPreference;
    public String packageName;
    public int uid;

    public RestrictedPreferenceHelper(
            Context context, Preference preference, AttributeSet attributeSet, String str, int i) {
        CharSequence charSequence;
        this.mAttrUserRestriction = null;
        boolean z = false;
        this.mDisabledSummary = false;
        this.mDisabledByEcmIntent = null;
        this.mContext = context;
        this.mPreference = preference;
        this.packageName = str;
        this.uid = i;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes =
                    context.obtainStyledAttributes(attributeSet, R$styleable.RestrictedPreference);
            TypedValue peekValue = obtainStyledAttributes.peekValue(1);
            if (peekValue == null || peekValue.type != 3) {
                charSequence = null;
            } else {
                int i2 = peekValue.resourceId;
                charSequence = i2 != 0 ? context.getText(i2) : peekValue.string;
            }
            String charSequence2 = charSequence == null ? null : charSequence.toString();
            this.mAttrUserRestriction = charSequence2;
            if (RestrictedLockUtilsInternal.hasBaseUserRestriction(
                    context, UserHandle.myUserId(), charSequence2)) {
                this.mAttrUserRestriction = null;
                return;
            }
            TypedValue peekValue2 = obtainStyledAttributes.peekValue(0);
            if (peekValue2 != null) {
                if (peekValue2.type == 18 && peekValue2.data != 0) {
                    z = true;
                }
                this.mDisabledSummary = z;
            }
        }
    }

    public final void checkEcmRestrictionAndSetDisabled(String str, String str2) {
        this.packageName = str2;
        this.uid = -1;
        setDisabledByEcm(
                RestrictedLockUtilsInternal.checkIfRequiresEnhancedConfirmation(
                        this.mContext, str, str2));
    }

    public final void checkRestrictionAndSetDisabled(int i, String str) {
        setDisabledByAdmin(
                RestrictedLockUtilsInternal.checkIfRestrictionEnforced(this.mContext, i, str));
    }

    public final void onAttachedToHierarchy() {
        String str = this.mAttrUserRestriction;
        if (str != null) {
            checkRestrictionAndSetDisabled(UserHandle.myUserId(), str);
        }
    }

    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        TextView textView;
        if (this.mDisabledByAdmin || this.mDisabledByEcm) {
            preferenceViewHolder.itemView.setEnabled(true);
        }
        if (!this.mDisabledSummary
                || (textView = (TextView) preferenceViewHolder.findViewById(R.id.summary))
                        == null) {
            return;
        }
        String string =
                ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class))
                        .getResources()
                        .getString(
                                "Settings.CONTROLLED_BY_ADMIN_SUMMARY",
                                new Supplier() { // from class:
                                                 // com.android.settingslib.RestrictedPreferenceHelper$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        return RestrictedPreferenceHelper.this.mContext.getString(
                                                com.android.settings.R.string
                                                        .disabled_by_admin_summary_text);
                                    }
                                });
        if (this.mDisabledByAdmin) {
            textView.setText(string);
        } else if (this.mDisabledByEcm) {
            textView.setText(com.android.settings.R.string.disabled_by_app_ops_text);
        } else if (TextUtils.equals(string, textView.getText())) {
            textView.setText((CharSequence) null);
        }
    }

    public final boolean performClick() {
        if (this.mDisabledByAdmin) {
            RestrictedLockUtils.sendShowAdminSupportDetailsIntent(
                    this.mContext, this.mEnforcedAdmin);
            return true;
        }
        if (!this.mDisabledByEcm) {
            return false;
        }
        if (Flags.enhancedConfirmationModeApisEnabled()
                && android.security.Flags.extendEcmToAllSettings()) {
            this.mContext.startActivity(this.mDisabledByEcmIntent);
            return true;
        }
        Context context = this.mContext;
        String str = this.packageName;
        int i = this.uid;
        Intent intent = new Intent("android.settings.SHOW_RESTRICTED_SETTING_DIALOG");
        intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
        intent.putExtra("android.intent.extra.UID", i);
        context.startActivity(intent);
        return true;
    }

    public final boolean setDisabledByAdmin(RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        boolean z;
        this.mEnforcedAdmin = null;
        if (enforcedAdmin != null) {
            RestrictedLockUtils.EnforcedAdmin enforcedAdmin2 =
                    new RestrictedLockUtils.EnforcedAdmin();
            enforcedAdmin2.component = null;
            enforcedAdmin2.enforcedRestriction = null;
            enforcedAdmin2.user = null;
            enforcedAdmin2.component = enforcedAdmin.component;
            enforcedAdmin2.enforcedRestriction = enforcedAdmin.enforcedRestriction;
            enforcedAdmin2.user = enforcedAdmin.user;
            this.mEnforcedAdmin = enforcedAdmin2;
            z = true;
        } else {
            z = false;
        }
        if (this.mDisabledByAdmin == z) {
            return false;
        }
        this.mDisabledByAdmin = z;
        updateDisabledState();
        return true;
    }

    public final void setDisabledByEcm(Intent intent) {
        boolean z = intent != null;
        if (this.mDisabledByEcm != z) {
            this.mDisabledByEcmIntent = intent;
            this.mDisabledByEcm = z;
            updateDisabledState();
        }
    }

    public final void updateDisabledState() {
        boolean z = (this.mDisabledByAdmin || this.mDisabledByEcm) ? false : true;
        Preference preference = this.mPreference;
        if (!(preference instanceof RestrictedTopLevelPreference)) {
            preference.setEnabled(z);
        }
        if (preference instanceof PrimarySwitchPreference) {
            ((PrimarySwitchPreference) preference).setSwitchEnabled(z);
        }
        if (z || !this.mDisabledByEcm) {
            return;
        }
        preference.setSummary(com.android.settings.R.string.disabled_by_app_ops_text);
    }

    public RestrictedPreferenceHelper(
            Context context, Preference preference, AttributeSet attributeSet) {
        this(context, preference, attributeSet, null, -1);
    }
}
