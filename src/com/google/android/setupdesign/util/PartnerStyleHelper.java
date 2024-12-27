package com.google.android.setupdesign.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;

import com.android.settings.R;

import com.google.android.setupcompat.PartnerCustomizationLayout;
import com.google.android.setupcompat.internal.TemplateLayout;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import com.google.android.setupcompat.util.Logger;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.GlifLayout;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class PartnerStyleHelper {
    public static TemplateLayout findLayoutFromActivity(Activity activity) {
        View findViewById;
        if (activity == null
                || (findViewById = activity.findViewById(R.id.suc_layout_status)) == null) {
            return null;
        }
        return (TemplateLayout) findViewById.getParent();
    }

    public static int getLayoutGravity(Context context) {
        String string =
                PartnerConfigHelper.get(context)
                        .getString(context, PartnerConfig.CONFIG_LAYOUT_GRAVITY);
        if (string == null) {
            return 0;
        }
        String lowerCase = string.toLowerCase(Locale.ROOT);
        lowerCase.getClass();
        if (lowerCase.equals("center")) {
            return 17;
        }
        return !lowerCase.equals(NetworkAnalyticsConstants.DataPoints.OPEN_TIME) ? 0 : 8388611;
    }

    public static boolean shouldApplyPartnerHeavyThemeResource(View view) {
        boolean z = false;
        if (view == null) {
            return false;
        }
        if (view instanceof GlifLayout) {
            return ((GlifLayout) view).shouldApplyPartnerHeavyThemeResource();
        }
        Context context = view.getContext();
        try {
            Logger logger = PartnerCustomizationLayout.LOG;
            TemplateLayout findLayoutFromActivity =
                    findLayoutFromActivity(PartnerConfigHelper.lookupActivityFromContext(context));
            if (findLayoutFromActivity instanceof GlifLayout) {
                return ((GlifLayout) findLayoutFromActivity).shouldApplyPartnerHeavyThemeResource();
            }
        } catch (ClassCastException | IllegalArgumentException unused) {
        }
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(new int[] {R.attr.sudUsePartnerHeavyTheme});
        boolean z2 = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
        boolean z3 = z2 || PartnerConfigHelper.shouldApplyExtendedPartnerConfig(context);
        if (shouldApplyPartnerResource(context) && z3) {
            z = true;
        }
        return z;
    }

    public static boolean shouldApplyPartnerResource(View view) {
        if (view == null) {
            return false;
        }
        return view instanceof PartnerCustomizationLayout
                ? ((PartnerCustomizationLayout) view).shouldApplyPartnerResource()
                : shouldApplyPartnerResource(view.getContext());
    }

    public static boolean shouldApplyPartnerResource(Context context) {
        if (!PartnerConfigHelper.get(context).isAvailable()) {
            return false;
        }
        Activity activity = null;
        try {
            Logger logger = PartnerCustomizationLayout.LOG;
            activity = PartnerConfigHelper.lookupActivityFromContext(context);
            if (activity != null) {
                TemplateLayout findLayoutFromActivity = findLayoutFromActivity(activity);
                if (findLayoutFromActivity instanceof PartnerCustomizationLayout) {
                    return ((PartnerCustomizationLayout) findLayoutFromActivity)
                            .shouldApplyPartnerResource();
                }
            }
        } catch (ClassCastException | IllegalArgumentException unused) {
        }
        boolean isAnySetupWizard =
                activity != null
                        ? WizardManagerHelper.isAnySetupWizard(activity.getIntent())
                        : false;
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(new int[] {R.attr.sucUsePartnerResource});
        boolean z = obtainStyledAttributes.getBoolean(0, true);
        obtainStyledAttributes.recycle();
        return isAnySetupWizard || z;
    }
}
