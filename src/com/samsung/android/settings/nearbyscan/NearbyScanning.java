package com.samsung.android.settings.nearbyscan;

import android.content.ActivityNotFoundException;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.secutil.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;

import com.android.settings.DefaultRingtonePreference$$ExternalSyntheticOutline0;
import com.android.settings.R;
import com.android.settings.SettingsActivity;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import com.android.settings.widget.SettingsMainSwitchBar;

import com.samsung.android.settings.logging.LoggingHelper;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class NearbyScanning extends SettingsPreferenceFragment {
    public TextView mBadge;
    public Context mContext;
    public ImageView mNearbyHelpImg;
    public NearbyScanningEnabler mNearbyScanningEnabler;
    public NestedScrollView mNestedScrollView = null;
    public SettingsMainSwitchBar mSwitchBar;
    public View mSwitchBarChildView;
    public View mView;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    /* renamed from: com.samsung.android.settings.nearbyscan.NearbyScanning$2, reason: invalid class name */
    public final class AnonymousClass2 implements Runnable {
        public final /* synthetic */ AnimationDrawable val$ani;

        public AnonymousClass2(AnimationDrawable animationDrawable) {
            this.val$ani = animationDrawable;
        }

        @Override // java.lang.Runnable
        public final void run() {
            this.val$ani.start();
        }
    }

    public final Boolean checkApkUpdateStatus() {
        try {
            ContentProviderClient acquireUnstableContentProviderClient =
                    this.mContext
                            .getContentResolver()
                            .acquireUnstableContentProviderClient(
                                    Uri.parse(
                                            "content://com.samsung.android.easysetup.provider.BadgeContentProvider"));
            if (acquireUnstableContentProviderClient != null) {
                try {
                    Bundle call =
                            acquireUnstableContentProviderClient.call(
                                    "is_update_available_method",
                                    this.mContext.getPackageName(),
                                    null);
                    if (call != null && call.getBoolean("key_is_update_available", false)) {
                        Boolean bool = Boolean.TRUE;
                        acquireUnstableContentProviderClient.close();
                        return bool;
                    }
                } finally {
                }
            }
            if (acquireUnstableContentProviderClient != null) {
                acquireUnstableContentProviderClient.close();
            }
        } catch (RemoteException e) {
            Log.secD("NearbyScanning", e.getMessage());
        }
        return Boolean.FALSE;
    }

    public final View createNeaybyScanningView(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        if (viewGroup != null) {
            viewGroup.removeAllViewsInLayout();
        }
        if (Utils.isTablet()) {
            Log.secD("NearbyScanning", "createNeaybyScanningView for Tablet");
            this.mView =
                    layoutInflater.inflate(
                            R.layout.sec_nearby_device_scanning_help_tablet, viewGroup);
        } else {
            Log.secD("NearbyScanning", "createNeaybyScanningView");
            this.mView =
                    layoutInflater.inflate(R.layout.sec_nearby_device_scanning_help, viewGroup);
        }
        NestedScrollView nestedScrollView = (NestedScrollView) this.mView.findViewById(R.id.scroll);
        this.mNestedScrollView = nestedScrollView;
        nestedScrollView.semSetRoundedCorners(15);
        this.mNestedScrollView.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mNestedScrollView.setScrollBarStyle(33554432);
        this.mNestedScrollView.seslSetFillHorizontalPaddingEnabled(
                getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        LinearLayout linearLayout =
                (LinearLayout) this.mView.findViewById(R.id.nearby_layout_container);
        linearLayout.semSetRoundedCorners(15);
        linearLayout.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        FrameLayout frameLayout = (FrameLayout) this.mView.findViewById(R.id.about_layout);
        frameLayout.semSetRoundedCorners(15);
        frameLayout.semSetRoundedCornerColor(
                15, getResources().getColor(R.color.sec_widget_round_and_bgcolor));
        this.mNearbyHelpImg = (ImageView) this.mView.findViewById(R.id.nearby_help_img);
        Context context = this.mContext;
        if (context != null && (context.getResources().getConfiguration().uiMode & 48) == 32) {
            this.mNearbyHelpImg.setImageDrawable(
                    this.mContext.getDrawable(R.drawable.sec_nearby_device_scanning_anim_dark));
        } else {
            this.mNearbyHelpImg.setImageDrawable(
                    this.mContext.getDrawable(R.drawable.sec_nearby_device_scanning_anim));
        }
        ((FrameLayout) this.mView.findViewById(R.id.about_layout))
                .setOnClickListener(
                        new View.OnClickListener() { // from class:
                            // com.samsung.android.settings.nearbyscan.NearbyScanning.1
                            @Override // android.view.View.OnClickListener
                            public final void onClick(View view) {
                                Intent intent = new Intent();
                                DefaultRingtonePreference$$ExternalSyntheticOutline0.m(
                                        "com.samsung.android.easysetup",
                                        "com.samsung.android.easysetup.appupdate.view.EasySetupAboutSetting",
                                        intent);
                                try {
                                    intent.setFlags(268435456);
                                    NearbyScanning.this.mContext.startActivity(intent);
                                } catch (ActivityNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
        this.mBadge = (TextView) this.mView.findViewById(R.id.badge);
        if (checkApkUpdateStatus().booleanValue()) {
            this.mBadge.setVisibility(0);
        } else {
            this.mBadge.setVisibility(8);
        }
        return this.mView;
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return 3700;
    }

    @Override // com.android.settings.SettingsPreferenceFragment, androidx.fragment.app.Fragment
    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        Log.secD("NearbyScanning", "onActivityCreated");
        NearbyScanningEnabler nearbyScanningEnabler =
                new NearbyScanningEnabler(
                        this.mContext, ((SettingsActivity) getActivity()).mMainSwitch);
        this.mNearbyScanningEnabler = nearbyScanningEnabler;
        nearbyScanningEnabler.init();
        SettingsMainSwitchBar settingsMainSwitchBar =
                this.mNearbyScanningEnabler.mNearbyScanningSwitchBar;
        this.mSwitchBar = settingsMainSwitchBar;
        if (settingsMainSwitchBar != null) {
            int childCount = settingsMainSwitchBar.getChildCount();
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    break;
                }
                if (this.mSwitchBar.getChildAt(i) instanceof LinearLayout) {
                    this.mSwitchBarChildView = this.mSwitchBar.getChildAt(i);
                    break;
                }
                i++;
            }
            View view = this.mSwitchBarChildView;
            if (view != null) {
                view.setOnTouchListener(
                        new View
                                .OnTouchListener() { // from class:
                                                     // com.samsung.android.settings.nearbyscan.NearbyScanning.3
                            public Rect rect;

                            @Override // android.view.View.OnTouchListener
                            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                                Rect rect;
                                Context context;
                                int action = motionEvent.getAction();
                                if (action == 0) {
                                    this.rect =
                                            new Rect(
                                                    view2.getLeft(),
                                                    view2.getTop(),
                                                    view2.getRight(),
                                                    view2.getBottom());
                                    return false;
                                }
                                if (action != 1
                                        || (rect = this.rect) == null
                                        || !rect.contains(
                                                view2.getLeft() + ((int) motionEvent.getX()),
                                                view2.getTop() + ((int) motionEvent.getY()))
                                        || (context = NearbyScanning.this.mContext) == null) {
                                    return false;
                                }
                                int dBInt =
                                        NearbyScanningUtil.getDBInt(context.getContentResolver());
                                Log.secD(
                                        "NearbyScanning",
                                        "SwitchBar, send Event Log : 3701, on/off ("
                                                .concat(dBInt == 1 ? "off 0)" : "on 1000)"));
                                LoggingHelper.insertEventLogging(
                                        3700, 3701, dBInt == 1 ? 0L : 1000L);
                                return false;
                            }
                        });
                return;
            }
        }
        Log.secD("NearbyScanning", "onActivityCreated.setLayoutOnClickForLogging Failed");
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment,
              // android.content.ComponentCallbacks
    public final void onConfigurationChanged(Configuration configuration) {
        AnimationDrawable animationDrawable;
        super.onConfigurationChanged(configuration);
        Log.secD("NearbyScanning", "onConfigurationChanged");
        createNeaybyScanningView(LayoutInflater.from(this.mContext), (ViewGroup) getView());
        if (Settings.System.getInt(getContext().getContentResolver(), "remove_animations", 0) == 1
                || (animationDrawable = (AnimationDrawable) this.mNearbyHelpImg.getDrawable())
                        == null) {
            return;
        }
        this.mNearbyHelpImg.post(new AnonymousClass2(animationDrawable));
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.secD("NearbyScanning", "onCreate");
        setHasOptionsMenu(false);
        Context context = getContext();
        if (context != null && !NearbyScanningUtil.isBeaconManagerInstall(context)) {
            NearbyScanningUtil.setDBInt(context, 0);
            Toast.makeText(context, "Not Installed BeaconManager", 1).show();
        }
        this.mContext = context;
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        return createNeaybyScanningView(layoutInflater, null);
    }

    @Override // androidx.preference.PreferenceFragmentCompat, androidx.fragment.app.Fragment
    public final void onDestroyView() {
        super.onDestroyView();
        NearbyScanningEnabler nearbyScanningEnabler = this.mNearbyScanningEnabler;
        SettingsMainSwitchBar settingsMainSwitchBar =
                nearbyScanningEnabler.mNearbyScanningSwitchBar;
        if (settingsMainSwitchBar != null) {
            settingsMainSwitchBar.removeOnSwitchChangeListener(
                    nearbyScanningEnabler.mOnCheckedChangeListener);
            settingsMainSwitchBar.hide();
        }
        View view = this.mSwitchBarChildView;
        if (view != null) {
            view.setOnTouchListener(null);
        }
    }

    @Override // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onPause() {
        super.onPause();
        Log.secD("NearbyScanning", "onPause");
        NearbyScanningEnabler nearbyScanningEnabler = this.mNearbyScanningEnabler;
        if (nearbyScanningEnabler != null) {
            nearbyScanningEnabler.onPause();
        }
    }

    @Override // com.android.settings.SettingsPreferenceFragment,
              // com.android.settings.core.InstrumentedPreferenceFragment,
              // com.android.settingslib.core.lifecycle.ObservablePreferenceFragment,
              // androidx.fragment.app.Fragment
    public final void onResume() {
        AnimationDrawable animationDrawable;
        super.onResume();
        Log.secD("NearbyScanning", "onResume");
        NearbyScanningEnabler nearbyScanningEnabler = this.mNearbyScanningEnabler;
        if (nearbyScanningEnabler != null) {
            nearbyScanningEnabler.onResume();
        }
        if (this.mBadge != null) {
            if (checkApkUpdateStatus().booleanValue()) {
                this.mBadge.setVisibility(0);
            } else {
                this.mBadge.setVisibility(8);
            }
        }
        if (Settings.System.getInt(getContext().getContentResolver(), "remove_animations", 0) == 1
                || (animationDrawable = (AnimationDrawable) this.mNearbyHelpImg.getDrawable())
                        == null) {
            return;
        }
        this.mNearbyHelpImg.post(new AnonymousClass2(animationDrawable));
    }
}
