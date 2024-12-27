package com.samsung.android.settings.inputmethod;

import android.hardware.input.InputManagerGlobal;
import android.util.Log;

import com.android.settings.search.BaseSearchIndexProvider;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final /* synthetic */ class PhysicalKeyboardFragment$$ExternalSyntheticLambda2
        implements InputManagerGlobal.OnWirelessKeyboardShareChangedListener {
    public final /* synthetic */ PhysicalKeyboardFragment f$0;

    public final void onWirelessKeyboardShareChanged(long j, int i, String str) {
        PhysicalKeyboardFragment physicalKeyboardFragment = this.f$0;
        BaseSearchIndexProvider baseSearchIndexProvider =
                PhysicalKeyboardFragment.SEARCH_INDEX_DATA_PROVIDER;
        physicalKeyboardFragment.getClass();
        Log.v(
                "PhysicalKeyboardFragment",
                "whenNanos : " + j + " index : " + i + " contents : " + str);
        physicalKeyboardFragment.mWirelessKeyboardShareDBUtil.parsing(false);
        if (i == 0) {
            physicalKeyboardFragment.mWirelessKeyboardShareDBUtil.save(0, "true".equals(str));
            return;
        }
        if (i == 1) {
            physicalKeyboardFragment.mWirelessKeyboardShareDBUtil.save(1, str);
            return;
        }
        if (i == 2) {
            physicalKeyboardFragment.mWirelessKeyboardShareDBUtil.save(7, str);
            return;
        }
        if (i == 3) {
            physicalKeyboardFragment.mWirelessKeyboardShareDBUtil.save(2, str);
        } else if (i == 4) {
            physicalKeyboardFragment.mWirelessKeyboardShareDBUtil.save(3, "true".equals(str));
        } else {
            if (i != 5) {
                return;
            }
            physicalKeyboardFragment.mWirelessKeyboardShareDBUtil.save(4, "true".equals(str));
        }
    }
}
