package sg.np.edu.mad.animationtest;

import java.lang.Throwable;

final class InvalidBinaryNumberFormat extends Exception {
    private String msg;

    public InvalidBinaryNumberFormat (String msg){
        this.msg = msg;
    }
}


final class NotAnArrayException extends Exception {
    private String msg;

    public NotAnArrayException(String msg) {
        this.msg = msg;
    }

    public NotAnArrayException() { }
}

//This error will be thrown when a multi-dimensional array is supplied instead of a single dimension array
final class NotSingleDimensionalArrayException extends Exception {
    private String msg;

    public NotSingleDimensionalArrayException() { }

    public NotSingleDimensionalArrayException(String msg) { this.msg = msg; }
}

final class ContentsNotPrimitiveException extends Exception {
    private String msg;

    public ContentsNotPrimitiveException(String msg) {
        this.msg = msg;
    }

    public ContentsNotPrimitiveException() { }
}

//Throw this exception when Object[][] is not expressed in the correct format needed to be converted into ParsedMap<K, V>
final class InvalidObjectDoubleArrayToMapFormatException extends Exception {
    private String msg;

    public InvalidObjectDoubleArrayToMapFormatException(String msg){ this. msg = msg; }

    public InvalidObjectDoubleArrayToMapFormatException() { }
}

//Throw this exception when String is not expressed in the correct format needed to be converted into ParsedMap<K, V>
final class InvalidStringToMapFormatException extends Exception {
    private String msg;

    public InvalidStringToMapFormatException(String msg) { this.msg = msg; }

    public InvalidStringToMapFormatException() { }
}

//Throw this exception when an int value cannot be 0 or less than 0
final class ValueLessThanOrEqualsZeroException extends Exception{
    private String msg;

    public ValueLessThanOrEqualsZeroException(String msg){ this.msg = msg; }

    public ValueLessThanOrEqualsZeroException() { }
}

final class NoSuchKeyException extends Exception{
    private String msg;

    public NoSuchKeyException(String msg){ this.msg = msg; }

    public NoSuchKeyException(){ }
}

final class IncompatibleTypeException extends Exception {
    private String msg;

    public IncompatibleTypeException(){

    }

    public IncompatibleTypeException(String msg){
        this.msg = msg;
    }
}

//Throw this exception when there is a syntax error within the conditional regex statement
final class RegexConditionalCommandSyntaxError extends Exception {
    private String msg;

    public RegexConditionalCommandSyntaxError() {

    }

    public RegexConditionalCommandSyntaxError(String msg) {
        this.msg = msg;
    }
}

//Throw this exception when the required target is not of type "String"
final class NotAStringException extends Exception {
    private String msg;

    public NotAStringException(){

    }

    public NotAStringException(String msg){ this.msg = msg; }
}

//Throw this exception when the string placeholder is not expressed correctly
final class InvalidStringFormatPlaceholderException extends Exception {
    private String msg;

    public InvalidStringFormatPlaceholderException(){

    }

    public InvalidStringFormatPlaceholderException(String msg){
        this.msg = msg;
    }
}