package com.github.rschmitt.dynamicobject.internal;

import static com.github.rschmitt.dynamicobject.internal.ClojureStuff.Assoc;
import static com.github.rschmitt.dynamicobject.internal.ClojureStuff.Get;
import static com.github.rschmitt.dynamicobject.internal.ClojureStuff.Meta;
import static com.github.rschmitt.dynamicobject.internal.ClojureStuff.Name;
import static com.github.rschmitt.dynamicobject.internal.ClojureStuff.Type;
import static com.github.rschmitt.dynamicobject.internal.ClojureStuff.VaryMeta;
import static com.github.rschmitt.dynamicobject.internal.ClojureStuff.cachedRead;

public class Metadata {
    static Class<?> getTypeMetadata(Object obj) {
        Object meta = Meta.invoke(obj);
        if (meta == null) return null;
        Object tag = Get.invoke(meta, Type);
        if (tag == null) return null;
        try {
            return Class.forName((String) Name.invoke(tag));
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException(ex);
        }
    }

    static Object withTypeMetadata(Object obj, Class<?> type) {
        return VaryMeta.invoke(obj, Assoc, Type, cachedRead(":" + type.getTypeName()));
    }
}