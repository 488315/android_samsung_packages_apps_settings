package com.android.settings.network.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;

import com.android.internal.util.ArrayUtils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class TelephonyBasePreferenceController extends BasePreferenceController
        implements TelephonyAvailabilityCallback, TelephonyAvailabilityHandler {
    private AtomicInteger mAvailabilityStatus;
    private AtomicInteger mSetSessionCount;
    protected int mSubId;

    public TelephonyBasePreferenceController(Context context, String str) {
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

    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    public PersistableBundle getCarrierConfigForSubId(int i) {
        if (SubscriptionManager.isValidSubscriptionId(i)) {
            return ((CarrierConfigManager)
                            this.mContext.getSystemService(CarrierConfigManager.class))
                    .getConfigForSubId(i);
        }
        return null;
    }

    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    public Resources getResourcesForSubId() {
        return SubscriptionManager.getResourcesForSubId(this.mContext, this.mSubId);
    }

    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.network.telephony.TelephonyAvailabilityHandler
    public void setAvailabilityStatus(int i) {
        this.mAvailabilityStatus.set(i);
        this.mSetSessionCount.getAndIncrement();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.network.telephony.TelephonyAvailabilityHandler
    public void unsetAvailabilityStatus() {
        this.mSetSessionCount.getAndDecrement();
    }

    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
