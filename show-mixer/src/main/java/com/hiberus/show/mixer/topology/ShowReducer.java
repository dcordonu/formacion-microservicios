package com.hiberus.show.mixer.topology;

import com.hiberus.show.library.EventType;
import com.hiberus.show.library.InputShowEvent;
import org.apache.kafka.streams.kstream.Reducer;
import org.springframework.stereotype.Component;

@Component
public class ShowReducer implements Reducer<InputShowEvent> {

    @Override
    public InputShowEvent apply(InputShowEvent previous, InputShowEvent current) {
        final InputShowEvent result;

        if (EventType.DELETE.equals(current.getEventType())) {
            result = null;
        } else {
            result = current;
        }

        return result;
    }
}
