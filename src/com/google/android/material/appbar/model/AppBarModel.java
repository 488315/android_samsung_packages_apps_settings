package com.google.android.material.appbar.model;

import android.content.Context;

import com.google.android.material.appbar.model.view.AppBarView;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;
import kotlin.reflect.KFunction;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u001c\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b"
                + "\t\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003:\u0001\u000eB\u001d\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0004\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\b\u0010"
                + "\tJ\u000f\u0010\n"
                + "\u001a\u00028\u0000H\u0016¢\u0006\u0004\b\n"
                + "\u0010\u000bR\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u00048\u0002X\u0082\u0004¢\u0006\u0006\n"
                + "\u0004\b\u0005\u0010\fR\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004¢\u0006\u0006\n"
                + "\u0004\b\u0007\u0010\r"
                + "¨\u0006\u000f"
        },
        d2 = {
            "Lcom/google/android/material/appbar/model/AppBarModel;",
            "Lcom/google/android/material/appbar/model/view/AppBarView;",
            "T",
            ApnSettings.MVNO_NONE,
            "Lkotlin/reflect/KClass;",
            "kclazz",
            "Landroid/content/Context;",
            "context",
            "<init>",
            "(Lkotlin/reflect/KClass;Landroid/content/Context;)V",
            "create",
            "()Lcom/google/android/material/appbar/model/view/AppBarView;",
            "Lkotlin/reflect/KClass;",
            "Landroid/content/Context;",
            "OnClickListener",
            "material_release"
        },
        k = 1,
        mv = {1, 8, 0})
/* loaded from: classes3.dex */
public class AppBarModel<T extends AppBarView> {
    private final Context context;
    private final KClass kclazz;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\n\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0000\bf\u0018\u00002\u00020\u0001ø\u0001\u0000\u0082\u0002\u0006\n"
                    + "\u0004\b!0\u0001¨\u0006\u0002À\u0006\u0001"
            },
            d2 = {
                "Lcom/google/android/material/appbar/model/AppBarModel$OnClickListener;",
                ApnSettings.MVNO_NONE,
                "material_release"
            },
            k = 1,
            mv = {1, 8, 0})
    public interface OnClickListener {
        void onClick();
    }

    public AppBarModel(KClass kclazz, Context context) {
        Intrinsics.checkNotNullParameter(kclazz, "kclazz");
        Intrinsics.checkNotNullParameter(context, "context");
        this.kclazz = kclazz;
        this.context = context;
    }

    public T create() {
        return (T)
                init(
                        (AppBarView)
                                ((KFunction)
                                                CollectionsKt___CollectionsKt.first(
                                                        this.kclazz.getConstructors()))
                                        .call(this.context, null));
    }

    public AppBarView init(AppBarView moduleView) {
        Intrinsics.checkNotNullParameter(moduleView, "moduleView");
        return moduleView;
    }
}
