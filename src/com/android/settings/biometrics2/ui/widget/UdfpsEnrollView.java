package com.android.settings.biometrics2.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.RotationUtils;
import android.view.DisplayInfo;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.settings.R;
import com.android.systemui.biometrics.UdfpsUtils;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class UdfpsEnrollView extends FrameLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final UdfpsEnrollDrawable mFingerprintDrawable;
    public final UdfpsEnrollProgressBarDrawable mFingerprintProgressDrawable;
    public final Handler mHandler;
    public final UdfpsEnrollView$$ExternalSyntheticLambda1 mOnDrawListener;
    public UdfpsOverlayParams mOverlayParams;
    public int mProgressBarRadius;
    public int mRemainingSteps;
    public FingerprintSensorPropertiesInternal mSensorProperties;
    public Rect mSensorRect;
    public int mTotalSteps;
    public final UdfpsUtils mUdfpsUtils;

    public static void $r8$lambda$PiM0aR_wvNKlynWGHro_9xCsfqk(UdfpsEnrollView udfpsEnrollView) {
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
        RelativeLayout relativeLayout = (RelativeLayout) udfpsEnrollView.getParent();
        if (relativeLayout == null) {
            Log.e(
                    "UdfpsEnrollView",
                    "Fail to updateDimensions for " + udfpsEnrollView + ", parent null");
        } else {
            int[] locationOnScreen = relativeLayout.getLocationOnScreen();
            int i2 = locationOnScreen[0];
            int i3 = locationOnScreen[1];
            int width = relativeLayout.getWidth() + i2;
            int height = relativeLayout.getHeight() + i3;
            RelativeLayout.LayoutParams layoutParams =
                    new RelativeLayout.LayoutParams(
                            udfpsEnrollView.getWidth(), udfpsEnrollView.getHeight());
            if (i == 0 || i == 2) {
                layoutParams.addRule(10);
                layoutParams.addRule(11);
                layoutParams.rightMargin = (width - rect.right) - udfpsEnrollView.getPaddingX();
                layoutParams.topMargin = (rect.top - i3) - udfpsEnrollView.getPaddingY();
            } else if (i == 1) {
                layoutParams.addRule(12);
                layoutParams.addRule(11);
                layoutParams.rightMargin = (width - rect.right) - udfpsEnrollView.getPaddingX();
                layoutParams.bottomMargin = (height - rect.bottom) - udfpsEnrollView.getPaddingY();
            } else {
                layoutParams.addRule(12);
                layoutParams.addRule(9);
                layoutParams.bottomMargin = (height - rect.bottom) - udfpsEnrollView.getPaddingY();
                layoutParams.leftMargin = (rect.left - i2) - udfpsEnrollView.getPaddingX();
            }
            layoutParams.height = (udfpsEnrollView.getPaddingX() * 2) + rect.height();
            layoutParams.width = (udfpsEnrollView.getPaddingY() * 2) + rect.width();
            udfpsEnrollView.setLayoutParams(layoutParams);
        }
        udfpsEnrollView.mSensorRect.set(
                udfpsEnrollView.getPaddingX(),
                udfpsEnrollView.getPaddingY(),
                udfpsEnrollView.mOverlayParams.sensorBounds.width() + udfpsEnrollView.getPaddingX(),
                udfpsEnrollView.mOverlayParams.sensorBounds.height()
                        + udfpsEnrollView.getPaddingY());
        UdfpsEnrollDrawable udfpsEnrollDrawable = udfpsEnrollView.mFingerprintDrawable;
        RectF rectF = new RectF(udfpsEnrollView.mSensorRect);
        udfpsEnrollDrawable.getClass();
        int height2 = ((int) rectF.height()) / 8;
        Rect rect2 =
                new Rect(
                        ((int) rectF.left) + height2,
                        ((int) rectF.top) + height2,
                        ((int) rectF.right) - height2,
                        ((int) rectF.bottom) - height2);
        udfpsEnrollDrawable.mFingerprintDrawable.setBounds(rect2);
        udfpsEnrollDrawable.invalidateSelf();
        udfpsEnrollDrawable.mMovingTargetFpIcon.setBounds(rect2);
        udfpsEnrollDrawable.invalidateSelf();
        udfpsEnrollDrawable.mSensorRect = rectF;
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.settings.biometrics2.ui.widget.UdfpsEnrollView$$ExternalSyntheticLambda1] */
    public UdfpsEnrollView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTotalSteps = -1;
        this.mRemainingSteps = -1;
        this.mOnDrawListener =
                new ViewTreeObserver
                        .OnDrawListener() { // from class:
                                            // com.android.settings.biometrics2.ui.widget.UdfpsEnrollView$$ExternalSyntheticLambda1
                    @Override // android.view.ViewTreeObserver.OnDrawListener
                    public final void onDraw() {
                        UdfpsEnrollView udfpsEnrollView = UdfpsEnrollView.this;
                        if (udfpsEnrollView.mSensorProperties == null) {
                            Log.e("UdfpsEnrollView", "There is no sensor info!");
                            return;
                        }
                        DisplayInfo displayInfo = new DisplayInfo();
                        if (udfpsEnrollView.getDisplay() == null) {
                            Log.e("UdfpsEnrollView", "Can not get display");
                            return;
                        }
                        udfpsEnrollView.getDisplay().getDisplayInfo(displayInfo);
                        Rect rect = udfpsEnrollView.mSensorProperties.getLocation().getRect();
                        udfpsEnrollView.mUdfpsUtils.getClass();
                        float scaleFactor = UdfpsUtils.getScaleFactor(displayInfo);
                        rect.scale(scaleFactor);
                        udfpsEnrollView.mOverlayParams =
                                new UdfpsOverlayParams(
                                        rect,
                                        new Rect(
                                                0,
                                                displayInfo.getNaturalHeight() / 2,
                                                displayInfo.getNaturalWidth(),
                                                displayInfo.getNaturalHeight()),
                                        displayInfo.getNaturalWidth(),
                                        displayInfo.getNaturalHeight(),
                                        scaleFactor,
                                        displayInfo.rotation,
                                        udfpsEnrollView.mSensorProperties.sensorType);
                        udfpsEnrollView.post(
                                new UdfpsEnrollView$$ExternalSyntheticLambda2(udfpsEnrollView, 0));
                    }
                };
        this.mFingerprintDrawable =
                new UdfpsEnrollDrawable(((FrameLayout) this).mContext, attributeSet);
        this.mFingerprintProgressDrawable =
                new UdfpsEnrollProgressBarDrawable(context, attributeSet);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mUdfpsUtils = new UdfpsUtils();
    }

    private int getPaddingX() {
        return this.mProgressBarRadius;
    }

    private int getPaddingY() {
        return this.mProgressBarRadius;
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        ViewTreeObserver viewTreeObserver;
        ViewGroup viewGroup = (ViewGroup) getParent();
        if (viewGroup != null && (viewTreeObserver = viewGroup.getViewTreeObserver()) != null) {
            viewTreeObserver.removeOnDrawListener(this.mOnDrawListener);
        }
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        ((ImageView) findViewById(R.id.udfps_enroll_animation_fp_view))
                .setImageDrawable(this.mFingerprintDrawable);
        ((ImageView) findViewById(R.id.udfps_enroll_animation_fp_progress_view))
                .setImageDrawable(this.mFingerprintProgressDrawable);
    }

    public void setSensorProperties(
            FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal) {
        this.mSensorProperties = fingerprintSensorPropertiesInternal;
        ((ViewGroup) getParent()).getViewTreeObserver().addOnDrawListener(this.mOnDrawListener);
    }
}
