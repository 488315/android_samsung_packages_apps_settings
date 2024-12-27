package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.reflect.TypeToken;

/* compiled from: qb/89523975 2c9f6d15a1195540f21380d26e2599d2824bfc1ae85110b01296b5f4d9a9b658 */
/* loaded from: classes3.dex */
public final class JsonAdapterAnnotationTypeAdapterFactory implements TypeAdapterFactory {
    public final ConstructorConstructor constructorConstructor;

    public JsonAdapterAnnotationTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    public static TypeAdapter getTypeAdapter(
            ConstructorConstructor constructorConstructor,
            Gson gson,
            TypeToken typeToken,
            JsonAdapter jsonAdapter) {
        TypeAdapter create;
        Object construct =
                constructorConstructor.get(TypeToken.get(jsonAdapter.value())).construct();
        boolean nullSafe = jsonAdapter.nullSafe();
        if (construct instanceof TypeAdapter) {
            create = (TypeAdapter) construct;
        } else {
            if (!(construct instanceof TypeAdapterFactory)) {
                throw new IllegalArgumentException(
                        "Invalid attempt to bind an instance of "
                                + construct.getClass().getName()
                                + " as a @JsonAdapter for "
                                + typeToken.toString()
                                + ". @JsonAdapter value must be a TypeAdapter, TypeAdapterFactory,"
                                + " JsonSerializer or JsonDeserializer.");
            }
            create = ((TypeAdapterFactory) construct).create(gson, typeToken);
        }
        return (create == null || !nullSafe) ? create : create.nullSafe();
    }

    @Override // com.google.gson.TypeAdapterFactory
    public final TypeAdapter create(Gson gson, TypeToken typeToken) {
        JsonAdapter jsonAdapter =
                (JsonAdapter) typeToken.getRawType().getAnnotation(JsonAdapter.class);
        if (jsonAdapter == null) {
            return null;
        }
        return getTypeAdapter(this.constructorConstructor, gson, typeToken, jsonAdapter);
    }
}
