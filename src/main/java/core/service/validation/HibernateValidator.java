package core.service.validation;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Имплементация интерфейса IValidator.
 * Использует реализацию спецификации Bean Validation API - org.hibernate.validator
 * Реализация может использоваться всеми сущностями, которые используют валидацию на базе аннотаций,
 * определенных в спецификации.
 *
 * @param <T> the type parameter
 */
@Service
@PropertySource(value = "classpath:validation_messages.properties", encoding = "UTF-8")
public class HibernateValidator<T> implements IValidator<T> {

    @Autowired
    private MessageSource messageSource;
    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Override
    public List<String> isValid(T model) {
        return validator.validate(model)
                .stream()
                .map(violation -> messageSource.getMessage(violation.getMessage(),null, null))
                .collect(Collectors.toList());

    }


}
