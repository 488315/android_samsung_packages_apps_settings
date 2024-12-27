package com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.lifecycle.Observer;

import com.android.settings.R;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AbsPageController;
import com.samsung.android.settings.analyzestorage.presenter.controllers.AppListController;
import com.samsung.android.settings.analyzestorage.presenter.utils.StringConverter;

import kotlin.jvm.internal.Intrinsics;

import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class AnalyzeStorageSubList$observeList$1 implements Observer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AnalyzeStorageSubList this$0;

    public /* synthetic */ AnalyzeStorageSubList$observeList$1(
            AnalyzeStorageSubList analyzeStorageSubList, int i) {
        this.$r8$classId = i;
        this.this$0 = analyzeStorageSubList;
    }

    @Override // androidx.lifecycle.Observer
    public final void onChanged(Object obj) {
        AnalyzeStorageSubList analyzeStorageSubList = this.this$0;
        switch (this.$r8$classId) {
            case 0:
                Log.d(
                        analyzeStorageSubList.logTag,
                        "getListItemData()] isEmpty : "
                                + ((List) obj)
                                + ".isNullOrEmpty(), type : "
                                + analyzeStorageSubList.type);
                break;
            case 1:
                Boolean bool = (Boolean) obj;
                TextView textView = analyzeStorageSubList.headerSize;
                if (textView != null) {
                    textView.setVisibility(bool.booleanValue() ^ true ? 0 : 8);
                }
                Intrinsics.checkNotNull(bool);
                if (!bool.booleanValue()) {
                    Job job = analyzeStorageSubList.delayJob;
                    if (job != null) {
                        job.cancel(null);
                    }
                    analyzeStorageSubList.delayJob = null;
                    ProgressBar progressBar = analyzeStorageSubList.progressBar;
                    if (progressBar != null) {
                        progressBar.setVisibility(8);
                        break;
                    }
                } else {
                    if (analyzeStorageSubList.duringLoading) {
                        AbsPageController absPageController = analyzeStorageSubList.controller;
                        AppListController appListController =
                                absPageController instanceof AppListController
                                        ? (AppListController) absPageController
                                        : null;
                        if (appListController == null || !appListController.mNeedLoading) {}
                    }
                    analyzeStorageSubList.duringLoading = true;
                    if (analyzeStorageSubList.delayJob == null) {
                        DefaultScheduler defaultScheduler = Dispatchers.Default;
                        analyzeStorageSubList.delayJob =
                                BuildersKt.launch$default(
                                        CoroutineScopeKt.CoroutineScope(
                                                MainDispatcherLoader.dispatcher),
                                        null,
                                        null,
                                        new AnalyzeStorageSubList$observeLoadingState$1$1(
                                                analyzeStorageSubList, null),
                                        3);
                        break;
                    }
                }
                break;
            case 2:
                Bundle bundle = (Bundle) obj;
                long j = bundle.getLong("largeSize") / 1000000;
                analyzeStorageSubList.getClass();
                String m =
                        (j == 25 || j == 100 || j == 500 || bundle.getInt("largeUnit") == 0)
                                ? AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                        String.format(
                                                Locale.getDefault(),
                                                "%d",
                                                Arrays.copyOf(
                                                        new Object[] {Integer.valueOf((int) j)},
                                                        1)),
                                        analyzeStorageSubList.context.getString(
                                                R.string.megabyteShort))
                                : AbstractResolvableFuture$$ExternalSyntheticOutline0.m(
                                        String.format(
                                                Locale.getDefault(),
                                                "%d",
                                                Arrays.copyOf(
                                                        new Object[] {
                                                            Integer.valueOf((int) (j / 1000))
                                                        },
                                                        1)),
                                        analyzeStorageSubList.context.getString(
                                                R.string.gigabyteShort));
                View view = analyzeStorageSubList.rootView;
                TextView textView2 =
                        view != null ? (TextView) view.findViewById(R.id.sub_text) : null;
                if (textView2 != null) {
                    textView2.setText(
                            analyzeStorageSubList.context.getString(
                                    R.string.as_large_files_sub_text, m));
                    break;
                }
                break;
            default:
                Long l = (Long) obj;
                if (l != null) {
                    long longValue = l.longValue();
                    TextView textView3 = analyzeStorageSubList.headerSize;
                    if (textView3 != null) {
                        textView3.setText(
                                StringConverter.formatFileSize(
                                        0, longValue, analyzeStorageSubList.context));
                    }
                    analyzeStorageSubList.sizeInfo.supportSize.setValue(Boolean.TRUE);
                    break;
                }
                break;
        }
    }
}
