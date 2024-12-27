package com.samsung.android.settings.accessibility.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.android.settings.R;
import com.android.settings.R$styleable;
import com.android.settingslib.widget.ButtonPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AccessibilityContainedButtonPreference extends ButtonPreference {
    public AccessibilityContainedButtonPreference(
            Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setLayoutResource(R.layout.accessibility_preference_contained_button);
        View.OnClickListener onClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.accessibility.base.widget.AccessibilityContainedButtonPreference$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        AccessibilityContainedButtonPreference.this.performClick();
                    }
                };
        ((ButtonPreference) this).mClickListener = onClickListener;
        Button button = this.mButton;
        if (button != null) {
            button.setOnClickListener(onClickListener);
        }
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes =
                    context.obtainStyledAttributes(
                            attributeSet, R$styleable.ButtonPreference, i, 0);
            setGravity(obtainStyledAttributes.getInt(0, 17));
            obtainStyledAttributes.recycle();
        }
    }

    public AccessibilityContainedButtonPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AccessibilityContainedButtonPreference(Context context) {
        this(context, null);
    }
}
