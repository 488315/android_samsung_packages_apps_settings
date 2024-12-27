package com.google.android.setupdesign.util;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class ThemeResolver {
    public static ThemeResolver defaultResolver;
    public final int defaultTheme;
    public final String oldestSupportedTheme = null;
    public final boolean useDayNight;

    public ThemeResolver(int i, boolean z) {
        this.defaultTheme = i;
        this.useDayNight = z;
    }

    public static int getThemeRes(String str) {
        if (str != null) {
            switch (str) {
                case "glif_v2_light":
                    return 2132083863;
                case "material_light":
                    return 2132083872;
                case "glif_v3_light":
                    return 2132083866;
                case "glif_v4_light":
                    return 2132083869;
                case "glif":
                    return R.style.SudThemeGlif;
                case "glif_v2":
                    return R.style.SudThemeGlifV2;
                case "glif_v3":
                    return R.style.SudThemeGlifV3;
                case "glif_v4":
                    return R.style.SudThemeGlifV4;
                case "material":
                    return R.style.SudThemeMaterial;
                case "glif_light":
                    return 2132083860;
            }
        }
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x01bb, code lost:

       if (r0.equals("glif_v2_light") == false) goto L118;
    */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0158  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x01c1  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x01ca  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x01cd  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x01d9 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x015c  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0167  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x017b  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x018f  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01ad  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01b7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int resolve(java.lang.String r25, boolean r26) {
        /*
            Method dump skipped, instructions count: 674
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.google.android.setupdesign.util.ThemeResolver.resolve(java.lang.String,"
                    + " boolean):int");
    }
}
