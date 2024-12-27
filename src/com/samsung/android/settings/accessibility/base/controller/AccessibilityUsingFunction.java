package com.samsung.android.settings.accessibility.base.controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;

import com.android.settings.core.BasePreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface AccessibilityUsingFunction {
    String getActionButtonDescription(Context context);

    Object getDefaultValue(Context context);

    String getFragmentClassName();

    String getFunctionName();

    Drawable getIcon(Context context);

    Intent getLearnMoreButtonIntent(Context context);

    String getUsingFunctionHighlightKey();

    CharSequence getUsingFunctionTitle(Context context);

    /* JADX WARN: Multi-variable type inference failed */
    default int getUsingFunctionType() {
        if (this instanceof BasePreferenceController) {
            return ((BasePreferenceController) this).getControlType();
        }
        return 1;
    }
}
