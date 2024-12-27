package com.google.android.material.snackbar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.SeslTouchTargetDelegate;
import androidx.core.view.ViewCompat;

import com.android.settings.R;

import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.motion.MotionUtils;

import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SnackbarContentLayout extends LinearLayout {
    public Button actionView;
    public final InputMethodManager mImm;
    public boolean mIsSuggestMultiLine;
    public final SnackbarContentLayout mSnackBarContentLayout;
    public int mWidthWtihAction;
    public final WindowManager mWindowManager;
    public final int maxInlineActionWidth;
    public int maxWidth;
    public TextView messageView;

    public SnackbarContentLayout(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Resources resources = getContext().getResources();
        int fraction =
                (int)
                        resources.getFraction(
                                R.dimen.sesl_config_prefSnackWidth,
                                resources.getDisplayMetrics().widthPixels,
                                resources.getDisplayMetrics().widthPixels);
        this.mWidthWtihAction = fraction;
        this.maxWidth = fraction;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.messageView = (TextView) findViewById(R.id.snackbar_text);
        this.actionView = (Button) findViewById(R.id.snackbar_action);
    }

    /* JADX WARN: Code restructure failed: missing block: B:79:0x0206, code lost:

       if (updateViewsWithinLayout(1, r0, r0 - r1) != false) goto L84;
    */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0212, code lost:

       if (updateViewsWithinLayout(0, r0, r0) != false) goto L84;
    */
    @Override // android.widget.LinearLayout, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onMeasure(int r10, int r11) {
        /*
            Method dump skipped, instructions count: 539
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.material.snackbar.SnackbarContentLayout.onMeasure(int,"
                    + " int):void");
    }

    public final boolean updateViewsWithinLayout(int i, int i2, int i3) {
        boolean z;
        if (i != getOrientation()) {
            setOrientation(i);
            z = true;
        } else {
            z = false;
        }
        if (this.messageView.getPaddingTop() == i2 && this.messageView.getPaddingBottom() == i3) {
            return z;
        }
        TextView textView = this.messageView;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        if (textView.isPaddingRelative()) {
            textView.setPaddingRelative(
                    textView.getPaddingStart(), i2, textView.getPaddingEnd(), i3);
            return true;
        }
        textView.setPadding(textView.getPaddingLeft(), i2, textView.getPaddingRight(), i3);
        return true;
    }

    public SnackbarContentLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mIsSuggestMultiLine = false;
        MotionUtils.resolveThemeInterpolator(
                context,
                R.attr.motionEasingEmphasizedInterpolator,
                AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
        TypedArray obtainStyledAttributes =
                context.obtainStyledAttributes(attributeSet, R$styleable.SnackbarLayout);
        this.maxWidth = obtainStyledAttributes.getDimensionPixelSize(0, -1);
        this.maxInlineActionWidth = obtainStyledAttributes.getDimensionPixelSize(7, -1);
        obtainStyledAttributes.recycle();
        Resources resources = context.getResources();
        int fraction =
                (int)
                        resources.getFraction(
                                R.dimen.sesl_config_prefSnackWidth,
                                resources.getDisplayMetrics().widthPixels,
                                resources.getDisplayMetrics().widthPixels);
        this.mWidthWtihAction = fraction;
        this.maxWidth = fraction;
        this.mSnackBarContentLayout =
                (SnackbarContentLayout) findViewById(R.id.snackbar_content_layout);
        this.mImm = (InputMethodManager) context.getSystemService(InputMethodManager.class);
        this.mWindowManager = (WindowManager) context.getSystemService("window");
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver != null) {
            viewTreeObserver.addOnGlobalLayoutListener(
                    new ViewTreeObserver
                            .OnGlobalLayoutListener() { // from class:
                                                        // com.google.android.material.snackbar.SnackbarContentLayout.1
                        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                        public final void onGlobalLayout() {
                            Button button;
                            SnackbarContentLayout.this
                                    .getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);
                            SnackbarContentLayout snackbarContentLayout =
                                    SnackbarContentLayout.this;
                            if (snackbarContentLayout.mSnackBarContentLayout == null
                                    || (button = snackbarContentLayout.actionView) == null
                                    || button.getVisibility() != 0) {
                                return;
                            }
                            SnackbarContentLayout.this.mSnackBarContentLayout.post(
                                    new Runnable() { // from class:
                                                     // com.google.android.material.snackbar.SnackbarContentLayout.1.1
                                        @Override // java.lang.Runnable
                                        public final void run() {
                                            SeslTouchTargetDelegate seslTouchTargetDelegate =
                                                    new SeslTouchTargetDelegate(
                                                            SnackbarContentLayout.this
                                                                    .mSnackBarContentLayout);
                                            int measuredHeight =
                                                    SnackbarContentLayout.this.actionView
                                                                    .getMeasuredHeight()
                                                            / 2;
                                            seslTouchTargetDelegate.addTouchDelegate(
                                                    SnackbarContentLayout.this.actionView,
                                                    SeslTouchTargetDelegate.ExtraInsets.of(
                                                            measuredHeight,
                                                            measuredHeight,
                                                            measuredHeight,
                                                            measuredHeight));
                                            SnackbarContentLayout.this.mSnackBarContentLayout
                                                    .setTouchDelegate(seslTouchTargetDelegate);
                                        }
                                    });
                        }
                    });
        }
    }
}
