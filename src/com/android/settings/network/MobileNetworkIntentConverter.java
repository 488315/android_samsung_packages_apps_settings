package com.android.settings.network;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.telephony.ims.ImsManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.settings.Settings;
import com.android.settings.network.telephony.MobileNetworkUtils;

import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class MobileNetworkIntentConverter implements Function {
    public final Context mAppContext;
    public final ComponentName mComponent;
    public static final ComponentName sTargetComponent =
            ComponentName.createRelative(
                    KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG,
                    Settings.MobileNetworkActivity.class.getTypeName());
    public static final String[] sPotentialActions = {
        null,
        "android.intent.action.MAIN",
        "android.settings.NETWORK_OPERATOR_SETTINGS",
        "android.settings.DATA_ROAMING_SETTINGS",
        "android.settings.MMS_MESSAGE_SETTING",
        "android.telephony.ims.action.SHOW_CAPABILITY_DISCOVERY_OPT_IN",
        "android.settings.SEARCH_RESULT_TRAMPOLINE"
    };
    public static final AtomicReference mCachedClassName = new AtomicReference();

    public MobileNetworkIntentConverter(Activity activity) {
        this.mAppContext = activity.getApplicationContext();
        this.mComponent = activity.getComponentName();
    }

    public static Bundle extractArguments(int i, Intent intent) {
        Bundle bundleExtra = intent.getBundleExtra(":settings:show_fragment_args");
        Bundle bundle = bundleExtra != null ? new Bundle(bundleExtra) : new Bundle();
        bundle.putParcelable("intent", intent);
        bundle.putInt("android.provider.extra.SUB_ID", i);
        return bundle;
    }

    public static Intent rePackIntent(Intent intent, Bundle bundle) {
        Intent intent2 = new Intent(intent);
        intent2.setComponent(sTargetComponent);
        intent2.putExtra(
                "android.provider.extra.SUB_ID", bundle.getInt("android.provider.extra.SUB_ID"));
        intent2.putExtra(":settings:show_fragment_args", bundle);
        return intent2;
    }

    public CharSequence getFragmentTitle(Context context, int i) {
        return SubscriptionUtil.getUniqueSubscriptionDisplayName(
                context, SubscriptionUtil.getSubscriptionOrDefault(context, i));
    }

    public boolean isAttachedToExposedComponents() {
        return sTargetComponent.compareTo(this.mComponent) == 0;
    }

    public boolean mayShowContactDiscoveryDialog(Context context, int i) {
        return MobileNetworkUtils.isContactDiscoveryVisible(context, i)
                && !MobileNetworkUtils.isContactDiscoveryEnabled(
                        (ImsManager) context.getSystemService(ImsManager.class), i);
    }

    public final void updateFragment(int i, Context context, Intent intent) {
        Bundle bundle;
        CharSequence fragmentTitle;
        if (intent.getStringExtra(":settings:show_fragment_title") == null
                && (fragmentTitle = getFragmentTitle(context, i)) != null) {
            intent.putExtra(":settings:show_fragment_title", fragmentTitle.toString());
        }
        AtomicReference atomicReference = mCachedClassName;
        String str = (String) atomicReference.get();
        if (str == null) {
            try {
                ActivityInfo activityInfo =
                        context.getPackageManager().getActivityInfo(sTargetComponent, 128);
                if (activityInfo != null && (bundle = activityInfo.metaData) != null) {
                    str = bundle.getString("com.android.settings.FRAGMENT_CLASS");
                    if (str != null) {
                        atomicReference.set(str);
                    }
                }
            } catch (PackageManager.NameNotFoundException unused) {
                Log.d(
                        "MobileNetworkIntentConverter",
                        "Cannot get Metadata for: " + sTargetComponent.toString());
            }
            str = null;
        }
        intent.putExtra(":settings:show_fragment", str);
    }

    @Override // java.util.function.Function
    public final Intent apply(final Intent intent) {
        boolean anyMatch;
        Function andThen;
        long elapsedRealtimeNanos = SystemClock.elapsedRealtimeNanos();
        if (isAttachedToExposedComponents()) {
            if (intent != null) {
                if (TextUtils.equals(
                        intent.getAction(), "android.settings.SETTINGS_EMBED_DEEP_LINK_ACTIVITY")) {
                    try {
                        intent =
                                Intent.parseUri(
                                        intent.getStringExtra(
                                                "android.provider.extra.SETTINGS_EMBEDDED_DEEP_LINK_INTENT_URI"),
                                        1);
                    } catch (URISyntaxException e) {
                        Log.d("MobileNetworkIntentConverter", "Intent URI corrupted", e);
                    }
                }
            }
            intent = null;
        } else {
            if (intent == null) {
                anyMatch = false;
            } else {
                final String action = intent.getAction();
                anyMatch =
                        Arrays.stream(sPotentialActions)
                                .anyMatch(
                                        new Predicate() { // from class:
                                                          // com.android.settings.network.MobileNetworkIntentConverter$$ExternalSyntheticLambda16
                                            @Override // java.util.function.Predicate
                                            public final boolean test(Object obj) {
                                                return TextUtils.equals(action, (String) obj);
                                            }
                                        });
            }
            if (!anyMatch) {
                return null;
            }
        }
        String action2 = intent.getAction();
        final int intExtra = intent.getIntExtra("android.provider.extra.SUB_ID", -1);
        Function identity = Function.identity();
        if (TextUtils.equals(action2, "android.settings.NETWORK_OPERATOR_SETTINGS")
                || TextUtils.equals(action2, "android.settings.DATA_ROAMING_SETTINGS")
                || TextUtils.equals(action2, "android.settings.SEARCH_RESULT_TRAMPOLINE")) {
            final int i = 0;
            Function andThen2 =
                    identity.andThen(
                            new Function(
                                    this) { // from class:
                                            // com.android.settings.network.MobileNetworkIntentConverter$$ExternalSyntheticLambda0
                                public final /* synthetic */ MobileNetworkIntentConverter f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    switch (i) {
                                        case 0:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 1:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter = this.f$0;
                                            Intent intent2 = (Intent) obj;
                                            mobileNetworkIntentConverter.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter.mAppContext,
                                                    intent2);
                                            return intent2;
                                        case 2:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 3:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter2 = this.f$0;
                                            int i2 = intExtra;
                                            Bundle bundle = (Bundle) obj;
                                            boolean mayShowContactDiscoveryDialog =
                                                    mobileNetworkIntentConverter2
                                                            .mayShowContactDiscoveryDialog(
                                                                    mobileNetworkIntentConverter2
                                                                            .mAppContext,
                                                                    i2);
                                            Log.d(
                                                    "MobileNetworkIntentConverter",
                                                    "maybeShowContactDiscoveryDialog subId="
                                                            + i2
                                                            + ", show="
                                                            + mayShowContactDiscoveryDialog);
                                            bundle.putBoolean(
                                                    "show_capability_discovery_opt_in",
                                                    mayShowContactDiscoveryDialog);
                                            return bundle;
                                        case 4:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter3 = this.f$0;
                                            Intent intent3 = (Intent) obj;
                                            mobileNetworkIntentConverter3.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter3.mAppContext,
                                                    intent3);
                                            return intent3;
                                        case 5:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 6:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter4 = this.f$0;
                                            Intent intent4 = (Intent) obj;
                                            mobileNetworkIntentConverter4.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter4.mAppContext,
                                                    intent4);
                                            return intent4;
                                        case 7:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter5 = this.f$0;
                                            Intent intent5 = (Intent) obj;
                                            mobileNetworkIntentConverter5.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter5.mAppContext,
                                                    intent5);
                                            return intent5;
                                        default:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                    }
                                }
                            });
            final int i2 = 3;
            Function andThen3 =
                    andThen2.andThen(
                            new Function(
                                    this) { // from class:
                                            // com.android.settings.network.MobileNetworkIntentConverter$$ExternalSyntheticLambda3
                                public final /* synthetic */ MobileNetworkIntentConverter f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    switch (i2) {
                                        case 0:
                                            this.f$0.getClass();
                                            break;
                                        case 1:
                                            this.f$0.getClass();
                                            break;
                                        case 2:
                                            this.f$0.getClass();
                                            break;
                                        default:
                                            this.f$0.getClass();
                                            break;
                                    }
                                    return MobileNetworkIntentConverter.rePackIntent(
                                            intent, (Bundle) obj);
                                }
                            });
            final int i3 = 7;
            andThen =
                    andThen3.andThen(
                            new Function(
                                    this) { // from class:
                                            // com.android.settings.network.MobileNetworkIntentConverter$$ExternalSyntheticLambda0
                                public final /* synthetic */ MobileNetworkIntentConverter f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    switch (i3) {
                                        case 0:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 1:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter = this.f$0;
                                            Intent intent2 = (Intent) obj;
                                            mobileNetworkIntentConverter.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter.mAppContext,
                                                    intent2);
                                            return intent2;
                                        case 2:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 3:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter2 = this.f$0;
                                            int i22 = intExtra;
                                            Bundle bundle = (Bundle) obj;
                                            boolean mayShowContactDiscoveryDialog =
                                                    mobileNetworkIntentConverter2
                                                            .mayShowContactDiscoveryDialog(
                                                                    mobileNetworkIntentConverter2
                                                                            .mAppContext,
                                                                    i22);
                                            Log.d(
                                                    "MobileNetworkIntentConverter",
                                                    "maybeShowContactDiscoveryDialog subId="
                                                            + i22
                                                            + ", show="
                                                            + mayShowContactDiscoveryDialog);
                                            bundle.putBoolean(
                                                    "show_capability_discovery_opt_in",
                                                    mayShowContactDiscoveryDialog);
                                            return bundle;
                                        case 4:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter3 = this.f$0;
                                            Intent intent3 = (Intent) obj;
                                            mobileNetworkIntentConverter3.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter3.mAppContext,
                                                    intent3);
                                            return intent3;
                                        case 5:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 6:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter4 = this.f$0;
                                            Intent intent4 = (Intent) obj;
                                            mobileNetworkIntentConverter4.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter4.mAppContext,
                                                    intent4);
                                            return intent4;
                                        case 7:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter5 = this.f$0;
                                            Intent intent5 = (Intent) obj;
                                            mobileNetworkIntentConverter5.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter5.mAppContext,
                                                    intent5);
                                            return intent5;
                                        default:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                    }
                                }
                            });
        } else if (TextUtils.equals(action2, "android.settings.MMS_MESSAGE_SETTING")) {
            final int i4 = 8;
            Function andThen4 =
                    identity.andThen(
                            new Function(
                                    this) { // from class:
                                            // com.android.settings.network.MobileNetworkIntentConverter$$ExternalSyntheticLambda0
                                public final /* synthetic */ MobileNetworkIntentConverter f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    switch (i4) {
                                        case 0:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 1:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter = this.f$0;
                                            Intent intent2 = (Intent) obj;
                                            mobileNetworkIntentConverter.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter.mAppContext,
                                                    intent2);
                                            return intent2;
                                        case 2:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 3:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter2 = this.f$0;
                                            int i22 = intExtra;
                                            Bundle bundle = (Bundle) obj;
                                            boolean mayShowContactDiscoveryDialog =
                                                    mobileNetworkIntentConverter2
                                                            .mayShowContactDiscoveryDialog(
                                                                    mobileNetworkIntentConverter2
                                                                            .mAppContext,
                                                                    i22);
                                            Log.d(
                                                    "MobileNetworkIntentConverter",
                                                    "maybeShowContactDiscoveryDialog subId="
                                                            + i22
                                                            + ", show="
                                                            + mayShowContactDiscoveryDialog);
                                            bundle.putBoolean(
                                                    "show_capability_discovery_opt_in",
                                                    mayShowContactDiscoveryDialog);
                                            return bundle;
                                        case 4:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter3 = this.f$0;
                                            Intent intent3 = (Intent) obj;
                                            mobileNetworkIntentConverter3.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter3.mAppContext,
                                                    intent3);
                                            return intent3;
                                        case 5:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 6:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter4 = this.f$0;
                                            Intent intent4 = (Intent) obj;
                                            mobileNetworkIntentConverter4.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter4.mAppContext,
                                                    intent4);
                                            return intent4;
                                        case 7:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter5 = this.f$0;
                                            Intent intent5 = (Intent) obj;
                                            mobileNetworkIntentConverter5.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter5.mAppContext,
                                                    intent5);
                                            return intent5;
                                        default:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                    }
                                }
                            });
            final int i5 = 1;
            andThen =
                    andThen4.andThen(
                                    new Function(this) { // from class:
                                        // com.android.settings.network.MobileNetworkIntentConverter$$ExternalSyntheticLambda4
                                        public final /* synthetic */ MobileNetworkIntentConverter
                                                f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            int i6 = i5;
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter = this.f$0;
                                            switch (i6) {
                                                case 0:
                                                    Intent intent2 = (Intent) obj;
                                                    mobileNetworkIntentConverter.getClass();
                                                    intent2.setAction(
                                                            "android.settings.NETWORK_OPERATOR_SETTINGS");
                                                    return intent2;
                                                case 1:
                                                    Bundle bundle = (Bundle) obj;
                                                    mobileNetworkIntentConverter.getClass();
                                                    bundle.putString(
                                                            ":settings:fragment_args_key",
                                                            "mms_message");
                                                    return bundle;
                                                default:
                                                    Intent intent3 = (Intent) obj;
                                                    mobileNetworkIntentConverter.getClass();
                                                    if (!intent3.hasExtra(
                                                            ":reroute:MobileNetworkIntentConverter")) {
                                                        return intent3.putExtra(
                                                                        ":reroute:MobileNetworkIntentConverter",
                                                                        intent3.getAction())
                                                                .setComponent(null);
                                                    }
                                                    Log.d(
                                                            "MobileNetworkIntentConverter",
                                                            "Skip re-routed intent " + intent3);
                                                    return null;
                                            }
                                        }
                                    })
                            .andThen(
                                    new Function(this) { // from class:
                                        // com.android.settings.network.MobileNetworkIntentConverter$$ExternalSyntheticLambda3
                                        public final /* synthetic */ MobileNetworkIntentConverter
                                                f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            switch (i5) {
                                                case 0:
                                                    this.f$0.getClass();
                                                    break;
                                                case 1:
                                                    this.f$0.getClass();
                                                    break;
                                                case 2:
                                                    this.f$0.getClass();
                                                    break;
                                                default:
                                                    this.f$0.getClass();
                                                    break;
                                            }
                                            return MobileNetworkIntentConverter.rePackIntent(
                                                    intent, (Bundle) obj);
                                        }
                                    })
                            .andThen(
                                    new Function(this) { // from class:
                                        // com.android.settings.network.MobileNetworkIntentConverter$$ExternalSyntheticLambda0
                                        public final /* synthetic */ MobileNetworkIntentConverter
                                                f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            switch (i5) {
                                                case 0:
                                                    this.f$0.getClass();
                                                    return MobileNetworkIntentConverter
                                                            .extractArguments(
                                                                    intExtra, (Intent) obj);
                                                case 1:
                                                    MobileNetworkIntentConverter
                                                            mobileNetworkIntentConverter = this.f$0;
                                                    Intent intent2 = (Intent) obj;
                                                    mobileNetworkIntentConverter.updateFragment(
                                                            intExtra,
                                                            mobileNetworkIntentConverter
                                                                    .mAppContext,
                                                            intent2);
                                                    return intent2;
                                                case 2:
                                                    this.f$0.getClass();
                                                    return MobileNetworkIntentConverter
                                                            .extractArguments(
                                                                    intExtra, (Intent) obj);
                                                case 3:
                                                    MobileNetworkIntentConverter
                                                            mobileNetworkIntentConverter2 =
                                                                    this.f$0;
                                                    int i22 = intExtra;
                                                    Bundle bundle = (Bundle) obj;
                                                    boolean mayShowContactDiscoveryDialog =
                                                            mobileNetworkIntentConverter2
                                                                    .mayShowContactDiscoveryDialog(
                                                                            mobileNetworkIntentConverter2
                                                                                    .mAppContext,
                                                                            i22);
                                                    Log.d(
                                                            "MobileNetworkIntentConverter",
                                                            "maybeShowContactDiscoveryDialog subId="
                                                                    + i22
                                                                    + ", show="
                                                                    + mayShowContactDiscoveryDialog);
                                                    bundle.putBoolean(
                                                            "show_capability_discovery_opt_in",
                                                            mayShowContactDiscoveryDialog);
                                                    return bundle;
                                                case 4:
                                                    MobileNetworkIntentConverter
                                                            mobileNetworkIntentConverter3 =
                                                                    this.f$0;
                                                    Intent intent3 = (Intent) obj;
                                                    mobileNetworkIntentConverter3.updateFragment(
                                                            intExtra,
                                                            mobileNetworkIntentConverter3
                                                                    .mAppContext,
                                                            intent3);
                                                    return intent3;
                                                case 5:
                                                    this.f$0.getClass();
                                                    return MobileNetworkIntentConverter
                                                            .extractArguments(
                                                                    intExtra, (Intent) obj);
                                                case 6:
                                                    MobileNetworkIntentConverter
                                                            mobileNetworkIntentConverter4 =
                                                                    this.f$0;
                                                    Intent intent4 = (Intent) obj;
                                                    mobileNetworkIntentConverter4.updateFragment(
                                                            intExtra,
                                                            mobileNetworkIntentConverter4
                                                                    .mAppContext,
                                                            intent4);
                                                    return intent4;
                                                case 7:
                                                    MobileNetworkIntentConverter
                                                            mobileNetworkIntentConverter5 =
                                                                    this.f$0;
                                                    Intent intent5 = (Intent) obj;
                                                    mobileNetworkIntentConverter5.updateFragment(
                                                            intExtra,
                                                            mobileNetworkIntentConverter5
                                                                    .mAppContext,
                                                            intent5);
                                                    return intent5;
                                                default:
                                                    this.f$0.getClass();
                                                    return MobileNetworkIntentConverter
                                                            .extractArguments(
                                                                    intExtra, (Intent) obj);
                                            }
                                        }
                                    });
        } else if (TextUtils.equals(
                action2, "android.telephony.ims.action.SHOW_CAPABILITY_DISCOVERY_OPT_IN")) {
            final int i6 = 2;
            Function andThen5 =
                    identity.andThen(
                            new Function(
                                    this) { // from class:
                                            // com.android.settings.network.MobileNetworkIntentConverter$$ExternalSyntheticLambda0
                                public final /* synthetic */ MobileNetworkIntentConverter f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    switch (i6) {
                                        case 0:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 1:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter = this.f$0;
                                            Intent intent2 = (Intent) obj;
                                            mobileNetworkIntentConverter.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter.mAppContext,
                                                    intent2);
                                            return intent2;
                                        case 2:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 3:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter2 = this.f$0;
                                            int i22 = intExtra;
                                            Bundle bundle = (Bundle) obj;
                                            boolean mayShowContactDiscoveryDialog =
                                                    mobileNetworkIntentConverter2
                                                            .mayShowContactDiscoveryDialog(
                                                                    mobileNetworkIntentConverter2
                                                                            .mAppContext,
                                                                    i22);
                                            Log.d(
                                                    "MobileNetworkIntentConverter",
                                                    "maybeShowContactDiscoveryDialog subId="
                                                            + i22
                                                            + ", show="
                                                            + mayShowContactDiscoveryDialog);
                                            bundle.putBoolean(
                                                    "show_capability_discovery_opt_in",
                                                    mayShowContactDiscoveryDialog);
                                            return bundle;
                                        case 4:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter3 = this.f$0;
                                            Intent intent3 = (Intent) obj;
                                            mobileNetworkIntentConverter3.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter3.mAppContext,
                                                    intent3);
                                            return intent3;
                                        case 5:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 6:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter4 = this.f$0;
                                            Intent intent4 = (Intent) obj;
                                            mobileNetworkIntentConverter4.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter4.mAppContext,
                                                    intent4);
                                            return intent4;
                                        case 7:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter5 = this.f$0;
                                            Intent intent5 = (Intent) obj;
                                            mobileNetworkIntentConverter5.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter5.mAppContext,
                                                    intent5);
                                            return intent5;
                                        default:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                    }
                                }
                            });
            final int i7 = 3;
            Function andThen6 =
                    andThen5.andThen(
                            new Function(
                                    this) { // from class:
                                            // com.android.settings.network.MobileNetworkIntentConverter$$ExternalSyntheticLambda0
                                public final /* synthetic */ MobileNetworkIntentConverter f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    switch (i7) {
                                        case 0:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 1:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter = this.f$0;
                                            Intent intent2 = (Intent) obj;
                                            mobileNetworkIntentConverter.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter.mAppContext,
                                                    intent2);
                                            return intent2;
                                        case 2:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 3:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter2 = this.f$0;
                                            int i22 = intExtra;
                                            Bundle bundle = (Bundle) obj;
                                            boolean mayShowContactDiscoveryDialog =
                                                    mobileNetworkIntentConverter2
                                                            .mayShowContactDiscoveryDialog(
                                                                    mobileNetworkIntentConverter2
                                                                            .mAppContext,
                                                                    i22);
                                            Log.d(
                                                    "MobileNetworkIntentConverter",
                                                    "maybeShowContactDiscoveryDialog subId="
                                                            + i22
                                                            + ", show="
                                                            + mayShowContactDiscoveryDialog);
                                            bundle.putBoolean(
                                                    "show_capability_discovery_opt_in",
                                                    mayShowContactDiscoveryDialog);
                                            return bundle;
                                        case 4:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter3 = this.f$0;
                                            Intent intent3 = (Intent) obj;
                                            mobileNetworkIntentConverter3.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter3.mAppContext,
                                                    intent3);
                                            return intent3;
                                        case 5:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 6:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter4 = this.f$0;
                                            Intent intent4 = (Intent) obj;
                                            mobileNetworkIntentConverter4.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter4.mAppContext,
                                                    intent4);
                                            return intent4;
                                        case 7:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter5 = this.f$0;
                                            Intent intent5 = (Intent) obj;
                                            mobileNetworkIntentConverter5.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter5.mAppContext,
                                                    intent5);
                                            return intent5;
                                        default:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                    }
                                }
                            });
            final int i8 = 2;
            Function andThen7 =
                    andThen6.andThen(
                            new Function(
                                    this) { // from class:
                                            // com.android.settings.network.MobileNetworkIntentConverter$$ExternalSyntheticLambda3
                                public final /* synthetic */ MobileNetworkIntentConverter f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    switch (i8) {
                                        case 0:
                                            this.f$0.getClass();
                                            break;
                                        case 1:
                                            this.f$0.getClass();
                                            break;
                                        case 2:
                                            this.f$0.getClass();
                                            break;
                                        default:
                                            this.f$0.getClass();
                                            break;
                                    }
                                    return MobileNetworkIntentConverter.rePackIntent(
                                            intent, (Bundle) obj);
                                }
                            });
            final int i9 = 4;
            andThen =
                    andThen7.andThen(
                            new Function(
                                    this) { // from class:
                                            // com.android.settings.network.MobileNetworkIntentConverter$$ExternalSyntheticLambda0
                                public final /* synthetic */ MobileNetworkIntentConverter f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    switch (i9) {
                                        case 0:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 1:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter = this.f$0;
                                            Intent intent2 = (Intent) obj;
                                            mobileNetworkIntentConverter.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter.mAppContext,
                                                    intent2);
                                            return intent2;
                                        case 2:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 3:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter2 = this.f$0;
                                            int i22 = intExtra;
                                            Bundle bundle = (Bundle) obj;
                                            boolean mayShowContactDiscoveryDialog =
                                                    mobileNetworkIntentConverter2
                                                            .mayShowContactDiscoveryDialog(
                                                                    mobileNetworkIntentConverter2
                                                                            .mAppContext,
                                                                    i22);
                                            Log.d(
                                                    "MobileNetworkIntentConverter",
                                                    "maybeShowContactDiscoveryDialog subId="
                                                            + i22
                                                            + ", show="
                                                            + mayShowContactDiscoveryDialog);
                                            bundle.putBoolean(
                                                    "show_capability_discovery_opt_in",
                                                    mayShowContactDiscoveryDialog);
                                            return bundle;
                                        case 4:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter3 = this.f$0;
                                            Intent intent3 = (Intent) obj;
                                            mobileNetworkIntentConverter3.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter3.mAppContext,
                                                    intent3);
                                            return intent3;
                                        case 5:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 6:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter4 = this.f$0;
                                            Intent intent4 = (Intent) obj;
                                            mobileNetworkIntentConverter4.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter4.mAppContext,
                                                    intent4);
                                            return intent4;
                                        case 7:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter5 = this.f$0;
                                            Intent intent5 = (Intent) obj;
                                            mobileNetworkIntentConverter5.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter5.mAppContext,
                                                    intent5);
                                            return intent5;
                                        default:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                    }
                                }
                            });
        } else {
            if (sTargetComponent.compareTo(this.mComponent) != 0
                    || (action2 != null && !"android.intent.action.MAIN".equals(action2))) {
                return null;
            }
            Log.d(
                    "MobileNetworkIntentConverter",
                    "Support default actions direct to this component");
            final int i10 = 5;
            Function andThen8 =
                    identity.andThen(
                            new Function(
                                    this) { // from class:
                                            // com.android.settings.network.MobileNetworkIntentConverter$$ExternalSyntheticLambda0
                                public final /* synthetic */ MobileNetworkIntentConverter f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    switch (i10) {
                                        case 0:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 1:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter = this.f$0;
                                            Intent intent2 = (Intent) obj;
                                            mobileNetworkIntentConverter.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter.mAppContext,
                                                    intent2);
                                            return intent2;
                                        case 2:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 3:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter2 = this.f$0;
                                            int i22 = intExtra;
                                            Bundle bundle = (Bundle) obj;
                                            boolean mayShowContactDiscoveryDialog =
                                                    mobileNetworkIntentConverter2
                                                            .mayShowContactDiscoveryDialog(
                                                                    mobileNetworkIntentConverter2
                                                                            .mAppContext,
                                                                    i22);
                                            Log.d(
                                                    "MobileNetworkIntentConverter",
                                                    "maybeShowContactDiscoveryDialog subId="
                                                            + i22
                                                            + ", show="
                                                            + mayShowContactDiscoveryDialog);
                                            bundle.putBoolean(
                                                    "show_capability_discovery_opt_in",
                                                    mayShowContactDiscoveryDialog);
                                            return bundle;
                                        case 4:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter3 = this.f$0;
                                            Intent intent3 = (Intent) obj;
                                            mobileNetworkIntentConverter3.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter3.mAppContext,
                                                    intent3);
                                            return intent3;
                                        case 5:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 6:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter4 = this.f$0;
                                            Intent intent4 = (Intent) obj;
                                            mobileNetworkIntentConverter4.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter4.mAppContext,
                                                    intent4);
                                            return intent4;
                                        case 7:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter5 = this.f$0;
                                            Intent intent5 = (Intent) obj;
                                            mobileNetworkIntentConverter5.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter5.mAppContext,
                                                    intent5);
                                            return intent5;
                                        default:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                    }
                                }
                            });
            final int i11 = 0;
            Function andThen9 =
                    andThen8.andThen(
                                    new Function(this) { // from class:
                                        // com.android.settings.network.MobileNetworkIntentConverter$$ExternalSyntheticLambda3
                                        public final /* synthetic */ MobileNetworkIntentConverter
                                                f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            switch (i11) {
                                                case 0:
                                                    this.f$0.getClass();
                                                    break;
                                                case 1:
                                                    this.f$0.getClass();
                                                    break;
                                                case 2:
                                                    this.f$0.getClass();
                                                    break;
                                                default:
                                                    this.f$0.getClass();
                                                    break;
                                            }
                                            return MobileNetworkIntentConverter.rePackIntent(
                                                    intent, (Bundle) obj);
                                        }
                                    })
                            .andThen(
                                    new Function(this) { // from class:
                                        // com.android.settings.network.MobileNetworkIntentConverter$$ExternalSyntheticLambda4
                                        public final /* synthetic */ MobileNetworkIntentConverter
                                                f$0;

                                        {
                                            this.f$0 = this;
                                        }

                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            int i62 = i11;
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter = this.f$0;
                                            switch (i62) {
                                                case 0:
                                                    Intent intent2 = (Intent) obj;
                                                    mobileNetworkIntentConverter.getClass();
                                                    intent2.setAction(
                                                            "android.settings.NETWORK_OPERATOR_SETTINGS");
                                                    return intent2;
                                                case 1:
                                                    Bundle bundle = (Bundle) obj;
                                                    mobileNetworkIntentConverter.getClass();
                                                    bundle.putString(
                                                            ":settings:fragment_args_key",
                                                            "mms_message");
                                                    return bundle;
                                                default:
                                                    Intent intent3 = (Intent) obj;
                                                    mobileNetworkIntentConverter.getClass();
                                                    if (!intent3.hasExtra(
                                                            ":reroute:MobileNetworkIntentConverter")) {
                                                        return intent3.putExtra(
                                                                        ":reroute:MobileNetworkIntentConverter",
                                                                        intent3.getAction())
                                                                .setComponent(null);
                                                    }
                                                    Log.d(
                                                            "MobileNetworkIntentConverter",
                                                            "Skip re-routed intent " + intent3);
                                                    return null;
                                            }
                                        }
                                    });
            final int i12 = 6;
            andThen =
                    andThen9.andThen(
                            new Function(
                                    this) { // from class:
                                            // com.android.settings.network.MobileNetworkIntentConverter$$ExternalSyntheticLambda0
                                public final /* synthetic */ MobileNetworkIntentConverter f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    switch (i12) {
                                        case 0:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 1:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter = this.f$0;
                                            Intent intent2 = (Intent) obj;
                                            mobileNetworkIntentConverter.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter.mAppContext,
                                                    intent2);
                                            return intent2;
                                        case 2:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 3:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter2 = this.f$0;
                                            int i22 = intExtra;
                                            Bundle bundle = (Bundle) obj;
                                            boolean mayShowContactDiscoveryDialog =
                                                    mobileNetworkIntentConverter2
                                                            .mayShowContactDiscoveryDialog(
                                                                    mobileNetworkIntentConverter2
                                                                            .mAppContext,
                                                                    i22);
                                            Log.d(
                                                    "MobileNetworkIntentConverter",
                                                    "maybeShowContactDiscoveryDialog subId="
                                                            + i22
                                                            + ", show="
                                                            + mayShowContactDiscoveryDialog);
                                            bundle.putBoolean(
                                                    "show_capability_discovery_opt_in",
                                                    mayShowContactDiscoveryDialog);
                                            return bundle;
                                        case 4:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter3 = this.f$0;
                                            Intent intent3 = (Intent) obj;
                                            mobileNetworkIntentConverter3.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter3.mAppContext,
                                                    intent3);
                                            return intent3;
                                        case 5:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                        case 6:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter4 = this.f$0;
                                            Intent intent4 = (Intent) obj;
                                            mobileNetworkIntentConverter4.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter4.mAppContext,
                                                    intent4);
                                            return intent4;
                                        case 7:
                                            MobileNetworkIntentConverter
                                                    mobileNetworkIntentConverter5 = this.f$0;
                                            Intent intent5 = (Intent) obj;
                                            mobileNetworkIntentConverter5.updateFragment(
                                                    intExtra,
                                                    mobileNetworkIntentConverter5.mAppContext,
                                                    intent5);
                                            return intent5;
                                        default:
                                            this.f$0.getClass();
                                            return MobileNetworkIntentConverter.extractArguments(
                                                    intExtra, (Intent) obj);
                                    }
                                }
                            });
        }
        if (!isAttachedToExposedComponents()) {
            final int i13 = 2;
            andThen =
                    andThen.andThen(
                            new Function(this) { // from class:
                                // com.android.settings.network.MobileNetworkIntentConverter$$ExternalSyntheticLambda4
                                public final /* synthetic */ MobileNetworkIntentConverter f$0;

                                {
                                    this.f$0 = this;
                                }

                                @Override // java.util.function.Function
                                public final Object apply(Object obj) {
                                    int i62 = i13;
                                    MobileNetworkIntentConverter mobileNetworkIntentConverter =
                                            this.f$0;
                                    switch (i62) {
                                        case 0:
                                            Intent intent2 = (Intent) obj;
                                            mobileNetworkIntentConverter.getClass();
                                            intent2.setAction(
                                                    "android.settings.NETWORK_OPERATOR_SETTINGS");
                                            return intent2;
                                        case 1:
                                            Bundle bundle = (Bundle) obj;
                                            mobileNetworkIntentConverter.getClass();
                                            bundle.putString(
                                                    ":settings:fragment_args_key", "mms_message");
                                            return bundle;
                                        default:
                                            Intent intent3 = (Intent) obj;
                                            mobileNetworkIntentConverter.getClass();
                                            if (!intent3.hasExtra(
                                                    ":reroute:MobileNetworkIntentConverter")) {
                                                return intent3.putExtra(
                                                                ":reroute:MobileNetworkIntentConverter",
                                                                intent3.getAction())
                                                        .setComponent(null);
                                            }
                                            Log.d(
                                                    "MobileNetworkIntentConverter",
                                                    "Skip re-routed intent " + intent3);
                                            return null;
                                    }
                                }
                            });
        }
        Intent intent2 = (Intent) andThen.apply(intent);
        if (intent2 != null) {
            Log.d(
                    "MobileNetworkIntentConverter",
                    this.mComponent.toString()
                            + " intent conversion: "
                            + (SystemClock.elapsedRealtimeNanos() - elapsedRealtimeNanos)
                            + " ns");
        }
        return intent2;
    }
}
