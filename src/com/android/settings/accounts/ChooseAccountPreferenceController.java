package com.android.settings.accounts;

import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SyncAdapterType;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.UserHandle;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.Log;
import android.widget.Toast;

import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.google.android.collect.Maps;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.account.AccountUtils;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.voiceinput.samsungaccount.contract.SaContract;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ChooseAccountPreferenceController extends BasePreferenceController {
    private static final String TAG = "ChooseAccountPrefCtrler";
    private Map<String, List<String>> mAccountTypeToAuthorities;
    private Set<String> mAccountTypesFilter;
    private Activity mActivity;
    private AuthenticatorDescription[] mAuthDescs;
    private String[] mAuthorities;
    private final List<ProviderEntry> mProviderList;
    private PreferenceScreen mScreen;
    private final Map<String, AuthenticatorDescription> mTypeToAuthDescription;
    private UserHandle mUserHandle;

    public ChooseAccountPreferenceController(Context context, String str) {
        super(context, str);
        this.mProviderList = new ArrayList();
        this.mTypeToAuthDescription = new HashMap();
    }

    public static Boolean checkDisableContactSync() {
        return Boolean.valueOf(!AccountUtils.supportSamsungCloud());
    }

    private void finishWithAccountType(String str) {
        LoggingHelper.insertEventLogging(10, 4650);
        Intent intent = new Intent();
        intent.putExtra("selected_account", str);
        intent.putExtra("android.intent.extra.USER", this.mUserHandle);
        this.mActivity.setResult(-1, intent);
        this.mActivity.finish();
    }

    private List<String> getAuthoritiesForAccountType(String str) {
        if (this.mAccountTypeToAuthorities == null) {
            this.mAccountTypeToAuthorities = Maps.newHashMap();
            for (SyncAdapterType syncAdapterType :
                    ContentResolver.getSyncAdapterTypesAsUser(this.mUserHandle.getIdentifier())) {
                List<String> list = this.mAccountTypeToAuthorities.get(syncAdapterType.accountType);
                if (list == null) {
                    list = new ArrayList<>();
                    this.mAccountTypeToAuthorities.put(syncAdapterType.accountType, list);
                }
                if (Log.isLoggable(TAG, 2)) {
                    Log.v(
                            TAG,
                            "added authority "
                                    + syncAdapterType.authority
                                    + " to accountType "
                                    + syncAdapterType.accountType);
                }
                list.add(syncAdapterType.authority);
            }
        }
        return this.mAccountTypeToAuthorities.get(str);
    }

    private void onAuthDescriptionsUpdated() {
        boolean z;
        Set<String> set;
        String[] strArr;
        int i = 0;
        while (true) {
            AuthenticatorDescription[] authenticatorDescriptionArr = this.mAuthDescs;
            if (i >= authenticatorDescriptionArr.length) {
                break;
            }
            String str = authenticatorDescriptionArr[i].type;
            CharSequence labelForType = getLabelForType(str);
            if (labelForType != null && !labelForType.toString().isEmpty()) {
                List<String> authoritiesForAccountType = getAuthoritiesForAccountType(str);
                String[] strArr2 = this.mAuthorities;
                if (strArr2 != null && strArr2.length > 0) {
                    int i2 = 0;
                    while (true) {
                        String[] strArr3 = this.mAuthorities;
                        if (i2 >= strArr3.length) {
                            z = false;
                            break;
                        } else if (authoritiesForAccountType != null
                                && authoritiesForAccountType.contains(strArr3[i2])) {
                            break;
                        } else {
                            i2++;
                        }
                    }
                }
                z = true;
                if (checkDisableContactSync().booleanValue()
                        && str.equals("com.osp.app.signin")
                        && (strArr = this.mAuthorities) != null
                        && strArr.length > 0
                        && Arrays.asList(strArr).contains("com.android.contacts")) {
                    z = false;
                }
                if (!SemCscFeature.getInstance().getBoolean("CscFeature_Web_ConfigSyncSource", true)
                        && str.compareToIgnoreCase("org.mozilla.firefox") == 0) {
                    z = false;
                }
                if ("com.samsung.android.coreapps".equals(str)) {
                    Log.d(TAG, "Hide Easysignup account");
                    z = false;
                }
                if (SaContract.OLD_PACKAGE_NAME.equals(str)) {
                    Log.d(TAG, "Hide mobileservice account");
                    z = false;
                }
                if (SemPersonaManager.isSecureFolderId(UserHandle.getCallingUserId())
                        && "com.google.work".equals(str)) {
                    Log.d(TAG, "Hide managed account");
                    z = false;
                }
                if (SemCscFeature.getInstance().getBoolean("CscFeature_Contact_SupportWhitePages")
                        && str.equals("com.whitepages.nameid.account")) {
                    Log.w(TAG, "Hide com.whitepages.nameid.account Account Add Option");
                    z = false;
                }
                if (SemCscFeature.getInstance()
                                .getBoolean("CscFeature_VoiceCall_SupportCallProtect")
                        && str.equals("com.att.callprotect.account")) {
                    Log.w(TAG, "com.att.callprotect.account Account Add Option");
                    z = false;
                }
                if (z && (set = this.mAccountTypesFilter) != null && !set.contains(str)) {
                    z = false;
                }
                if (z) {
                    this.mProviderList.add(new ProviderEntry(str, labelForType));
                } else if (Log.isLoggable(TAG, 2)) {
                    Log.v(
                            TAG,
                            "Skipped pref "
                                    + ((Object) labelForType)
                                    + ": has no authority we need");
                }
            }
            i++;
        }
        Context context = this.mScreen.getContext();
        if (this.mProviderList.size() == 1) {
            RestrictedLockUtils.EnforcedAdmin checkIfAccountManagementDisabled =
                    RestrictedLockUtilsInternal.checkIfAccountManagementDisabled(
                            context,
                            this.mUserHandle.getIdentifier(),
                            this.mProviderList.get(0).type);
            if (checkIfAccountManagementDisabled == null) {
                finishWithAccountType(this.mProviderList.get(0).type);
                return;
            } else {
                this.mActivity.setResult(
                        0,
                        RestrictedLockUtils.getShowAdminSupportDetailsIntent(
                                checkIfAccountManagementDisabled));
                this.mActivity.finish();
                return;
            }
        }
        if (this.mProviderList.size() > 0) {
            Collections.sort(this.mProviderList);
            for (ProviderEntry providerEntry : this.mProviderList) {
                Drawable drawableForType = getDrawableForType(providerEntry.type);
                String str2 = providerEntry.type;
                CharSequence charSequence = providerEntry.name;
                ProviderPreference providerPreference = new ProviderPreference(context);
                providerPreference.mAccountType = str2;
                providerPreference.setLayoutResource(R.layout.sec_account_provider_preference);
                providerPreference.setIcon(drawableForType);
                providerPreference.setPersistent();
                providerPreference.setTitle(charSequence);
                providerPreference.mHelper.mDisabledSummary = true;
                providerPreference.setKey(providerEntry.type.toString());
                providerPreference.setDisabledByAdmin(
                        RestrictedLockUtilsInternal.checkIfAccountManagementDisabled(
                                providerPreference.getContext(),
                                this.mUserHandle.getIdentifier(),
                                str2));
                this.mScreen.addPreference(providerPreference);
            }
            return;
        }
        if (this.mAuthorities != null && Log.isLoggable(TAG, 2)) {
            StringBuilder sb = new StringBuilder();
            for (String str3 : this.mAuthorities) {
                sb.append(str3);
                sb.append(' ');
            }
            Log.v(TAG, "No providers found for authorities: " + ((Object) sb));
        }
        Activity activity = this.mActivity;
        Toast.makeText(activity, activity.getString(R.string.status_unavailable), 1).show();
        if (this.mAccountTypesFilter != null) {
            final StringJoiner stringJoiner =
                    new StringJoiner(", ", ApnSettings.MVNO_NONE, ApnSettings.MVNO_NONE);
            this.mAccountTypesFilter.forEach(
                    new Consumer() { // from class:
                                     // com.android.settings.accounts.ChooseAccountPreferenceController$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            stringJoiner.add((String) obj);
                        }
                    });
            Log.w(TAG, "No providers found for account types: " + stringJoiner);
        }
        this.mActivity.setResult(0);
    }

    private void updateAuthDescriptions() {
        this.mAuthDescs =
                AccountManager.get(this.mContext)
                        .getAuthenticatorTypesAsUser(this.mUserHandle.getIdentifier());
        int i = 0;
        while (true) {
            AuthenticatorDescription[] authenticatorDescriptionArr = this.mAuthDescs;
            if (i >= authenticatorDescriptionArr.length) {
                onAuthDescriptionsUpdated();
                return;
            }
            Map<String, AuthenticatorDescription> map = this.mTypeToAuthDescription;
            AuthenticatorDescription authenticatorDescription = authenticatorDescriptionArr[i];
            map.put(authenticatorDescription.type, authenticatorDescription);
            i++;
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mScreen = preferenceScreen;
        updateAuthDescriptions();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return 0;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:5:0x006e A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x006f  */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v1, types: [java.lang.Object, java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v10, types: [android.graphics.drawable.Drawable] */
    /* JADX WARN: Type inference failed for: r7v11 */
    /* JADX WARN: Type inference failed for: r7v12 */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v5, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v6, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.drawable.Drawable getDrawableForType(java.lang.String r7) {
        /*
            r6 = this;
            java.lang.String r0 = "ChooseAccountPrefCtrler"
            java.util.Map<java.lang.String, android.accounts.AuthenticatorDescription> r1 = r6.mTypeToAuthDescription
            boolean r1 = r1.containsKey(r7)
            if (r1 == 0) goto L6b
            java.util.Map<java.lang.String, android.accounts.AuthenticatorDescription> r1 = r6.mTypeToAuthDescription     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            java.lang.Object r1 = r1.get(r7)     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            android.accounts.AuthenticatorDescription r1 = (android.accounts.AuthenticatorDescription) r1     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            android.app.Activity r2 = r6.mActivity     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            java.lang.String r3 = r1.packageName     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            android.os.UserHandle r4 = r6.mUserHandle     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            r5 = 0
            android.content.Context r2 = r2.createPackageContextAsUser(r3, r5, r4)     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            android.content.Context r3 = r6.mContext     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            android.content.pm.PackageManager r3 = r3.getPackageManager()     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            java.lang.String r4 = r1.packageName     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            boolean r4 = r3.semShouldPackIntoIconTray(r4)     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            if (r4 == 0) goto L3d
            int r1 = r1.iconId     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            android.graphics.drawable.Drawable r1 = r2.getDrawable(r1)     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            r2 = 1
            android.graphics.drawable.Drawable r1 = r3.semGetDrawableForIconTray(r1, r2)     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            android.os.UserHandle r2 = r6.mUserHandle     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            android.graphics.drawable.Drawable r7 = r3.getUserBadgedIcon(r1, r2)     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            goto L6c
        L3d:
            int r1 = r1.iconId     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            android.graphics.drawable.Drawable r1 = r2.getDrawable(r1)     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            android.os.UserHandle r2 = r6.mUserHandle     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            android.graphics.drawable.Drawable r7 = r3.getUserBadgedIcon(r1, r2)     // Catch: java.lang.IllegalArgumentException -> L4a android.content.res.Resources.NotFoundException -> L60 android.content.pm.PackageManager.NameNotFoundException -> L66
            goto L6c
        L4a:
            java.lang.String r1 = "width and height must be > 0 "
            java.lang.String r2 = " / "
            java.lang.StringBuilder r1 = androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0.m(r1, r7, r2)
            java.util.Map<java.lang.String, android.accounts.AuthenticatorDescription> r2 = r6.mTypeToAuthDescription
            java.lang.Object r7 = r2.get(r7)
            android.accounts.AuthenticatorDescription r7 = (android.accounts.AuthenticatorDescription) r7
            java.lang.String r7 = r7.packageName
            com.android.settings.MainClear$$ExternalSyntheticOutline0.m$1(r1, r7, r0)
            goto L6b
        L60:
            java.lang.String r1 = "No icon resource for account type "
            androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0.m(r1, r7, r0)
            goto L6b
        L66:
            java.lang.String r1 = "No icon name for account type "
            androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0.m(r1, r7, r0)
        L6b:
            r7 = 0
        L6c:
            if (r7 == 0) goto L6f
            return r7
        L6f:
            android.content.Context r6 = r6.mContext
            android.content.pm.PackageManager r6 = r6.getPackageManager()
            android.graphics.drawable.Drawable r6 = r6.getDefaultActivityIcon()
            return r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.settings.accounts.ChooseAccountPreferenceController.getDrawableForType(java.lang.String):android.graphics.drawable.Drawable");
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    public CharSequence getLabelForType(String str) {
        if (this.mTypeToAuthDescription.containsKey(str)) {
            try {
                AuthenticatorDescription authenticatorDescription =
                        this.mTypeToAuthDescription.get(str);
                Context createPackageContextAsUser =
                        this.mActivity.createPackageContextAsUser(
                                authenticatorDescription.packageName, 0, this.mUserHandle);
                String applicationNameFromDb =
                        EnterpriseDeviceManager.getInstance()
                                .getApplicationPolicy()
                                .getApplicationNameFromDb(
                                        authenticatorDescription.packageName,
                                        UserHandle.getCallingUserId());
                return applicationNameFromDb != null
                        ? applicationNameFromDb
                        : createPackageContextAsUser
                                .getResources()
                                .getText(authenticatorDescription.labelId);
            } catch (PackageManager.NameNotFoundException unused) {
                MotionLayout$$ExternalSyntheticOutline0.m(
                        "No label name for account type ", str, TAG);
            } catch (Resources.NotFoundException unused2) {
                MotionLayout$$ExternalSyntheticOutline0.m(
                        "No label resource for account type ", str, TAG);
            }
        }
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

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!(preference instanceof ProviderPreference)) {
            return false;
        }
        ProviderPreference providerPreference = (ProviderPreference) preference;
        if (Log.isLoggable(TAG, 2)) {
            Log.v(TAG, "Attempting to add account of type " + providerPreference.mAccountType);
        }
        finishWithAccountType(providerPreference.mAccountType);
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    public void initialize(
            String[] strArr, String[] strArr2, UserHandle userHandle, Activity activity) {
        this.mActivity = activity;
        this.mAuthorities = strArr;
        this.mUserHandle = userHandle;
        if (strArr2 != null) {
            this.mAccountTypesFilter = new HashSet();
            for (String str : strArr2) {
                this.mAccountTypesFilter.add(str);
            }
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    public boolean isSignedIn(String str) {
        AccountManager accountManager = AccountManager.get(this.mActivity);
        return (accountManager == null
                        || str == null
                        || accountManager.getAccountsByTypeAsUser(str, this.mUserHandle).length
                                <= 0)
                ? false
                : true;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
