package com.android.settings;

import android.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.widget.Checkable;
import android.widget.LinearLayout;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes.dex */
public class CheckableLinearLayout extends LinearLayout implements Checkable {
    public boolean mChecked;
    public final float mDisabledAlpha;

    public CheckableLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.disabledAlpha, typedValue, true);
        this.mDisabledAlpha = typedValue.getFloat();
    }

    @Override // android.widget.Checkable
    public final boolean isChecked() {
        return this.mChecked;
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean z) {
        this.mChecked = z;
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            KeyEvent.Callback childAt = getChildAt(i);
            if (childAt instanceof Checkable) {
                ((Checkable) childAt).setChecked(this.mChecked);
            }
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            getChildAt(i).setAlpha(z ? 1.0f : this.mDisabledAlpha);
        }
    }

    @Override // android.widget.Checkable
    public final void toggle() {
        setChecked(!this.mChecked);
    }
}
