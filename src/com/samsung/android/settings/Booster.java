package com.samsung.android.settings;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference$$ExternalSyntheticOutline0;

import com.samsung.android.os.SemDvfsManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class Booster {
    public final SemDvfsManager mCPUMinBooster;
    public final int mCpuMinFreq;
    public final SemDvfsManager mGPUMinBooster;
    public final int mGpuMinFreq;
    public long mLastBoosterTime = 0;
    public long mLastBoosterDuration = 0;

    public Booster(FragmentActivity fragmentActivity) {
        this.mCPUMinBooster = null;
        this.mGPUMinBooster = null;
        this.mCpuMinFreq = -1;
        this.mGpuMinFreq = -1;
        SemDvfsManager createInstance =
                SemDvfsManager.createInstance(
                        fragmentActivity, fragmentActivity.getPackageName(), 12);
        this.mCPUMinBooster = createInstance;
        int[] supportedFrequency = createInstance.getSupportedFrequency();
        if (supportedFrequency == null) {
            android.util.Log.d("Booster", "mSupportedCPUFreqTable is null");
            return;
        }
        int length = supportedFrequency.length;
        if (length > 1) {
            this.mCpuMinFreq = supportedFrequency[1];
        } else if (length > 0) {
            this.mCpuMinFreq = supportedFrequency[0];
        } else {
            this.mCpuMinFreq = -1;
        }
        int i = this.mCpuMinFreq;
        if (i > 0) {
            this.mCPUMinBooster.setDvfsValue(i);
        }
        android.util.Log.d("Booster", "mCpuMinFreq = " + this.mCpuMinFreq);
        SemDvfsManager createInstance2 =
                SemDvfsManager.createInstance(
                        fragmentActivity, fragmentActivity.getPackageName(), 16);
        this.mGPUMinBooster = createInstance2;
        int[] supportedFrequency2 = createInstance2.getSupportedFrequency();
        if (supportedFrequency2 == null) {
            return;
        }
        int length2 = supportedFrequency2.length;
        if (length2 > 1) {
            this.mGpuMinFreq = supportedFrequency2[1];
        } else if (length2 > 0) {
            this.mGpuMinFreq = supportedFrequency2[0];
        } else {
            this.mGpuMinFreq = -1;
        }
        int i2 = this.mGpuMinFreq;
        if (i2 > 0) {
            this.mGPUMinBooster.setDvfsValue(i2);
        }
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("mGpuMinFreq = "), this.mGpuMinFreq, "Booster");
    }
}
