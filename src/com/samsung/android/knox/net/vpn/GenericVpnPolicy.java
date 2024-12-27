package com.samsung.android.knox.net.vpn;

import android.annotation.RequiresPermission;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.Log;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.IEnterpriseDeviceManager;
import com.samsung.android.knox.container.RCPPolicy$$ExternalSyntheticOutline0;
import com.samsung.android.knox.keystore.CertificateInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;

import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class GenericVpnPolicy {
    public static final float ERROR_SUPPORTED_VERSION = 2.4f;
    public static final int INVALID_CONTAINER_ID = 0;
    public static final String KEY_TETHER_AUTH_LOGIN_PAGE = "key-tether-auth-login-page";
    public static final String KEY_TETHER_AUTH_RESPONSE_PAGE = "key-tether-auth-response-page";
    public static final String KEY_TETHER_CAPTIVE_PORTAL_ALIAS = "key-tether-captive-portal-alias";
    public static final String KEY_TETHER_CAPTIVE_PORTAL_CERTIFICATE =
            "key-tether-captive-portal-certificate";
    public static final String KEY_TETHER_CAPTIVE_PORTAL_CERT_PASSWORD =
            "key-tether-captive-portal-cert-password";
    public static final String KEY_TETHER_CA_ALIAS = "key-tether-ca-alias";
    public static final String KEY_TETHER_CA_CERTIFICATE = "key-tether-ca-certificate";
    public static final String KEY_TETHER_CA_CERT_PASSWORD = "key-tether-ca-cert-password";
    public static final String KEY_TETHER_CLIENT_CERTIFICATE_ISSUED_CN =
            "key-tether-client-certificate-issued-cn";
    public static final String KEY_TETHER_CLIENT_CERTIFICATE_ISSUER_CN =
            "key-tether-client-certificate-issuer-cn";
    public static final String KEY_TETHER_USER_ALIAS = "key-tether-user-alias";
    public static final String KEY_TETHER_USER_CERTIFICATE = "key-tether-user-certificate";
    public static final String KEY_TETHER_USER_CERT_PASSWORD = "key-tether-user-cert-password";
    public static final String KNOX_SDK_VERSION_CHARACTER = "KNOX_ENTERPRISE_SDK_VERSION_";
    public static String TAG = "GenericVpnPolicy";
    public static boolean VPN_RETURN_BOOL_ERROR = false;
    public static int VPN_RETURN_INT_ERROR = -1;
    public static int VPN_RETURN_INT_SUCCESS;
    public static IKnoxVpnPolicy mKnoxVpnPolicyService;
    public String vendorName;
    public KnoxVpnContext vpnContext;
    public static final boolean DBG = Debug.semIsProductDev();
    public static Context mContext = null;
    public static IEnterpriseDeviceManager mEnterpriseDeviceManager = null;
    public static HashMap<String, GenericVpnPolicy> genericVpnObjectMap = new HashMap<>();

    public GenericVpnPolicy(Context context, String str) {
        this.vpnContext = null;
        this.vendorName = null;
        mContext = context;
        if (DBG) {
            DialogFragment$$ExternalSyntheticOutline0.m(
                    "GenericVpnPolicy ctor : vendorName = ", str, TAG);
        }
        this.vendorName = str;
    }

    public static synchronized GenericVpnPolicy getInstance(
            Context context, KnoxVpnContext knoxVpnContext) {
        GenericVpnPolicy genericVpnPolicy;
        GenericVpnPolicy genericVpnPolicy2;
        synchronized (GenericVpnPolicy.class) {
            String str = knoxVpnContext.vendorName;
            String transformedVendorName = getTransformedVendorName(str, knoxVpnContext.personaId);
            boolean z = DBG;
            if (z) {
                Log.d(TAG, "GenericVpnPolicy getInstance : vendorName = " + transformedVendorName);
            }
            genericVpnPolicy = null;
            if (transformedVendorName != null) {
                try {
                    synchronized (GenericVpnPolicy.class) {
                        try {
                            if (genericVpnObjectMap.containsKey(transformedVendorName)) {
                                genericVpnPolicy2 = genericVpnObjectMap.get(transformedVendorName);
                            } else {
                                GenericVpnPolicy genericVpnPolicy3 =
                                        new GenericVpnPolicy(context, knoxVpnContext);
                                genericVpnObjectMap.put(transformedVendorName, genericVpnPolicy3);
                                genericVpnPolicy2 = genericVpnPolicy3;
                            }
                            if (genericVpnPolicy2 != null
                                    && !KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(str)) {
                                boolean bindKnoxVpnInterface =
                                        getKnoxVpnPolicyService()
                                                .bindKnoxVpnInterface(knoxVpnContext);
                                if (z) {
                                    Log.d(
                                            TAG,
                                            "GenericVpnPolicy getInstance : bindSuccess = "
                                                    + bindKnoxVpnInterface);
                                }
                                if (!bindKnoxVpnInterface) {
                                    genericVpnObjectMap.remove(transformedVendorName);
                                    genericVpnPolicy2 = null;
                                }
                            }
                            genericVpnPolicy = genericVpnPolicy2;
                        } finally {
                        }
                    }
                } catch (RemoteException e) {
                    Log.e(
                            TAG,
                            "GenericVpnPolicy getInstance : returning null for vendorName = "
                                    + str
                                    + "; Exception = "
                                    + Log.getStackTraceString(e));
                }
            }
        }
        return genericVpnPolicy;
    }

    public static IKnoxVpnPolicy getKnoxVpnPolicyService() {
        if (mKnoxVpnPolicyService == null) {
            mKnoxVpnPolicyService =
                    IKnoxVpnPolicy.Stub.asInterface(
                            ServiceManager.getService(
                                    KnoxVpnPolicyConstants.KNOX_VPN_POLICY_SERVICE));
        }
        if (DBG) {
            Log.d(
                    TAG,
                    "KnoxVpnPolicy getService : mKnoxVpnPolicyService = " + mKnoxVpnPolicyService);
        }
        return mKnoxVpnPolicyService;
    }

    public static String getTransformedVendorName(String str, int i) {
        return i + "_" + str;
    }

    public int activateVpnProfile(String str, boolean z) {
        int i = -1;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseLicenseManager.log(
                        new ContextInfo(Process.myUid()), "GenericVpnPolicy.activateVpnProfile");
                EnterpriseResponseData activateVpnProfile =
                        mKnoxVpnPolicyService.activateVpnProfile(this.vpnContext, str, z);
                if (activateVpnProfile != null) {
                    i = ((Integer) activateVpnProfile.getData()).intValue();
                    if (i == 0 && z) {
                        Intent intent = new Intent();
                        int userId = UserHandle.getUserId(Binder.getCallingUid());
                        intent.setClassName(
                                "com.android.vpndialogs", "com.android.vpndialogs.KnoxVpnPPDialog");
                        intent.addFlags(1350565888);
                        if (mContext != null) {
                            Log.d(TAG, "startActivityAsUser  KnoxVpnPPDialog userId = " + userId);
                            mContext.startActivityAsUser(intent, new UserHandle(userId));
                        }
                    }
                } else {
                    Log.e(TAG, "activateVpnProfile >> mEnterpriseResponseData == null");
                }
            } else {
                Log.e(TAG, "activateVpnProfile >> mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Failed at GenericVpnPolicy API activateVpnProfile-Exception"),
                    TAG);
        }
        return i;
    }

    public int addAllContainerPackagesToVpn(int i, String str) throws IllegalArgumentException {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "addAllContainerPackagesToVpn > mService == null");
                return -1;
            }
            EnterpriseLicenseManager.log(
                    new ContextInfo(Process.myUid()),
                    "GenericVpnPolicy.addAllContainerPackagesToVpn");
            EnterpriseResponseData addAllContainerPackagesToVpn =
                    mKnoxVpnPolicyService.addAllContainerPackagesToVpn(this.vpnContext, i, str);
            if (addAllContainerPackagesToVpn == null) {
                Log.e(TAG, "addAllContainerPackagesToVpn > mEnterpriseResponseData == null");
                return -1;
            }
            if (addAllContainerPackagesToVpn.getFailureState() != 11) {
                return ((Integer) addAllContainerPackagesToVpn.getData()).intValue();
            }
            Log.d(TAG, "The container id entered is invalid and throwing an exception");
            throw new IllegalArgumentException();
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Failed at GenericVpnPolicy API"
                                + " addAllContainerPackagesToVpn-Exception"),
                    TAG);
            return -1;
        }
    }

    public int addAllPackagesToVpn(String str) {
        int i = -1;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseLicenseManager.log(
                        new ContextInfo(Process.myUid()), "GenericVpnPolicy.addAllPackagesToVpn");
                EnterpriseResponseData addAllPackagesToVpn =
                        mKnoxVpnPolicyService.addAllPackagesToVpn(this.vpnContext, str);
                if (addAllPackagesToVpn != null) {
                    i = ((Integer) addAllPackagesToVpn.getData()).intValue();
                } else {
                    Log.e(TAG, "addAllPackagesToVpn > mEnterpriseResponseData == null");
                }
            } else {
                Log.e(TAG, "addAllPackagesToVpn > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Exception at GenericVpnPolicy API addAllPackagesToVpn:"),
                    TAG);
        }
        return i;
    }

    public int addContainerPackagesToVpn(int i, String[] strArr, String str)
            throws IllegalArgumentException {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "addContainerPackageToVpn > mService == null");
                return -1;
            }
            EnterpriseLicenseManager.log(
                    new ContextInfo(Process.myUid()), "GenericVpnPolicy.addContainerPackagesToVpn");
            EnterpriseResponseData addContainerPackagesToVpn =
                    mKnoxVpnPolicyService.addContainerPackagesToVpn(
                            this.vpnContext, i, strArr, str);
            if (addContainerPackagesToVpn == null) {
                Log.e(TAG, "addContainerPackageToVpn > mEnterpriseResponseData == null");
                return -1;
            }
            if (addContainerPackagesToVpn.getFailureState() != 11) {
                return ((Integer) addContainerPackagesToVpn.getData()).intValue();
            }
            Log.d(TAG, "The container id entered is invalid and throwing an exception");
            throw new IllegalArgumentException();
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Failed at GenericVpnPolicy API addContainerPackageToVpn-Exception"),
                    TAG);
            return -1;
        }
    }

    public int addPackagesToVpn(String[] strArr, String str) {
        int i = -1;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseLicenseManager.log(
                        new ContextInfo(Process.myUid()), "GenericVpnPolicy.addPackagesToVpn");
                EnterpriseResponseData addPackagesToVpn =
                        mKnoxVpnPolicyService.addPackagesToVpn(this.vpnContext, strArr, str);
                if (addPackagesToVpn != null) {
                    i = ((Integer) addPackagesToVpn.getData()).intValue();
                } else {
                    Log.e(TAG, "addPackageToVpn > mEnterpriseResponseData == null");
                }
            } else {
                Log.e(TAG, "addPackageToVpn > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Failed at GenericVpnPolicy API addPackagetoDatabase-Exception"),
                    TAG);
        }
        return i;
    }

    public int allowUsbTetheringOverVpn(String str, boolean z, Bundle bundle) {
        int i;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseLicenseManager.log(
                        new ContextInfo(Process.myUid()),
                        "GenericVpnPolicy.allowUsbTetheringOverVpn");
                if (z) {
                    if (bundle != null && !bundle.isEmpty()) {
                        i =
                                !bundle.isEmpty()
                                        ? mKnoxVpnPolicyService.allowAuthUsbTetheringOverVpn(
                                                this.vpnContext, str, bundle)
                                        : 100;
                    }
                    i = mKnoxVpnPolicyService.allowNoAuthUsbTetheringOverVpn(this.vpnContext, str);
                } else {
                    i = mKnoxVpnPolicyService.disallowUsbTetheringOverVpn(this.vpnContext, str);
                }
            } else {
                Log.e(TAG, "KVES not started");
                i = 110;
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Exception at GenericVpnPolicy API allowUsbTetheringOverVpn:"),
                    TAG);
            i = 101;
        }
        if (i == 100) {
            i = VPN_RETURN_INT_SUCCESS;
        }
        if (i != 141) {
            return i;
        }
        throw new SecurityException();
    }

    public int createVpnProfile(String str) {
        int i = VPN_RETURN_INT_ERROR;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseLicenseManager.log(
                        new ContextInfo(Process.myUid()), "GenericVpnPolicy.createVpnProfile");
                EnterpriseResponseData createVpnProfile =
                        mKnoxVpnPolicyService.createVpnProfile(this.vpnContext, str);
                if (createVpnProfile != null) {
                    i = ((Integer) createVpnProfile.getData()).intValue();
                } else {
                    Log.e(TAG, "createVpnProfile Error> mEnterpriseResponseData == null");
                }
            } else {
                Log.e(TAG, "createVpnProfile Error > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Failed at GenericVpnPolicy API createVpnProfile-Exception"),
                    TAG);
        }
        if (i != 141) {
            return i;
        }
        throw new SecurityException();
    }

    public String[] getAllContainerPackagesInVpnProfile(int i, String str)
            throws IllegalArgumentException {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "getAllContainerPackagesInVpnProfile > mService == null");
                return null;
            }
            EnterpriseResponseData allContainerPackagesInVpnProfile =
                    mKnoxVpnPolicyService.getAllContainerPackagesInVpnProfile(
                            this.vpnContext, i, str);
            if (allContainerPackagesInVpnProfile == null) {
                Log.e(TAG, "getAllContainerPackagesInVpnProfile > mEnterpriseResponseData == null");
                return null;
            }
            if (allContainerPackagesInVpnProfile.getFailureState() == 11) {
                Log.d(TAG, "The container id entered is invalid and throwing an exception");
                throw new IllegalArgumentException();
            }
            if (allContainerPackagesInVpnProfile.getFailureState() == 0) {
                return (String[]) allContainerPackagesInVpnProfile.getData();
            }
            return null;
        } catch (RemoteException e) {
            Log.e(
                    TAG,
                    "Failed at EnterpriseContainerManager API getAllContainerPackagesInVpnProfile ",
                    e);
            return null;
        }
    }

    public String[] getAllPackagesInVpnProfile(String str) {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "getAllPackagesInVpnProfile > mService == null");
                return null;
            }
            EnterpriseResponseData allPackagesInVpnProfile =
                    mKnoxVpnPolicyService.getAllPackagesInVpnProfile(this.vpnContext, str);
            if (allPackagesInVpnProfile == null) {
                Log.e(TAG, "getAllPackagesInVpnProfile > mEnterpriseResponseData == null");
                return null;
            }
            if (allPackagesInVpnProfile.getFailureState() == 0) {
                return (String[]) allPackagesInVpnProfile.getData();
            }
            return null;
        } catch (RemoteException e) {
            Log.e(TAG, "Failed at EnterpriseContainerManager API getAllPackagesInVpnProfile ", e);
            return null;
        }
    }

    public List<String> getAllVpnProfiles() {
        List<String> list = null;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseResponseData allVpnProfiles =
                        mKnoxVpnPolicyService.getAllVpnProfiles(this.vpnContext);
                if (allVpnProfiles == null) {
                    Log.e(TAG, "getAllVpnProfiles > mEnterpriseResponseData == null");
                } else if (allVpnProfiles.getFailureState() == 0) {
                    list = (List) allVpnProfiles.getData();
                }
            } else {
                Log.e(TAG, "getAllVpnProfiles > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Failed at GenericVpnPolicy API getAllVpnProfiles-Exception"),
                    TAG);
        }
        return list;
    }

    public CertificateInfo getCACertificate(String str) {
        CertificateInfo certificateInfo = null;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseResponseData cACertificate =
                        mKnoxVpnPolicyService.getCACertificate(this.vpnContext, str);
                if (cACertificate == null) {
                    Log.e(TAG, "getCACertificate > mEnterpriseResponseData == null");
                } else if (cACertificate.getFailureState() == 0) {
                    certificateInfo = (CertificateInfo) cACertificate.getData();
                }
            } else {
                Log.e(TAG, "getCACertificate > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Failed at GenericVpnPolicy API getCACertificate-Exception"),
                    TAG);
        }
        return certificateInfo;
    }

    public String getErrorString(String str) {
        String str2 = null;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseResponseData errorString =
                        mKnoxVpnPolicyService.getErrorString(this.vpnContext, str);
                if (errorString != null) {
                    if (errorString.getStatus() != 0) {
                        if (errorString.getStatus() == 2) {}
                    }
                    str2 = (String) errorString.getData();
                } else {
                    Log.e(TAG, "getErrorString > mEnterpriseResponseData == null");
                }
            } else {
                Log.e(TAG, "getErrorString > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Failed at GenericVpnPolicy API getErrorString-Exception"),
                    TAG);
        }
        return str2;
    }

    @RequiresPermission("com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION")
    public int getNotificationDismissibleFlag(int i) {
        int i2 = 1;
        try {
            if (getKnoxVpnPolicyService() != null) {
                i2 = mKnoxVpnPolicyService.getNotificationDismissibleFlag(this.vpnContext, i);
            } else {
                Log.e(TAG, "getNotificationDismissibleFlag > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Failed at GenericVpnPolicy API"
                                + " getNotificationDismissibleFlag-Exception"),
                    TAG);
        }
        return i2;
    }

    public int getState(String str) {
        int i = -1;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseResponseData state = mKnoxVpnPolicyService.getState(this.vpnContext, str);
                if (state != null) {
                    i = ((Integer) state.getData()).intValue();
                } else {
                    Log.e(TAG, "getState >> mEnterpriseResponseData == null");
                }
            } else {
                Log.e(TAG, "getState >> mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("Failed at GenericVpnPolicy API getState-Exception"), TAG);
        }
        return i;
    }

    public CertificateInfo getUserCertificate(String str) {
        CertificateInfo certificateInfo = null;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseResponseData userCertificate =
                        mKnoxVpnPolicyService.getUserCertificate(this.vpnContext, str);
                if (userCertificate == null) {
                    Log.e(TAG, "getUserCertificate > mEnterpriseResponseData == null");
                } else if (userCertificate.getFailureState() == 0) {
                    certificateInfo = (CertificateInfo) userCertificate.getData();
                }
            } else {
                Log.e(TAG, "getUserCertificate > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Failed at GenericVpnPolicy API getUserCertificate-Exception"),
                    TAG);
        }
        return certificateInfo;
    }

    public int getVpnModeOfOperation(String str) {
        int i = -1;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseResponseData vpnModeOfOperation =
                        mKnoxVpnPolicyService.getVpnModeOfOperation(this.vpnContext, str);
                if (vpnModeOfOperation == null) {
                    Log.e(TAG, "getVpnModeOfOperation > mEnterpriseResponseData == null");
                } else if (vpnModeOfOperation.getFailureState() == 0) {
                    i = ((Integer) vpnModeOfOperation.getData()).intValue();
                }
            } else {
                Log.e(TAG, "getVpnModeOfOperation > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Failed at GenericVpnPolicy API getVpnModeOfOperation-Exception"),
                    TAG);
        }
        return i;
    }

    public String getVpnProfile(String str) {
        String str2 = null;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseResponseData vpnProfile =
                        mKnoxVpnPolicyService.getVpnProfile(this.vpnContext, str);
                if (vpnProfile == null) {
                    Log.e(TAG, "getVpnProfile Error> mEnterpriseResponseData == null");
                } else if (vpnProfile.getFailureState() == 0) {
                    str2 = (String) vpnProfile.getData();
                }
            } else {
                Log.e(TAG, "getVpnProfile Error > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Failed at GenericVpnPolicy API getVpnProfile-Exception"),
                    TAG);
        }
        return str2;
    }

    public int isUsbTetheringOverVpnEnabled(String str) {
        int i;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseLicenseManager.log(
                        new ContextInfo(Process.myUid()),
                        "GenericVpnPolicy.isUsbTetheringOverVpnEnabled");
                i = mKnoxVpnPolicyService.isUsbTetheringOverVpnEnabled(this.vpnContext, str);
            } else {
                Log.e(TAG, "KVES not started");
                i = 110;
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Exception at GenericVpnPolicy API isUsbTetheringOverVpnEnabled:"),
                    TAG);
            i = 101;
        }
        if (i == 100) {
            i = VPN_RETURN_INT_SUCCESS;
        }
        if (i != 141) {
            return i;
        }
        throw new SecurityException();
    }

    public int removeAllContainerPackagesFromVpn(int i, String str)
            throws IllegalArgumentException {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "removeAllContainerPackagesFromVpn > mService == null");
                return -1;
            }
            EnterpriseLicenseManager.log(
                    new ContextInfo(Process.myUid()),
                    "GenericVpnPolicy.removeAllContainerPackagesFromVpn");
            EnterpriseResponseData removeAllContainerPackagesFromVpn =
                    mKnoxVpnPolicyService.removeAllContainerPackagesFromVpn(
                            this.vpnContext, i, str);
            if (removeAllContainerPackagesFromVpn == null) {
                Log.e(TAG, "removeAllContainerPackagesFromVpn > mEnterpriseResponseData == null");
                return -1;
            }
            if (removeAllContainerPackagesFromVpn.getFailureState() != 11) {
                return ((Integer) removeAllContainerPackagesFromVpn.getData()).intValue();
            }
            Log.d(TAG, "The container id entered is invalid and throwing an exception");
            throw new IllegalArgumentException();
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Exception at GenericVpnPolicy API removeAllContainerPackagesFromVpn:"),
                    TAG);
            return -1;
        }
    }

    public int removeAllPackagesFromVpn(String str) {
        int i = -1;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseLicenseManager.log(
                        new ContextInfo(Process.myUid()),
                        "GenericVpnPolicy.removeAllPackagesFromVpn");
                EnterpriseResponseData removeAllPackagesFromVpn =
                        mKnoxVpnPolicyService.removeAllPackagesFromVpn(this.vpnContext, str);
                if (removeAllPackagesFromVpn != null) {
                    i = ((Integer) removeAllPackagesFromVpn.getData()).intValue();
                } else {
                    Log.e(TAG, "removeAllPackagesFromVpn > mEnterpriseResponseData == null");
                }
            } else {
                Log.e(TAG, "removeAllPackagesFromVpn > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Exception at GenericVpnPolicy API removeAllPackagesFromVpn:"),
                    TAG);
        }
        return i;
    }

    public int removeContainerPackagesFromVpn(int i, String[] strArr, String str)
            throws IllegalArgumentException {
        try {
            if (getKnoxVpnPolicyService() == null) {
                Log.e(TAG, "removeContainerPackageFromVpn > mService == null");
                return -1;
            }
            EnterpriseLicenseManager.log(
                    new ContextInfo(Process.myUid()),
                    "GenericVpnPolicy.removeContainerPackagesFromVpn");
            EnterpriseResponseData removeContainerPackagesFromVpn =
                    mKnoxVpnPolicyService.removeContainerPackagesFromVpn(
                            this.vpnContext, i, strArr, str);
            if (removeContainerPackagesFromVpn == null) {
                Log.e(TAG, "removeContainerPackageFromVpn > mEnterpriseResponseData == null");
                return -1;
            }
            if (removeContainerPackagesFromVpn.getFailureState() != 11) {
                return ((Integer) removeContainerPackagesFromVpn.getData()).intValue();
            }
            Log.d(TAG, "The container id entered is invalid and throwing an exception");
            throw new IllegalArgumentException();
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Exception at GenericVpnPolicy API removeContainerPackageFromVpn:"),
                    TAG);
            return -1;
        }
    }

    public int removePackagesFromVpn(String[] strArr, String str) {
        int i = -1;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseLicenseManager.log(
                        new ContextInfo(Process.myUid()), "GenericVpnPolicy.removePackagesFromVpn");
                EnterpriseResponseData removePackagesFromVpn =
                        mKnoxVpnPolicyService.removePackagesFromVpn(this.vpnContext, strArr, str);
                if (removePackagesFromVpn != null) {
                    i = ((Integer) removePackagesFromVpn.getData()).intValue();
                } else {
                    Log.e(TAG, "removePackageFromVpn > mEnterpriseResponseData == null");
                }
            } else {
                Log.e(TAG, "removePackageFromVpn > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Exception at GenericVpnPolicy API removePackageFromVpn:"),
                    TAG);
        }
        return i;
    }

    public int removeVpnProfile(String str) {
        int i = VPN_RETURN_INT_ERROR;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseLicenseManager.log(
                        new ContextInfo(Process.myUid()), "GenericVpnPolicy.removeVpnProfile");
                EnterpriseResponseData removeVpnProfile =
                        mKnoxVpnPolicyService.removeVpnProfile(this.vpnContext, str);
                if (removeVpnProfile != null) {
                    i = ((Integer) removeVpnProfile.getData()).intValue();
                } else {
                    Log.e(TAG, "removeVpnProfile  Error> mEnterpriseResponseData == null");
                }
            } else {
                Log.e(TAG, "removeVpnProfile  Error > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Failed at GenericVpnPolicy API removeVpnProfile -Exception"),
                    TAG);
        }
        return i;
    }

    public boolean setAutoRetryOnConnectionError(String str, boolean z) {
        boolean z2 = false;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseLicenseManager.log(
                        new ContextInfo(Process.myUid()),
                        "GenericVpnPolicy.setAutoRetryOnConnectionError");
                EnterpriseResponseData autoRetryOnConnectionError =
                        mKnoxVpnPolicyService.setAutoRetryOnConnectionError(
                                this.vpnContext, str, z);
                if (autoRetryOnConnectionError == null) {
                    Log.e(TAG, "setAutoRetryOnConnection Error > mEnterpriseResponseData == null");
                } else if (autoRetryOnConnectionError.getFailureState() == 0) {
                    z2 = ((Boolean) autoRetryOnConnectionError.getData()).booleanValue();
                }
            } else {
                Log.e(TAG, "setAutoRetryOnConnection Error > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Failed at GenericVpnPolicy API"
                                + " setAutoRetryOnConnectionError-Exception"),
                    TAG);
        }
        return z2;
    }

    public boolean setCACertificate(String str, byte[] bArr) {
        boolean z = false;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseLicenseManager.log(
                        new ContextInfo(Process.myUid()), "GenericVpnPolicy.setCACertificate");
                EnterpriseResponseData cACertificate =
                        mKnoxVpnPolicyService.setCACertificate(this.vpnContext, str, bArr);
                if (cACertificate == null) {
                    Log.e(TAG, "setCACertificate > mEnterpriseResponseData == null");
                } else if (cACertificate.getFailureState() == 0) {
                    z = ((Boolean) cACertificate.getData()).booleanValue();
                }
            } else {
                Log.e(TAG, "setCACertificate > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder("Failed at GenericVpnPolicy API setCACertificate-Exception"),
                    TAG);
        }
        return z;
    }

    @RequiresPermission("com.samsung.android.knox.permission.KNOX_INTERNAL_EXCEPTION")
    public int setNotificationDismissibleFlag(String str, int i, int i2) {
        int i3 = -1;
        try {
            if (getKnoxVpnPolicyService() != null) {
                i3 =
                        mKnoxVpnPolicyService.setNotificationDismissibleFlag(
                                this.vpnContext, str, i, i2);
            } else {
                Log.e(TAG, "setNotificationDismissibleFlag > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Failed at GenericVpnPolicy API"
                                + " setNotificationDismissibleFlag-Exception"),
                    TAG);
        }
        return i3;
    }

    public boolean setServerCertValidationUserAcceptanceCriteria(
            String str, boolean z, List<Integer> list, int i) {
        boolean z2 = false;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseLicenseManager.log(
                        new ContextInfo(Process.myUid()),
                        "GenericVpnPolicy.setServerCertValidationUserAcceptanceCriteria");
                EnterpriseResponseData serverCertValidationUserAcceptanceCriteria =
                        mKnoxVpnPolicyService.setServerCertValidationUserAcceptanceCriteria(
                                this.vpnContext, str, z, list, i);
                if (serverCertValidationUserAcceptanceCriteria == null) {
                    Log.e(
                            TAG,
                            "setServerCertValidationUserAcceptanceCriteria Error >"
                                + " mEnterpriseResponseData == null");
                } else if (serverCertValidationUserAcceptanceCriteria.getFailureState() == 0) {
                    z2 =
                            ((Boolean) serverCertValidationUserAcceptanceCriteria.getData())
                                    .booleanValue();
                }
            } else {
                Log.e(
                        TAG,
                        "setServerCertValidationUserAcceptanceCriteria Error > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Failed at GenericVpnPolicy API"
                                + " setServerCertValidationUserAcceptanceCriteria-Exception"),
                    TAG);
        }
        return z2;
    }

    public boolean setUserCertificate(String str, byte[] bArr, String str2) {
        boolean z = false;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseLicenseManager.log(
                        new ContextInfo(Process.myUid()), "GenericVpnPolicy.setUserCertificate");
                EnterpriseResponseData userCertificate =
                        mKnoxVpnPolicyService.setUserCertificate(this.vpnContext, str, bArr, str2);
                if (userCertificate == null) {
                    Log.e(TAG, "setUserCertificate > mEnterpriseResponseData == null");
                } else if (userCertificate.getFailureState() == 0) {
                    z = ((Boolean) userCertificate.getData()).booleanValue();
                }
            } else {
                Log.e(TAG, "setUserCertificate > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Failed at GenericVpnPolicy API setUserCertificate-Exception"),
                    TAG);
        }
        return z;
    }

    public int setVpnModeOfOperation(String str, int i) {
        int i2 = -1;
        try {
            if (getKnoxVpnPolicyService() != null) {
                EnterpriseLicenseManager.log(
                        new ContextInfo(Process.myUid()), "GenericVpnPolicy.setVpnModeOfOperation");
                EnterpriseResponseData vpnModeOfOperation =
                        mKnoxVpnPolicyService.setVpnModeOfOperation(this.vpnContext, str, i);
                if (vpnModeOfOperation == null) {
                    Log.e(TAG, "setVpnModeOfOperation > mEnterpriseResponseData == null");
                } else if (vpnModeOfOperation.getFailureState() == 0) {
                    i2 = ((Integer) vpnModeOfOperation.getData()).intValue();
                }
            } else {
                Log.e(TAG, "setVpnModeOfOperation > mService == null");
            }
        } catch (RemoteException e) {
            RCPPolicy$$ExternalSyntheticOutline0.m(
                    e,
                    new StringBuilder(
                            "Failed at GenericVpnPolicy API setVpnModeOfOperation-Exception"),
                    TAG);
        }
        return i2;
    }

    public GenericVpnPolicy(Context context, KnoxVpnContext knoxVpnContext) {
        this.vpnContext = null;
        this.vendorName = null;
        mContext = context;
        if (DBG) {
            MainClearConfirm$$ExternalSyntheticOutline0.m(
                    new StringBuilder("GenericVpnPolicy ctor : vendorName = "),
                    knoxVpnContext.vendorName,
                    TAG);
        }
        this.vpnContext = knoxVpnContext;
    }
}
