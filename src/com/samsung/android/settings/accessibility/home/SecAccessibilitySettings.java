package com.samsung.android.settings.accessibility.home;

import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;

import com.samsung.android.settings.accessibility.AccessibilityConstant;
import com.samsung.android.settings.accessibility.SecAccessibilityUtils;
import com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment;
import com.samsung.android.settings.accessibility.recommend.AccessibilityProfile;
import com.samsung.android.settings.accessibility.recommend.RecommendedForYouUtils;
import com.sec.ims.im.ImIntent;

import java.util.Map;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecAccessibilitySettings extends AccessibilityDashboardFragment {
    public static final BaseSearchIndexProvider SEARCH_INDEX_DATA_PROVIDER =
            new BaseSearchIndexProvider(R.xml.sec_accessibility_settings);

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return null;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1000;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.sec_accessibility_settings;
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment, com.android.settings.dashboard.DashboardFragment, com.android.settings.SettingsPreferenceFragment, com.android.settings.core.InstrumentedPreferenceFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        int i;
        super.onAttach(context);
        ((InstalledAppsPreferenceController) use(InstalledAppsPreferenceController.class))
                .init(getLifecycle());
        int i2 = RecommendedForYouUtils.$r8$clinit;
        if ("1"
                        .equals(
                                SecAccessibilityUtils.readDataFromAccessibilityProvider(
                                        context, "is_accessibility_user"))
                || (AccessibilityProfile.initProfileType(context) & 62) == 0) {
            return;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", "is_accessibility_user");
        contentValues.put("value", "1");
        try {
            i =
                    context.getContentResolver()
                            .update(
                                    AccessibilityConstant.URI_ACCESSIBILITY_PROVIDER,
                                    contentValues,
                                    "name=?",
                                    new String[] {"is_accessibility_user"});
        } catch (Exception e) {
            Log.w("A11yUtils", "writeDataToAccessibilityProvider - exception occurred.", e);
            i = 0;
        }
        if (i > 0) {
            return;
        }
        Log.w("RecommendedForYouUtils", "setAccessibilityUser - update value is failed");
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        String str;
        super.onCreate(bundle);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String string = arguments.getString(ImIntent.Extras.EXTRA_FROM);
            if ("Settings".equals(string)) {
                str = "GlobalSetting";
            } else if ("Shortcut".equals(string)) {
                str = "ShortcutKey";
            }
            this.mMetricsFeatureProvider.action(1000, "A11Y1000", Map.of("From", str));
        }
        str = "OthersIncludingShortcutIcon";
        this.mMetricsFeatureProvider.action(1000, "A11Y1000", Map.of("From", str));
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.accessibility_home_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override // com.samsung.android.settings.accessibility.base.widget.AccessibilityDashboardFragment, com.android.settingslib.core.lifecycle.ObservablePreferenceFragment, androidx.fragment.app.Fragment
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        if (itemId != R.id.action_add_to_homescreen) {
            if (itemId == R.id.more_about_accessibility) {
                startActivity(
                        new Intent("android.intent.action.VIEW")
                                .setData(Uri.parse("https://www.samsung.com/accessibility")));
            }
            return super.onOptionsItemSelected(menuItem);
        }
        ShortcutInfo build =
                new ShortcutInfo.Builder(getPrefContext(), "acc-shortcut")
                        .setShortLabel(getString(R.string.accessibility_settings))
                        .setIcon(
                                Icon.createWithResource(
                                        getPrefContext(), R.mipmap.ic_accessibility_launcher))
                        .setIntent(
                                new Intent("android.settings.ACCESSIBILITY_SETTINGS")
                                        .addFlags(268435456))
                        .build();
        ShortcutManager shortcutManager = (ShortcutManager) getSystemService(ShortcutManager.class);
        if (!shortcutManager.isRequestPinShortcutSupported()) {
            return true;
        }
        Context prefContext = getPrefContext();
        try {
            shortcutManager.requestPinShortcut(
                    build,
                    PendingIntent.getBroadcast(
                                    prefContext,
                                    0,
                                    shortcutManager.createShortcutResultIntent(build),
                                    67108864)
                            .getIntentSender());
            return true;
        } catch (IllegalArgumentException | IllegalStateException e) {
            Log.e("AccessibilitySettings", "addHomeScreen() : exception occurred", e);
            if (!(e instanceof IllegalArgumentException)) {
                return true;
            }
            Toast.makeText(prefContext, R.string.unable_add_shortcut_msg, 0).show();
            return true;
        }
    }
}
