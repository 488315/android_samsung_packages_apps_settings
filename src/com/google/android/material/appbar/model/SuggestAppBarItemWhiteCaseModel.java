package com.google.android.material.appbar.model;

import android.content.Context;

import com.google.android.material.appbar.model.view.SuggestAppBarItemWhiteCaseView;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

import kotlin.Metadata;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u00000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\u000e\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0006\b\u0017\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001\u0012B7\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00010"
                + "\t\u0012\b\u0010\n"
                + "\u001a\u0004\u0018\u00010\u000b\u0012\u0006\u0010\f\u001a\u00020\r"
                + "¢\u0006\u0002\u0010\u000eJ\u0015\u0010\u000f\u001a\u00028\u00002\u0006\u0010\u0010\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0011¨\u0006\u0013"
        },
        d2 = {
            "Lcom/google/android/material/appbar/model/SuggestAppBarItemWhiteCaseModel;",
            "T",
            "Lcom/google/android/material/appbar/model/view/SuggestAppBarItemWhiteCaseView;",
            "Lcom/google/android/material/appbar/model/SuggestAppBarItemModel;",
            "kclazz",
            "Lkotlin/reflect/KClass;",
            "context",
            "Landroid/content/Context;",
            UniversalCredentialUtil.AGENT_TITLE,
            ApnSettings.MVNO_NONE,
            "closeClickListener",
            "Lcom/google/android/material/appbar/model/AppBarModel$OnClickListener;",
            "buttonListModel",
            "Lcom/google/android/material/appbar/model/ButtonListModel;",
            "(Lkotlin/reflect/KClass;Landroid/content/Context;Ljava/lang/String;Lcom/google/android/material/appbar/model/AppBarModel$OnClickListener;Lcom/google/android/material/appbar/model/ButtonListModel;)V",
            "init",
            "moduleView",
            "(Lcom/google/android/material/appbar/model/view/SuggestAppBarItemWhiteCaseView;)Lcom/google/android/material/appbar/model/view/SuggestAppBarItemWhiteCaseView;",
            "Builder",
            "material_release"
        },
        k = 1,
        mv = {1, 8, 0},
        xi = 48)
/* loaded from: classes3.dex */
public class SuggestAppBarItemWhiteCaseModel<T extends SuggestAppBarItemWhiteCaseView>
        extends SuggestAppBarItemModel<T> {

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
                "Lcom/google/android/material/appbar/model/SuggestAppBarItemWhiteCaseModel$Builder;",
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
    public SuggestAppBarItemWhiteCaseModel(
            KClass kclazz,
            Context context,
            String str,
            AppBarModel.OnClickListener onClickListener,
            ButtonListModel buttonListModel) {
        super(kclazz, context, str, onClickListener, buttonListModel);
        Intrinsics.checkNotNullParameter(kclazz, "kclazz");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(buttonListModel, "buttonListModel");
    }

    @Override // com.google.android.material.appbar.model.SuggestAppBarItemModel,
              // com.google.android.material.appbar.model.SuggestAppBarModel
    public T init(T moduleView) {
        Intrinsics.checkNotNullParameter(moduleView, "moduleView");
        moduleView.setModel(this);
        moduleView.setTitle(getTitle());
        moduleView.setCloseClickListener(getCloseClickListener());
        moduleView.setButtonModules(getButtonListModel());
        Context context = moduleView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "context");
        moduleView.updateResource(context);
        return moduleView;
    }
}
