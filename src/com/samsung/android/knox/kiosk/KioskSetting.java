package com.samsung.android.knox.kiosk;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class KioskSetting implements Parcelable {
    public static final Parcelable.Creator<KioskSetting> CREATOR = new AnonymousClass1();
    public boolean airCommand;
    public boolean airView;
    public int blockedEdgeFunctions;
    public boolean clearAllNotifications;
    public List<Integer> hardwareKey;
    public boolean homeKey;
    public boolean multiWindow;
    public boolean navigationBar;
    public boolean settingsChanges;
    public boolean smartClip;
    public boolean statusBar;
    public boolean statusBarExpansion;
    public boolean systemBar;
    public boolean taskManager;
    public boolean wipeRecentTasks;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.knox.kiosk.KioskSetting$1, reason: invalid class name */
    public class AnonymousClass1 implements Parcelable.Creator<KioskSetting> {
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KioskSetting createFromParcel(Parcel parcel) {
            return new KioskSetting(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KioskSetting[] newArray(int i) {
            return new KioskSetting[i];
        }
    }

    public KioskSetting() {}

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.settingsChanges = parcel.readInt() != 0;
        this.statusBarExpansion = parcel.readInt() != 0;
        this.homeKey = parcel.readInt() != 0;
        this.airCommand = parcel.readInt() != 0;
        this.airView = parcel.readInt() != 0;
        if (parcel.readInt() != 0) {
            int[] createIntArray = parcel.createIntArray();
            this.hardwareKey = new ArrayList();
            for (int i : createIntArray) {
                this.hardwareKey.add(Integer.valueOf(i));
            }
        }
        this.multiWindow = parcel.readInt() != 0;
        this.smartClip = parcel.readInt() != 0;
        this.taskManager = parcel.readInt() != 0;
        this.clearAllNotifications = parcel.readInt() != 0;
        this.navigationBar = parcel.readInt() != 0;
        this.statusBar = parcel.readInt() != 0;
        this.systemBar = parcel.readInt() != 0;
        this.wipeRecentTasks = parcel.readInt() != 0;
        this.blockedEdgeFunctions = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.settingsChanges ? 1 : 0);
        parcel.writeInt(this.statusBarExpansion ? 1 : 0);
        parcel.writeInt(this.homeKey ? 1 : 0);
        parcel.writeInt(this.airCommand ? 1 : 0);
        parcel.writeInt(this.airView ? 1 : 0);
        if (this.hardwareKey != null) {
            parcel.writeInt(1);
            int[] iArr = new int[this.hardwareKey.size()];
            for (int i2 = 0; i2 != this.hardwareKey.size(); i2++) {
                iArr[i2] = this.hardwareKey.get(i2).intValue();
            }
            parcel.writeIntArray(iArr);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.multiWindow ? 1 : 0);
        parcel.writeInt(this.smartClip ? 1 : 0);
        parcel.writeInt(this.taskManager ? 1 : 0);
        parcel.writeInt(this.clearAllNotifications ? 1 : 0);
        parcel.writeInt(this.navigationBar ? 1 : 0);
        parcel.writeInt(this.statusBar ? 1 : 0);
        parcel.writeInt(this.systemBar ? 1 : 0);
        parcel.writeInt(this.wipeRecentTasks ? 1 : 0);
        parcel.writeInt(this.blockedEdgeFunctions);
    }

    public KioskSetting(Parcel parcel) {
        readFromParcel(parcel);
    }
}
