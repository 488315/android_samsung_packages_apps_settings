package com.samsung.android.settings.voiceinput.offline;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ApiResponseCallAdapterFactory extends CallAdapter.Factory {
    @Override // retrofit2.CallAdapter.Factory
    public final CallAdapter get(Type type) {
        if (Utils.getRawType(type) != Call.class) {
            return null;
        }
        Type parameterUpperBound = Utils.getParameterUpperBound(0, (ParameterizedType) type);
        if (Utils.getRawType(parameterUpperBound) != ApiResponse.class) {
            throw new IllegalArgumentException("type must be a ApiResponse");
        }
        if (parameterUpperBound instanceof ParameterizedType) {
            return new ApiResponseCallAdapter(
                    Utils.getParameterUpperBound(0, (ParameterizedType) parameterUpperBound));
        }
        throw new IllegalArgumentException("ApiResponse must be parameterized");
    }
}
