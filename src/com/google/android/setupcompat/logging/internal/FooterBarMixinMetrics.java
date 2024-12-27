package com.google.android.setupcompat.logging.internal;

import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FooterBarMixinMetrics {
    public static final String EXTRA_PRIMARY_BUTTON_VISIBILITY = "PrimaryButtonVisibility";
    public static final String EXTRA_SECONDARY_BUTTON_VISIBILITY = "SecondaryButtonVisibility";
    public String primaryButtonVisibility;
    public String secondaryButtonVisibility;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Retention(RetentionPolicy.SOURCE)
    public @interface FooterButtonVisibility {}

    public static String updateButtonVisibilityState(String str, boolean z) {
        if ("VisibleUsingXml".equals(str) || "Visible".equals(str) || "Invisible".equals(str)) {
            return (z && "Invisible".equals(str))
                    ? "Invisible_to_Visible"
                    : !z
                            ? "VisibleUsingXml".equals(str)
                                    ? "VisibleUsingXml_to_Invisible"
                                    : "Visible".equals(str) ? "Visible_to_Invisible" : str
                            : str;
        }
        throw new IllegalStateException(
                AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m(
                        "Illegal visibility state: ", str));
    }
}
