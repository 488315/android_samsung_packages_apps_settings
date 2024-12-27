package com.android.settings.webview;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.Flags;
import android.webkit.UserPackage;
import android.webkit.WebViewFactory;
import android.webkit.WebViewProviderInfo;
import android.webkit.WebViewUpdateManager;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.applications.defaultapps.DefaultAppPickerFragment;
import com.android.settingslib.applications.DefaultAppInfo;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class WebViewAppPicker extends DefaultAppPickerFragment {
    public WebViewUpdateServiceWrapper mWebViewUpdateServiceWrapper;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WebViewAppInfo extends DefaultAppInfo {
        @Override // com.android.settingslib.applications.DefaultAppInfo,
                  // com.android.settingslib.widget.CandidateInfo
        public final CharSequence loadLabel() {
            String str;
            try {
                str = this.mPm.getPackageInfo(this.packageItemInfo.packageName, 0).versionName;
            } catch (PackageManager.NameNotFoundException unused) {
                str = ApnSettings.MVNO_NONE;
            }
            return String.format("%s %s", super.loadLabel(), str);
        }
    }

    public DefaultAppInfo createDefaultAppInfo(
            Context context,
            PackageManager packageManager,
            PackageItemInfo packageItemInfo,
            String str) {
        return new WebViewAppInfo(
                context,
                packageManager,
                this.mUserId,
                packageItemInfo,
                str,
                TextUtils.isEmpty(str));
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        WebViewProviderInfo[] webViewProviderInfoArr;
        ArrayList arrayList = new ArrayList();
        Context context = getContext();
        WebViewUpdateServiceWrapper webViewUpdateServiceWrapper = getWebViewUpdateServiceWrapper();
        webViewUpdateServiceWrapper.getClass();
        try {
            webViewProviderInfoArr =
                    Flags.updateServiceIpcWrapper()
                            ? ((WebViewUpdateManager)
                                            context.getSystemService(WebViewUpdateManager.class))
                                    .getValidWebViewPackages()
                            : WebViewFactory.getUpdateService().getValidWebViewPackages();
        } catch (Exception unused) {
            webViewProviderInfoArr = null;
        }
        ArrayList arrayList2 = new ArrayList();
        for (WebViewProviderInfo webViewProviderInfo : webViewProviderInfoArr) {
            try {
                arrayList2.add(
                        context.getPackageManager()
                                .getApplicationInfo(webViewProviderInfo.packageName, 4194304));
            } catch (PackageManager.NameNotFoundException unused2) {
            }
        }
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            ApplicationInfo applicationInfo = (ApplicationInfo) it.next();
            arrayList.add(
                    createDefaultAppInfo(
                            context,
                            this.mPm,
                            applicationInfo,
                            getDisabledReason(
                                    webViewUpdateServiceWrapper,
                                    context,
                                    applicationInfo.packageName)));
        }
        return arrayList;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        getWebViewUpdateServiceWrapper().getClass();
        PackageInfo currentWebViewPackage = WebViewUpdateServiceWrapper.getCurrentWebViewPackage();
        if (currentWebViewPackage == null) {
            return null;
        }
        return currentWebViewPackage.packageName;
    }

    public String getDisabledReason(
            WebViewUpdateServiceWrapper webViewUpdateServiceWrapper, Context context, String str) {
        webViewUpdateServiceWrapper.getClass();
        for (UserPackage userPackage : UserPackage.getPackageInfosAllUsers(context, str, 4194304)) {
            if (!userPackage.isInstalledPackage()) {
                return context.getString(
                        R.string.webview_uninstalled_for_user, userPackage.getUserInfo().name);
            }
            if (!userPackage.isEnabledPackage()) {
                return context.getString(
                        R.string.webview_disabled_for_user, userPackage.getUserInfo().name);
            }
        }
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 405;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.webview_app_settings;
    }

    public final WebViewUpdateServiceWrapper getWebViewUpdateServiceWrapper() {
        if (this.mWebViewUpdateServiceWrapper == null) {
            setWebViewUpdateServiceWrapper(new WebViewUpdateServiceWrapper());
        }
        return this.mWebViewUpdateServiceWrapper;
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPickerFragment,
              // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        if (this.mUserManager.isAdminUser()) {
            return;
        }
        getActivity().finish();
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void onSelectionPerformed(boolean z) {
        if (!z) {
            WebViewUpdateServiceWrapper webViewUpdateServiceWrapper =
                    getWebViewUpdateServiceWrapper();
            FragmentActivity activity = getActivity();
            webViewUpdateServiceWrapper.getClass();
            Toast.makeText(activity, R.string.select_webview_provider_toast_text, 0).show();
            updateCandidates();
            return;
        }
        FragmentActivity activity2 = getActivity();
        Intent intent = activity2 == null ? null : activity2.getIntent();
        if (intent == null || !"android.settings.WEBVIEW_SETTINGS".equals(intent.getAction())) {
            return;
        }
        getActivity().finish();
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        getWebViewUpdateServiceWrapper().getClass();
        try {
            return Flags.updateServiceIpcWrapper()
                    ? str.equals(WebViewUpdateManager.getInstance().changeProviderAndSetting(str))
                    : str.equals(WebViewFactory.getUpdateService().changeProviderAndSetting(str));
        } catch (Exception e) {
            Log.e("WVUSWrapper", "Exception when trying to change provider to " + str, e);
            return false;
        }
    }

    public void setWebViewUpdateServiceWrapper(
            WebViewUpdateServiceWrapper webViewUpdateServiceWrapper) {
        this.mWebViewUpdateServiceWrapper = webViewUpdateServiceWrapper;
    }
}
