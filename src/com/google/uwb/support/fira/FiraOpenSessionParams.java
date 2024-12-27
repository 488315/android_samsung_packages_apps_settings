package com.google.uwb.support.fira;

import android.os.Parcel;
import android.os.PersistableBundle;
import android.uwb.UwbAddress;

import com.google.common.base.Preconditions;
import com.google.uwb.support.base.RequiredParam;

import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class FiraOpenSessionParams {
    public static final FiraProtocolVersion PROTOCOL_VERSION_1_1 = new FiraProtocolVersion();
    public final long mAbsoluteInitiationTime;
    public final int mAoaResultRequest;
    public final int mAoaType;
    public final int mBlockStrideLength;
    public final int mBprfPhrDataRate;
    public final byte[] mCapSize;
    public final int mChannelNumber;
    public final List mDestAddressList;
    public final UwbAddress mDeviceAddress;
    public final int mDeviceRole;
    public final int mDeviceType;
    public final int mFcsType;
    public final int mFilterType;
    public final boolean mHasAngleOfArrivalAzimuthReport;
    public final boolean mHasAngleOfArrivalElevationReport;
    public final boolean mHasAngleOfArrivalFigureOfMeritReport;
    public final boolean mHasControlMessage;
    public final boolean mHasRangingControlPhase;
    public final boolean mHasRangingResultReportMessage;
    public final boolean mHasTimeOfFlightReport;
    public final int mHoppingMode;
    public final int mInBandTerminationAttemptCount;
    public final long mInitiationTime;
    public final int mInterFrameInterval;
    public final boolean mIsDiagnosticsEnabled;
    public final boolean mIsKeyRotationEnabled;
    public final boolean mIsRssiReportingEnabled;
    public final boolean mIsTxAdaptivePayloadPowerEnabled;
    public final int mKeyRotationRate;
    public final int mLinkLayerMode;
    public final int mMacAddressMode;
    public final int mMaxNumberOfMeasurements;
    public final int mMaxRangingRoundRetries;
    public final int mMeasurementReportType;
    public final int mMinFramesPerRr;
    public final int mMtuSize;
    public final int mMultiNodeMode;
    public final int mNumOfMsrmtFocusOnAoaAzimuth;
    public final int mNumOfMsrmtFocusOnAoaElevation;
    public final int mNumOfMsrmtFocusOnRange;
    public final int mPreambleCodeIndex;
    public final int mPreambleDuration;
    public final int mPrfMode;
    public final FiraProtocolVersion mProtocolVersion;
    public final int mPsduDataRate;
    public final int mRangeDataNtfConfig;
    public final int mRangeDataNtfProximityFar;
    public final int mRangeDataNtfProximityNear;
    public final Long mRangingErrorStreakTimeoutMs;
    public final int mRangingIntervalMs;
    public final int mRangingRoundUsage;
    public final int mRframeConfig;
    public final int mScheduledMode;
    public final int mSessionId;
    public final byte[] mSessionKey;
    public final int mSessionPriority;
    public final int mSessionType;
    public final int mSfdId;
    public final int mSlotDurationRstu;
    public final int mSlotsPerRangingRound;
    public final byte[] mStaticStsIV;
    public final int mStsConfig;
    public final int mStsLength;
    public final int mStsSegmentCount;
    public final int mSubSessionId;
    public final byte[] mSubSessionKey;
    public final byte[] mUlTdoaDeviceId;
    public final int mUlTdoaDeviceIdType;
    public final int mUlTdoaRandomWindowMs;
    public final int mUlTdoaTxIntervalMs;
    public final int mUlTdoaTxTimestampType;
    public final byte[] mVendorId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public byte[] mUlTdoaDeviceId;
        public final RequiredParam mProtocolVersion = new RequiredParam();
        public final RequiredParam mSessionId = new RequiredParam();
        public int mSessionType = 0;
        public final RequiredParam mDeviceType = new RequiredParam();
        public final RequiredParam mDeviceRole = new RequiredParam();
        public int mRangingRoundUsage = 2;
        public final RequiredParam mMultiNodeMode = new RequiredParam();
        public UwbAddress mDeviceAddress = null;
        public List mDestAddressList = null;
        public long mInitiationTime = 0;
        public long mAbsoluteInitiationTime = 0;
        public int mSlotDurationRstu = 2400;
        public int mSlotsPerRangingRound = 25;
        public int mRangingIntervalMs = 200;
        public int mBlockStrideLength = 0;
        public int mHoppingMode = 0;
        public int mMaxRangingRoundRetries = 0;
        public int mSessionPriority = 50;
        public int mMacAddressMode = 0;
        public boolean mHasRangingResultReportMessage = true;
        public boolean mHasControlMessage = true;
        public boolean mHasRangingControlPhase = false;
        public int mMeasurementReportType = 0;
        public int mInBandTerminationAttemptCount = 1;
        public int mChannelNumber = 9;
        public int mPreambleCodeIndex = 10;
        public int mRframeConfig = 3;
        public int mPrfMode = 0;
        public byte[] mCapSize = {24, 5};
        public int mScheduledMode = 1;
        public int mPreambleDuration = 1;
        public int mSfdId = 2;
        public int mStsSegmentCount = 1;
        public int mStsLength = 1;
        public byte[] mSessionKey = null;
        public byte[] mSubsessionKey = null;
        public int mPsduDataRate = 0;
        public int mBprfPhrDataRate = 0;
        public int mFcsType = 0;
        public boolean mIsTxAdaptivePayloadPowerEnabled = false;
        public int mStsConfig = 0;
        public final RequiredParam mSubSessionId = new RequiredParam();
        public byte[] mVendorId = null;
        public byte[] mStaticStsIV = null;
        public boolean mIsRssiReportingEnabled = false;
        public boolean mIsDiagnosticsEnabled = false;
        public boolean mIsKeyRotationEnabled = false;
        public int mKeyRotationRate = 0;
        public int mAoaResultRequest = 1;
        public int mRangeDataNtfConfig = 1;
        public int mRangeDataNtfProximityNear = 0;
        public int mRangeDataNtfProximityFar = 20000;
        public boolean mHasTimeOfFlightReport = true;
        public boolean mHasAngleOfArrivalAzimuthReport = false;
        public boolean mHasAngleOfArrivalElevationReport = false;
        public boolean mHasAngleOfArrivalFigureOfMeritReport = false;
        public int mAoaType = 0;
        public int mNumOfMsrmtFocusOnRange = 0;
        public int mNumOfMsrmtFocusOnAoaAzimuth = 0;
        public int mNumOfMsrmtFocusOnAoaElevation = 0;
        public long mRangingErrorStreakTimeoutMs = 10000;
        public int mLinkLayerMode = 0;
        public int mMinFramesPerRr = 1;
        public int mMtuSize = 1048;
        public int mInterFrameInterval = 1;
        public int mUlTdoaTxIntervalMs = 2000;
        public int mUlTdoaRandomWindowMs = 0;
        public int mUlTdoaDeviceIdType = 0;
        public int mUlTdoaTxTimestampType = 0;
        public int mFilterType = 1;
        public int mMaxNumberOfMeasurements = 0;

        public final FiraOpenSessionParams build() {
            byte[] bArr;
            int i;
            int i2 = this.mMacAddressMode;
            Preconditions.checkArgument(i2 == 0 || i2 == 2);
            int i3 = this.mMacAddressMode == 2 ? 8 : 2;
            UwbAddress uwbAddress = this.mDeviceAddress;
            Preconditions.checkArgument(uwbAddress != null && uwbAddress.size() == i3);
            if (this.mScheduledMode == 1
                    && (((i = this.mRangingRoundUsage) == 1 || i == 2 || i == 3 || i == 4)
                            && this.mStsConfig != 4)) {
                this.mDestAddressList.getClass();
                for (UwbAddress uwbAddress2 : this.mDestAddressList) {
                    Preconditions.checkArgument(uwbAddress2 != null && uwbAddress2.size() == i3);
                }
            }
            if (this.mStsConfig == 0) {
                byte[] bArr2 = this.mVendorId;
                Preconditions.checkArgument(bArr2 != null && bArr2.length == 2);
                byte[] bArr3 = this.mStaticStsIV;
                Preconditions.checkArgument(bArr3 != null && bArr3.length == 6);
            }
            int i4 = this.mStsConfig;
            RequiredParam requiredParam = this.mDeviceType;
            RequiredParam requiredParam2 = this.mSubSessionId;
            if ((i4 == 2 || i4 == 4) && ((Integer) requiredParam.get()).intValue() == 0) {
                Preconditions.checkArgument(requiredParam2.mIsSet);
            } else {
                requiredParam2.set(0);
            }
            if (this.mStsConfig == 3 && (bArr = this.mSessionKey) != null) {
                Preconditions.checkArgument(bArr.length == 16 || bArr.length == 32);
            }
            if (this.mStsConfig == 4
                    && ((Integer) requiredParam.get()).intValue() == 0
                    && this.mSubsessionKey != null) {
                byte[] bArr4 = this.mSessionKey;
                Preconditions.checkArgument(
                        bArr4 != null && (bArr4.length == 16 || bArr4.length == 32));
                byte[] bArr5 = this.mSubsessionKey;
                Preconditions.checkArgument(bArr5.length == 16 || bArr5.length == 32);
            }
            if (this.mAoaResultRequest != 240) {
                Preconditions.checkArgument(this.mNumOfMsrmtFocusOnRange == 0);
                Preconditions.checkArgument(this.mNumOfMsrmtFocusOnAoaAzimuth == 0);
                Preconditions.checkArgument(this.mNumOfMsrmtFocusOnAoaElevation == 0);
            } else {
                Preconditions.checkArgument(
                        this.mNumOfMsrmtFocusOnRange > 0
                                || this.mNumOfMsrmtFocusOnAoaAzimuth > 0
                                || this.mNumOfMsrmtFocusOnAoaElevation > 0);
            }
            int i5 = this.mRangeDataNtfConfig;
            if (i5 == 0) {
                Preconditions.checkArgument(this.mRangeDataNtfProximityNear == 0);
                Preconditions.checkArgument(this.mRangeDataNtfProximityFar == 20000);
            } else if (i5 == 2 || i5 == 5) {
                Preconditions.checkArgument(
                        (this.mRangeDataNtfProximityNear == 0
                                        && this.mRangeDataNtfProximityFar == 20000)
                                ? false
                                : true);
            } else if (i5 == 3 || i5 == 6) {
                Preconditions.checkArgument(this.mRangeDataNtfProximityNear == 0);
                Preconditions.checkArgument(this.mRangeDataNtfProximityFar == 20000);
                Preconditions.checkArgument(false);
            } else if (i5 == 4 || i5 == 7) {
                Preconditions.checkArgument(
                        (this.mRangeDataNtfProximityNear == 0
                                        && this.mRangeDataNtfProximityFar == 20000)
                                ? false
                                : true);
            }
            RequiredParam requiredParam3 = this.mDeviceRole;
            int intValue = ((Integer) requiredParam3.get()).intValue();
            RequiredParam requiredParam4 = this.mMultiNodeMode;
            if (intValue == 8) {
                Preconditions.checkArgument(
                        this.mStsConfig == 0
                                && ((Integer) requiredParam4.get()).intValue() == 1
                                && this.mRframeConfig == 1);
            }
            return new FiraOpenSessionParams(
                    (FiraProtocolVersion) this.mProtocolVersion.get(),
                    ((Integer) this.mSessionId.get()).intValue(),
                    this.mSessionType,
                    ((Integer) requiredParam.get()).intValue(),
                    ((Integer) requiredParam3.get()).intValue(),
                    this.mRangingRoundUsage,
                    ((Integer) requiredParam4.get()).intValue(),
                    this.mDeviceAddress,
                    this.mDestAddressList,
                    this.mInitiationTime,
                    this.mAbsoluteInitiationTime,
                    this.mSlotDurationRstu,
                    this.mSlotsPerRangingRound,
                    this.mRangingIntervalMs,
                    this.mBlockStrideLength,
                    this.mHoppingMode,
                    this.mMaxRangingRoundRetries,
                    this.mSessionPriority,
                    this.mMacAddressMode,
                    this.mHasRangingResultReportMessage,
                    this.mHasControlMessage,
                    this.mHasRangingControlPhase,
                    this.mMeasurementReportType,
                    this.mInBandTerminationAttemptCount,
                    this.mChannelNumber,
                    this.mPreambleCodeIndex,
                    this.mRframeConfig,
                    this.mPrfMode,
                    this.mCapSize,
                    this.mScheduledMode,
                    this.mPreambleDuration,
                    this.mSfdId,
                    this.mStsSegmentCount,
                    this.mStsLength,
                    this.mSessionKey,
                    this.mSubsessionKey,
                    this.mPsduDataRate,
                    this.mBprfPhrDataRate,
                    this.mFcsType,
                    this.mIsTxAdaptivePayloadPowerEnabled,
                    this.mStsConfig,
                    ((Integer) requiredParam2.get()).intValue(),
                    this.mVendorId,
                    this.mStaticStsIV,
                    this.mIsRssiReportingEnabled,
                    this.mIsDiagnosticsEnabled,
                    this.mIsKeyRotationEnabled,
                    this.mKeyRotationRate,
                    this.mAoaResultRequest,
                    this.mRangeDataNtfConfig,
                    this.mRangeDataNtfProximityNear,
                    this.mRangeDataNtfProximityFar,
                    this.mHasTimeOfFlightReport,
                    this.mHasAngleOfArrivalAzimuthReport,
                    this.mHasAngleOfArrivalElevationReport,
                    this.mHasAngleOfArrivalFigureOfMeritReport,
                    this.mAoaType,
                    this.mNumOfMsrmtFocusOnRange,
                    this.mNumOfMsrmtFocusOnAoaAzimuth,
                    this.mNumOfMsrmtFocusOnAoaElevation,
                    Long.valueOf(this.mRangingErrorStreakTimeoutMs),
                    this.mLinkLayerMode,
                    this.mMinFramesPerRr,
                    this.mMtuSize,
                    this.mInterFrameInterval,
                    this.mUlTdoaTxIntervalMs,
                    this.mUlTdoaRandomWindowMs,
                    this.mUlTdoaDeviceIdType,
                    this.mUlTdoaDeviceId,
                    this.mUlTdoaTxTimestampType,
                    this.mFilterType,
                    this.mMaxNumberOfMeasurements);
        }
    }

    public FiraOpenSessionParams(
            FiraProtocolVersion firaProtocolVersion,
            int i,
            int i2,
            int i3,
            int i4,
            int i5,
            int i6,
            UwbAddress uwbAddress,
            List list,
            long j,
            long j2,
            int i7,
            int i8,
            int i9,
            int i10,
            int i11,
            int i12,
            int i13,
            int i14,
            boolean z,
            boolean z2,
            boolean z3,
            int i15,
            int i16,
            int i17,
            int i18,
            int i19,
            int i20,
            byte[] bArr,
            int i21,
            int i22,
            int i23,
            int i24,
            int i25,
            byte[] bArr2,
            byte[] bArr3,
            int i26,
            int i27,
            int i28,
            boolean z4,
            int i29,
            int i30,
            byte[] bArr4,
            byte[] bArr5,
            boolean z5,
            boolean z6,
            boolean z7,
            int i31,
            int i32,
            int i33,
            int i34,
            int i35,
            boolean z8,
            boolean z9,
            boolean z10,
            boolean z11,
            int i36,
            int i37,
            int i38,
            int i39,
            Long l,
            int i40,
            int i41,
            int i42,
            int i43,
            int i44,
            int i45,
            int i46,
            byte[] bArr6,
            int i47,
            int i48,
            int i49) {
        this.mProtocolVersion = firaProtocolVersion;
        this.mSessionId = i;
        this.mSessionType = i2;
        this.mDeviceType = i3;
        this.mDeviceRole = i4;
        this.mRangingRoundUsage = i5;
        this.mMultiNodeMode = i6;
        this.mDeviceAddress = uwbAddress;
        this.mDestAddressList = list;
        this.mInitiationTime = j;
        this.mAbsoluteInitiationTime = j2;
        this.mSlotDurationRstu = i7;
        this.mSlotsPerRangingRound = i8;
        this.mRangingIntervalMs = i9;
        this.mBlockStrideLength = i10;
        this.mHoppingMode = i11;
        this.mMaxRangingRoundRetries = i12;
        this.mSessionPriority = i13;
        this.mMacAddressMode = i14;
        this.mHasRangingResultReportMessage = z;
        this.mHasControlMessage = z2;
        this.mHasRangingControlPhase = z3;
        this.mMeasurementReportType = i15;
        this.mInBandTerminationAttemptCount = i16;
        this.mChannelNumber = i17;
        this.mPreambleCodeIndex = i18;
        this.mRframeConfig = i19;
        this.mPrfMode = i20;
        this.mCapSize = bArr;
        this.mScheduledMode = i21;
        this.mPreambleDuration = i22;
        this.mSfdId = i23;
        this.mStsSegmentCount = i24;
        this.mStsLength = i25;
        this.mSessionKey = bArr2;
        this.mSubSessionKey = bArr3;
        this.mPsduDataRate = i26;
        this.mBprfPhrDataRate = i27;
        this.mFcsType = i28;
        this.mIsTxAdaptivePayloadPowerEnabled = z4;
        this.mStsConfig = i29;
        this.mSubSessionId = i30;
        this.mVendorId = bArr4;
        this.mStaticStsIV = bArr5;
        this.mIsRssiReportingEnabled = z5;
        this.mIsDiagnosticsEnabled = z6;
        this.mIsKeyRotationEnabled = z7;
        this.mKeyRotationRate = i31;
        this.mAoaResultRequest = i32;
        this.mRangeDataNtfConfig = i33;
        this.mRangeDataNtfProximityNear = i34;
        this.mRangeDataNtfProximityFar = i35;
        this.mHasTimeOfFlightReport = z8;
        this.mHasAngleOfArrivalAzimuthReport = z9;
        this.mHasAngleOfArrivalElevationReport = z10;
        this.mHasAngleOfArrivalFigureOfMeritReport = z11;
        this.mAoaType = i36;
        this.mNumOfMsrmtFocusOnRange = i37;
        this.mNumOfMsrmtFocusOnAoaAzimuth = i38;
        this.mNumOfMsrmtFocusOnAoaElevation = i39;
        this.mRangingErrorStreakTimeoutMs = l;
        this.mLinkLayerMode = i40;
        this.mMinFramesPerRr = i41;
        this.mMtuSize = i42;
        this.mInterFrameInterval = i43;
        this.mUlTdoaTxIntervalMs = i44;
        this.mUlTdoaRandomWindowMs = i45;
        this.mUlTdoaDeviceIdType = i46;
        this.mUlTdoaDeviceId = bArr6;
        this.mUlTdoaTxTimestampType = i47;
        this.mFilterType = i48;
        this.mMaxNumberOfMeasurements = i49;
    }

    public static int[] byteArrayToIntArray(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        int length = bArr.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = bArr[i];
        }
        return iArr;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof FiraOpenSessionParams)
                && Arrays.equals(toBytes(), ((FiraOpenSessionParams) obj).toBytes());
    }

    public final int hashCode() {
        return Arrays.hashCode(toBytes());
    }

    public final PersistableBundle toBundle() {
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putInt("bundle_version", 1);
        persistableBundle.putString("protocol_name", "fira");
        this.mProtocolVersion.getClass();
        persistableBundle.putString("protocol_version", "1.1");
        persistableBundle.putInt("session_id", this.mSessionId);
        persistableBundle.putInt("session_type", this.mSessionType);
        int i = this.mDeviceType;
        persistableBundle.putInt("device_type", i);
        int i2 = this.mDeviceRole;
        persistableBundle.putInt("device_role", i2);
        persistableBundle.putInt("ranging_round_usage", this.mRangingRoundUsage);
        persistableBundle.putInt("multi_node_mode", this.mMultiNodeMode);
        persistableBundle.putLong(
                "device_address",
                ByteBuffer.wrap(Arrays.copyOf(this.mDeviceAddress.toBytes(), 8)).getLong());
        int i3 = this.mScheduledMode;
        if (i2 == 8 || i3 == 0) {
            persistableBundle.putInt("dltdoa_block_striding", 0);
        } else {
            long[] jArr = new long[this.mDestAddressList.size()];
            Iterator it = this.mDestAddressList.iterator();
            int i4 = 0;
            while (it.hasNext()) {
                jArr[i4] =
                        ByteBuffer.wrap(Arrays.copyOf(((UwbAddress) it.next()).toBytes(), 8))
                                .getLong();
                i4++;
            }
            persistableBundle.putLongArray("dest_address_list", jArr);
        }
        persistableBundle.putLong("initiation_time_ms", this.mInitiationTime);
        persistableBundle.putLong("absolute_initiation_time_us", this.mAbsoluteInitiationTime);
        persistableBundle.putInt("slot_duration_rstu", this.mSlotDurationRstu);
        persistableBundle.putInt("slots_per_ranging_round", this.mSlotsPerRangingRound);
        persistableBundle.putInt("ranging_interval_ms", this.mRangingIntervalMs);
        persistableBundle.putInt("block_stride_length", this.mBlockStrideLength);
        persistableBundle.putInt("hopping_mode", this.mHoppingMode);
        persistableBundle.putInt("max_ranging_round_retries", this.mMaxRangingRoundRetries);
        persistableBundle.putInt("session_priority", this.mSessionPriority);
        persistableBundle.putInt("mac_address_mode", this.mMacAddressMode);
        persistableBundle.putBoolean(
                "has_result_report_phase", this.mHasRangingResultReportMessage);
        persistableBundle.putBoolean("has_control_message", this.mHasControlMessage);
        persistableBundle.putBoolean("has_ranging_control_phase", this.mHasRangingControlPhase);
        persistableBundle.putInt("measurement_report_type", this.mMeasurementReportType);
        persistableBundle.putInt("measurement_report_phase", 0);
        persistableBundle.putInt(
                "in_band_termination_attempt_count", this.mInBandTerminationAttemptCount);
        persistableBundle.putInt("channel_number", this.mChannelNumber);
        persistableBundle.putInt("preamble_code_index", this.mPreambleCodeIndex);
        persistableBundle.putInt("rframe_config", this.mRframeConfig);
        persistableBundle.putInt("prf_mode", this.mPrfMode);
        persistableBundle.putInt("scheduled_mode", i3);
        if (i3 == 0) {
            persistableBundle.putIntArray("cap_size_range", byteArrayToIntArray(this.mCapSize));
        }
        persistableBundle.putInt("preamble_duration", this.mPreambleDuration);
        persistableBundle.putInt("sfd_id", this.mSfdId);
        persistableBundle.putInt("sts_segment_count", this.mStsSegmentCount);
        persistableBundle.putInt("sts_length", this.mStsLength);
        persistableBundle.putInt("psdu_data_rate", this.mPsduDataRate);
        persistableBundle.putInt("bprf_phr_data_rate", this.mBprfPhrDataRate);
        persistableBundle.putInt("fcs_type", this.mFcsType);
        persistableBundle.putBoolean(
                "is_tx_adaptive_payload_power_enabled", this.mIsTxAdaptivePayloadPowerEnabled);
        int i5 = this.mStsConfig;
        persistableBundle.putInt("sts_config", i5);
        if (i5 == 2 || i5 == 4) {
            persistableBundle.putInt("sub_session_id", this.mSubSessionId);
        }
        byte[] bArr = this.mSessionKey;
        if (bArr != null) {
            persistableBundle.putIntArray("session_key", byteArrayToIntArray(bArr));
        }
        byte[] bArr2 = this.mSubSessionKey;
        if (bArr2 != null) {
            persistableBundle.putIntArray("subsession_key", byteArrayToIntArray(bArr2));
        }
        persistableBundle.putIntArray("vendor_id", byteArrayToIntArray(this.mVendorId));
        persistableBundle.putIntArray("static_sts_iv", byteArrayToIntArray(this.mStaticStsIV));
        persistableBundle.putBoolean("is_rssi_reporting_enabled", this.mIsRssiReportingEnabled);
        persistableBundle.putBoolean("is_diagnostics_enabled", this.mIsDiagnosticsEnabled);
        persistableBundle.putInt("diagrams_frame_reports_fields_flags", 0);
        persistableBundle.putInt("antenna_mode", 0);
        persistableBundle.putBoolean("is_key_rotation_enabled", this.mIsKeyRotationEnabled);
        persistableBundle.putInt("key_rotation_rate", this.mKeyRotationRate);
        persistableBundle.putInt("aoa_result_request", this.mAoaResultRequest);
        persistableBundle.putInt("range_data_ntf_config", this.mRangeDataNtfConfig);
        persistableBundle.putInt("range_data_ntf_proximity_near", this.mRangeDataNtfProximityNear);
        persistableBundle.putInt("range_data_ntf_proximity_far", this.mRangeDataNtfProximityFar);
        persistableBundle.putDouble("range_data_ntf_aoa_azimuth_lower", -3.141592653589793d);
        persistableBundle.putDouble("range_data_ntf_aoa_azimuth_upper", 3.141592653589793d);
        persistableBundle.putDouble("range_data_ntf_aoa_elevation_lower", -1.5707963267948966d);
        persistableBundle.putDouble("range_data_ntf_aoa_elevation_upper", 1.5707963267948966d);
        persistableBundle.putBoolean("has_time_of_flight_report", this.mHasTimeOfFlightReport);
        persistableBundle.putBoolean(
                "has_angle_of_arrival_azimuth_report", this.mHasAngleOfArrivalAzimuthReport);
        persistableBundle.putBoolean(
                "has_angle_of_arrival_elevation_report", this.mHasAngleOfArrivalElevationReport);
        persistableBundle.putBoolean(
                "has_angle_of_arrival_figure_of_merit_report",
                this.mHasAngleOfArrivalFigureOfMeritReport);
        persistableBundle.putInt("aoa_type", this.mAoaType);
        persistableBundle.putInt("num_of_msrmt_focus_on_range", this.mNumOfMsrmtFocusOnRange);
        persistableBundle.putInt(
                "num_of_msrmt_focus_on_aoa_azimuth", this.mNumOfMsrmtFocusOnAoaAzimuth);
        persistableBundle.putInt(
                "num_of_msrmt_focus_on_aoa_elevation", this.mNumOfMsrmtFocusOnAoaElevation);
        persistableBundle.putLong(
                "ranging_error_streak_timeout_ms", this.mRangingErrorStreakTimeoutMs.longValue());
        persistableBundle.putInt("link_layer_mode", this.mLinkLayerMode);
        persistableBundle.putInt("data_repetition_count", 0);
        persistableBundle.putInt("ranging_time_struct", 1);
        persistableBundle.putInt("min_frames_per_rr", this.mMinFramesPerRr);
        persistableBundle.putInt("mtu_size", this.mMtuSize);
        persistableBundle.putInt("inter_frame_interval", this.mInterFrameInterval);
        persistableBundle.putInt("ul_tdoa_tx_interval", this.mUlTdoaTxIntervalMs);
        persistableBundle.putInt("ul_tdoa_random_window", this.mUlTdoaRandomWindowMs);
        persistableBundle.putInt("ul_tdoa_device_id_type", this.mUlTdoaDeviceIdType);
        persistableBundle.putIntArray(
                "ul_tdoa_device_id", byteArrayToIntArray(this.mUlTdoaDeviceId));
        persistableBundle.putInt("ul_tdoa_tx_timestamp_type", this.mUlTdoaTxTimestampType);
        persistableBundle.putInt("filter_type", this.mFilterType);
        persistableBundle.putInt("max_number_of_measurements", this.mMaxNumberOfMeasurements);
        persistableBundle.putBoolean("session_data_transfer_status_ntf_config", false);
        if (i == 1) {
            persistableBundle.putInt("reference_time_base", 0);
            persistableBundle.putInt("reference_session_handle", 0);
            persistableBundle.putInt("session_offset_in_micro_seconds", 0);
        }
        persistableBundle.putInt("application_data_endpoint", 0);
        return persistableBundle;
    }

    public final byte[] toBytes() {
        Parcel obtain = Parcel.obtain();
        toBundle().writeToParcel(obtain, 0);
        byte[] marshall = obtain.marshall();
        obtain.recycle();
        return marshall;
    }
}
