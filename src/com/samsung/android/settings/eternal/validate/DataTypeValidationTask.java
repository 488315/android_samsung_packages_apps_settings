package com.samsung.android.settings.eternal.validate;

import androidx.appcompat.util.SeslRoundedCorner$$ExternalSyntheticOutline0;

import java.util.List;
import java.util.concurrent.FutureTask;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class DataTypeValidationTask extends FutureTask {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DataTypeValidationWorker extends BaseValidationWorker {
        public List mDataTypeViolationItems;

        public static String getValidationMessage(String str, String str2, String str3) {
            StringBuilder m =
                    SeslRoundedCorner$$ExternalSyntheticOutline0.m(
                            "validateContentType() key = ", str, " / type = ", str2, " / value = ");
            m.append(str3);
            return m.toString();
        }

        @Override // com.samsung.android.settings.eternal.validate.BaseValidationWorker
        public final String getValidationFileName() {
            return "backupTypeValidation.json";
        }

        /* JADX WARN: Removed duplicated region for block: B:46:0x00ef A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:65:0x00d7 A[SYNTHETIC] */
        @Override // com.samsung.android.settings.eternal.validate.BaseValidationWorker
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.util.List validateSceneList(java.util.Map.Entry r17, java.util.List r18) {
            /*
                Method dump skipped, instructions count: 335
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.samsung.android.settings.eternal.validate.DataTypeValidationTask.DataTypeValidationWorker.validateSceneList(java.util.Map$Entry,"
                        + " java.util.List):java.util.List");
        }
    }
}
