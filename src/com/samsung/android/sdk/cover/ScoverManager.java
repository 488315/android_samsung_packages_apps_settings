package com.samsung.android.sdk.cover;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.os.Binder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.util.Slog;

import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;

import com.samsung.android.cover.CoverState;
import com.samsung.android.cover.ICoverManager;
import com.samsung.android.knox.custom.CustomDeviceManager;

import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class ScoverManager {
    public static boolean sIsClearCameraViewCoverSystemFeatureEnabled = false;
    public static boolean sIsClearCoverSystemFeatureEnabled = false;
    public static boolean sIsClearSideViewCoverSystemFeatureEnabled = false;
    public static boolean sIsFilpCoverSystemFeatureEnabled = false;
    public static boolean sIsLEDBackCoverSystemFeatureEnabled = false;
    public static boolean sIsMiniSviewWalletCoverSysltemFeatureEnabled = false;
    public static boolean sIsNeonCoverSystemFeatureEnabled = false;
    public static boolean sIsNfcLedCoverSystemFeatureEnabled = false;
    public static boolean sIsSViewCoverSystemFeatureEnabled = false;
    public static boolean sIsSystemFeatureQueried = false;
    public static int sServiceVersion = 16777216;
    public ICoverManager mService;

    public ScoverManager(Context context) {
        int intValue;
        new CopyOnWriteArrayList();
        new CopyOnWriteArrayList();
        new CopyOnWriteArrayList();
        new CopyOnWriteArrayList();
        new CopyOnWriteArrayList();
        new CopyOnWriteArrayList();
        new CopyOnWriteArrayList();
        new Binder();
        if (sIsSystemFeatureQueried) {
            return;
        }
        sIsFilpCoverSystemFeatureEnabled =
                context.getPackageManager().hasSystemFeature("com.sec.feature.cover.flip");
        sIsSViewCoverSystemFeatureEnabled =
                context.getPackageManager().hasSystemFeature("com.sec.feature.cover.sview");
        sIsNfcLedCoverSystemFeatureEnabled =
                context.getPackageManager().hasSystemFeature("com.sec.feature.cover.nfcledcover");
        sIsClearCoverSystemFeatureEnabled =
                context.getPackageManager().hasSystemFeature("com.sec.feature.cover.clearcover");
        sIsNeonCoverSystemFeatureEnabled =
                context.getPackageManager().hasSystemFeature("com.sec.feature.cover.neoncover");
        sIsClearSideViewCoverSystemFeatureEnabled =
                context.getPackageManager()
                        .hasSystemFeature("com.sec.feature.cover.clearsideviewcover");
        sIsLEDBackCoverSystemFeatureEnabled =
                context.getPackageManager().hasSystemFeature("com.sec.feature.cover.ledbackcover");
        sIsMiniSviewWalletCoverSysltemFeatureEnabled =
                context.getPackageManager()
                        .hasSystemFeature("com.sec.feature.cover.minisviewwalletcover");
        sIsClearCameraViewCoverSystemFeatureEnabled =
                context.getPackageManager()
                        .hasSystemFeature("com.sec.feature.cover.clearcameraviewcover");
        sIsSystemFeatureQueried = true;
        if (isSupportCover()) {
            try {
                Class[] clsArr = new Class[0];
                intValue =
                        ((Integer)
                                        ICoverManager.class
                                                .getMethod("getVersion", null)
                                                .invoke(getService(), null))
                                .intValue();
            } catch (Exception e) {
                Log.w("ScoverManager", "getVersion failed : " + e);
            }
            ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                    intValue, "serviceVersion : ", "ScoverManager");
            sServiceVersion = intValue;
        }
        intValue = 16777216;
        ListPopupWindow$$ExternalSyntheticOutline0.m1m(
                intValue, "serviceVersion : ", "ScoverManager");
        sServiceVersion = intValue;
    }

    public static boolean isSupportCover() {
        return sIsFilpCoverSystemFeatureEnabled
                || sIsSViewCoverSystemFeatureEnabled
                || sIsClearCoverSystemFeatureEnabled
                || sIsNeonCoverSystemFeatureEnabled
                || sIsClearSideViewCoverSystemFeatureEnabled
                || sIsNfcLedCoverSystemFeatureEnabled
                || sIsLEDBackCoverSystemFeatureEnabled
                || sIsMiniSviewWalletCoverSysltemFeatureEnabled
                || sIsClearCameraViewCoverSystemFeatureEnabled;
    }

    public static boolean isSupportableVersion(int i) {
        int i2 = (i >> 16) & 255;
        int i3 = sServiceVersion;
        return ((i3 >> 24) & 255) >= 1
                && ((i3 >> 16) & 255) >= i2
                && (i3 & CustomDeviceManager.QUICK_PANEL_ALL) >= 0;
    }

    public final ScoverState getCoverState() {
        ScoverState scoverState;
        if (!isSupportCover()) {
            Log.w("ScoverManager", "getCoverState : This device is not supported cover");
            return null;
        }
        try {
            ICoverManager service = getService();
            if (service != null) {
                CoverState coverState = service.getCoverState();
                if (coverState != null) {
                    if (coverState.type == 255 && !coverState.switchState) {
                        Log.e(
                                "ScoverManager",
                                "getCoverState : type of cover is nfc smart cover and cover is"
                                    + " closed");
                        return null;
                    }
                    if (isSupportableVersion(R.animator.fade_in)) {
                        boolean z = coverState.switchState;
                        int i = coverState.type;
                        int i2 = coverState.color;
                        int i3 = coverState.widthPixel;
                        int i4 = coverState.heightPixel;
                        boolean z2 = coverState.attached;
                        boolean z3 = coverState.fakeCover;
                        int i5 = coverState.fotaMode;
                        scoverState = new ScoverState();
                        scoverState.mVisibleRect = new Rect();
                        scoverState.switchState = z;
                        scoverState.type = i;
                        scoverState.color = i2;
                        scoverState.widthPixel = i3;
                        scoverState.heightPixel = i4;
                        scoverState.attached = z2;
                        scoverState.fakeCover = z3;
                        scoverState.fotaMode = i5;
                    } else if (isSupportableVersion(R.array.emailAddressTypes)) {
                        boolean z4 = coverState.switchState;
                        int i6 = coverState.type;
                        int i7 = coverState.color;
                        int i8 = coverState.widthPixel;
                        int i9 = coverState.heightPixel;
                        boolean z5 = coverState.attached;
                        boolean z6 = coverState.fakeCover;
                        scoverState = new ScoverState();
                        scoverState.mVisibleRect = new Rect();
                        scoverState.switchState = z4;
                        scoverState.type = i6;
                        scoverState.color = i7;
                        scoverState.widthPixel = i8;
                        scoverState.heightPixel = i9;
                        scoverState.attached = z5;
                        scoverState.fakeCover = z6;
                        scoverState.fotaMode = 0;
                    } else if (isSupportableVersion(R.id.background)) {
                        boolean z7 = coverState.switchState;
                        int i10 = coverState.type;
                        int i11 = coverState.color;
                        int i12 = coverState.widthPixel;
                        int i13 = coverState.heightPixel;
                        boolean z8 = coverState.attached;
                        scoverState = new ScoverState();
                        scoverState.mVisibleRect = new Rect();
                        scoverState.switchState = z7;
                        scoverState.type = i10;
                        scoverState.color = i11;
                        scoverState.widthPixel = i12;
                        scoverState.heightPixel = i13;
                        scoverState.attached = z8;
                        scoverState.fakeCover = false;
                        scoverState.fotaMode = 0;
                    } else if (isSupportableVersion(R.attr.theme)) {
                        boolean z9 = coverState.switchState;
                        int i14 = coverState.type;
                        int i15 = coverState.color;
                        int i16 = coverState.widthPixel;
                        int i17 = coverState.heightPixel;
                        boolean z10 = coverState.attached;
                        scoverState = new ScoverState();
                        scoverState.mVisibleRect = new Rect();
                        scoverState.switchState = z9;
                        scoverState.type = i14;
                        scoverState.color = i15;
                        scoverState.widthPixel = i16;
                        scoverState.heightPixel = i17;
                        scoverState.attached = z10;
                        scoverState.fakeCover = false;
                        scoverState.fotaMode = 0;
                    } else {
                        boolean z11 = coverState.switchState;
                        int i18 = coverState.type;
                        int i19 = coverState.color;
                        int i20 = coverState.widthPixel;
                        int i21 = coverState.heightPixel;
                        scoverState = new ScoverState();
                        scoverState.mVisibleRect = new Rect();
                        scoverState.switchState = z11;
                        scoverState.type = i18;
                        scoverState.color = i19;
                        scoverState.widthPixel = i20;
                        scoverState.heightPixel = i21;
                        scoverState.attached = false;
                        scoverState.fakeCover = false;
                        scoverState.fotaMode = 0;
                    }
                    if (isSupportableVersion(R.interpolator.accelerate_quad)) {
                        scoverState.mVisibleRect = new Rect(coverState.getVisibleRect());
                    }
                    return scoverState;
                }
                Log.e("ScoverManager", "getCoverState : coverState is null");
            }
        } catch (RemoteException e) {
            Log.e("ScoverManager", "RemoteException in getCoverState: ", e);
        }
        return null;
    }

    public final synchronized ICoverManager getService() {
        try {
            if (this.mService == null) {
                ICoverManager asInterface =
                        ICoverManager.Stub.asInterface(ServiceManager.getService("cover"));
                this.mService = asInterface;
                if (asInterface == null) {
                    Slog.w("ScoverManager", "warning: no COVER_MANAGER_SERVICE");
                }
            }
        } catch (Throwable th) {
            throw th;
        }
        return this.mService;
    }
}
