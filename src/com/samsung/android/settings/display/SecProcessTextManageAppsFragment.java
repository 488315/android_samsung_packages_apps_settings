package com.samsung.android.settings.display;

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.MainClear$$ExternalSyntheticOutline0;
import com.android.settingslib.applications.RecentAppOpsAccess;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SecProcessTextManageAppsFragment extends Activity {
    public static String sProcessTextManageAppsStr = "";
    public static String sResolveInfo = "";

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class MainPreferenceFragment extends PreferenceFragment
            implements Preference.OnPreferenceChangeListener {
        @Override // android.preference.PreferenceFragment, android.app.Fragment
        public final void onActivityCreated(Bundle bundle) {
            super.onActivityCreated(bundle);
            ListView listView = (ListView) getView().findViewById(R.id.list);
            if (SecProcessTextManageAppsFragment.isDebuggableBinary()) {
                Log.e("ProcessTextManageApps", "preference fragment list : " + listView);
            }
            listView.setDivider(
                    getResources()
                            .getDrawable(com.android.settings.R.drawable.manage_apps_list_divider));
        }

        @Override // android.preference.PreferenceFragment, android.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            addPreferencesFromResource(
                    com.android.settings.R.layout.process_text_manager_apps_dialog);
            PreferenceScreen preferenceScreen = getPreferenceScreen();
            preferenceScreen.semSetCategoryBGColor(
                    getResources()
                            .getColor(com.android.settings.R.color.manage_apps_background_color));
            PackageManager packageManager = preferenceScreen.getContext().getPackageManager();
            for (ResolveInfo resolveInfo :
                    packageManager.queryIntentActivities(
                            new Intent()
                                    .setAction("android.intent.action.PROCESS_TEXT")
                                    .setType("text/plain"),
                            0)) {
                if (resolveInfo != null
                        && resolveInfo.activityInfo != null
                        && resolveInfo.getComponentInfo() != null
                        && !"com.samsung.android.app.interpreter"
                                .contentEquals(resolveInfo.activityInfo.packageName)
                        && (SecProcessTextManageAppsFragment.sResolveInfo.contains(
                                        resolveInfo.activityInfo.packageName)
                                || SecProcessTextManageAppsFragment.sResolveInfo.contains(
                                        resolveInfo.getComponentInfo().name)
                                || SecProcessTextManageAppsFragment.sResolveInfo.equals("*"))) {
                    if (SecProcessTextManageAppsFragment.isDebuggableBinary()) {
                        StringBuilder sb = new StringBuilder("info added : ");
                        sb.append(resolveInfo.activityInfo.packageName);
                        sb.append(" / ");
                        sb.append((Object) resolveInfo.loadLabel(packageManager));
                        sb.append(" / ");
                        MainClear$$ExternalSyntheticOutline0.m(
                                sb, resolveInfo.getComponentInfo().name, "ProcessTextManageApps");
                    }
                    SwitchPreference switchPreference =
                            new SwitchPreference(preferenceScreen.getContext());
                    switchPreference.setTitle(
                            packageManager.getApplicationLabel(
                                    resolveInfo.activityInfo.applicationInfo));
                    switchPreference.setSummary(resolveInfo.loadLabel(packageManager));
                    switchPreference.setKey(resolveInfo.getComponentInfo().name);
                    switchPreference.setPersistent(false);
                    String str = SecProcessTextManageAppsFragment.sProcessTextManageAppsStr;
                    if (str != null && str.contains(resolveInfo.getComponentInfo().name)) {
                        switchPreference.setChecked(true);
                    }
                    Drawable loadIcon = resolveInfo.loadIcon(packageManager);
                    if (loadIcon != null) {
                        Bitmap createBitmap =
                                Bitmap.createBitmap(
                                        loadIcon.getIntrinsicWidth(),
                                        loadIcon.getIntrinsicHeight(),
                                        Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(createBitmap);
                        loadIcon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                        loadIcon.draw(canvas);
                        int dimensionPixelSize =
                                getResources()
                                        .getDimensionPixelSize(
                                                com.android.settings.R.dimen
                                                        .manage_apps_preference_icon_size);
                        switchPreference.setIcon(
                                new BitmapDrawable(
                                        getContext().getResources(),
                                        Bitmap.createScaledBitmap(
                                                createBitmap,
                                                dimensionPixelSize,
                                                dimensionPixelSize,
                                                false)));
                    }
                    preferenceScreen.addPreference(switchPreference);
                    switchPreference.setOnPreferenceChangeListener(this);
                }
            }
        }

        @Override // android.preference.Preference.OnPreferenceChangeListener
        public final boolean onPreferenceChange(Preference preference, Object obj) {
            String str = preference.getKey() + "#";
            if (SecProcessTextManageAppsFragment.isDebuggableBinary()) {
                StringBuilder m =
                        ActivityResultRegistry$$ExternalSyntheticOutline0.m(
                                "pref key : ", str, " / new value : ");
                m.append(((Boolean) obj).booleanValue());
                Log.e("ProcessTextManageApps", m.toString());
            }
            if (((Boolean) obj).booleanValue()) {
                SecProcessTextManageAppsFragment.sProcessTextManageAppsStr =
                        SecProcessTextManageAppsFragment.sProcessTextManageAppsStr.concat(str);
            } else {
                SecProcessTextManageAppsFragment.sProcessTextManageAppsStr =
                        SecProcessTextManageAppsFragment.sProcessTextManageAppsStr.replace(
                                str, ApnSettings.MVNO_NONE);
            }
            if (!SecProcessTextManageAppsFragment.isDebuggableBinary()) {
                return true;
            }
            MainClear$$ExternalSyntheticOutline0.m(
                    new StringBuilder("pref changed : "),
                    SecProcessTextManageAppsFragment.sProcessTextManageAppsStr,
                    "ProcessTextManageApps");
            return true;
        }
    }

    public static boolean isDebuggableBinary() {
        String str = Build.TYPE;
        return "eng".equals(str) || "userdebug".equals(str);
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        setContentView(com.android.settings.R.layout.process_text_manager_apps);
        sProcessTextManageAppsStr =
                Settings.Global.getString(getContentResolver(), "process_text_manager_apps");
        if (isDebuggableBinary()) {
            MainClear$$ExternalSyntheticOutline0.m(
                    new StringBuilder("load manage apps list : "),
                    sProcessTextManageAppsStr,
                    "ProcessTextManageApps");
        }
        if (sProcessTextManageAppsStr == null) {
            sProcessTextManageAppsStr = ApnSettings.MVNO_NONE;
            saveProcessTextManageAppsList();
        }
        String stringExtra = getIntent().getStringExtra("resolveInfo");
        sResolveInfo = stringExtra;
        if (stringExtra == null) {
            sResolveInfo = "*";
        }
        if (isDebuggableBinary()) {
            MainClear$$ExternalSyntheticOutline0.m(
                    new StringBuilder("sResolveInfo received : "),
                    sResolveInfo,
                    "ProcessTextManageApps");
        }
        View findViewById = findViewById(com.android.settings.R.id.manage_apps_bottom_sheet);
        BottomSheetBehavior from = BottomSheetBehavior.from(findViewById);
        ((Button) findViewById.findViewById(com.android.settings.R.id.process_text_done_button))
                .setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.display.SecProcessTextManageAppsFragment.1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                SecProcessTextManageAppsFragment.this.finish();
                            }
                        });
        DisplayMetrics displayMetrics = getBaseContext().getResources().getDisplayMetrics();
        float f = displayMetrics.widthPixels;
        float f2 = displayMetrics.density;
        from.maxWidth =
                (int)
                        ((f / f2 < 600.0f ? VolteConstants.ErrorCode.TEMPORARILY_UNAVAILABLE : 600)
                                * f2);
        int i = displayMetrics.heightPixels;
        int identifier =
                getResources()
                        .getIdentifier(
                                "status_bar_height",
                                "dimen",
                                RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME);
        int dimensionPixelSize =
                identifier > 0 ? getResources().getDimensionPixelSize(identifier) : 0;
        Log.d("ProcessTextManageApps", "getStatusBarHeight : " + dimensionPixelSize);
        from.maxHeight =
                (i - dimensionPixelSize)
                        - getResources()
                                .getDimensionPixelSize(
                                        com.android.settings.R.dimen
                                                .manage_apps_expanded_state_top_margin);
        from.setState$1(6);
        BottomSheetBehavior.BottomSheetCallback bottomSheetCallback =
                new BottomSheetBehavior
                        .BottomSheetCallback() { // from class:
                                                 // com.samsung.android.settings.display.SecProcessTextManageAppsFragment.2
                    @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
                    public final void onStateChanged(View view, int i2) {
                        if (SecProcessTextManageAppsFragment.isDebuggableBinary()) {
                            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                                    .m(i2, "sheet state : ", "ProcessTextManageApps");
                        }
                        if (i2 != 5) {
                            return;
                        }
                        SecProcessTextManageAppsFragment.this.finish();
                    }

                    @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
                    public final void onSlide(View view) {}
                };
        Log.w(
                "BottomSheetBehavior",
                "BottomSheetBehavior now supports multiple callbacks. `setBottomSheetCallback()`"
                    + " removes all existing callbacks, including ones set internally by library"
                    + " authors, which may result in unintended behavior. This may change in the"
                    + " future. Please use `addBottomSheetCallback()` and"
                    + " `removeBottomSheetCallback()` instead to set your own callbacks.");
        from.callbacks.clear();
        from.callbacks.add(bottomSheetCallback);
        getFragmentManager()
                .beginTransaction()
                .replace(com.android.settings.R.id.pref_container, new MainPreferenceFragment())
                .commit();
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        if (isDebuggableBinary()) {
            Log.e("ProcessTextManageApps", "bottom sheet destroyed");
        }
        saveProcessTextManageAppsList();
        super.onDestroy();
    }

    @Override // android.app.Activity
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        if (isDebuggableBinary()) {
            Log.e("ProcessTextManageApps", "event : " + motionEvent.getActionMasked());
        }
        if (motionEvent.getActionMasked() != 1) {
            return false;
        }
        finish();
        return true;
    }

    public final void saveProcessTextManageAppsList() {
        if (isDebuggableBinary()) {
            MainClear$$ExternalSyntheticOutline0.m(
                    new StringBuilder("save manage apps list : "),
                    sProcessTextManageAppsStr,
                    "ProcessTextManageApps");
        }
        new Thread(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.display.SecProcessTextManageAppsFragment$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                SecProcessTextManageAppsFragment secProcessTextManageAppsFragment =
                                        SecProcessTextManageAppsFragment.this;
                                String str =
                                        SecProcessTextManageAppsFragment.sProcessTextManageAppsStr;
                                Settings.Global.putString(
                                        secProcessTextManageAppsFragment.getContentResolver(),
                                        "process_text_manager_apps",
                                        SecProcessTextManageAppsFragment.sProcessTextManageAppsStr);
                            }
                        })
                .start();
    }
}
