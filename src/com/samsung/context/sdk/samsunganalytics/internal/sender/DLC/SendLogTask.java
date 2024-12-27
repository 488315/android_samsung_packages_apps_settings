package com.samsung.context.sdk.samsunganalytics.internal.sender.DLC;

import com.samsung.context.sdk.samsunganalytics.Configuration;
import com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient;
import com.samsung.context.sdk.samsunganalytics.internal.sender.SimpleLog;
import com.samsung.context.sdk.samsunganalytics.internal.util.Debug;
import com.sec.spp.push.dlc.api.IDlcService;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class SendLogTask implements AsyncTaskClient {
    public DLCBinder binder;
    public Configuration configuration;
    public int result;
    public SimpleLog simpleLog;

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final int onFinish() {
        if (this.result == 0) {
            Debug.LogD("DLC Sender", "send result success : " + this.result);
            return 1;
        }
        Debug.LogD("DLC Sender", "send result fail : " + this.result);
        return -7;
    }

    @Override // com.samsung.context.sdk.samsunganalytics.internal.executor.AsyncTaskClient
    public final void run() {
        SimpleLog simpleLog = this.simpleLog;
        try {
            this.result =
                    ((IDlcService.Stub.Proxy) this.binder.dlcService)
                            .requestSend(
                                    simpleLog.type.getAbbrev(),
                                    this.configuration.trackingId.substring(0, 3),
                                    simpleLog._id,
                                    simpleLog.data,
                                    simpleLog.timestamp);
            Debug.LogENG("send to DLC : " + simpleLog.data);
        } catch (Exception e) {
            Debug.LogException(SendLogTask.class, e);
        }
    }
}
