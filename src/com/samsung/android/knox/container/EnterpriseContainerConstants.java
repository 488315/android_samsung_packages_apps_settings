package com.samsung.android.knox.container;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface EnterpriseContainerConstants {
    public static final String ACTION_CONTAINER_STATE_CHANGED =
            "com.samsung.android.knox.intent.action.CONTAINER_STATE_CHANGED";
    public static final String ACTION_CONTAINER_UNLOCKED =
            "com.samsung.android.knox.intent.action.CONTAINER_UNLOCKED";
    public static final String ADMIN_UID = "admin";
    public static final int CONTAINER_ACTIVE = 91;
    public static final int CONTAINER_ADMIN_PWD_RESET = 98;
    public static final int CONTAINER_BASE = 90;
    public static final int CONTAINER_CREATED_NOT_ACTIVATED = 96;
    public static final int CONTAINER_CREATION_IN_PROGRESS = 93;
    public static final String CONTAINER_DATA_PATH = "/data/system/container/";
    public static final int CONTAINER_DOESNT_EXISTS = -1;
    public static final String CONTAINER_ID = "containerid";
    public static final int CONTAINER_INACTIVE = 90;
    public static final int CONTAINER_LOCKED = 95;
    public static final int CONTAINER_MOUNT_INACTIVE = 92;
    public static final String CONTAINER_NEW_STATE = "container_new_state";
    public static final String CONTAINER_OLD_STATE = "container_old_state";
    public static final int CONTAINER_REMOVE_IN_PROGRESS = 94;
    public static final int CONTAINER_UPGRADE_IN_PROGRESS = 97;
    public static final int DEFAULT_INSTALLATION_SOURCE = 1;
    public static final int DISABLE_CONTAINER_ACTIVATION_FLOW = 2;
    public static final String EMAIL_DOMAIN = "domain";
    public static final String EMAIL_USERNAME = "username";
    public static final int ENABLE_CONTAINER_ACTIVATION_FLOW = 1;
    public static final String ENTERPRISE_CONTAINER_SERVICE = "container_service";
    public static final int FREEMIUM_CONTAINER_TYPE = 1;
    public static final int INSTALL_ONLY = 503;
    public static final String INTENT_ACTION_EXPIRED_PASSWORD_NOTIFICATION =
            "enterprise.container.INTENT_ACTION_EXPIRED_PASSWORD_NOTIFICATION";
    public static final String INTENT_ACTION_PASSWORD_TIMEOUT_NOTIFICATION =
            "com.sec.knox.container.INTENT_ALARM_TIMEOUT";
    public static final String INTENT_ACTION_UNMOUNT_TIMEOUT_NOTIFICATION =
            "com.sec.knox.container.INTENT_ALARM_UNMOUNT_TIMEOUT";
    public static final String INTENT_BUNDLE = "intent";
    public static final String INTENT_CONTAINER_CANCELLED = "enterprise.container.cancelled";
    public static final String INTENT_CONTAINER_CREATION_SUCCESS =
            "enterprise.container.created.nonactive";
    public static final String INTENT_CONTAINER_EMAIL_ADDED = "enterprise.container.email.added";
    public static final String INTENT_CONTAINER_EMAIL_REMOVED =
            "enterprise.container.email.removed";
    public static final String INTENT_CONTAINER_LOCKED_BY_ADMIN = "enterprise.container.locked";
    public static final String INTENT_CONTAINER_REMOUNT_FAILURE =
            "enterprise.container.remount.failed";
    public static final String INTENT_CONTAINER_REMOUNT_SUCCESS =
            "enterprise.container.remount.success";
    public static final String INTENT_CONTAINER_REMOVED = "enterprise.container.uninstalled";
    public static final String INTENT_CONTAINER_REMOVE_PROGRESS =
            "enterprise.container.remove.progress";
    public static final String INTENT_CONTAINER_REMOVE_UNMOUNT_FAILURE =
            "enterprise.container.unmountfailure";
    public static final String INTENT_CONTAINER_SETUP_FAILURE =
            "enterprise.container.setup.failure";
    public static final String INTENT_CONTAINER_SETUP_SUCCESS =
            "enterprise.container.setup.success";
    public static final String INTENT_EMAIL_ACCOUNT_CREATED =
            "android.intent.action.EMAIL_ACCOUNT_CREATED_INTENT";
    public static final String INTENT_EMAIL_ACCOUNT_DELETED =
            "android.intent.action.EMAIL_ACCOUNT_DELETED_INTENT";
    public static final String INTENT_PASSWORD_EXPIRE =
            "com.samsung.redex.proxy.activity.show_dialog";
    public static final String INTENT_PASSWORD_TIMEOUT =
            "com.samsung.redex.proxy.activity.show_dialog";
    public static final String INTENT_REDEX_ACTIVITY_CLOSE =
            "com.samsung.redex.proxy.activity.close_dialog";
    public static final int INVALID_CONTAINER_ID = -1;
    public static final int INVALID_REQUEST_ID = -1;
    public static final int LOCK_TYPE_PASSWORD = 0;
    public static final int LOCK_TYPE_PIN = 1;
    public static final int MAX_CONTAINER_NUMBER = 1;
    public static final int MAX_FAILED_PASSWORD_ATTEMPT_BEFORE_LOCK = 10;
    public static final int MDM_INSTALLATION_SOURCE = 2;
    public static final int PASSWORD_ACTIVE = 81;
    public static final int PASSWORD_INACTIVE = 80;
    public static final int PASSWORD_RESET = 83;
    public static final int PASSWORD_STATUS_BASE = 80;
    public static final int PASSWORD_VALIDATION_BASE = 50;
    public static final int PASSWORD_VALIDATION_CONTAINER_INVALID = 50;
    public static final int PASSWORD_VALIDATION_FORBIDDEN_STRING = 60;
    public static final int PASSWORD_VALIDATION_INVALID_LENGTH = 52;
    public static final int PASSWORD_VALIDATION_INVALID_LETTERS_LENGTH = 53;
    public static final int PASSWORD_VALIDATION_INVALID_LOWERCASE_LENGTH = 55;
    public static final int PASSWORD_VALIDATION_INVALID_NONLETTERS_LENGTH = 58;
    public static final int PASSWORD_VALIDATION_INVALID_NUMBERS_LENGTH = 54;
    public static final int PASSWORD_VALIDATION_INVALID_QUALITY = 51;
    public static final int PASSWORD_VALIDATION_INVALID_SYMBOLS_LENGTH = 57;
    public static final int PASSWORD_VALIDATION_INVALID_UPPERCASE_LENGTH = 56;
    public static final int PASSWORD_VALIDATION_MAXIMUM_CHARACTER_OCCURRENCE_ERROR = 62;
    public static final int PASSWORD_VALIDATION_MAXIMUM_CHARACTER_SEQUENCE_LENGTH_ERROR = 63;
    public static final int PASSWORD_VALIDATION_MAXIMUM_LIMIT_EXCEEDED = 69;
    public static final int PASSWORD_VALIDATION_MAXIMUM_NUMERIC_SEQUENCE_LENGTH_ERROR = 64;
    public static final int PASSWORD_VALIDATION_MINIMUM_CHARACTER_CHANGE_LENGTH_ERROR = 61;
    public static final int PASSWORD_VALIDATION_PASSWORD_ILLEGAL_CHARACTERS = 70;
    public static final int PASSWORD_VALIDATION_PASSWORD_REPEATING_CHARACTERS = 67;
    public static final int PASSWORD_VALIDATION_PASSWORD_SEQUENTIAL_CHARACTERS = 66;
    public static final int PASSWORD_VALIDATION_PASSWORD_USED_ALREADY_ERROR = 65;
    public static final int PASSWORD_VALIDATION_SUCCESSFUL = 59;
    public static final int PASSWORD_VALIDATION_SYSTEM_KEY_API_FAILED = 71;
    public static final int PASSWORD_VALIDATION_SYSTEM_KEY_DEVICE_UNAUTHORIZED = 72;
    public static final int PASSWORD_VALIDATION_WRONG_OLD_PASSWORD = 68;
    public static final int PASSWORD_VERIFY = 82;
    public static final String PERMISSION_EMAIL_ACCOUNT_SETUP =
            "com.sec.android.email.EMAIL_ACCOUNT_SETUP";
    public static final int PERSONAL_CONTAINER_TYPE = 0;
    public static final int PREMIUM_CONTAINER_TYPE = 2;
    public static final int REDEX_SIGN_INSTALL = 501;
    public static final String REQUEST_ID = "requestid";
    public static final int SIGN_INSTALL = 502;
    public static final int STORE_INSTALLATION_SOURCE = 3;
    public static final int SYSTEM_SIGNED_APP = 10000;
    public static final int UNKNOWN_INSTALLATION_SOURCE = 4;
}
