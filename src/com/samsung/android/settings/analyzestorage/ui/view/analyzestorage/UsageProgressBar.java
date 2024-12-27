package com.samsung.android.settings.analyzestorage.ui.view.analyzestorage;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.android.settings.R;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0018\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0013\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002¢\u0006\u0004\b\u0004\u0010\u0005B\u001d\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\u0004\u0010\b¨\u0006"
                + "\t"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/ui/view/analyzestorage/UsageProgressBar;",
            "Landroid/widget/LinearLayout;",
            "Landroid/content/Context;",
            "context",
            "<init>",
            "(Landroid/content/Context;)V",
            "Landroid/util/AttributeSet;",
            "attrs",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class UsageProgressBar extends LinearLayout {
    public final Lazy innerProgress$delegate;
    public float progress;
    public final ValueAnimator progressAnimator;
    public final LinearLayout.LayoutParams progressLayoutParams;
    public long total;
    public long used;
    public float weightSum;

    public UsageProgressBar(Context context) {
        super(context);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        Intrinsics.checkNotNullExpressionValue(ofFloat, "ofFloat(...)");
        this.progressAnimator = ofFloat;
        this.progressLayoutParams = new LinearLayout.LayoutParams(0, -1);
        this.innerProgress$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.view.analyzestorage.UsageProgressBar$innerProgress$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById =
                                        UsageProgressBar.this.requireViewById(R.id.inner_progress);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById, "requireViewById(...)");
                                return (LinearLayout) requireViewById;
                            }
                        });
    }

    public final LinearLayout getInnerProgress() {
        return (LinearLayout) this.innerProgress$delegate.getValue();
    }

    public UsageProgressBar(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        Intrinsics.checkNotNullExpressionValue(ofFloat, "ofFloat(...)");
        this.progressAnimator = ofFloat;
        this.progressLayoutParams = new LinearLayout.LayoutParams(0, -1);
        this.innerProgress$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.samsung.android.settings.analyzestorage.ui.view.analyzestorage.UsageProgressBar$innerProgress$2
                            {
                                super(0);
                            }

                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                View requireViewById =
                                        UsageProgressBar.this.requireViewById(R.id.inner_progress);
                                Intrinsics.checkNotNullExpressionValue(
                                        requireViewById, "requireViewById(...)");
                                return (LinearLayout) requireViewById;
                            }
                        });
    }
}
