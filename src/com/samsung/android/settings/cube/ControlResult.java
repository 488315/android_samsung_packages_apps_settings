package com.samsung.android.settings.cube;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ControlResult {
    public final ErrorCode mErrorCode;
    public final String mErrorMsg;
    public final String mKey;
    public final ResultCode mResultCode;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public ErrorCode mErrorCode;
        public String mErrorMsg = ApnSettings.MVNO_NONE;
        public final String mKey;
        public ResultCode mResultCode;

        public Builder(String str) {
            this.mKey = str;
        }

        public final ControlResult build() {
            return new ControlResult(this);
        }

        public final void setErrorMsg(String str) {
            if (str == null) {
                str = ApnSettings.MVNO_NONE;
            }
            this.mErrorMsg = str;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum ErrorCode {
        /* JADX INFO: Fake field, exist only in values array */
        NOT_INITIALIZE(0),
        NOT_FOUND_CONTROLLER(1),
        NOT_SUPPORT_DEVICE(2),
        NOT_SUPPORT_TEMPORARY(3),
        INVALID_DATA(4),
        OUT_OF_RANGE(5),
        NOT_SUPPORT_BY_POLICY(6),
        DEPENDENT_SETTING(7),
        /* JADX INFO: Fake field, exist only in values array */
        EXCLUSIVE_SETTING(8),
        /* JADX INFO: Fake field, exist only in values array */
        HAS_ASYNC(9),
        CANCELED_BY_USER(10),
        /* JADX INFO: Fake field, exist only in values array */
        PERMISSION(11),
        ALREADY_SET(12),
        REQUEST_USER_INTERACTION(13),
        UNKNOWN(100);

        private final int mType;

        ErrorCode(int i) {
            this.mType = i;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum ResultCode {
        SUCCESS(0),
        FAIL(1),
        REQUEST_SUCCESS(2),
        /* JADX INFO: Fake field, exist only in values array */
        ASYNC(3);

        private final int mType;

        ResultCode(int i) {
            this.mType = i;
        }
    }

    public ControlResult(Builder builder) {
        this.mKey = builder.mKey;
        this.mResultCode = builder.mResultCode;
        this.mErrorCode = builder.mErrorCode;
        this.mErrorMsg = builder.mErrorMsg;
    }
}
