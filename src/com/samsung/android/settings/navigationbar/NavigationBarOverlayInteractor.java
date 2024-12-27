package com.samsung.android.settings.navigationbar;

import android.content.Context;
import android.content.om.IOverlayManager;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.settings.Rune;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public final class NavigationBarOverlayInteractor {
    public final Context mContext;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public final IOverlayManager mOverlayManager = IOverlayManager.Stub.asInterface(ServiceManager.getService("overlay"));
    public NavigationBarOverlayInteractor$$ExternalSyntheticLambda0 mRunnable;

    public NavigationBarOverlayInteractor(Context context) {
        this.mContext = context;
    }

    public final String getGestureOverlayPackageName() {
        int i = Settings.Global.getInt(this.mContext.getContentResolver(), "navigation_bar_gesture_detail_type", 1);
        boolean z = Settings.Global.getInt(this.mContext.getContentResolver(), "navigation_bar_gesture_hint", 1) != 0;
        return i == 1 ? z ? "com.android.internal.systemui.navbar.gestural" : "com.samsung.internal.systemui.navbar.gestural_no_hint" : z ? "com.samsung.internal.systemui.navbar.sec_gestural" : "com.samsung.internal.systemui.navbar.sec_gestural_no_hint";
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.navigationbar.NavigationBarOverlayInteractor$$ExternalSyntheticLambda0, java.lang.Runnable] */
    public final void setInteractionMode(final String str) {
        NavigationBarOverlayInteractor$$ExternalSyntheticLambda0 navigationBarOverlayInteractor$$ExternalSyntheticLambda0 = this.mRunnable;
        Handler handler = this.mHandler;
        if (navigationBarOverlayInteractor$$ExternalSyntheticLambda0 != null) {
            handler.removeCallbacks(navigationBarOverlayInteractor$$ExternalSyntheticLambda0);
        }
        ?? r0 = new Runnable() { // from class: com.samsung.android.settings.navigationbar.NavigationBarOverlayInteractor$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                NavigationBarOverlayInteractor navigationBarOverlayInteractor = NavigationBarOverlayInteractor.this;
                String str2 = str;
                navigationBarOverlayInteractor.getClass();
                try {
                    if (Rune.supportNavigationBarForHardKey() && "com.android.internal.systemui.navbar.threebutton".equals(str2)) {
                        navigationBarOverlayInteractor.mOverlayManager.setEnabled(navigationBarOverlayInteractor.getGestureOverlayPackageName(), false, -2);
                        Log.d("NavigationBarOverlayInteractor", "setInteractionMode() hard key model three button mode");
                    } else {
                        navigationBarOverlayInteractor.mOverlayManager.setEnabledExclusiveInCategory(str2, -2);
                        Log.d("NavigationBarOverlayInteractor", "setInteractionMode() packageName : " + str2);
                    }
                    if ("com.android.internal.systemui.navbar.gestural".equals(str2) || "com.samsung.internal.systemui.navbar.gestural_no_hint".equals(str2) || "com.samsung.internal.systemui.navbar.sec_gestural_no_hint".equals(str2)) {
                        NavigationBarSettingsUtil.setSensitivityInsetScale(navigationBarOverlayInteractor.mContext);
                    }
                } catch (RemoteException e) {
                    Log.e("NavigationBarOverlayInteractor", "An error occurred while set interaction mode: " + str2);
                    throw e.rethrowFromSystemServer();
                }
            }
        };
        this.mRunnable = r0;
        handler.postDelayed(r0, 300L);
    }

    public final void setInteractionMode() {
        String gestureOverlayPackageName;
        if (Settings.Global.getInt(this.mContext.getContentResolver(), "navigation_bar_gesture_while_hidden", 0) == 0) {
            gestureOverlayPackageName = "com.android.internal.systemui.navbar.threebutton";
        } else {
            gestureOverlayPackageName = getGestureOverlayPackageName();
            Log.secD("NavigationBarOverlayInteractor", "getPackageName() : ".concat(gestureOverlayPackageName));
        }
        setInteractionMode(gestureOverlayPackageName);
    }
}
