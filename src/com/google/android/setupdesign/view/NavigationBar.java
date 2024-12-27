package com.google.android.setupdesign.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.LinearLayout;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class NavigationBar extends LinearLayout implements View.OnClickListener {
    public NavigationBar(Context context) {
        super(getThemedContext(context));
        init();
    }

    public static Context getThemedContext(Context context) {
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        new int[] {
                            R.attr.sudNavBarTheme,
                            android.R.attr.colorForeground,
                            android.R.attr.colorBackground
                        });
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        if (resourceId == 0) {
            float[] fArr = new float[3];
            float[] fArr2 = new float[3];
            Color.colorToHSV(obtainStyledAttributes.getColor(1, 0), fArr);
            Color.colorToHSV(obtainStyledAttributes.getColor(2, 0), fArr2);
            resourceId =
                    fArr[2] > fArr2[2] ? R.style.SudNavBarThemeDark : R.style.SudNavBarThemeLight;
        }
        obtainStyledAttributes.recycle();
        return new ContextThemeWrapper(context, resourceId);
    }

    public final void init() {
        if (isInEditMode()) {
            return;
        }
        View.inflate(getContext(), R.layout.sud_navbar_view, this);
    }

    public NavigationBar(Context context, AttributeSet attributeSet) {
        super(getThemedContext(context), attributeSet);
        init();
    }

    @TargetApi(11)
    public NavigationBar(Context context, AttributeSet attributeSet, int i) {
        super(getThemedContext(context), attributeSet, i);
        init();
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {}
}
