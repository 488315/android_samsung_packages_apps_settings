package com.samsung.android.settings.languagepack.widget;

import android.view.accessibility.AccessibilityNodeInfo;

import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.preference.SecPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class LanguagePackDetailsPreference extends SecPreference {
    @Override // androidx.preference.Preference
    public final void onInitializeAccessibilityNodeInfo(
            AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        accessibilityNodeInfoCompat.mInfo.removeAction(
                (AccessibilityNodeInfo.AccessibilityAction)
                        new AccessibilityNodeInfoCompat.AccessibilityActionCompat(
                                        16, (CharSequence) null)
                                .mAction);
        accessibilityNodeInfoCompat.setClickable(false);
    }
}
