package com.samsung.android.settings.uwb.labs.view.uwbtest;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.uwb.RangingMeasurement;
import android.uwb.RangingReport;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.preference.Preference;
import androidx.preference.PreferenceViewHolder;

import com.android.settings.R;

import com.samsung.android.knox.net.apn.ApnSettings;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class UwbSimpleTestPreference extends Preference implements UwbDataReceiver {
    public boolean isFirstReceived;
    public boolean isInitiator;
    public boolean isRanging;
    public boolean isUwbEnabled;
    public final Context mContext;
    public UwbSimpleTestFragmentController mController;
    public ImageView mImageViewInitiator;
    public ImageView mImageViewResponder;
    public LinearLayout mLayout0;
    public RelativeLayout mLayout1;
    public RelativeLayout mLayout2;
    public int mOriginalHeight;
    public int mOriginalImageMargin;
    public int mOriginalWidth;
    public String mStringRangingData;
    public TextView mTextGuideline;
    public TextView mTextRangingData;
    public PreferenceViewHolder mViewHolder;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.uwb.labs.view.uwbtest.UwbSimpleTestPreference$4, reason: invalid class name */
    public final class AnonymousClass4 implements Runnable {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ UwbSimpleTestPreference this$0;

        public /* synthetic */ AnonymousClass4(
                UwbSimpleTestPreference uwbSimpleTestPreference, int i) {
            this.$r8$classId = i;
            this.this$0 = uwbSimpleTestPreference;
        }

        @Override // java.lang.Runnable
        public final void run() {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.motionStop();
                    break;
                case 1:
                    this.this$0.mTextGuideline.setText(ApnSettings.MVNO_NONE);
                    UwbSimpleTestPreference uwbSimpleTestPreference = this.this$0;
                    if (uwbSimpleTestPreference.isInitiator) {
                        ((AnimationDrawable)
                                        uwbSimpleTestPreference.mImageViewResponder.getBackground())
                                .start();
                    } else {
                        ((AnimationDrawable)
                                        uwbSimpleTestPreference.mImageViewInitiator.getBackground())
                                .start();
                    }
                    ((AnimationDrawable) this.this$0.mLayout0.getBackground()).start();
                    break;
                default:
                    UwbSimpleTestPreference uwbSimpleTestPreference2 = this.this$0;
                    uwbSimpleTestPreference2.mTextRangingData.setText(
                            uwbSimpleTestPreference2.mStringRangingData);
                    break;
            }
        }
    }

    /* renamed from: -$$Nest$mmotionStart, reason: not valid java name */
    public static void m1303$$Nest$mmotionStart(UwbSimpleTestPreference uwbSimpleTestPreference) {
        if (uwbSimpleTestPreference.isInitiator) {
            ((AnimationDrawable) uwbSimpleTestPreference.mImageViewInitiator.getBackground())
                    .start();
        } else {
            ((AnimationDrawable) uwbSimpleTestPreference.mImageViewResponder.getBackground())
                    .start();
        }
        uwbSimpleTestPreference.mTextGuideline.setText(R.string.uwb_simple_ranging_waiting_message);
    }

    public UwbSimpleTestPreference(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isInitiator = false;
        this.isRanging = false;
        this.isFirstReceived = true;
        this.mContext = context;
        Log.i("UwbSimpleTestPreference", "CREATE: UwbSimpleTestPreference");
        setLayoutResource(R.layout.sec_uwb_labs_simple_test_preference);
    }

    public final void initViewDisenabled() {
        this.mLayout0.setVisibility(8);
        this.mLayout1.setVisibility(8);
        this.mLayout2.setVisibility(0);
        this.isInitiator = false;
        this.isRanging = false;
        this.isFirstReceived = true;
        motionStop();
    }

    public final void motionStop() {
        ((AnimationDrawable) this.mImageViewInitiator.getBackground()).stop();
        ((AnimationDrawable) this.mImageViewResponder.getBackground()).stop();
        ((AnimationDrawable) this.mLayout0.getBackground()).stop();
        this.mTextGuideline.setText(R.string.uwb_simple_ranging_greeting_message);
        ViewGroup.LayoutParams layoutParams = this.mImageViewInitiator.getLayoutParams();
        layoutParams.width = this.mOriginalWidth;
        layoutParams.height = this.mOriginalHeight;
        this.mImageViewInitiator.setLayoutParams(layoutParams);
        ViewGroup.MarginLayoutParams marginLayoutParams =
                (ViewGroup.MarginLayoutParams) this.mImageViewInitiator.getLayoutParams();
        marginLayoutParams.setMargins(this.mOriginalImageMargin, 0, 0, 0);
        this.mImageViewInitiator.setLayoutParams(marginLayoutParams);
        ViewGroup.LayoutParams layoutParams2 = this.mImageViewResponder.getLayoutParams();
        layoutParams2.width = this.mOriginalWidth;
        layoutParams2.height = this.mOriginalHeight;
        this.mImageViewResponder.setLayoutParams(layoutParams2);
        ViewGroup.MarginLayoutParams marginLayoutParams2 =
                (ViewGroup.MarginLayoutParams) this.mImageViewResponder.getLayoutParams();
        marginLayoutParams2.setMargins(0, 0, this.mOriginalImageMargin, 0);
        this.mImageViewResponder.setLayoutParams(marginLayoutParams2);
    }

    @Override // androidx.preference.Preference
    public final void onBindViewHolder(PreferenceViewHolder preferenceViewHolder) {
        super.onBindViewHolder(preferenceViewHolder);
        Log.d("UwbSimpleTestPreference", "onBindViewHolder");
        this.mViewHolder = preferenceViewHolder;
        UwbSimpleTestFragmentController.setOnUwbReceiver(this);
        this.isUwbEnabled =
                UwbSimpleTestFragmentController.mUwbTestController.mRanging.mUwbManager
                        .isUwbEnabled();
        this.mLayout0 = (LinearLayout) this.mViewHolder.findViewById(R.id.layout0);
        this.mLayout1 = (RelativeLayout) this.mViewHolder.findViewById(R.id.layout1);
        this.mLayout2 = (RelativeLayout) this.mViewHolder.findViewById(R.id.layout2);
        this.mTextRangingData = (TextView) this.mViewHolder.findViewById(R.id.textview_rangingData);
        this.mTextGuideline = (TextView) this.mViewHolder.findViewById(R.id.textview_guideline);
        this.mImageViewInitiator =
                (ImageView) this.mViewHolder.findViewById(R.id.imageView_initiator);
        this.mImageViewResponder =
                (ImageView) this.mViewHolder.findViewById(R.id.imageView_responder);
        this.mLayout0 = (LinearLayout) this.mViewHolder.findViewById(R.id.layout0);
        ViewGroup.LayoutParams layoutParams = this.mImageViewResponder.getLayoutParams();
        this.mOriginalWidth = layoutParams.width;
        this.mOriginalHeight = layoutParams.height;
        this.mOriginalImageMargin =
                ((ViewGroup.MarginLayoutParams) this.mImageViewResponder.getLayoutParams())
                        .rightMargin;
        final int i = 0;
        this.mViewHolder
                .findViewById(R.id.imageView_initiator)
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.uwb.labs.view.uwbtest.UwbSimpleTestPreference.1
                            public final /* synthetic */ UwbSimpleTestPreference this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i) {
                                    case 0:
                                        UwbSimpleTestPreference uwbSimpleTestPreference =
                                                this.this$0;
                                        if (!uwbSimpleTestPreference.isRanging) {
                                            uwbSimpleTestPreference.isInitiator = true;
                                            uwbSimpleTestPreference.isRanging = true;
                                            UwbSimpleTestPreference.m1303$$Nest$mmotionStart(
                                                    uwbSimpleTestPreference);
                                            UwbSimpleTestPreference uwbSimpleTestPreference2 =
                                                    this.this$0;
                                            UwbSimpleTestFragmentController
                                                    uwbSimpleTestFragmentController =
                                                            uwbSimpleTestPreference2.mController;
                                            UwbSimpleTestFragmentController.start(
                                                    uwbSimpleTestPreference2.isInitiator);
                                            break;
                                        } else if (uwbSimpleTestPreference.isInitiator) {
                                            UwbSimpleTestFragmentController
                                                    uwbSimpleTestFragmentController2 =
                                                            uwbSimpleTestPreference.mController;
                                            UwbSimpleTestFragmentController.stop();
                                            this.this$0.motionStop();
                                            break;
                                        }
                                        break;
                                    default:
                                        UwbSimpleTestPreference uwbSimpleTestPreference3 =
                                                this.this$0;
                                        if (!uwbSimpleTestPreference3.isRanging) {
                                            uwbSimpleTestPreference3.isInitiator = false;
                                            uwbSimpleTestPreference3.isRanging = true;
                                            UwbSimpleTestPreference.m1303$$Nest$mmotionStart(
                                                    uwbSimpleTestPreference3);
                                            UwbSimpleTestPreference uwbSimpleTestPreference4 =
                                                    this.this$0;
                                            UwbSimpleTestFragmentController
                                                    uwbSimpleTestFragmentController3 =
                                                            uwbSimpleTestPreference4.mController;
                                            UwbSimpleTestFragmentController.start(
                                                    uwbSimpleTestPreference4.isInitiator);
                                            break;
                                        } else if (!uwbSimpleTestPreference3.isInitiator) {
                                            UwbSimpleTestFragmentController
                                                    uwbSimpleTestFragmentController4 =
                                                            uwbSimpleTestPreference3.mController;
                                            UwbSimpleTestFragmentController.stop();
                                            this.this$0.motionStop();
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
        final int i2 = 1;
        this.mViewHolder
                .findViewById(R.id.imageView_responder)
                .setOnClickListener(
                        new View.OnClickListener(
                                this) { // from class:
                                        // com.samsung.android.settings.uwb.labs.view.uwbtest.UwbSimpleTestPreference.1
                            public final /* synthetic */ UwbSimpleTestPreference this$0;

                            {
                                this.this$0 = this;
                            }

                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                switch (i2) {
                                    case 0:
                                        UwbSimpleTestPreference uwbSimpleTestPreference =
                                                this.this$0;
                                        if (!uwbSimpleTestPreference.isRanging) {
                                            uwbSimpleTestPreference.isInitiator = true;
                                            uwbSimpleTestPreference.isRanging = true;
                                            UwbSimpleTestPreference.m1303$$Nest$mmotionStart(
                                                    uwbSimpleTestPreference);
                                            UwbSimpleTestPreference uwbSimpleTestPreference2 =
                                                    this.this$0;
                                            UwbSimpleTestFragmentController
                                                    uwbSimpleTestFragmentController =
                                                            uwbSimpleTestPreference2.mController;
                                            UwbSimpleTestFragmentController.start(
                                                    uwbSimpleTestPreference2.isInitiator);
                                            break;
                                        } else if (uwbSimpleTestPreference.isInitiator) {
                                            UwbSimpleTestFragmentController
                                                    uwbSimpleTestFragmentController2 =
                                                            uwbSimpleTestPreference.mController;
                                            UwbSimpleTestFragmentController.stop();
                                            this.this$0.motionStop();
                                            break;
                                        }
                                        break;
                                    default:
                                        UwbSimpleTestPreference uwbSimpleTestPreference3 =
                                                this.this$0;
                                        if (!uwbSimpleTestPreference3.isRanging) {
                                            uwbSimpleTestPreference3.isInitiator = false;
                                            uwbSimpleTestPreference3.isRanging = true;
                                            UwbSimpleTestPreference.m1303$$Nest$mmotionStart(
                                                    uwbSimpleTestPreference3);
                                            UwbSimpleTestPreference uwbSimpleTestPreference4 =
                                                    this.this$0;
                                            UwbSimpleTestFragmentController
                                                    uwbSimpleTestFragmentController3 =
                                                            uwbSimpleTestPreference4.mController;
                                            UwbSimpleTestFragmentController.start(
                                                    uwbSimpleTestPreference4.isInitiator);
                                            break;
                                        } else if (!uwbSimpleTestPreference3.isInitiator) {
                                            UwbSimpleTestFragmentController
                                                    uwbSimpleTestFragmentController4 =
                                                            uwbSimpleTestPreference3.mController;
                                            UwbSimpleTestFragmentController.stop();
                                            this.this$0.motionStop();
                                            break;
                                        }
                                        break;
                                }
                            }
                        });
        if (!this.isUwbEnabled) {
            initViewDisenabled();
            return;
        }
        this.mLayout0.setVisibility(0);
        this.mLayout1.setVisibility(0);
        this.mLayout2.setVisibility(8);
    }

    @Override // com.samsung.android.settings.uwb.labs.view.uwbtest.UwbDataReceiver
    public final void onReceive(RangingReport rangingReport) {
        int meters =
                ((RangingMeasurement) rangingReport.getMeasurements().get(0))
                                        .getDistanceMeasurement()
                                != null
                        ? (int)
                                (((RangingMeasurement) rangingReport.getMeasurements().get(0))
                                                .getDistanceMeasurement()
                                                .getMeters()
                                        * 100.0d)
                        : -1;
        if (meters <= 0 || meters == 65535) {
            Log.i("UwbSimpleTestPreference", "Distance is not valid");
            return;
        }
        if (this.isFirstReceived) {
            this.isFirstReceived = false;
            ((Activity) this.mContext).runOnUiThread(new AnonymousClass4(this, 1));
        }
        String string = this.mContext.getString(R.string.uwb_distance_unit_cm);
        int i = meters / 100;
        int i2 = meters % 100;
        String num = Integer.toString(meters);
        if (i > 0) {
            string = this.mContext.getString(R.string.uwb_distance_unit_m);
            num = i + "." + String.format("%02d", Integer.valueOf(i2));
        }
        this.mStringRangingData =
                AbstractResolvableFuture$$ExternalSyntheticOutline0.m(num, string);
        ((Activity) this.mContext).runOnUiThread(new AnonymousClass4(this, 2));
        double d = meters / 1000.0d;
        if (d > 0.95d) {
            d = 0.95d;
        }
        double d2 = 1.0d - d;
        final int i3 = (int) (this.mOriginalWidth * d2);
        final int i4 = (int) (this.mOriginalHeight * d2);
        ((Activity) this.mContext)
                .runOnUiThread(
                        new Runnable() { // from class:
                                         // com.samsung.android.settings.uwb.labs.view.uwbtest.UwbSimpleTestPreference.5
                            @Override // java.lang.Runnable
                            public final void run() {
                                UwbSimpleTestPreference uwbSimpleTestPreference =
                                        UwbSimpleTestPreference.this;
                                if (uwbSimpleTestPreference.isInitiator) {
                                    ViewGroup.LayoutParams layoutParams =
                                            uwbSimpleTestPreference.mImageViewResponder
                                                    .getLayoutParams();
                                    layoutParams.width = i3;
                                    layoutParams.height = i4;
                                    UwbSimpleTestPreference.this.mImageViewResponder
                                            .setLayoutParams(layoutParams);
                                    ViewGroup.MarginLayoutParams marginLayoutParams =
                                            (ViewGroup.MarginLayoutParams)
                                                    UwbSimpleTestPreference.this.mImageViewResponder
                                                            .getLayoutParams();
                                    UwbSimpleTestPreference uwbSimpleTestPreference2 =
                                            UwbSimpleTestPreference.this;
                                    marginLayoutParams.setMargins(
                                            0,
                                            0,
                                            (int)
                                                    ((((uwbSimpleTestPreference2.mOriginalWidth
                                                                                    - i3)
                                                                            * 9.0d)
                                                                    / 10.0d)
                                                            + uwbSimpleTestPreference2
                                                                    .mOriginalImageMargin),
                                            0);
                                    UwbSimpleTestPreference.this.mImageViewResponder
                                            .setLayoutParams(marginLayoutParams);
                                    return;
                                }
                                ViewGroup.LayoutParams layoutParams2 =
                                        uwbSimpleTestPreference.mImageViewInitiator
                                                .getLayoutParams();
                                layoutParams2.width = i3;
                                layoutParams2.height = i4;
                                UwbSimpleTestPreference.this.mImageViewInitiator.setLayoutParams(
                                        layoutParams2);
                                ViewGroup.MarginLayoutParams marginLayoutParams2 =
                                        (ViewGroup.MarginLayoutParams)
                                                UwbSimpleTestPreference.this.mImageViewInitiator
                                                        .getLayoutParams();
                                UwbSimpleTestPreference uwbSimpleTestPreference3 =
                                        UwbSimpleTestPreference.this;
                                marginLayoutParams2.setMargins(
                                        (int)
                                                ((((uwbSimpleTestPreference3.mOriginalWidth - i3)
                                                                        * 9.0d)
                                                                / 10.0d)
                                                        + uwbSimpleTestPreference3
                                                                .mOriginalImageMargin),
                                        0,
                                        0,
                                        0);
                                UwbSimpleTestPreference.this.mImageViewInitiator.setLayoutParams(
                                        marginLayoutParams2);
                            }
                        });
    }

    @Override // com.samsung.android.settings.uwb.labs.view.uwbtest.UwbDataReceiver
    public final void onStateReceive(int i) {
        if (i == 3) {
            this.isRanging = false;
            this.isFirstReceived = true;
            UwbSimpleTestFragmentController.stop();
            ((Activity) this.mContext).runOnUiThread(new AnonymousClass4(this, 0));
            return;
        }
        if (i == -1) {
            this.isRanging = false;
            this.isFirstReceived = true;
        }
    }
}
