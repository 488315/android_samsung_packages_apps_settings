package com.samsung.android.settings.analyzestorage.external.database.detector;

import android.content.Context;
import android.os.UserHandle;
import android.provider.MediaStore;
import android.util.SparseArray;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoCheckerImpl;
import com.samsung.android.settings.analyzestorage.presenter.managers.UserInfoManager;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class MediaProviderChangeDetector {
    public final SparseArray mArgsList = new SparseArray();
    public final Context mContext;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ArgsByUser {
        public boolean mLastChangeResult;
        public long mLastCheckTime;
        public final Object mLock = new Object();
    }

    public MediaProviderChangeDetector(Context context) {
        this.mContext = context;
    }

    public static long getGenerationFromMp(Context currentContext, int i) {
        try {
            Intrinsics.checkNotNullParameter(currentContext, "currentContext");
            UserInfoCheckerImpl userInfoCheckerImpl = UserInfoManager.sUserInfoChecker;
            if (userInfoCheckerImpl == null) {
                Intrinsics.throwUninitializedPropertyAccessException("sUserInfoChecker");
                throw null;
            }
            if (userInfoCheckerImpl.getMyUserId() != i && !SemDualAppManager.isDualAppId(i)) {
                currentContext =
                        currentContext.semCreatePackageContextAsUser(
                                currentContext.getPackageName(), 0, UserHandle.semOf(i));
                Intrinsics.checkNotNullExpressionValue(
                        currentContext, "semCreatePackageContextAsUser(...)");
            }
            return MediaStore.getGeneration(currentContext, "external_primary");
        } catch (Exception e) {
            StringBuilder m =
                    ListPopupWindow$$ExternalSyntheticOutline0.m(
                            i,
                            "getGenerationFromMp() ] Fail to get generation value for userId : ",
                            " , Exception e : ");
            m.append(e.getMessage());
            Log.e("MediaProviderChangeDetector", m.toString());
            e.printStackTrace();
            return -1L;
        }
    }
}
