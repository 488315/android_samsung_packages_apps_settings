package com.android.settings.deviceinfo.simstatus;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicReference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EidStatus {
    public final Phaser mBlocker = new Phaser(1);
    public final AtomicReference mEid = new AtomicReference();
    public final SlotSimStatus mSlotSimStatus;

    public EidStatus(SlotSimStatus slotSimStatus, final Context context, Executor executor) {
        this.mSlotSimStatus = slotSimStatus;
        if (executor == null) {
            getEidOperation(context);
        } else {
            executor.execute(
                    new Runnable() { // from class:
                                     // com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            EidStatus.this.getEidOperation(context);
                        }
                    });
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:5:0x009c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void getEidOperation(android.content.Context r6) {
        /*
            r5 = this;
            java.lang.Class<android.telephony.euicc.EuiccManager> r0 = android.telephony.euicc.EuiccManager.class
            java.lang.Object r0 = r6.getSystemService(r0)
            android.telephony.euicc.EuiccManager r0 = (android.telephony.euicc.EuiccManager) r0
            com.android.settings.deviceinfo.simstatus.SlotSimStatus r1 = r5.mSlotSimStatus
            int r2 = r1.size()
            r3 = 1
            r4 = 0
            if (r2 > r3) goto L15
        L12:
            r6 = r4
            goto L9a
        L15:
            java.lang.Class<android.telephony.TelephonyManager> r2 = android.telephony.TelephonyManager.class
            java.lang.Object r6 = r6.getSystemService(r2)
            android.telephony.TelephonyManager r6 = (android.telephony.TelephonyManager) r6
            if (r6 != 0) goto L20
            goto L12
        L20:
            java.util.List r6 = r6.getUiccCardsInfo()
            if (r6 != 0) goto L27
            goto L12
        L27:
            r2 = 0
            int r1 = r1.size()
            java.util.stream.IntStream r1 = java.util.stream.IntStream.range(r2, r1)
            com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda1 r2 = new com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda1
            r2.<init>()
            java.util.stream.Stream r1 = r1.mapToObj(r2)
            com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda2 r2 = new com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda2
            r3 = 0
            r2.<init>(r3)
            java.util.stream.Stream r1 = r1.filter(r2)
            com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda2 r2 = new com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda2
            r3 = 1
            r2.<init>(r3)
            java.util.stream.Stream r1 = r1.filter(r2)
            com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda4 r2 = new com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda4
            r2.<init>()
            java.util.stream.IntStream r1 = r1.mapToInt(r2)
            java.util.stream.IntStream r1 = r1.sorted()
            java.util.stream.IntStream r1 = r1.distinct()
            int[] r1 = r1.toArray()
            int r2 = r1.length
            if (r2 != 0) goto L66
            goto L12
        L66:
            java.util.stream.Stream r6 = r6.stream()
            com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda2 r2 = new com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda2
            r3 = 2
            r2.<init>(r3)
            java.util.stream.Stream r6 = r6.filter(r2)
            com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda6 r2 = new com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda6
            r2.<init>()
            java.util.stream.Stream r6 = r6.filter(r2)
            com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda7 r1 = new com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda7
            r1.<init>()
            java.util.stream.Stream r6 = r6.map(r1)
            com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda2 r1 = new com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda2
            r2 = 3
            r1.<init>(r2)
            java.util.stream.Stream r6 = r6.filter(r1)
            java.util.Optional r6 = r6.findFirst()
            java.lang.Object r6 = r6.orElse(r4)
            java.lang.String r6 = (java.lang.String) r6
        L9a:
            if (r6 != 0) goto Laa
            if (r0 == 0) goto La9
            boolean r6 = r0.isEnabled()
            if (r6 != 0) goto La5
            goto La9
        La5:
            java.lang.String r4 = r0.getEid()
        La9:
            r6 = r4
        Laa:
            java.util.concurrent.atomic.AtomicReference r0 = r5.mEid
            r0.set(r6)
            java.util.concurrent.Phaser r5 = r5.mBlocker
            r5.arrive()
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.deviceinfo.simstatus.EidStatus.getEidOperation(android.content.Context):void");
    }
}
