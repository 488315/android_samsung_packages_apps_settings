package com.samsung.android.scloud.lib.storage.internal;

import android.os.Handler;
import android.os.Message;

import com.samsung.android.scloud.oem.lib.LOG;

import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SyncRecordDataHelper {
    public CountDownLatch countDownLatch;
    public String fileDownloadResult;
    public String sourceKey;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CallbackHandler extends Handler {
        public CallbackHandler() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            SyncRecordDataHelper syncRecordDataHelper = SyncRecordDataHelper.this;
            if (i == 1001) {
                syncRecordDataHelper.fileDownloadResult =
                        message.getData().getString("MSG_KEY_RESULT");
                LOG.i(
                        "RecordDataHelper",
                        "["
                                + syncRecordDataHelper.sourceKey
                                + "] handleMessage MSG_RESPONSE_FILE_DOWNLOAD_COMPLETED "
                                + syncRecordDataHelper.fileDownloadResult);
            }
            syncRecordDataHelper.countDownLatch.countDown();
        }
    }
}
