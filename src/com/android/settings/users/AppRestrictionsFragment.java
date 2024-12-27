package com.android.settings.users;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.RestrictionEntry;
import android.content.RestrictionsManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.os.UserManager;
import android.util.EventLog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.ListPreference;
import androidx.preference.MultiSelectListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SwitchPreferenceCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settingslib.applications.RecentAppOpsAccess;
import com.android.settingslib.users.AppRestrictionsHelper;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.samsung.android.settings.widget.SecDividerItemDecorator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AppRestrictionsFragment extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener,
                View.OnClickListener,
                Preference.OnPreferenceClickListener,
                AppRestrictionsHelper.OnDisableUiForPackageListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public PreferenceScreen mAppList;
    public boolean mAppListChanged;
    public AsyncTask mAppLoadingTask;
    public AppRestrictionsHelper mHelper;
    public IPackageManager mIPm;
    public boolean mNewUser;
    public PackageManager mPackageManager;
    public final AnonymousClass1 mPackageObserver;
    public boolean mRestrictedProfile;
    public UserHandle mUser;
    public final AnonymousClass1 mUserBackgrounding;
    public UserManager mUserManager;
    public boolean mFirstTime = true;
    public int mCustomRequestCode = 1000;
    public final HashMap mCustomRequestMap = new HashMap();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.users.AppRestrictionsFragment$3, reason: invalid class name */
    public final class AnonymousClass3 extends AsyncTask {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AppRestrictionsFragment this$0;

        public /* synthetic */ AnonymousClass3(
                AppRestrictionsFragment appRestrictionsFragment, int i) {
            this.$r8$classId = i;
            this.this$0 = appRestrictionsFragment;
        }

        @Override // android.os.AsyncTask
        public final Object doInBackground(Object[] objArr) {
            switch (this.$r8$classId) {
                case 0:
                    AppRestrictionsFragment appRestrictionsFragment = this.this$0;
                    appRestrictionsFragment.mHelper.applyUserAppsStates(appRestrictionsFragment);
                    break;
                default:
                    this.this$0.mHelper.fetchAndMergeApps();
                    break;
            }
            return null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.os.AsyncTask
        public void onPostExecute(Object obj) {
            byte b;
            PackageInfo packageInfo;
            String string;
            switch (this.$r8$classId) {
                case 1:
                    AppRestrictionsFragment appRestrictionsFragment = this.this$0;
                    int i = AppRestrictionsFragment.$r8$clinit;
                    FragmentActivity activity = appRestrictionsFragment.getActivity();
                    if (activity != null) {
                        PackageManager packageManager = appRestrictionsFragment.mPackageManager;
                        IPackageManager iPackageManager = appRestrictionsFragment.mIPm;
                        int identifier = appRestrictionsFragment.mUser.getIdentifier();
                        if (Utils.getExistingUser(
                                        appRestrictionsFragment.mUser,
                                        appRestrictionsFragment.mUserManager)
                                != null) {
                            appRestrictionsFragment.mAppList.removeAll();
                            boolean z = false;
                            List<ResolveInfo> queryBroadcastReceivers =
                                    packageManager.queryBroadcastReceivers(
                                            new Intent(
                                                    "android.intent.action.GET_RESTRICTION_ENTRIES"),
                                            0);
                            Iterator it =
                                    ((ArrayList) appRestrictionsFragment.mHelper.mVisibleApps)
                                            .iterator();
                            while (it.hasNext()) {
                                AppRestrictionsHelper.SelectableAppInfo selectableAppInfo =
                                        (AppRestrictionsHelper.SelectableAppInfo) it.next();
                                String str = selectableAppInfo.packageName;
                                if (str != null) {
                                    boolean equals = str.equals(activity.getPackageName());
                                    AppRestrictionsPreference appRestrictionsPreference =
                                            new AppRestrictionsPreference(
                                                    appRestrictionsFragment.getPrefContext());
                                    appRestrictionsPreference.mChildren = new ArrayList();
                                    appRestrictionsPreference.setLayoutResource(
                                            R.layout.preference_app_restrictions);
                                    appRestrictionsPreference.listener = appRestrictionsFragment;
                                    Iterator<ResolveInfo> it2 = queryBroadcastReceivers.iterator();
                                    while (true) {
                                        if (!it2.hasNext()) {
                                            b = z ? 1 : 0;
                                        } else if (it2.next()
                                                .activityInfo
                                                .packageName
                                                .equals(str)) {
                                            b = true;
                                        }
                                    }
                                    if (equals) {
                                        String str2 = selectableAppInfo.packageName;
                                        appRestrictionsPreference.setIcon(
                                                R.drawable.ic_preference_location);
                                        appRestrictionsPreference.setKey(
                                                AppRestrictionsFragment.getKeyForPackage$1(str2));
                                        FragmentActivity activity2 =
                                                appRestrictionsFragment.getActivity();
                                        UserHandle userHandle = appRestrictionsFragment.mUser;
                                        Resources resources = activity2.getResources();
                                        ArrayList arrayList = new ArrayList();
                                        Bundle userRestrictions =
                                                UserManager.get(activity2)
                                                        .getUserRestrictions(userHandle);
                                        RestrictionEntry restrictionEntry =
                                                new RestrictionEntry(
                                                        RestrictionUtils.sRestrictionKeys[
                                                                z ? 1 : 0],
                                                        !userRestrictions.getBoolean(r15, z));
                                        restrictionEntry.setTitle(
                                                resources.getString(
                                                        RestrictionUtils.sRestrictionTitles[
                                                                z ? 1 : 0]));
                                        restrictionEntry.setDescription(
                                                resources.getString(
                                                        RestrictionUtils.sRestrictionDescriptions[
                                                                z ? 1 : 0]));
                                        restrictionEntry.setType(1);
                                        arrayList.add(restrictionEntry);
                                        RestrictionEntry restrictionEntry2 =
                                                (RestrictionEntry) arrayList.get(z ? 1 : 0);
                                        appRestrictionsPreference.setTitle(
                                                restrictionEntry2.getTitle());
                                        appRestrictionsPreference.restrictions = arrayList;
                                        appRestrictionsPreference.setSummary(
                                                restrictionEntry2.getDescription());
                                        appRestrictionsPreference.setChecked(
                                                restrictionEntry2.getSelectedState());
                                        appRestrictionsPreference.setPersistent();
                                        appRestrictionsPreference.setOnPreferenceClickListener(
                                                appRestrictionsFragment);
                                        appRestrictionsPreference.setOrder(100);
                                        appRestrictionsFragment.mAppList.addPreference(
                                                appRestrictionsPreference);
                                        appRestrictionsFragment.mHelper.setPackageSelected(
                                                str, true);
                                    } else {
                                        try {
                                            packageInfo =
                                                    iPackageManager.getPackageInfo(
                                                            str, 4194368L, identifier);
                                        } catch (RemoteException unused) {
                                            packageInfo = null;
                                        }
                                        if (packageInfo != null
                                                && (!appRestrictionsFragment.mRestrictedProfile
                                                        || packageInfo.requiredAccountType == null
                                                        || packageInfo.restrictedAccountType
                                                                != null)) {
                                            Drawable drawable = selectableAppInfo.icon;
                                            appRestrictionsPreference.setIcon(
                                                    drawable != null ? drawable.mutate() : null);
                                            appRestrictionsPreference.setChecked(z);
                                            appRestrictionsPreference.setTitle(
                                                    selectableAppInfo.activityName);
                                            appRestrictionsPreference.setKey(
                                                    AppRestrictionsFragment.getKeyForPackage$1(
                                                            str));
                                            appRestrictionsPreference.hasSettings =
                                                    (b == true
                                                                    && selectableAppInfo
                                                                                    .primaryEntry
                                                                            == null)
                                                            ? true
                                                            : z ? 1 : 0;
                                            appRestrictionsPreference.setPersistent();
                                            appRestrictionsPreference.setOnPreferenceChangeListener(
                                                    appRestrictionsFragment);
                                            appRestrictionsPreference.setOnPreferenceClickListener(
                                                    appRestrictionsFragment);
                                            AppRestrictionsHelper.SelectableAppInfo
                                                    selectableAppInfo2 =
                                                            selectableAppInfo.primaryEntry;
                                            if (selectableAppInfo2 == null) {
                                                string =
                                                        packageInfo.restrictedAccountType != null
                                                                ? appRestrictionsFragment.getString(
                                                                        R.string
                                                                                .app_sees_restricted_accounts)
                                                                : null;
                                            } else if (!appRestrictionsFragment.mRestrictedProfile
                                                    || packageInfo.restrictedAccountType == null) {
                                                string =
                                                        appRestrictionsFragment.getString(
                                                                R.string
                                                                        .user_restrictions_controlled_by,
                                                                selectableAppInfo2.activityName);
                                            } else {
                                                string =
                                                        appRestrictionsFragment.getString(
                                                                        R.string
                                                                                .app_sees_restricted_accounts)
                                                                + " "
                                                                + appRestrictionsFragment.getString(
                                                                        R.string
                                                                                .user_restrictions_controlled_by,
                                                                        selectableAppInfo
                                                                                .primaryEntry
                                                                                .activityName);
                                            }
                                            appRestrictionsPreference.setSummary(string);
                                            if (packageInfo.requiredForAllUsers) {
                                                appRestrictionsPreference.setChecked(true);
                                                appRestrictionsPreference.immutable = true;
                                                if (b != false) {
                                                    if (selectableAppInfo.primaryEntry == null) {
                                                        appRestrictionsFragment
                                                                .requestRestrictionsForApp(
                                                                        str,
                                                                        appRestrictionsPreference,
                                                                        false);
                                                    }
                                                }
                                                z = false;
                                            } else if (!appRestrictionsFragment.mNewUser) {
                                                ApplicationInfo applicationInfo =
                                                        packageInfo.applicationInfo;
                                                int i2 = applicationInfo.flags;
                                                int i3 = applicationInfo.privateFlags;
                                                if ((i2 & 8388608) != 0 && (i3 & 1) == 0) {
                                                    appRestrictionsPreference.setChecked(true);
                                                }
                                            }
                                            if (selectableAppInfo.primaryEntry != null) {
                                                appRestrictionsPreference.immutable = true;
                                                appRestrictionsPreference.setChecked(
                                                        ((Boolean)
                                                                        appRestrictionsFragment
                                                                                .mHelper
                                                                                .mSelectedPackages
                                                                                .get(str))
                                                                .booleanValue());
                                            }
                                            appRestrictionsPreference.setOrder(
                                                    (appRestrictionsFragment.mAppList
                                                                            .getPreferenceCount()
                                                                    + 2)
                                                            * 100);
                                            appRestrictionsFragment.mHelper.setPackageSelected(
                                                    str, appRestrictionsPreference.mChecked);
                                            appRestrictionsFragment.mAppList.addPreference(
                                                    appRestrictionsPreference);
                                            z = false;
                                        }
                                    }
                                }
                            }
                            appRestrictionsFragment.mAppListChanged = true;
                            if (appRestrictionsFragment.mNewUser
                                    && appRestrictionsFragment.mFirstTime) {
                                appRestrictionsFragment.mFirstTime = false;
                                appRestrictionsFragment.mHelper.applyUserAppsStates(
                                        appRestrictionsFragment);
                                break;
                            }
                        }
                    }
                    break;
                default:
                    super.onPostExecute(obj);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class RestrictionsResultReceiver extends BroadcastReceiver {
        public final boolean invokeIfCustom;
        public final String packageName;
        public final AppRestrictionsPreference preference;

        public RestrictionsResultReceiver(
                String str, AppRestrictionsPreference appRestrictionsPreference, boolean z) {
            this.packageName = str;
            this.preference = appRestrictionsPreference;
            this.invokeIfCustom = z;
        }

        public final void assertSafeToStartCustomActivity(Intent intent) {
            EventLog.writeEvent(1397638484, "223578534", -1, ApnSettings.MVNO_NONE);
            ResolveInfo resolveActivity =
                    AppRestrictionsFragment.this.mPackageManager.resolveActivity(intent, 65536);
            if (resolveActivity == null) {
                throw new ActivityNotFoundException("No result for resolving " + intent);
            }
            if (this.packageName.equals(resolveActivity.activityInfo.packageName)) {
                return;
            }
            throw new SecurityException(
                    "Application "
                            + this.packageName
                            + " is not allowed to start activity "
                            + intent);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            SwitchPreferenceCompat switchPreferenceCompat;
            String str;
            SwitchPreferenceCompat switchPreferenceCompat2;
            Bundle resultExtras = getResultExtras(true);
            ArrayList parcelableArrayList =
                    resultExtras.getParcelableArrayList("android.intent.extra.restrictions_list");
            Intent intent2 =
                    (Intent) resultExtras.getParcelable("android.intent.extra.restrictions_intent");
            if (parcelableArrayList == null || intent2 != null) {
                if (intent2 != null) {
                    this.preference.restrictions = parcelableArrayList;
                    intent2.removeFlags(195);
                    if (this.invokeIfCustom && AppRestrictionsFragment.this.isResumed()) {
                        try {
                            assertSafeToStartCustomActivity(intent2);
                            AppRestrictionsFragment appRestrictionsFragment =
                                    AppRestrictionsFragment.this;
                            AppRestrictionsPreference appRestrictionsPreference = this.preference;
                            int i = appRestrictionsFragment.mCustomRequestCode + 1;
                            appRestrictionsFragment.mCustomRequestCode = i;
                            appRestrictionsFragment.mCustomRequestMap.put(
                                    Integer.valueOf(i), appRestrictionsPreference);
                            AppRestrictionsFragment.this.startActivityForResult(
                                    new Intent(intent2),
                                    appRestrictionsFragment.mCustomRequestCode);
                            return;
                        } catch (ActivityNotFoundException | SecurityException e) {
                            int i2 = AppRestrictionsFragment.$r8$clinit;
                            Log.e(
                                    "AppRestrictionsFragment",
                                    "Cannot start restrictionsIntent " + e);
                            EventLog.writeEvent(1397638484, "200688991", -1, ApnSettings.MVNO_NONE);
                            return;
                        }
                    }
                    return;
                }
                return;
            }
            AppRestrictionsFragment appRestrictionsFragment2 = AppRestrictionsFragment.this;
            AppRestrictionsPreference appRestrictionsPreference2 = this.preference;
            int i3 = AppRestrictionsFragment.$r8$clinit;
            appRestrictionsFragment2.removeRestrictionsForApp(appRestrictionsPreference2);
            Iterator it = parcelableArrayList.iterator();
            int i4 = 1;
            while (it.hasNext()) {
                RestrictionEntry restrictionEntry = (RestrictionEntry) it.next();
                int type = restrictionEntry.getType();
                if (type != 1) {
                    switchPreferenceCompat = null;
                    if (type == 2 || type == 3) {
                        ListPreference listPreference =
                                new ListPreference(appRestrictionsFragment2.getPrefContext(), null);
                        listPreference.setTitle(restrictionEntry.getTitle());
                        String selectedString = restrictionEntry.getSelectedString();
                        if (selectedString == null) {
                            selectedString = restrictionEntry.getDescription();
                        }
                        String[] choiceEntries = restrictionEntry.getChoiceEntries();
                        String[] choiceValues = restrictionEntry.getChoiceValues();
                        int i5 = 0;
                        while (true) {
                            if (i5 >= choiceValues.length) {
                                str = selectedString;
                                break;
                            } else {
                                if (choiceValues[i5].equals(selectedString)) {
                                    str = choiceEntries[i5];
                                    break;
                                }
                                i5++;
                            }
                        }
                        listPreference.setSummary(str);
                        listPreference.mEntryValues = restrictionEntry.getChoiceValues();
                        listPreference.mEntries = restrictionEntry.getChoiceEntries();
                        listPreference.setValue(selectedString);
                        listPreference.mDialogTitle = restrictionEntry.getTitle();
                        switchPreferenceCompat2 = listPreference;
                    } else if (type == 4) {
                        MultiSelectListPreference multiSelectListPreference =
                                new MultiSelectListPreference(
                                        appRestrictionsFragment2.getPrefContext(), null);
                        multiSelectListPreference.setTitle(restrictionEntry.getTitle());
                        multiSelectListPreference.mEntryValues = restrictionEntry.getChoiceValues();
                        multiSelectListPreference.mEntries = restrictionEntry.getChoiceEntries();
                        HashSet hashSet = new HashSet();
                        Collections.addAll(hashSet, restrictionEntry.getAllSelectedStrings());
                        multiSelectListPreference.setValues(hashSet);
                        multiSelectListPreference.mDialogTitle = restrictionEntry.getTitle();
                        switchPreferenceCompat2 = multiSelectListPreference;
                    }
                    switchPreferenceCompat = switchPreferenceCompat2;
                } else {
                    switchPreferenceCompat =
                            new SwitchPreferenceCompat(appRestrictionsFragment2.getPrefContext());
                    switchPreferenceCompat.setTitle(restrictionEntry.getTitle());
                    switchPreferenceCompat.setSummary(restrictionEntry.getDescription());
                    switchPreferenceCompat.setChecked(restrictionEntry.getSelectedState());
                }
                if (switchPreferenceCompat != null) {
                    switchPreferenceCompat.setPersistent();
                    switchPreferenceCompat.setOrder(appRestrictionsPreference2.getOrder() + i4);
                    switchPreferenceCompat.setKey(
                            appRestrictionsPreference2.getKey().substring(4)
                                    + ";"
                                    + restrictionEntry.getKey());
                    appRestrictionsFragment2.mAppList.addPreference(switchPreferenceCompat);
                    switchPreferenceCompat.setOnPreferenceChangeListener(appRestrictionsFragment2);
                    switchPreferenceCompat.setIcon(R.drawable.empty_icon);
                    ((ArrayList) appRestrictionsPreference2.mChildren).add(switchPreferenceCompat);
                    i4++;
                }
            }
            appRestrictionsPreference2.restrictions = parcelableArrayList;
            if (i4 == 1
                    && appRestrictionsPreference2.immutable
                    && appRestrictionsPreference2.mChecked) {
                appRestrictionsFragment2.mAppList.removePreference(appRestrictionsPreference2);
            }
            AppRestrictionsFragment appRestrictionsFragment3 = AppRestrictionsFragment.this;
            if (appRestrictionsFragment3.mRestrictedProfile) {
                appRestrictionsFragment3.mUserManager.setApplicationRestrictions(
                        this.packageName,
                        RestrictionsManager.convertRestrictionsToBundle(parcelableArrayList),
                        AppRestrictionsFragment.this.mUser);
            }
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.users.AppRestrictionsFragment$1] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.settings.users.AppRestrictionsFragment$1] */
    public AppRestrictionsFragment() {
        final int i = 0;
        this.mUserBackgrounding =
                new BroadcastReceiver(
                        this) { // from class: com.android.settings.users.AppRestrictionsFragment.1
                    public final /* synthetic */ AppRestrictionsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i) {
                            case 0:
                                AppRestrictionsFragment appRestrictionsFragment = this.this$0;
                                if (appRestrictionsFragment.mAppListChanged) {
                                    appRestrictionsFragment.mHelper.applyUserAppsStates(
                                            appRestrictionsFragment);
                                    break;
                                }
                                break;
                            default:
                                AppRestrictionsFragment appRestrictionsFragment2 = this.this$0;
                                int i2 = AppRestrictionsFragment.$r8$clinit;
                                appRestrictionsFragment2.getClass();
                                String action = intent.getAction();
                                AppRestrictionsPreference appRestrictionsPreference =
                                        (AppRestrictionsPreference)
                                                appRestrictionsFragment2.findPreference(
                                                        AppRestrictionsFragment.getKeyForPackage$1(
                                                                intent.getData()
                                                                        .getSchemeSpecificPart()));
                                if (appRestrictionsPreference != null) {
                                    if (("android.intent.action.PACKAGE_ADDED".equals(action)
                                                    && appRestrictionsPreference.mChecked)
                                            || ("android.intent.action.PACKAGE_REMOVED"
                                                            .equals(action)
                                                    && !appRestrictionsPreference.mChecked)) {
                                        appRestrictionsPreference.setEnabled(true);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        this.mPackageObserver =
                new BroadcastReceiver(
                        this) { // from class: com.android.settings.users.AppRestrictionsFragment.1
                    public final /* synthetic */ AppRestrictionsFragment this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i2) {
                            case 0:
                                AppRestrictionsFragment appRestrictionsFragment = this.this$0;
                                if (appRestrictionsFragment.mAppListChanged) {
                                    appRestrictionsFragment.mHelper.applyUserAppsStates(
                                            appRestrictionsFragment);
                                    break;
                                }
                                break;
                            default:
                                AppRestrictionsFragment appRestrictionsFragment2 = this.this$0;
                                int i22 = AppRestrictionsFragment.$r8$clinit;
                                appRestrictionsFragment2.getClass();
                                String action = intent.getAction();
                                AppRestrictionsPreference appRestrictionsPreference =
                                        (AppRestrictionsPreference)
                                                appRestrictionsFragment2.findPreference(
                                                        AppRestrictionsFragment.getKeyForPackage$1(
                                                                intent.getData()
                                                                        .getSchemeSpecificPart()));
                                if (appRestrictionsPreference != null) {
                                    if (("android.intent.action.PACKAGE_ADDED".equals(action)
                                                    && appRestrictionsPreference.mChecked)
                                            || ("android.intent.action.PACKAGE_REMOVED"
                                                            .equals(action)
                                                    && !appRestrictionsPreference.mChecked)) {
                                        appRestrictionsPreference.setEnabled(true);
                                        break;
                                    }
                                }
                                break;
                        }
                    }
                };
    }

    public static String getKeyForPackage$1(String str) {
        return AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("pkg_", str);
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 97;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        RecyclerView listView = getListView();
        if (listView != null) {
            listView.seslSetGoToTopEnabled(true);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public final void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        AppRestrictionsPreference appRestrictionsPreference =
                (AppRestrictionsPreference) this.mCustomRequestMap.get(Integer.valueOf(i));
        if (appRestrictionsPreference == null) {
            RecordingInputConnection$$ExternalSyntheticOutline0.m(
                    i, "Unknown requestCode ", "AppRestrictionsFragment");
            return;
        }
        if (i2 == -1) {
            String substring = appRestrictionsPreference.getKey().substring(4);
            ArrayList parcelableArrayListExtra =
                    intent.getParcelableArrayListExtra("android.intent.extra.restrictions_list");
            Bundle bundleExtra = intent.getBundleExtra("android.intent.extra.restrictions_bundle");
            if (parcelableArrayListExtra != null) {
                appRestrictionsPreference.restrictions = parcelableArrayListExtra;
                this.mUserManager.setApplicationRestrictions(
                        substring,
                        RestrictionsManager.convertRestrictionsToBundle(parcelableArrayListExtra),
                        this.mUser);
            } else if (bundleExtra != null) {
                this.mUserManager.setApplicationRestrictions(substring, bundleExtra, this.mUser);
            }
        }
        this.mCustomRequestMap.remove(Integer.valueOf(i));
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (view.getTag() instanceof AppRestrictionsPreference) {
            AppRestrictionsPreference appRestrictionsPreference =
                    (AppRestrictionsPreference) view.getTag();
            if (view.getId() == R.id.app_restrictions_settings) {
                if (appRestrictionsPreference.getKey().startsWith("pkg_")) {
                    if (appRestrictionsPreference.panelOpen) {
                        removeRestrictionsForApp(appRestrictionsPreference);
                    } else {
                        requestRestrictionsForApp(
                                appRestrictionsPreference.getKey().substring(4),
                                appRestrictionsPreference,
                                true);
                    }
                    appRestrictionsPreference.panelOpen = !appRestrictionsPreference.panelOpen;
                    return;
                }
                return;
            }
            if (appRestrictionsPreference.immutable) {
                return;
            }
            appRestrictionsPreference.setChecked(!appRestrictionsPreference.mChecked);
            String substring = appRestrictionsPreference.getKey().substring(4);
            if (substring.equals(getActivity().getPackageName())) {
                ((RestrictionEntry) appRestrictionsPreference.restrictions.get(0))
                        .setSelectedState(appRestrictionsPreference.mChecked);
                FragmentActivity activity = getActivity();
                ArrayList arrayList = appRestrictionsPreference.restrictions;
                UserHandle userHandle = this.mUser;
                UserManager userManager = UserManager.get(activity);
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    userManager.setUserRestriction(
                            ((RestrictionEntry) it.next()).getKey(),
                            !r1.getSelectedState(),
                            userHandle);
                }
                return;
            }
            this.mHelper.setPackageSelected(substring, appRestrictionsPreference.mChecked);
            if (appRestrictionsPreference.mChecked
                    && appRestrictionsPreference.hasSettings
                    && appRestrictionsPreference.restrictions == null) {
                requestRestrictionsForApp(substring, appRestrictionsPreference, false);
            }
            this.mAppListChanged = true;
            if (!this.mRestrictedProfile) {
                this.mHelper.applyUserAppState(substring, appRestrictionsPreference.mChecked, this);
            }
            updateAllEntries(
                    appRestrictionsPreference.getKey(), appRestrictionsPreference.mChecked);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.mUser =
                    new UserHandle(
                            bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID));
        } else {
            Bundle arguments = getArguments();
            if (arguments != null) {
                if (arguments.containsKey(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID)) {
                    this.mUser =
                            new UserHandle(
                                    arguments.getInt(
                                            UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID));
                }
                this.mNewUser = arguments.getBoolean("new_user", false);
            }
        }
        if (this.mUser == null) {
            this.mUser = Process.myUserHandle();
        }
        this.mHelper = new AppRestrictionsHelper(getContext(), this.mUser);
        this.mPackageManager = getActivity().getPackageManager();
        this.mIPm = IPackageManager.Stub.asInterface(ServiceManager.getService("package"));
        UserManager userManager = (UserManager) getActivity().getSystemService("user");
        this.mUserManager = userManager;
        this.mRestrictedProfile =
                userManager.getUserInfo(this.mUser.getIdentifier()).isRestricted();
        try {
            this.mPackageManager.getPackageInfo(RecentAppOpsAccess.ANDROID_SYSTEM_PACKAGE_NAME, 64);
        } catch (PackageManager.NameNotFoundException unused) {
        }
        addPreferencesFromResource(R.xml.app_restrictions);
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        this.mAppList = preferenceScreen;
        preferenceScreen.mOrderingAsAdded = false;
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        this.mNewUser = false;
        getActivity().unregisterReceiver(this.mUserBackgrounding);
        getActivity().unregisterReceiver(this.mPackageObserver);
        if (this.mAppListChanged) {
            new AnonymousClass3(this, 0).execute(new Void[0]);
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        String key = preference.getKey();
        int i = 0;
        if (key == null || !key.contains(";")) {
            return false;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(key, ";");
        String nextToken = stringTokenizer.nextToken();
        String nextToken2 = stringTokenizer.nextToken();
        ArrayList arrayList =
                ((AppRestrictionsPreference) this.mAppList.findPreference("pkg_" + nextToken))
                        .restrictions;
        if (arrayList != null) {
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                RestrictionEntry restrictionEntry = (RestrictionEntry) it.next();
                if (restrictionEntry.getKey().equals(nextToken2)) {
                    int type = restrictionEntry.getType();
                    if (type == 1) {
                        restrictionEntry.setSelectedState(((Boolean) obj).booleanValue());
                    } else if (type == 2 || type == 3) {
                        ListPreference listPreference = (ListPreference) preference;
                        String str = (String) obj;
                        restrictionEntry.setSelectedString(str);
                        String[] choiceEntries = restrictionEntry.getChoiceEntries();
                        String[] choiceValues = restrictionEntry.getChoiceValues();
                        while (true) {
                            if (i >= choiceValues.length) {
                                break;
                            }
                            if (choiceValues[i].equals(str)) {
                                str = choiceEntries[i];
                                break;
                            }
                            i++;
                        }
                        listPreference.setSummary(str);
                    } else if (type == 4) {
                        Set set = (Set) obj;
                        String[] strArr = new String[set.size()];
                        set.toArray(strArr);
                        restrictionEntry.setAllSelectedStrings(strArr);
                    }
                    this.mUserManager.setApplicationRestrictions(
                            nextToken,
                            RestrictionsManager.convertRestrictionsToBundle(arrayList),
                            this.mUser);
                    break;
                }
            }
        }
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        if (!preference.getKey().startsWith("pkg_")) {
            return false;
        }
        AppRestrictionsPreference appRestrictionsPreference =
                (AppRestrictionsPreference) preference;
        if (!appRestrictionsPreference.immutable) {
            String substring = appRestrictionsPreference.getKey().substring(4);
            boolean z = !appRestrictionsPreference.mChecked;
            appRestrictionsPreference.setChecked(z);
            this.mHelper.setPackageSelected(substring, z);
            updateAllEntries(appRestrictionsPreference.getKey(), z);
            this.mAppListChanged = true;
            this.mHelper.applyUserAppState(substring, z, this);
        }
        return true;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        getActivity()
                .registerReceiver(
                        this.mUserBackgrounding,
                        new IntentFilter("android.intent.action.USER_BACKGROUND"));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addDataScheme("package");
        getActivity().registerReceiver(this.mPackageObserver, intentFilter);
        this.mAppListChanged = false;
        AsyncTask asyncTask = this.mAppLoadingTask;
        if (asyncTask == null || asyncTask.getStatus() == AsyncTask.Status.FINISHED) {
            this.mAppLoadingTask = new AnonymousClass3(this, 1).execute(new Void[0]);
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt(
                UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID, this.mUser.getIdentifier());
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        setDivider(null);
        getListView()
                .addItemDecoration(
                        new SecDividerItemDecorator(
                                (int)
                                        (getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_app_list_item_icon_min_width)
                                                + getResources()
                                                        .getDimension(
                                                                R.dimen
                                                                        .sec_widget_list_item_padding_start)),
                                getContext(),
                                getResources().getDrawable(R.drawable.sec_top_level_list_divider)));
    }

    public final void removeRestrictionsForApp(
            AppRestrictionsPreference appRestrictionsPreference) {
        Iterator it = ((ArrayList) appRestrictionsPreference.mChildren).iterator();
        while (it.hasNext()) {
            this.mAppList.removePreference((Preference) it.next());
        }
        ((ArrayList) appRestrictionsPreference.mChildren).clear();
    }

    public final void requestRestrictionsForApp(
            String str, AppRestrictionsPreference appRestrictionsPreference, boolean z) {
        Bundle applicationRestrictions =
                this.mUserManager.getApplicationRestrictions(str, this.mUser);
        Intent intent = new Intent("android.intent.action.GET_RESTRICTION_ENTRIES");
        intent.setPackage(str);
        intent.putExtra("android.intent.extra.restrictions_bundle", applicationRestrictions);
        intent.addFlags(32);
        getActivity()
                .sendOrderedBroadcast(
                        intent,
                        null,
                        new RestrictionsResultReceiver(str, appRestrictionsPreference, z),
                        null,
                        -1,
                        null,
                        null);
    }

    public final void updateAllEntries(String str, boolean z) {
        for (int i = 0; i < this.mAppList.getPreferenceCount(); i++) {
            Preference preference = this.mAppList.getPreference(i);
            if ((preference instanceof AppRestrictionsPreference)
                    && str.equals(preference.getKey())) {
                ((AppRestrictionsPreference) preference).setChecked(z);
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class AppRestrictionsPreference extends SwitchPreferenceCompat {
        public boolean hasSettings;
        public boolean immutable;
        public View.OnClickListener listener;
        public List mChildren;
        public boolean panelOpen;
        public ArrayList restrictions;

        @Override // androidx.preference.SwitchPreferenceCompat, androidx.preference.Preference
        public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
            super.onBindViewHolder(preferenceViewHolder);
            View findViewById = preferenceViewHolder.findViewById(R.id.app_restrictions_settings);
            findViewById.setVisibility(this.hasSettings ? 0 : 8);
            preferenceViewHolder
                    .findViewById(R.id.settings_divider)
                    .setVisibility(this.hasSettings ? 0 : 8);
            findViewById.setOnClickListener(this.listener);
            findViewById.setTag(this);
            View findViewById2 = preferenceViewHolder.findViewById(R.id.app_restrictions_pref);
            findViewById2.setOnClickListener(this.listener);
            findViewById2.setTag(this);
            findViewById2.setEnabled(!this.immutable);
            preferenceViewHolder
                    .findViewById(android.R.id.title)
                    .setAlpha(!this.immutable ? 1.0f : 0.4f);
            preferenceViewHolder
                    .findViewById(android.R.id.summary)
                    .setAlpha(this.immutable ? 0.4f : 1.0f);
            ViewGroup viewGroup =
                    (ViewGroup) preferenceViewHolder.findViewById(android.R.id.widget_frame);
            viewGroup.setEnabled(!this.immutable);
            if (viewGroup.getChildCount() > 0) {
                final CompoundButton compoundButton = (CompoundButton) viewGroup.getChildAt(0);
                compoundButton.setEnabled(!this.immutable);
                compoundButton.setTag(this);
                compoundButton.setClickable(true);
                compoundButton.setFocusable(true);
                compoundButton.setOnClickListener(new AnonymousClass1());
                compoundButton.setOnCheckedChangeListener(
                        new CompoundButton
                                .OnCheckedChangeListener() { // from class:
                                                             // com.android.settings.users.AppRestrictionsFragment.AppRestrictionsPreference.2
                            @Override // android.widget.CompoundButton.OnCheckedChangeListener
                            public final void onCheckedChanged(
                                    CompoundButton compoundButton2, boolean z) {
                                AppRestrictionsPreference.this.listener.onClick(compoundButton);
                            }
                        });
            }
        }

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        /* renamed from: com.android.settings.users.AppRestrictionsFragment$AppRestrictionsPreference$1, reason: invalid class name */
        public final class AnonymousClass1 implements View.OnClickListener {
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {}
        }
    }
}
