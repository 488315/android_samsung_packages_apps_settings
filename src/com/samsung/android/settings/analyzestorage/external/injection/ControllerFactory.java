package com.samsung.android.settings.analyzestorage.external.injection;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.HomeController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.RecommendCardController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.SubFileListController;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;

import java.util.EnumMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ControllerFactory implements ViewModelProvider.Factory {
    public final Application mApplication;
    public final int mInstanceId;
    public ViewModelStoreOwner mOwner;
    public final PageInfo mPageInfo;
    public final int mSaType;

    static {
        EnumMap enumMap = new EnumMap(PageType.class);
        enumMap.put(
                (EnumMap) PageType.ANALYZE_STORAGE_UNUSED_APPS,
                (PageType) new ControllerFactory$$ExternalSyntheticLambda0());
        enumMap.put(
                (EnumMap) PageType.ANALYZE_STORAGE_APP_CACHE,
                (PageType) new ControllerFactory$$ExternalSyntheticLambda0());
    }

    public ControllerFactory(Application application, int i, int i2, PageInfo pageInfo) {
        this(application, i);
        this.mSaType = i2;
        this.mPageInfo = pageInfo;
    }

    @Override // androidx.lifecycle.ViewModelProvider.Factory
    public final ViewModel create(Class cls) {
        boolean isAssignableFrom = cls.isAssignableFrom(AppListController.class);
        int i = this.mSaType;
        PageInfo pageInfo = this.mPageInfo;
        int i2 = this.mInstanceId;
        if (isAssignableFrom) {
            StringBuilder sb = new StringBuilder("create() ] AppListController - mSaType : ");
            sb.append(i);
            sb.append(" , mPageInfo : ");
            sb.append(pageInfo);
            sb.append(" , pageType : ");
            sb.append(pageInfo != null ? pageInfo.mPageType : null);
            sb.append(" , this(factory) : ");
            sb.append(this);
            Log.d("ControllerFactory", sb.toString());
            return new AppListController(this.mApplication, i2, i, pageInfo);
        }
        if (cls.isAssignableFrom(RecommendCardController.class)) {
            return new RecommendCardController(this.mApplication, i2);
        }
        if (cls.isAssignableFrom(OverViewController.class)) {
            return new OverViewController(this.mApplication, i2);
        }
        if (cls.isAssignableFrom(HomeController.class)) {
            return new HomeController(this.mOwner, this.mApplication, i2);
        }
        if (!cls.isAssignableFrom(SubFileListController.class)) {
            throw new IllegalArgumentException("Unknown ViewModel Class: ".concat(cls.getName()));
        }
        StringBuilder sb2 = new StringBuilder("create() ] SubFileListController - mSaType : ");
        sb2.append(i);
        sb2.append(" , mPageInfo : ");
        sb2.append(pageInfo);
        sb2.append(" , pageType : ");
        sb2.append(pageInfo != null ? pageInfo.mPageType : null);
        sb2.append(" , this(factory) : ");
        sb2.append(this);
        Log.d("ControllerFactory", sb2.toString());
        return new SubFileListController(this.mApplication, i2, i, pageInfo);
    }

    public ControllerFactory(Application application, int i) {
        this.mSaType = -1;
        this.mApplication = application;
        this.mInstanceId = i;
    }
}
