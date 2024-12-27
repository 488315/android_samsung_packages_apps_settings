package com.samsung.android.knox;

import android.R;
import android.app.admin.DeviceAdminInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.util.Printer;
import android.util.SparseArray;

import androidx.activity.ComponentActivity$1$$ExternalSyntheticOutline0;

import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.configuration.DATA;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class EnterpriseDeviceAdminInfo implements Parcelable {
    public static final Parcelable.Creator<EnterpriseDeviceAdminInfo> CREATOR;
    public static final String TAG = "EnterpriseDeviceAdminInfo";
    public static final int USES_POLICY_KNOX_ADVANCED_APP_MGMT = 80;
    public static final String USES_POLICY_KNOX_ADVANCED_APP_MGMT_TAG =
            "com.samsung.android.knox.permission.KNOX_ADVANCED_APP_MGMT";
    public static final int USES_POLICY_KNOX_ADVANCED_SECURITY = 81;
    public static final String USES_POLICY_KNOX_ADVANCED_SECURITY_TAG =
            "com.samsung.android.knox.permission.KNOX_ADVANCED_SECURITY";
    public static final int USES_POLICY_KNOX_APP_SEPARATION = 112;
    public static final String USES_POLICY_KNOX_APP_SEPARATION_TAG =
            "com.samsung.android.knox.permission.KNOX_APP_SEPARATION";
    public static final int USES_POLICY_KNOX_AUTHENTICATION_MANAGER = 123;
    public static final String USES_POLICY_KNOX_AUTHENTICATION_MANAGER_TAG =
            "com.samsung.android.knox.permission.KNOX_AUTH_MGMT";
    public static final int USES_POLICY_KNOX_CAPTURE = 113;
    public static final int USES_POLICY_KNOX_CAPTURE_ADVANCED = 120;
    public static final String USES_POLICY_KNOX_CAPTURE_ADVANCED_TAG =
            "com.samsung.android.knox.permission.SMART_SCAN_ADVANCED";
    public static final int USES_POLICY_KNOX_CAPTURE_BASIC = 119;
    public static final String USES_POLICY_KNOX_CAPTURE_BASIC_TAG =
            "com.samsung.android.knox.permission.SMART_SCAN_BASIC";
    public static final String USES_POLICY_KNOX_CAPTURE_TAG =
            "com.samsung.android.knox.permission.SMART_SCAN";
    public static final int USES_POLICY_KNOX_CCM = 61;
    public static final String USES_POLICY_KNOX_CCM_TAG =
            "com.sec.enterprise.knox.permission.KNOX_CCM,com.samsung.android.knox.permission.KNOX_CCM_KEYSTORE";
    public static final int USES_POLICY_KNOX_CERTENROL = 66;
    public static final String USES_POLICY_KNOX_CERTENROL_TAG =
            "com.sec.enterprise.knox.permission.KNOX_CERTENROLL,com.samsung.android.knox.permission.KNOX_CERTIFICATE_ENROLLMENT";
    public static final int USES_POLICY_KNOX_CERT_PROVISIONING = 78;
    public static final String USES_POLICY_KNOX_CERT_PROVISIONING_TAG =
            "com.samsung.android.knox.permission.KNOX_CERT_PROVISIONING";
    public static final int USES_POLICY_KNOX_CLIPBOARD = 79;
    public static final String USES_POLICY_KNOX_CLIPBOARD_TAG =
            "com.samsung.android.knox.permission.KNOX_CLIPBOARD";
    public static final int USES_POLICY_KNOX_CONTAINER_VPN = 55;
    public static final String USES_POLICY_KNOX_CONTAINER_VPN_TAG =
            "com.samsung.android.knox.permission.KNOX_VPN_CONTAINER";
    public static final int USES_POLICY_KNOX_CRITICAL_COMMUNICATIONS = 108;
    public static final String USES_POLICY_KNOX_CRITICAL_COMMUNICATIONS_TAG =
            "com.samsung.android.knox.permission.KNOX_CRITICAL_COMMUNICATIONS";
    public static final int USES_POLICY_KNOX_CUSTOM_DEX = 85;
    public static final String USES_POLICY_KNOX_CUSTOM_DEX_TAG =
            "com.samsung.android.knox.permission.KNOX_CUSTOM_DEX";
    public static final int USES_POLICY_KNOX_CUSTOM_PROKIOSK = 70;
    public static final String USES_POLICY_KNOX_CUSTOM_PROKIOSK_TAG =
            "com.samsung.android.knox.permission.KNOX_CUSTOM_PROKIOSK";
    public static final int USES_POLICY_KNOX_CUSTOM_SEALEDMODE = 65;
    public static final String USES_POLICY_KNOX_CUSTOM_SEALEDMODE_TAG =
            "com.sec.enterprise.knox.permission.CUSTOM_SEALEDMODE,com.samsung.android.knox.permission.KNOX_CUSTOM_SEALEDMODE";
    public static final int USES_POLICY_KNOX_CUSTOM_SETTING = 63;
    public static final String USES_POLICY_KNOX_CUSTOM_SETTING_TAG =
            "com.sec.enterprise.knox.permission.CUSTOM_SETTING,com.samsung.android.knox.permission.KNOX_CUSTOM_SETTING";
    public static final int USES_POLICY_KNOX_CUSTOM_SYSTEM = 64;
    public static final String USES_POLICY_KNOX_CUSTOM_SYSTEM_TAG =
            "com.sec.enterprise.knox.permission.CUSTOM_SYSTEM,com.samsung.android.knox.permission.KNOX_CUSTOM_SYSTEM";
    public static final int USES_POLICY_KNOX_DEACTIVATE_LICENSE = 111;
    public static final String USES_POLICY_KNOX_DEACTIVATE_LICENSE_TAG =
            "com.sec.enterprise.knox.permission.KNOX_DEACTIVATE_LICENSE";
    public static final int USES_POLICY_KNOX_DEVICE_CONFIGURATION = 109;
    public static final String USES_POLICY_KNOX_DEVICE_CONFIGURATION_TAG =
            "com.samsung.android.knox.permission.KNOX_DEVICE_CONFIGURATION";
    public static final int USES_POLICY_KNOX_DEX = 84;
    public static final String USES_POLICY_KNOX_DEX_TAG =
            "com.samsung.android.knox.permission.KNOX_DEX";
    public static final int USES_POLICY_KNOX_DUAL_DAR = 87;
    public static final String USES_POLICY_KNOX_DUAL_DAR_TAG =
            "com.samsung.android.knox.permission.KNOX_DUAL_DAR";
    public static final int USES_POLICY_KNOX_EBILLING_NOMDM = 83;
    public static final String USES_POLICY_KNOX_EBILLING_NOMDM_TAG =
            "com.samsung.android.knox.permission.KNOX_EBILLING_NOMDM";
    public static final int USES_POLICY_KNOX_ENHANCED_ATTESTATION = 107;
    public static final String USES_POLICY_KNOX_ENHANCED_ATTESTATION_TAG =
            "com.samsung.android.knox.permission.KNOX_ENHANCED_ATTESTATION";
    public static final int USES_POLICY_KNOX_ENTERPRISE_BILLING = 68;
    public static final String USES_POLICY_KNOX_ENTERPRISE_BILLING_TAG =
            "com.samsung.android.knox.permission.KNOX_EBILLING";
    public static final int USES_POLICY_KNOX_FORESIGHT = 122;
    public static final String USES_POLICY_KNOX_FORESIGHT_TAG =
            "com.samsung.android.knox.permission.KNOX_FORESIGHT";
    public static final int USES_POLICY_KNOX_GENERIC_VPN = 54;
    public static final String USES_POLICY_KNOX_GENERIC_VPN_TAG =
            "com.samsung.android.knox.permission.KNOX_VPN_GENERIC";
    public static final int USES_POLICY_KNOX_HDM = 110;
    public static final String USES_POLICY_KNOX_HDM_TAG =
            "com.samsung.android.knox.permission.KNOX_HDM";
    public static final int USES_POLICY_KNOX_KEYSTORE = 62;
    public static final int USES_POLICY_KNOX_KEYSTORE_PER_APP = 75;
    public static final String USES_POLICY_KNOX_KEYSTORE_PER_APP_TAG =
            "com.samsung.android.knox.permission.KNOX_TIMA_KEYSTORE_PER_APP";
    public static final String USES_POLICY_KNOX_KEYSTORE_TAG =
            "com.sec.enterprise.knox.permission.KNOX_KEYSTORE,com.samsung.android.knox.permission.KNOX_TIMA_KEYSTORE";
    public static final int USES_POLICY_KNOX_MPOS = 121;
    public static final String USES_POLICY_KNOX_MPOS_TAG =
            "com.samsung.android.knox.permission.KNOX_MPOS";
    public static final int USES_POLICY_KNOX_NDA_AI = 118;
    public static final String USES_POLICY_KNOX_NDA_AI_TAG =
            "com.samsung.android.knox.permission.KNOX_NDA_AI";
    public static final int USES_POLICY_KNOX_NDA_DATA_ANALYTICS = 117;
    public static final String USES_POLICY_KNOX_NDA_DATA_ANALYTICS_TAG =
            "com.samsung.android.knox.permission.KNOX_NDA_DATA_ANALYTICS";
    public static final int USES_POLICY_KNOX_NDA_DEVICE_SETTINGS = 116;
    public static final String USES_POLICY_KNOX_NDA_DEVICE_SETTINGS_TAG =
            "com.samsung.android.knox.permission.KNOX_NDA_DEVICE_SETTINGS";
    public static final int USES_POLICY_KNOX_NDA_PERIPHERAL = 115;
    public static final String USES_POLICY_KNOX_NDA_PERIPHERAL_TAG =
            "com.samsung.android.knox.permission.KNOX_NDA_PERIPHERAL";
    public static final int USES_POLICY_KNOX_NETWORK_FILTER_MGMT = 124;
    public static final String USES_POLICY_KNOX_NETWORK_FILTER_MGMT_TAG =
            "com.samsung.android.knox.permission.KNOX_NETWORK_FILTER_MGMT";
    public static final int USES_POLICY_KNOX_NETWORK_FILTER_SP = 125;
    public static final String USES_POLICY_KNOX_NETWORK_FILTER_SP_TAG =
            "com.samsung.android.knox.permission.KNOX_NETWORK_FILTER_SERVICE_PROVIDER";
    public static final int USES_POLICY_KNOX_NPA = 82;
    public static final String USES_POLICY_KNOX_NPA_TAG =
            "com.samsung.android.knox.permission.KNOX_NPA";
    public static final int USES_POLICY_KNOX_RESTRICTION_PERM = 60;
    public static final String USES_POLICY_KNOX_RESTRICTION_PERM_TAG =
            "com.samsung.android.knox.permission.KNOX_ADVANCED_RESTRICTION";
    public static final int USES_POLICY_KNOX_SDP = 71;
    public static final String USES_POLICY_KNOX_SDP_TAG =
            "com.samsung.android.knox.permission.KNOX_SENSITIVE_DATA_PROTECTION";
    public static final int USES_POLICY_KNOX_SEAMS_PERM = 58;
    public static final String USES_POLICY_KNOX_SEAMS_PERM_TAG =
            "com.sec.enterprise.knox.permission.KNOX_SEAMS,com.samsung.android.knox.permission.KNOX_SEAMS_MGMT";
    public static final int USES_POLICY_KNOX_SEAMS_SEPOLICY = 114;
    public static final int USES_POLICY_KNOX_SEAMS_SEPOLICY_PERM = 59;
    public static final String USES_POLICY_KNOX_SEAMS_SEPOLICY_PERM_TAG =
            "com.samsung.android.knox.permission.KNOX_SEAMS_SEPOLICY_INTERNAL";
    public static final String USES_POLICY_KNOX_SEAMS_SEPOLICY_TAG =
            "com.sec.enterprise.knox.permission.KNOX_SEAMS_SEPOLICY";
    public static final int USES_POLICY_KNOX_SECURE_TIMER = 88;
    public static final String USES_POLICY_KNOX_SECURE_TIMER_TAG =
            "com.samsung.android.knox.permission.KNOX_SECURE_TIMER";
    public static final int USES_POLICY_KNOX_SIM_RESTRICTION = 89;
    public static final String USES_POLICY_KNOX_SIM_RESTRICTION_TAG =
            "com.samsung.android.knox.permission.KNOX_SIM_RESTRICTION";
    public static final int USES_POLICY_KNOX_UCM_MGMT = 86;
    public static final String USES_POLICY_KNOX_UCM_MGMT_TAG =
            "com.samsung.android.knox.permission.KNOX_UCM_MGMT";
    public static final int USES_POLICY_KNOX_UCM_PRIVILEGED = 76;
    public static final String USES_POLICY_KNOX_UCM_PRIVILEGED_TAG =
            "com.samsung.android.knox.permission.KNOX_UCM_PRIVILEGED_MGMT";
    public static final int USES_POLICY_KNOX_UCSM_ESE = 72;
    public static final String USES_POLICY_KNOX_UCSM_ESE_TAG =
            "com.samsung.android.knox.permission.KNOX_UCM_ESE_MGMT";
    public static final int USES_POLICY_KNOX_UCSM_OTHER = 73;
    public static final String USES_POLICY_KNOX_UCSM_OTHER_TAG =
            "com.samsung.android.knox.permission.KNOX_UCM_OTHER_MGMT";
    public static final int USES_POLICY_KNOX_UCS_PLUGIN = 74;
    public static final String USES_POLICY_KNOX_UCS_PLUGIN_TAG =
            "com.samsung.android.knox.permission.KNOX_UCM_PLUGIN_SERVICE";
    public static final int USES_POLICY_MDM_APN_SETTINGS = 34;
    public static final String USES_POLICY_MDM_APN_SETTINGS_TAG =
            "com.samsung.android.knox.permission.KNOX_APN";
    public static final int USES_POLICY_MDM_APPLICATION = 22;
    public static final int USES_POLICY_MDM_APPLICATION_PERMISSION = 90;
    public static final String USES_POLICY_MDM_APPLICATION_PERMISSION_TAG =
            "com.samsung.android.knox.permission.KNOX_APP_PERMISSION_MGMT";
    public static final String USES_POLICY_MDM_APPLICATION_TAG =
            "com.samsung.android.knox.permission.KNOX_APP_MGMT";
    public static final int USES_POLICY_MDM_AUDIT_LOG_PERMISSION = 43;
    public static final String USES_POLICY_MDM_AUDIT_LOG_PERMISSION_TAG =
            "com.samsung.android.knox.permission.KNOX_AUDIT_LOG";
    public static final int USES_POLICY_MDM_BLUETOOTH = 23;
    public static final int USES_POLICY_MDM_BLUETOOTH_SECURE_MODE = 51;
    public static final String USES_POLICY_MDM_BLUETOOTH_SECURE_MODE_TAG =
            "com.samsung.android.knox.permission.KNOX_BLUETOOTH_SECUREMODE";
    public static final String USES_POLICY_MDM_BLUETOOTH_TAG =
            "com.samsung.android.knox.permission.KNOX_BLUETOOTH";
    public static final int USES_POLICY_MDM_BROWSER_PROXY = 53;
    public static final String USES_POLICY_MDM_BROWSER_PROXY_TAG =
            "com.samsung.android.knox.permission.KNOX_BROWSER_PROXY";
    public static final int USES_POLICY_MDM_BROWSER_SETTINGS = 36;
    public static final String USES_POLICY_MDM_BROWSER_SETTINGS_TAG =
            "com.samsung.android.knox.permission.KNOX_BROWSER_SETTINGS";
    public static final int USES_POLICY_MDM_CERTIFICATE_PERMISSION = 42;
    public static final String USES_POLICY_MDM_CERTIFICATE_PERMISSION_TAG =
            "com.samsung.android.knox.permission.KNOX_CERTIFICATE";
    public static final int USES_POLICY_MDM_DATE_TIME = 37;
    public static final String USES_POLICY_MDM_DATE_TIME_TAG =
            "com.samsung.android.knox.permission.KNOX_DATE_TIME";
    public static final int USES_POLICY_MDM_DEVICE_INVENTORY = 24;
    public static final String USES_POLICY_MDM_DEVICE_INVENTORY_TAG =
            "com.samsung.android.knox.permission.KNOX_INVENTORY";
    public static final int USES_POLICY_MDM_DUAL_SIM = 47;
    public static final String USES_POLICY_MDM_DUAL_SIM_TAG =
            "com.samsung.android.knox.permission.KNOX_DUAL_SIM";
    public static final int USES_POLICY_MDM_EMAIL_ACCOUNT = 32;
    public static final String USES_POLICY_MDM_EMAIL_ACCOUNT_TAG =
            "com.samsung.android.knox.permission.KNOX_EMAIL";
    public static final int USES_POLICY_MDM_ENTERPRISE_CONTAINER = 48;
    public static final String USES_POLICY_MDM_ENTERPRISE_CONTAINER_TAG =
            "com.samsung.android.knox.permission.KNOX_CONTAINER";
    public static final int USES_POLICY_MDM_ENTERPRISE_DEVICE_ADMIN = 39;
    public static final String USES_POLICY_MDM_ENTERPRISE_DEVICE_ADMIN_TAG =
            "com.samsung.android.knox.permission.KNOX_ENTERPRISE_DEVICE_ADMIN";
    public static final int USES_POLICY_MDM_EXCHANGE_ACCOUNT = 25;
    public static final String USES_POLICY_MDM_EXCHANGE_ACCOUNT_TAG =
            "com.samsung.android.knox.permission.KNOX_EXCHANGE";
    public static final int USES_POLICY_MDM_FIREWALL = 38;
    public static final String USES_POLICY_MDM_FIREWALL_TAG =
            "com.samsung.android.knox.permission.KNOX_FIREWALL";
    public static final int USES_POLICY_MDM_GEOFENCING = 45;
    public static final String USES_POLICY_MDM_GEOFENCING_TAG =
            "com.samsung.android.knox.permission.KNOX_GEOFENCING";
    public static final int USES_POLICY_MDM_GLOBALPROXY = 77;
    public static final String USES_POLICY_MDM_GLOBALPROXY_TAG =
            "com.samsung.android.knox.permission.KNOX_GLOBALPROXY";
    public static final int USES_POLICY_MDM_HARDWARE_CONTROL = 29;
    public static final String USES_POLICY_MDM_HARDWARE_CONTROL_TAG =
            "com.samsung.android.knox.permission.KNOX_HW_CONTROL";
    public static final int USES_POLICY_MDM_KIOSK_MODE = 41;
    public static final String USES_POLICY_MDM_KIOSK_MODE_TAG =
            "com.samsung.android.knox.permission.KNOX_KIOSK_MODE";
    public static final int USES_POLICY_MDM_KNOX_ACTIVATE_DEVICE_PERMISSIONS = 56;
    public static final String USES_POLICY_MDM_KNOX_ACTIVATE_DEVICE_PERMISSIONS_TAG =
            "com.samsung.android.knox.permission.KNOX_ACTIVATE_DEVICE_PERMISSIONS_INTERNAL";
    public static final int USES_POLICY_MDM_KNOX_MOBILE_THREAT_DEFENSE = 106;
    public static final String USES_POLICY_MDM_KNOX_MOBILE_THREAT_DEFENSE_TAG =
            "com.samsung.android.knox.permission.KNOX_MOBILE_THREAT_DEFENSE";
    public static final int USES_POLICY_MDM_LDAP_SETTINGS = 44;
    public static final String USES_POLICY_MDM_LDAP_SETTINGS_TAG =
            "com.samsung.android.knox.permission.KNOX_LDAP";
    public static final int USES_POLICY_MDM_LICENSE_LOG = 49;
    public static final String USES_POLICY_MDM_LICENSE_LOG_TAG =
            "com.samsung.android.knox.permission.KNOX_LICENSE_LOG";
    public static final int USES_POLICY_MDM_LOCATION = 31;
    public static final String USES_POLICY_MDM_LOCATION_TAG =
            "com.samsung.android.knox.permission.KNOX_LOCATION";
    public static final int USES_POLICY_MDM_LOCKSCREEN = 46;
    public static final String USES_POLICY_MDM_LOCKSCREEN_TAG =
            "com.samsung.android.knox.permission.KNOX_LOCKSCREEN";
    public static final int USES_POLICY_MDM_MULTI_USER_MGMT = 50;
    public static final String USES_POLICY_MDM_MULTI_USER_MGMT_TAG =
            "com.samsung.android.knox.permission.KNOX_MULTI_USER_MGMT";
    public static final int USES_POLICY_MDM_PHONE_RESTRICTION = 35;
    public static final String USES_POLICY_MDM_PHONE_RESTRICTION_TAG =
            "com.samsung.android.knox.permission.KNOX_PHONE_RESTRICTION";
    public static final int USES_POLICY_MDM_RCP_SYNC_MGMT = 57;
    public static final String USES_POLICY_MDM_RCP_SYNC_MGMT_TAG =
            "com.sec.enterprise.knox.permission.KNOX_RCP_SYNC_MGMT,com.samsung.android.knox.permission.KNOX_CONTAINER_RCP";
    public static final int USES_POLICY_MDM_REMOTE_CONTROL = 40;
    public static final String USES_POLICY_MDM_REMOTE_CONTROL_TAG =
            "com.samsung.android.knox.permission.KNOX_REMOTE_CONTROL";
    public static final int USES_POLICY_MDM_RESTRICTION = 30;
    public static final String USES_POLICY_MDM_RESTRICTION_TAG =
            "com.samsung.android.knox.permission.KNOX_RESTRICTION_MGMT";
    public static final int USES_POLICY_MDM_ROAMING = 26;
    public static final String USES_POLICY_MDM_ROAMING_TAG =
            "com.samsung.android.knox.permission.KNOX_ROAMING";
    public static final int USES_POLICY_MDM_SECURITY = 28;
    public static final String USES_POLICY_MDM_SECURITY_TAG =
            "com.samsung.android.knox.permission.KNOX_SECURITY";
    public static final int USES_POLICY_MDM_SMARTCARD = 91;
    public static final String USES_POLICY_MDM_SMARTCARD_TAG =
            "com.samsung.android.knox.permission.KNOX_SMARTCARD";
    public static final int USES_POLICY_MDM_SSO = 67;
    public static final String USES_POLICY_MDM_SSO_TAG =
            "com.sec.enterprise.mdm.permission.MDM_SSO,com.samsung.android.knox.permission.KNOX_SSO";
    public static final int USES_POLICY_MDM_VPN = 33;
    public static final String USES_POLICY_MDM_VPN_TAG =
            "com.samsung.android.knox.permission.KNOX_VPN";
    public static final int USES_POLICY_MDM_WIFI = 27;
    public static final String USES_POLICY_MDM_WIFI_TAG =
            "com.samsung.android.knox.permission.KNOX_WIFI";
    public static final int USES_POLICY_RAPID_TEST_ATTESTATION = 127;
    public static final String USES_POLICY_RAPID_TEST_ATTESTATION_TAG =
            "com.sec.enterprise.knox.permission.KNOX_ATTESTATION";
    public static final int USES_POLICY_RAPID_TEST_SPD = 126;
    public static final String USES_POLICY_RAPID_TEST_SPD_TAG =
            "com.samsung.android.knox.permission.KNOX_SPDCONTROL";
    public boolean mAuthorized;
    public DeviceAdminInfo mDeviceAdminInfo;
    public boolean mIsPseudoAdmin;
    public long mLicenseExpiryTime;
    public final ResolveInfo mReceiver;
    public List<String> mRequestedPermissions;
    public BitSet mUsesPolicies;
    public boolean mVisible;
    public static final boolean timaversion =
            "3.0"
                    .equals(
                            SystemProperties.get(
                                    "ro.config.timaversion", DATA.DM_FIELD_INDEX.PCSCF_DOMAIN));
    public static HashMap<String, String> sOldToNewPermissionMapping = new HashMap<>();
    public static HashMap<String, String> sNewToOldPermissionMapping = new HashMap<>();
    public static ArrayList<PolicyInfo> sPoliciesDisplayOrder = new ArrayList<>();
    public static HashMap<String, Integer> sKnownPolicies = new HashMap<>();
    public static SparseArray<PolicyInfo> sRevKnownPolicies = new SparseArray<>();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.EnterpriseDeviceAdminInfo$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<EnterpriseDeviceAdminInfo> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnterpriseDeviceAdminInfo createFromParcel(Parcel parcel) {
            return new EnterpriseDeviceAdminInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EnterpriseDeviceAdminInfo[] newArray(int i) {
            return new EnterpriseDeviceAdminInfo[i];
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class PolicyInfo {
        public final int description;
        public final int descriptionForSecondaryUsers;
        public final int ident;
        public final int label;
        public final int labelForSecondaryUsers;
        public final String tag;

        public PolicyInfo(int i, String str, int i2, int i3) {
            this(i, str, i2, i3, i2, i3);
        }

        public PolicyInfo(int i, String str, int i2, int i3, int i4, int i5) {
            this.ident = i;
            this.tag = str;
            this.label = i2;
            this.description = i3;
            this.labelForSecondaryUsers = i4;
            this.descriptionForSecondaryUsers = i5;
        }
    }

    static {
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                22,
                USES_POLICY_MDM_APPLICATION_TAG,
                17042212,
                R.string.user_logging_out_message,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                23,
                USES_POLICY_MDM_BLUETOOTH_TAG,
                17042215,
                R.string.user_switched,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                24,
                USES_POLICY_MDM_DEVICE_INVENTORY_TAG,
                17042227,
                R.string.volume_dialog_ringer_guidance_silent,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                25,
                USES_POLICY_MDM_EXCHANGE_ACCOUNT_TAG,
                17042238,
                R.string.volume_ringtone,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                26,
                USES_POLICY_MDM_ROAMING_TAG,
                17042259,
                R.string.wfcSpnFormat_spn_wlan_call,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                27,
                USES_POLICY_MDM_WIFI_TAG,
                17042277,
                R.string.whichHomeApplication,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                28,
                USES_POLICY_MDM_SECURITY_TAG,
                17042266,
                R.string.wfcSpnFormat_wlan_call,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                29,
                USES_POLICY_MDM_HARDWARE_CONTROL_TAG,
                17042242,
                R.string.vpn_lockdown_connecting,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                30,
                USES_POLICY_MDM_RESTRICTION_TAG,
                17042258,
                R.string.wfcSpnFormat_spn_wifi_calling_vo_hyphen,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                31,
                USES_POLICY_MDM_LOCATION_TAG,
                17042252,
                R.string.wfcRegErrorTitle,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                32,
                USES_POLICY_MDM_EMAIL_ACCOUNT_TAG,
                17042231,
                R.string.volume_icon_description_media,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                33,
                USES_POLICY_MDM_VPN_TAG,
                17042276,
                R.string.whichGiveAccessToApplicationLabel,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                34,
                USES_POLICY_MDM_APN_SETTINGS_TAG,
                17042210,
                R.string.user_creation_account_exists,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                35,
                USES_POLICY_MDM_PHONE_RESTRICTION_TAG,
                17042254,
                R.string.wfcSpnFormat_spn,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                36,
                USES_POLICY_MDM_BROWSER_SETTINGS_TAG,
                17042218,
                R.string.vdm_camera_access_denied,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                53,
                USES_POLICY_MDM_BROWSER_PROXY_TAG,
                17042217,
                R.string.validity_period,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                37,
                USES_POLICY_MDM_DATE_TIME_TAG,
                17042225,
                R.string.volume_bluetooth_call,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                54,
                "com.samsung.android.knox.permission.KNOX_VPN_GENERIC",
                17042195,
                R.string.usb_midi_notification_title,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                55,
                "com.samsung.android.knox.permission.KNOX_VPN_CONTAINER",
                17042187,
                R.string.upload_file,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                38,
                USES_POLICY_MDM_FIREWALL_TAG,
                17042239,
                R.string.volume_unknown,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                39,
                USES_POLICY_MDM_ENTERPRISE_DEVICE_ADMIN_TAG,
                17042232,
                R.string.volume_icon_description_notification,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                40,
                USES_POLICY_MDM_REMOTE_CONTROL_TAG,
                17042257,
                R.string.wfcSpnFormat_spn_wifi_calling,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                41,
                USES_POLICY_MDM_KIOSK_MODE_TAG,
                17042245,
                R.string.vpn_text,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                42,
                USES_POLICY_MDM_CERTIFICATE_PERMISSION_TAG,
                17042222,
                R.string.view_and_control_notification_content,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                43,
                USES_POLICY_MDM_AUDIT_LOG_PERMISSION_TAG,
                17042214,
                R.string.user_owner_label,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                48,
                USES_POLICY_MDM_ENTERPRISE_CONTAINER_TAG,
                17042233,
                R.string.volume_icon_description_ringer,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                44,
                USES_POLICY_MDM_LDAP_SETTINGS_TAG,
                17042250,
                R.string.wait,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                46,
                USES_POLICY_MDM_LOCKSCREEN_TAG,
                17042251,
                R.string.wallpaper_binding_label,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                47,
                USES_POLICY_MDM_DUAL_SIM_TAG,
                17042229,
                R.string.volume_icon_description_bluetooth,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                67,
                USES_POLICY_MDM_SSO_TAG,
                17042262,
                R.string.wfcSpnFormat_wifi_call,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                45,
                USES_POLICY_MDM_GEOFENCING_TAG,
                17042240,
                R.string.vpn_lockdown_config,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                49,
                USES_POLICY_MDM_LICENSE_LOG_TAG,
                17042230,
                R.string.volume_icon_description_incall,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                50,
                USES_POLICY_MDM_MULTI_USER_MGMT_TAG,
                17042253,
                R.string.wfcSpnFormat,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                51,
                USES_POLICY_MDM_BLUETOOTH_SECURE_MODE_TAG,
                17042216,
                R.string.user_switching_message,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                107,
                USES_POLICY_KNOX_ENHANCED_ATTESTATION_TAG,
                17042150,
                R.string.textSelectionCABTitle,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                106,
                "com.samsung.android.knox.permission.KNOX_MOBILE_THREAT_DEFENSE",
                17042270,
                R.string.whichApplication,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                108,
                USES_POLICY_KNOX_CRITICAL_COMMUNICATIONS_TAG,
                17042142,
                R.string.sync_undo_deletes,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                57,
                USES_POLICY_MDM_RCP_SYNC_MGMT_TAG,
                17042248,
                R.string.vpn_title_long,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                56,
                USES_POLICY_MDM_KNOX_ACTIVATE_DEVICE_PERMISSIONS_TAG,
                17042246,
                R.string.vpn_text_long,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                111,
                USES_POLICY_KNOX_DEACTIVATE_LICENSE_TAG,
                17042226,
                R.string.volume_call,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                58,
                USES_POLICY_KNOX_SEAMS_PERM_TAG,
                17042264,
                R.string.wfcSpnFormat_wifi_calling_bar_spn,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                59,
                USES_POLICY_KNOX_SEAMS_SEPOLICY_PERM_TAG,
                17042265,
                R.string.wfcSpnFormat_wifi_calling_wo_hyphen,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                60,
                USES_POLICY_KNOX_RESTRICTION_PERM_TAG,
                17042249,
                R.string.vr_listener_binding_label,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                63,
                USES_POLICY_KNOX_CUSTOM_SETTING_TAG,
                17042190,
                R.string.usb_contaminant_detected_message,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                64,
                USES_POLICY_KNOX_CUSTOM_SYSTEM_TAG,
                17042191,
                R.string.usb_contaminant_detected_title,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                65,
                USES_POLICY_KNOX_CUSTOM_SEALEDMODE_TAG,
                17042188,
                R.string.usb_accessory_notification_title,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                70,
                USES_POLICY_KNOX_CUSTOM_PROKIOSK_TAG,
                17042188,
                R.string.usb_accessory_notification_title,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                68,
                USES_POLICY_KNOX_ENTERPRISE_BILLING_TAG,
                17042151,
                R.string.time_of_day,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                61,
                USES_POLICY_KNOX_CCM_TAG,
                17042220,
                R.string.vdm_secure_window,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                72,
                USES_POLICY_KNOX_UCSM_ESE_TAG,
                17042274,
                R.string.whichEditApplicationLabel,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                73,
                USES_POLICY_KNOX_UCSM_OTHER_TAG,
                17042275,
                R.string.whichEditApplicationNamed,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                74, USES_POLICY_KNOX_UCS_PLUGIN_TAG, 17042357, 17042034, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                76,
                USES_POLICY_KNOX_UCM_PRIVILEGED_TAG,
                17042273,
                R.string.whichEditApplication,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                62,
                USES_POLICY_KNOX_KEYSTORE_TAG,
                17042243,
                R.string.vpn_lockdown_disconnected,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                75,
                USES_POLICY_KNOX_KEYSTORE_PER_APP_TAG,
                17042244,
                R.string.vpn_lockdown_error,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                66,
                USES_POLICY_KNOX_CERTENROL_TAG,
                17042263,
                R.string.wfcSpnFormat_wifi_calling,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                71,
                USES_POLICY_KNOX_SDP_TAG,
                17042260,
                R.string.wfcSpnFormat_vowifi,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                77,
                USES_POLICY_MDM_GLOBALPROXY_TAG,
                17042241,
                R.string.vpn_lockdown_connected,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                78,
                USES_POLICY_KNOX_CERT_PROVISIONING_TAG,
                17042221,
                R.string.vendor_required_attestation_revocation_list_url,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                79,
                USES_POLICY_KNOX_CLIPBOARD_TAG,
                17042223,
                R.string.view_and_control_notification_title,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                80,
                USES_POLICY_KNOX_ADVANCED_APP_MGMT_TAG,
                17042183,
                R.string.unsupported_compile_sdk_message,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                81,
                USES_POLICY_KNOX_ADVANCED_SECURITY_TAG,
                17042184,
                R.string.unsupported_compile_sdk_show,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                82,
                "com.samsung.android.knox.permission.KNOX_NPA",
                17042198,
                R.string.usb_midi_peripheral_product_name,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                83,
                USES_POLICY_KNOX_EBILLING_NOMDM_TAG,
                17042151,
                R.string.time_of_day,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                84,
                USES_POLICY_KNOX_DEX_TAG,
                17042228,
                R.string.volume_dialog_ringer_guidance_vibrate,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                85,
                USES_POLICY_KNOX_CUSTOM_DEX_TAG,
                17042191,
                R.string.usb_contaminant_detected_title,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                86, USES_POLICY_KNOX_UCM_MGMT_TAG, 17042356, 17042033, sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                87,
                USES_POLICY_KNOX_DUAL_DAR_TAG,
                17042193,
                R.string.usb_contaminant_not_detected_title,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                89,
                USES_POLICY_KNOX_SIM_RESTRICTION_TAG,
                17042254,
                R.string.wfcSpnFormat_spn,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                90,
                USES_POLICY_MDM_APPLICATION_PERMISSION_TAG,
                17042213,
                R.string.user_owner_app_label,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                91,
                USES_POLICY_MDM_SMARTCARD_TAG,
                17042269,
                R.string.wfc_mode_wifi_preferred_summary,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                110,
                USES_POLICY_KNOX_HDM_TAG,
                17042196,
                R.string.usb_midi_peripheral_manufacturer_name,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                112,
                USES_POLICY_KNOX_APP_SEPARATION_TAG,
                17042182,
                R.string.unsupported_compile_sdk_check_update,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                113,
                USES_POLICY_KNOX_CAPTURE_TAG,
                17042186,
                R.string.unsupported_display_size_show,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                122,
                USES_POLICY_KNOX_FORESIGHT_TAG,
                17042194,
                R.string.usb_device_resolve_prompt_warn,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                123,
                USES_POLICY_KNOX_AUTHENTICATION_MANAGER_TAG,
                17042185,
                R.string.unsupported_display_size_message,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                114,
                USES_POLICY_KNOX_SEAMS_SEPOLICY_TAG,
                17042265,
                R.string.wfcSpnFormat_wifi_calling_wo_hyphen,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                109,
                "com.samsung.android.knox.permission.KNOX_DEVICE_CONFIGURATION",
                17042192,
                R.string.usb_contaminant_not_detected_message,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                115,
                USES_POLICY_KNOX_NDA_PERIPHERAL_TAG,
                17042292,
                R.string.whichSendToApplicationNamed,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                116,
                USES_POLICY_KNOX_NDA_DEVICE_SETTINGS_TAG,
                17042147,
                R.string.taking_remote_bugreport_notification_title,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                117,
                USES_POLICY_KNOX_NDA_DATA_ANALYTICS_TAG,
                17042146,
                R.string.system_ui_date_pattern,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                118,
                USES_POLICY_KNOX_NDA_AI_TAG,
                17042203,
                R.string.usb_ptp_notification_title,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                119,
                USES_POLICY_KNOX_CAPTURE_BASIC_TAG,
                17042186,
                R.string.unsupported_display_size_show,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                120,
                USES_POLICY_KNOX_CAPTURE_ADVANCED_TAG,
                17042186,
                R.string.unsupported_display_size_show,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                121,
                USES_POLICY_KNOX_MPOS_TAG,
                17042197,
                R.string.usb_midi_peripheral_name,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                124,
                USES_POLICY_KNOX_NETWORK_FILTER_MGMT_TAG,
                17042199,
                R.string.usb_mtp_launch_notification_description,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                125,
                USES_POLICY_KNOX_NETWORK_FILTER_SP_TAG,
                17042199,
                R.string.usb_mtp_launch_notification_title,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                126,
                USES_POLICY_RAPID_TEST_SPD_TAG,
                17042199,
                R.string.usb_mtp_launch_notification_title,
                sPoliciesDisplayOrder);
        EnterpriseDeviceAdminInfo$$ExternalSyntheticOutline0.m(
                127,
                USES_POLICY_RAPID_TEST_ATTESTATION_TAG,
                17042199,
                R.string.usb_mtp_launch_notification_title,
                sPoliciesDisplayOrder);
        for (int i = 0; i < sPoliciesDisplayOrder.size(); i++) {
            PolicyInfo policyInfo = sPoliciesDisplayOrder.get(i);
            sRevKnownPolicies.put(policyInfo.ident, policyInfo);
            sKnownPolicies.put(policyInfo.tag, Integer.valueOf(policyInfo.ident));
            String[] split = policyInfo.tag.split(",");
            if (split != null && split.length == 2) {
                sOldToNewPermissionMapping.put(split[0], split[1]);
                sNewToOldPermissionMapping.put(split[1], split[0]);
            }
        }
        CREATOR = new AnonymousClass1();
    }

    public EnterpriseDeviceAdminInfo(Context context, ResolveInfo resolveInfo)
            throws XmlPullParserException, IOException {
        this.mRequestedPermissions = new ArrayList();
        this.mDeviceAdminInfo = new DeviceAdminInfo(context, resolveInfo);
        this.mUsesPolicies = new BitSet();
        this.mReceiver = resolveInfo;
        if ("com.android.email".equals(resolveInfo.activityInfo.packageName)) {
            return;
        }
        parseRequestedPermissions(context);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void dump(Printer printer, String str) {
        printer.println(str + "Receiver:");
        this.mReceiver.dump(printer, str + "  ");
    }

    public ActivityInfo getActivityInfo() {
        DeviceAdminInfo deviceAdminInfo = this.mDeviceAdminInfo;
        if (deviceAdminInfo != null) {
            return deviceAdminInfo.getActivityInfo();
        }
        return null;
    }

    public ComponentName getComponent() {
        DeviceAdminInfo deviceAdminInfo = this.mDeviceAdminInfo;
        if (deviceAdminInfo != null) {
            return deviceAdminInfo.getComponent();
        }
        return null;
    }

    public long getLicenseExpiry() {
        return this.mLicenseExpiryTime;
    }

    public String getPackageName() {
        DeviceAdminInfo deviceAdminInfo = this.mDeviceAdminInfo;
        return deviceAdminInfo != null ? deviceAdminInfo.getPackageName() : "NonExist";
    }

    public ResolveInfo getReceiver() {
        return this.mReceiver;
    }

    public String getReceiverName() {
        return this.mDeviceAdminInfo.getReceiverName();
    }

    public List<String> getRequestedPermissions() {
        return this.mRequestedPermissions;
    }

    public String getTagForPolicy(int i) {
        DeviceAdminInfo deviceAdminInfo = this.mDeviceAdminInfo;
        if (deviceAdminInfo == null) {
            return null;
        }
        if (i < 22) {
            return deviceAdminInfo.getTagForPolicy(i);
        }
        if (sRevKnownPolicies.get(i) != null) {
            return sRevKnownPolicies.get(i).tag;
        }
        return null;
    }

    public ArrayList<PolicyInfo> getUsedPolicies() {
        ArrayList<PolicyInfo> arrayList = new ArrayList<>();
        ArrayList usedPolicies = this.mDeviceAdminInfo.getUsedPolicies();
        for (int i = 0; i < usedPolicies.size(); i++) {
            arrayList.add(
                    new PolicyInfo(
                            ((DeviceAdminInfo.PolicyInfo) usedPolicies.get(i)).ident,
                            ((DeviceAdminInfo.PolicyInfo) usedPolicies.get(i)).tag,
                            ((DeviceAdminInfo.PolicyInfo) usedPolicies.get(i)).label,
                            ((DeviceAdminInfo.PolicyInfo) usedPolicies.get(i)).description));
        }
        for (int i2 = 0; i2 < sPoliciesDisplayOrder.size(); i2++) {
            PolicyInfo policyInfo = sPoliciesDisplayOrder.get(i2);
            if (usesPolicy(policyInfo.ident)) {
                arrayList.add(policyInfo);
            }
        }
        return arrayList;
    }

    public boolean isAuthorized() {
        return this.mAuthorized;
    }

    public boolean isProxy() {
        return false;
    }

    public boolean isPseudo() {
        return this.mIsPseudoAdmin;
    }

    public boolean isVisible() {
        return this.mDeviceAdminInfo.isVisible();
    }

    public CharSequence loadDescription(PackageManager packageManager)
            throws Resources.NotFoundException {
        return this.mDeviceAdminInfo.loadDescription(packageManager);
    }

    public Drawable loadIcon(PackageManager packageManager) {
        return this.mDeviceAdminInfo.loadIcon(packageManager);
    }

    public CharSequence loadLabel(PackageManager packageManager) {
        return this.mDeviceAdminInfo.loadLabel(packageManager);
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x00cc, code lost:

       if (com.samsung.android.knox.EnterpriseDeviceAdminInfo.sNewToOldPermissionMapping.containsKey(r10) == false) goto L52;
    */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x00ce, code lost:

       r11 = com.samsung.android.knox.EnterpriseDeviceAdminInfo.sNewToOldPermissionMapping.get(r10) + "," + r10;
    */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x00e9, code lost:

       r11 = r10;
    */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x0123, code lost:

       if (r8 == null) goto L71;
    */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0115, code lost:

       if (r8 != null) goto L63;
    */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0117, code lost:

       r8.recycle();
    */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0126, code lost:

       r2.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0129, code lost:

       if (r5 == null) goto L74;
    */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x012b, code lost:

       r5.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x012e, code lost:

       r2 = com.samsung.android.knox.license.EnterpriseLicenseManager.getInstance(null);
       r14 = r14.getPackageManager();
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0136, code lost:

       r2 = r2.getELMPermissions(r13.mDeviceAdminInfo.getPackageName());
    */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0140, code lost:

       if (r2 == null) goto L100;
    */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0142, code lost:

       r2 = r2.iterator();
    */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x014a, code lost:

       if (r2.hasNext() == false) goto L130;
    */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x014c, code lost:

       r3 = r2.next();
    */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0158, code lost:

       if (com.samsung.android.knox.EnterpriseDeviceAdminInfo.sOldToNewPermissionMapping.containsKey(r3) == false) goto L83;
    */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x015a, code lost:

       r4 = r3 + "," + com.samsung.android.knox.EnterpriseDeviceAdminInfo.sOldToNewPermissionMapping.get(r3);
    */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0199, code lost:

       r5 = com.samsung.android.knox.EnterpriseDeviceAdminInfo.sKnownPolicies.get(r4);
    */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x01a1, code lost:

       if (r5 == null) goto L134;
    */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x01a3, code lost:

       r7 = r4.split(",");
       r8 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x01a9, code lost:

       if (r8 >= r7.length) goto L135;
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x01b7, code lost:

       if (r14.checkPermission(r7[r8], r13.mDeviceAdminInfo.getPackageName()) != 0) goto L98;
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x01e9, code lost:

       r8 = r8 + 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x01b9, code lost:

       android.util.Log.i(com.samsung.android.knox.EnterpriseDeviceAdminInfo.TAG, "Add Granted permission : " + r4);
       r13.mUsesPolicies.set(r5.intValue());
    */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x01dc, code lost:

       if (r13.mRequestedPermissions.contains(r3) != false) goto L136;
    */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x01de, code lost:

       r13.mRequestedPermissions.add(r3.intern());
    */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x017b, code lost:

       if (com.samsung.android.knox.EnterpriseDeviceAdminInfo.sNewToOldPermissionMapping.containsKey(r3) == false) goto L86;
    */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x017d, code lost:

       r4 = com.samsung.android.knox.EnterpriseDeviceAdminInfo.sNewToOldPermissionMapping.get(r3) + "," + r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0198, code lost:

       r4 = r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01ec, code lost:

       android.util.Log.e(com.samsung.android.knox.EnterpriseDeviceAdminInfo.TAG, "Failed to get ELM permissions");
    */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0087, code lost:

       if (r10 != 4) goto L119;
    */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x008a, code lost:

       r10 = r2.getName();
    */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x008e, code lost:

       if (r10 == null) goto L125;
    */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0097, code lost:

       if (r10.equals("uses-permission") == false) goto L126;
    */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0099, code lost:

       r8 = r7.obtainAttributes(r2, com.android.internal.R.styleable.AndroidManifestUsesPermission);
       r10 = r8.getNonResourceString(0);
    */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x00a9, code lost:

       if (com.samsung.android.knox.EnterpriseDeviceAdminInfo.sOldToNewPermissionMapping.containsKey(r10) == false) goto L49;
    */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x00ab, code lost:

       r11 = r10 + "," + com.samsung.android.knox.EnterpriseDeviceAdminInfo.sOldToNewPermissionMapping.get(r10);
    */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x00ea, code lost:

       r11 = com.samsung.android.knox.EnterpriseDeviceAdminInfo.sKnownPolicies.get(r11);
    */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x00f2, code lost:

       if (r11 == null) goto L60;
    */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x00f4, code lost:

       r13.mUsesPolicies.set(r11.intValue());
    */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x00fd, code lost:

       if (r10 == null) goto L60;
    */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0105, code lost:

       if (r13.mRequestedPermissions.contains(r10) != false) goto L60;
    */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0107, code lost:

       r13.mRequestedPermissions.add(r10.intern());
    */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0110, code lost:

       com.android.internal.util.XmlUtils.skipCurrentTag(r2);
    */
    /* JADX WARN: Not initialized variable reg: 8, insn: 0x007e: MOVE (r4 I:??[OBJECT, ARRAY]) = (r8 I:??[OBJECT, ARRAY]) (LINE:127), block:B:119:0x007e */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:121:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<java.lang.String> parseRequestedPermissions(android.content.Context r14) {
        /*
            Method dump skipped, instructions count: 506
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.knox.EnterpriseDeviceAdminInfo.parseRequestedPermissions(android.content.Context):java.util.List");
    }

    public final BitSet readBitSet(Parcel parcel) {
        int readInt = parcel.readInt();
        BitSet bitSet = new BitSet();
        for (int i = 0; i < readInt; i++) {
            bitSet.set(parcel.readInt());
        }
        return bitSet;
    }

    public void readPoliciesFromXml(TypedXmlPullParser typedXmlPullParser)
            throws XmlPullParserException, IOException {
        this.mDeviceAdminInfo.readPoliciesFromXml(typedXmlPullParser);
    }

    public void setAuthorized(boolean z) {
        this.mAuthorized = z;
    }

    public void setLicenseExpiry(long j) {
        this.mLicenseExpiryTime = j;
    }

    public String toString() {
        return this.mReceiver != null
                ? ComponentActivity$1$$ExternalSyntheticOutline0.m(
                        new StringBuilder("DeviceAdminInfo{"),
                        this.mReceiver.activityInfo.name,
                        "}")
                : ApnSettings.MVNO_NONE;
    }

    public boolean usesMDMPolicy() {
        BitSet bitSet = this.mUsesPolicies;
        return (bitSet == null || bitSet.isEmpty()) ? false : true;
    }

    public boolean usesPolicy(int i) {
        DeviceAdminInfo deviceAdminInfo = this.mDeviceAdminInfo;
        if (deviceAdminInfo == null) {
            return false;
        }
        if (deviceAdminInfo.usesPolicy(i)) {
            return true;
        }
        return this.mUsesPolicies.get(i);
    }

    public final void writeBitSet(Parcel parcel, BitSet bitSet) {
        parcel.writeInt(bitSet.cardinality());
        int i = -1;
        while (true) {
            i = bitSet.nextSetBit(i + 1);
            if (i == -1) {
                return;
            } else {
                parcel.writeInt(i);
            }
        }
    }

    public void writePoliciesToXml(TypedXmlSerializer typedXmlSerializer)
            throws IllegalArgumentException, IllegalStateException, IOException {
        this.mDeviceAdminInfo.writePoliciesToXml(typedXmlSerializer);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mReceiver.writeToParcel(parcel, i);
        writeBitSet(parcel, this.mUsesPolicies);
        parcel.writeInt(this.mIsPseudoAdmin ? 1 : 0);
    }

    public EnterpriseDeviceAdminInfo(Parcel parcel) {
        this.mRequestedPermissions = new ArrayList();
        this.mReceiver = (ResolveInfo) ResolveInfo.CREATOR.createFromParcel(parcel);
        this.mUsesPolicies = readBitSet(parcel);
        this.mIsPseudoAdmin = parcel.readInt() == 1;
    }

    public EnterpriseDeviceAdminInfo(boolean z) {
        this.mRequestedPermissions = new ArrayList();
        this.mIsPseudoAdmin = z;
        this.mReceiver = null;
    }
}
