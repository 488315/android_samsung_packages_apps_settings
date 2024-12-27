package com.android.settingslib.notification;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.service.notification.ZenModeConfig;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.policy.PhoneWindow;
import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.util.Arrays;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class ZenDurationDialog {
    protected static final int ALWAYS_ASK_CONDITION_INDEX = 2;
    protected static final int COUNTDOWN_CONDITION_INDEX = 1;
    public static final int DEFAULT_BUCKET_INDEX;
    protected static final int FOREVER_CONDITION_INDEX = 0;
    protected static final int MAX_BUCKET_MINUTES;
    public static final int[] MINUTE_BUCKETS;
    protected static final int MIN_BUCKET_MINUTES;
    protected int mBucketIndex = -1;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    public RadioGroup mZenRadioGroup;
    protected LinearLayout mZenRadioGroupContent;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ConditionTag {
        public int countdownZenDuration;
        public TextView line1;
        public View lines;
        public RadioButton rb;
    }

    static {
        int[] iArr = ZenModeConfig.MINUTE_BUCKETS;
        MINUTE_BUCKETS = iArr;
        MIN_BUCKET_MINUTES = iArr[0];
        MAX_BUCKET_MINUTES = iArr[iArr.length - 1];
        DEFAULT_BUCKET_INDEX = Arrays.binarySearch(iArr, 60);
    }

    public ZenDurationDialog(Context context) {
        this.mContext = context;
    }

    public final void bindTag(View view, int i, int i2) {
        final ConditionTag conditionTag =
                view.getTag() != null ? (ConditionTag) view.getTag() : new ConditionTag();
        view.setTag(conditionTag);
        if (conditionTag.rb == null) {
            conditionTag.rb = (RadioButton) this.mZenRadioGroup.getChildAt(i2);
        }
        if (i <= 0) {
            conditionTag.countdownZenDuration = MINUTE_BUCKETS[DEFAULT_BUCKET_INDEX];
        } else {
            conditionTag.countdownZenDuration = i;
        }
        conditionTag.rb.setOnCheckedChangeListener(
                new CompoundButton
                        .OnCheckedChangeListener() { // from class:
                                                     // com.android.settingslib.notification.ZenDurationDialog.2
                    @Override // android.widget.CompoundButton.OnCheckedChangeListener
                    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                        if (z) {
                            ConditionTag.this.rb.setChecked(true);
                        }
                        ConditionTag.this.line1.setStateDescription(
                                z ? compoundButton.getContext().getString(17042800) : null);
                    }
                });
        updateUi(conditionTag, view, i2);
    }

    public final AlertDialog createDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        setupDialog(builder);
        return builder.create();
    }

    public ConditionTag getConditionTagAt(int i) {
        return (ConditionTag) this.mZenRadioGroupContent.getChildAt(i).getTag();
    }

    public View getContentView() {
        if (this.mLayoutInflater == null) {
            this.mLayoutInflater = new PhoneWindow(this.mContext).getLayoutInflater();
        }
        View inflate =
                this.mLayoutInflater.inflate(R.layout.zen_mode_duration_dialog, (ViewGroup) null);
        ScrollView scrollView = (ScrollView) inflate.findViewById(R.id.zen_duration_container);
        this.mZenRadioGroup = (RadioGroup) scrollView.findViewById(R.id.zen_radio_buttons);
        this.mZenRadioGroupContent =
                (LinearLayout) scrollView.findViewById(R.id.zen_radio_buttons_content);
        for (int i = 0; i < 3; i++) {
            View inflate2 =
                    this.mLayoutInflater.inflate(
                            R.layout.zen_mode_radio_button, (ViewGroup) this.mZenRadioGroup, false);
            this.mZenRadioGroup.addView(inflate2);
            inflate2.setId(i);
            View inflate3 =
                    this.mLayoutInflater.inflate(
                            R.layout.zen_mode_condition,
                            (ViewGroup) this.mZenRadioGroupContent,
                            false);
            inflate3.setId(i + 3);
            this.mZenRadioGroupContent.addView(inflate3);
        }
        return inflate;
    }

    public void onClickTimeButton(View view, ConditionTag conditionTag, boolean z, int i) {
        int i2;
        int[] iArr = MINUTE_BUCKETS;
        int length = iArr.length;
        int i3 = this.mBucketIndex;
        if (i3 == -1) {
            long j = conditionTag.countdownZenDuration;
            for (int i4 = 0; i4 < length; i4++) {
                int i5 = z ? i4 : (length - 1) - i4;
                i2 = iArr[i5];
                if ((z && i2 > j) || (!z && i2 < j)) {
                    this.mBucketIndex = i5;
                    break;
                }
            }
            i2 = -1;
            if (i2 == -1) {
                int i6 = DEFAULT_BUCKET_INDEX;
                this.mBucketIndex = i6;
                i2 = iArr[i6];
            }
        } else {
            int max = Math.max(0, Math.min(length - 1, i3 + (z ? 1 : -1)));
            this.mBucketIndex = max;
            i2 = iArr[max];
        }
        conditionTag.countdownZenDuration = i2;
        bindTag(view, i2, i);
        conditionTag.rb.setChecked(true);
    }

    public final void setupDialog(AlertDialog.Builder builder) {
        final int i = Settings.Secure.getInt(this.mContext.getContentResolver(), "zen_duration", 0);
        builder.setTitle(R.string.zen_mode_duration_settings_title);
        builder.setNegativeButton(R.string.cancel, (DialogInterface.OnClickListener) null);
        builder.setPositiveButton(
                R.string.okay,
                new DialogInterface.OnClickListener() { // from class:
                    // com.android.settingslib.notification.ZenDurationDialog.1
                    @Override // android.content.DialogInterface.OnClickListener
                    public final void onClick(DialogInterface dialogInterface, int i2) {
                        ZenDurationDialog.this.updateZenDuration(i);
                        ZenDurationDialog zenDurationDialog = ZenDurationDialog.this;
                        Context context = zenDurationDialog.mContext;
                        zenDurationDialog.getClass();
                        Intent intent =
                                new Intent(
                                        "com.samsung.android.settings.notification.zen.salogging.NOTIFICATION_DND_SA_LOGGING");
                        intent.setPackage(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG);
                        context.sendBroadcast(intent);
                    }
                });
        View contentView = getContentView();
        setupRadioButtons(i);
        builder.setView(contentView);
    }

    public void setupRadioButtons(int i) {
        int i2 = i == 0 ? 0 : i > 0 ? 1 : 2;
        bindTag(this.mZenRadioGroupContent.getChildAt(0), i, 0);
        bindTag(this.mZenRadioGroupContent.getChildAt(1), i, 1);
        bindTag(this.mZenRadioGroupContent.getChildAt(2), i, 2);
        getConditionTagAt(i2).rb.setChecked(true);
    }

    public void updateUi(final ConditionTag conditionTag, final View view, final int i) {
        int i2;
        View view2 = conditionTag.lines;
        if (view2 == null) {
            if (view2 == null) {
                conditionTag.lines = view.findViewById(android.R.id.content);
            }
            if (conditionTag.line1 == null) {
                conditionTag.line1 = (TextView) view.findViewById(android.R.id.text1);
            }
            view.findViewById(android.R.id.text2).setVisibility(8);
            conditionTag.lines.setOnClickListener(
                    new View
                            .OnClickListener() { // from class:
                                                 // com.android.settingslib.notification.ZenDurationDialog.3
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view3) {
                            ConditionTag.this.rb.setChecked(true);
                        }
                    });
        }
        ImageView imageView = (ImageView) view.findViewById(android.R.id.button1);
        ImageView imageView2 = (ImageView) view.findViewById(android.R.id.button2);
        View findViewById = view.findViewById(R.id.divider_view);
        long j = conditionTag.countdownZenDuration;
        if (i == 1) {
            final int i3 = 0;
            i2 = 1;
            imageView.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.android.settingslib.notification.ZenDurationDialog.4
                        public final /* synthetic */ ZenDurationDialog this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view3) {
                            switch (i3) {
                                case 0:
                                    this.this$0.onClickTimeButton(view, conditionTag, false, i);
                                    conditionTag.lines.setAccessibilityLiveRegion(1);
                                    break;
                                default:
                                    this.this$0.onClickTimeButton(view, conditionTag, true, i);
                                    conditionTag.lines.setAccessibilityLiveRegion(1);
                                    break;
                            }
                        }
                    });
            final int i4 = 1;
            imageView2.setOnClickListener(
                    new View.OnClickListener(
                            this) { // from class:
                                    // com.android.settingslib.notification.ZenDurationDialog.4
                        public final /* synthetic */ ZenDurationDialog this$0;

                        {
                            this.this$0 = this;
                        }

                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view3) {
                            switch (i4) {
                                case 0:
                                    this.this$0.onClickTimeButton(view, conditionTag, false, i);
                                    conditionTag.lines.setAccessibilityLiveRegion(1);
                                    break;
                                default:
                                    this.this$0.onClickTimeButton(view, conditionTag, true, i);
                                    conditionTag.lines.setAccessibilityLiveRegion(1);
                                    break;
                            }
                        }
                    });
            imageView.setVisibility(0);
            imageView2.setVisibility(0);
            findViewById.setVisibility(0);
            imageView.setEnabled(j > ((long) MIN_BUCKET_MINUTES));
            imageView2.setEnabled(conditionTag.countdownZenDuration != MAX_BUCKET_MINUTES);
            imageView.setAlpha(imageView.isEnabled() ? 1.0f : 0.5f);
            imageView2.setAlpha(imageView2.isEnabled() ? 1.0f : 0.5f);
        } else {
            i2 = 1;
            if (imageView != null) {
                ((ViewGroup) view).removeView(imageView);
            }
            if (imageView2 != null) {
                ((ViewGroup) view).removeView(imageView2);
            }
        }
        conditionTag.line1.setText(
                i != 0
                        ? i != i2
                                ? i != 2
                                        ? ApnSettings.MVNO_NONE
                                        : this.mContext.getString(
                                                R.string.zen_mode_duration_always_prompt_title)
                                : ZenModeConfig.toTimeCondition(
                                                this.mContext,
                                                conditionTag.countdownZenDuration,
                                                ActivityManager.getCurrentUser(),
                                                false)
                                        .line1
                        : this.mContext.getString(R.string.zen_mode_forever));
    }

    public void updateZenDuration(int i) {
        int checkedRadioButtonId = this.mZenRadioGroup.getCheckedRadioButtonId();
        int i2 = 0;
        int i3 = Settings.Secure.getInt(this.mContext.getContentResolver(), "zen_duration", 0);
        if (checkedRadioButtonId == 0) {
            MetricsLogger.action(this.mContext, 1343);
        } else if (checkedRadioButtonId == 1) {
            i2 = getConditionTagAt(checkedRadioButtonId).countdownZenDuration;
            MetricsLogger.action(this.mContext, 1342, i2);
        } else if (checkedRadioButtonId != 2) {
            i2 = i3;
        } else {
            MetricsLogger.action(this.mContext, 1344);
            i2 = -1;
        }
        if (i != i2) {
            Settings.Secure.putInt(this.mContext.getContentResolver(), "zen_duration", i2);
        }
    }
}
