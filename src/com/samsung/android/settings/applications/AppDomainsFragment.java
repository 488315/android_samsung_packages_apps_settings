package com.samsung.android.settings.applications;

import android.content.pm.PackageManager;
import android.content.pm.verify.domain.DomainVerificationManager;
import android.content.pm.verify.domain.DomainVerificationUserState;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.SecSwitchPreference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.applications.intentpicker.IntentPickerUtils;
import com.android.settings.applications.intentpicker.IntentPickerUtils$$ExternalSyntheticLambda1;
import com.android.settings.applications.intentpicker.IntentPickerUtils$$ExternalSyntheticLambda2;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class AppDomainsFragment extends SettingsPreferenceFragment
        implements Preference.OnPreferenceChangeListener {
    public DomainVerificationManager mDomainVerificationManager;
    public String mPackageName;
    public List mUrls;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3873;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.placeholder_prefs);
        Bundle arguments = getArguments();
        if (arguments != null) {
            this.mPackageName = arguments.getString("packageName");
        }
        DomainVerificationManager domainVerificationManager =
                (DomainVerificationManager)
                        getContext().getSystemService(DomainVerificationManager.class);
        this.mDomainVerificationManager = domainVerificationManager;
        this.mUrls =
                IntentPickerUtils.getLinksList(domainVerificationManager, this.mPackageName, 2);
        DomainVerificationUserState domainVerificationUserState =
                IntentPickerUtils.getDomainVerificationUserState(
                        this.mDomainVerificationManager, this.mPackageName);
        List<String> list =
                domainVerificationUserState == null
                        ? null
                        : (List)
                                domainVerificationUserState.getHostToStateMap().entrySet().stream()
                                        .filter(new IntentPickerUtils$$ExternalSyntheticLambda2())
                                        .map(new IntentPickerUtils$$ExternalSyntheticLambda1(1))
                                        .collect(Collectors.toList());
        List<String> list2 = this.mUrls;
        if (list2 != null) {
            for (String str : list2) {
                Preference preference = new Preference(getPrefContext());
                preference.setTitle(str);
                preference.setKey(UUID.randomUUID().toString());
                preference.setSelectable(false);
                getPreferenceScreen().addPreference(preference);
            }
        }
        if (list != null) {
            for (String str2 : list) {
                SecSwitchPreference secSwitchPreference = new SecSwitchPreference(getPrefContext());
                secSwitchPreference.setTitle(str2);
                List linksList =
                        IntentPickerUtils.getLinksList(
                                this.mDomainVerificationManager, this.mPackageName, 1);
                if (linksList != null) {
                    secSwitchPreference.setChecked(linksList.contains(str2));
                }
                secSwitchPreference.setOnPreferenceChangeListener(this);
                secSwitchPreference.setKey(UUID.randomUUID().toString());
                getPreferenceScreen().addPreference(secSwitchPreference);
            }
        }
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        ArraySet arraySet = new ArraySet();
        arraySet.add(preference.getTitle().toString());
        boolean booleanValue = ((Boolean) obj).booleanValue();
        DomainVerificationUserState domainVerificationUserState =
                IntentPickerUtils.getDomainVerificationUserState(
                        this.mDomainVerificationManager, this.mPackageName);
        if (domainVerificationUserState == null) {
            return false;
        }
        try {
            this.mDomainVerificationManager.setDomainVerificationUserSelection(
                    domainVerificationUserState.getIdentifier(), arraySet, booleanValue);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("AppDomainsFragment", "addSelectedItems : " + e.getMessage());
            return true;
        }
    }
}
