package com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.ActivityInfoStore;
import com.samsung.android.settings.analyzestorage.presenter.controllers.RecommendCardController;
import com.samsung.android.settings.analyzestorage.presenter.managers.SamsungAnalyticsConvertManager;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;
import com.samsung.android.settings.logging.LoggingHelper;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class RecommendCardViewPagerAdapter$initAppList$1$1 implements View.OnClickListener {
    public final /* synthetic */ int $index;
    public final /* synthetic */ String $packageName;
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ int $recommendType;
    public final /* synthetic */ RecommendCardViewPagerAdapter this$0;

    public /* synthetic */ RecommendCardViewPagerAdapter$initAppList$1$1(
            int i,
            String str,
            int i2,
            RecommendCardViewPagerAdapter recommendCardViewPagerAdapter,
            int i3) {
        this.$r8$classId = i3;
        this.$index = i;
        this.$packageName = str;
        this.$recommendType = i2;
        this.this$0 = recommendCardViewPagerAdapter;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                RecommendCardController recommendCardController = this.this$0.controller;
                if (recommendCardController != null) {
                    String packageName = this.$packageName;
                    int i = this.$index;
                    Intrinsics.checkNotNullParameter(packageName, "packageName");
                    LoggingHelper.insertEventLogging(
                            PageType.ANALYZE_STORAGE_HOME.getScreenIdForSA(),
                            i != 0 ? i != 1 ? 8949 : 8948 : 8947);
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.parse("package:".concat(packageName)));
                    try {
                        Context context = ActivityInfoStore.context;
                        ActivityResultLauncher activityResultLauncher =
                                ActivityInfoStore.Companion.getInstance(
                                                recommendCardController.mInstanceId)
                                        .getActivityResultLauncher();
                        if (activityResultLauncher != null) {
                            activityResultLauncher.launch(intent);
                        }
                    } catch (ActivityNotFoundException e) {
                        Log.e(
                                recommendCardController.mTag,
                                "ActivityNotFoundException : " + e.getMessage());
                    }
                }
                RecommendCardController recommendCardController2 = this.this$0.controller;
                if (recommendCardController2 != null) {
                    recommendCardController2.removeCard(this.$recommendType);
                    break;
                }
                break;
            case 1:
                Integer convertCardToSAEventId =
                        SamsungAnalyticsConvertManager.convertCardToSAEventId(
                                this.$index,
                                this.$recommendType,
                                this.$packageName.length() == 0,
                                2);
                if (convertCardToSAEventId != null) {
                    LoggingHelper.insertEventLogging(
                            PageType.ANALYZE_STORAGE_HOME.getScreenIdForSA(),
                            convertCardToSAEventId.intValue());
                }
                RecommendCardController recommendCardController3 = this.this$0.controller;
                if (recommendCardController3 != null) {
                    int i2 = this.$index;
                    int i3 = recommendCardController3.mInstanceId;
                    if (i2 != 0 && i2 != 1 && i2 != 2 && i2 != 3 && i2 != 4) {
                        if (i2 == 6) {
                            Intent intent2 =
                                    new Intent("android.intent.action.MANAGE_PACKAGE_STORAGE");
                            Context context2 = ActivityInfoStore.context;
                            ActivityResultLauncher activityResultLauncher2 =
                                    ActivityInfoStore.Companion.getInstance(i3)
                                            .getActivityResultLauncher();
                            if (activityResultLauncher2 != null) {
                                activityResultLauncher2.launch(intent2);
                            }
                        } else if (i2 != 8) {
                            if (i2 == 9) {
                                Intent intent3 =
                                        new Intent(
                                                "com.samsung.android.settings.analyzestorage.ENTER_UNUSED_APPS");
                                intent3.putExtra("instanceId", i3);
                                intent3.putStringArrayListExtra(
                                        "archive_app_package_name_list",
                                        recommendCardController3.archiveAppNameList);
                                Context context3 = ActivityInfoStore.context;
                                ActivityResultLauncher activityResultLauncher3 =
                                        ActivityInfoStore.Companion.getInstance(i3)
                                                .getActivityResultLauncher();
                                if (activityResultLauncher3 != null) {
                                    activityResultLauncher3.launch(intent3);
                                }
                            }
                        }
                    }
                    Intent intent4 =
                            new Intent("com.sec.android.app.myfiles.VIEW_RECOMMENDATION_CARD");
                    intent4.setClassName(
                            "com.sec.android.app.myfiles",
                            "com.sec.android.app.myfiles.ui.AnalyzeStorageActivity");
                    intent4.putExtra("recommendType", i2);
                    intent4.putExtra(
                            "name",
                            (String) recommendCardController3.nameCard.get(Integer.valueOf(i2)));
                    intent4.putExtra(
                            "path",
                            (String) recommendCardController3.pathCard.get(Integer.valueOf(i2)));
                    intent4.putExtra(
                            "bucket_id",
                            (String)
                                    recommendCardController3.bucketIdCard.get(Integer.valueOf(i2)));
                    Context context4 = ActivityInfoStore.context;
                    ActivityResultLauncher activityResultLauncher4 =
                            ActivityInfoStore.Companion.getInstance(i3).getActivityResultLauncher();
                    if (activityResultLauncher4 != null) {
                        activityResultLauncher4.launch(intent4);
                    }
                }
                RecommendCardController recommendCardController4 = this.this$0.controller;
                if (recommendCardController4 != null) {
                    recommendCardController4.removeCard(this.$index);
                    break;
                }
                break;
            default:
                Integer convertCardToSAEventId2 =
                        SamsungAnalyticsConvertManager.convertCardToSAEventId(
                                this.$index,
                                this.$recommendType,
                                this.$packageName.length() == 0,
                                1);
                if (convertCardToSAEventId2 != null) {
                    LoggingHelper.insertEventLogging(
                            PageType.ANALYZE_STORAGE_HOME.getScreenIdForSA(),
                            convertCardToSAEventId2.intValue());
                }
                RecommendCardController recommendCardController5 = this.this$0.controller;
                if (recommendCardController5 != null) {
                    recommendCardController5.removeCard(this.$index);
                }
                this.this$0.disappearAnimator.start();
                break;
        }
    }

    public RecommendCardViewPagerAdapter$initAppList$1$1(
            RecommendCardViewPagerAdapter recommendCardViewPagerAdapter,
            String str,
            int i,
            int i2) {
        this.$r8$classId = 0;
        this.this$0 = recommendCardViewPagerAdapter;
        this.$packageName = str;
        this.$index = i;
        this.$recommendType = i2;
    }
}
