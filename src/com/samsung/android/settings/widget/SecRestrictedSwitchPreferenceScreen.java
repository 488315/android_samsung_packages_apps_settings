package com.samsung.android.settings.widget;

import android.app.admin.DevicePolicyManager;
import android.app.admin.DevicePolicyResourcesManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecSwitchPreferenceScreen;

import com.android.settings.R;
import com.android.settingslib.R$styleable;
import com.android.settingslib.RestrictedPreferenceHelper;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SecRestrictedSwitchPreferenceScreen extends SecSwitchPreferenceScreen {
    public final RestrictedPreferenceHelper mHelper;
    public final CharSequence mRestrictedSwitchSummary;
    public final boolean mUseAdditionalSummary;

    public SecRestrictedSwitchPreferenceScreen(
            Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mUseAdditionalSummary = false;
        RestrictedPreferenceHelper restrictedPreferenceHelper =
                new RestrictedPreferenceHelper(context, this, attributeSet);
        this.mHelper = restrictedPreferenceHelper;
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes =
                    context.obtainStyledAttributes(
                            attributeSet, R$styleable.RestrictedSwitchPreference);
            TypedValue peekValue = obtainStyledAttributes.peekValue(1);
            if (peekValue != null) {
                this.mUseAdditionalSummary = peekValue.type == 18 && peekValue.data != 0;
            }
            TypedValue peekValue2 = obtainStyledAttributes.peekValue(0);
            obtainStyledAttributes.recycle();
            if (peekValue2 != null && peekValue2.type == 3) {
                int i3 = peekValue2.resourceId;
                if (i3 != 0) {
                    this.mRestrictedSwitchSummary = context.getText(i3);
                } else {
                    this.mRestrictedSwitchSummary = peekValue2.string;
                }
            }
        }
        if (this.mUseAdditionalSummary) {
            setLayoutResource(R.layout.sec_restricted_switch_preference_screen);
            restrictedPreferenceHelper.mDisabledSummary = false;
        }
    }

    @Override // androidx.preference.Preference
    public final void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        this.mHelper.onAttachedToHierarchy();
        super.onAttachedToHierarchy(preferenceManager);
    }

    @Override // androidx.preference.SecSwitchPreferenceScreen,
              // androidx.preference.SeslSwitchPreferenceScreen,
              // androidx.preference.SwitchPreferenceCompat, androidx.preference.Preference
    public void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        View findViewById = preferenceViewHolder.findViewById(android.R.id.switch_widget);
        if (findViewById != null) {
            findViewById.getRootView().setFilterTouchesWhenObscured(true);
        }
        this.mHelper.onBindViewHolder(preferenceViewHolder);
        CharSequence charSequence = this.mRestrictedSwitchSummary;
        if (charSequence == null) {
            if (this.mChecked) {
                final Context context = getContext();
                DevicePolicyResourcesManager resources =
                        ((DevicePolicyManager) context.getSystemService(DevicePolicyManager.class))
                                .getResources();
                final int i = R.string.enabled_by_admin;
                charSequence =
                        resources.getString(
                                "Settings.ENABLED_BY_ADMIN_SWITCH_SUMMARY",
                                new Supplier() { // from class:
                                                 // com.samsung.android.settings.widget.SecRestrictedSwitchPreferenceScreen$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        return context.getString(i);
                                    }
                                });
            } else {
                final Context context2 = getContext();
                DevicePolicyResourcesManager resources2 =
                        ((DevicePolicyManager) context2.getSystemService(DevicePolicyManager.class))
                                .getResources();
                final int i2 = R.string.disabled_by_admin;
                charSequence =
                        resources2.getString(
                                "Settings.DISABLED_BY_ADMIN_SWITCH_SUMMARY",
                                new Supplier() { // from class:
                                                 // com.samsung.android.settings.widget.SecRestrictedSwitchPreferenceScreen$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Supplier
                                    public final Object get() {
                                        return context2.getString(i2);
                                    }
                                });
            }
        }
        View findViewById2 = preferenceViewHolder.findViewById(R.id.icon_frame);
        if (this.mUseAdditionalSummary) {
            TextView textView =
                    (TextView) preferenceViewHolder.findViewById(R.id.additional_summary);
            if (textView != null) {
                if (this.mHelper.mDisabledByAdmin) {
                    textView.setText(charSequence);
                    textView.setVisibility(0);
                } else {
                    textView.setVisibility(8);
                }
            }
        } else {
            TextView textView2 = (TextView) preferenceViewHolder.findViewById(android.R.id.summary);
            if (textView2 != null && this.mHelper.mDisabledByAdmin) {
                textView2.setText(charSequence);
                textView2.setVisibility(0);
            }
        }
        if (findViewById2 != null) {
            findViewById2.setVisibility(isIconSpaceReserved() ? 0 : 8);
        }
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
                    + " com.samsung.android.settings.widget.SecRestrictedSwitchPreferenceScreen.setEnabled(boolean):void");
    }

    public SecRestrictedSwitchPreferenceScreen(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SecRestrictedSwitchPreferenceScreen(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.switchPreferenceCompatStyle);
    }

    public SecRestrictedSwitchPreferenceScreen(Context context) {
        this(context, null);
    }
}
