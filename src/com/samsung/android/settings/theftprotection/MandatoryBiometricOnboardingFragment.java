package com.samsung.android.settings.theftprotection;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.core.SubSettingLauncher;

import com.samsung.android.settings.theftprotection.location.LocationStorage;
import com.samsung.android.settings.theftprotection.logging.Log;
import com.samsung.android.settings.theftprotection.utils.NecessaryElementChecker;

import java.util.ArrayList;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class MandatoryBiometricOnboardingFragment extends SettingsPreferenceFragment
        implements View.OnClickListener {
    public Context mContext;
    public Button mContinueButton;
    public View mFingerprintView;
    public View mSamsungAccountView;
    public View mTrustedPlacesView;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    public final void initOnboardingItemView(View view, int i, int i2, int i3) {
        ((TextView) view.findViewById(R.id.onboarding_title)).setText(i);
        ((TextView) view.findViewById(R.id.onboarding_description)).setText(i2);
        view.findViewById(R.id.onboarding_icon).setBackground(this.mContext.getDrawable(i3));
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        if (!TheftProtectionUtils.isSamsungAccountSignedIn(this.mContext)) {
            Log.d(
                    "MandatoryBiometricOnboardingFragment",
                    "onContinueClicked: Launching Samsung account.");
            Intent intent = new Intent();
            intent.setClassName(
                    getString(R.string.config_sec_toplevel_samsung_account_package),
                    getString(R.string.config_sec_toplevel_samsung_account_class));
            intent.putExtra("from_settings", "true");
            try {
                startActivity(intent);
                return;
            } catch (ActivityNotFoundException e) {
                Log.d(
                        "MandatoryBiometricOnboardingFragment",
                        "Samsung Account Activity Not Found" + e.getMessage());
                return;
            }
        }
        if (!TheftProtectionUtils.hasBiometricStrong(this.mContext)) {
            Log.d(
                    "MandatoryBiometricOnboardingFragment",
                    "onContinueClicked: Launching Fingerprint settings.");
            TheftProtectionUtils.startFingerprintLockSettings(this.mContext);
            return;
        }
        if (!((ArrayList) LocationStorage.InstanceHolder.INSTANCE.loadLocationData()).isEmpty()) {
            Log.d("MandatoryBiometricOnboardingFragment", "onContinueClicked: Setup complete.");
            NecessaryElementChecker necessaryElementChecker =
                    new NecessaryElementChecker(this.mContext);
            final int i = 0;
            Runnable runnable =
                    new Runnable(
                            this) { // from class:
                                    // com.samsung.android.settings.theftprotection.MandatoryBiometricOnboardingFragment$$ExternalSyntheticLambda0
                        public final /* synthetic */ MandatoryBiometricOnboardingFragment f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            int i2 = i;
                            MandatoryBiometricOnboardingFragment
                                    mandatoryBiometricOnboardingFragment = this.f$0;
                            switch (i2) {
                                case 0:
                                    Context context = mandatoryBiometricOnboardingFragment.mContext;
                                    if (context != null) {
                                        TheftProtectionUtils.setMandatoryBiometricStatus(
                                                context, 1);
                                        mandatoryBiometricOnboardingFragment.finish();
                                        break;
                                    }
                                    break;
                                default:
                                    Context context2 =
                                            mandatoryBiometricOnboardingFragment.mContext;
                                    if (context2 != null) {
                                        Toast.makeText(
                                                        context2,
                                                        R.string
                                                                .mandatory_biometric_activate_fail_toast_message,
                                                        1)
                                                .show();
                                        mandatoryBiometricOnboardingFragment.finish();
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
            final int i2 = 1;
            Runnable runnable2 =
                    new Runnable(
                            this) { // from class:
                                    // com.samsung.android.settings.theftprotection.MandatoryBiometricOnboardingFragment$$ExternalSyntheticLambda0
                        public final /* synthetic */ MandatoryBiometricOnboardingFragment f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            int i22 = i2;
                            MandatoryBiometricOnboardingFragment
                                    mandatoryBiometricOnboardingFragment = this.f$0;
                            switch (i22) {
                                case 0:
                                    Context context = mandatoryBiometricOnboardingFragment.mContext;
                                    if (context != null) {
                                        TheftProtectionUtils.setMandatoryBiometricStatus(
                                                context, 1);
                                        mandatoryBiometricOnboardingFragment.finish();
                                        break;
                                    }
                                    break;
                                default:
                                    Context context2 =
                                            mandatoryBiometricOnboardingFragment.mContext;
                                    if (context2 != null) {
                                        Toast.makeText(
                                                        context2,
                                                        R.string
                                                                .mandatory_biometric_activate_fail_toast_message,
                                                        1)
                                                .show();
                                        mandatoryBiometricOnboardingFragment.finish();
                                        break;
                                    }
                                    break;
                            }
                        }
                    };
            necessaryElementChecker.mDoSuccess = runnable;
            necessaryElementChecker.mDoFail = runnable2;
            necessaryElementChecker.processNecessaryElements(
                    NecessaryElementChecker.Sequence.START_CHECKING);
            return;
        }
        Log.d(
                "MandatoryBiometricOnboardingFragment",
                "onContinueClicked: Launching Trusted places.");
        Bundle bundle = new Bundle();
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(this.mContext);
        String name = MandatoryBiometricLocationFragment.class.getName();
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mDestinationName = name;
        launchRequest.mSourceMetricsCategory = 0;
        launchRequest.mArguments = bundle;
        subSettingLauncher.setTitleRes(R.string.mandatory_biometric_trusted_places, null);
        subSettingLauncher.launch();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate =
                layoutInflater.inflate(
                        R.layout.mandatory_biometric_onboarding_layout, viewGroup, false);
        View findViewById = inflate.findViewById(R.id.onboarding_samsung_account);
        this.mSamsungAccountView = findViewById;
        initOnboardingItemView(
                findViewById,
                R.string.mandatory_biometric_onboarding_samsung_account_title,
                R.string.mandatory_biometric_onboarding_samsung_account_description,
                R.drawable.ic_security_accounts);
        View findViewById2 = inflate.findViewById(R.id.onboarding_fingerprint);
        this.mFingerprintView = findViewById2;
        initOnboardingItemView(
                findViewById2,
                R.string.mandatory_biometric_onboarding_fingerprint_title,
                R.string.mandatory_biometric_onboarding_fingerprint_description,
                R.drawable.ic_security_fingerprint);
        View findViewById3 = inflate.findViewById(R.id.onboarding_trusted_places);
        this.mTrustedPlacesView = findViewById3;
        initOnboardingItemView(
                findViewById3,
                R.string.mandatory_biometric_onboarding_trusted_places_title,
                R.string.mandatory_biometric_onboarding_trusted_places_description,
                R.drawable.ic_perm_group_location);
        TextView textView = (TextView) inflate.findViewById(R.id.description_text);
        Context context = this.mContext;
        if (textView != null && textView.getText() != null) {
            String charSequence = textView.getText().toString();
            String str =
                    ((Object) textView.getText()) + " " + context.getString(R.string.learn_more);
            int length = charSequence.length() + 1;
            int length2 = str.length();
            SpannableString spannableString = new SpannableString(str);
            spannableString.setSpan(
                    new TheftProtectionUtils.AnonymousClass1(context), length, length2, 33);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            textView.setText(spannableString);
        }
        return inflate;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        Context context = this.mContext;
        boolean z =
                TheftProtectionUtils.isSamsungAccountSignedIn(context)
                        && TheftProtectionUtils.hasBiometricStrong(context)
                        && (((ArrayList) LocationStorage.InstanceHolder.INSTANCE.loadLocationData())
                                        .isEmpty()
                                ^ true);
        View view = this.mSamsungAccountView;
        int i = R.drawable.list_status_orange;
        if (view != null) {
            view.findViewById(R.id.onboarding_status_icon)
                    .setBackground(
                            getResources()
                                    .getDrawable(
                                            TheftProtectionUtils.isSamsungAccountSignedIn(
                                                            this.mContext)
                                                    ? R.drawable.list_status_green
                                                    : R.drawable.list_status_orange));
        }
        View view2 = this.mFingerprintView;
        if (view2 != null) {
            view2.findViewById(R.id.onboarding_status_icon)
                    .setBackground(
                            getResources()
                                    .getDrawable(
                                            TheftProtectionUtils.hasBiometricStrong(this.mContext)
                                                    ? R.drawable.list_status_green
                                                    : R.drawable.list_status_orange));
        }
        View view3 = this.mTrustedPlacesView;
        if (view3 != null) {
            View findViewById = view3.findViewById(R.id.onboarding_status_icon);
            Resources resources = getResources();
            if (!((ArrayList) r2.loadLocationData()).isEmpty()) {
                i = R.drawable.list_status_green;
            }
            findViewById.setBackground(resources.getDrawable(i));
        }
        Button button = this.mContinueButton;
        if (button != null && z) {
            button.setText(R.string.sec_dlg_turn_on);
        }
        super.onResume();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ViewGroup viewGroup = (ViewGroup) getActivity().findViewById(R.id.button_bar);
        View inflate =
                LayoutInflater.from(getActivity())
                        .inflate(R.layout.sec_button_preference_layout, (ViewGroup) null);
        Button button = (Button) inflate.findViewById(R.id.button);
        this.mContinueButton = button;
        button.setText(R.string.common_continue);
        this.mContinueButton.setOnClickListener(this);
        if (viewGroup != null) {
            viewGroup.removeAllViews();
            LinearLayout.LayoutParams layoutParams =
                    (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
            layoutParams.gravity = 8388613;
            viewGroup.addView(inflate, layoutParams);
            viewGroup.setVisibility(0);
        }
    }
}
