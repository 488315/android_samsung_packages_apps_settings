package com.samsung.android.knox.keystore;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.samsung.android.knox.ContextInfo;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface ICertificatePolicy extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.keystore.ICertificatePolicy";

    boolean addPermissionApplicationPrivateKey(
            ContextInfo contextInfo,
            PermissionApplicationPrivateKey permissionApplicationPrivateKey)
            throws RemoteException;

    boolean addTrustedCaCertificateList(ContextInfo contextInfo, List<CertificateInfo> list)
            throws RemoteException;

    boolean addUntrustedCertificateList(ContextInfo contextInfo, List<CertificateInfo> list)
            throws RemoteException;

    boolean allowUserRemoveCertificates(ContextInfo contextInfo, boolean z) throws RemoteException;

    boolean clearPermissionApplicationPrivateKey(ContextInfo contextInfo) throws RemoteException;

    boolean clearTrustedCaCertificateList(ContextInfo contextInfo) throws RemoteException;

    boolean clearUntrustedCertificateList(ContextInfo contextInfo) throws RemoteException;

    boolean enableCertificateFailureNotification(ContextInfo contextInfo, boolean z)
            throws RemoteException;

    boolean enableCertificateValidationAtInstall(ContextInfo contextInfo, boolean z)
            throws RemoteException;

    boolean enableSignatureIdentityInformation(ContextInfo contextInfo, boolean z)
            throws RemoteException;

    List getIdentitiesFromSignatures(ContextInfo contextInfo, List<String> list)
            throws RemoteException;

    List<PermissionApplicationPrivateKey> getListPermissionApplicationPrivateKey(
            ContextInfo contextInfo) throws RemoteException;

    List<CertificateControlInfo> getTrustedCaCertificateList(ContextInfo contextInfo)
            throws RemoteException;

    List<CertificateControlInfo> getUntrustedCertificateList(ContextInfo contextInfo)
            throws RemoteException;

    boolean isCaCertificateDisabledAsUser(String str, int i) throws RemoteException;

    boolean isCaCertificateTrustedAsUser(CertificateInfo certificateInfo, boolean z, int i)
            throws RemoteException;

    boolean isCertificateFailureNotificationEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isCertificateTrustedUntrustedEnabledAsUser(int i) throws RemoteException;

    boolean isCertificateValidationAtInstallEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isCertificateValidationAtInstallEnabledAsUser(int i) throws RemoteException;

    String isPrivateKeyApplicationPermitted(
            ContextInfo contextInfo, String str, String str2, int i, List<String> list)
            throws RemoteException;

    String isPrivateKeyApplicationPermittedAsUser(
            String str, String str2, int i, List<String> list, int i2) throws RemoteException;

    boolean isSignatureIdentityInformationEnabled(ContextInfo contextInfo, boolean z)
            throws RemoteException;

    boolean isUserRemoveCertificatesAllowed(ContextInfo contextInfo) throws RemoteException;

    boolean isUserRemoveCertificatesAllowedAsUser(int i) throws RemoteException;

    void notifyCertificateFailure(String str, String str2, boolean z) throws RemoteException;

    void notifyCertificateFailureAsUser(String str, String str2, boolean z, int i)
            throws RemoteException;

    void notifyCertificateRemovedAsUser(String str, int i) throws RemoteException;

    void notifyUserKeystoreUnlocked(int i) throws RemoteException;

    boolean removePermissionApplicationPrivateKey(
            ContextInfo contextInfo,
            PermissionApplicationPrivateKey permissionApplicationPrivateKey)
            throws RemoteException;

    boolean removeTrustedCaCertificateList(ContextInfo contextInfo, List<CertificateInfo> list)
            throws RemoteException;

    boolean removeUntrustedCertificateList(ContextInfo contextInfo, List<CertificateInfo> list)
            throws RemoteException;

    int validateCertificateAtInstall(CertificateInfo certificateInfo) throws RemoteException;

    int validateCertificateAtInstallAsUser(CertificateInfo certificateInfo, int i)
            throws RemoteException;

    int validateChainAtInstall(List<CertificateInfo> list) throws RemoteException;

    int validateChainAtInstallAsUser(List<CertificateInfo> list, int i) throws RemoteException;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Stub extends Binder implements ICertificatePolicy {
        public static final int TRANSACTION_addPermissionApplicationPrivateKey = 27;
        public static final int TRANSACTION_addTrustedCaCertificateList = 1;
        public static final int TRANSACTION_addUntrustedCertificateList = 7;
        public static final int TRANSACTION_allowUserRemoveCertificates = 24;
        public static final int TRANSACTION_clearPermissionApplicationPrivateKey = 29;
        public static final int TRANSACTION_clearTrustedCaCertificateList = 4;
        public static final int TRANSACTION_clearUntrustedCertificateList = 10;
        public static final int TRANSACTION_enableCertificateFailureNotification = 12;
        public static final int TRANSACTION_enableCertificateValidationAtInstall = 16;
        public static final int TRANSACTION_enableSignatureIdentityInformation = 35;
        public static final int TRANSACTION_getIdentitiesFromSignatures = 11;
        public static final int TRANSACTION_getListPermissionApplicationPrivateKey = 30;
        public static final int TRANSACTION_getTrustedCaCertificateList = 2;
        public static final int TRANSACTION_getUntrustedCertificateList = 9;
        public static final int TRANSACTION_isCaCertificateDisabledAsUser = 6;
        public static final int TRANSACTION_isCaCertificateTrustedAsUser = 3;
        public static final int TRANSACTION_isCertificateFailureNotificationEnabled = 13;
        public static final int TRANSACTION_isCertificateTrustedUntrustedEnabledAsUser = 34;
        public static final int TRANSACTION_isCertificateValidationAtInstallEnabled = 17;
        public static final int TRANSACTION_isCertificateValidationAtInstallEnabledAsUser = 18;
        public static final int TRANSACTION_isPrivateKeyApplicationPermitted = 31;
        public static final int TRANSACTION_isPrivateKeyApplicationPermittedAsUser = 32;
        public static final int TRANSACTION_isSignatureIdentityInformationEnabled = 36;
        public static final int TRANSACTION_isUserRemoveCertificatesAllowed = 25;
        public static final int TRANSACTION_isUserRemoveCertificatesAllowedAsUser = 26;
        public static final int TRANSACTION_notifyCertificateFailure = 14;
        public static final int TRANSACTION_notifyCertificateFailureAsUser = 15;
        public static final int TRANSACTION_notifyCertificateRemovedAsUser = 23;
        public static final int TRANSACTION_notifyUserKeystoreUnlocked = 33;
        public static final int TRANSACTION_removePermissionApplicationPrivateKey = 28;
        public static final int TRANSACTION_removeTrustedCaCertificateList = 5;
        public static final int TRANSACTION_removeUntrustedCertificateList = 8;
        public static final int TRANSACTION_validateCertificateAtInstall = 19;
        public static final int TRANSACTION_validateCertificateAtInstallAsUser = 20;
        public static final int TRANSACTION_validateChainAtInstall = 21;
        public static final int TRANSACTION_validateChainAtInstallAsUser = 22;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements ICertificatePolicy {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean addPermissionApplicationPrivateKey(
                    ContextInfo contextInfo,
                    PermissionApplicationPrivateKey permissionApplicationPrivateKey)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(permissionApplicationPrivateKey, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean addTrustedCaCertificateList(
                    ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean addUntrustedCertificateList(
                    ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean allowUserRemoveCertificates(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean clearPermissionApplicationPrivateKey(ContextInfo contextInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean clearTrustedCaCertificateList(ContextInfo contextInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean clearUntrustedCertificateList(ContextInfo contextInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean enableCertificateFailureNotification(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean enableCertificateValidationAtInstall(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean enableSignatureIdentityInformation(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public List getIdentitiesFromSignatures(ContextInfo contextInfo, List<String> list)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readArrayList(getClass().getClassLoader());
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return ICertificatePolicy.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public List<PermissionApplicationPrivateKey> getListPermissionApplicationPrivateKey(
                    ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(PermissionApplicationPrivateKey.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public List<CertificateControlInfo> getTrustedCaCertificateList(ContextInfo contextInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(CertificateControlInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public List<CertificateControlInfo> getUntrustedCertificateList(ContextInfo contextInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(CertificateControlInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isCaCertificateDisabledAsUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isCaCertificateTrustedAsUser(
                    CertificateInfo certificateInfo, boolean z, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(certificateInfo, 0);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isCertificateFailureNotificationEnabled(ContextInfo contextInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isCertificateTrustedUntrustedEnabledAsUser(int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isCertificateValidationAtInstallEnabled(ContextInfo contextInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isCertificateValidationAtInstallEnabledAsUser(int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public String isPrivateKeyApplicationPermitted(
                    ContextInfo contextInfo, String str, String str2, int i, List<String> list)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public String isPrivateKeyApplicationPermittedAsUser(
                    String str, String str2, int i, List<String> list, int i2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    obtain.writeInt(i2);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isSignatureIdentityInformationEnabled(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isUserRemoveCertificatesAllowed(ContextInfo contextInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean isUserRemoveCertificatesAllowedAsUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public void notifyCertificateFailure(String str, String str2, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public void notifyCertificateFailureAsUser(String str, String str2, boolean z, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public void notifyCertificateRemovedAsUser(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public void notifyUserKeystoreUnlocked(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean removePermissionApplicationPrivateKey(
                    ContextInfo contextInfo,
                    PermissionApplicationPrivateKey permissionApplicationPrivateKey)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedObject(permissionApplicationPrivateKey, 0);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean removeTrustedCaCertificateList(
                    ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public boolean removeUntrustedCertificateList(
                    ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public int validateCertificateAtInstall(CertificateInfo certificateInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(certificateInfo, 0);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public int validateCertificateAtInstallAsUser(CertificateInfo certificateInfo, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedObject(certificateInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public int validateChainAtInstall(List<CertificateInfo> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.keystore.ICertificatePolicy
            public int validateChainAtInstallAsUser(List<CertificateInfo> list, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICertificatePolicy.DESCRIPTOR);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, ICertificatePolicy.DESCRIPTOR);
        }

        public static ICertificatePolicy asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(ICertificatePolicy.DESCRIPTOR);
            return (queryLocalInterface == null
                            || !(queryLocalInterface instanceof ICertificatePolicy))
                    ? new Proxy(iBinder)
                    : (ICertificatePolicy) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICertificatePolicy.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICertificatePolicy.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList createTypedArrayList =
                            parcel.createTypedArrayList(CertificateInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean addTrustedCaCertificateList =
                            addTrustedCaCertificateList(contextInfo, createTypedArrayList);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addTrustedCaCertificateList);
                    return true;
                case 2:
                    ContextInfo contextInfo2 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<CertificateControlInfo> trustedCaCertificateList =
                            getTrustedCaCertificateList(contextInfo2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(trustedCaCertificateList, 1);
                    return true;
                case 3:
                    CertificateInfo certificateInfo =
                            (CertificateInfo) parcel.readTypedObject(CertificateInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCaCertificateTrustedAsUser =
                            isCaCertificateTrustedAsUser(certificateInfo, readBoolean, readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCaCertificateTrustedAsUser);
                    return true;
                case 4:
                    ContextInfo contextInfo3 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearTrustedCaCertificateList =
                            clearTrustedCaCertificateList(contextInfo3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearTrustedCaCertificateList);
                    return true;
                case 5:
                    ContextInfo contextInfo4 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList createTypedArrayList2 =
                            parcel.createTypedArrayList(CertificateInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean removeTrustedCaCertificateList =
                            removeTrustedCaCertificateList(contextInfo4, createTypedArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeTrustedCaCertificateList);
                    return true;
                case 6:
                    String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCaCertificateDisabledAsUser =
                            isCaCertificateDisabledAsUser(readString, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCaCertificateDisabledAsUser);
                    return true;
                case 7:
                    ContextInfo contextInfo5 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList createTypedArrayList3 =
                            parcel.createTypedArrayList(CertificateInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean addUntrustedCertificateList =
                            addUntrustedCertificateList(contextInfo5, createTypedArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addUntrustedCertificateList);
                    return true;
                case 8:
                    ContextInfo contextInfo6 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList createTypedArrayList4 =
                            parcel.createTypedArrayList(CertificateInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean removeUntrustedCertificateList =
                            removeUntrustedCertificateList(contextInfo6, createTypedArrayList4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeUntrustedCertificateList);
                    return true;
                case 9:
                    ContextInfo contextInfo7 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<CertificateControlInfo> untrustedCertificateList =
                            getUntrustedCertificateList(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(untrustedCertificateList, 1);
                    return true;
                case 10:
                    ContextInfo contextInfo8 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearUntrustedCertificateList =
                            clearUntrustedCertificateList(contextInfo8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearUntrustedCertificateList);
                    return true;
                case 11:
                    ContextInfo contextInfo9 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    List identitiesFromSignatures =
                            getIdentitiesFromSignatures(contextInfo9, createStringArrayList);
                    parcel2.writeNoException();
                    parcel2.writeList(identitiesFromSignatures);
                    return true;
                case 12:
                    ContextInfo contextInfo10 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableCertificateFailureNotification =
                            enableCertificateFailureNotification(contextInfo10, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableCertificateFailureNotification);
                    return true;
                case 13:
                    ContextInfo contextInfo11 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCertificateFailureNotificationEnabled =
                            isCertificateFailureNotificationEnabled(contextInfo11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCertificateFailureNotificationEnabled);
                    return true;
                case 14:
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    notifyCertificateFailure(readString2, readString3, readBoolean3);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    boolean readBoolean4 = parcel.readBoolean();
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCertificateFailureAsUser(
                            readString4, readString5, readBoolean4, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    ContextInfo contextInfo12 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableCertificateValidationAtInstall =
                            enableCertificateValidationAtInstall(contextInfo12, readBoolean5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableCertificateValidationAtInstall);
                    return true;
                case 17:
                    ContextInfo contextInfo13 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isCertificateValidationAtInstallEnabled =
                            isCertificateValidationAtInstallEnabled(contextInfo13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCertificateValidationAtInstallEnabled);
                    return true;
                case 18:
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCertificateValidationAtInstallEnabledAsUser =
                            isCertificateValidationAtInstallEnabledAsUser(readInt4);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCertificateValidationAtInstallEnabledAsUser);
                    return true;
                case 19:
                    CertificateInfo certificateInfo2 =
                            (CertificateInfo) parcel.readTypedObject(CertificateInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int validateCertificateAtInstall =
                            validateCertificateAtInstall(certificateInfo2);
                    parcel2.writeNoException();
                    parcel2.writeInt(validateCertificateAtInstall);
                    return true;
                case 20:
                    CertificateInfo certificateInfo3 =
                            (CertificateInfo) parcel.readTypedObject(CertificateInfo.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int validateCertificateAtInstallAsUser =
                            validateCertificateAtInstallAsUser(certificateInfo3, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeInt(validateCertificateAtInstallAsUser);
                    return true;
                case 21:
                    ArrayList createTypedArrayList5 =
                            parcel.createTypedArrayList(CertificateInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    int validateChainAtInstall = validateChainAtInstall(createTypedArrayList5);
                    parcel2.writeNoException();
                    parcel2.writeInt(validateChainAtInstall);
                    return true;
                case 22:
                    ArrayList createTypedArrayList6 =
                            parcel.createTypedArrayList(CertificateInfo.CREATOR);
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int validateChainAtInstallAsUser =
                            validateChainAtInstallAsUser(createTypedArrayList6, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeInt(validateChainAtInstallAsUser);
                    return true;
                case 23:
                    String readString6 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyCertificateRemovedAsUser(readString6, readInt7);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    ContextInfo contextInfo14 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean allowUserRemoveCertificates =
                            allowUserRemoveCertificates(contextInfo14, readBoolean6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(allowUserRemoveCertificates);
                    return true;
                case 25:
                    ContextInfo contextInfo15 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isUserRemoveCertificatesAllowed =
                            isUserRemoveCertificatesAllowed(contextInfo15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserRemoveCertificatesAllowed);
                    return true;
                case 26:
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isUserRemoveCertificatesAllowedAsUser =
                            isUserRemoveCertificatesAllowedAsUser(readInt8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isUserRemoveCertificatesAllowedAsUser);
                    return true;
                case 27:
                    ContextInfo contextInfo16 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    PermissionApplicationPrivateKey permissionApplicationPrivateKey =
                            (PermissionApplicationPrivateKey)
                                    parcel.readTypedObject(PermissionApplicationPrivateKey.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean addPermissionApplicationPrivateKey =
                            addPermissionApplicationPrivateKey(
                                    contextInfo16, permissionApplicationPrivateKey);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addPermissionApplicationPrivateKey);
                    return true;
                case 28:
                    ContextInfo contextInfo17 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    PermissionApplicationPrivateKey permissionApplicationPrivateKey2 =
                            (PermissionApplicationPrivateKey)
                                    parcel.readTypedObject(PermissionApplicationPrivateKey.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean removePermissionApplicationPrivateKey =
                            removePermissionApplicationPrivateKey(
                                    contextInfo17, permissionApplicationPrivateKey2);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removePermissionApplicationPrivateKey);
                    return true;
                case 29:
                    ContextInfo contextInfo18 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean clearPermissionApplicationPrivateKey =
                            clearPermissionApplicationPrivateKey(contextInfo18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(clearPermissionApplicationPrivateKey);
                    return true;
                case 30:
                    ContextInfo contextInfo19 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    List<PermissionApplicationPrivateKey> listPermissionApplicationPrivateKey =
                            getListPermissionApplicationPrivateKey(contextInfo19);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(listPermissionApplicationPrivateKey, 1);
                    return true;
                case 31:
                    ContextInfo contextInfo20 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    String isPrivateKeyApplicationPermitted =
                            isPrivateKeyApplicationPermitted(
                                    contextInfo20,
                                    readString7,
                                    readString8,
                                    readInt9,
                                    createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeString(isPrivateKeyApplicationPermitted);
                    return true;
                case 32:
                    String readString9 = parcel.readString();
                    String readString10 = parcel.readString();
                    int readInt10 = parcel.readInt();
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    String isPrivateKeyApplicationPermittedAsUser =
                            isPrivateKeyApplicationPermittedAsUser(
                                    readString9,
                                    readString10,
                                    readInt10,
                                    createStringArrayList3,
                                    readInt11);
                    parcel2.writeNoException();
                    parcel2.writeString(isPrivateKeyApplicationPermittedAsUser);
                    return true;
                case 33:
                    int readInt12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    notifyUserKeystoreUnlocked(readInt12);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isCertificateTrustedUntrustedEnabledAsUser =
                            isCertificateTrustedUntrustedEnabledAsUser(readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isCertificateTrustedUntrustedEnabledAsUser);
                    return true;
                case 35:
                    ContextInfo contextInfo21 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean enableSignatureIdentityInformation =
                            enableSignatureIdentityInformation(contextInfo21, readBoolean7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enableSignatureIdentityInformation);
                    return true;
                case 36:
                    ContextInfo contextInfo22 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean isSignatureIdentityInformationEnabled =
                            isSignatureIdentityInformationEnabled(contextInfo22, readBoolean8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSignatureIdentityInformationEnabled);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Default implements ICertificatePolicy {
        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean addPermissionApplicationPrivateKey(
                ContextInfo contextInfo,
                PermissionApplicationPrivateKey permissionApplicationPrivateKey)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean addTrustedCaCertificateList(
                ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean addUntrustedCertificateList(
                ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean allowUserRemoveCertificates(ContextInfo contextInfo, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean clearPermissionApplicationPrivateKey(ContextInfo contextInfo)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean clearTrustedCaCertificateList(ContextInfo contextInfo)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean clearUntrustedCertificateList(ContextInfo contextInfo)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean enableCertificateFailureNotification(ContextInfo contextInfo, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean enableCertificateValidationAtInstall(ContextInfo contextInfo, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean enableSignatureIdentityInformation(ContextInfo contextInfo, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public List getIdentitiesFromSignatures(ContextInfo contextInfo, List<String> list)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public List<PermissionApplicationPrivateKey> getListPermissionApplicationPrivateKey(
                ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public List<CertificateControlInfo> getTrustedCaCertificateList(ContextInfo contextInfo)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public List<CertificateControlInfo> getUntrustedCertificateList(ContextInfo contextInfo)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isCaCertificateDisabledAsUser(String str, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isCaCertificateTrustedAsUser(
                CertificateInfo certificateInfo, boolean z, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isCertificateFailureNotificationEnabled(ContextInfo contextInfo)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isCertificateTrustedUntrustedEnabledAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isCertificateValidationAtInstallEnabled(ContextInfo contextInfo)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isCertificateValidationAtInstallEnabledAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public String isPrivateKeyApplicationPermitted(
                ContextInfo contextInfo, String str, String str2, int i, List<String> list)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public String isPrivateKeyApplicationPermittedAsUser(
                String str, String str2, int i, List<String> list, int i2) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isSignatureIdentityInformationEnabled(ContextInfo contextInfo, boolean z)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isUserRemoveCertificatesAllowed(ContextInfo contextInfo)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean isUserRemoveCertificatesAllowedAsUser(int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean removePermissionApplicationPrivateKey(
                ContextInfo contextInfo,
                PermissionApplicationPrivateKey permissionApplicationPrivateKey)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean removeTrustedCaCertificateList(
                ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public boolean removeUntrustedCertificateList(
                ContextInfo contextInfo, List<CertificateInfo> list) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public int validateCertificateAtInstall(CertificateInfo certificateInfo)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public int validateCertificateAtInstallAsUser(CertificateInfo certificateInfo, int i)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public int validateChainAtInstall(List<CertificateInfo> list) throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public int validateChainAtInstallAsUser(List<CertificateInfo> list, int i)
                throws RemoteException {
            return 0;
        }

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public void notifyUserKeystoreUnlocked(int i) throws RemoteException {}

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public void notifyCertificateRemovedAsUser(String str, int i) throws RemoteException {}

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public void notifyCertificateFailure(String str, String str2, boolean z)
                throws RemoteException {}

        @Override // com.samsung.android.knox.keystore.ICertificatePolicy
        public void notifyCertificateFailureAsUser(String str, String str2, boolean z, int i)
                throws RemoteException {}
    }
}
