package com.samsung.android.settings.voiceinput.samsungaccount.contract;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class SaContract {
    public static final String BINDING_CLASS_NAME = "com.msc.sa.service.RequestService";
    public static final String OLD_PACKAGE_NAME = "com.samsung.android.mobileservice";
    public static final String PACKAGE_NAME = "com.osp.app.signin";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @Retention(RetentionPolicy.SOURCE)
    public @interface ErrorType {
        public static final int RESULT_ERROR = -1000;
        public static final int SA_ERROR_BIND_ERROR = -1012;
        public static final int SA_ERROR_DEVICEID_IS_NULL = -1015;
        public static final int
                SA_ERROR_DISCLAIMER_AGREEMENT_MUST_BE_COMPLETED_TO_USE_SAMSUNG_ACCOUNT = -1010;
        public static final int SA_ERROR_DUPLICATED_REQUEST = -1014;
        public static final int SA_ERROR_INTERNAL_ERROR = -1005;
        public static final int SA_ERROR_LOGIN_REQUIRED = -1001;
        public static final int SA_ERROR_MANDATORY_INFO_MISSED = -1003;
        public static final int SA_ERROR_NETWORK_ERROR = -1004;
        public static final int SA_ERROR_NETWORK_SSL = -1013;
        public static final int SA_ERROR_PASSWORD_INCORRECT = -1006;
        public static final int SA_ERROR_PASSWORD_LENGTH_INCORRECT = -1007;
        public static final int SA_ERROR_REQUEST_ID_EXIST = -1011;
        public static final int SA_ERROR_SESSION_EXPIRED = -1008;
        public static final int SA_ERROR_SIGNATURE_NOT_REGISTER = -1009;
        public static final int SA_ERROR_UNDEFINED_ERROR = -1016;
        public static final int SA_ERROR_UPDATE_REQUIRED = -1002;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ExtraKey {
        public static final String ADDITIONAL = "additional";
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ExtraValue {
        public static final String CLIENT_ID = "bmne5004w6";
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface IntentAction {
        public static final String SERVICE_BINDING =
                "com.msc.action.samsungaccount.REQUEST_SERVICE";
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface RequestCode {
        public static final int ACCESS_TOKEN = 18;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ResultCode {
        public static final int ERROR = -1000;
        public static final int OK = 1;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ResultKey {
        public static final String ERROR_CODE = "error_code";
        public static final String ERROR_MESSAGE = "error_message";
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface TokenInfo {
        public static final String CC = "cc";
        public static final String NONE = "NONE";
    }
}
