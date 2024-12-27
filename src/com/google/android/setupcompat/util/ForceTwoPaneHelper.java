package com.google.android.setupcompat.util;

import android.content.Context;
import android.content.res.Resources;

import androidx.window.reflection.WindowExtensionsConstants;

import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ForceTwoPaneHelper {
    public static final Logger LOG = new Logger("ForceTwoPaneHelper");

    public static int getForceTwoPaneStyleLayout(Context context, int i) {
        int identifier;
        if (!PartnerConfigHelper.isForceTwoPaneEnabled(context)) {
            return i;
        }
        if (i == 0) {
            return i;
        }
        try {
            String resourceEntryName = context.getResources().getResourceEntryName(i);
            identifier =
                    context.getResources()
                            .getIdentifier(
                                    resourceEntryName + "_two_pane",
                                    WindowExtensionsConstants.LAYOUT_PACKAGE,
                                    context.getPackageName());
        } catch (Resources.NotFoundException unused) {
            LOG.w("Resource id 0x" + Integer.toHexString(i) + " is not found");
        }
        return identifier != 0 ? identifier : i;
    }
}
