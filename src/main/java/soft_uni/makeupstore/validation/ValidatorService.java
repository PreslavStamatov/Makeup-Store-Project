package soft_uni.makeupstore.validation;

import jakarta.validation.ConstraintViolation;

import java.util.Set;

public interface ValidatorService {

    <E> boolean isValid(E entity);

    <E> Set<ConstraintViolation<E>> validate(E entity);
}
