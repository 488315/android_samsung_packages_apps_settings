package com.android.settings.bugreporthandler;

import android.R;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.RemoteException;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.PreferenceScreen;

import com.android.settings.applications.defaultapps.DefaultAppPickerFragment;
import com.android.settings.development.DeveloperOptionAwareMixin;
import com.android.settingslib.applications.DefaultAppInfo;
import com.android.settingslib.development.DevelopmentSettingsEnabler;
import com.android.settingslib.widget.CandidateInfo;
import com.android.settingslib.widget.FooterPreference;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BugReportHandlerPicker extends DefaultAppPickerFragment
        implements DeveloperOptionAwareMixin {
    public BugReportHandlerUtil mBugReportHandlerUtil;
    public FooterPreference mFooter;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class BugreportHandlerAppInfo extends DefaultAppInfo {
        public final Context mContext;

        public BugreportHandlerAppInfo(
                Context context,
                PackageManager packageManager,
                int i,
                PackageItemInfo packageItemInfo,
                String str) {
            super(context, packageManager, i, packageItemInfo, str, true);
            this.mContext = context;
        }

        @Override // com.android.settingslib.applications.DefaultAppInfo,
                  // com.android.settingslib.widget.CandidateInfo
        public final String getKey() {
            PackageItemInfo packageItemInfo = this.packageItemInfo;
            if (packageItemInfo != null) {
                return BugReportHandlerPicker.getKey(packageItemInfo.packageName, this.userId);
            }
            return null;
        }

        @Override // com.android.settingslib.applications.DefaultAppInfo,
                  // com.android.settingslib.widget.CandidateInfo
        public final CharSequence loadLabel() {
            PackageItemInfo packageItemInfo;
            if (this.mContext == null || (packageItemInfo = this.packageItemInfo) == null) {
                return null;
            }
            return "com.android.shell".equals(packageItemInfo.packageName)
                    ? this.mContext.getString(R.string.capability_title_canPerformGestures)
                    : super.loadLabel();
        }
    }

    public static String getKey(String str, int i) {
        return str + "#" + i;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void addStaticPreferences(PreferenceScreen preferenceScreen) {
        if (this.mFooter == null) {
            FooterPreference footerPreference = new FooterPreference(preferenceScreen.getContext());
            this.mFooter = footerPreference;
            footerPreference.setIcon(com.android.settings.R.drawable.ic_info_outline_24dp);
            this.mFooter.setSingleLineTitle(false);
            this.mFooter.setTitle(
                    com.android.settings.R.string.bug_report_handler_picker_footer_text);
            this.mFooter.setSelectable(false);
        }
        preferenceScreen.addPreference(this.mFooter);
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPickerFragment,
              // com.android.settings.widget.RadioButtonPickerFragment
    public final void bindPreferenceExtra(
            SelectorWithWidgetPreference selectorWithWidgetPreference,
            String str,
            CandidateInfo candidateInfo,
            String str2) {
        super.bindPreferenceExtra(selectorWithWidgetPreference, str, candidateInfo, str2);
        View view = selectorWithWidgetPreference.mAppendix;
        if (view != null) {
            view.setVisibility(8);
        }
        selectorWithWidgetPreference.mAppendixVisibility = 8;
    }

    public DefaultAppInfo createDefaultAppInfo(
            Context context,
            PackageManager packageManager,
            int i,
            PackageItemInfo packageItemInfo) {
        String string;
        String str = packageItemInfo.packageName;
        final Context context2 = getContext();
        if ("com.android.shell".equals(str)) {
            string = context2.getString(com.android.settings.R.string.system_default_app_subtext);
        } else if (this.mUserManager.getUserProfiles().size() < 2) {
            string = ApnSettings.MVNO_NONE;
        } else {
            UserInfo userInfo = this.mUserManager.getUserInfo(i);
            DevicePolicyManager devicePolicyManager =
                    (DevicePolicyManager) context2.getSystemService(DevicePolicyManager.class);
            if (userInfo == null || !userInfo.isManagedProfile()) {
                final int i2 = 1;
                string =
                        devicePolicyManager
                                .getResources()
                                .getString(
                                        "Settings.PERSONAL_PROFILE_APP_SUBTEXT",
                                        new Supplier() { // from class:
                                                         // com.android.settings.bugreporthandler.BugReportHandlerPicker$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Supplier
                                            public final Object get() {
                                                int i3 = i2;
                                                Context context3 = context2;
                                                switch (i3) {
                                                    case 0:
                                                        return context3.getString(
                                                                com.android.settings.R.string
                                                                        .work_profile_app_subtext);
                                                    default:
                                                        return context3.getString(
                                                                com.android.settings.R.string
                                                                        .personal_profile_app_subtext);
                                                }
                                            }
                                        });
            } else {
                final int i3 = 0;
                string =
                        devicePolicyManager
                                .getResources()
                                .getString(
                                        "Settings.WORK_PROFILE_APP_SUBTEXT",
                                        new Supplier() { // from class:
                                                         // com.android.settings.bugreporthandler.BugReportHandlerPicker$$ExternalSyntheticLambda0
                                            @Override // java.util.function.Supplier
                                            public final Object get() {
                                                int i32 = i3;
                                                Context context3 = context2;
                                                switch (i32) {
                                                    case 0:
                                                        return context3.getString(
                                                                com.android.settings.R.string
                                                                        .work_profile_app_subtext);
                                                    default:
                                                        return context3.getString(
                                                                com.android.settings.R.string
                                                                        .personal_profile_app_subtext);
                                                }
                                            }
                                        });
            }
        }
        return new BugreportHandlerAppInfo(context, packageManager, i, packageItemInfo, string);
    }

    public BugReportHandlerUtil createDefaultBugReportHandlerUtil() {
        return new BugReportHandlerUtil();
    }

    public final BugReportHandlerUtil getBugReportHandlerUtil() {
        if (this.mBugReportHandlerUtil == null) {
            setBugReportHandlerUtil(createDefaultBugReportHandlerUtil());
        }
        return this.mBugReportHandlerUtil;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        Context context = getContext();
        getBugReportHandlerUtil().getClass();
        ArrayList arrayList = new ArrayList();
        try {
            List bugreportWhitelistedPackages =
                    ActivityManager.getService().getBugreportWhitelistedPackages();
            int callingUserId = UserHandle.getCallingUserId();
            if (bugreportWhitelistedPackages.contains("com.android.shell")
                    && !BugReportHandlerUtil.getBugReportHandlerAppReceivers(
                                    context, callingUserId, "com.android.shell")
                            .isEmpty()) {
                try {
                    arrayList.add(
                            Pair.create(
                                    context.getPackageManager()
                                            .getApplicationInfo("com.android.shell", 4194304),
                                    Integer.valueOf(callingUserId)));
                } catch (PackageManager.NameNotFoundException unused) {
                }
            }
            List profiles =
                    ((UserManager) context.getSystemService(UserManager.class))
                            .getProfiles(callingUserId);
            List<String> list =
                    (List)
                            bugreportWhitelistedPackages.stream()
                                    .filter(new BugReportHandlerUtil$$ExternalSyntheticLambda0())
                                    .collect(Collectors.toList());
            Collections.sort(list);
            for (String str : list) {
                Iterator it = profiles.iterator();
                while (it.hasNext()) {
                    int identifier = ((UserInfo) it.next()).getUserHandle().getIdentifier();
                    if (!BugReportHandlerUtil.getBugReportHandlerAppReceivers(
                                    context, identifier, str)
                            .isEmpty()) {
                        try {
                            arrayList.add(
                                    Pair.create(
                                            context.getPackageManager()
                                                    .getApplicationInfo(str, 4194304),
                                            Integer.valueOf(identifier)));
                        } catch (PackageManager.NameNotFoundException unused2) {
                        }
                    }
                }
            }
        } catch (RemoteException e) {
            Log.e("BugReportHandlerUtil", "Failed to get bugreportAllowlistedPackages:", e);
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            Pair pair = (Pair) it2.next();
            arrayList2.add(
                    createDefaultAppInfo(
                            context,
                            this.mPm,
                            ((Integer) pair.second).intValue(),
                            (PackageItemInfo) pair.first));
        }
        return arrayList2;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        BugReportHandlerUtil bugReportHandlerUtil = getBugReportHandlerUtil();
        Context context = getContext();
        bugReportHandlerUtil.getClass();
        Pair currentBugReportHandlerAppAndUser =
                BugReportHandlerUtil.getCurrentBugReportHandlerAppAndUser(context);
        return getKey(
                (String) currentBugReportHandlerAppAndUser.first,
                ((Integer) currentBugReportHandlerAppAndUser.second).intValue());
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1808;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return com.android.settings.R.xml.bug_report_handler_settings;
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPickerFragment,
              // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        if (DevelopmentSettingsEnabler.isDevelopmentSettingsEnabled(context)) {
            return;
        }
        getActivity().finish();
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final void onSelectionPerformed(boolean z) {
        if (!z) {
            BugReportHandlerUtil bugReportHandlerUtil = getBugReportHandlerUtil();
            Context context = getContext();
            bugReportHandlerUtil.getClass();
            Toast.makeText(
                            context,
                            com.android.settings.R.string
                                    .select_invalid_bug_report_handler_toast_text,
                            0)
                    .show();
            updateCandidates();
            return;
        }
        FragmentActivity activity = getActivity();
        Intent intent = activity == null ? null : activity.getIntent();
        if (intent == null
                || !"android.settings.BUGREPORT_HANDLER_SETTINGS".equals(intent.getAction())) {
            return;
        }
        getActivity().finish();
    }

    public void setBugReportHandlerUtil(BugReportHandlerUtil bugReportHandlerUtil) {
        this.mBugReportHandlerUtil = bugReportHandlerUtil;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        int i;
        BugReportHandlerUtil bugReportHandlerUtil = getBugReportHandlerUtil();
        Context context = getContext();
        String substring = str.substring(0, str.lastIndexOf(35));
        try {
            i = Integer.parseInt(str.substring(str.lastIndexOf(35) + 1));
        } catch (NumberFormatException unused) {
            Log.e("BugReportHandlerPicker", "Failed to get handlerUser");
            i = 0;
        }
        bugReportHandlerUtil.getClass();
        if (!BugReportHandlerUtil.isBugreportAllowlistedApp(substring)
                || BugReportHandlerUtil.getBugReportHandlerAppReceivers(context, i, substring)
                        .isEmpty()) {
            return false;
        }
        Settings.Secure.putString(
                context.getContentResolver(), "custom_bugreport_handler_app", substring);
        Settings.Secure.putInt(context.getContentResolver(), "custom_bugreport_handler_user", i);
        return true;
    }
}
