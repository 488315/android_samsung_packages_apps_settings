package com.google.android.setupdesign.accessibility;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityNodeProviderCompat;
import androidx.customview.widget.ExploreByTouchHelper;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LinkAccessibilityHelper extends AccessibilityDelegateCompat {
    public final AccessibilityDelegateCompat delegate;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    class PreOLinkAccessibilityHelper extends ExploreByTouchHelper {
        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final int getVirtualViewAt(float f, float f2) {
            throw null;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void getVisibleVirtualViews(List list) {
            throw null;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final boolean onPerformActionForVirtualView(int i, int i2) {
            if (i2 != 16) {
                return false;
            }
            throw null;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateEventForVirtualView(
                int i, AccessibilityEvent accessibilityEvent) {
            throw null;
        }

        @Override // androidx.customview.widget.ExploreByTouchHelper
        public final void onPopulateNodeForVirtualView(
                int i, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            throw null;
        }
    }

    public LinkAccessibilityHelper(AccessibilityDelegateCompat accessibilityDelegateCompat) {
        this.delegate = accessibilityDelegateCompat;
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final boolean dispatchPopulateAccessibilityEvent(
            View view, AccessibilityEvent accessibilityEvent) {
        return this.delegate.dispatchPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        return this.delegate.getAccessibilityNodeProvider(view);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final void onInitializeAccessibilityEvent(
            View view, AccessibilityEvent accessibilityEvent) {
        this.delegate.onInitializeAccessibilityEvent(view, accessibilityEvent);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final void onInitializeAccessibilityNodeInfo(
            View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        this.delegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final void onPopulateAccessibilityEvent(
            View view, AccessibilityEvent accessibilityEvent) {
        this.delegate.onPopulateAccessibilityEvent(view, accessibilityEvent);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final boolean onRequestSendAccessibilityEvent(
            ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        return this.delegate.onRequestSendAccessibilityEvent(viewGroup, view, accessibilityEvent);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        return this.delegate.performAccessibilityAction(view, i, bundle);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final void sendAccessibilityEvent(View view, int i) {
        this.delegate.sendAccessibilityEvent(view, i);
    }

    @Override // androidx.core.view.AccessibilityDelegateCompat
    public final void sendAccessibilityEventUnchecked(
            View view, AccessibilityEvent accessibilityEvent) {
        this.delegate.sendAccessibilityEventUnchecked(view, accessibilityEvent);
    }
}
