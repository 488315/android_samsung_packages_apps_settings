package com.samsung.android.knox.restriction;

import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.notification.RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0;

import com.samsung.android.knox.AccessController;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PhoneRestrictionPolicy {
    public static final String ACTION_ICCID_AVAILABLE_INTERNAL =
            "com.samsung.android.knox.intent.action.ICCID_AVAILABLE_INTERNAL";
    public static final String ACTION_RCS_MSG_FILE_RECEIVED =
            "com.samsung.android.knox.intent.action.RCS_MSG_FILE_RECEIVED";
    public static final String ACTION_RCS_MSG_FILE_SENT =
            "com.samsung.android.knox.intent.action.RCS_MSG_FILE_SENT";
    public static final String ACTION_RCS_MSG_FILE_THUMBNAIL_RECEIVED =
            "com.samsung.android.knox.intent.action.RCS_MSG_FILE_THUMBNAIL_RECEIVED";
    public static final String ACTION_RCS_MSG_TEXT_RECEIVED =
            "com.samsung.android.knox.intent.action.RCS_MSG_TEXT_RECEIVED";
    public static final String ACTION_RCS_MSG_TEXT_SENT =
            "com.samsung.android.knox.intent.action.RCS_MSG_TEXT_SENT";
    public static final String BODY = "body";
    public static final String CONTENT_TYPE = "content-type";
    public static final int ERROR_DATABASE_OPERATION = -4;
    public static final int ERROR_INVALID_INPUT = -1;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_SUPPORTED = -3;
    public static final int ERROR_SIM_NOT_INSERTED = 13;
    public static final int ERROR_SIM_PIN_ALREADY_LOCKED = 4;
    public static final int ERROR_SIM_PIN_ALREADY_LOCKED_BY_ADMIN = 11;
    public static final int ERROR_SIM_PIN_ALREADY_UNLOCKED = 5;
    public static final int ERROR_SIM_PIN_BLOCKED_BY_PUK = 6;
    public static final int ERROR_SIM_PIN_DATABASE = 10;
    public static final int ERROR_SIM_PIN_FAILED = 1;
    public static final int ERROR_SIM_PIN_ID_NOT_READY = 9;
    public static final int ERROR_SIM_PIN_INCORRECT_CODE = 3;
    public static final int ERROR_SIM_PIN_INVALID_CODE = 2;
    public static final int ERROR_SIM_PIN_MAX_RETRIES_EXCEEDED = 8;
    public static final int ERROR_SIM_PIN_NONE = 0;
    public static final int ERROR_SIM_PIN_OWNED_BY_OTHER_ADMIN = 12;
    public static final int ERROR_SIM_PIN_UNKNOWN = 100;
    public static final int ERROR_UNKNOWN = -2;
    public static final String EXTRA_MSG_ID = "id";
    public static final String EXTRA_SIM_PIN_INTERNAL =
            "com.samsung.android.knox.intent.extra.SIM_PIN_INTERNAL";
    public static final String FILE_PATH = "file-path";
    public static final int LIMIT_NUMBER_OF_CALLS_BY_DAY = 0;
    public static final int LIMIT_NUMBER_OF_CALLS_BY_MONTH = 2;
    public static final int LIMIT_NUMBER_OF_CALLS_BY_WEEK = 1;
    public static final int LIMIT_NUMBER_OF_DATA_CALLS_BY_DAY = 0;
    public static final int LIMIT_NUMBER_OF_DATA_CALLS_BY_MONTH = 2;
    public static final int LIMIT_NUMBER_OF_DATA_CALLS_BY_WEEK = 1;
    public static final int LIMIT_NUMBER_OF_SMS_BY_DAY = 0;
    public static final int LIMIT_NUMBER_OF_SMS_BY_MONTH = 2;
    public static final int LIMIT_NUMBER_OF_SMS_BY_WEEK = 1;
    public static final String METHOD_NOT_COMPATIBLE_WITH_SIM_SPECIFICATION =
            "This method does not support iccId being specified by administrator.";
    public static final int OP_DATA_ALLOWED = 1;
    public static final int OP_INCOMING_CALL_ALLOWED = 2;
    public static final int OP_INCOMING_SMS_ALLOWED = 4;
    public static final int OP_MMS_ALLOWED = 6;
    public static final int OP_OUTGOING_CALL_ALLOWED = 3;
    public static final int OP_OUTGOING_SMS_ALLOWED = 5;
    public static final String PERMISSION_NOTIFY_ICCID_AVAILABLE_INTERNAL =
            "com.samsung.android.knox.permission.KNOX_NOTIFY_ICCID_AVAILABLE_INTERNAL";
    public static final int RCS_FEATURE_ALL = 1;
    public static final String REMOTE_URI = "remote-uri";
    public static final String SENDER_ALIAS = "sender-alias";
    public static final int SIM_SLOT_0 = 0;
    public static final int SIM_SLOT_1 = 1;
    public static final int SUCCESS = 0;
    public static String TAG = "PhoneRestrictionPolicy";
    public static final String THUMBNAIL_PATH = "thumbnail-path";
    public static final String TIMESTAMP = "timestamp";
    public ContextInfo mContextInfo;
    public String mIccId;
    public IPhoneRestrictionPolicy mService;

    public PhoneRestrictionPolicy(ContextInfo contextInfo, String str) {
        this.mContextInfo = contextInfo;
        this.mIccId = str;
    }

    public boolean addIncomingCallExceptionPattern(String str) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.addIncomingCallExceptionPattern");
        if (getService(false) != null) {
            try {
                return getService(false).addIncomingCallExceptionPattern(this.mContextInfo, str);
            } catch (RemoteException e) {
                RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Failed addIncomingCallExceptionPattern"), TAG);
            }
        }
        return false;
    }

    public boolean addIncomingCallRestriction(String str) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.addIncomingCallRestriction");
        if (getService(false) != null) {
            try {
                return getService(false).addIncomingCallRestriction(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean addIncomingSmsExceptionPattern(String str) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.addIncomingSmsExceptionPattern");
        if (getService(false) != null) {
            try {
                return getService(false).addIncomingSmsExceptionPattern(this.mContextInfo, str);
            } catch (RemoteException e) {
                RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Failed addIncomingSmsExceptionPattern"), TAG);
            }
        }
        return false;
    }

    public boolean addIncomingSmsRestriction(String str) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.addIncomingSmsRestriction");
        if (getService(false) != null) {
            try {
                return getService(false).addIncomingSmsRestriction(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean addNumberOfIncomingCalls() {
        if (getService(false) != null) {
            try {
                return getService(false).addNumberOfIncomingCalls();
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean addNumberOfIncomingSms() {
        if (getService(false) != null) {
            try {
                return getService(false).addNumberOfIncomingSms();
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean addNumberOfOutgoingCalls() {
        if (getService(false) != null) {
            try {
                return getService(false).addNumberOfOutgoingCalls();
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean addNumberOfOutgoingSms() {
        if (getService(false) != null) {
            try {
                return getService(false).addNumberOfOutgoingSms();
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean addOutgoingCallExceptionPattern(String str) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.addOutgoingCallExceptionPattern");
        if (getService(false) != null) {
            try {
                return getService(false).addOutgoingCallExceptionPattern(this.mContextInfo, str);
            } catch (RemoteException e) {
                RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Failed addOutgoingCallExceptionPattern"), TAG);
            }
        }
        return false;
    }

    public boolean addOutgoingCallRestriction(String str) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.addOutgoingCallRestriction");
        if (getService(false) != null) {
            try {
                return getService(false).addOutgoingCallRestriction(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean addOutgoingSmsExceptionPattern(String str) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.addOutgoingSmsExceptionPattern");
        if (getService(false) != null) {
            try {
                return getService(false).addOutgoingSmsExceptionPattern(this.mContextInfo, str);
            } catch (RemoteException e) {
                RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Failed addOutgoingSmsExceptionPattern"), TAG);
            }
        }
        return false;
    }

    public boolean addOutgoingSmsRestriction(String str) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.addOutgoingSmsRestriction");
        if (getService(false) != null) {
            try {
                return getService(false).addOutgoingSmsRestriction(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean allowCallerIDDisplay(boolean z) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.allowCallerIDDisplay");
        if (getService(false) != null) {
            try {
                return getService(false).allowCallerIDDisplay(this.mContextInfo, z);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to block caller id display ");
            }
        }
        return false;
    }

    public boolean allowCopyContactToSim(boolean z) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.allowCopyContactToSim");
        if (getService(false) != null) {
            try {
                return getService(false).allowCopyContactToSim(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with security policy", e);
            }
        }
        return false;
    }

    public int allowDataNetworkFromSimSlot(int i, boolean z) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.allowDataNetworkFromSimSlot");
        if (getService(false) == null) {
            return -2;
        }
        try {
            return getService(false).allowDataNetworkFromSimSlot(this.mContextInfo, i, z);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.w(TAG, "failed talking with phoneRestrictionPolicy");
            return -2;
        }
    }

    public int allowIncomingCallFromSimSlot(int i, boolean z) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.allowIncomingCallFromSimSlot");
        if (getService(false) == null) {
            return -2;
        }
        try {
            return getService(false).allowIncomingCallFromSimSlot(this.mContextInfo, i, z);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.w(TAG, "failed talking with phoneRestrictionPolicy");
            return -2;
        }
    }

    public boolean allowIncomingMms(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PhoneRestrictionPolicy.allowIncomingMms");
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).allowIncomingMms(this.mContextInfo, z);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to set incoming MMS");
            return true;
        }
    }

    public boolean allowIncomingSms(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PhoneRestrictionPolicy.allowIncomingSms");
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).allowIncomingSms(this.mContextInfo, z);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to set incoming SMS");
            return true;
        }
    }

    public int allowIncomingSmsFromSimSlot(int i, boolean z) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.allowIncomingSmsFromSimSlot");
        if (getService(false) == null) {
            return -2;
        }
        try {
            return getService(false).allowIncomingSmsFromSimSlot(this.mContextInfo, i, z);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.w(TAG, "failed talking with phoneRestrictionPolicy");
            return -2;
        }
    }

    public int allowMmsFromSimSlot(int i, boolean z) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.allowMmsFromSimSlot");
        if (getService(false) == null) {
            return -2;
        }
        try {
            return getService(false).allowMmsFromSimSlot(this.mContextInfo, i, z);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.w(TAG, "failed talking with phoneRestrictionPolicy");
            return -2;
        }
    }

    public int allowOutgoingCallFromSimSlot(int i, boolean z) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.allowOutgoingCallFromSimSlot");
        if (getService(false) == null) {
            return -2;
        }
        try {
            return getService(false).allowOutgoingCallFromSimSlot(this.mContextInfo, i, z);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.w(TAG, "failed talking with phoneRestrictionPolicy");
            return -2;
        }
    }

    public boolean allowOutgoingMms(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PhoneRestrictionPolicy.allowOutgoingMms");
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).allowOutgoingMms(this.mContextInfo, z);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to set outgoing MMS");
            return true;
        }
    }

    public boolean allowOutgoingSms(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PhoneRestrictionPolicy.allowOutgoingSms");
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).allowOutgoingSms(this.mContextInfo, z);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to set outgoing SMS");
            return true;
        }
    }

    public int allowOutgoingSmsFromSimSlot(int i, boolean z) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.allowOutgoingSmsFromSimSlot");
        if (getService(false) == null) {
            return -2;
        }
        try {
            return getService(false).allowOutgoingSmsFromSimSlot(this.mContextInfo, i, z);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.w(TAG, "failed talking with phoneRestrictionPolicy");
            return -2;
        }
    }

    public boolean allowWapPush(boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PhoneRestrictionPolicy.allowWapPush");
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).allowWapPush(this.mContextInfo, z);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to allow wap push");
            return true;
        }
    }

    public boolean blockMmsWithStorage(boolean z) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.blockMmsWithStorage");
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).blockMmsWithStorage(this.mContextInfo, z);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to block MMS with storage");
            return true;
        }
    }

    public boolean blockSmsWithStorage(boolean z) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.blockSmsWithStorage");
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).blockSmsWithStorage(this.mContextInfo, z);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to block SMS with storage");
            return true;
        }
    }

    public boolean canIncomingCall(String str) {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).canIncomingCall(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with phone restriction policy", e);
            return true;
        }
    }

    public boolean canIncomingSms(String str) {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).canIncomingSms(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with phone restriction policy", e);
            return true;
        }
    }

    public boolean canOutgoingCall(String str) {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).canOutgoingCall(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with phone restriction policy", e);
            return true;
        }
    }

    public boolean canOutgoingSms(String str) {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).canOutgoingSms(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with phone restriction policy", e);
            return true;
        }
    }

    public int changeSimPinCode(String str, String str2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PhoneRestrictionPolicy.changeSimPinCode");
        if (getService(true) == null) {
            return 100;
        }
        try {
            return getService(true).changeSimPinCode(this.mContextInfo, this.mIccId, str, str2);
        } catch (RemoteException e) {
            RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Failed changeSimPinCode"), TAG);
            return 100;
        }
    }

    public boolean checkDataCallLimit() {
        if (getService(false) != null) {
            try {
                return getService(false).checkDataCallLimit();
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to checkDataCallLimit");
            }
        }
        return false;
    }

    public boolean checkEnableUseOfPacketData(boolean z) {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).checkEnableUseOfPacketData(z);
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to checkEnableUseOfPacketData");
            return true;
        }
    }

    public boolean clearStoredBlockedMms() {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.clearStoredBlockedMms");
        if (getService(false) != null) {
            try {
                return getService(false).clearStoredBlockedMms(this.mContextInfo);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to clear stored blocked mms");
            }
        }
        return false;
    }

    public boolean clearStoredBlockedSms() {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.clearStoredBlockedSms");
        if (getService(false) != null) {
            try {
                return getService(false).clearStoredBlockedSms(this.mContextInfo);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to clear stored blocked sms");
            }
        }
        return false;
    }

    public boolean decreaseNumberOfOutgoingSms() {
        if (getService(false) != null) {
            try {
                return getService(false).decreaseNumberOfOutgoingSms();
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public int disableSimPinLock(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PhoneRestrictionPolicy.disableSimPinLock");
        if (getService(true) == null) {
            return 100;
        }
        try {
            return getService(true)
                    .lockUnlockCorporateSimCard(this.mContextInfo, this.mIccId, str, false);
        } catch (RemoteException e) {
            RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Failed disableSimPinLock"), TAG);
            return 100;
        }
    }

    public boolean enableLimitNumberOfCalls(boolean z) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.enableLimitNumberOfCalls");
        if (getService(false) != null) {
            try {
                return getService(false).enableLimitNumberOfCalls(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean enableLimitNumberOfSms(boolean z) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.enableLimitNumberOfSms");
        if (getService(false) != null) {
            try {
                return getService(false).enableLimitNumberOfSms(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public int enableSimPinLock(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PhoneRestrictionPolicy.enableSimPinLock");
        if (getService(true) == null) {
            return 100;
        }
        try {
            return getService(true)
                    .lockUnlockCorporateSimCard(this.mContextInfo, this.mIccId, str, true);
        } catch (RemoteException e) {
            RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Failed enableSimPinLock"), TAG);
            return 100;
        }
    }

    public boolean getDataCallLimitEnabled() {
        if (getService(false) != null) {
            try {
                return getService(false).getDataCallLimitEnabled(this.mContextInfo);
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to getDataCallLimitEnabled");
            }
        }
        return false;
    }

    public String getDisclaimerText() {
        if (getService(false) == null) {
            return null;
        }
        try {
            return getService(false).getDisclaimerText(this.mContextInfo);
        } catch (RemoteException e) {
            RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Failed getDisclaimerText"), TAG);
            return null;
        }
    }

    public boolean getEmergencyCallOnly(boolean z) {
        if (getService(false) != null) {
            try {
                return getService(false).getEmergencyCallOnly(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public String getIncomingCallExceptionPatterns() {
        if (getService(false) == null) {
            return null;
        }
        try {
            return getService(false).getIncomingCallExceptionPatterns(this.mContextInfo);
        } catch (RemoteException e) {
            RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Failed getIncomingCallExceptionPatterns"), TAG);
            return null;
        }
    }

    public String getIncomingCallRestriction(boolean z) {
        if (getService(false) == null) {
            return null;
        }
        try {
            return getService(false).getIncomingCallRestriction(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with phone restriction policy", e);
            return null;
        }
    }

    public String getIncomingSmsExceptionPatterns() {
        if (getService(false) == null) {
            return null;
        }
        try {
            return getService(false).getIncomingSmsExceptionPatterns(this.mContextInfo);
        } catch (RemoteException e) {
            RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Failed getIncomingSmsExceptionPatterns"), TAG);
            return null;
        }
    }

    public String getIncomingSmsRestriction(boolean z) {
        if (getService(false) == null) {
            return null;
        }
        try {
            return getService(false).getIncomingSmsRestriction(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with phone restriction policy", e);
            return null;
        }
    }

    public long getLimitOfDataCalls(int i) {
        if (getService(false) == null) {
            return -1L;
        }
        try {
            return getService(false).getLimitOfDataCalls(this.mContextInfo, i);
        } catch (RemoteException unused) {
            Log.e(TAG, "Failed to getLimitOfDataCalls");
            return -1L;
        }
    }

    public int getLimitOfIncomingCalls(int i) {
        if (getService(false) == null) {
            return -1;
        }
        try {
            return getService(false).getLimitOfIncomingCalls(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with phone restriction policy", e);
            return -1;
        }
    }

    public int getLimitOfIncomingSms(int i) {
        if (getService(false) == null) {
            return -1;
        }
        try {
            return getService(false).getLimitOfIncomingSms(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with phone restriction policy", e);
            return -1;
        }
    }

    public int getLimitOfOutgoingCalls(int i) {
        if (getService(false) == null) {
            return -1;
        }
        try {
            return getService(false).getLimitOfOutgoingCalls(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with phone restriction policy", e);
            return -1;
        }
    }

    public int getLimitOfOutgoingSms(int i) {
        if (getService(false) == null) {
            return -1;
        }
        try {
            return getService(false).getLimitOfOutgoingSms(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with phone restriction policy", e);
            return -1;
        }
    }

    public String getOutgoingCallExceptionPatterns() {
        if (getService(false) == null) {
            return null;
        }
        try {
            return getService(false).getOutgoingCallExceptionPatterns(this.mContextInfo);
        } catch (RemoteException e) {
            RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Failed getOutgoingCallExceptionPatterns"), TAG);
            return null;
        }
    }

    public String getOutgoingCallRestriction(boolean z) {
        if (getService(false) == null) {
            return null;
        }
        try {
            return getService(false).getOutgoingCallRestriction(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with phone restriction policy", e);
            return null;
        }
    }

    public String getOutgoingSmsExceptionPatterns() {
        if (getService(false) == null) {
            return null;
        }
        try {
            return getService(false).getOutgoingSmsExceptionPatterns(this.mContextInfo);
        } catch (RemoteException e) {
            RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Failed getOutgoingSmsExceptionPatterns"), TAG);
            return null;
        }
    }

    public String getOutgoingSmsRestriction(boolean z) {
        if (getService(false) == null) {
            return null;
        }
        try {
            return getService(false).getOutgoingSmsRestriction(this.mContextInfo, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with phone restriction policy", e);
            return null;
        }
    }

    public String getPinCode(String str) {
        if (getService(true) == null) {
            return ApnSettings.MVNO_NONE;
        }
        try {
            return getService(true).getPinCode(str);
        } catch (RemoteException e) {
            RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Failed getPinCode"), TAG);
            return ApnSettings.MVNO_NONE;
        }
    }

    public Bundle getRCSMessage(long j) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getRCSMessage");
        EnterpriseLicenseManager.log(this.mContextInfo, "PhoneRestrictionPolicy.getRCSMessage");
        if (getService(false) != null) {
            try {
                return getService(false).getRCSMessage(this.mContextInfo, j);
            } catch (RemoteException e) {
                RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Failed getRCSMessage"), TAG);
            }
        }
        return new Bundle();
    }

    public final IPhoneRestrictionPolicy getService() {
        return getService(false);
    }

    public boolean isBlockMmsWithStorageEnabled() {
        if (getService(false) != null) {
            try {
                return getService(false).isBlockMmsWithStorageEnabled(this.mContextInfo);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to get status of block MMS with storage");
            }
        }
        return false;
    }

    public boolean isBlockSmsWithStorageEnabled() {
        if (getService(false) != null) {
            try {
                return getService(false).isBlockSmsWithStorageEnabled(this.mContextInfo);
            } catch (RemoteException unused) {
                Log.w(TAG, "Failed to get status of block SMS with storage");
            }
        }
        return false;
    }

    public boolean isCallerIDDisplayAllowed() {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).isCallerIDDisplayAllowed(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed getting caller id display status");
            return true;
        }
    }

    public boolean isCopyContactToSimAllowed() {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).isCopyContactToSimAllowed(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with security policy", e);
            return true;
        }
    }

    public boolean isDataAllowedFromSimSlot(int i) {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).isDataAllowedFromSimSlot(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.w(TAG, "failed talking with phoneRestrictionPolicy");
            return true;
        }
    }

    public boolean isIncomingCallAllowedFromSimSlot(int i) {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).isIncomingCallAllowedFromSimSlot(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.w(TAG, "failed talking with phoneRestrictionPolicy");
            return true;
        }
    }

    public boolean isIncomingMmsAllowed() {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).isIncomingMmsAllowed(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to get incoming MMS status");
            return true;
        }
    }

    public boolean isIncomingSmsAllowed() {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).isIncomingSmsAllowed(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to get incoming SMS status");
            return true;
        }
    }

    public boolean isIncomingSmsAllowedFromSimSlot(int i) {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).isIncomingSmsAllowedFromSimSlot(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.w(TAG, "failed talking with phoneRestrictionPolicy");
            return true;
        }
    }

    public boolean isLimitNumberOfCallsEnabled() {
        if (getService(false) != null) {
            try {
                return getService(false).isLimitNumberOfCallsEnabled(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean isLimitNumberOfSmsEnabled() {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).isLimitNumberOfSmsEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with phone restriction policy", e);
            return true;
        }
    }

    public boolean isMmsAllowedFromSimSlot(int i) {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).isMmsAllowedFromSimSlot(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.w(TAG, "failed talking with phoneRestrictionPolicy");
            return true;
        }
    }

    public boolean isOutgoingCallAllowedFromSimSlot(int i) {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).isOutgoingCallAllowedFromSimSlot(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.w(TAG, "failed talking with phoneRestrictionPolicy");
            return true;
        }
    }

    public boolean isOutgoingMmsAllowed() {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).isOutgoingMmsAllowed(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to get outgoing MMS");
            return true;
        }
    }

    public boolean isOutgoingSmsAllowed() {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).isOutgoingSmsAllowed(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to get outgoing SMS");
            return true;
        }
    }

    public boolean isOutgoingSmsAllowedFromSimSlot(int i) {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).isOutgoingSmsAllowedFromSimSlot(i);
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.w(TAG, "failed talking with phoneRestrictionPolicy");
            return true;
        }
    }

    public boolean isRCSEnabled(int i) {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).isRCSEnabled(this.mContextInfo, i, false);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with phone restriction policy", e);
            return true;
        }
    }

    public boolean isSimLockedByAdmin(String str) {
        if (getService(true) == null) {
            return false;
        }
        try {
            return getService(true).isSimLockedByAdmin(str);
        } catch (RemoteException e) {
            RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Failed isSimLockedByAdmin"), TAG);
            return false;
        }
    }

    public boolean isWapPushAllowed() {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).isWapPushAllowed(this.mContextInfo);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to get status of allow wap push");
            return true;
        }
    }

    public boolean removeIncomingCallExceptionPattern() {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.removeIncomingCallExceptionPattern");
        if (getService(false) != null) {
            try {
                return getService(false).removeIncomingCallExceptionPattern(this.mContextInfo);
            } catch (RemoteException e) {
                RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Failed removeIncomingCallExceptionPattern"), TAG);
            }
        }
        return false;
    }

    public boolean removeIncomingCallRestriction() {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.removeIncomingCallRestriction");
        if (getService(false) != null) {
            try {
                return getService(false).removeIncomingCallRestriction(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean removeIncomingSmsExceptionPattern() {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.removeIncomingSmsExceptionPattern");
        if (getService(false) != null) {
            try {
                return getService(false).removeIncomingSmsExceptionPattern(this.mContextInfo);
            } catch (RemoteException e) {
                RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Failed removeIncomingSmsExceptionPattern"), TAG);
            }
        }
        return false;
    }

    public boolean removeIncomingSmsRestriction() {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.removeIncomingSmsRestriction");
        if (getService(false) != null) {
            try {
                return getService(false).removeIncomingSmsRestriction(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean removeOutgoingCallExceptionPattern() {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.removeOutgoingCallExceptionPattern");
        if (getService(false) != null) {
            try {
                return getService(false).removeOutgoingCallExceptionPattern(this.mContextInfo);
            } catch (RemoteException e) {
                RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Failed removeOutgoingCallExceptionPattern"), TAG);
            }
        }
        return false;
    }

    public boolean removeOutgoingCallRestriction() {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.removeOutgoingCallRestriction");
        if (getService(false) != null) {
            try {
                return getService(false).removeOutgoingCallRestriction(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean removeOutgoingSmsExceptionPattern() {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.removeOutgoingSmsExceptionPattern");
        if (getService(false) != null) {
            try {
                return getService(false).removeOutgoingSmsExceptionPattern(this.mContextInfo);
            } catch (RemoteException e) {
                RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Failed removeOutgoingSmsExceptionPattern"), TAG);
            }
        }
        return false;
    }

    public boolean removeOutgoingSmsRestriction() {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.removeOutgoingSmsRestriction");
        if (getService(false) != null) {
            try {
                return getService(false).removeOutgoingSmsRestriction(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean resetCallsCount() {
        EnterpriseLicenseManager.log(this.mContextInfo, "PhoneRestrictionPolicy.resetCallsCount");
        if (getService(false) != null) {
            try {
                return getService(false).resetCallsCount(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean resetDataCallLimitCounter() {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.resetDataCallLimitCounter");
        if (getService(false) != null) {
            try {
                return getService(false).resetDataCallLimitCounter(this.mContextInfo);
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to resetDataCallLimitCounter");
            }
        }
        return false;
    }

    public boolean resetSmsCount() {
        EnterpriseLicenseManager.log(this.mContextInfo, "PhoneRestrictionPolicy.resetSmsCount");
        if (getService(false) != null) {
            try {
                return getService(false).resetSmsCount(this.mContextInfo);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean setDataCallLimitEnabled(boolean z) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.setDataCallLimitEnabled");
        if (getService(false) != null) {
            try {
                return getService(false).setDataCallLimitEnabled(this.mContextInfo, z);
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to setDataCallLimitEnabled");
            }
        }
        return false;
    }

    public boolean setDisclaimerText(String str) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PhoneRestrictionPolicy.setDisclaimerText");
        if (getService(false) != null) {
            try {
                return getService(false).setDisclaimerText(this.mContextInfo, str);
            } catch (RemoteException e) {
                RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Failed setDisclaimerText"), TAG);
            }
        }
        return false;
    }

    public boolean setEmergencyCallOnly(boolean z) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.setEmergencyCallOnly");
        if (getService(false) != null) {
            try {
                return getService(false).setEmergencyCallOnly(this.mContextInfo, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean setIncomingCallExceptionPattern(String str) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.setIncomingCallExceptionPattern");
        if (getService(false) != null) {
            try {
                return getService(false).setIncomingCallExceptionPattern(this.mContextInfo, str);
            } catch (RemoteException e) {
                RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Failed setIncomingCallExceptionPattern"), TAG);
            }
        }
        return false;
    }

    public boolean setIncomingCallRestriction(String str) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.setIncomingCallRestriction");
        if (getService(false) != null) {
            try {
                return getService(false).setIncomingCallRestriction(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean setIncomingSmsExceptionPattern(String str) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.setIncomingSmsExceptionPattern");
        if (getService(false) != null) {
            try {
                return getService(false).setIncomingSmsExceptionPattern(this.mContextInfo, str);
            } catch (RemoteException e) {
                RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Failed setIncomingSmsExceptionPattern"), TAG);
            }
        }
        return false;
    }

    public boolean setIncomingSmsRestriction(String str) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.setIncomingSmsRestriction");
        if (getService(false) != null) {
            try {
                return getService(false).setIncomingSmsRestriction(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean setLimitOfDataCalls(long j, long j2, long j3) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.setLimitOfDataCalls");
        if (getService(false) != null) {
            try {
                return getService(false).setLimitOfDataCalls(this.mContextInfo, j, j2, j3);
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed to setLimitOfDataCalls");
            }
        }
        return false;
    }

    public boolean setLimitOfIncomingCalls(int i, int i2, int i3) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.setLimitOfIncomingCalls");
        if (getService(false) != null) {
            try {
                return getService(false).setLimitOfIncomingCalls(this.mContextInfo, i, i2, i3);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean setLimitOfIncomingSms(int i, int i2, int i3) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.setLimitOfIncomingSms");
        if (getService(false) != null) {
            try {
                return getService(false).setLimitOfIncomingSms(this.mContextInfo, i, i2, i3);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean setLimitOfOutgoingCalls(int i, int i2, int i3) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.setLimitOfOutgoingCalls");
        if (getService(false) != null) {
            try {
                return getService(false).setLimitOfOutgoingCalls(this.mContextInfo, i, i2, i3);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean setLimitOfOutgoingSms(int i, int i2, int i3) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.setLimitOfOutgoingSms");
        if (getService(false) != null) {
            try {
                return getService(false).setLimitOfOutgoingSms(this.mContextInfo, i, i2, i3);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean setOutgoingCallExceptionPattern(String str) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.setOutgoingCallExceptionPattern");
        if (getService(false) != null) {
            try {
                return getService(false).setOutgoingCallExceptionPattern(this.mContextInfo, str);
            } catch (RemoteException e) {
                RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Failed setOutgoingCallExceptionPattern"), TAG);
            }
        }
        return false;
    }

    public boolean setOutgoingCallRestriction(String str) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.setOutgoingCallRestriction");
        if (getService(false) != null) {
            try {
                return getService(false).setOutgoingCallRestriction(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public boolean setOutgoingSmsExceptionPattern(String str) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.setOutgoingSmsExceptionPattern");
        if (getService(false) != null) {
            try {
                return getService(false).setOutgoingSmsExceptionPattern(this.mContextInfo, str);
            } catch (RemoteException e) {
                RingerModeAffectedVolumePreferenceController$$ExternalSyntheticOutline0.m(
                        e, new StringBuilder("Failed setOutgoingSmsExceptionPattern"), TAG);
            }
        }
        return false;
    }

    public boolean setOutgoingSmsRestriction(String str) {
        EnterpriseLicenseManager.log(
                this.mContextInfo, "PhoneRestrictionPolicy.setOutgoingSmsRestriction");
        if (getService(false) != null) {
            try {
                return getService(false).setOutgoingSmsRestriction(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
        return false;
    }

    public int setRCSEnabled(int i, boolean z) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PhoneRestrictionPolicy.setRCSEnabled");
        if (getService(false) == null) {
            return -2;
        }
        try {
            return getService(false).setRCSEnabled(this.mContextInfo, i, z);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with phone restriction policy", e);
            return -2;
        }
    }

    public void updateDataLimitState() {
        if (getService(false) != null) {
            try {
                getService(false).updateDataLimitState();
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with phone restriction policy", e);
            }
        }
    }

    public void updateDateAndDataCallCounters(long j) {
        if (getService(false) != null) {
            try {
                getService(false).updateDateAndDataCallCounters(j);
            } catch (RemoteException unused) {
                Log.e(TAG, "Failed talking with phone restriction policy");
            }
        }
    }

    public final IPhoneRestrictionPolicy getService(boolean z) {
        if (!z && !TextUtils.isEmpty(this.mIccId)) {
            throw new UnsupportedOperationException(METHOD_NOT_COMPATIBLE_WITH_SIM_SPECIFICATION);
        }
        if (this.mService == null) {
            this.mService =
                    IPhoneRestrictionPolicy.Stub.asInterface(
                            ServiceManager.getService("phone_restriction_policy"));
        }
        return this.mService;
    }

    public boolean isRCSEnabled(int i, int i2) {
        if (getService(false) == null) {
            return true;
        }
        try {
            return getService(false).isRCSEnabledBySimSlot(this.mContextInfo, i, false, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with phone restriction policy", e);
            return true;
        }
    }

    public int setRCSEnabled(int i, boolean z, int i2) {
        EnterpriseLicenseManager.log(this.mContextInfo, "PhoneRestrictionPolicy.setRCSEnabled");
        if (getService(false) == null) {
            return -2;
        }
        try {
            return getService(false).setRCSEnabledBySimSlot(this.mContextInfo, i, z, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with phone restriction policy", e);
            return -2;
        }
    }
}
