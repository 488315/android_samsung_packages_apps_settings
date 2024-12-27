package com.samsung.android.settings.eternal.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class EpisodeHolder {
    public BackupInfo mBackupInfo;
    public final HashMap mBackupSceneMap = new HashMap();
    public final HashMap mRestoreResultMap = new HashMap();
    public final HashMap mSourceInfoMap = new HashMap();
    public final List mGeneralInfoList = new ArrayList();
    public final HashMap mSupplierInfo = new HashMap();

    public final boolean hasRestoreResult() {
        HashMap hashMap = this.mRestoreResultMap;
        return (hashMap == null || hashMap.isEmpty()) ? false : true;
    }

    public final void putBackupSceneList(String str, List list) {
        List list2 = (List) this.mBackupSceneMap.get(str);
        if (list2 == null) {
            this.mBackupSceneMap.put(str, list);
        } else {
            list2.addAll(list);
        }
    }
}
