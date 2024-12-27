package com.google.android.setupdesign.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.google.android.setupdesign.template.ScrollViewScrollHandlingDelegate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class BottomScrollView extends ScrollView {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final AnonymousClass1 checkScrollRunnable;
    public BottomScrollListener listener;
    public boolean requiringScroll;
    public int scrollThreshold;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface BottomScrollListener {}

    /* JADX WARN: Type inference failed for: r1v2, types: [com.google.android.setupdesign.view.BottomScrollView$1] */
    public BottomScrollView(Context context) {
        super(context);
        this.requiringScroll = false;
        this.checkScrollRunnable =
                new Runnable() { // from class:
                                 // com.google.android.setupdesign.view.BottomScrollView.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BottomScrollView bottomScrollView = BottomScrollView.this;
                        int i = BottomScrollView.$r8$clinit;
                        bottomScrollView.checkScroll();
                    }
                };
    }

    public final void checkScroll() {
        if (this.listener != null) {
            if (getScrollY() >= this.scrollThreshold) {
                ((ScrollViewScrollHandlingDelegate) this.listener)
                        .requireScrollMixin.notifyScrollabilityChange(false);
            } else {
                if (this.requiringScroll) {
                    return;
                }
                this.requiringScroll = true;
                ((ScrollViewScrollHandlingDelegate) this.listener)
                        .requireScrollMixin.notifyScrollabilityChange(true);
            }
        }
    }

    public BottomScrollListener getBottomScrollListener() {
        return this.listener;
    }

    @Override // android.widget.ScrollView, android.widget.FrameLayout, android.view.ViewGroup,
              // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        View childAt = getChildAt(0);
        if (childAt != null) {
            this.scrollThreshold =
                    Math.max(0, ((childAt.getMeasuredHeight() - i4) + i2) - getPaddingBottom());
        }
        if (i4 - i2 > 0) {
            post(this.checkScrollRunnable);
        }
    }

    @Override // android.view.View
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        if (i4 != i2) {
            checkScroll();
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.google.android.setupdesign.view.BottomScrollView$1] */
    public BottomScrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.requiringScroll = false;
        this.checkScrollRunnable =
                new Runnable() { // from class:
                                 // com.google.android.setupdesign.view.BottomScrollView.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BottomScrollView bottomScrollView = BottomScrollView.this;
                        int i = BottomScrollView.$r8$clinit;
                        bottomScrollView.checkScroll();
                    }
                };
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [com.google.android.setupdesign.view.BottomScrollView$1] */
    public BottomScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.requiringScroll = false;
        this.checkScrollRunnable =
                new Runnable() { // from class:
                                 // com.google.android.setupdesign.view.BottomScrollView.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        BottomScrollView bottomScrollView = BottomScrollView.this;
                        int i2 = BottomScrollView.$r8$clinit;
                        bottomScrollView.checkScroll();
                    }
                };
    }
}
