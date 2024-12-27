package com.google.android.material.datepicker;

import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;

import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
final class MaterialCalendarGridView extends GridView {
    public final boolean nestedScrollable;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.google.android.material.datepicker.MaterialCalendarGridView$1, reason: invalid class name */
    public final class AnonymousClass1 extends AccessibilityDelegateCompat {
        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(
                View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(
                    view, accessibilityNodeInfoCompat.mInfo);
            accessibilityNodeInfoCompat.setCollectionInfo(null);
        }
    }

    public MaterialCalendarGridView(Context context) {
        this(context, null);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.widget.GridView, android.widget.AdapterView
    public final ListAdapter getAdapter() {
        return (MonthAdapter) super.getAdapter();
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        ((MonthAdapter) super.getAdapter()).notifyDataSetChanged();
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        MonthAdapter monthAdapter = (MonthAdapter) super.getAdapter();
        monthAdapter.getClass();
        int max = Math.max(monthAdapter.firstPositionInMonth(), getFirstVisiblePosition());
        int min = Math.min(monthAdapter.lastPositionInMonth(), getLastVisiblePosition());
        monthAdapter.getItem(max);
        monthAdapter.getItem(min);
        throw null;
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
    public final void onFocusChanged(boolean z, int i, Rect rect) {
        if (!z) {
            super.onFocusChanged(false, i, rect);
            return;
        }
        if (i == 33) {
            setSelection(((MonthAdapter) super.getAdapter()).lastPositionInMonth());
        } else if (i == 130) {
            setSelection(((MonthAdapter) super.getAdapter()).firstPositionInMonth());
        } else {
            super.onFocusChanged(true, i, rect);
        }
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View,
              // android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (!super.onKeyDown(i, keyEvent)) {
            return false;
        }
        if (getSelectedItemPosition() == -1
                || getSelectedItemPosition()
                        >= ((MonthAdapter) super.getAdapter()).firstPositionInMonth()) {
            return true;
        }
        if (19 != i) {
            return false;
        }
        setSelection(((MonthAdapter) super.getAdapter()).firstPositionInMonth());
        return true;
    }

    @Override // android.widget.GridView, android.widget.AbsListView, android.view.View
    public final void onMeasure(int i, int i2) {
        if (!this.nestedScrollable) {
            super.onMeasure(i, i2);
            return;
        }
        super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(16777215, Integer.MIN_VALUE));
        getLayoutParams().height = getMeasuredHeight();
    }

    @Override // android.widget.GridView, android.widget.AdapterView
    public final void setSelection(int i) {
        if (i < ((MonthAdapter) super.getAdapter()).firstPositionInMonth()) {
            super.setSelection(((MonthAdapter) super.getAdapter()).firstPositionInMonth());
        } else {
            super.setSelection(i);
        }
    }

    public MaterialCalendarGridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // android.widget.GridView, android.widget.AdapterView
    /* renamed from: getAdapter, reason: avoid collision after fix types in other method */
    public final ListAdapter getAdapter2() {
        return (MonthAdapter) super.getAdapter();
    }

    @Override // android.widget.AdapterView
    public final void setAdapter(ListAdapter listAdapter) {
        if (!(listAdapter instanceof MonthAdapter)) {
            throw new IllegalArgumentException(
                    String.format(
                            "%1$s must have its Adapter set to a %2$s",
                            MaterialCalendarGridView.class.getCanonicalName(),
                            MonthAdapter.class.getCanonicalName()));
        }
        super.setAdapter(listAdapter);
    }

    public MaterialCalendarGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        UtcDates.getUtcCalendarOf(null);
        if (MaterialDatePicker.readMaterialCalendarStyleBoolean(
                getContext(), R.attr.windowFullscreen)) {
            setNextFocusLeftId(com.android.settings.R.id.cancel_button);
            setNextFocusRightId(com.android.settings.R.id.confirm_button);
        }
        this.nestedScrollable =
                MaterialDatePicker.readMaterialCalendarStyleBoolean(
                        getContext(), com.android.settings.R.attr.nestedScrollable);
        ViewCompat.setAccessibilityDelegate(this, new AnonymousClass1());
    }

    @Override // android.widget.GridView, android.widget.AdapterView
    public final ListAdapter getAdapter() {
        return (MonthAdapter) super.getAdapter();
    }
}
