package com.github.mikephil.charting.utils;

import android.graphics.Color;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ColorTemplate {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        Color.rgb(
                207,
                IKnoxCustomManager.Stub.TRANSACTION_removeDexShortcut,
                IKnoxCustomManager.Stub.TRANSACTION_getHomeScreenMode);
        Color.rgb(148, 212, 212);
        Color.rgb(136, 180, 187);
        Color.rgb(118, 174, 175);
        Color.rgb(42, 109, 130);
        Color.rgb(217, 80, 138);
        Color.rgb(254, 149, 7);
        Color.rgb(254, IKnoxCustomManager.Stub.TRANSACTION_addDexShortcut, 120);
        Color.rgb(106, 167, 134);
        Color.rgb(53, 194, 209);
        Color.rgb(64, 89, 128);
        Color.rgb(149, 165, 124);
        Color.rgb(217, 184, 162);
        Color.rgb(191, 134, 134);
        Color.rgb(179, 48, 80);
        Color.rgb(193, 37, 82);
        Color.rgb(255, 102, 0);
        Color.rgb(IKnoxCustomManager.Stub.TRANSACTION_setHomeScreenMode, 199, 0);
        Color.rgb(106, 150, 31);
        Color.rgb(179, 100, 53);
        Color.rgb(192, 255, 140);
        Color.rgb(255, IKnoxCustomManager.Stub.TRANSACTION_addDexShortcut, 140);
        Color.rgb(255, 208, 140);
        Color.rgb(140, IKnoxCustomManager.Stub.TRANSACTION_deleteHomeScreenPage, 255);
        Color.rgb(255, 140, 157);
        rgb("#2ecc71");
        rgb("#f1c40f");
        rgb("#e74c3c");
        rgb("#3498db");
    }

    public static void rgb(String str) {
        int parseLong = (int) Long.parseLong(str.replace("#", ApnSettings.MVNO_NONE), 16);
        Color.rgb((parseLong >> 16) & 255, (parseLong >> 8) & 255, parseLong & 255);
    }
}
