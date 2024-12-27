package com.android.settings.location;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.os.Looper;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;

import androidx.preference.Preference;

import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settingslib.location.InjectedSetting;
import com.android.settingslib.location.SettingsInjector$StatusLoadingHandler;

import com.samsung.android.feature.SemCarrierFeature;
import com.samsung.android.knox.net.apn.ApnSettings;

import org.xmlpull.v1.XmlPullParserException;

import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class AppSettingsInjector {
    public final Context mContext;
    public final SettingsInjector$StatusLoadingHandler mHandler;
    public final int mMetricsCategory;
    public final SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public final Set mSettings;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SecServiceSettingClickedListener
            implements Preference.OnPreferenceClickListener {
        public final InjectedSetting mInfo;

        public SecServiceSettingClickedListener(InjectedSetting injectedSetting) {
            this.mInfo = injectedSetting;
        }

        @Override // androidx.preference.Preference.OnPreferenceClickListener
        public final boolean onPreferenceClick(Preference preference) {
            Intent intent = new Intent();
            InjectedSetting injectedSetting = this.mInfo;
            intent.setClassName(injectedSetting.packageName, injectedSetting.settingsActivity);
            AppSettingsInjector appSettingsInjector = AppSettingsInjector.this;
            appSettingsInjector.mMetricsFeatureProvider.logStartedIntent(
                    appSettingsInjector.mMetricsCategory, intent);
            appSettingsInjector.mContext.startActivityAsUser(intent, injectedSetting.mUserHandle);
            return true;
        }
    }

    public AppSettingsInjector(Context context, int i) {
        this.mContext = context;
        HashSet hashSet = new HashSet();
        this.mSettings = hashSet;
        SettingsInjector$StatusLoadingHandler settingsInjector$StatusLoadingHandler =
                new SettingsInjector$StatusLoadingHandler(Looper.getMainLooper());
        settingsInjector$StatusLoadingHandler.mSettingsToLoad = new ArrayDeque();
        settingsInjector$StatusLoadingHandler.mSettingsBeingLoaded = new ArraySet();
        settingsInjector$StatusLoadingHandler.mAllSettings = new WeakReference(hashSet);
        this.mHandler = settingsInjector$StatusLoadingHandler;
        this.mMetricsCategory = i;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    public static InjectedSetting parseAttributes(
            String str,
            String str2,
            UserHandle userHandle,
            Resources resources,
            AttributeSet attributeSet) {
        TypedArray obtainAttributes =
                resources.obtainAttributes(attributeSet, R.styleable.SettingInjectorService);
        try {
            String string = obtainAttributes.getString(1);
            int resourceId = obtainAttributes.getResourceId(0, 0);
            String string2 = obtainAttributes.getString(2);
            String string3 = obtainAttributes.getString(3);
            if (Log.isLoggable("SettingsInjector", 3)) {
                Log.d(
                        "SettingsInjector",
                        "parsed title: "
                                + string
                                + ", iconId: "
                                + resourceId
                                + ", settingsActivity: "
                                + string2);
            }
            InjectedSetting.Builder builder = new InjectedSetting.Builder();
            builder.mPackageName = str;
            builder.mClassName = str2;
            builder.mTitle = string;
            builder.mIconId = resourceId;
            builder.mUserHandle = userHandle;
            builder.mSettingsActivity = string2;
            builder.mUserRestriction = string3;
            InjectedSetting build = builder.build();
            obtainAttributes.recycle();
            return build;
        } catch (Throwable th) {
            obtainAttributes.recycle();
            throw th;
        }
    }

    public static InjectedSetting parseServiceInfo(
            ResolveInfo resolveInfo, UserHandle userHandle, PackageManager packageManager) {
        int next;
        ServiceInfo serviceInfo = resolveInfo.serviceInfo;
        XmlResourceParser xmlResourceParser = null;
        if ((serviceInfo.applicationInfo.flags & 1) == 0 && Log.isLoggable("SettingsInjector", 5)) {
            Log.w(
                    "SettingsInjector",
                    "Ignoring attempt to inject setting from app not in system image: "
                            + resolveInfo);
            return null;
        }
        try {
            try {
                XmlResourceParser loadXmlMetaData =
                        serviceInfo.loadXmlMetaData(
                                packageManager, "android.location.SettingInjectorService");
                if (loadXmlMetaData == null) {
                    throw new XmlPullParserException(
                            "No android.location.SettingInjectorService meta-data for "
                                    + resolveInfo
                                    + ": "
                                    + serviceInfo);
                }
                AttributeSet asAttributeSet = Xml.asAttributeSet(loadXmlMetaData);
                do {
                    next = loadXmlMetaData.next();
                    if (next == 1) {
                        break;
                    }
                } while (next != 2);
                if (!"injected-location-setting".equals(loadXmlMetaData.getName())) {
                    throw new XmlPullParserException(
                            "Meta-data does not start with injected-location-setting tag");
                }
                InjectedSetting parseAttributes =
                        parseAttributes(
                                serviceInfo.packageName,
                                serviceInfo.name,
                                userHandle,
                                packageManager.getResourcesForApplication(serviceInfo.packageName),
                                asAttributeSet);
                loadXmlMetaData.close();
                return parseAttributes;
            } catch (PackageManager.NameNotFoundException unused) {
                throw new XmlPullParserException(
                        "Unable to load resources for package " + serviceInfo.packageName);
            }
        } catch (Throwable th) {
            if (0 != 0) {
                xmlResourceParser.close();
            }
            throw th;
        }
    }

    public static boolean supportGoogleLocationAccuracyService() {
        SemCarrierFeature semCarrierFeature = SemCarrierFeature.getInstance();
        String str = ApnSettings.MVNO_NONE;
        String string =
                semCarrierFeature.getString(
                        0, "CarrierFeature_GPS_ConfigAgpsSetting", ApnSettings.MVNO_NONE, false);
        if (string == null || string.length() == 0) {
            try {
                str = SystemProperties.get("persist.omc.sales_code");
                if (TextUtils.isEmpty(str)) {
                    str = SystemProperties.get("ro.csc.sales_code");
                    if (TextUtils.isEmpty(str)) {
                        str = SystemProperties.get("ril.sales_code");
                    }
                }
            } catch (Exception unused) {
            }
            string = str;
        }
        return ("CHN".equals(string)
                        || "CHC".equals(string)
                        || "CHU".equals(string)
                        || "CTC".equals(string)
                        || "CHM".equals(string))
                ? false
                : true;
    }

    public final void reloadStatusMessages() {
        if (Log.isLoggable("SettingsInjector", 3)) {
            Log.d("SettingsInjector", "reloadingStatusMessages: " + this.mSettings);
        }
        SettingsInjector$StatusLoadingHandler settingsInjector$StatusLoadingHandler = this.mHandler;
        settingsInjector$StatusLoadingHandler.sendMessage(
                settingsInjector$StatusLoadingHandler.obtainMessage(1));
    }
}
