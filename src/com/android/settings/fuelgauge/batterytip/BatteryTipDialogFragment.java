package com.android.settings.fuelgauge.batterytip;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.ListFormatter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.Utils;
import com.android.settings.core.InstrumentedPreferenceFragment;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;
import com.android.settings.fuelgauge.batterytip.actions.BatteryTipAction;
import com.android.settings.fuelgauge.batterytip.tips.BatteryTip;
import com.android.settings.fuelgauge.batterytip.tips.HighUsageTip;
import com.android.settings.fuelgauge.batterytip.tips.RestrictAppTip;
import com.android.settings.fuelgauge.batterytip.tips.UnrestrictAppTip;
import com.android.settingslib.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class BatteryTipDialogFragment extends InstrumentedDialogFragment
        implements DialogInterface.OnClickListener {
    BatteryTip mBatteryTip;
    int mMetricsKey;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1323;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        BatteryTipPreferenceController.BatteryTipListener batteryTipListener =
                (BatteryTipPreferenceController.BatteryTipListener) getTargetFragment();
        if (batteryTipListener == null) {
            return;
        }
        BatteryTipAction actionForBatteryTip =
                BatteryTipUtils.getActionForBatteryTip(
                        this.mBatteryTip,
                        (SettingsActivity) getActivity(),
                        (InstrumentedPreferenceFragment) getTargetFragment());
        if (actionForBatteryTip != null) {
            actionForBatteryTip.handlePositiveAction(this.mMetricsKey);
        }
        batteryTipListener.onBatteryTipHandled(this.mBatteryTip);
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        Bundle arguments = getArguments();
        Context context = getContext();
        this.mBatteryTip =
                (BatteryTip) arguments.getParcelable(BatteryTipPreferenceController.PREF_NAME);
        this.mMetricsKey = arguments.getInt("metrics_key");
        BatteryTip batteryTip = this.mBatteryTip;
        int i = batteryTip.mType;
        if (i != 1) {
            if (i == 2) {
                HighUsageTip highUsageTip = (HighUsageTip) batteryTip;
                RecyclerView recyclerView =
                        (RecyclerView)
                                LayoutInflater.from(context)
                                        .inflate(R.layout.recycler_view, (ViewGroup) null);
                recyclerView.setLayoutManager(new LinearLayoutManager(1));
                recyclerView.setAdapter(
                        new HighUsageAdapter(context, highUsageTip.getHighUsageAppList()));
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.P.mMessage =
                        getString(
                                R.string.battery_tip_dialog_message,
                                Integer.valueOf(highUsageTip.getHighUsageAppList().size()));
                builder.setView(recyclerView);
                builder.setPositiveButton(
                        android.R.string.ok, (DialogInterface.OnClickListener) null);
                return builder.create();
            }
            if (i == 6) {
                AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
                builder2.setMessage(R.string.battery_tip_dialog_summary_message);
                builder2.setPositiveButton(
                        android.R.string.ok, (DialogInterface.OnClickListener) null);
                return builder2.create();
            }
            if (i != 7) {
                throw new IllegalArgumentException("unknown type " + this.mBatteryTip.mType);
            }
            Utils.getApplicationLabel(
                    context, ((UnrestrictAppTip) batteryTip).mAppInfo.packageName);
            AlertDialog.Builder builder3 = new AlertDialog.Builder(context);
            builder3.P.mTitle = getString(R.string.battery_tip_unrestrict_app_dialog_title);
            builder3.setMessage(R.string.battery_tip_unrestrict_app_dialog_message);
            builder3.setPositiveButton(R.string.battery_tip_unrestrict_app_dialog_ok, this);
            builder3.setNegativeButton(
                    R.string.battery_tip_unrestrict_app_dialog_cancel,
                    (DialogInterface.OnClickListener) null);
            return builder3.create();
        }
        RestrictAppTip restrictAppTip = (RestrictAppTip) batteryTip;
        List list = restrictAppTip.mRestrictAppList;
        int size = list.size();
        CharSequence applicationLabel =
                Utils.getApplicationLabel(context, ((AppInfo) list.get(0)).packageName);
        AlertDialog.Builder builder4 = new AlertDialog.Builder(context);
        String icuPluralsString =
                StringUtil.getIcuPluralsString(
                        context, size, R.string.battery_tip_restrict_app_dialog_title);
        AlertController.AlertParams alertParams = builder4.P;
        alertParams.mTitle = icuPluralsString;
        builder4.setPositiveButton(R.string.battery_tip_restrict_app_dialog_ok, this);
        builder4.setNegativeButton(android.R.string.cancel, (DialogInterface.OnClickListener) null);
        if (size == 1) {
            alertParams.mMessage =
                    getString(R.string.battery_tip_restrict_app_dialog_message, applicationLabel);
        } else if (size <= 5) {
            alertParams.mMessage =
                    getString(R.string.battery_tip_restrict_apps_less_than_5_dialog_message);
            RecyclerView recyclerView2 =
                    (RecyclerView)
                            LayoutInflater.from(context)
                                    .inflate(R.layout.recycler_view, (ViewGroup) null);
            recyclerView2.setLayoutManager(new LinearLayoutManager(1));
            recyclerView2.setAdapter(new HighUsageAdapter(context, list));
            builder4.setView(recyclerView2);
        } else {
            ArrayList arrayList = new ArrayList();
            int size2 = restrictAppTip.mRestrictAppList.size();
            for (int i2 = 0; i2 < size2; i2++) {
                arrayList.add(
                        Utils.getApplicationLabel(
                                context,
                                ((AppInfo) restrictAppTip.mRestrictAppList.get(i2)).packageName));
            }
            alertParams.mMessage =
                    context.getString(
                            R.string.battery_tip_restrict_apps_more_than_5_dialog_message,
                            ListFormatter.getInstance().format(arrayList));
        }
        return builder4.create();
    }
}
