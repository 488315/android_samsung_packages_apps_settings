package com.samsung.android.settings.cube;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import androidx.compose.ui.text.SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.samsung.android.gtscell.data.GtsConfiguration;
import com.samsung.android.knox.accounts.Account;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.lib.episode.SourceInfo;
import com.samsung.android.settings.goodsettings.GoodSettingsContract;

import java.util.ArrayList;
import java.util.Base64;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ControlValue {
    public final int mAvailabilityStatus;
    public final Bundle mBundle;
    public final String mControlId;
    public final int mControlType;
    public final boolean mForceChange;
    public final boolean mIsDefault;
    public final String mKey;
    public final SourceInfo mSourceInfo;
    public final String mStatusCode;
    public final String mStorePackage;
    public final String mSummary;
    public final Uri mUri;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class Builder {
        public int mAvailabilityStatus;
        public Bundle mBundle = new Bundle();
        public String mBundleDataAsString;
        public String mControlId;
        public final int mControlType;
        public Boolean mForceChange;
        public Boolean mIsDefault;
        public final String mKey;
        public SourceInfo mSourceInfo;
        public String mStatusCode;
        public String mStorePackage;
        public String mSummary;
        public Uri mUri;

        public Builder(String str, int i) {
            this.mKey = str;
            Boolean bool = Boolean.FALSE;
            this.mIsDefault = bool;
            this.mForceChange = bool;
            this.mControlType = i;
            StringBuilder m =
                    PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(str, "@@");
            m.append(System.currentTimeMillis());
            this.mControlId = m.toString();
            this.mSourceInfo = null;
        }

        public final void addAttribute(String str, String str2) {
            if (this.mBundle.containsKey(str)) {
                SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0.m(
                        "the value of the attribute (",
                        str,
                        ") will be replaced with a new one",
                        "ControlValue.Builder");
            }
            this.mBundle.putString(str, str2);
        }

        public final void addAttributeInt(int i, String str) {
            if (this.mBundle.containsKey(str)) {
                SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0.m(
                        "the value of the attribute (",
                        str,
                        ") will be replaced with a new one",
                        "ControlValue.Builder");
            }
            this.mBundle.putInt(str, i);
        }

        public final void addAttributeStringArrayList(String str, ArrayList arrayList) {
            if (this.mBundle.containsKey(str)) {
                SaversKt$LocaleSaver$2$$ExternalSyntheticOutline0.m(
                        "the value of the attribute (",
                        str,
                        ") will be replaced with a new one",
                        "ControlValue.Builder");
            }
            this.mBundle.putStringArrayList(str, arrayList);
        }

        public final ControlValue build() {
            boolean isEmpty = this.mBundle.isEmpty();
            String str = this.mKey;
            boolean z =
                    ((isEmpty && TextUtils.isEmpty(this.mBundleDataAsString))
                                    || TextUtils.isEmpty(str))
                            ? false
                            : true;
            if (!z) {
                Log.i(
                        "ControlValue.Builder",
                        "isValid() - invalid data : "
                                + this.mBundle.isEmpty()
                                + " / "
                                + TextUtils.isEmpty(this.mBundleDataAsString)
                                + " / "
                                + TextUtils.isEmpty(str));
            }
            if (z) {
                return new ControlValue(this);
            }
            return null;
        }

        public final void setControlId(String str) {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            this.mControlId = str;
        }

        public final void setValue(Object obj) {
            String valueOf = String.valueOf(obj);
            if (this.mBundle.containsKey("value")) {
                Log.e("ControlValue.Builder", "the element value will be replaced with a new one");
                Log.e(
                        "ControlValue.Builder",
                        "old : " + this.mBundle.getString("value") + ", new : " + obj);
            }
            this.mBundle.putString("value", valueOf);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ControlValueWrapper {

        @SerializedName("bundle")
        private String mBundleAsString;

        @SerializedName("controlType")
        private final String mControlType;

        @SerializedName("forceChange")
        private final String mForceChange;

        @SerializedName(Account.IS_DEFAULT)
        private final String mIsDefault;

        @SerializedName(GoodSettingsContract.EXTRA_NAME.POLICY_KEY)
        private final String mKey;

        public ControlValueWrapper(ControlValue controlValue) {
            this.mKey = controlValue.mKey;
            Bundle bundle = controlValue.mBundle;
            Parcel obtain = Parcel.obtain();
            bundle.writeToParcel(obtain, 0);
            byte[] marshall = obtain.marshall();
            obtain.recycle();
            if (marshall != null && marshall.length > 0) {
                this.mBundleAsString = Base64.getEncoder().encodeToString(marshall);
            }
            this.mIsDefault = String.valueOf(controlValue.mIsDefault);
            this.mForceChange = String.valueOf(controlValue.mForceChange);
            this.mControlType = String.valueOf(controlValue.mControlType);
        }

        public final ControlValue buildControlValue(String str) {
            return buildControlValue(str, null, Boolean.parseBoolean(this.mForceChange), null);
        }

        public final ControlValue buildControlValue(
                String str, Uri uri, boolean z, GtsConfiguration gtsConfiguration) {
            SourceInfo buildEmptySourceInfo;
            try {
                if (!TextUtils.isEmpty(this.mKey) && !TextUtils.isEmpty(this.mControlType)) {
                    Builder builder = new Builder(this.mKey, Integer.parseInt(this.mControlType));
                    builder.mIsDefault = Boolean.valueOf(Boolean.parseBoolean(this.mIsDefault));
                    builder.mForceChange = Boolean.valueOf(z);
                    builder.mBundleDataAsString = this.mBundleAsString;
                    builder.setControlId(str);
                    builder.mUri = uri;
                    if (gtsConfiguration == null) {
                        buildEmptySourceInfo = null;
                    } else {
                        buildEmptySourceInfo = ControlValue.buildEmptySourceInfo();
                        buildEmptySourceInfo.mDeviceType =
                                gtsConfiguration.getBuildCharacteristics();
                        buildEmptySourceInfo.mOSVersion = gtsConfiguration.getOsVersion();
                    }
                    builder.mSourceInfo = buildEmptySourceInfo;
                    return builder.build();
                }
                Log.w(
                        "ControlValue$ControlValueWrapper",
                        "mKey and mControlType should not be null");
                return null;
            } catch (NumberFormatException e) {
                Log.w("ControlValue$ControlValueWrapper", "buildControlValue() " + e.toString());
                return null;
            }
        }
    }

    public ControlValue(Builder builder) {
        this.mKey = builder.mKey;
        this.mSummary = builder.mSummary;
        this.mStorePackage = builder.mStorePackage;
        this.mUri = builder.mUri;
        this.mIsDefault = builder.mIsDefault.booleanValue();
        this.mForceChange = builder.mForceChange.booleanValue();
        this.mControlType = builder.mControlType;
        if (!builder.mBundle.isEmpty() || TextUtils.isEmpty(builder.mBundleDataAsString)) {
            this.mBundle = builder.mBundle;
        } else {
            byte[] decode = Base64.getDecoder().decode(builder.mBundleDataAsString);
            if (decode == null || decode.length <= 0) {
                this.mBundle = new Bundle();
            } else {
                Parcelable.Creator creator = Bundle.CREATOR;
                Parcel obtain = Parcel.obtain();
                obtain.unmarshall(decode, 0, decode.length);
                obtain.setDataPosition(0);
                Object createFromParcel = creator.createFromParcel(obtain);
                obtain.recycle();
                this.mBundle = new Bundle((Bundle) createFromParcel);
            }
        }
        this.mControlId = builder.mControlId;
        this.mAvailabilityStatus = builder.mAvailabilityStatus;
        this.mStatusCode = builder.mStatusCode;
        this.mSourceInfo = builder.mSourceInfo;
    }

    public static SourceInfo buildEmptySourceInfo() {
        SourceInfo sourceInfo = new SourceInfo();
        sourceInfo.mVersion = ApnSettings.MVNO_NONE;
        sourceInfo.mDTDVersion = ApnSettings.MVNO_NONE;
        sourceInfo.mOneUIVersion = ApnSettings.MVNO_NONE;
        sourceInfo.mManufacturer = 0;
        sourceInfo.mRestoreViaFastTrack = false;
        sourceInfo.mPackageList = new ArrayList();
        sourceInfo.mDeviceType = ApnSettings.MVNO_NONE;
        sourceInfo.mOSVersion = 0;
        sourceInfo.mRequestFrom = 0;
        return sourceInfo;
    }

    public final String getAttribute(String str) {
        Bundle bundle = this.mBundle;
        if (bundle == null || !bundle.containsKey(str)) {
            return null;
        }
        return this.mBundle.getString(str);
    }

    public final int getAttributeInt(String str) {
        Bundle bundle = this.mBundle;
        if (bundle == null || !bundle.containsKey(str)) {
            return -1;
        }
        return this.mBundle.getInt(str);
    }

    public final ArrayList getAttributeStringArrayList(String str) {
        Bundle bundle = this.mBundle;
        if (bundle == null || !bundle.containsKey(str)) {
            return null;
        }
        return this.mBundle.getStringArrayList(str);
    }

    public final Object getTypedValue() {
        String value = getValue();
        if (TextUtils.isEmpty(value)) {
            return value;
        }
        int i = this.mControlType;
        return i != 1
                ? (i == 2 || i == 100 || i == 101)
                        ? Integer.valueOf(Integer.parseInt(value))
                        : value
                : Boolean.valueOf(Boolean.parseBoolean(value));
    }

    public final String getValue() {
        Bundle bundle = this.mBundle;
        if (bundle == null || !bundle.containsKey("value")) {
            return null;
        }
        return this.mBundle.getString("value");
    }

    public final String toString() {
        return new Gson().toJson(new ControlValueWrapper(this), ControlValueWrapper.class);
    }
}
