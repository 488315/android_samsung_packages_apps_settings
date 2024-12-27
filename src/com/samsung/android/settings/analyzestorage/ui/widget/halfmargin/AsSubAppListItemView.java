package com.samsung.android.settings.analyzestorage.ui.widget.halfmargin;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0002\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005B\u001b\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006¢\u0006\u0004\b\u0004\u0010\bB#\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\n"
                + "\u001a\u00020\t¢\u0006\u0004\b\u0004\u0010\u000b¨\u0006\f"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/ui/widget/halfmargin/AsSubAppListItemView;",
            "Landroidx/constraintlayout/widget/ConstraintLayout;",
            "Landroid/content/Context;",
            "context",
            "<init>",
            "(Landroid/content/Context;)V",
            "Landroid/util/AttributeSet;",
            "attrs",
            "(Landroid/content/Context;Landroid/util/AttributeSet;)V",
            ApnSettings.MVNO_NONE,
            "defStyleAttr",
            "(Landroid/content/Context;Landroid/util/AttributeSet;I)V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class AsSubAppListItemView extends ConstraintLayout {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AsSubAppListItemView(Context context) {
        super(context);
        Intrinsics.checkNotNullParameter(context, "context");
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$checkBox$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.checkbox);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$appIcon$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.app_icon);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$mainText$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.main_text);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$subText$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.sub_text);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$divider$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.divider);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$verticalDivider$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.vertical_divider);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$appInfoIcon$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return (ImageView)
                                AsSubAppListItemView.this.findViewById(R.id.app_info_icon);
                    }
                });
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AsSubAppListItemView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$checkBox$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.checkbox);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$appIcon$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.app_icon);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$mainText$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.main_text);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$subText$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.sub_text);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$divider$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.divider);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$verticalDivider$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.vertical_divider);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$appInfoIcon$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return (ImageView)
                                AsSubAppListItemView.this.findViewById(R.id.app_info_icon);
                    }
                });
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AsSubAppListItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$checkBox$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.checkbox);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$appIcon$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.app_icon);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$mainText$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.main_text);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$subText$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.sub_text);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$divider$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.divider);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$verticalDivider$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return AsSubAppListItemView.this.findViewById(R.id.vertical_divider);
                    }
                });
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.samsung.android.settings.analyzestorage.ui.widget.halfmargin.AsSubAppListItemView$appInfoIcon$2
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return (ImageView)
                                AsSubAppListItemView.this.findViewById(R.id.app_info_icon);
                    }
                });
    }
}
