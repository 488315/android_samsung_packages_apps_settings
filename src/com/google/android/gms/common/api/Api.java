package com.google.android.gms.common.api;

import android.content.Context;
import android.os.Looper;

import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.api.internal.zabp;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.IAccountAccessor;

import java.util.Set;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class Api {
    public final AbstractClientBuilder zaa;
    public final String zac;

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class AbstractClientBuilder<T extends Client, O>
            extends BaseClientBuilder<T, O> {
        public Client buildClient(
                Context context,
                Looper looper,
                ClientSettings clientSettings,
                Object obj,
                GoogleApiClient.ConnectionCallbacks connectionCallbacks,
                GoogleApiClient.OnConnectionFailedListener onConnectionFailedListener) {
            return buildClient(
                    context,
                    looper,
                    clientSettings,
                    obj,
                    (ConnectionCallbacks) connectionCallbacks,
                    (OnConnectionFailedListener) onConnectionFailedListener);
        }

        public Client buildClient(
                Context context,
                Looper looper,
                ClientSettings clientSettings,
                Object obj,
                ConnectionCallbacks connectionCallbacks,
                OnConnectionFailedListener onConnectionFailedListener) {
            throw new UnsupportedOperationException("buildClient must be implemented");
        }
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface ApiOptions {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public abstract class BaseClientBuilder<T extends Client, O> {}

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public interface Client {
        void connect(
                BaseGmsClient.ConnectionProgressReportCallbacks connectionProgressReportCallbacks);

        void disconnect();

        void disconnect(String str);

        Feature[] getAvailableFeatures();

        String getEndpointPackageName();

        String getLastDisconnectMessage();

        int getMinApkVersion();

        void getRemoteService(IAccountAccessor iAccountAccessor, Set set);

        Set getScopesForConnectionlessNonSignIn();

        boolean isConnected();

        boolean isConnecting();

        void onUserSignOut(zabp zabpVar);

        boolean requiresSignIn();
    }

    /* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
    public final class ClientKey<C extends Client> {}

    public Api(String str, AbstractClientBuilder abstractClientBuilder, ClientKey clientKey) {
        this.zac = str;
        this.zaa = abstractClientBuilder;
    }
}
