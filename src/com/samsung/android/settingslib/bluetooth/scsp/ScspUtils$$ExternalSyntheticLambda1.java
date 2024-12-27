package com.samsung.android.settingslib.bluetooth.scsp;

import com.samsung.android.settingslib.bluetooth.SemBluetoothCallback;

import java.util.Collection;
import java.util.Iterator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class ScspUtils$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Collection f$0;

    public /* synthetic */ ScspUtils$$ExternalSyntheticLambda1(int i, Collection collection) {
        this.$r8$classId = i;
        this.f$0 = collection;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                Collection collection = this.f$0;
                synchronized (collection) {
                    try {
                        Iterator it = collection.iterator();
                        while (it.hasNext()) {
                            ((SemBluetoothCallback) it.next()).onResourceUpdated();
                        }
                    } finally {
                    }
                }
                return;
            case 1:
                Collection collection2 = this.f$0;
                synchronized (collection2) {
                    try {
                        Iterator it2 = collection2.iterator();
                        while (it2.hasNext()) {
                            ((SemBluetoothCallback) it2.next()).onResourceUpdated();
                        }
                    } finally {
                    }
                }
                return;
            default:
                Collection collection3 = this.f$0;
                synchronized (collection3) {
                    try {
                        Iterator it3 = collection3.iterator();
                        while (it3.hasNext()) {
                            ((SemBluetoothCallback) it3.next()).onResourceUpdated();
                        }
                    } finally {
                    }
                }
                return;
        }
    }
}
