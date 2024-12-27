package com.android.wifitrackerlib;

import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;

import java.util.function.Function;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiEntry$$ExternalSyntheticLambda4 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i;
        WifiEntry wifiEntry = (WifiEntry) obj;
        switch (this.$r8$classId) {
            case 0:
                return Boolean.valueOf(wifiEntry.getConnectedState() != 2);
            case 1:
                return Integer.valueOf(-wifiEntry.mFrequency);
            case 2:
                return wifiEntry.getTitle();
            case 3:
                return Boolean.valueOf(!(wifiEntry.mRssi != -127));
            case 4:
                return Boolean.valueOf(!wifiEntry.isSaved());
            case 5:
                return Boolean.valueOf(!wifiEntry.isSuggestion());
            case 6:
                SemWifiEntryFlags semWifiEntryFlags = wifiEntry.mSemFlags;
                if (!semWifiEntryFlags.networkScoringUiEnabled
                        || (i = wifiEntry.mSpeed) < 20
                        || semWifiEntryFlags.networkType == 2) {
                    i = 0;
                }
                return Integer.valueOf(-i);
            case 7:
                return Integer.valueOf(-wifiEntry.getLevel());
            case 8:
                return Integer.valueOf(wifiEntry.getSecurity$1());
            case 9:
                return Boolean.valueOf(wifiEntry.getConnectedState() != 2);
            case 10:
                return Boolean.valueOf(wifiEntry.getConnectedState() != 2);
            case 11:
                return Boolean.valueOf(!(wifiEntry.mRssi != -127));
            case 12:
                return Integer.valueOf(-wifiEntry.mRssi);
            case 13:
                return Boolean.valueOf(wifiEntry.getConnectedState() != 2);
            default:
                return Boolean.valueOf(!(wifiEntry.mRssi != -127));
        }
    }
}
