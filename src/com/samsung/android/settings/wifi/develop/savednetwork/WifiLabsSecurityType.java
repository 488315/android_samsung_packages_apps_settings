package com.samsung.android.settings.wifi.develop.savednetwork;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiLabsSecurityType {
    public final UndefinedType mType;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UndefinedType {
        public final /* synthetic */ int $r8$classId;

        public /* synthetic */ UndefinedType(int i) {
            this.$r8$classId = i;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public WifiLabsSecurityType(com.android.wifitrackerlib.WifiEntry r9) {
        /*
            r8 = this;
            r0 = 4
            r1 = 1
            r2 = 3
            r3 = 2
            r4 = 0
            r8.<init>()
            android.net.wifi.WifiConfiguration r9 = r9.getWifiConfiguration()
            if (r9 != 0) goto L15
            com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsSecurityType$UndefinedType r9 = new com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsSecurityType$UndefinedType
            r9.<init>(r4)
            goto L7f
        L15:
            android.telephony.SubscriptionManager r5 = com.samsung.android.wifitrackerlib.SemWifiUtils.mSubscriptionManager
            int r5 = r9.getAuthType()
            if (r5 != 0) goto L33
            int r5 = r9.wepTxKeyIndex
            if (r5 < 0) goto L29
            java.lang.String[] r6 = r9.wepKeys
            int r7 = r6.length
            if (r5 >= r7) goto L29
            r5 = r6[r5]
            goto L2a
        L29:
            r5 = 0
        L2a:
            if (r5 == 0) goto L33
            com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsSecurityType$UndefinedType r9 = new com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsSecurityType$UndefinedType
            r0 = 5
            r9.<init>(r0)
            goto L7f
        L33:
            int r5 = r9.getAuthType()
            if (r5 != 0) goto L3f
            com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsSecurityType$UndefinedType r9 = new com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsSecurityType$UndefinedType
            r9.<init>(r3)
            goto L7f
        L3f:
            int r5 = r9.getAuthType()
            r6 = 9
            if (r5 != r6) goto L4d
            com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsSecurityType$UndefinedType r9 = new com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsSecurityType$UndefinedType
            r9.<init>(r2)
            goto L7f
        L4d:
            int r5 = r9.getAuthType()
            if (r5 == r1) goto L79
            if (r5 != r0) goto L56
            goto L79
        L56:
            int r5 = r9.getAuthType()
            r6 = 8
            if (r5 != r6) goto L64
            com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsSecurityType$UndefinedType r9 = new com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsSecurityType$UndefinedType
            r9.<init>(r0)
            goto L7f
        L64:
            int r9 = r9.getAuthType()
            if (r9 == r3) goto L73
            if (r9 != r2) goto L6d
            goto L73
        L6d:
            com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsSecurityType$UndefinedType r9 = new com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsSecurityType$UndefinedType
            r9.<init>(r4)
            goto L7f
        L73:
            com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsSecurityType$UndefinedType r9 = new com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsSecurityType$UndefinedType
            r9.<init>(r1)
            goto L7f
        L79:
            com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsSecurityType$UndefinedType r9 = new com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsSecurityType$UndefinedType
            r0 = 6
            r9.<init>(r0)
        L7f:
            r8.mType = r9
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.wifi.develop.savednetwork.WifiLabsSecurityType.<init>(com.android.wifitrackerlib.WifiEntry):void");
    }
}
