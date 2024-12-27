package com.google.android.setupcompat.internal;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;

import com.google.android.setupcompat.logging.CustomEvent;
import com.google.android.setupcompat.logging.MetricKey;
import com.google.android.setupcompat.logging.SetupMetricsLogger;

import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class LifecycleFragment extends Fragment {
    public static final /* synthetic */ int $r8$clinit = 0;
    public long durationInNanos = 0;
    public MetricKey metricKey;
    public long startInNanos;

    public LifecycleFragment() {
        setRetainInstance(true);
    }

    @Override // android.app.Fragment
    public final void onAttach(Context context) {
        super.onAttach(context);
        this.metricKey = MetricKey.get(getActivity(), "ScreenDuration");
    }

    @Override // android.app.Fragment
    public final void onDetach() {
        super.onDetach();
        Activity activity = getActivity();
        MetricKey metricKey = this.metricKey;
        long millis = TimeUnit.NANOSECONDS.toMillis(this.durationInNanos);
        int i = SetupMetricsLogger.$r8$clinit;
        Preconditions.checkNotNull(activity, "Context cannot be null.");
        Preconditions.checkNotNull(metricKey, "Timer name cannot be null.");
        Preconditions.checkArgument("Duration cannot be negative.", millis >= 0);
        SetupCompatServiceInvoker setupCompatServiceInvoker =
                SetupCompatServiceInvoker.get(activity);
        Bundle bundle = new Bundle();
        bundle.putParcelable("MetricKey_bundle", MetricKey.fromMetricKey(metricKey));
        bundle.putLong("timeMillis", millis);
        setupCompatServiceInvoker.logMetricEvent(2, bundle);
    }

    @Override // android.app.Fragment
    public final void onPause() {
        super.onPause();
        this.durationInNanos =
                (ClockProvider.ticker.read() - this.startInNanos) + this.durationInNanos;
    }

    @Override // android.app.Fragment
    public final void onResume() {
        super.onResume();
        this.startInNanos = ClockProvider.ticker.read();
        PersistableBundle persistableBundle = new PersistableBundle();
        persistableBundle.putLong("onScreenResume", System.nanoTime());
        Activity activity = getActivity();
        MetricKey metricKey = MetricKey.get(getActivity(), "ScreenActivity");
        Parcelable.Creator<CustomEvent> creator = CustomEvent.CREATOR;
        PersistableBundle persistableBundle2 = PersistableBundle.EMPTY;
        long millis = TimeUnit.NANOSECONDS.toMillis(ClockProvider.ticker.read());
        PersistableBundles.assertIsValid(persistableBundle);
        PersistableBundles.assertIsValid(persistableBundle2);
        SetupMetricsLogger.logCustomEvent(
                activity,
                new CustomEvent(millis, metricKey, persistableBundle, persistableBundle2));
    }
}
