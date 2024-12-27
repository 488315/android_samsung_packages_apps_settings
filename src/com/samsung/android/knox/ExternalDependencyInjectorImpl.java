package com.samsung.android.knox;

import android.app.admin.IDevicePolicyManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.storage.StorageVolume;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ExternalDependencyInjectorImpl implements ExternalDependencyInjector {
    @Override // com.samsung.android.knox.ExternalDependencyInjector
    public Bundle getApplicationRestrictionsMDM(
            IDevicePolicyManager iDevicePolicyManager,
            ComponentName componentName,
            String str,
            int i)
            throws RemoteException {
        return iDevicePolicyManager.getApplicationRestrictionsMDM(componentName, str, i);
    }

    @Override // com.samsung.android.knox.ExternalDependencyInjector
    public void setApplicationRestrictionsMDM(
            IDevicePolicyManager iDevicePolicyManager,
            ComponentName componentName,
            String str,
            Bundle bundle,
            int i)
            throws RemoteException {
        iDevicePolicyManager.setApplicationRestrictionsMDM(componentName, str, bundle, i);
    }

    @Override // com.samsung.android.knox.ExternalDependencyInjector
    public String storageVolumeGetSubSystem(StorageVolume storageVolume) {
        return storageVolume.getSubSystem();
    }
}
