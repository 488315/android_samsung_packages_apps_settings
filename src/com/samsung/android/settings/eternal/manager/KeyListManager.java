package com.samsung.android.settings.eternal.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class KeyListManager {
    public static final String[] GENERAL_KEY_LIST = {
        "/GeneralInfo/Version",
        "/GeneralInfo/BuildNum",
        "/GeneralInfo/CreatedTime",
        "/GeneralInfo/InitialOsVersion",
        "/GeneralInfo/DeviceType",
        "/GeneralInfo/OSVersion",
        "/GeneralInfo/OneUIVersion",
        "/GeneralInfo/PackageList"
    };
    public HashMap mDTDItemHashMap;
    public HashMap mKeyListHashMap;
    public final HashMap mSupplierInfoHashMap;

    public KeyListManager(HashMap hashMap) {
        this.mSupplierInfoHashMap = hashMap;
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x008a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x028e  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x030a A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:166:0x030c  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x00de A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0127  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.util.HashMap getDTDItemHashMap(
            com.samsung.android.settings.eternal.manager.XmlManager r20, int r21) {
        /*
            Method dump skipped, instructions count: 788
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.eternal.manager.KeyListManager.getDTDItemHashMap(com.samsung.android.settings.eternal.manager.XmlManager,"
                    + " int):java.util.HashMap");
    }

    public final HashMap getKeyListHashMap(XmlManager xmlManager, int i) {
        if (this.mDTDItemHashMap == null) {
            getDTDItemHashMap(xmlManager, i);
        }
        if (this.mKeyListHashMap == null && this.mDTDItemHashMap != null) {
            this.mKeyListHashMap = new HashMap();
            for (Map.Entry entry : this.mDTDItemHashMap.entrySet()) {
                HashMap hashMap = (HashMap) entry.getValue();
                if (hashMap != null) {
                    this.mKeyListHashMap.put(
                            (String) entry.getKey(), new ArrayList(hashMap.keySet()));
                }
            }
        }
        if (this.mKeyListHashMap == null) {
            return null;
        }
        return new HashMap(this.mKeyListHashMap);
    }
}
