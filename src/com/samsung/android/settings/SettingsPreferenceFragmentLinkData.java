package com.samsung.android.settings;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SettingsPreferenceFragmentLinkData implements Parcelable {
    public static final Parcelable.Creator<SettingsPreferenceFragmentLinkData> CREATOR =
            new AnonymousClass1();
    public long clickInterval;
    public Bundle extras;
    public String fragment;
    public Intent intent;
    public String runType;
    public int titleRes;
    public String topLevelKey;
    public int linkedTitleRes = 0;
    public String flowId = null;
    public int callerMetric = -1;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.SettingsPreferenceFragmentLinkData$1, reason: invalid class name */
    public final class AnonymousClass1 implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            SettingsPreferenceFragmentLinkData settingsPreferenceFragmentLinkData =
                    new SettingsPreferenceFragmentLinkData();
            settingsPreferenceFragmentLinkData.linkedTitleRes = 0;
            settingsPreferenceFragmentLinkData.flowId = null;
            settingsPreferenceFragmentLinkData.callerMetric = -1;
            settingsPreferenceFragmentLinkData.titleRes = parcel.readInt();
            settingsPreferenceFragmentLinkData.linkedTitleRes = parcel.readInt();
            settingsPreferenceFragmentLinkData.fragment = parcel.readString();
            if (parcel.readInt() != 0) {
                settingsPreferenceFragmentLinkData.intent =
                        (Intent) Intent.CREATOR.createFromParcel(parcel);
            }
            settingsPreferenceFragmentLinkData.runType = parcel.readString();
            settingsPreferenceFragmentLinkData.extras = parcel.readBundle();
            settingsPreferenceFragmentLinkData.flowId = parcel.readString();
            settingsPreferenceFragmentLinkData.callerMetric = parcel.readInt();
            settingsPreferenceFragmentLinkData.topLevelKey = parcel.readString();
            settingsPreferenceFragmentLinkData.clickInterval = parcel.readLong();
            return settingsPreferenceFragmentLinkData;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new SettingsPreferenceFragmentLinkData[i];
        }
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.titleRes);
        parcel.writeInt(this.linkedTitleRes);
        parcel.writeString(this.fragment);
        if (this.intent != null) {
            parcel.writeInt(1);
            this.intent.writeToParcel(parcel, i);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.runType);
        parcel.writeBundle(this.extras);
        parcel.writeString(this.flowId);
        parcel.writeInt(this.callerMetric);
        parcel.writeString(this.topLevelKey);
        parcel.writeLong(this.clickInterval);
    }
}
