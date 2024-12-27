package com.samsung.android.settings.suggestionappbar;

import android.content.Context;

import com.google.android.material.appbar.model.AppBarModel;
import com.google.android.material.appbar.model.SuggestAppBarModel;
import com.google.android.material.appbar.model.ViewPagerAppBarModel;
import com.google.android.material.appbar.model.view.AppBarView;
import com.google.android.material.appbar.model.view.SuggestAppBarItemView;
import com.samsung.android.knox.net.apn.ApnSettings;

import kotlin.Metadata;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.TypeIntrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000H\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010 \n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\n"
                + "\u0002\u0010\b\n"
                + "\u0002\b\u0003\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0004\n"
                + "\u0002\u0010\u000b\n"
                + "\u0000\n"
                + "\u0002\u0010\u0002\n"
                + "\u0000\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0000\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B-\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0014\b\u0002\u0010\u0005\u001a\u000e\u0012\n"
                + "\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u0012\b\b\u0002\u0010"
                + "\t\u001a\u00020\n"
                + "¢\u0006\u0002\u0010\u000bJ\u0006\u0010\u000f\u001a\u00020\n"
                + "J\u0010\u0010\u0010\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u0002H\u0016J\u0006\u0010\u0012\u001a\u00020\u0013J\u0016\u0010\u0014\u001a\u00020\u00152\u000e\u0010\u0016\u001a\n"
                + "\u0012\u0006\b\u0001\u0012\u00020\u00180\u0017R\u001a\u0010\u0005\u001a\u000e\u0012\n"
                + "\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\f\u001a\u00020\u0002X\u0082.¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\t\u001a\u00020\n"
                + "X\u0082\u0004¢\u0006\u0002\n"
                + "\u0000R\u000e\u0010\r"
                + "\u001a\u00020\u000eX\u0082.¢\u0006\u0002\n"
                + "\u0000¨\u0006\u0019"
        },
        d2 = {
            "Lcom/samsung/android/settings/suggestionappbar/SecViewPagerAppBarModel;",
            "Lcom/google/android/material/appbar/model/ViewPagerAppBarModel;",
            "Lcom/samsung/android/settings/suggestionappbar/SecViewPagerAppBarView;",
            "context",
            "Landroid/content/Context;",
            "appBarModels",
            ApnSettings.MVNO_NONE,
            "Lcom/google/android/material/appbar/model/SuggestAppBarModel;",
            "Lcom/google/android/material/appbar/model/view/SuggestAppBarItemView;",
            "defaultPosition",
            ApnSettings.MVNO_NONE,
            "(Landroid/content/Context;Ljava/util/List;I)V",
            "appBarView",
            "viewPagerAdapter",
            "Lcom/samsung/android/settings/suggestionappbar/SecViewPagerAdapter;",
            "getDefaultPosition",
            "init",
            "moduleView",
            "isEmpty",
            ApnSettings.MVNO_NONE,
            "removeSuggestItemView",
            ApnSettings.MVNO_NONE,
            "appBarModel",
            "Lcom/google/android/material/appbar/model/AppBarModel;",
            "Lcom/google/android/material/appbar/model/view/AppBarView;",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0},
        xi = 48)
/* loaded from: classes4.dex */
public final class SecViewPagerAppBarModel extends ViewPagerAppBarModel<SecViewPagerAppBarView> {
    public static final int $stable = 8;
    private final List<SuggestAppBarModel<SuggestAppBarItemView>> appBarModels;
    private SecViewPagerAppBarView appBarView;
    private final int defaultPosition;
    private SecViewPagerAdapter viewPagerAdapter;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public SecViewPagerAppBarModel(
            Context context,
            List<? extends SuggestAppBarModel<SuggestAppBarItemView>> appBarModels,
            int i) {
        super(
                Reflection.factory.getOrCreateKotlinClass(SecViewPagerAppBarView.class),
                context,
                appBarModels);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(appBarModels, "appBarModels");
        this.appBarModels = appBarModels;
        this.defaultPosition = i;
    }

    public final int getDefaultPosition() {
        SecViewPagerAppBarView secViewPagerAppBarView = this.appBarView;
        if (secViewPagerAppBarView != null) {
            return secViewPagerAppBarView.getDefaultPosition();
        }
        Intrinsics.throwUninitializedPropertyAccessException("appBarView");
        throw null;
    }

    public final boolean isEmpty() {
        SecViewPagerAdapter secViewPagerAdapter = this.viewPagerAdapter;
        if (secViewPagerAdapter != null) {
            return secViewPagerAdapter.getItemCount() == 0;
        }
        Intrinsics.throwUninitializedPropertyAccessException("viewPagerAdapter");
        throw null;
    }

    public final void removeSuggestItemView(AppBarModel<? extends AppBarView> appBarModel) {
        Intrinsics.checkNotNullParameter(appBarModel, "appBarModel");
        SecViewPagerAdapter secViewPagerAdapter = this.viewPagerAdapter;
        if (secViewPagerAdapter == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPagerAdapter");
            throw null;
        }
        List list = secViewPagerAdapter.data;
        Intrinsics.checkNotNullParameter(list, "<this>");
        int indexOf = ((ArrayList) list).indexOf(appBarModel);
        SecViewPagerAdapter secViewPagerAdapter2 = this.viewPagerAdapter;
        if (secViewPagerAdapter2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("viewPagerAdapter");
            throw null;
        }
        TypeIntrinsics.asMutableCollection(secViewPagerAdapter2.data).remove(appBarModel);
        secViewPagerAdapter2.notifyDataSetChanged();
        SecViewPagerAppBarView secViewPagerAppBarView = this.appBarView;
        if (secViewPagerAppBarView != null) {
            secViewPagerAppBarView.removeIndicator(indexOf);
        } else {
            Intrinsics.throwUninitializedPropertyAccessException("appBarView");
            throw null;
        }
    }

    @Override // com.google.android.material.appbar.model.ViewPagerAppBarModel
    public SecViewPagerAppBarView init(SecViewPagerAppBarView moduleView) {
        Intrinsics.checkNotNullParameter(moduleView, "moduleView");
        Context context = moduleView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        SecViewPagerAdapter secViewPagerAdapter = new SecViewPagerAdapter(context);
        List<SuggestAppBarModel<SuggestAppBarItemView>> dataModel = this.appBarModels;
        Intrinsics.checkNotNullParameter(dataModel, "dataModel");
        ((ArrayList) secViewPagerAdapter.data).clear();
        ((ArrayList) secViewPagerAdapter.data).addAll(dataModel);
        secViewPagerAdapter.notifyDataSetChanged();
        this.viewPagerAdapter = secViewPagerAdapter;
        SecViewPagerAppBarView secViewPagerAppBarView =
                (SecViewPagerAppBarView) super.init((SecViewPagerAppBarModel) moduleView);
        SecViewPagerAdapter secViewPagerAdapter2 = this.viewPagerAdapter;
        if (secViewPagerAdapter2 != null) {
            secViewPagerAppBarView.setAdapter(secViewPagerAdapter2);
            secViewPagerAppBarView.initIndicator(this.appBarModels.size(), this.defaultPosition);
            this.appBarView = secViewPagerAppBarView;
            if (secViewPagerAppBarView != null) {
                return secViewPagerAppBarView;
            }
            Intrinsics.throwUninitializedPropertyAccessException("appBarView");
            throw null;
        }
        Intrinsics.throwUninitializedPropertyAccessException("viewPagerAdapter");
        throw null;
    }

    public SecViewPagerAppBarModel(
            Context context,
            List list,
            int i,
            int i2,
            DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? EmptyList.INSTANCE : list, (i2 & 4) != 0 ? 0 : i);
    }
}
