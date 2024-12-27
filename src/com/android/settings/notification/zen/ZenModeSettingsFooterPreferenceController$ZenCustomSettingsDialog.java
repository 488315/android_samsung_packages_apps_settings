package com.android.settings.notification.zen;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
        extends InstrumentedDialogFragment {
    public final String KEY_POLICY = "policy";
    public NotificationManager.Policy mPolicy;
    public ZenModeSettings.SummaryBuilder mSummaryBuilder;

    /* renamed from: -$$Nest$mgetAllowRes, reason: not valid java name */
    public static int m990$$Nest$mgetAllowRes(
            ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                    zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog,
            boolean z) {
        zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog.getClass();
        return z ? R.string.zen_mode_sound_summary_on : R.string.switch_off_text;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 1612;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        NotificationManager.Policy policy;
        final FragmentActivity activity = getActivity();
        if (bundle != null
                && (policy = (NotificationManager.Policy) bundle.getParcelable(this.KEY_POLICY))
                        != null) {
            this.mPolicy = policy;
        }
        this.mSummaryBuilder = new ZenModeSettings.SummaryBuilder(activity);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.zen_custom_settings_dialog_title);
        builder.setNeutralButton(
                R.string.zen_custom_settings_dialog_review_schedule,
                new DialogInterface
                        .OnClickListener() { // from class:
                                             // com.android.settings.notification.zen.ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i) {
                        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(activity);
                        String name = ZenModeAutomationSettings.class.getName();
                        SubSettingLauncher.LaunchRequest launchRequest =
                                subSettingLauncher.mLaunchRequest;
                        launchRequest.mDestinationName = name;
                        launchRequest.mSourceMetricsCategory = 142;
                        subSettingLauncher.launch();
                    }
                });
        builder.setPositiveButton(
                R.string.zen_custom_settings_dialog_ok, (DialogInterface.OnClickListener) null);
        builder.setView(
                LayoutInflater.from(activity)
                        .inflate(
                                (XmlPullParser)
                                        activity.getResources()
                                                .getLayout(R.layout.zen_custom_settings_dialog),
                                (ViewGroup) null,
                                false));
        final AlertDialog create = builder.create();
        create.setOnShowListener(
                new DialogInterface
                        .OnShowListener() { // from class:
                                            // com.android.settings.notification.zen.ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog.2
                    @Override // android.content.DialogInterface.OnShowListener
                    public final void onShow(DialogInterface dialogInterface) {
                        TextView textView =
                                (TextView)
                                        create.findViewById(
                                                R.id.zen_custom_settings_dialog_calls_allow);
                        TextView textView2 =
                                (TextView)
                                        create.findViewById(
                                                R.id.zen_custom_settings_dialog_messages_allow);
                        TextView textView3 =
                                (TextView)
                                        create.findViewById(
                                                R.id.zen_custom_settings_dialog_alarms_allow);
                        TextView textView4 =
                                (TextView)
                                        create.findViewById(
                                                R.id.zen_custom_settings_dialog_media_allow);
                        TextView textView5 =
                                (TextView)
                                        create.findViewById(
                                                R.id.zen_custom_settings_dialog_system_allow);
                        TextView textView6 =
                                (TextView)
                                        create.findViewById(
                                                R.id.zen_custom_settings_dialog_reminders_allow);
                        TextView textView7 =
                                (TextView)
                                        create.findViewById(
                                                R.id.zen_custom_settings_dialog_events_allow);
                        TextView textView8 =
                                (TextView)
                                        create.findViewById(
                                                R.id.zen_custom_settings_dialog_show_notifications);
                        ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog =
                                        ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                                .this;
                        textView.setText(
                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                        .mSummaryBuilder.getCallsSettingSummary(
                                        zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                                .mPolicy));
                        ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog2 =
                                        ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                                .this;
                        textView2.setText(
                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog2
                                        .mSummaryBuilder.getMessagesSettingSummary(
                                        zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog2
                                                .mPolicy));
                        ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog3 =
                                        ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                                .this;
                        textView3.setText(
                                ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                        .m990$$Nest$mgetAllowRes(
                                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog3,
                                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog3
                                                        .mPolicy.allowAlarms()));
                        ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog4 =
                                        ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                                .this;
                        textView4.setText(
                                ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                        .m990$$Nest$mgetAllowRes(
                                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog4,
                                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog4
                                                        .mPolicy.allowMedia()));
                        ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog5 =
                                        ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                                .this;
                        textView5.setText(
                                ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                        .m990$$Nest$mgetAllowRes(
                                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog5,
                                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog5
                                                        .mPolicy.allowSystem()));
                        ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog6 =
                                        ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                                .this;
                        textView6.setText(
                                ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                        .m990$$Nest$mgetAllowRes(
                                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog6,
                                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog6
                                                        .mPolicy.allowReminders()));
                        ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog7 =
                                        ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                                .this;
                        textView7.setText(
                                ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                        .m990$$Nest$mgetAllowRes(
                                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog7,
                                                zenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog7
                                                        .mPolicy.allowEvents()));
                        textView8.setText(
                                ZenModeSettingsFooterPreferenceController$ZenCustomSettingsDialog
                                        .this
                                        .mSummaryBuilder
                                        .mContext
                                        .getResources()
                                        .getString(
                                                R.string
                                                        .zen_mode_restrict_notifications_hide_summary));
                    }
                });
        return create;
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelable(this.KEY_POLICY, this.mPolicy);
    }
}
