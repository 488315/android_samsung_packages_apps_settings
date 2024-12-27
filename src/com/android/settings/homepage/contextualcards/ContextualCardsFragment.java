package com.android.settings.homepage.contextualcards;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment$$ExternalSyntheticOutline0;
import androidx.loader.app.LoaderManager;
import androidx.loader.app.LoaderManagerImpl;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.android.settings.R;
import com.android.settings.core.InstrumentedFragment;
import com.android.settings.homepage.contextualcards.slices.BluetoothUpdateWorker;
import com.android.settings.homepage.contextualcards.slices.BluetoothUpdateWorker$LoadBtManagerHandler$$ExternalSyntheticLambda0;
import com.android.settings.homepage.contextualcards.slices.SwipeDismissalDelegate;
import com.android.settings.overlay.FeatureFactoryImpl;
import com.android.settings.slices.SlicesFeatureProviderImpl;

import com.sec.ims.volte2.data.VolteConstants;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class ContextualCardsFragment extends InstrumentedFragment
        implements FocusRecyclerView.FocusListener {
    public static final boolean DEBUG = Build.IS_DEBUGGABLE;
    static boolean sRestartLoaderNeeded;
    public FocusRecyclerView mCardsContainer;
    public ContextualCardManager mContextualCardManager;
    public ContextualCardsAdapter mContextualCardsAdapter;
    BroadcastReceiver mKeyEventReceiver;
    BroadcastReceiver mScreenOffReceiver;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class KeyEventReceiver extends BroadcastReceiver {
        public KeyEventReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent == null
                    || !"android.intent.action.CLOSE_SYSTEM_DIALOGS".equals(intent.getAction())) {
                return;
            }
            String stringExtra = intent.getStringExtra("reason");
            if ("recentapps".equals(stringExtra) || "homekey".equals(stringExtra)) {
                if (ContextualCardsFragment.DEBUG) {
                    DialogFragment$$ExternalSyntheticOutline0.m(
                            "key pressed = ", stringExtra, "ContextualCardsFragment");
                }
                ContextualCardsFragment contextualCardsFragment = ContextualCardsFragment.this;
                contextualCardsFragment.getClass();
                ContextualCardsFragment.sRestartLoaderNeeded = true;
                if (contextualCardsFragment.mScreenOffReceiver != null) {
                    contextualCardsFragment
                            .getActivity()
                            .unregisterReceiver(contextualCardsFragment.mScreenOffReceiver);
                    contextualCardsFragment.mScreenOffReceiver = null;
                }
                FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
                if (featureFactoryImpl == null) {
                    throw new UnsupportedOperationException("No feature factory configured");
                }
                SlicesFeatureProviderImpl slicesFeatureProvider =
                        featureFactoryImpl.getSlicesFeatureProvider();
                slicesFeatureProvider.getClass();
                slicesFeatureProvider.mUiSessionToken = SystemClock.elapsedRealtime();
            }
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public class ScreenOffReceiver extends BroadcastReceiver {
        public ScreenOffReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent == null || !"android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                return;
            }
            if (ContextualCardsFragment.DEBUG) {
                Log.d("ContextualCardsFragment", "screen off");
            }
            ContextualCardsFragment contextualCardsFragment = ContextualCardsFragment.this;
            contextualCardsFragment.getClass();
            ContextualCardsFragment.sRestartLoaderNeeded = true;
            if (contextualCardsFragment.mScreenOffReceiver != null) {
                contextualCardsFragment
                        .getActivity()
                        .unregisterReceiver(contextualCardsFragment.mScreenOffReceiver);
                contextualCardsFragment.mScreenOffReceiver = null;
            }
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            SlicesFeatureProviderImpl slicesFeatureProvider =
                    featureFactoryImpl.getSlicesFeatureProvider();
            slicesFeatureProvider.getClass();
            slicesFeatureProvider.mUiSessionToken = SystemClock.elapsedRealtime();
        }
    }

    @Override // com.android.settingslib.core.instrumentation.Instrumentable
    public final int getMetricsCategory() {
        return VolteConstants.ErrorCode.SERVER_ERROR;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Context context = getContext();
        if (bundle == null) {
            FeatureFactoryImpl featureFactoryImpl = FeatureFactoryImpl._factory;
            if (featureFactoryImpl == null) {
                throw new UnsupportedOperationException("No feature factory configured");
            }
            SlicesFeatureProviderImpl slicesFeatureProvider =
                    featureFactoryImpl.getSlicesFeatureProvider();
            slicesFeatureProvider.getClass();
            slicesFeatureProvider.mUiSessionToken = SystemClock.elapsedRealtime();
            Context context2 = getContext();
            if (BluetoothUpdateWorker.sLocalBluetoothManager == null) {
                BluetoothUpdateWorker.LoadBtManagerHandler m939$$Nest$smgetInstance =
                        BluetoothUpdateWorker.LoadBtManagerHandler.m939$$Nest$smgetInstance(
                                context2);
                BluetoothUpdateWorker$LoadBtManagerHandler$$ExternalSyntheticLambda0
                        bluetoothUpdateWorker$LoadBtManagerHandler$$ExternalSyntheticLambda0 =
                                m939$$Nest$smgetInstance.mLoadBtManagerTask;
                if (!m939$$Nest$smgetInstance.hasCallbacks(
                        bluetoothUpdateWorker$LoadBtManagerHandler$$ExternalSyntheticLambda0)) {
                    m939$$Nest$smgetInstance.post(
                            bluetoothUpdateWorker$LoadBtManagerHandler$$ExternalSyntheticLambda0);
                }
            }
        }
        this.mContextualCardManager = new ContextualCardManager(context, this.mLifecycle, bundle);
        this.mKeyEventReceiver = new KeyEventReceiver();
    }

    @Override // androidx.fragment.app.Fragment
    public final View onCreateView(
            LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        Context context = getContext();
        View inflate = layoutInflater.inflate(R.layout.settings_homepage, viewGroup, false);
        this.mCardsContainer = (FocusRecyclerView) inflate.findViewById(R.id.card_container);
        getActivity();
        this.mCardsContainer.setLayoutManager(new GridLayoutManager(2, 0));
        this.mContextualCardsAdapter =
                new ContextualCardsAdapter(context, this, this.mContextualCardManager);
        this.mCardsContainer.setItemAnimator(null);
        this.mCardsContainer.setAdapter(this.mContextualCardsAdapter);
        this.mContextualCardManager.mListener = this.mContextualCardsAdapter;
        this.mCardsContainer.setListener(this);
        new ItemTouchHelper(new SwipeDismissalDelegate(this.mContextualCardsAdapter))
                .attachToRecyclerView(this.mCardsContainer);
        return inflate;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onDestroy() {
        if (this.mScreenOffReceiver != null) {
            getActivity().unregisterReceiver(this.mScreenOffReceiver);
            this.mScreenOffReceiver = null;
        }
        super.onDestroy();
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onStart() {
        super.onStart();
        if (this.mScreenOffReceiver == null) {
            this.mScreenOffReceiver = new ScreenOffReceiver();
            getActivity()
                    .registerReceiver(
                            this.mScreenOffReceiver,
                            new IntentFilter("android.intent.action.SCREEN_OFF"));
        }
        getActivity()
                .registerReceiver(
                        this.mKeyEventReceiver,
                        new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"),
                        2);
        ContextualCardManager contextualCardManager = this.mContextualCardManager;
        LoaderManagerImpl loaderManager = LoaderManager.getInstance(this);
        boolean z = sRestartLoaderNeeded;
        if (contextualCardManager
                .mContext
                .getResources()
                .getBoolean(R.bool.config_use_legacy_suggestion)) {
            Log.w(
                    "ContextualCardManager",
                    "Legacy suggestion contextual card enabled, skipping contextual cards.");
        } else {
            contextualCardManager.mStartTime = System.currentTimeMillis();
            Context context = contextualCardManager.mContext;
            ContextualCardManager.CardContentLoaderCallbacks cardContentLoaderCallbacks =
                    new ContextualCardManager.CardContentLoaderCallbacks();
            cardContentLoaderCallbacks.mContext = context.getApplicationContext();
            cardContentLoaderCallbacks.mListener = contextualCardManager;
            if (z) {
                contextualCardManager.mIsFirstLaunch = true;
                loaderManager.restartLoader(1, null, cardContentLoaderCallbacks);
            } else {
                loaderManager.initLoader(1, null, cardContentLoaderCallbacks);
            }
        }
        sRestartLoaderNeeded = false;
    }

    @Override // com.android.settingslib.core.lifecycle.ObservableFragment,
              // androidx.fragment.app.Fragment
    public final void onStop() {
        getActivity().unregisterReceiver(this.mKeyEventReceiver);
        super.onStop();
    }
}
