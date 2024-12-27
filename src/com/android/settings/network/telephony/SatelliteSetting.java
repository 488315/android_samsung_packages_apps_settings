package com.android.settings.network.telephony;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.TelephonyManager;
import android.telephony.satellite.SatelliteManager;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;

import com.android.settings.R;
import com.android.settings.dashboard.RestrictedDashboardFragment;
import com.android.settingslib.HelpUtils;
import com.android.settingslib.Utils;
import com.android.settingslib.widget.FooterPreference;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SatelliteSetting extends RestrictedDashboardFragment {
    public FragmentActivity mActivity;
    public CarrierConfigManager mCarrierConfigManager;
    public PersistableBundle mConfigBundle;
    public SatelliteManager mSatelliteManager;
    public int mSubId;
    public TelephonyManager mTelephonymanager;

    public SatelliteSetting() {
        super("no_config_mobile_networks");
    }

    @Override // com.android.settings.dashboard.DashboardFragment
    /* renamed from: getLogTag */
    public final String getTAG() {
        return "SatelliteSetting";
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 2071;
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment
    public final int getPreferenceScreenResId() {
        return R.xml.satellite_setting;
    }

    @Override // com.android.settings.dashboard.RestrictedDashboardFragment,
              // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        FragmentActivity activity = getActivity();
        this.mActivity = activity;
        this.mSubId = activity.getIntent().getIntExtra("sub_id", -1);
        CarrierConfigManager carrierConfigManager =
                (CarrierConfigManager) this.mActivity.getSystemService(CarrierConfigManager.class);
        this.mCarrierConfigManager = carrierConfigManager;
        PersistableBundle configForSubId =
                carrierConfigManager.getConfigForSubId(
                        this.mSubId, "satellite_attach_supported_bool");
        if (configForSubId.isEmpty()) {
            Log.d("SatelliteSetting", "SatelliteSettings: getDefaultConfig");
            configForSubId = CarrierConfigManager.getDefaultConfig();
        }
        if (configForSubId.getBoolean("satellite_attach_supported_bool", false)) {
            this.mTelephonymanager =
                    (TelephonyManager) this.mActivity.getSystemService(TelephonyManager.class);
            this.mSatelliteManager =
                    (SatelliteManager) this.mActivity.getSystemService(SatelliteManager.class);
        } else {
            Log.d(
                    "SatelliteSetting",
                    "SatelliteSettings: KEY_SATELLITE_ATTACH_SUPPORTED_BOOL is false, do nothing.");
            finish();
        }
    }

    @Override // com.android.settings.dashboard.DashboardFragment,
              // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        boolean z;
        Drawable drawable;
        super.onViewCreated(view, bundle);
        String simOperatorName = this.mTelephonymanager.getSimOperatorName(this.mSubId);
        try {
            z =
                    !this.mSatelliteManager
                            .getAttachRestrictionReasonsForCarrier(this.mSubId)
                            .contains(2);
        } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
            Log.e("SatelliteSetting", e.toString());
            z = false;
        }
        findPreference("key_about_satellite_messaging")
                .setTitle(
                        getResources()
                                .getString(
                                        R.string.title_about_satellite_setting, simOperatorName));
        ((PreferenceCategory) findPreference("key_category_your_satellite_plan"))
                .setTitle(
                        getResources()
                                .getString(
                                        R.string.category_title_your_satellite_plan,
                                        simOperatorName));
        Preference findPreference = findPreference("key_your_satellite_plan");
        if (z) {
            findPreference.setTitle(R.string.title_have_satellite_plan);
            drawable = getResources().getDrawable(R.drawable.ic_check_circle_24px);
        } else {
            findPreference.setTitle(R.string.title_no_satellite_plan);
            SpannableString spannableString =
                    new SpannableString(
                            getResources().getString(R.string.summary_add_satellite_setting));
            spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 18);
            spannableString.setSpan(new StyleSpan(1), 0, spannableString.length(), 18);
            findPreference.setSummary(spannableString);
            findPreference.setOnPreferenceClickListener(
                    new Preference
                            .OnPreferenceClickListener() { // from class:
                                                           // com.android.settings.network.telephony.SatelliteSetting$$ExternalSyntheticLambda0
                        @Override // androidx.preference.Preference.OnPreferenceClickListener
                        public final boolean onPreferenceClick(Preference preference) {
                            SatelliteSetting satelliteSetting = SatelliteSetting.this;
                            String readSatelliteMoreInfoString =
                                    satelliteSetting.readSatelliteMoreInfoString(
                                            satelliteSetting.mSubId);
                            if (readSatelliteMoreInfoString.isEmpty()) {
                                return true;
                            }
                            satelliteSetting.startActivity(
                                    new Intent(
                                            "android.intent.action.VIEW",
                                            Uri.parse(readSatelliteMoreInfoString)));
                            return true;
                        }
                    });
            drawable = getResources().getDrawable(R.drawable.ic_block_24px);
        }
        drawable.setTintList(Utils.getColorAttr(getContext(), android.R.attr.textColorPrimary));
        findPreference.setIcon(drawable);
        if (!z) {
            PreferenceCategory preferenceCategory =
                    (PreferenceCategory) findPreference("key_category_how_it_works");
            preferenceCategory.setEnabled(false);
            preferenceCategory.setShouldDisableView(true);
        }
        FooterPreference footerPreference =
                (FooterPreference) findPreference("satellite_setting_extra_info_footer_pref");
        if (footerPreference != null) {
            footerPreference.setTitle(
                    getResources()
                            .getString(
                                    R.string.satellite_setting_summary_more_information,
                                    simOperatorName));
            final String[] strArr = {readSatelliteMoreInfoString(this.mSubId)};
            footerPreference.setLearnMoreAction(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.network.telephony.SatelliteSetting$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view2) {
                            Intent helpIntent;
                            SatelliteSetting satelliteSetting = SatelliteSetting.this;
                            String[] strArr2 = strArr;
                            satelliteSetting.getClass();
                            if (strArr2[0].isEmpty()
                                    || (helpIntent =
                                                    HelpUtils.getHelpIntent(
                                                            satelliteSetting.mActivity,
                                                            strArr2[0],
                                                            satelliteSetting.getClass().getName()))
                                            == null) {
                                return;
                            }
                            satelliteSetting.mActivity.startActivityForResult(helpIntent, 0);
                        }
                    });
            footerPreference.setLearnMoreText(
                    getResources().getString(R.string.more_about_satellite_messaging));
        }
    }

    public final String readSatelliteMoreInfoString(int i) {
        if (this.mConfigBundle == null) {
            PersistableBundle configForSubId =
                    this.mCarrierConfigManager.getConfigForSubId(
                            i, "satellite_information_redirect_url_string");
            this.mConfigBundle = configForSubId;
            if (configForSubId.isEmpty()) {
                Log.d("SatelliteSetting", "SatelliteSettings: getDefaultConfig");
                this.mConfigBundle = CarrierConfigManager.getDefaultConfig();
            }
        }
        return this.mConfigBundle.getString(
                "satellite_information_redirect_url_string", ApnSettings.MVNO_NONE);
    }
}
