package com.samsung.android.scloud.lib.storage.internal;

import android.os.Handler;
import android.os.Message;

import com.samsung.android.scloud.oem.lib.LOG;

import java.util.concurrent.CountDownLatch;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class BackupRecordDataHelper {
    public String appInstallationResult;
    public CountDownLatch countDownLatch;
    public String fileDownloadResult;
    public String sourceKey;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CallbackHandler extends Handler {
        public CallbackHandler() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            BackupRecordDataHelper backupRecordDataHelper = BackupRecordDataHelper.this;
            if (i == 1001) {
                backupRecordDataHelper.fileDownloadResult =
                        message.getData().getString("MSG_KEY_RESULT");
                LOG.i(
                        "RecordDataHelper",
                        "["
                                + backupRecordDataHelper.sourceKey
                                + "] handleMessage MSG_RESPONSE_FILE_DOWNLOAD_COMPLETED "
                                + backupRecordDataHelper.fileDownloadResult);
            } else if (i == 2001) {
                backupRecordDataHelper.appInstallationResult =
                        message.getData().getString("MSG_KEY_RESULT");
                LOG.i(
                        "RecordDataHelper",
                        "["
                                + backupRecordDataHelper.sourceKey
                                + "] handleMessage MSG_RESPONSE_CMD_RESPONSE_INSTALL_APP "
                                + backupRecordDataHelper.appInstallationResult);
            }
            backupRecordDataHelper.countDownLatch.countDown();
        }
    }
}
