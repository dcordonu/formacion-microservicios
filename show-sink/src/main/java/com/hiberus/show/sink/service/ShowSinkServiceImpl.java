package com.hiberus.show.sink.service;

import com.hiberus.show.library.repository.Show;
import com.hiberus.show.library.topology.OutputShowPlatformListEvent;
import com.hiberus.show.library.topology.OutputShowPlatformListKey;
import com.hiberus.show.sink.mapper.ShowMapper;
import com.hiberus.show.sink.repository.ShowSinkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShowSinkServiceImpl implements ShowSinkService {

    private final ShowMapper showMapper;
    private final ShowSinkRepository showSinkRepository;

    @Override
    public void performOperation(final OutputShowPlatformListKey key, final OutputShowPlatformListEvent value) {
        switch (value.getEventType()) {
            case CREATE:
            case UPDATE:
                this.createOrUpdateShow(showMapper.map(key, value));
                break;
            case DELETE:
                this.deleteShow(key.getIsan());
        }
    }

    private void createOrUpdateShow(final Show show) {
        final Optional<Show> optionalShow = this.showSinkRepository.findByIsan(show.getIsan());
        if (optionalShow.isPresent()) {
            log.info("Show with isan {} found, updating...", show.getIsan());
            this.showSinkRepository.save(this.showMapper.merge(optionalShow.get(), show));
        } else {
            log.info("Show with isan {} not found, creating...", show.getIsan());
            this.showSinkRepository.insert(show);
        }
    }

    private void deleteShow(final String isan) {
        final Optional<Show> optionalShow = this.showSinkRepository.findByIsan(isan);
        if (optionalShow.isPresent()) {
            log.info("Show with isan {} found, deleting...", isan);
            this.showSinkRepository.delete(optionalShow.get());
        } else {
            log.info("Show with isan {} not found.", isan);
        }
    }
}
