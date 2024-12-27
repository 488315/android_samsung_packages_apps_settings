package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Button;
import android.widget.TextView;

import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CustomDisplayPreference extends SingleChoicePreference {
    public static final String ACCESSIBILITY_EVENT_CLASS_NAME = Button.class.getName();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CustomDisplayAccessibilityDelegate extends AccessibilityDelegateCompat {
        public final CharSequence description;
        public final boolean isSelected;

        public CustomDisplayAccessibilityDelegate(CharSequence charSequence, boolean z) {
            this.isSelected = z;
            this.description = charSequence;
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityEvent(
                View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName(CustomDisplayPreference.ACCESSIBILITY_EVENT_CLASS_NAME);
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(
                View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                    view, accessibilityNodeInfoCompat.mInfo);
            accessibilityNodeInfoCompat.setClassName(
                    CustomDisplayPreference.ACCESSIBILITY_EVENT_CLASS_NAME);
            accessibilityNodeInfoCompat.mInfo.setStateDescription(
                    view.getContext()
                            .getString(
                                    this.isSelected ? R.string.selected : R.string.not_selected));
            accessibilityNodeInfoCompat.setContentDescription(this.description);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CustomDisplayAdapter extends SingleChoicePreference.SingleChoiceAdapter {
        @Override // com.samsung.android.settings.accessibility.base.widget.SingleChoicePreference.SingleChoiceAdapter, androidx.recyclerview.widget.RecyclerView.Adapter
        public final void onBindViewHolder(SingleChoicePreference.ViewHolder viewHolder, int i) {
            super.onBindViewHolder(viewHolder, i);
            SingleChoicePreference.SingleChoiceCandidateInfo singleChoiceCandidateInfo =
                    (SingleChoicePreference.SingleChoiceCandidateInfo) this.list.get(i);
            if (singleChoiceCandidateInfo == null) {
                return;
            }
            String defaultKey = this.defaultKey.getDefaultKey();
            boolean equals =
                    defaultKey == null
                            ? singleChoiceCandidateInfo.defaultItem
                            : defaultKey.equals(singleChoiceCandidateInfo.key);
            TextView textView = viewHolder.titleView;
            ViewCompat.setAccessibilityDelegate(
                    viewHolder.itemView,
                    new CustomDisplayAccessibilityDelegate(
                            textView != null ? textView.getText() : null, equals));
        }
    }

    public CustomDisplayPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.SingleChoicePreference
    public final SingleChoicePreference.SingleChoiceAdapter createAdapter(int i) {
        return new CustomDisplayAdapter(i);
    }
}
