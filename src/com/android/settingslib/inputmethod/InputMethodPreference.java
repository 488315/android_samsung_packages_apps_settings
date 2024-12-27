package com.android.settingslib.inputmethod;

import android.R;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.ImageView;

import androidx.preference.Preference;
import androidx.preference.Preference$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceViewHolder;

import com.android.internal.annotations.VisibleForTesting;
import com.android.settings.inputmethod.AvailableVirtualKeyboardFragment;
import com.android.settingslib.PrimarySwitchPreference;
import com.android.settingslib.RestrictedLockUtilsInternal;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class InputMethodPreference extends PrimarySwitchPreference
        implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AlertDialog mDialog;
    public final boolean mHasPriorityInSorting;
    public final InputMethodInfo mImi;
    public final InputMethodSettingValuesWrapper mInputMethodSettingValues;
    public final boolean mIsAllowedByOrganization;
    public final OnSavePreferenceListener mOnSaveListener;
    public final int mUserId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnSavePreferenceListener {}

    @VisibleForTesting
    public InputMethodPreference(
            Context context,
            InputMethodInfo inputMethodInfo,
            CharSequence charSequence,
            boolean z,
            OnSavePreferenceListener onSavePreferenceListener,
            int i) {
        super(context);
        this.mDialog = null;
        setPersistent();
        this.mImi = inputMethodInfo;
        this.mIsAllowedByOrganization = z;
        this.mOnSaveListener = onSavePreferenceListener;
        setKey(inputMethodInfo.getId());
        setTitle(charSequence);
        String settingsActivity = inputMethodInfo.getSettingsActivity();
        if (TextUtils.isEmpty(settingsActivity)) {
            setIntent(null);
        } else {
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setClassName(inputMethodInfo.getPackageName(), settingsActivity);
            setIntent(intent);
        }
        boolean z2 = false;
        this.mInputMethodSettingValues =
                InputMethodSettingValuesWrapper.getInstance(
                        i != UserHandle.myUserId()
                                ? getContext().createContextAsUser(UserHandle.of(i), 0)
                                : context);
        this.mUserId = i;
        if (inputMethodInfo.isSystem()
                && InputMethodAndSubtypeUtil.isValidNonAuxAsciiCapableIme(inputMethodInfo)) {
            z2 = true;
        }
        this.mHasPriorityInSorting = z2;
        setOnPreferenceClickListener(this);
        setOnPreferenceChangeListener(this);
    }

    public final boolean isTv$1() {
        return (getContext().getResources().getConfiguration().uiMode & 15) == 4;
    }

    @Override // com.android.settingslib.PrimarySwitchPreference,
              // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        final CompoundButton compoundButton = this.mSwitch;
        if (compoundButton != null) {
            compoundButton.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda5
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            InputMethodPreference inputMethodPreference =
                                    InputMethodPreference.this;
                            CompoundButton compoundButton2 = compoundButton;
                            int i = InputMethodPreference.$r8$clinit;
                            inputMethodPreference.getClass();
                            if (compoundButton2.isEnabled()) {
                                boolean z = !inputMethodPreference.isChecked();
                                compoundButton2.setChecked(inputMethodPreference.isChecked());
                                inputMethodPreference.callChangeListener(Boolean.valueOf(z));
                            }
                        }
                    });
        }
        ImageView imageView = (ImageView) preferenceViewHolder.itemView.findViewById(R.id.icon);
        int dimensionPixelSize =
                getContext()
                        .getResources()
                        .getDimensionPixelSize(
                                com.android.settings.R.dimen.secondary_app_icon_size);
        if (imageView == null || dimensionPixelSize <= 0) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = dimensionPixelSize;
        layoutParams.width = dimensionPixelSize;
        imageView.setLayoutParams(layoutParams);
    }

    @Override // androidx.preference.Preference.OnPreferenceChangeListener
    public final boolean onPreferenceChange(Preference preference, Object obj) {
        if (isChecked()) {
            setCheckedInternal$1(false);
            return false;
        }
        if (!this.mImi.isSystem()) {
            AlertDialog alertDialog = this.mDialog;
            if (alertDialog != null && alertDialog.isShowing()) {
                this.mDialog.dismiss();
            }
            Context context = getContext();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setCancelable(true);
            builder.setTitle(R.string.dialog_alert_title);
            builder.setMessage(
                    context.getString(
                            com.android.settings.R.string.ime_security_warning,
                            this.mImi
                                    .getServiceInfo()
                                    .applicationInfo
                                    .loadLabel(context.getPackageManager())));
            builder.setPositiveButton(
                    R.string.ok, new InputMethodPreference$$ExternalSyntheticLambda0(this, 2));
            builder.setNegativeButton(
                    R.string.cancel, new InputMethodPreference$$ExternalSyntheticLambda0(this, 3));
            builder.setOnCancelListener(
                    new DialogInterface
                            .OnCancelListener() { // from class:
                                                  // com.android.settingslib.inputmethod.InputMethodPreference$$ExternalSyntheticLambda4
                        @Override // android.content.DialogInterface.OnCancelListener
                        public final void onCancel(DialogInterface dialogInterface) {
                            InputMethodPreference inputMethodPreference =
                                    InputMethodPreference.this;
                            int i = InputMethodPreference.$r8$clinit;
                            inputMethodPreference.setCheckedInternal$1(false);
                        }
                    });
            AlertDialog create = builder.create();
            this.mDialog = create;
            create.show();
        } else if (this.mImi.getServiceInfo().directBootAware || isTv$1()) {
            setCheckedInternal$1(true);
        } else if (!isTv$1()) {
            showDirectBootWarnDialog$1();
        }
        return false;
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        Context context = getContext();
        try {
            Intent intent = getIntent();
            if (intent != null) {
                context.startActivityAsUser(intent, UserHandle.of(this.mUserId));
            }
        } catch (ActivityNotFoundException e) {
            Log.d("InputMethodPreference", "IME's Settings Activity Not Found", e);
            Preference$$ExternalSyntheticOutline0.m(
                    context,
                    com.android.settings.R.string.failed_to_open_app_settings_toast,
                    new Object[] {this.mImi.loadLabel(context.getPackageManager())},
                    context,
                    1);
        }
        return true;
    }

    public final void setCheckedInternal$1(boolean z) {
        setChecked(z);
        ((AvailableVirtualKeyboardFragment) this.mOnSaveListener).onSaveInputMethodPreference();
        notifyChanged();
    }

    public final void showDirectBootWarnDialog$1() {
        AlertDialog alertDialog = this.mDialog;
        if (alertDialog != null && alertDialog.isShowing()) {
            this.mDialog.dismiss();
        }
        Context context = getContext();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setMessage(
                context.getText(com.android.settings.R.string.direct_boot_unaware_dialog_message));
        builder.setPositiveButton(
                R.string.ok, new InputMethodPreference$$ExternalSyntheticLambda0(this, 0));
        builder.setNegativeButton(
                R.string.cancel, new InputMethodPreference$$ExternalSyntheticLambda0(this, 1));
        AlertDialog create = builder.create();
        this.mDialog = create;
        create.show();
    }

    public final void updatePreferenceViews() {
        if (this.mInputMethodSettingValues.isAlwaysCheckedIme(this.mImi)) {
            setDisabledByAdmin(null);
            setSwitchEnabled(false);
        } else if (this.mIsAllowedByOrganization) {
            setEnabled(true);
            setSwitchEnabled(true);
        } else {
            setDisabledByAdmin(
                    RestrictedLockUtilsInternal.checkIfInputMethodDisallowed(
                            getContext(), this.mUserId, this.mImi.getPackageName()));
        }
        setChecked(this.mInputMethodSettingValues.isEnabledImi(this.mImi));
        if (this.mHelper.mDisabledByAdmin) {
            return;
        }
        setSummary(
                InputMethodAndSubtypeUtil.getSubtypeLocaleNameListAsSentence(
                        ((InputMethodManager) getContext().getSystemService("input_method"))
                                .getEnabledInputMethodSubtypeList(this.mImi, true),
                        getContext(),
                        this.mImi));
    }
}
