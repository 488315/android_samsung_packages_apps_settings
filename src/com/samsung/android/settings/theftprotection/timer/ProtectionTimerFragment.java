package com.samsung.android.settings.theftprotection.timer;

import android.app.Activity;
import android.content.Context;
import android.database.ContentObserver;
import android.graphics.Paint;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;

import com.samsung.android.settings.theftprotection.TheftProtectionConstants;
import com.samsung.android.settings.theftprotection.TheftProtectionUtils;
import com.samsung.android.settings.theftprotection.logging.Log;

import java.util.Date;
import java.util.Locale;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class ProtectionTimerFragment extends SettingsPreferenceFragment {
    public ProtectionTimerCircleView mCircleView;
    public View mContentView;
    public TextView mEndTimeView;
    public final AnonymousClass1 mObserver =
            new ContentObserver(
                    new Handler(
                            Looper
                                    .getMainLooper())) { // from class:
                                                         // com.samsung.android.settings.theftprotection.timer.ProtectionTimerFragment.1
                @Override // android.database.ContentObserver
                public final void onChange(boolean z) {
                    if (TheftProtectionUtils.isInTrustedPlace(
                            ProtectionTimerFragment.this.getContext())) {
                        ProtectionTimerFragment.this.finish();
                    }
                }
            };
    public ProtectionTimerTimeView mTimeView;
    public ProtectionTimerManager mTimerManager;

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 0;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ((SettingsActivity) getActivity()).hideAppBar();
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ProtectionTimerManager protectionTimerManager = ProtectionTimerManager.getInstance();
        this.mTimerManager = protectionTimerManager;
        protectionTimerManager.mTimer = this;
        getContentResolver()
                .registerContentObserver(
                        Settings.Secure.getUriFor("in_trusted_location"), false, this.mObserver);
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.protection_timer_layout, viewGroup, false);
        this.mContentView = inflate;
        this.mCircleView = (ProtectionTimerCircleView) inflate.findViewById(R.id.timer_circle_view);
        this.mTimeView =
                (ProtectionTimerTimeView) this.mContentView.findViewById(R.id.timer_time_view);
        this.mEndTimeView = (TextView) this.mContentView.findViewById(R.id.timer_end_time_text);
        TextView textView = (TextView) this.mContentView.findViewById(R.id.description);
        if (textView != null) {
            Bundle bundleExtra = getIntent().getBundleExtra("pp_security_delay_bundle");
            if (bundleExtra != null) {
                String string = bundleExtra.getString("pp_package_name");
                int i = bundleExtra.getInt("pp_security_delay_description_res");
                if (i >= 0) {
                    textView.setText(
                            TheftProtectionUtils.getStringFromPackage(getContext(), i, string));
                }
            }
            TheftProtectionUtils.setLinkableText(textView);
        }
        return this.mContentView;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        super.onDestroy();
        getContentResolver().unregisterContentObserver(this.mObserver);
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStart() {
        String str;
        super.onStart();
        long j =
                (getArguments() != null
                                ? getArguments().getLong("delay_expiration_time")
                                : Settings.Secure.getLong(
                                        getContext().getContentResolver(),
                                        "mandatory_biometrics_delay_time",
                                        0L))
                        - SystemClock.elapsedRealtime();
        TheftProtectionUtils.isSecurityDelayTest();
        ProtectionTimerData.sInputMillis = TheftProtectionConstants.SECURITY_DELAY_DURATION_MILLIS;
        ProtectionTimerData.sRemainMillis = j;
        ProtectionTimerManager protectionTimerManager = this.mTimerManager;
        protectionTimerManager.getClass();
        Log.d(
                "ProtectionTimerManager",
                "startTimer() / millis = " + j + " / this = " + protectionTimerManager);
        StringBuilder sb = new StringBuilder("setCountDownTimer() / time= ");
        sb.append(j);
        sb.append(" / COUNT_DOWN_ADJUSTMENT = 0");
        Log.d("ProtectionTimerManager", sb.toString());
        long j2 = TheftProtectionConstants.SECOND_MILLIS;
        long j3 = j / j2;
        if (j3 > 50) {
            j3 = 50;
        } else if (j3 < 10) {
            j3 = 10;
        }
        ProtectionTimerManager.sCountDownTimer =
                new ProtectionTimerManager.NoLeakCountDownTimer(protectionTimerManager, j, j3);
        StringBuilder sb2 = new StringBuilder("startCountDownTimer() / ");
        Object obj = ProtectionTimerManager.sCountDownTimer;
        if (obj == null) {
            obj = "null";
        }
        sb2.append(obj);
        Log.d("ProtectionTimerManager", sb2.toString());
        ProtectionTimerManager.NoLeakCountDownTimer noLeakCountDownTimer =
                ProtectionTimerManager.sCountDownTimer;
        if (noLeakCountDownTimer != null) {
            noLeakCountDownTimer.start();
        }
        ProtectionTimerCircleView protectionTimerCircleView = this.mCircleView;
        if (protectionTimerCircleView != null) {
            long j4 = ProtectionTimerData.sInputMillis;
            long j5 = ProtectionTimerData.sRemainMillis;
            Log.d("ProtectionTimerCircleView", "init()");
            if (protectionTimerCircleView.mCirclePaint == null) {
                protectionTimerCircleView.mCirclePaint = new Paint();
            }
            Paint paint = protectionTimerCircleView.mCirclePaint;
            Paint.Style style = Paint.Style.STROKE;
            paint.setStyle(style);
            protectionTimerCircleView.mCirclePaint.setAntiAlias(true);
            protectionTimerCircleView.mCirclePaint.setStrokeCap(Paint.Cap.ROUND);
            if (protectionTimerCircleView.mStartEffectPaint == null) {
                protectionTimerCircleView.mStartEffectPaint = new Paint();
            }
            protectionTimerCircleView.mStartEffectPaint.setStyle(style);
            protectionTimerCircleView.mStartEffectPaint.setAntiAlias(true);
            if (protectionTimerCircleView.mEndEffectPaint == null) {
                protectionTimerCircleView.mEndEffectPaint = new Paint();
            }
            protectionTimerCircleView.mEndEffectPaint.setStyle(style);
            protectionTimerCircleView.mEndEffectPaint.setAntiAlias(true);
            if (protectionTimerCircleView.mCircleBGPaint == null) {
                protectionTimerCircleView.mCircleBGPaint = new Paint();
            }
            protectionTimerCircleView.mCircleBGPaint.setAntiAlias(true);
            protectionTimerCircleView.mCircleBGPaint.setStyle(style);
            if (protectionTimerCircleView.mPointerPaint == null) {
                protectionTimerCircleView.mPointerPaint = new Paint();
            }
            protectionTimerCircleView.mPointerPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            protectionTimerCircleView.mPointerPaint.setAntiAlias(true);
            protectionTimerCircleView.mCircleBackgroundColor =
                    protectionTimerCircleView
                            .getResources()
                            .getColor(R.color.protection_timer_circle_bg_color, null);
            protectionTimerCircleView.mCircleOnGoingColor =
                    protectionTimerCircleView
                            .getResources()
                            .getColor(R.color.protection_timer_circle_ongoing_color, null);
            protectionTimerCircleView.mCircleBGPaint.setColor(
                    protectionTimerCircleView.mCircleBackgroundColor);
            ProtectionTimerCircleView.updateTime(j4, j5);
            protectionTimerCircleView.setCircleSize();
            protectionTimerCircleView.setCircleStrokeWidth();
            ((Activity) protectionTimerCircleView.getContext())
                    .getWindowManager()
                    .getDefaultDisplay()
                    .getMetrics(new DisplayMetrics());
            ProtectionTimerCircleView protectionTimerCircleView2 = this.mCircleView;
            protectionTimerCircleView2.getClass();
            Log.d("ProtectionTimerCircleView", "Start()");
            Log.d("ProtectionTimerCircleView", "setDrawInterval()");
            long j6 = (ProtectionTimerCircleView.sInputMillis + 180) / j2;
            ProtectionTimerCircleView.sDrawInterval = j6;
            if (j6 > 50) {
                ProtectionTimerCircleView.sDrawInterval = 50L;
            } else if (j6 < 10) {
                ProtectionTimerCircleView.sDrawInterval = 10L;
            }
            protectionTimerCircleView2.invalidate();
            ProtectionTimerCircleView.NoLeakHandler noLeakHandler =
                    protectionTimerCircleView2.mHandler;
            if (noLeakHandler != null) {
                noLeakHandler.sendEmptyMessageDelayed(0, ProtectionTimerCircleView.sDrawInterval);
            }
        }
        this.mTimeView.setTimeTextView(ProtectionTimerData.sRemainMillis);
        TextView textView = this.mEndTimeView;
        Context context = getContext();
        long currentTimeMillis = System.currentTimeMillis() + ProtectionTimerData.sRemainMillis;
        boolean is24HourFormat = DateFormat.is24HourFormat(context);
        String[] strArr = {"ko", "ja", "iw", "ur", "zh", "my", "hu"};
        String language = Locale.getDefault().getLanguage();
        int i = 0;
        while (true) {
            if (i >= 7) {
                break;
            }
            if (!TextUtils.equals(strArr[i], language)) {
                i++;
            } else if (!TextUtils.equals(Locale.getDefault().getLanguage(), "ur")) {
                str = "a h:mm";
            }
        }
        str = "h:mm a";
        if (is24HourFormat) {
            str = "HH:mm";
        }
        textView.setText(
                new SimpleDateFormat(str, Locale.getDefault())
                        .format(new Date(currentTimeMillis))
                        .replace("AM", "am")
                        .replace("PM", "pm"));
        ProtectionTimerManager protectionTimerManager2 = this.mTimerManager;
        protectionTimerManager2.getClass();
        Log.d("ProtectionTimerManager", "pauseTimer() : false");
        protectionTimerManager2.mIsPause = false;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onStop() {
        super.onStop();
        this.mTimerManager.getClass();
        Log.d("ProtectionTimerManager", "stopTimer()");
        ProtectionTimerManager.NoLeakCountDownTimer noLeakCountDownTimer =
                ProtectionTimerManager.sCountDownTimer;
        if (noLeakCountDownTimer != null) {
            noLeakCountDownTimer.cancel();
            ProtectionTimerManager.sCountDownTimer = null;
        }
        ProtectionTimerManager protectionTimerManager = this.mTimerManager;
        protectionTimerManager.getClass();
        Log.d("ProtectionTimerManager", "pauseTimer() : true");
        protectionTimerManager.mIsPause = true;
    }
}
