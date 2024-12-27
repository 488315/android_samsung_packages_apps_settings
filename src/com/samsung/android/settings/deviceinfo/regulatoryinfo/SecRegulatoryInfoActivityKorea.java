package com.samsung.android.settings.deviceinfo.regulatoryinfo;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecRegulatoryInfoActivityKorea extends SettingsPreferenceFragment {
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:67:0x023c  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0245  */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0233 A[Catch: IOException -> 0x022c, TRY_ENTER, TRY_LEAVE, TryCatch #5 {IOException -> 0x022c, blocks: (B:87:0x0228, B:93:0x0233), top: B:86:0x0228 }] */
    /* JADX WARN: Type inference failed for: r0v10, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v25 */
    /* JADX WARN: Type inference failed for: r0v27, types: [java.io.IOException] */
    /* JADX WARN: Type inference failed for: r0v34 */
    /* JADX WARN: Type inference failed for: r0v35 */
    /* JADX WARN: Type inference failed for: r0v36 */
    /* JADX WARN: Type inference failed for: r0v37 */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:88:0x022d -> B:63:0x0236). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String getActualInformation(java.lang.String r8) {
        /*
            Method dump skipped, instructions count: 653
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.regulatoryinfo.SecRegulatoryInfoActivityKorea.getActualInformation(java.lang.String):java.lang.String");
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 77030;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x016b  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0102 A[Catch: all -> 0x011f, LOOP:0: B:39:0x0100->B:40:0x0102, LOOP_END, TryCatch #14 {all -> 0x011f, blocks: (B:38:0x00f7, B:40:0x0102, B:42:0x0121), top: B:37:0x00f7, outer: #9 }] */
    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View onCreateView(
            android.view.LayoutInflater r11, android.view.ViewGroup r12, android.os.Bundle r13) {
        /*
            Method dump skipped, instructions count: 376
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.settings.deviceinfo.regulatoryinfo.SecRegulatoryInfoActivityKorea.onCreateView(android.view.LayoutInflater,"
                    + " android.view.ViewGroup, android.os.Bundle):android.view.View");
    }

    public final String translateManufactureCountry(String str) {
        return str.equalsIgnoreCase("KOREA")
                ? getString(R.string.regulatory_info_manufacture_country_korea)
                : str.equalsIgnoreCase("VIETNAM")
                        ? getString(R.string.regulatory_info_manufacture_country_vietnam)
                        : str.equalsIgnoreCase("CHINA")
                                ? getString(R.string.regulatory_info_manufacture_country_china)
                                : str.equalsIgnoreCase("INDIA")
                                        ? getString(
                                                R.string.regulatory_info_manufacture_country_india)
                                        : str.equalsIgnoreCase("INDONESIA")
                                                ? getString(
                                                        R.string
                                                                .regulatory_info_manufacture_country_indonesia)
                                                : str.equalsIgnoreCase("ARGENTINA")
                                                        ? getString(
                                                                R.string
                                                                        .regulatory_info_manufacture_country_argentina)
                                                        : str.equalsIgnoreCase("BRAZIL")
                                                                ? getString(
                                                                        R.string
                                                                                .regulatory_info_manufacture_country_brazil)
                                                                : getString(R.string.unknown);
    }
}
