package com.samsung.android.settings.password;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.recyclerview.widget.RecyclerView;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.password.ChooseLockGeneric;
import com.android.settings.password.SetupChooseLockPassword;
import com.android.settings.password.SetupChooseLockPattern;
import com.android.settings.password.SetupSkipDialog;
import com.android.settings.utils.SettingsDividerItemDecoration;

import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.biometrics.face.FaceLockSettings;
import com.samsung.android.settings.biometrics.fingerprint.FingerprintLockSettings;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.sec.android.secsetupwizardlib.SuwBaseActivity;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class SetupChooseLockGenericForNoneGoogle extends SuwBaseActivity {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class SetupChooseLockGenericForNoneGoogleFragment
            extends ChooseLockGeneric.ChooseLockGenericFragment {
        public static SetupChooseLockGenericForNoneGoogleFragment
                mSetupChooseLockGenericForNoneGoogleFragment;

        public SetupChooseLockGenericForNoneGoogleFragment() {
            mSetupChooseLockGenericForNoneGoogleFragment = this;
        }

        @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment
        public final void addPreferences$2() {
            if (this.mForFingerprint) {
                super.addPreferences$2();
            } else {
                addPreferencesFromResource(R.xml.sec_setup_security_settings_picker);
            }
        }

        @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment
        public final boolean canRunBeforeDeviceProvisioned() {
            if (!LockUtils.readGoogleFRPFlag(getActivity())) {
                return true;
            }
            Log.i(
                    "SetupChooseLockGenericForNoneGoogle",
                    "Refusing to start because device is not provisioned");
            return false;
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

        @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment,
                  // androidx.fragment.app.Fragment
        public final void onActivityResult(int i, int i2, Intent intent) {
            int keyguardStoredPasswordQuality =
                    new LockPatternUtils(getActivity())
                            .getKeyguardStoredPasswordQuality(UserHandle.myUserId());
            if (i != 10037) {
                if (i2 == 0) {
                    return;
                }
                if (intent == null) {
                    intent = new Intent();
                }
                intent.putExtra(":settings:password_quality", keyguardStoredPasswordQuality);
                super.onActivityResult(i, i2, intent);
                return;
            }
            if (i2 == 11) {
                setResult(i2);
                finish();
                return;
            }
            if ((intent != null && intent.getIntExtra(":settings:password_quality", 0) != 0)
                    || keyguardStoredPasswordQuality != 0) {
                i2 = -1;
            }
            if (i2 == -1) {
                setResult(i2);
                finish();
            }
        }

        @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment,
                  // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            setAutoAddFooterPreference(false);
            super.onCreate(bundle);
        }

        @Override // com.android.settings.password.ChooseLockGeneric.ChooseLockGenericFragment,
                  // com.android.settings.core.InstrumentedPreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat,
                  // androidx.preference.PreferenceManager.OnPreferenceTreeClickListener
        public final boolean onPreferenceTreeClick(Preference preference) {
            String key = preference.getKey();
            if ("unlock_set_do_later".equals(key)) {
                Intent intent = getActivity().getIntent();
                SetupSkipDialog.newInstance(
                                intent.getBooleanExtra(":settings:frp_supported", false),
                                intent.getBooleanExtra("isSetupFlow", false))
                        .show(getFragmentManager());
                return true;
            }
            if ("unlock_set_face".equals(key)) {
                Intent intent2 = new Intent();
                intent2.setClass(getActivity(), FaceLockSettings.class);
                intent2.putExtra("previousStage", "setupwizard_face");
                intent2.putExtra(
                        "hasMultipleUsers",
                        ((UserManager) getSystemService("user")).getUsers().size() > 1);
                intent2.putExtra("useImmersiveMode", true);
                startActivityForResult(intent2, 10037);
            } else if ("unlock_set_fingerprint".equals(key)) {
                Intent intent3 = new Intent();
                intent3.setClass(getActivity(), FingerprintLockSettings.class);
                intent3.putExtra("previousStage", "google_setupwizard_fingerprint");
                intent3.putExtra("fromSetupWizard", true);
                intent3.putExtra(
                        "hasMultipleUsers",
                        ((UserManager) getSystemService("user")).getUsers().size() > 1);
                intent3.putExtra("useImmersiveMode", true);
                startActivityForResult(intent3, 10037);
            }
            return super.onPreferenceTreeClick(preference);
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onViewCreated(View view, Bundle bundle) {
            super.onViewCreated(view, bundle);
            int i =
                    this.mForFingerprint
                            ? R.string.lock_settings_picker_title
                            : R.string.sec_setup_lock_settings_picker_title;
            if (getActivity() != null) {
                getActivity().setTitle(i);
            }
            seslSetRoundedCorner(false);
            RecyclerView listView = getListView();
            if (listView != null) {
                listView.seslSetFillBottomEnabled(false);
                listView.setPadding(0, 0, 0, 0);
                listView.setBackgroundColor(
                        getContext().getColor(R.color.sec_lock_suw_listview_background_color));
                listView.semSetRoundedCorners(15);
                listView.semSetRoundedCornerColor(
                        15, getContext().getColor(R.color.sec_lock_suw_background_color));
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

    @Override // android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra("firstRun", true);
        intent.putExtra("fromSetupWizard", true);
        return intent;
    }

    @Override // com.sec.android.secsetupwizardlib.SuwBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (LockUtils.isLockSettingsBlockonDexMode(this)) {
            Toast.makeText(
                            this,
                            getString(R.string.sec_lock_screen_disable_by_samsung_dex_waring_text),
                            0)
                    .show();
            setResult(1);
            finish();
            return;
        }
        setContentView(R.layout.sec_protect_your_phone_setupwizard);
        setHeaderTitle(R.string.sec_setup_lock_settings_picker_title);
        setHeaderIcon(getDrawable(R.drawable.sec_lock_suw_ic));
        setDescriptionText(R.string.sec_setup_lock_settings_picker_message);
        if (Rune.LOCKSCREEN_SECURITY_HIDE_SKIP_SUW_BUTTON) {
            return;
        }
        FooterButton footerButton = this.mSecondaryButton;
        if (footerButton != null) {
            footerButton.setEnabled(true);
        }
        setSecondaryActionButton(
                R.string.intelligent_biometrics_lock_settings_button_skip,
                new SetupChooseLockGenericForNoneGoogle$$ExternalSyntheticLambda0());
    }
}
