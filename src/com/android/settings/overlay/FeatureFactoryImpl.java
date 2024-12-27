package com.android.settings.overlay;

import android.app.AppGlobals;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.net.VpnManager;
import android.os.UserManager;

import com.android.settings.accessibility.AccessibilityMetricsFeatureProviderImpl;
import com.android.settings.accessibility.AccessibilitySearchFeatureProviderImpl;
import com.android.settings.accounts.AccountFeatureProviderImpl;
import com.android.settings.applications.ApplicationFeatureProviderImpl;
import com.android.settings.biometrics.face.FaceFeatureProvider;
import com.android.settings.biometrics.face.FaceFeatureProviderImpl;
import com.android.settings.biometrics.fingerprint.FingerprintFeatureProviderImpl;
import com.android.settings.biometrics2.factory.BiometricsRepositoryProviderImpl;
import com.android.settings.bluetooth.BluetoothFeatureProviderImpl;
import com.android.settings.connecteddevice.dock.DockUpdaterFeatureProviderImpl;
import com.android.settings.connecteddevice.fastpair.FastPairFeatureProviderImpl;
import com.android.settings.connecteddevice.stylus.StylusFeatureProviderImpl;
import com.android.settings.core.instrumentation.SettingsMetricsFeatureProvider;
import com.android.settings.dashboard.DashboardFeatureProviderImpl;
import com.android.settings.dashboard.suggestions.SuggestionFeatureProviderImpl;
import com.android.settings.display.DisplayFeatureProviderImpl;
import com.android.settings.enterprise.EnterprisePrivacyFeatureProviderImpl;
import com.android.settings.fuelgauge.BatterySettingsFeatureProviderImpl;
import com.android.settings.fuelgauge.BatteryStatusFeatureProviderImpl;
import com.android.settings.fuelgauge.PowerUsageFeatureProviderImpl;
import com.android.settings.homepage.contextualcards.ContextualCardFeatureProviderImpl;
import com.android.settings.inputmethod.KeyboardSettingsFeatureProviderImpl;
import com.android.settings.localepicker.LocaleFeatureProviderImpl;
import com.android.settings.notification.syncacrossdevices.SyncAcrossDevicesFeatureProviderImpl;
import com.android.settings.panel.PanelFeatureProviderImpl;
import com.android.settings.privatespace.PrivateSpaceLoginFeatureProviderImpl;
import com.android.settings.search.SearchFeatureProviderImpl;
import com.android.settings.security.SecurityFeatureProviderImpl;
import com.android.settings.security.SecuritySettingsFeatureProviderImpl;
import com.android.settings.slices.SlicesFeatureProviderImpl;
import com.android.settings.users.UserFeatureProviderImpl;
import com.android.settings.vpn2.AdvancedVpnFeatureProviderImpl;
import com.android.settings.wifi.WifiTrackerLibProviderImpl;
import com.android.settings.wifi.factory.WifiFeatureProvider;
import com.android.settingslib.core.instrumentation.MetricsFeatureProvider;
import com.android.settingslib.spaprivileged.framework.common.ContextsKt;

import com.samsung.android.settings.accessibility.base.logging.A11ySettingsMetricsFeatureProvider;
import com.samsung.android.settings.connection.SecSimFeatureProvider;
import com.samsung.android.settings.connection.SecSimFeatureProviderImpl;
import com.samsung.android.settings.datausage.DataUsageFeatureProviderImpl;
import com.samsung.android.settings.dynamicmenu.SecDynamicMenuFeatureProviderImpl;
import com.samsung.android.settings.eternal.BackupFeatureProviderImpl;
import com.samsung.android.settings.gts.GtsFeatureProviderImpl;

import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class FeatureFactoryImpl {
    public static Context _appContext;
    public static FeatureFactoryImpl _factory;
    public final Lazy a11ySettingsMetricsFeatureProvider$delegate;
    public final Lazy contextualCardFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$contextualCardFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            Context context = FeatureFactoryImpl._appContext;
                            if (context != null) {
                                return new ContextualCardFeatureProviderImpl(context);
                            }
                            throw new UnsupportedOperationException(
                                    "No feature factory configured");
                        }
                    });
    public final Lazy metricsFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$metricsFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new SettingsMetricsFeatureProvider();
                        }
                    });
    public final Lazy powerUsageFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$powerUsageFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            Context context = FeatureFactoryImpl._appContext;
                            if (context == null) {
                                throw new UnsupportedOperationException(
                                        "No feature factory configured");
                            }
                            PowerUsageFeatureProviderImpl powerUsageFeatureProviderImpl =
                                    new PowerUsageFeatureProviderImpl();
                            context.getPackageManager();
                            powerUsageFeatureProviderImpl.mContext =
                                    context.getApplicationContext();
                            return powerUsageFeatureProviderImpl;
                        }
                    });
    public final Lazy batteryStatusFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$batteryStatusFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            Context context = FeatureFactoryImpl._appContext;
                            if (context == null) {
                                throw new UnsupportedOperationException(
                                        "No feature factory configured");
                            }
                            BatteryStatusFeatureProviderImpl batteryStatusFeatureProviderImpl =
                                    new BatteryStatusFeatureProviderImpl();
                            context.getApplicationContext();
                            return batteryStatusFeatureProviderImpl;
                        }
                    });
    public final Lazy batterySettingsFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$batterySettingsFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new BatterySettingsFeatureProviderImpl();
                        }
                    });
    public final Lazy dashboardFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$dashboardFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            Context context = FeatureFactoryImpl._appContext;
                            if (context != null) {
                                return new DashboardFeatureProviderImpl(context);
                            }
                            throw new UnsupportedOperationException(
                                    "No feature factory configured");
                        }
                    });
    public final Lazy dockUpdaterFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$dockUpdaterFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new DockUpdaterFeatureProviderImpl();
                        }
                    });
    public final Lazy applicationFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$applicationFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            Context context = FeatureFactoryImpl._appContext;
                            if (context == null) {
                                throw new UnsupportedOperationException(
                                        "No feature factory configured");
                            }
                            if (context == null) {
                                throw new UnsupportedOperationException(
                                        "No feature factory configured");
                            }
                            PackageManager packageManager = context.getPackageManager();
                            IPackageManager packageManager2 = AppGlobals.getPackageManager();
                            Context context2 = FeatureFactoryImpl._appContext;
                            if (context2 != null) {
                                return new ApplicationFeatureProviderImpl(
                                        context,
                                        packageManager,
                                        packageManager2,
                                        ContextsKt.getDevicePolicyManager(context2));
                            }
                            throw new UnsupportedOperationException(
                                    "No feature factory configured");
                        }
                    });
    public final Lazy localeFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$localeFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new LocaleFeatureProviderImpl();
                        }
                    });
    public final Lazy enterprisePrivacyFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$enterprisePrivacyFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            Context appContext = FeatureFactory$Companion.getAppContext();
                            DevicePolicyManager devicePolicyManager =
                                    ContextsKt.getDevicePolicyManager(
                                            FeatureFactory$Companion.getAppContext());
                            PackageManager packageManager =
                                    FeatureFactory$Companion.getAppContext().getPackageManager();
                            UserManager userManager =
                                    UserManager.get(FeatureFactory$Companion.getAppContext());
                            return new EnterprisePrivacyFeatureProviderImpl(
                                    appContext,
                                    devicePolicyManager,
                                    packageManager,
                                    userManager,
                                    (VpnManager)
                                            FeatureFactory$Companion.getAppContext()
                                                    .getSystemService(VpnManager.class),
                                    FeatureFactory$Companion.getAppContext().getResources());
                        }
                    });
    public final Lazy searchFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$searchFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new SearchFeatureProviderImpl();
                        }
                    });
    public final Lazy securityFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$securityFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new SecurityFeatureProviderImpl();
                        }
                    });
    public final Lazy suggestionFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$suggestionFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new SuggestionFeatureProviderImpl();
                        }
                    });
    public final Lazy userFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$userFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            Context context = FeatureFactoryImpl._appContext;
                            if (context == null) {
                                throw new UnsupportedOperationException(
                                        "No feature factory configured");
                            }
                            UserFeatureProviderImpl userFeatureProviderImpl =
                                    new UserFeatureProviderImpl();
                            userFeatureProviderImpl.mUm =
                                    (UserManager) context.getSystemService("user");
                            return userFeatureProviderImpl;
                        }
                    });
    public final Lazy slicesFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$slicesFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new SlicesFeatureProviderImpl();
                        }
                    });
    public final Lazy accountFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$accountFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new AccountFeatureProviderImpl();
                        }
                    });
    public final Lazy panelFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$panelFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new PanelFeatureProviderImpl();
                        }
                    });
    public final Lazy bluetoothFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$bluetoothFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new BluetoothFeatureProviderImpl();
                        }
                    });
    public final Lazy faceFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$faceFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new FaceFeatureProviderImpl();
                        }
                    });
    public final Lazy fingerprintFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$fingerprintFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            FingerprintFeatureProviderImpl fingerprintFeatureProviderImpl =
                                    new FingerprintFeatureProviderImpl();
                            fingerprintFeatureProviderImpl.mSfpsEnrollmentFeatureImpl = null;
                            fingerprintFeatureProviderImpl.mSfpsRestToUnlockFeature = null;
                            return fingerprintFeatureProviderImpl;
                        }
                    });
    public final Lazy biometricsRepositoryProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$biometricsRepositoryProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new BiometricsRepositoryProviderImpl();
                        }
                    });
    public final Lazy wifiTrackerLibProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$wifiTrackerLibProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new WifiTrackerLibProviderImpl();
                        }
                    });
    public final Lazy securitySettingsFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$securitySettingsFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new SecuritySettingsFeatureProviderImpl();
                        }
                    });
    public final Lazy accessibilitySearchFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$accessibilitySearchFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new AccessibilitySearchFeatureProviderImpl();
                        }
                    });
    public final Lazy accessibilityMetricsFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$accessibilityMetricsFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new AccessibilityMetricsFeatureProviderImpl();
                        }
                    });
    public final Lazy advancedVpnFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$advancedVpnFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new AdvancedVpnFeatureProviderImpl();
                        }
                    });
    public final Lazy wifiFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$wifiFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            Context context = FeatureFactoryImpl._appContext;
                            if (context != null) {
                                return new WifiFeatureProvider(context);
                            }
                            throw new UnsupportedOperationException(
                                    "No feature factory configured");
                        }
                    });
    public final Lazy keyboardSettingsFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$keyboardSettingsFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new KeyboardSettingsFeatureProviderImpl();
                        }
                    });
    public final Lazy stylusFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$stylusFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new StylusFeatureProviderImpl();
                        }
                    });
    public final Lazy fastPairFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$fastPairFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new FastPairFeatureProviderImpl();
                        }
                    });
    public final Lazy privateSpaceLoginFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$privateSpaceLoginFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new PrivateSpaceLoginFeatureProviderImpl();
                        }
                    });
    public final Lazy displayFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$displayFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new DisplayFeatureProviderImpl();
                        }
                    });
    public final Lazy syncAcrossDevicesFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$syncAcrossDevicesFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new SyncAcrossDevicesFeatureProviderImpl();
                        }
                    });
    public final Lazy dataUsageFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$dataUsageFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new DataUsageFeatureProviderImpl();
                        }
                    });
    public final Lazy secDynamicMenuFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$secDynamicMenuFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            Context context = FeatureFactoryImpl._appContext;
                            if (context != null) {
                                return new SecDynamicMenuFeatureProviderImpl(context);
                            }
                            throw new UnsupportedOperationException(
                                    "No feature factory configured");
                        }
                    });
    public final Lazy secSimFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$secSimFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            Context context = FeatureFactoryImpl._appContext;
                            if (context == null) {
                                throw new UnsupportedOperationException(
                                        "No feature factory configured");
                            }
                            SecSimFeatureProviderImpl secSimFeatureProviderImpl =
                                    new SecSimFeatureProviderImpl();
                            secSimFeatureProviderImpl.mContext = context;
                            return secSimFeatureProviderImpl;
                        }
                    });
    public final Lazy backupFeatureProvider$delegate =
            LazyKt__LazyJVMKt.lazy(
                    new Function0() { // from class:
                                      // com.android.settings.overlay.FeatureFactoryImpl$backupFeatureProvider$2
                        @Override // kotlin.jvm.functions.Function0
                        /* renamed from: invoke */
                        public final Object mo1068invoke() {
                            return new BackupFeatureProviderImpl();
                        }
                    });

    public FeatureFactoryImpl() {
        LazyKt__LazyJVMKt.lazy(
                new Function0() { // from class:
                                  // com.android.settings.overlay.FeatureFactoryImpl$gtsFeatureProvider$2
                    @Override // kotlin.jvm.functions.Function0
                    /* renamed from: invoke */
                    public final Object mo1068invoke() {
                        return new GtsFeatureProviderImpl();
                    }
                });
        this.a11ySettingsMetricsFeatureProvider$delegate =
                LazyKt__LazyJVMKt.lazy(
                        new Function0() { // from class:
                                          // com.android.settings.overlay.FeatureFactoryImpl$a11ySettingsMetricsFeatureProvider$2
                            @Override // kotlin.jvm.functions.Function0
                            /* renamed from: invoke */
                            public final Object mo1068invoke() {
                                return new A11ySettingsMetricsFeatureProvider();
                            }
                        });
    }

    public final MetricsFeatureProvider getA11ySettingsMetricsFeatureProvider() {
        return (MetricsFeatureProvider) this.a11ySettingsMetricsFeatureProvider$delegate.getValue();
    }

    public final ApplicationFeatureProviderImpl getApplicationFeatureProvider() {
        return (ApplicationFeatureProviderImpl) this.applicationFeatureProvider$delegate.getValue();
    }

    public final BatterySettingsFeatureProviderImpl getBatterySettingsFeatureProvider() {
        return (BatterySettingsFeatureProviderImpl)
                this.batterySettingsFeatureProvider$delegate.getValue();
    }

    public final BluetoothFeatureProviderImpl getBluetoothFeatureProvider() {
        return (BluetoothFeatureProviderImpl) this.bluetoothFeatureProvider$delegate.getValue();
    }

    public final DashboardFeatureProviderImpl getDashboardFeatureProvider() {
        return (DashboardFeatureProviderImpl) this.dashboardFeatureProvider$delegate.getValue();
    }

    public final DataUsageFeatureProviderImpl getDataUsageFeatureProvider() {
        return (DataUsageFeatureProviderImpl) this.dataUsageFeatureProvider$delegate.getValue();
    }

    public final EnterprisePrivacyFeatureProviderImpl getEnterprisePrivacyFeatureProvider() {
        return (EnterprisePrivacyFeatureProviderImpl)
                this.enterprisePrivacyFeatureProvider$delegate.getValue();
    }

    public final FaceFeatureProvider getFaceFeatureProvider() {
        return (FaceFeatureProvider) this.faceFeatureProvider$delegate.getValue();
    }

    public final FingerprintFeatureProviderImpl getFingerprintFeatureProvider() {
        return (FingerprintFeatureProviderImpl) this.fingerprintFeatureProvider$delegate.getValue();
    }

    public final SettingsMetricsFeatureProvider getMetricsFeatureProvider() {
        return (SettingsMetricsFeatureProvider) this.metricsFeatureProvider$delegate.getValue();
    }

    public final PowerUsageFeatureProviderImpl getPowerUsageFeatureProvider() {
        return (PowerUsageFeatureProviderImpl) this.powerUsageFeatureProvider$delegate.getValue();
    }

    public final SearchFeatureProviderImpl getSearchFeatureProvider() {
        return (SearchFeatureProviderImpl) this.searchFeatureProvider$delegate.getValue();
    }

    public final SecSimFeatureProvider getSecSimFeatureProvider() {
        return (SecSimFeatureProvider) this.secSimFeatureProvider$delegate.getValue();
    }

    public final SecurityFeatureProviderImpl getSecurityFeatureProvider() {
        return (SecurityFeatureProviderImpl) this.securityFeatureProvider$delegate.getValue();
    }

    public final SlicesFeatureProviderImpl getSlicesFeatureProvider() {
        return (SlicesFeatureProviderImpl) this.slicesFeatureProvider$delegate.getValue();
    }

    public final SuggestionFeatureProviderImpl getSuggestionFeatureProvider() {
        return (SuggestionFeatureProviderImpl) this.suggestionFeatureProvider$delegate.getValue();
    }

    public final WifiFeatureProvider getWifiFeatureProvider() {
        return (WifiFeatureProvider) this.wifiFeatureProvider$delegate.getValue();
    }

    public final WifiTrackerLibProviderImpl getWifiTrackerLibProvider() {
        return (WifiTrackerLibProviderImpl) this.wifiTrackerLibProvider$delegate.getValue();
    }
}
