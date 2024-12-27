package com.android.settings.deviceinfo;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.os.storage.VolumeRecord;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.picker.adapter.AbsAdapter$1$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.search.actionbar.SearchMenuController;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class PrivateVolumeForget extends InstrumentedFragment {
    static final String TAG_FORGET_CONFIRM = "forget_confirm";
    public final AnonymousClass1 mConfirmListener =
            new View
                    .OnClickListener() { // from class:
                                         // com.android.settings.deviceinfo.PrivateVolumeForget.1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PrivateVolumeForget privateVolumeForget = PrivateVolumeForget.this;
                    Bundle m =
                            AbsAdapter$1$$ExternalSyntheticOutline0.m(
                                    "android.os.storage.extra.FS_UUID",
                                    privateVolumeForget.mRecord.getFsUuid());
                    ForgetConfirmFragment forgetConfirmFragment = new ForgetConfirmFragment();
                    forgetConfirmFragment.setArguments(m);
                    forgetConfirmFragment.setTargetFragment(privateVolumeForget, 0);
                    forgetConfirmFragment.show(
                            privateVolumeForget.getFragmentManager(),
                            PrivateVolumeForget.TAG_FORGET_CONFIRM);
                }
            };
    public VolumeRecord mRecord;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public static class ForgetConfirmFragment extends InstrumentedDialogFragment {
        @Override // com.android.settingslib.core.instrumentation.Instrumentable
        public final int getMetricsCategory() {
            return 559;
        }

        @Override // androidx.fragment.app.DialogFragment
        public final Dialog onCreateDialog(Bundle bundle) {
            FragmentActivity activity = getActivity();
            final StorageManager storageManager =
                    (StorageManager) activity.getSystemService(StorageManager.class);
            final String string = getArguments().getString("android.os.storage.extra.FS_UUID");
            VolumeRecord findRecordByUuid = storageManager.findRecordByUuid(string);
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            CharSequence expandTemplate =
                    TextUtils.expandTemplate(
                            getText(R.string.storage_internal_forget_confirm_title),
                            findRecordByUuid.getNickname());
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mTitle = expandTemplate;
            alertParams.mMessage =
                    TextUtils.expandTemplate(
                            getText(R.string.storage_internal_forget_confirm),
                            findRecordByUuid.getNickname());
            builder.setPositiveButton(
                    R.string.storage_menu_forget,
                    new DialogInterface
                            .OnClickListener() { // from class:
                                                 // com.android.settings.deviceinfo.PrivateVolumeForget.ForgetConfirmFragment.1
                        @Override // android.content.DialogInterface.OnClickListener
                        public final void onClick(DialogInterface dialogInterface, int i) {
                            storageManager.forgetVolume(string);
                            ForgetConfirmFragment.this.getActivity().finish();
                        }
                    });
            builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
            return builder.create();
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1980;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
        this.mLifecycle.addObserver(new SearchMenuController(this, 1980));
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        StorageManager storageManager =
                (StorageManager) getActivity().getSystemService(StorageManager.class);
        String string = getArguments().getString("android.os.storage.extra.FS_UUID");
        if (string == null) {
            getActivity().finish();
            return null;
        }
        VolumeRecord findRecordByUuid = storageManager.findRecordByUuid(string);
        this.mRecord = findRecordByUuid;
        if (findRecordByUuid == null) {
            getActivity().finish();
            return null;
        }
        View inflate = layoutInflater.inflate(R.layout.storage_internal_forget, viewGroup, false);
        TextView textView = (TextView) inflate.findViewById(R.id.body);
        Button button = (Button) inflate.findViewById(R.id.confirm);
        textView.setText(
                TextUtils.expandTemplate(
                        getText(R.string.storage_internal_forget_details),
                        this.mRecord.getNickname()));
        button.setOnClickListener(this.mConfirmListener);
        return inflate;
    }
}
