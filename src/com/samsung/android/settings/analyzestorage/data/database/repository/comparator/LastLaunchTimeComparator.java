package com.samsung.android.settings.analyzestorage.data.database.repository.comparator;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.analyzestorage.data.model.UnusedAppInfo;
import com.samsung.android.settings.analyzestorage.domain.entity.AppInfo;
import com.samsung.android.settings.analyzestorage.domain.log.Log;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

import java.io.Serializable;
import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
@Metadata(
        d1 = {
            "\u0000\u001c\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0018\u0002\n"
                + "\u0002\u0010\u000b\n"
                + "\u0002\b\u0003\b\u0007\u0018\u00002\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u00032\u00020\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0082\u0004¢\u0006\u0006\n"
                + "\u0004\b\u0006\u0010\u0007¨\u0006\b"
        },
        d2 = {
            "Lcom/samsung/android/settings/analyzestorage/data/database/repository/comparator/LastLaunchTimeComparator;",
            "Ljava/util/Comparator;",
            "Lcom/samsung/android/settings/analyzestorage/domain/entity/AppInfo;",
            "Lkotlin/Comparator;",
            "Ljava/io/Serializable;",
            ApnSettings.MVNO_NONE,
            "isAscending",
            "Z",
            "applications__sources__apps__SecSettings__android_common__SecSettings-core"
        },
        k = 1,
        mv = {1, 9, 0})
/* loaded from: classes4.dex */
public final class LastLaunchTimeComparator implements Comparator<AppInfo>, Serializable {
    private static final long serialVersionUID = 1;
    private final boolean isAscending;

    public LastLaunchTimeComparator(boolean z) {
        this.isAscending = z;
    }

    @Override // java.util.Comparator
    public final int compare(AppInfo appInfo, AppInfo appInfo2) {
        AppInfo o1 = appInfo;
        AppInfo o2 = appInfo2;
        Intrinsics.checkNotNullParameter(o1, "o1");
        Intrinsics.checkNotNullParameter(o2, "o2");
        if (!(o1 instanceof UnusedAppInfo) || !(o2 instanceof UnusedAppInfo)) {
            Log.w("LastLaunchTimeComparator", "compare() ] AppInfo type is incorrect.");
            return -1;
        }
        long lastLaunchTime = ((UnusedAppInfo) o1).getLastLaunchTime();
        long lastLaunchTime2 = ((UnusedAppInfo) o2).getLastLaunchTime();
        int i = lastLaunchTime >= lastLaunchTime2 ? lastLaunchTime == lastLaunchTime2 ? 0 : 1 : -1;
        return this.isAscending ? i : -i;
    }
}
