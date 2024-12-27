package com.android.settings.fuelgauge.batterytip.tips;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseIntArray;

import androidx.preference.Preference;

import com.android.settings.widget.TipCardPreference;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class BatteryTip implements Comparable, Parcelable {
    static final SparseIntArray TIP_ORDER;
    public boolean mNeedUpdate;
    public final boolean mShowDialog;
    public int mState;
    public final int mType;

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        TIP_ORDER = sparseIntArray;
        sparseIntArray.append(3, 0);
        sparseIntArray.append(5, 1);
        sparseIntArray.append(8, 2);
        sparseIntArray.append(9, 3);
        sparseIntArray.append(10, 4);
        sparseIntArray.append(1, 5);
        sparseIntArray.append(2, 6);
        sparseIntArray.append(6, 7);
        sparseIntArray.append(0, 8);
        sparseIntArray.append(4, 9);
        sparseIntArray.append(7, 10);
        sparseIntArray.append(11, 11);
    }

    public BatteryTip(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mState = parcel.readInt();
        this.mShowDialog = parcel.readBoolean();
        this.mNeedUpdate = parcel.readBoolean();
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        SparseIntArray sparseIntArray = TIP_ORDER;
        return sparseIntArray.get(this.mType) - sparseIntArray.get(((BatteryTip) obj).mType);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public abstract int getIconId();

    public abstract CharSequence getSummary(Context context);

    public abstract CharSequence getTitle(Context context);

    public abstract void log(Context context, MetricsFeatureProvider metricsFeatureProvider);

    public String toString() {
        return "type=" + this.mType + " state=" + this.mState;
    }

    public void updatePreference(Preference preference) {
        Context context = preference.getContext();
        preference.setTitle(getTitle(context));
        preference.setSummary(getSummary(context));
        preference.setIcon(getIconId());
        TipCardPreference tipCardPreference =
                preference instanceof TipCardPreference ? (TipCardPreference) preference : null;
        if (tipCardPreference != null) {
            tipCardPreference.primaryButtonVisibility = false;
            tipCardPreference.secondaryButtonVisibility = false;
            tipCardPreference.notifyChanged();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeInt(this.mState);
        parcel.writeBoolean(this.mShowDialog);
        parcel.writeBoolean(this.mNeedUpdate);
    }

    public BatteryTip(int i, int i2, boolean z) {
        this.mType = i;
        this.mState = i2;
        this.mShowDialog = z;
        this.mNeedUpdate = true;
    }

    public void validateCheck(Context context) {}
}
