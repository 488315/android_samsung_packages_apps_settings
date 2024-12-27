package com.samsung.android.settings.voiceinput.offline;

import androidx.lifecycle.LiveData;

import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.OkHttpCall;
import retrofit2.Response;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class LiveDataCallAdapter<T> implements CallAdapter {
    private Type mResponseType;

    public LiveDataCallAdapter(Type type) {
        this.mResponseType = type;
    }

    @Override // retrofit2.CallAdapter
    public final Object adapt(final OkHttpCall okHttpCall) {
        return new LiveData() { // from class:
                                // com.samsung.android.settings.voiceinput.offline.LiveDataCallAdapter.1
            AtomicBoolean started = new AtomicBoolean(false);

            @Override // androidx.lifecycle.LiveData
            public final void onActive() {
                if (this.started.compareAndSet(false, true)) {
                    okHttpCall.enqueue(
                            new Callback() { // from class:
                                             // com.samsung.android.settings.voiceinput.offline.LiveDataCallAdapter.1.1
                                @Override // retrofit2.Callback
                                public final void onFailure(Throwable th) {
                                    postValue(ApiResponse.create(th));
                                }

                                @Override // retrofit2.Callback
                                public final void onResponse(Response response) {
                                    postValue(ApiResponse.create(response));
                                }
                            });
                }
            }
        };
    }

    @Override // retrofit2.CallAdapter
    public final Type responseType() {
        return this.mResponseType;
    }
}
