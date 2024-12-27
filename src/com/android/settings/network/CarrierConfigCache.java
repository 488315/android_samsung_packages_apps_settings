package com.android.settings.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.SubscriptionManager;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes2.dex */
public final class CarrierConfigCache {
    protected static CarrierConfigManager sCarrierConfigManager;
    public static CarrierConfigCache sInstance;
    public static Map sTestInstances;
    public static final Object sInstanceLock = new Object();
    protected static final Map<Integer, PersistableBundle> sCarrierConfigs =
            new ConcurrentHashMap();

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class CarrierConfigChangeReceiver extends BroadcastReceiver {
        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("android.telephony.action.CARRIER_CONFIG_CHANGED".equals(intent.getAction())) {
                int intExtra = intent.getIntExtra("android.telephony.extra.SUBSCRIPTION_INDEX", -1);
                Map<Integer, PersistableBundle> map = CarrierConfigCache.sCarrierConfigs;
                synchronized (map) {
                    try {
                        if (SubscriptionManager.isValidSubscriptionId(intExtra)) {
                            map.remove(Integer.valueOf(intExtra));
                        } else {
                            map.clear();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        }
    }

    public static PersistableBundle getConfig() {
        if (sCarrierConfigManager == null) {
            return null;
        }
        return getConfigForSubId(SubscriptionManager.getDefaultSubscriptionId());
    }

    public static PersistableBundle getConfigForSubId(int i) {
        if (sCarrierConfigManager == null) {
            return null;
        }
        Map<Integer, PersistableBundle> map = sCarrierConfigs;
        synchronized (map) {
            try {
                if (map.containsKey(Integer.valueOf(i))) {
                    return map.get(Integer.valueOf(i));
                }
                PersistableBundle configForSubId = sCarrierConfigManager.getConfigForSubId(i);
                if (configForSubId != null) {
                    map.put(Integer.valueOf(i), configForSubId);
                    return configForSubId;
                }
                Log.e("CarrConfCache", "Could not get carrier config, subId:" + i);
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static CarrierConfigCache getInstance(Context context) {
        synchronized (sInstanceLock) {
            try {
                Map map = sTestInstances;
                if (map != null && ((ConcurrentHashMap) map).containsKey(context)) {
                    CarrierConfigCache carrierConfigCache =
                            (CarrierConfigCache) ((ConcurrentHashMap) sTestInstances).get(context);
                    Log.w(
                            "CarrConfCache",
                            "The context owner try to use a test instance:" + carrierConfigCache);
                    return carrierConfigCache;
                }
                CarrierConfigCache carrierConfigCache2 = sInstance;
                if (carrierConfigCache2 != null) {
                    return carrierConfigCache2;
                }
                sInstance = new CarrierConfigCache();
                CarrierConfigChangeReceiver carrierConfigChangeReceiver =
                        new CarrierConfigChangeReceiver();
                Context applicationContext = context.getApplicationContext();
                sCarrierConfigManager =
                        (CarrierConfigManager)
                                applicationContext.getSystemService(CarrierConfigManager.class);
                applicationContext.registerReceiver(
                        carrierConfigChangeReceiver,
                        new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED"),
                        2);
                return sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static boolean hasCarrierConfigManager() {
        return sCarrierConfigManager != null;
    }

    public static void setTestInstance(Context context, CarrierConfigCache carrierConfigCache) {
        synchronized (sInstanceLock) {
            try {
                if (sTestInstances == null) {
                    sTestInstances = new ConcurrentHashMap();
                }
                Log.w("CarrConfCache", "Try to set a test instance by context:" + context);
                sTestInstances.put(context, carrierConfigCache);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
