package com.android.settings.sim.receivers;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.os.UserHandle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.UiccCardInfo;
import android.telephony.UiccPortInfo;
import android.telephony.UiccSlotInfo;
import android.telephony.euicc.EuiccManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.mediarouter.app.MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.deviceinfo.simstatus.EidStatus$$ExternalSyntheticLambda2;
import com.android.settings.fuelgauge.datasaver.DynamicDenylistManager$$ExternalSyntheticOutline0;
import com.android.settings.network.SatelliteRepository;
import com.android.settings.network.SimOnboardingActivity;
import com.android.settings.network.SimOnboardingService;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.network.UiccSlotUtil;
import com.android.settings.network.UiccSlotsException;
import com.android.settings.network.telephony.SubscriptionRepositoryKt;
import com.android.settings.sim.ChooseSimActivity;
import com.android.settings.sim.DsdsDialogActivity;
import com.android.settings.sim.SimNotificationService;
import com.android.settings.sim.SwitchToEsimConfirmDialogActivity;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.ListenableFuture;

import kotlin.jvm.internal.Intrinsics;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SimSlotChangeReceiver extends BroadcastReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;

    public static void runOnBackgroundThread(final Context context) {
        UiccSlotInfo uiccSlotInfo;
        if (!context.getResources().getBoolean(R.bool.config_handle_sim_slot_change)) {
            Log.i("SlotChangeReceiver", "The flag is off. Ignore slot changes.");
            return;
        }
        EuiccManager euiccManager = (EuiccManager) context.getSystemService(EuiccManager.class);
        if (euiccManager == null || !euiccManager.isEnabled()) {
            Log.i("SlotChangeReceiver", "Ignore slot changes because EuiccManager is disabled.");
            return;
        }
        boolean z = true;
        if (euiccManager.getOtaStatus() == 1) {
            Log.i("SlotChangeReceiver", "Ignore slot changes because eSIM OTA is in progress.");
            return;
        }
        TelephonyManager telephonyManager =
                (TelephonyManager) context.getSystemService(TelephonyManager.class);
        UiccSlotInfo[] uiccSlotsInfo = telephonyManager.getUiccSlotsInfo();
        if (uiccSlotsInfo == null) {
            Log.e("SlotChangeReceiver", "slotInfos is null. Unable to get slot infos.");
        } else {
            final int i = 0;
            while (true) {
                if (i < uiccSlotsInfo.length) {
                    uiccSlotInfo = uiccSlotsInfo[i];
                    if (uiccSlotInfo == null) {
                        break;
                    }
                    if (uiccSlotInfo.getCardStateInfo() == 3
                            || uiccSlotInfo.getCardStateInfo() == 4) {
                        break;
                    }
                    List<UiccCardInfo> uiccCardsInfo = telephonyManager.getUiccCardsInfo();
                    UiccCardInfo orElse =
                            uiccCardsInfo != null
                                    ? uiccCardsInfo.stream()
                                            .filter(
                                                    new Predicate() { // from class:
                                                                      // com.android.settings.sim.receivers.SimSlotChangeReceiver$$ExternalSyntheticLambda1
                                                        @Override // java.util.function.Predicate
                                                        public final boolean test(Object obj) {
                                                            int i2 = i;
                                                            int i3 =
                                                                    SimSlotChangeReceiver
                                                                            .$r8$clinit;
                                                            return ((UiccCardInfo) obj)
                                                                            .getPhysicalSlotIndex()
                                                                    == i2;
                                                        }
                                                    })
                                            .findFirst()
                                            .orElse(null)
                                    : null;
                    if (orElse != null) {
                        for (UiccPortInfo uiccPortInfo : orElse.getPorts()) {
                            if (!TextUtils.isEmpty(uiccSlotInfo.getCardId())
                                    || !TextUtils.isEmpty(uiccPortInfo.getIccId())) {
                                z = false;
                            }
                        }
                    }
                    i++;
                } else {
                    if (!z) {
                        Log.d("SlotChangeReceiver", "Checking satellite session status");
                        ExecutorService newSingleThreadExecutor =
                                Executors.newSingleThreadExecutor();
                        final ListenableFuture requestIsSessionStarted =
                                new SatelliteRepository(context)
                                        .requestIsSessionStarted(newSingleThreadExecutor);
                        requestIsSessionStarted.addListener(
                                new Runnable() { // from class:
                                    // com.android.settings.sim.receivers.SimSlotChangeReceiver$$ExternalSyntheticLambda0
                                    /* JADX WARN: Multi-variable type inference failed */
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        boolean z2;
                                        UiccSlotInfo uiccSlotInfo2;
                                        final int i2 = 0;
                                        final int i3 = 1;
                                        ListenableFuture listenableFuture = ListenableFuture.this;
                                        Context context2 = context;
                                        int i4 = SimSlotChangeReceiver.$r8$clinit;
                                        try {
                                            z2 = ((Boolean) listenableFuture.get()).booleanValue();
                                        } catch (InterruptedException | ExecutionException e) {
                                            Log.w(
                                                    "SlotChangeReceiver",
                                                    "Can't get satellite session status",
                                                    e);
                                            z2 = false;
                                        }
                                        if (z2) {
                                            Log.i(
                                                    "SlotChangeReceiver",
                                                    "Device is in a satellite session. Unable to"
                                                        + " handle SIM slot changes");
                                            return;
                                        }
                                        Log.i(
                                                "SlotChangeReceiver",
                                                "Not in a satellite session. Handle slot changes");
                                        if (SimSlotChangeHandler.sSlotChangeHandler == null) {
                                            synchronized (SimSlotChangeHandler.class) {
                                                try {
                                                    if (SimSlotChangeHandler.sSlotChangeHandler
                                                            == null) {
                                                        SimSlotChangeHandler.sSlotChangeHandler =
                                                                new SimSlotChangeHandler();
                                                    }
                                                } finally {
                                                }
                                            }
                                        }
                                        SimSlotChangeHandler simSlotChangeHandler =
                                                SimSlotChangeHandler.sSlotChangeHandler;
                                        Context applicationContext =
                                                context2.getApplicationContext();
                                        simSlotChangeHandler.getClass();
                                        simSlotChangeHandler.mSubMgr =
                                                (SubscriptionManager)
                                                        applicationContext.getSystemService(
                                                                "telephony_subscription_service");
                                        simSlotChangeHandler.mTelMgr =
                                                (TelephonyManager)
                                                        applicationContext.getSystemService(
                                                                TelephonyManager.class);
                                        simSlotChangeHandler.mContext = applicationContext;
                                        if (Looper.myLooper() == Looper.getMainLooper()) {
                                            throw new IllegalStateException(
                                                    "Cannot be called from main thread.");
                                        }
                                        UiccSlotInfo[] uiccSlotsInfo2 =
                                                simSlotChangeHandler.mTelMgr.getUiccSlotsInfo();
                                        if (uiccSlotsInfo2 != null) {
                                            int length = uiccSlotsInfo2.length;
                                            for (int i5 = 0; i5 < length; i5++) {
                                                uiccSlotInfo2 = uiccSlotsInfo2[i5];
                                                if (uiccSlotInfo2 != null
                                                        && uiccSlotInfo2.isRemovable()) {
                                                    break;
                                                }
                                            }
                                        } else {
                                            Log.e(
                                                    "SimSlotChangeHandler",
                                                    "slotInfos is null. Unable to get slot infos.");
                                        }
                                        uiccSlotInfo2 = null;
                                        if (uiccSlotInfo2 == null) {
                                            Log.e(
                                                    "SimSlotChangeHandler",
                                                    "Unable to find the removable slot. Do"
                                                        + " nothing.");
                                            return;
                                        }
                                        int i6 =
                                                simSlotChangeHandler
                                                        .mContext
                                                        .getSharedPreferences("euicc_prefs", 0)
                                                        .getInt("removable_slot_state", 1);
                                        int cardStateInfo = uiccSlotInfo2.getCardStateInfo();
                                        DynamicDenylistManager$$ExternalSyntheticOutline0.m(
                                                "lastRemovableSlotState: ",
                                                ",currentRemovableSlotState: ",
                                                i6,
                                                cardStateInfo,
                                                "SimSlotChangeHandler");
                                        boolean z3 = i6 == 1 && cardStateInfo == 2;
                                        boolean z4 = i6 == 2 && cardStateInfo == 1;
                                        simSlotChangeHandler
                                                .mContext
                                                .getSharedPreferences("euicc_prefs", 0)
                                                .edit()
                                                .putInt("removable_slot_state", cardStateInfo)
                                                .apply();
                                        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                                                cardStateInfo,
                                                "setRemovableSimSlotState: ",
                                                "SimSlotChangeHandler");
                                        if (simSlotChangeHandler.mTelMgr.getActiveModemCount()
                                                > 1) {
                                            if (!z3) {
                                                Log.d(
                                                        "SimSlotChangeHandler",
                                                        "Removable Sim is not inserted in DSDS"
                                                            + " mode. Do nothing.");
                                                return;
                                            }
                                            ImmutableList copyOf =
                                                    ImmutableList.copyOf(
                                                            (Collection)
                                                                    SubscriptionUtil
                                                                            .getAvailableSubscriptions(
                                                                                    simSlotChangeHandler
                                                                                            .mContext)
                                                                            .stream()
                                                                            .filter(
                                                                                    new Predicate() { // from class: com.android.settings.sim.receivers.SimSlotChangeHandler$$ExternalSyntheticLambda0
                                                                                        @Override // java.util.function.Predicate
                                                                                        public final
                                                                                        boolean
                                                                                                test(
                                                                                                        Object
                                                                                                                obj) {
                                                                                            SubscriptionInfo
                                                                                                    subscriptionInfo =
                                                                                                            (SubscriptionInfo)
                                                                                                                    obj;
                                                                                            switch (i2) {
                                                                                                case 0:
                                                                                                    return !subscriptionInfo
                                                                                                            .isEmbedded();
                                                                                                default:
                                                                                                    return subscriptionInfo
                                                                                                            .isEmbedded();
                                                                                            }
                                                                                        }
                                                                                    })
                                                                            .collect(
                                                                                    Collectors
                                                                                            .toList()));
                                            if (copyOf.isEmpty()) {
                                                Log.e(
                                                        "SimSlotChangeHandler",
                                                        "Unable to find the removable"
                                                            + " subscriptionInfo. Do nothing.");
                                            } else {
                                                Log.d(
                                                        "SimSlotChangeHandler",
                                                        "ForNewUi and"
                                                            + " getAvailableRemovableSubscription:"
                                                                + copyOf);
                                                int subscriptionId =
                                                        ((SubscriptionInfo) copyOf.get(0))
                                                                .getSubscriptionId();
                                                if (!SimSlotChangeHandler.isSuwFinished(
                                                        simSlotChangeHandler.mContext)) {
                                                    Log.d(
                                                            "SimSlotChangeHandler",
                                                            "Still in SUW. Do nothing");
                                                } else if (SubscriptionManager
                                                        .isUsableSubscriptionId(subscriptionId)) {
                                                    MediaRouteControllerDialog$VolumeGroupAdapter$$ExternalSyntheticOutline0
                                                            .m(
                                                                    subscriptionId,
                                                                    "Start"
                                                                        + " ToggleSubscriptionDialogActivity"
                                                                        + " with ",
                                                                    " under DSDS+Mep.",
                                                                    "SimSlotChangeHandler");
                                                    Context context3 =
                                                            simSlotChangeHandler.mContext;
                                                    if (SubscriptionManager.isUsableSubscriptionId(
                                                            subscriptionId)) {
                                                        SimOnboardingService simOnboardingService =
                                                                SimOnboardingActivity
                                                                        .onboardingService;
                                                        Intrinsics.checkNotNullParameter(
                                                                context3, "context");
                                                        Intent intent =
                                                                new Intent(
                                                                        context3,
                                                                        (Class<?>)
                                                                                SimOnboardingActivity
                                                                                        .class);
                                                        intent.putExtra("sub_id", subscriptionId);
                                                        intent.setFlags(268435456);
                                                        context3.startActivity(intent);
                                                    } else {
                                                        Log.i(
                                                                "SubscriptionUtil",
                                                                "Unable to toggle subscription due"
                                                                    + " to invalid subscription"
                                                                    + " ID.");
                                                    }
                                                } else {
                                                    Log.i(
                                                            "SimSlotChangeHandler",
                                                            "Unable to enable subscription due to"
                                                                + " invalid subscription ID.");
                                                }
                                            }
                                            Log.d(
                                                    "SimSlotChangeHandler",
                                                    "the device is already in DSDS mode and have"
                                                        + " the DDS. Do nothing.");
                                            return;
                                        }
                                        if (z3) {
                                            Log.i("SimSlotChangeHandler", "Handle SIM inserted.");
                                            if (!SimSlotChangeHandler.isSuwFinished(
                                                    simSlotChangeHandler.mContext)) {
                                                Log.i(
                                                        "SimSlotChangeHandler",
                                                        "Still in SUW. Handle SIM insertion after"
                                                            + " SUW is finished");
                                                simSlotChangeHandler
                                                        .mContext
                                                        .getSharedPreferences("euicc_prefs", 0)
                                                        .edit()
                                                        .putInt("suw_psim_action", 1)
                                                        .apply();
                                                return;
                                            }
                                            if (((UiccPortInfo)
                                                            uiccSlotInfo2.getPorts().stream()
                                                                    .findFirst()
                                                                    .get())
                                                    .isActive()) {
                                                Log.i(
                                                        "SimSlotChangeHandler",
                                                        "The removable slot is already active. Do"
                                                            + " nothing.");
                                                return;
                                            }
                                            if (!SubscriptionUtil.getActiveSubscriptions(
                                                            simSlotChangeHandler.mSubMgr)
                                                    .stream()
                                                    .anyMatch(
                                                            new EidStatus$$ExternalSyntheticLambda2(
                                                                    1))) {
                                                Log.i(
                                                        "SimSlotChangeHandler",
                                                        "No enabled eSIM profile. Ready to switch"
                                                            + " to removable slot and show"
                                                            + " notification.");
                                                try {
                                                    Context applicationContext2 =
                                                            simSlotChangeHandler.mContext
                                                                    .getApplicationContext();
                                                    synchronized (UiccSlotUtil.class) {
                                                        UiccSlotUtil.switchToRemovableSlot(
                                                                -1, applicationContext2, null);
                                                    }
                                                    SimNotificationService.scheduleSimNotification(
                                                            simSlotChangeHandler.mContext, 2);
                                                    return;
                                                } catch (UiccSlotsException unused) {
                                                    Log.e(
                                                            "SimSlotChangeHandler",
                                                            "Failed to switch to removable slot.");
                                                    return;
                                                }
                                            }
                                            if (simSlotChangeHandler.mTelMgr.isMultiSimSupported()
                                                    == 0) {
                                                Log.i(
                                                        "SimSlotChangeHandler",
                                                        "Enabled profile exists. DSDS condition"
                                                            + " satisfied.");
                                                Intent intent2 =
                                                        new Intent(
                                                                simSlotChangeHandler.mContext,
                                                                (Class<?>)
                                                                        DsdsDialogActivity.class);
                                                intent2.setFlags(268435456);
                                                simSlotChangeHandler.mContext.startActivityAsUser(
                                                        intent2, UserHandle.SYSTEM);
                                                return;
                                            }
                                            Log.i(
                                                    "SimSlotChangeHandler",
                                                    "Enabled profile exists. DSDS condition not"
                                                        + " satisfied.");
                                            Context context4 = simSlotChangeHandler.mContext;
                                            int i7 = ChooseSimActivity.$r8$clinit;
                                            Intent intent3 =
                                                    new Intent(
                                                            context4,
                                                            (Class<?>) ChooseSimActivity.class);
                                            intent3.setFlags(268435456);
                                            intent3.putExtra("has_psim", true);
                                            simSlotChangeHandler.mContext.startActivityAsUser(
                                                    intent3, UserHandle.SYSTEM);
                                            return;
                                        }
                                        if (!z4) {
                                            Log.i(
                                                    "SimSlotChangeHandler",
                                                    "Do nothing on slot status changes.");
                                            return;
                                        }
                                        Log.i("SimSlotChangeHandler", "Handle SIM removed.");
                                        if (!SimSlotChangeHandler.isSuwFinished(
                                                simSlotChangeHandler.mContext)) {
                                            Log.i(
                                                    "SimSlotChangeHandler",
                                                    "Still in SUW. Handle SIM removal after SUW is"
                                                        + " finished");
                                            simSlotChangeHandler
                                                    .mContext
                                                    .getSharedPreferences("euicc_prefs", 0)
                                                    .edit()
                                                    .putInt("suw_psim_action", 2)
                                                    .apply();
                                            return;
                                        }
                                        Context context5 = simSlotChangeHandler.mContext;
                                        Pattern pattern =
                                                SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
                                        List selectableSubscriptionInfoList =
                                                SubscriptionRepositoryKt
                                                        .getSelectableSubscriptionInfoList(
                                                                context5);
                                        ImmutableList of =
                                                selectableSubscriptionInfoList == null
                                                        ? ImmutableList.of()
                                                        : ImmutableList.copyOf(
                                                                (Collection)
                                                                        selectableSubscriptionInfoList
                                                                                .stream()
                                                                                .filter(
                                                                                        new Predicate() { // from class: com.android.settings.sim.receivers.SimSlotChangeHandler$$ExternalSyntheticLambda0
                                                                                            @Override // java.util.function.Predicate
                                                                                            public
                                                                                            final
                                                                                            boolean
                                                                                                    test(
                                                                                                            Object
                                                                                                                    obj) {
                                                                                                SubscriptionInfo
                                                                                                        subscriptionInfo =
                                                                                                                (SubscriptionInfo)
                                                                                                                        obj;
                                                                                                switch (i3) {
                                                                                                    case 0:
                                                                                                        return !subscriptionInfo
                                                                                                                .isEmbedded();
                                                                                                    default:
                                                                                                        return subscriptionInfo
                                                                                                                .isEmbedded();
                                                                                                }
                                                                                            }
                                                                                        })
                                                                                .collect(
                                                                                        Collectors
                                                                                                .toList()));
                                        if (of.size() == 0
                                                || !((UiccPortInfo)
                                                                uiccSlotInfo2.getPorts().stream()
                                                                        .findFirst()
                                                                        .get())
                                                        .isActive()) {
                                            Log.i(
                                                    "SimSlotChangeHandler",
                                                    "eSIM slot is active or no subscriptions exist."
                                                        + " Do nothing. The removableSlotInfo: "
                                                            + uiccSlotInfo2
                                                            + ", groupedEmbeddedSubscriptions: "
                                                            + of);
                                            return;
                                        }
                                        if (of.size() == 1) {
                                            Log.i(
                                                    "SimSlotChangeHandler",
                                                    "Only 1 eSIM profile found. Ask user's consent"
                                                        + " to switch.");
                                            SubscriptionInfo subscriptionInfo =
                                                    (SubscriptionInfo) of.get(0);
                                            Intent intent4 =
                                                    new Intent(
                                                            simSlotChangeHandler.mContext,
                                                            (Class<?>)
                                                                    SwitchToEsimConfirmDialogActivity
                                                                            .class);
                                            intent4.setFlags(268435456);
                                            intent4.putExtra("sub_to_enable", subscriptionInfo);
                                            simSlotChangeHandler.mContext.startActivityAsUser(
                                                    intent4, UserHandle.SYSTEM);
                                            return;
                                        }
                                        Log.i(
                                                "SimSlotChangeHandler",
                                                "Multiple eSIM profiles found. Ask user which"
                                                    + " subscription to use.");
                                        Context context6 = simSlotChangeHandler.mContext;
                                        int i8 = ChooseSimActivity.$r8$clinit;
                                        Intent intent5 =
                                                new Intent(
                                                        context6,
                                                        (Class<?>) ChooseSimActivity.class);
                                        intent5.setFlags(268435456);
                                        intent5.putExtra("has_psim", false);
                                        simSlotChangeHandler.mContext.startActivityAsUser(
                                                intent5, UserHandle.SYSTEM);
                                    }
                                },
                                newSingleThreadExecutor);
                        return;
                    }
                    Log.i(
                            "SlotChangeReceiver",
                            "All UICC card strings are empty. Drop this event.");
                }
            }
            Log.i(
                    "SlotChangeReceiver",
                    "The SIM state is in an error. Drop the event. SIM info: " + uiccSlotInfo);
        }
        Log.i("SlotChangeReceiver", "Ignore slot changes because SIM states are not valid.");
    }

    @Override // android.content.BroadcastReceiver
    public final void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!"android.telephony.action.SIM_SLOT_STATUS_CHANGED".equals(action)) {
            Log.e("SlotChangeReceiver", "Ignore slot changes due to unexpected action: " + action);
            return;
        }
        Log.i("SlotChangeReceiver", "start scheduleSimSlotChange");
        int i = SimSlotChangeService.$r8$clinit;
        Intrinsics.checkNotNullParameter(context, "context");
        ComponentName componentName =
                new ComponentName(context, (Class<?>) SimSlotChangeService.class);
        Object systemService = context.getSystemService((Class<Object>) JobScheduler.class);
        Intrinsics.checkNotNull(systemService);
        JobScheduler jobScheduler = (JobScheduler) systemService;
        if (jobScheduler.getPendingJob(R.integer.sim_slot_changed) != null) {
            Log.i("SimSlotChangeService", "There is already Pending Job");
        } else {
            Log.i("SimSlotChangeService", "There is not Pending Job");
            jobScheduler.schedule(
                    new JobInfo.Builder(R.integer.sim_slot_changed, componentName).build());
        }
    }
}
