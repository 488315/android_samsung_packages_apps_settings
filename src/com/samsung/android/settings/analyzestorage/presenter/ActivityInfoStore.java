package com.samsung.android.settings.analyzestorage.presenter;

import android.content.Context;
import android.util.SparseArray;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.FragmentActivity;

import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ActivityInfoStore {
    public static Context context;
    public static final SparseArray instanceMap = new SparseArray();
    public final List activityListenerList = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class Companion {
        public static ActivityInfoStore getInstance(int i) {
            SparseArray sparseArray = ActivityInfoStore.instanceMap;
            ActivityInfoStore activityInfoStore = (ActivityInfoStore) sparseArray.get(i);
            if (activityInfoStore != null) {
                return activityInfoStore;
            }
            ActivityInfoStore activityInfoStore2 = new ActivityInfoStore();
            sparseArray.put(i, activityInfoStore2);
            return activityInfoStore2;
        }
    }

    public final ActivityResultLauncher getActivityResultLauncher() {
        if (((ArrayList) this.activityListenerList).isEmpty()) {
            return null;
        }
        List list = this.activityListenerList;
        return ((ActivityInfo$ActivityInfoListener)
                        ((ArrayList) list).get(CollectionsKt__CollectionsKt.getLastIndex(list)))
                .getActivityResultLauncher();
    }

    public final FragmentActivity getFragmentActivity() {
        if (((ArrayList) this.activityListenerList).isEmpty()) {
            return null;
        }
        List list = this.activityListenerList;
        return (FragmentActivity)
                ((ActivityInfo$ActivityInfoListener)
                                ((ArrayList) list)
                                        .get(CollectionsKt__CollectionsKt.getLastIndex(list)))
                        .getActivity();
    }

    public final void register(ActivityInfo$ActivityInfoListener activityListener) {
        Intrinsics.checkNotNullParameter(activityListener, "activityListener");
        Iterator it = this.activityListenerList.iterator();
        while (it.hasNext()) {
            if (Intrinsics.areEqual(
                    (ActivityInfo$ActivityInfoListener) it.next(), activityListener)) {
                return;
            }
        }
        ((ArrayList) this.activityListenerList).add(activityListener);
    }
}
