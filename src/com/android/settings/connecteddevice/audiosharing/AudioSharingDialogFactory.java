package com.android.settings.connecteddevice.audiosharing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertController;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

import com.android.settings.R;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public abstract class AudioSharingDialogFactory {

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class DialogBuilder {
        public AlertDialog.Builder mBuilder;
        public Context mContext;
        public View mCustomBody;
        public View mCustomTitle;
        public boolean mIsCustomBodyEnabled;

        public final AlertDialog build() {
            boolean z = this.mIsCustomBodyEnabled;
            AlertDialog.Builder builder = this.mBuilder;
            if (z) {
                builder.setView(this.mCustomBody);
            }
            View view = this.mCustomTitle;
            AlertController.AlertParams alertParams = builder.P;
            alertParams.mCustomTitleView = view;
            alertParams.mCancelable = false;
            AlertDialog create = builder.create();
            create.setCanceledOnTouchOutside(false);
            return create;
        }

        public final void setCustomMessage(int i) {
            TextView textView = (TextView) this.mCustomBody.findViewById(R.id.description_text);
            textView.setText(i);
            textView.setVisibility(0);
        }

        public final void setCustomNegativeButton(int i, View.OnClickListener onClickListener) {
            Button button = (Button) this.mCustomBody.findViewById(R.id.negative_btn);
            button.setText(i);
            button.setOnClickListener(onClickListener);
            button.setVisibility(0);
        }

        public final void setTitle(int i) {
            ((TextView) this.mCustomTitle.findViewById(R.id.title_text)).setText(i);
        }

        public final void setTitleIcon(int i) {
            ((ImageView) this.mCustomTitle.findViewById(R.id.title_icon)).setImageResource(i);
        }
    }

    public static DialogBuilder newBuilder(FragmentActivity fragmentActivity) {
        DialogBuilder dialogBuilder = new DialogBuilder();
        AlertDialog.Builder builder = new AlertDialog.Builder(fragmentActivity);
        dialogBuilder.mBuilder = builder;
        LayoutInflater from = LayoutInflater.from(builder.P.mContext);
        dialogBuilder.mCustomTitle =
                from.inflate(R.layout.dialog_custom_title_audio_sharing, (ViewGroup) null);
        dialogBuilder.mCustomBody =
                from.inflate(R.layout.dialog_custom_body_audio_sharing, (ViewGroup) null);
        return dialogBuilder;
    }
}
