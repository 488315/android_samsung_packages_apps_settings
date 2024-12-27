package com.samsung.android.lib.episode;

import android.os.Bundle;
import android.text.TextUtils;

import com.samsung.android.gtscell.data.FieldName;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class SourceInfo {
    public String mDTDVersion;
    public String mDeviceType;
    public int mManufacturer;
    public int mOSVersion;
    public String mOneUIVersion;
    public ArrayList mPackageList;
    public int mRequestFrom;
    public boolean mRestoreViaFastTrack;
    public String mVersion;

    public SourceInfo() {
        this.mDeviceType = null;
        this.mVersion = null;
        this.mDTDVersion = null;
        this.mRestoreViaFastTrack = false;
        this.mRequestFrom = -1;
        this.mPackageList = null;
        this.mManufacturer = -1;
    }

    public final String getPackageIndex(String str) {
        ArrayList arrayList;
        return (TextUtils.isEmpty(str)
                        || (arrayList = this.mPackageList) == null
                        || !arrayList.contains(str))
                ? str
                : Integer.toString(this.mPackageList.indexOf(str));
    }

    public final String getPackageName(String str) {
        try {
            int parseInt = Integer.parseInt(str);
            ArrayList arrayList = this.mPackageList;
            if (arrayList != null && parseInt < arrayList.size()) {
                return (String) this.mPackageList.get(parseInt);
            }
            return null;
        } catch (NumberFormatException unused) {
            return str;
        }
    }

    public SourceInfo(Bundle bundle) {
        this.mDeviceType = null;
        this.mVersion = null;
        this.mDTDVersion = null;
        this.mRestoreViaFastTrack = false;
        this.mRequestFrom = -1;
        this.mPackageList = null;
        this.mManufacturer = -1;
        if (bundle == null) {
            return;
        }
        this.mDeviceType = bundle.getString("deviceType");
        this.mVersion = bundle.getString(FieldName.VERSION);
        this.mDTDVersion = bundle.getString("dtd_version");
        this.mRequestFrom = bundle.getInt("requestFrom");
        this.mRestoreViaFastTrack = bundle.getBoolean("fastTrack");
        this.mOSVersion = bundle.getInt("OSVersion");
        this.mOneUIVersion = bundle.getString("oneUIVersion");
        this.mPackageList =
                EpisodeUtils.convertStringToArrayList(
                        EpisodeUtils.decompressString(bundle.getString("packageList")));
        this.mManufacturer = bundle.getInt("manufacturer");
    }
}
