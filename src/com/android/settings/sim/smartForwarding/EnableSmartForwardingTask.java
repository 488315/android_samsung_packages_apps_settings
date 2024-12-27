package com.android.settings.sim.smartForwarding;

import android.content.Context;
import android.telephony.CallForwardingInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

import com.google.common.util.concurrent.SettableFuture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EnableSmartForwardingTask implements Callable {
    public final SettableFuture client;
    public final String[] mCallForwardingNumber;
    public final FeatureResult mResult;
    public final SubscriptionManager sm;
    public final TelephonyManager tm;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Command {
        boolean process();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class FeatureResult {
        public FailedReason reason;
        public boolean result;
        public SlotUTData[] slotUTData;

        /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
        /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        final class FailedReason {
            public static final /* synthetic */ FailedReason[] $VALUES;
            public static final FailedReason SIM_NOT_ACTIVE;

            /* JADX INFO: Fake field, exist only in values array */
            FailedReason EF0;

            static {
                FailedReason failedReason = new FailedReason("NETWORK_ERROR", 0);
                FailedReason failedReason2 = new FailedReason("SIM_NOT_ACTIVE", 1);
                SIM_NOT_ACTIVE = failedReason2;
                $VALUES = new FailedReason[] {failedReason, failedReason2};
            }

            public static FailedReason valueOf(String str) {
                return (FailedReason) Enum.valueOf(FailedReason.class, str);
            }

            public static FailedReason[] values() {
                return (FailedReason[]) $VALUES.clone();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class QueryCallForwardingCommand extends QueryCommand {
        public CallForwardingInfo result;
        public SettableFuture resultFuture;

        @Override // com.android.settings.sim.smartForwarding.EnableSmartForwardingTask.Command
        public final boolean process() {
            this.tm
                    .createForSubscriptionId(this.subId)
                    .getCallForwarding(
                            3,
                            this.executor,
                            new TelephonyManager
                                    .CallForwardingInfoCallback() { // from class:
                                                                    // com.android.settings.sim.smartForwarding.EnableSmartForwardingTask.QueryCallForwardingCommand.1
                                public final void onCallForwardingInfoAvailable(
                                        CallForwardingInfo callForwardingInfo) {
                                    Log.d(
                                            "SmartForwarding",
                                            "Call Forwarding result: " + callForwardingInfo);
                                    QueryCallForwardingCommand queryCallForwardingCommand =
                                            QueryCallForwardingCommand.this;
                                    queryCallForwardingCommand.result = callForwardingInfo;
                                    queryCallForwardingCommand.resultFuture.set(Boolean.TRUE);
                                }

                                public final void onError(int i) {
                                    Log.d("SmartForwarding", "Query Call Forwarding failed.");
                                    QueryCallForwardingCommand.this.resultFuture.set(Boolean.FALSE);
                                }
                            });
            return ((Boolean) this.resultFuture.get()).booleanValue();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class QueryCallWaitingCommand extends QueryCommand {
        public int result;
        public SettableFuture resultFuture;

        @Override // com.android.settings.sim.smartForwarding.EnableSmartForwardingTask.Command
        public final boolean process() {
            this.tm
                    .createForSubscriptionId(this.subId)
                    .getCallWaitingStatus(
                            this.executor,
                            new EnableSmartForwardingTask$QueryCallWaitingCommand$$ExternalSyntheticLambda0(
                                    this, 0));
            return ((Boolean) this.resultFuture.get()).booleanValue();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class QueryCommand implements Command {
        public final Executor executor;
        public final int subId;
        public final TelephonyManager tm;

        public QueryCommand(TelephonyManager telephonyManager, Executor executor, int i) {
            this.subId = i;
            this.tm = telephonyManager;
            this.executor = executor;
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(getClass().getSimpleName());
            sb.append("[SubId ");
            return Anchor$$ExternalSyntheticOutline0.m(sb, this.subId, "]");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SlotUTData {
        public QueryCallForwardingCommand mQueryCallForwarding;
        public QueryCallWaitingCommand mQueryCallWaiting;
        public UpdateCallForwardingCommand mUpdateCallForwarding;
        public UpdateCallWaitingCommand mUpdateCallWaiting;
        public int subId;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UpdateCallForwardingCommand extends UpdateCommand {
        public String phoneNum;
        public QueryCallForwardingCommand queryResult;
        public SettableFuture resultFuture;

        @Override // com.android.settings.sim.smartForwarding.EnableSmartForwardingTask.UpdateCommand
        public final void onRestore() {
            Log.d("SmartForwarding", "onRestore: " + this);
            this.tm
                    .createForSubscriptionId(this.subId)
                    .setCallForwarding(this.queryResult.result, null, null);
        }

        @Override // com.android.settings.sim.smartForwarding.EnableSmartForwardingTask.Command
        public final boolean process() {
            this.tm
                    .createForSubscriptionId(this.subId)
                    .setCallForwarding(
                            new CallForwardingInfo(true, 3, this.phoneNum, 3),
                            this.executor,
                            new EnableSmartForwardingTask$QueryCallWaitingCommand$$ExternalSyntheticLambda0(
                                    this, 1));
            return ((Boolean) this.resultFuture.get()).booleanValue();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class UpdateCallWaitingCommand extends UpdateCommand {
        public QueryCallWaitingCommand queryResult;
        public SettableFuture resultFuture;

        @Override // com.android.settings.sim.smartForwarding.EnableSmartForwardingTask.UpdateCommand
        public final void onRestore() {
            Log.d("SmartForwarding", "onRestore: " + this);
            if (this.queryResult.result != 1) {
                this.tm
                        .createForSubscriptionId(this.subId)
                        .setCallWaitingEnabled(false, null, null);
            }
        }

        @Override // com.android.settings.sim.smartForwarding.EnableSmartForwardingTask.Command
        public final boolean process() {
            this.tm
                    .createForSubscriptionId(this.subId)
                    .setCallWaitingEnabled(
                            true,
                            this.executor,
                            new EnableSmartForwardingTask$QueryCallWaitingCommand$$ExternalSyntheticLambda0(
                                    this, 2));
            return ((Boolean) this.resultFuture.get()).booleanValue();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class UpdateCommand implements Command {
        public final Executor executor;
        public final int subId;
        public final TelephonyManager tm;

        public UpdateCommand(TelephonyManager telephonyManager, Executor executor, int i) {
            this.subId = i;
            this.tm = telephonyManager;
            this.executor = executor;
        }

        public abstract void onRestore();

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(getClass().getSimpleName());
            sb.append("[SubId ");
            return Anchor$$ExternalSyntheticOutline0.m(sb, this.subId, "] ");
        }
    }

    public EnableSmartForwardingTask(Context context, String[] strArr) {
        FeatureResult featureResult = new FeatureResult();
        featureResult.result = false;
        featureResult.slotUTData = null;
        this.mResult = featureResult;
        this.client = new SettableFuture();
        this.tm = (TelephonyManager) context.getSystemService(TelephonyManager.class);
        this.sm = (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        this.mCallForwardingNumber = strArr;
    }

    @Override // java.util.concurrent.Callable
    public final Object call() {
        EnableSmartForwardingTask enableSmartForwardingTask = this;
        ArrayList arrayList = new ArrayList();
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        TelephonyManager telephonyManager = enableSmartForwardingTask.tm;
        SettableFuture settableFuture = enableSmartForwardingTask.client;
        FeatureResult featureResult = enableSmartForwardingTask.mResult;
        if (telephonyManager == null || enableSmartForwardingTask.sm == null) {
            Log.e("SmartForwarding", "TelephonyManager or SubscriptionManager is null");
        } else {
            String[] strArr = enableSmartForwardingTask.mCallForwardingNumber;
            if (strArr.length == telephonyManager.getActiveModemCount()) {
                int activeModemCount = enableSmartForwardingTask.tm.getActiveModemCount();
                SlotUTData[] slotUTDataArr = new SlotUTData[activeModemCount];
                int i = 0;
                while (i < activeModemCount) {
                    int subscriptionId = SubscriptionManager.getSubscriptionId(i);
                    if (SubscriptionManager.isValidSubscriptionId(subscriptionId)) {
                        QueryCallWaitingCommand queryCallWaitingCommand =
                                new QueryCallWaitingCommand(
                                        enableSmartForwardingTask.tm,
                                        newSingleThreadExecutor,
                                        subscriptionId);
                        queryCallWaitingCommand.resultFuture = new SettableFuture();
                        QueryCallForwardingCommand queryCallForwardingCommand =
                                new QueryCallForwardingCommand(
                                        enableSmartForwardingTask.tm,
                                        newSingleThreadExecutor,
                                        subscriptionId);
                        queryCallForwardingCommand.resultFuture = new SettableFuture();
                        UpdateCallWaitingCommand updateCallWaitingCommand =
                                new UpdateCallWaitingCommand(
                                        enableSmartForwardingTask.tm,
                                        newSingleThreadExecutor,
                                        subscriptionId);
                        updateCallWaitingCommand.resultFuture = new SettableFuture();
                        updateCallWaitingCommand.queryResult = queryCallWaitingCommand;
                        TelephonyManager telephonyManager2 = enableSmartForwardingTask.tm;
                        String str = strArr[i];
                        UpdateCallForwardingCommand updateCallForwardingCommand =
                                new UpdateCallForwardingCommand(
                                        telephonyManager2, newSingleThreadExecutor, subscriptionId);
                        updateCallForwardingCommand.resultFuture = new SettableFuture();
                        updateCallForwardingCommand.phoneNum = str;
                        updateCallForwardingCommand.queryResult = queryCallForwardingCommand;
                        String str2 = strArr[i];
                        SlotUTData slotUTData = new SlotUTData();
                        slotUTData.subId = subscriptionId;
                        slotUTData.mQueryCallWaiting = queryCallWaitingCommand;
                        slotUTData.mQueryCallForwarding = queryCallForwardingCommand;
                        slotUTData.mUpdateCallWaiting = updateCallWaitingCommand;
                        slotUTData.mUpdateCallForwarding = updateCallForwardingCommand;
                        slotUTDataArr[i] = slotUTData;
                        i++;
                        enableSmartForwardingTask = this;
                    } else {
                        featureResult.reason = FeatureResult.FailedReason.SIM_NOT_ACTIVE;
                    }
                }
                for (int i2 = 0; i2 < activeModemCount; i2++) {
                    arrayList.add(slotUTDataArr[i2].mQueryCallWaiting);
                }
                for (int i3 = 0; i3 < activeModemCount; i3++) {
                    arrayList.add(slotUTDataArr[i3].mQueryCallForwarding);
                }
                for (int i4 = 0; i4 < activeModemCount; i4++) {
                    arrayList.add(slotUTDataArr[i4].mUpdateCallWaiting);
                }
                for (int i5 = 0; i5 < activeModemCount; i5++) {
                    arrayList.add(slotUTDataArr[i5].mUpdateCallForwarding);
                }
                boolean z = true;
                int i6 = 0;
                while (i6 < arrayList.size() && z) {
                    Command command = (Command) arrayList.get(i6);
                    Log.d("SmartForwarding", "processing : " + command);
                    try {
                        z = command.process();
                    } catch (Exception e) {
                        Log.d("SmartForwarding", "Failed on : " + command, e);
                        z = false;
                    }
                    if (z) {
                        i6++;
                    } else {
                        Log.d("SmartForwarding", "Failed on : " + command);
                    }
                }
                if (z) {
                    featureResult.result = true;
                    featureResult.slotUTData = slotUTDataArr;
                    Log.d("SmartForwarding", "Smart forwarding successful");
                    settableFuture.set(featureResult);
                } else {
                    List<Command> subList = arrayList.subList(0, i6);
                    Collections.reverse(subList);
                    for (Command command2 : subList) {
                        Log.d("SmartForwarding", "restoreStep: " + command2);
                        if (command2 instanceof UpdateCommand) {
                            ((UpdateCommand) command2).onRestore();
                        }
                    }
                    settableFuture.set(featureResult);
                }
                return (FeatureResult) settableFuture.get(20L, TimeUnit.SECONDS);
            }
            Log.e("SmartForwarding", "The length of PhoneNum array should same as phone count.");
        }
        settableFuture.set(featureResult);
        return (FeatureResult) settableFuture.get(20L, TimeUnit.SECONDS);
    }
}
