package com.samsung.android.settings.wifi.details;

import android.content.Context;
import android.content.res.ColorStateList;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.widget.LayoutPreference;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.settings.scloud.SCloudWifiDataManager;
import com.samsung.android.wifitrackerlib.SemWifiUtils;
import com.sec.ims.settings.ImsProfile;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiPrivacyPreferenceController extends AbstractPreferenceController
        implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    public final Fragment mFragment;
    public final InputMethodManager mInputManager;
    public DropDownPreference mPrivacyPref;
    public LayoutPreference mPrivacyWarningPref;
    public final String mSAScreenId;
    public final WifiEntry mWifiEntry;
    public final WifiManager mWifiManager;

    public WifiPrivacyPreferenceController(
            Context context, WifiEntry wifiEntry, Fragment fragment, String str) {
        super(context);
        this.mWifiEntry = wifiEntry;
        this.mFragment = fragment;
        this.mInputManager = (InputMethodManager) context.getSystemService("input_method");
        this.mWifiManager = (WifiManager) context.getSystemService(ImsProfile.PDN_WIFI);
        this.mSAScreenId = str;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        DropDownPreference dropDownPreference =
                (DropDownPreference) preferenceScreen.findPreference("privacy");
        this.mPrivacyPref = dropDownPreference;
        dropDownPreference.setOnPreferenceChangeListener(
                new Preference
                        .OnPreferenceChangeListener() { // from class:
                                                        // com.samsung.android.settings.wifi.details.WifiPrivacyPreferenceController$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceChangeListener
                    public final boolean onPreferenceChange(Preference preference, Object obj) {
                        WifiPrivacyPreferenceController.this.onPreferenceChange(preference, obj);
                        return true;
                    }
                });
        this.mPrivacyPref.setOnPreferenceClickListener(
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.samsung.android.settings.wifi.details.WifiPrivacyPreferenceController$$ExternalSyntheticLambda1
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        WifiPrivacyPreferenceController.this.onPreferenceClick(preference);
                        return false;
                    }
                });
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference("privacy_warning_text");
        this.mPrivacyWarningPref = layoutPreference;
        TextView textView =
                (TextView) layoutPreference.mRootView.findViewById(R.id.wifi_error_text);
        if (textView != null) {
            textView.setText(
                    Utils.isTablet()
                            ? R.string.wifi_privacy_warning_message_tablet
                            : R.string.wifi_privacy_warning_message);
        }
        WifiEntry wifiEntry = this.mWifiEntry;
        int privacy = wifiEntry == null ? 1 : wifiEntry.getPrivacy();
        boolean z = wifiEntry == null || !wifiEntry.isSuggestion();
        this.mPrivacyPref.setSelectable(z);
        this.mPrivacyPref.setValue(Integer.toString(privacy));
        updateSummary$1(this.mPrivacyPref, z ? privacy : 1);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "privacy";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        WifiEntry wifiEntry;
        return this.mWifiManager.isConnectedMacRandomizationSupported()
                && ((wifiEntry = this.mWifiEntry) == null
                        || (!wifiEntry.isSubscription()
                                && !wifiEntry.semIsEphemeral()
                                && SemWifiUtils.isSupportRandomMac(wifiEntry)
                                && SemWifiUtils.isSupportRandomMacForLgu(wifiEntry)));
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Log.d("WifiPrivacyPrefCtrl", "Privacy onPreferenceChange checked : " + obj);
        int parseInt = Integer.parseInt((String) obj);
        HashMap hashMap = new HashMap();
        hashMap.put("mac_address_type", parseInt == 1 ? "Randomized MAC" : "Device MAC");
        SALogging.insertSALog(this.mSAScreenId, "1027", hashMap, 0);
        updateSummary$1((DropDownPreference) preference, parseInt);
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry == null) {
            return true;
        }
        if (wifiEntry.isSaved()) {
            SCloudWifiDataManager.getInstance(this.mContext.getApplicationContext())
                    .syncToAddOrUpdated(wifiEntry.getWifiConfiguration());
        }
        wifiEntry.setPrivacy(parseInt);
        if (wifiEntry.getConnectedState() == 2) {
            wifiEntry.disconnect(null);
            wifiEntry.connect(null);
        }
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        if (!this.mInputManager.isInputMethodShown()) {
            return false;
        }
        View currentFocus = this.mFragment.getActivity().getCurrentFocus();
        if (currentFocus == null) {
            Log.i("WifiPrivacyPrefCtrl", "hideSoftKeyboard focusedView is null force hide");
            this.mInputManager.semForceHideSoftInput();
        } else {
            Log.i("WifiPrivacyPrefCtrl", "hideSoftKeyboard");
            this.mInputManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 2);
        }
        if (currentFocus == null) {
            return false;
        }
        currentFocus.clearFocus();
        return false;
    }

    public final void updateSummary$1(DropDownPreference dropDownPreference, int i) {
        boolean z = false;
        char c = i == 1 ? (char) 0 : (char) 1;
        ColorStateList colorStateList =
                this.mContext
                        .getResources()
                        .getColorStateList(
                                R.color.sec_preference_summary_primary_color,
                                this.mContext.getTheme());
        dropDownPreference.setSummary(dropDownPreference.mEntries[c]);
        dropDownPreference.seslSetSummaryColor(colorStateList);
        LayoutPreference layoutPreference = this.mPrivacyWarningPref;
        if (layoutPreference != null) {
            if (isAvailable() && i != 1) {
                z = true;
            }
            layoutPreference.setVisible(z);
        }
    }
}
