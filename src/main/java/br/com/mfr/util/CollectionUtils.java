package br.com.mfr.util;

import java.util.Collection;

public class CollectionUtils {

    private CollectionUtils() { }

    public static boolean isEmpty(Collection collection) {
        return (collection == null || collection.isEmpty());
    }
}
