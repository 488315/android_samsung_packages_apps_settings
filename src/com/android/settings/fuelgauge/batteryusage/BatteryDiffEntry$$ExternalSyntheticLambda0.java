package com.android.settings.fuelgauge.batteryusage;

import java.util.Comparator;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class BatteryDiffEntry$$ExternalSyntheticLambda0
        implements Comparator {
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0068, code lost:

       if (r8.equals("A|UninstalledApps") == false) goto L31;
    */
    /* JADX WARN: Removed duplicated region for block: B:12:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0053  */
    @Override // java.util.Comparator
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int compare(java.lang.Object r17, java.lang.Object r18) {
        /*
            r16 = this;
            r0 = 2
            java.lang.String r1 = "A|UninstalledApps"
            r2 = 1
            java.lang.String r3 = "S|Others"
            r4 = 0
            java.lang.String r5 = "A|SystemApps"
            r6 = -1
            r7 = r17
            com.android.settings.fuelgauge.batteryusage.BatteryDiffEntry r7 = (com.android.settings.fuelgauge.batteryusage.BatteryDiffEntry) r7
            r8 = r18
            com.android.settings.fuelgauge.batteryusage.BatteryDiffEntry r8 = (com.android.settings.fuelgauge.batteryusage.BatteryDiffEntry) r8
            java.lang.String r9 = r8.mKey
            r10 = -4611686018427387904(0xc000000000000000, double:-2.0)
            r12 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            if (r9 != 0) goto L21
            double r14 = r8.mPercentage
            int r8 = r8.mAdjustPercentageOffset
        L1e:
            double r8 = (double) r8
            double r14 = r14 + r8
            goto L4f
        L21:
            int r14 = r9.hashCode()
            switch(r14) {
                case -1342410756: goto L3c;
                case -622293172: goto L33;
                case 1527638328: goto L2a;
                default: goto L28;
            }
        L28:
            r9 = r6
            goto L44
        L2a:
            boolean r9 = r9.equals(r1)
            if (r9 != 0) goto L31
            goto L28
        L31:
            r9 = r0
            goto L44
        L33:
            boolean r9 = r9.equals(r3)
            if (r9 != 0) goto L3a
            goto L28
        L3a:
            r9 = r2
            goto L44
        L3c:
            boolean r9 = r9.equals(r5)
            if (r9 != 0) goto L43
            goto L28
        L43:
            r9 = r4
        L44:
            switch(r9) {
                case 0: goto L4e;
                case 1: goto L4c;
                case 2: goto L4c;
                default: goto L47;
            }
        L47:
            double r14 = r8.mPercentage
            int r8 = r8.mAdjustPercentageOffset
            goto L1e
        L4c:
            r14 = r12
            goto L4f
        L4e:
            r14 = r10
        L4f:
            java.lang.String r8 = r7.mKey
            if (r8 != 0) goto L5b
            double r0 = r7.mPercentage
            int r2 = r7.mAdjustPercentageOffset
        L57:
            double r2 = (double) r2
            double r10 = r0 + r2
            goto L85
        L5b:
            int r9 = r8.hashCode()
            switch(r9) {
                case -1342410756: goto L74;
                case -622293172: goto L6b;
                case 1527638328: goto L64;
                default: goto L62;
            }
        L62:
            r0 = r6
            goto L7c
        L64:
            boolean r1 = r8.equals(r1)
            if (r1 != 0) goto L7c
            goto L62
        L6b:
            boolean r0 = r8.equals(r3)
            if (r0 != 0) goto L72
            goto L62
        L72:
            r0 = r2
            goto L7c
        L74:
            boolean r0 = r8.equals(r5)
            if (r0 != 0) goto L7b
            goto L62
        L7b:
            r0 = r4
        L7c:
            switch(r0) {
                case 0: goto L85;
                case 1: goto L84;
                case 2: goto L84;
                default: goto L7f;
            }
        L7f:
            double r0 = r7.mPercentage
            int r2 = r7.mAdjustPercentageOffset
            goto L57
        L84:
            r10 = r12
        L85:
            int r0 = java.lang.Double.compare(r14, r10)
            return r0
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.fuelgauge.batteryusage.BatteryDiffEntry$$ExternalSyntheticLambda0.compare(java.lang.Object,"
                    + " java.lang.Object):int");
    }
}
