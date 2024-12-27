package com.samsung.android.settings.wifi.develop.diagnosis.accesspoints;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class NetworkDiagnosisInformationAdapter extends RecyclerView.Adapter {
    public final String[][] infoInitialList = {
        new String[] {"Current connection state", ApnSettings.MVNO_NONE},
        new String[] {
            "Target domain for DNS query", "Click to enter target domain to test DNS request"
        },
        new String[] {"Validation check HTTP url", "Click to enter URL"},
        new String[] {"Validation check HTTPS url", "Click to enter URL"}
    };
    public final String[] infoList = {
        ApnSettings.MVNO_NONE,
        "Click to enter target domain to test DNS request",
        "Click to enter URL",
        "Click to enter URL"
    };
    public final Context mContext;

    public NetworkDiagnosisInformationAdapter(Context context) {
        this.mContext = context;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return 4;
    }

    public final boolean isNotSet(int i) {
        return this.infoInitialList[i][1].equals(this.infoList[i]);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        NetworkDiagnosisItem networkDiagnosisItem = ((NetworkDiagnosisInfoHolder) viewHolder).mCell;
        networkDiagnosisItem.mTitle.setTextColor(
                networkDiagnosisItem.mContext.getColor(R.color.wifi_preference_title_color));
        String[] strArr = this.infoList;
        boolean isEmpty = TextUtils.isEmpty(strArr[i]);
        String[][] strArr2 = this.infoInitialList;
        if (isEmpty) {
            strArr[i] = strArr2[i][1];
        }
        String str = strArr2[i][0];
        String str2 = strArr[i];
        networkDiagnosisItem.mTitle.setText(str);
        if (TextUtils.isEmpty(str2)) {
            networkDiagnosisItem.mSummary.setVisibility(8);
        } else {
            networkDiagnosisItem.mSummary.setVisibility(0);
            networkDiagnosisItem.mSummary.setText(str2);
        }
        if (i > 0) {
            networkDiagnosisItem.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.samsung.android.settings.wifi.develop.diagnosis.accesspoints.NetworkDiagnosisInformationAdapter$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            final NetworkDiagnosisInformationAdapter
                                    networkDiagnosisInformationAdapter =
                                            NetworkDiagnosisInformationAdapter.this;
                            final int i2 = i;
                            boolean z =
                                    (networkDiagnosisInformationAdapter
                                                            .mContext
                                                            .getResources()
                                                            .getConfiguration()
                                                            .uiMode
                                                    & 48)
                                            == 32;
                            View inflate =
                                    View.inflate(
                                            networkDiagnosisInformationAdapter.mContext,
                                            R.layout.sec_wifi_developer_network_diagnosis_dialog,
                                            null);
                            final EditText editText =
                                    (EditText) inflate.findViewById(R.id.input_box);
                            editText.setText("example.com");
                            AlertDialog.Builder builder =
                                    new AlertDialog.Builder(
                                            networkDiagnosisInformationAdapter.mContext, z ? 4 : 5);
                            builder.setTitle(
                                    networkDiagnosisInformationAdapter.infoInitialList[i2][0]);
                            builder.setView(inflate);
                            builder.setPositiveButton(
                                    android.R.string.ok,
                                    new DialogInterface
                                            .OnClickListener() { // from class:
                                                                 // com.samsung.android.settings.wifi.develop.diagnosis.accesspoints.NetworkDiagnosisInformationAdapter$$ExternalSyntheticLambda1
                                        @Override // android.content.DialogInterface.OnClickListener
                                        public final void onClick(
                                                DialogInterface dialogInterface, int i3) {
                                            NetworkDiagnosisInformationAdapter
                                                    networkDiagnosisInformationAdapter2 =
                                                            NetworkDiagnosisInformationAdapter.this;
                                            int i4 = i2;
                                            EditText editText2 = editText;
                                            networkDiagnosisInformationAdapter2.getClass();
                                            dialogInterface.dismiss();
                                            String editable = editText2.getText().toString();
                                            String[] strArr3 =
                                                    networkDiagnosisInformationAdapter2.infoList;
                                            strArr3[i4] = editable;
                                            if ("example.com".equals(editable)) {
                                                strArr3[i4] =
                                                        networkDiagnosisInformationAdapter2
                                                                .infoInitialList[i4][1];
                                            }
                                            networkDiagnosisInformationAdapter2
                                                    .notifyDataSetChanged();
                                        }
                                    });
                            builder.setNegativeButton(
                                    android.R.string.cancel,
                                    new NetworkDiagnosisInformationAdapter$$ExternalSyntheticLambda2());
                            AlertDialog create = builder.create();
                            create.setCanceledOnTouchOutside(false);
                            create.show();
                        }
                    });
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        NetworkDiagnosisItem networkDiagnosisItem =
                (NetworkDiagnosisItem)
                        LayoutInflater.from(this.mContext)
                                .inflate(
                                        R.layout.sec_wifi_developer_network_diagnosis_cell,
                                        viewGroup,
                                        false);
        NetworkDiagnosisInfoHolder networkDiagnosisInfoHolder =
                new NetworkDiagnosisInfoHolder(networkDiagnosisItem);
        networkDiagnosisInfoHolder.mCell = networkDiagnosisItem;
        return networkDiagnosisInfoHolder;
    }
}
