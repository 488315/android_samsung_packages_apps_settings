package com.samsung.android.settings.uwb.labs.view.uwbtest;

import android.content.Context;
import android.os.PersistableBundle;
import android.util.Log;
import android.uwb.RangingReport;
import android.uwb.RangingSession;
import android.uwb.UwbAddress;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.TooltipPopup$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.uwb.support.fira.FiraOpenSessionParams;
import com.samsung.android.settings.uwb.labs.common.UwbStateChangedHandler;
import com.samsung.android.settings.uwb.labs.common.UwbStateChangedListener;
import com.samsung.android.settings.uwb.labs.control.uwbtest.Ranging;
import com.samsung.android.settings.uwb.labs.control.uwbtest.UwbTestController;
import com.samsung.android.settings.uwb.labs.view.uwbtest.uwbconfig.UwbConfigListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class UwbFiraTestFragmentController extends AbstractPreferenceController
        implements UwbConfigListener {
    public static UwbTestController mController;
    public long mAbsoluteInitiationTime;
    public int mAoaResultRequest;
    public int mAoaType;
    public int mBlockStrideLength;
    public RelativeLayout mBottomLayout;
    public int mBprfPhrDataRate;
    public final AnonymousClass2 mCallback;
    public byte[] mCapSize;
    public int mChannelNumber;
    public MenuItem mCloseMenu;
    public final Context mContext;
    public List mDestAddressList;
    public UwbAddress mDeviceAddress;
    public int mDeviceRole;
    public int mDeviceType;
    public int mFcsType;
    public int mFilterType;
    public FiraOpenSessionParams mFiraOpenSessionParams;
    public final Fragment mFragment;
    public boolean mHasAngleOfArrivalAzimuthReport;
    public boolean mHasAngleOfArrivalElevationReport;
    public boolean mHasAngleOfArrivalFigureOfMeritReport;
    public boolean mHasControlMessage;
    public boolean mHasRangingControlPhase;
    public boolean mHasRangingResultReportMessage;
    public boolean mHasTimeOfFlightReport;
    public int mHoppingMode;
    public int mInBandTerminationAttemptCount;
    public long mInitiationTime;
    public int mInterFrameInterval;
    public boolean mIsDiagnosticsEnabled;
    public boolean mIsKeyRotationEnabled;
    public boolean mIsRssiReportingEnabled;
    public boolean mIsTxAdaptivePayloadPowerEnabled;
    public int mKeyRotationRate;
    public int mLinkLayerMode;
    public int mMacAddressMode;
    public int mMaxNumberOfMeasurements;
    public int mMaxRangingRoundRetries;
    public int mMeasurementReportType;
    public int mMinFramesPerRr;
    public int mMtuSize;
    public int mMultiNodeMode;
    public int mNumOfMsrmtFocusOnAoaAzimuth;
    public int mNumOfMsrmtFocusOnAoaElevation;
    public int mNumOfMsrmtFocusOnRange;
    public MenuItem mOpenMenu;
    public int mPreambleCodeIndex;
    public int mPreambleDuration;
    public int mPrfMode;
    public int mPsduDataRate;
    public int mRangeDataNtfConfig;
    public int mRangeDataNtfProximityFar;
    public int mRangeDataNtfProximityNear;
    public Long mRangingErrorStreakTimeoutMs;
    public int mRangingIntervalMs;
    public int mRangingRoundUsage;
    public int mRframeConfig;
    public int mScheduledMode;
    public int mSessionId;
    public byte[] mSessionKey;
    public int mSessionPriority;
    public int mSessionType;
    public int mSfdId;
    public int mSlotDurationRstu;
    public int mSlotsPerRangingRound;
    public MenuItem mStartMenu;
    public byte[] mStaticStsIV;
    public MenuItem mStopMenu;
    public int mStsConfig;
    public int mStsLength;
    public int mStsSegmentCount;
    public int mSubSessionId;
    public byte[] mSubSessionKey;
    public BottomNavigationView mTestButtonView;
    public byte[] mUlTdoaDeviceId;
    public int mUlTdoaDeviceIdType;
    public int mUlTdoaRandomWindowMs;
    public int mUlTdoaTxIntervalMs;
    public int mUlTdoaTxTimestampType;
    public UwbDataReceiver mUwbDataReceiver;
    public UwbFiraTestFragment mUwbUiEventReceiver;
    public byte[] mVendorId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.uwb.labs.view.uwbtest.UwbFiraTestFragmentController$1, reason: invalid class name */
    public final class AnonymousClass1 implements UwbStateChangedListener {
        @Override // com.samsung.android.settings.uwb.labs.common.UwbStateChangedListener
        public final void onUpdatedState(int i, int i2) {
            Log.i(
                    "UwbFiraTestFragmentController",
                    "onUpdatedState in UwbFiraTestFragmentController");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.uwb.labs.view.uwbtest.UwbFiraTestFragmentController$2, reason: invalid class name */
    public final class AnonymousClass2
            implements Ranging.Callback, BottomNavigationView.OnNavigationItemSelectedListener {
        public /* synthetic */ AnonymousClass2() {}

        @Override // com.samsung.android.settings.uwb.labs.control.uwbtest.Ranging.Callback
        public void onClosed() {
            Log.i("UwbFiraTestFragmentController", "onClosed");
            UwbFiraTestFragmentController.this.updateBottomMenu();
        }

        @Override // com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            RangingSession rangingSession;
            int itemId = menuItem.getItemId();
            UwbFiraTestFragmentController uwbFiraTestFragmentController =
                    UwbFiraTestFragmentController.this;
            if (itemId != R.id.open_button) {
                if (itemId == R.id.close_button) {
                    RangingSession rangingSession2 =
                            UwbFiraTestFragmentController.mController.mRanging.mRangingSession;
                    if (rangingSession2 != null) {
                        rangingSession2.close();
                    }
                    uwbFiraTestFragmentController.mUwbUiEventReceiver.onButtonClicked(2);
                    return true;
                }
                if (itemId == R.id.start_button) {
                    UwbFiraTestFragmentController.mController.startRanging();
                    uwbFiraTestFragmentController.mUwbUiEventReceiver.onButtonClicked(3);
                    return true;
                }
                if (itemId != R.id.stop_button) {
                    return true;
                }
                Ranging ranging = UwbFiraTestFragmentController.mController.mRanging;
                if (ranging.mState == 2 && (rangingSession = ranging.mRangingSession) != null) {
                    rangingSession.stop();
                }
                uwbFiraTestFragmentController.mUwbUiEventReceiver.onButtonClicked(4);
                return true;
            }
            uwbFiraTestFragmentController.getClass();
            FiraOpenSessionParams.Builder builder = new FiraOpenSessionParams.Builder();
            builder.mSessionId.set(Integer.valueOf(uwbFiraTestFragmentController.mSessionId));
            builder.mDeviceRole.set(Integer.valueOf(uwbFiraTestFragmentController.mDeviceRole));
            builder.mDeviceType.set(Integer.valueOf(uwbFiraTestFragmentController.mDeviceType));
            builder.mSessionType = uwbFiraTestFragmentController.mSessionType;
            builder.mScheduledMode = uwbFiraTestFragmentController.mScheduledMode;
            builder.mChannelNumber = uwbFiraTestFragmentController.mChannelNumber;
            builder.mMultiNodeMode.set(
                    Integer.valueOf(uwbFiraTestFragmentController.mMultiNodeMode));
            builder.mDeviceAddress = uwbFiraTestFragmentController.mDeviceAddress;
            builder.mDestAddressList = uwbFiraTestFragmentController.mDestAddressList;
            builder.mSlotDurationRstu = uwbFiraTestFragmentController.mSlotDurationRstu;
            builder.mRangingIntervalMs = uwbFiraTestFragmentController.mRangingIntervalMs;
            builder.mFcsType = uwbFiraTestFragmentController.mFcsType;
            builder.mSfdId = uwbFiraTestFragmentController.mSfdId;
            builder.mPsduDataRate = uwbFiraTestFragmentController.mPsduDataRate;
            builder.mPreambleDuration = uwbFiraTestFragmentController.mPreambleDuration;
            builder.mPreambleCodeIndex = uwbFiraTestFragmentController.mPreambleCodeIndex;
            builder.mSessionPriority = uwbFiraTestFragmentController.mSessionPriority;
            builder.mBlockStrideLength = uwbFiraTestFragmentController.mBlockStrideLength;
            builder.mSlotsPerRangingRound = uwbFiraTestFragmentController.mSlotsPerRangingRound;
            builder.mPrfMode = uwbFiraTestFragmentController.mPrfMode;
            builder.mMacAddressMode = uwbFiraTestFragmentController.mMacAddressMode;
            builder.mHoppingMode = uwbFiraTestFragmentController.mHoppingMode;
            builder.mInBandTerminationAttemptCount =
                    uwbFiraTestFragmentController.mInBandTerminationAttemptCount;
            builder.mRangingRoundUsage = uwbFiraTestFragmentController.mRangingRoundUsage;
            builder.mInitiationTime = uwbFiraTestFragmentController.mInitiationTime;
            builder.mAbsoluteInitiationTime = uwbFiraTestFragmentController.mAbsoluteInitiationTime;
            builder.mMaxRangingRoundRetries = uwbFiraTestFragmentController.mMaxRangingRoundRetries;
            builder.mHasRangingResultReportMessage =
                    uwbFiraTestFragmentController.mHasRangingResultReportMessage;
            builder.mHasControlMessage = uwbFiraTestFragmentController.mHasControlMessage;
            builder.mHasRangingControlPhase = uwbFiraTestFragmentController.mHasRangingControlPhase;
            builder.mMeasurementReportType = uwbFiraTestFragmentController.mMeasurementReportType;
            builder.mRframeConfig = uwbFiraTestFragmentController.mRframeConfig;
            builder.mCapSize = uwbFiraTestFragmentController.mCapSize;
            builder.mStsSegmentCount = uwbFiraTestFragmentController.mStsSegmentCount;
            builder.mStsLength = uwbFiraTestFragmentController.mStsLength;
            builder.mSessionKey = uwbFiraTestFragmentController.mSessionKey;
            builder.mSubsessionKey = uwbFiraTestFragmentController.mSubSessionKey;
            builder.mBprfPhrDataRate = uwbFiraTestFragmentController.mBprfPhrDataRate;
            builder.mIsTxAdaptivePayloadPowerEnabled =
                    uwbFiraTestFragmentController.mIsTxAdaptivePayloadPowerEnabled;
            builder.mStsConfig = uwbFiraTestFragmentController.mStsConfig;
            builder.mSubSessionId.set(Integer.valueOf(uwbFiraTestFragmentController.mSubSessionId));
            builder.mAoaType = uwbFiraTestFragmentController.mAoaType;
            builder.mVendorId = uwbFiraTestFragmentController.mVendorId;
            builder.mStaticStsIV = uwbFiraTestFragmentController.mStaticStsIV;
            builder.mIsRssiReportingEnabled = uwbFiraTestFragmentController.mIsRssiReportingEnabled;
            builder.mIsDiagnosticsEnabled = uwbFiraTestFragmentController.mIsDiagnosticsEnabled;
            builder.mIsKeyRotationEnabled = uwbFiraTestFragmentController.mIsKeyRotationEnabled;
            builder.mKeyRotationRate = uwbFiraTestFragmentController.mKeyRotationRate;
            builder.mAoaResultRequest = uwbFiraTestFragmentController.mAoaResultRequest;
            builder.mRangeDataNtfConfig = uwbFiraTestFragmentController.mRangeDataNtfConfig;
            builder.mRangeDataNtfProximityNear =
                    uwbFiraTestFragmentController.mRangeDataNtfProximityNear;
            builder.mRangeDataNtfProximityFar =
                    uwbFiraTestFragmentController.mRangeDataNtfProximityFar;
            builder.mHasTimeOfFlightReport = uwbFiraTestFragmentController.mHasTimeOfFlightReport;
            builder.mHasAngleOfArrivalAzimuthReport =
                    uwbFiraTestFragmentController.mHasAngleOfArrivalAzimuthReport;
            builder.mHasAngleOfArrivalElevationReport =
                    uwbFiraTestFragmentController.mHasAngleOfArrivalElevationReport;
            builder.mHasAngleOfArrivalFigureOfMeritReport =
                    uwbFiraTestFragmentController.mHasAngleOfArrivalFigureOfMeritReport;
            int i = uwbFiraTestFragmentController.mNumOfMsrmtFocusOnRange;
            int i2 = uwbFiraTestFragmentController.mNumOfMsrmtFocusOnAoaAzimuth;
            int i3 = uwbFiraTestFragmentController.mNumOfMsrmtFocusOnAoaElevation;
            builder.mNumOfMsrmtFocusOnRange = i;
            builder.mNumOfMsrmtFocusOnAoaAzimuth = i2;
            builder.mNumOfMsrmtFocusOnAoaElevation = i3;
            builder.mRangingErrorStreakTimeoutMs =
                    uwbFiraTestFragmentController.mRangingErrorStreakTimeoutMs.longValue();
            builder.mLinkLayerMode = uwbFiraTestFragmentController.mLinkLayerMode;
            builder.mMinFramesPerRr = uwbFiraTestFragmentController.mMinFramesPerRr;
            builder.mMtuSize = uwbFiraTestFragmentController.mMtuSize;
            builder.mInterFrameInterval = uwbFiraTestFragmentController.mInterFrameInterval;
            builder.mUlTdoaTxIntervalMs = uwbFiraTestFragmentController.mUlTdoaTxIntervalMs;
            builder.mUlTdoaRandomWindowMs = uwbFiraTestFragmentController.mUlTdoaRandomWindowMs;
            builder.mUlTdoaDeviceIdType = uwbFiraTestFragmentController.mUlTdoaDeviceIdType;
            builder.mUlTdoaDeviceId = uwbFiraTestFragmentController.mUlTdoaDeviceId;
            builder.mUlTdoaTxTimestampType = uwbFiraTestFragmentController.mUlTdoaTxTimestampType;
            builder.mFilterType = uwbFiraTestFragmentController.mFilterType;
            builder.mMaxNumberOfMeasurements =
                    uwbFiraTestFragmentController.mMaxNumberOfMeasurements;
            builder.mProtocolVersion.set(
                    uwbFiraTestFragmentController.mFiraOpenSessionParams.mProtocolVersion);
            FiraOpenSessionParams build = builder.build();
            uwbFiraTestFragmentController.mFiraOpenSessionParams = build;
            UwbTestController uwbTestController = UwbFiraTestFragmentController.mController;
            uwbTestController.mConfiguration.mFiraOpenSessionParams = build;
            ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
            PersistableBundle bundle =
                    uwbTestController.mConfiguration.mFiraOpenSessionParams.toBundle();
            Ranging ranging2 = uwbTestController.mRanging;
            ranging2.mUwbManager.openRangingSession(
                    bundle, newSingleThreadExecutor, ranging2.mRangingCallback);
            uwbFiraTestFragmentController.mUwbUiEventReceiver.onButtonClicked(0);
            return true;
        }

        @Override // com.samsung.android.settings.uwb.labs.control.uwbtest.Ranging.Callback
        public void onOpenFailed() {
            Log.i("UwbFiraTestFragmentController", "onOpenFailed");
            UwbFiraTestFragmentController.this.updateBottomMenu();
        }

        @Override // com.samsung.android.settings.uwb.labs.control.uwbtest.Ranging.Callback
        public void onOpened() {
            Log.i("UwbFiraTestFragmentController", "onOpened");
            UwbFiraTestFragmentController.this.updateBottomMenu();
        }

        @Override // com.samsung.android.settings.uwb.labs.control.uwbtest.Ranging.Callback
        public void onReportReceived(RangingReport rangingReport) {
            Log.i("UwbFiraTestFragmentController", "onReportReceived");
            UwbFiraTestFragmentController.this.mUwbDataReceiver.onReceive(rangingReport);
        }

        @Override // com.samsung.android.settings.uwb.labs.control.uwbtest.Ranging.Callback
        public void onStarted() {
            Log.i("UwbFiraTestFragmentController", "onStarted");
            UwbFiraTestFragmentController.this.updateBottomMenu();
        }

        @Override // com.samsung.android.settings.uwb.labs.control.uwbtest.Ranging.Callback
        public void onStopped() {
            Log.i("UwbFiraTestFragmentController", "onStopped");
            UwbFiraTestFragmentController.this.updateBottomMenu();
        }
    }

    public UwbFiraTestFragmentController(Context context, Fragment fragment) {
        super(context);
        this.mUwbDataReceiver = null;
        Log.i("UwbFiraTestFragmentController", "CREATE: UwbFiraTestFragmentController ");
        this.mContext = context;
        this.mFragment = fragment;
        UwbTestController uwbTestController = new UwbTestController(context);
        mController = uwbTestController;
        this.mFiraOpenSessionParams = uwbTestController.mConfiguration.mFiraOpenSessionParams;
        UwbStateChangedHandler uwbStateChangedHandler = UwbStateChangedHandler.getInstance();
        ((ArrayList) uwbStateChangedHandler.listeners).add(new AnonymousClass1());
        mController.mRanging.mCallback = new AnonymousClass2();
    }

    /* JADX WARN: Code restructure failed: missing block: B:102:0x01d5, code lost:

       if (r5.mFiraOpenSessionParams.mHasRangingResultReportMessage == true) goto L96;
    */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x01d7, code lost:

       r0 = 1;
    */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x01da, code lost:

       r0 = 0;
    */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x01e9, code lost:

       if (r5.mFiraOpenSessionParams.mHasControlMessage == true) goto L96;
    */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x01f8, code lost:

       if (r5.mFiraOpenSessionParams.mHasRangingControlPhase == true) goto L96;
    */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x0292, code lost:

       if (r5.mFiraOpenSessionParams.mIsTxAdaptivePayloadPowerEnabled == true) goto L96;
    */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x02f4, code lost:

       if (r5.mFiraOpenSessionParams.mIsRssiReportingEnabled == true) goto L96;
    */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x0304, code lost:

       if (r5.mFiraOpenSessionParams.mIsDiagnosticsEnabled == true) goto L96;
    */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x031e, code lost:

       if (r5.mFiraOpenSessionParams.mIsKeyRotationEnabled == true) goto L96;
    */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x0374, code lost:

       if (r5.mFiraOpenSessionParams.mHasTimeOfFlightReport == true) goto L96;
    */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x0384, code lost:

       if (r5.mFiraOpenSessionParams.mHasAngleOfArrivalAzimuthReport == true) goto L96;
    */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x0394, code lost:

       if (r5.mFiraOpenSessionParams.mHasAngleOfArrivalElevationReport == true) goto L96;
    */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x03a4, code lost:

       if (r5.mFiraOpenSessionParams.mHasAngleOfArrivalFigureOfMeritReport == true) goto L96;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getDefaultValue(java.lang.String r6) {
        /*
            Method dump skipped, instructions count: 1257
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.uwb.labs.view.uwbtest.UwbFiraTestFragmentController.getDefaultValue(java.lang.String):java.lang.Object");
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    public final void setValue(Object obj, String str) {
        if (obj.toString().equals("default value is null")) {
            return;
        }
        if ("device_address".equals(str)) {
            String obj2 = obj.toString();
            if (obj2.contains("0x")) {
                obj2 = obj2.substring(2);
            }
            try {
                this.mDeviceAddress = UwbAddress.fromBytes(UwbUtil.toByteArray(obj2));
                return;
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        if ("dest_address_list".equals(str)) {
            String[] split = obj.toString().split(",");
            try {
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < split.length; i++) {
                    if (split[i].contains("0x")) {
                        split[i] = split[i].substring(2);
                    }
                    arrayList.add(UwbAddress.fromBytes(UwbUtil.toByteArray(split[i])));
                }
                this.mDestAddressList = arrayList;
                return;
            } catch (Exception e2) {
                e2.printStackTrace();
                return;
            }
        }
        if ("initiation_time_ms".equals(str)) {
            this.mInitiationTime = Long.parseLong(obj.toString());
        } else if ("absolute_initiation_time_us".equals(str)) {
            this.mAbsoluteInitiationTime = Long.parseLong(obj.toString());
        } else if ("cap_size_range".equals(str)) {
            Log.i("UwbFiraTestFragmentController", "*** cap  size = " + obj.toString());
            this.mCapSize = UwbUtil.toByteArray(obj.toString());
        } else if ("session_key".equals(str)) {
            Log.i("UwbFiraTestFragmentController", "*** session key = " + obj.toString());
            this.mSessionKey = UwbUtil.toByteArray(obj.toString());
        } else if ("subsession_key".equals(str)) {
            this.mSubSessionKey = UwbUtil.toByteArray(obj.toString());
        } else if ("vendor_id".equals(str)) {
            this.mVendorId = UwbUtil.toByteArray(obj.toString());
        } else if ("static_sts_iv".equals(str)) {
            this.mStaticStsIV = UwbUtil.toByteArray(obj.toString());
        } else if ("ranging_error_streak_timeout_ms".equals(str)) {
            this.mRangingErrorStreakTimeoutMs = Long.valueOf(Long.parseLong(obj.toString()));
        } else if ("ul_tdoa_device_id".equals(str)) {
            this.mUlTdoaDeviceId = UwbUtil.toByteArray(obj.toString());
        }
        try {
            int parseInt = Integer.parseInt(obj.toString());
            if ("session_id".equals(str)) {
                this.mSessionId = parseInt;
                return;
            }
            if ("device_type".equals(str)) {
                this.mDeviceType = parseInt;
                UwbFiraTestFragment uwbFiraTestFragment = this.mUwbUiEventReceiver;
                if (parseInt == 0) {
                    Preference findPreference = uwbFiraTestFragment.findPreference("device_role");
                    findPreference
                            .getOnPreferenceChangeListener()
                            .onPreferenceChange(findPreference, 0);
                    Preference findPreference2 =
                            uwbFiraTestFragment.findPreference("device_address");
                    findPreference2
                            .getOnPreferenceChangeListener()
                            .onPreferenceChange(findPreference2, "0x0001");
                    Preference findPreference3 =
                            uwbFiraTestFragment.findPreference("dest_address_list");
                    findPreference3
                            .getOnPreferenceChangeListener()
                            .onPreferenceChange(findPreference3, "0x0000");
                    return;
                }
                Preference findPreference4 = uwbFiraTestFragment.findPreference("device_role");
                if (findPreference4.getOnPreferenceChangeListener() != null) {
                    findPreference4
                            .getOnPreferenceChangeListener()
                            .onPreferenceChange(findPreference4, 1);
                }
                Preference findPreference5 = uwbFiraTestFragment.findPreference("device_address");
                if (findPreference5.getOnPreferenceChangeListener() != null) {
                    findPreference5
                            .getOnPreferenceChangeListener()
                            .onPreferenceChange(findPreference5, "0x0000");
                }
                Preference findPreference6 =
                        uwbFiraTestFragment.findPreference("dest_address_list");
                if (findPreference6.getOnPreferenceChangeListener() != null) {
                    findPreference6
                            .getOnPreferenceChangeListener()
                            .onPreferenceChange(findPreference6, "0x0001");
                    return;
                }
                return;
            }
            if ("device_role".equals(str)) {
                this.mDeviceRole = parseInt;
                return;
            }
            if ("session_type".equals(str)) {
                this.mSessionType = parseInt;
                return;
            }
            if ("channel_number".equals(str)) {
                this.mChannelNumber = parseInt;
                return;
            }
            if ("multi_node_mode".equals(str)) {
                this.mMultiNodeMode = parseInt;
                return;
            }
            if ("slot_duration_rstu".equals(str)) {
                this.mSlotDurationRstu = parseInt;
                return;
            }
            if ("ranging_interval_ms".equals(str)) {
                this.mRangingIntervalMs = parseInt;
                return;
            }
            if ("slots_per_ranging_round".equals(str)) {
                this.mSlotsPerRangingRound = parseInt;
                return;
            }
            if ("fcs_type".equals(str)) {
                this.mFcsType = parseInt;
                return;
            }
            if ("preamble_code_index".equals(str)) {
                this.mPreambleCodeIndex = parseInt;
                return;
            }
            if ("sfd_id".equals(str)) {
                this.mSfdId = parseInt;
                return;
            }
            if ("psdu_data_rate".equals(str)) {
                this.mPsduDataRate = parseInt;
                return;
            }
            if ("preamble_duration".equals(str)) {
                this.mPreambleDuration = parseInt;
                return;
            }
            if ("prf_mode".equals(str)) {
                this.mPrfMode = parseInt;
                return;
            }
            if ("scheduled_mode".equals(str)) {
                this.mScheduledMode = parseInt;
                return;
            }
            if ("mac_address_mode".equals(str)) {
                this.mMacAddressMode = parseInt;
                return;
            }
            if ("session_priority".equals(str)) {
                this.mSessionPriority = parseInt;
                return;
            }
            if ("hopping_mode".equals(str)) {
                this.mHoppingMode = parseInt;
                return;
            }
            if ("block_stride_length".equals(str)) {
                this.mBlockStrideLength = parseInt;
                return;
            }
            if ("in_band_termination_attempt_count".equals(str)) {
                this.mInBandTerminationAttemptCount = parseInt;
                return;
            }
            if ("ranging_round_usage".equals(str)) {
                this.mRangingRoundUsage = parseInt;
                return;
            }
            if ("max_ranging_round_retries".equals(str)) {
                this.mMaxRangingRoundRetries = parseInt;
                return;
            }
            if ("has_result_report_phase".equals(str)) {
                this.mHasRangingResultReportMessage = parseInt == 1;
                return;
            }
            if ("has_control_message".equals(str)) {
                this.mHasControlMessage = parseInt == 1;
                return;
            }
            if ("has_ranging_control_phase".equals(str)) {
                this.mHasRangingControlPhase = parseInt == 1;
                return;
            }
            if ("measurement_report_type".equals(str)) {
                this.mMeasurementReportType = parseInt;
                return;
            }
            if ("measurement_report_phase".equals(str)) {
                return;
            }
            if ("rframe_config".equals(str)) {
                this.mRframeConfig = parseInt;
                return;
            }
            if ("sts_segment_count".equals(str)) {
                this.mStsSegmentCount = parseInt;
                return;
            }
            if ("sts_length".equals(str)) {
                this.mStsLength = parseInt;
                return;
            }
            if ("bprf_phr_data_rate".equals(str)) {
                this.mBprfPhrDataRate = parseInt;
                return;
            }
            if ("is_tx_adaptive_payload_power_enabled".equals(str)) {
                this.mIsTxAdaptivePayloadPowerEnabled = parseInt == 1;
                return;
            }
            if ("sts_config".equals(str)) {
                this.mStsConfig = parseInt;
                return;
            }
            if ("sub_session_id".equals(str)) {
                this.mSubSessionId = parseInt;
                return;
            }
            if ("aoa_type".equals(str)) {
                this.mAoaType = parseInt;
                return;
            }
            if ("is_rssi_reporting_enabled".equals(str)) {
                this.mIsRssiReportingEnabled = parseInt == 1;
                return;
            }
            if ("is_diagnostics_enabled".equals(str)) {
                this.mIsDiagnosticsEnabled = parseInt == 1;
                return;
            }
            if ("antenna_mode".equals(str)) {
                return;
            }
            if ("is_key_rotation_enabled".equals(str)) {
                this.mIsKeyRotationEnabled = parseInt == 1;
                return;
            }
            if ("key_rotation_rate".equals(str)) {
                this.mKeyRotationRate = parseInt;
                return;
            }
            if ("aoa_result_request".equals(str)) {
                this.mAoaResultRequest = parseInt;
                return;
            }
            if ("range_data_ntf_config".equals(str)) {
                this.mRangeDataNtfConfig = parseInt;
                return;
            }
            if ("range_data_ntf_proximity_near".equals(str)) {
                this.mRangeDataNtfProximityNear = parseInt;
                return;
            }
            if ("range_data_ntf_proximity_far".equals(str)) {
                this.mRangeDataNtfProximityFar = parseInt;
                return;
            }
            if ("has_time_of_flight_report".equals(str)) {
                this.mHasTimeOfFlightReport = parseInt == 1;
                return;
            }
            if ("has_angle_of_arrival_azimuth_report".equals(str)) {
                this.mHasAngleOfArrivalAzimuthReport = parseInt == 1;
                return;
            }
            if ("has_angle_of_arrival_elevation_report".equals(str)) {
                this.mHasAngleOfArrivalElevationReport = parseInt == 1;
                return;
            }
            if ("has_angle_of_arrival_figure_of_merit_report".equals(str)) {
                this.mHasAngleOfArrivalFigureOfMeritReport = parseInt == 1;
                return;
            }
            if ("num_of_msrmt_focus_on_range".equals(str)) {
                this.mNumOfMsrmtFocusOnRange = parseInt;
                return;
            }
            if ("num_of_msrmt_focus_on_aoa_azimuth".equals(str)) {
                this.mNumOfMsrmtFocusOnAoaAzimuth = parseInt;
                return;
            }
            if ("num_of_msrmt_focus_on_aoa_elevation".equals(str)) {
                this.mNumOfMsrmtFocusOnAoaElevation = parseInt;
                return;
            }
            if ("link_layer_mode".equals(str)) {
                this.mLinkLayerMode = parseInt;
                return;
            }
            if ("data_repetition_count".equals(str) || "ranging_time_struct".equals(str)) {
                return;
            }
            if ("min_frames_per_rr".equals(str)) {
                this.mMinFramesPerRr = parseInt;
                return;
            }
            if ("mtu_size".equals(str)) {
                this.mMtuSize = parseInt;
                return;
            }
            if ("inter_frame_interval".equals(str)) {
                this.mInterFrameInterval = parseInt;
                return;
            }
            if ("dl_tdoa_block_striding".equals(str)) {
                return;
            }
            if ("ul_tdoa_tx_interval".equals(str)) {
                this.mUlTdoaTxIntervalMs = parseInt;
                return;
            }
            if ("ul_tdoa_random_window".equals(str)) {
                this.mUlTdoaRandomWindowMs = parseInt;
                return;
            }
            if ("ul_tdoa_device_id_type".equals(str)) {
                this.mUlTdoaDeviceIdType = parseInt;
                return;
            }
            if ("ul_tdoa_tx_timestamp_type".equals(str)) {
                this.mUlTdoaTxTimestampType = parseInt;
                return;
            }
            if ("filter_type".equals(str)) {
                this.mFilterType = parseInt;
                return;
            }
            if ("max_number_of_measurements".equals(str)) {
                this.mMaxNumberOfMeasurements = parseInt;
            } else {
                if ("session_data_transfer_status_ntf_config".equals(str)
                        || "reference_time_base".equals(str)
                        || "reference_session_handle".equals(str)) {
                    return;
                }
                "session_offset_in_ms".equals(str);
            }
        } catch (NumberFormatException e3) {
            e3.printStackTrace();
        }
    }

    public final void updateBottomMenu() {
        ((FragmentActivity) this.mContext)
                .runOnUiThread(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.uwb.labs.view.uwbtest.UwbFiraTestFragmentController.4
                            @Override // java.lang.Runnable
                            public final void run() {
                                TooltipPopup$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("state = "),
                                        UwbFiraTestFragmentController.mController.mRanging.mState,
                                        "UwbFiraTestFragmentController");
                                int i = UwbFiraTestFragmentController.mController.mRanging.mState;
                                if (i == 0 || i == 3) {
                                    UwbFiraTestFragmentController.this.mOpenMenu.setEnabled(true);
                                    UwbFiraTestFragmentController.this.mCloseMenu.setEnabled(true);
                                    UwbFiraTestFragmentController.this.mStartMenu.setEnabled(false);
                                    UwbFiraTestFragmentController.this.mStopMenu.setEnabled(false);
                                    return;
                                }
                                if (i == 1) {
                                    UwbFiraTestFragmentController.this.mStartMenu.setEnabled(true);
                                    UwbFiraTestFragmentController.this.mOpenMenu.setEnabled(false);
                                    UwbFiraTestFragmentController.this.mStopMenu.setEnabled(false);
                                    UwbFiraTestFragmentController.this.mCloseMenu.setEnabled(true);
                                    return;
                                }
                                if (i == 2) {
                                    UwbFiraTestFragmentController.this.mStartMenu.setEnabled(false);
                                    UwbFiraTestFragmentController.this.mOpenMenu.setEnabled(false);
                                    UwbFiraTestFragmentController.this.mStopMenu.setEnabled(true);
                                    UwbFiraTestFragmentController.this.mCloseMenu.setEnabled(true);
                                }
                            }
                        });
    }
}
