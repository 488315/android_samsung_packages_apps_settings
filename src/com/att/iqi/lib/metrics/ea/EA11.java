package com.att.iqi.lib.metrics.ea;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Keep;

import com.att.iqi.lib.Metric;
import com.samsung.android.knox.net.apn.ApnSettings;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class EA11 extends Metric {
    public static final String KEY_ALERT_REMINDER_INTERVAL = "alert_reminder_interval";
    public final byte[] dwFlags;
    public int mBooleanIndex;
    public int mBooleanShift;
    public String szInterval;
    public static final Metric.ID ID = new Metric.ID("EA11");
    public static final byte[] ALL_SETTINGS_UNKNOWN = {-86, -86, -86, -86};
    public static final String KEY_ENABLE_ALERTS_MASTER_TOGGLE = "enable_alerts_master_toggle";
    public static final String KEY_ENABLE_CMAS_PRESIDENTIAL_ALERTS =
            "enable_cmas_presidential_alerts";
    public static final String KEY_ENABLE_CMAS_EXTREME_THREAT_ALERTS =
            "enable_cmas_extreme_threat_alerts";
    public static final String KEY_ENABLE_CMAS_SEVERE_THREAT_ALERTS =
            "enable_cmas_severe_threat_alerts";
    public static final String KEY_ENABLE_CMAS_AMBER_ALERTS = "enable_cmas_amber_alerts";
    public static final String KEY_ENABLE_PUBLIC_SAFETY_MESSAGES = "enable_public_safety_messages";
    public static final String KEY_ENABLE_EMERGENCY_ALERTS = "enable_emergency_alerts";
    public static final String KEY_ENABLE_TEST_ALERTS = "enable_test_alerts";
    public static final String KEY_ENABLE_EXERCISE_ALERTS = "enable_exercise_alerts";
    public static final String KEY_OPERATOR_DEFINED_ALERTS = "enable_operator_defined_alerts";
    public static final String KEY_ENABLE_STATE_LOCAL_TEST_ALERTS =
            "enable_state_local_test_alerts";
    public static final String KEY_ENABLE_PUBLIC_SAFETY_MESSAGES_FULL_SCREEN =
            "enable_public_safety_messages_full_screen";
    public static final String KEY_ENABLE_ALERT_VIBRATE = "enable_alert_vibrate";
    public static final String KEY_ENABLE_ALERT_SPEECH = "enable_alert_speech";
    public static final String KEY_OVERRIDE_DND = "override_dnd";
    public static final String KEY_RECEIVE_CMAS_IN_SECOND_LANGUAGE =
            "receive_cmas_in_second_language";
    public static final String[] sPreferenceKeys = {
        KEY_ENABLE_ALERTS_MASTER_TOGGLE,
        KEY_ENABLE_CMAS_PRESIDENTIAL_ALERTS,
        KEY_ENABLE_CMAS_EXTREME_THREAT_ALERTS,
        KEY_ENABLE_CMAS_SEVERE_THREAT_ALERTS,
        KEY_ENABLE_CMAS_AMBER_ALERTS,
        KEY_ENABLE_PUBLIC_SAFETY_MESSAGES,
        KEY_ENABLE_EMERGENCY_ALERTS,
        KEY_ENABLE_TEST_ALERTS,
        KEY_ENABLE_EXERCISE_ALERTS,
        KEY_OPERATOR_DEFINED_ALERTS,
        KEY_ENABLE_STATE_LOCAL_TEST_ALERTS,
        KEY_ENABLE_PUBLIC_SAFETY_MESSAGES_FULL_SCREEN,
        KEY_ENABLE_ALERT_VIBRATE,
        KEY_ENABLE_ALERT_SPEECH,
        KEY_OVERRIDE_DND,
        KEY_RECEIVE_CMAS_IN_SECOND_LANGUAGE
    };
    public static final Parcelable.Creator<EA11> CREATOR = new AnonymousClass1();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.att.iqi.lib.metrics.ea.EA11$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<EA11> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EA11 createFromParcel(Parcel parcel) {
            return new EA11(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public EA11[] newArray(int i) {
            return new EA11[i];
        }
    }

    public EA11() {
        byte[] bArr = ALL_SETTINGS_UNKNOWN;
        byte[] bArr2 = new byte[bArr.length];
        this.dwFlags = bArr2;
        this.szInterval = ApnSettings.MVNO_NONE;
        this.mBooleanIndex = 0;
        this.mBooleanShift = 6;
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
    }

    public String[] getPreferenceKeys() {
        this.mBooleanIndex = 0;
        this.mBooleanShift = 6;
        byte[] bArr = ALL_SETTINGS_UNKNOWN;
        System.arraycopy(bArr, 0, this.dwFlags, 0, bArr.length);
        return sPreferenceKeys;
    }

    @Override // com.att.iqi.lib.Metric
    public int serialize(ByteBuffer byteBuffer) throws BufferOverflowException {
        byteBuffer.put(this.dwFlags);
        stringOut(byteBuffer, this.szInterval);
        return byteBuffer.position();
    }

    public EA11 setBoolean(boolean z) throws IllegalStateException {
        int i = this.mBooleanIndex;
        byte[] bArr = this.dwFlags;
        if (i >= bArr.length) {
            throw new IllegalStateException("out of bounds");
        }
        byte b = bArr[i];
        int i2 = this.mBooleanShift;
        byte b2 = (byte) (b & (~(3 << i2)));
        bArr[i] = b2;
        bArr[i] = (byte) (((z ? 1 : 0) << i2) | b2);
        if (i2 == 0) {
            this.mBooleanIndex = i + 1;
            this.mBooleanShift = 6;
        } else {
            this.mBooleanShift = i2 - 2;
        }
        return this;
    }

    public EA11 setInterval(String str) {
        this.szInterval = str;
        return this;
    }

    public EA11 setUnknown() {
        int i = this.mBooleanShift;
        if (i == 0) {
            this.mBooleanIndex++;
            this.mBooleanShift = 6;
        } else {
            this.mBooleanShift = i - 2;
        }
        return this;
    }

    @Override // com.att.iqi.lib.Metric, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeByteArray(this.dwFlags);
        parcel.writeString(this.szInterval);
    }

    @Keep
    public EA11(Parcel parcel) {
        super(parcel);
        byte[] bArr = new byte[ALL_SETTINGS_UNKNOWN.length];
        this.dwFlags = bArr;
        this.szInterval = ApnSettings.MVNO_NONE;
        this.mBooleanIndex = 0;
        this.mBooleanShift = 6;
        parcel.readInt();
        parcel.readByteArray(bArr);
        this.szInterval = parcel.readString();
    }
}