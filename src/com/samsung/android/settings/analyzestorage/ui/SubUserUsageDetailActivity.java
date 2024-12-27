package com.samsung.android.settings.analyzestorage.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import com.android.settings.R;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.samsung.android.settings.analyzestorage.presenter.controllers.OverViewController;
import com.samsung.android.settings.analyzestorage.presenter.observer.IContentObserver;
import com.samsung.android.settings.analyzestorage.presenter.page.PageInfo;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;
import com.samsung.android.settings.analyzestorage.presenter.utils.ConfigurationUtils;
import com.samsung.android.settings.analyzestorage.ui.manager.AsUsageInfoManager;
import com.samsung.android.settings.analyzestorage.ui.manager.LayoutWidthManager;
import com.samsung.android.settings.analyzestorage.ui.utils.UiUtils;
import com.samsung.android.settings.analyzestorage.ui.view.analyzestorage.UsageDetailItem;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u0014\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0007¢\u0006\u0004\b\u0004\u0010\u0005¨\u0006\u0006"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/ui/SubUserUsageDetailActivity;",
            "Landroidx/appcompat/app/AppCompatActivity;",
            "Lcom/samsung/android/settings/analyzestorage/ui/manager/LayoutWidthManager$OnWidthChangedListener;",
            "Lcom/samsung/android/settings/analyzestorage/presenter/observer/IContentObserver;",
            "<init>",
            "()V",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class SubUserUsageDetailActivity extends AppCompatActivity
        implements LayoutWidthManager.OnWidthChangedListener, IContentObserver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int instanceId;
    public int layoutState;
    public UsageDetailItem usageDetailItem;
    public final Lazy controller$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.samsung.android.settings.analyzestorage.ui.SubUserUsageDetailActivity$controller$2
                        {
                            super(0);
                        }

                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            SubUserUsageDetailActivity subUserUsageDetailActivity =
                                    SubUserUsageDetailActivity.this;
                            int i = SubUserUsageDetailActivity.$r8$clinit;
                            subUserUsageDetailActivity.getClass();
                            return new OverViewController(
                                    subUserUsageDetailActivity.getApplication(),
                                    subUserUsageDetailActivity.instanceId);
                        }
                    });
    public final PageInfo pageInfo = new PageInfo(PageType.ANALYZE_STORAGE_SUB_USER_USAGE_DETAIL);
    public final Lazy layoutWidthManager$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.samsung.android.settings.analyzestorage.ui.SubUserUsageDetailActivity$layoutWidthManager$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            LayoutWidthManager.Companion companion = LayoutWidthManager.Companion;
                            LayoutWidthManager layoutWidthManager = LayoutWidthManager.instance;
                            if (layoutWidthManager == null) {
                                synchronized (companion) {
                                    layoutWidthManager = new LayoutWidthManager();
                                    LayoutWidthManager.instance = layoutWidthManager;
                                }
                            }
                            return layoutWidthManager;
                        }
                    });

    public final OverViewController getController() {
        return (OverViewController) this.controller$delegate.getValue();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration newConfig) {
        Intrinsics.checkNotNullParameter(newConfig, "newConfig");
        super.onConfigurationChanged(newConfig);
        Lazy lazy = this.layoutWidthManager$delegate;
        this.layoutState = ((LayoutWidthManager) lazy.getValue()).contentWidthDp >= 589 ? 1 : 0;
        ((LayoutWidthManager) lazy.getValue()).setContentWidthDp(newConfig.screenWidthDp, true);
        View requireViewById = requireViewById(R.id.as_usage_detail_item);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
        UiUtils.setFlexibleSpacing(requireViewById);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity,
              // android.view.Window.Callback
    public final void onContentChanged() {
        getController().updateUsageInfo(true, false);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.as_sub_user_usage_detail_activity);
        this.instanceId = getIntent().getIntExtra("instanceId", -1);
        int intExtra = getIntent().getIntExtra("domainType", 0);
        PageInfo pageInfo = this.pageInfo;
        pageInfo.mExtra.putInt("from_usage_type", intExtra);
        getController().onCreate(pageInfo);
        getController().updateUsageInfo(false, false);
        getController().checkSupportedStorageList();
        View requireViewById = requireViewById(R.id.toolbar);
        Intrinsics.checkNotNullExpressionValue(requireViewById, "requireViewById(...)");
        setSupportActionBar((Toolbar) requireViewById);
        String string = getString(R.string.as_app_title);
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeButtonEnabled();
            supportActionBar.setDisplayShowTitleEnabled(true);
            supportActionBar.setTitle(string);
            supportActionBar.setBackgroundDrawable(null);
        }
        View requireViewById2 = requireViewById(R.id.collapsing_app_bar);
        Intrinsics.checkNotNullExpressionValue(requireViewById2, "requireViewById(...)");
        ((CollapsingToolbarLayout) requireViewById2).setTitle(string);
        Lazy lazy = this.layoutWidthManager$delegate;
        LayoutWidthManager layoutWidthManager = (LayoutWidthManager) lazy.getValue();
        layoutWidthManager.getClass();
        if (!((ArrayList) layoutWidthManager.listenerList).contains(this)) {
            ((ArrayList) layoutWidthManager.listenerList).add(this);
        }
        this.layoutState = ((LayoutWidthManager) lazy.getValue()).contentWidthDp >= 589 ? 1 : 0;
        this.usageDetailItem = (UsageDetailItem) requireViewById(R.id.as_usage_detail_item);
        int i = pageInfo.mExtra.getInt("from_usage_type", -1);
        AsUsageInfoManager.Companion companion = AsUsageInfoManager.Companion;
        Context baseContext = getBaseContext();
        Intrinsics.checkNotNullExpressionValue(baseContext, "getBaseContext(...)");
        List usageInfoList = companion.getInstance(baseContext).getUsageInfoList(i);
        UsageDetailItem usageDetailItem = this.usageDetailItem;
        if (usageDetailItem != null) {
            usageDetailItem.setInitItem(
                    this,
                    usageInfoList,
                    i,
                    this.layoutState == 1,
                    getController(),
                    ConfigurationUtils.isInRTLMode(getBaseContext()),
                    true);
        }
        getController()
                .getUsageDetailData(i)
                .observe(
                        this,
                        new Observer() { // from class:
                                         // com.samsung.android.settings.analyzestorage.ui.SubUserUsageDetailActivity$bindDataObserver$1
                            @Override // androidx.lifecycle.Observer
                            public final void onChanged(Object obj) {
                                UsageDetailItem usageDetailItem2;
                                ArrayList arrayList = (ArrayList) obj;
                                if (arrayList != null) {
                                    SubUserUsageDetailActivity subUserUsageDetailActivity =
                                            SubUserUsageDetailActivity.this;
                                    if (subUserUsageDetailActivity
                                                            .getController()
                                                            .mStorageUsedSize
                                                            .getValue()
                                                    == null
                                            || (usageDetailItem2 =
                                                            subUserUsageDetailActivity
                                                                    .usageDetailItem)
                                                    == null) {
                                        return;
                                    }
                                    usageDetailItem2.updateSize(arrayList);
                                }
                            }
                        });
        getController().updateUsageInfo(true, false);
        View requireViewById3 = requireViewById(R.id.as_usage_detail_item);
        Intrinsics.checkNotNullExpressionValue(requireViewById3, "requireViewById(...)");
        UiUtils.setFlexibleSpacing(requireViewById3);
        UiUtils.initStatusBar(this);
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity,
              // android.app.Activity
    public final void onDestroy() {
        LayoutWidthManager layoutWidthManager =
                (LayoutWidthManager) this.layoutWidthManager$delegate.getValue();
        layoutWidthManager.getClass();
        ((ArrayList) layoutWidthManager.listenerList).remove(this);
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() != 16908332) {
            return super.onOptionsItemSelected(item);
        }
        getOnBackPressedDispatcher().onBackPressed();
        return true;
    }

    @Override // com.samsung.android.settings.analyzestorage.ui.manager.LayoutWidthManager.OnWidthChangedListener
    public final void onWidthChanged() {
        int i =
                ((LayoutWidthManager) this.layoutWidthManager$delegate.getValue()).contentWidthDp
                                >= 589
                        ? 1
                        : 0;
        this.layoutState = i;
        UsageDetailItem usageDetailItem = this.usageDetailItem;
        if (usageDetailItem != null) {
            usageDetailItem.updateColumnCount(i == 1);
        }
    }
}
