package com.samsung.android.settings.analyzestorage.domain.exception;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;

import com.samsung.android.knox.container.EnterpriseContainerConstants;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class AbsMyFilesException extends IOException {
    private final String mDetailMessage;
    private final Map<String, Object> mExtraMap;
    protected ErrorType mType;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum ErrorType {
        ERROR_NONE(-1),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_UNKNOWN(0),
        ERROR_UNSUPPORTED_ARGS(2),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_FILE_RELATED_START(EnterpriseContainerConstants.SYSTEM_SIGNED_APP),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_FILE_RELATED_END(19999),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_QUOTA_RELATED_START(20000),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_QUOTA_RELATED_END(29999),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_REPOSITORY_RELATED_START(30000),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_REPOSITORY_RELATED_END(39999),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_NETWORK_RELATED_START(40000),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_NETWORK_UNSTABLE(40002),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_NETWORK_POLICY_BLOCKED(40003),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_NETWORK_NOT_SUFFICIENT_PERMISSION(40004),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_NETWORK_WIFI_NOT_CONNECTED(40005),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_NEED_USER_INTERACTION(40006),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_PARSE_ERROR(49999),
        ERROR_CLOUD_RELATED_START(50000),
        ERROR_CLOUD_SIGN_IN_ALREADY_IN_PROGRESS(50001),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_NEED_USER_INTERACTION(50002),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_PARSE_ERROR(50003),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_NEED_USER_INTERACTION(50004),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_PARSE_ERROR(50005),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_NEED_USER_INTERACTION(50006),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_PARSE_ERROR(50007),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_NEED_USER_INTERACTION(50100),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_PARSE_ERROR(50101),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_NEED_USER_INTERACTION(50102),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_PARSE_ERROR(50201),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_NEED_USER_INTERACTION(50202),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_PARSE_ERROR(50300),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_NEED_USER_INTERACTION(50301),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_PARSE_ERROR(50302),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_NEED_USER_INTERACTION(50303),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_PARSE_ERROR(50304),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_NEED_USER_INTERACTION(50305),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_PARSE_ERROR(50400),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_CLOUD_NEED_USER_INTERACTION(50600),
        ERROR_CLOUD_RELATED_END(59999),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_COMPRESS_RELATED_START(60000),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_COMPRESSOR_COMPRESS_FAILED(60001),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_COMPRESSOR_EXTRACT_FAILED(60002),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_COMPRESSOR_INVALID_SRC(60003),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_COMPRESSOR_NO_SUCH_FILE(60004),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_COMPRESSOR_NOT_ENOUGH_MEMORY(60005),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_COMPRESSOR_NOT_SUPPORT_RAR(60006),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_COMPRESSOR_NOTHING_EXTRACTED(60007),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_COMPRESSOR_EXTRACT_FAILED_BY_ENCRYPTION(60008),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_COMPRESSOR_FILE_SIZE_NOT_SUPPORTED_FAT32(60009),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_COMPRESSOR_EXTRACT_NOT_ENOUGH_MEMORY(60010),
        /* JADX INFO: Fake field, exist only in values array */
        ERROR_COMPRESS_RELATED_END(69999);

        private final int mValue;

        ErrorType(int i) {
            this.mValue = i;
        }

        public final int getValue() {
            return this.mValue;
        }
    }

    public AbsMyFilesException() {
        ErrorType errorType = ErrorType.ERROR_UNSUPPORTED_ARGS;
        this.mExtraMap = new HashMap();
        this.mType = errorType;
        this.mDetailMessage = null;
    }

    public abstract void checkValid();

    public final String getDetailMessage() {
        return this.mDetailMessage;
    }

    public final ErrorType getExceptionType() {
        return this.mType;
    }

    public final int getInt(int i) {
        return ((Integer)
                        Optional.ofNullable(this.mExtraMap.get("messageRes"))
                                .filter(new AbsMyFilesException$$ExternalSyntheticLambda0())
                                .map(new AbsMyFilesException$$ExternalSyntheticLambda1())
                                .orElse(Integer.valueOf(i)))
                .intValue();
    }

    @Override // java.lang.Throwable
    public final String toString() {
        StringBuilder sb = new StringBuilder("AbsMyFilesException{mType=");
        sb.append(this.mType);
        sb.append(", mDetailMessage='");
        return ComponentActivity$1$$ExternalSyntheticOutline0.m(sb, this.mDetailMessage, "'}");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AbsMyFilesException(int i) {
        super(ApnSettings.MVNO_NONE);
        ErrorType errorType = ErrorType.ERROR_CLOUD_SIGN_IN_ALREADY_IN_PROGRESS;
        this.mExtraMap = new HashMap();
        this.mType = errorType;
        this.mDetailMessage = ApnSettings.MVNO_NONE;
        checkValid();
    }
}
