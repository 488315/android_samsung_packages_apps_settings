package com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.DisplayInfo;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import com.android.systemui.biometrics.UdfpsUtils;
import com.android.systemui.biometrics.shared.model.FingerprintSensorType;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000,\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001:\u0001\u0011B\u0019\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0006\u0010\u0007J\u0015\u0010\u000b\u001a\u00020\n"
                + "2\u0006\u0010"
                + "\t\u001a\u00020\b¢\u0006\u0004\b\u000b\u0010\fJ\u001b\u0010\u000f\u001a\u00020\n"
                + "2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\n"
                + "0\r"
                + "¢\u0006\u0004\b\u000f\u0010\u0010¨\u0006\u0012"
        },
        d2 = {
            "Lcom/android/settings/biometrics/fingerprint2/ui/enrollment/modules/enrolling/udfps/ui/widget/UdfpsEnrollViewV2;",
            "Landroid/widget/FrameLayout;",
            "Landroid/content/Context;",
            "context",
            "Landroid/util/AttributeSet;",
            "attrs",
            "<init>",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            ApnSettings.MVNO_NONE,
            "enabled",
            ApnSettings.MVNO_NONE,
            "setAccessibilityEnabled",
            "(Z)V",
            "Lkotlin/Function0;",
            "onFinishedAnimation",
            "setFinishAnimationCompleted",
            "(Lkotlin/jvm/functions/Function0;)V",
            "TouchExplorationAnnouncer",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes2.dex */
public final class UdfpsEnrollViewV2 extends FrameLayout {
    public final UdfpsEnrollIconV2 fingerprintIcon;
    public final UdfpsEnrollProgressBarDrawableV2 fingerprintProgressDrawable;
    public FingerprintSensorType fingerprintSensorType;
    public Function0 onFinishedCompletionAnimation;
    public Rect sensorRect;
    public final TouchExplorationAnnouncer touchExplorationAnnouncer;
    public final UdfpsUtils udfpsUtils;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class TouchExplorationAnnouncer {
        public final Context context;
        public final UdfpsOverlayParams overlayParams;
        public final UdfpsUtils udfpsUtils;
        public final View view;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UdfpsEnrollViewV2(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        Context mContext = ((FrameLayout) this).mContext;
        Intrinsics.checkNotNullExpressionValue(mContext, "mContext");
        new UdfpsEnrollIconV2(mContext, attributeSet);
        Context mContext2 = ((FrameLayout) this).mContext;
        Intrinsics.checkNotNullExpressionValue(mContext2, "mContext");
        this.fingerprintProgressDrawable =
                new UdfpsEnrollProgressBarDrawableV2(mContext2, attributeSet);
        this.udfpsUtils = new UdfpsUtils();
        new Function0() { // from class:
                          // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.widget.UdfpsEnrollViewV2.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            /* renamed from: invoke */
            public final Object mo1068invoke() {
                Function0 function0 = UdfpsEnrollViewV2.this.onFinishedCompletionAnimation;
                if (function0 != null) {
                    function0.mo1068invoke();
                }
                return Unit.INSTANCE;
            }
        };
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.sensorRect == null || this.fingerprintSensorType == null) {
            return;
        }
        Intrinsics.checkNotNullParameter(null, "rect");
        Intrinsics.checkNotNullParameter(null, "sensorType");
        this.sensorRect = null;
        this.fingerprintSensorType = null;
        DisplayInfo displayInfo = new DisplayInfo();
        getContext().getDisplay().getDisplayInfo(displayInfo);
        int i5 = displayInfo.rotation;
        this.udfpsUtils.getClass();
        UdfpsUtils.getScaleFactor(displayInfo);
        ViewParent parent = getParent();
        Intrinsics.checkNotNull(
                parent, "null cannot be cast to non-null type android.view.ViewGroup");
        int[] locationOnScreen = ((ViewGroup) parent).getLocationOnScreen();
        int i6 = locationOnScreen[0];
        int i7 = locationOnScreen[1];
        Rect rect = this.sensorRect;
        if (rect == null) {
            Intrinsics.throwUninitializedPropertyAccessException("sensorRect");
            throw null;
        }
        Rect rect2 = new Rect(rect);
        if (i5 == 1) {
            rect2.set(rect2.top, rect2.left, rect2.bottom, rect2.right);
            rect2.offset(-i6, -i7);
        } else if (i5 != 3) {
            rect2.offset(-i6, -i7);
        } else {
            rect2.set(
                    (displayInfo.getNaturalHeight() - rect2.bottom) - i6,
                    rect2.left - i7,
                    (displayInfo.getNaturalHeight() - rect2.top) - i6,
                    rect2.right - i7);
        }
        Intrinsics.checkNotNullExpressionValue(
                this.fingerprintProgressDrawable.getBounds(), "getBounds(...)");
        displayInfo.getNaturalWidth();
        displayInfo.getNaturalHeight();
        throw null;
    }

    public final void setAccessibilityEnabled(boolean enabled) {
        this.fingerprintProgressDrawable.isAccessibilityEnabled = enabled;
        if (enabled) {
            setOnHoverListener(
                    new View
                            .OnHoverListener() { // from class:
                                                 // com.android.settings.biometrics.fingerprint2.ui.enrollment.modules.enrolling.udfps.ui.widget.UdfpsEnrollViewV2$addHoverListener$1
                        @Override // android.view.View.OnHoverListener
                        public final boolean onHover(View view, MotionEvent event) {
                            Intrinsics.checkNotNullParameter(view, "<anonymous parameter 0>");
                            Intrinsics.checkNotNullParameter(event, "event");
                            UdfpsEnrollViewV2.TouchExplorationAnnouncer touchExplorationAnnouncer =
                                    UdfpsEnrollViewV2.this.touchExplorationAnnouncer;
                            Intrinsics.throwUninitializedPropertyAccessException(
                                    "touchExplorationAnnouncer");
                            throw null;
                        }
                    });
        } else {
            setOnHoverListener(UdfpsEnrollViewV2$clearHoverListener$listener$1.INSTANCE);
        }
    }

    public final void setFinishAnimationCompleted(Function0 onFinishedAnimation) {
        Intrinsics.checkNotNullParameter(onFinishedAnimation, "onFinishedAnimation");
        this.onFinishedCompletionAnimation = onFinishedAnimation;
    }
}
