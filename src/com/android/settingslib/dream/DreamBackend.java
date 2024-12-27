package com.android.settingslib.dream;

import android.R;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.dreams.DreamService;
import android.service.dreams.IDreamManager;
import android.util.Log;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.FrameworkStatsLog;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class DreamBackend {
    public static DreamBackend sInstance;
    public final DreamInfoComparator mComparator;
    public final Context mContext;
    public final Set mDisabledDreams;
    public final IDreamManager mDreamManager;
    public final boolean mDreamsActivatedOnDockByDefault;
    public final boolean mDreamsActivatedOnSleepByDefault;
    public final boolean mDreamsEnabledByDefault;
    public final List mLoggableDreamPrefixes;
    public Set mSupportedComplications;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DreamInfo {
        public CharSequence caption;
        public ComponentName componentName;
        public CharSequence description;
        public int dreamCategory;
        public Drawable icon;
        public boolean isActive;
        public Drawable previewImage;
        public ComponentName settingsComponentName;
        public boolean supportsComplications;

        public final String toString() {
            StringBuilder sb = new StringBuilder(DreamInfo.class.getSimpleName());
            sb.append('[');
            sb.append(this.caption);
            if (this.isActive) {
                sb.append(",active");
            }
            sb.append(',');
            sb.append(this.componentName);
            if (this.settingsComponentName != null) {
                sb.append("settings=");
                sb.append(this.settingsComponentName);
            }
            sb.append(']');
            return sb.toString();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DreamInfoComparator implements Comparator {
        public final ComponentName mDefaultDream;

        public DreamInfoComparator(ComponentName componentName) {
            this.mDefaultDream = componentName;
        }

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            return sortKey((DreamInfo) obj).compareTo(sortKey((DreamInfo) obj2));
        }

        public final String sortKey(DreamInfo dreamInfo) {
            StringBuilder sb = new StringBuilder();
            sb.append(dreamInfo.componentName.equals(this.mDefaultDream) ? '0' : '1');
            sb.append(dreamInfo.caption);
            return sb.toString();
        }
    }

    public DreamBackend(Context context) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        Resources resources = applicationContext.getResources();
        IDreamManager asInterface =
                IDreamManager.Stub.asInterface(ServiceManager.getService("dreams"));
        this.mDreamManager = asInterface;
        ComponentName componentName = null;
        if (asInterface != null) {
            try {
                componentName =
                        asInterface.getDefaultDreamComponentForUser(applicationContext.getUserId());
            } catch (RemoteException e) {
                Log.w("DreamBackend", "Failed to get default dream", e);
            }
        }
        this.mComparator = new DreamInfoComparator(componentName);
        this.mDreamsEnabledByDefault =
                resources.getBoolean(R.bool.config_eap_sim_based_auth_supported);
        this.mDreamsActivatedOnSleepByDefault = resources.getBoolean(R.bool.config_dreamsSupported);
        this.mDreamsActivatedOnDockByDefault =
                resources.getBoolean(R.bool.config_dreamsOnlyEnabledForDockUser);
        this.mDisabledDreams =
                (Set)
                        Arrays.stream(resources.getStringArray(R.array.config_tether_usb_regexs))
                                .map(new DreamBackend$$ExternalSyntheticLambda0())
                                .collect(Collectors.toSet());
        this.mLoggableDreamPrefixes =
                Arrays.stream(
                                resources.getStringArray(
                                        R.array.wfcOperatorErrorNotificationMessages))
                        .toList();
        this.mSupportedComplications =
                (Set)
                        Arrays.stream(resources.getIntArray(17236324))
                                .boxed()
                                .collect(Collectors.toSet());
    }

    public static DreamBackend getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DreamBackend(context);
        }
        return sInstance;
    }

    public final ComponentName getActiveDream() {
        IDreamManager iDreamManager = this.mDreamManager;
        if (iDreamManager == null) {
            return null;
        }
        try {
            ComponentName[] dreamComponents = iDreamManager.getDreamComponents();
            if (dreamComponents == null || dreamComponents.length <= 0) {
                return null;
            }
            return dreamComponents[0];
        } catch (RemoteException e) {
            Log.w("DreamBackend", "Failed to get active dream", e);
            return null;
        }
    }

    public final boolean getComplicationsEnabled() {
        return Settings.Secure.getInt(
                        this.mContext.getContentResolver(), "screensaver_complications_enabled", 1)
                == 1;
    }

    public final List getDreamInfos() {
        ComponentName componentName;
        ApplicationInfo applicationInfo;
        ComponentName activeDream = getActiveDream();
        PackageManager packageManager = this.mContext.getPackageManager();
        List<ResolveInfo> queryIntentServices =
                packageManager.queryIntentServices(
                        new Intent("android.service.dreams.DreamService"), 128);
        ArrayList arrayList = new ArrayList(queryIntentServices.size());
        for (ResolveInfo resolveInfo : queryIntentServices) {
            if (resolveInfo == null || resolveInfo.serviceInfo == null) {
                componentName = null;
            } else {
                ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
            }
            if (componentName != null && !this.mDisabledDreams.contains(componentName)) {
                DreamInfo dreamInfo = new DreamInfo();
                dreamInfo.supportsComplications = false;
                dreamInfo.caption = resolveInfo.loadLabel(packageManager);
                dreamInfo.icon = resolveInfo.loadIcon(packageManager);
                String str = resolveInfo.resolvePackageName;
                if (str == null) {
                    ServiceInfo serviceInfo2 = resolveInfo.serviceInfo;
                    String str2 = serviceInfo2.packageName;
                    applicationInfo = serviceInfo2.applicationInfo;
                    str = str2;
                } else {
                    applicationInfo = null;
                }
                int i = resolveInfo.serviceInfo.descriptionRes;
                dreamInfo.description =
                        i != 0 ? packageManager.getText(str, i, applicationInfo) : null;
                dreamInfo.componentName = componentName;
                dreamInfo.isActive = componentName.equals(activeDream);
                DreamService.DreamMetadata dreamMetadata =
                        DreamService.getDreamMetadata(
                                this.mContext.getPackageManager(), resolveInfo.serviceInfo);
                if (dreamMetadata != null) {
                    dreamInfo.settingsComponentName = dreamMetadata.settingsActivity;
                    dreamInfo.previewImage = dreamMetadata.previewImage;
                    dreamInfo.supportsComplications = dreamMetadata.showComplications;
                    dreamInfo.dreamCategory = dreamMetadata.dreamCategory;
                }
                arrayList.add(dreamInfo);
            }
        }
        arrayList.sort(this.mComparator);
        return arrayList;
    }

    public final boolean getHomeControlsEnabled() {
        return Settings.Secure.getInt(
                                this.mContext.getContentResolver(), "lockscreen_show_controls", 0)
                        == 1
                && Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "screensaver_home_controls_enabled",
                                1)
                        == 1;
    }

    public final int getWhenToDreamSetting() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        boolean z = this.mDreamsActivatedOnDockByDefault;
        int i = Settings.Secure.getInt(contentResolver, "screensaver_activate_on_dock", z ? 1 : 0);
        boolean z2 = this.mDreamsActivatedOnSleepByDefault;
        if (i == 1
                && Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "screensaver_activate_on_sleep",
                                z2 ? 1 : 0)
                        == 1) {
            return 2;
        }
        if (Settings.Secure.getInt(
                        this.mContext.getContentResolver(),
                        "screensaver_activate_on_dock",
                        z ? 1 : 0)
                == 1) {
            return 1;
        }
        return Settings.Secure.getInt(
                                this.mContext.getContentResolver(),
                                "screensaver_activate_on_sleep",
                                z2 ? 1 : 0)
                        == 1
                ? 0
                : 3;
    }

    public final boolean isEnabled() {
        return Settings.Secure.getInt(
                        this.mContext.getContentResolver(),
                        "screensaver_enabled",
                        this.mDreamsEnabledByDefault ? 1 : 0)
                == 1;
    }

    public final void logDreamSettingChangeToStatsd(int i) {
        String flattenToShortString;
        int myUserId = UserHandle.myUserId();
        boolean isEnabled = isEnabled();
        ComponentName activeDream = getActiveDream();
        if (activeDream != null) {
            flattenToShortString = activeDream.flattenToShortString();
            int i2 = 0;
            while (true) {
                if (i2 >= this.mLoggableDreamPrefixes.size()) {
                    flattenToShortString = "other";
                    break;
                } else if (flattenToShortString.startsWith(
                        (String) this.mLoggableDreamPrefixes.get(i2))) {
                    break;
                } else {
                    i2++;
                }
            }
        } else {
            flattenToShortString = ApnSettings.MVNO_NONE;
        }
        String str = flattenToShortString;
        int whenToDreamSetting = getWhenToDreamSetting();
        FrameworkStatsLog.write(
                705,
                myUserId,
                isEnabled,
                str,
                whenToDreamSetting != 0
                        ? whenToDreamSetting != 1 ? whenToDreamSetting != 2 ? 0 : 3 : 2
                        : 1,
                getComplicationsEnabled(),
                getHomeControlsEnabled(),
                i);
    }

    public final void setActiveDream(ComponentName componentName) {
        IDreamManager iDreamManager = this.mDreamManager;
        if (iDreamManager == null) {
            return;
        }
        try {
            ComponentName[] componentNameArr = {componentName};
            if (componentName == null) {
                componentNameArr = null;
            }
            iDreamManager.setDreamComponents(componentNameArr);
            logDreamSettingChangeToStatsd(2);
        } catch (RemoteException e) {
            Log.w("DreamBackend", "Failed to set active dream to " + componentName, e);
        }
    }

    @VisibleForTesting
    public void setSupportedComplications(Set<Integer> set) {
        this.mSupportedComplications = set;
    }
}
