package com.samsung.android.settings.deviceinfo.legalinfo;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.SystemProperties;
import android.util.Base64;
import android.util.Log;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;

import com.android.settings.Utils;
import com.android.settings.core.BasePreferenceController;

import com.samsung.android.settings.Rune;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;
import com.samsung.android.settings.logging.LoggingHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PrivacyPolicyPreferenceController extends BasePreferenceController {
    private static final String PRIVACY_POLICY_LINK_CN =
            new String(
                    Base64.decode(
                            "aHR0cHM6Ly90ZXJtcy5zYW1zdW5nY29uc2VudC5jb20vY2huZGV2aWNwcC9QRFAvMS4wL0NITi9DSE5femhvLmh0bWw=",
                            2));
    private static final String PRIVACY_POLICY_LINK_DEFAULT =
            "com.samsung.android.mobileservice.action.ACTION_SHOW_PRIVACY_POLICY";
    private static final String PRIVACY_POLICY_LINK_KR =
            "https://legal.samsungdm.com/kor/%s/pp.html";
    private static final int PRIVACY_POLICY_LINK_TYPE_CN = 2;
    private static final int PRIVACY_POLICY_LINK_TYPE_DEFAULT = 0;
    private static final int PRIVACY_POLICY_LINK_TYPE_KR = 1;
    private static final String TAG = "PrivacyPolicyPreferenceController";

    public PrivacyPolicyPreferenceController(Context context, String str) {
        super(context, str);
    }

    private String getLanguageCode() {
        return Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry();
    }

    private int getPrivacyPolicyLinkType() {
        if (Rune.isDomesticModel()) {
            return 1;
        }
        return isChinaModel() ? 2 : 0;
    }

    private boolean isChinaModel() {
        return "CN".equalsIgnoreCase(SystemProperties.get("ro.csc.countryiso_code"))
                || "china".equalsIgnoreCase(SystemProperties.get("ro.csc.country_code"));
    }

    private boolean isSAPPIntentAvailable() {
        return Utils.isIntentAvailable(this.mContext, new Intent(PRIVACY_POLICY_LINK_DEFAULT));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$displayPreference$0(Preference preference) {
        LoggingHelper.insertEventLogging(225, 9550);
        return false;
    }

    private void setPrivacyPolicyIntent(Preference preference) {
        Intent data;
        int privacyPolicyLinkType = getPrivacyPolicyLinkType();
        if (privacyPolicyLinkType == 1) {
            data =
                    new Intent()
                            .setAction("android.intent.action.VIEW")
                            .setData(
                                    Uri.parse(
                                            PRIVACY_POLICY_LINK_KR.replace(
                                                    "%s", getLanguageCode())));
        } else if (privacyPolicyLinkType != 2) {
            data = new Intent(PRIVACY_POLICY_LINK_DEFAULT);
        } else {
            data =
                    new Intent()
                            .setAction("android.intent.action.VIEW")
                            .setData(Uri.parse(PRIVACY_POLICY_LINK_CN));
        }
        if (preference != null) {
            preference.setIntent(data);
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        Preference findPreference = preferenceScreen.findPreference(getPreferenceKey());
        super.displayPreference(preferenceScreen);
        findPreference.setOnPreferenceClickListener(
                new PrivacyPolicyPreferenceController$$ExternalSyntheticLambda0());
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        if (Rune.isDomesticModel() || isSAPPIntentAvailable()) {
            return 0;
        }
        Log.i(TAG, "SA PP intent is not available");
        return 3;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
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

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        setPrivacyPolicyIntent(preference);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}
}
