package com.android.settings.notification;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserManager;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.RestrictedRadioButton;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.SetupRedactionInterstitial;
import com.android.settings.SetupWizardUtils;
import com.android.settings.Utils;
import com.android.settingslib.RestrictedLockUtilsInternal;

import com.google.android.setupcompat.template.FooterBarMixin;
import com.google.android.setupcompat.template.FooterButton;
import com.google.android.setupcompat.util.WizardManagerHelper;
import com.google.android.setupdesign.GlifLayout;
import com.google.android.setupdesign.util.ThemeHelper;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.util.function.Supplier;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class RedactionInterstitial extends SettingsActivity {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class RedactionInterstitialFragment extends SettingsPreferenceFragment
            implements RadioGroup.OnCheckedChangeListener {
        public RadioGroup mRadioGroup;
        public RestrictedRadioButton mRedactSensitiveButton;
        public RestrictedRadioButton mShowAllButton;
        public int mUserId;

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 74;
        }

        @Override // android.widget.RadioGroup.OnCheckedChangeListener
        public final void onCheckedChanged(RadioGroup radioGroup, int i) {
            int i2 = i == R.id.show_all ? 1 : 0;
            int i3 = i != R.id.hide_all ? 1 : 0;
            Settings.Secure.putIntForUser(
                    getContentResolver(),
                    "lock_screen_allow_private_notifications",
                    i2,
                    this.mUserId);
            Settings.Secure.putIntForUser(
                    getContentResolver(), "lock_screen_show_notifications", i3, this.mUserId);
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final View onCreateView(
                LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            return layoutInflater.inflate(R.layout.redaction_interstitial, viewGroup, false);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onResume() {
            int i;
            super.onResume();
            this.mShowAllButton.setDisabledByAdmin(
                    RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                            getActivity(), 12, this.mUserId));
            this.mRedactSensitiveButton.setDisabledByAdmin(
                    RestrictedLockUtilsInternal.checkIfKeyguardFeaturesDisabled(
                            getActivity(), 4, this.mUserId));
            boolean z =
                    getContext()
                            .getResources()
                            .getBoolean(R.bool.default_allow_sensitive_lockscreen_content);
            byte b =
                    UserManager.get(getContext()).isManagedProfile(this.mUserId)
                            || Settings.Secure.getIntForUser(
                                            getContentResolver(),
                                            "lock_screen_show_notifications",
                                            0,
                                            this.mUserId)
                                    != 0;
            boolean z2 =
                    Settings.Secure.getIntForUser(
                                    getContentResolver(),
                                    "lock_screen_allow_private_notifications",
                                    z ? 1 : 0,
                                    this.mUserId)
                            != 0;
            if (b != false) {
                if (z2 && !this.mShowAllButton.mDisabledByAdmin) {
                    i = R.id.show_all;
                } else if (!this.mRedactSensitiveButton.mDisabledByAdmin) {
                    i = R.id.redact_sensitive;
                }
                this.mRadioGroup.check(i);
            }
            i = R.id.hide_all;
            this.mRadioGroup.check(i);
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onViewCreated(View view, Bundle bundle) {
            super.onViewCreated(view, bundle);
            DevicePolicyManager devicePolicyManager =
                    (DevicePolicyManager) getSystemService(DevicePolicyManager.class);
            this.mRadioGroup = (RadioGroup) view.findViewById(R.id.radio_group);
            this.mShowAllButton = (RestrictedRadioButton) view.findViewById(R.id.show_all);
            this.mRedactSensitiveButton =
                    (RestrictedRadioButton) view.findViewById(R.id.redact_sensitive);
            this.mRadioGroup.setOnCheckedChangeListener(this);
            this.mUserId =
                    Utils.getUserIdFromBundle(
                            getContext(), getActivity().getIntent().getExtras(), false);
            if (UserManager.get(getContext()).isManagedProfile(this.mUserId)) {
                ((TextView) view.findViewById(R.id.sud_layout_description))
                        .setText(R.string.lock_screen_notifications_interstitial_message_profile);
                final int i = 0;
                this.mShowAllButton.setText(
                        devicePolicyManager
                                .getResources()
                                .getString(
                                        "Settings.LOCK_SCREEN_SHOW_WORK_NOTIFICATION_CONTENT",
                                        new Supplier(
                                                this) { // from class:
                                                        // com.android.settings.notification.RedactionInterstitial$RedactionInterstitialFragment$$ExternalSyntheticLambda0
                                            public final /* synthetic */ RedactionInterstitial
                                                            .RedactionInterstitialFragment
                                                    f$0;

                                            {
                                                this.f$0 = this;
                                            }

                                            @Override // java.util.function.Supplier
                                            public final Object get() {
                                                int i2 = i;
                                                RedactionInterstitial.RedactionInterstitialFragment
                                                        redactionInterstitialFragment = this.f$0;
                                                switch (i2) {
                                                    case 0:
                                                        return redactionInterstitialFragment
                                                                .getString(
                                                                        R.string
                                                                                .lock_screen_notifications_summary_show_profile);
                                                    default:
                                                        return redactionInterstitialFragment
                                                                .getString(
                                                                        R.string
                                                                                .lock_screen_notifications_summary_hide_profile);
                                                }
                                            }
                                        }));
                final int i2 = 1;
                this.mRedactSensitiveButton.setText(
                        devicePolicyManager
                                .getResources()
                                .getString(
                                        "Settings.LOCK_SCREEN_HIDE_WORK_NOTIFICATION_CONTENT",
                                        new Supplier(
                                                this) { // from class:
                                                        // com.android.settings.notification.RedactionInterstitial$RedactionInterstitialFragment$$ExternalSyntheticLambda0
                                            public final /* synthetic */ RedactionInterstitial
                                                            .RedactionInterstitialFragment
                                                    f$0;

                                            {
                                                this.f$0 = this;
                                            }

                                            @Override // java.util.function.Supplier
                                            public final Object get() {
                                                int i22 = i2;
                                                RedactionInterstitial.RedactionInterstitialFragment
                                                        redactionInterstitialFragment = this.f$0;
                                                switch (i22) {
                                                    case 0:
                                                        return redactionInterstitialFragment
                                                                .getString(
                                                                        R.string
                                                                                .lock_screen_notifications_summary_show_profile);
                                                    default:
                                                        return redactionInterstitialFragment
                                                                .getString(
                                                                        R.string
                                                                                .lock_screen_notifications_summary_hide_profile);
                                                }
                                            }
                                        }));
                ((RadioButton) view.findViewById(R.id.hide_all)).setVisibility(8);
            }
            ((FooterBarMixin)
                            ((GlifLayout) view.findViewById(R.id.setup_wizard_layout))
                                    .getMixin(FooterBarMixin.class))
                    .setPrimaryButton(
                            new FooterButton(
                                    getContext().getString(R.string.app_notifications_dialog_done),
                                    new View
                                            .OnClickListener() { // from class:
                                                                 // com.android.settings.notification.RedactionInterstitial$RedactionInterstitialFragment$$ExternalSyntheticLambda2
                                        @Override // android.view.View.OnClickListener
                                        public final void onClick(View view2) {
                                            RedactionInterstitial.RedactionInterstitialFragment
                                                    redactionInterstitialFragment =
                                                            RedactionInterstitial
                                                                    .RedactionInterstitialFragment
                                                                    .this;
                                            if (!WizardManagerHelper.isAnySetupWizard(
                                                    redactionInterstitialFragment.getIntent())) {
                                                SetupRedactionInterstitial.setEnabled(
                                                        redactionInterstitialFragment.getContext(),
                                                        false);
                                            }
                                            RedactionInterstitial redactionInterstitial =
                                                    (RedactionInterstitial)
                                                            redactionInterstitialFragment
                                                                    .getActivity();
                                            if (redactionInterstitial != null) {
                                                redactionInterstitial.setResult(0, null);
                                                redactionInterstitialFragment.finish();
                                            }
                                        }
                                    },
                                    5,
                                    2132083805));
        }
    }

    public static Intent createStartIntent(Context context, int i) {
        if (SemPersonaManager.isKnoxId(i)) {
            return null;
        }
        return new Intent()
                .putExtra(
                        ":settings:show_fragment_title_resid",
                        UserManager.get(context).isManagedProfile(i)
                                ? R.string.lock_screen_notifications_interstitial_title_profile
                                : R.string.lockscreen_notifications)
                .putExtra("android.intent.extra.USER_ID", i)
                .setClassName(
                        KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                        "com.android.settings.Settings$LockscreenNotificationActivity")
                .putExtra("needRedaction", true);
    }

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(":settings:show_fragment", RedactionInterstitialFragment.class.getName());
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public boolean isValidFragment(String str) {
        return RedactionInterstitialFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        setTheme(SetupWizardUtils.getTheme(this, getIntent()));
        ThemeHelper.trySetDynamicColor(this);
        super.onCreate(bundle);
        findViewById(R.id.content_parent).setFitsSystemWindows(false);
    }
}
