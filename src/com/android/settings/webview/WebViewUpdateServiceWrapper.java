package com.android.settings.webview;

import android.content.pm.PackageInfo;
import android.util.Log;
import android.webkit.Flags;
import android.webkit.WebViewFactory;
import android.webkit.WebViewUpdateManager;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WebViewUpdateServiceWrapper {
    public static PackageInfo getCurrentWebViewPackage() {
        try {
            return Flags.updateServiceIpcWrapper()
                    ? WebViewUpdateManager.getInstance().getCurrentWebViewPackage()
                    : WebViewFactory.getUpdateService().getCurrentWebViewPackage();
        } catch (Exception e) {
            Log.e("WVUSWrapper", e.toString());
            return null;
        }
    }
}
