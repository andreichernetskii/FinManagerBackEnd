package com.example.finmanagerbackend.limit;

import com.example.finmanagerbackend.analyser.FinAnalyser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LimitService {
    private final LimitRepository limitRepository;

    public LimitService( LimitRepository limitRepository ) {
        this.limitRepository = limitRepository;
    }

    @Transactional
    public void deleteLimit( LimitType limitId ) {
        if ( limitId == LimitType.ZERO ) {
            throw new IllegalStateException( "Cannot delete the default limit with type ZERO." );
        }

        boolean isLimitExists = limitRepository.existsById( limitId );
        if ( !isLimitExists ) {
            throw new IllegalStateException( "Limit with id " + limitId + " is not exists!" );
        }

        limitRepository.deleteById( limitId );
    }

    public List<Limit> getLimits() {
        return limitRepository.getAllLimitsWithoutZero();
    }

    public void addLimit( LimitDTO limitDTO ) {
        Limit limit = createLimit( limitDTO );
        limitRepository.save( limit );
    }

    public void updateLimit( LimitType limitId, Limit limit ) {
        Optional<Limit> optimalLimit = limitRepository.findById( limitId );
        if ( !optimalLimit.isPresent() ) {
            throw new IllegalStateException( "Limit z id " + limitId + " nie istnieje w bazie!" );
        }

        limitRepository.save( limit );
    }


    // not DB using functions

    public List<String> getLimitTypes() {
        List<String> list = new ArrayList<>();
        for ( LimitType limType : LimitType.values() ) {
            list.add( limType.toString() );
        }

        return list;
    }

    private Limit createLimit( LimitDTO limitDTO ) {
        return new Limit(
                limitDTO.getLimitType(),
                limitDTO.getLimitAmount(),
                limitDTO.getCategory(),
                limitDTO.getCreationDate()
        );
    }
}
