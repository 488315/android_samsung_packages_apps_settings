package com.android.settings.network;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.SubscriptionManager;
import android.util.ArrayMap;
import android.util.Log;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceScreen;

import com.android.settings.R;
import com.android.settings.core.BasePreferenceController;
import com.android.settings.core.SubSettingLauncher;
import com.android.settings.network.telephony.MobileNetworkSettings;
import com.android.settings.network.telephony.MobileNetworkUtils;
import com.android.settingslib.RestrictedPreference;
import com.android.settingslib.mobile.dataservice.SubscriptionInfoEntity;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.cube.ControlResult;
import com.samsung.android.settings.cube.ControlValue;
import com.samsung.android.settings.cube.Controllable$ControllableType;

import kotlin.jvm.internal.Intrinsics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public class NetworkProviderSimListController extends BasePreferenceController
        implements DefaultLifecycleObserver,
                MobileNetworkRepository.MobileNetworkCallback,
                DefaultSubscriptionReceiver.DefaultSubscriptionListener {
    private static final String TAG = "NetworkProviderSimListController";
    private final DefaultSubscriptionReceiver mDataSubscriptionChangedReceiver;
    private final MobileNetworkRepository mMobileNetworkRepository;
    private PreferenceCategory mPreferenceCategory;
    private Map<Integer, RestrictedPreference> mPreferences;
    private List<SubscriptionInfoEntity> mSubInfoEntityList;
    private final SubscriptionManager mSubscriptionManager;

    public NetworkProviderSimListController(Context context, String str) {
        super(context, str);
        this.mSubInfoEntityList = new ArrayList();
        this.mSubscriptionManager =
                (SubscriptionManager) context.getSystemService(SubscriptionManager.class);
        this.mPreferences = new ArrayMap();
        this.mMobileNetworkRepository = MobileNetworkRepository.getInstance(context);
        this.mDataSubscriptionChangedReceiver = new DefaultSubscriptionReceiver(context, this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean lambda$update$0(
            SubscriptionInfoEntity subscriptionInfoEntity,
            boolean z,
            int i,
            Preference preference) {
        if (!subscriptionInfoEntity.isEmbedded && !z) {
            SubscriptionManager subscriptionManager = this.mSubscriptionManager;
            Pattern pattern = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
            if (!subscriptionManager.canDisablePhysicalSubscription()) {
                Context context = this.mContext;
                if (!SubscriptionManager.isUsableSubscriptionId(i)) {
                    Log.i(
                            "SubscriptionUtil",
                            "Unable to toggle subscription due to invalid subscription ID.");
                    return true;
                }
                SimOnboardingService simOnboardingService = SimOnboardingActivity.onboardingService;
                Intrinsics.checkNotNullParameter(context, "context");
                Intent intent = new Intent(context, (Class<?>) SimOnboardingActivity.class);
                intent.putExtra("sub_id", i);
                intent.setFlags(268435456);
                context.startActivity(intent);
                return true;
            }
        }
        Context context2 = this.mContext;
        Drawable drawable = MobileNetworkUtils.EMPTY_DRAWABLE;
        int intValue = Integer.valueOf(subscriptionInfoEntity.subId).intValue();
        if (!subscriptionInfoEntity.isValidSubscription) {
            Log.d("MobileNetworkUtils", "launchMobileNetworkSettings fail, subId is invalid.");
            return true;
        }
        Log.d(
                "MobileNetworkUtils",
                "launchMobileNetworkSettings for SubscriptionInfoEntity subId: " + intValue);
        Bundle bundle = new Bundle();
        bundle.putInt("android.provider.extra.SUB_ID", intValue);
        SubSettingLauncher subSettingLauncher = new SubSettingLauncher(context2);
        String str = subscriptionInfoEntity.uniqueName;
        SubSettingLauncher.LaunchRequest launchRequest = subSettingLauncher.mLaunchRequest;
        launchRequest.mTitle = str;
        launchRequest.mDestinationName = MobileNetworkSettings.class.getCanonicalName();
        launchRequest.mSourceMetricsCategory = 0;
        launchRequest.mArguments = bundle;
        subSettingLauncher.launch();
        return true;
    }

    private void update() {
        if (this.mPreferenceCategory == null) {
            return;
        }
        Map<Integer, RestrictedPreference> map = this.mPreferences;
        this.mPreferences = new ArrayMap();
        for (final SubscriptionInfoEntity subscriptionInfoEntity :
                getAvailablePhysicalSubscriptions()) {
            final int parseInt = Integer.parseInt(subscriptionInfoEntity.subId);
            RestrictedPreference remove = map.remove(Integer.valueOf(parseInt));
            if (remove == null) {
                remove = new RestrictedPreference(this.mPreferenceCategory.getContext());
                this.mPreferenceCategory.addPreference(remove);
            }
            String str = subscriptionInfoEntity.uniqueName;
            remove.setTitle(str);
            remove.setSummary(getSummary(subscriptionInfoEntity, str));
            remove.setIcon(
                    this.mContext.getDrawable(
                            subscriptionInfoEntity.isEmbedded
                                    ? R.drawable.ic_sim_card_download
                                    : R.drawable.ic_sim_card));
            if (SubscriptionUtil.isConvertedPsimSubscription(this.mContext, parseInt)) {
                remove.setEnabled(false);
            } else {
                final boolean z = subscriptionInfoEntity.isActiveSubscriptionId;
                remove.setOnPreferenceClickListener(
                        new Preference
                                .OnPreferenceClickListener() { // from class:
                                                               // com.android.settings.network.NetworkProviderSimListController$$ExternalSyntheticLambda0
                            @Override // androidx.preference.Preference.OnPreferenceClickListener
                            public final boolean onPreferenceClick(Preference preference) {
                                boolean lambda$update$0;
                                lambda$update$0 =
                                        NetworkProviderSimListController.this.lambda$update$0(
                                                subscriptionInfoEntity, z, parseInt, preference);
                                return lambda$update$0;
                            }
                        });
            }
            this.mPreferences.put(Integer.valueOf(parseInt), remove);
        }
        Iterator<RestrictedPreference> it = map.values().iterator();
        while (it.hasNext()) {
            this.mPreferenceCategory.removePreference(it.next());
        }
    }

    @Override // com.android.settings.core.BasePreferenceController,
              // com.android.settingslib.core.AbstractPreferenceController
    public void displayPreference(PreferenceScreen preferenceScreen) {
        super.displayPreference(preferenceScreen);
        this.mPreferenceCategory =
                (PreferenceCategory) preferenceScreen.findPreference(getPreferenceKey());
        update();
    }

    @Override // com.android.settings.core.BasePreferenceController
    public int getAvailabilityStatus() {
        return getAvailablePhysicalSubscriptions().isEmpty() ? 2 : 0;
    }

    public List<SubscriptionInfoEntity> getAvailablePhysicalSubscriptions() {
        return new ArrayList(this.mSubInfoEntityList);
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ Class getBackgroundWorkerClass() {
        return null;
    }

    public List getBackupKeys() {
        return new ArrayList();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ IntentFilter getIntentFilter() {
        return null;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Intent getLaunchIntent() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ int getSliceHighlightMenuRes() {
        return 0;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ String getStatusText() {
        return null;
    }

    public CharSequence getSummary(
            SubscriptionInfoEntity subscriptionInfoEntity, CharSequence charSequence) {
        String string;
        if (!subscriptionInfoEntity.isEmbedded
                && SubscriptionUtil.isConvertedPsimSubscription(
                        this.mContext, subscriptionInfoEntity.getSubId())) {
            return this.mContext.getString(R.string.sim_category_converted_sim);
        }
        if (!subscriptionInfoEntity.isActiveSubscriptionId) {
            if (!subscriptionInfoEntity.isEmbedded) {
                SubscriptionManager subscriptionManager = this.mSubscriptionManager;
                Pattern pattern = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
                if (!subscriptionManager.canDisablePhysicalSubscription()) {
                    return this.mContext.getString(
                            R.string.mobile_network_tap_to_activate, charSequence);
                }
            }
            return this.mContext.getString(R.string.sim_category_inactive_sim);
        }
        Context context = this.mContext;
        int subId = subscriptionInfoEntity.getSubId();
        Pattern pattern2 = SubscriptionUtil.REGEX_DISPLAY_NAME_SUFFIX_PATTERN;
        boolean z = subId == SubscriptionManager.getDefaultVoiceSubscriptionId();
        boolean z2 = subId == SubscriptionManager.getDefaultSmsSubscriptionId();
        boolean z3 = subId == SubscriptionManager.getDefaultDataSubscriptionId();
        if (z3 || z || z2) {
            StringBuilder sb = new StringBuilder();
            if (z3) {
                sb.append(
                        context.getResources().getString(R.string.default_active_sim_mobile_data));
                sb.append(", ");
            }
            if (z) {
                sb.append(context.getResources().getString(R.string.default_active_sim_calls));
                sb.append(", ");
            }
            if (z2) {
                sb.append(context.getResources().getString(R.string.default_active_sim_sms));
                sb.append(", ");
            }
            sb.setLength(sb.length() - 2);
            string = context.getResources().getString(R.string.sim_category_default_active_sim, sb);
        } else {
            string = ApnSettings.MVNO_NONE;
        }
        String string2 = this.mContext.getResources().getString(R.string.sim_category_active_sim);
        if (string == ApnSettings.MVNO_NONE) {
            return string2;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append((CharSequence) string2);
        sb2.append((CharSequence) string);
        return sb2;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlValue getValue() {
        return null;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean hasAsyncUpdate() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean isControllable() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isPublicSlice() {
        return false;
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean isSliceable() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ Controllable$ControllableType needUserInteraction(
            Object obj) {
        return Controllable$ControllableType.NO_INTERACTION;
    }

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public void onAvailableSubInfoChanged(List<SubscriptionInfoEntity> list) {
        this.mSubInfoEntityList = list;
        PreferenceCategory preferenceCategory = this.mPreferenceCategory;
        if (preferenceCategory != null) {
            preferenceCategory.setVisible(isAvailable());
        }
        update();
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onCreate(LifecycleOwner lifecycleOwner) {
        super.onCreate(lifecycleOwner);
    }

    @Override // com.android.settings.network.DefaultSubscriptionReceiver.DefaultSubscriptionListener
    public void onDefaultDataChanged(int i) {
        refreshSummary(this.mPreferenceCategory);
        update();
    }

    @Override // com.android.settings.network.DefaultSubscriptionReceiver.DefaultSubscriptionListener
    public void onDefaultSmsChanged(int i) {
        refreshSummary(this.mPreferenceCategory);
        update();
    }

    @Override // com.android.settings.network.DefaultSubscriptionReceiver.DefaultSubscriptionListener
    public void onDefaultVoiceChanged(int i) {
        refreshSummary(this.mPreferenceCategory);
        update();
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onDestroy(LifecycleOwner lifecycleOwner) {
        super.onDestroy(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onPause(LifecycleOwner lifecycleOwner) {
        this.mMobileNetworkRepository.removeRegister(this);
        DefaultSubscriptionReceiver defaultSubscriptionReceiver =
                this.mDataSubscriptionChangedReceiver;
        defaultSubscriptionReceiver.mContext.unregisterReceiver(defaultSubscriptionReceiver);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public void onResume(LifecycleOwner lifecycleOwner) {
        this.mMobileNetworkRepository.addRegister(lifecycleOwner, this, -1);
        this.mMobileNetworkRepository.updateEntity();
        this.mDataSubscriptionChangedReceiver.registerReceiver();
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onStart(LifecycleOwner lifecycleOwner) {
        super.onStart(lifecycleOwner);
    }

    @Override // androidx.lifecycle.DefaultLifecycleObserver
    public /* bridge */ /* synthetic */ void onStop(LifecycleOwner lifecycleOwner) {
        super.onStop(lifecycleOwner);
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ boolean runDefaultAction() {
        return false;
    }

    @Override // com.android.settings.core.BasePreferenceController
    public /* bridge */ /* synthetic */ ControlResult setValue(ControlValue controlValue) {
        return null;
    }

    @Override // com.android.settingslib.core.AbstractPreferenceController
    public void updateState(Preference preference) {
        super.updateState(preference);
        refreshSummary(this.mPreferenceCategory);
        update();
    }

    @Override // com.android.settings.slices.Sliceable
    public /* bridge */ /* synthetic */ boolean useDynamicSliceSummary() {
        return false;
    }

    public /* bridge */ /* synthetic */ void ignoreUserInteraction() {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onActiveSubInfoChanged(List list) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onAirplaneModeChanged(boolean z) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onAllMobileNetworkInfoChanged(List list) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onAllUiccInfoChanged(List list) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onCallStateChanged(int i) {}

    @Override // com.android.settings.network.DefaultSubscriptionReceiver.DefaultSubscriptionListener
    public /* bridge */ /* synthetic */ void onDefaultSubInfoChanged(int i) {}

    @Override // com.android.settings.network.MobileNetworkRepository.MobileNetworkCallback
    public /* bridge */ /* synthetic */ void onDataRoamingChanged(int i, boolean z) {}
}
