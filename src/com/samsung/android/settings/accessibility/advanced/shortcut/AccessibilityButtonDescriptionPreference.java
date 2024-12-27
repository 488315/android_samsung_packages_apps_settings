package com.samsung.android.settings.accessibility.advanced.shortcut;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.preference.PreferenceViewHolder;

import com.airbnb.lottie.LottieAnimationView;
import com.samsung.android.settings.accessibility.base.widget.DescriptionPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccessibilityButtonDescriptionPreference extends DescriptionPreference {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AccessibilityDelegate extends AccessibilityDelegateCompat {
        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(
                View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                    view, accessibilityNodeInfoCompat.mInfo);
            if (view instanceof LottieAnimationView) {
                accessibilityNodeInfoCompat.mInfo.setStateDescription(null);
            }
        }
    }

    public AccessibilityButtonDescriptionPreference(Context context) {
        this(context, null);
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.DescriptionPreference,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ViewCompat.setAccessibilityDelegate(
                (LottieAnimationView) preferenceViewHolder.itemView.findViewById(R.id.icon),
                new AccessibilityDelegate());
    }

    public AccessibilityButtonDescriptionPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
