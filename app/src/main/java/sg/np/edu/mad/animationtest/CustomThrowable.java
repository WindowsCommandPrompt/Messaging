package sg.np.edu.mad.animationtest;

import java.lang.Throwable;

final class InvalidBinaryNumberFormat extends Exception {
    private String msg;

    public InvalidBinaryNumberFormat (String msg){
        this.msg = msg;
    }

    public void setMessage(String msg){
        this.msg = msg;
    }

    public String getMessage(String msg){
        return this.msg;
    }
}


final class NotAnArrayException extends Exception {
    private String msg;

    public NotAnArrayException(String msg) {
        this.msg = msg;
    }

    public NotAnArrayException() { }

    public void setMessage(String msg){
        this.msg = msg;
    }

    public String getMessage(String msg){
        return this.msg;
    }
}

final class ContentsNotPrimitiveException extends Exception {
    private String msg;

    public ContentsNotPrimitiveException(String msg) {
        this.msg = msg;
    }

    public ContentsNotPrimitiveException() { }

    public void setMessage(String msg){
        this.msg = msg;
    }

    public String getMessage(String msg){
        return this.msg;
    }
}

//Throw this exception when Object[][] is not expressed in the correct format needed to be converted into ParsedMap<K, V>
final class InvalidObjectDoubleArrayToMapFormatException extends Exception {
    private String msg;

    public InvalidObjectDoubleArrayToMapFormatException(String msg){ this. msg = msg; }

    public InvalidObjectDoubleArrayToMapFormatException() { }

    public void setMessage(String msg){
        this.msg = msg;
    }

    public String getMessage(String msg){
        return this.msg;
    }
}

//Throw this exception when String is not expressed in the correct format needed to be converted into ParsedMap<K, V>
final class InvalidStringToMapFormatException extends Exception {
    private String msg;

    public InvalidStringToMapFormatException(String msg) { this.msg = msg; }

    public InvalidStringToMapFormatException() { }

    public void setMessage(String msg){
        this.msg = msg;
    }

    public String getMessage(String msg){
        return this.msg;
    }
}

//Throw this exception when an int value cannot be 0 or less than 0
final class ValueLessThanOrEqualsZeroException extends Exception{
    private String msg;

    public ValueLessThanOrEqualsZeroException(String msg){ this.msg = msg; }

    public ValueLessThanOrEqualsZeroException() { }

    public void setMessage(String msg){
        this.msg = msg;
    }

    public String getMessage(String msg){
        return this.msg;
    }
}

final class NoSuchKeyException extends Exception{
    private String msg;

    public NoSuchKeyException(String msg){ this.msg = msg; }

    public NoSuchKeyException(){ }

    public void setMessage(String msg){
        this.msg = msg;
    }

    public String getMessage(String msg){
        return this.msg;
    }
}

final class IncompatibleTypeException extends Exception {
    private String msg;

    public IncompatibleTypeException(){

    }

    public IncompatibleTypeException(String msg){
        this.msg = msg;
    }

    public void setMessage(String msg){
        this.msg = msg;
    }

    public String getMessage(String msg){
        return this.msg;
    }
}

final class RegexCommandSyntaxError extends Exception {
    private String msg;

    public RegexCommandSyntaxError() {

    }

    public RegexCommandSyntaxError(String msg) {
        this.msg = msg;
    }
}