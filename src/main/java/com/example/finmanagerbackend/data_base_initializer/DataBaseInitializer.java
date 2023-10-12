package com.example.finmanagerbackend.data_base_initializer;

import com.example.finmanagerbackend.limit.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class DataBaseInitializer implements ApplicationRunner {
    private LimitService limitService;
    private LimitRepository limitRepository;

    public DataBaseInitializer( LimitService limitService, LimitRepository limitRepository ) {
        this.limitService = limitService;
        this.limitRepository = limitRepository;
    }

    private LimitDTO createZeroLimit() {
        return new LimitDTO(
                LimitType.ZERO,
                new BigDecimal( 0 ),
                null,
                LocalDate.now() // todo: pamiętać, że tutaj już ustalona data. bo niewiadomo teraz, jaki będzie algorytm pinowania limita od current date
        );
    }

    @Override
    public void run( ApplicationArguments args ) throws Exception {
        LimitDTO limitDTO = createZeroLimit();
        if ( !isZeroLimExists( limitDTO ) ) limitService.addLimit( limitDTO );
    }

    // todo: czy ma to sens robić przez Example?
    private boolean isZeroLimExists(LimitDTO limitDTO) {
        Example<Limit> limitExample = Example.of( new Limit(
                limitDTO.getLimitType(),
                limitDTO.getLimitAmount(),
                limitDTO.getCategory(),
                limitDTO.getCreationDate()
        ) );

        Optional<Limit> optionalLimit = limitRepository.findOne( limitExample );
        return optionalLimit.isPresent();
    }
}
