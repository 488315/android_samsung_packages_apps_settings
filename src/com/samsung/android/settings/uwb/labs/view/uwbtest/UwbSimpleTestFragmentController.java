package com.samsung.android.settings.uwb.labs.view.uwbtest;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.util.Log;
import android.uwb.RangingMeasurement;
import android.uwb.RangingReport;
import android.uwb.RangingSession;
import android.uwb.UwbAddress;

import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.android.settings.core.BasePreferenceController;

import com.google.uwb.support.fira.FiraOpenSessionParams;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.uwb.labs.control.uwbtest.Configurations;
import com.samsung.android.settings.uwb.labs.control.uwbtest.Ranging;
import com.samsung.android.settings.uwb.labs.control.uwbtest.UwbTestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class UwbSimpleTestFragmentController extends BasePreferenceController {
    public static final int CLOSE = -1;
    public static final int INIT = 1;
    public static final int START = 2;
    public static final int STOP = 3;
    public static final int STOPFAIL = 4;
    private static final String TAG = "UwbSimpleTestController";
    static int mState;
    private static UwbDataReceiver mUwbDataReceiver;
    static UwbTestController mUwbTestController;
    private Ranging.Callback mCallback;
    private Context mContext;
    private RangingSession mUwbRanging;

    public UwbSimpleTestFragmentController(Context context, String str) {
        super(context, str);
        this.mUwbRanging = null;
        Log.i(TAG, "CREATE: UwbSimpleTestFragmentController ");
        Log.i(TAG, "Key = " + str);
        this.mContext = context;
        mState = -1;
        UwbTestController uwbTestController = new UwbTestController(context);
        mUwbTestController = uwbTestController;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1();
        this.mCallback = anonymousClass1;
        uwbTestController.mRanging.mCallback = anonymousClass1;
    }

    public static void setOnUwbReceiver(UwbDataReceiver uwbDataReceiver) {
        mUwbDataReceiver = uwbDataReceiver;
    }

    public static void start(boolean z) {
        UwbAddress fromBytes;
        int i;
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("START button is clicked, myAddress: "), !z ? 1 : 0, TAG);
        if (mState == 3) {
            mUwbTestController.startRanging();
            return;
        }
        ArrayList arrayList = new ArrayList();
        Executors.newSingleThreadExecutor();
        if (z) {
            fromBytes = UwbAddress.fromBytes(new byte[] {0, 0});
            arrayList.add(UwbAddress.fromBytes(new byte[] {0, 1}));
            i = 1;
        } else {
            fromBytes = UwbAddress.fromBytes(new byte[] {0, 1});
            arrayList.add(UwbAddress.fromBytes(new byte[] {0, 0}));
            i = 0;
        }
        int i2 = i;
        FiraOpenSessionParams.Builder builder = new FiraOpenSessionParams.Builder();
        builder.mDeviceType.set(Integer.valueOf(i));
        builder.mDeviceRole.set(Integer.valueOf(i2));
        builder.mMultiNodeMode.set(1);
        builder.mDeviceAddress = fromBytes;
        builder.mDestAddressList = arrayList;
        builder.mStsConfig = 0;
        builder.mVendorId = new byte[] {0, 0};
        builder.mStaticStsIV = new byte[] {0, 0, 0, 0, 0, 0};
        builder.mChannelNumber = 9;
        builder.mPreambleCodeIndex = 9;
        builder.mIsTxAdaptivePayloadPowerEnabled = true;
        builder.mSlotsPerRangingRound = 25;
        builder.mSessionId.set(1);
        builder.mRangingIntervalMs = 100;
        builder.mProtocolVersion.set(FiraOpenSessionParams.PROTOCOL_VERSION_1_1);
        FiraOpenSessionParams build = builder.build();
        UwbTestController uwbTestController = mUwbTestController;
        Configurations configurations = uwbTestController.mConfiguration;
        configurations.mSessionId = 1;
        configurations.mFiraOpenSessionParams = build;
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        PersistableBundle bundle =
                uwbTestController.mConfiguration.mFiraOpenSessionParams.toBundle();
        Ranging ranging = uwbTestController.mRanging;
        ranging.mUwbManager.openRangingSession(
                bundle, newSingleThreadExecutor, ranging.mRangingCallback);
    }

    public static void stop() {
        Log.d(TAG, "STOP button is clicked");
        RangingSession rangingSession = mUwbTestController.mRanging.mRangingSession;
        if (rangingSession != null) {
            rangingSession.close();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        Log.d(TAG, "getAvailabilityStatus");
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
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

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.uwb.labs.view.uwbtest.UwbSimpleTestFragmentController$1, reason: invalid class name */
    public final class AnonymousClass1 implements Ranging.Callback {
        @Override // com.samsung.android.settings.uwb.labs.control.uwbtest.Ranging.Callback
        public final void onClosed() {
            Log.d(UwbSimpleTestFragmentController.TAG, "onSessionStatusChanged  status: CLOSE");
            UwbSimpleTestFragmentController.mState = -1;
            UwbSimpleTestFragmentController.mUwbDataReceiver.onStateReceive(-1);
        }

        @Override // com.samsung.android.settings.uwb.labs.control.uwbtest.Ranging.Callback
        public final void onOpened() {
            Log.d(UwbSimpleTestFragmentController.TAG, "onSessionStatusChanged  status: INIT");
            UwbSimpleTestFragmentController.mState = 1;
            UwbSimpleTestFragmentController.mUwbTestController.startRanging();
        }

        @Override // com.samsung.android.settings.uwb.labs.control.uwbtest.Ranging.Callback
        public final void onReportReceived(RangingReport rangingReport) {
            if (((RangingMeasurement) rangingReport.getMeasurements().get(0))
                            .getDistanceMeasurement()
                    == null) {
                Log.d(UwbSimpleTestFragmentController.TAG, "onRangingResults distance: null");
            } else {
                UwbSimpleTestFragmentController.mUwbDataReceiver.onReceive(rangingReport);
            }
        }

        @Override // com.samsung.android.settings.uwb.labs.control.uwbtest.Ranging.Callback
        public final void onStarted() {
            UwbSimpleTestFragmentController.mState = 2;
        }

        @Override // com.samsung.android.settings.uwb.labs.control.uwbtest.Ranging.Callback
        public final void onStopped() {
            Log.d(UwbSimpleTestFragmentController.TAG, "onSessionStatusChanged  status: STOP");
            UwbSimpleTestFragmentController.mState = 3;
            UwbSimpleTestFragmentController.mUwbDataReceiver.onStateReceive(3);
        }

        @Override // com.samsung.android.settings.uwb.labs.control.uwbtest.Ranging.Callback
        public final void onOpenFailed() {}
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
