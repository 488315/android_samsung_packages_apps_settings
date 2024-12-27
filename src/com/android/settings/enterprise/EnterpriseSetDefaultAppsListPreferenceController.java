package com.android.settings.enterprise;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.os.UserHandle;

import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.applications.ApplicationFeatureProviderImpl;
import com.android.settings.applications.EnterpriseDefaultApps;
import com.android.settings.applications.UserAppInfo;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.users.UserFeatureProviderImpl;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.utils.StringUtil;
import com.android.settingslib.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class EnterpriseSetDefaultAppsListPreferenceController
        extends AbstractPreferenceController implements PreferenceControllerMixin {
    public final ApplicationFeatureProviderImpl mApplicationFeatureProvider;
    public final List mApps;
    public final EnterprisePrivacyFeatureProviderImpl mEnterprisePrivacyFeatureProvider;
    public final SettingsPreferenceFragment mParent;
    public final PackageManager mPm;
    public final List mUsers;

    public static void $r8$lambda$rPH0uEOoYWkmfu8bI7X_Fm4WTQ0(
            final EnterpriseSetDefaultAppsListPreferenceController
                    enterpriseSetDefaultAppsListPreferenceController) {
        SettingsPreferenceFragment settingsPreferenceFragment =
                enterpriseSetDefaultAppsListPreferenceController.mParent;
        Context context = settingsPreferenceFragment.getPreferenceManager().mContext;
        PreferenceScreen preferenceScreen = settingsPreferenceFragment.getPreferenceScreen();
        if (preferenceScreen == null) {
            return;
        }
        if (!enterpriseSetDefaultAppsListPreferenceController.mEnterprisePrivacyFeatureProvider
                        .isInCompMode()
                && enterpriseSetDefaultAppsListPreferenceController.mUsers.size() == 1) {
            enterpriseSetDefaultAppsListPreferenceController.createPreferences(
                    context,
                    preferenceScreen,
                    (EnumMap) enterpriseSetDefaultAppsListPreferenceController.mApps.get(0));
            return;
        }
        DevicePolicyManager devicePolicyManager =
                (DevicePolicyManager)
                        enterpriseSetDefaultAppsListPreferenceController.mContext.getSystemService(
                                DevicePolicyManager.class);
        for (int i = 0; i < enterpriseSetDefaultAppsListPreferenceController.mUsers.size(); i++) {
            UserInfo userInfo =
                    (UserInfo) enterpriseSetDefaultAppsListPreferenceController.mUsers.get(i);
            PreferenceCategory preferenceCategory = new PreferenceCategory(context);
            preferenceScreen.addPreference(preferenceCategory);
            if (userInfo.isManagedProfile()) {
                final int i2 = 0;
                preferenceCategory.setTitle(
                        devicePolicyManager
                                .getResources()
                                .getString(
                                        "Settings.WORK_CATEGORY_HEADER",
                                        new Supplier(
                                                enterpriseSetDefaultAppsListPreferenceController) { // from class: com.android.settings.enterprise.EnterpriseSetDefaultAppsListPreferenceController$$ExternalSyntheticLambda1
                                            public final /* synthetic */
                                            EnterpriseSetDefaultAppsListPreferenceController f$0;

                                            {
                                                this.f$0 =
                                                        enterpriseSetDefaultAppsListPreferenceController;
                                            }

                                            @Override // java.util.function.Supplier
                                            public final Object get() {
                                                String string;
                                                String string2;
                                                int i3 = i2;
                                                EnterpriseSetDefaultAppsListPreferenceController
                                                        enterpriseSetDefaultAppsListPreferenceController2 =
                                                                this.f$0;
                                                switch (i3) {
                                                    case 0:
                                                        string =
                                                                enterpriseSetDefaultAppsListPreferenceController2
                                                                        .mContext.getString(
                                                                        R.string.category_work);
                                                        return string;
                                                    default:
                                                        string2 =
                                                                enterpriseSetDefaultAppsListPreferenceController2
                                                                        .mContext.getString(
                                                                        R.string.category_personal);
                                                        return string2;
                                                }
                                            }
                                        }));
            } else {
                final int i3 = 1;
                preferenceCategory.setTitle(
                        devicePolicyManager
                                .getResources()
                                .getString(
                                        "Settings.PERSONAL_CATEGORY_HEADER",
                                        new Supplier(
                                                enterpriseSetDefaultAppsListPreferenceController) { // from class: com.android.settings.enterprise.EnterpriseSetDefaultAppsListPreferenceController$$ExternalSyntheticLambda1
                                            public final /* synthetic */
                                            EnterpriseSetDefaultAppsListPreferenceController f$0;

                                            {
                                                this.f$0 =
                                                        enterpriseSetDefaultAppsListPreferenceController;
                                            }

                                            @Override // java.util.function.Supplier
                                            public final Object get() {
                                                String string;
                                                String string2;
                                                int i32 = i3;
                                                EnterpriseSetDefaultAppsListPreferenceController
                                                        enterpriseSetDefaultAppsListPreferenceController2 =
                                                                this.f$0;
                                                switch (i32) {
                                                    case 0:
                                                        string =
                                                                enterpriseSetDefaultAppsListPreferenceController2
                                                                        .mContext.getString(
                                                                        R.string.category_work);
                                                        return string;
                                                    default:
                                                        string2 =
                                                                enterpriseSetDefaultAppsListPreferenceController2
                                                                        .mContext.getString(
                                                                        R.string.category_personal);
                                                        return string2;
                                                }
                                            }
                                        }));
            }
            preferenceCategory.setOrder(i);
            enterpriseSetDefaultAppsListPreferenceController.createPreferences(
                    context,
                    preferenceCategory,
                    (EnumMap) enterpriseSetDefaultAppsListPreferenceController.mApps.get(i));
        }
    }

    public EnterpriseSetDefaultAppsListPreferenceController(
            Context context,
            SettingsPreferenceFragment settingsPreferenceFragment,
            PackageManager packageManager) {
        super(context);
        this.mUsers = Collections.emptyList();
        this.mApps = Collections.emptyList();
        this.mPm = packageManager;
        this.mParent = settingsPreferenceFragment;
        FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
        if (featureFactoryImpl == null) {
            throw new UnsupportedOperationException("No feature factory configured");
        }
        this.mApplicationFeatureProvider = featureFactoryImpl.getApplicationFeatureProvider();
        this.mEnterprisePrivacyFeatureProvider =
                featureFactoryImpl.getEnterprisePrivacyFeatureProvider();
        UserFeatureProviderImpl userFeatureProviderImpl =
                (UserFeatureProviderImpl)
                        featureFactoryImpl.userFeatureProvider$delegate.getValue();
        this.mUsers = new ArrayList();
        this.mApps = new ArrayList();
        for (UserHandle userHandle : userFeatureProviderImpl.mUm.getUserProfiles()) {
            EnumMap enumMap = null;
            boolean z = false;
            for (EnterpriseDefaultApps enterpriseDefaultApps : EnterpriseDefaultApps.values()) {
                ArrayList arrayList =
                        (ArrayList)
                                this.mApplicationFeatureProvider.findPersistentPreferredActivities(
                                        userHandle.getIdentifier(),
                                        enterpriseDefaultApps.getIntents());
                if (!arrayList.isEmpty()) {
                    if (!z) {
                        this.mUsers.add(((UserAppInfo) arrayList.get(0)).userInfo);
                        enumMap = new EnumMap(EnterpriseDefaultApps.class);
                        this.mApps.add(enumMap);
                        z = true;
                    }
                    ArrayList arrayList2 = new ArrayList();
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        arrayList2.add(((UserAppInfo) it.next()).appInfo);
                    }
                    enumMap.put(
                            (EnumMap) enterpriseDefaultApps, (EnterpriseDefaultApps) arrayList2);
                }
            }
        }
        ThreadUtils.postOnMainThread(
                new Runnable() { // from class:
                                 // com.android.settings.enterprise.EnterpriseSetDefaultAppsListPreferenceController$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        EnterpriseSetDefaultAppsListPreferenceController
                                .$r8$lambda$rPH0uEOoYWkmfu8bI7X_Fm4WTQ0(
                                        EnterpriseSetDefaultAppsListPreferenceController.this);
                    }
                });
    }

    public final void createPreferences(
            Context context, PreferenceGroup preferenceGroup, EnumMap enumMap) {
        String string;
        for (EnterpriseDefaultApps enterpriseDefaultApps : EnterpriseDefaultApps.values()) {
            List list = (List) enumMap.get(enterpriseDefaultApps);
            if (list != null && !list.isEmpty()) {
                Preference preference = new Preference(context);
                int size = list.size();
                switch (enterpriseDefaultApps) {
                    case BROWSER:
                        string = context.getString(R.string.default_browser_title);
                        break;
                    case CALENDAR:
                        string = context.getString(R.string.default_calendar_app_title);
                        break;
                    case CAMERA:
                        string =
                                StringUtil.getIcuPluralsString(
                                        context, size, R.string.default_camera_app_title);
                        break;
                    case CONTACTS:
                        string = context.getString(R.string.default_contacts_app_title);
                        break;
                    case EMAIL:
                        string =
                                StringUtil.getIcuPluralsString(
                                        context, size, R.string.default_email_app_title);
                        break;
                    case MAP:
                        string = context.getString(R.string.default_map_app_title);
                        break;
                    case PHONE:
                        string =
                                StringUtil.getIcuPluralsString(
                                        context, size, R.string.default_phone_app_title);
                        break;
                    default:
                        throw new IllegalStateException(
                                "Unknown type of default " + enterpriseDefaultApps);
                }
                preference.setTitle(string);
                CharSequence[] charSequenceArr = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    charSequenceArr[i] = ((ApplicationInfo) list.get(i)).loadLabel(this.mPm);
                }
                preference.setSummary(
                        list.size() == 1
                                ? charSequenceArr[0]
                                : list.size() == 2
                                        ? context.getString(
                                                R.string.app_names_concatenation_template_2,
                                                charSequenceArr[0],
                                                charSequenceArr[1])
                                        : context.getString(
                                                R.string.app_names_concatenation_template_3,
                                                charSequenceArr[0],
                                                charSequenceArr[1],
                                                charSequenceArr[2]));
                preference.setOrder(enterpriseDefaultApps.ordinal());
                preference.setSelectable(false);
                preferenceGroup.addPreference(preference);
            }
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }
}
