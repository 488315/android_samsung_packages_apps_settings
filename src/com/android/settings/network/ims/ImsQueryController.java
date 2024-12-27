package com.android.settings.network.ims;

import android.telephony.SubscriptionManager;
import android.telephony.ims.ImsException;
import android.telephony.ims.ImsMmTelManager;
import android.telephony.ims.ProvisioningManager;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class ImsQueryController {
    public volatile int mCapability;
    public volatile int mTech;
    public volatile int mTransportType;

    public ImsQueryController(int i, int i2, int i3) {
        this.mCapability = i;
        this.mTech = i2;
        this.mTransportType = i3;
    }

    public boolean isEnabledByPlatform(int i)
            throws InterruptedException, ImsException, IllegalArgumentException {
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            return false;
        }
        ImsMmTelManager createForSubscriptionId = ImsMmTelManager.createForSubscriptionId(i);
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        BooleanConsumer booleanConsumer = new BooleanConsumer();
        createForSubscriptionId.isSupported(
                this.mCapability, this.mTransportType, newSingleThreadExecutor, booleanConsumer);
        return booleanConsumer.get();
    }

    public boolean isProvisionedOnDevice(int i) {
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            return false;
        }
        try {
            return ProvisioningManager.createForSubscriptionId(i)
                    .getProvisioningStatusForCapability(this.mCapability, this.mTech);
        } catch (IllegalArgumentException e) {
            Log.w("QueryPrivisioningStat", "fail to get Provisioning stat. subId=" + i, e);
            return false;
        }
    }

    public boolean isServiceStateReady(int i)
            throws InterruptedException, ImsException, IllegalArgumentException {
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            return false;
        }
        ImsMmTelManager createForSubscriptionId = ImsMmTelManager.createForSubscriptionId(i);
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        IntegerConsumer integerConsumer = new IntegerConsumer();
        createForSubscriptionId.getFeatureState(newSingleThreadExecutor, integerConsumer);
        return integerConsumer.get() == 2;
    }

    public boolean isTtyOnVolteEnabled(int i) {
        try {
            return ImsMmTelManager.createForSubscriptionId(i).isTtyOverVolteEnabled();
        } catch (IllegalArgumentException e) {
            Log.w("QueryTtyOnVolteStat", "fail to get VoLte Tty Stat. subId=" + i, e);
            return false;
        }
    }
}
