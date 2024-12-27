package com.android.settings.biometrics2.ui.view;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.settings.R;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class FingerprintEnrollEnrollingUdfpsFragmentKt {
    public static final void configLayout(
            Context context,
            int i,
            TextView textView,
            TextView textView2,
            ImageView imageView,
            Button button) {
        if (i == 1) {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.addRule(10);
            layoutParams.addRule(20);
            layoutParams.topMargin = convertDpToPixel(context, 76.64f);
            layoutParams.leftMargin = convertDpToPixel(context, 71.99f);
            imageView.setLayoutParams(layoutParams);
            RelativeLayout.LayoutParams layoutParams2 =
                    new RelativeLayout.LayoutParams(displayMetrics.widthPixels / 2, -2);
            layoutParams2.addRule(10);
            layoutParams2.addRule(20, R.id.udfps_animation_view);
            layoutParams2.topMargin = convertDpToPixel(context, 138.0f);
            layoutParams2.leftMargin = convertDpToPixel(context, 66.0f);
            textView.setLayoutParams(layoutParams2);
            RelativeLayout.LayoutParams layoutParams3 =
                    new RelativeLayout.LayoutParams(displayMetrics.widthPixels / 2, -2);
            layoutParams3.addRule(10);
            layoutParams3.addRule(20);
            layoutParams3.topMargin = convertDpToPixel(context, 198.0f);
            layoutParams3.leftMargin = convertDpToPixel(context, 66.0f);
            textView2.setLayoutParams(layoutParams3);
        } else if (i == 3) {
            RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams4.addRule(10);
            layoutParams4.addRule(17, R.id.udfps_animation_view);
            layoutParams4.topMargin = convertDpToPixel(context, 76.64f);
            layoutParams4.leftMargin = convertDpToPixel(context, 151.54f);
            imageView.setLayoutParams(layoutParams4);
            RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams5.addRule(10);
            layoutParams5.addRule(17, R.id.udfps_animation_view);
            layoutParams5.topMargin = convertDpToPixel(context, 138.0f);
            layoutParams5.leftMargin = convertDpToPixel(context, 144.0f);
            textView.setLayoutParams(layoutParams5);
            RelativeLayout.LayoutParams layoutParams6 = new RelativeLayout.LayoutParams(-1, -2);
            layoutParams6.addRule(10);
            layoutParams6.addRule(17, R.id.udfps_animation_view);
            layoutParams6.topMargin = convertDpToPixel(context, 198.0f);
            layoutParams6.leftMargin = convertDpToPixel(context, 144.0f);
            textView2.setLayoutParams(layoutParams6);
        }
        if (i == 1 || i == 3) {
            ViewGroup.LayoutParams layoutParams7 = button.getLayoutParams();
            Intrinsics.checkNotNull(
                    layoutParams7,
                    "null cannot be cast to non-null type"
                        + " android.widget.RelativeLayout.LayoutParams");
            RelativeLayout.LayoutParams layoutParams8 = (RelativeLayout.LayoutParams) layoutParams7;
            layoutParams8.topMargin = convertDpToPixel(context, 26.0f);
            layoutParams8.leftMargin = convertDpToPixel(context, 54.0f);
            button.requestLayout();
        }
    }

    public static final int convertDpToPixel(Context context, float f) {
        return (int) (f * context.getResources().getDisplayMetrics().density);
    }
}
