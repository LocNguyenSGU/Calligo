package com.example.commonservice.customEnum;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    private Class<? extends Enum<?>> enumClass;

    @Override
    public void initialize(ValidEnum annotation) {
        this.enumClass = annotation.enumClass(); // Lấy enum class từ annotation
    }
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Cho phép null, nếu muốn bắt lỗi null thì dùng thêm annotation @NotNull
        }
        // Kiểm tra xem giá trị có khớp với bất kỳ tên nào của enum không
        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(enumValue -> enumValue.name().equals(value));
    }
}
