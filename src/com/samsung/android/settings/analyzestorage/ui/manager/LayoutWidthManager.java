package com.samsung.android.settings.analyzestorage.ui.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class LayoutWidthManager {
    public static final Companion Companion = new Companion();
    public static volatile LayoutWidthManager instance;
    public final List listenerList = new ArrayList();
    public int contentWidthDp = -1;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Companion {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnWidthChangedListener {
        void onWidthChanged();
    }

    public final void setContentWidthDp(int i, boolean z) {
        if (i != this.contentWidthDp) {
            this.contentWidthDp = i;
            if (z) {
                Iterator it = ((ArrayList) this.listenerList).iterator();
                while (it.hasNext()) {
                    ((OnWidthChangedListener) it.next()).onWidthChanged();
                }
            }
        }
    }
}
