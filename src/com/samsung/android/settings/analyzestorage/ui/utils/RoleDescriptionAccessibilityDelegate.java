package com.samsung.android.settings.analyzestorage.ui.utils;

import android.view.View;

import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RoleDescriptionAccessibilityDelegate extends AccessibilityDelegateCompat {
    public final String roleDescription;

    public RoleDescriptionAccessibilityDelegate(String str) {
        this.roleDescription = str;
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final void onInitializeAccessibilityNodeInfo(
            View host, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        Intrinsics.checkNotNullParameter(host, "host");
        this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                host, accessibilityNodeInfoCompat.mInfo);
        String str = this.roleDescription;
        if (str == null || str.length() == 0) {
            return;
        }
        accessibilityNodeInfoCompat.setClassName(str);
    }
}
