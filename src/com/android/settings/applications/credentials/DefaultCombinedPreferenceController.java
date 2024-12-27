package com.android.settings.applications.credentials;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ServiceInfo;
import android.credentials.CredentialManager;
import android.credentials.SetEnabledProvidersException;
import android.graphics.drawable.Drawable;
import android.os.OutcomeReceiver;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.autofill.AutofillServiceInfo;
import android.text.TextUtils;
import android.util.IconDrawableFactory;
import android.util.Log;
import android.view.autofill.AutofillManager;
import android.widget.Button;

import androidx.preference.Preference;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.R;
import com.android.settings.applications.defaultapps.DefaultAppPreferenceController;
import com.android.settingslib.applications.DefaultAppInfo;

import com.samsung.android.settings.widget.SecGearPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class DefaultCombinedPreferenceController extends DefaultAppPreferenceController {
    public final AutofillManager mAutofillManager;
    public final CredentialManager mCredentialManager;
    public final Executor mExecutor;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.credentials.DefaultCombinedPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final /* synthetic */ String val$packageName;
        public final /* synthetic */ CharSequence val$settingsActivity;

        public AnonymousClass1(String str, CharSequence charSequence) {
            this.val$packageName = str;
            this.val$settingsActivity = charSequence;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.android.settings.applications.credentials.DefaultCombinedPreferenceController$2, reason: invalid class name */
    public final class AnonymousClass2 implements OutcomeReceiver {
        @Override // android.os.OutcomeReceiver
        public final void onError(Throwable th) {
            Log.e(
                    "DefaultCombinedPreferenceController",
                    "setEnabledProviders error: " + ((SetEnabledProvidersException) th).toString());
        }

        @Override // android.os.OutcomeReceiver
        public final void onResult(Object obj) {
            Log.i("DefaultCombinedPreferenceController", "setEnabledProviders success");
        }
    }

    static {
        new Intent("android.service.autofill.AutofillService");
    }

    public DefaultCombinedPreferenceController(Context context) {
        super(context);
        this.mExecutor = context.getMainExecutor();
        this.mAutofillManager =
                (AutofillManager) this.mContext.getSystemService(AutofillManager.class);
        if (CredentialManager.isServiceEnabled(context)) {
            this.mCredentialManager =
                    (CredentialManager) this.mContext.getSystemService(CredentialManager.class);
        } else {
            this.mCredentialManager = null;
        }
    }

    public final Intent createIntentToOpenPicker() {
        return new Intent(
                this.mContext.createContextAsUser(UserHandle.of(getUser()), 0),
                (Class<?>) CredentialsPickerActivity.class);
    }

    public final List getAllProviders$1(int i) {
        List availableServices = AutofillServiceInfo.getAvailableServices(this.mContext, i);
        String selectedAutofillProvider =
                CredentialManagerPreferenceController.getSelectedAutofillProvider(
                        this.mContext, i, "DefaultCombinedPreferenceController");
        ArrayList arrayList = new ArrayList();
        CredentialManager credentialManager = this.mCredentialManager;
        if (credentialManager != null) {
            arrayList.addAll(credentialManager.getCredentialProviderServices(i, 3));
        }
        return CombinedProviderInfo.buildMergedList(
                availableServices, arrayList, selectedAutofillProvider);
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public final DefaultAppInfo getDefaultAppInfo() {
        CombinedProviderInfo topProvider =
                CombinedProviderInfo.getTopProvider(getAllProviders$1(getUser()));
        if (topProvider == null) {
            return null;
        }
        ServiceInfo brandingService = topProvider.getBrandingService();
        return brandingService == null
                ? new DefaultAppInfo(
                        this.mContext,
                        this.mPackageManager,
                        getUser(),
                        topProvider.getApplicationInfo(),
                        topProvider.getSettingsSubtitle(),
                        true)
                : new DefaultAppInfo(
                        this.mContext,
                        this.mPackageManager,
                        getUser(),
                        brandingService,
                        topProvider.getSettingsSubtitle(),
                        true);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public String getPreferenceKey() {
        return "default_credman_autofill_main";
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public final Intent getSettingIntent(DefaultAppInfo defaultAppInfo) {
        return createIntentToOpenPicker();
    }

    public int getUser() {
        return UserHandle.myUserId();
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public boolean isAvailable() {
        AutofillManager autofillManager = this.mAutofillManager;
        return autofillManager != null
                && this.mCredentialManager != null
                && autofillManager.hasAutofillFeature()
                && this.mAutofillManager.isAutofillSupported();
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public final boolean showAppSummary() {
        return true;
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController
    public final boolean showLabelAsTitle() {
        return false;
    }

    @VisibleForTesting
    public void updatePreferenceForProvider(
            Preference preference,
            CharSequence charSequence,
            String str,
            Drawable drawable,
            final String str2,
            final CharSequence charSequence2) {
        if (charSequence == null) {
            preference.setTitle(R.string.app_list_preference_none);
        } else {
            preference.setTitle(charSequence);
        }
        if ("com.samsung.android.samsungpassautofill".equals(str2)) {
            preference.setTitle(R.string.sec_samsung_pass_title);
        }
        preference.setIcon((Drawable) null);
        preference.setSummary((CharSequence) null);
        if (preference instanceof SecGearPreference) {
            ((SecGearPreference) preference)
                    .setOnPreferenceClickListener(
                            new Preference
                                    .OnPreferenceClickListener() { // from class:
                                                                   // com.android.settings.applications.credentials.DefaultCombinedPreferenceController$$ExternalSyntheticLambda0
                                @Override // androidx.preference.Preference.OnPreferenceClickListener
                                public final boolean onPreferenceClick(Preference preference2) {
                                    CombinedProviderInfo.launchSettingsActivityIntent(
                                            r0.mContext,
                                            str2,
                                            charSequence2,
                                            DefaultCombinedPreferenceController.this.getUser());
                                    return true;
                                }
                            });
        }
        if (preference instanceof PrimaryProviderPreference) {
            PrimaryProviderPreference primaryProviderPreference =
                    (PrimaryProviderPreference) preference;
            primaryProviderPreference.mIconSize = 1;
            primaryProviderPreference.mDelegate = new AnonymousClass1(str2, charSequence2);
            boolean z = !TextUtils.isEmpty(charSequence2);
            Button button = primaryProviderPreference.mOpenButton;
            if (button != null) {
                button.setVisibility(z ? 0 : 8);
                primaryProviderPreference.mOpenButton.setVisibility(z ? 0 : 8);
            }
            primaryProviderPreference.mOpenButtonVisible = z;
            primaryProviderPreference.mButtonsCompactMode = charSequence != null;
            primaryProviderPreference.updateButtonFramePadding();
        }
    }

    @Override // com.android.settings.applications.defaultapps.DefaultAppPreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        Context context;
        super.updateState(preference);
        int user = getUser();
        CombinedProviderInfo topProvider =
                CombinedProviderInfo.getTopProvider(getAllProviders$1(user));
        if (topProvider != null
                && topProvider.getDeviceAdminRestrictions(this.mContext, user) != null) {
            if (this.mCredentialManager != null) {
                Settings.Secure.putStringForUser(
                        this.mContext.getContentResolver(), "autofill_service", null, getUser());
                this.mCredentialManager.setEnabledProviders(
                        List.of(), List.of(), getUser(), this.mExecutor, new AnonymousClass2());
            }
            topProvider = null;
        }
        int user2 = getUser();
        if (topProvider == null || (context = this.mContext) == null) {
            updatePreferenceForProvider(preference, null, null, null, null, null);
            return;
        }
        CharSequence appName = topProvider.getAppName(context);
        String settingsSubtitle = topProvider.getSettingsSubtitle();
        IconDrawableFactory newInstance = IconDrawableFactory.newInstance(this.mContext);
        ServiceInfo brandingService = topProvider.getBrandingService();
        ApplicationInfo applicationInfo = topProvider.getApplicationInfo();
        Drawable badgedIcon =
                (brandingService == null || applicationInfo == null)
                        ? null
                        : newInstance.getBadgedIcon(brandingService, applicationInfo, user2);
        if (badgedIcon == null) {
            badgedIcon =
                    applicationInfo != null
                            ? newInstance.getBadgedIcon(applicationInfo, user2)
                            : null;
        }
        ApplicationInfo applicationInfo2 = topProvider.getApplicationInfo();
        updatePreferenceForProvider(
                preference,
                appName,
                settingsSubtitle,
                badgedIcon,
                applicationInfo2 != null ? applicationInfo2.packageName : null,
                topProvider.getSettingsActivity());
    }
}
