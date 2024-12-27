package com.samsung.android.settings.voiceinput.offline;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class ApiResponseCall<T> implements Call<ApiResponse<T>> {
    private Call<T> mDelegate;

    public ApiResponseCall(Call call) {
        this.mDelegate = call;
    }

    @Override // retrofit2.Call
    public final void enqueue(final Callback callback) {
        this.mDelegate.enqueue(
                new Callback() { // from class:
                                 // com.samsung.android.settings.voiceinput.offline.ApiResponseCall.1
                    @Override // retrofit2.Callback
                    public final void onFailure(Throwable th) {
                        callback.onResponse(Response.success(ApiResponse.create(th)));
                    }

                    @Override // retrofit2.Callback
                    public final void onResponse(Response response) {
                        callback.onResponse(Response.success(ApiResponse.create(response)));
                    }
                });
    }

    @Override // retrofit2.Call
    public final Response execute() {
        Response execute = this.mDelegate.execute();
        return execute.rawResponse.isSuccessful()
                ? Response.success(ApiResponse.create(execute))
                : Response.success(ApiResponse.create(new Throwable(execute.errorBody.string())));
    }

    @Override // retrofit2.Call
    public final boolean isCanceled() {
        return this.mDelegate.isCanceled();
    }

    @Override // retrofit2.Call
    public final Call clone() {
        return new ApiResponseCall(this.mDelegate);
    }
}
