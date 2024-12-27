package com.android.settings.gestures;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BackGestureIndicatorView extends LinearLayout {
    public final ViewGroup mLayout;
    public final BackGestureIndicatorDrawable mLeftDrawable;
    public final BackGestureIndicatorDrawable mRightDrawable;

    public BackGestureIndicatorView(Context context) {
        super(context);
        ViewGroup viewGroup =
                (ViewGroup)
                        LayoutInflater.from(context)
                                .inflate(
                                        R.layout.back_gesture_indicator_container,
                                        (ViewGroup) this,
                                        false);
        this.mLayout = viewGroup;
        if (viewGroup == null) {
            return;
        }
        addView(viewGroup);
        BackGestureIndicatorDrawable backGestureIndicatorDrawable =
                new BackGestureIndicatorDrawable(context, false);
        this.mLeftDrawable = backGestureIndicatorDrawable;
        BackGestureIndicatorDrawable backGestureIndicatorDrawable2 =
                new BackGestureIndicatorDrawable(context, true);
        this.mRightDrawable = backGestureIndicatorDrawable2;
        ImageView imageView = (ImageView) viewGroup.findViewById(R.id.indicator_left);
        ImageView imageView2 = (ImageView) viewGroup.findViewById(R.id.indicator_right);
        imageView.setImageDrawable(backGestureIndicatorDrawable);
        imageView2.setImageDrawable(backGestureIndicatorDrawable2);
        int systemUiVisibility = getSystemUiVisibility();
        int i = systemUiVisibility | 3846;
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(
                        new int[] {
                            android.R.attr.windowLightNavigationBar,
                            android.R.attr.windowLightStatusBar
                        });
        i = obtainStyledAttributes.getBoolean(0, false) ? systemUiVisibility | 3862 : i;
        i = obtainStyledAttributes.getBoolean(1, false) ? i | 8192 : i;
        obtainStyledAttributes.recycle();
        setSystemUiVisibility(i);
    }
}
