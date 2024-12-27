package com.samsung.android.settings.wifi.details;

import android.app.backup.BackupManager;
import android.content.Context;
import android.content.res.ColorStateList;
import android.net.wifi.WifiConfiguration;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.fragment.app.Fragment;
import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.wifi.WifiUtils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.settings.logging.SALogging;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiMeteredPreferenceController extends AbstractPreferenceController
        implements Preference.OnPreferenceChangeListener {
    public WifiConfiguration mConfig;
    public final Fragment mFragment;
    public final InputMethodManager mInputManager;
    public DropDownPreference mMeteredPref;
    public final String mSAScreenId;
    public final WifiEntry mWifiEntry;

    public WifiMeteredPreferenceController(
            Context context, WifiEntry wifiEntry, Fragment fragment, String str) {
        super(context);
        this.mWifiEntry = wifiEntry;
        this.mFragment = fragment;
        this.mInputManager = (InputMethodManager) context.getSystemService("input_method");
        this.mSAScreenId = str;
    }

    public final void disableViewsIfAppropriate$3() {
        DropDownPreference dropDownPreference;
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry == null || !wifiEntry.isSaved()) {
            return;
        }
        if ((WifiUtils.isBlockedByEnterprise(this.mContext, wifiEntry.getSsid())
                        || WifiUtils.isNetworkLockedDown(
                                this.mContext, wifiEntry.getWifiConfiguration()))
                && (dropDownPreference = this.mMeteredPref) != null) {
            dropDownPreference.setEnabled(false);
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry != null) {
            this.mConfig = wifiEntry.getWifiConfiguration();
        }
        DropDownPreference dropDownPreference =
                (DropDownPreference) preferenceScreen.findPreference("metered");
        this.mMeteredPref = dropDownPreference;
        dropDownPreference.setOnPreferenceClickListener(
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.samsung.android.settings.wifi.details.WifiMeteredPreferenceController$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        WifiMeteredPreferenceController wifiMeteredPreferenceController =
                                WifiMeteredPreferenceController.this;
                        if (!wifiMeteredPreferenceController.mInputManager.isInputMethodShown()) {
                            return false;
                        }
                        View currentFocus =
                                wifiMeteredPreferenceController
                                        .mFragment
                                        .getActivity()
                                        .getCurrentFocus();
                        if (currentFocus == null) {
                            Log.i(
                                    "WifiMeteredPrefCtrl",
                                    "hideSoftKeyboard focusedView is null force hide");
                            wifiMeteredPreferenceController.mInputManager.semForceHideSoftInput();
                        } else {
                            Log.i("WifiMeteredPrefCtrl", "hideSoftKeyboard");
                            wifiMeteredPreferenceController.mInputManager.hideSoftInputFromWindow(
                                    currentFocus.getWindowToken(), 2);
                        }
                        if (currentFocus == null) {
                            return false;
                        }
                        currentFocus.clearFocus();
                        return false;
                    }
                });
        WifiConfiguration wifiConfiguration = this.mConfig;
        int i = wifiConfiguration != null ? wifiConfiguration.meteredOverride : 0;
        this.mMeteredPref.setValue(Integer.toString(i));
        DropDownPreference dropDownPreference2 = this.mMeteredPref;
        ColorStateList colorStateList =
                this.mContext
                        .getResources()
                        .getColorStateList(
                                R.color.sec_preference_summary_primary_color,
                                this.mContext.getTheme());
        dropDownPreference2.setSummary(dropDownPreference2.mEntries[i]);
        dropDownPreference2.seslSetSummaryColor(colorStateList);
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return "metered";
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        WifiEntry wifiEntry = this.mWifiEntry;
        return wifiEntry == null || !(wifiEntry.isSubscription() || wifiEntry.semIsEphemeral());
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        Log.d("WifiMeteredPrefCtrl", "Metered onPreferenceChange checked : " + obj);
        String str = (String) obj;
        int parseInt = Integer.parseInt(str);
        HashMap hashMap = new HashMap();
        hashMap.put(
                "metered_type",
                parseInt == 0
                        ? "Detect automatically"
                        : parseInt == 1 ? "Treat as metered" : "Treat as unmetered");
        SALogging.insertSALog(this.mSAScreenId, "1027", hashMap, 0);
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry != null && (wifiEntry.isSaved() || wifiEntry.isSubscription())) {
            wifiEntry.setMeteredChoice(Integer.parseInt(str));
        }
        BackupManager.dataChanged("com.android.providers.settings");
        DropDownPreference dropDownPreference = (DropDownPreference) preference;
        int parseInt2 = Integer.parseInt(str);
        ColorStateList colorStateList =
                this.mContext
                        .getResources()
                        .getColorStateList(
                                R.color.sec_preference_summary_primary_color,
                                this.mContext.getTheme());
        dropDownPreference.setSummary(dropDownPreference.mEntries[parseInt2]);
        dropDownPreference.seslSetSummaryColor(colorStateList);
        disableViewsIfAppropriate$3();
        return true;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void updateState(Preference preference) {
        Log.d("WifiMeteredPrefCtrl", "updateState - entered ");
        super.updateState(preference);
        disableViewsIfAppropriate$3();
        Log.d("WifiMeteredPrefCtrl", "updateState - exited ");
    }
}
