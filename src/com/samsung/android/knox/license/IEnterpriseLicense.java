package com.samsung.android.knox.license;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.samsung.android.knox.ContextInfo;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public interface IEnterpriseLicense extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.license.IEnterpriseLicense";

    void activateKnoxLicense(
            ContextInfo contextInfo,
            String str,
            String str2,
            ILicenseResultCallback iLicenseResultCallback)
            throws RemoteException;

    void activateKnoxLicenseForUMC(ContextInfo contextInfo, String str, String str2)
            throws RemoteException;

    void activateLicense(
            ContextInfo contextInfo,
            String str,
            String str2,
            String str3,
            ILicenseResultCallback iLicenseResultCallback)
            throws RemoteException;

    void activateLicenseForUMC(ContextInfo contextInfo, String str, String str2, String str3)
            throws RemoteException;

    void deActivateKnoxLicense(
            ContextInfo contextInfo,
            String str,
            String str2,
            ILicenseResultCallback iLicenseResultCallback)
            throws RemoteException;

    boolean deleteAllApiCallData() throws RemoteException;

    boolean deleteApiCallData(String str, String str2, Error error) throws RemoteException;

    boolean deleteApiCallDataByAdmin(String str) throws RemoteException;

    boolean deleteLicense(String str) throws RemoteException;

    boolean deleteLicenseByAdmin(String str) throws RemoteException;

    List<ActivationInfo> getAllLicenseActivationsInfos() throws RemoteException;

    LicenseInfo[] getAllLicenseInfo() throws RemoteException;

    Bundle getApiCallData(String str) throws RemoteException;

    Bundle getApiCallDataByAdmin(ContextInfo contextInfo, String str) throws RemoteException;

    List<String> getELMPermissions(String str) throws RemoteException;

    String getInstanceId(String str) throws RemoteException;

    ActivationInfo getLicenseActivationInfo(ContextInfo contextInfo, String str)
            throws RemoteException;

    LicenseInfo getLicenseInfo(String str) throws RemoteException;

    LicenseInfo getLicenseInfoByAdmin(String str) throws RemoteException;

    RightsObject getRightsObject(String str) throws RemoteException;

    RightsObject getRightsObjectByAdmin(String str) throws RemoteException;

    boolean isEulaBypassAllowed(String str) throws RemoteException;

    boolean isServiceAvailable(String str, String str2) throws RemoteException;

    void log(ContextInfo contextInfo, String str, boolean z, boolean z2) throws RemoteException;

    void notifyKlmObservers(String str, LicenseResult licenseResult) throws RemoteException;

    boolean processKnoxLicenseResponse(
            String str,
            String str2,
            String str3,
            String str4,
            Error error,
            int i,
            String str5,
            RightsObject rightsObject,
            int i2)
            throws RemoteException;

    boolean processLicenseActivationResponse(
            String str,
            String str2,
            String str3,
            String str4,
            String str5,
            RightsObject rightsObject,
            Error error,
            String str6,
            String str7,
            int i)
            throws RemoteException;

    boolean processLicenseValidationResult(
            String str,
            RightsObject rightsObject,
            Error error,
            String str2,
            String str3,
            String str4,
            String str5)
            throws RemoteException;

    boolean resetLicense(String str) throws RemoteException;

    boolean resetLicenseByAdmin(String str) throws RemoteException;

    void updateAdminPermissions() throws RemoteException;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class Default implements IEnterpriseLicense {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean deleteAllApiCallData() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean deleteApiCallData(String str, String str2, Error error)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean deleteApiCallDataByAdmin(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean deleteLicense(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean deleteLicenseByAdmin(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public List<ActivationInfo> getAllLicenseActivationsInfos() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public LicenseInfo[] getAllLicenseInfo() throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public Bundle getApiCallData(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public Bundle getApiCallDataByAdmin(ContextInfo contextInfo, String str)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public List<String> getELMPermissions(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public String getInstanceId(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public ActivationInfo getLicenseActivationInfo(ContextInfo contextInfo, String str)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public LicenseInfo getLicenseInfo(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public LicenseInfo getLicenseInfoByAdmin(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public RightsObject getRightsObject(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public RightsObject getRightsObjectByAdmin(String str) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean isEulaBypassAllowed(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean isServiceAvailable(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean processKnoxLicenseResponse(
                String str,
                String str2,
                String str3,
                String str4,
                Error error,
                int i,
                String str5,
                RightsObject rightsObject,
                int i2)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean processLicenseActivationResponse(
                String str,
                String str2,
                String str3,
                String str4,
                String str5,
                RightsObject rightsObject,
                Error error,
                String str6,
                String str7,
                int i)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean processLicenseValidationResult(
                String str,
                RightsObject rightsObject,
                Error error,
                String str2,
                String str3,
                String str4,
                String str5)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean resetLicense(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public boolean resetLicenseByAdmin(String str) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public void updateAdminPermissions() throws RemoteException {}

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public void notifyKlmObservers(String str, LicenseResult licenseResult)
                throws RemoteException {}

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public void activateKnoxLicenseForUMC(ContextInfo contextInfo, String str, String str2)
                throws RemoteException {}

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public void activateKnoxLicense(
                ContextInfo contextInfo,
                String str,
                String str2,
                ILicenseResultCallback iLicenseResultCallback)
                throws RemoteException {}

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public void activateLicenseForUMC(
                ContextInfo contextInfo, String str, String str2, String str3)
                throws RemoteException {}

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public void deActivateKnoxLicense(
                ContextInfo contextInfo,
                String str,
                String str2,
                ILicenseResultCallback iLicenseResultCallback)
                throws RemoteException {}

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public void log(ContextInfo contextInfo, String str, boolean z, boolean z2)
                throws RemoteException {}

        @Override // com.samsung.android.knox.license.IEnterpriseLicense
        public void activateLicense(
                ContextInfo contextInfo,
                String str,
                String str2,
                String str3,
                ILicenseResultCallback iLicenseResultCallback)
                throws RemoteException {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Stub extends Binder implements IEnterpriseLicense {
        static final int TRANSACTION_activateKnoxLicense = 20;
        static final int TRANSACTION_activateKnoxLicenseForUMC = 21;
        static final int TRANSACTION_activateLicense = 10;
        static final int TRANSACTION_activateLicenseForUMC = 13;
        static final int TRANSACTION_deActivateKnoxLicense = 22;
        static final int TRANSACTION_deleteAllApiCallData = 25;
        static final int TRANSACTION_deleteApiCallData = 4;
        static final int TRANSACTION_deleteApiCallDataByAdmin = 5;
        static final int TRANSACTION_deleteLicense = 17;
        static final int TRANSACTION_deleteLicenseByAdmin = 18;
        static final int TRANSACTION_getAllLicenseActivationsInfos = 29;
        static final int TRANSACTION_getAllLicenseInfo = 7;
        static final int TRANSACTION_getApiCallData = 3;
        static final int TRANSACTION_getApiCallDataByAdmin = 6;
        static final int TRANSACTION_getELMPermissions = 23;
        static final int TRANSACTION_getInstanceId = 24;
        static final int TRANSACTION_getLicenseActivationInfo = 28;
        static final int TRANSACTION_getLicenseInfo = 8;
        static final int TRANSACTION_getLicenseInfoByAdmin = 9;
        static final int TRANSACTION_getRightsObject = 1;
        static final int TRANSACTION_getRightsObjectByAdmin = 2;
        static final int TRANSACTION_isEulaBypassAllowed = 30;
        static final int TRANSACTION_isServiceAvailable = 26;
        static final int TRANSACTION_log = 14;
        static final int TRANSACTION_notifyKlmObservers = 27;
        static final int TRANSACTION_processKnoxLicenseResponse = 19;
        static final int TRANSACTION_processLicenseActivationResponse = 11;
        static final int TRANSACTION_processLicenseValidationResult = 12;
        static final int TRANSACTION_resetLicense = 15;
        static final int TRANSACTION_resetLicenseByAdmin = 16;
        static final int TRANSACTION_updateAdminPermissions = 31;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements IEnterpriseLicense {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public void activateKnoxLicense(
                    ContextInfo contextInfo,
                    String str,
                    String str2,
                    ILicenseResultCallback iLicenseResultCallback)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iLicenseResultCallback);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public void activateKnoxLicenseForUMC(ContextInfo contextInfo, String str, String str2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public void activateLicense(
                    ContextInfo contextInfo,
                    String str,
                    String str2,
                    String str3,
                    ILicenseResultCallback iLicenseResultCallback)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeStrongInterface(iLicenseResultCallback);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public void activateLicenseForUMC(
                    ContextInfo contextInfo, String str, String str2, String str3)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public void deActivateKnoxLicense(
                    ContextInfo contextInfo,
                    String str,
                    String str2,
                    ILicenseResultCallback iLicenseResultCallback)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStrongInterface(iLicenseResultCallback);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean deleteAllApiCallData() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean deleteApiCallData(String str, String str2, Error error)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeTypedObject(error, 0);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean deleteApiCallDataByAdmin(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean deleteLicense(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean deleteLicenseByAdmin(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public List<ActivationInfo> getAllLicenseActivationsInfos() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ActivationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public LicenseInfo[] getAllLicenseInfo() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LicenseInfo[]) obtain2.createTypedArray(LicenseInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public Bundle getApiCallData(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public Bundle getApiCallDataByAdmin(ContextInfo contextInfo, String str)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return (Bundle) obtain2.readTypedObject(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public List<String> getELMPermissions(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public String getInstanceId(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IEnterpriseLicense.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public ActivationInfo getLicenseActivationInfo(ContextInfo contextInfo, String str)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ActivationInfo) obtain2.readTypedObject(ActivationInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public LicenseInfo getLicenseInfo(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LicenseInfo) obtain2.readTypedObject(LicenseInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public LicenseInfo getLicenseInfoByAdmin(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (LicenseInfo) obtain2.readTypedObject(LicenseInfo.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public RightsObject getRightsObject(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (RightsObject) obtain2.readTypedObject(RightsObject.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public RightsObject getRightsObjectByAdmin(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (RightsObject) obtain2.readTypedObject(RightsObject.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean isEulaBypassAllowed(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean isServiceAvailable(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public void log(ContextInfo contextInfo, String str, boolean z, boolean z2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public void notifyKlmObservers(String str, LicenseResult licenseResult)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(licenseResult, 0);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean processKnoxLicenseResponse(
                    String str,
                    String str2,
                    String str3,
                    String str4,
                    Error error,
                    int i,
                    String str5,
                    RightsObject rightsObject,
                    int i2)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeTypedObject(error, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str5);
                    obtain.writeTypedObject(rightsObject, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean processLicenseActivationResponse(
                    String str,
                    String str2,
                    String str3,
                    String str4,
                    String str5,
                    RightsObject rightsObject,
                    Error error,
                    String str6,
                    String str7,
                    int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    obtain.writeTypedObject(rightsObject, 0);
                    obtain.writeTypedObject(error, 0);
                    obtain.writeString(str6);
                    obtain.writeString(str7);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean processLicenseValidationResult(
                    String str,
                    RightsObject rightsObject,
                    Error error,
                    String str2,
                    String str3,
                    String str4,
                    String str5)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(rightsObject, 0);
                    obtain.writeTypedObject(error, 0);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeString(str5);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean resetLicense(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public boolean resetLicenseByAdmin(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.license.IEnterpriseLicense
            public void updateAdminPermissions() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IEnterpriseLicense.DESCRIPTOR);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IEnterpriseLicense.DESCRIPTOR);
        }

        public static IEnterpriseLicense asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface =
                    iBinder.queryLocalInterface(IEnterpriseLicense.DESCRIPTOR);
            return (queryLocalInterface == null
                            || !(queryLocalInterface instanceof IEnterpriseLicense))
                    ? new Proxy(iBinder)
                    : (IEnterpriseLicense) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IEnterpriseLicense.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IEnterpriseLicense.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    RightsObject rightsObject = getRightsObject(readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rightsObject, 1);
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    RightsObject rightsObjectByAdmin = getRightsObjectByAdmin(readString2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(rightsObjectByAdmin, 1);
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle apiCallData = getApiCallData(readString3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(apiCallData, 1);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    String readString5 = parcel.readString();
                    Error error = (Error) parcel.readTypedObject(Error.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean deleteApiCallData = deleteApiCallData(readString4, readString5, error);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteApiCallData);
                    return true;
                case 5:
                    String readString6 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteApiCallDataByAdmin = deleteApiCallDataByAdmin(readString6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteApiCallDataByAdmin);
                    return true;
                case 6:
                    ContextInfo contextInfo =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString7 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    Bundle apiCallDataByAdmin = getApiCallDataByAdmin(contextInfo, readString7);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(apiCallDataByAdmin, 1);
                    return true;
                case 7:
                    LicenseInfo[] allLicenseInfo = getAllLicenseInfo();
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(allLicenseInfo, 1);
                    return true;
                case 8:
                    String readString8 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    LicenseInfo licenseInfo = getLicenseInfo(readString8);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(licenseInfo, 1);
                    return true;
                case 9:
                    String readString9 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    LicenseInfo licenseInfoByAdmin = getLicenseInfoByAdmin(readString9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(licenseInfoByAdmin, 1);
                    return true;
                case 10:
                    ContextInfo contextInfo2 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    ILicenseResultCallback asInterface =
                            ILicenseResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    activateLicense(
                            contextInfo2, readString10, readString11, readString12, asInterface);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String readString13 = parcel.readString();
                    String readString14 = parcel.readString();
                    String readString15 = parcel.readString();
                    String readString16 = parcel.readString();
                    String readString17 = parcel.readString();
                    RightsObject rightsObject2 =
                            (RightsObject) parcel.readTypedObject(RightsObject.CREATOR);
                    Error error2 = (Error) parcel.readTypedObject(Error.CREATOR);
                    String readString18 = parcel.readString();
                    String readString19 = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean processLicenseActivationResponse =
                            processLicenseActivationResponse(
                                    readString13,
                                    readString14,
                                    readString15,
                                    readString16,
                                    readString17,
                                    rightsObject2,
                                    error2,
                                    readString18,
                                    readString19,
                                    readInt);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(processLicenseActivationResponse);
                    return true;
                case 12:
                    String readString20 = parcel.readString();
                    RightsObject rightsObject3 =
                            (RightsObject) parcel.readTypedObject(RightsObject.CREATOR);
                    Error error3 = (Error) parcel.readTypedObject(Error.CREATOR);
                    String readString21 = parcel.readString();
                    String readString22 = parcel.readString();
                    String readString23 = parcel.readString();
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean processLicenseValidationResult =
                            processLicenseValidationResult(
                                    readString20,
                                    rightsObject3,
                                    error3,
                                    readString21,
                                    readString22,
                                    readString23,
                                    readString24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(processLicenseValidationResult);
                    return true;
                case 13:
                    ContextInfo contextInfo3 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString25 = parcel.readString();
                    String readString26 = parcel.readString();
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    activateLicenseForUMC(contextInfo3, readString25, readString26, readString27);
                    parcel2.writeNoException();
                    return true;
                case 14:
                    ContextInfo contextInfo4 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString28 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    log(contextInfo4, readString28, readBoolean, readBoolean2);
                    parcel2.writeNoException();
                    return true;
                case 15:
                    String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean resetLicense = resetLicense(readString29);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetLicense);
                    return true;
                case 16:
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean resetLicenseByAdmin = resetLicenseByAdmin(readString30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(resetLicenseByAdmin);
                    return true;
                case 17:
                    String readString31 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteLicense = deleteLicense(readString31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteLicense);
                    return true;
                case 18:
                    String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean deleteLicenseByAdmin = deleteLicenseByAdmin(readString32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteLicenseByAdmin);
                    return true;
                case 19:
                    String readString33 = parcel.readString();
                    String readString34 = parcel.readString();
                    String readString35 = parcel.readString();
                    String readString36 = parcel.readString();
                    Error error4 = (Error) parcel.readTypedObject(Error.CREATOR);
                    int readInt2 = parcel.readInt();
                    String readString37 = parcel.readString();
                    RightsObject rightsObject4 =
                            (RightsObject) parcel.readTypedObject(RightsObject.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean processKnoxLicenseResponse =
                            processKnoxLicenseResponse(
                                    readString33,
                                    readString34,
                                    readString35,
                                    readString36,
                                    error4,
                                    readInt2,
                                    readString37,
                                    rightsObject4,
                                    readInt3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(processKnoxLicenseResponse);
                    return true;
                case 20:
                    ContextInfo contextInfo5 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString38 = parcel.readString();
                    String readString39 = parcel.readString();
                    ILicenseResultCallback asInterface2 =
                            ILicenseResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    activateKnoxLicense(contextInfo5, readString38, readString39, asInterface2);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    ContextInfo contextInfo6 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString40 = parcel.readString();
                    String readString41 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    activateKnoxLicenseForUMC(contextInfo6, readString40, readString41);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    ContextInfo contextInfo7 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString42 = parcel.readString();
                    String readString43 = parcel.readString();
                    ILicenseResultCallback asInterface3 =
                            ILicenseResultCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    deActivateKnoxLicense(contextInfo7, readString42, readString43, asInterface3);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    String readString44 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> eLMPermissions = getELMPermissions(readString44);
                    parcel2.writeNoException();
                    parcel2.writeStringList(eLMPermissions);
                    return true;
                case 24:
                    String readString45 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String instanceId = getInstanceId(readString45);
                    parcel2.writeNoException();
                    parcel2.writeString(instanceId);
                    return true;
                case 25:
                    boolean deleteAllApiCallData = deleteAllApiCallData();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(deleteAllApiCallData);
                    return true;
                case 26:
                    String readString46 = parcel.readString();
                    String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isServiceAvailable = isServiceAvailable(readString46, readString47);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isServiceAvailable);
                    return true;
                case 27:
                    String readString48 = parcel.readString();
                    LicenseResult licenseResult =
                            (LicenseResult) parcel.readTypedObject(LicenseResult.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyKlmObservers(readString48, licenseResult);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    ContextInfo contextInfo8 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    String readString49 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ActivationInfo licenseActivationInfo =
                            getLicenseActivationInfo(contextInfo8, readString49);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(licenseActivationInfo, 1);
                    return true;
                case 29:
                    List<ActivationInfo> allLicenseActivationsInfos =
                            getAllLicenseActivationsInfos();
                    parcel2.writeNoException();
                    parcel2.writeTypedList(allLicenseActivationsInfos, 1);
                    return true;
                case 30:
                    String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isEulaBypassAllowed = isEulaBypassAllowed(readString50);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEulaBypassAllowed);
                    return true;
                case 31:
                    updateAdminPermissions();
                    parcel2.writeNoException();
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
}
