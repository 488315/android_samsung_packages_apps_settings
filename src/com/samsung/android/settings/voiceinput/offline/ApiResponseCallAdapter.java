package com.samsung.android.settings.voiceinput.offline;

import retrofit2.CallAdapter;
import retrofit2.OkHttpCall;

import java.lang.reflect.Type;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ApiResponseCallAdapter<T> implements CallAdapter {
    private Type mSuccessType;

    public ApiResponseCallAdapter(Type type) {
        this.mSuccessType = type;
    }

    @Override // retrofit2.CallAdapter
    public final Object adapt(OkHttpCall okHttpCall) {
        return new ApiResponseCall(okHttpCall);
    }

    @Override // retrofit2.CallAdapter
    public final Type responseType() {
        return this.mSuccessType;
    }
}
