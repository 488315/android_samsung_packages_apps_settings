package com.android.settings.localepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;

import androidx.appcompat.app.AlertDialog;

import com.android.internal.app.LocaleStore;
import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class LocaleDialogFragment extends InstrumentedDialogFragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public AlertDialog mAlertDialog;
    public final LocaleDialogFragment$$ExternalSyntheticLambda1 mBackCallback =
            new LocaleDialogFragment$$ExternalSyntheticLambda1();
    public OnBackInvokedDispatcher mBackDispatcher;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class LocaleDialogController implements DialogInterface.OnClickListener {
        public final Context mContext;
        public final int mDialogType;
        public final LocaleStore.LocaleInfo mLocaleInfo;
        public final ResultReceiver mResultReceiver;

        /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
        class DialogContent {
            public String mTitle = ApnSettings.MVNO_NONE;
            public String mMessage = ApnSettings.MVNO_NONE;
            public String mPositiveButton = ApnSettings.MVNO_NONE;
            public String mNegativeButton = ApnSettings.MVNO_NONE;
        }

        public LocaleDialogController(LocaleDialogFragment localeDialogFragment) {
            this.mContext = localeDialogFragment.getContext();
            Bundle arguments = localeDialogFragment.getArguments();
            this.mDialogType = arguments.getInt("arg_dialog_type");
            this.mLocaleInfo = arguments.getSerializable("arg_target_locale");
            this.mResultReceiver = (ResultReceiver) arguments.getParcelable("arg_result_receiver");
        }

        public DialogContent getDialogContent() {
            DialogContent dialogContent = new DialogContent();
            String secFullNameNative = this.mLocaleInfo.getSecFullNameNative();
            int i = this.mDialogType;
            if (i == 0) {
                dialogContent.mTitle =
                        String.format(
                                this.mContext.getString(R.string.title_change_system_locale),
                                secFullNameNative);
                dialogContent.mMessage =
                        this.mContext.getString(R.string.desc_notice_device_locale_settings_change);
                dialogContent.mPositiveButton =
                        this.mContext.getString(
                                R.string.button_label_confirmation_of_system_locale_change);
                dialogContent.mNegativeButton = this.mContext.getString(R.string.cancel);
            } else if (i == 1) {
                dialogContent.mTitle =
                        String.format(
                                this.mContext.getString(R.string.title_unavailable_locale),
                                secFullNameNative);
                dialogContent.mMessage = this.mContext.getString(R.string.desc_unavailable_locale);
                dialogContent.mPositiveButton = this.mContext.getString(R.string.okay);
            } else if (i == 3) {
                dialogContent.mTitle =
                        String.format(
                                this.mContext.getString(R.string.title_system_locale_addition),
                                secFullNameNative);
                dialogContent.mMessage =
                        this.mContext.getString(R.string.desc_system_locale_addition);
                dialogContent.mPositiveButton = this.mContext.getString(R.string.add);
                dialogContent.mNegativeButton = this.mContext.getString(R.string.cancel);
            }
            return dialogContent;
        }

        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            if (this.mResultReceiver == null || this.mDialogType != 0) {
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putInt("arg_dialog_type", 0);
            if (i == -1) {
                this.mResultReceiver.send(-1, bundle);
            } else if (i == -2) {
                this.mResultReceiver.send(0, bundle);
            }
        }
    }

    public OnBackInvokedCallback getBackInvokedCallback() {
        return this.mBackCallback;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return getArguments().getInt("arg_dialog_type") != 1 ? 2022 : 2023;
    }

    public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
        OnBackInvokedDispatcher onBackInvokedDispatcher = this.mBackDispatcher;
        return onBackInvokedDispatcher != null
                ? onBackInvokedDispatcher
                : this.mAlertDialog.getOnBackInvokedDispatcher();
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        LocaleDialogController localeDialogController = new LocaleDialogController(this);
        LocaleDialogController.DialogContent dialogContent =
                localeDialogController.getDialogContent();
        ViewGroup viewGroup =
                (ViewGroup)
                        LayoutInflater.from(getContext())
                                .inflate(R.layout.locale_dialog, (ViewGroup) null);
        String str = dialogContent.mTitle;
        TextView textView = (TextView) viewGroup.findViewById(R.id.dialog_title);
        if (textView != null) {
            textView.setText(str);
        }
        String str2 = dialogContent.mMessage;
        TextView textView2 = (TextView) viewGroup.findViewById(R.id.dialog_msg);
        if (textView2 != null) {
            textView2.setText(str2);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(viewGroup);
        if (!dialogContent.mPositiveButton.isEmpty()) {
            builder.setPositiveButton(dialogContent.mPositiveButton, localeDialogController);
        }
        if (!dialogContent.mNegativeButton.isEmpty()) {
            builder.setNegativeButton(dialogContent.mNegativeButton, localeDialogController);
        }
        this.mAlertDialog = builder.create();
        getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mBackCallback);
        this.mAlertDialog.setCanceledOnTouchOutside(false);
        this.mAlertDialog.setOnDismissListener(
                new DialogInterface
                        .OnDismissListener() { // from class:
                                               // com.android.settings.localepicker.LocaleDialogFragment$$ExternalSyntheticLambda0
                    @Override // android.content.DialogInterface.OnDismissListener
                    public final void onDismiss(DialogInterface dialogInterface) {
                        LocaleDialogFragment localeDialogFragment = LocaleDialogFragment.this;
                        localeDialogFragment
                                .mAlertDialog
                                .getOnBackInvokedDispatcher()
                                .unregisterOnBackInvokedCallback(
                                        localeDialogFragment.mBackCallback);
                    }
                });
        return this.mAlertDialog;
    }

    public void setBackDispatcher(OnBackInvokedDispatcher onBackInvokedDispatcher) {
        this.mBackDispatcher = onBackInvokedDispatcher;
    }
}
