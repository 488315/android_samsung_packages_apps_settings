package com.android.settings.network;

import android.telephony.SubscriptionInfo;
import android.util.Log;

import com.android.settings.AsyncTaskSidecar;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class SwitchSlotSidecar extends AsyncTaskSidecar {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Param {
        public int command;
        public int port;
        public SubscriptionInfo removedSubInfo;
        public int slotId;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Result {
        public UiccSlotsException exception;
    }

    @Override // com.android.settings.AsyncTaskSidecar
    public final Object doInBackground(Param param) {
        Result result = new Result();
        if (param == null) {
            result.exception = new UiccSlotsException("Null param");
        } else {
            try {
                int i = param.command;
                if (i == 0) {
                    Log.i("SwitchSlotSidecar", "Start to switch to removable slot.");
                    UiccSlotUtil.switchToRemovableSlot(
                            param.slotId, getContext(), param.removedSubInfo);
                } else if (i != 1) {
                    Log.e("SwitchSlotSidecar", "Wrong command.");
                } else {
                    Log.i("SwitchSlotSidecar", "Start to switch to euicc slot.");
                    UiccSlotUtil.switchToEuiccSlot(
                            getContext(), param.slotId, param.port, param.removedSubInfo);
                }
            } catch (UiccSlotsException e) {
                result.exception = e;
            }
            Log.i("SwitchSlotSidecar", "return command.");
        }
        return result;
    }

    @Override // com.android.settings.AsyncTaskSidecar
    public final void onPostExecute$1(Object obj) {
        Log.i("SwitchSlotSidecar", "onPostExecute: get result");
        if (((Result) obj).exception == null) {
            setState(2, 0);
        } else {
            setState(3, 0);
        }
    }
}
