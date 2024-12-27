package com.samsung.android.settings.wifi.details;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.net.wifi.WifiConfiguration;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.picker3.widget.SeslColorPicker$16$$ExternalSyntheticOutline0;
import androidx.preference.DropDownPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.window.area.WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.wifi.WifiUtils;
import com.android.settingslib.Utils;
import com.android.settingslib.core.AbstractPreferenceController;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnResume;
import com.android.settingslib.widget.LayoutPreference;
import com.android.wifitrackerlib.WifiEntry;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.widget.SecInsetCategoryPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class WifiEapPreferenceController extends AbstractPreferenceController
        implements Preference.OnPreferenceChangeListener, LifecycleObserver, OnResume {
    public final WifiConfiguration mConfig;
    public TextView mEapAnonymousErrorText;
    public LayoutPreference mEapAnonymousPref;
    public EditText mEapAnonymousView;
    public LayoutPreference mEapIdPref;
    public SecInsetCategoryPreference mEapInsetPref;
    public final AnonymousClass1 mEapMethodChangeListener;
    public DropDownPreference mEapMethodPref;
    public final Fragment mFragment;
    public final WifiImeHelper mImeHelper;
    public boolean mIsExpanded;
    public final AnonymousClass1 mListenerForHidingKeyboard;
    public LayoutPreference mPasswordPref;
    public int mPhase2Method;
    public DropDownPreference mPhase2Pref;
    public final Resources mRes;
    public PreferenceScreen mScreen;
    public final WifiEntry mWifiEntry;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.wifi.details.WifiEapPreferenceController$1, reason: invalid class name */
    public final class AnonymousClass1 implements Preference.OnPreferenceClickListener {
        public /* synthetic */ AnonymousClass1() {}

        @Override // androidx.preference.Preference.OnPreferenceClickListener
        public boolean onPreferenceClick(Preference preference) {
            WifiEapPreferenceController wifiEapPreferenceController =
                    WifiEapPreferenceController.this;
            View currentFocus =
                    wifiEapPreferenceController.mFragment.getActivity().getCurrentFocus();
            wifiEapPreferenceController.mImeHelper.hideSoftKeyboard(currentFocus);
            if (currentFocus == null) {
                return false;
            }
            currentFocus.clearFocus();
            return false;
        }
    }

    public WifiEapPreferenceController(
            Context context,
            Fragment fragment,
            WifiEntry wifiEntry,
            WifiImeHelper wifiImeHelper,
            Lifecycle lifecycle) {
        super(context);
        this.mListenerForHidingKeyboard = new AnonymousClass1();
        this.mEapMethodChangeListener = new AnonymousClass1();
        this.mFragment = fragment;
        this.mImeHelper = wifiImeHelper;
        this.mWifiEntry = wifiEntry;
        if (wifiEntry != null) {
            this.mConfig = wifiEntry.getWifiConfiguration();
        }
        this.mRes = context.getResources();
        this.mPhase2Method = -1;
        lifecycle.addObserver(this);
    }

    public final void disableViewsIfAppropriate() {
        WifiEntry wifiEntry = this.mWifiEntry;
        if (wifiEntry == null || !wifiEntry.isSaved()) {
            return;
        }
        if (WifiUtils.isBlockedByEnterprise(this.mContext, wifiEntry.getSsid())
                || WifiUtils.isNetworkLockedDown(this.mContext, wifiEntry.getWifiConfiguration())) {
            DropDownPreference dropDownPreference = this.mPhase2Pref;
            if (dropDownPreference != null) {
                dropDownPreference.setEnabled(false);
            }
            LayoutPreference layoutPreference = this.mEapAnonymousPref;
            if (layoutPreference != null) {
                layoutPreference.setEnabled(false);
            }
        }
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mScreen = preferenceScreen;
        LayoutPreference layoutPreference =
                (LayoutPreference) preferenceScreen.findPreference("eap_anonymous");
        this.mEapAnonymousPref = layoutPreference;
        ((TextView) layoutPreference.mRootView.findViewById(R.id.title))
                .setText(R.string.wifi_eap_anonymous);
        this.mEapAnonymousView =
                (EditText) this.mEapAnonymousPref.mRootView.findViewById(R.id.edittext);
        TextView textView =
                (TextView) this.mEapAnonymousPref.mRootView.findViewById(R.id.wifi_error_text);
        this.mEapAnonymousErrorText = textView;
        EditText editText = this.mEapAnonymousView;
        editText.addTextChangedListener(new WifiEapIdTextWatcher(editText, textView));
        this.mEapAnonymousErrorText.setVisibility(8);
        this.mEapInsetPref =
                (SecInsetCategoryPreference) preferenceScreen.findPreference("eap_inset");
        DropDownPreference dropDownPreference =
                (DropDownPreference) preferenceScreen.findPreference("eap_phase2");
        this.mPhase2Pref = dropDownPreference;
        dropDownPreference.setOnPreferenceChangeListener(this);
        DropDownPreference dropDownPreference2 = this.mPhase2Pref;
        AnonymousClass1 anonymousClass1 = this.mListenerForHidingKeyboard;
        dropDownPreference2.setOnPreferenceClickListener(anonymousClass1);
        DropDownPreference dropDownPreference3 =
                (DropDownPreference) preferenceScreen.findPreference("eap_method");
        this.mEapMethodPref = dropDownPreference3;
        dropDownPreference3.setOnPreferenceClickListener(anonymousClass1);
        this.mEapIdPref = (LayoutPreference) preferenceScreen.findPreference("eap_identity");
        this.mPasswordPref = (LayoutPreference) preferenceScreen.findPreference("wifi_password");
    }

    public final String getEapMethod() {
        DropDownPreference dropDownPreference = this.mEapMethodPref;
        if (dropDownPreference == null || !dropDownPreference.isVisible()) {
            return "NONE";
        }
        CharSequence entry = this.mEapMethodPref.getEntry();
        String charSequence = entry != null ? entry.toString() : "PEAP";
        DialogFragment$$ExternalSyntheticOutline0.m(
                "getEapMethod ", charSequence, "WifiEAPPreferenceController");
        return charSequence;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final String getPreferenceKey() {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public final boolean isAvailable() {
        return true;
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        int i;
        Log.d("WifiEAPPreferenceController", "onPreferenceChange checked : " + obj);
        int parseInt = Integer.parseInt((String) obj);
        if ("eap_phase2".equals(preference.getKey())) {
            this.mPhase2Method = parseInt;
            String eapMethod = getEapMethod();
            Log.d(
                    "WifiEAPPreferenceController",
                    "onPreferenceChange KEY_EAP_PHASE2 : "
                            + this.mPhase2Method
                            + ", eapMethod= "
                            + eapMethod);
            if ("PEAP".equals(eapMethod) && ((i = this.mPhase2Method) == 2 || i == 3 || i == 4)) {
                this.mEapIdPref.setVisible(false);
                this.mPasswordPref.setVisible(false);
            } else {
                this.mEapIdPref.setVisible(true);
                this.mPasswordPref.setVisible(true);
            }
        }
        updateSummary((DropDownPreference) preference, parseInt);
        disableViewsIfAppropriate();
        this.mImeHelper.updateImeOptions();
        return true;
    }

    @Override // com.android.settingslib.core.lifecycle.events.OnResume
    public final void onResume() {
        if (this.mEapAnonymousPref.isVisible()) {
            WifiConfiguration wifiConfiguration = this.mConfig;
            String anonymousIdentity =
                    wifiConfiguration != null
                            ? wifiConfiguration.enterpriseConfig.getAnonymousIdentity()
                            : ApnSettings.MVNO_NONE;
            if (TextUtils.isEmpty(anonymousIdentity)) {
                return;
            }
            this.mEapAnonymousView.setText(anonymousIdentity);
        }
    }

    public final void updatePreference(boolean z) {
        if (this.mScreen == null) {
            return;
        }
        this.mIsExpanded = z;
        String eapMethod = getEapMethod();
        if (z && ("PEAP".equals(eapMethod) || "TTLS".equals(eapMethod))) {
            WifiEntry wifiEntry = this.mWifiEntry;
            int i = 2;
            if (wifiEntry == null || wifiEntry.getConnectedState() != 2) {
                this.mEapAnonymousPref.setVisible(true);
                EditText editText = this.mEapAnonymousView;
                if (editText != null && TextUtils.isEmpty(editText.getText())) {
                    WifiConfiguration wifiConfiguration = this.mConfig;
                    String anonymousIdentity =
                            wifiConfiguration != null
                                    ? wifiConfiguration.enterpriseConfig.getAnonymousIdentity()
                                    : ApnSettings.MVNO_NONE;
                    if (!TextUtils.isEmpty(anonymousIdentity)) {
                        this.mEapAnonymousView.setText(anonymousIdentity);
                    }
                }
            } else {
                this.mEapAnonymousPref.setVisible(false);
                this.mEapAnonymousErrorText.setVisibility(8);
            }
            this.mPhase2Pref.setVisible(true);
            int i2 = this.mPhase2Method;
            if (i2 == -1) {
                WifiConfiguration wifiConfiguration2 = this.mConfig;
                if (wifiConfiguration2 != null) {
                    int phase2Method = wifiConfiguration2.enterpriseConfig.getPhase2Method();
                    Log.e(
                            "WifiEAPPreferenceController",
                            "getPhase2Override :: phase2 method :" + phase2Method);
                    String eapMethod2 = getEapMethod();
                    if ("PEAP".equals(eapMethod2)) {
                        if (phase2Method != 3) {
                            if (phase2Method != 4) {
                                if (phase2Method != 5) {
                                    if (phase2Method != 6) {
                                        if (phase2Method != 7) {
                                            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                                                    .m(
                                                            phase2Method,
                                                            "getPhase2Override :: Unknown phase2"
                                                                + " method :",
                                                            "WifiEAPPreferenceController");
                                        } else {
                                            i = 4;
                                        }
                                    }
                                    i = 3;
                                }
                                this.mPhase2Method = i;
                                i2 = i;
                            }
                            i = 1;
                            this.mPhase2Method = i;
                            i2 = i;
                        }
                        i = 0;
                        this.mPhase2Method = i;
                        i2 = i;
                    } else {
                        if ("TTLS".equals(eapMethod2)) {
                            if (phase2Method != 1) {
                                if (phase2Method != 2) {
                                    if (phase2Method != 3) {
                                        if (phase2Method != 4) {
                                            WindowAreaControllerImpl$RearDisplayPresentationSessionConsumer$$ExternalSyntheticOutline0
                                                    .m(
                                                            phase2Method,
                                                            "getPhase2Override :: Unknown phase2"
                                                                + " method :",
                                                            "WifiEAPPreferenceController");
                                        }
                                        i = 3;
                                    }
                                }
                                i = 1;
                            }
                            i = 0;
                        } else {
                            i = phase2Method;
                        }
                        this.mPhase2Method = i;
                        i2 = i;
                    }
                } else {
                    i2 = 0;
                }
            }
            if ("PEAP".equals(eapMethod)) {
                if (Utils.isWifiOnly(this.mContext)
                        || !this.mRes.getBoolean(android.R.bool.config_emergencyGestureEnabled)) {
                    this.mPhase2Pref.setEntries(R.array.sec_wifi_peap_phase2_entries);
                    this.mPhase2Pref.setEntryValues(R.array.sec_wifi_peap_phase2_values);
                    this.mPhase2Pref.setValueIndex(0);
                    this.mPhase2Pref.setSummary(
                            this.mRes.getStringArray(R.array.sec_wifi_peap_phase2_entries)[0]);
                } else {
                    this.mPhase2Pref.setEntries(R.array.sec_wifi_peap_phase2_with_sim_auth_entries);
                    this.mPhase2Pref.setEntryValues(
                            R.array.sec_wifi_peap_phase2_with_sim_auth_values);
                    this.mPhase2Pref.setValueIndex(0);
                    this.mPhase2Pref.setSummary(
                            this.mRes
                                    .getStringArray(
                                            R.array.sec_wifi_peap_phase2_with_sim_auth_entries)[0]);
                }
            } else if ("TTLS".equals(eapMethod)) {
                this.mPhase2Pref.setEntries(R.array.wifi_ttls_phase2_entries);
                this.mPhase2Pref.setEntryValues(R.array.wifi_phase2_values);
                this.mPhase2Pref.setValueIndex(0);
                this.mPhase2Pref.setSummary(
                        this.mRes.getStringArray(R.array.wifi_ttls_phase2_entries)[0]);
            }
            updateSummary(this.mPhase2Pref, i2);
        } else {
            this.mPhase2Pref.setVisible(false);
            this.mEapAnonymousPref.setVisible(false);
            this.mEapAnonymousErrorText.setVisibility(8);
        }
        this.mEapInsetPref.setVisible(true);
        disableViewsIfAppropriate();
        this.mImeHelper.updateImeOptions();
    }

    public final void updateSummary(DropDownPreference dropDownPreference, int i) {
        ColorStateList colorStateList =
                this.mContext
                        .getResources()
                        .getColorStateList(
                                R.color.sec_preference_summary_primary_color,
                                this.mContext.getTheme());
        dropDownPreference.setValueIndex(i);
        dropDownPreference.setSummary(dropDownPreference.mEntries[i]);
        dropDownPreference.seslSetSummaryColor(colorStateList);
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class WifiEapIdTextWatcher implements TextWatcher {
        public final EditText mEditText;
        public final TextView mErrorText;
        public String mPreviousString;

        public WifiEapIdTextWatcher(EditText editText, TextView textView) {
            this.mEditText = editText;
            this.mErrorText = textView;
        }

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            int length;
            if (charSequence == null
                    || (length = charSequence.toString().getBytes().length) <= 0
                    || length > 200) {
                return;
            }
            this.mPreviousString = charSequence.toString();
        }

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (charSequence.toString().getBytes().length <= 200) {
                this.mErrorText.setVisibility(8);
                this.mEditText.setBackgroundTintList(
                        ((AbstractPreferenceController) WifiEapPreferenceController.this)
                                .mContext
                                .getResources()
                                .getColorStateList(R.color.dashboard_tab_selected_color));
                return;
            }
            this.mEditText.setPrivateImeOptions(
                    "inputType=PredictionOff;disableEmoticonInput=true");
            String str = this.mPreviousString;
            if (str == null || str.getBytes().length > 200) {
                Log.e(
                        "WifiEAPPreferenceController",
                        "String: "
                                + ((Object) charSequence)
                                + " start: "
                                + i
                                + " before: "
                                + i2
                                + " count: "
                                + i3);
                this.mEditText.setText(ApnSettings.MVNO_NONE);
            } else {
                this.mEditText.setText(this.mPreviousString);
            }
            this.mEditText.setPrivateImeOptions(
                    "inputType=PredictionOff;disableEmoticonInput=true;defaultInputmode=english");
            this.mErrorText.setText(R.string.max_char_reached);
            this.mErrorText.setVisibility(0);
            this.mEditText.setBackgroundTintList(
                    ((AbstractPreferenceController) WifiEapPreferenceController.this)
                            .mContext
                            .getResources()
                            .getColorStateList(R.color.sec_wifi_dialog_error_color));
            SeslColorPicker$16$$ExternalSyntheticOutline0.m(this.mEditText);
        }

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {}
    }
}
