package soft_uni.makeupstore.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class ValidatorServiceImpl implements ValidatorService {

    private final jakarta.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public <E> boolean isValid(E entity) {
        return this.validator.validate(entity).isEmpty();
    }

    @Override
    public <E> Set<ConstraintViolation<E>> validate(E entity) {
        return this.validator.validate(entity);
    }
}
