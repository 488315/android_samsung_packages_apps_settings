package com.samsung.android.settings.uwb.labs.control.uwbtest;

import android.content.Context;
import android.os.PersistableBundle;
import android.util.Log;
import android.uwb.RangingReport;
import android.uwb.RangingSession;
import android.uwb.UwbAddress;
import android.uwb.UwbManager;

import com.google.uwb.support.fira.FiraOpenSessionParams;
import com.google.uwb.support.fira.FiraProtocolVersion;
import com.sec.ims.volte2.data.VolteConstants;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class UwbTestController {
    public final Configurations mConfiguration;
    public final Ranging mRanging;

    public UwbTestController(Context context) {
        UwbManager uwbManager = (UwbManager) context.getSystemService(UwbManager.class);
        Configurations configurations = new Configurations();
        configurations.mSessionId = VolteConstants.ErrorCode.CALL_HOLD_FAILED;
        Log.i("UwbTestConfiguration", "UwbTest_Configuration created by default initiator config");
        FiraProtocolVersion firaProtocolVersion = FiraOpenSessionParams.PROTOCOL_VERSION_1_1;
        ArrayList arrayList = new ArrayList();
        arrayList.add(UwbAddress.fromBytes(new byte[] {0, 1}));
        UwbAddress fromBytes = UwbAddress.fromBytes(new byte[] {0, 0});
        FiraOpenSessionParams.Builder builder = new FiraOpenSessionParams.Builder();
        builder.mDeviceType.set(1);
        builder.mDeviceRole.set(1);
        builder.mMultiNodeMode.set(1);
        builder.mDeviceAddress = fromBytes;
        builder.mDestAddressList = arrayList;
        builder.mStsConfig = 0;
        builder.mVendorId = new byte[] {0, 0};
        builder.mStaticStsIV = new byte[] {0, 0, 0, 0, 0, 0};
        builder.mSessionId.set(Integer.valueOf(configurations.mSessionId));
        builder.mProtocolVersion.set(firaProtocolVersion);
        configurations.mFiraOpenSessionParams = builder.build();
        this.mConfiguration = configurations;
        Ranging ranging = new Ranging();
        ranging.mState = 0;
        ranging.mUwbManager = uwbManager;
        ranging.mRangingCallback =
                new RangingSession
                        .Callback() { // from class:
                                      // com.samsung.android.settings.uwb.labs.control.uwbtest.Ranging.1
                    public AnonymousClass1() {}

                    public final void onClosed(int i, PersistableBundle persistableBundle) {
                        Log.e("Ranging", "onClosed");
                        Ranging ranging2 = Ranging.this;
                        ranging2.mState = 3;
                        ranging2.mCallback.onClosed();
                    }

                    public final void onOpenFailed(int i, PersistableBundle persistableBundle) {
                        Log.e("Ranging", "onOpenFailed");
                        Ranging ranging2 = Ranging.this;
                        ranging2.mState = 3;
                        ranging2.mCallback.onOpenFailed();
                    }

                    public final void onOpened(RangingSession rangingSession) {
                        Log.i("Ranging", "onOpened");
                        Ranging ranging2 = Ranging.this;
                        ranging2.mState = 1;
                        ranging2.mRangingSession = rangingSession;
                        ranging2.mCallback.onOpened();
                    }

                    public final void onReconfigureFailed(
                            int i, PersistableBundle persistableBundle) {
                        Log.e("Ranging", "onReconfigureFailed");
                    }

                    public final void onReconfigured(PersistableBundle persistableBundle) {
                        Log.i("Ranging", "onReconfigured");
                    }

                    public final void onReportReceived(RangingReport rangingReport) {
                        Log.i("Ranging", "onReportReceived");
                        Ranging.this.mCallback.onReportReceived(rangingReport);
                    }

                    public final void onStartFailed(int i, PersistableBundle persistableBundle) {
                        Log.e("Ranging", "onStartFailed");
                    }

                    public final void onStarted(PersistableBundle persistableBundle) {
                        Log.i("Ranging", "onStarted");
                        Ranging ranging2 = Ranging.this;
                        ranging2.mState = 2;
                        ranging2.mCallback.onStarted();
                    }

                    public final void onStopFailed(int i, PersistableBundle persistableBundle) {
                        Log.e("Ranging", "onStopFailed");
                    }

                    public final void onStopped(int i, PersistableBundle persistableBundle) {
                        Log.i("Ranging", "onStopped");
                        Ranging ranging2 = Ranging.this;
                        ranging2.mState = 1;
                        ranging2.mCallback.onStopped();
                    }
                };
        this.mRanging = ranging;
    }

    public final void startRanging() {
        RangingSession rangingSession;
        Ranging ranging = this.mRanging;
        if (ranging.mState == 1 && (rangingSession = ranging.mRangingSession) != null) {
            rangingSession.start(new PersistableBundle());
        }
    }
}
