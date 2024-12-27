package com.samsung.android.settings.analyzestorage.external.bnr;

import android.content.Context;
import android.content.Intent;

import androidx.compose.foundation.layout.RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0;

import com.samsung.android.settings.analyzestorage.domain.log.Log;
import com.samsung.android.settings.analyzestorage.presenter.utils.fileutils.FileWrapper;

import kotlin.jvm.internal.Intrinsics;

import java.io.File;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class BnrUtils {
    public static final void deleteBnrFiles(String deletedTargetPath) {
        Intrinsics.checkNotNullParameter(deletedTargetPath, "deletedTargetPath");
        File[] listFiles = new FileWrapper(deletedTargetPath).listFiles();
        if (listFiles == null) {
            listFiles = new File[0];
        }
        int i = 0;
        for (File file : listFiles) {
            if (file != null) {
                if (file.delete()) {
                    i++;
                } else {
                    Log.e(
                            "BnrUtils",
                            "deleteBnrFiles() ] Fail to delete "
                                    + Log.getEncodedMsg(file.getAbsolutePath()));
                }
            }
        }
        boolean delete = new FileWrapper(deletedTargetPath).delete();
        StringBuilder m =
                RowColumnMeasurePolicyKt$$ExternalSyntheticOutline0.m(
                        "deleteBnrFiles() ] (D: ",
                        " from T: ",
                        i,
                        listFiles.length,
                        " isWorkingDirectoryDeleted : ");
        m.append(delete);
        Log.d("BnrUtils", m.toString());
    }

    public static final boolean isValidDestinationPath(String str) {
        Log.d("BnrUtils", "isValidDestinationPath() ] Path : " + Log.getEncodedMsg(str));
        Object orElse =
                Optional.ofNullable(str)
                        .map(BnrUtils$isValidDestinationPath$1.INSTANCE)
                        .orElse(Boolean.FALSE);
        Intrinsics.checkNotNullExpressionValue(orElse, "orElse(...)");
        return ((Boolean) orElse).booleanValue();
    }

    public static final void sendBnrResult(
            Context context, String str, int i, String source, String sessionTime) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(sessionTime, "sessionTime");
        Intent intent = new Intent(str);
        intent.putExtra("RESULT", i == 0 ? 0 : 1);
        intent.setPackage("com.sec.android.easyMover");
        intent.putExtra("ERR_CODE", i);
        intent.putExtra("REQ_SIZE", 0);
        intent.putExtra("SOURCE", source);
        intent.putExtra("EXPORT_SESSION_TIME", sessionTime);
        context.sendBroadcast(intent);
    }
}
