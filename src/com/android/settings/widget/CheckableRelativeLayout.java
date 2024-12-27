package com.android.settings.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import android.widget.RelativeLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CheckableRelativeLayout extends RelativeLayout implements Checkable {
    public Checkable mCheckable;
    public View mCheckableChild;
    public boolean mChecked;

    public CheckableRelativeLayout(Context context) {
        super(context);
    }

    public static View findFirstCheckableView(ViewGroup viewGroup) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if (childAt instanceof Checkable) {
                return childAt;
            }
            if (childAt instanceof ViewGroup) {
                findFirstCheckableView((ViewGroup) childAt);
            }
        }
        return null;
    }

    @Override // android.widget.Checkable
    public final boolean isChecked() {
        return this.mChecked;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        View findFirstCheckableView = findFirstCheckableView(this);
        this.mCheckableChild = findFirstCheckableView;
        if (findFirstCheckableView != null) {
            findFirstCheckableView.setClickable(false);
            this.mCheckableChild.setFocusable(false);
            this.mCheckableChild.setImportantForAccessibility(2);
            Checkable checkable = (Checkable) this.mCheckableChild;
            this.mCheckable = checkable;
            checkable.setChecked(this.mChecked);
            View view = this.mCheckableChild;
            if (view != null) {
                setStateDescription(view.getStateDescription());
            }
        }
        super.onFinishInflate();
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setChecked(this.mChecked);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(
            AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setChecked(this.mChecked);
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean z) {
        if (this.mChecked != z) {
            this.mChecked = z;
            Checkable checkable = this.mCheckable;
            if (checkable != null) {
                checkable.setChecked(z);
            }
        }
        View view = this.mCheckableChild;
        if (view != null) {
            setStateDescription(view.getStateDescription());
        }
        notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    @Override // android.widget.Checkable
    public final void toggle() {
        setChecked(!this.mChecked);
    }

    public CheckableRelativeLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
