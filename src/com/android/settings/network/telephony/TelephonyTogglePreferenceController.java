package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.PersistableBundle;
import android.telephony.SubscriptionManager;

import com.android.internal.util.ArrayUtils;
import com.android.settings.core.TogglePreferenceController;
import com.android.settings.network.CarrierConfigCache;

import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class TelephonyTogglePreferenceController extends TogglePreferenceController
        implements TelephonyAvailabilityCallback, TelephonyAvailabilityHandler {
    private AtomicInteger mAvailabilityStatus;
    private AtomicInteger mSetSessionCount;
    protected int mSubId;

    public TelephonyTogglePreferenceController(Context context, String str) {
        super(context, str);
        this.mAvailabilityStatus = new AtomicInteger(0);
        this.mSetSessionCount = new AtomicInteger(0);
        this.mSubId = -1;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        int availabilityStatus;
        if (this.mSetSessionCount.get() <= 0) {
            AtomicInteger atomicInteger = this.mAvailabilityStatus;
            Context context = this.mContext;
            int i = this.mSubId;
            if (i != -1) {
                availabilityStatus = getAvailabilityStatus(i);
            } else {
                int[] activeSubscriptionIdList =
                        MobileNetworkUtils.getActiveSubscriptionIdList(context);
                if (ArrayUtils.isEmpty(activeSubscriptionIdList)) {
                    availabilityStatus = getAvailabilityStatus(-1);
                } else {
                    int length = activeSubscriptionIdList.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            availabilityStatus = getAvailabilityStatus(activeSubscriptionIdList[0]);
                            break;
                        }
                        int availabilityStatus2 =
                                getAvailabilityStatus(activeSubscriptionIdList[i2]);
                        if (availabilityStatus2 == 0) {
                            availabilityStatus = availabilityStatus2;
                            break;
                        }
                        i2++;
                    }
                }
            }
            atomicInteger.set(availabilityStatus);
        }
        return this.mAvailabilityStatus.get();
    }

    public abstract /* synthetic */ int getAvailabilityStatus(int i);

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ List getBackupKeys() {
        return super.getBackupKeys();
    }

    public PersistableBundle getCarrierConfigForSubId(int i) {
        if (!SubscriptionManager.isValidSubscriptionId(i)) {
            return null;
        }
        CarrierConfigCache.getInstance(this.mContext).getClass();
        return CarrierConfigCache.getConfigForSubId(i);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return super.getLaunchIntent();
    }

    public Resources getResourcesForSubId() {
        return SubscriptionManager.getResourcesForSubId(this.mContext, this.mSubId);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return super.getStatusText();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController
    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {
        super.ignoreUserInteraction();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return super.isControllable();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return super.needUserInteraction(obj);
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return super.runDefaultAction();
    }

    @Override // com.android.settings.network.telephony.TelephonyAvailabilityHandler
    public void setAvailabilityStatus(int i) {
        this.mAvailabilityStatus.set(i);
        this.mSetSessionCount.getAndIncrement();
    }

    @Override // com.android.settings.network.telephony.TelephonyAvailabilityHandler
    public void unsetAvailabilityStatus() {
        this.mSetSessionCount.getAndDecrement();
    }

    @Override // com.android.settings.core.TogglePreferenceController,
              // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }
}
