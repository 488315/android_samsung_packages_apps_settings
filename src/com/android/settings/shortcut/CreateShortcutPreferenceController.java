package com.android.settings.shortcut;

import android.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceGroup;

import com.android.settings.Settings;
import com.android.settings.activityembedding.ActivityEmbeddingUtils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.gestures.OneHandedSettingsUtils;
import com.android.settings.network.SubscriptionUtil;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.wifi.WifiUtils;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;

import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CreateShortcutPreferenceController extends BasePreferenceController {
    static final String SHORTCUT_ID_PREFIX = "component-shortcut-";
    private static final String TAG = "CreateShortcutPrefCtrl";
    private final ConnectivityManager mConnectivityManager;
    private Activity mHost;
    private final MetricsFeatureProvider mMetricsFeatureProvider;
    private final PackageManager mPackageManager;
    private final ShortcutManager mShortcutManager;
    static final Intent SHORTCUT_PROBE =
            new Intent("android.intent.action.MAIN")
                    .addCategory("com.android.settings.SHORTCUT")
                    .addFlags(268435456);
    private static final Comparator<ResolveInfo> SHORTCUT_COMPARATOR =
            new CreateShortcutPreferenceController$$ExternalSyntheticLambda0();

    public CreateShortcutPreferenceController(Context context, String str) {
        super(context, str);
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.mShortcutManager = (ShortcutManager) context.getSystemService(ShortcutManager.class);
        this.mPackageManager = context.getPackageManager();
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mMetricsFeatureProvider = featureFactoryImpl.getMetricsFeatureProvider();
    }

    private static Intent buildShortcutIntent(Context context, ResolveInfo resolveInfo) {
        Intent flags = new Intent(SHORTCUT_PROBE).setFlags(335544320);
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        Intent className = flags.setClassName(activityInfo.packageName, activityInfo.name);
        if (ActivityEmbeddingUtils.isEmbeddingActivityEnabled(context)) {
            className.addFlags(536870912);
        }
        return className;
    }

    private static Bitmap createIcon(
            Context context, ApplicationInfo applicationInfo, int i, int i2, int i3) {
        ContextThemeWrapper contextThemeWrapper =
                new ContextThemeWrapper(context, R.style.Theme.Material);
        View inflate = LayoutInflater.from(contextThemeWrapper).inflate(i2, (ViewGroup) null);
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i3, 1073741824);
        inflate.measure(makeMeasureSpec, makeMeasureSpec);
        Bitmap createBitmap =
                Bitmap.createBitmap(
                        inflate.getMeasuredWidth(),
                        inflate.getMeasuredHeight(),
                        Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        try {
            Drawable drawable =
                    context.getPackageManager()
                            .getResourcesForApplication(applicationInfo)
                            .getDrawable(i, contextThemeWrapper.getTheme());
            if (drawable instanceof LayerDrawable) {
                drawable = ((LayerDrawable) drawable).getDrawable(1);
            }
            ((ImageView) inflate.findViewById(R.id.icon)).setImageDrawable(drawable);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w(
                    TAG,
                    "Cannot load icon from app " + applicationInfo + ", returning a default icon");
            ((ImageView) inflate.findViewById(R.id.icon))
                    .setImageIcon(
                            Icon.createWithResource(
                                    context, com.android.settings.R.drawable.ic_launcher_settings));
        }
        inflate.layout(0, 0, inflate.getMeasuredWidth(), inflate.getMeasuredHeight());
        inflate.draw(canvas);
        return createBitmap;
    }

    private static ShortcutInfo createShortcutInfo(
            Context context, Intent intent, ResolveInfo resolveInfo, CharSequence charSequence) {
        ApplicationInfo applicationInfo;
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        int i = activityInfo.icon;
        return new ShortcutInfo.Builder(
                        context, SHORTCUT_ID_PREFIX + intent.getComponent().flattenToShortString())
                .setShortLabel(charSequence)
                .setIntent(intent)
                .setIcon(
                        (i == 0 || (applicationInfo = activityInfo.applicationInfo) == null)
                                ? Icon.createWithResource(
                                        context,
                                        com.android.settings.R.drawable.ic_launcher_settings)
                                : Icon.createWithAdaptiveBitmap(
                                        createIcon(
                                                context,
                                                applicationInfo,
                                                i,
                                                com.android.settings.R.layout
                                                        .shortcut_badge_maskable,
                                                context.getResources()
                                                        .getDimensionPixelSize(
                                                                com.android.settings.R.dimen
                                                                        .shortcut_size_maskable))))
                .build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ int lambda$static$1(
            ResolveInfo resolveInfo, ResolveInfo resolveInfo2) {
        return resolveInfo.priority - resolveInfo2.priority;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$updateState$0(
            Context context, ResolveInfo resolveInfo, Preference preference) {
        if (this.mHost == null) {
            return false;
        }
        this.mHost.setResult(
                -1,
                createResultIntent(
                        buildShortcutIntent(context, resolveInfo),
                        resolveInfo,
                        preference.getTitle()));
        logCreateShortcut(resolveInfo);
        this.mHost.finish();
        return true;
    }

    private void logCreateShortcut(ResolveInfo resolveInfo) {
        ActivityInfo activityInfo;
        if (resolveInfo == null || (activityInfo = resolveInfo.activityInfo) == null) {
            return;
        }
        this.mMetricsFeatureProvider.action(this.mContext, 829, activityInfo.name);
    }

    public static void updateRestoredShortcuts(Context context) {
        ResolveInfo resolveActivity;
        ShortcutManager shortcutManager =
                (ShortcutManager) context.getSystemService(ShortcutManager.class);
        ArrayList arrayList = new ArrayList();
        for (ShortcutInfo shortcutInfo : shortcutManager.getPinnedShortcuts()) {
            if (shortcutInfo.getId().startsWith(SHORTCUT_ID_PREFIX)
                    && (resolveActivity =
                                    context.getPackageManager()
                                            .resolveActivity(shortcutInfo.getIntent(), 0))
                            != null) {
                arrayList.add(
                        createShortcutInfo(
                                context,
                                buildShortcutIntent(context, resolveActivity),
                                resolveActivity,
                                shortcutInfo.getShortLabel()));
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        shortcutManager.updateShortcuts(arrayList);
    }

    public boolean canShowDataUsage() {
        return SubscriptionUtil.isSimHardwareVisible(this.mContext)
                && !MobileNetworkUtils.isMobileNetworkUserRestricted(this.mContext);
    }

    public boolean canShowWifiHotspot() {
        return WifiUtils.canShowWifiHotspot(this.mContext);
    }

    public Intent createResultIntent(
            Intent intent, ResolveInfo resolveInfo, CharSequence charSequence) {
        Intent createShortcutResultIntent =
                this.mShortcutManager.createShortcutResultIntent(
                        createShortcutInfo(this.mContext, intent, resolveInfo, charSequence));
        if (createShortcutResultIntent == null) {
            createShortcutResultIntent = new Intent();
        }
        createShortcutResultIntent
                .putExtra(
                        "android.intent.extra.shortcut.ICON_RESOURCE",
                        Intent.ShortcutIconResource.fromContext(
                                this.mContext, com.android.settings.R.mipmap.ic_launcher_settings))
                .putExtra("android.intent.extra.shortcut.INTENT", intent)
                .putExtra("android.intent.extra.shortcut.NAME", charSequence);
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        int i = activityInfo.icon;
        if (i != 0) {
            Context context = this.mContext;
            createShortcutResultIntent.putExtra(
                    "android.intent.extra.shortcut.ICON",
                    createIcon(
                            context,
                            activityInfo.applicationInfo,
                            i,
                            com.android.settings.R.layout.shortcut_badge,
                            context.getResources()
                                    .getDimensionPixelSize(
                                            com.android.settings.R.dimen.shortcut_size)));
        }
        return createShortcutResultIntent;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 1;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    public List<ResolveInfo> queryShortcuts() {
        ArrayList arrayList = new ArrayList();
        List<ResolveInfo> queryIntentActivities =
                this.mPackageManager.queryIntentActivities(SHORTCUT_PROBE, 128);
        if (queryIntentActivities == null) {
            return null;
        }
        for (ResolveInfo resolveInfo : queryIntentActivities) {
            if (!resolveInfo.activityInfo.name.contains(
                            Settings.OneHandedSettingsActivity.class.getSimpleName())
                    || OneHandedSettingsUtils.isSupportOneHandedMode()) {
                if (!resolveInfo.activityInfo.name.endsWith(
                                Settings.TetherSettingsActivity.class.getSimpleName())
                        || this.mConnectivityManager.isTetheringSupported()) {
                    if (resolveInfo.activityInfo.name.endsWith(
                                    Settings.WifiTetherSettingsActivity.class.getSimpleName())
                            && !canShowWifiHotspot()) {
                        Log.d(TAG, "Skipping Wi-Fi hotspot settings:" + resolveInfo.activityInfo);
                    } else if (!resolveInfo.activityInfo.applicationInfo.isSystemApp()) {
                        Log.d(TAG, "Skipping non-system app: " + resolveInfo.activityInfo);
                    } else if (!resolveInfo.activityInfo.name.endsWith(
                                    Settings.DataUsageSummaryActivity.class.getSimpleName())
                            || canShowDataUsage()) {
                        arrayList.add(resolveInfo);
                    } else {
                        Log.d(TAG, "Skipping data usage settings:" + resolveInfo.activityInfo);
                    }
                }
            }
        }
        Collections.sort(arrayList, SHORTCUT_COMPARATOR);
        return arrayList;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    public void setActivity(Activity activity) {
        this.mHost = activity;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        if (preference instanceof PreferenceGroup) {
            PreferenceGroup preferenceGroup = (PreferenceGroup) preference;
            preferenceGroup.removeAll();
            List<ResolveInfo> queryShortcuts = queryShortcuts();
            final Context context = preference.getContext();
            if (queryShortcuts.isEmpty()) {
                return;
            }
            PreferenceCategory preferenceCategory = new PreferenceCategory(context);
            preferenceGroup.addPreference(preferenceCategory);
            int i = 0;
            for (final ResolveInfo resolveInfo : queryShortcuts) {
                int i2 = resolveInfo.priority / 10;
                if (i2 != i) {
                    preferenceCategory = new PreferenceCategory(context);
                    preferenceGroup.addPreference(preferenceCategory);
                }
                Preference preference2 = new Preference(context);
                preference2.setTitle(resolveInfo.loadLabel(this.mPackageManager));
                preference2.setKey(resolveInfo.activityInfo.getComponentName().flattenToString());
                preference2.setOnPreferenceClickListener(
                        new Preference
                                .OnPreferenceClickListener() { // from class:
                                                               // com.android.settings.shortcut.CreateShortcutPreferenceController$$ExternalSyntheticLambda1
                            @Override // androidx.preference.Preference.OnPreferenceClickListener
                            public final boolean onPreferenceClick(Preference preference3) {
                                boolean lambda$updateState$0;
                                lambda$updateState$0 =
                                        CreateShortcutPreferenceController.this
                                                .lambda$updateState$0(
                                                        context, resolveInfo, preference3);
                                return lambda$updateState$0;
                            }
                        });
                preferenceCategory.addPreference(preference2);
                i = i2;
            }
        }
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
