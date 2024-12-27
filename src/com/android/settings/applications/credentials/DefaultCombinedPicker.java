package com.android.settings.applications.credentials;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.credentials.CredentialManager;
import android.credentials.CredentialProviderInfo;
import android.credentials.SetEnabledProvidersException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.OutcomeReceiver;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.autofill.AutofillServiceInfo;
import android.service.autofill.Flags;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;

import com.android.internal.content.PackageMonitor;
import com.android.settings.R;
import com.android.settingslib.RestrictedSelectorWithWidgetPreference;
import com.android.settingslib.applications.DefaultAppInfo;
import com.android.settingslib.widget.CandidateInfo;
import com.android.settingslib.widget.SelectorWithWidgetPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.applications.defaultapps.DefaultCombinedProviderAppPickerFragment;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.logging.status.BaseStatusLoggingProvider;
import com.samsung.android.settings.logging.status.StatusData;
import com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider;
import com.samsung.android.settings.widget.SecUnclickablePreference;
import com.sec.ims.configuration.DATA;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DefaultCombinedPicker extends DefaultCombinedProviderAppPickerFragment {
    public DefaultCombinedPicker$$ExternalSyntheticLambda0 mCancelListener;
    public CredentialManager mCredentialManager;
    public int mIntentSenderUserId = -1;
    public final AnonymousClass1 mSettingsPackageMonitor = new AnonymousClass1();
    public static final Handler sMainHandler = new Handler(Looper.getMainLooper());
    public static final StatusLogger$StatusLoggingProvider STATUS_LOGGING_PROVIDER =
            new AnonymousClass3();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.credentials.DefaultCombinedPicker$1, reason: invalid class name */
    public final class AnonymousClass1 extends PackageMonitor {
        public AnonymousClass1() {}

        public final void onPackageAdded(String str, int i) {
            DefaultCombinedPicker.sMainHandler.post(
                    new DefaultCombinedPicker$1$$ExternalSyntheticLambda0(this, 2));
        }

        public final void onPackageModified(String str) {
            DefaultCombinedPicker.sMainHandler.post(
                    new DefaultCombinedPicker$1$$ExternalSyntheticLambda0(this, 0));
        }

        public final void onPackageRemoved(String str, int i) {
            DefaultCombinedPicker.sMainHandler.post(
                    new DefaultCombinedPicker$1$$ExternalSyntheticLambda0(this, 1));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.credentials.DefaultCombinedPicker$2, reason: invalid class name */
    public final class AnonymousClass2 implements OutcomeReceiver {
        @Override // android.os.OutcomeReceiver
        public final void onError(Throwable th) {
            Log.e(
                    "DefaultCombinedPicker",
                    "setEnabledProviders error: " + ((SetEnabledProvidersException) th).toString());
        }

        @Override // android.os.OutcomeReceiver
        public final void onResult(Object obj) {
            Log.i("DefaultCombinedPicker", "setEnabledProviders success");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.credentials.DefaultCombinedPicker$3, reason: invalid class name */
    public final class AnonymousClass3 extends BaseStatusLoggingProvider {
        @Override // com.samsung.android.settings.logging.status.StatusLogger$StatusLoggingProvider
        public final List getStatusLoggingData(Context context) {
            int myUserId = UserHandle.myUserId();
            ArrayList arrayList = new ArrayList();
            Handler handler = DefaultCombinedPicker.sMainHandler;
            String stringForUser =
                    Settings.Secure.getStringForUser(
                            context.getContentResolver(), "autofill_service", myUserId);
            String stringForUser2 =
                    Settings.Secure.getStringForUser(
                            context.getContentResolver(), "credential_service_primary", myUserId);
            if (TextUtils.isEmpty(stringForUser)) {
                stringForUser = !TextUtils.isEmpty(stringForUser2) ? stringForUser2 : "None";
            }
            StatusData statusData = new StatusData();
            statusData.mStatusValue = stringForUser;
            statusData.mStatusKey = "ST8504";
            arrayList.add(statusData);
            CredentialManager credentialManager =
                    (CredentialManager) context.getSystemService("credential");
            if (credentialManager == null) {
                return arrayList;
            }
            List<CredentialProviderInfo> credentialProviderServices =
                    credentialManager.getCredentialProviderServices(myUserId, 3);
            StringBuilder sb = new StringBuilder();
            int i = 0;
            for (CredentialProviderInfo credentialProviderInfo : credentialProviderServices) {
                if (credentialProviderInfo.isEnabled() && !credentialProviderInfo.isPrimary()) {
                    i++;
                    sb.append(credentialProviderInfo.getComponentName().flattenToString());
                    sb.append(":");
                }
            }
            if (sb.length() > 0) {
                sb.insert(0, i + ";");
                sb.deleteCharAt(sb.length() + (-1));
            } else {
                sb.append(DATA.DM_FIELD_INDEX.PCSCF_DOMAIN);
            }
            String sb2 = sb.toString();
            StatusData statusData2 = new StatusData();
            statusData2.mStatusValue = sb2;
            statusData2.mStatusKey = "ST8505";
            arrayList.add(statusData2);
            return arrayList;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class AutofillPickerConfirmationDialogFragment
            extends DefaultCombinedProviderAppPickerFragment.ConfirmationDialogFragment {
        @Override // com.android.settingslib.core.lifecycle.ObservableDialogFragment,
                  // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            this.mCancelListener = ((DefaultCombinedPicker) getTargetFragment()).mCancelListener;
            super.onCreate(bundle);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CredentialManagerDefaultAppInfo extends DefaultAppInfo {
        public final CombinedProviderInfo mCombinedProviderInfo;

        public CredentialManagerDefaultAppInfo(
                Context context,
                PackageManager packageManager,
                int i,
                PackageItemInfo packageItemInfo,
                CombinedProviderInfo combinedProviderInfo) {
            super(
                    context,
                    packageManager,
                    i,
                    packageItemInfo,
                    combinedProviderInfo.getSettingsSubtitle(),
                    true);
            this.mCombinedProviderInfo = combinedProviderInfo;
        }
    }

    @Override // com.samsung.android.settings.applications.defaultapps.DefaultCombinedProviderAppPickerFragment, com.android.settings.widget.RadioButtonPickerFragment
    public final void bindPreferenceExtra(
            SelectorWithWidgetPreference selectorWithWidgetPreference,
            String str,
            CandidateInfo candidateInfo,
            String str2) {
        super.bindPreferenceExtra(selectorWithWidgetPreference, str, candidateInfo, str2);
        if (!(candidateInfo instanceof CredentialManagerDefaultAppInfo)) {
            Log.e(
                    "DefaultCombinedPicker",
                    "Candidate info should be a subclass of CredentialManagerDefaultAppInfo");
            return;
        }
        if (!(selectorWithWidgetPreference instanceof RestrictedSelectorWithWidgetPreference)) {
            Log.e(
                    "DefaultCombinedPicker",
                    "Preference should be a subclass of RestrictedSelectorWithWidgetPreference");
            return;
        }
        RestrictedSelectorWithWidgetPreference restrictedSelectorWithWidgetPreference =
                (RestrictedSelectorWithWidgetPreference) selectorWithWidgetPreference;
        if (restrictedSelectorWithWidgetPreference.mHelper.setDisabledByAdmin(
                ((CredentialManagerDefaultAppInfo) candidateInfo)
                        .mCombinedProviderInfo.getDeviceAdminRestrictions(
                                getContext(), getUser()))) {
            restrictedSelectorWithWidgetPreference.notifyChanged();
        }
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final SelectorWithWidgetPreference createPreference() {
        return new RestrictedSelectorWithWidgetPreference(getPrefContext());
    }

    public final List getAllProviders(int i) {
        Context context = getContext();
        List availableServices = AutofillServiceInfo.getAvailableServices(context, i);
        if (this.mCredentialManager == null) {
            this.mCredentialManager =
                    (CredentialManager) getContext().getSystemService(CredentialManager.class);
        }
        CredentialManager credentialManager = this.mCredentialManager;
        ArrayList arrayList = new ArrayList();
        if (credentialManager != null) {
            arrayList.addAll(credentialManager.getCredentialProviderServices(i, 3));
        }
        return CombinedProviderInfo.buildMergedList(
                availableServices,
                arrayList,
                CredentialManagerPreferenceController.getSelectedAutofillProvider(
                        context, i, "DefaultCombinedPicker"));
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final List getCandidates() {
        Context context = getContext();
        int user = getUser();
        List allProviders = getAllProviders(user);
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) allProviders).iterator();
        while (it.hasNext()) {
            CombinedProviderInfo combinedProviderInfo = (CombinedProviderInfo) it.next();
            ServiceInfo brandingService = combinedProviderInfo.getBrandingService();
            ApplicationInfo applicationInfo = combinedProviderInfo.getApplicationInfo();
            if (brandingService != null) {
                arrayList.add(
                        new CredentialManagerDefaultAppInfo(
                                context, this.mPm, user, brandingService, combinedProviderInfo));
            } else if (applicationInfo != null) {
                arrayList.add(
                        new CredentialManagerDefaultAppInfo(
                                context, this.mPm, user, applicationInfo, combinedProviderInfo));
            }
        }
        return arrayList;
    }

    @Override // com.samsung.android.settings.applications.defaultapps.DefaultCombinedProviderAppPickerFragment
    public final CharSequence getConfirmationMessage(CandidateInfo candidateInfo) {
        if (candidateInfo == null) {
            return null;
        }
        CharSequence loadLabel = candidateInfo.loadLabel();
        if (candidateInfo instanceof CredentialManagerDefaultAppInfo) {
            loadLabel =
                    ((CredentialManagerDefaultAppInfo) candidateInfo)
                            .mCombinedProviderInfo.getAppName(getContext());
        }
        return Html.fromHtml(
                getContext()
                        .getString(
                                R.string.autofill_confirmation_message,
                                Html.escapeHtml(loadLabel)));
    }

    @Override // com.samsung.android.settings.applications.defaultapps.DefaultCombinedProviderAppPickerFragment
    public final CharSequence getConfirmationTitle(CandidateInfo candidateInfo) {
        if (candidateInfo == null) {
            return null;
        }
        return getContext().getString(R.string.sec_autofill_select_popup_title);
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final String getDefaultKey() {
        ApplicationInfo applicationInfo;
        int user = getUser();
        CombinedProviderInfo topProvider =
                CombinedProviderInfo.getTopProvider(getAllProviders(user));
        return (topProvider == null
                        || topProvider.getDeviceAdminRestrictions(getContext(), user) != null
                        || (applicationInfo = topProvider.getApplicationInfo()) == null)
                ? ApnSettings.MVNO_NONE
                : applicationInfo.packageName;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 792;
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.default_credman_picker;
    }

    public int getUser() {
        int i = this.mIntentSenderUserId;
        return i >= 0 ? i : UserHandle.myUserId();
    }

    @Override // com.samsung.android.settings.applications.defaultapps.DefaultCombinedProviderAppPickerFragment
    public final DefaultCombinedProviderAppPickerFragment.ConfirmationDialogFragment
            newConfirmationDialogFragment(String str, CharSequence charSequence) {
        AutofillPickerConfirmationDialogFragment autofillPickerConfirmationDialogFragment =
                new AutofillPickerConfirmationDialogFragment();
        autofillPickerConfirmationDialogFragment.init(this, str, charSequence, null);
        return autofillPickerConfirmationDialogFragment;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.settings.applications.credentials.DefaultCombinedPicker$$ExternalSyntheticLambda0] */
    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        final FragmentActivity activity = getActivity();
        if (activity != null && activity.getIntent().getStringExtra("package_name") != null) {
            this.mCancelListener =
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.android.settings.applications.credentials.DefaultCombinedPicker$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            Activity activity2 = activity;
                            Handler handler = DefaultCombinedPicker.sMainHandler;
                            activity2.setResult(0);
                            activity2.finish();
                        }
                    };
            this.mIntentSenderUserId = UserHandle.myUserId();
        }
        getUser();
        if (activity != null) {
            this.mSettingsPackageMonitor.register(activity, activity.getMainLooper(), false);
        }
        update$7$1();
        SALogging.insertSALog("ST8502");
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        this.mSettingsPackageMonitor.unregister();
        super.onDestroy();
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean setDefaultKey(String str) {
        CombinedProviderInfo combinedProviderInfo;
        String stringExtra;
        Iterator it = ((ArrayList) getAllProviders(getUser())).iterator();
        while (true) {
            if (!it.hasNext()) {
                combinedProviderInfo = null;
                break;
            }
            combinedProviderInfo = (CombinedProviderInfo) it.next();
            if (combinedProviderInfo.getApplicationInfo().packageName.equals(str)) {
                break;
            }
        }
        if (combinedProviderInfo == null) {
            setProviders(null, new ArrayList());
            return true;
        }
        ArrayList arrayList = new ArrayList();
        Iterator it2 = ((ArrayList) combinedProviderInfo.mCredentialProviderInfos).iterator();
        while (it2.hasNext()) {
            arrayList.add(
                    ((CredentialProviderInfo) it2.next())
                            .getServiceInfo()
                            .getComponentName()
                            .flattenToString());
        }
        AutofillServiceInfo autofillServiceInfo = combinedProviderInfo.mAutofillServiceInfo;
        setProviders(
                autofillServiceInfo != null
                        ? autofillServiceInfo.getServiceInfo().getComponentName().flattenToString()
                        : null,
                arrayList);
        FragmentActivity activity = getActivity();
        if (activity != null
                && (stringExtra = activity.getIntent().getStringExtra("package_name")) != null) {
            activity.setResult((str == null || !str.startsWith(stringExtra)) ? 0 : -1);
            activity.finish();
        }
        return true;
    }

    public final void setProviders(String str, List list) {
        boolean z;
        int i = 0;
        if (getDefaultKey().isEmpty()
                || getDefaultKey().contains("com.samsung.android.samsungpassautofill")) {
            Log.d("DefaultCombinedPicker", "previous top provider is " + getDefaultKey());
            z = true;
        } else {
            z = false;
        }
        if (TextUtils.isEmpty(str) && ((ArrayList) list).size() > 0) {
            str =
                    Flags.autofillCredmanDevIntegration()
                            ? CredentialManagerPreferenceController.getCredentialAutofillService(
                                    getContext(), "DefaultCombinedPicker")
                            : CredentialManagerPreferenceController
                                    .AUTOFILL_CREDMAN_ONLY_PROVIDER_PLACEHOLDER;
        }
        Settings.Secure.putStringForUser(
                getContext().getContentResolver(), "autofill_service", str, getUser());
        if (this.mCredentialManager == null) {
            this.mCredentialManager =
                    (CredentialManager) getContext().getSystemService(CredentialManager.class);
        }
        CredentialManager credentialManager = this.mCredentialManager;
        if (credentialManager == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        List<CredentialProviderInfo> credentialProviderServices =
                credentialManager.getCredentialProviderServices(getUser(), 3);
        if (z) {
            for (CredentialProviderInfo credentialProviderInfo : credentialProviderServices) {
                if (credentialProviderInfo.isEnabled() && !credentialProviderInfo.isPrimary()) {
                    i++;
                }
            }
        }
        for (CredentialProviderInfo credentialProviderInfo2 : credentialProviderServices) {
            if (credentialProviderInfo2.isEnabled() && !credentialProviderInfo2.isPrimary()) {
                arrayList.add(
                        credentialProviderInfo2
                                .getServiceInfo()
                                .getComponentName()
                                .flattenToString());
            } else if (z
                    && "com.samsung.android.samsungpassautofill"
                            .equals(credentialProviderInfo2.getServiceInfo().packageName)
                    && i < 4) {
                arrayList.add(
                        credentialProviderInfo2
                                .getServiceInfo()
                                .getComponentName()
                                .flattenToString());
            }
        }
        arrayList.addAll(list);
        if (TextUtils.isEmpty(str) && ((ArrayList) list).isEmpty()) {
            arrayList.clear();
        }
        credentialManager.setEnabledProviders(
                list, arrayList, getUser(), getContext().getMainExecutor(), new AnonymousClass2());
    }

    @Override // com.android.settings.widget.RadioButtonPickerFragment
    public final boolean shouldShowItemNone() {
        return true;
    }

    public final void update$7$1() {
        Preference preference;
        updateCandidates();
        String stringForUser =
                Settings.Secure.getStringForUser(
                        getActivity().getContentResolver(),
                        "autofill_service_search_uri",
                        getUser());
        if (TextUtils.isEmpty(stringForUser)) {
            preference = null;
        } else {
            final Intent intent =
                    new Intent("android.intent.action.VIEW", Uri.parse(stringForUser));
            final Context prefContext = getPrefContext();
            preference = new Preference(prefContext);
            preference.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.applications.credentials.DefaultCombinedPicker$$ExternalSyntheticLambda1
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference2) {
                            Context context = prefContext;
                            Intent intent2 = intent;
                            Handler handler = DefaultCombinedPicker.sMainHandler;
                            context.startActivityAsUser(
                                    intent2, UserHandle.of(DefaultCombinedPicker.this.getUser()));
                            SALogging.insertSALog("ST8502", "PS8502");
                            return true;
                        }
                    });
            preference.setTitle(R.string.sec_autofill_add_service);
            preference.setLayoutResource(R.layout.sec_preference_radio);
            preference.setWidgetLayoutResource(R.layout.sec_preference_widget_add);
            preference.setIconSpaceReserved(false);
            preference.setOrder(2147483646);
            preference.setPersistent();
        }
        if (preference != null) {
            getPreferenceScreen().addPreference(preference);
        }
        SecUnclickablePreference secUnclickablePreference =
                new SecUnclickablePreference(getPrefContext(), null);
        secUnclickablePreference.setTitle(R.string.sec_spass_set_a_preferred_service);
        secUnclickablePreference.setKey("description");
        secUnclickablePreference.setOrder(-1);
        secUnclickablePreference.mPositionMode = 1;
        getPreferenceScreen().addPreference(secUnclickablePreference);
    }
}
