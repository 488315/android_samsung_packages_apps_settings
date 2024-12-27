package com.samsung.context.sdk.samsunganalytics.internal.sender.DMA;

import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.sec.android.diagmonagent.sa.IDMAInterface;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SendLogTask implements AsyncTaskClient {
    public Configuration configuration;
    public IDMAInterface dmaInterface;
    public SimpleLog log;

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final int onFinish() {
        return 0;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final void run() {
        SimpleLog simpleLog = this.log;
        Configuration configuration = this.configuration;
        try {
            IDMAInterface iDMAInterface = this.dmaInterface;
            configuration.getClass();
            IDMAInterface.Stub.Proxy proxy = (IDMAInterface.Stub.Proxy) iDMAInterface;
            proxy.sendLog(
                    simpleLog.timestamp,
                    configuration.trackingId,
                    simpleLog.type.getAbbrev(),
                    simpleLog.data);
        } catch (Exception e) {
            Debug.LogException(e.getClass(), e);
        }
    }
}
