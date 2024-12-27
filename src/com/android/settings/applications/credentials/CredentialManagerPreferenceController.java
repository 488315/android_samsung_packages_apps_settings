package com.android.settings.applications.credentials;

import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.credentials.CredentialManager;
import android.credentials.CredentialProviderInfo;
import android.credentials.SetEnabledProvidersException;
import android.database.ContentObserver;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.OutcomeReceiver;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.service.autofill.AutofillServiceInfo;
import android.text.TextUtils;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.util.Pair;
import android.widget.Toast;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;

import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.content.PackageMonitor;
import com.android.internal.hidden_from_bootclasspath.android.credentials.flags.Flags;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.RestrictedLockUtils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.utils.ThreadUtils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.widget.SecRestrictedSwitchPreferenceScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class CredentialManagerPreferenceController extends BasePreferenceController
        implements LifecycleObserver {
    public static final String ADD_SERVICE_DEVICE_CONFIG = "credential_manager_service_search_uri";
    private static final String ALTERNATE_INTENT = "android.settings.SYNC_SETTINGS";
    public static final String AUTOFILL_CREDMAN_ONLY_PROVIDER_PLACEHOLDER = "credential-provider";
    public static final int MAX_ADDITIONAL_PROVIDER_COUNT = 4;
    private static final int MAX_SELECTABLE_PROVIDERS = 5;
    private static final String PRIMARY_INTENT = "android.settings.CREDENTIAL_PROVIDER";
    private static final String TAG = "CredentialManagerPreferenceController";
    private final CredentialManager mCredentialManager;
    private Delegate mDelegate;
    private final Set<String> mEnabledPackageNames;
    private final Executor mExecutor;
    private String mFlagOverrideForTest;
    private FragmentManager mFragmentManager;
    private final Handler mHandler;
    private final ImageUtils$IconResizer mIconResizer;
    private boolean mIsWorkProfile;
    private final List<ServiceInfo> mPendingServiceInfos;
    private final PackageManager mPm;
    private PreferenceGroup mPreferenceGroup;
    private PreferenceScreen mPreferenceScreen;
    private final Map<String, SecRestrictedSwitchPreferenceScreen> mPrefs;
    private int mServiceId;
    private final List<CredentialProviderInfo> mServices;
    private final SettingContentObserver mSettingsContentObserver;
    private final PackageMonitor mSettingsPackageMonitor;
    private boolean mSimulateConnectedForTests;
    private Optional<Boolean> mSimulateHiddenForTests;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.credentials.CredentialManagerPreferenceController$6, reason: invalid class name */
    public final class AnonymousClass6 extends PackageMonitor {
        public AnonymousClass6() {}

        public final void onPackageAdded(String str, int i) {
            ThreadUtils.postOnMainThread(
                    new CredentialManagerPreferenceController$6$$ExternalSyntheticLambda0(this, 1));
        }

        public final void onPackageModified(String str) {
            ThreadUtils.postOnMainThread(
                    new CredentialManagerPreferenceController$6$$ExternalSyntheticLambda0(this, 0));
        }

        public final void onPackageRemoved(String str, int i) {
            ThreadUtils.postOnMainThread(
                    new CredentialManagerPreferenceController$6$$ExternalSyntheticLambda0(this, 2));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class CredentialManagerDialogFragment extends DialogFragment
            implements DialogInterface.OnClickListener {
        public final DialogHost mDialogHost;

        public CredentialManagerDialogFragment(DialogHost dialogHost) {
            this.mDialogHost = dialogHost;
        }

        @Override // androidx.fragment.app.DialogFragment,
                  // android.content.DialogInterface.OnCancelListener
        public final void onCancel(DialogInterface dialogInterface) {
            this.mDialogHost.getClass();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Delegate {
        void forceDelegateRefresh();

        void setActivityResult(int i);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface DialogHost {
        void onDialogClick(int i);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class NewProviderConfirmationDialogFragment
            extends CredentialManagerDialogFragment {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            this.mDialogHost.onDialogClick(i);
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            Bundle arguments = getArguments();
            Context context = getContext();
            CharSequence charSequence = arguments.getCharSequence("app_name");
            String string =
                    context.getString(
                            R.string.credman_enable_confirmation_message_title, charSequence);
            String string2 =
                    context.getString(R.string.credman_enable_confirmation_message, charSequence);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mTitle = string;
            alertParams.mMessage = string2;
            builder.setPositiveButton(android.R.string.ok, this);
            builder.setNegativeButton(android.R.string.cancel, this);
            return builder.create();
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SettingContentObserver extends ContentObserver {
        public final Uri mAutofillService;
        public final ContentResolver mContentResolver;
        public final Uri mCredentialPrimaryService;
        public final Uri mCredentialService;

        public SettingContentObserver(Handler handler, ContentResolver contentResolver) {
            super(handler);
            this.mAutofillService = Settings.Secure.getUriFor("autofill_service");
            this.mCredentialService = Settings.Secure.getUriFor("credential_service");
            this.mCredentialPrimaryService =
                    Settings.Secure.getUriFor("credential_service_primary");
            this.mContentResolver = contentResolver;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            CredentialManagerPreferenceController.this.updateFromExternal();
        }

        public final void register() {
            this.mContentResolver.registerContentObserver(
                    this.mAutofillService,
                    false,
                    this,
                    CredentialManagerPreferenceController.this.getUser());
            this.mContentResolver.registerContentObserver(
                    this.mCredentialService,
                    false,
                    this,
                    CredentialManagerPreferenceController.this.getUser());
            this.mContentResolver.registerContentObserver(
                    this.mCredentialPrimaryService,
                    false,
                    this,
                    CredentialManagerPreferenceController.this.getUser());
        }
    }

    public CredentialManagerPreferenceController(Context context, String str) {
        super(context, str);
        this.mPrefs = new HashMap();
        this.mPendingServiceInfos = new ArrayList();
        Handler handler = new Handler();
        this.mHandler = handler;
        this.mFragmentManager = null;
        this.mDelegate = null;
        this.mFlagOverrideForTest = null;
        this.mPreferenceScreen = null;
        this.mPreferenceGroup = null;
        this.mSimulateHiddenForTests = Optional.empty();
        this.mIsWorkProfile = false;
        this.mSimulateConnectedForTests = false;
        this.mServiceId = 1;
        AnonymousClass6 anonymousClass6 = new AnonymousClass6();
        this.mSettingsPackageMonitor = anonymousClass6;
        this.mPm = context.getPackageManager();
        this.mServices = new ArrayList();
        this.mEnabledPackageNames = new HashSet();
        this.mExecutor = this.mContext.getMainExecutor();
        this.mCredentialManager = getCredentialManager(context, str.equals("credentials_test"));
        SettingContentObserver settingContentObserver =
                new SettingContentObserver(handler, context.getContentResolver());
        this.mSettingsContentObserver = settingContentObserver;
        settingContentObserver.register();
        anonymousClass6.register(context, context.getMainLooper(), false);
        this.mIconResizer = getResizer(context);
    }

    private SecRestrictedSwitchPreferenceScreen addProviderPreference(
            final Context context,
            CharSequence charSequence,
            Drawable drawable,
            final String str,
            CharSequence charSequence2,
            final CharSequence charSequence3,
            RestrictedLockUtils.EnforcedAdmin enforcedAdmin) {
        SecRestrictedSwitchPreferenceScreen secRestrictedSwitchPreferenceScreen =
                new SecRestrictedSwitchPreferenceScreen(context);
        secRestrictedSwitchPreferenceScreen.setChecked(this.mEnabledPackageNames.contains(str));
        secRestrictedSwitchPreferenceScreen.setTitle(charSequence);
        secRestrictedSwitchPreferenceScreen.setLayoutResource(R.layout.preference_icon_credman);
        secRestrictedSwitchPreferenceScreen.setIconSpaceReserved(true);
        if (Flags.newSettingsUi()) {
            secRestrictedSwitchPreferenceScreen.setIcon(processIcon(drawable));
        } else if (drawable != null) {
            secRestrictedSwitchPreferenceScreen.setIcon(drawable);
        }
        if (secRestrictedSwitchPreferenceScreen.mHelper.setDisabledByAdmin(enforcedAdmin)) {
            secRestrictedSwitchPreferenceScreen.notifyChanged();
        }
        secRestrictedSwitchPreferenceScreen.setOnPreferenceClickListener(
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.android.settings.applications.credentials.CredentialManagerPreferenceController.1
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        HashMap hashMap = new HashMap();
                        CredentialManagerPreferenceController
                                credentialManagerPreferenceController =
                                        CredentialManagerPreferenceController.this;
                        int i = credentialManagerPreferenceController.mServiceId;
                        credentialManagerPreferenceController.mServiceId = i + 1;
                        String num = Integer.toString(i);
                        String str2 = str;
                        hashMap.put(num, str2);
                        SALogging.insertSALog("ST8500", "ST8501", hashMap, 0);
                        CombinedProviderInfo.launchSettingsActivityIntent(
                                ((AbstractPreferenceController)
                                                credentialManagerPreferenceController)
                                        .mContext,
                                str2,
                                charSequence3,
                                credentialManagerPreferenceController.getUser());
                        return false;
                    }
                });
        secRestrictedSwitchPreferenceScreen.setOnPreferenceChangeListener(
                new Preference
                        .OnPreferenceChangeListener() { // from class:
                                                        // com.android.settings.applications.credentials.CredentialManagerPreferenceController.2
                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        boolean booleanValue = ((Boolean) obj).booleanValue();
                        String str2 = str;
                        CredentialManagerPreferenceController
                                credentialManagerPreferenceController =
                                        CredentialManagerPreferenceController.this;
                        if (!booleanValue) {
                            credentialManagerPreferenceController.togglePackageNameDisabled(str2);
                        } else {
                            if (credentialManagerPreferenceController
                                    .hasProviderLimitBeenReached()) {
                                Toast.makeText(
                                                context,
                                                ((AbstractPreferenceController)
                                                                credentialManagerPreferenceController)
                                                        .mContext
                                                        .getResources()
                                                        .getQuantityString(
                                                                R.plurals
                                                                        .sec_credman_services_limit_text,
                                                                4,
                                                                4),
                                                0)
                                        .show();
                                return false;
                            }
                            credentialManagerPreferenceController.togglePackageNameEnabled(str2);
                            if (credentialManagerPreferenceController.mPrefs.containsKey(str2)) {
                                ((SecRestrictedSwitchPreferenceScreen)
                                                credentialManagerPreferenceController.mPrefs.get(
                                                        str2))
                                        .setChecked(true);
                            }
                        }
                        return false;
                    }
                });
        return secRestrictedSwitchPreferenceScreen;
    }

    private Set<ComponentName> buildComponentNameSet(List<CredentialProviderInfo> list, boolean z) {
        HashSet hashSet = new HashSet();
        for (CredentialProviderInfo credentialProviderInfo : list) {
            if (!z || credentialProviderInfo.isPrimary()) {
                hashSet.add(credentialProviderInfo.getComponentName());
            }
        }
        return hashSet;
    }

    private void commitEnabledPackages() {
        if (this.mCredentialManager == null) {
            return;
        }
        HashSet hashSet = new HashSet();
        List<String> enabledSettings = getEnabledSettings();
        for (CredentialProviderInfo credentialProviderInfo : this.mServices) {
            if (credentialProviderInfo.isPrimary()) {
                String flattenToString =
                        credentialProviderInfo
                                .getServiceInfo()
                                .getComponentName()
                                .flattenToString();
                hashSet.add(flattenToString);
                enabledSettings.add(flattenToString);
            }
        }
        this.mCredentialManager.setEnabledProviders(
                new ArrayList(hashSet),
                enabledSettings,
                getUser(),
                this.mExecutor,
                new OutcomeReceiver() { // from class:
                                        // com.android.settings.applications.credentials.CredentialManagerPreferenceController.3
                    @Override // android.os.OutcomeReceiver
                    public final void onError(Throwable th) {
                        Log.e(
                                CredentialManagerPreferenceController.TAG,
                                "setEnabledProviders error: "
                                        + ((SetEnabledProvidersException) th).toString());
                    }

                    @Override // android.os.OutcomeReceiver
                    public final void onResult(Object obj) {
                        Log.i(
                                CredentialManagerPreferenceController.TAG,
                                "setEnabledProviders success");
                        CredentialManagerPreferenceController.this.updateFromExternal();
                    }
                });
    }

    public static String getCredentialAutofillService(Context context, String str) {
        try {
            return context.getResources().getString(android.R.string.date_time);
        } catch (Resources.NotFoundException e) {
            Log.e(str, "Failed to find credential autofill service.", e);
            return ApnSettings.MVNO_NONE;
        }
    }

    private CredentialManager getCredentialManager(Context context, boolean z) {
        Object systemService;
        if (z
                || (systemService = context.getSystemService("credential")) == null
                || !CredentialManager.isServiceEnabled(context)) {
            return null;
        }
        return (CredentialManager) systemService;
    }

    private Pair<List<CombinedProviderInfo>, CombinedProviderInfo> getProviders() {
        List buildMergedList =
                CombinedProviderInfo.buildMergedList(
                        AutofillServiceInfo.getAvailableServices(this.mContext, getUser()),
                        this.mServices,
                        getSelectedAutofillProvider(this.mContext, getUser(), TAG));
        return new Pair<>(buildMergedList, CombinedProviderInfo.getTopProvider(buildMergedList));
    }

    private static ImageUtils$IconResizer getResizer(Context context) {
        Resources resources = context.getResources();
        int dimension = (int) resources.getDimension(android.R.dimen.app_icon_size);
        return new ImageUtils$IconResizer(dimension, dimension, resources.getDisplayMetrics());
    }

    public static String getSelectedAutofillProvider(Context context, int i, String str) {
        String stringForUser =
                Settings.Secure.getStringForUser(
                        context.getContentResolver(), "autofill_service", i);
        if (TextUtils.isEmpty(stringForUser)) {
            return stringForUser;
        }
        if (stringForUser.equals(AUTOFILL_CREDMAN_ONLY_PROVIDER_PLACEHOLDER)) {
            return ApnSettings.MVNO_NONE;
        }
        return stringForUser.equals(
                        android.service.autofill.Flags.autofillCredmanDevIntegration()
                                ? getCredentialAutofillService(context, str)
                                : ApnSettings.MVNO_NONE)
                ? ApnSettings.MVNO_NONE
                : stringForUser;
    }

    private UserHandle getWorkProfileUserHandle() {
        if (this.mIsWorkProfile) {
            return Utils.getManagedProfile(UserManager.get(this.mContext));
        }
        return null;
    }

    private void handleIntent() {
        NewProviderConfirmationDialogFragment newNewProviderConfirmationDialogFragment;
        FragmentManager fragmentManager;
        ArrayList arrayList = new ArrayList(this.mPendingServiceInfos);
        this.mPendingServiceInfos.clear();
        if (arrayList.isEmpty()) {
            return;
        }
        ServiceInfo serviceInfo = (ServiceInfo) arrayList.get(0);
        ApplicationInfo applicationInfo = serviceInfo.applicationInfo;
        CharSequence loadLabel =
                applicationInfo.nonLocalizedLabel != null
                        ? applicationInfo.loadLabel(this.mPm)
                        : ApnSettings.MVNO_NONE;
        if (TextUtils.isEmpty(loadLabel)
                || (newNewProviderConfirmationDialogFragment =
                                newNewProviderConfirmationDialogFragment(
                                        serviceInfo.packageName, loadLabel, true))
                        == null
                || (fragmentManager = this.mFragmentManager) == null) {
            return;
        }
        newNewProviderConfirmationDialogFragment.show(
                fragmentManager, "CredentialManagerDialogFragment");
    }

    @VisibleForTesting
    public static boolean hasProviderLimitBeenReached(int i) {
        return i + 1 >= 5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$newAddServicePreference$0(
            Context context, Intent intent, Preference preference) {
        context.startActivityAsUser(intent, UserHandle.of(getUser()));
        return true;
    }

    private void maybeUpdateListOfPrefs(
            Pair<List<CombinedProviderInfo>, CombinedProviderInfo> pair) {
        PreferenceScreen preferenceScreen = this.mPreferenceScreen;
        if (preferenceScreen == null || this.mPreferenceGroup == null) {
            return;
        }
        Map<String, SecRestrictedSwitchPreferenceScreen> buildPreferenceList =
                buildPreferenceList(preferenceScreen.getContext(), pair);
        if (this.mPrefs.keySet().equals(buildPreferenceList.keySet())) {
            return;
        }
        this.mPrefs.clear();
        this.mPreferenceGroup.removeAll();
        this.mPrefs.putAll(buildPreferenceList);
        Iterator<SecRestrictedSwitchPreferenceScreen> it = buildPreferenceList.values().iterator();
        while (it.hasNext()) {
            this.mPreferenceGroup.addPreference(it.next());
        }
    }

    private void maybeUpdatePreferenceVisibility(
            Pair<List<CombinedProviderInfo>, CombinedProviderInfo> pair) {
        if (this.mPreferenceScreen == null || this.mPreferenceGroup == null) {
            return;
        }
        if (getAvailabilityStatus() != 0 || isHiddenDueToNoProviderSet(pair)) {
            this.mPreferenceScreen.removePreference(this.mPreferenceGroup);
            this.mPreferenceGroup.setVisible(false);
        } else {
            this.mPreferenceScreen.addPreference(this.mPreferenceGroup);
            this.mPreferenceGroup.setVisible(true);
        }
    }

    private ErrorDialogFragment newErrorDialogFragment() {
        return new ErrorDialogFragment(new AnonymousClass5());
    }

    private NewProviderConfirmationDialogFragment newNewProviderConfirmationDialogFragment(
            final String str, CharSequence charSequence, final boolean z) {
        NewProviderConfirmationDialogFragment newProviderConfirmationDialogFragment =
                new NewProviderConfirmationDialogFragment(
                        new DialogHost() { // from class:
                                           // com.android.settings.applications.credentials.CredentialManagerPreferenceController.4
                            @Override // com.android.settings.applications.credentials.CredentialManagerPreferenceController.DialogHost
                            public final void onDialogClick(int i) {
                                CredentialManagerPreferenceController.this
                                        .completeEnableProviderDialogBox(i, str, z);
                            }
                        });
        Bundle bundle = new Bundle();
        bundle.putString("package_name", str);
        bundle.putCharSequence("app_name", charSequence);
        newProviderConfirmationDialogFragment.setArguments(bundle);
        return newProviderConfirmationDialogFragment;
    }

    private void setActivityResult(int i) {
        Delegate delegate = this.mDelegate;
        if (delegate == null) {
            Log.e(TAG, "Missing delegate");
        } else {
            delegate.setActivityResult(i);
        }
    }

    private void update() {
        CredentialManager credentialManager = this.mCredentialManager;
        if (credentialManager == null) {
            return;
        }
        setAvailableServices(credentialManager.getCredentialProviderServices(getUser(), 3), null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFromExternal() {
        CredentialManager credentialManager = this.mCredentialManager;
        if (credentialManager == null) {
            return;
        }
        setAvailableServices(credentialManager.getCredentialProviderServices(getUser(), 3), null);
        PreferenceScreen preferenceScreen = this.mPreferenceScreen;
        if (preferenceScreen != null) {
            displayPreference(preferenceScreen);
        }
        Delegate delegate = this.mDelegate;
        if (delegate != null) {
            delegate.forceDelegateRefresh();
        }
    }

    @VisibleForTesting
    public Map<String, SecRestrictedSwitchPreferenceScreen> buildPreferenceList(
            Context context, Pair<List<CombinedProviderInfo>, CombinedProviderInfo> pair) {
        CombinedProviderInfo combinedProviderInfo = (CombinedProviderInfo) pair.second;
        List<CombinedProviderInfo> list = (List) pair.first;
        if (isHiddenDueToNoProviderSet(pair)) {
            forceDelegateRefresh();
            return new HashMap();
        }
        HashMap hashMap = new HashMap();
        for (CombinedProviderInfo combinedProviderInfo2 : list) {
            String str = combinedProviderInfo2.getApplicationInfo().packageName;
            if (combinedProviderInfo == null
                    || combinedProviderInfo.getApplicationInfo() == null
                    || !combinedProviderInfo.getApplicationInfo().packageName.equals(str)) {
                if (!((ArrayList) combinedProviderInfo2.mCredentialProviderInfos).isEmpty()) {
                    int user = getUser();
                    IconDrawableFactory newInstance = IconDrawableFactory.newInstance(context);
                    ServiceInfo brandingService = combinedProviderInfo2.getBrandingService();
                    ApplicationInfo applicationInfo = combinedProviderInfo2.getApplicationInfo();
                    Drawable badgedIcon =
                            (brandingService == null || applicationInfo == null)
                                    ? null
                                    : newInstance.getBadgedIcon(
                                            brandingService, applicationInfo, user);
                    Drawable badgedIcon2 =
                            badgedIcon != null
                                    ? badgedIcon
                                    : applicationInfo != null
                                            ? newInstance.getBadgedIcon(applicationInfo, user)
                                            : null;
                    CharSequence appName = combinedProviderInfo2.getAppName(context);
                    if (appName == null) {
                        appName = ApnSettings.MVNO_NONE;
                    }
                    hashMap.put(
                            str,
                            addProviderPreference(
                                    context,
                                    appName,
                                    badgedIcon2,
                                    str,
                                    combinedProviderInfo2.getSettingsSubtitle(),
                                    combinedProviderInfo2.getSettingsActivity(),
                                    combinedProviderInfo2.getDeviceAdminRestrictions(
                                            context, getUser())));
                }
            }
        }
        forceDelegateRefresh();
        return hashMap;
    }

    @VisibleForTesting
    public int completeEnableProviderDialogBox(int i, String str, boolean z) {
        FragmentManager fragmentManager;
        int i2 = 0;
        if (i == -1) {
            if (togglePackageNameEnabled(str)) {
                if (this.mPrefs.containsKey(str)) {
                    this.mPrefs.get(str).setChecked(true);
                }
                i2 = -1;
            } else {
                ErrorDialogFragment newErrorDialogFragment = newErrorDialogFragment();
                if (newErrorDialogFragment == null
                        || (fragmentManager = this.mFragmentManager) == null) {
                    return 0;
                }
                newErrorDialogFragment.show(fragmentManager, "CredentialManagerDialogFragment");
            }
        }
        if (i2 == -1 || !z) {
            setActivityResult(i2);
        }
        return i2;
    }

    @VisibleForTesting
    public SecRestrictedSwitchPreferenceScreen createPreference(
            Context context, CredentialProviderInfo credentialProviderInfo) {
        CharSequence label = credentialProviderInfo.getLabel(context);
        if (label == null) {
            label = ApnSettings.MVNO_NONE;
        }
        return addProviderPreference(
                context,
                label,
                credentialProviderInfo.getServiceIcon(this.mContext),
                credentialProviderInfo.getServiceInfo().packageName,
                credentialProviderInfo.getSettingsSubtitle(),
                credentialProviderInfo.getSettingsActivity(),
                null);
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        String preferenceKey = getPreferenceKey();
        if (TextUtils.isEmpty(preferenceKey)) {
            Log.w(TAG, "Skipping displayPreference because key is empty");
            return;
        }
        if (this.mPreferenceScreen == null) {
            this.mPreferenceScreen = preferenceScreen;
            this.mPreferenceGroup =
                    (PreferenceGroup) preferenceScreen.findPreference(preferenceKey);
        }
        Pair<List<CombinedProviderInfo>, CombinedProviderInfo> providers = getProviders();
        maybeUpdateListOfPrefs(providers);
        maybeUpdatePreferenceVisibility(providers);
    }

    @VisibleForTesting
    public void forceDelegateRefresh() {
        Delegate delegate = this.mDelegate;
        if (delegate != null) {
            delegate.forceDelegateRefresh();
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (!isConnected()) {
            return 3;
        }
        if (hasNonPrimaryServices()) {
            return (this.mIsWorkProfile && getWorkProfileUserHandle() == null) ? 2 : 0;
        }
        return 2;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @VisibleForTesting
    public Set<String> getEnabledProviders() {
        return this.mEnabledPackageNames;
    }

    @VisibleForTesting
    public List<String> getEnabledSettings() {
        ArrayList arrayList = new ArrayList();
        for (CredentialProviderInfo credentialProviderInfo : this.mServices) {
            ComponentName componentName =
                    credentialProviderInfo.getServiceInfo().getComponentName();
            if (this.mEnabledPackageNames.contains(
                    credentialProviderInfo.getServiceInfo().packageName)) {
                arrayList.add(componentName.flattenToString());
            }
        }
        return arrayList;
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

    public int getUser() {
        UserHandle workProfileUserHandle;
        return (!this.mIsWorkProfile
                        || (workProfileUserHandle = getWorkProfileUserHandle()) == null)
                ? UserHandle.myUserId()
                : workProfileUserHandle.getIdentifier();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @VisibleForTesting
    public boolean hasNonPrimaryServices() {
        Iterator<CredentialProviderInfo> it = this.mServices.iterator();
        while (it.hasNext()) {
            if (!it.next().isPrimary()) {
                return true;
            }
        }
        return false;
    }

    public void init(
            DashboardFragment dashboardFragment,
            FragmentManager fragmentManager,
            Intent intent,
            Delegate delegate,
            boolean z) {
        dashboardFragment.getSettingsLifecycle().addObserver(this);
        this.mFragmentManager = fragmentManager;
        this.mIsWorkProfile = z;
        setDelegate(delegate);
        verifyReceivedIntent(intent);
        SettingContentObserver settingContentObserver = this.mSettingsContentObserver;
        settingContentObserver.mContentResolver.unregisterContentObserver(settingContentObserver);
        this.mSettingsContentObserver.register();
    }

    @VisibleForTesting
    public boolean isConnected() {
        return this.mCredentialManager != null || this.mSimulateConnectedForTests;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @VisibleForTesting
    public boolean isHiddenDueToNoProviderSet(
            Pair<List<CombinedProviderInfo>, CombinedProviderInfo> pair) {
        return this.mSimulateHiddenForTests.isPresent()
                ? this.mSimulateHiddenForTests.get().booleanValue()
                : ((List) pair.first).size() == 0 || pair.second == null;
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

    @VisibleForTesting
    public Preference newAddServicePreference(String str, final Context context) {
        final Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        Preference preference = new Preference(context);
        preference.setOnPreferenceClickListener(
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.android.settings.applications.credentials.CredentialManagerPreferenceController$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference2) {
                        boolean lambda$newAddServicePreference$0;
                        lambda$newAddServicePreference$0 =
                                CredentialManagerPreferenceController.this
                                        .lambda$newAddServicePreference$0(
                                                context, intent, preference2);
                        return lambda$newAddServicePreference$0;
                    }
                });
        preference.setTitle(R.string.print_menu_item_add_service);
        preference.setOrder(2147483646);
        preference.setPersistent();
        try {
            preference.setIcon(R.drawable.ic_add_24dp);
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Failed to find icon for add services link", e);
        }
        return preference;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate(LifecycleOwner lifecycleOwner) {
        update();
    }

    @VisibleForTesting
    public Drawable processIcon(Drawable drawable) {
        int i;
        int i2;
        if (drawable == null) {
            drawable = this.mPm.getDefaultActivityIcon();
        }
        Drawable safeIcon = Utils.getSafeIcon(drawable);
        ImageUtils$IconResizer imageUtils$IconResizer = this.mIconResizer;
        final int i3 = imageUtils$IconResizer.mIconWidth;
        final int i4 = imageUtils$IconResizer.mIconHeight;
        if (safeIcon == null) {
            return new Drawable(
                    i3,
                    i4) { // from class:
                          // com.android.settings.applications.credentials.ImageUtils$EmptyDrawable
                public final int mHeight;
                public final int mWidth;

                {
                    this.mWidth = i3;
                    this.mHeight = i4;
                }

                @Override // android.graphics.drawable.Drawable
                public final int getIntrinsicHeight() {
                    return this.mHeight;
                }

                @Override // android.graphics.drawable.Drawable
                public final int getIntrinsicWidth() {
                    return this.mWidth;
                }

                @Override // android.graphics.drawable.Drawable
                public final int getMinimumHeight() {
                    return this.mHeight;
                }

                @Override // android.graphics.drawable.Drawable
                public final int getMinimumWidth() {
                    return this.mWidth;
                }

                @Override // android.graphics.drawable.Drawable
                public final int getOpacity() {
                    return -3;
                }

                @Override // android.graphics.drawable.Drawable
                public final void draw(Canvas canvas) {}

                @Override // android.graphics.drawable.Drawable
                public final void setAlpha(int i5) {}

                @Override // android.graphics.drawable.Drawable
                public final void setColorFilter(ColorFilter colorFilter) {}
            };
        }
        try {
            if (safeIcon instanceof PaintDrawable) {
                PaintDrawable paintDrawable = (PaintDrawable) safeIcon;
                paintDrawable.setIntrinsicWidth(i3);
                paintDrawable.setIntrinsicHeight(i4);
            } else if (safeIcon instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) safeIcon;
                if (bitmapDrawable.getBitmap().getDensity() == 0) {
                    bitmapDrawable.setTargetDensity(imageUtils$IconResizer.mMetrics);
                }
            }
            int intrinsicWidth = safeIcon.getIntrinsicWidth();
            int intrinsicHeight = safeIcon.getIntrinsicHeight();
            Drawable drawable2 = safeIcon;
            drawable2 = safeIcon;
            if (intrinsicWidth > 0 && intrinsicHeight > 0) {
                drawable2 = safeIcon;
                drawable2 = safeIcon;
                if (i3 >= intrinsicWidth && i4 >= intrinsicHeight) {
                    if (intrinsicWidth < i3 && intrinsicHeight < i4) {
                        Bitmap createBitmap = Bitmap.createBitmap(i3, i4, Bitmap.Config.ARGB_8888);
                        Canvas canvas = imageUtils$IconResizer.mCanvas;
                        canvas.setBitmap(createBitmap);
                        imageUtils$IconResizer.mOldBounds.set(safeIcon.getBounds());
                        int i5 = (i3 - intrinsicWidth) / 2;
                        int i6 = (i4 - intrinsicHeight) / 2;
                        safeIcon.setBounds(i5, i6, intrinsicWidth + i5, intrinsicHeight + i6);
                        safeIcon.draw(canvas);
                        safeIcon.setBounds(imageUtils$IconResizer.mOldBounds);
                        BitmapDrawable bitmapDrawable2 = new BitmapDrawable(createBitmap);
                        bitmapDrawable2.setTargetDensity(imageUtils$IconResizer.mMetrics);
                        imageUtils$IconResizer.mCanvas.setBitmap(null);
                        drawable2 = bitmapDrawable2;
                    }
                }
                float f = intrinsicWidth / intrinsicHeight;
                if (intrinsicWidth > intrinsicHeight) {
                    i2 = (int) (i3 / f);
                    i = i3;
                } else {
                    i = intrinsicHeight > intrinsicWidth ? (int) (i4 * f) : i3;
                    i2 = i4;
                }
                try {
                    Bitmap createBitmap2 =
                            Bitmap.createBitmap(
                                    i3,
                                    i4,
                                    safeIcon.getOpacity() != -1
                                            ? Bitmap.Config.ARGB_8888
                                            : Bitmap.Config.RGB_565);
                    Canvas canvas2 = imageUtils$IconResizer.mCanvas;
                    canvas2.setBitmap(createBitmap2);
                    imageUtils$IconResizer.mOldBounds.set(safeIcon.getBounds());
                    int i7 = (i3 - i) / 2;
                    int i8 = (i4 - i2) / 2;
                    safeIcon.setBounds(i7, i8, i7 + i, i8 + i2);
                    safeIcon.draw(canvas2);
                    safeIcon.setBounds(imageUtils$IconResizer.mOldBounds);
                    BitmapDrawable bitmapDrawable3 = new BitmapDrawable(createBitmap2);
                    bitmapDrawable3.setTargetDensity(imageUtils$IconResizer.mMetrics);
                    imageUtils$IconResizer.mCanvas.setBitmap(null);
                    drawable2 = bitmapDrawable3;
                } catch (Throwable unused) {
                    i3 = i;
                    i4 = i2;
                    return new Drawable(
                            i3,
                            i4) { // from class:
                                  // com.android.settings.applications.credentials.ImageUtils$EmptyDrawable
                        public final int mHeight;
                        public final int mWidth;

                        {
                            this.mWidth = i3;
                            this.mHeight = i4;
                        }

                        @Override // android.graphics.drawable.Drawable
                        public final int getIntrinsicHeight() {
                            return this.mHeight;
                        }

                        @Override // android.graphics.drawable.Drawable
                        public final int getIntrinsicWidth() {
                            return this.mWidth;
                        }

                        @Override // android.graphics.drawable.Drawable
                        public final int getMinimumHeight() {
                            return this.mHeight;
                        }

                        @Override // android.graphics.drawable.Drawable
                        public final int getMinimumWidth() {
                            return this.mWidth;
                        }

                        @Override // android.graphics.drawable.Drawable
                        public final int getOpacity() {
                            return -3;
                        }

                        @Override // android.graphics.drawable.Drawable
                        public final void draw(Canvas canvas3) {}

                        @Override // android.graphics.drawable.Drawable
                        public final void setAlpha(int i52) {}

                        @Override // android.graphics.drawable.Drawable
                        public final void setColorFilter(ColorFilter colorFilter) {}
                    };
                }
            }
            return drawable2;
        } catch (Throwable unused2) {
        }
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @VisibleForTesting
    public void setAvailableServices(List<CredentialProviderInfo> list, String str) {
        this.mFlagOverrideForTest = str;
        this.mServices.clear();
        this.mServices.addAll(list);
        handleIntent();
        this.mEnabledPackageNames.clear();
        for (CredentialProviderInfo credentialProviderInfo : list) {
            if (credentialProviderInfo.isEnabled() && !credentialProviderInfo.isPrimary()) {
                this.mEnabledPackageNames.add(credentialProviderInfo.getServiceInfo().packageName);
            }
        }
        for (String str2 : this.mPrefs.keySet()) {
            this.mPrefs.get(str2).setChecked(this.mEnabledPackageNames.contains(str2));
        }
    }

    @VisibleForTesting
    public void setDelegate(Delegate delegate) {
        this.mDelegate = delegate;
    }

    public void setSimulateConnectedForTests(boolean z) {
        this.mSimulateConnectedForTests = z;
    }

    @VisibleForTesting
    public void setSimulateHiddenForTests(Optional<Boolean> optional) {
        this.mSimulateHiddenForTests = optional;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @VisibleForTesting
    public void togglePackageNameDisabled(String str) {
        this.mEnabledPackageNames.remove(str);
        commitEnabledPackages();
    }

    @VisibleForTesting
    public boolean togglePackageNameEnabled(String str) {
        if (hasProviderLimitBeenReached()) {
            return false;
        }
        this.mEnabledPackageNames.add(str);
        commitEnabledPackages();
        return true;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    @VisibleForTesting
    public boolean verifyReceivedIntent(Intent intent) {
        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            boolean equals = TextUtils.equals(action, PRIMARY_INTENT);
            boolean equals2 = TextUtils.equals(action, ALTERNATE_INTENT);
            if (!equals && !equals2) {
                return false;
            }
            if (intent.getData() == null) {
                setActivityResult(0);
                return false;
            }
            String schemeSpecificPart = intent.getData().getSchemeSpecificPart();
            if (schemeSpecificPart == null) {
                setActivityResult(0);
                return false;
            }
            this.mPendingServiceInfos.clear();
            Iterator<CredentialProviderInfo> it = this.mServices.iterator();
            while (it.hasNext()) {
                ServiceInfo serviceInfo = it.next().getServiceInfo();
                if (serviceInfo.packageName.equals(schemeSpecificPart)) {
                    this.mPendingServiceInfos.add(serviceInfo);
                }
            }
            if (!this.mPendingServiceInfos.isEmpty()) {
                return true;
            }
            setActivityResult(0);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean hasProviderLimitBeenReached() {
        return hasProviderLimitBeenReached(this.mEnabledPackageNames.size());
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.credentials.CredentialManagerPreferenceController$5, reason: invalid class name */
    public final class AnonymousClass5 implements DialogHost {
        @Override // com.android.settings.applications.credentials.CredentialManagerPreferenceController.DialogHost
        public final void onDialogClick(int i) {}
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ErrorDialogFragment extends CredentialManagerDialogFragment {
        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            String string =
                    getContext()
                            .getString(
                                    Flags.newSettingsUi()
                                            ? R.string.credman_limit_error_msg_title
                                            : R.string.credman_error_message_title);
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mTitle = string;
            alertParams.mMessage =
                    getContext()
                            .getString(
                                    Flags.newSettingsUi()
                                            ? R.string.credman_limit_error_msg
                                            : R.string.credman_error_message);
            builder.setPositiveButton(android.R.string.ok, this);
            return builder.create();
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {}
    }
}
