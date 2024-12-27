package com.android.settings.sim.smartForwarding;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.BackStackRecord;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManagerImpl;

import com.android.settings.R;
import com.android.settingslib.core.instrumentation.Instrumentable;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class MDNHandlerFragment extends Fragment implements Instrumentable {
    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1571;
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.xml.smart_forwarding_mdn_handler, viewGroup, false);
        getActivity()
                .getActionBar()
                .setTitle(getResources().getString(R.string.smart_forwarding_input_mdn_title));
        final int i = 0;
        ((Button) inflate.findViewById(R.id.process))
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.sim.smartForwarding.MDNHandlerFragment$$ExternalSyntheticLambda0
                            public final /* synthetic */ MDNHandlerFragment f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                String str;
                                String str2;
                                int i2 = i;
                                MDNHandlerFragment mDNHandlerFragment = this.f$0;
                                switch (i2) {
                                    case 0:
                                        MDNHandlerHeaderFragment mDNHandlerHeaderFragment =
                                                (MDNHandlerHeaderFragment)
                                                        mDNHandlerFragment
                                                                .getChildFragmentManager()
                                                                .findFragmentById(
                                                                        R.id.fragment_settings);
                                        if (mDNHandlerHeaderFragment != null) {
                                            str =
                                                    mDNHandlerHeaderFragment
                                                            .findPreference("slot0_phone_number")
                                                            .getSummary()
                                                            .toString();
                                            str2 =
                                                    mDNHandlerHeaderFragment
                                                            .findPreference("slot1_phone_number")
                                                            .getSummary()
                                                            .toString();
                                        } else {
                                            str = ApnSettings.MVNO_NONE;
                                            str2 = ApnSettings.MVNO_NONE;
                                        }
                                        String[] strArr = {str2, str};
                                        if (!TextUtils.isEmpty(strArr[0])
                                                && !TextUtils.isEmpty(strArr[1])) {
                                            mDNHandlerFragment.switchToMainFragment(false);
                                            ((SmartForwardingActivity)
                                                            mDNHandlerFragment.getActivity())
                                                    .enableSmartForwarding(strArr);
                                            break;
                                        } else {
                                            new AlertDialog.Builder(
                                                            mDNHandlerFragment.getActivity())
                                                    .setTitle(
                                                            R.string.smart_forwarding_failed_title)
                                                    .setMessage(
                                                            R.string
                                                                    .smart_forwarding_missing_mdn_text)
                                                    .setPositiveButton(
                                                            R.string
                                                                    .smart_forwarding_missing_alert_dialog_text,
                                                            new MDNHandlerFragment$$ExternalSyntheticLambda2())
                                                    .create()
                                                    .show();
                                            break;
                                        }
                                        break;
                                    default:
                                        mDNHandlerFragment.switchToMainFragment(true);
                                        break;
                                }
                            }
                        });
        final int i2 = 1;
        ((Button) inflate.findViewById(R.id.cancel))
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.sim.smartForwarding.MDNHandlerFragment$$ExternalSyntheticLambda0
                            public final /* synthetic */ MDNHandlerFragment f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                String str;
                                String str2;
                                int i22 = i2;
                                MDNHandlerFragment mDNHandlerFragment = this.f$0;
                                switch (i22) {
                                    case 0:
                                        MDNHandlerHeaderFragment mDNHandlerHeaderFragment =
                                                (MDNHandlerHeaderFragment)
                                                        mDNHandlerFragment
                                                                .getChildFragmentManager()
                                                                .findFragmentById(
                                                                        R.id.fragment_settings);
                                        if (mDNHandlerHeaderFragment != null) {
                                            str =
                                                    mDNHandlerHeaderFragment
                                                            .findPreference("slot0_phone_number")
                                                            .getSummary()
                                                            .toString();
                                            str2 =
                                                    mDNHandlerHeaderFragment
                                                            .findPreference("slot1_phone_number")
                                                            .getSummary()
                                                            .toString();
                                        } else {
                                            str = ApnSettings.MVNO_NONE;
                                            str2 = ApnSettings.MVNO_NONE;
                                        }
                                        String[] strArr = {str2, str};
                                        if (!TextUtils.isEmpty(strArr[0])
                                                && !TextUtils.isEmpty(strArr[1])) {
                                            mDNHandlerFragment.switchToMainFragment(false);
                                            ((SmartForwardingActivity)
                                                            mDNHandlerFragment.getActivity())
                                                    .enableSmartForwarding(strArr);
                                            break;
                                        } else {
                                            new AlertDialog.Builder(
                                                            mDNHandlerFragment.getActivity())
                                                    .setTitle(
                                                            R.string.smart_forwarding_failed_title)
                                                    .setMessage(
                                                            R.string
                                                                    .smart_forwarding_missing_mdn_text)
                                                    .setPositiveButton(
                                                            R.string
                                                                    .smart_forwarding_missing_alert_dialog_text,
                                                            new MDNHandlerFragment$$ExternalSyntheticLambda2())
                                                    .create()
                                                    .show();
                                            break;
                                        }
                                        break;
                                    default:
                                        mDNHandlerFragment.switchToMainFragment(true);
                                        break;
                                }
                            }
                        });
        return inflate;
    }

    public final void switchToMainFragment(boolean z) {
        FragmentManagerImpl supportFragmentManager = getActivity().getSupportFragmentManager();
        supportFragmentManager.getClass();
        BackStackRecord backStackRecord = new BackStackRecord(supportFragmentManager);
        SmartForwardingFragment smartForwardingFragment = new SmartForwardingFragment();
        smartForwardingFragment.turnOffSwitch = z;
        backStackRecord.replace(R.id.content_frame, smartForwardingFragment, null);
        backStackRecord.commitInternal(false);
    }
}
