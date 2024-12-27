package com.android.settings.inputmethod;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.settings.R;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class TrackpadGestureDialogFragment extends BottomSheetDialogFragment {
    public Button mButtonEndDone;
    public Button mButtonEndNext;
    public Button mButtonStartRestart;
    public Button mButtonStartSkip;
    public Context mContext;
    public ImageView[] mDotIndicators;
    public LayoutInflater mInflater;
    public ArrayList mPageList;
    public ViewPager mViewPager;
    public View[] mViewPagerItems;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class GesturePagerAdapter extends PagerAdapter {
        public final ArrayList mPageViewList;

        public GesturePagerAdapter(ArrayList arrayList) {
            this.mPageViewList = arrayList;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            if (this.mPageViewList.get(i) != null) {
                viewGroup.removeView((View) this.mPageViewList.get(i));
            }
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final int getCount() {
            return this.mPageViewList.size();
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final Object instantiateItem(ViewGroup viewGroup, int i) {
            viewGroup.addView((View) this.mPageViewList.get(i));
            return this.mPageViewList.get(i);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public final boolean isViewFromObject(View view, Object obj) {
            return obj == view;
        }
    }

    public static ArrayList getViewPagerResource$1() {
        return new ArrayList(
                Arrays.asList(
                        Integer.valueOf(R.layout.gesture_tip1_go_home),
                        Integer.valueOf(R.layout.gesture_tip2_go_back),
                        Integer.valueOf(R.layout.gesture_tip3_recent_apps),
                        Integer.valueOf(R.layout.gesture_tip4_notifications),
                        Integer.valueOf(R.layout.gesture_tip5_switch_apps)));
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override // com.google.android.material.bottomsheet.BottomSheetDialogFragment,
              // androidx.appcompat.app.AppCompatDialogFragment,
              // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Dialog onCreateDialog = super.onCreateDialog(bundle);
        LayoutInflater layoutInflater =
                (LayoutInflater) this.mContext.getSystemService(LayoutInflater.class);
        this.mInflater = layoutInflater;
        final View inflate =
                layoutInflater.inflate(R.layout.trackpad_gesture_preview, (ViewGroup) null);
        this.mViewPager = (ViewPager) inflate.findViewById(R.id.viewpager);
        int size = getViewPagerResource$1().size();
        this.mViewPagerItems = new View[size];
        for (int i = 0; i < size; i++) {
            this.mViewPagerItems[i] =
                    this.mInflater.inflate(
                            ((Integer) getViewPagerResource$1().get(i)).intValue(),
                            (ViewGroup) null);
        }
        this.mPageList = new ArrayList();
        int i2 = 0;
        while (true) {
            View[] viewArr = this.mViewPagerItems;
            if (i2 >= viewArr.length) {
                break;
            }
            this.mPageList.add(viewArr[i2]);
            i2++;
        }
        this.mViewPager.setAdapter(new GesturePagerAdapter(this.mPageList));
        Button button = (Button) inflate.findViewById(R.id.button_restart);
        this.mButtonStartRestart = button;
        final int i3 = 0;
        button.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.inputmethod.TrackpadGestureDialogFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ TrackpadGestureDialogFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i4 = i3;
                        TrackpadGestureDialogFragment trackpadGestureDialogFragment = this.f$0;
                        switch (i4) {
                            case 0:
                                trackpadGestureDialogFragment.mViewPager.setCurrentItem(
                                        trackpadGestureDialogFragment.mViewPager.getCurrentItem()
                                                - trackpadGestureDialogFragment
                                                        .mViewPagerItems
                                                        .length,
                                        true);
                                break;
                            case 1:
                                trackpadGestureDialogFragment.dismiss();
                                break;
                            case 2:
                                trackpadGestureDialogFragment.dismiss();
                                break;
                            default:
                                trackpadGestureDialogFragment.mViewPager.setCurrentItem(
                                        trackpadGestureDialogFragment.mViewPager.getCurrentItem()
                                                + 1,
                                        true);
                                break;
                        }
                    }
                });
        Button button2 = (Button) inflate.findViewById(R.id.button_done);
        this.mButtonEndDone = button2;
        final int i4 = 1;
        button2.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.inputmethod.TrackpadGestureDialogFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ TrackpadGestureDialogFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i42 = i4;
                        TrackpadGestureDialogFragment trackpadGestureDialogFragment = this.f$0;
                        switch (i42) {
                            case 0:
                                trackpadGestureDialogFragment.mViewPager.setCurrentItem(
                                        trackpadGestureDialogFragment.mViewPager.getCurrentItem()
                                                - trackpadGestureDialogFragment
                                                        .mViewPagerItems
                                                        .length,
                                        true);
                                break;
                            case 1:
                                trackpadGestureDialogFragment.dismiss();
                                break;
                            case 2:
                                trackpadGestureDialogFragment.dismiss();
                                break;
                            default:
                                trackpadGestureDialogFragment.mViewPager.setCurrentItem(
                                        trackpadGestureDialogFragment.mViewPager.getCurrentItem()
                                                + 1,
                                        true);
                                break;
                        }
                    }
                });
        Button button3 = (Button) inflate.findViewById(R.id.button_skip);
        this.mButtonStartSkip = button3;
        final int i5 = 2;
        button3.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.inputmethod.TrackpadGestureDialogFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ TrackpadGestureDialogFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i42 = i5;
                        TrackpadGestureDialogFragment trackpadGestureDialogFragment = this.f$0;
                        switch (i42) {
                            case 0:
                                trackpadGestureDialogFragment.mViewPager.setCurrentItem(
                                        trackpadGestureDialogFragment.mViewPager.getCurrentItem()
                                                - trackpadGestureDialogFragment
                                                        .mViewPagerItems
                                                        .length,
                                        true);
                                break;
                            case 1:
                                trackpadGestureDialogFragment.dismiss();
                                break;
                            case 2:
                                trackpadGestureDialogFragment.dismiss();
                                break;
                            default:
                                trackpadGestureDialogFragment.mViewPager.setCurrentItem(
                                        trackpadGestureDialogFragment.mViewPager.getCurrentItem()
                                                + 1,
                                        true);
                                break;
                        }
                    }
                });
        Button button4 = (Button) inflate.findViewById(R.id.button_next);
        this.mButtonEndNext = button4;
        final int i6 = 3;
        button4.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.android.settings.inputmethod.TrackpadGestureDialogFragment$$ExternalSyntheticLambda0
                    public final /* synthetic */ TrackpadGestureDialogFragment f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        int i42 = i6;
                        TrackpadGestureDialogFragment trackpadGestureDialogFragment = this.f$0;
                        switch (i42) {
                            case 0:
                                trackpadGestureDialogFragment.mViewPager.setCurrentItem(
                                        trackpadGestureDialogFragment.mViewPager.getCurrentItem()
                                                - trackpadGestureDialogFragment
                                                        .mViewPagerItems
                                                        .length,
                                        true);
                                break;
                            case 1:
                                trackpadGestureDialogFragment.dismiss();
                                break;
                            case 2:
                                trackpadGestureDialogFragment.dismiss();
                                break;
                            default:
                                trackpadGestureDialogFragment.mViewPager.setCurrentItem(
                                        trackpadGestureDialogFragment.mViewPager.getCurrentItem()
                                                + 1,
                                        true);
                                break;
                        }
                    }
                });
        this.mViewPager.addOnPageChangeListener(
                new ViewPager
                        .OnPageChangeListener() { // from class:
                                                  // com.android.settings.inputmethod.TrackpadGestureDialogFragment.3
                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrolled(int i7, int i8, float f) {
                        TrackpadGestureDialogFragment trackpadGestureDialogFragment =
                                TrackpadGestureDialogFragment.this;
                        if (f != 0.0f) {
                            for (int i9 = 0;
                                    i9 < trackpadGestureDialogFragment.mPageList.size();
                                    i9++) {
                                trackpadGestureDialogFragment.mViewPagerItems[i9].setVisibility(0);
                            }
                            return;
                        }
                        for (int i10 = 0;
                                i10 < trackpadGestureDialogFragment.mPageList.size();
                                i10++) {
                            if (i7 == i10) {
                                trackpadGestureDialogFragment.mDotIndicators[i10]
                                        .setBackgroundResource(
                                                R.drawable.ic_color_page_indicator_focused);
                                trackpadGestureDialogFragment.mViewPagerItems[i10].setVisibility(0);
                            } else {
                                trackpadGestureDialogFragment.mDotIndicators[i10]
                                        .setBackgroundResource(
                                                R.drawable.ic_color_page_indicator_unfocused);
                                trackpadGestureDialogFragment.mViewPagerItems[i10].setVisibility(4);
                            }
                        }
                        if (i7 < 0
                                || i7 >= trackpadGestureDialogFragment.mViewPagerItems.length - 1) {
                            trackpadGestureDialogFragment.mButtonStartSkip.setVisibility(8);
                            trackpadGestureDialogFragment.mButtonEndNext.setVisibility(8);
                            trackpadGestureDialogFragment.mButtonStartRestart.setVisibility(0);
                            trackpadGestureDialogFragment.mButtonEndDone.setVisibility(0);
                            return;
                        }
                        trackpadGestureDialogFragment.mButtonStartSkip.setVisibility(0);
                        trackpadGestureDialogFragment.mButtonEndNext.setVisibility(0);
                        trackpadGestureDialogFragment.mButtonStartRestart.setVisibility(8);
                        trackpadGestureDialogFragment.mButtonEndDone.setVisibility(8);
                    }

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageScrollStateChanged(int i7) {}

                    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
                    public final void onPageSelected(int i7) {}
                });
        ViewGroup viewGroup = (ViewGroup) inflate.findViewById(R.id.viewGroup);
        this.mDotIndicators = new ImageView[this.mPageList.size()];
        for (int i7 = 0; i7 < this.mPageList.size(); i7++) {
            ImageView imageView = new ImageView(this.mContext);
            ViewGroup.MarginLayoutParams marginLayoutParams =
                    new ViewGroup.MarginLayoutParams(12, 12);
            marginLayoutParams.setMargins(6, 0, 6, 0);
            imageView.setLayoutParams(marginLayoutParams);
            this.mDotIndicators[i7] = imageView;
            viewGroup.addView(imageView);
        }
        onCreateDialog.setContentView(inflate);
        onCreateDialog.getWindow().setType(2008);
        FrameLayout frameLayout =
                (FrameLayout) onCreateDialog.findViewById(R.id.design_bottom_sheet);
        frameLayout.setBackgroundResource(android.R.color.transparent);
        final BottomSheetBehavior from = BottomSheetBehavior.from(frameLayout);
        if (2
                != this.mContext
                        .getResources()
                        .getInteger(android.R.integer.config_pinnerWebviewPinBytes)) {
            from.setState$1(3);
        }
        inflate.getViewTreeObserver()
                .addOnGlobalLayoutListener(
                        new ViewTreeObserver
                                .OnGlobalLayoutListener() { // from class:
                                                            // com.android.settings.inputmethod.TrackpadGestureDialogFragment.1
                            @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                            public final void onGlobalLayout() {
                                inflate.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                                from.setPeekHeight(
                                        inflate.getMeasuredHeight()
                                                - ((Activity)
                                                                TrackpadGestureDialogFragment.this
                                                                        .mContext)
                                                        .getWindowManager()
                                                        .getCurrentWindowMetrics()
                                                        .getWindowInsets()
                                                        .getInsets(
                                                                WindowInsets.Type.navigationBars())
                                                        .bottom);
                            }
                        });
        BottomSheetBehavior.BottomSheetCallback bottomSheetCallback =
                new BottomSheetBehavior
                        .BottomSheetCallback() { // from class:
                                                 // com.android.settings.inputmethod.TrackpadGestureDialogFragment.2
                    @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
                    public final void onStateChanged(View view, int i8) {
                        if (i8 == 1) {
                            boolean z =
                                    2
                                            == TrackpadGestureDialogFragment.this
                                                    .mContext
                                                    .getResources()
                                                    .getInteger(
                                                            android.R.integer
                                                                    .config_pinnerWebviewPinBytes);
                            BottomSheetBehavior bottomSheetBehavior = from;
                            if (z) {
                                bottomSheetBehavior.setState$1(4);
                            } else {
                                bottomSheetBehavior.setState$1(3);
                            }
                        }
                    }

                    @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
                    public final void onSlide(View view) {}
                };
        from.getClass();
        Log.w(
                "BottomSheetBehavior",
                "BottomSheetBehavior now supports multiple callbacks. `setBottomSheetCallback()`"
                    + " removes all existing callbacks, including ones set internally by library"
                    + " authors, which may result in unintended behavior. This may change in the"
                    + " future. Please use `addBottomSheetCallback()` and"
                    + " `removeBottomSheetCallback()` instead to set your own callbacks.");
        from.callbacks.clear();
        from.callbacks.add(bottomSheetCallback);
        return onCreateDialog;
    }

    @Override // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        dismiss();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onStart() {
        Window window;
        super.onStart();
        Dialog dialog = this.mDialog;
        if (dialog == null || (window = dialog.getWindow()) == null) {
            return;
        }
        View decorView = window.getDecorView();
        decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | 768);
        window.setNavigationBarColor(0);
        ((Activity) this.mContext).getWindowManager().getDefaultDisplay().getSize(new Point());
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (r1.x * 0.75d);
        window.setAttributes(attributes);
    }
}
