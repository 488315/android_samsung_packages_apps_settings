package com.android.settings.widget;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import androidx.compose.animation.AndroidFlingSpline$$ExternalSyntheticOutline0;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.settings.R$styleable;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DotsPageIndicator extends View implements ViewPager.OnPageChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final long animDuration;
    public final long animHalfDuration;
    public boolean attachedState;
    public final Path combinedUnselectedPath;
    public int currentPage;
    public float dotBottomY;
    public float[] dotCenterX;
    public float dotCenterY;
    public final int dotDiameter;
    public final float dotRadius;
    public float[] dotRevealFractions;
    public float dotTopY;
    public float endX1;
    public float endY1;
    public final int gap;
    public final float halfDotRadius;
    public final Interpolator interpolator;
    public AnimatorSet joiningAnimationSet;
    public ValueAnimator[] joiningAnimations;
    public float[] joiningFractions;
    public ValueAnimator moveAnimation;
    public ViewPager.OnPageChangeListener pageChangeListener;
    public int pageCount;
    public final RectF rectF;
    public PendingRetreatAnimator retreatAnimation;
    public float retreatingJoinX1;
    public float retreatingJoinX2;
    public PendingRevealAnimator[] revealAnimations;
    public final int selectedColour;
    public boolean selectedDotInPosition;
    public float selectedDotX;
    public final Paint selectedPaint;
    public final int unselectedColour;
    public final Path unselectedDotLeftPath;
    public final Path unselectedDotPath;
    public final Path unselectedDotRightPath;
    public final Paint unselectedPaint;
    public ViewPager viewPager;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.widget.DotsPageIndicator$3, reason: invalid class name */
    public final class AnonymousClass3 implements ValueAnimator.AnimatorUpdateListener {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass3(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            switch (this.$r8$classId) {
                case 0:
                    ((DotsPageIndicator) this.this$0).selectedDotX =
                            ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    DotsPageIndicator dotsPageIndicator = (DotsPageIndicator) this.this$0;
                    dotsPageIndicator.retreatAnimation.startIfNecessary(
                            dotsPageIndicator.selectedDotX);
                    ((DotsPageIndicator) this.this$0).postInvalidateOnAnimation();
                    break;
                default:
                    PendingRevealAnimator pendingRevealAnimator =
                            (PendingRevealAnimator) this.this$0;
                    DotsPageIndicator dotsPageIndicator2 = pendingRevealAnimator.this$0;
                    dotsPageIndicator2.dotRevealFractions[pendingRevealAnimator.dot] =
                            ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    dotsPageIndicator2.postInvalidateOnAnimation();
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.widget.DotsPageIndicator$4, reason: invalid class name */
    public final class AnonymousClass4 extends AnimatorListenerAdapter {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass4(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // android.animation.AnimatorListenerAdapter,
                  // android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            switch (this.$r8$classId) {
                case 0:
                    ((DotsPageIndicator) this.this$0).selectedDotInPosition = true;
                    break;
                default:
                    PendingRevealAnimator pendingRevealAnimator =
                            (PendingRevealAnimator) this.this$0;
                    DotsPageIndicator dotsPageIndicator = pendingRevealAnimator.this$0;
                    dotsPageIndicator.dotRevealFractions[pendingRevealAnimator.dot] = 0.0f;
                    dotsPageIndicator.postInvalidateOnAnimation();
                    ((PendingRevealAnimator) this.this$0).this$0.postInvalidateOnAnimation();
                    break;
            }
        }

        @Override // android.animation.AnimatorListenerAdapter,
                  // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            switch (this.$r8$classId) {
                case 0:
                    ((DotsPageIndicator) this.this$0).selectedDotInPosition = false;
                    break;
                default:
                    super.onAnimationStart(animator);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LeftwardStartPredicate {
        public final /* synthetic */ int $r8$classId;
        public final float thresholdValue;

        public LeftwardStartPredicate(float f, int i) {
            this.$r8$classId = i;
            this.thresholdValue = f;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PendingRetreatAnimator extends PendingStartAnimator {
        public final /* synthetic */ DotsPageIndicator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PendingRetreatAnimator(
                DotsPageIndicator dotsPageIndicator,
                int i,
                int i2,
                int i3,
                LeftwardStartPredicate leftwardStartPredicate) {
            super(leftwardStartPredicate);
            float f;
            float f2;
            float f3;
            float f4;
            float max;
            float f5;
            float f6;
            float f7;
            final int i4 = 0;
            final int i5 = 1;
            this.this$0 = dotsPageIndicator;
            setDuration(dotsPageIndicator.animHalfDuration);
            setInterpolator(dotsPageIndicator.interpolator);
            if (i2 > i) {
                f = Math.min(dotsPageIndicator.dotCenterX[i], dotsPageIndicator.selectedDotX);
                f2 = dotsPageIndicator.dotRadius;
            } else {
                f = dotsPageIndicator.dotCenterX[i2];
                f2 = dotsPageIndicator.dotRadius;
            }
            final float f8 = f - f2;
            if (i2 > i) {
                f3 = dotsPageIndicator.dotCenterX[i2];
                f4 = dotsPageIndicator.dotRadius;
            } else {
                f3 = dotsPageIndicator.dotCenterX[i2];
                f4 = dotsPageIndicator.dotRadius;
            }
            float f9 = f3 - f4;
            if (i2 > i) {
                max = dotsPageIndicator.dotCenterX[i2];
                f5 = dotsPageIndicator.dotRadius;
            } else {
                max = Math.max(dotsPageIndicator.dotCenterX[i], dotsPageIndicator.selectedDotX);
                f5 = dotsPageIndicator.dotRadius;
            }
            final float f10 = max + f5;
            if (i2 > i) {
                f6 = dotsPageIndicator.dotCenterX[i2];
                f7 = dotsPageIndicator.dotRadius;
            } else {
                f6 = dotsPageIndicator.dotCenterX[i2];
                f7 = dotsPageIndicator.dotRadius;
            }
            float f11 = f6 + f7;
            dotsPageIndicator.revealAnimations = new PendingRevealAnimator[i3];
            final int[] iArr = new int[i3];
            if (f8 != f9) {
                setFloatValues(f8, f9);
                for (int i6 = 0; i6 < i3; i6++) {
                    int i7 = i + i6;
                    dotsPageIndicator.revealAnimations[i6] =
                            new PendingRevealAnimator(
                                    dotsPageIndicator,
                                    i7,
                                    new LeftwardStartPredicate(
                                            dotsPageIndicator.dotCenterX[i7], 1));
                    iArr[i6] = i7;
                }
                addUpdateListener(
                        new ValueAnimator.AnimatorUpdateListener(
                                this) { // from class:
                                        // com.android.settings.widget.DotsPageIndicator.PendingRetreatAnimator.1
                            public final /* synthetic */ PendingRetreatAnimator this$1;

                            {
                                this.this$1 = this;
                            }

                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                switch (i4) {
                                    case 0:
                                        this.this$1.this$0.retreatingJoinX1 =
                                                ((Float) valueAnimator.getAnimatedValue())
                                                        .floatValue();
                                        this.this$1.this$0.postInvalidateOnAnimation();
                                        for (PendingRevealAnimator pendingRevealAnimator :
                                                this.this$1.this$0.revealAnimations) {
                                            pendingRevealAnimator.startIfNecessary(
                                                    this.this$1.this$0.retreatingJoinX1);
                                        }
                                        break;
                                    default:
                                        this.this$1.this$0.retreatingJoinX2 =
                                                ((Float) valueAnimator.getAnimatedValue())
                                                        .floatValue();
                                        this.this$1.this$0.postInvalidateOnAnimation();
                                        for (PendingRevealAnimator pendingRevealAnimator2 :
                                                this.this$1.this$0.revealAnimations) {
                                            pendingRevealAnimator2.startIfNecessary(
                                                    this.this$1.this$0.retreatingJoinX2);
                                        }
                                        break;
                                }
                            }
                        });
            } else {
                setFloatValues(f10, f11);
                for (int i8 = 0; i8 < i3; i8++) {
                    int i9 = i - i8;
                    dotsPageIndicator.revealAnimations[i8] =
                            new PendingRevealAnimator(
                                    dotsPageIndicator,
                                    i9,
                                    new LeftwardStartPredicate(
                                            dotsPageIndicator.dotCenterX[i9], 0));
                    iArr[i8] = i9;
                }
                addUpdateListener(
                        new ValueAnimator.AnimatorUpdateListener(
                                this) { // from class:
                                        // com.android.settings.widget.DotsPageIndicator.PendingRetreatAnimator.1
                            public final /* synthetic */ PendingRetreatAnimator this$1;

                            {
                                this.this$1 = this;
                            }

                            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                                switch (i5) {
                                    case 0:
                                        this.this$1.this$0.retreatingJoinX1 =
                                                ((Float) valueAnimator.getAnimatedValue())
                                                        .floatValue();
                                        this.this$1.this$0.postInvalidateOnAnimation();
                                        for (PendingRevealAnimator pendingRevealAnimator :
                                                this.this$1.this$0.revealAnimations) {
                                            pendingRevealAnimator.startIfNecessary(
                                                    this.this$1.this$0.retreatingJoinX1);
                                        }
                                        break;
                                    default:
                                        this.this$1.this$0.retreatingJoinX2 =
                                                ((Float) valueAnimator.getAnimatedValue())
                                                        .floatValue();
                                        this.this$1.this$0.postInvalidateOnAnimation();
                                        for (PendingRevealAnimator pendingRevealAnimator2 :
                                                this.this$1.this$0.revealAnimations) {
                                            pendingRevealAnimator2.startIfNecessary(
                                                    this.this$1.this$0.retreatingJoinX2);
                                        }
                                        break;
                                }
                            }
                        });
            }
            addListener(
                    new AnimatorListenerAdapter() { // from class:
                                                    // com.android.settings.widget.DotsPageIndicator.PendingRetreatAnimator.3
                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(Animator animator) {
                            DotsPageIndicator dotsPageIndicator2 =
                                    PendingRetreatAnimator.this.this$0;
                            dotsPageIndicator2.retreatingJoinX1 = -1.0f;
                            dotsPageIndicator2.retreatingJoinX2 = -1.0f;
                            dotsPageIndicator2.postInvalidateOnAnimation();
                        }

                        @Override // android.animation.AnimatorListenerAdapter,
                                  // android.animation.Animator.AnimatorListener
                        public final void onAnimationStart(Animator animator) {
                            DotsPageIndicator dotsPageIndicator2 =
                                    PendingRetreatAnimator.this.this$0;
                            int i10 = DotsPageIndicator.$r8$clinit;
                            AnimatorSet animatorSet = dotsPageIndicator2.joiningAnimationSet;
                            if (animatorSet != null && animatorSet.isRunning()) {
                                dotsPageIndicator2.joiningAnimationSet.cancel();
                            }
                            DotsPageIndicator dotsPageIndicator3 =
                                    PendingRetreatAnimator.this.this$0;
                            Arrays.fill(dotsPageIndicator3.joiningFractions, 0.0f);
                            dotsPageIndicator3.postInvalidateOnAnimation();
                            for (int i11 : iArr) {
                                DotsPageIndicator dotsPageIndicator4 =
                                        PendingRetreatAnimator.this.this$0;
                                dotsPageIndicator4.dotRevealFractions[i11] = 1.0E-5f;
                                dotsPageIndicator4.postInvalidateOnAnimation();
                            }
                            DotsPageIndicator dotsPageIndicator5 =
                                    PendingRetreatAnimator.this.this$0;
                            dotsPageIndicator5.retreatingJoinX1 = f8;
                            dotsPageIndicator5.retreatingJoinX2 = f10;
                            dotsPageIndicator5.postInvalidateOnAnimation();
                        }
                    });
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class PendingRevealAnimator extends PendingStartAnimator {
        public final int dot;
        public final /* synthetic */ DotsPageIndicator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public PendingRevealAnimator(
                DotsPageIndicator dotsPageIndicator,
                int i,
                LeftwardStartPredicate leftwardStartPredicate) {
            super(leftwardStartPredicate);
            int i2 = 1;
            this.this$0 = dotsPageIndicator;
            this.dot = i;
            setFloatValues(1.0E-5f, 1.0f);
            setDuration(dotsPageIndicator.animHalfDuration);
            setInterpolator(dotsPageIndicator.interpolator);
            addUpdateListener(new AnonymousClass3(i2, this));
            addListener(new AnonymousClass4(i2, this));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class PendingStartAnimator extends ValueAnimator {
        public boolean hasStarted = false;
        public final LeftwardStartPredicate predicate;

        public PendingStartAnimator(LeftwardStartPredicate leftwardStartPredicate) {
            this.predicate = leftwardStartPredicate;
        }

        /* JADX WARN: Code restructure failed: missing block: B:15:0x0019, code lost:

           if (r3 < r0.thresholdValue) goto L8;
        */
        /* JADX WARN: Code restructure failed: missing block: B:6:0x000f, code lost:

           if (r3 > r0.thresholdValue) goto L8;
        */
        /* JADX WARN: Code restructure failed: missing block: B:7:0x0011, code lost:

           r3 = true;
        */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void startIfNecessary(float r3) {
            /*
                r2 = this;
                boolean r0 = r2.hasStarted
                if (r0 != 0) goto L24
                com.android.settings.widget.DotsPageIndicator$LeftwardStartPredicate r0 = r2.predicate
                int r1 = r0.$r8$classId
                switch(r1) {
                    case 0: goto L15;
                    default: goto Lb;
                }
            Lb:
                float r0 = r0.thresholdValue
                int r3 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
                if (r3 <= 0) goto L13
            L11:
                r3 = 1
                goto L1c
            L13:
                r3 = 0
                goto L1c
            L15:
                float r0 = r0.thresholdValue
                int r3 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
                if (r3 >= 0) goto L13
                goto L11
            L1c:
                if (r3 == 0) goto L24
                r2.start()
                r3 = 1
                r2.hasStarted = r3
            L24:
                return
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.settings.widget.DotsPageIndicator.PendingStartAnimator.startIfNecessary(float):void");
        }
    }

    public DotsPageIndicator(Context context) {
        this(context, null, 0);
    }

    private int getDesiredHeight() {
        return getPaddingBottom() + getPaddingTop() + this.dotDiameter;
    }

    private int getDesiredWidth() {
        return getPaddingRight() + getPaddingLeft() + getRequiredWidth();
    }

    private int getRequiredWidth() {
        int i = this.pageCount;
        return ((i - 1) * this.gap) + (this.dotDiameter * i);
    }

    private Path getRetreatingJoinPath() {
        this.unselectedDotPath.rewind();
        this.rectF.set(this.retreatingJoinX1, this.dotTopY, this.retreatingJoinX2, this.dotBottomY);
        Path path = this.unselectedDotPath;
        RectF rectF = this.rectF;
        float f = this.dotRadius;
        path.addRoundRect(rectF, f, f, Path.Direction.CW);
        return this.unselectedDotPath;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPageCount(int i) {
        this.pageCount = i;
        calculateDotPositions();
        resetState();
    }

    private void setSelectedPage(int i) {
        int i2 = 0;
        int i3 = this.currentPage;
        if (i == i3 || this.pageCount == 0) {
            return;
        }
        this.currentPage = i;
        cancelRunningAnimations();
        int abs = Math.abs(i - i3);
        float f = this.dotCenterX[i];
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.selectedDotX, f);
        this.retreatAnimation =
                new PendingRetreatAnimator(
                        this,
                        i3,
                        i,
                        abs,
                        i > i3
                                ? new LeftwardStartPredicate(
                                        f - ((f - this.selectedDotX) * 0.25f), 1)
                                : new LeftwardStartPredicate(
                                        AndroidFlingSpline$$ExternalSyntheticOutline0.m(
                                                this.selectedDotX, f, 0.25f, f),
                                        0));
        ofFloat.addUpdateListener(new AnonymousClass3(i2, this));
        ofFloat.addListener(new AnonymousClass4(i2, this));
        ofFloat.setStartDelay(this.selectedDotInPosition ? this.animDuration / 4 : 0L);
        ofFloat.setDuration((this.animDuration * 3) / 4);
        ofFloat.setInterpolator(this.interpolator);
        this.moveAnimation = ofFloat;
        this.joiningAnimations = new ValueAnimator[abs];
        while (i2 < abs) {
            ValueAnimator[] valueAnimatorArr = this.joiningAnimations;
            final int i4 = i > i3 ? i3 + i2 : (i3 - 1) - i2;
            long j = (this.animDuration / 8) * i2;
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
            ofFloat2.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // com.android.settings.widget.DotsPageIndicator.5
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            DotsPageIndicator dotsPageIndicator = DotsPageIndicator.this;
                            dotsPageIndicator.joiningFractions[i4] =
                                    valueAnimator.getAnimatedFraction();
                            dotsPageIndicator.postInvalidateOnAnimation();
                        }
                    });
            ofFloat2.setDuration(this.animHalfDuration);
            ofFloat2.setStartDelay(j);
            ofFloat2.setInterpolator(this.interpolator);
            valueAnimatorArr[i2] = ofFloat2;
            i2++;
        }
        this.moveAnimation.start();
        AnimatorSet animatorSet = new AnimatorSet();
        this.joiningAnimationSet = animatorSet;
        animatorSet.playTogether(this.joiningAnimations);
        this.joiningAnimationSet.start();
    }

    public final void calculateDotPositions() {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        float width =
                ((((getWidth() - getPaddingRight()) - paddingLeft) - getRequiredWidth()) / 2)
                        + paddingLeft
                        + this.dotRadius;
        this.dotCenterX = new float[this.pageCount];
        for (int i = 0; i < this.pageCount; i++) {
            this.dotCenterX[i] = ((this.dotDiameter + this.gap) * i) + width;
        }
        float f = paddingTop;
        this.dotTopY = f;
        this.dotCenterY = f + this.dotRadius;
        this.dotBottomY = paddingTop + this.dotDiameter;
        setCurrentPageImmediate();
    }

    public final void cancelRunningAnimations() {
        ValueAnimator valueAnimator = this.moveAnimation;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.moveAnimation.cancel();
        }
        AnimatorSet animatorSet = this.joiningAnimationSet;
        if (animatorSet != null && animatorSet.isRunning()) {
            this.joiningAnimationSet.cancel();
        }
        PendingRetreatAnimator pendingRetreatAnimator = this.retreatAnimation;
        if (pendingRetreatAnimator != null && pendingRetreatAnimator.isRunning()) {
            this.retreatAnimation.cancel();
        }
        PendingRevealAnimator[] pendingRevealAnimatorArr = this.revealAnimations;
        if (pendingRevealAnimatorArr != null) {
            for (PendingRevealAnimator pendingRevealAnimator : pendingRevealAnimatorArr) {
                pendingRevealAnimator.cancel();
            }
        }
        resetState();
    }

    @Override // android.view.View
    public final void clearAnimation() {
        super.clearAnimation();
        cancelRunningAnimations();
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public float getDotCenterY() {
        return this.dotCenterY;
    }

    public int getSelectedColour() {
        return this.selectedColour;
    }

    public float getSelectedDotX() {
        return this.selectedDotX;
    }

    public int getUnselectedColour() {
        return this.unselectedColour;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        if (this.viewPager == null || this.pageCount == 0) {
            return;
        }
        this.combinedUnselectedPath.rewind();
        int i = 0;
        while (true) {
            int i2 = this.pageCount;
            if (i >= i2) {
                break;
            }
            int i3 = i2 - 1;
            int i4 = i == i3 ? i : i + 1;
            float[] fArr = this.dotCenterX;
            float f = fArr[i];
            float f2 = fArr[i4];
            float f3 = i == i3 ? -1.0f : this.joiningFractions[i];
            float f4 = this.dotRevealFractions[i];
            this.unselectedDotPath.rewind();
            if ((f3 == 0.0f || f3 == -1.0f)
                    && f4 == 0.0f
                    && (i != this.currentPage || !this.selectedDotInPosition)) {
                this.unselectedDotPath.addCircle(
                        this.dotCenterX[i], this.dotCenterY, this.dotRadius, Path.Direction.CW);
            }
            if (f3 > 0.0f && f3 < 0.5f && this.retreatingJoinX1 == -1.0f) {
                this.unselectedDotLeftPath.rewind();
                this.unselectedDotLeftPath.moveTo(f, this.dotBottomY);
                RectF rectF = this.rectF;
                float f5 = this.dotRadius;
                rectF.set(f - f5, this.dotTopY, f5 + f, this.dotBottomY);
                this.unselectedDotLeftPath.arcTo(this.rectF, 90.0f, 180.0f, true);
                float f6 = this.dotRadius + f + (this.gap * f3);
                this.endX1 = f6;
                float f7 = this.dotCenterY;
                this.endY1 = f7;
                float f8 = this.halfDotRadius;
                this.unselectedDotLeftPath.cubicTo(f + f8, this.dotTopY, f6, f7 - f8, f6, f7);
                float f9 = this.dotBottomY;
                float f10 = this.endX1;
                float f11 = this.endY1;
                float f12 = this.halfDotRadius;
                this.unselectedDotLeftPath.cubicTo(f10, f11 + f12, f + f12, f9, f, f9);
                Path path = this.unselectedDotPath;
                Path path2 = this.unselectedDotLeftPath;
                Path.Op op = Path.Op.UNION;
                path.op(path2, op);
                this.unselectedDotRightPath.rewind();
                this.unselectedDotRightPath.moveTo(f2, this.dotBottomY);
                RectF rectF2 = this.rectF;
                float f13 = this.dotRadius;
                rectF2.set(f2 - f13, this.dotTopY, f13 + f2, this.dotBottomY);
                this.unselectedDotRightPath.arcTo(this.rectF, 90.0f, -180.0f, true);
                float f14 = (f2 - this.dotRadius) - (this.gap * f3);
                this.endX1 = f14;
                float f15 = this.dotCenterY;
                this.endY1 = f15;
                float f16 = this.halfDotRadius;
                this.unselectedDotRightPath.cubicTo(
                        f2 - f16, this.dotTopY, f14, f15 - f16, f14, f15);
                float f17 = this.dotBottomY;
                float f18 = this.endX1;
                float f19 = this.endY1;
                float f20 = this.halfDotRadius;
                this.unselectedDotRightPath.cubicTo(f18, f19 + f20, f2 - f20, f17, f2, f17);
                this.unselectedDotPath.op(this.unselectedDotRightPath, op);
            }
            if (f3 > 0.5f && f3 < 1.0f && this.retreatingJoinX1 == -1.0f) {
                this.unselectedDotPath.moveTo(f, this.dotBottomY);
                RectF rectF3 = this.rectF;
                float f21 = this.dotRadius;
                rectF3.set(f - f21, this.dotTopY, f21 + f, this.dotBottomY);
                this.unselectedDotPath.arcTo(this.rectF, 90.0f, 180.0f, true);
                float f22 = this.dotRadius;
                float f23 = f + f22 + (this.gap / 2);
                this.endX1 = f23;
                float f24 = f3 * f22;
                float f25 = this.dotCenterY - f24;
                this.endY1 = f25;
                float f26 = 1.0f - f3;
                this.unselectedDotPath.cubicTo(
                        f23 - f24, this.dotTopY, f23 - (f22 * f26), f25, f23, f25);
                float f27 = this.dotTopY;
                float f28 = this.endX1;
                float f29 = this.dotRadius;
                this.unselectedDotPath.cubicTo(
                        (f26 * f29) + f28, this.endY1, (f29 * f3) + f28, f27, f2, f27);
                RectF rectF4 = this.rectF;
                float f30 = this.dotRadius;
                rectF4.set(f2 - f30, this.dotTopY, f30 + f2, this.dotBottomY);
                this.unselectedDotPath.arcTo(this.rectF, 270.0f, 180.0f, true);
                float f31 = this.dotCenterY;
                float f32 = this.dotRadius;
                float f33 = f3 * f32;
                float f34 = f31 + f33;
                this.endY1 = f34;
                float f35 = this.endX1;
                this.unselectedDotPath.cubicTo(
                        f33 + f35, this.dotBottomY, (f32 * f26) + f35, f34, f35, f34);
                float f36 = this.dotBottomY;
                float f37 = this.endX1;
                float f38 = this.dotRadius;
                this.unselectedDotPath.cubicTo(
                        f37 - (f26 * f38), this.endY1, f37 - (f38 * f3), f36, f, f36);
            }
            if (f3 == 1.0f && this.retreatingJoinX1 == -1.0f) {
                RectF rectF5 = this.rectF;
                float f39 = this.dotRadius;
                rectF5.set(f - f39, this.dotTopY, f2 + f39, this.dotBottomY);
                Path path3 = this.unselectedDotPath;
                RectF rectF6 = this.rectF;
                float f40 = this.dotRadius;
                path3.addRoundRect(rectF6, f40, f40, Path.Direction.CW);
            }
            if (f4 > 1.0E-5f) {
                this.unselectedDotPath.addCircle(
                        f, this.dotCenterY, f4 * this.dotRadius, Path.Direction.CW);
            }
            this.combinedUnselectedPath.op(this.unselectedDotPath, Path.Op.UNION);
            i++;
        }
        if (this.retreatingJoinX1 != -1.0f) {
            this.combinedUnselectedPath.op(getRetreatingJoinPath(), Path.Op.UNION);
        }
        canvas.drawPath(this.combinedUnselectedPath, this.unselectedPaint);
        canvas.drawCircle(this.selectedDotX, this.dotCenterY, this.dotRadius, this.selectedPaint);
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        int desiredHeight = getDesiredHeight();
        int mode = View.MeasureSpec.getMode(i2);
        if (mode == Integer.MIN_VALUE) {
            desiredHeight = Math.min(desiredHeight, View.MeasureSpec.getSize(i2));
        } else if (mode == 1073741824) {
            desiredHeight = View.MeasureSpec.getSize(i2);
        }
        int desiredWidth = getDesiredWidth();
        int mode2 = View.MeasureSpec.getMode(i);
        if (mode2 == Integer.MIN_VALUE) {
            desiredWidth = Math.min(desiredWidth, View.MeasureSpec.getSize(i));
        } else if (mode2 == 1073741824) {
            desiredWidth = View.MeasureSpec.getSize(i);
        }
        setMeasuredDimension(desiredWidth, desiredHeight);
        calculateDotPositions();
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public final void onPageScrollStateChanged(int i) {
        ViewPager.OnPageChangeListener onPageChangeListener = this.pageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(i);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public final void onPageScrolled(int i, int i2, float f) {
        ViewPager.OnPageChangeListener onPageChangeListener = this.pageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrolled(i, i2, f);
        }
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public final void onPageSelected(int i) {
        if (this.attachedState) {
            setSelectedPage(i);
        } else {
            setCurrentPageImmediate();
        }
        ViewPager.OnPageChangeListener onPageChangeListener = this.pageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageSelected(i);
        }
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        setMeasuredDimension(i, i2);
        calculateDotPositions();
    }

    public final void resetState() {
        int i = this.pageCount;
        if (i > 0) {
            float[] fArr = new float[i - 1];
            this.joiningFractions = fArr;
            Arrays.fill(fArr, 0.0f);
            float[] fArr2 = new float[this.pageCount];
            this.dotRevealFractions = fArr2;
            Arrays.fill(fArr2, 0.0f);
            this.retreatingJoinX1 = -1.0f;
            this.retreatingJoinX2 = -1.0f;
            this.selectedDotInPosition = true;
        }
    }

    public final void setCurrentPageImmediate() {
        ViewPager viewPager = this.viewPager;
        if (viewPager != null) {
            this.currentPage = viewPager.getCurrentItem();
        } else {
            this.currentPage = 0;
        }
        if (this.pageCount > 0) {
            this.selectedDotX = this.dotCenterX[this.currentPage];
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.pageChangeListener = onPageChangeListener;
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        viewPager.setOnPageChangeListener(this);
        setPageCount(viewPager.getAdapter().getCount());
        PagerAdapter adapter = viewPager.getAdapter();
        adapter.mObservable.registerObserver(
                new DataSetObserver() { // from class:
                                        // com.android.settings.widget.DotsPageIndicator.2
                    @Override // android.database.DataSetObserver
                    public final void onChanged() {
                        DotsPageIndicator dotsPageIndicator = DotsPageIndicator.this;
                        dotsPageIndicator.setPageCount(
                                dotsPageIndicator.viewPager.getAdapter().getCount());
                    }
                });
        setCurrentPageImmediate();
    }

    public DotsPageIndicator(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DotsPageIndicator(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        int i2 = (int) context.getResources().getDisplayMetrics().scaledDensity;
        TypedArray obtainStyledAttributes =
                getContext()
                        .obtainStyledAttributes(attributeSet, R$styleable.DotsPageIndicator, i, 0);
        int dimensionPixelSize = obtainStyledAttributes.getDimensionPixelSize(2, i2 * 8);
        this.dotDiameter = dimensionPixelSize;
        float f = dimensionPixelSize / 2;
        this.dotRadius = f;
        this.halfDotRadius = f / 2.0f;
        this.gap = obtainStyledAttributes.getDimensionPixelSize(3, i2 * 12);
        long integer = obtainStyledAttributes.getInteger(0, 400);
        this.animDuration = integer;
        this.animHalfDuration = integer / 2;
        int color = obtainStyledAttributes.getColor(4, -2130706433);
        this.unselectedColour = color;
        int color2 = obtainStyledAttributes.getColor(1, -1);
        this.selectedColour = color2;
        obtainStyledAttributes.recycle();
        Paint paint = new Paint(1);
        this.unselectedPaint = paint;
        paint.setColor(color);
        Paint paint2 = new Paint(1);
        this.selectedPaint = paint2;
        paint2.setColor(color2);
        this.interpolator =
                AnimationUtils.loadInterpolator(context, R.interpolator.fast_out_slow_in);
        this.combinedUnselectedPath = new Path();
        this.unselectedDotPath = new Path();
        this.unselectedDotLeftPath = new Path();
        this.unselectedDotRightPath = new Path();
        this.rectF = new RectF();
        addOnAttachStateChangeListener(
                new View
                        .OnAttachStateChangeListener() { // from class:
                                                         // com.android.settings.widget.DotsPageIndicator.1
                    @Override // android.view.View.OnAttachStateChangeListener
                    public final void onViewAttachedToWindow(View view) {
                        DotsPageIndicator.this.attachedState = true;
                    }

                    @Override // android.view.View.OnAttachStateChangeListener
                    public final void onViewDetachedFromWindow(View view) {
                        DotsPageIndicator.this.attachedState = false;
                    }
                });
    }
}
