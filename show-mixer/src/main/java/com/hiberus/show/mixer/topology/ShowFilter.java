package com.hiberus.show.mixer.topology;

import com.hiberus.show.library.topology.EventType;
import com.hiberus.show.library.topology.InputPlatformEvent;
import com.hiberus.show.library.topology.InputShowEvent;
import com.hiberus.show.library.topology.OutputShowPlatformListKey;
import com.hiberus.show.mixer.service.ShowMixerServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.streams.kstream.Predicate;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShowFilter implements Predicate<OutputShowPlatformListKey, InputShowEvent> {

    private final InteractiveQueryService queryService;

    private ReadOnlyKeyValueStore<OutputShowPlatformListKey, InputShowEvent> showStore;

    @Override
    public boolean test(final OutputShowPlatformListKey key, final InputShowEvent current) {
        final boolean passes;
        final InputShowEvent previous = showStore().get(key);

        if (EventType.DELETE.equals(current.getEventType())) {
            passes = true;
        } else {
            passes = previous == null || !StringUtils.equals(previous.getName(), current.getName());

            if (passes) {
                final EventType eventType = current.getEventType();
                if (previous != null && !EventType.UPDATE.equals(eventType)) {
                    log.warn("Entered value should be an UPDATE, not {}. Updating...", eventType);
                    current.setEventType(EventType.UPDATE);
                } else if (previous == null && EventType.UPDATE.equals(eventType)) {
                    log.warn("Entered value should be a CREATE, not {}. Updating...", eventType);
                    current.setEventType(EventType.CREATE);
                }
            }
        }

        log.info("Previous: {}", previous);
        log.info("Current: {}", current);
        log.info("Show has changes: {}", passes);

        return passes;
    }

    private ReadOnlyKeyValueStore<OutputShowPlatformListKey, InputShowEvent> showStore() {
        if (showStore == null) {
            showStore = queryService.getQueryableStore(ShowMixerServiceImpl.SHOW_TABLE, QueryableStoreTypes.keyValueStore());
        }

        return showStore;
    }
}
