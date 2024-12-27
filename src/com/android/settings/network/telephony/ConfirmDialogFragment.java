package com.android.settings.network.telephony;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.MainClearConfirm$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.Utils$$ExternalSyntheticOutline0;

import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ConfirmDialogFragment extends BaseDialogFragment
        implements DialogInterface.OnClickListener {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ButtonArrayAdapter extends ArrayAdapter {
        public final List mList;

        public ButtonArrayAdapter(Context context, List list) {
            super(
                    context,
                    R.layout.sim_confirm_dialog_item_multiple_enabled_profiles_supported,
                    list);
            this.mList = list;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(final int i, View view, ViewGroup viewGroup) {
            View view2 = super.getView(i, view, viewGroup);
            view2.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.network.telephony.ConfirmDialogFragment$ButtonArrayAdapter$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view3) {
                            ConfirmDialogFragment.ButtonArrayAdapter buttonArrayAdapter =
                                    ConfirmDialogFragment.ButtonArrayAdapter.this;
                            int i2 = i;
                            buttonArrayAdapter.getClass();
                            Log.i("ConfirmDialogFragment", "list onClick =" + i2);
                            Utils$$ExternalSyntheticOutline0.m(
                                    new StringBuilder("list item ="),
                                    (String) buttonArrayAdapter.mList.get(i2),
                                    "ConfirmDialogFragment");
                            if (i2 == buttonArrayAdapter.mList.size() - 1) {
                                ConfirmDialogFragment.this.informCaller(-1, false);
                            } else {
                                ConfirmDialogFragment.this.informCaller(i2, true);
                            }
                        }
                    });
            return view2;
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnConfirmListener {
        void onConfirm(int i, int i2, boolean z);
    }

    public static void show(
            FragmentActivity fragmentActivity,
            int i,
            String str,
            String str2,
            String str3,
            String str4) {
        ConfirmDialogFragment confirmDialogFragment = new ConfirmDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(UniversalCredentialUtil.AGENT_TITLE, str);
        bundle.putCharSequence("msg", str2);
        bundle.putString("pos_button_string", str3);
        bundle.putString("neg_button_string", str4);
        BaseDialogFragment.setListener(fragmentActivity, OnConfirmListener.class, i, bundle);
        confirmDialogFragment.setArguments(bundle);
        confirmDialogFragment.show(
                fragmentActivity.getSupportFragmentManager(), "ConfirmDialogFragment");
    }

    public final void informCaller(int i, boolean z) {
        try {
            OnConfirmListener onConfirmListener =
                    (OnConfirmListener) getListener(OnConfirmListener.class);
            if (onConfirmListener == null) {
                return;
            }
            onConfirmListener.onConfirm(getArguments().getInt("in_caller_tag"), i, z);
        } catch (IllegalArgumentException e) {
            Log.e("ConfirmDialogFragment", "Do nothing and return.", e);
        }
    }

    @Override // androidx.fragment.app.DialogFragment,
              // android.content.DialogInterface.OnCancelListener
    public final void onCancel(DialogInterface dialogInterface) {
        informCaller(-1, false);
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        MainClearConfirm$$ExternalSyntheticOutline0.m(
                i, "dialog onClick =", "ConfirmDialogFragment");
        informCaller(-1, i == -1);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        String string = getArguments().getString(UniversalCredentialUtil.AGENT_TITLE);
        String string2 = getArguments().getString("msg");
        String string3 = getArguments().getString("pos_button_string");
        String string4 = getArguments().getString("neg_button_string");
        ArrayList<String> stringArrayList = getArguments().getStringArrayList("list");
        Log.i("ConfirmDialogFragment", "Showing dialog with title =" + string);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setPositiveButton(string3, this);
        builder.setNegativeButton(string4, this);
        View inflate =
                LayoutInflater.from(getContext())
                        .inflate(
                                R.layout.sim_confirm_dialog_multiple_enabled_profiles_supported,
                                (ViewGroup) null);
        AlertController.AlertParams alertParams = builder.P;
        if (stringArrayList == null || stringArrayList.isEmpty() || inflate == null) {
            if (!TextUtils.isEmpty(string)) {
                alertParams.mTitle = string;
            }
            if (!TextUtils.isEmpty(string2)) {
                alertParams.mMessage = string2;
            }
        } else {
            Log.i("ConfirmDialogFragment", "list =" + stringArrayList.toString());
            if (!TextUtils.isEmpty(string)) {
                TextView textView =
                        (TextView)
                                LayoutInflater.from(getContext())
                                        .inflate(
                                                R.layout
                                                        .sim_confirm_dialog_title_multiple_enabled_profiles_supported,
                                                (ViewGroup) null)
                                        .findViewById(R.id.title);
                textView.setText(string);
                alertParams.mCustomTitleView = textView;
            }
            TextView textView2 = (TextView) inflate.findViewById(R.id.msg);
            if (!TextUtils.isEmpty(string2) && textView2 != null) {
                textView2.setText(string2);
                textView2.setVisibility(0);
            }
            ListView listView = (ListView) inflate.findViewById(R.id.carrier_list);
            if (listView != null) {
                listView.setVisibility(0);
                listView.setAdapter(
                        (ListAdapter) new ButtonArrayAdapter(getContext(), stringArrayList));
            }
            LinearLayout linearLayout =
                    (LinearLayout) inflate.findViewById(R.id.info_outline_layout);
            if (linearLayout != null) {
                linearLayout.setVisibility(0);
            }
            builder.setView(inflate);
        }
        AlertDialog create = builder.create();
        create.setCanceledOnTouchOutside(false);
        return create;
    }
}
