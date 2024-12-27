package com.android.settings.biometrics.fingerprint;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.systemui.biometrics.UdfpsUtils;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupdesign.GlifLayout;

import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UdfpsEnrollEnrollingView extends GlifLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AccessibilityManager mAccessibilityManager;
    public final Context mContext;
    public View mHeaderView;
    public final boolean mIsLandscape;
    public final int mRotation;
    public final boolean mShouldUseReverseLandscape;
    public UdfpsEnrollView mUdfpsEnrollView;
    public final UdfpsUtils mUdfpsUtils;

    public UdfpsEnrollEnrollingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        int rotation = context.getDisplay().getRotation();
        this.mRotation = rotation;
        boolean z = false;
        this.mIsLandscape = rotation == 1 || rotation == 3;
        boolean z2 = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
        if ((rotation == 1 && z2) || (rotation == 3 && !z2)) {
            z = true;
        }
        this.mShouldUseReverseLandscape = z;
        this.mUdfpsUtils = new UdfpsUtils();
    }

    @VisibleForTesting
    public boolean hasOverlap(View view, View view2) {
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        view.getLocationOnScreen(iArr);
        view2.getLocationOnScreen(iArr2);
        int i = iArr[0];
        Rect rect =
                new Rect(
                        i,
                        iArr[1],
                        view.getMeasuredWidth() + i,
                        view.getMeasuredHeight() + iArr[1]);
        int i2 = iArr2[0];
        return rect.intersect(
                new Rect(
                        i2,
                        iArr2[1],
                        view2.getMeasuredWidth() + i2,
                        view2.getMeasuredHeight() + iArr2[1]));
    }

    @Override // com.google.android.setupdesign.GlifLayout, android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mHeaderView = findViewById(R.id.sud_landscape_header_area);
        this.mUdfpsEnrollView = (UdfpsEnrollView) findViewById(R.id.udfps_animation_view);
    }

    public void setSecondaryButtonBackground(int i) {
        if (!this.mIsLandscape || this.mShouldUseReverseLandscape) {
            return;
        }
        final Button secondaryButtonView =
                ((FooterBarMixin) getMixin(FooterBarMixin.class)).getSecondaryButtonView();
        secondaryButtonView.setBackgroundColor(i);
        if (this.mRotation == 1) {
            secondaryButtonView.setGravity(8388611);
        } else {
            secondaryButtonView.setGravity(8388613);
        }
        this.mHeaderView.post(
                new Runnable() { // from class:
                                 // com.android.settings.biometrics.fingerprint.UdfpsEnrollEnrollingView$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        UdfpsEnrollEnrollingView udfpsEnrollEnrollingView =
                                UdfpsEnrollEnrollingView.this;
                        Button button = secondaryButtonView;
                        int i2 = UdfpsEnrollEnrollingView.$r8$clinit;
                        udfpsEnrollEnrollingView.getClass();
                        button.setLayoutParams(
                                new LinearLayout.LayoutParams(
                                        udfpsEnrollEnrollingView.mHeaderView.getMeasuredWidth(),
                                        -2));
                    }
                });
    }
}
