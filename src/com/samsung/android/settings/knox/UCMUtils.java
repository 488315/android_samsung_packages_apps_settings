package com.samsung.android.settings.knox;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;

import com.android.internal.widget.LockscreenCredential;
import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.configurator.IUniversalCredentialManager;
import com.samsung.android.knox.ucm.core.IUcmService;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentService;
import com.samsung.android.knox.zt.config.securelog.SignalSeverity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public abstract class UCMUtils {
    public static String mChildSafeMsg = null;
    public static int mPinMaxLength = 256;
    public static int mPinMinLength = 4;
    public static String miscInfo = "";

    public static boolean changeUCMLockPin(String str, String str2, String str3) {
        UniversalCredentialUtil universalCredentialUtil = UniversalCredentialUtil.getInstance();
        boolean z = false;
        if (universalCredentialUtil == null) {
            Log.d("com.samsung.android.settings.knox.UCMUtils", "failed to get UCM Util");
            return false;
        }
        try {
            Bundle changePin = universalCredentialUtil.changePin(str, str2, str3);
            if (changePin != null) {
                int i = changePin.getInt(UcmAgentService.PLUGIN_ERROR_CODE, -1);
                changePin.getBoolean(UcmAgentService.PLUGIN_BOOLEAN_RESPONSE, false);
                if (i == 0) {
                    z = true;
                }
            } else {
                Log.d("com.samsung.android.settings.knox.UCMUtils", "ChangePin bundle is null.");
            }
        } catch (SecurityException e) {
            Log.w("com.samsung.android.settings.knox.UCMUtils", "SecurityException: " + e);
        }
        return z;
    }

    public static LockscreenCredential generatePassword(int i, String str) {
        IUcmService asInterface =
                IUcmService.Stub.asInterface(
                        ServiceManager.getService("com.samsung.ucs.ucsservice"));
        if (asInterface != null) {
            try {
                Bundle generateKeyguardPassword =
                        asInterface.generateKeyguardPassword(i, str, null);
                if (generateKeyguardPassword == null) {
                    return null;
                }
                return LockscreenCredential.createSmartcardPassword(
                        generateKeyguardPassword.getByteArray(
                                UcmAgentService.PLUGIN_BYTEARRAY_RESPONSE));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            Log.d("com.samsung.android.settings.knox.UCMUtils", "mUcmBinder == null");
        }
        return null;
    }

    public static String getErrorMessage(Context context, int i) {
        String str = "\n(" + String.format("0x%08X", Integer.valueOf(i)) + ")";
        switch (i) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
                break;
            default:
                switch (i) {
                    case 257:
                    case 258:
                    case 259:
                    case 260:
                    case 261:
                    case 262:
                    case 263:
                    case 264:
                    case 265:
                    case 266:
                    case 267:
                    case 268:
                    case 269:
                    case 270:
                    case 271:
                        break;
                    default:
                        switch (i) {
                            case 4096:
                            case 8191:
                            case UcmAgentService.ERROR_APPLET_INSTALLATION /* 150994944 */:
                            case UcmAgentService.ERROR_UNREADABLE_ODE_CONFIGURATION /* 201326848 */:
                            case UcmAgentService.ERROR_INVALID_ODE_CONFIGURATION /* 201327104 */:
                                break;
                            case UcmAgentService.ERROR_APDU_CREATION /* 16777472 */:
                            case UcmAgentService.ERROR_BAD_APPLET_RESPONSE /* 16777728 */:
                            case UcmAgentService.ERROR_SMARTCARD_UNAVAILABLE /* 16777984 */:
                            case UcmAgentService.ERROR_INTERNAL_COMMUNICATION /* 16778240 */:
                            case UcmAgentService.ERROR_OPEN_SESSION_IO_EXCEPTION /* 33554945 */:
                                break;
                            case UcmAgentService.ERROR_APPLET_UNKNOWN /* 134217728 */:
                                return context.getResources()
                                                .getString(R.string.ucm_smartcard_error)
                                        + str;
                            default:
                                switch (i) {
                                    case UcmAgentService
                                            .ERROR_GET_READERS_NULL_POINTER_EXCEPTION /* 33554689 */:
                                    case UcmAgentService
                                            .ERROR_GET_READERS_ILLEGAL_STATE_EXCEPTION /* 33554690 */:
                                        break;
                                    default:
                                        switch (i) {
                                            case UcmAgentService
                                                    .ERROR_OPEN_LOGICAL_CHANNEL_IO_EXCEPTION /* 33555201 */:
                                            case UcmAgentService
                                                    .ERROR_OPEN_LOGICAL_CHANNEL_ILLEGAL_STATE_EXCEPTION /* 33555202 */:
                                            case UcmAgentService
                                                    .ERROR_OPEN_LOGICAL_CHANNEL_ILLEGAL_ARGUMENT_EXCEPTION /* 33555203 */:
                                            case UcmAgentService
                                                    .ERROR_OPEN_LOGICAL_CHANNEL_SECURITY_EXCEPTION /* 33555204 */:
                                            case UcmAgentService
                                                    .ERROR_OPEN_LOGICAL_CHANNEL_NO_SUCH_ELEMENT_EXCEPTION /* 33555205 */:
                                            case UcmAgentService
                                                    .ERROR_OPEN_LOGICAL_CHANNEL_UNKNOWN /* 33555206 */:
                                                break;
                                            default:
                                                switch (i) {
                                                    case UcmAgentService
                                                            .ERROR_TRANSMIT_IO_EXCEPTION /* 33555457 */:
                                                    case UcmAgentService
                                                            .ERROR_TRANSMIT_ILLEGAL_STATE_EXCEPTION /* 33555458 */:
                                                    case UcmAgentService
                                                            .ERROR_TRANSMIT_ILLEGAL_ARGUMENT_EXCEPTION /* 33555459 */:
                                                    case UcmAgentService
                                                            .ERROR_TRANSMIT_SECURITY_EXCEPTION /* 33555460 */:
                                                    case UcmAgentService
                                                            .ERROR_TRANSMIT_NULL_POINTER_EXCEPTION /* 33555461 */:
                                                    case UcmAgentService
                                                            .ERROR_TRANSMIT_UNKNOWN /* 33555462 */:
                                                        break;
                                                    default:
                                                        switch (i) {
                                                            case UcmAgentService
                                                                    .ERROR_NO_SESSION_AVAILABLE /* 33555713 */:
                                                            case UcmAgentService
                                                                    .ERROR_FAILED_TO_GET_READER_FOR_STORAGE /* 33555714 */:
                                                                break;
                                                            default:
                                                                switch (i) {
                                                                    case UcmAgentService
                                                                            .ERROR_SCP_UNKNOWN /* 50331648 */:
                                                                    case UcmAgentService
                                                                            .ERROR_SCP_ENCRYPTION_FAILED /* 50331649 */:
                                                                    case UcmAgentService
                                                                            .ERROR_SCP_DECRYPTION_FAILED /* 50331650 */:
                                                                    case UcmAgentService
                                                                            .ERROR_SCP_CREATE_CHANNEL_FAILED /* 50331651 */:
                                                                    case UcmAgentService
                                                                            .ERROR_SCP_NULL_RESPONSE_RECV /* 50331652 */:
                                                                        break;
                                                                    default:
                                                                        if (134217728 >= i
                                                                                || 134283264 <= i) {
                                                                            return context.getResources()
                                                                                            .getString(
                                                                                                    R
                                                                                                            .string
                                                                                                            .ucm_unknown_error)
                                                                                    + str;
                                                                        }
                                                                        String format =
                                                                                String.format(
                                                                                        "0x%08X",
                                                                                        Integer
                                                                                                .valueOf(
                                                                                                        i));
                                                                        return context.getResources()
                                                                                        .getString(
                                                                                                R
                                                                                                        .string
                                                                                                        .ucm_smartcard_error)
                                                                                + "\n("
                                                                                + format.substring(
                                                                                        format
                                                                                                        .length()
                                                                                                - 4,
                                                                                        format
                                                                                                .length())
                                                                                + ")";
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                        return context.getResources().getString(R.string.ucm_communication_error)
                                + str;
                }
        }
        return context.getResources().getString(R.string.ucm_internal_error) + str;
    }

    public static String getKeyguardStorageForUser(int i) {
        IUcmService asInterface =
                IUcmService.Stub.asInterface(
                        ServiceManager.getService("com.samsung.ucs.ucsservice"));
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i,
                "getKeyguardStorageForUser() for userId = ",
                "com.samsung.android.settings.knox.UCMUtils");
        if (asInterface == null) {
            return null;
        }
        try {
            return asInterface.getKeyguardStorageForCurrentUser(i);
        } catch (Exception e) {
            Log.e(
                    "com.samsung.android.settings.knox.UCMUtils",
                    "Error in getKeyguardStorageForUser: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static String getUCMKeyguardStorageForUser(int i) {
        IUcmService asInterface =
                IUcmService.Stub.asInterface(
                        ServiceManager.getService("com.samsung.ucs.ucsservice"));
        if (asInterface == null) {
            Log.e("com.samsung.android.settings.knox.UCMUtils", "Failed to get UCM Service");
            return null;
        }
        try {
            String keyguardStorageForCurrentUser = asInterface.getKeyguardStorageForCurrentUser(i);
            if (!TextUtils.isEmpty(keyguardStorageForCurrentUser)
                    && !keyguardStorageForCurrentUser.equalsIgnoreCase(SignalSeverity.NONE)) {
                Log.d(
                        "com.samsung.android.settings.knox.UCMUtils",
                        "getUCMKeyguardStorageForUser. UCM Keyguard enabled for user [" + i + "]");
                return keyguardStorageForCurrentUser;
            }
            Log.d(
                    "com.samsung.android.settings.knox.UCMUtils",
                    "getUCMKeyguardStorageForUser. UCM Keyguard disabled for user [" + i + "]");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUCMUri(int i) {
        String uCMKeyguardStorageForUser = getUCMKeyguardStorageForUser(i);
        if (uCMKeyguardStorageForUser == null) {
            return null;
        }
        String uri =
                UniversalCredentialUtil.getUri(uCMKeyguardStorageForUser, ApnSettings.MVNO_NONE);
        DialogFragment$$ExternalSyntheticOutline0.m(
                "getStatus, uri: ", uri, "com.samsung.android.settings.knox.UCMUtils");
        return uri;
    }

    public static boolean isEnforcedCredentialStorageExistAsUser(int i) {
        IUniversalCredentialManager asInterface =
                IUniversalCredentialManager.Stub.asInterface(
                        ServiceManager.getService("knox_ucsm_policy"));
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                i,
                "isEnforcedCredentialStorageExist() for userId = ",
                "com.samsung.android.settings.knox.UCMUtils");
        boolean z = false;
        try {
            if (asInterface == null) {
                Log.d("com.samsung.android.settings.knox.UCMUtils", "ucmService is null");
            } else if (asInterface.getEnforcedCredentialStorageForLockTypeAsUser(i) != null) {
                z = true;
            }
            return z;
        } catch (Exception e) {
            Log.e(
                    "com.samsung.android.settings.knox.UCMUtils",
                    "Error in isEnforcedCredentialStorageExist: " + e.getMessage());
            e.printStackTrace();
            return z;
        }
    }

    public static boolean isSupportBiometricForUCM(int i) {
        IUcmService asInterface =
                IUcmService.Stub.asInterface(
                        ServiceManager.getService("com.samsung.ucs.ucsservice"));
        if (asInterface == null) {
            Log.e("com.samsung.android.settings.knox.UCMUtils", "Failed to get UCM Service");
            return false;
        }
        try {
            Bundle agentInfo = asInterface.getAgentInfo(getUCMUri(i));
            if (agentInfo == null) {
                return false;
            }
            return agentInfo.getBoolean(
                    UniversalCredentialUtil.AGENT_IS_SUPPORT_BIOMETRIC_FOR_UCM, false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isSupportChangePin(int i) {
        IUcmService asInterface =
                IUcmService.Stub.asInterface(
                        ServiceManager.getService("com.samsung.ucs.ucsservice"));
        if (asInterface == null) {
            Log.e("com.samsung.android.settings.knox.UCMUtils", "Failed to get UCM Service");
            return false;
        }
        try {
            Bundle agentInfo = asInterface.getAgentInfo(getUCMUri(i));
            if (agentInfo == null) {
                return false;
            }
            return agentInfo.getBoolean(UniversalCredentialUtil.AGENT_IS_SUPPORT_CHANGE_PIN, false);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isUCMKeyguardEnabled() {
        int myUserId = UserHandle.myUserId();
        if (getUCMKeyguardStorageForUser(myUserId) == null) {
            return false;
        }
        MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                myUserId,
                "UCM Keyguard is enabled for user [",
                "]",
                "com.samsung.android.settings.knox.UCMUtils");
        return true;
    }

    public static boolean isUCMKeyguardEnabledAsUser(int i) {
        if (getUCMKeyguardStorageForUser(i) == null) {
            return false;
        }
        MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0.m(
                i,
                "UCM Keyguard is enabled for user [",
                "]",
                "com.samsung.android.settings.knox.UCMUtils");
        return true;
    }

    public static void sendKeyguardConfiguredEvent(int i, String str) {
        IUcmService asInterface =
                IUcmService.Stub.asInterface(
                        ServiceManager.getService("com.samsung.ucs.ucsservice"));
        if (asInterface == null) {
            Log.d("com.samsung.android.settings.knox.UCMUtils", "mUcmBinder == null");
            return;
        }
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("userId", i);
            if (str == null) {
                asInterface.notifyChangeToPlugin(str, 19, bundle);
                Log.d(
                        "com.samsung.android.settings.knox.UCMUtils",
                        "notifyChangeToPlugin KEYGUARD_SET : " + str);
            } else {
                asInterface.notifyChangeToPlugin(str, 18, bundle);
                Log.d(
                        "com.samsung.android.settings.knox.UCMUtils",
                        "notifyChangeToPlugin KEYGUARD_UNSET : ".concat(str));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
