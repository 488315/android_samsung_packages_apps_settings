package com.samsung.android.settings.wifi.mobileap.views;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;
import com.android.settings.widget.SeekBarPreference;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.wifi.mobileap.configure.WifiApConfigureBandSeekBarController;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFeatureUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApFrameworkUtils;
import com.samsung.android.settings.wifi.mobileap.utils.WifiApSettingsUtils;
import com.samsung.android.util.SemLog;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class WifiApSeekBarPreference extends SeekBarPreference {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context mContext;
    public String[] mLabels;
    public Preference.OnPreferenceChangeListener mOnPreferenceChangeListener;
    public OnPreferenceItemsCLickListener mOnPreferenceItemsCLickListener;
    public final AnonymousClass2 mOnSeekBarChangeListener;
    public TextView mProgressTextView;
    public final AnonymousClass1 mSecondaryIconClickListener;
    public Drawable mSecondaryIconDrawable;
    public ImageView mSecondaryIconImageView;
    public String mSecondaryIconLabel;
    public SeekBar mSeekBar;
    public final WifiApSeekBarPreference thisPreference;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface OnPreferenceItemsCLickListener {}

    /* JADX WARN: Type inference failed for: r2v1, types: [com.samsung.android.settings.wifi.mobileap.views.WifiApSeekBarPreference$1] */
    /* JADX WARN: Type inference failed for: r3v1, types: [com.samsung.android.settings.wifi.mobileap.views.WifiApSeekBarPreference$2] */
    public WifiApSeekBarPreference(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSecondaryIconClickListener =
                new View
                        .OnClickListener() { // from class:
                                             // com.samsung.android.settings.wifi.mobileap.views.WifiApSeekBarPreference.1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view) {
                        String str;
                        Context context2;
                        Context context3;
                        Context context4;
                        Context context5;
                        Context context6;
                        int i3 = WifiApSeekBarPreference.$r8$clinit;
                        SemLog.i("WifiApSeekBarPreference", "Secondary icon clicked.");
                        OnPreferenceItemsCLickListener onPreferenceItemsCLickListener =
                                WifiApSeekBarPreference.this.mOnPreferenceItemsCLickListener;
                        if (onPreferenceItemsCLickListener != null) {
                            str = WifiApConfigureBandSeekBarController.TAG;
                            Log.i(str, "Secondary Icon clicked");
                            WifiApConfigureBandSeekBarController
                                    wifiApConfigureBandSeekBarController =
                                            WifiApConfigureBandSeekBarController.this;
                            context2 = wifiApConfigureBandSeekBarController.mContext;
                            WifiApSettingsUtils.hideKeyboard(context2);
                            context3 = wifiApConfigureBandSeekBarController.mContext;
                            AlertDialog.Builder builder = new AlertDialog.Builder(context3, 0);
                            builder.setView(R.layout.sec_wifi_ap_band_tips_dialog);
                            builder.setPositiveButton(
                                    R.string.dlg_ok,
                                    new WifiApConfigureBandSeekBarController.AnonymousClass1
                                            .DialogInterfaceOnClickListenerC00671());
                            AlertDialog create = builder.create();
                            create.show();
                            LinearLayout linearLayout =
                                    (LinearLayout) create.findViewById(R.id.band_2ghz_5ghz_layout);
                            LinearLayout linearLayout2 =
                                    (LinearLayout) create.findViewById(R.id.band_5ghz_layout);
                            LinearLayout linearLayout3 =
                                    (LinearLayout) create.findViewById(R.id.band_6ghz_layout);
                            context4 = wifiApConfigureBandSeekBarController.mContext;
                            if (WifiApFeatureUtils.is5GhzBandSupported(context4)) {
                                linearLayout.setVisibility(0);
                            } else {
                                linearLayout.setVisibility(8);
                            }
                            context5 = wifiApConfigureBandSeekBarController.mContext;
                            if (WifiApFeatureUtils.is5GhzBandSupported(context5)) {
                                linearLayout2.setVisibility(0);
                            } else {
                                linearLayout2.setVisibility(8);
                            }
                            context6 = wifiApConfigureBandSeekBarController.mContext;
                            if (WifiApFrameworkUtils.getSemWifiManager(context6)
                                    .supportWifiAp6GBasedOnCountry()) {
                                linearLayout3.setVisibility(0);
                            } else {
                                linearLayout3.setVisibility(8);
                            }
                        }
                    }
                };
        this.mSecondaryIconLabel = ApnSettings.MVNO_NONE;
        this.mOnSeekBarChangeListener =
                new SeekBar
                        .OnSeekBarChangeListener() { // from class:
                                                     // com.samsung.android.settings.wifi.mobileap.views.WifiApSeekBarPreference.2
                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onProgressChanged(SeekBar seekBar, int i3, boolean z) {
                        WifiApSeekBarPreference.this.setProgress(i3);
                        WifiApSeekBarPreference wifiApSeekBarPreference =
                                WifiApSeekBarPreference.this;
                        Preference.OnPreferenceChangeListener onPreferenceChangeListener =
                                wifiApSeekBarPreference.mOnPreferenceChangeListener;
                        if (onPreferenceChangeListener != null) {
                            onPreferenceChangeListener.onPreferenceChange(
                                    wifiApSeekBarPreference.thisPreference, Integer.valueOf(i3));
                        }
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStopTrackingTouch(SeekBar seekBar) {
                        WifiApSeekBarPreference wifiApSeekBarPreference =
                                WifiApSeekBarPreference.this;
                        Preference.OnPreferenceChangeListener onPreferenceChangeListener =
                                wifiApSeekBarPreference.mOnPreferenceChangeListener;
                        if (onPreferenceChangeListener != null) {
                            onPreferenceChangeListener.onPreferenceChange(
                                    wifiApSeekBarPreference.thisPreference,
                                    Integer.valueOf(seekBar.getProgress()));
                        }
                        WifiApSeekBarPreference.this.setSeekBarThumbTextView();
                    }

                    @Override // android.widget.SeekBar.OnSeekBarChangeListener
                    public final void onStartTrackingTouch(SeekBar seekBar) {}
                };
        setLayoutResource(R.layout.sec_wifi_ap_seekbar_preference);
        this.mContext = context;
        this.thisPreference = this;
        String[] strArr = new String[this.mMax + 1];
        this.mLabels = strArr;
        Arrays.fill(strArr, ApnSettings.MVNO_NONE);
    }

    @Override // com.android.settings.widget.SeekBarPreference,
              // com.android.settingslib.RestrictedPreference,
              // com.android.settingslib.widget.TwoTargetPreference, androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        SemLog.i("WifiApSeekBarPreference", "onBindViewHolder");
        super.onBindViewHolder(preferenceViewHolder);
        this.mProgressTextView = (TextView) preferenceViewHolder.findViewById(R.id.progress_text);
        this.mSeekBar = (SeekBar) preferenceViewHolder.findViewById(android.R.id.textClassifier);
        ImageView imageView = (ImageView) preferenceViewHolder.findViewById(R.id.icon_secondary);
        this.mSecondaryIconImageView = imageView;
        Drawable drawable = this.mSecondaryIconDrawable;
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
            this.mSecondaryIconImageView.setOnClickListener(this.mSecondaryIconClickListener);
            this.mSecondaryIconImageView.setVisibility(0);
            this.mSecondaryIconImageView.setContentDescription(this.mSecondaryIconLabel);
        } else {
            SemLog.e("WifiApSeekBarPreference", "Error is setting secondary Icon");
            this.mSecondaryIconImageView.setVisibility(8);
        }
        this.mSeekBar.setOnSeekBarChangeListener(this.mOnSeekBarChangeListener);
        setSeekBarThumbTextView();
    }

    @Override // androidx.preference.Preference
    public final void setOnPreferenceChangeListener(
            Preference.OnPreferenceChangeListener onPreferenceChangeListener) {
        SemLog.i("WifiApSeekBarPreference", "setOnPreferenceChangeListener() - Triggered");
        this.mOnPreferenceChangeListener = onPreferenceChangeListener;
    }

    @Override // com.android.settings.widget.SeekBarPreference
    public final void setProgress(int i) {
        setProgress(i, true);
        notifyChanged();
    }

    public final void setSeekBarThumbTextView() {
        TextView textView;
        SeekBar seekBar = this.mSeekBar;
        if (seekBar == null || (textView = this.mProgressTextView) == null) {
            SemLog.e("WifiApSeekBarPreference", "setting SeekBar ThumbTextView - Null Error");
        } else {
            textView.setText(this.mLabels[seekBar.getProgress()]);
            this.mProgressTextView.post(
                    new Runnable() { // from class:
                        // com.samsung.android.settings.wifi.mobileap.views.WifiApSeekBarPreference$$ExternalSyntheticLambda0
                        /* JADX WARN: Code restructure failed: missing block: B:14:0x002b, code lost:

                           if (com.android.settings.Utils.isRTL(r5.mContext) != false) goto L7;
                        */
                        /* JADX WARN: Code restructure failed: missing block: B:4:0x001e, code lost:

                           if (com.android.settings.Utils.isRTL(r5.mContext) != false) goto L13;
                        */
                        /* JADX WARN: Code restructure failed: missing block: B:5:0x0021, code lost:

                           r0 = 0.0f;
                        */
                        @Override // java.lang.Runnable
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                            To view partially-correct code enable 'Show inconsistent code' option in preferences
                        */
                        public final void run() {
                            /*
                                r5 = this;
                                com.samsung.android.settings.wifi.mobileap.views.WifiApSeekBarPreference r5 = com.samsung.android.settings.wifi.mobileap.views.WifiApSeekBarPreference.this
                                android.widget.TextView r0 = r5.mProgressTextView
                                int r0 = r0.getWidth()
                                float r0 = (float) r0
                                android.widget.SeekBar r1 = r5.mSeekBar
                                int r1 = r1.getProgress()
                                android.widget.SeekBar r2 = r5.mSeekBar
                                int r2 = r2.getMax()
                                r3 = 0
                                if (r1 != 0) goto L23
                                android.content.Context r4 = r5.mContext
                                boolean r4 = com.android.settings.Utils.isRTL(r4)
                                if (r4 == 0) goto L21
                                goto L31
                            L21:
                                r0 = r3
                                goto L31
                            L23:
                                if (r1 != r2) goto L2e
                                android.content.Context r4 = r5.mContext
                                boolean r4 = com.android.settings.Utils.isRTL(r4)
                                if (r4 == 0) goto L31
                                goto L21
                            L2e:
                                r3 = 1073741824(0x40000000, float:2.0)
                                float r0 = r0 / r3
                            L31:
                                android.widget.SeekBar r3 = r5.mSeekBar
                                int r3 = r3.getWidth()
                                android.widget.SeekBar r4 = r5.mSeekBar
                                int r4 = r4.getPaddingLeft()
                                int r3 = r3 - r4
                                android.widget.SeekBar r4 = r5.mSeekBar
                                int r4 = r4.getPaddingRight()
                                int r3 = r3 - r4
                                float r3 = (float) r3
                                float r2 = (float) r2
                                float r3 = r3 / r2
                                android.content.Context r2 = r5.mContext
                                boolean r2 = com.android.settings.Utils.isRTL(r2)
                                if (r2 == 0) goto L57
                                java.lang.String[] r2 = r5.mLabels
                                int r2 = r2.length
                                int r2 = r2 + (-1)
                                int r1 = r2 - r1
                            L57:
                                android.widget.SeekBar r2 = r5.mSeekBar
                                int r2 = r2.getPaddingLeft()
                                float r2 = (float) r2
                                float r1 = (float) r1
                                float r1 = r1 * r3
                                float r1 = r1 + r2
                                float r1 = r1 - r0
                                android.widget.TextView r0 = r5.mProgressTextView
                                r0.setX(r1)
                                android.widget.TextView r5 = r5.mProgressTextView
                                r0 = 0
                                r5.setVisibility(r0)
                                return
                            */
                            throw new UnsupportedOperationException(
                                    "Method not decompiled:"
                                        + " com.samsung.android.settings.wifi.mobileap.views.WifiApSeekBarPreference$$ExternalSyntheticLambda0.run():void");
                        }
                    });
        }
    }

    public WifiApSeekBarPreference(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public WifiApSeekBarPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public WifiApSeekBarPreference(Context context) {
        this(context, null);
    }
}
