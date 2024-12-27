package com.samsung.android.settings.wifi;

import android.os.UserHandle;
import android.util.Log;

import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class SetupWizardLogMsg {
    public static void out(String str, String str2) {
        try {
            Date date = new Date(System.currentTimeMillis());
            writeLog(
                    new SimpleDateFormat("MM-dd HH:mm:ss:SSS").format(date)
                            + " "
                            + str
                            + " : "
                            + str2
                            + "\n");
        } catch (Exception e) {
            Log.secE("SetupWizardLogMsg", e.getMessage());
        }
    }

    public static void writeLog(String str) {
        int myUserId = UserHandle.myUserId();
        String m =
                myUserId == 0
                        ? "/data/log/setupwizard.txt"
                        : LazyListMeasuredItem$$ExternalSyntheticOutline0.m(
                                myUserId,
                                "/data/user/",
                                "/com.sec.android.app.SecSetupWizard/setupwizard.txt");
        File file = new File(m);
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(m, "rw");
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                randomAccessFile.seek(randomAccessFile.length());
                randomAccessFile.writeBytes(str);
                randomAccessFile.close();
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
