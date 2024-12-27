package com.android.settings.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccCardInfo;
import android.telephony.UiccPortInfo;
import android.telephony.UiccSlotInfo;
import android.telephony.UiccSlotMapping;
import android.util.Log;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.apppickerview.widget.AbsAdapter$$ExternalSyntheticOutline0;
import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settingslib.utils.ThreadUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class UiccSlotUtil {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    @VisibleForTesting
    class SimCardStateChangeReceiver extends BroadcastReceiver {
        public final CountDownLatch mLatch;

        public SimCardStateChangeReceiver(CountDownLatch countDownLatch) {
            this.mLatch = countDownLatch;
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Log.i("UiccSlotUtil", "Action: " + intent.getAction());
            if ("android.telephony.action.SIM_CARD_STATE_CHANGED".equals(intent.getAction())) {
                int intExtra = intent.getIntExtra("android.telephony.extra.SIM_STATE", 0);
                MainClearConfirm$$ExternalSyntheticOutline0.m(intExtra, "simState: ", "UiccSlotUtil");
                if (intExtra == 0 || intExtra == 1) {
                    return;
                }
                this.mLatch.countDown();
            }
        }
    }

    @VisibleForTesting
    public static int getExcludedLogicalSlotIndex(Collection<UiccSlotMapping> collection, final Collection<SubscriptionInfo> collection2, SubscriptionInfo subscriptionInfo, boolean z) {
        if (!z) {
            Log.i("UiccSlotUtil", "In the ss mode.");
            return 0;
        }
        if (subscriptionInfo != null) {
            Log.i("UiccSlotUtil", "The removedSubInfo is not null");
            return subscriptionInfo.getSimSlotIndex();
        }
        Log.i("UiccSlotUtil", "The removedSubInfo is null");
        return collection.stream().filter(new Predicate() { // from class: com.android.settings.network.UiccSlotUtil$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                UiccSlotMapping uiccSlotMapping = (UiccSlotMapping) obj;
                Iterator it = collection2.iterator();
                while (it.hasNext()) {
                    if (((SubscriptionInfo) it.next()).getSimSlotIndex() == uiccSlotMapping.getLogicalSlotIndex()) {
                        return false;
                    }
                }
                return true;
            }
        }).sorted(Comparator.comparingInt(new UiccSlotUtil$$ExternalSyntheticLambda2(0))).mapToInt(new UiccSlotUtil$$ExternalSyntheticLambda2(1)).findFirst().orElse(-1);
    }

    public static int getInactiveRemovableSlot(UiccSlotInfo[] uiccSlotInfoArr, int i) {
        UiccSlotInfo uiccSlotInfo;
        if (uiccSlotInfoArr == null) {
            throw new UiccSlotsException("UiccSlotInfo is null");
        }
        if (i == -1) {
            for (int i2 = 0; i2 < uiccSlotInfoArr.length; i2++) {
                UiccSlotInfo uiccSlotInfo2 = uiccSlotInfoArr[i2];
                if (uiccSlotInfo2 != null && uiccSlotInfo2.isRemovable() && !uiccSlotInfoArr[i2].getIsEuicc() && !((UiccPortInfo) uiccSlotInfoArr[i2].getPorts().stream().findFirst().get()).isActive() && uiccSlotInfoArr[i2].getCardStateInfo() != 3 && uiccSlotInfoArr[i2].getCardStateInfo() != 4) {
                    return i2;
                }
            }
        } else {
            if (i >= uiccSlotInfoArr.length || (uiccSlotInfo = uiccSlotInfoArr[i]) == null || !uiccSlotInfo.isRemovable()) {
                ListPopupWindow$$ExternalSyntheticOutline0.m1m(i, "The given slotId is not a removable slot: ", "UiccSlotUtil");
                return -1;
            }
            if (!((UiccPortInfo) uiccSlotInfoArr[i].getPorts().stream().findFirst().get()).isActive()) {
                return i;
            }
        }
        return -1;
    }

    public static boolean isMultipleEnabledProfilesSupported(TelephonyManager telephonyManager) {
        List<UiccCardInfo> uiccCardsInfo = telephonyManager.getUiccCardsInfo();
        if (uiccCardsInfo != null) {
            return uiccCardsInfo.stream().anyMatch(new UiccSlotUtil$$ExternalSyntheticLambda4(0));
        }
        Log.w("UiccSlotUtil", "UICC cards info list is empty.");
        return false;
    }

    public static boolean isRemovableSimEnabled(List list) {
        boolean anyMatch = list.stream().anyMatch(new UiccSlotUtil$$ExternalSyntheticLambda4(1));
        AbsAdapter$$ExternalSyntheticOutline0.m("isRemovableSimEnabled: ", "UiccSlotUtil", anyMatch);
        return anyMatch;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v7 */
    /* JADX WARN: Type inference failed for: r5v8 */
    @VisibleForTesting
    public static void performSwitchToSlot(TelephonyManager telephonyManager, Collection<UiccSlotMapping> collection, Context context) throws UiccSlotsException {
        long j = Settings.Global.getLong(context.getContentResolver(), "euicc_switch_slot_timeout_millis", 25000L);
        Log.d("UiccSlotUtil", "Set waitingTime as " + j);
        BroadcastReceiver broadcastReceiver = null;
        try {
            try {
                CountDownLatch countDownLatch = new CountDownLatch(1);
                ?? isMultipleEnabledProfilesSupported = isMultipleEnabledProfilesSupported(telephonyManager);
                try {
                    if (isMultipleEnabledProfilesSupported != 0) {
                        SimCardStateChangeReceiver simCardStateChangeReceiver = new SimCardStateChangeReceiver(countDownLatch);
                        context.registerReceiver(simCardStateChangeReceiver, new IntentFilter("android.telephony.action.SIM_CARD_STATE_CHANGED"), 4);
                        isMultipleEnabledProfilesSupported = simCardStateChangeReceiver;
                    } else {
                        CarrierConfigChangedReceiver carrierConfigChangedReceiver = new CarrierConfigChangedReceiver(countDownLatch);
                        context.registerReceiver(carrierConfigChangedReceiver, new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"), 2);
                        isMultipleEnabledProfilesSupported = carrierConfigChangedReceiver;
                    }
                    broadcastReceiver = isMultipleEnabledProfilesSupported;
                    telephonyManager.setSimSlotMapping(collection);
                    countDownLatch.await(j, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e = e;
                    broadcastReceiver = isMultipleEnabledProfilesSupported;
                    Thread.currentThread().interrupt();
                    Log.e("UiccSlotUtil", "Failed switching to physical slot.", e);
                    if (broadcastReceiver == null) {
                        return;
                    }
                    context.unregisterReceiver(broadcastReceiver);
                } catch (Throwable th) {
                    th = th;
                    broadcastReceiver = isMultipleEnabledProfilesSupported;
                    if (broadcastReceiver != null) {
                        context.unregisterReceiver(broadcastReceiver);
                    }
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (InterruptedException e2) {
            e = e2;
        }
        context.unregisterReceiver(broadcastReceiver);
    }

    @VisibleForTesting
    public static Collection<UiccSlotMapping> prepareUiccSlotMappings(Collection<UiccSlotMapping> collection, boolean z, int i, int i2, int i3) {
        if (i3 == -1) {
            Log.d("UiccSlotUtil", "There is no removedLogicalSlotId. Do nothing.");
            return collection;
        }
        Log.d("UiccSlotUtil", String.format("Create new SimSlotMapping. Remove the UiccSlotMapping of logicalSlot%d, and insert PhysicalSlotId%d-Port%d", Integer.valueOf(i3), Integer.valueOf(i), Integer.valueOf(i2)));
        ArrayList arrayList = new ArrayList();
        int i4 = 0;
        if (z) {
            arrayList.add(new UiccSlotMapping(i2, i, 0));
            i4 = 1;
        }
        for (UiccSlotMapping uiccSlotMapping : (Collection) collection.stream().sorted(Comparator.comparingInt(new UiccSlotUtil$$ExternalSyntheticLambda2(0))).collect(Collectors.toList())) {
            if (uiccSlotMapping.getLogicalSlotIndex() != i3) {
                if (z) {
                    uiccSlotMapping = new UiccSlotMapping(uiccSlotMapping.getPortIndex(), uiccSlotMapping.getPhysicalSlotIndex(), i4);
                    i4++;
                }
                arrayList.add(uiccSlotMapping);
            } else if (!z) {
                arrayList.add(new UiccSlotMapping(i2, i, uiccSlotMapping.getLogicalSlotIndex()));
            }
        }
        Log.d("UiccSlotUtil", "The new SimSlotMapping: " + arrayList);
        return arrayList;
    }

    public static synchronized void switchToEuiccSlot(Context context, final int i, final int i2, SubscriptionInfo subscriptionInfo) {
        synchronized (UiccSlotUtil.class) {
            if (ThreadUtils.isMainThread()) {
                throw new IllegalThreadStateException("Do not call switchToRemovableSlot on the main thread.");
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TelephonyManager.class);
            Collection simSlotMapping = telephonyManager.getSimSlotMapping();
            Log.d("UiccSlotUtil", "The SimSlotMapping: " + simSlotMapping);
            if (simSlotMapping.stream().anyMatch(new Predicate() { // from class: com.android.settings.network.UiccSlotUtil$$ExternalSyntheticLambda7
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    UiccSlotMapping uiccSlotMapping = (UiccSlotMapping) obj;
                    return uiccSlotMapping.getPhysicalSlotIndex() == i && uiccSlotMapping.getPortIndex() == i2;
                }
            })) {
                Log.d("UiccSlotUtil", "The slot is active, then the sim can enable directly.");
            } else {
                performSwitchToSlot(telephonyManager, prepareUiccSlotMappings(simSlotMapping, false, i, i2, getExcludedLogicalSlotIndex(simSlotMapping, SubscriptionUtil.getActiveSubscriptions(((SubscriptionManager) context.getSystemService(SubscriptionManager.class)).createForAllUserProfiles()), subscriptionInfo, telephonyManager.isMultiSimEnabled())), context);
            }
        }
    }

    public static synchronized void switchToRemovableSlot(int i, Context context, SubscriptionInfo subscriptionInfo) {
        synchronized (UiccSlotUtil.class) {
            if (ThreadUtils.isMainThread()) {
                throw new IllegalThreadStateException("Do not call switchToRemovableSlot on the main thread.");
            }
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TelephonyManager.class);
            int inactiveRemovableSlot = getInactiveRemovableSlot(telephonyManager.getUiccSlotsInfo(), i);
            Log.d("UiccSlotUtil", "The InactiveRemovableSlot: " + inactiveRemovableSlot);
            if (inactiveRemovableSlot == -1) {
                return;
            }
            Collection simSlotMapping = telephonyManager.getSimSlotMapping();
            Log.d("UiccSlotUtil", "The SimSlotMapping: " + simSlotMapping);
            performSwitchToSlot(telephonyManager, prepareUiccSlotMappings(simSlotMapping, true, inactiveRemovableSlot, 0, getExcludedLogicalSlotIndex(simSlotMapping, SubscriptionUtil.getActiveSubscriptions(((SubscriptionManager) context.getSystemService(SubscriptionManager.class)).createForAllUserProfiles()), subscriptionInfo, telephonyManager.isMultiSimEnabled())), context);
        }
    }
}
