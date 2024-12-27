package com.samsung.android.settings.deviceinfo.aboutphone;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.core.PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0;
import androidx.fragment.app.DialogFragment;

import com.android.settings.R;
import com.android.settings.Utils;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import com.samsung.android.settings.Rune;
import com.samsung.android.settings.deviceinfo.SecDeviceInfoUtils;
import com.samsung.android.settings.general.BaseResetSettingsData;
import com.samsung.android.settings.general.OnResetSettingsDataListener;
import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class DeviceNameEditor extends DialogFragment implements DialogInterface.OnClickListener {
    public static final OnResetSettingsDataListener RESET_SETTINGS_DATA = new AnonymousClass8();
    public ConfirmationDialogFragmentListener listener;
    public AlertDialog mAlertDialog;
    public View mAnchorView;
    public ScrollView mDeviceNameScrollView;
    public EditText mEditText;
    public TextView mErrorTextView;
    public View mView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.deviceinfo.aboutphone.DeviceNameEditor$5, reason: invalid class name */
    public final class AnonymousClass5 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ Object this$0;

        public /* synthetic */ AnonymousClass5(int i, Object obj) {
            this.$r8$classId = i;
            this.this$0 = obj;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    try {
                        ((InputMethodManager)
                                        ((DeviceNameEditor) this.this$0)
                                                .mEditText
                                                .getContext()
                                                .getSystemService("input_method"))
                                .showSoftInput(((DeviceNameEditor) this.this$0).mEditText, 1);
                        break;
                    } catch (NullPointerException unused) {
                        Log.e("DeviceInfoHeaderDeviceName", "Caught showInputMethod Exception");
                        return;
                    }
                case 1:
                    try {
                        ((InputMethodManager)
                                        ((DeviceNameEditor) this.this$0)
                                                .getActivity()
                                                .getSystemService("input_method"))
                                .hideSoftInputFromWindow(
                                        ((DeviceNameEditor) this.this$0).mEditText.getWindowToken(),
                                        2);
                        break;
                    } catch (NullPointerException unused2) {
                        Log.e("DeviceInfoHeaderDeviceName", "Caught Exception showInputMethod");
                        return;
                    }
                default:
                    DeviceNameEditor.this.mDeviceNameScrollView.fullScroll(130);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.deviceinfo.aboutphone.DeviceNameEditor$8, reason: invalid class name */
    public final class AnonymousClass8 extends BaseResetSettingsData {
        @Override // com.samsung.android.settings.general.OnResetSettingsDataListener
        public final void resetSettings(Context context) {
            if (Rune.isShopDemo(context)) {
                Log.d(
                        "DeviceInfoHeaderDeviceName",
                        "set default device name : Retail mode Demo Reset");
                Settings.Global.putString(
                        context.getContentResolver(),
                        "device_name",
                        SecDeviceInfoUtils.getDefaultDeviceName(context));
                context.sendBroadcast(new Intent("com.android.settings.DEVICE_NAME_CHANGED"));
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ConfirmationDialogFragmentListener {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CustomLengthFilter extends InputFilter.LengthFilter {
        public CustomLengthFilter() {
            super(32);
        }

        @Override // android.text.InputFilter.LengthFilter, android.text.InputFilter
        public final CharSequence filter(
                CharSequence charSequence, int i, int i2, Spanned spanned, int i3, int i4) {
            char charAt;
            CharSequence filter = super.filter(charSequence, i, i2, spanned, i3, i4);
            if (filter != null) {
                DeviceNameEditor.this.mErrorTextView.setVisibility(0);
                DeviceNameEditor.this.mDeviceNameScrollView.post(new AnonymousClass5(2, this));
                if (filter.length() > 0
                        && ((charAt = charSequence.charAt(filter.length() - 1)) == 9770
                                || charAt == 10013)) {
                    return ApnSettings.MVNO_NONE;
                }
            } else {
                DeviceNameEditor.this.mErrorTextView.setVisibility(8);
            }
            return filter;
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i != -2) {
            if (i != -1) {
                return;
            }
            LoggingHelper.insertEventLogging(8107, 0);
            saveDeviceName();
            return;
        }
        LoggingHelper.insertEventLogging(8107, 1);
        ConfirmationDialogFragmentListener confirmationDialogFragmentListener = this.listener;
        if (confirmationDialogFragmentListener != null) {
            ((DeviceInfoHeaderPreferenceController) confirmationDialogFragmentListener)
                            .mDeviceNameEditor =
                    null;
        }
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        LoggingHelper.insertEventLogging(40, 8107);
        getActivity().setTheme(2132084353);
        this.mView =
                getActivity()
                        .getLayoutInflater()
                        .inflate(R.layout.sec_device_name_popup, (ViewGroup) null);
        String string =
                Settings.Global.getString(getActivity().getContentResolver(), "device_name");
        this.mDeviceNameScrollView =
                (ScrollView) this.mView.findViewById(R.id.device_name_scrollview);
        TextView textView = (TextView) this.mView.findViewById(R.id.error_textview);
        this.mErrorTextView = textView;
        textView.setVisibility(8);
        EditText editText = (EditText) this.mView.findViewById(R.id.device_name_edit);
        this.mEditText = editText;
        editText.setText(string);
        EditText editText2 = this.mEditText;
        StringBuilder m =
                PriorityGoalRow$GoalVariableAccessor$$ExternalSyntheticOutline0.m(string, "\n");
        m.append((Object) getText(R.string.device_name_popup_msg));
        editText2.setContentDescription(m.toString());
        this.mEditText.setFilters(
                new InputFilter[] {
                    new CustomLengthFilter(),
                    new Utils.EmojiInputFilter(getActivity().getBaseContext())
                });
        if (!TextUtils.isEmpty(string)) {
            try {
                this.mEditText.setSelection(string.length());
            } catch (IndexOutOfBoundsException unused) {
                Log.e("DeviceInfoHeaderDeviceName", "Caught Exception setSelection");
            }
        }
        this.mEditText.setEnabled(true);
        this.mEditText.selectAll();
        this.mEditText.setOnEditorActionListener(
                new TextView
                        .OnEditorActionListener() { // from class:
                                                    // com.samsung.android.settings.deviceinfo.aboutphone.DeviceNameEditor.3
                    @Override // android.widget.TextView.OnEditorActionListener
                    public final boolean onEditorAction(
                            TextView textView2, int i, KeyEvent keyEvent) {
                        if (i != 6
                                || !DeviceNameEditor.this.mAlertDialog.getButton(-1).isEnabled()) {
                            return false;
                        }
                        DeviceNameEditor.this.saveDeviceName();
                        DeviceNameEditor.this.mAlertDialog.dismiss();
                        return false;
                    }
                });
        this.mEditText.setOnKeyListener(
                new View
                        .OnKeyListener() { // from class:
                                           // com.samsung.android.settings.deviceinfo.aboutphone.DeviceNameEditor.4
                    @Override // android.view.View.OnKeyListener
                    public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                        if (i != 66
                                || keyEvent.getAction() != 0
                                || !DeviceNameEditor.this.mAlertDialog.getButton(-1).isEnabled()) {
                            return false;
                        }
                        DeviceNameEditor.this.saveDeviceName();
                        DeviceNameEditor.this.mAlertDialog.dismiss();
                        return false;
                    }
                });
        this.mEditText.setPrivateImeOptions("disableAutoReplacement=true");
        this.mEditText.setPrivateImeOptions(
                "disableEmoticonInput=true;disableImage=true;ignoreImeInternalFlagAppWindowPortrait=true");
        Bundle inputExtras = this.mEditText.getInputExtras(true);
        if (inputExtras != null) {
            inputExtras.putInt("maxLength", 32);
        }
        showInputMethod();
        this.mView.addOnLayoutChangeListener(
                new View
                        .OnLayoutChangeListener() { // from class:
                                                    // com.samsung.android.settings.deviceinfo.aboutphone.DeviceNameEditor.1
                    @Override // android.view.View.OnLayoutChangeListener
                    public final void onLayoutChange(
                            View view,
                            int i,
                            int i2,
                            int i3,
                            int i4,
                            int i5,
                            int i6,
                            int i7,
                            int i8) {
                        AlertDialog alertDialog;
                        DeviceNameEditor deviceNameEditor = DeviceNameEditor.this;
                        if (deviceNameEditor.mAnchorView == null
                                || (alertDialog = deviceNameEditor.mAlertDialog) == null
                                || !alertDialog.isShowing()) {
                            return;
                        }
                        DeviceNameEditor deviceNameEditor2 = DeviceNameEditor.this;
                        deviceNameEditor2.mAlertDialog.semSetAnchor(deviceNameEditor2.mAnchorView);
                    }
                });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(this.mView);
        builder.setTitle(getArguments().getInt(UniversalCredentialUtil.AGENT_TITLE));
        builder.setPositiveButton(R.string.common_done, this);
        builder.setNegativeButton(R.string.cancel, this);
        AlertDialog create = builder.create();
        this.mAlertDialog = create;
        View view = this.mAnchorView;
        if (view != null) {
            create.semSetAnchor(view);
        }
        if (this.mAlertDialog.getWindow() != null) {
            this.mAlertDialog.getWindow().setSoftInputMode(21);
        }
        this.mEditText.addTextChangedListener(
                new TextWatcher() { // from class:
                                    // com.samsung.android.settings.deviceinfo.aboutphone.DeviceNameEditor.2
                    @Override // android.text.TextWatcher
                    public final synchronized void afterTextChanged(Editable editable) {}

                    @Override // android.text.TextWatcher
                    public final void onTextChanged(
                            CharSequence charSequence, int i, int i2, int i3) {
                        String charSequence2 = charSequence.toString();
                        if (ApnSettings.MVNO_NONE.equals(
                                        charSequence2
                                                .replace(" ", ApnSettings.MVNO_NONE)
                                                .replace("\n", ApnSettings.MVNO_NONE))
                                || ApnSettings.MVNO_NONE.equals(charSequence2)) {
                            DeviceNameEditor.this.mAlertDialog.getButton(-1).setEnabled(false);
                        } else {
                            DeviceNameEditor.this.mAlertDialog.getButton(-1).setEnabled(true);
                        }
                    }

                    @Override // android.text.TextWatcher
                    public final void beforeTextChanged(
                            CharSequence charSequence, int i, int i2, int i3) {}
                });
        return this.mAlertDialog;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        Log.d("DeviceInfoHeaderDeviceName", "onDestroyView");
        ((InputMethodManager) getActivity().getSystemService("input_method"))
                .hideSoftInputFromWindow(this.mEditText.getWindowToken(), 0);
        ConfirmationDialogFragmentListener confirmationDialogFragmentListener = this.listener;
        if (confirmationDialogFragmentListener != null) {
            DeviceInfoHeaderPreferenceController deviceInfoHeaderPreferenceController =
                    (DeviceInfoHeaderPreferenceController) confirmationDialogFragmentListener;
            deviceInfoHeaderPreferenceController.mDeviceNameEditor = null;
            Activity activity = deviceInfoHeaderPreferenceController.mActivity;
            if (activity != null && deviceInfoHeaderPreferenceController.mHasEditDeviceNameExtra) {
                activity.finish();
            }
        }
        super.onDestroyView();
    }

    @Override // androidx.fragment.app.Fragment
    public final void onResume() {
        super.onResume();
        showInputMethod();
        this.mEditText.setOnFocusChangeListener(
                new View
                        .OnFocusChangeListener() { // from class:
                                                   // com.samsung.android.settings.deviceinfo.aboutphone.DeviceNameEditor.7
                    @Override // android.view.View.OnFocusChangeListener
                    public final void onFocusChange(View view, boolean z) {
                        EditText editText = DeviceNameEditor.this.mEditText;
                        if (editText == null || editText.isFocused()) {
                            return;
                        }
                        InputMethodManager inputMethodManager =
                                (InputMethodManager)
                                        DeviceNameEditor.this
                                                .mEditText
                                                .getContext()
                                                .getSystemService("input_method");
                        if (inputMethodManager.isActive()) {
                            inputMethodManager.hideSoftInputFromWindow(
                                    DeviceNameEditor.this.mEditText.getWindowToken(), 0);
                        }
                    }
                });
    }

    public final void saveDeviceName() {
        ContentResolver contentResolver = getActivity().getContentResolver();
        String editable = this.mEditText.getText().toString();
        if (!TextUtils.isEmpty(editable) && !"null".equals(editable)) {
            Settings.Global.putString(contentResolver, "device_name", editable);
        }
        getActivity().sendBroadcast(new Intent("com.android.settings.DEVICE_NAME_CHANGED"));
        ConfirmationDialogFragmentListener confirmationDialogFragmentListener = this.listener;
        if (confirmationDialogFragmentListener != null) {
            DeviceInfoHeaderPreferenceController deviceInfoHeaderPreferenceController =
                    (DeviceInfoHeaderPreferenceController) confirmationDialogFragmentListener;
            deviceInfoHeaderPreferenceController.mDeviceNameView.setText(
                    Settings.Global.getString(
                            deviceInfoHeaderPreferenceController.mContext.getContentResolver(),
                            "device_name"));
            deviceInfoHeaderPreferenceController.mDeviceNameEditor = null;
        }
    }

    public final void showInputMethod() {
        if (!this.mEditText.isEnabled()) {
            this.mEditText.postDelayed(new AnonymousClass5(1, this), 1L);
        } else {
            this.mEditText.requestFocus();
            this.mEditText.postDelayed(new AnonymousClass5(0, this), 400L);
        }
    }
}
