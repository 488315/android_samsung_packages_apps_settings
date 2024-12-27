package com.samsung.android.settings.wifi.mobileap.views;

import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.fragment.app.FragmentActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.settings.wifi.mobileap.autohotspot.WifiApInvitationList;
import com.samsung.android.settings.wifi.mobileap.datamodels.WifiApAutoHotspotInvitationConfig;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSmartTetheringApkUtils;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApPreferenceWith2Buttons extends Preference
        implements Preference.OnPreferenceClickListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public WifiApInvitationList.AnonymousClass2 mClickListener;
    public WifiApAutoHotspotInvitationConfig mInvitationConfig;

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        TextView textView = (TextView) preferenceViewHolder.findViewById(R.id.decline_button);
        TextView textView2 = (TextView) preferenceViewHolder.findViewById(R.id.accept_button);
        final int i = 0;
        textView.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.wifi.mobileap.views.WifiApPreferenceWith2Buttons.1
                    public final /* synthetic */ WifiApPreferenceWith2Buttons this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i) {
                            case 0:
                                WifiApInvitationList.AnonymousClass2 anonymousClass2 =
                                        this.this$0.mClickListener;
                                FragmentActivity fragmentActivity =
                                        WifiApInvitationList.this.mContext;
                                String groupId = anonymousClass2.val$invitationConfig.getGroupId();
                                boolean z = WifiApSmartTetheringApkUtils.DBG;
                                DialogFragment$$ExternalSyntheticOutline0.m(
                                        "Declining Invitation for id ",
                                        groupId,
                                        "WifiApSmartTetheringApkUtils");
                                WifiApSmartTetheringApkUtils.startSmartTetheringApk(
                                        fragmentActivity, 5, groupId);
                                int i2 = WifiApPreferenceWith2Buttons.$r8$clinit;
                                Log.i(
                                        "WifiApPreferenceWith2Buttons",
                                        "First button clicked for key " + this.this$0.getKey());
                                break;
                            default:
                                WifiApPreferenceWith2Buttons wifiApPreferenceWith2Buttons =
                                        this.this$0;
                                WifiApInvitationList.AnonymousClass2 anonymousClass22 =
                                        wifiApPreferenceWith2Buttons.mClickListener;
                                WifiApAutoHotspotInvitationConfig
                                        wifiApAutoHotspotInvitationConfig =
                                                wifiApPreferenceWith2Buttons.mInvitationConfig;
                                anonymousClass22.getClass();
                                IntentFilter intentFilter = WifiApInvitationList.INTENT_FILTER;
                                WifiApInvitationList wifiApInvitationList =
                                        WifiApInvitationList.this;
                                wifiApInvitationList.getClass();
                                StringBuilder sb =
                                        new StringBuilder(
                                                "showing dialog for invitation with id: ");
                                WifiApAutoHotspotInvitationConfig
                                        wifiApAutoHotspotInvitationConfig2 =
                                                anonymousClass22.val$invitationConfig;
                                sb.append(wifiApAutoHotspotInvitationConfig2.getGroupId());
                                Log.i("WifiApInvitationList", sb.toString());
                                wifiApInvitationList.mClickedInvitationConfig =
                                        wifiApAutoHotspotInvitationConfig2;
                                wifiApInvitationList.showDialog(31);
                                int i3 = WifiApPreferenceWith2Buttons.$r8$clinit;
                                Log.i(
                                        "WifiApPreferenceWith2Buttons",
                                        "Second button clicked for key " + this.this$0.getKey());
                                break;
                        }
                    }
                });
        final int i2 = 1;
        textView2.setOnClickListener(
                new View.OnClickListener(
                        this) { // from class:
                                // com.samsung.android.settings.wifi.mobileap.views.WifiApPreferenceWith2Buttons.1
                    public final /* synthetic */ WifiApPreferenceWith2Buttons this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        switch (i2) {
                            case 0:
                                WifiApInvitationList.AnonymousClass2 anonymousClass2 =
                                        this.this$0.mClickListener;
                                FragmentActivity fragmentActivity =
                                        WifiApInvitationList.this.mContext;
                                String groupId = anonymousClass2.val$invitationConfig.getGroupId();
                                boolean z = WifiApSmartTetheringApkUtils.DBG;
                                DialogFragment$$ExternalSyntheticOutline0.m(
                                        "Declining Invitation for id ",
                                        groupId,
                                        "WifiApSmartTetheringApkUtils");
                                WifiApSmartTetheringApkUtils.startSmartTetheringApk(
                                        fragmentActivity, 5, groupId);
                                int i22 = WifiApPreferenceWith2Buttons.$r8$clinit;
                                Log.i(
                                        "WifiApPreferenceWith2Buttons",
                                        "First button clicked for key " + this.this$0.getKey());
                                break;
                            default:
                                WifiApPreferenceWith2Buttons wifiApPreferenceWith2Buttons =
                                        this.this$0;
                                WifiApInvitationList.AnonymousClass2 anonymousClass22 =
                                        wifiApPreferenceWith2Buttons.mClickListener;
                                WifiApAutoHotspotInvitationConfig
                                        wifiApAutoHotspotInvitationConfig =
                                                wifiApPreferenceWith2Buttons.mInvitationConfig;
                                anonymousClass22.getClass();
                                IntentFilter intentFilter = WifiApInvitationList.INTENT_FILTER;
                                WifiApInvitationList wifiApInvitationList =
                                        WifiApInvitationList.this;
                                wifiApInvitationList.getClass();
                                StringBuilder sb =
                                        new StringBuilder(
                                                "showing dialog for invitation with id: ");
                                WifiApAutoHotspotInvitationConfig
                                        wifiApAutoHotspotInvitationConfig2 =
                                                anonymousClass22.val$invitationConfig;
                                sb.append(wifiApAutoHotspotInvitationConfig2.getGroupId());
                                Log.i("WifiApInvitationList", sb.toString());
                                wifiApInvitationList.mClickedInvitationConfig =
                                        wifiApAutoHotspotInvitationConfig2;
                                wifiApInvitationList.showDialog(31);
                                int i3 = WifiApPreferenceWith2Buttons.$r8$clinit;
                                Log.i(
                                        "WifiApPreferenceWith2Buttons",
                                        "Second button clicked for key " + this.this$0.getKey());
                                break;
                        }
                    }
                });
    }

    @Override // androidx.preference.Preference.OnPreferenceClickListener
    public final boolean onPreferenceClick(Preference preference) {
        Log.i("WifiApPreferenceWith2Buttons", "Preference clicked for key " + getKey());
        this.mClickListener.getClass();
        return true;
    }
}
