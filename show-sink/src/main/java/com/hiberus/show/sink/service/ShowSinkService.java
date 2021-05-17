package com.hiberus.show.sink.service;

import com.hiberus.show.library.topology.OutputShowPlatformListEvent;
import com.hiberus.show.library.topology.OutputShowPlatformListKey;

public interface ShowSinkService {

    void performOperation(final OutputShowPlatformListKey key, final OutputShowPlatformListEvent value);
}
