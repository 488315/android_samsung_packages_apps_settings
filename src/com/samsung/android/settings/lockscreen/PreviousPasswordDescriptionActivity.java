package com.samsung.android.settings.lockscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.core.InstrumentedPreferenceFragment;

import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.samsung.android.settings.logging.SALogging;
import com.samsung.android.util.SemLog;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PreviousPasswordDescriptionActivity extends SettingsActivity {
    public static final /* synthetic */ int $r8$clinit = 0;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class PreviousPasswordDescriptionFragment extends InstrumentedPreferenceFragment {
        public LockscreenCredential mCurrentPW;
        public TextView mDescriptionTextView;
        public LockPatternUtils mLockPatternUtils;
        public TextView mSubDescriptionTextView;
        public int mUserId;

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 0;
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            this.mLockPatternUtils = new LockPatternUtils(getActivity());
            Intent intent = getActivity().getIntent();
            this.mUserId = Utils.getUserIdFromBundle(getActivity(), intent.getExtras(), false);
            this.mCurrentPW =
                    intent.getParcelableExtra(
                            UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_SECRET_KEY);
        }

        @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final View onCreateView(
                LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            return layoutInflater.inflate(
                    R.layout.sec_previous_password_description_layout, viewGroup, false);
        }

        @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onViewCreated(View view, Bundle bundle) {
            super.onViewCreated(view, bundle);
            this.mDescriptionTextView =
                    (TextView) view.findViewById(R.id.previous_password_description);
            this.mSubDescriptionTextView =
                    (TextView) view.findViewById(R.id.previous_password_sub_description);
            Button button = (Button) view.findViewById(R.id.ok_button);
            if (button != null) {
                button.setOnClickListener(
                        new View
                                .OnClickListener() { // from class:
                                                     // com.samsung.android.settings.lockscreen.PreviousPasswordDescriptionActivity$PreviousPasswordDescriptionFragment$$ExternalSyntheticLambda0
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view2) {
                                PreviousPasswordDescriptionActivity
                                                .PreviousPasswordDescriptionFragment
                                        previousPasswordDescriptionFragment =
                                                PreviousPasswordDescriptionActivity
                                                        .PreviousPasswordDescriptionFragment.this;
                                Intent chooseLockHintSettingsIntent =
                                        LockUtils.getChooseLockHintSettingsIntent(
                                                previousPasswordDescriptionFragment.getActivity(),
                                                previousPasswordDescriptionFragment.mUserId);
                                if (chooseLockHintSettingsIntent != null) {
                                    LockscreenCredential lockscreenCredential =
                                            previousPasswordDescriptionFragment.mCurrentPW;
                                    if (lockscreenCredential != null) {
                                        chooseLockHintSettingsIntent.putExtra(
                                                UcmAgentProviderImpl.UcmAgentSpiProperty
                                                        .KEY_SECRET_KEY,
                                                (Parcelable) lockscreenCredential);
                                    }
                                    previousPasswordDescriptionFragment.startActivity(
                                            chooseLockHintSettingsIntent);
                                }
                                SALogging.insertSALog(
                                        Settings.Secure.getInt(
                                                previousPasswordDescriptionFragment
                                                        .getContext()
                                                        .getContentResolver(),
                                                "save_previous_credential_description_show_count",
                                                0),
                                        String.valueOf(4400),
                                        "LSE2041",
                                        (String) null);
                                previousPasswordDescriptionFragment.getActivity().finish();
                            }
                        });
            }
            int credentialTypeForUser =
                    this.mLockPatternUtils.getCredentialTypeForUser(this.mUserId);
            int credentialTypeForUser2 = this.mLockPatternUtils.getCredentialTypeForUser(-9899);
            if (credentialTypeForUser == -1 || credentialTypeForUser2 == -1) {
                SemLog.d(
                        "PreviousPasswordDescriptionActivity",
                        "Lock Type is None! newLockType : "
                                + credentialTypeForUser
                                + " previousLockType : "
                                + credentialTypeForUser2);
                getActivity().finish();
            }
            if (credentialTypeForUser == 1) {
                getActivity()
                        .setTitle(
                                getResources()
                                        .getString(R.string.sec_lockscreen_previous_pattern_title));
            } else if (credentialTypeForUser == 3) {
                getActivity()
                        .setTitle(
                                getResources()
                                        .getString(R.string.sec_lockscreen_previous_pin_title));
            } else if (credentialTypeForUser == 4) {
                getActivity()
                        .setTitle(
                                getResources()
                                        .getString(
                                                R.string.sec_lockscreen_previous_password_title));
            }
            if (credentialTypeForUser2 == 1) {
                if (credentialTypeForUser == 1) {
                    this.mDescriptionTextView.setText(
                            getResources()
                                    .getString(
                                            R.string
                                                    .sec_lockscreen_previous_pattern_to_new_pattern_description));
                } else if (credentialTypeForUser == 3) {
                    this.mDescriptionTextView.setText(
                            getResources()
                                    .getString(
                                            R.string
                                                    .sec_lockscreen_previous_pattern_to_new_pin_description));
                } else if (credentialTypeForUser == 4) {
                    this.mDescriptionTextView.setText(
                            getResources()
                                    .getString(
                                            R.string
                                                    .sec_lockscreen_previous_pattern_to_new_password_description));
                }
            } else if (credentialTypeForUser2 == 3) {
                if (credentialTypeForUser == 1) {
                    this.mDescriptionTextView.setText(
                            getResources()
                                    .getString(
                                            R.string
                                                    .sec_lockscreen_previous_pin_to_new_pattern_description));
                } else if (credentialTypeForUser == 3) {
                    this.mDescriptionTextView.setText(
                            getResources()
                                    .getString(
                                            R.string
                                                    .sec_lockscreen_previous_pin_to_new_pin_description));
                } else if (credentialTypeForUser == 4) {
                    this.mDescriptionTextView.setText(
                            getResources()
                                    .getString(
                                            R.string
                                                    .sec_lockscreen_previous_pin_to_new_password_description));
                }
            } else if (credentialTypeForUser2 == 4) {
                if (credentialTypeForUser == 1) {
                    this.mDescriptionTextView.setText(
                            getResources()
                                    .getString(
                                            R.string
                                                    .sec_lockscreen_previous_password_to_new_pattern_description));
                } else if (credentialTypeForUser == 3) {
                    this.mDescriptionTextView.setText(
                            getResources()
                                    .getString(
                                            R.string
                                                    .sec_lockscreen_previous_password_to_new_pin_description));
                } else if (credentialTypeForUser == 4) {
                    this.mDescriptionTextView.setText(
                            getResources()
                                    .getString(
                                            R.string
                                                    .sec_lockscreen_previous_password_to_new_password_description));
                }
            }
            if (LockUtils.isSupportAodService()) {
                this.mSubDescriptionTextView.setText(
                        getResources()
                                .getString(
                                        R.string
                                                .sec_lockscreen_previous_credential_sub_description_lockscreen_aod));
            } else {
                this.mSubDescriptionTextView.setText(
                        getResources()
                                .getString(
                                        R.string
                                                .sec_lockscreen_previous_credential_sub_description_lockscreen));
            }
        }
    }

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(
                ":settings:show_fragment", PreviousPasswordDescriptionFragment.class.getName());
        intent.putExtra(":android:no_headers", true);
        return intent;
    }
}
