package com.samsung.android.settings.lockscreen;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.UserHandle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;

import androidx.fragment.app.DialogFragment;

import com.android.internal.widget.LockPatternUtils;
import com.android.settings.R;

import com.google.android.material.textfield.TextInputLayout;
import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ContactInformation extends DialogFragment implements DialogInterface.OnClickListener {
    public static final int MY_USER_ID = UserHandle.myUserId();
    public AlertDialog mAlertDialog;
    public Context mContext;
    public LockPatternUtils mLockPatternUtils;
    public String mOwnerInfo = null;
    public EditText mOwnerInfoEditText;
    public ScrollView mScrollView;
    public TextInputLayout mTextInputLayout;
    public View mView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CustomLengthFilter extends InputFilter.LengthFilter {
        public final int mMaxLength;

        public CustomLengthFilter() {
            super(65);
            this.mMaxLength = 65;
        }

        @Override // android.text.InputFilter.LengthFilter, android.text.InputFilter
        public final CharSequence filter(
                CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            char charAt;
            CharSequence filter = super.filter(charSequence, i, i2, spanned, i3, i4);
            if (filter != null) {
                ContactInformation.this.mTextInputLayout.setError(
                        String.format(
                                ContactInformation.this
                                        .getResources()
                                        .getString(
                                                R.string
                                                        .contact_info_settings_max_character_reached),
                                Integer.valueOf(this.mMaxLength)));
                ContactInformation.this.mScrollView.fullScroll(130);
                if (filter.length() > 0
                        && ((charAt = charSequence.charAt(filter.length() - 1)) == 9770
                                || charAt == 10013)) {
                    return ApnSettings.MVNO_NONE;
                }
            } else {
                ContactInformation.this.mTextInputLayout.setErrorEnabled(false);
            }
            return filter;
        }
    }

    public final void hideSoftInput$1() {
        ((InputMethodManager) getActivity().getSystemService("input_method"))
                .hideSoftInputFromWindow(this.mOwnerInfoEditText.getWindowToken(), 0);
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -2) {
            hideSoftInput$1();
            LoggingHelper.insertEventLogging(4400, 4420);
            return;
        }
        if (i != -1) {
            return;
        }
        String editable = this.mOwnerInfoEditText.getText().toString();
        LockPatternUtils lockPatternUtils = this.mLockPatternUtils;
        int i2 = MY_USER_ID;
        lockPatternUtils.setOwnerInfo(editable, i2);
        this.mLockPatternUtils.setOwnerInfoEnabled(!editable.isEmpty(), i2);
        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(200, -1, new Intent());
        }
        hideSoftInput$1();
        LoggingHelper.insertEventLogging(4400, 4450);
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        showInputMethod$1();
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = getContext();
        this.mLockPatternUtils = new LockPatternUtils(getActivity());
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        int i = getArguments().getInt(UniversalCredentialUtil.AGENT_TITLE);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View inflate =
                getLayoutInflater()
                        .inflate(R.layout.sec_contact_information_popup, (ViewGroup) null);
        this.mView = inflate;
        this.mScrollView = (ScrollView) inflate.findViewById(R.id.ownerinfo_scrollview);
        String ownerInfo = this.mLockPatternUtils.getOwnerInfo(MY_USER_ID);
        this.mOwnerInfo = ownerInfo;
        if (ownerInfo != null && !TextUtils.isEmpty(ownerInfo) && this.mOwnerInfo.length() > 65) {
            this.mOwnerInfo = this.mOwnerInfo.substring(0, 65);
        }
        this.mTextInputLayout =
                (TextInputLayout) this.mView.findViewById(R.id.contact_info_input_layout);
        EditText editText = (EditText) this.mView.findViewById(R.id.owner_info_edit_text_popup);
        this.mOwnerInfoEditText = editText;
        editText.setText(this.mOwnerInfo);
        this.mOwnerInfoEditText.setPrivateImeOptions("disableImage=true;");
        if (!TextUtils.isEmpty(this.mOwnerInfo)) {
            this.mOwnerInfoEditText.setSelection(this.mOwnerInfo.length());
        }
        this.mOwnerInfoEditText.setFilters(new InputFilter[] {new CustomLengthFilter()});
        AlertDialog create =
                builder.setView(this.mView)
                        .setTitle(i)
                        .setPositiveButton(R.string.done_button, this)
                        .setNegativeButton(R.string.cancel, this)
                        .create();
        this.mAlertDialog = create;
        create.setOnShowListener(
                new DialogInterface
                        .OnShowListener() { // from class:
                                            // com.samsung.android.settings.lockscreen.ContactInformation$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        if (TextUtils.isEmpty(
                                ContactInformation.this.mOwnerInfoEditText.getText())) {
                            ((AlertDialog) dialogInterface).getButton(-1).setEnabled(false);
                        }
                    }
                });
        return this.mAlertDialog;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        hideSoftInput$1();
        super.onDestroyView();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        showInputMethod$1();
        this.mOwnerInfoEditText.addTextChangedListener(
                new TextWatcher() { // from class:
                                    // com.samsung.android.settings.lockscreen.ContactInformation.1
                    @Override // android.text.TextWatcher
                    public final void beforeTextChanged(
                            CharSequence charSequence, int i, int i2, int i3) {
                        if (i2 > 0) {
                            ContactInformation.this.mAlertDialog.getButton(-1).setEnabled(true);
                        }
                    }

                    @Override // android.text.TextWatcher
                    public final void onTextChanged(
                            CharSequence charSequence, int i, int i2, int i3) {
                        ContactInformation.this.mAlertDialog.getButton(-1).setEnabled(true);
                    }

                    @Override // android.text.TextWatcher
                    public final void afterTextChanged(Editable editable) {}
                });
        LoggingHelper.insertFlowLogging(4400);
        LoggingHelper.insertEventLogging(4400, 4400);
    }

    public final void showInputMethod$1() {
        if (this.mOwnerInfoEditText.isEnabled()) {
            this.mOwnerInfoEditText.setFocusableInTouchMode(true);
            this.mOwnerInfoEditText.requestFocus();
            this.mOwnerInfoEditText.postDelayed(
                    new ContactInformation$$ExternalSyntheticLambda1(this, 0), 400L);
        } else {
            Dialog dialog = this.mDialog;
            if (dialog != null && dialog.getWindow() != null) {
                this.mDialog.getWindow().setSoftInputMode(48);
            }
            ((InputMethodManager) getActivity().getSystemService("input_method"))
                    .hideSoftInputFromWindow(this.mOwnerInfoEditText.getWindowToken(), 2);
        }
    }
}
