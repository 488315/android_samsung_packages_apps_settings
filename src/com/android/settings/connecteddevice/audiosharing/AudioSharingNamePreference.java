package com.android.settings.connecteddevice.audiosharing;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.connecteddevice.audiosharing.audiostreams.AudioStreamsQrCodeFragment;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.widget.ValidatedEditTextPreference;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class AudioSharingNamePreference extends ValidatedEditTextPreference {
    public boolean mShowQrCodeIcon;

    public AudioSharingNamePreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mShowQrCodeIcon = false;
        setLayoutResource(R.layout.preference_two_target);
        setWidgetLayoutResource(R.layout.preference_widget_qrcode);
    }

    @Override // com.android.settings.widget.ValidatedEditTextPreference,
              // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        ImageButton imageButton = (ImageButton) preferenceViewHolder.findViewById(R.id.button_icon);
        View findViewById = preferenceViewHolder.findViewById(R.id.two_target_divider);
        if (imageButton == null || findViewById == null) {
            Log.w(
                    "AudioSharingNamePreference",
                    "onBindViewHolder() : shareButton or divider is null!");
            return;
        }
        if (!this.mShowQrCodeIcon) {
            findViewById.setVisibility(4);
            imageButton.setVisibility(4);
            imageButton.setOnClickListener(null);
        } else {
            findViewById.setVisibility(0);
            imageButton.setVisibility(0);
            imageButton.setImageDrawable(getContext().getDrawable(R.drawable.ic_qrcode_24dp));
            imageButton.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settings.connecteddevice.audiosharing.AudioSharingNamePreference$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            AudioSharingNamePreference audioSharingNamePreference =
                                    AudioSharingNamePreference.this;
                            SubSettingLauncher subSettingLauncher =
                                    new SubSettingLauncher(audioSharingNamePreference.getContext());
                            String string =
                                    audioSharingNamePreference
                                            .getContext()
                                            .getString(R.string.audio_streams_qr_code_page_title);
                            SubSettingLauncher.LaunchRequest launchRequest =
                                    subSettingLauncher.mLaunchRequest;
                            launchRequest.mTitle = string;
                            launchRequest.mDestinationName =
                                    AudioStreamsQrCodeFragment.class.getName();
                            launchRequest.mSourceMetricsCategory = 2048;
                            subSettingLauncher.launch();
                        }
                    });
        }
    }

    public AudioSharingNamePreference(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mShowQrCodeIcon = false;
        setLayoutResource(R.layout.preference_two_target);
        setWidgetLayoutResource(R.layout.preference_widget_qrcode);
    }

    public AudioSharingNamePreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mShowQrCodeIcon = false;
        setLayoutResource(R.layout.preference_two_target);
        setWidgetLayoutResource(R.layout.preference_widget_qrcode);
    }

    public AudioSharingNamePreference(Context context) {
        super(context);
        this.mShowQrCodeIcon = false;
        setLayoutResource(R.layout.preference_two_target);
        setWidgetLayoutResource(R.layout.preference_widget_qrcode);
    }
}
