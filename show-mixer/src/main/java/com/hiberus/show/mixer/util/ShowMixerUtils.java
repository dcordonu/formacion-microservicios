package com.hiberus.show.mixer.util;

import java.text.Normalizer;

public class ShowMixerUtils {

    private ShowMixerUtils() { }

    public static String normalize(final String text) {
        return Normalizer
                .normalize(text, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }
}
