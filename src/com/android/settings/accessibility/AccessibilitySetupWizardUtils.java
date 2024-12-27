package com.android.settings.accessibility;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.LinearLayout;

import androidx.preference.Preference;

import com.android.settings.R;

import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupdesign.GlifPreferenceLayout;
import com.google.android.setupdesign.template.RecyclerMixin;
import com.google.android.setupdesign.util.ThemeHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AccessibilitySetupWizardUtils {
    public static void setPrimaryButton(
            Context context, FooterBarMixin footerBarMixin, Runnable runnable) {
        footerBarMixin.setPrimaryButton(
                new FooterButton(
                        context.getString(R.string.done),
                        new AccessibilitySetupWizardUtils$$ExternalSyntheticLambda0(runnable),
                        4,
                        2132083805));
    }

    public static void updateGlifPreferenceLayout(
            Context context,
            GlifPreferenceLayout glifPreferenceLayout,
            CharSequence charSequence,
            CharSequence charSequence2,
            Drawable drawable) {
        LinearLayout linearLayout;
        if (!TextUtils.isEmpty(charSequence)) {
            glifPreferenceLayout.setHeaderText(charSequence);
        }
        if (!TextUtils.isEmpty(charSequence2)) {
            glifPreferenceLayout.setDescriptionText(charSequence2);
        }
        if (drawable != null) {
            glifPreferenceLayout.setIcon(drawable);
        }
        RecyclerMixin recyclerMixin = glifPreferenceLayout.recyclerMixin;
        recyclerMixin.dividerInsetStart = Preference.DEFAULT_ORDER;
        recyclerMixin.dividerInsetEnd = 0;
        recyclerMixin.updateDivider();
        Logger logger = ThemeHelper.LOG;
        if (!PartnerConfigHelper.shouldApplyMaterialYouStyle(context)
                || (linearLayout =
                                (LinearLayout)
                                        glifPreferenceLayout.findManagedViewById(
                                                R.id.sud_layout_header))
                        == null) {
            return;
        }
        linearLayout.setPadding(
                0,
                glifPreferenceLayout.getPaddingTop(),
                0,
                glifPreferenceLayout.getPaddingBottom());
    }
}
