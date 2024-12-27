package com.samsung.android.settings.voiceinput;

import com.samsung.android.knox.net.apn.ApnSettings;
import com.samsung.android.settings.voiceinput.offline.ApiResponseCallAdapterFactory;
import com.samsung.android.settings.voiceinput.offline.LiveDataCallAdapterFactory;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import org.simpleframework.xml.core.Persister;

import retrofit2.BuiltInConverters;
import retrofit2.Platform;
import retrofit2.Retrofit;
import retrofit2.Utils;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public class GalaxyAppsApiClient {
    private static Retrofit mRetrofit;

    public static Retrofit getClient() {
        if (mRetrofit == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE;
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY;
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            ((ArrayList) builder.interceptors).add(httpLoggingInterceptor);
            OkHttpClient okHttpClient = new OkHttpClient(builder);
            Platform platform = Platform.PLATFORM;
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Type[] typeArr = Utils.EMPTY_TYPE_ARRAY;
            HttpUrl httpUrl = null;
            try {
                HttpUrl.Builder builder2 = new HttpUrl.Builder();
                builder2.parse(null, SamsungAppsConstant.BASE_URL);
                httpUrl = builder2.build();
            } catch (IllegalArgumentException unused) {
            }
            if (httpUrl == null) {
                throw new IllegalArgumentException(
                        "Illegal URL: ".concat(SamsungAppsConstant.BASE_URL));
            }
            Type[] typeArr2 = Utils.EMPTY_TYPE_ARRAY;
            if (!ApnSettings.MVNO_NONE.equals(httpUrl.pathSegments.get(r4.size() - 1))) {
                throw new IllegalArgumentException("baseUrl must end in /: " + httpUrl);
            }
            arrayList.add(new SimpleXmlConverterFactory(new Persister()));
            arrayList2.add(new LiveDataCallAdapterFactory());
            arrayList2.add(new ApiResponseCallAdapterFactory());
            Executor defaultCallbackExecutor = platform.defaultCallbackExecutor();
            ArrayList arrayList3 = new ArrayList(arrayList2);
            arrayList3.add(platform.defaultCallAdapterFactory(defaultCallbackExecutor));
            ArrayList arrayList4 = new ArrayList(arrayList.size() + 1);
            arrayList4.add(new BuiltInConverters());
            arrayList4.addAll(arrayList);
            mRetrofit =
                    new Retrofit(
                            okHttpClient,
                            httpUrl,
                            Collections.unmodifiableList(arrayList4),
                            Collections.unmodifiableList(arrayList3));
        }
        return mRetrofit;
    }
}
