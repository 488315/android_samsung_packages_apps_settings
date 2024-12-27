package com.android.settings.datausage.lib;

import android.content.Context;
import android.net.NetworkTemplate;
import android.os.Process;
import android.os.UserHandle;
import android.util.SparseArray;
import android.util.SparseBooleanArray;

import com.android.settingslib.AppItem;

import com.samsung.android.knox.SemPersonaManager;

import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt___SequencesKt;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppDataUsageRepository {
    public final Context context;
    public final int currentUserId;
    public final NetworkStatsRepository networkStatsRepository;
    public long secureusage;

    public AppDataUsageRepository(
            Context context, int i, NetworkTemplate template, Function1 function1) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(template, "template");
        this.context = context;
        this.currentUserId = i;
        this.networkStatsRepository = new NetworkStatsRepository(context, template);
    }

    public static final List getAppUidList(final SparseBooleanArray uids) {
        Intrinsics.checkNotNullParameter(uids, "uids");
        return SequencesKt___SequencesKt.toList(
                SequencesKt___SequencesKt.distinct(
                        SequencesKt___SequencesKt.map(
                                SequencesKt___SequencesKt.asSequence(
                                        new IntIterator() { // from class:
                                                            // androidx.core.util.SparseBooleanArrayKt$keyIterator$1
                                            public int index;

                                            @Override // java.util.Iterator
                                            public final boolean hasNext() {
                                                return this.index < uids.size();
                                            }

                                            @Override // kotlin.collections.IntIterator
                                            public final int nextInt() {
                                                SparseBooleanArray sparseBooleanArray = uids;
                                                int i = this.index;
                                                this.index = i + 1;
                                                return sparseBooleanArray.keyAt(i);
                                            }
                                        }),
                                new Function1() { // from class:
                                                  // com.android.settings.datausage.lib.AppDataUsageRepository$Companion$getAppUidList$1
                                    @Override // kotlin.jvm.functions.Function1
                                    public final Object invoke(Object obj) {
                                        int intValue = ((Number) obj).intValue();
                                        if (Process.isSdkSandboxUid(intValue)) {
                                            intValue = Process.getAppUidForSdkSandboxUid(intValue);
                                        }
                                        return Integer.valueOf(intValue);
                                    }
                                })));
    }

    public final void accumulate(
            int i,
            SparseArray sparseArray,
            NetworkStatsRepository.Companion.Bucket bucket,
            int i2,
            ArrayList arrayList) {
        AppItem appItem = (AppItem) sparseArray.get(i);
        if (appItem == null) {
            appItem = new AppItem(i);
            appItem.category = i2;
            arrayList.add(appItem);
            sparseArray.put(appItem.key, appItem);
        }
        int i3 = bucket.uid;
        appItem.addUid(i3);
        long j = appItem.total;
        long j2 = bucket.bytes;
        appItem.total = j + j2;
        if (SemPersonaManager.isSecureFolderId(UserHandle.myUserId())
                && UserHandle.getUserId(i3) == UserHandle.myUserId()) {
            this.secureusage += j2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:66:0x0148, code lost:

       if (com.samsung.android.knox.SemPersonaManager.isSecureFolderId(r4) != false) goto L63;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.List<kotlin.Pair<com.android.settingslib.AppItem, java.lang.Integer>>
            getAppPercent(
                    java.lang.Integer r18,
                    java.util.List<
                                    com.android.settings.datausage.lib.NetworkStatsRepository
                                            .Companion.Bucket>
                            r19) {
        /*
            Method dump skipped, instructions count: 738
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.datausage.lib.AppDataUsageRepository.getAppPercent(java.lang.Integer,"
                    + " java.util.List):java.util.List");
    }
}
