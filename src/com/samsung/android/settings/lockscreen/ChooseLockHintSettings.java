package com.samsung.android.settings.lockscreen;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.android.internal.widget.LockPatternUtils;
import com.android.internal.widget.LockscreenCredential;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.core.InstrumentedPreferenceFragment;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ChooseLockHintSettings extends SettingsActivity {
    public static final /* synthetic */ int $r8$clinit = 0;

    public static String getConfiguredPasswordType(Context context) {
        int activePasswordQuality =
                new LockPatternUtils(context).getActivePasswordQuality(UserHandle.semGetMyUserId());
        return activePasswordQuality != 65536
                ? (activePasswordQuality == 131072 || activePasswordQuality == 196608)
                        ? context.getString(R.string.sec_unlock_set_unlock_pin_title)
                        : (activePasswordQuality == 262144
                                        || activePasswordQuality == 327680
                                        || activePasswordQuality == 393216)
                                ? context.getString(R.string.sec_unlock_set_unlock_password_title)
                                : ApnSettings.MVNO_NONE
                : context.getResources().getString(R.string.sec_unlock_set_unlock_pattern_title);
    }

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(":settings:show_fragment", ChooseLockHintSettingsFragment.class.getName());
        intent.putExtra(":android:no_headers", true);
        return intent;
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Utils.isTablet() && getWindow().isFloating()) {
            getWindow().clearFlags(Integer.MIN_VALUE);
        }
        if (LockUtils.isApplyingTabletGUI(getApplicationContext())) {
            hideAppBar();
            ((CoordinatorLayout) findViewById(R.id.coordinator))
                    .setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
            AppCompatTextView appCompatTextView =
                    (AppCompatTextView) getWindow().getDecorView().findViewById(R.id.title);
            if (appCompatTextView != null) {
                appCompatTextView.setText(
                        String.format(
                                getString(R.string.sec_choose_lock_hint_title),
                                getConfiguredPasswordType(getApplicationContext())));
            }
            ((LinearLayout) findViewById(R.id.content_layout))
                    .setLayoutParams(new CoordinatorLayout.LayoutParams(-1, -2));
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ChooseLockHintSettingsFragment extends InstrumentedPreferenceFragment
            implements View.OnClickListener, TextView.OnEditorActionListener, TextWatcher {
        public String mConfiguredHintText;
        public LockscreenCredential mCurrentPW;
        public TextView mErrorHintTextView;
        public TextView mHeaderText;
        public EditText mHintEditText;
        public LinearLayout mImageContainer;
        public InputMethodManager mImm;
        public LockPatternUtils mLockPatternUtils;
        public Button mNextButton;
        public ScrollView mScrollView;
        public int mUserId;
        public final Handler mHandler = new Handler(Looper.getMainLooper());
        public boolean mErrorState = false;

        @Override // android.text.TextWatcher
        public final void afterTextChanged(Editable editable) {
            if (editable.length() < 1) {
                this.mNextButton.setEnabled(false);
                return;
            }
            this.mNextButton.setEnabled(true);
            this.mErrorState = false;
            this.mErrorHintTextView.setVisibility(4);
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 9999;
        }

        public final void handleNext$1() {
            String editable = this.mHintEditText.getText().toString();
            this.mConfiguredHintText = editable;
            if (TextUtils.isEmpty(editable)) {
                return;
            }
            String str = this.mConfiguredHintText;
            LockscreenCredential lockscreenCredential = this.mCurrentPW;
            if (lockscreenCredential != null
                    && new String(lockscreenCredential.getCredential()).equals(str)) {
                this.mErrorState = true;
                resetInputStateAndUpdateDesc();
                return;
            }
            int i = this.mUserId;
            Log.d("ChooseLockHintSettings", "startSaveHintAndFinish");
            LoggingHelper.insertEventLogging(28, 9012, 1L);
            this.mLockPatternUtils.setPasswordHint(str, i);
            getActivity().finish();
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            int id = view.getId();
            if (id == R.id.next_button) {
                handleNext$1();
            } else if (id == R.id.cancel_button) {
                Log.d("ChooseLockHintSettings", "Not save HintText");
                LoggingHelper.insertEventLogging(28, 9012, 0L);
                getActivity().finish();
            }
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            this.mLockPatternUtils = new LockPatternUtils(getActivity());
            this.mImm = (InputMethodManager) getActivity().getSystemService("input_method");
            Intent intent = getActivity().getIntent();
            if (!(getActivity() instanceof ChooseLockHintSettings)) {
                throw new SecurityException("Fragment contained in wrong activity");
            }
            this.mUserId = Utils.getUserIdFromBundle(getActivity(), intent.getExtras(), false);
            Log.d("ChooseLockHintSettings", "Initialize Hint value");
            this.mLockPatternUtils.setPasswordHint((String) null, this.mUserId);
        }

        @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final View onCreateView(
                LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            return LockUtils.isApplyingTabletGUI(getActivity())
                    ? layoutInflater.inflate(
                            R.layout.sec_choose_lock_hint_settings_tablet, viewGroup, false)
                    : layoutInflater.inflate(
                            R.layout.sec_choose_lock_hint_settings, viewGroup, false);
        }

        @Override // android.widget.TextView.OnEditorActionListener
        public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i != 0 && i != 6 && i != 5) {
                return false;
            }
            handleNext$1();
            return true;
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onPause() {
            super.onPause();
            this.mImm.hideSoftInputFromWindow(this.mHintEditText.getWindowToken(), 0);
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onResume() {
            super.onResume();
            resetInputStateAndUpdateDesc();
        }

        @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putBoolean("error_state", this.mErrorState);
        }

        @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onViewCreated(View view, Bundle bundle) {
            super.onViewCreated(view, bundle);
            this.mCurrentPW =
                    getActivity()
                            .getIntent()
                            .getParcelableExtra(
                                    UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_SECRET_KEY);
            this.mImageContainer =
                    (LinearLayout) view.findViewById(R.id.sec_choose_lock_hint_img_container);
            this.mScrollView = (ScrollView) view.findViewById(R.id.hint_scrollview);
            this.mHeaderText = (TextView) view.findViewById(R.id.hint_head_text);
            String configuredPasswordType =
                    ChooseLockHintSettings.getConfiguredPasswordType(getActivity());
            this.mHeaderText.setText(
                    String.format(
                            getString(R.string.sec_choose_lock_hint_description),
                            configuredPasswordType,
                            configuredPasswordType));
            getActivity()
                    .setTitle(
                            String.format(
                                    getString(R.string.sec_choose_lock_hint_title),
                                    configuredPasswordType));
            ((Button) view.findViewById(R.id.cancel_button)).setOnClickListener(this);
            Button button = (Button) view.findViewById(R.id.next_button);
            this.mNextButton = button;
            button.setOnClickListener(this);
            this.mNextButton.setEnabled(false);
            this.mErrorHintTextView = (TextView) view.findViewById(R.id.hint_error_text);
            EditText editText = (EditText) view.findViewById(R.id.hint_edit_text);
            this.mHintEditText = editText;
            editText.setSingleLine(true);
            this.mHintEditText.setOnEditorActionListener(this);
            this.mHintEditText.addTextChangedListener(this);
            this.mHintEditText.setImeOptions(33554432);
            this.mHintEditText.requestFocus();
            if (bundle != null) {
                this.mErrorState = bundle.getBoolean("error_state");
            }
            this.mHandler.postDelayed(
                    new ChooseLockHintSettings$ChooseLockHintSettingsFragment$$ExternalSyntheticLambda0(
                            this),
                    100L);
        }

        public final void resetInputStateAndUpdateDesc() {
            if (this.mErrorState) {
                this.mImageContainer.setVisibility(8);
                String format =
                        String.format(
                                getResources().getString(R.string.sec_choose_lock_wrong_hint_input),
                                ChooseLockHintSettings.getConfiguredPasswordType(getActivity()));
                this.mErrorHintTextView.setVisibility(0);
                this.mErrorHintTextView.setText(format);
                this.mErrorHintTextView.requestFocus();
                this.mHintEditText.setText(ApnSettings.MVNO_NONE);
                this.mScrollView.fullScroll(130);
            } else {
                this.mErrorHintTextView.setVisibility(4);
                this.mHandler.postDelayed(
                        new ChooseLockHintSettings$ChooseLockHintSettingsFragment$$ExternalSyntheticLambda0(
                                this),
                        100L);
            }
            if (this.mHintEditText.length() >= 1) {
                this.mNextButton.setEnabled(true);
            } else {
                this.mNextButton.setEnabled(false);
            }
        }

        @Override // android.text.TextWatcher
        public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}

        @Override // android.text.TextWatcher
        public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
    }
}
