package com.samsung.android.knox.location;

import android.annotation.NonNull;
import android.annotation.RequiresPermission;
import android.content.Context;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceAdminInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;

import java.util.List;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes4.dex */
public class Geofencing {
    public static final String ACTION_DEVICE_INSIDE_GEOFENCE =
            "com.samsung.android.knox.intent.action.DEVICE_INSIDE_GEOFENCE";
    public static final String ACTION_DEVICE_LOCATION_UNAVAILABLE =
            "com.samsung.android.knox.intent.action.DEVICE_LOCATION_UNAVAILABLE";
    public static final String ACTION_DEVICE_OUTSIDE_GEOFENCE =
            "com.samsung.android.knox.intent.action.DEVICE_OUTSIDE_GEOFENCE";
    public static final int ERROR_GEOFENCING_FAILED = -1;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_UNKNOWN = -2000;
    public static final String EXTRA_ID = "com.samsung.android.knox.intent.extra.ID";
    public static final String EXTRA_USER_ID = "com.samsung.android.knox.intent.extra.USER_ID";
    public static final String TAG = "Geofencing";
    public static final int TYPE_CIRCLE = 1;
    public static final int TYPE_LINEAR = 3;
    public static final int TYPE_POLYGON = 2;
    public static final Object mSync = new Object();
    public final Context mContext;
    public ContextInfo mContextInfo;
    public IGeofencing mGeofenceService;

    public Geofencing(@NonNull ContextInfo contextInfo, @NonNull Context context) {
        this.mContext = context;
        this.mContextInfo = contextInfo;
    }

    public static Geofencing createInstance(ContextInfo contextInfo, Context context) {
        return new Geofencing(contextInfo, context.getApplicationContext());
    }

    public static Geofencing getInstance(Context context) {
        synchronized (mSync) {
            try {
                if (context == null) {
                    return null;
                }
                return new Geofencing(
                        new ContextInfo(Process.myUid()), context.getApplicationContext());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @RequiresPermission(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_GEOFENCING_TAG)
    public int createGeofence(@NonNull Geofence geofence) {
        EnterpriseLicenseManager.log(this.mContextInfo, "Geofencing.createGeofence");
        if (getService() == null) {
            return -1;
        }
        try {
            return this.mGeofenceService.createGeofence(this.mContextInfo, geofence);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with geofencing service", e);
            return -1;
        }
    }

    @RequiresPermission(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_GEOFENCING_TAG)
    public boolean destroyGeofence(int i) {
        EnterpriseLicenseManager.log(this.mContextInfo, "Geofencing.destroyGeofence");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mGeofenceService.destroyGeofence(this.mContextInfo, i);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with geofencing service", e);
            return false;
        }
    }

    @RequiresPermission(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_GEOFENCING_TAG)
    public List<Geofence> getGeofences() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mGeofenceService.getGeofences(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with geofencing service", e);
            return null;
        }
    }

    public float getMinDistanceParameter() {
        if (getService() == null) {
            return -1.0f;
        }
        try {
            return this.mGeofenceService.getMinDistanceParameter(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with geofencing service", e);
            return -1.0f;
        }
    }

    public long getMinTimeParameter() {
        if (getService() == null) {
            return -1L;
        }
        try {
            return this.mGeofenceService.getMinTimeParameter(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with geofencing service", e);
            return -1L;
        }
    }

    public final IGeofencing getService() {
        if (this.mGeofenceService == null) {
            this.mGeofenceService =
                    IGeofencing.Stub.asInterface(ServiceManager.getService("geofencing"));
        }
        return this.mGeofenceService;
    }

    @RequiresPermission(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_GEOFENCING_TAG)
    public List<Integer> isDeviceInsideGeofence() {
        if (getService() == null) {
            return null;
        }
        try {
            return this.mGeofenceService.isDeviceInsideGeofence(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with geofencing service", e);
            return null;
        }
    }

    @RequiresPermission(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_GEOFENCING_TAG)
    public boolean isGeofencingEnabled() {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mGeofenceService.isGeofencingEnabled(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with geofencing service", e);
            return false;
        }
    }

    @RequiresPermission(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_GEOFENCING_TAG)
    public boolean setMinDistanceParameter(float f) {
        EnterpriseLicenseManager.log(this.mContextInfo, "Geofencing.setMinDistanceParameter");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mGeofenceService.setMinDistanceParameter(this.mContextInfo, f);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with geofencing service", e);
            return false;
        }
    }

    @RequiresPermission(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_GEOFENCING_TAG)
    public boolean setMinTimeParameter(long j) {
        EnterpriseLicenseManager.log(this.mContextInfo, "Geofencing.setMinTimeParameter");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mGeofenceService.setMinTimeParameter(this.mContextInfo, j);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with geofencing service", e);
            return false;
        }
    }

    @RequiresPermission(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_GEOFENCING_TAG)
    public boolean startGeofencing() {
        EnterpriseLicenseManager.log(this.mContextInfo, "Geofencing.startGeofencing");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mGeofenceService.startGeofencing(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with geofencing service", e);
            return false;
        }
    }

    @RequiresPermission(EnterpriseDeviceAdminInfo.USES_POLICY_MDM_GEOFENCING_TAG)
    public boolean stopGeofencing() {
        EnterpriseLicenseManager.log(this.mContextInfo, "Geofencing.stopGeofencing");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mGeofenceService.stopGeofencing(this.mContextInfo);
        } catch (RemoteException e) {
            Log.w(TAG, "Failed talking with geofencing service", e);
            return false;
        }
    }

    public static Geofencing getInstance(ContextInfo contextInfo, Context context) {
        synchronized (mSync) {
            try {
                if (contextInfo == null || context == null) {
                    return null;
                }
                return new Geofencing(contextInfo, context.getApplicationContext());
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
