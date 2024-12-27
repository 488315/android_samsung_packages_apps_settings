package com.samsung.android.settings.usefulfeature.multiwindow.swipeforpopupview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SwipeForPopUpViewCornerAreaIndicatorView extends LinearLayout {
    public final ViewGroup mLayout;
    public final SwipeForPopUpViewCornerAreaIndicatorDrawable mLeftDrawable;
    public final SwipeForPopUpViewCornerAreaIndicatorDrawable mRightDrawable;

    public SwipeForPopUpViewCornerAreaIndicatorView(Context context) {
        super(context);
        ViewGroup viewGroup =
                (ViewGroup)
                        LayoutInflater.from(context)
                                .inflate(
                                        R.layout.swipe_for_popup_view_corner_area_indicator,
                                        (ViewGroup) this,
                                        false);
        this.mLayout = viewGroup;
        if (viewGroup == null) {
            return;
        }
        addView(viewGroup);
        SwipeForPopUpViewCornerAreaIndicatorDrawable swipeForPopUpViewCornerAreaIndicatorDrawable =
                new SwipeForPopUpViewCornerAreaIndicatorDrawable(context, 10);
        this.mLeftDrawable = swipeForPopUpViewCornerAreaIndicatorDrawable;
        SwipeForPopUpViewCornerAreaIndicatorDrawable swipeForPopUpViewCornerAreaIndicatorDrawable2 =
                new SwipeForPopUpViewCornerAreaIndicatorDrawable(context, 20);
        this.mRightDrawable = swipeForPopUpViewCornerAreaIndicatorDrawable2;
        ImageView imageView = (ImageView) viewGroup.findViewById(R.id.indicator_left);
        ImageView imageView2 = (ImageView) viewGroup.findViewById(R.id.indicator_right);
        imageView.setImageDrawable(swipeForPopUpViewCornerAreaIndicatorDrawable);
        imageView2.setImageDrawable(swipeForPopUpViewCornerAreaIndicatorDrawable2);
    }

    @Override // android.view.View
    public final WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams layoutParams =
                new WindowManager.LayoutParams(2038, -2130640616, -3);
        layoutParams.setFitInsetsTypes(0);
        layoutParams.alpha = 0.2f;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.setTitle("CornerAreaIndicatorView");
        layoutParams.token = getContext().getActivityToken();
        return layoutParams;
    }
}
