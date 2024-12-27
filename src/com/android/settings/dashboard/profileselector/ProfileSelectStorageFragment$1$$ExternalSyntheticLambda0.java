package com.android.settings.dashboard.profileselector;

import android.os.storage.VolumeRecord;
import android.text.TextUtils;

import com.android.settings.deviceinfo.storage.StorageEntry;

import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ProfileSelectStorageFragment$1$$ExternalSyntheticLambda0
        implements Predicate {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                StorageEntry storageEntry = (StorageEntry) obj;
                return storageEntry.isVolumeInfo()
                        && TextUtils.equals(
                                storageEntry.getFsUuid(), ((VolumeRecord) obj2).getFsUuid());
            default:
                return ((StorageEntry) obj).equals((StorageEntry) obj2);
        }
    }
}
