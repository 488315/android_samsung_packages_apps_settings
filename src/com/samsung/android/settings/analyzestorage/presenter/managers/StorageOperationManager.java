package com.samsung.android.settings.analyzestorage.presenter.managers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.R;

import com.samsung.android.settings.analyzestorage.data.constant.DomainType;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.domain.thread.ThreadExecutor;
import com.samsung.android.settings.analyzestorage.presenter.page.PageType;
import com.samsung.android.settings.analyzestorage.ui.pages.adapter.analyzestorage.ViewPagerAdapter;
import com.samsung.android.settings.analyzestorage.ui.pages.analyzestorage.OverView;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class StorageOperationManager {
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final HashSet mUpdateListenerSet = new HashSet();
    public final HashSet mOperationList = new HashSet();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class StorageOperationManagerHolder {
        public static final StorageOperationManager INSTANCE = new StorageOperationManager();
    }

    public final void onSuccess(final Context context, final int i, final int i2) {
        this.mOperationList.add(Integer.valueOf(i));
        Handler handler = this.mHandler;
        if (i2 == 2) {
            handler.postDelayed(
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.analyzestorage.presenter.managers.StorageOperationManager$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            StorageOperationManager.this.showToast(context, i, 2);
                        }
                    },
                    100L);
        } else {
            handler.post(
                    new Runnable() { // from class:
                                     // com.samsung.android.settings.analyzestorage.presenter.managers.StorageOperationManager$$ExternalSyntheticLambda2
                        @Override // java.lang.Runnable
                        public final void run() {
                            StorageOperationManager storageOperationManager =
                                    StorageOperationManager.this;
                            final int i3 = i2;
                            final int i4 = i;
                            storageOperationManager.mUpdateListenerSet.forEach(
                                    new Consumer(
                                            i3,
                                            i4) { // from class:
                                                  // com.samsung.android.settings.analyzestorage.presenter.managers.StorageOperationManager$$ExternalSyntheticLambda3
                                        public final /* synthetic */ int f$0;

                                        @Override // java.util.function.Consumer
                                        public final void accept(Object obj) {
                                            int i5 = this.f$0;
                                            ViewPagerAdapter viewPagerAdapter =
                                                    ((OverView) obj).adapter;
                                            if (viewPagerAdapter != null) {
                                                if (i5 == 0) {
                                                    viewPagerAdapter.updateMountState(
                                                            Integer.valueOf(R.string.as_mounting),
                                                            true,
                                                            false);
                                                } else {
                                                    if (i5 != 1) {
                                                        return;
                                                    }
                                                    viewPagerAdapter.updateMountVisibility(true);
                                                    viewPagerAdapter.updateMountState(
                                                            Integer.valueOf(R.string.as_unmounting),
                                                            true,
                                                            false);
                                                }
                                            }
                                        }
                                    });
                        }
                    });
        }
    }

    public final void showToast(Context context, int i, int i2) {
        if (this.mOperationList.contains(Integer.valueOf(i))) {
            this.mOperationList.remove(Integer.valueOf(i));
            if (DomainType.isSd(i)) {
                Preference$$ExternalSyntheticOutline0.m(
                        context,
                        i2 != 0
                                ? i2 != 1
                                        ? i2 != 2 ? -1 : R.string.storage_formatted
                                        : R.string.storage_unmounted
                                : R.string.storage_mounted,
                        new Object[] {context.getString(R.string.as_sd_card)},
                        context,
                        0);
                return;
            }
        }
        Log.d(
                "StorageOperationManager",
                "showToast() ] There is no valid operation for DomainType : " + i);
    }

    public final void storageOperation(Context context, int i, int i2) {
        int i3;
        if (i == 0) {
            List list = StorageVolumeManager.sStorageMountInfoList;
            ThreadExecutor.runOnWorkThread(
                    new StorageVolumeManager$$ExternalSyntheticLambda0(context, i2, 0, this));
            i3 = -1;
        } else if (i == 1) {
            List list2 = StorageVolumeManager.sStorageMountInfoList;
            ThreadExecutor.runOnWorkThread(
                    new StorageVolumeManager$$ExternalSyntheticLambda0(context, i2, 1, this));
            i3 = 8852;
        } else {
            this.mUpdateListenerSet.forEach(
                    new StorageOperationManager$$ExternalSyntheticLambda0());
            i3 = 8853;
        }
        LoggingHelper.insertEventLogging(PageType.ANALYZE_STORAGE_HOME.getScreenIdForSA(), i3);
    }
}
