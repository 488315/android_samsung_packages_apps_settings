package com.samsung.android.settings.analyzestorage.domain.exception;


/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class CloudException extends AbsMyFilesException {
    @Override // com.samsung.android.settings.analyzestorage.domain.exception.AbsMyFilesException
    public final void checkValid() {
        AbsMyFilesException.ErrorType errorType = this.mType;
        if (errorType.getValue()
                        <= AbsMyFilesException.ErrorType.ERROR_CLOUD_RELATED_START.getValue()
                || errorType.getValue()
                        >= AbsMyFilesException.ErrorType.ERROR_CLOUD_RELATED_END.getValue()) {
            throw new IllegalStateException("not under cloud exception range");
        }
    }
}
