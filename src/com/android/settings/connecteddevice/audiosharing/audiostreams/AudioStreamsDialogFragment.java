package com.android.settings.connecteddevice.audiosharing.audiostreams;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import com.google.common.base.Platform;

import java.util.function.Consumer;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioStreamsDialogFragment extends InstrumentedDialogFragment {
    public final DialogBuilder mDialogBuilder;
    public final int mDialogId;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DialogBuilder {
        public final AlertDialog.Builder mBuilder;
        public final Context mContext;
        public Consumer mLeftButtonOnClickListener;
        public String mLeftButtonText;
        public Consumer mRightButtonOnClickListener;
        public String mRightButtonText;
        public String mSubTitle1;
        public String mSubTitle2;
        public String mTitle;

        public DialogBuilder(Context context) {
            this.mContext = context;
            this.mBuilder = new AlertDialog.Builder(context);
        }

        public final AlertDialog build() {
            View inflate =
                    LayoutInflater.from(this.mContext)
                            .inflate(R.xml.bluetooth_audio_streams_dialog, (ViewGroup) null);
            final AlertDialog create = this.mBuilder.setView(inflate).setCancelable(false).create();
            create.setCanceledOnTouchOutside(false);
            ((TextView) inflate.requireViewById(R.id.dialog_title)).setText(this.mTitle);
            if (!Platform.stringIsNullOrEmpty(this.mSubTitle1)) {
                TextView textView = (TextView) inflate.requireViewById(R.id.dialog_subtitle);
                textView.setText(this.mSubTitle1);
                textView.setVisibility(0);
            }
            if (!Platform.stringIsNullOrEmpty(this.mSubTitle2)) {
                TextView textView2 = (TextView) inflate.requireViewById(R.id.dialog_subtitle_2);
                textView2.setText(this.mSubTitle2);
                textView2.setVisibility(0);
            }
            if (!Platform.stringIsNullOrEmpty(this.mLeftButtonText)) {
                Button button = (Button) inflate.requireViewById(R.id.left_button);
                button.setText(this.mLeftButtonText);
                button.setVisibility(0);
                final int i = 0;
                button.setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsDialogFragment$DialogBuilder$$ExternalSyntheticLambda0
                            public final /* synthetic */ AudioStreamsDialogFragment.DialogBuilder
                                    f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i) {
                                    case 0:
                                        AudioStreamsDialogFragment.DialogBuilder dialogBuilder =
                                                this.f$0;
                                        AlertDialog alertDialog = create;
                                        Consumer consumer =
                                                dialogBuilder.mLeftButtonOnClickListener;
                                        if (consumer != null) {
                                            consumer.accept(alertDialog);
                                            break;
                                        }
                                        break;
                                    default:
                                        AudioStreamsDialogFragment.DialogBuilder dialogBuilder2 =
                                                this.f$0;
                                        AlertDialog alertDialog2 = create;
                                        Consumer consumer2 =
                                                dialogBuilder2.mRightButtonOnClickListener;
                                        if (consumer2 != null) {
                                            consumer2.accept(alertDialog2);
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
            }
            if (!Platform.stringIsNullOrEmpty(this.mRightButtonText)) {
                Button button2 = (Button) inflate.requireViewById(R.id.right_button);
                button2.setText(this.mRightButtonText);
                button2.setVisibility(0);
                final int i2 = 1;
                button2.setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsDialogFragment$DialogBuilder$$ExternalSyntheticLambda0
                            public final /* synthetic */ AudioStreamsDialogFragment.DialogBuilder
                                    f$0;

                            {
                                this.f$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i2) {
                                    case 0:
                                        AudioStreamsDialogFragment.DialogBuilder dialogBuilder =
                                                this.f$0;
                                        AlertDialog alertDialog = create;
                                        Consumer consumer =
                                                dialogBuilder.mLeftButtonOnClickListener;
                                        if (consumer != null) {
                                            consumer.accept(alertDialog);
                                            break;
                                        }
                                        break;
                                    default:
                                        AudioStreamsDialogFragment.DialogBuilder dialogBuilder2 =
                                                this.f$0;
                                        AlertDialog alertDialog2 = create;
                                        Consumer consumer2 =
                                                dialogBuilder2.mRightButtonOnClickListener;
                                        if (consumer2 != null) {
                                            consumer2.accept(alertDialog2);
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
            }
            return create;
        }
    }

    public AudioStreamsDialogFragment(DialogBuilder dialogBuilder, int i) {
        this.mDialogBuilder = dialogBuilder;
        this.mDialogId = i;
    }

    public static void show(Fragment fragment, DialogBuilder dialogBuilder, int i) {
        if (!fragment.isAdded()) {
            Log.w("AudioStreamsDialogFragment", "The host fragment is not added to the activity!");
        } else {
            new AudioStreamsDialogFragment(dialogBuilder, i)
                    .show(fragment.getChildFragmentManager(), "AudioStreamsDialogFragment");
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return this.mDialogId;
    }

    @Override // androidx.fragment.app.DialogFragment
    public final Dialog onCreateDialog(Bundle bundle) {
        return this.mDialogBuilder.build();
    }

    @Override // androidx.fragment.app.DialogFragment
    public final void show(FragmentManager fragmentManager, String str) {
        Dialog dialog;
        Fragment findFragmentByTag =
                fragmentManager.findFragmentByTag("AudioStreamsDialogFragment");
        if (findFragmentByTag != null
                && (dialog = ((DialogFragment) findFragmentByTag).mDialog) != null
                && dialog.isShowing()) {
            Log.w("AudioStreamsDialogFragment", "Dialog already showing, ignore");
        } else {
            super.show(fragmentManager, str);
        }
    }
}
