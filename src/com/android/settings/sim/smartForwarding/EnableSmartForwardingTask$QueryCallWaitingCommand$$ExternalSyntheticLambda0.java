package com.android.settings.sim.smartForwarding;

import android.util.Log;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.google.common.util.concurrent.SettableFuture;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */
class EnableSmartForwardingTask$QueryCallWaitingCommand$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ EnableSmartForwardingTask.Command f$0;

    public /* synthetic */
    EnableSmartForwardingTask$QueryCallWaitingCommand$$ExternalSyntheticLambda0(
            EnableSmartForwardingTask.Command command, int i) {
        this.$r8$classId = i;
        this.f$0 = command;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        EnableSmartForwardingTask.Command command = this.f$0;
        switch (i) {
            case 0:
                EnableSmartForwardingTask.QueryCallWaitingCommand queryCallWaitingCommand =
                        (EnableSmartForwardingTask.QueryCallWaitingCommand) command;
                int intValue = ((Integer) obj).intValue();
                queryCallWaitingCommand.result = intValue;
                SettableFuture settableFuture = queryCallWaitingCommand.resultFuture;
                if (intValue != 1 && intValue != 2) {
                    settableFuture.set(Boolean.FALSE);
                    break;
                } else {
                    ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                            intValue, "Call Waiting result: ", "SmartForwarding");
                    settableFuture.set(Boolean.TRUE);
                    break;
                }
                break;
            case 1:
                EnableSmartForwardingTask.UpdateCallForwardingCommand updateCallForwardingCommand =
                        (EnableSmartForwardingTask.UpdateCallForwardingCommand) command;
                int intValue2 = ((Integer) obj).intValue();
                updateCallForwardingCommand.getClass();
                Log.d(
                        "SmartForwarding",
                        "UpdateCallForwardingCommand updateStatusCallBack : " + intValue2);
                SettableFuture settableFuture2 = updateCallForwardingCommand.resultFuture;
                if (intValue2 != 0) {
                    settableFuture2.set(Boolean.FALSE);
                    break;
                } else {
                    settableFuture2.set(Boolean.TRUE);
                    break;
                }
            default:
                EnableSmartForwardingTask.UpdateCallWaitingCommand updateCallWaitingCommand =
                        (EnableSmartForwardingTask.UpdateCallWaitingCommand) command;
                int intValue3 = ((Integer) obj).intValue();
                updateCallWaitingCommand.getClass();
                Log.d(
                        "SmartForwarding",
                        "UpdateCallWaitingCommand updateStatusCallBack result: " + intValue3);
                SettableFuture settableFuture3 = updateCallWaitingCommand.resultFuture;
                if (intValue3 != 1 && intValue3 != 2) {
                    settableFuture3.set(Boolean.FALSE);
                    break;
                } else {
                    settableFuture3.set(Boolean.TRUE);
                    break;
                }
                break;
        }
    }
}
