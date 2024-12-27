package com.android.settings.localepicker;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LocaleManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.LocaleList;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.core.app.NotificationCompat$Builder;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.FragmentManagerImpl;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.app.LocalePickerWithRegion;
import com.android.internal.app.LocaleStore;
import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.applications.AppLocaleUtil;
import com.android.settings.applications.appinfo.AppLocaleDetails;
import com.android.settings.biometrics.BiometricsEnrollEnrolling$$ExternalSyntheticOutline0;
import com.android.settings.core.SettingsBaseActivity;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.overlay.FeatureFactoryImpl;

import com.samsung.android.knox.ex.peripheral.PeripheralConstants;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;

import java.util.WeakHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppLocalePickerActivity extends SettingsBaseActivity
        implements LocalePickerWithRegion.LocaleSelectedListener, MenuItem.OnActionExpandListener {
    public View mAppLocaleDetailContainer;
    public AppLocaleDetails mAppLocaleDetails;
    public LocalePickerWithRegion mLocalePickerWithRegion;
    public SettingsMetricsFeatureProvider mMetricsFeatureProvider;
    public NotificationController mNotificationController;
    public String mPackageName;

    public final PendingIntent createPendingIntent(int i, String str, boolean z) {
        Intent intent =
                z
                        ? new Intent(this, (Class<?>) NotificationCancelReceiver.class)
                        : new Intent(this, (Class<?>) NotificationActionActivity.class)
                                .setFlags(268468224);
        intent.putExtra("app_locale", str).putExtra("notification_id", i);
        int elapsedRealtimeNanos = (int) SystemClock.elapsedRealtimeNanos();
        return z
                ? PendingIntent.getBroadcast(this, elapsedRealtimeNanos, intent, 201326592)
                : PendingIntent.getActivity(this, elapsedRealtimeNanos, intent, 201326592);
    }

    @Override // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        boolean z;
        super.onCreate(bundle);
        Uri data = getIntent().getData();
        if (data == null) {
            Log.d("AppLocalePickerActivity", "There is no uri data.");
            finish();
            return;
        }
        String schemeSpecificPart = data.getSchemeSpecificPart();
        this.mPackageName = schemeSpecificPart;
        if (TextUtils.isEmpty(schemeSpecificPart)) {
            Log.d("AppLocalePickerActivity", "There is no package name.");
            finish();
            return;
        }
        try {
            PackageManager packageManager = getPackageManager();
            z =
                    AppLocaleUtil.canDisplayLocaleUi(
                            this,
                            packageManager.getApplicationInfo(this.mPackageName, 0),
                            packageManager.queryIntentActivities(
                                    AppLocaleUtil.LAUNCHER_ENTRY_INTENT, 128));
        } catch (PackageManager.NameNotFoundException unused) {
            MainClear$$ExternalSyntheticOutline0.m(
                    new StringBuilder("Unable to find info for package: "),
                    this.mPackageName,
                    "AppLocalePickerActivity");
            z = false;
        }
        if (!z) {
            Log.w("AppLocalePickerActivity", "Not allow to display Locale Settings UI.");
            finish();
            return;
        }
        setTitle(R.string.app_locale_picker_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        disableExtendedAppBar();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
        this.mNotificationController = NotificationController.getInstance(this);
        this.mLocalePickerWithRegion =
                LocalePickerWithRegion.createLanguagePicker(
                        this, this, false, (LocaleList) null, this.mPackageName, this);
        String str = this.mPackageName;
        int userId = getUserId();
        AppLocaleDetails appLocaleDetails = new AppLocaleDetails();
        Bundle bundle2 = new Bundle();
        bundle2.putString("package", str);
        bundle2.putInt(NetworkAnalyticsConstants.DataPoints.UID, userId);
        appLocaleDetails.setArguments(bundle2);
        this.mAppLocaleDetails = appLocaleDetails;
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setId(R.id.layout_app_locale_details);
        FragmentManagerImpl supportFragmentManager = getSupportFragmentManager();
        BackStackRecord m =
                BiometricsEnrollEnrolling$$ExternalSyntheticOutline0.m(
                        supportFragmentManager, supportFragmentManager);
        m.replace(R.id.layout_app_locale_details, this.mAppLocaleDetails, null);
        m.commitInternal(false);
        this.mAppLocaleDetailContainer = frameLayout;
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.registerFragmentLifecycleCallbacks(
                new FragmentManager
                        .FragmentLifecycleCallbacks() { // from class:
                                                        // com.android.settings.localepicker.AppLocalePickerActivity.1
                    @Override // android.app.FragmentManager.FragmentLifecycleCallbacks
                    public final void onFragmentViewCreated(
                            FragmentManager fragmentManager2,
                            Fragment fragment,
                            View view,
                            Bundle bundle3) {
                        super.onFragmentViewCreated(fragmentManager2, fragment, view, bundle3);
                        ListView listView = (ListView) view.findViewById(android.R.id.list);
                        if (listView != null) {
                            listView.addHeaderView(
                                    AppLocalePickerActivity.this.mAppLocaleDetailContainer);
                        }
                    }
                },
                true);
        fragmentManager
                .beginTransaction()
                .setTransition(PeripheralConstants.ErrorCode.ERROR_PERIPHERAL_NOT_FOUND)
                .replace(R.id.content_frame, this.mLocalePickerWithRegion)
                .commit();
    }

    public final void onLocaleSelected(LocaleStore.LocaleInfo localeInfo) {
        if (localeInfo == null || localeInfo.getLocale() == null || localeInfo.isSystemLocale()) {
            setAppDefaultLocale(ApnSettings.MVNO_NONE);
        } else {
            if (localeInfo.isSuggested() && !localeInfo.isAppCurrentLocale()) {
                int i = localeInfo.isSuggestionOfType(64) ? 2 : 0;
                if (localeInfo.isSuggestionOfType(16)) {
                    i |= 4;
                }
                if (localeInfo.isSuggestionOfType(32)) {
                    i |= 8;
                }
                if (localeInfo.isSuggestionOfType(1)) {
                    i |= 1;
                }
                this.mMetricsFeatureProvider.action(this, 1837, i);
            }
            setAppDefaultLocale(localeInfo.getLocale().toLanguageTag());
            if (!localeInfo.isAppCurrentLocale()) {
                try {
                    String languageTag = localeInfo.getLocale().toLanguageTag();
                    if (this.mNotificationController.shouldTriggerNotification(
                            getPackageManager().getApplicationInfo(this.mPackageName, 128).uid,
                            languageTag)) {
                        NotificationInfo notificationInfo =
                                this.mNotificationController.mDataManager.getNotificationInfo(
                                        languageTag);
                        triggerNotification(
                                notificationInfo != null ? notificationInfo.mNotificationId : -1,
                                getString(
                                        R.string.title_system_locale_addition,
                                        new Object[] {localeInfo.getFullNameNative()}),
                                getString(R.string.desc_system_locale_addition),
                                languageTag);
                        this.mMetricsFeatureProvider.action(this, 1895, new Pair[0]);
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    MainClear$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Unable to find info for package: "),
                            this.mPackageName,
                            "AppLocalePickerActivity");
                }
            }
        }
        finish();
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionCollapse(MenuItem menuItem) {
        this.mAppBarLayout.setExpanded(false, false, true);
        RecyclerView listView = this.mAppLocaleDetails.getListView();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.setNestedScrollingEnabled(listView, true);
        ViewCompat.Api21Impl.setNestedScrollingEnabled(
                this.mLocalePickerWithRegion.getListView(), true);
        return true;
    }

    @Override // android.view.MenuItem.OnActionExpandListener
    public final boolean onMenuItemActionExpand(MenuItem menuItem) {
        this.mAppBarLayout.setExpanded(false, false, true);
        RecyclerView listView = this.mAppLocaleDetails.getListView();
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api21Impl.setNestedScrollingEnabled(listView, false);
        ViewCompat.Api21Impl.setNestedScrollingEnabled(
                this.mLocalePickerWithRegion.getListView(), false);
        return true;
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        super.onBackPressed();
        return true;
    }

    public final void setAppDefaultLocale(String str) {
        Log.d("AppLocalePickerActivity", "setAppDefaultLocale: " + str);
        LocaleManager localeManager = (LocaleManager) getSystemService(LocaleManager.class);
        if (localeManager == null) {
            Log.w(
                    "AppLocalePickerActivity",
                    "LocaleManager is null, cannot set default app locale");
        } else {
            localeManager.setApplicationLocales(this.mPackageName, LocaleList.forLanguageTags(str));
        }
    }

    public final void triggerNotification(int i, String str, String str2, String str3) {
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NotificationManager.class);
        if (notificationManager.getNotificationChannel("suggestion") == null) {
            NotificationChannel notificationChannel =
                    new NotificationChannel("suggestion", "Locale suggestion", 3);
            notificationChannel.setSound(null, null);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat$Builder notificationCompat$Builder =
                new NotificationCompat$Builder(this, "suggestion");
        notificationCompat$Builder.mNotification.icon = R.drawable.ic_settings_language;
        notificationCompat$Builder.setFlag(16);
        notificationCompat$Builder.mContentTitle =
                NotificationCompat$Builder.limitCharSequenceLength(str);
        notificationCompat$Builder.mContentText =
                NotificationCompat$Builder.limitCharSequenceLength(str2);
        notificationCompat$Builder.mVisibility = 1;
        notificationCompat$Builder.mContentIntent = createPendingIntent(i, str3, false);
        notificationCompat$Builder.mNotification.deleteIntent = createPendingIntent(i, str3, true);
        notificationManager.notify(i, notificationCompat$Builder.build());
    }
}
