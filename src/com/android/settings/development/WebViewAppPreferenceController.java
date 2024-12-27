package com.android.settings.development;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;

import androidx.preference.Preference;

import com.android.settings.R;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.webview.WebViewUpdateServiceWrapper;
import com.android.settingslib.applications.DefaultAppInfo;
import com.android.settingslib.development.DeveloperOptionsPreferenceController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class WebViewAppPreferenceController extends DeveloperOptionsPreferenceController
        implements PreferenceControllerMixin {
    public final PackageManager mPackageManager;
    public final WebViewUpdateServiceWrapper mWebViewUpdateServiceWrapper;

    public WebViewAppPreferenceController(Context context) {
        super(context);
        this.mPackageManager = context.getPackageManager();
        this.mWebViewUpdateServiceWrapper = new WebViewUpdateServiceWrapper();
    }

    public DefaultAppInfo getDefaultAppInfo() {
        this.mWebViewUpdateServiceWrapper.getClass();
        PackageInfo currentWebViewPackage = WebViewUpdateServiceWrapper.getCurrentWebViewPackage();
        return new DefaultAppInfo(
                this.mContext,
                this.mPackageManager,
                UserHandle.myUserId(),
                currentWebViewPackage == null ? null : currentWebViewPackage.applicationInfo);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "select_webview_provider";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        CharSequence loadLabel = getDefaultAppInfo().loadLabel();
        if (!TextUtils.isEmpty(loadLabel)) {
            this.mPreference.setSummary(loadLabel);
        } else {
            Log.d("WebViewAppPrefCtrl", "No default app");
            this.mPreference.setSummary(R.string.app_list_preference_none);
        }
    }
}
