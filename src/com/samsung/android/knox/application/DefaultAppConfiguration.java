package com.samsung.android.knox.application;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DefaultAppConfiguration implements Parcelable {
    public static final Parcelable.Creator<DefaultAppConfiguration> CREATOR = new AnonymousClass1();
    public ComponentName mComponentName;
    public Intent mTaskType;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.application.DefaultAppConfiguration$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<DefaultAppConfiguration> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DefaultAppConfiguration createFromParcel(Parcel parcel) {
            return new DefaultAppConfiguration(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DefaultAppConfiguration[] newArray(int i) {
            return new DefaultAppConfiguration[i];
        }
    }

    public DefaultAppConfiguration(Intent intent, ComponentName componentName) {
        this.mTaskType = intent;
        this.mComponentName = componentName;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ComponentName getComponentName() {
        return this.mComponentName;
    }

    public Intent getTaskType() {
        return this.mTaskType;
    }

    public void readFromParcel(Parcel parcel) {
        this.mTaskType = (Intent) Intent.CREATOR.createFromParcel(parcel);
        this.mComponentName = ComponentName.readFromParcel(parcel);
    }

    public void setComponentName(ComponentName componentName) {
        this.mComponentName = componentName;
    }

    public void setTaskType(Intent intent) {
        this.mTaskType = intent;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        this.mTaskType.writeToParcel(parcel, i);
        ComponentName.writeToParcel(this.mComponentName, parcel);
    }

    public DefaultAppConfiguration(Parcel parcel) {
        readFromParcel(parcel);
    }

    public DefaultAppConfiguration() {}
}
