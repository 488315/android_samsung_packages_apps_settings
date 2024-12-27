package com.google.android.material.appbar.model;

import android.content.Context;

import com.google.android.material.appbar.model.view.AppBarView;
import com.google.android.material.appbar.model.view.ViewPagerAppBarView;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000(\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0006\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001\u000fB3\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0016\b\u0002\u0010\b\u001a\u0010\u0012\f\u0012\n"
                + "\u0012\u0006\b\u0001\u0012\u00020\n"
                + "0\u00030"
                + "\t¢\u0006\u0002\u0010\u000bJ\u0015\u0010\f\u001a\u00028\u00002\u0006\u0010\r"
                + "\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u000eR\u001c\u0010\b\u001a\u0010\u0012\f\u0012\n"
                + "\u0012\u0006\b\u0001\u0012\u00020\n"
                + "0\u00030\tX\u0082\u0004¢\u0006\u0002\n"
                + "\u0000¨\u0006\u0010"
        },
        d2 = {
            "Lcom/google/android/material/appbar/model/ViewPagerAppBarModel;",
            "T",
            "Lcom/google/android/material/appbar/model/view/ViewPagerAppBarView;",
            "Lcom/google/android/material/appbar/model/AppBarModel;",
            "kclazz",
            "Lkotlin/reflect/KClass;",
            "context",
            "Landroid/content/Context;",
            "appBarModels",
            ApnSettings.MVNO_NONE,
            "Lcom/google/android/material/appbar/model/view/AppBarView;",
            "(Lkotlin/reflect/KClass;Landroid/content/Context;Ljava/util/List;)V",
            "init",
            "moduleView",
            "(Lcom/google/android/material/appbar/model/view/ViewPagerAppBarView;)Lcom/google/android/material/appbar/model/view/ViewPagerAppBarView;",
            "Builder",
            "material_release"
        },
        k = 1,
        mv = {1, 8, 0},
        xi = 48)
/* loaded from: classes3.dex */
public class ViewPagerAppBarModel<T extends ViewPagerAppBarView> extends AppBarModel<T> {
    private final List<AppBarModel<? extends AppBarView>> appBarModels;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Metadata(
            d1 = {
                "\u0000\u0010\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\u0010\u0000\n"
                    + "\u0002\u0018\u0002\n"
                    + "\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0004\b\u0004\u0010\u0005¨\u0006\u0006"
            },
            d2 = {
                "Lcom/google/android/material/appbar/model/ViewPagerAppBarModel$Builder;",
                ApnSettings.MVNO_NONE,
                "Landroid/content/Context;",
                "context",
                "<init>",
                "(Landroid/content/Context;)V",
                "material_release"
            },
            k = 1,
            mv = {1, 8, 0})
    public static final class Builder {
        public Builder(Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            EmptyList emptyList = EmptyList.INSTANCE;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public ViewPagerAppBarModel(
            KClass kclazz,
            Context context,
            List<? extends AppBarModel<? extends AppBarView>> appBarModels) {
        super(kclazz, context);
        Intrinsics.checkNotNullParameter(kclazz, "kclazz");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(appBarModels, "appBarModels");
        this.appBarModels = appBarModels;
    }

    @Override // com.google.android.material.appbar.model.AppBarModel
    public T init(T moduleView) {
        Intrinsics.checkNotNullParameter(moduleView, "moduleView");
        return moduleView;
    }

    public ViewPagerAppBarModel(
            KClass kClass,
            Context context,
            List list,
            int i,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(kClass, context, (i & 4) != 0 ? EmptyList.INSTANCE : list);
    }
}
