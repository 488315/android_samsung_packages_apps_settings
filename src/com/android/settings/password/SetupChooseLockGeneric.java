package com.android.settings.password;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.IBinder;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.SetupWizardUtils;
import com.android.settings.Utils;
import com.android.settings.utils.SettingsDividerItemDecoration;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.GlifPreferenceLayout;
import com.google.android.setupdesign.template.HeaderMixin;
import com.google.android.setupdesign.util.ThemeHelper;
import com.samsung.android.settings.biometrics.face.FaceLockSettings;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintLockSettings;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SetupChooseLockGeneric extends ChooseLockGeneric {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class InternalActivity extends ChooseLockGeneric.InternalActivity {

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        public static class InternalSetupChooseLockGenericFragment
                extends ChooseLockGeneric.ChooseLockGenericFragment {
            @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment
            public boolean canRunBeforeDeviceProvisioned() {
                return true;
            }

            @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment
            public final Intent getBiometricEnrollIntent(FragmentActivity fragmentActivity) {
                Intent biometricEnrollIntent = super.getBiometricEnrollIntent(fragmentActivity);
                WizardManagerHelper.copyWizardManagerExtras(getIntent(), biometricEnrollIntent);
                return biometricEnrollIntent;
            }

            @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment
            public final Intent getLockPasswordIntent(int i) {
                Context context = getContext();
                Intent lockPasswordIntent = super.getLockPasswordIntent(i);
                int i2 = SetupChooseLockPassword.$r8$clinit;
                lockPasswordIntent.setClass(context, SetupChooseLockPassword.class);
                lockPasswordIntent.putExtra("extra_prefs_show_button_bar", false);
                WizardManagerHelper.copyWizardManagerExtras(getIntent(), lockPasswordIntent);
                return lockPasswordIntent;
            }

            @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment
            public final Intent getLockPatternIntent() {
                Context context = getContext();
                Intent lockPatternIntent = super.getLockPatternIntent();
                int i = SetupChooseLockPattern.$r8$clinit;
                lockPatternIntent.setClass(context, SetupChooseLockPattern.class);
                WizardManagerHelper.copyWizardManagerExtras(getIntent(), lockPatternIntent);
                return lockPatternIntent;
            }

            @Override // androidx.preference.PreferenceFragmentCompat
            public final RecyclerView onCreateRecyclerView(
                    LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
                return ((GlifPreferenceLayout) viewGroup).recyclerMixin.recyclerView;
            }

            @Override // com.android.settings.SettingsPreferenceFragment,
                      // androidx.preference.PreferenceFragmentCompat,
                      // androidx.fragment.app.Fragment
            public final void onViewCreated(View view, Bundle bundle) {
                super.onViewCreated(view, bundle);
                ((GlifPreferenceLayout) view)
                        .setHeaderText(R.string.lock_settings_picker_new_lock_title);
                setDivider(new ColorDrawable(0));
                setDividerHeight(0);
            }
        }

        @Override // com.android.settings.password.ChooseLockGeneric
        public Class getFragmentClass() {
            return InternalSetupChooseLockGenericFragment.class;
        }

        @Override // com.android.settings.password.ChooseLockGeneric,
                  // com.android.settings.SettingsActivity
        public boolean isValidFragment(String str) {
            return InternalSetupChooseLockGenericFragment.class.getName().equals(str);
        }

        @Override // com.android.settings.password.ChooseLockGeneric,
                  // com.android.settings.SettingsActivity,
                  // com.android.settings.core.SettingsBaseActivity,
                  // com.samsung.android.settings.core.SecSettingsBaseActivity,
                  // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
                  // androidx.core.app.ComponentActivity, android.app.Activity
        public void onCreate(Bundle bundle) {
            setTheme(SetupWizardUtils.getTheme(this, getIntent()));
            ThemeHelper.trySetDynamicColor(this);
            super.onCreate(bundle);
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class SetupChooseLockGenericFragment
            extends ChooseLockGeneric.ChooseLockGenericFragment {
        public static final /* synthetic */ int $r8$clinit = 0;

        @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment
        public final void addPreferences$2() {
            if (isForBiometric()) {
                super.addPreferences$2();
            } else {
                addPreferencesFromResource(R.xml.sec_setup_security_settings_picker);
            }
        }

        @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment
        public final boolean alwaysHideInsecureScreenLockTypes() {
            return true;
        }

        @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment
        public final boolean canRunBeforeDeviceProvisioned() {
            if (!LockUtils.readGoogleFRPFlag(getActivity())) {
                return true;
            }
            SemLog.i(
                    "SetupChooseLockGeneric",
                    "Refusing to start because device is not provisioned");
            return false;
        }

        @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment
        public final Intent getBiometricEnrollIntent(FragmentActivity fragmentActivity) {
            Intent biometricEnrollIntent = super.getBiometricEnrollIntent(fragmentActivity);
            WizardManagerHelper.copyWizardManagerExtras(
                    getActivity().getIntent(), biometricEnrollIntent);
            return biometricEnrollIntent;
        }

        @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment
        public Class getInternalActivityClass() {
            return InternalActivity.class;
        }

        @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment
        public final Intent getLockPasswordIntent(int i) {
            Context context = getContext();
            Intent lockPasswordIntent = super.getLockPasswordIntent(i);
            int i2 = SetupChooseLockPassword.$r8$clinit;
            lockPasswordIntent.setClass(context, SetupChooseLockPassword.class);
            lockPasswordIntent.putExtra("extra_prefs_show_button_bar", false);
            WizardManagerHelper.copyWizardManagerExtras(
                    getActivity().getIntent(), lockPasswordIntent);
            return lockPasswordIntent;
        }

        @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment
        public final Intent getLockPatternIntent() {
            Context context = getContext();
            Intent lockPatternIntent = super.getLockPatternIntent();
            int i = SetupChooseLockPattern.$r8$clinit;
            lockPatternIntent.setClass(context, SetupChooseLockPattern.class);
            WizardManagerHelper.copyWizardManagerExtras(
                    getActivity().getIntent(), lockPatternIntent);
            return lockPatternIntent;
        }

        public final boolean isForBiometric() {
            return this.mForFingerprint || this.mForFace || this.mForBiometrics;
        }

        @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment,
                  // androidx.fragment.app.Fragment
        public void onActivityResult(int i, int i2, Intent intent) {
            if (intent == null) {
                intent = new Intent();
            }
            LockPatternUtils lockPatternUtils = new LockPatternUtils(getActivity());
            if (i != 10037) {
                if (i2 == 0) {
                    return;
                }
                intent.putExtra(
                        ":settings:password_quality",
                        lockPatternUtils.getKeyguardStoredPasswordQuality(UserHandle.myUserId()));
                super.onActivityResult(i, i2, intent);
                return;
            }
            if (i2 == 11) {
                setResult(i2);
                finish();
            } else {
                if (intent.getIntExtra(":settings:password_quality", 0) == 0
                        && lockPatternUtils.getKeyguardStoredPasswordQuality(UserHandle.myUserId())
                                == 0) {
                    return;
                }
                SemLog.d(
                        "SetupChooseLockGeneric",
                        "onActivityResult requestCode = " + i + ", resultCode = -1");
                setResult(-1);
                finish();
            }
        }

        @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment,
                  // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public void onCreate(Bundle bundle) {
            setAutoAddFooterPreference(false);
            super.onCreate(bundle);
        }

        @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment,
                  // com.android.settings.core.InstrumentedPreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat,
                  // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
        public final boolean onPreferenceTreeClick(Preference preference) {
            String key = preference.getKey();
            if ("unlock_set_face".equals(key)) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), FaceLockSettings.class);
                intent.putExtra("previousStage", "setupwizard_face");
                intent.putExtra(
                        "hasMultipleUsers",
                        ((UserManager) getSystemService("user")).getUsers().size() > 1);
                intent.putExtra("useImmersiveMode", true);
                startActivityForResult(intent, 10037);
            } else if ("unlock_set_fingerprint".equals(key)) {
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), FingerprintLockSettings.class);
                intent2.putExtra("previousStage", "google_setupwizard_fingerprint");
                intent2.putExtra("fromSetupWizard", true);
                intent2.putExtra(
                        "hasMultipleUsers",
                        ((UserManager) getSystemService("user")).getUsers().size() > 1);
                intent2.putExtra("useImmersiveMode", true);
                startActivityForResult(intent2, 10037);
            }
            return super.onPreferenceTreeClick(preference);
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public void onViewCreated(View view, Bundle bundle) {
            super.onViewCreated(view, bundle);
            GlifLayout glifLayout = (GlifLayout) view;
            glifLayout.setDescriptionText(
                    getString(
                            isForBiometric()
                                    ? R.string
                                            .lock_settings_picker_biometrics_added_security_message
                                    : R.string.sec_setup_lock_settings_picker_message));
            glifLayout.setIcon(getContext().getDrawable(R.drawable.sec_lock_suw_ic));
            int i =
                    isForBiometric()
                            ? R.string.lock_settings_picker_title
                            : R.string.sec_setup_lock_settings_picker_title;
            if (getActivity() != null) {
                getActivity().setTitle(i);
            }
            glifLayout.setHeaderText(i);
            setDivider(null);
            int color = getContext().getColor(R.color.sec_lock_suw_background_color);
            getActivity().getWindow().setStatusBarColor(color);
            getActivity().getWindow().setNavigationBarColor(color);
            glifLayout.setBackgroundColor(color);
            ScrollView scrollView = (ScrollView) glifLayout.findViewById(R.id.sud_scroll_view);
            if (scrollView != null) {
                scrollView.setScrollIndicators(0);
            }
            View findManagedViewById = glifLayout.findManagedViewById(R.id.sud_layout_header);
            if (Utils.isTablet()) {
                findManagedViewById.setPadding(
                        findManagedViewById.getPaddingLeft(),
                        findManagedViewById.getPaddingTop(),
                        findManagedViewById.getPaddingRight(),
                        getResources()
                                .getDimensionPixelSize(
                                        R.dimen
                                                .sec_lock_setting_suw_header_bottom_margin_for_tablet));
            }
            FooterBarMixin footerBarMixin =
                    (FooterBarMixin) glifLayout.getMixin(FooterBarMixin.class);
            HeaderMixin headerMixin = (HeaderMixin) glifLayout.getMixin(HeaderMixin.class);
            if (headerMixin != null) {
                ColorStateList valueOf =
                        ColorStateList.valueOf(
                                getResources().getColor(R.color.sec_widget_body_text_color));
                TextView textView = headerMixin.getTextView();
                if (textView != null) {
                    textView.setTextColor(valueOf);
                }
            }
            getActivity();
            footerBarMixin.setSecondaryButton(
                    new FooterButton(
                            getString(R.string.intelligent_biometrics_lock_settings_button_skip),
                            new View
                                    .OnClickListener() { // from class:
                                                         // com.android.settings.password.SetupChooseLockGeneric$SetupChooseLockGenericFragment$$ExternalSyntheticLambda0
                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    SetupChooseLockGeneric.SetupChooseLockGenericFragment
                                            setupChooseLockGenericFragment =
                                                    SetupChooseLockGeneric
                                                            .SetupChooseLockGenericFragment.this;
                                    int i2 =
                                            SetupChooseLockGeneric.SetupChooseLockGenericFragment
                                                    .$r8$clinit;
                                    Intent intent =
                                            setupChooseLockGenericFragment
                                                    .getActivity()
                                                    .getIntent();
                                    SetupSkipDialog.newInstance(
                                                    intent.getBooleanExtra(
                                                            ":settings:frp_supported", false),
                                                    intent.getBooleanExtra("isSetupFlow", false))
                                            .show(
                                                    setupChooseLockGenericFragment
                                                            .getFragmentManager());
                                }
                            },
                            7,
                            2132083806),
                    false);
            footerBarMixin.getButtonContainer().setBackgroundColor(color);
            seslSetRoundedCorner(false);
            RecyclerView listView = getListView();
            if (listView != null) {
                listView.mDrawLastRoundedCorner = false;
                listView.setPadding(0, 0, 0, 0);
                listView.setBackgroundColor(
                        getResources().getColor(R.color.sec_lock_suw_listview_background_color));
                listView.semSetRoundedCorners(15);
                listView.semSetRoundedCornerColor(15, color);
                listView.addItemDecoration(new SettingsDividerItemDecoration(getContext()));
            }
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat
        public final void setPreferenceScreen(PreferenceScreen preferenceScreen) {
            super.setPreferenceScreen(preferenceScreen);
            if (preferenceScreen != null) {
                for (int i = 0; i < preferenceScreen.getPreferenceCount(); i++) {
                    Preference preference = preferenceScreen.getPreference(i);
                    preference.setLayoutResource(R.layout.sec_setup_choose_lock_preference_layout);
                    CharSequence title = preference.getTitle();
                    if (title != null) {
                        SpannableString spannableString = new SpannableString(title.toString());
                        spannableString.setSpan(
                                new ForegroundColorSpan(
                                        getResources()
                                                .getColor(
                                                        R.color.sec_widget_alert_dialog_text_color,
                                                        null)),
                                0,
                                spannableString.length(),
                                0);
                        preference.setTitle(spannableString);
                    }
                    preference.setIcon((Drawable) null);
                    preference.setIconSpaceReserved(false);
                    preference.seslSetSubheaderColor(
                            getResources()
                                    .getColor(R.color.sec_widget_alert_dialog_text_color, null));
                }
            }
        }
    }

    @Override // com.android.settings.password.ChooseLockGeneric
    public Class getFragmentClass() {
        return SetupChooseLockGenericFragment.class;
    }

    @Override // com.android.settings.password.ChooseLockGeneric,
              // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra("fromSetupWizard", true);
        intent.putExtra("isSetupFlow", true);
        return intent;
    }

    @Override // com.android.settings.password.ChooseLockGeneric,
              // com.android.settings.SettingsActivity
    public boolean isValidFragment(String str) {
        return SetupChooseLockGenericFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.password.ChooseLockGeneric,
              // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        setTheme(R.style.LockscreenSUWTheme);
        ThemeHelper.trySetDynamicColor(this);
        super.onCreate(bundle);
        if (getIntent().hasExtra("requested_min_complexity")) {
            IBinder activityToken = getActivityToken();
            if (!PasswordUtils.isCallingAppPermitted(this, activityToken)) {
                PasswordUtils.crashCallingApplication(activityToken);
                finish();
                return;
            }
        }
        if (!Utils.isDeviceProvisioned(this) && LockUtils.readGoogleFRPFlag(this)) {
            SemLog.i(
                    "SetupChooseLockGeneric",
                    "Refusing to start because device is not provisioned");
            finish();
            return;
        }
        if (LockUtils.isApplyingTabletGUI(this)) {
            getWindow().setSoftInputMode(48);
        }
        if (LockUtils.isLockSettingsBlockonDexMode(this)) {
            Toast.makeText(
                            this,
                            getString(R.string.sec_lock_screen_disable_by_samsung_dex_waring_text),
                            0)
                    .show();
            setResult(1);
            finish();
        } else {
            hideAppBar();
            View findViewById = findViewById(R.id.round_corner);
            if (findViewById != null) {
                findViewById.setVisibility(8);
            }
            ((LinearLayout) findViewById(R.id.content_parent)).setFitsSystemWindows(true);
        }
    }
}
