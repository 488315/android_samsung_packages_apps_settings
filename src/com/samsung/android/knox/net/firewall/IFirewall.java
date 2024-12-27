package com.samsung.android.knox.net.firewall;

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
public interface IFirewall extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.knox.net.firewall.IFirewall";

    FirewallResponse[] addDomainFilterRules(ContextInfo contextInfo, int i) throws RemoteException;

    FirewallResponse[] addRules(ContextInfo contextInfo, FirewallRule[] firewallRuleArr)
            throws RemoteException;

    FirewallResponse[] clearAllDomainFilterRules(ContextInfo contextInfo) throws RemoteException;

    FirewallResponse[] clearRules(ContextInfo contextInfo, int i) throws RemoteException;

    FirewallResponse enableDomainFilterOnIptables(ContextInfo contextInfo, boolean z)
            throws RemoteException;

    FirewallResponse enableDomainFilterReport(ContextInfo contextInfo, boolean z)
            throws RemoteException;

    FirewallResponse enableFirewall(ContextInfo contextInfo, boolean z) throws RemoteException;

    List<DomainFilterReport> getDomainFilterReport(ContextInfo contextInfo, List<String> list)
            throws RemoteException;

    List<DomainFilterRule> getDomainFilterRules(ContextInfo contextInfo, List<String> list, int i)
            throws RemoteException;

    FirewallRule[] getRules(ContextInfo contextInfo, int i, String str) throws RemoteException;

    boolean isDomainFilterOnIptablesEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isDomainFilterReportEnabled(ContextInfo contextInfo) throws RemoteException;

    boolean isFirewallEnabled(ContextInfo contextInfo) throws RemoteException;

    String[] listIptablesRules(ContextInfo contextInfo) throws RemoteException;

    void populateDomainFilterBrokenRules(
            ContextInfo contextInfo, List<DomainFilterRule> list, int i) throws RemoteException;

    FirewallResponse[] removeDomainFilterRules(ContextInfo contextInfo, int i)
            throws RemoteException;

    FirewallResponse[] removeRules(ContextInfo contextInfo, FirewallRule[] firewallRuleArr)
            throws RemoteException;

    boolean shouldBlockDownload(String str, String str2, int i) throws RemoteException;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract static class Stub extends Binder implements IFirewall {
        public static final int TRANSACTION_addDomainFilterRules = 10;
        public static final int TRANSACTION_addRules = 1;
        public static final int TRANSACTION_clearAllDomainFilterRules = 9;
        public static final int TRANSACTION_clearRules = 4;
        public static final int TRANSACTION_enableDomainFilterOnIptables = 16;
        public static final int TRANSACTION_enableDomainFilterReport = 13;
        public static final int TRANSACTION_enableFirewall = 5;
        public static final int TRANSACTION_getDomainFilterReport = 15;
        public static final int TRANSACTION_getDomainFilterRules = 12;
        public static final int TRANSACTION_getRules = 3;
        public static final int TRANSACTION_isDomainFilterOnIptablesEnabled = 17;
        public static final int TRANSACTION_isDomainFilterReportEnabled = 14;
        public static final int TRANSACTION_isFirewallEnabled = 6;
        public static final int TRANSACTION_listIptablesRules = 7;
        public static final int TRANSACTION_populateDomainFilterBrokenRules = 8;
        public static final int TRANSACTION_removeDomainFilterRules = 11;
        public static final int TRANSACTION_removeRules = 2;
        public static final int TRANSACTION_shouldBlockDownload = 18;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class Proxy implements IFirewall {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse[] addDomainFilterRules(ContextInfo contextInfo, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    return (FirewallResponse[]) obtain2.createTypedArray(FirewallResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse[] addRules(
                    ContextInfo contextInfo, FirewallRule[] firewallRuleArr)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedArray(firewallRuleArr, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return (FirewallResponse[]) obtain2.createTypedArray(FirewallResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse[] clearAllDomainFilterRules(ContextInfo contextInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return (FirewallResponse[]) obtain2.createTypedArray(FirewallResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse[] clearRules(ContextInfo contextInfo, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return (FirewallResponse[]) obtain2.createTypedArray(FirewallResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse enableDomainFilterOnIptables(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return (FirewallResponse) obtain2.readTypedObject(FirewallResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse enableDomainFilterReport(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return (FirewallResponse) obtain2.readTypedObject(FirewallResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse enableFirewall(ContextInfo contextInfo, boolean z)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    return (FirewallResponse) obtain2.readTypedObject(FirewallResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public List<DomainFilterReport> getDomainFilterReport(
                    ContextInfo contextInfo, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(DomainFilterReport.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public List<DomainFilterRule> getDomainFilterRules(
                    ContextInfo contextInfo, List<String> list, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeStringList(list);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(DomainFilterRule.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IFirewall.DESCRIPTOR;
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallRule[] getRules(ContextInfo contextInfo, int i, String str)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return (FirewallRule[]) obtain2.createTypedArray(FirewallRule.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public boolean isDomainFilterOnIptablesEnabled(ContextInfo contextInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public boolean isDomainFilterReportEnabled(ContextInfo contextInfo)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public boolean isFirewallEnabled(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public String[] listIptablesRules(ContextInfo contextInfo) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public void populateDomainFilterBrokenRules(
                    ContextInfo contextInfo, List<DomainFilterRule> list, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedList(list, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse[] removeDomainFilterRules(ContextInfo contextInfo, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return (FirewallResponse[]) obtain2.createTypedArray(FirewallResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public FirewallResponse[] removeRules(
                    ContextInfo contextInfo, FirewallRule[] firewallRuleArr)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeTypedObject(contextInfo, 0);
                    obtain.writeTypedArray(firewallRuleArr, 0);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    return (FirewallResponse[]) obtain2.createTypedArray(FirewallResponse.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.knox.net.firewall.IFirewall
            public boolean shouldBlockDownload(String str, String str2, int i)
                    throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(IFirewall.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IFirewall.DESCRIPTOR);
        }

        public static IFirewall asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IFirewall.DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IFirewall))
                    ? new Proxy(iBinder)
                    : (IFirewall) queryLocalInterface;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2)
                throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IFirewall.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFirewall.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    ContextInfo contextInfo =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    FirewallRule[] firewallRuleArr =
                            (FirewallRule[]) parcel.createTypedArray(FirewallRule.CREATOR);
                    parcel.enforceNoDataAvail();
                    FirewallResponse[] addRules = addRules(contextInfo, firewallRuleArr);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(addRules, 1);
                    return true;
                case 2:
                    ContextInfo contextInfo2 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    FirewallRule[] firewallRuleArr2 =
                            (FirewallRule[]) parcel.createTypedArray(FirewallRule.CREATOR);
                    parcel.enforceNoDataAvail();
                    FirewallResponse[] removeRules = removeRules(contextInfo2, firewallRuleArr2);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(removeRules, 1);
                    return true;
                case 3:
                    ContextInfo contextInfo3 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt = parcel.readInt();
                    String readString = parcel.readString();
                    parcel.enforceNoDataAvail();
                    FirewallRule[] rules = getRules(contextInfo3, readInt, readString);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(rules, 1);
                    return true;
                case 4:
                    ContextInfo contextInfo4 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt2 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    FirewallResponse[] clearRules = clearRules(contextInfo4, readInt2);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(clearRules, 1);
                    return true;
                case 5:
                    ContextInfo contextInfo5 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    FirewallResponse enableFirewall = enableFirewall(contextInfo5, readBoolean);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enableFirewall, 1);
                    return true;
                case 6:
                    ContextInfo contextInfo6 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isFirewallEnabled = isFirewallEnabled(contextInfo6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isFirewallEnabled);
                    return true;
                case 7:
                    ContextInfo contextInfo7 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    String[] listIptablesRules = listIptablesRules(contextInfo7);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(listIptablesRules);
                    return true;
                case 8:
                    ContextInfo contextInfo8 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList createTypedArrayList =
                            parcel.createTypedArrayList(DomainFilterRule.CREATOR);
                    int readInt3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    populateDomainFilterBrokenRules(contextInfo8, createTypedArrayList, readInt3);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    ContextInfo contextInfo9 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    FirewallResponse[] clearAllDomainFilterRules =
                            clearAllDomainFilterRules(contextInfo9);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(clearAllDomainFilterRules, 1);
                    return true;
                case 10:
                    ContextInfo contextInfo10 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt4 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    FirewallResponse[] addDomainFilterRules =
                            addDomainFilterRules(contextInfo10, readInt4);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(addDomainFilterRules, 1);
                    return true;
                case 11:
                    ContextInfo contextInfo11 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    int readInt5 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    FirewallResponse[] removeDomainFilterRules =
                            removeDomainFilterRules(contextInfo11, readInt5);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(removeDomainFilterRules, 1);
                    return true;
                case 12:
                    ContextInfo contextInfo12 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<DomainFilterRule> domainFilterRules =
                            getDomainFilterRules(contextInfo12, createStringArrayList, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(domainFilterRules, 1);
                    return true;
                case 13:
                    ContextInfo contextInfo13 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean2 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    FirewallResponse enableDomainFilterReport =
                            enableDomainFilterReport(contextInfo13, readBoolean2);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enableDomainFilterReport, 1);
                    return true;
                case 14:
                    ContextInfo contextInfo14 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isDomainFilterReportEnabled =
                            isDomainFilterReportEnabled(contextInfo14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDomainFilterReportEnabled);
                    return true;
                case 15:
                    ContextInfo contextInfo15 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    List<DomainFilterReport> domainFilterReport =
                            getDomainFilterReport(contextInfo15, createStringArrayList2);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(domainFilterReport, 1);
                    return true;
                case 16:
                    ContextInfo contextInfo16 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    boolean readBoolean3 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    FirewallResponse enableDomainFilterOnIptables =
                            enableDomainFilterOnIptables(contextInfo16, readBoolean3);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(enableDomainFilterOnIptables, 1);
                    return true;
                case 17:
                    ContextInfo contextInfo17 =
                            (ContextInfo) parcel.readTypedObject(ContextInfo.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isDomainFilterOnIptablesEnabled =
                            isDomainFilterOnIptablesEnabled(contextInfo17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isDomainFilterOnIptablesEnabled);
                    return true;
                case 18:
                    String readString2 = parcel.readString();
                    String readString3 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean shouldBlockDownload =
                            shouldBlockDownload(readString2, readString3, readInt7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldBlockDownload);
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
    public static class Default implements IFirewall {
        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse[] addDomainFilterRules(ContextInfo contextInfo, int i)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse[] addRules(ContextInfo contextInfo, FirewallRule[] firewallRuleArr)
                throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse[] clearAllDomainFilterRules(ContextInfo contextInfo)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse[] clearRules(ContextInfo contextInfo, int i)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse enableDomainFilterOnIptables(ContextInfo contextInfo, boolean z)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse enableDomainFilterReport(ContextInfo contextInfo, boolean z)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse enableFirewall(ContextInfo contextInfo, boolean z)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public List<DomainFilterReport> getDomainFilterReport(
                ContextInfo contextInfo, List<String> list) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public List<DomainFilterRule> getDomainFilterRules(
                ContextInfo contextInfo, List<String> list, int i) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallRule[] getRules(ContextInfo contextInfo, int i, String str)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public boolean isDomainFilterOnIptablesEnabled(ContextInfo contextInfo)
                throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public boolean isDomainFilterReportEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public boolean isFirewallEnabled(ContextInfo contextInfo) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public String[] listIptablesRules(ContextInfo contextInfo) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse[] removeDomainFilterRules(ContextInfo contextInfo, int i)
                throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public FirewallResponse[] removeRules(
                ContextInfo contextInfo, FirewallRule[] firewallRuleArr) throws RemoteException {
            return null;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public boolean shouldBlockDownload(String str, String str2, int i) throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.knox.net.firewall.IFirewall
        public void populateDomainFilterBrokenRules(
                ContextInfo contextInfo, List<DomainFilterRule> list, int i)
                throws RemoteException {}
    }
}