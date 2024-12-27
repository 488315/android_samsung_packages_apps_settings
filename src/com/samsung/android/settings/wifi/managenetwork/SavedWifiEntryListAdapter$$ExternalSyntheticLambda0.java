package com.samsung.android.settings.wifi.managenetwork;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final /* synthetic */ class SavedWifiEntryListAdapter$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        SavedWifiEntry savedWifiEntry = (SavedWifiEntry) obj;
        switch (this.$r8$classId) {
            case 0:
                return savedWifiEntry.isRemovableNetwork().booleanValue();
            case 1:
                return savedWifiEntry.mIsChecked;
            default:
                return savedWifiEntry.isRemovableNetwork().booleanValue();
        }
    }
}
