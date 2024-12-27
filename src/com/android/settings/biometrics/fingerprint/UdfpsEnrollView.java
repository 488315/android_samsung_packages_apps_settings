package com.android.settings.biometrics.fingerprint;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.RotationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.settings.R;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UdfpsEnrollView extends FrameLayout implements UdfpsEnrollHelper.Listener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final UdfpsEnrollDrawable mFingerprintDrawable;
    public final UdfpsEnrollProgressBarDrawable mFingerprintProgressDrawable;
    public final Handler mHandler;
    public UdfpsOverlayParams mOverlayParams;
    public int mProgressBarRadius;
    public Rect mSensorRect;

    public static void $r8$lambda$BF2LnLPda7pHzf6nSGUJBYkDPgE(UdfpsEnrollView udfpsEnrollView) {
        udfpsEnrollView.mProgressBarRadius =
                (int)
                        (udfpsEnrollView.mOverlayParams.scaleFactor
                                * udfpsEnrollView
                                        .getContext()
                                        .getResources()
                                        .getInteger(R.integer.config_udfpsEnrollProgressBar));
        udfpsEnrollView.mSensorRect = new Rect(udfpsEnrollView.mOverlayParams.sensorBounds);
        Rect rect = new Rect(udfpsEnrollView.mOverlayParams.sensorBounds);
        UdfpsOverlayParams udfpsOverlayParams = udfpsEnrollView.mOverlayParams;
        int i = udfpsOverlayParams.rotation;
        if (i == 1 || i == 3) {
            RotationUtils.rotateBounds(
                    rect,
                    udfpsOverlayParams.naturalDisplayWidth,
                    udfpsOverlayParams.naturalDisplayHeight,
                    i);
        }
        ViewGroup viewGroup = (ViewGroup) udfpsEnrollView.getParent();
        ViewGroup.MarginLayoutParams marginLayoutParams =
                (ViewGroup.MarginLayoutParams) udfpsEnrollView.getLayoutParams();
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) udfpsEnrollView.getLayoutParams();
        if (i == 0 || i == 2) {
            int[] locationOnScreen = viewGroup.getLocationOnScreen();
            int i2 = locationOnScreen[0];
            int i3 = locationOnScreen[1];
            int width = viewGroup.getWidth() + i2;
            layoutParams.gravity = 53;
            int paddingX = (width - rect.right) - udfpsEnrollView.getPaddingX();
            int paddingY = (rect.top - i3) - udfpsEnrollView.getPaddingY();
            if (marginLayoutParams.rightMargin != paddingX
                    || marginLayoutParams.topMargin != paddingY) {
                marginLayoutParams.rightMargin = paddingX;
                marginLayoutParams.topMargin = paddingY;
                udfpsEnrollView.setLayoutParams(layoutParams);
            }
            udfpsEnrollView.mSensorRect.set(
                    udfpsEnrollView.getPaddingX(),
                    udfpsEnrollView.getPaddingY(),
                    udfpsEnrollView.mOverlayParams.sensorBounds.width()
                            + udfpsEnrollView.getPaddingX(),
                    udfpsEnrollView.mOverlayParams.sensorBounds.height()
                            + udfpsEnrollView.getPaddingY());
            UdfpsEnrollDrawable udfpsEnrollDrawable = udfpsEnrollView.mFingerprintDrawable;
            RectF rectF = new RectF(udfpsEnrollView.mSensorRect);
            udfpsEnrollDrawable.getClass();
            int height = ((int) rectF.height()) / 8;
            Rect rect2 =
                    new Rect(
                            ((int) rectF.left) + height,
                            ((int) rectF.top) + height,
                            ((int) rectF.right) - height,
                            ((int) rectF.bottom) - height);
            udfpsEnrollDrawable.mFingerprintDrawable.setBounds(rect2);
            udfpsEnrollDrawable.invalidateSelf();
            udfpsEnrollDrawable.mMovingTargetFpIcon.setBounds(rect2);
            udfpsEnrollDrawable.invalidateSelf();
            udfpsEnrollDrawable.mSensorRect = rectF;
        }
        int[] locationOnScreen2 = viewGroup.getLocationOnScreen();
        int i4 = locationOnScreen2[0];
        int i5 = locationOnScreen2[1];
        int width2 = viewGroup.getWidth() + i4;
        int height2 = viewGroup.getHeight() + i5;
        if (i == 1) {
            layoutParams.gravity = 85;
            marginLayoutParams.rightMargin = (width2 - rect.right) - udfpsEnrollView.getPaddingX();
            marginLayoutParams.bottomMargin =
                    (height2 - rect.bottom) - udfpsEnrollView.getPaddingY();
        } else if (i == 3) {
            layoutParams.gravity = 83;
            marginLayoutParams.leftMargin = (rect.left - i4) - udfpsEnrollView.getPaddingX();
            marginLayoutParams.bottomMargin =
                    (height2 - rect.bottom) - udfpsEnrollView.getPaddingY();
        }
        layoutParams.height = (udfpsEnrollView.getPaddingX() * 2) + rect.height();
        layoutParams.width = (udfpsEnrollView.getPaddingY() * 2) + rect.width();
        udfpsEnrollView.setLayoutParams(layoutParams);
        udfpsEnrollView.mSensorRect.set(
                udfpsEnrollView.getPaddingX(),
                udfpsEnrollView.getPaddingY(),
                udfpsEnrollView.mOverlayParams.sensorBounds.width() + udfpsEnrollView.getPaddingX(),
                udfpsEnrollView.mOverlayParams.sensorBounds.height()
                        + udfpsEnrollView.getPaddingY());
        UdfpsEnrollDrawable udfpsEnrollDrawable2 = udfpsEnrollView.mFingerprintDrawable;
        RectF rectF2 = new RectF(udfpsEnrollView.mSensorRect);
        udfpsEnrollDrawable2.getClass();
        int height3 = ((int) rectF2.height()) / 8;
        Rect rect22 =
                new Rect(
                        ((int) rectF2.left) + height3,
                        ((int) rectF2.top) + height3,
                        ((int) rectF2.right) - height3,
                        ((int) rectF2.bottom) - height3);
        udfpsEnrollDrawable2.mFingerprintDrawable.setBounds(rect22);
        udfpsEnrollDrawable2.invalidateSelf();
        udfpsEnrollDrawable2.mMovingTargetFpIcon.setBounds(rect22);
        udfpsEnrollDrawable2.invalidateSelf();
        udfpsEnrollDrawable2.mSensorRect = rectF2;
    }

    public UdfpsEnrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFingerprintDrawable =
                new UdfpsEnrollDrawable(((FrameLayout) this).mContext, attributeSet);
        this.mFingerprintProgressDrawable =
                new UdfpsEnrollProgressBarDrawable(context, attributeSet);
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    private int getPaddingX() {
        return this.mProgressBarRadius;
    }

    private int getPaddingY() {
        return this.mProgressBarRadius;
    }

    public UdfpsOverlayParams getOverlayParams() {
        return this.mOverlayParams;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        ((ImageView) findViewById(R.id.udfps_enroll_animation_fp_view))
                .setImageDrawable(this.mFingerprintDrawable);
        ((ImageView) findViewById(R.id.udfps_enroll_animation_fp_progress_view))
                .setImageDrawable(this.mFingerprintProgressDrawable);
    }

    public void setEnrollHelper(UdfpsEnrollHelper udfpsEnrollHelper) {
        this.mFingerprintDrawable.mEnrollHelper = udfpsEnrollHelper;
        udfpsEnrollHelper.mListener = this;
        int i = udfpsEnrollHelper.mTotalSteps;
        if (i != -1) {
            this.mHandler.post(
                    new UdfpsEnrollView$$ExternalSyntheticLambda1(
                            this, udfpsEnrollHelper.mRemainingSteps, i, 0));
        }
    }

    public void setOverlayParams(UdfpsOverlayParams udfpsOverlayParams) {
        this.mOverlayParams = udfpsOverlayParams;
        post(
                new Runnable() { // from class:
                                 // com.android.settings.biometrics.fingerprint.UdfpsEnrollView$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        UdfpsEnrollView.$r8$lambda$BF2LnLPda7pHzf6nSGUJBYkDPgE(
                                UdfpsEnrollView.this);
                    }
                });
    }
}
