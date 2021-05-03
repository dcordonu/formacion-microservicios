package com.hiberus.show.mixer.topology;

import com.hiberus.show.library.InputShowEvent;
import org.apache.kafka.streams.kstream.Reducer;
import org.springframework.stereotype.Component;

@Component
public class ShowReducer implements Reducer<InputShowEvent> {

    @Override
    public InputShowEvent apply(InputShowEvent previous, InputShowEvent current) {
        return current;
    }
}
