package sg.np.edu.mad.animationtest;

import java.lang.annotation.*;
import java.util.function.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.PARAMETER, ElementType.METHOD})
@interface AcceptStrings { //Any parameter that do not fulfil this requirement will throw IllegalArgumentException
    String[] permittedStrings();

    String defaultValueIfInvalid() default "";
}

class CharacterCategory {
    static final int LOWER_CASE_ALPHABET = 0;
    static final int UPPER_CASE_ALPHABET = 1;
    static final int NUMERICALS = 2;

    private int value;

    @FunctionalInterface
    private interface Exec{
        void run() throws NoSuchMethodException;
    }

    private static void process(Exec r) {
        try {
            r.run();
        }
        catch (NoSuchMethodException e){ }
    }

    private void check() throws NoSuchMethodException {
        if (!CharCategory.class.getMethod("value", int.class).isAccessible()){
            throw new NoSuchMethodException("No such method");
        }
        else {
            this.value = (int) CharCategory.class.getMethod("value", int.class).getDefaultValue();
        }
    }

    public int getValue(){
        process(() -> { check(); });
        return this.value;
    }
}

@interface CharCategory{
    int value() default 0;
}

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.PARAMETER, ElementType.METHOD})
@interface CharRange {
    //default value based on CharacterCategory.getValue()
    char from() default new CharacterCategory().getValue();

    char to() default 'z';
}

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.PARAMETER, ElementType.METHOD})
@interface AcceptStringsByRegex {
    String[] multipleRegex() default "";

    String singleRegex() default "";
}



