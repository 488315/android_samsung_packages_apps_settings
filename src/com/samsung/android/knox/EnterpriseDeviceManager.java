package com.samsung.android.knox;

import android.annotation.NonNull;
import android.annotation.Nullable;
import android.annotation.RequiresPermission;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.Handler;
import android.os.Process;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;

import com.samsung.android.knox.accounts.DeviceAccountPolicy;
import com.samsung.android.knox.accounts.EmailAccountPolicy;
import com.samsung.android.knox.accounts.EmailPolicy;
import com.samsung.android.knox.accounts.ExchangeAccountPolicy;
import com.samsung.android.knox.accounts.LDAPAccountPolicy;
import com.samsung.android.knox.application.ApplicationPolicy;
import com.samsung.android.knox.bluetooth.BluetoothPolicy;
import com.samsung.android.knox.bluetooth.BluetoothSecureModePolicy;
import com.samsung.android.knox.browser.BrowserPolicy;
import com.samsung.android.knox.cmfa.CmfaManager;
import com.samsung.android.knox.container.BasePasswordPolicy;
import com.samsung.android.knox.custom.utils.KnoxsdkFileLog;
import com.samsung.android.knox.datetime.DateTimePolicy;
import com.samsung.android.knox.ddar.DualDARPolicy;
import com.samsung.android.knox.deviceinfo.DeviceInventory;
import com.samsung.android.knox.devicesecurity.APMPolicy;
import com.samsung.android.knox.devicesecurity.DeviceSecurityPolicy;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;
import com.samsung.android.knox.dex.DexManager;
import com.samsung.android.knox.display.Font;
import com.samsung.android.knox.hdm.HdmManager;
import com.samsung.android.knox.keystore.CertificateProvisioning;
import com.samsung.android.knox.kiosk.KioskMode;
import com.samsung.android.knox.kpcc.KPCCManager;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.location.Geofencing;
import com.samsung.android.knox.location.LocationPolicy;
import com.samsung.android.knox.lockscreen.BootBanner;
import com.samsung.android.knox.lockscreen.LockscreenOverlay;
import com.samsung.android.knox.multiuser.MultiUserManager;
import com.samsung.android.knox.net.GlobalProxy;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.apn.ApnSettingsPolicy;
import com.samsung.android.knox.net.firewall.Firewall;
import com.samsung.android.knox.net.vpn.VpnPolicy;
import com.samsung.android.knox.net.wifi.WifiPolicy;
import com.samsung.android.knox.nfc.NfcPolicy;
import com.samsung.android.knox.profile.ProfilePolicy;
import com.samsung.android.knox.remotecontrol.RemoteInjection;
import com.samsung.android.knox.restriction.PhoneRestrictionPolicy;
import com.samsung.android.knox.restriction.RestrictionPolicy;
import com.samsung.android.knox.restriction.RoamingPolicy;
import com.samsung.android.knox.restriction.SPDControlPolicy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class EnterpriseDeviceManager {
    public static final String ACTION_ADD_DEVICE_ADMIN = "android.app.action.ADD_DEVICE_ADMIN";
    public static final String ACTION_CALL_STATE_CHANGED =
            "com.samsung.android.knox.intent.action.CALL_STATE_CHANGED";
    public static final String ACTION_CHECK_REENROLLMENT =
            "edm.intent.action.sec.CHECK_REENROLLMENT";
    public static final String ACTION_CHECK_REENROLLMENT_INTERNAL =
            "com.samsung.android.knox.intent.action.CHECK_REENROLLMENT_INTERNAL";
    public static final String ACTION_DO_KEYGUARD_INTERNAL =
            "com.samsung.android.knox.intent.action.DO_KEYGUARD_INTERNAL";
    public static final String ACTION_EDM_BOOT_COMPLETED =
            "edm.intent.action.ACTION_EDM_BOOT_COMPLETED";
    public static final String ACTION_EDM_BOOT_COMPLETED_INTERNAL =
            "com.samsung.android.knox.intent.action.EDM_BOOT_COMPLETED_INTERNAL";
    public static final String ACTION_HARD_KEY_PRESS =
            "com.samsung.android.knox.intent.action.HARD_KEY_PRESS";
    public static final String ACTION_KEYGUARD_REFRESH_INTERNAL =
            "com.samsung.android.knox.intent.action.KEYGUARD_REFRESH_INTERNAL";
    public static final String ACTION_KNOX_RESTRICTIONS_CHANGED =
            "com.samsung.android.knox.intent.action.KNOX_RESTRICTIONS_CHANGED_INTERNAL";
    public static final String ACTION_MAM_KNOX_PRIVACY_POLICY_CHANGED_BY_USER =
            "com.samsung.android.knox.intent.action.MAM_KNOX_PRIVACY_POLICY_CHANGED_BY_USER";
    public static final String ACTION_MTP_BLOCKED_INTERNAL =
            "com.samsung.android.knox.intent.action.MTP_BLOCKED_INTERNAL";
    public static final String ACTION_NOTIFY_STORAGE_CARD_INTERNAL =
            "com.samsung.android.knox.intent.action.NOTIFY_STORAGE_CARD_INTERNAL";
    public static final String ACTION_NO_USER_ACTIVITY =
            "com.samsung.android.knox.intent.action.NO_USER_ACTIVITY";
    public static final String ACTION_OPERATOR_NAME_INTERNAL =
            "com.samsung.android.knox.intent.action.OPERATOR_NAME_INTERNAL";
    public static final String ACTION_QUICKSETTING_REFRESH_INTERNAL =
            "com.samsung.android.knox.intent.action.QUICKSETTING_REFRESH_INTERNAL";
    public static final String ACTION_SEND_DTMF_INTERNAL =
            "com.samsung.android.knox.intent.action.SEND_DTMF_INTERNAL";
    public static final String ACTION_SET_KEYBOARD_MODE_INTERNAL =
            "com.samsung.android.knox.intent.action.SET_KEYBOARD_MODE_INTERNAL";
    public static final String ACTION_USER_ACTIVITY =
            "com.samsung.android.knox.intent.action.USER_ACTIVITY";
    public static final String APN_SETTINGS_POLICY_SERVICE = "apn_settings_policy";
    public static final String APPLICATION_POLICY_SERVICE = "application_policy";
    public static final String AUDIT_LOG = "auditlog";
    public static final String BLUETOOTH_POLICY_SERVICE = "bluetooth_policy";
    public static final String BROWSER_SETTINGS_POLICY_SERVICE = "browser_policy";
    public static final String BT_SECURE_MODE_POLICY_SERVICE = "bluetooth_secure_mode_policy";
    public static final String CERTIFICATE_POLICY_SERVICE = "certificate_policy";
    public static final String DATE_TIME_POLICY_SERVICE = "date_time_policy";
    public static final int DEFAULT_USER_ACTIVITY_TIMEOUT = 0;
    public static final String DEVICE_ACCOUNT_POLICY_SERVICE = "device_account_policy";
    public static final String DEVICE_INVENTORY_SERVICE = "device_info";
    public static final String DEX_POLICY_SERVICE = "dex_policy";
    public static final String EAS_ACCOUNT_POLICY_SERVICE = "eas_account_policy";
    public static final String EMAIL_ACCOUNT_POLICY_SERVICE = "email_account_policy";
    public static final String EMAIL_POLICY_SERVICE = "email_policy";
    public static final String ENTERPRISE_BILLING_POLICY_SERVICE = "enterprise_billing_policy";
    public static final String ENTERPRISE_LICENSE_POLICY_SERVICE = "enterprise_license_policy";
    public static final String ENTERPRISE_POLICY_SERVICE = "enterprise_policy";
    public static final int ERROR_CRYPTO_CHECK_FAILURE = -5;
    public static final int ERROR_INVALID_FILE = -3;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_NOT_ACTIVE_ADMIN = -2;
    public static final int ERROR_PACKAGE_NAME_MISMATCH = -4;
    public static final int ERROR_UNKNOWN = -1;
    public static final String EXTRA_ADD_EXPLANATION = "android.app.extra.ADD_EXPLANATION";
    public static final String EXTRA_CALL_STATE =
            "com.samsung.android.knox.intent.extra.CALL_STATE";
    public static final String EXTRA_CURRENT_VERSION =
            "com.samsung.android.knox.intent.extra.CURRENT_VERSION";
    public static final String EXTRA_DEVICE_ADMIN = "android.app.extra.DEVICE_ADMIN";
    public static final String EXTRA_DTMF_DURATION_INTERNAL =
            "com.samsung.android.knox.intent.extra.DTMF_DURATION_INTERNAL";
    public static final String EXTRA_DTMF_TONE_INTERNAL =
            "com.samsung.android.knox.intent.extra.DTMF_TONE_INTERNAL";
    public static final String EXTRA_KEYBOARD_MODE_INTERNAL =
            "com.samsung.android.knox.intent.extra.KEYBOARD_MODE_INTERNAL";
    public static final String EXTRA_KEY_CODE = "com.samsung.android.knox.intent.extra.KEY_CODE";
    public static final String EXTRA_MIGRATION_RESULT =
            "com.samsung.android.knox.intent.extra.MIGRATION_RESULT";
    public static final String EXTRA_PHONE_STATE =
            "com.samsung.android.knox.intent.extra.PHONE_STATE";
    public static final String FIREWALL_SERVICE = "firewall";
    public static final String GEOFENCING = "geofencing";
    public static final String HDM_SERVICE = "hdm_service";
    public static final String KIOSKMODE = "kioskmode";
    public static final int KNOX_2_7_1 = 21;
    public static final int KNOX_2_8 = 22;
    public static final String KNOX_CCM_POLICY_SERVICE = "knox_ccm_policy";
    public static final String KNOX_CUSTOM_MANAGER_SERVICE = "knoxcustom";
    public static final String KNOX_KPCC_MANAGER_SERVICE = "kpcc";
    public static final String KNOX_NETWORK_ANALYTICS_SERVICE = "knoxnap";
    public static final String KNOX_NETWORK_FILTER_SERVICE = "knox_nwFilterMgr_policy";
    public static final int KNOX_NOT_SUPPORTED = -1;
    public static final String KNOX_SCEP_POLICY_SERVICE = "knox_scep_policy";
    public static final String KNOX_TIMAKEYSTORE_POLICY_SERVICE = "knox_timakeystore_policy";
    public static final String KNOX_TRUSTED_PINPAD_POLICY_SERVICE = "knox_pinpad_service";
    public static final String KNOX_UCSM_POLICY_SERVICE = "knox_ucsm_policy";
    public static final String KPE_CORE_PACKAGE_NAME = "com.samsung.android.knox.kpecore";
    public static final String LDAP_ACCOUNT_POLICY_SERVICE = "ldap_account_policy";
    public static final String LICENSE_LOG_SERVICE = "license_log_service";
    public static final String LOCATION_POLICY_SERVICE = "location_policy";
    public static final String LOCKSCREEN_OVERLAY_SERVICE = "lockscreen_overlay";
    public static final String MISC_POLICY_SERVICE = "misc_policy";
    public static final String MULTI_USER_MANAGER_SERVICE = "multi_user_manager_service";
    public static final String MUM_CONTAINER_POLICY_SERVICE = "mum_container_policy";
    public static final String MUM_CONTAINER_RCP_POLICY_SERVICE = "mum_container_rcp_policy";
    public static final int NO_AUTHORIZATION = 0;
    public static final String PASSWORD_POLICY_SERVICE = "password_policy";
    public static final int PASSWORD_QUALITY_ALPHABETIC = 262144;
    public static final int PASSWORD_QUALITY_ALPHANUMERIC = 327680;
    public static final int PASSWORD_QUALITY_NUMERIC = 131072;
    public static final int PASSWORD_QUALITY_SOMETHING = 65536;
    public static final int PASSWORD_QUALITY_UNSPECIFIED = 0;
    public static final String PHONE_RESTRICTION_POLICY_SERVICE = "phone_restriction_policy";
    public static final String PROFILE_POLICY_SERVICE = "profilepolicy";
    public static final int PROVISION_CERT_MASK = 2;
    public static final int REMOTE_CONTROL_MASK = 1;
    public static final String REMOTE_INJECTION_SERVICE = "remoteinjection";
    public static final int RESET_PASSWORD_REQUIRE_ENTRY = 1;
    public static final String RESTRICTION_POLICY_SERVICE = "restriction_policy";
    public static final String ROAMING_POLICY_SERVICE = "roaming_policy";
    public static final String SECURITY_POLICY_SERVICE = "security_policy";
    public static final String SMARTCARD_BROWSER_POLICY_SERVICE = "smartcard_browser_policy";
    public static final String SMARTCARD_EMAIL_POLICY_SERVICE = "smartcard_email_policy";
    public static String TAG = "EnterpriseDeviceManager";
    public static final String THREAT_DEFENSE_SERVICE = "threat_defense_service";
    public static final int USER_ACTIVE = 91;
    public static final int USER_CREATION_IN_PROGRESS = 93;
    public static final int USER_DOESNT_EXISTS = -1;
    public static final int USER_LOCKED = 95;
    public static final String VPN_POLICY_SERVICE = "vpn_policy";
    public static final String WIFI_POLICY_SERVICE = "wifi_policy";
    public static final int WIPE_EXTERNAL_STORAGE = 1;
    public static EnterpriseDeviceManager mParentInstance;
    public static final Object mSync = new Object();
    public static EnterpriseDeviceManager sEnterpriseDeviceManager;
    public volatile APMPolicy mAPMPolicy;
    public volatile ApnSettingsPolicy mApnSettingsPolicy;
    public volatile ApplicationPolicy mApplicationPolicy;
    public volatile BluetoothSecureModePolicy mBTSecureModePolicy;
    public volatile BluetoothPolicy mBluetoothPolicy;
    public volatile BootBanner mBootBanner;
    public volatile BrowserPolicy mBrowserPolicy;
    public volatile CertificateProvisioning mCertificateProvisioning;
    public volatile CmfaManager mCmfaManager;
    public final Context mContext;
    public final ContextInfo mContextInfo;
    public DevicePolicyManager mDPM;
    public volatile DateTimePolicy mDateTimePolicy;
    public volatile DeviceAccountPolicy mDeviceAccountPolicy;
    public volatile DeviceInventory mDeviceInventory;
    public volatile DeviceSecurityPolicy mDeviceSecurityPolicy;
    public volatile DexManager mDexManager;
    public volatile DualDARPolicy mDualDARPolicy;
    public volatile ExchangeAccountPolicy mEasAccountPolicy;
    public volatile EmailAccountPolicy mEmailAccountPolicy;
    public volatile EmailPolicy mEmailPolicy;
    public volatile EnterpriseLicenseManager mEnterpriseLicenseManager;
    public volatile Firewall mFirewall;
    public volatile Font mFont;
    public volatile Geofencing mGeofencing;
    public volatile GlobalProxy mGlobalProxy;
    public volatile HdmManager mHdmManager;
    public volatile KPCCManager mKPCCManager;
    public volatile KioskMode mKioskMode;
    public volatile LDAPAccountPolicy mLDAPAccountPolicy;
    public volatile LocationPolicy mLocationPolicy;
    public volatile LockscreenOverlay mLockscreenOverlay;
    public volatile MultiUserManager mMultiUserManager;
    public volatile NfcPolicy mNfcPolicy;
    public volatile PasswordPolicy mPasswordPolicy;
    public volatile Map<String, PhoneRestrictionPolicy> mPhoneRestrictionMap;
    public volatile ProfilePolicy mProfilePolicy;
    public HashMap<Integer, ProfilePolicy> mProfilePolicyMap;
    public volatile RemoteInjection mRemoteInjection;
    public volatile RestrictionPolicy mRestrictionPolicy;
    public volatile RoamingPolicy mRoamingPolicy;
    public volatile SPDControlPolicy mSPDControlPolicy;
    public IEnterpriseDeviceManager mService;
    public volatile VpnPolicy mVpnPolicy;
    public volatile WifiPolicy mWifiPolicy;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public enum EnterpriseKeyVersion {
        ENTERPRISE_KEY_VERSION_1;

        public static /* synthetic */ EnterpriseKeyVersion[] $values() {
            return new EnterpriseKeyVersion[] {ENTERPRISE_KEY_VERSION_1};
        }
    }

    public EnterpriseDeviceManager(Context context) {
        this(context, true, new ContextInfo(Process.myUid()));
    }

    @Nullable
    public static EnterpriseDeviceManager create(Context context, Handler handler) {
        EnterpriseDeviceManager enterpriseDeviceManager =
                new EnterpriseDeviceManager(context, true, new ContextInfo(Process.myUid()));
        if (enterpriseDeviceManager.getService() != null) {
            return enterpriseDeviceManager;
        }
        return null;
    }

    public static boolean enforceWpcod() {
        return AccessController.enforceWpcod();
    }

    public static int getAPILevel() {
        try {
            return EdmUtils.getAPILevelForInternal();
        } catch (UnsupportedOperationException e) {
            Log.e(TAG, e.getMessage());
            return 0;
        }
    }

    public static int getAPILevelForInternal() {
        return EdmUtils.getAPILevelForInternal();
    }

    public static int getCallingUserId(ContextInfo contextInfo) {
        return EdmUtils.getCallingUserId(contextInfo);
    }

    public static int getContainerId(int i) {
        return 0;
    }

    public static int getContainerType(int i) {
        return -1;
    }

    public static EdmConstants.EnterpriseSdkVersion getEnterpriseSdkVerInternal() {
        return EdmConstants.getEnterpriseSdkVerInternal();
    }

    public static EnterpriseDeviceManager getInstance(Context context) {
        EnterpriseDeviceManager enterpriseDeviceManager;
        synchronized (mSync) {
            try {
                int myUid = Process.myUid();
                int myPid = Process.myPid();
                int myTid = Process.myTid();
                if (sEnterpriseDeviceManager == null) {
                    sEnterpriseDeviceManager = new EnterpriseDeviceManager(context);
                    KnoxsdkFileLog.d(
                            TAG,
                            "getInstance() : ("
                                    + myPid
                                    + "/"
                                    + myTid
                                    + ") create an instance with UID "
                                    + myUid);
                }
                if (sEnterpriseDeviceManager.mContextInfo.mCallerUid != myUid) {
                    KnoxsdkFileLog.w(
                            TAG,
                            "getInstance() : ("
                                    + myPid
                                    + "/"
                                    + myTid
                                    + ") currentUid is "
                                    + myUid
                                    + " but mCallerUid is "
                                    + sEnterpriseDeviceManager.mContextInfo.mCallerUid);
                }
                enterpriseDeviceManager = sEnterpriseDeviceManager;
            } catch (Throwable th) {
                throw th;
            }
        }
        return enterpriseDeviceManager;
    }

    @Nullable
    public static EnterpriseDeviceManager getParentInstance(Context context) {
        EnterpriseDeviceManager enterpriseDeviceManager;
        if (!AccessController.enforceWpcod()) {
            return null;
        }
        synchronized (mSync) {
            try {
                if (mParentInstance == null) {
                    mParentInstance = new EnterpriseDeviceManager(context, true);
                }
                enterpriseDeviceManager = mParentInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return enterpriseDeviceManager;
    }

    public static int getUserId(UserHandle userHandle) {
        if (userHandle == null) {
            return -1;
        }
        try {
            return userHandle.getIdentifier();
        } catch (Exception unused) {
            Log.w(TAG, "Failed to get user id by calling userHandle.getIdentifier()");
            return -1;
        }
    }

    public static boolean guardianMUsed() {
        return !TextUtils.isEmpty(ApnSettings.MVNO_NONE);
    }

    public static boolean inHouseManufacturing() {
        return true;
    }

    public static boolean isOfficiallySupported() throws UnsupportedOperationException {
        return true;
    }

    public static boolean jdmManufacturing() {
        return false;
    }

    public static boolean sepBasicSupported() {
        return true;
    }

    public static boolean sepLiteNewSupported() {
        return false;
    }

    public static boolean sepLiteSupported() {
        return false;
    }

    public static void throwIfParentInstance(ContextInfo contextInfo, String str) {
        AccessController.throwIfParentInstance(contextInfo, str);
    }

    public boolean activateDevicePermissions(@NonNull List<String> list) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.activateDevicePermissions(list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean addAuthorizedUid(int i, int i2) {
        if (getService() == null) {
            return false;
        }
        try {
            Log.d(TAG, "addAuthorizedUid");
            return this.mService.addAuthorizedUid(i, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public int addPseudoAdminForParent(int i) {
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.addPseudoAdminForParent(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return -1;
        }
    }

    @Nullable
    public byte[] captureUmcLogs(String str, @NonNull List<String> list) {
        if (getService() == null) {
            return null;
        }
        try {
            Log.d(TAG, "captureUmcLogs");
            return this.mService.captureUmcLogs(this.mContextInfo, str, list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return null;
        }
    }

    public boolean disableConstrainedState() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.disableConstrainedState(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        } catch (Exception e2) {
            Log.d(TAG, "exception occured! " + e2);
            return false;
        }
    }

    public boolean enableConstrainedState(
            String str, String str2, String str3, String str4, int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.enableConstrainedState(
                    this.mContextInfo, str, str2, str3, str4, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        } catch (Exception e2) {
            Log.d(TAG, "exception occured! " + e2);
            return false;
        }
    }

    public boolean enableWipe() {
        if (getService() == null) {
            return false;
        }
        try {
            Log.d(TAG, "enableWipe");
            return this.mService.enableWipe(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public void enforceActiveAdminPermission(String str) throws SecurityException {
        if (getService() != null) {
            try {
                ArrayList arrayList = new ArrayList();
                arrayList.add(str);
                this.mService.enforceActiveAdminPermission(arrayList);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public ContextInfo enforceActiveAdminPermissionByContext(ContextInfo contextInfo, String str)
            throws SecurityException {
        if (getService() != null) {
            try {
                ArrayList arrayList = new ArrayList();
                arrayList.add(str);
                return this.mService.enforceActiveAdminPermissionByContext(contextInfo, arrayList);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public void enforceComponentCheck(ContextInfo contextInfo, ComponentName componentName)
            throws SecurityException {
        if (getService() != null) {
            try {
                this.mService.enforceComponentCheck(contextInfo, componentName);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public ContextInfo enforceContainerOwnerShipPermissionByContext(
            ContextInfo contextInfo, String str) throws SecurityException {
        if (getService() != null) {
            try {
                ArrayList arrayList = new ArrayList();
                arrayList.add(str);
                return this.mService.enforceContainerOwnerShipPermissionByContext(
                        contextInfo, arrayList);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public ContextInfo enforceDoPoOnlyPermissionByContext(
            ContextInfo contextInfo, List<String> list) throws SecurityException {
        if (getService() != null) {
            try {
                return this.mService.enforceDoPoOnlyPermissionByContext(contextInfo, list);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public ContextInfo enforceOwnerOnlyAndActiveAdminPermission(ContextInfo contextInfo, String str)
            throws SecurityException {
        if (getService() != null) {
            try {
                ArrayList arrayList = new ArrayList();
                arrayList.add(str);
                return this.mService.enforceOwnerOnlyAndActiveAdminPermission(
                        contextInfo, arrayList);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public ContextInfo enforcePermissionByContext(ContextInfo contextInfo, String str)
            throws SecurityException {
        if (getService() != null) {
            try {
                ArrayList arrayList = new ArrayList();
                arrayList.add(str);
                return this.mService.enforcePermissionByContext(contextInfo, arrayList);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public void enforceZtFwCaller(String str) {
        if (getService() != null) {
            try {
                this.mService.enforceZtFwCaller(new ContextInfo(Binder.getCallingUid()), str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public APMPolicy getAPMPolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getAPMPolicy");
        APMPolicy aPMPolicy = this.mAPMPolicy;
        if (aPMPolicy == null) {
            synchronized (this) {
                try {
                    aPMPolicy = this.mAPMPolicy;
                    if (aPMPolicy == null) {
                        aPMPolicy = new APMPolicy(this.mContextInfo);
                        this.mAPMPolicy = aPMPolicy;
                    }
                } finally {
                }
            }
        }
        return aPMPolicy;
    }

    @Nullable
    public ComponentName getActiveAdminComponent() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getActiveAdminComponent();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return null;
        }
    }

    @Nullable
    public List<ComponentName> getActiveAdmins(int i) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getActiveAdmins(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return null;
        }
    }

    @Nullable
    public List<EnterpriseDeviceAdminInfo> getActiveAdminsInfo(int i) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getActiveAdminsInfo(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return null;
        }
    }

    public ContextInfo getAdminContextIfCallerInCertWhiteList(List<String> list) {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mService.getAdminContextIfCallerInCertWhiteList(list);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return null;
        }
    }

    public boolean getAdminRemovable(String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getAdminRemovable");
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getAdminRemovable(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return true;
        }
    }

    public int getAdminUidForAuthorizedUid(int i) {
        if (getService() == null) {
            return -1;
        }
        try {
            Log.d(TAG, "getAdminUidForAuthorizedUid");
            return this.mService.getAdminUidForAuthorizedUid(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return -1;
        }
    }

    public ApnSettingsPolicy getApnSettingsPolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getApnSettingsPolicy");
        ApnSettingsPolicy apnSettingsPolicy = this.mApnSettingsPolicy;
        if (apnSettingsPolicy == null) {
            synchronized (this) {
                try {
                    apnSettingsPolicy = this.mApnSettingsPolicy;
                    if (apnSettingsPolicy == null) {
                        apnSettingsPolicy = new ApnSettingsPolicy(this.mContextInfo);
                        this.mApnSettingsPolicy = apnSettingsPolicy;
                    }
                } finally {
                }
            }
        }
        return apnSettingsPolicy;
    }

    public ApplicationPolicy getApplicationPolicy() {
        ApplicationPolicy applicationPolicy = this.mApplicationPolicy;
        if (applicationPolicy == null) {
            synchronized (this) {
                try {
                    applicationPolicy = this.mApplicationPolicy;
                    if (applicationPolicy == null) {
                        applicationPolicy =
                                new ApplicationPolicy(
                                        this.mContextInfo,
                                        this.mContext,
                                        new ExternalDependencyInjectorImpl());
                        this.mApplicationPolicy = applicationPolicy;
                    }
                } finally {
                }
            }
        }
        return applicationPolicy;
    }

    public int getAuthorizedUidForAdminUid(int i) {
        if (getService() == null) {
            return -1;
        }
        try {
            Log.d(TAG, "getAuthorizedUidForAdminUid");
            return this.mService.getAuthorizedUidForAdminUid(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return -1;
        }
    }

    public BasePasswordPolicy getBasePasswordPolicy() {
        return getPasswordPolicy().getBasePasswordPolicy();
    }

    public BluetoothPolicy getBluetoothPolicy() {
        BluetoothPolicy bluetoothPolicy = this.mBluetoothPolicy;
        if (bluetoothPolicy == null) {
            synchronized (this) {
                try {
                    bluetoothPolicy = this.mBluetoothPolicy;
                    if (bluetoothPolicy == null) {
                        bluetoothPolicy = new BluetoothPolicy(this.mContextInfo);
                        this.mBluetoothPolicy = bluetoothPolicy;
                    }
                } finally {
                }
            }
        }
        return bluetoothPolicy;
    }

    public BluetoothSecureModePolicy getBluetoothSecureModePolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getBluetoothSecureModePolicy");
        BluetoothSecureModePolicy bluetoothSecureModePolicy = this.mBTSecureModePolicy;
        if (bluetoothSecureModePolicy == null) {
            synchronized (this) {
                try {
                    bluetoothSecureModePolicy = this.mBTSecureModePolicy;
                    if (bluetoothSecureModePolicy == null) {
                        bluetoothSecureModePolicy =
                                new BluetoothSecureModePolicy(this.mContextInfo);
                        this.mBTSecureModePolicy = bluetoothSecureModePolicy;
                    }
                } finally {
                }
            }
        }
        return bluetoothSecureModePolicy;
    }

    public BootBanner getBootBanner() {
        BootBanner bootBanner = this.mBootBanner;
        if (bootBanner == null) {
            synchronized (this) {
                try {
                    bootBanner = this.mBootBanner;
                    if (bootBanner == null) {
                        bootBanner = new BootBanner(this.mContextInfo);
                        this.mBootBanner = bootBanner;
                    }
                } finally {
                }
            }
        }
        return bootBanner;
    }

    public BrowserPolicy getBrowserPolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getBrowserPolicy");
        BrowserPolicy browserPolicy = this.mBrowserPolicy;
        if (browserPolicy == null) {
            synchronized (this) {
                try {
                    browserPolicy = this.mBrowserPolicy;
                    if (browserPolicy == null) {
                        browserPolicy = new BrowserPolicy(this.mContextInfo);
                        this.mBrowserPolicy = browserPolicy;
                    }
                } finally {
                }
            }
        }
        return browserPolicy;
    }

    public CertificateProvisioning getCertificateProvisioning() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getCertificateProvisioning");
        CertificateProvisioning certificateProvisioning = this.mCertificateProvisioning;
        if (certificateProvisioning == null) {
            synchronized (this) {
                try {
                    certificateProvisioning = this.mCertificateProvisioning;
                    if (certificateProvisioning == null) {
                        certificateProvisioning = new CertificateProvisioning(this.mContextInfo);
                        this.mCertificateProvisioning = certificateProvisioning;
                    }
                } finally {
                }
            }
        }
        return certificateProvisioning;
    }

    public CmfaManager getCmfaManager() {
        int aPILevel = getAPILevel();
        if (aPILevel == -1 || aPILevel > 36) {
            return null;
        }
        CmfaManager cmfaManager = this.mCmfaManager;
        if (cmfaManager == null) {
            synchronized (this) {
                try {
                    cmfaManager = this.mCmfaManager;
                    if (cmfaManager == null) {
                        cmfaManager = new CmfaManager(this.mContext);
                        this.mCmfaManager = cmfaManager;
                    }
                } finally {
                }
            }
        }
        return cmfaManager;
    }

    public int getConstrainedState() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getConstrainedState();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return 0;
        } catch (Exception e2) {
            Log.d(TAG, "Security exception occured! " + e2);
            return 0;
        }
    }

    public int getCurrentFailedPasswordAttempts() {
        return this.mDPM.getCurrentFailedPasswordAttempts();
    }

    public DateTimePolicy getDateTimePolicy() {
        DateTimePolicy dateTimePolicy = this.mDateTimePolicy;
        if (dateTimePolicy == null) {
            synchronized (this) {
                try {
                    dateTimePolicy = this.mDateTimePolicy;
                    if (dateTimePolicy == null) {
                        dateTimePolicy = new DateTimePolicy(this.mContextInfo);
                        this.mDateTimePolicy = dateTimePolicy;
                    }
                } finally {
                }
            }
        }
        return dateTimePolicy;
    }

    public DeviceAccountPolicy getDeviceAccountPolicy() {
        DeviceAccountPolicy deviceAccountPolicy = this.mDeviceAccountPolicy;
        if (deviceAccountPolicy == null) {
            synchronized (this) {
                try {
                    deviceAccountPolicy = this.mDeviceAccountPolicy;
                    if (deviceAccountPolicy == null) {
                        deviceAccountPolicy = new DeviceAccountPolicy(this.mContextInfo);
                        this.mDeviceAccountPolicy = deviceAccountPolicy;
                    }
                } finally {
                }
            }
        }
        return deviceAccountPolicy;
    }

    public DeviceInventory getDeviceInventory() {
        DeviceInventory deviceInventory = this.mDeviceInventory;
        if (deviceInventory == null) {
            synchronized (this) {
                try {
                    deviceInventory = this.mDeviceInventory;
                    if (deviceInventory == null) {
                        deviceInventory =
                                new DeviceInventory(
                                        this.mContextInfo,
                                        this.mContext,
                                        new ExternalDependencyInjectorImpl());
                        this.mDeviceInventory = deviceInventory;
                    }
                } finally {
                }
            }
        }
        return deviceInventory;
    }

    public DeviceSecurityPolicy getDeviceSecurityPolicy() {
        DeviceSecurityPolicy deviceSecurityPolicy = this.mDeviceSecurityPolicy;
        if (deviceSecurityPolicy == null) {
            synchronized (this) {
                try {
                    deviceSecurityPolicy = this.mDeviceSecurityPolicy;
                    if (deviceSecurityPolicy == null) {
                        deviceSecurityPolicy =
                                new DeviceSecurityPolicy(this.mContextInfo, this.mContext);
                        this.mDeviceSecurityPolicy = deviceSecurityPolicy;
                    }
                } finally {
                }
            }
        }
        return deviceSecurityPolicy;
    }

    public DexManager getDexManager() {
        DexManager dexManager = this.mDexManager;
        if (dexManager == null) {
            synchronized (this) {
                try {
                    dexManager = this.mDexManager;
                    if (dexManager == null) {
                        dexManager = new DexManager(this.mContextInfo);
                        this.mDexManager = dexManager;
                    }
                } finally {
                }
            }
        }
        return dexManager;
    }

    public DualDARPolicy getDualDARPolicy() {
        DualDARPolicy dualDARPolicy = this.mDualDARPolicy;
        if (dualDARPolicy == null) {
            synchronized (this) {
                try {
                    dualDARPolicy = this.mDualDARPolicy;
                    if (dualDARPolicy == null) {
                        dualDARPolicy = new DualDARPolicy(this.mContextInfo);
                        this.mDualDARPolicy = dualDARPolicy;
                    }
                } finally {
                }
            }
        }
        return dualDARPolicy;
    }

    public EmailAccountPolicy getEmailAccountPolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getEmailAccountPolicy");
        EmailAccountPolicy emailAccountPolicy = this.mEmailAccountPolicy;
        if (emailAccountPolicy == null) {
            synchronized (this) {
                try {
                    emailAccountPolicy = this.mEmailAccountPolicy;
                    if (emailAccountPolicy == null) {
                        emailAccountPolicy = new EmailAccountPolicy(this.mContextInfo);
                        this.mEmailAccountPolicy = emailAccountPolicy;
                    }
                } finally {
                }
            }
        }
        return emailAccountPolicy;
    }

    public EmailPolicy getEmailPolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getEmailPolicy");
        EmailPolicy emailPolicy = this.mEmailPolicy;
        if (emailPolicy == null) {
            synchronized (this) {
                try {
                    emailPolicy = this.mEmailPolicy;
                    if (emailPolicy == null) {
                        emailPolicy = new EmailPolicy(this.mContextInfo);
                        this.mEmailPolicy = emailPolicy;
                    }
                } finally {
                }
            }
        }
        return emailPolicy;
    }

    public EnterpriseLicenseManager getEnterpriseLicenseManager() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getEnterpriseLicenseManager");
        EnterpriseLicenseManager enterpriseLicenseManager = this.mEnterpriseLicenseManager;
        if (enterpriseLicenseManager == null) {
            synchronized (this) {
                try {
                    enterpriseLicenseManager = this.mEnterpriseLicenseManager;
                    if (enterpriseLicenseManager == null) {
                        enterpriseLicenseManager =
                                new EnterpriseLicenseManager(this.mContextInfo, this.mContext);
                        this.mEnterpriseLicenseManager = enterpriseLicenseManager;
                    }
                } finally {
                }
            }
        }
        return enterpriseLicenseManager;
    }

    public EdmConstants.EnterpriseSdkVersion getEnterpriseSdkVer() {
        return EdmConstants.getEnterpriseSdkVerInternal();
    }

    public ExchangeAccountPolicy getExchangeAccountPolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getExchangeAccountPolicy");
        ExchangeAccountPolicy exchangeAccountPolicy = this.mEasAccountPolicy;
        if (exchangeAccountPolicy == null) {
            synchronized (this) {
                try {
                    exchangeAccountPolicy = this.mEasAccountPolicy;
                    if (exchangeAccountPolicy == null) {
                        exchangeAccountPolicy = new ExchangeAccountPolicy(this.mContextInfo);
                        this.mEasAccountPolicy = exchangeAccountPolicy;
                    }
                } finally {
                }
            }
        }
        return exchangeAccountPolicy;
    }

    public Firewall getFirewall() {
        Firewall firewall = this.mFirewall;
        if (firewall == null) {
            synchronized (this) {
                try {
                    firewall = this.mFirewall;
                    if (firewall == null) {
                        firewall = new Firewall(this.mContextInfo);
                        this.mFirewall = firewall;
                    }
                } finally {
                }
            }
        }
        return firewall;
    }

    public Font getFont() {
        Font font = this.mFont;
        if (font == null) {
            synchronized (this) {
                try {
                    font = this.mFont;
                    if (font == null) {
                        font = new Font(this.mContextInfo, this.mContext);
                        this.mFont = font;
                    }
                } finally {
                }
            }
        }
        return font;
    }

    public Geofencing getGeofencing() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getGeofencing");
        Geofencing geofencing = this.mGeofencing;
        if (geofencing == null) {
            synchronized (this) {
                try {
                    geofencing = this.mGeofencing;
                    if (geofencing == null) {
                        geofencing = new Geofencing(this.mContextInfo, this.mContext);
                        this.mGeofencing = geofencing;
                    }
                } finally {
                }
            }
        }
        return geofencing;
    }

    public GlobalProxy getGlobalProxy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getGlobalProxy");
        GlobalProxy globalProxy = this.mGlobalProxy;
        if (globalProxy == null) {
            synchronized (this) {
                try {
                    globalProxy = this.mGlobalProxy;
                    if (globalProxy == null) {
                        globalProxy = new GlobalProxy(this.mContextInfo);
                        this.mGlobalProxy = globalProxy;
                    }
                } finally {
                }
            }
        }
        return globalProxy;
    }

    public HdmManager getHypervisorDeviceManager() {
        HdmManager hdmManager = this.mHdmManager;
        if (hdmManager == null) {
            synchronized (this) {
                try {
                    hdmManager = this.mHdmManager;
                    if (hdmManager == null) {
                        hdmManager = new HdmManager(this.mContextInfo);
                        this.mHdmManager = hdmManager;
                    }
                } finally {
                }
            }
        }
        return hdmManager;
    }

    public KPCCManager getKPCCManager() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getKPCCManager");
        KPCCManager kPCCManager = this.mKPCCManager;
        if (kPCCManager == null) {
            synchronized (this) {
                try {
                    kPCCManager = this.mKPCCManager;
                    if (kPCCManager == null) {
                        kPCCManager = new KPCCManager(this.mContextInfo);
                        this.mKPCCManager = kPCCManager;
                    }
                } finally {
                }
            }
        }
        return kPCCManager;
    }

    @Nullable
    public String getKPUPackageName() {
        if (getService() == null) {
            return null;
        }
        try {
            Log.d(TAG, "getKPUPackageName");
            return this.mService.getKPUPackageName();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return null;
        }
    }

    public KioskMode getKioskMode() {
        KioskMode kioskMode = this.mKioskMode;
        if (kioskMode == null) {
            synchronized (this) {
                try {
                    kioskMode = this.mKioskMode;
                    if (kioskMode == null) {
                        kioskMode = new KioskMode(this.mContextInfo, this.mContext);
                        this.mKioskMode = kioskMode;
                    }
                } finally {
                }
            }
        }
        return kioskMode;
    }

    public LDAPAccountPolicy getLDAPAccountPolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getLDAPAccountPolicy");
        LDAPAccountPolicy lDAPAccountPolicy = this.mLDAPAccountPolicy;
        if (lDAPAccountPolicy == null) {
            synchronized (this) {
                try {
                    lDAPAccountPolicy = this.mLDAPAccountPolicy;
                    if (lDAPAccountPolicy == null) {
                        lDAPAccountPolicy = new LDAPAccountPolicy(this.mContextInfo, this.mContext);
                        this.mLDAPAccountPolicy = lDAPAccountPolicy;
                    }
                } finally {
                }
            }
        }
        return lDAPAccountPolicy;
    }

    public LocationPolicy getLocationPolicy() {
        LocationPolicy locationPolicy = this.mLocationPolicy;
        if (locationPolicy == null) {
            synchronized (this) {
                try {
                    locationPolicy = this.mLocationPolicy;
                    if (locationPolicy == null) {
                        locationPolicy = new LocationPolicy(this.mContextInfo);
                        this.mLocationPolicy = locationPolicy;
                    }
                } finally {
                }
            }
        }
        return locationPolicy;
    }

    public LockscreenOverlay getLockscreenOverlay() {
        LockscreenOverlay lockscreenOverlay = this.mLockscreenOverlay;
        if (lockscreenOverlay == null) {
            synchronized (this) {
                try {
                    lockscreenOverlay = this.mLockscreenOverlay;
                    if (lockscreenOverlay == null) {
                        lockscreenOverlay = new LockscreenOverlay(this.mContextInfo, this.mContext);
                        this.mLockscreenOverlay = lockscreenOverlay;
                    }
                } finally {
                }
            }
        }
        return lockscreenOverlay;
    }

    public int getMaximumFailedPasswordsForWipe() {
        return this.mDPM.getMaximumFailedPasswordsForWipe(null);
    }

    public long getMaximumTimeToLock() {
        return this.mDPM.getMaximumTimeToLock(null);
    }

    public MultiUserManager getMultiUserManager() {
        MultiUserManager multiUserManager = this.mMultiUserManager;
        if (multiUserManager == null) {
            synchronized (this) {
                try {
                    multiUserManager = this.mMultiUserManager;
                    if (multiUserManager == null) {
                        multiUserManager = new MultiUserManager(this.mContextInfo, this.mContext);
                        this.mMultiUserManager = multiUserManager;
                    }
                } finally {
                }
            }
        }
        return multiUserManager;
    }

    public NfcPolicy getNfcPolicy() {
        NfcPolicy nfcPolicy = this.mNfcPolicy;
        if (nfcPolicy == null) {
            synchronized (this) {
                try {
                    nfcPolicy = this.mNfcPolicy;
                    if (nfcPolicy == null) {
                        nfcPolicy = new NfcPolicy(this.mContextInfo);
                        this.mNfcPolicy = nfcPolicy;
                    }
                } finally {
                }
            }
        }
        return nfcPolicy;
    }

    public ProfilePolicy getOtherProfilePolicy(int i) {
        throwIfParentInstance(this.mContextInfo, "getProfilePolicy");
        ProfilePolicy profilePolicy = this.mProfilePolicyMap.get(Integer.valueOf(i));
        if (profilePolicy == null) {
            synchronized (this) {
                if (profilePolicy == null) {
                    try {
                        profilePolicy = new ProfilePolicy(this.mContextInfo, i);
                        this.mProfilePolicyMap.put(Integer.valueOf(i), profilePolicy);
                    } finally {
                    }
                }
            }
        }
        return profilePolicy;
    }

    public String getPassword(ComponentName componentName) {
        return ApnSettings.MVNO_NONE;
    }

    public int getPasswordMaximumLength(int i) {
        return this.mDPM.getPasswordMaximumLength(i);
    }

    public int getPasswordMinimumLength() {
        return this.mDPM.getPasswordMinimumLength(null);
    }

    public PasswordPolicy getPasswordPolicy() {
        PasswordPolicy passwordPolicy = this.mPasswordPolicy;
        if (passwordPolicy == null) {
            synchronized (this) {
                try {
                    passwordPolicy = this.mPasswordPolicy;
                    if (passwordPolicy == null) {
                        passwordPolicy = new PasswordPolicy(this.mContextInfo, this.mContext);
                        this.mPasswordPolicy = passwordPolicy;
                    }
                } finally {
                }
            }
        }
        return passwordPolicy;
    }

    public int getPasswordQuality() {
        return this.mDPM.getPasswordQuality(null);
    }

    public PhoneRestrictionPolicy getPhoneRestrictionPolicy() {
        return getPhoneRestrictionPolicy(null);
    }

    public ProfilePolicy getProfilePolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getProfilePolicy");
        ProfilePolicy profilePolicy = this.mProfilePolicy;
        if (profilePolicy == null) {
            synchronized (this) {
                try {
                    profilePolicy = this.mProfilePolicy;
                    if (profilePolicy == null) {
                        profilePolicy = new ProfilePolicy(this.mContextInfo);
                        this.mProfilePolicy = profilePolicy;
                    }
                } finally {
                }
            }
        }
        return profilePolicy;
    }

    public RemoteInjection getRemoteInjection() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getRemoteInjection");
        RemoteInjection remoteInjection = this.mRemoteInjection;
        if (remoteInjection == null) {
            synchronized (this) {
                try {
                    remoteInjection = this.mRemoteInjection;
                    if (remoteInjection == null) {
                        remoteInjection = new RemoteInjection();
                        this.mRemoteInjection = remoteInjection;
                    }
                } finally {
                }
            }
        }
        return remoteInjection;
    }

    public void getRemoveWarning(ComponentName componentName, RemoteCallback remoteCallback) {
        if (getService() != null) {
            try {
                this.mService.getRemoveWarning(componentName, remoteCallback);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with device policy service", e);
            }
        }
    }

    public RestrictionPolicy getRestrictionPolicy() {
        RestrictionPolicy restrictionPolicy = this.mRestrictionPolicy;
        if (restrictionPolicy == null) {
            synchronized (this) {
                try {
                    restrictionPolicy = this.mRestrictionPolicy;
                    if (restrictionPolicy == null) {
                        restrictionPolicy = new RestrictionPolicy(this.mContextInfo, this.mContext);
                        this.mRestrictionPolicy = restrictionPolicy;
                    }
                } finally {
                }
            }
        }
        return restrictionPolicy;
    }

    public RoamingPolicy getRoamingPolicy() {
        RoamingPolicy roamingPolicy = this.mRoamingPolicy;
        if (roamingPolicy == null) {
            synchronized (this) {
                try {
                    roamingPolicy = this.mRoamingPolicy;
                    if (roamingPolicy == null) {
                        roamingPolicy = new RoamingPolicy(this.mContextInfo);
                        this.mRoamingPolicy = roamingPolicy;
                    }
                } finally {
                }
            }
        }
        return roamingPolicy;
    }

    public SPDControlPolicy getSPDControlPolicy() {
        SPDControlPolicy sPDControlPolicy = this.mSPDControlPolicy;
        if (sPDControlPolicy == null) {
            synchronized (this) {
                try {
                    sPDControlPolicy = this.mSPDControlPolicy;
                    if (sPDControlPolicy == null) {
                        sPDControlPolicy = new SPDControlPolicy(this.mContextInfo, this.mContext);
                        this.mSPDControlPolicy = sPDControlPolicy;
                    }
                } finally {
                }
            }
        }
        return sPDControlPolicy;
    }

    public final IEnterpriseDeviceManager getService() {
        if (this.mService == null) {
            this.mService =
                    IEnterpriseDeviceManager.Stub.asInterface(
                            ServiceManager.getService("enterprise_policy"));
        }
        return this.mService;
    }

    public int getUserStatus(int i) {
        AccessController.throwIfParentInstance(this.mContextInfo, "getUserStatus");
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mService.getUserStatus(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return -1;
        }
    }

    public VpnPolicy getVpnPolicy() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getVpnPolicy");
        VpnPolicy vpnPolicy = this.mVpnPolicy;
        if (vpnPolicy == null) {
            synchronized (this) {
                try {
                    vpnPolicy = this.mVpnPolicy;
                    if (vpnPolicy == null) {
                        vpnPolicy = new VpnPolicy(this.mContextInfo);
                        this.mVpnPolicy = vpnPolicy;
                    }
                } finally {
                }
            }
        }
        return vpnPolicy;
    }

    public WifiPolicy getWifiPolicy() {
        WifiPolicy wifiPolicy = this.mWifiPolicy;
        if (wifiPolicy == null) {
            synchronized (this) {
                try {
                    wifiPolicy = this.mWifiPolicy;
                    if (wifiPolicy == null) {
                        wifiPolicy = new WifiPolicy(this.mContextInfo);
                        this.mWifiPolicy = wifiPolicy;
                    }
                } finally {
                }
            }
        }
        return wifiPolicy;
    }

    public boolean hasAnyActiveAdmin() {
        if (getService() == null) {
            Log.w(TAG, "No EnterpriseDeviceManager service");
            return false;
        }
        try {
            return this.mService.hasAnyActiveAdmin();
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to get hasAnyActiveAdmin");
            return false;
        }
    }

    public boolean hasGrantedPolicy(ComponentName componentName, int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.hasGrantedPolicy(componentName, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean isActivePasswordSufficient() {
        return this.mDPM.isActivePasswordSufficient();
    }

    public boolean isAdminActive(ComponentName componentName) {
        AccessController.throwIfParentInstance(this.mContextInfo, "isAdminActive");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isAdminActive(componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean isAdminRemovable(ComponentName componentName) {
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.isAdminRemovable(componentName);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return true;
        }
    }

    public boolean isCallerValidKPU(ContextInfo contextInfo) {
        if (getService() == null) {
            Log.w(TAG, "Failed talking with enterprise policy service");
            return false;
        }
        try {
            return this.mService.isCallerValidKPU(contextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean isKPUPlatformSigned(String str, int i) {
        if (getService() == null) {
            Log.w(TAG, "Failed talking with enterprise policy service");
            return false;
        }
        try {
            return this.mService.isKPUPlatformSigned(str, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean isMdmAdminPresent() {
        if (getService() == null) {
            Log.w(TAG, "Failed talking with enterprise policy service");
            return false;
        }
        try {
            return this.mService.isMdmAdminPresent();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean isRestrictedByConstrainedState(int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isRestrictedByConstrainedState(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        } catch (Exception e2) {
            Log.d(TAG, "Security exception occured! " + e2);
            return false;
        }
    }

    public boolean isUserSelectable(String str) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isUserSelectable(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean keychainMarkedReset(ContextInfo contextInfo) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.keychainMarkedReset(contextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public void lockNow() {
        this.mDPM.lockNow();
    }

    public boolean migrateKnoxPoliciesForWpcod(int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.migrateKnoxPoliciesForWpcod(i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean packageHasActiveAdmins(String str) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.packageHasActiveAdmins(str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean packageHasActiveAdminsAsUser(String str, int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.packageHasActiveAdminsAsUser(str, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    @Nullable
    public String readUmcEnrollmentData() {
        if (getService() == null) {
            return null;
        }
        try {
            Log.d(TAG, "readUmcEnrollmentData");
            return this.mService.readUmcEnrollmentData(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return null;
        }
    }

    public void removeActiveAdmin(ComponentName componentName) {
        if (getService() != null) {
            try {
                this.mService.removeActiveAdmin(componentName);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public void removeActiveAdminFromDpm(ComponentName componentName) {
        if (getService() != null) {
            try {
                this.mService.removeActiveAdminFromDpm(
                        componentName, UserHandle.getCallingUserId());
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public boolean removeAuthorizedUid(int i, int i2) {
        if (getService() == null) {
            return false;
        }
        try {
            Log.d(TAG, "removeAuthorizedUid");
            return this.mService.removeAuthorizedUid(i, i2);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public boolean resetPassword(String str, int i) {
        return this.mDPM.resetPassword(str, i);
    }

    public void setActiveAdmin(ComponentName componentName, boolean z) {
        if (getService() != null) {
            try {
                this.mService.setActiveAdmin(componentName, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public void setActiveAdminSilent(ComponentName componentName) {
        if (getService() != null) {
            try {
                this.mService.setActiveAdminSilent(componentName);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    @RequiresPermission(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_ENTERPRISE_DEVICE_ADMIN_TAG)
    public boolean setAdminRemovable(boolean z, String str) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setAdminRemovable");
        if (getService() == null) {
            return false;
        }
        try {
            EnterpriseLicenseManager.log(
                    this.mContextInfo,
                    "EnterpriseDeviceManager.setAdminRemovable(boolean, String)");
            return this.mService.setAdminRemovable(this.mContextInfo, z, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public void setAndroidLogProperty(String str) {
        if (getService() == null) {
            Log.e(TAG, "Trying to set android log property but service was not started yet!");
            return;
        }
        try {
            this.mService.setAndroidLogProperty(str);
        } catch (RemoteException unused) {
            Log.w(TAG, "Failed to setAndroidLogProperty");
        }
    }

    public void setMaximumFailedPasswordsForWipe(int i) {
        this.mDPM.setMaximumFailedPasswordsForWipe(getActiveAdminComponent(), i);
    }

    public void setMaximumTimeToLock(long j) {
        this.mDPM.setMaximumTimeToLock(getActiveAdminComponent(), j);
    }

    public void setPasswordMinimumLength(int i) {
        this.mDPM.setPasswordMinimumLength(getActiveAdminComponent(), i);
    }

    public void setPasswordQuality(int i) {
        this.mDPM.setPasswordQuality(getActiveAdminComponent(), i);
    }

    public void setUserSelectable(int i, String str, boolean z) {
        if (getService() != null) {
            try {
                this.mService.setUserSelectable(i, str, z);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public void startDualDARServices() {
        if (getService() == null) {
            Log.w(TAG, "Failed talking with enterprise policy service");
            return;
        }
        try {
            this.mService.startDualDARServices();
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
        }
    }

    @RequiresPermission("com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION")
    public void updateNotificationExemption(String str) {
        if (getService() != null) {
            try {
                Log.d(TAG, "updateNotificationExemption");
                this.mService.updateNotificationExemption(this.mContextInfo, str);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public void wipeData(int i) {
        this.mDPM.wipeData(i);
    }

    public boolean writeUmcEnrollmentData(String str) {
        if (getService() == null) {
            return false;
        }
        try {
            Log.d(TAG, "writeUmcEnrollmentData");
            return this.mService.writeUmcEnrollmentData(this.mContextInfo, str);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    public EnterpriseDeviceManager(Context context, ContextInfo contextInfo, Handler handler) {
        this(context, false, contextInfo);
    }

    public PhoneRestrictionPolicy getPhoneRestrictionPolicy(@Nullable String str) {
        if (str == null) {
            str = ApnSettings.MVNO_NONE;
        }
        if (this.mPhoneRestrictionMap == null) {
            this.mPhoneRestrictionMap = new HashMap();
        }
        if (this.mPhoneRestrictionMap.containsKey(str)) {
            return this.mPhoneRestrictionMap.get(str);
        }
        PhoneRestrictionPolicy phoneRestrictionPolicy =
                new PhoneRestrictionPolicy(this.mContextInfo, str);
        this.mPhoneRestrictionMap.put(str, phoneRestrictionPolicy);
        return phoneRestrictionPolicy;
    }

    public EnterpriseDeviceManager(Context context, Handler handler, boolean z) {
        this(context, z, new ContextInfo(Process.myUid()));
    }

    public EnterpriseDeviceManager(Context context, Handler handler) {
        this(context);
    }

    public EnterpriseDeviceManager(
            Context context, Handler handler, boolean z, ContextInfo contextInfo) {
        this(context, z, contextInfo);
    }

    public boolean getAdminRemovable() {
        AccessController.throwIfParentInstance(this.mContextInfo, "getAdminRemovable");
        if (getService() == null) {
            return true;
        }
        try {
            return this.mService.getAdminRemovable(this.mContextInfo, null);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return true;
        }
    }

    public EnterpriseDeviceManager(Context context, boolean z, ContextInfo contextInfo) {
        this.mProfilePolicyMap = new HashMap<>();
        this.mContext = context;
        this.mDPM = (DevicePolicyManager) context.getSystemService("device_policy");
        if (z) {
            int i = contextInfo.mCallerUid;
            int i2 = contextInfo.mContainerId;
            int i3 = contextInfo.mDALessCallerUid;
            if (i == 1000 && (i = Binder.getCallingUid()) != 1000) {
                KnoxsdkFileLog.d(
                        TAG,
                        "("
                                + Process.myPid()
                                + "/"
                                + Process.myTid()
                                + ") callerUid is SYSTEM_UID but Binder.getCallingUid() returns "
                                + i,
                        new Exception("STACK TRACE"));
            }
            contextInfo = new ContextInfo(i, i2, contextInfo.mParent, i3);
        }
        this.mContextInfo = contextInfo;
    }

    public void enforceActiveAdminPermission(List<String> list) throws SecurityException {
        if (getService() != null) {
            try {
                this.mService.enforceActiveAdminPermission(list);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
    }

    public ContextInfo enforceActiveAdminPermissionByContext(
            ContextInfo contextInfo, List<String> list) throws SecurityException {
        return AccessController.enforceActiveAdminPermissionByContext(contextInfo, list);
    }

    public ContextInfo enforceContainerOwnerShipPermissionByContext(
            ContextInfo contextInfo, List<String> list) throws SecurityException {
        if (getService() != null) {
            try {
                return this.mService.enforceContainerOwnerShipPermissionByContext(
                        contextInfo, list);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public ContextInfo enforceOwnerOnlyAndActiveAdminPermission(
            ContextInfo contextInfo, List<String> list) throws SecurityException {
        return AccessController.enforceOwnerOnlyAndActiveAdminPermission(contextInfo, list);
    }

    public ContextInfo enforcePermissionByContext(ContextInfo contextInfo, List<String> list)
            throws SecurityException {
        if (getService() != null) {
            try {
                return this.mService.enforcePermissionByContext(contextInfo, list);
            } catch (RemoteException e) {
                Log.w(TAG, "Failed talking with enterprise policy service", e);
            }
        }
        return contextInfo;
    }

    public boolean setAdminRemovable(boolean z) {
        AccessController.throwIfParentInstance(this.mContextInfo, "setAdminRemovable");
        if (getService() == null) {
            return false;
        }
        try {
            EnterpriseLicenseManager.log(
                    this.mContextInfo, "EnterpriseDeviceManager.setAdminRemovable(boolean)");
            return this.mService.setAdminRemovable(this.mContextInfo, z, null);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with enterprise policy service", e);
            return false;
        }
    }

    @Nullable
    public static EnterpriseDeviceManager getParentInstance(Context context, int i) {
        EnterpriseDeviceManager enterpriseDeviceManager;
        String packageName = context.getPackageName();
        if (packageName != null && packageName.equals("com.samsung.android.knox.kpecore")) {
            if (!AccessController.enforceWpcod()) {
                return null;
            }
            synchronized (mSync) {
                enterpriseDeviceManager = new EnterpriseDeviceManager(context, true, i);
                mParentInstance = enterpriseDeviceManager;
            }
            return enterpriseDeviceManager;
        }
        throw new SecurityException("Can only be called by com.samsung.android.knox.kpecore");
    }

    public static EnterpriseDeviceManager getInstance(Context context, int i) {
        EnterpriseDeviceManager enterpriseDeviceManager;
        String packageName = context.getPackageName();
        if (packageName != null && packageName.equals("com.samsung.android.knox.kpecore")) {
            synchronized (mSync) {
                sEnterpriseDeviceManager = new EnterpriseDeviceManager(context, false, i);
                KnoxsdkFileLog.d(
                        TAG,
                        "getInstance() : ("
                                + Process.myPid()
                                + "/"
                                + Process.myTid()
                                + ") create an instance with UID "
                                + Process.myUid());
                enterpriseDeviceManager = sEnterpriseDeviceManager;
            }
            return enterpriseDeviceManager;
        }
        throw new SecurityException("Can only be called by com.samsung.android.knox.kpecore");
    }

    public EnterpriseDeviceManager(Context context, boolean z) {
        this(context, true, new ContextInfo(Process.myUid(), z));
    }

    public EnterpriseDeviceManager(Context context, boolean z, int i) {
        this(context, true, new ContextInfo(Process.myUid(), z, i));
    }

    @VisibleForTesting
    public EnterpriseDeviceManager(
            Context context,
            ContextInfo contextInfo,
            IEnterpriseDeviceManager iEnterpriseDeviceManager) {
        this(context, false, contextInfo);
        this.mService = iEnterpriseDeviceManager;
    }

    public void deactivateAdminForUser(ComponentName componentName, int i) {}

    public void activateAdminForUser(ComponentName componentName, boolean z, int i) {}
}
