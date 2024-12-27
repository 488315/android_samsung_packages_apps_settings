package com.samsung.android.settings.voiceinput.offline;

import retrofit2.Response;

import java.io.IOException;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public abstract class ApiResponse<T> {
    private static final String TAG = "@VoiceIn: ApiResponse";

    public static ApiErrorResponse create(Throwable th) {
        return new ApiErrorResponse(th.getMessage() == null ? "Unknown Error" : th.getMessage());
    }

    public static ApiResponse create(Response response) {
        String str;
        okhttp3.Response response2 = response.rawResponse;
        if (response2.isSuccessful()) {
            Object obj = response.body;
            return (obj == null || response2.code == 204)
                    ? new ApiEmptyResponse()
                    : new ApiSuccessResponse(obj);
        }
        try {
            str = response.errorBody.string();
        } catch (IOException e) {
            e.printStackTrace();
            str = null;
        }
        if (str == null || str.isEmpty()) {
            str = response2.message;
        }
        if (str == null) {
            str = "Unknown Error";
        }
        return new ApiErrorResponse(str);
    }
}
