package com.android.settings.sim;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;

import com.android.settings.R;
import com.android.settings.network.SubscriptionUtil;

import com.samsung.android.knox.net.apn.ApnSettings;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class SimListDialogFragment extends SimDialogFragment {
    public SelectSubscriptionAdapter mAdapter;
    List<SubscriptionInfo> mSubscriptions = new ArrayList();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SelectSubscriptionAdapter extends BaseAdapter {
        public Context mContext;
        public LayoutInflater mInflater;
        public List mSubscriptions;

        @Override // android.widget.Adapter
        public final int getCount() {
            return this.mSubscriptions.size();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return (SubscriptionInfo) this.mSubscriptions.get(i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            if (((SubscriptionInfo) this.mSubscriptions.get(i)) == null) {
                return -1L;
            }
            return r0.getSubscriptionId();
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                if (this.mInflater == null) {
                    this.mInflater = LayoutInflater.from(viewGroup.getContext());
                }
                view = this.mInflater.inflate(R.layout.select_account_list_item, viewGroup, false);
            }
            SubscriptionInfo subscriptionInfo = (SubscriptionInfo) this.mSubscriptions.get(i);
            TextView textView = (TextView) view.findViewById(R.id.title);
            TextView textView2 = (TextView) view.findViewById(R.id.summary);
            if (subscriptionInfo == null) {
                if (i == 0) {
                    textView.setText(R.string.sim_calls_ask_first_prefs_title);
                } else {
                    textView.setText(R.string.sim_action_cancel);
                }
                textView2.setVisibility(8);
            } else {
                textView.setText(
                        SubscriptionUtil.getUniqueSubscriptionDisplayName(
                                this.mContext, subscriptionInfo));
                String number = subscriptionInfo.getNumber();
                String number2 =
                        (TextUtils.isEmpty(number) || number.matches("[\\D0]+"))
                                ? ApnSettings.MVNO_NONE
                                : subscriptionInfo.getNumber();
                if (TextUtils.isEmpty(number2)) {
                    textView2.setVisibility(8);
                } else {
                    textView2.setVisibility(0);
                    textView2.setText(number2);
                }
            }
            return view;
        }
    }

    public static SimListDialogFragment newInstance(int i, int i2, boolean z, boolean z2) {
        SimListDialogFragment simListDialogFragment = new SimListDialogFragment();
        Bundle initArguments = SimDialogFragment.initArguments(i, i2);
        initArguments.putBoolean("include_ask_every_time", z);
        initArguments.putBoolean("show_cancel_item", z2);
        simListDialogFragment.setArguments(initArguments);
        return simListDialogFragment;
    }

    public List getCurrentSubscriptions() {
        return ((SubscriptionManager) getContext().getSystemService(SubscriptionManager.class))
                .createForAllUserProfiles()
                .getActiveSubscriptionInfoList();
    }

    public int getMetricsCategory() {
        return 1707;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        TextView textView =
                (TextView)
                        LayoutInflater.from(getContext())
                                .inflate(
                                        R.layout
                                                .sim_confirm_dialog_title_multiple_enabled_profiles_supported,
                                        (ViewGroup) null)
                                .findViewById(R.id.title);
        textView.setText(getContext().getString(getArguments().getInt("title_id")));
        AlertController.AlertParams alertParams = builder.P;
        alertParams.mCustomTitleView = textView;
        Context context = alertParams.mContext;
        List<SubscriptionInfo> list = this.mSubscriptions;
        SelectSubscriptionAdapter selectSubscriptionAdapter = new SelectSubscriptionAdapter();
        selectSubscriptionAdapter.mSubscriptions = list;
        selectSubscriptionAdapter.mContext = context;
        this.mAdapter = selectSubscriptionAdapter;
        AlertDialog create = builder.create();
        View inflate =
                LayoutInflater.from(getContext())
                        .inflate(
                                R.layout.sim_confirm_dialog_multiple_enabled_profiles_supported,
                                (ViewGroup) null);
        ListView listView =
                inflate != null ? (ListView) inflate.findViewById(R.id.carrier_list) : null;
        if (listView != null) {
            setAdapter(listView);
            listView.setVisibility(0);
            listView.setOnItemClickListener(
                    new AdapterView
                            .OnItemClickListener() { // from class:
                                                     // com.android.settings.sim.SimListDialogFragment.1
                        @Override // android.widget.AdapterView.OnItemClickListener
                        public final void onItemClick(
                                AdapterView adapterView, View view, int i, long j) {
                            SimListDialogFragment simListDialogFragment =
                                    SimListDialogFragment.this;
                            SimDialogActivity simDialogActivity =
                                    (SimDialogActivity) simListDialogFragment.getActivity();
                            if (i >= 0 && i < simListDialogFragment.mSubscriptions.size()) {
                                SubscriptionInfo subscriptionInfo =
                                        simListDialogFragment.mSubscriptions.get(i);
                                simDialogActivity.onSubscriptionSelected(
                                        simListDialogFragment.getDialogType(),
                                        subscriptionInfo != null
                                                ? subscriptionInfo.getSubscriptionId()
                                                : -1);
                            }
                            Log.d("SimListDialogFragment", "Start showing auto data switch dialog");
                            simDialogActivity.showEnableAutoDataSwitchDialog();
                            Dialog dialog = simListDialogFragment.mDialog;
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        }
                    });
        }
        create.setView$1(inflate);
        updateDialog();
        return create;
    }

    public void setAdapter(ListView listView) {
        listView.setAdapter((ListAdapter) this.mAdapter);
    }

    @Override // com.android.settings.sim.SimDialogFragment
    public final void updateDialog() {
        ActionBarContextView$$ExternalSyntheticOutline0.m(
                new StringBuilder("Dialog updated, dismiss status: "),
                this.mWasDismissed,
                "SimListDialogFragment");
        if (this.mWasDismissed) {
            return;
        }
        List currentSubscriptions = getCurrentSubscriptions();
        if (currentSubscriptions == null) {
            dismiss();
            return;
        }
        currentSubscriptions.removeIf(new SimListDialogFragment$$ExternalSyntheticLambda0());
        boolean z = getArguments().getBoolean("include_ask_every_time");
        boolean z2 = getArguments().getBoolean("show_cancel_item");
        if (z || z2) {
            ArrayList arrayList =
                    new ArrayList(currentSubscriptions.size() + (z ? 1 : 0) + (z2 ? 1 : 0));
            if (z) {
                arrayList.add(null);
            }
            arrayList.addAll(currentSubscriptions);
            if (z2) {
                arrayList.add(null);
            }
            currentSubscriptions = arrayList;
        }
        if (currentSubscriptions.equals(this.mSubscriptions)) {
            return;
        }
        this.mSubscriptions.clear();
        this.mSubscriptions.addAll(currentSubscriptions);
        this.mAdapter.notifyDataSetChanged();
    }
}
