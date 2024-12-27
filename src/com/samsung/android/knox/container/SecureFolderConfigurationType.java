package com.samsung.android.knox.container;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecureFolderConfigurationType extends KnoxConfigurationType {
    public static final Parcelable.Creator<SecureFolderConfigurationType> CREATOR =
            new AnonymousClass1();
    public static final String TAG = "SecureFolderConfigurationType";
    public boolean mAllowClearAllNotification;
    public boolean mAllowHomeKey;
    public boolean mAllowSettingsChanges;
    public boolean mAllowStatusBarExpansion;
    public boolean mHideSystemBar;
    public boolean mWipeRecentTasks;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.container.SecureFolderConfigurationType$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<SecureFolderConfigurationType> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SecureFolderConfigurationType createFromParcel(Parcel parcel) {
            return new SecureFolderConfigurationType(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SecureFolderConfigurationType[] newArray(int i) {
            Log.d(
                    SecureFolderConfigurationType.TAG,
                    "SecureFolderConfigurationType[] array to be created");
            return new SecureFolderConfigurationType[i];
        }
    }

    public SecureFolderConfigurationType() {
        this.mAllowSettingsChanges = true;
        this.mAllowStatusBarExpansion = true;
        this.mAllowHomeKey = true;
        this.mAllowClearAllNotification = false;
        this.mHideSystemBar = false;
        this.mWipeRecentTasks = false;
    }

    public void allowClearAllNotification(boolean z) {
        this.mAllowClearAllNotification = z;
    }

    public void allowHomeKey(boolean z) {
        this.mAllowHomeKey = z;
    }

    public void allowSettingsChanges(boolean z) {
        this.mAllowSettingsChanges = z;
    }

    public void allowStatusBarExpansion(boolean z) {
        this.mAllowStatusBarExpansion = z;
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType
    public void dumpState() {
        Log.d(TAG, "COM config dump START:");
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                MainClearConfirm$$ExternalSyntheticOutline0.m(
                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                MainClearConfirm$$ExternalSyntheticOutline0.m(
                                        MainClearConfirm$$ExternalSyntheticOutline0.m(
                                                MainClearConfirm$$ExternalSyntheticOutline0.m(
                                                        new StringBuilder(
                                                                "mAllowSettingsChanges : "),
                                                        this.mAllowSettingsChanges,
                                                        TAG,
                                                        "mAllowStatusBarExpansion : "),
                                                this.mAllowStatusBarExpansion,
                                                TAG,
                                                "mAllowHomeKey : "),
                                        this.mAllowHomeKey,
                                        TAG,
                                        "mAllowClearAllNotification : "),
                                this.mAllowClearAllNotification,
                                TAG,
                                "mHideSystemBar : "),
                        this.mHideSystemBar,
                        TAG,
                        "mWipeRecentTasks : "),
                this.mWipeRecentTasks,
                TAG);
        super.dumpState();
        Log.d(TAG, "COM config dump END.");
    }

    public boolean isClearAllNotificationAllowed() {
        return this.mAllowClearAllNotification;
    }

    public boolean isHideSystemBarEnabled() {
        return this.mHideSystemBar;
    }

    public boolean isHomeKeyAllowed() {
        return this.mAllowHomeKey;
    }

    public boolean isSettingChangesAllowed() {
        return this.mAllowSettingsChanges;
    }

    public boolean isStatusBarExpansionAllowed() {
        return this.mAllowStatusBarExpansion;
    }

    public boolean isWipeRecentTasksEnabled() {
        return this.mWipeRecentTasks;
    }

    public void setHideSystemBar(boolean z) {
        this.mHideSystemBar = z;
    }

    public void setWipeRecentTasks(boolean z) {
        this.mWipeRecentTasks = z;
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.mAllowSettingsChanges ? 1 : 0);
        parcel.writeInt(this.mAllowStatusBarExpansion ? 1 : 0);
        parcel.writeInt(this.mAllowHomeKey ? 1 : 0);
        parcel.writeInt(this.mAllowClearAllNotification ? 1 : 0);
        parcel.writeInt(this.mHideSystemBar ? 1 : 0);
        parcel.writeInt(this.mWipeRecentTasks ? 1 : 0);
    }

    @Override // com.samsung.android.knox.container.KnoxConfigurationType
    public SecureFolderConfigurationType clone(String str) {
        if (str == null || str.isEmpty()) {
            Log.d(TAG, "clone(): name is either null or empty, hence returning null");
            return null;
        }
        SecureFolderConfigurationType secureFolderConfigurationType =
                new SecureFolderConfigurationType();
        cloneConfiguration(secureFolderConfigurationType, str);
        secureFolderConfigurationType.allowSettingsChanges(this.mAllowSettingsChanges);
        secureFolderConfigurationType.allowStatusBarExpansion(this.mAllowStatusBarExpansion);
        secureFolderConfigurationType.allowHomeKey(this.mAllowHomeKey);
        secureFolderConfigurationType.allowClearAllNotification(this.mAllowClearAllNotification);
        secureFolderConfigurationType.setHideSystemBar(this.mHideSystemBar);
        secureFolderConfigurationType.setWipeRecentTasks(this.mWipeRecentTasks);
        return secureFolderConfigurationType;
    }

    public SecureFolderConfigurationType(Parcel parcel) {
        super(parcel);
        this.mAllowSettingsChanges = true;
        this.mAllowStatusBarExpansion = true;
        this.mAllowHomeKey = true;
        this.mAllowClearAllNotification = false;
        this.mHideSystemBar = false;
        this.mWipeRecentTasks = false;
        this.mAllowSettingsChanges = parcel.readInt() == 1;
        this.mAllowStatusBarExpansion = parcel.readInt() == 1;
        this.mAllowHomeKey = parcel.readInt() == 1;
        this.mAllowClearAllNotification = parcel.readInt() == 1;
        this.mHideSystemBar = parcel.readInt() == 1;
        this.mWipeRecentTasks = parcel.readInt() == 1;
    }
}
