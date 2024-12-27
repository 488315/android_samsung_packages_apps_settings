package com.samsung.android.settings.biometrics.fingerprint;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.fingerprint.Fingerprint;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.UserHandle;
import android.preference.PreferenceActivity;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceScreen;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.core.SettingsBaseActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.biometrics.BiometricsGenericHelper;
import com.samsung.android.settings.knox.KnoxUtils;
import com.samsung.android.settings.lockscreen.LockUtils;
import com.samsung.android.settings.logging.LoggingHelper;
import com.samsung.android.settings.security.SecurityUtils;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class FingerprintSettingsEdit extends SettingsActivity {
    public static ActionBar mActionbar = null;
    public static FingerprintSettingsEdit mActivity = null;
    public static AlertDialog mDeleteAllDialog = null;
    public static AlertDialog mDeleteDialog = null;
    public static TextInputEditText mEditText = null;
    public static FingerprintManager mFingerprintManager = null;
    public static CharSequence mFingerprintName = null;
    public static final AnonymousClass2 mHandler = new AnonymousClass2();
    public static InputMethodManager mInputMethodManager = null;
    public static Menu mOptionsMenu = null;
    public static int mSelectedFId = -1;
    public static int mUserId;
    public LockPatternUtils mLockPatternUtils = null;
    public List mItems = null;
    public int mSelectedIndexId = -1;
    public int mEnrolledFingerCount = 0;
    public final AnonymousClass1 mRemoveCallback =
            new FingerprintManager
                    .RemovalCallback() { // from class:
                                         // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit.1
                public final void onRemovalError(
                        Fingerprint fingerprint, int i, CharSequence charSequence) {
                    Log.secE(
                            "FpstFingerprintSettingsRename",
                            "Remove Error : "
                                    + i
                                    + ", "
                                    + ((Object) charSequence)
                                    + ", activity="
                                    + FingerprintSettingsEdit.mActivity);
                    FingerprintSettingsEdit fingerprintSettingsEdit =
                            FingerprintSettingsEdit.mActivity;
                    if (fingerprintSettingsEdit != null) {
                        String string =
                                FingerprintSettingsEdit.this.getString(
                                        R.string.sec_fingerprint_error_message_sensor_error);
                        boolean z = FingerprintSettingsUtils.SUB_DISPLAY_IS_LARGE_SCREEN;
                        FingerprintSettingsUtils.showSensorErrorDialog(
                                fingerprintSettingsEdit,
                                fingerprintSettingsEdit.getString(
                                        R.string.sec_fingerprint_attention),
                                string,
                                false);
                    }
                }

                public final void onRemovalSucceeded(Fingerprint fingerprint, int i) {
                    Log.d(
                            "FpstFingerprintSettingsRename",
                            "mRemoveCallback onRemovalSucceeded called: "
                                    + fingerprint.getBiometricId()
                                    + " ,remaining : "
                                    + i);
                    FingerprintSettingsEdit.mHandler
                            .obtainMessage(1000, fingerprint.getBiometricId(), 0)
                            .sendToTarget();
                    FingerprintSettingsEdit fingerprintSettingsEdit =
                            FingerprintSettingsEdit.mActivity;
                    if (fingerprintSettingsEdit != null) {
                        Context applicationContext =
                                fingerprintSettingsEdit.getApplicationContext();
                        View currentFocus = FingerprintSettingsEdit.mActivity.getCurrentFocus();
                        if (applicationContext == null || currentFocus == null) {
                            return;
                        }
                        currentFocus.announceForAccessibility(
                                applicationContext.getString(
                                        R.string.sec_biometrics_common_removed));
                    }
                }
            };

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit$2, reason: invalid class name */
    public final class AnonymousClass2 extends Handler {
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Log.d(
                    "FpstFingerprintSettingsRename",
                    "Handler received: "
                            .concat(FingerprintSettingsUtils.convertEvtToString(message.what)));
            int i = message.what;
            if (i != 1000) {
                Log.d(
                        "FpstFingerprintSettingsRename",
                        "handleMessage: ".concat(FingerprintSettingsUtils.convertEvtToString(i)));
                return;
            }
            FingerprintSettingsEdit fingerprintSettingsEdit = FingerprintSettingsEdit.mActivity;
            if (fingerprintSettingsEdit != null) {
                fingerprintSettingsEdit.onBackPressed();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class FingerprintSettingsRenameFragment extends SettingsPreferenceFragment
            implements View.OnKeyListener {
        public static FingerprintSettingsRenameFragment mFingerprintSettingsRenameFragment;
        public ViewGroup mButtonBar;
        public Button mCancelButton;
        public String mEditFingerName;
        public int mLastOrientation;
        public String mOriginalName;
        public Button mSaveButton;
        public TextInputLayout mTextInputLayout;
        public boolean mIsKeepEnrollSession = false;
        public final
        FingerprintSettingsEdit$FingerprintSettingsRenameFragment$$ExternalSyntheticLambda2
                initTextRunnable =
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit$FingerprintSettingsRenameFragment$$ExternalSyntheticLambda2
                            @Override // java.lang.Runnable
                            public final void run() {
                                FingerprintSettingsEdit.FingerprintSettingsRenameFragment.this
                                        .initRenameEditTextView();
                            }
                        };

        /* JADX WARN: Type inference failed for: r0v1, types: [com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit$FingerprintSettingsRenameFragment$$ExternalSyntheticLambda2] */
        public FingerprintSettingsRenameFragment() {
            mFingerprintSettingsRenameFragment = this;
        }

        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 254;
        }

        public final void handleNext() {
            TextInputEditText textInputEditText = FingerprintSettingsEdit.mEditText;
            if (textInputEditText == null) {
                Log.e(
                        "FpstFingerprintSettingsRenameFragment",
                        "handleNext can't get layout resource");
                finish();
                return;
            }
            Editable text = textInputEditText.getText();
            String charSequence = text == null ? null : text.toString();
            if (TextUtils.isEmpty(charSequence)) {
                return;
            }
            if (charSequence == null || !TextUtils.isEmpty(charSequence.trim())) {
                FingerprintManager fingerprintManager = FingerprintSettingsEdit.mFingerprintManager;
                if (fingerprintManager != null) {
                    fingerprintManager.rename(
                            FingerprintSettingsEdit.mSelectedFId,
                            FingerprintSettingsEdit.mUserId,
                            charSequence);
                }
                finish();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final void initRenameEditTextView() {
            Log.d("FpstFingerprintSettingsRenameFragment", "initRenameEditTextView");
            String string = getResources().getString(R.string.sec_fingerprint);
            final String format =
                    String.format(getString(R.string.sec_fingerprint_maximum_rename_character), 50);
            this.mTextInputLayout =
                    (TextInputLayout) getView().findViewById(R.id.fingerprint_name_input);
            TextInputEditText textInputEditText =
                    (TextInputEditText) getView().findViewById(R.id.fingerprint_name_editText);
            FingerprintSettingsEdit.mEditText = textInputEditText;
            if (textInputEditText == null) {
                Log.w("FpstFingerprintSettingsRenameFragment", "EditText view is null");
                AnonymousClass2 anonymousClass2 = FingerprintSettingsEdit.mHandler;
                anonymousClass2.removeCallbacks(this.initTextRunnable);
                anonymousClass2.postDelayed(this.initTextRunnable, 200L);
                return;
            }
            CharSequence charSequence = FingerprintSettingsEdit.mFingerprintName;
            if (charSequence != null) {
                string = charSequence.toString();
            } else if (FingerprintSettingsEdit.mSelectedFId != -1) {
                string =
                        String.format(
                                getResources().getString(R.string.sec_fingerprint_list_item),
                                Integer.valueOf(FingerprintSettingsEdit.mSelectedFId));
            }
            this.mOriginalName = string;
            String str = this.mEditFingerName;
            if (str != null) {
                string = str;
            } else {
                this.mEditFingerName = string;
            }
            FingerprintSettingsEdit.mEditText.setTextIsSelectable(false);
            FingerprintSettingsEdit.mEditText.setText(string);
            if (!TextUtils.isEmpty(string)) {
                try {
                    FingerprintSettingsEdit.mEditText.setSelection(string.length());
                } catch (IndexOutOfBoundsException unused) {
                    Log.d("FpstFingerprintSettingsRenameFragment", "Caught Exception setSelection");
                }
            }
            FingerprintSettingsEdit.mEditText.setEnabled(true);
            FingerprintSettingsEdit.mEditText.selectAll();
            FingerprintSettingsEdit.mEditText.setOnEditorActionListener(
                    new TextView
                            .OnEditorActionListener() { // from class:
                                                        // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit$FingerprintSettingsRenameFragment$$ExternalSyntheticLambda0
                        @Override // android.widget.TextView.OnEditorActionListener
                        public final boolean onEditorAction(
                                TextView textView, int i, KeyEvent keyEvent) {
                            FingerprintSettingsEdit.FingerprintSettingsRenameFragment
                                    fingerprintSettingsRenameFragment =
                                            FingerprintSettingsEdit
                                                    .FingerprintSettingsRenameFragment.this;
                            fingerprintSettingsRenameFragment.getClass();
                            if (i != 5 && i != 6) {
                                return false;
                            }
                            fingerprintSettingsRenameFragment.handleNext();
                            return false;
                        }
                    });
            FingerprintSettingsEdit.mEditText.addTextChangedListener(
                    new TextWatcher() { // from class:
                                        // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit.FingerprintSettingsRenameFragment.1
                        @Override // android.text.TextWatcher
                        public final synchronized void afterTextChanged(Editable editable) {
                            try {
                                if (editable.length() >= 50) {
                                    TextInputLayout textInputLayout =
                                            FingerprintSettingsRenameFragment.this.mTextInputLayout;
                                    if (textInputLayout != null) {
                                        textInputLayout.setError(format);
                                    }
                                } else {
                                    TextInputLayout textInputLayout2 =
                                            FingerprintSettingsRenameFragment.this.mTextInputLayout;
                                    if (textInputLayout2 != null) {
                                        textInputLayout2.setErrorEnabled(false);
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }

                        @Override // android.text.TextWatcher
                        public final void onTextChanged(
                                CharSequence charSequence2, int i, int i2, int i3) {
                            Menu menu;
                            String charSequence3 = charSequence2.toString();
                            FingerprintSettingsRenameFragment.this.mEditFingerName = charSequence3;
                            MenuItem item =
                                    (Utils.isTablet()
                                                    || (menu = FingerprintSettingsEdit.mOptionsMenu)
                                                            == null
                                                    || menu.size() <= 1)
                                            ? null
                                            : FingerprintSettingsEdit.mOptionsMenu.getItem(1);
                            if (charSequence3.trim().isEmpty()
                                    || FingerprintSettingsRenameFragment.this.mOriginalName.equals(
                                            charSequence3)) {
                                Button button = FingerprintSettingsRenameFragment.this.mSaveButton;
                                if (button != null) {
                                    button.setEnabled(false);
                                }
                                if (item != null) {
                                    item.setEnabled(false);
                                }
                                FingerprintSettingsEdit.mEditText.setHint(
                                        R.string.sec_fingerprint_rename_enter_text);
                                return;
                            }
                            Button button2 = FingerprintSettingsRenameFragment.this.mSaveButton;
                            if (button2 != null) {
                                button2.setEnabled(true);
                            }
                            if (item != null) {
                                item.setEnabled(true);
                            }
                        }

                        @Override // android.text.TextWatcher
                        public final void beforeTextChanged(
                                CharSequence charSequence2, int i, int i2, int i3) {}
                    });
            FingerprintSettingsEdit.mEditText.setOnKeyListener(this);
            FingerprintSettingsEdit.mEditText.setFilters(
                    new InputFilter[] {
                        new InputFilter
                                .LengthFilter() { // from class:
                                                  // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit.FingerprintSettingsRenameFragment.2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            {
                                super(50);
                            }

                            @Override // android.text.InputFilter.LengthFilter,
                                      // android.text.InputFilter
                            public final CharSequence filter(
                                    CharSequence charSequence2,
                                    int i,
                                    int i2,
                                    Spanned spanned,
                                    int i3,
                                    int i4) {
                                CharSequence filter =
                                        super.filter(charSequence2, i, i2, spanned, i3, i4);
                                if (filter != null) {
                                    FingerprintSettingsRenameFragment.this.mTextInputLayout
                                            .setError(format);
                                }
                                return filter;
                            }
                        }
                    });
            final FragmentActivity activity = getActivity();
            FingerprintSettingsEdit.mEditText.setInputType(
                    FingerprintSettingsEdit.mEditText.getInputType());
            if (activity instanceof PreferenceActivity) {
                CharSequence text = getText(R.string.common_edit);
                ((PreferenceActivity) activity).showBreadCrumbs(text, text);
            }
            FingerprintSettingsEdit.mEditText.setOnFocusChangeListener(
                    new View
                            .OnFocusChangeListener() { // from class:
                                                       // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit$FingerprintSettingsRenameFragment$$ExternalSyntheticLambda1
                        @Override // android.view.View.OnFocusChangeListener
                        public final void onFocusChange(View view, boolean z) {
                            Activity activity2 = activity;
                            InputMethodManager inputMethodManager =
                                    FingerprintSettingsEdit.mInputMethodManager;
                            if (inputMethodManager != null) {
                                if (z) {
                                    inputMethodManager.showSoftInput(
                                            FingerprintSettingsEdit.mEditText, 2);
                                } else {
                                    if (activity2.isFinishing()) {
                                        FingerprintSettingsEdit.mInputMethodManager
                                                .hideSoftInputFromWindow(
                                                        FingerprintSettingsEdit.mEditText
                                                                .getWindowToken(),
                                                        0);
                                        return;
                                    }
                                    FingerprintSettingsEdit.mEditText.setImeOptions(1);
                                    FingerprintSettingsEdit.mEditText.requestLayout();
                                    FingerprintSettingsEdit.mEditText.requestFocus();
                                }
                            }
                        }
                    });
            FingerprintSettingsEdit.mEditText.requestFocus();
            if (this.mSaveButton != null) {
                if (string.isEmpty()
                        || FingerprintSettingsEdit.mEditText
                                .getText()
                                .toString()
                                .equals(this.mOriginalName)) {
                    this.mSaveButton.setEnabled(false);
                } else {
                    this.mSaveButton.setEnabled(true);
                }
            }
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            Log.d("FpstFingerprintSettingsRenameFragment", "onCreate");
            setRetainInstance(true);
            FingerprintSettingsEdit.mInputMethodManager =
                    (InputMethodManager) getContext().getSystemService("input_method");
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final View onCreateView(
                LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            Log.d("FpstFingerprintSettingsRenameFragment", "onCreateView");
            FragmentActivity activity = getActivity();
            if (activity instanceof SettingsBaseActivity) {
                ((SettingsBaseActivity) activity).disableExtendedAppBar();
                ActionBar supportActionBar = ((AppCompatActivity) activity).getSupportActionBar();
                FingerprintSettingsEdit.mActionbar = supportActionBar;
                if (supportActionBar != null) {
                    supportActionBar.setDisplayHomeAsUpEnabled(true);
                    FingerprintSettingsEdit.mActionbar.setTitle(R.string.common_edit);
                } else {
                    Log.e(
                            "FpstFingerprintSettingsRenameFragment",
                            " onCreateView mActionbar is null");
                }
            }
            if (bundle != null) {
                this.mEditFingerName = bundle.getString("finger_name");
            }
            PreferenceScreen preferenceScreen = getPreferenceScreen();
            if (preferenceScreen != null) {
                preferenceScreen.removeAll();
            }
            addPreferencesFromResource(R.xml.sec_fingerprint_edit_fragment);
            return super.onCreateView(layoutInflater, viewGroup, bundle);
        }

        @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onDestroy() {
            Log.d("FpstFingerprintSettingsRenameFragment", "onDestroy");
            super.onDestroy();
        }

        @Override // android.view.View.OnKeyListener
        public final boolean onKey(View view, int i, KeyEvent keyEvent) {
            if (keyEvent == null || keyEvent.getAction() != 1 || i != 23) {
                return false;
            }
            handleNext();
            return false;
        }

        @Override // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onPause() {
            TextInputEditText textInputEditText;
            super.onPause();
            Log.d("FpstFingerprintSettingsRenameFragment", "onPause");
            Intent intent = null;
            if (FingerprintSettingsEdit.mInputMethodManager != null
                    && (textInputEditText = FingerprintSettingsEdit.mEditText) != null) {
                textInputEditText.setOnFocusChangeListener(null);
                if (SecurityUtils.ConnectedMobileKeypad(getContext())) {
                    FingerprintSettingsEdit.mInputMethodManager.toggleSoftInputFromWindow(
                            FingerprintSettingsEdit.mEditText.getWindowToken(), 2, 0);
                } else {
                    FingerprintSettingsEdit.mInputMethodManager.hideSoftInputFromWindow(
                            FingerprintSettingsEdit.mEditText.getWindowToken(), 0);
                }
            }
            AnonymousClass2 anonymousClass2 = FingerprintSettingsEdit.mHandler;
            if (anonymousClass2 != null) {
                anonymousClass2.removeCallbacks(this.initTextRunnable);
            }
            if (this.mLastOrientation == getResources().getConfiguration().orientation
                    && !getActivity().isChangingConfigurations()) {
                Log.d(
                        "FpstFingerprintSettingsRenameFragment",
                        "cancelAndSessionEnd() : mIsKeepEnrollSession -"
                                + this.mIsKeepEnrollSession);
                if (getActivity() == null) {
                    Log.e(
                            "FpstFingerprintSettingsRenameFragment",
                            "cancelAndSessionEnd() : Null Activity");
                } else {
                    if (!this.mIsKeepEnrollSession) {
                        intent = new Intent();
                        intent.putExtra("reason", "cancelsession");
                        Log.d(
                                "FpstFingerprintSettingsRenameFragment",
                                "cancelAndSessionEnd cancel session with intent");
                    }
                    this.mIsKeepEnrollSession = false;
                    Log.d("FpstFingerprintSettingsRenameFragment", "finishFingerprintSettings()");
                    if (getTargetFragment() != null) {
                        getTargetFragment().onActivityResult(getTargetRequestCode(), 0, intent);
                    }
                }
            }
            this.mLastOrientation = getResources().getConfiguration().orientation;
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settings.core.InstrumentedPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.fragment.app.Fragment
        public final void onResume() {
            super.onResume();
            Log.d("FpstFingerprintSettingsRenameFragment", "onResume");
            initRenameEditTextView();
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onSaveInstanceState(Bundle bundle) {
            super.onSaveInstanceState(bundle);
            bundle.putString("finger_name", this.mEditFingerName);
        }

        @Override // com.android.settings.SettingsPreferenceFragment,
                  // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
        public final void onViewCreated(View view, Bundle bundle) {
            super.onViewCreated(view, bundle);
            ViewGroup viewGroup = (ViewGroup) getActivity().findViewById(R.id.button_bar);
            this.mButtonBar = viewGroup;
            if (viewGroup != null) {
                viewGroup.setVisibility(0);
                View inflate =
                        getLayoutInflater()
                                .inflate(
                                        R.layout.sec_fingerprint_edit_bottom_button,
                                        (ViewGroup) null);
                this.mCancelButton = (Button) inflate.findViewById(R.id.cancel_button);
                this.mSaveButton = (Button) inflate.findViewById(R.id.save_button);
                Button button = this.mCancelButton;
                if (button != null) {
                    final int i = 0;
                    button.setOnClickListener(
                            new View.OnClickListener(
                                    this) { // from class:
                                            // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit$FingerprintSettingsRenameFragment$$ExternalSyntheticLambda4
                                public final /* synthetic */ FingerprintSettingsEdit
                                                .FingerprintSettingsRenameFragment
                                        f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    int i2 = i;
                                    FingerprintSettingsEdit.FingerprintSettingsRenameFragment
                                            fingerprintSettingsRenameFragment = this.f$0;
                                    fingerprintSettingsRenameFragment.getClass();
                                    switch (i2) {
                                        case 0:
                                            LoggingHelper.insertEventLogging(8219, 8230);
                                            fingerprintSettingsRenameFragment.mIsKeepEnrollSession =
                                                    true;
                                            fingerprintSettingsRenameFragment
                                                    .getActivity()
                                                    .onBackPressed();
                                            break;
                                        default:
                                            LoggingHelper.insertEventLogging(8219, 8231);
                                            fingerprintSettingsRenameFragment.handleNext();
                                            break;
                                    }
                                }
                            });
                }
                Button button2 = this.mSaveButton;
                if (button2 != null) {
                    final int i2 = 1;
                    button2.setOnClickListener(
                            new View.OnClickListener(
                                    this) { // from class:
                                            // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit$FingerprintSettingsRenameFragment$$ExternalSyntheticLambda4
                                public final /* synthetic */ FingerprintSettingsEdit
                                                .FingerprintSettingsRenameFragment
                                        f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.view.View.OnClickListener
                                public final void onClick(View view2) {
                                    int i22 = i2;
                                    FingerprintSettingsEdit.FingerprintSettingsRenameFragment
                                            fingerprintSettingsRenameFragment = this.f$0;
                                    fingerprintSettingsRenameFragment.getClass();
                                    switch (i22) {
                                        case 0:
                                            LoggingHelper.insertEventLogging(8219, 8230);
                                            fingerprintSettingsRenameFragment.mIsKeepEnrollSession =
                                                    true;
                                            fingerprintSettingsRenameFragment
                                                    .getActivity()
                                                    .onBackPressed();
                                            break;
                                        default:
                                            LoggingHelper.insertEventLogging(8219, 8231);
                                            fingerprintSettingsRenameFragment.handleNext();
                                            break;
                                    }
                                }
                            });
                }
                if (!Utils.isTablet()) {
                    final int i3 = 1;
                    this.mButtonBar.addOnLayoutChangeListener(
                            new View.OnLayoutChangeListener(
                                    this) { // from class:
                                            // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit$FingerprintSettingsRenameFragment$$ExternalSyntheticLambda3
                                public final /* synthetic */ FingerprintSettingsEdit
                                                .FingerprintSettingsRenameFragment
                                        f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // android.view.View.OnLayoutChangeListener
                                public final void onLayoutChange(
                                        View view2,
                                        int i4,
                                        int i5,
                                        int i6,
                                        int i7,
                                        int i8,
                                        int i9,
                                        int i10,
                                        int i11) {
                                    InputMethodManager inputMethodManager;
                                    InputMethodManager inputMethodManager2;
                                    int i12 = i3;
                                    FingerprintSettingsEdit.FingerprintSettingsRenameFragment
                                            fingerprintSettingsRenameFragment = this.f$0;
                                    switch (i12) {
                                        case 0:
                                            fingerprintSettingsRenameFragment.getClass();
                                            if (!Utils.isTablet()
                                                    && FingerprintSettingsEdit.mActionbar != null
                                                    && FingerprintSettingsEdit.mOptionsMenu
                                                            != null) {
                                                if (!(fingerprintSettingsRenameFragment
                                                                        .getResources()
                                                                        .getConfiguration()
                                                                        .orientation
                                                                == 2
                                                        && (inputMethodManager =
                                                                        FingerprintSettingsEdit
                                                                                .mInputMethodManager)
                                                                != null
                                                        && inputMethodManager
                                                                .isInputMethodShown())) {
                                                    FingerprintSettingsEdit.mActionbar
                                                            .setDisplayHomeAsUpEnabled(true);
                                                    FingerprintSettingsEdit.mActionbar.setTitle(
                                                            R.string.common_edit);
                                                    FingerprintSettingsEdit.mOptionsMenu.clear();
                                                    FingerprintSettingsEdit.mOptionsMenu
                                                            .add(0, 4, 0, R.string.common_remove)
                                                            .setShowAsAction(1);
                                                    ViewGroup viewGroup2 =
                                                            fingerprintSettingsRenameFragment
                                                                    .mButtonBar;
                                                    if (viewGroup2 != null) {
                                                        viewGroup2.setVisibility(0);
                                                        break;
                                                    }
                                                } else {
                                                    FingerprintSettingsEdit.mActionbar
                                                            .setDisplayHomeAsUpEnabled(false);
                                                    FingerprintSettingsEdit.mActionbar.setTitle(
                                                            R.string.bluetooth_rename_button);
                                                    FingerprintSettingsEdit.mOptionsMenu.clear();
                                                    FingerprintSettingsEdit.mOptionsMenu
                                                            .add(
                                                                    0,
                                                                    2,
                                                                    0,
                                                                    R.string
                                                                            .lockpassword_cancel_label)
                                                            .setShowAsAction(1);
                                                    FingerprintSettingsEdit.mOptionsMenu
                                                            .add(0, 3, 1, R.string.menu_save)
                                                            .setShowAsAction(1);
                                                    TextInputEditText textInputEditText =
                                                            FingerprintSettingsEdit.mEditText;
                                                    if (textInputEditText != null) {
                                                        String editable =
                                                                textInputEditText
                                                                        .getText()
                                                                        .toString();
                                                        if (editable.trim().isEmpty()
                                                                || editable.equals(
                                                                        fingerprintSettingsRenameFragment
                                                                                .mOriginalName)) {
                                                            FingerprintSettingsEdit.mOptionsMenu
                                                                    .getItem(1)
                                                                    .setEnabled(false);
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                            break;
                                        default:
                                            if (fingerprintSettingsRenameFragment
                                                                    .getResources()
                                                                    .getConfiguration()
                                                                    .orientation
                                                            == 2
                                                    && (inputMethodManager2 =
                                                                    FingerprintSettingsEdit
                                                                            .mInputMethodManager)
                                                            != null
                                                    && inputMethodManager2.isInputMethodShown()) {
                                                fingerprintSettingsRenameFragment.mButtonBar
                                                        .setVisibility(8);
                                                View findViewById =
                                                        fingerprintSettingsRenameFragment
                                                                .getView()
                                                                .findViewById(R.id.pwd_body_layout);
                                                if (findViewById != null) {
                                                    findViewById.requestLayout();
                                                    break;
                                                }
                                            }
                                            break;
                                    }
                                }
                            });
                }
                this.mButtonBar.removeAllViews();
                this.mButtonBar.addView(inflate);
            } else {
                Log.e(
                        "FpstFingerprintSettingsRenameFragment",
                        "initBottomButtonBar buttonBar null");
            }
            final int i4 = 0;
            view.addOnLayoutChangeListener(
                    new View.OnLayoutChangeListener(
                            this) { // from class:
                                    // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit$FingerprintSettingsRenameFragment$$ExternalSyntheticLambda3
                        public final /* synthetic */ FingerprintSettingsEdit
                                        .FingerprintSettingsRenameFragment
                                f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.view.View.OnLayoutChangeListener
                        public final void onLayoutChange(
                                View view2,
                                int i42,
                                int i5,
                                int i6,
                                int i7,
                                int i8,
                                int i9,
                                int i10,
                                int i11) {
                            InputMethodManager inputMethodManager;
                            InputMethodManager inputMethodManager2;
                            int i12 = i4;
                            FingerprintSettingsEdit.FingerprintSettingsRenameFragment
                                    fingerprintSettingsRenameFragment = this.f$0;
                            switch (i12) {
                                case 0:
                                    fingerprintSettingsRenameFragment.getClass();
                                    if (!Utils.isTablet()
                                            && FingerprintSettingsEdit.mActionbar != null
                                            && FingerprintSettingsEdit.mOptionsMenu != null) {
                                        if (!(fingerprintSettingsRenameFragment
                                                                .getResources()
                                                                .getConfiguration()
                                                                .orientation
                                                        == 2
                                                && (inputMethodManager =
                                                                FingerprintSettingsEdit
                                                                        .mInputMethodManager)
                                                        != null
                                                && inputMethodManager.isInputMethodShown())) {
                                            FingerprintSettingsEdit.mActionbar
                                                    .setDisplayHomeAsUpEnabled(true);
                                            FingerprintSettingsEdit.mActionbar.setTitle(
                                                    R.string.common_edit);
                                            FingerprintSettingsEdit.mOptionsMenu.clear();
                                            FingerprintSettingsEdit.mOptionsMenu
                                                    .add(0, 4, 0, R.string.common_remove)
                                                    .setShowAsAction(1);
                                            ViewGroup viewGroup2 =
                                                    fingerprintSettingsRenameFragment.mButtonBar;
                                            if (viewGroup2 != null) {
                                                viewGroup2.setVisibility(0);
                                                break;
                                            }
                                        } else {
                                            FingerprintSettingsEdit.mActionbar
                                                    .setDisplayHomeAsUpEnabled(false);
                                            FingerprintSettingsEdit.mActionbar.setTitle(
                                                    R.string.bluetooth_rename_button);
                                            FingerprintSettingsEdit.mOptionsMenu.clear();
                                            FingerprintSettingsEdit.mOptionsMenu
                                                    .add(
                                                            0,
                                                            2,
                                                            0,
                                                            R.string.lockpassword_cancel_label)
                                                    .setShowAsAction(1);
                                            FingerprintSettingsEdit.mOptionsMenu
                                                    .add(0, 3, 1, R.string.menu_save)
                                                    .setShowAsAction(1);
                                            TextInputEditText textInputEditText =
                                                    FingerprintSettingsEdit.mEditText;
                                            if (textInputEditText != null) {
                                                String editable =
                                                        textInputEditText.getText().toString();
                                                if (editable.trim().isEmpty()
                                                        || editable.equals(
                                                                fingerprintSettingsRenameFragment
                                                                        .mOriginalName)) {
                                                    FingerprintSettingsEdit.mOptionsMenu
                                                            .getItem(1)
                                                            .setEnabled(false);
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    break;
                                default:
                                    if (fingerprintSettingsRenameFragment
                                                            .getResources()
                                                            .getConfiguration()
                                                            .orientation
                                                    == 2
                                            && (inputMethodManager2 =
                                                            FingerprintSettingsEdit
                                                                    .mInputMethodManager)
                                                    != null
                                            && inputMethodManager2.isInputMethodShown()) {
                                        fingerprintSettingsRenameFragment.mButtonBar.setVisibility(
                                                8);
                                        View findViewById =
                                                fingerprintSettingsRenameFragment
                                                        .getView()
                                                        .findViewById(R.id.pwd_body_layout);
                                        if (findViewById != null) {
                                            findViewById.requestLayout();
                                            break;
                                        }
                                    }
                                    break;
                            }
                        }
                    });
        }
    }

    @Override // com.android.settings.SettingsActivity, android.app.Activity
    public final Intent getIntent() {
        Intent intent = new Intent(super.getIntent());
        intent.putExtra(
                ":settings:show_fragment", FingerprintSettingsRenameFragment.class.getName());
        return intent;
    }

    @Override // com.android.settings.SettingsActivity
    public final boolean isValidFragment(String str) {
        return FingerprintSettingsRenameFragment.class.getName().equals(str);
    }

    @Override // com.android.settings.SettingsActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.appcompat.app.AppCompatActivity, androidx.activity.ComponentActivity,
              // android.app.Activity, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Log.d("FpstFingerprintSettingsRename", "onConfigurationChanged");
        if (BiometricsGenericHelper.isBlockedInMultiWindowMode(this, configuration)) {
            finish();
        }
    }

    @Override // com.android.settings.SettingsActivity,
              // com.android.settings.core.SettingsBaseActivity,
              // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity,
              // androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("FpstFingerprintSettingsRename", "onCreate");
        Intent intent = getIntent();
        mActivity = this;
        if ((Rune.isSamsungDexMode(this)
                        || LockUtils.isInMultiWindow(this)
                        || getResources().getConfiguration().windowConfiguration.isSplitScreen())
                && !BiometricsGenericHelper.isEnabledBiometricsMenuInDexMode(this)) {
            finish();
            return;
        }
        mFingerprintManager = Utils.getFingerprintManagerOrNull(this);
        int intExtra = intent.getIntExtra("android.intent.extra.USER_ID", UserHandle.myUserId());
        mUserId = intExtra;
        FingerprintManager fingerprintManager = mFingerprintManager;
        if (fingerprintManager == null || !fingerprintManager.hasEnrolledFingerprints(intExtra)) {
            finish();
            return;
        }
        if (Utils.isTablet()) {
            setFinishOnTouchOutside(false);
        }
        this.mLockPatternUtils = new LockPatternUtils(this);
        mSelectedFId = intent.getIntExtra("selected_id", -1);
        Log.d("FpstFingerprintSettingsRename", "UserId : " + mUserId);
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("SelectedFId : "), mSelectedFId, "FpstFingerprintSettingsRename");
        List enrolledFingerprints = mFingerprintManager.getEnrolledFingerprints(mUserId);
        this.mItems = enrolledFingerprints;
        this.mEnrolledFingerCount = enrolledFingerprints.size();
        Preference$$ExternalSyntheticOutline0.m(
                new StringBuilder("Enrolled FingerCount: "),
                this.mEnrolledFingerCount,
                "FpstFingerprintSettingsRename");
        for (int i = 0; i < this.mEnrolledFingerCount; i++) {
            if (((Fingerprint) this.mItems.get(i)).getBiometricId() == mSelectedFId) {
                this.mSelectedIndexId = i;
                mFingerprintName = ((Fingerprint) this.mItems.get(i)).getName();
                Preference$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Selected IndexId: "),
                        this.mSelectedIndexId,
                        "FpstFingerprintSettingsRename");
                return;
            }
        }
    }

    @Override // android.app.Activity
    public final boolean onCreateOptionsMenu(Menu menu) {
        mOptionsMenu = menu;
        menu.clear();
        menu.add(0, 4, 0, R.string.common_remove).setShowAsAction(1);
        return super.onCreateOptionsMenu(menu);
    }

    @Override // com.android.settings.SettingsActivity, androidx.appcompat.app.AppCompatActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onDestroy() {
        Log.d("FpstFingerprintSettingsRename", "onDestroy");
        mFingerprintManager = null;
        super.onDestroy();
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity, android.app.Activity
    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        String string;
        int i;
        String str;
        TextInputEditText textInputEditText;
        FingerprintSettingsRenameFragment fingerprintSettingsRenameFragment =
                FingerprintSettingsRenameFragment.mFingerprintSettingsRenameFragment;
        int itemId = menuItem.getItemId();
        if (itemId == 2) {
            fingerprintSettingsRenameFragment.mIsKeepEnrollSession = true;
            fingerprintSettingsRenameFragment.getActivity().onBackPressed();
            return true;
        }
        if (itemId == 3) {
            fingerprintSettingsRenameFragment.handleNext();
            return true;
        }
        if (itemId != 4) {
            if (itemId != 16908332) {
                return super.onOptionsItemSelected(menuItem);
            }
            finish();
            return true;
        }
        LoggingHelper.insertFlowLogging(8229);
        InputMethodManager inputMethodManager = mInputMethodManager;
        if (inputMethodManager != null && (textInputEditText = mEditText) != null) {
            inputMethodManager.hideSoftInputFromWindow(textInputEditText.getWindowToken(), 0);
        }
        if (this.mEnrolledFingerCount == 1) {
            int i2 = mUserId;
            String str2 = null;
            if (i2 != 0 && SemPersonaManager.isKnoxId(i2)) {
                boolean fingerprintUnlockEnabled =
                        FingerprintSettingsUtils.getFingerprintUnlockEnabled(
                                this.mLockPatternUtils, mUserId);
                if (KnoxUtils.isMultifactorAuthEnforced(this, mUserId)
                        && fingerprintUnlockEnabled) {
                    String string2 = getString(R.string.knox_fp_cant_delete_popup_message_body);
                    String string3 = getString(R.string.knox_fp_cant_delete_popup_message_title);
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    AlertController.AlertParams alertParams = builder.P;
                    alertParams.mTitle = string3;
                    alertParams.mMessage = string2;
                    builder.setPositiveButton(
                            android.R.string.ok, (DialogInterface.OnClickListener) null);
                    builder.create().show();
                }
            }
            Log.d("FpstFingerprintSettingsRename", "deleteAllFingerPrint : selectedCount 1");
            final boolean fingerprintUnlockEnabled2 =
                    FingerprintSettingsUtils.getFingerprintUnlockEnabled(
                            this.mLockPatternUtils, mUserId);
            final boolean z =
                    Settings.System.getInt(getContentResolver(), "fingerprint_secret_box", 0) == 1
                            || (!SecurityUtils.isEnabledSamsungPass(this)
                                    && Settings.Secure.getInt(
                                                    getContentResolver(), "fingerprint_webpass", 0)
                                            == 1)
                            || Settings.Secure.getInt(
                                            getContentResolver(),
                                            "fingerprint_samsungaccount_confirmed",
                                            0)
                                    == 1;
            if (KnoxUtils.isKnoxOrganizationOwnedDevice(this, mUserId)
                    && KnoxUtils.isMultifactorAuthEnforced(this, mUserId)
                    && fingerprintUnlockEnabled2) {
                str = getString(R.string.knox_fp_delete_1_with_warning_popup_message_body);
                str2 = getString(R.string.knox_fp_delete_with_warning_popup_message_title);
                i = R.string.knox_fp_cant_delete_popup_positive_button;
            } else {
                if (Rune.isChinaModel()) {
                    string = getString(R.string.sec_fingerprint_delete_case3_fingerprints_msg_chn);
                } else if (fingerprintUnlockEnabled2 || z) {
                    str2 = getString(R.string.sec_fingerprint_delete_1_fingerprint_title_feature);
                    string = getString(R.string.sec_fingerprint_delete_case3_fingerprints_msg);
                } else {
                    string = getString(R.string.sec_fingerprint_delete_1_fingerprint_title);
                }
                String str3 = string;
                i = R.string.common_remove;
                str = str3;
            }
            AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
            AlertController.AlertParams alertParams2 = builder2.P;
            alertParams2.mTitle = str2;
            alertParams2.mMessage = str;
            builder2.setPositiveButton(
                    i,
                    new DialogInterface.OnClickListener() { // from class:
                        // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit$$ExternalSyntheticLambda3
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i3) {
                            FingerprintSettingsEdit fingerprintSettingsEdit =
                                    FingerprintSettingsEdit.this;
                            boolean z2 = fingerprintUnlockEnabled2;
                            boolean z3 = z;
                            Menu menu = FingerprintSettingsEdit.mOptionsMenu;
                            fingerprintSettingsEdit.getClass();
                            Log.d(
                                    "FpstFingerprintSettingsRename",
                                    "deRegisterAllFingerprint : removeAllEnrolledFingers");
                            LoggingHelper.insertEventLogging(8229, 8233, 5L);
                            if (z2) {
                                Log.d(
                                        "FpstFingerprintSettingsRename",
                                        "deRegisterAllFingerprint :"
                                            + " DevicePolicyManager.PASSWORD_QUALITY_UNSPECIFIED");
                                Settings.System.putInt(
                                        fingerprintSettingsEdit.getContentResolver(),
                                        "db_lockscreen_is_smart_lock",
                                        0);
                            }
                            if (!z2 && !z3) {
                                fingerprintSettingsEdit.removeFingerprint();
                                return;
                            }
                            Settings.System.putInt(
                                    fingerprintSettingsEdit.getContentResolver(),
                                    "fingerprint_secret_box",
                                    0);
                            Settings.Secure.putInt(
                                    fingerprintSettingsEdit.getContentResolver(),
                                    "fingerprint_webpass",
                                    0);
                            Settings.System.putInt(
                                    fingerprintSettingsEdit.getContentResolver(),
                                    "fingerprint_fingerIndex",
                                    0);
                            Settings.Secure.putInt(
                                    fingerprintSettingsEdit.getContentResolver(),
                                    "fingerprint_used_samsungaccount",
                                    0);
                            Settings.Secure.putInt(
                                    fingerprintSettingsEdit.getContentResolver(),
                                    "fingerprint_samsungaccount_confirmed",
                                    0);
                            FingerprintSettingsUtils.removeFingerprintLock(
                                    FingerprintSettingsEdit.mUserId,
                                    fingerprintSettingsEdit,
                                    fingerprintSettingsEdit.mLockPatternUtils);
                            fingerprintSettingsEdit.removeFingerprint();
                        }
                    });
            final int i3 = 1;
            builder2.setNegativeButton(
                    R.string.common_cancel,
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit$$ExternalSyntheticLambda1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i4) {
                            switch (i3) {
                                case 0:
                                    Menu menu = FingerprintSettingsEdit.mOptionsMenu;
                                    LoggingHelper.insertEventLogging(8229, 8232);
                                    break;
                                default:
                                    Menu menu2 = FingerprintSettingsEdit.mOptionsMenu;
                                    LoggingHelper.insertEventLogging(8229, 8232);
                                    break;
                            }
                        }
                    });
            AlertDialog create = builder2.create();
            mDeleteAllDialog = create;
            final int i4 = 1;
            create.setOnShowListener(
                    new DialogInterface.OnShowListener(
                            this) { // from class:
                                    // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit$$ExternalSyntheticLambda2
                        public final /* synthetic */ FingerprintSettingsEdit f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnShowListener
                        public final void onShow(DialogInterface dialogInterface) {
                            int i5 = i4;
                            FingerprintSettingsEdit fingerprintSettingsEdit = this.f$0;
                            switch (i5) {
                                case 0:
                                    Menu menu = FingerprintSettingsEdit.mOptionsMenu;
                                    fingerprintSettingsEdit.getClass();
                                    ((AlertDialog) dialogInterface)
                                            .getButton(-1)
                                            .setTextColor(
                                                    fingerprintSettingsEdit.getColor(
                                                            R.color
                                                                    .sec_biometrics_dialog_remove_btn_color));
                                    break;
                                default:
                                    Menu menu2 = FingerprintSettingsEdit.mOptionsMenu;
                                    fingerprintSettingsEdit.getClass();
                                    ((AlertDialog) dialogInterface)
                                            .getButton(-1)
                                            .setTextColor(
                                                    fingerprintSettingsEdit.getColor(
                                                            R.color
                                                                    .sec_biometrics_dialog_remove_btn_color));
                                    break;
                            }
                        }
                    });
            mDeleteAllDialog.semSetAnchor(findViewById(R.id.action_bar), 1);
            mDeleteAllDialog.show();
        } else {
            AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
            builder3.P.mMessage =
                    getString(R.string.sec_fingerprint_delete_case1_1_fingerprint_msg);
            builder3.setPositiveButton(
                    R.string.common_remove,
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit$$ExternalSyntheticLambda0
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i5) {
                            FingerprintSettingsEdit fingerprintSettingsEdit =
                                    FingerprintSettingsEdit.this;
                            LoggingHelper.insertEventLogging(
                                    8229, 8233, fingerprintSettingsEdit.mSelectedIndexId + 1);
                            fingerprintSettingsEdit.removeFingerprint();
                        }
                    });
            final int i5 = 0;
            builder3.setNegativeButton(
                    R.string.common_cancel,
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit$$ExternalSyntheticLambda1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i42) {
                            switch (i5) {
                                case 0:
                                    Menu menu = FingerprintSettingsEdit.mOptionsMenu;
                                    LoggingHelper.insertEventLogging(8229, 8232);
                                    break;
                                default:
                                    Menu menu2 = FingerprintSettingsEdit.mOptionsMenu;
                                    LoggingHelper.insertEventLogging(8229, 8232);
                                    break;
                            }
                        }
                    });
            AlertDialog create2 = builder3.create();
            mDeleteDialog = create2;
            final int i6 = 0;
            create2.setOnShowListener(
                    new DialogInterface.OnShowListener(
                            this) { // from class:
                                    // com.samsung.android.settings.biometrics.fingerprint.FingerprintSettingsEdit$$ExternalSyntheticLambda2
                        public final /* synthetic */ FingerprintSettingsEdit f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // android.content.DialogInterface.OnShowListener
                        public final void onShow(DialogInterface dialogInterface) {
                            int i52 = i6;
                            FingerprintSettingsEdit fingerprintSettingsEdit = this.f$0;
                            switch (i52) {
                                case 0:
                                    Menu menu = FingerprintSettingsEdit.mOptionsMenu;
                                    fingerprintSettingsEdit.getClass();
                                    ((AlertDialog) dialogInterface)
                                            .getButton(-1)
                                            .setTextColor(
                                                    fingerprintSettingsEdit.getColor(
                                                            R.color
                                                                    .sec_biometrics_dialog_remove_btn_color));
                                    break;
                                default:
                                    Menu menu2 = FingerprintSettingsEdit.mOptionsMenu;
                                    fingerprintSettingsEdit.getClass();
                                    ((AlertDialog) dialogInterface)
                                            .getButton(-1)
                                            .setTextColor(
                                                    fingerprintSettingsEdit.getColor(
                                                            R.color
                                                                    .sec_biometrics_dialog_remove_btn_color));
                                    break;
                            }
                        }
                    });
            mDeleteDialog.semSetAnchor(findViewById(R.id.action_bar), 1);
            mDeleteDialog.show();
        }
        return true;
    }

    @Override // com.samsung.android.settings.core.SecSettingsBaseActivity,
              // androidx.fragment.app.FragmentActivity, android.app.Activity
    public final void onPause() {
        super.onPause();
        Log.d("FpstFingerprintSettingsRename", "onPause");
        if (isChangingConfigurations()) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("reason", "cancelsession");
        setResult(0, intent);
        finish();
    }

    public final void removeFingerprint() {
        Fingerprint fingerprint = (Fingerprint) this.mItems.get(this.mSelectedIndexId);
        Log.d(
                "FpstFingerprintSettingsRename",
                "removeFingerprint : "
                        + fingerprint.getBiometricId()
                        + ", "
                        + ((Object) fingerprint.getName())
                        + ", "
                        + fingerprint.getGroupId());
        mFingerprintManager.remove(fingerprint, mUserId, this.mRemoveCallback);
    }
}
