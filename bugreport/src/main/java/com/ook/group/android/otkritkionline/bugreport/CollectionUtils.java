package com.ook.group.android.otkritkionline.bugreport;

import java.util.Collection;

public class CollectionUtils {
    private CollectionUtils() {/* No instances */}

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }
}
