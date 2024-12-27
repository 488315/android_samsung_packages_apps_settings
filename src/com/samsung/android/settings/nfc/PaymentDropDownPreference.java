package com.samsung.android.settings.nfc;

import android.content.Context;
import android.nfc.NfcAdapter;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

import androidx.appcompat.widget.ListPopupWindow;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;
import androidx.preference.SecPreference;
import androidx.preference.SecPreferenceUtils;
import androidx.window.reflection.WindowExtensionsConstants;

import com.android.settings.R;

import com.samsung.android.settings.logging.LoggingHelper;

import java.util.HashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class PaymentDropDownPreference extends SecPreference {
    public HashMap mActiveSubscriptionList;
    public final Context mContext;
    public boolean mHasActiveEmbeddedSim;
    public SimListArrayAdapter mListAdapter;
    public final ListPopupWindow mListPopup;
    public int nfcPreferredSimId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class SimListArrayAdapter extends ArrayAdapter {
        public final Integer[] subscriptionIds;

        public SimListArrayAdapter(Context context, int i, Integer[] numArr) {
            super(context, i, numArr);
            this.subscriptionIds = numArr;
        }

        @Override // android.widget.ArrayAdapter, android.widget.BaseAdapter,
                  // android.widget.SpinnerAdapter
        public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
            return getView(i, view, viewGroup);
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            View inflate =
                    View.inflate(
                            PaymentDropDownPreference.this.mContext,
                            R.layout.sec_nfc_euicc_dropdown_list_view_row,
                            null);
            SubscriptionInfo subscriptionInfo =
                    (SubscriptionInfo)
                            PaymentDropDownPreference.this.mActiveSubscriptionList.get(
                                    this.subscriptionIds[i]);
            inflate.setTag(this.subscriptionIds[i]);
            TextView textView =
                    (TextView) inflate.findViewById(R.id.nfc_sim_list_view_radio_text_title);
            textView.setText(PaymentDropDownPreference.this.getSimName(subscriptionInfo));
            TextView textView2 = (TextView) inflate.findViewById(R.id.nfc_sim_list_spn);
            TextView textView3 = (TextView) inflate.findViewById(R.id.nfc_sim_list_own_number);
            PaymentDropDownPreference.this.getClass();
            boolean z = false;
            if (!TextUtils.isEmpty(subscriptionInfo.getDisplayName().toString())) {
                textView2.setVisibility(0);
                PaymentDropDownPreference.this.getClass();
                textView2.setText(subscriptionInfo.getDisplayName().toString());
            }
            if (!TextUtils.isEmpty(
                    ((SubscriptionManager)
                                    PaymentDropDownPreference.this.mContext.getSystemService(
                                            "telephony_subscription_service"))
                            .getPhoneNumber(subscriptionInfo.getSubscriptionId()))) {
                textView3.setVisibility(0);
                textView3.setText(
                        ((SubscriptionManager)
                                        PaymentDropDownPreference.this.mContext.getSystemService(
                                                "telephony_subscription_service"))
                                .getPhoneNumber(subscriptionInfo.getSubscriptionId()));
            }
            int i2 = PaymentDropDownPreference.this.nfcPreferredSimId;
            if (i2 != -2) {
                if (i2 == -1) {
                    z = !subscriptionInfo.isEmbedded();
                } else if (subscriptionInfo.isEmbedded()
                        && subscriptionInfo.getSubscriptionId() == i2) {
                    z = true;
                }
            }
            if (z) {
                CheckedTextView checkedTextView = (CheckedTextView) textView;
                checkedTextView.setChecked(true);
                textView2.setTextColor(checkedTextView.getTextColors());
                textView3.setTextColor(checkedTextView.getTextColors());
            }
            return inflate;
        }
    }

    public PaymentDropDownPreference(Context context) {
        this(context, null);
    }

    public final void dismissListIfShowing() {
        ListPopupWindow listPopupWindow = this.mListPopup;
        if (listPopupWindow == null || !listPopupWindow.mPopup.isShowing()) {
            return;
        }
        this.mListPopup.dismiss();
    }

    public final String getSimName(SubscriptionInfo subscriptionInfo) {
        int simSlotIndex = subscriptionInfo.getSimSlotIndex();
        return simSlotIndex != 0
                ? simSlotIndex != 1
                        ? subscriptionInfo.getDisplayName().toString()
                        : Settings.System.getString(
                                this.mContext.getContentResolver(), "select_name_2")
                : Settings.System.getString(this.mContext.getContentResolver(), "select_name_1");
    }

    public final void initSubscriptionDetails() {
        setNfcPreferredSimId();
        if (this.mActiveSubscriptionList.size() == 0) {
            Log.d(
                    "PaymentDropDownPreference",
                    "Disabling nfc payment sim menu : no active subscription");
            setEnabled(false);
            setSummary((CharSequence) null);
            return;
        }
        setEnabled(true);
        int i = this.nfcPreferredSimId;
        if ((i == -2
                        ? null
                        : (SubscriptionInfo) this.mActiveSubscriptionList.get(Integer.valueOf(i)))
                == null) {
            setSummary((CharSequence) null);
            SecPreferenceUtils.applySummaryColor(this, false);
        } else {
            int i2 = this.nfcPreferredSimId;
            setSummary(
                    getSimName(
                            i2 != -2
                                    ? (SubscriptionInfo)
                                            this.mActiveSubscriptionList.get(Integer.valueOf(i2))
                                    : null));
            SecPreferenceUtils.applySummaryColor(this, true);
        }
        Context context = this.mContext;
        SimListArrayAdapter simListArrayAdapter =
                new SimListArrayAdapter(
                        context,
                        context.getResources()
                                .getIdentifier(
                                        "@layout/sec_nfc_euicc_dropdown_list_view_row",
                                        WindowExtensionsConstants.LAYOUT_PACKAGE,
                                        this.mContext.getPackageName()),
                        (Integer[]) this.mActiveSubscriptionList.keySet().toArray(new Integer[0]));
        this.mListAdapter = simListArrayAdapter;
        this.mListPopup.setAdapter(simListArrayAdapter);
        this.mListPopup.mItemClickListener =
                new AdapterView
                        .OnItemClickListener() { // from class:
                                                 // com.samsung.android.settings.nfc.PaymentDropDownPreference$$ExternalSyntheticLambda1
                    @Override // android.widget.AdapterView.OnItemClickListener
                    public final void onItemClick(
                            AdapterView adapterView, View view, int i3, long j) {
                        PaymentDropDownPreference paymentDropDownPreference =
                                PaymentDropDownPreference.this;
                        paymentDropDownPreference.getClass();
                        Log.d("PaymentDropDownPreference", "onClick: " + view.getTag());
                        int intValue = ((Integer) view.getTag()).intValue();
                        synchronized (paymentDropDownPreference.mListAdapter) {
                            try {
                                if (intValue == paymentDropDownPreference.nfcPreferredSimId) {
                                    Log.d(
                                            "PaymentDropDownPreference",
                                            "dropping click event, selection same");
                                } else {
                                    Settings.Global.putInt(
                                            paymentDropDownPreference.mContext.getContentResolver(),
                                            "nfc_preferred_sim_index",
                                            intValue);
                                    paymentDropDownPreference.setNfcPreferredSimId();
                                    paymentDropDownPreference.mListAdapter.notify();
                                    int i4 = paymentDropDownPreference.nfcPreferredSimId;
                                    SubscriptionInfo subscriptionInfo = null;
                                    if ((i4 == -2
                                                    ? null
                                                    : (SubscriptionInfo)
                                                            paymentDropDownPreference
                                                                    .mActiveSubscriptionList.get(
                                                                    Integer.valueOf(i4)))
                                            == null) {
                                        paymentDropDownPreference.setSummary((CharSequence) null);
                                        SecPreferenceUtils.applySummaryColor(
                                                paymentDropDownPreference, false);
                                    } else {
                                        int i5 = paymentDropDownPreference.nfcPreferredSimId;
                                        if (i5 != -2) {
                                            subscriptionInfo =
                                                    (SubscriptionInfo)
                                                            paymentDropDownPreference
                                                                    .mActiveSubscriptionList.get(
                                                                    Integer.valueOf(i5));
                                        }
                                        paymentDropDownPreference.setSummary(
                                                paymentDropDownPreference.getSimName(
                                                        subscriptionInfo));
                                        SecPreferenceUtils.applySummaryColor(
                                                paymentDropDownPreference, true);
                                    }
                                }
                            } finally {
                            }
                        }
                        paymentDropDownPreference.mListPopup.dismiss();
                    }
                };
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ListPopupWindow listPopupWindow = this.mListPopup;
        if (listPopupWindow != null) {
            listPopupWindow.mDropDownAnchorView = preferenceViewHolder.itemView;
        }
    }

    public final void setNfcPreferredSimId() {
        try {
            this.nfcPreferredSimId =
                    Settings.Global.getInt(
                            this.mContext.getContentResolver(), "nfc_preferred_sim_index");
        } catch (Settings.SettingNotFoundException unused) {
            Log.e("PaymentDropDownPreference", "preferred sim setting not found!");
            this.nfcPreferredSimId = -2;
        }
    }

    public PaymentDropDownPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.nfcPreferredSimId = -2;
        this.mActiveSubscriptionList = new HashMap();
        this.mHasActiveEmbeddedSim = false;
        this.mContext = context;
        NfcAdapter.getDefaultAdapter(context);
        this.mListPopup = new ListPopupWindow(context);
        setPersistent();
        setOnPreferenceClickListener(
                new Preference
                        .OnPreferenceClickListener() { // from class:
                                                       // com.samsung.android.settings.nfc.PaymentDropDownPreference$$ExternalSyntheticLambda0
                    @Override // androidx.preference.Preference.OnPreferenceClickListener
                    public final boolean onPreferenceClick(Preference preference) {
                        PaymentDropDownPreference paymentDropDownPreference =
                                PaymentDropDownPreference.this;
                        ListPopupWindow listPopupWindow = paymentDropDownPreference.mListPopup;
                        if (listPopupWindow != null && !listPopupWindow.mPopup.isShowing()) {
                            paymentDropDownPreference.mListPopup.show();
                        }
                        LoggingHelper.insertFlowLogging(7013);
                        return true;
                    }
                });
    }
}
