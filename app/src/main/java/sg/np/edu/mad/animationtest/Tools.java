package sg.np.edu.mad.animationtest;

import androidx.annotation.NonNull;

import java.util.*;
import java.util.function.*;

import android.util.Log;

final class Tools {

    private Tools(){

    }

    public static final class ParsedMap<K, V> {
    /*
       GOAL: To allow users to be able to create a map using

       Goal of the ParsedMap<K, V> as shown below

       ParsedMap<String, Integer> parsedMap = new ParsedMap<>(Arrays.asList(                =>      new Object[][] parsedMap = new Object[][]{         =>    String representation = "{ {\"key1\": \"sdf\"},  "
            new Entry<>("Key1", "sdf"),                                                     =>           { "key1", "sdf" },
            new Entry<>("Key2", Arrays.asList(                                              =>           { "key2", Arrays.asList(
                new Entry<>("InternalKey", 14)                                              =>               new Object[][]{
            ))                                                                              =>                   { "InternalKey", 14 }
        ));                                                                                 =>               }
                                                                                            =>          ) }
                                                                                            =>      }

       need a method to serve as a parser to convert it into a format so that indexing can be used to retrieve the required data
       Keys cannot be of type List<T>, Entry<K, V>
        */

        private K key;
        private V value;
        private Object[] keys; //unique to each ParsedMap
        private Object[] values; //unique to each ParsedMap
        private boolean allowDuplicate;
        private long defaultEntriesLimit = Long.MAX_VALUE;
        private int numOfNestedArrays = 0;

        class Entry<K, V> {   //K, V type parameters in Entry<K, V> must match K, V type parameters written in ParsedMap<K, V>
            private K key; //stores the value of the key
            private V value; //stores the value of the value

            public Entry(K key, V value){
                //check for type mismatch errors. Need to call
                this.key = key;
                this.value = value;
            }

            public K getKey(){
                return this.key;
            }

            public void setKey(K key){
                this.key = key;
            }

            public V getValue(){
                return this.value;
            }

            public void setValue(V value){
                this.value = value;
            }
        }

        //initialize empty map, attach entries using the setter
        @SuppressWarnings("unchecked")
        public ParsedMap(long entryLimit, boolean allowDuplicate) throws ValueLessThanOrEqualsZeroException{
            if (entryLimit > 0) {
                this.defaultEntriesLimit = entryLimit;
                this.keys = (K[]) new Object[(int) entryLimit];
                this.values = (V[]) new Object[(int) entryLimit];
                this.allowDuplicate = allowDuplicate;
            }
            else {
                throw new ValueLessThanOrEqualsZeroException("entryLimit parameter cannot be assigned to any value that is less than or equal to 0");
            }
        }

        @SuppressWarnings("unchecked")
        public ParsedMap(long entryLimit) throws ValueLessThanOrEqualsZeroException{
            if (entryLimit > 0) {
                this.defaultEntriesLimit = entryLimit;
                this.keys = (K[]) new Object[(int) entryLimit];
                this.values = (V[]) new Object[(int) entryLimit];
                this.allowDuplicate = false;
            }
            else {
                throw new ValueLessThanOrEqualsZeroException("entryLimit parameter cannot be assigned to any value that is less than or equal to 0");
            }
        }

        //initialize empty map, attach entries using the setter
        public ParsedMap(){
            this.keys = (K[]) new Object[(int) defaultEntriesLimit];
            this.values = (V[]) new Object[(int) defaultEntriesLimit];
            this.allowDuplicate = false;
        }

        //initialize a map that has values in it
        //check the datatype that has been stored within each Entry<> object  UNIQUE ENTRIES ONLY
        public ParsedMap(HashSet<Entry> entries) throws IncompatibleTypeException{
            Class<?> entryKeyClass; Class<?> entryValueClass;
            for (Entry e : entries){
                entryKeyClass = e.key.getClass(); //get the class of the key
                entryValueClass = e.value.getClass(); //get the class of the value
                if (!(entryKeyClass.equals(this.key.getClass())) || !(entryValueClass.equals(this.value.getClass()))){
                    //throw an exception and quit the constructor. Object is not created.
                    //Double -> Float
                    //Long -> Integer -> Short -> Byte
                    //String -> Character
                    throw new IncompatibleTypeException(
                        String.format(
                            entryKeyClass.equals(this.key.getClass())
                                ? entryValueClass.equals(this.value.getClass())
                                    ? ""
                                    : String.format(
                                            "This parsed map only accepts %s as values but %s was given",
                                            (this.value instanceof String
                                                ? "String"
                                                : this.value instanceof Integer
                                                    ? "Integer"
                                                    : this.value instanceof Character
                                                        ? "Character"
                                                        : this.value instanceof Double
                                                            ? "Double"
                                                            : ""),
                                            (e.key instanceof String
                                                ? "String"
                                                : e.key instanceof Integer
                                                    ? "Integer"
                                                    : ""))
                                : "This parsed map only accepts %s as key, but %s was given as one of its entries, so there is a datatype conflict",
                                    (this.key instanceof String
                                        ? "String"
                                        : this.key instanceof Integer
                                            ? "Integer"
                                            : this.key instanceof Character
                                                ? "Character"
                                                : this.key instanceof Double
                                                    ? "Double"
                                                    : ""))
                    );
                }
                //continue looping through the list of entry objects if required, otherwise throw an exception when the loop finishes
            }
            //Directly insert the information into this.keys and this.values separately.
            for (Entry e : entries) {
                for (int i = 0; i < this.keys.length; i++) {
                    if (this.keys[i] == null && this.values[i] == null) {
                        this.keys[i] = e.key;
                        this.values[i] = e.value;
                    }
                }
            }
        }

        //NON-UNIQUE ENTRIES
        public ParsedMap(List<Entry> entries) throws IncompatibleTypeException{
            Class<?> entryKeyClass; Class<?> entryValueClass;

        }

        public <K, V> void setItem(K key, V value) throws NotAnArrayException, ContentsNotPrimitiveException, IncompatibleTypeException{
            //remember to check for duplicate entries.
            Entry<K, V> entry = new Entry<>(key, value); //put  the item inside the Entry<K, V>'s constructor
            K keyToEntry = entry.getKey();
            V valueOfKey = entry.getValue();
            Class<?> classOfKeyToEntry = keyToEntry.getClass();
            Class<?> classOfValueOfKey = valueOfKey.getClass();
            if (classOfKeyToEntry.equals(this.key) && classOfValueOfKey.equals(this.value)) {
                //check if value of type V is an instance of Entry<K, V> or List<?>
                if (!(value instanceof List<?>) || !(value instanceof Entry<?, ?>)) {
                    //value can be either String, Long, Double, Byte, Short, Character, Float, Boolean
                    if (this.allowDuplicate) { //if this.allowDuplicate is true, then just assign it to a slot within the array that is not null
                        for (int i = 0; i < this.keys.length; i++) {
                            //default values for all object is null
                            if (this.keys[i] == null && this.values[i] == null) {
                                this.keys[i] = keyToEntry;  //assign the key at the same index within the keys array
                                this.values[i] = valueOfKey; //assign the value at the same index within the values array
                                break; //quit the for loop after assigning immediately
                            }
                        }
                    } else { //need to run a for loop to check for duplicate entries
                        if (!(Tools.ArrayUtilsCustom.containsElement(value, this.values)) && !(Tools.ArrayUtilsCustom.containsElement(key, this.keys))) {
                            for (int i = 0; i < this.keys.length; i++) {
                                if (this.keys[i] == null && this.values[i] == null) {
                                    this.keys[i] = keyToEntry;
                                    this.values[i] = valueOfKey;
                                    break;
                                }
                            }
                        } else { //drop entry, if duplicate element has been found
                            Log.i("DATANOTINSERTED", "Data is not inserted into the parsed map. Either it is because that parsed map does not allow duplicate entries.\nIf you think this is a mistake, please try to create a new map and re-insert the data again.");
                            Log.i("", "" + String.format(""));
                        }
                    }
                } else {
                    //Must check how many nested lists are there in the value.
                    int listStarters = 0; int listTerminators = 0;
                    //check if the item is really of a list, or just a string representation
                    for (char character : value.toString().toCharArray()){
                        if (character == '['){
                            listStarters++;
                        }
                        if (character == ']'){
                            listTerminators++;
                        }
                    }
                    int result = ((Integer) Converter.ReferenceTypeConverter.primitiveToReferenceType(listStarters)).compareTo((Integer) Converter.ReferenceTypeConverter.primitiveToReferenceType(listTerminators));
                }
            }
            else {
                //incompatible datatypes have been detected
                throw new IncompatibleTypeException(String.format("The parsed map requires the key to be of type %s but %s was provided as its key"));
            }
        }

        public <K> void removeItem(K key) throws NoSuchKeyException {
            for (Object item : this.keys){
                if (key.equals(item)){

                }
            }
        }

        public <K, V> void changeItemValueByKey(K identifierKey, V newValue) throws NoSuchKeyException{
            for (Object key : this.keys){
                if (key != null){
                    if (key.equals(identifierKey)){ //get the identified key
                        int index = Tools.ArrayUtilsCustom.findIndexOfElement(key).in(this.keys);
                        this.values[index] = newValue;
                    }
                }
            }
        }

        public void A(){

        }
    }

    private static final class TypeIdentifierUtil {
        public static final class ItemPosition {
            private int itemPosition;

            public ItemPosition(int itemPosition) {
                this.itemPosition = itemPosition;
            }
        }

        public static final class ItemDataType{
            private String itemDataType;

            public ItemDataType(String itemDataType){
                this.itemDataType = itemDataType;
            }
        }
    }

    public static final class ArrayListUtilsCustom {
        private ArrayListUtilsCustom(){

        }

        private static class InternalStorage {
            private int[] location;
            private String[] dataType;

            private int[] intArrGetter(){
                return this.location;
            }

            private String[] stringArrGetter(){
                return this.dataType;
            }

            private void intArrSetAt(int[] location, int index, int value){
                location[index] = value;
                this.location = location;
            }

            private void stringArrSetAt(String[] dataType, int index, String value){
                dataType[index] = value;
                this.dataType = dataType;
            }

            private void defaultIntArrAlloc(int elemCount) {
                this.location = new int[elemCount];
            }

            private void defaultStringArrAlloc(int elemCount) {
                this.dataType = new String[elemCount];
            }

            public static <T> Object[] checksFor(ArrayList<T> sample, int len){
                Random generator = new Random();
                InternalStorage is = new InternalStorage();
                is.defaultIntArrAlloc(len);                                //allocate the number of slots within the int[]
                is.defaultStringArrAlloc(len);
                int result = generator.nextInt(sample.size() + 1);  //Randomly draw a value that is between 0 and the size of the arraylist.. .
                int retriever = generator.nextInt(sample.size() + 1);
                int initializer = 0;
                //Check if current cell is empty
                is.intArrSetAt(is.intArrGetter(), is.intArrGetter()[initializer] == 0 ? initializer : initializer + 1, result);
                is.stringArrSetAt(is.stringArrGetter(), initializer, sample.get(result) instanceof String ? "Boolean" : sample.get(result) instanceof Integer ? "Integer" : sample.get(result) instanceof Double ? "Double" : );
            }
        }

        public static <T> boolean allAreBoolean(ArrayList<T> sample){
            return sample.size() > 1 ? InternalStorage.checksFor(sample, sample.size()) : sample.size() == 0 ? sample.get(0) instanceof Boolean :
        }

        public static <T> boolean allAreInteger(ArrayList<T> sample){
            return false;
        }

        public static <T> boolean allAreString(ArrayList<T> sample){
            return false;
        }
    }

    @SuppressWarnings({"JavaReflectionMemberAccess", "unchecked"})
    public static final class ArrayUtilsCustom{   //unfortunately you cannot extend the 'Arrays' class because the constructor is private

        private ArrayUtilsCustom(){

        }

        //HashMap<?, ?> ???
        public static <K, V> ParsedMap<K, V> arrayToParseMap(@NonNull Object[][] a) throws NotAnArrayException, ContentsNotPrimitiveException, InvalidObjectDoubleArrayToMapFormatException{
            /*
            IMPORTANT:
            1) Arrays.asList() cannot contain one element that is the Object[][] itself, must add a null if only one element
            2) Arrays.asList() without any elements are allowed
            3) Object[][] is used to represent a map, thus, when initializing a map, use new Object[][] { { } } and not new Object[][] { }!
            4) Nested Arrays.asList() is allowed
            5) new ArrayList<Object>(Arrays.asList()) can also be used, but type must be Object, unless you know what you are storing in it, but
            6) Object[] cannot be used as a substitute for Arrays.asList(), throw an error when obj of type Object[] has been detected
            7) Only Object[][] can be used to represent internal maps

            VALID  REPRESENTATIONS
            1)                                                      2)                                                      3)                                                      4)
            Object[][] parserMap = new Object[][]{                  Object[][] parserMap = new Object[][]{                  Object[][] parserMap = new Object[][]{                  Object[][] parserMap = new Object[][]{
                    {"key1", 1},                                          {   }                                                     Arrays.asList(
                    {"key2", Arrays.asList(                         };                                                                  new Object[][]{
                                new Object[][] {                                                                                            { }
                                    { "key", 12 }                                                                                       },
                                },                                                                                                      null
                                new Object[][] {                                                                                    )
                                    { "key2", 13 }                                                                          };
                                }
                            )
                    }
            };

            Object[][] -> String -> ParsedMap<K, V>

            Maybe this is better?
            new ParsedMap<String, Integer>(Arrays.asList(
                new EntryConstructor<>("Key1", "sdf"),
                new EntryConstructor<>("Key2", Arrays.asList(
                    new EntryConstructor<>("InternalKey", 14)
                ))
            ));
             */

            //analyze the contents which are stored within Object[][]
            //Object[][] -> map      Object[] -> entry       Arrays.asList() -> array

            String translate = "{";
            String starter = ""; String ender = "";
            ArrayList<Object> checker = new ArrayList<>();
            for (int i = 0; i < a.length; i++){   //i is responsible for entry number
                for (int j = 0; j < a[i].length; j++){   //j is responsible for accessing key or value   0 for key, and 1 for value only
                    checker.add(a[i][j].toString());
                    if (checker.size() % 2 != 0){  //Weed out new Object[][] { {"key", new Object[][] { "internalKey" } } }
                        throw new InvalidObjectDoubleArrayToMapFormatException("Object[][] was not passed in the correct format, in this method.");
                    }
                    else { //Check all Object[][]
                        if (a[i][j].toString().startsWith("[[L")){
                            Object[][] internal = (Object[][]) a[i][j];
                            if (internal.length == 0){
                                Log.w("EMPTY OBJECT[][] MAP", "" + String.format("A empty[][] map without any contents has been found at entry number %d's %s.\nThere will be a possibility that the map will not be rendered correctly.\nBy default, an empty map will be rendered as \"{ }\"", i + 1, j == 0 ? "key" : "value"));
                            }
                        }
                        if (a[i][j].toString().startsWith("[") && a[i][j].toString().endsWith("]")){ //if its a simple array with a Object[][] inside
                            //number of "[" at the start must match with the number of "]" at the end, this will determine the number of nested arrays within each Arrays.asList() as List<T>
                            final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
                            final String UPPER_CASE_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                            final String DIGITS = "0123456789";
                            final String STRING_POOL = LOWER_CASE_LETTERS + UPPER_CASE_LETTERS + DIGITS;
                            char[] allCharactersAsArray = STRING_POOL.toCharArray();
                            Character[] result = (Character[]) Converter.ReferenceTypeConverter.simplifiedToComplexArray(allCharactersAsArray);
                            for (Character c : result) {

                            }

                            List<Object> internalList = (List<Object>) a[i][j];
                            for (Object obj : internalList){
                                if (obj instanceof Object[][]){
                                    if (((Object[][]) obj).length == 0){
                                        Log.w("EMPTY OBJECT[][] MAP", "" + String.format(""));
                                    }
                                    else { //check for Object[][] in a Object[][]
                                        for (Object[] internalObj : ((Object[][]) obj)){

                                        }
                                    }
                                }
                                if (obj instanceof List){ //if nested array

                                }
                            }
                        }
                        //all test pass then carry on with the conversion
                        HashMap<TypeIdentifierUtil.ItemPosition, TypeIdentifierUtil.ItemDataType> dataTypes = new HashMap<>();
                        //focus on the values first
                        try {
                            String testResult = (String) a[i][1];
                            //check if the datatypes can be stored as a character
                            dataTypes.put(new TypeIdentifierUtil.ItemPosition(i), testResult.length() == 1 ? new TypeIdentifierUtil.ItemDataType("Character") : new TypeIdentifierUtil.ItemDataType("String"));
                        }
                        catch (Exception e1){
                            try {
                                double testResult = (Double) a[i][1];
                                //Before concluding that the datatype can be stored as a double check if it can be stored as a float or some sort
                                dataTypes.put(new TypeIdentifierUtil.ItemPosition(i), testResult > Float.MAX_VALUE ? new TypeIdentifierUtil.ItemDataType("Double") : new TypeIdentifierUtil.ItemDataType("Float"));
                            }
                            catch(Exception e2){
                                try {
                                    long testResult = (Long) a[i][1];
                                    dataTypes.put(new TypeIdentifierUtil.ItemPosition(i), testResult > Integer.MAX_VALUE || testResult < Integer.MIN_VALUE ? new TypeIdentifierUtil.ItemDataType("Long") : testResult > Short.MAX_VALUE || testResult < Short.MIN_VALUE ? new TypeIdentifierUtil.ItemDataType("Integer") : testResult > Byte.MAX_VALUE || testResult < Byte.MIN_VALUE ? new TypeIdentifierUtil.ItemDataType("Integer") : new TypeIdentifierUtil.ItemDataType("Byte"));
                                }
                                catch (Exception e3){
                                    //Check for whether the key can be casted into a List<T>
                                    try {
                                        List<?> result = (List<?>) a[i][1]; //if the thing can be casted into a list, internal item datatype still unknown so we will have to use the wildcard symbol '?'
                                        String[] dataTypeRecord = new String[result.size()];   //create an array of strings which stores the datatype
                                        for (int k = 0; k < result.size(); ++k){
                                            if (result.get(k).toString().equals("true") || result.get(k).toString().equals("false")){
                                                dataTypeRecord[k] = "Boolean";
                                            }
                                            if (result.get(k).toString().length() == 1){  //might be '0' as a char or 0 as an int
                                                //Check for whether it is an integer
                                                try {
                                                    int num = (int) result.get(k);
                                                    dataTypeRecord[k] = "Integer";
                                                }
                                                catch (Exception e3a){
                                                    //if element cannot be casted to an integer then it must be a character
                                                    dataTypeRecord[k] = "Character";
                                                }
                                            }
                                            if (result.get(k).toString().length() >= 2){
                                                //minimum lengths of all doubles when converted into a string is 3.
                                                if (result.get(k).toString().contains(".") && result.get(j).toString().length() >= 3 && new StringAddOn(result.get(j).toString()).getStringBefore(".").length() > 1){
                                                    dataTypeRecord[k] = (double) result.get(j) > Float.MAX_VALUE ? "Double" : "Float";
                                                }
                                                else {
                                                    dataTypeRecord[k] = "String"; //if does not fulfil condition then
                                                }
                                            }
                                            //check if there is like a potential nested object[][] inside of the arraylist
                                            if (result.get(k).getClass().toString().startsWith("[[")){
                                                dataTypeRecord[k] = "Map";
                                            }
                                        }
                                        if (findIndexesOfElement( "String").in(dataTypeRecord).get("String").size() == dataTypeRecord.length){
                                            ArrayList<String> copyOverList = new ArrayList<>();
                                            //copy contents from variable named results
                                            for (int k = 0; k < result.size(); k++){
                                                copyOverList.add((String) result.get(k));
                                            }
                                        }
                                        else if (findIndexesOfElement("Integer").in(dataTypeRecord).get("Integer").size() == dataTypeRecord.length){
                                            ArrayList<Integer> copyOverList = new ArrayList<>();
                                            for (int k = 0; k < result.size(); k++){
                                                copyOverList.add((Integer) result.get(k));
                                            }
                                        }
                                        else if (findIndexesOfElement("Boolean").in(dataTypeRecord).get("Boolean").size() == dataTypeRecord.length){
                                            ArrayList<Boolean> copyOverList = new ArrayList<>();
                                            for (int k = 0; k < result.size(); k++){
                                                copyOverList.add((Boolean) result.get(k));
                                            }
                                        }
                                        else if (findIndexesOfElement("Character").in(dataTypeRecord).get("Character").size() == dataTypeRecord.length){
                                            ArrayList<Character> copyOverList = new ArrayList<>();
                                            for (int k = 0; k < result.size(); k++){
                                                copyOverList.add((Character) result.get(k));
                                            }
                                        }
                                        else if (findIndexesOfElement("Float").in(dataTypeRecord).get("Float").size() == dataTypeRecord.length){
                                            ArrayList<Float> copyOverList = new ArrayList<>();
                                            for (int k = 0; k < result.size(); k++){
                                                copyOverList.add((Float) result.get(k));
                                            }
                                        }
                                        else if (findIndexesOfElement("Double").in(dataTypeRecord).get("Double").size() == dataTypeRecord.length){
                                            ArrayList<Double> copyOverList = new ArrayList<>();
                                            for (int k = 0; k < result.size(); k++){
                                                copyOverList.add((Double) result.get(k));
                                            }
                                        }
                                        else if (findIndexesOfElement("Map").in(dataTypeRecord).get("Map").size() == dataTypeRecord.length){   //if string is "Map"
                                            ArrayList<Object> copyOverList = new ArrayList<>();
                                        }
                                    }
                                    catch (Exception e4){
                                        //if the thing is not
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        public static <T> InternalArrayStorageSearchingSingle findIndexOfElement(@NonNull T target){        //an array of REFERENCE TYPES ONLY
            return new InternalArrayStorageSearchingSingle(target);
        }

        public static InternalArrayStorageLocale getElementAt(int index){
            return new InternalArrayStorageLocale(index);
        }

        //Direct extract from an array of primitives.
        public static <Array> Object findIndexOfElement(@NonNull Array sample, @NonNull Object target) throws NotAnArrayException, ContentsNotPrimitiveException { //for char[], int[], boolean[],
            Function<Object, Class<?>> typeChecker = x -> {
                //no string or any other objects allowed.
                if (x instanceof Integer){
                    return int.class;
                }
                else if (x instanceof Character){
                    return char.class;
                }
                else if (x instanceof Boolean){
                    return boolean.class;
                }
                else if (x instanceof Long){
                    return long.class;
                }
                else if (x instanceof Short){
                    return short.class;
                }
                else if (x instanceof Byte){
                    return byte.class;
                }
                else if (x instanceof Double){
                    return double.class;
                }
                else if (x instanceof Float){
                    return float.class;
                }
                else {

                }
            };
            if (sample instanceof char[]){
                char[] a = (char[]) sample;
                for (int i = 0; i < a.length; i++){
                    if (typeChecker.apply(target) != char.class){
                        char translate = (char) target;
                        if (a[i] == translate){
                            return i;
                        }
                    }
                    else { //type mismatch (do you want to throw an error?)   throw error or return null
                        Log.w("TYPEMISMATCHWARNING", "" + String.format("You are passing in an ARRAY OF char, and you would like to find \"target\", which is of type \"%s\"", typeChecker.apply(target).toString()));
                    }
                }
            }
            else if (sample instanceof int[]){
                int[] a = (int[]) sample;
                for (int i = 0; i < a.length; i++){
                    if (typeChecker.apply(target) != int.class){
                        int translate = (int) target;
                        if (a[i] == translate){
                            return i;
                        }
                    }
                    else { //type mismatch
                        Log.w("TYPEMISMATCHWARNING", "" + String.format("You are passing in an ARRAY OF int, and you would like to find \"target\", which is of type \"%s\"", typeChecker.apply(target).toString()));
                    }
                }
            }
            else if (sample instanceof boolean[]){
                boolean[] a = (boolean[]) sample;
                for (int i = 0; i < a.length; i++){
                    if (typeChecker.apply(target) != boolean.class){
                        boolean translate = (boolean) target;
                        if (a[i] == translate){
                            return i;
                        }
                    }
                    else { //type mismatch
                        Log.w("TYPEMISMATCHWARNING", "" + String.format("You are passing in an ARRAY OF boolean, and you would like to find \"target\", which is of type \"%s\"", typeChecker.apply(target).toString()));
                    }
                }
            }
            else if (sample instanceof double[]){
                double[] a = (double[]) sample;
                for (int i = 0; i < a.length; i++){
                    if (typeChecker.apply(target) != double.class){
                        double translate = (double) target;
                        if (a[i] == translate){
                            return i;
                        }
                    }
                    else { //type mismatch
                        Log.w("TYPEMISMATCHWARNING", "" + String.format("You are passing in an ARRAY OF double, and you would like to find \"target\", which is of type \"%s\"", typeChecker.apply(target).toString()));
                    }
                }
            }
            else if (sample instanceof float[]){
                float[] a = (float[]) sample;
                for (int i = 0; i < a.length; i++){
                    if (typeChecker.apply(target) != float.class){
                        float translate = (float) target;
                        if (a[i] == translate){
                            return i;
                        }
                    }
                    else { //type mismatch
                        Log.w("TYPEMISMATCHWARNING", "" + String.format("You are passing in an ARRAY OF float, and you would like to find \"target\", which is of type \"%s\"", typeChecker.apply(target).toString()));
                    }
                }
            }
            else if (sample instanceof byte[]){
                byte[] a = (byte[]) sample;
                for (int i = 0; i < a.length; i++){
                    if (typeChecker.apply(target) != byte.class){
                        byte translate = (byte) target;
                        if (a[i] == translate){
                            return i;
                        }
                    }
                    else { //type mismatch
                        Log.w("TYPEMISMATCHWARNING", "" + String.format("You are passing in an ARRAY OF byte, and you would like to find \"target\", which is of type \"%s\"", typeChecker.apply(target).toString()));
                    }
                }
            }
            else if (sample instanceof short[]){
                short[] a = (short[]) sample;
                for (int i = 0; i < a.length; i++){
                    if (typeChecker.apply(target) != short.class){
                        short translate = (short) target;
                        if (a[i] == translate){
                            return i;
                        }
                    }
                    else { //type mismatch
                        Log.w("TYPEMISMATCHWARNING", "" + String.format("You are passing in an ARRAY OF short, and you would like to find \"target\", which is of type \"%s\"", typeChecker.apply(target).toString()));
                    }
                }
            }
            else if (sample instanceof long[]){
                long[] a = (long[]) sample;
                for (int i = 0; i < a.length; i++){
                    if (typeChecker.apply(target) != long.class){
                        long translate = (long) target;
                        if (a[i] == translate){
                            return i;
                        }
                    }
                    else { //type mismatch
                        Log.w("TYPEMISMATCHWARNING", "" + String.format("You are passing in an ARRAY OF long, and you would like to find \"target\", which is of type \"%s\"", typeChecker.apply(target).toString()));
                    }
                }
            }
            else {
                if (!sample.getClass().isArray()) {
                    //its not an array
                    throw new NotAnArrayException("The given parameter for param 'charArr' is not of type (datatype)[], not an array");
                }
            }
        }

        public static <T> InternalArrayStorageSearchMultiple findIndexesOfElement(@NonNull T target){     //an array of REFERENCE TYPES ONLY
            return new InternalArrayStorageSearchMultiple(target);
        }

        //good for multi-dimensional-array, single dimensional array can also be used within this method, but not recommended lol
        public static <MultiDimensionalArray, T> MultiDimensionalArray reverse(@NonNull MultiDimensionalArray target, @AcceptStrings(permittedStrings = {"FlipAll", "FlipContainer"}, defaultValueIfInvalid = "FlipAll") String choice) throws NotAnArrayException, NoSuchMethodException, ContentsNotPrimitiveException{
            String[] allowedParameters = ((AcceptStrings) ArrayUtilsCustom.class.getMethod("reverse", String.class).getParameterAnnotations()[0][0]).permittedStrings();
            String defaultValueIfInvalid = ((AcceptStrings) ArrayUtilsCustom.class.getMethod("reverse", String.class).getParameterAnnotations()[0][0]).defaultValueIfInvalid();
            Function<T[], MultiDimensionalArray> singleDimensionArray = x -> {
                int starter = 0; int ender = x.length - 1;
                if (x.length > 1){ //not new Target[] { } or new Target[0];
                    Object[] out = new Object[x.length];
                    do {
                        out[starter] = x[ender];
                        ender--; starter++;
                    }
                    while (ender >= 0 && starter <= x.length - 1);
                    return (MultiDimensionalArray) out;
                }
                else {
                    return target;
                }
            };
            if (!ArrayUtilsCustom.containsElement(choice, allowedParameters)){
                choice = defaultValueIfInvalid;
            } else {
                //N_DimensionalArray can only be of
                if (target.getClass().isArray()) {
                    char[] detector = target.getClass().toString().toCharArray();
                    int dimension = 0;
                    for (char c : detector) {
                        if (c == '[') {
                            dimension++;
                        }
                    }
                    if (dimension == 1) {  //for Object[]
                        if (target instanceof Character[]) {
                            //process into char[] , default value is '\u0000'
                            Character[] a = (Character[]) target;
                            return singleDimensionArray.apply((T[]) a);
                        } else if (target instanceof Integer[]){
                            Integer[] a = (Integer[]) target;
                            return singleDimensionArray.apply((T[]) a);
                        } else if (target instanceof boolean[] || target instanceof Boolean[]) {
                            Boolean[] a = (Boolean[]) target;
                            return singleDimensionArray.apply((T[]) a);
                        } else if (target instanceof long[] || target instanceof Long[]){
                            Long[] a = (Long[]) target;
                            return singleDimensionArray.apply((T[]) a);
                        } else if (target instanceof short[] || target instanceof Short[]) {
                            Short[] a = (Short[]) target;
                            return singleDimensionArray.apply((T[]) a);
                        } else if (target instanceof byte[] || target instanceof Byte[]){
                            Byte[] a = (Byte[]) target;
                            return singleDimensionArray.apply((T[]) a);
                        } else if (target instanceof float[] || target instanceof Float[]){
                            Float[] a = (Float[]) target;
                            return singleDimensionArray.apply((T[]) a);
                        } else if (target instanceof double[] || target instanceof Double[]) {
                            Double[] a = (Double[]) target;
                            return singleDimensionArray.apply((T[]) a);
                        }
                    } else { //if its a multi-dimensional array Object[].....[] { }

                    }
                }
            }
        }

        //changes every single value within an array to NULL for reference types
        // Good for single and multi-dimensional array
        // char[], int[], long[], byte[], boolean[] accepted
        //boolean[] -> Boolean[] { null, null, null }
        //inform the user if a single dimensional array has been passed as part of the parameter
        public static <N_DimensionalArray> N_DimensionalArray initialize(@NonNull N_DimensionalArray target, @AcceptStrings(permittedStrings = {"Primitive", "Reference", "primitive", "reference"}, defaultValueIfInvalid = "Reference") String result) throws NotAnArrayException, NoSuchMethodException, ContentsNotPrimitiveException {
            String[] allowedParameters = ((AcceptStrings) ArrayUtilsCustom.class.getMethod("initialize", String.class).getParameterAnnotations()[0][0]).permittedStrings();
            String defaultValueIfInvalid = ((AcceptStrings) ArrayUtilsCustom.class.getMethod("initialize", String.class).getParameterAnnotations()[0][0]).defaultValueIfInvalid();
            if (!ArrayUtilsCustom.containsElement(result, allowedParameters)){
                result = defaultValueIfInvalid;
            } else {
                //N_DimensionalArray can only be of
                if (target.getClass().isArray()) {
                    char[] detector = target.getClass().toString().toCharArray();
                    int dimension = 0;
                    for (char c : detector) {
                        if (c == '[') {
                            dimension++;
                        }
                    }
                    if (dimension == 1) {  //for Object[]
                        if (target.equals("primitive") || target.equals("Primitive")) {
                            //convert target into, a processable array
                            if (target instanceof char[] || target instanceof Character[]) {
                                //process into char[] , default value is '\u0000'
                                char[] a = (char[]) target;
                                Arrays.fill(a, '\u0000');
                                return (N_DimensionalArray) a;
                            } else if (target instanceof int[] || target instanceof Integer[]){
                                int[] a = (int[]) target;
                                Arrays.fill(a, 0);
                                return (N_DimensionalArray) a;
                            } else if (target instanceof boolean[] || target instanceof Boolean[]) {
                                boolean[] a = (boolean[]) target;
                                Arrays.fill(a, false);
                                return (N_DimensionalArray) a;
                            } else if (target instanceof long[] || target instanceof Long[]){
                                long[] a = (long[]) target;
                                Arrays.fill(a, 0L);
                                return (N_DimensionalArray) a;
                            } else if (target instanceof short[] || target instanceof Short[]) {
                                short[] a = (short[]) target;
                                Arrays.fill(a, (short) 0);
                                return (N_DimensionalArray) a;
                            } else if (target instanceof byte[] || target instanceof Byte[]){
                                byte[] a = (byte[]) target;
                                Arrays.fill(a, (byte) 0);
                                return (N_DimensionalArray) a;
                            } else if (target instanceof float[] || target instanceof Float[]){
                                float[] a = (float[]) target;
                                Arrays.fill(a, 0.0F);
                                return (N_DimensionalArray) a;
                            } else if (target instanceof double[] || target instanceof Double[]) {
                                double[] a = (double[]) target;
                                Arrays.fill(a, 0.0D);
                                return (N_DimensionalArray) a;
                            } else {
                                //String[] { }, or any other arrays that stores objects, which IS NOT POSSIBLE TO BE CONVERTED INTO PRIMITIVE
                                throw new ContentsNotPrimitiveException("The array that you have supplied can only be of reference type and not primitive type. Please change to either 'reference' or 'Reference', or supply this method with a array that does not consists of referential data types.");
                            }
                        } else if (target.equals("reference") || target.equals("Reference")) {
                            if (target instanceof char[] || target instanceof Character[]) {
                                //process into char[] , default value is '\u0000'
                                Character[] a = (Character[]) target;
                                Arrays.fill(a, null);
                                return (N_DimensionalArray) a;
                            } else if (target instanceof int[] || target instanceof Integer[]){
                                Integer[] a = (Integer[]) target;
                                Arrays.fill(a, null);
                                return (N_DimensionalArray) a;
                            } else if (target instanceof boolean[] || target instanceof Boolean[]) {
                                Boolean[] a = (Boolean[]) target;
                                Arrays.fill(a, null);
                                return (N_DimensionalArray) a;
                            } else if (target instanceof long[] || target instanceof Long[]){
                                Long[] a = (Long[]) target;
                                Arrays.fill(a, 0L);
                                return (N_DimensionalArray) a;
                            } else if (target instanceof short[] || target instanceof Short[]) {
                                Short[] a = (Short[]) target;
                                Arrays.fill(a, null);
                                return (N_DimensionalArray) a;
                            } else if (target instanceof byte[] || target instanceof Byte[]){
                                Byte[] a = (Byte[]) target;
                                Arrays.fill(a, null);
                                return (N_DimensionalArray) a;
                            } else if (target instanceof float[] || target instanceof Float[]){
                                Float[] a = (Float[]) target;
                                Arrays.fill(a, null);
                                return (N_DimensionalArray) a;
                            } else if (target instanceof double[] || target instanceof Double[]) {
                                Double[] a = (Double[]) target;
                                Arrays.fill(a, null);
                                return (N_DimensionalArray) a;
                            } else {
                                //Fill array with null;
                            }
                        }
                    } else {
                        //root.length = 0? if not 0 then move on to for loop to identify which internal element's length is 0,
                    }
                } else {
                    throw new NotAnArrayException("The given value for parameter 'target is not of an array'");
                }
            }
        }

        public static <N_DimensionalArray> boolean isInitialized(@NonNull N_DimensionalArray target){

        }

        public static <T> boolean containsElement(@NonNull T target, @NonNull T[] sample) throws NotAnArrayException, ContentsNotPrimitiveException{
            if (!sample.getClass().isArray()) {
                //its not an array
                throw new NotAnArrayException("The given parameter for param 'charArr' is not of type (datatype)[], not an array");
            }
            else {
                if (sample.getClass().toString().substring(sample.getClass().toString().lastIndexOf("[")).length() != 2) {
                    throw new ContentsNotPrimitiveException("Contents within the given array is not of a primitive type");
                }
                else {
                    for (int i = 0; i < sample.length; i++){
                        if (sample[i] != null) {
                            return sample[i].equals(target);
                        }
                    }
                }
            }
            return false;
        }

        //only for single dimensional array
        public static <T> String toString(@NonNull T[] target){

        }

        public static class InternalArrayStorageSearchMultiple{
            private Object target;

            private InternalArrayStorageSearchMultiple(Object target){ this.target = target; }

            public <T> HashMap<T, ArrayList<Integer>> in(@NonNull T[] sample){
                HashMap<T, ArrayList<Integer>> hm = new HashMap<>();
                ArrayList<Integer> b = new ArrayList<>();
                hm.put((T) this.target, new ArrayList<>());
                Function<Integer, HashMap<T, ArrayList<Integer>>> a = arrSize -> {
                    int initializer = 0;
                    do {
                        if (sample[initializer] == target) {
                            //Introduce a new element if the target element is not found within the hashmap, otherwise.
                            b.add(initializer);
                            hm.put((T) this.target, b);
                        }
                        ++initializer;
                    }
                    while (initializer < arrSize);
                    return hm;
                };
                return sample.length > 0 ? a.apply(sample.length) : hm;
            }
        }

        public static class InternalArrayStorageSearchingSingle{
            private Object target;

            private InternalArrayStorageSearchingSingle(Object target){
                this.target = target;
            }

            //find index of something, but the array must be storing reference types, primitive types are not permitted over here
            public <T> int in(@NonNull T[] charArr){
                Supplier<Boolean> checker = () -> {
                    //single dimensional arrays only
                    char[] filter = charArr.getClass().toString().toCharArray();
                    int nestedCount = 0;
                    for (char c : filter) {
                        if (c == '[') {
                            ++nestedCount;
                        }
                    }
                    return nestedCount == 1;
                };
                Function<Integer, Integer> a = arrSize -> {
                    if (charArr.length > 0) {
                        int initializer = 0;
                        do {
                            if (charArr[initializer] == this.target) {
                                return initializer;
                            }
                            ++initializer;
                        }
                        while (initializer < arrSize);
                    }
                    Log.w("LENGTHISZERO", "The length of the supplied array has a length of 0. This will affect normal functioning of this method. As a result, this method will be returning a value of -1. \n This warning has been raised from the following method: Tools.ArrayUtilsCustom.InternalArrayStorageSearchingSingle.in()");
                    return -1;
                };
                Supplier<Integer> warn = () -> {
                    Log.w("NOTSINGLEDIMENSIONALARRAY", "Not a single dimensional array. This will affect normal functioning of this method. As a result, this method will be returning a value of -1.\n This warning has been raised from the following method: Tools.ArrayUtilsCustom.InternalArrayStorageSearchingSingle.in()");
                    return -1;
                };
                return checker.get() ? a.apply(charArr.length) : warn.get();
            }

            //find index of something, but the array can now accept primitive data types, reference types use the previous method.
            public <PrimitiveArray> int inPrimit(@NonNull PrimitiveArray array){
                Supplier<Boolean> checker = () -> {
                    //single dimensional arrays only
                    char[] filter = array.getClass().toString().toCharArray();
                    int nestedCount = 0;
                    for (char c : filter) {
                        if (c == '[') {
                            ++nestedCount;
                        }
                    }
                    return nestedCount == 1;
                };

            }
        }

        public static class InternalArrayStorageLocale{
            private int index;

            private InternalArrayStorageLocale(int index){
                this.index = index;
            }

            public <T> T in(@NonNull T[] charArr) throws NotAnArrayException, ContentsNotPrimitiveException, IndexOutOfBoundsException{
                if (charArr.length < index){
                    return charArr[this.index];
                }
                else {
                    throw new IndexOutOfBoundsException(String.format("Given index to access array \"charArr\" was: %d\nBut the overall length of the array \"charArr\" is %d." + (index >= charArr.length ? String.format("\nThe current index is %d." + ( (index - charArr.length) == 1 ? String.format("\nIndexes must always be one less than the length of the array. Do you mean %d?", charArr.length - 1) : "" ), index) : String.format("\nThe current index is %d. Indexes cannot be less than 0", index)), index, charArr.length));
                }
            }
        }

        public static final class ArraysExt<T>{
            private T[] arrayStorage;

            public ArraysExt(@NonNull T[] newArray){
                this.arrayStorage = newArray;
            }

            //element can be null
            //good for single dimensional array
            public <T> boolean startsWith(T target){
                return this.arrayStorage[0].equals(target);
            }

            //element can be null
            //good for single dimensional array
            public <T> boolean endsWith(T target){
                return this.arrayStorage[this.arrayStorage.length - 1].equals(target);
            }
        }
    }

    public static final class StringAddOn{

        private final String str;

        public StringAddOn(String str){
            this.str = str;
        }

        public String getStringBefore(@NonNull String symbol) throws NotAnArrayException, ContentsNotPrimitiveException{
            char[] temp = this.str.toCharArray();
            int currIndex = 0;
            for (char c : temp){
                if (Character.toString(c).equals(symbol)) {
                    currIndex = (Integer) ArrayUtilsCustom.findIndexOfElement(temp, c);
                    break;
                }
            }
            if (currIndex != 0){
                int initializer = 0;
                String result = "";
                do {
                    result += temp[initializer];
                    initializer++;
                }
                while (initializer < currIndex);
                return result;
            }
            else {  //If there is no element before the target symbol, then just return the symbol as a string.
                return symbol;
            }
        }

        public int getFirstIndexOf(@NonNull String symbol) throws NotAnArrayException, ContentsNotPrimitiveException{
            char[] temp = this.str.toCharArray();
            int currIndex = 0;
            for (char c : temp){
                if (Character.toString(c).equals(symbol)) {
                    return (Integer) ArrayUtilsCustom.findIndexOfElement(temp, c);
                }
            }
            return -1; //if symbol cannot be found within the array
        }

        //this method is used to reverse a string
        public String reverse() {
            String a = "";
            int index = this.str.length() - 1;
            do {
                a += this.str.charAt(index);
                index--;
            }
            while (index >= 0);
            return a;
        }

        //This class shall not be declared as private LOL
        public final class Determinant{
            private Object target;

            public <T> Determinant(T target){
                this.target = target;
            }

            public Determinant(){

            }

            public boolean is(final char retrieval) {
                return (((String[]) this.target)[0]).charAt(Integer.parseInt(((String[]) this.target)[1])) == retrieval;
            }

            public <T> T getItem(){
                return (T) this.target;
            }

            public <T extends String> void remove(T segment) throws NotAStringException {
                if (this.target instanceof String){

                } else {

                }
            }

            //return highlight object and prepare to highlight a section of the string.
            //advance can only be called when this.target is of
            public Highlight advance(int steps) throws StringIndexOutOfBoundsException, NotAStringException {
                if (this.target instanceof String[]){
                    //index 0: sample (full string)
                    //index 1: anchoringPoint (a single point within a sample string)
                    String sample = ((String[]) this.target)[0];
                    String anchoringPoint = ((String[]) this.target)[1];
                    BiFunction<Integer, String, String> concatenator = (stepsFromParameter, stringSample) -> {
                        String result = "";
                        //anchoringPoint is a string, which represents the target
                        int tempIndex = Integer.parseInt(((String[]) this.target)[2]); //location of the target in the sample
                        int end = tempIndex + steps; //ending index position
                        if (end <= sample.length() - 1) {
                            do {
                                result += stringSample.charAt(tempIndex);
                                tempIndex++;
                            }
                            while (tempIndex <= end);
                            return result; //returns the affected string
                        } else {
                            throw new StringIndexOutOfBoundsException(String.format("Given string length: %d\nGreatest character index possible: %d\nAnchored character index: %d\nIndex of expected element at the end of slicing: %d", anchoringPoint.length(), anchoringPoint.length() - 1, tempIndex, end));
                        }
                    };
                    return new Highlight(concatenator.apply(steps, sample));
                } else {
                    throw new NotAStringException(String.format("This method can only be used if 'this.target' is of type 'String[]' not ", this.target.getClass()));
                }
            }


        }

        public final class Highlight{
            private String slicedString;

            public Highlight(String s){
                this.slicedString = slicedString;
            }

            //what are you going to do with the sliced string segment?

            public String getString(){
                return this.slicedString;
            }

            //Removes highlighted text from the String.
            public Highlight remove(char target) throws ContentsNotPrimitiveException, NotAnArrayException {
                String[] result = new String[this.slicedString.length()];
                for (int i = 0; i < this.slicedString.length(); ++i){
                    result[i] = Character.toString(this.slicedString.charAt(i));
                }
                for (int i = 0; i < result.length; ++i){
                    if (result[i].equals(Character.toString(target))){
                        result[i] = "";
                    }
                }
                String output = ""; int i = 0;
                do {
                    output += result[i];
                }
                while (i < result.length);
                return new Highlight(output);
            }
        }

        //StringAddOn.format("{:@[2]}s{>12:@[1]}", 12, "string") => "strings\t\t\t\t\t\t\t\t\t\t\t\t12" => "strings                                             12"
        //Wrong syntax will result in the string not rendered correctly, no exception will be thrown
        public static String format(String representation, Object ...args){

        }

        // "String" => [s][t][r][i][n][g] => apply after('r') => returns Determinant => apply is() => returns a boolean value
        public Determinant after(char target, int indexAt) throws ContentsNotPrimitiveException, NotAnArrayException {
            char[] charArray = this.str.toCharArray(); int occurrences = 0;
            for (char c : charArray){
                if (c == target){
                    occurrences++;
                }
            }
            int[] indexRecords = new int[occurrences + 1];
            for (int i = 0; i < charArray.length; ++i){
                if (charArray[i] == target){
                    indexRecords[i] = i;
                }
            }
            if (indexAt > occurrences) {
                return new Determinant(new String[]{ new String(charArray), Integer.toString(indexRecords[indexAt] + 1) });
            } else {
                Log.i("TARGETNOTFOUND", "Required target character was not found. \n This warning message has been raised from the following method: Tools.StringAddOn.after()");
                //return unparameterized constructor instead of null
                return new Determinant();
            }
        }

        public Determinant before(char target, int indexAt) throws ContentsNotPrimitiveException, NotAnArrayException {
            char[] charArray = this.str.toCharArray(); int occurrences = 0;
            for (char c : charArray){
                if (c == target){
                    occurrences++;
                }
            }
            int[] indexRecords = new int[occurrences + 1];
            for (int i = 0; i < charArray.length; ++i){
                if (charArray[i] == target){
                    indexRecords[i] = i;
                }
            }
            if (indexAt > occurrences) {
                //return index before target char index at n-th occurrences (indexAt)
                return new Determinant(new String[]{ new String(charArray), Integer.toString(indexRecords[indexAt] - 1) });
            } else {
                Log.i("TARGETNOTFOUND", "Required target character was not found. \n This warning message has been raised from the following method: Tools.StringAddOn.before()");
                //return unparameterized constructor instead of null
                return new Determinant();
            }
        }

        @SuppressWarnings("unchecked")
        //target: the character that you are looking for within a string
        //occurrence: and what time did that target occur within the string (first occurrence use 0, and so on)
        public Determinant setAnchorAt(char target, int atIndex){
            char[] charArray = this.str.toCharArray(); int occurrence = 0; // -> number of times that 'target' has occurred within a particular string
            for (int i = 0; i < charArray.length; ++i) {
                if (charArray[i] == target) { //finds a match
                    occurrence++; //add 1 to the counter
                }
            }
            if (atIndex > occurrence) {
                Log.i("TARGETNOTFOUND", "Required target character was not found. Throwing error message from Tools.StringAddOn.setAnchorAt()");
                return new Determinant(); //call null constructor first.
            } else {
                int[] result = new int[occurrence + 1];
                for (int i = 0; i < charArray.length; ++i){
                    if (charArray[i] == target){
                        result[i] = i;
                    }
                }
                return new Determinant(new String[]{new String(charArray), Character.toString(target), Integer.toString(result[atIndex]) });
            }
        }

        public String[] splitWithRegexConditions(String command) throws RegexConditionalCommandSyntaxError, NotAStringException, ContentsNotPrimitiveException, NotAnArrayException {
            /*
                Command syntax:

                Create human-readable comments

                !!This is a comment!!
                ==================================================================================================
                Regex will ignore if symbol is not between character 'a' to 'z' and 'a' to 'z'
                @regexDivision{
                    @declareArray [ 'a', 'A' ] as $array end
                    if @symbol @sandwiched in $array end
                }
                ==================================================================================================
                @regexDivision {

                }
             */
            String[] basicTokenizing = command.split(" ");
            ArrayList<RegexConditionalCommandSyntaxError> errorList = new ArrayList<>();
            if (!(new ArrayUtilsCustom.ArraysExt<String>(basicTokenizing).startsWith("@regexDivision{"))){
                //did not start with "@"
                if (basicTokenizing[0].startsWith("@")){
                    int steps = 1; int pointerIndexInReference = 0;
                    char[] reference = "regexDivision".toCharArray();
                    for (; ; ) {
                        if (pointerIndexInReference < reference.length){
                            if (!(new StringAddOn(command).setAnchorAt('@', 0).advance(steps).getString().charAt(new StringAddOn(command).setAnchorAt('@', 0).advance(steps).getString().length() - 1) == reference[pointerIndexInReference])) {
                                //get unmatched character and add to error list
                                errorList.add(new RegexConditionalCommandSyntaxError(String.format("E1b: Unknown method, or declaration '%s'." + (pointerIndexInReference == 5 && new StringAddOn(command).setAnchorAt('@', 0).advance(steps).getString().charAt(new StringAddOn(command).setAnchorAt('@', 0).advance(steps).getString().length() - 1) == 'd' ? "\nDo you mean @regexDivision?" : ""), new StringAddOn(command).setAnchorAt('@', 0).advance(steps).getString())));
                            }
                            ++steps;
                            ++pointerIndexInReference;
                        } else {
                            break;
                        }
                    }
                    //Till this point the string should have at least "@regexDivision" inside
                    if (new StringAddOn(command).after('x', 0).is('{')) {
                        //Till this point the string should have at least @regexDivision{
                        char[][] referenceCharArray = new char[][] { "sandwiched".toCharArray(), "declareArray".toCharArray() };

                    } else {
                        errorList.add(new RegexConditionalCommandSyntaxError("E2: '{' expected over here"));
                    }
                } else {
                    //add error
                    errorList.add(new RegexConditionalCommandSyntaxError("E1a: Expected '@' at the start of every regex-co statement."));
                }
            }

            if (!(new ArrayUtilsCustom.ArraysExt<String>(basicTokenizing).endsWith("}"))){
                errorList.add(new RegexConditionalCommandSyntaxError("E9: '}' expected at the end of the regex-co statement"));
            }
        }
    }

    public static class RegexWithConditionals{
        private String command;

        public RegexWithConditionals(String command){

        }
    }

    @SuppressWarnings("unchecked")
    public static class Converter {
        public static final class ReferenceTypeConverter {
            private ReferenceTypeConverter(){    //Private constructor (no instantiation!!)

            }

            //simplifiedToComplexArray(new char[] { }) -> new Character[] { }
            public static <T, U> T[] simplifiedToComplexArray(@NonNull U charArr) throws NotAnArrayException, ContentsNotPrimitiveException{
                //<Destination, [source datatype, source], Destination without contents from source>
                //y[1] is like a datatype tag
                BiFunction<T[], Object[], T[]> copyContents = (x, y) -> {
                    if (y[1].equals(char[].class)){
                        char[] raw = (char[]) y[0];
                        for (int i = 0; i < raw.length; i++){
                            x[i] = (T) Character.valueOf(raw[i]);
                        }
                    }
                    else if (y[1].equals(int[].class)){
                        int[] raw = (int[]) y[0];
                        for (int i = 0; i < raw.length; i++){
                            x[i] = (T) Integer.valueOf(raw[i]);
                        }
                    }
                    else if (y[1].equals(double[].class)){
                        double[] raw = (double[]) y[0];
                        for (int i = 0; i < raw.length; i++){
                            x[i] = (T) Double.valueOf(raw[i]);
                        }
                    }
                    else if (y[1].equals(boolean[].class)){
                        boolean[] raw = (boolean[]) y[0];
                        for (int i = 0; i < raw.length; i++){
                            x[i] = (T) Boolean.valueOf(raw[i]);
                        }
                    }
                    else if (y[1].equals(long[].class)){
                        long[] raw = (long[]) y[0];
                        for (int i = 0; i < raw.length; i++){
                            x[i] = (T) Long.valueOf(raw[i]);
                        }
                    }
                    else if (y[1].equals(byte[].class)){
                        byte[] raw = (byte[]) y[0];
                        for (int i = 0; i < raw.length; i++){
                            x[i] = (T) Byte.valueOf(raw[i]);
                        }
                    }
                    else if (y[1].equals(short[].class)){
                        short[] raw = (short[]) y[0];
                        for (int i = 0; i < raw.length; i++){
                            x[i] = (T) Short.valueOf(raw[i]);
                        }
                    }
                    else if (y[1].equals(float[].class)){
                        float[] raw = (float[]) y[0];
                        for (int i = 0; i < raw.length; i++){
                            x[i] = (T) Float.valueOf(raw[i]);
                        }
                    }
                    return x;
                };
                //T now stores Characters, but when accessed individually, it is an object
                //charArr of type U
                if (!charArr.getClass().isArray()) {
                    //its not an array
                    throw new NotAnArrayException("The given parameter for param 'charArr' is not of type (datatype)[], not an array");
                }
                else {
                    if (charArr.getClass().toString().substring(charArr.getClass().toString().lastIndexOf("[")).length() != 2) {
                        throw new ContentsNotPrimitiveException("Contents within the given array is not of a primitive type");
                    } else {
                        if (charArr instanceof char[]) {
                            //safe to cast U into char[]
                            return copyContents.apply(((char[]) charArr).length != 0 ? (T[]) new Character[((char[]) charArr).length] : (T[]) new Character[]{}, new Object[] { charArr, char[].class });
                        } else if (charArr instanceof int[]) {
                            //safe to cast U into int[]
                            return copyContents.apply(((int[]) charArr).length != 0 ? (T[]) new Integer[((int[]) charArr).length] : (T[]) new Character[]{}, new Object[] { charArr, int[].class });
                        } else if (charArr instanceof double[]) {
                            //safe to cast U into double[]
                            return copyContents.apply(((double[]) charArr).length != 0 ? (T[]) new Double[((double[]) charArr).length] : (T[]) new Double[]{}, new Object[] { charArr, double[].class });
                        } else if (charArr instanceof boolean[]) {
                            //safe to cast U into boolean[]
                            return copyContents.apply(((boolean[]) charArr).length != 0 ? (T[]) new Boolean[((boolean[]) charArr).length] : (T[]) new Boolean[]{}, new Object[] { charArr, boolean[].class });
                        } else if (charArr instanceof long[]) {
                            //safe to cast U into long[]
                            return copyContents.apply(((long[]) charArr).length != 0 ? (T[]) new Long[((long[]) charArr).length] : (T[]) new Long[]{}, new Object[] { charArr, long[].class });
                        } else if (charArr instanceof byte[]) {
                            //safe to cast U into byte[]
                            return copyContents.apply(((byte[]) charArr).length != 0 ? (T[]) new Byte[((byte[]) charArr).length] : (T[]) new Byte[]{}, new Object[] { charArr, byte[].class });
                        } else if (charArr instanceof short[]) {
                            //safe to cast U into short[]
                            return copyContents.apply(((short[]) charArr).length != 0 ? (T[]) new Short[((short[]) charArr).length] : (T[]) new Short[]{}, new Object[] { charArr, short[].class });
                        } else if (charArr instanceof float[]) {
                            //safe to cast U into float[]
                            return copyContents.apply(((float[]) charArr).length != 0 ? (T[]) new Float[((float[]) charArr).length] : (T[]) new Float[]{}, new Object[] { charArr, float[].class });
                        } else {
                            //basically dead
                            return null;
                        }
                    }
                }
            }

            //TODO: METHOD NOT CHECKED FOR WHETHER IT PRODUCES DESIRED RESULTS
            public static <T> T primitiveToReferenceType(@NonNull Object value) throws ContentsNotPrimitiveException{ //No strings allowed
                try {
                    //long -> int -> short -> byte
                    return (long) value > Integer.MAX_VALUE || (long) value < Integer.MIN_VALUE ? (T) Long.valueOf((long) value) : (long) value > Short.MAX_VALUE || (long) value < Short.MIN_VALUE ? (T) Integer.valueOf((int) value) : (long) value > Byte.MAX_VALUE || (long) value < Byte.MIN_VALUE ? (T) Short.valueOf((short) value) : (T) Byte.valueOf((byte) value);
                }
                catch (Exception e1){
                    try {
                        //double -> float
                        return (double) value > Float.MAX_VALUE ? (T) Double.valueOf((double) value) : (T) Float.valueOf((float) value);
                    }
                    catch (Exception e2){
                        try {
                            //try to cast the item into a string?
                            return ((String) value).length() == 1 ? (T) Character.valueOf((char) value) : ((String) value).equals("true") || ((String) value).equals("false") ? (T) Boolean.valueOf((boolean) value) : (T) String.valueOf((String) value);
                        }
                        catch (Exception e3) {
                            throw new ContentsNotPrimitiveException("The provided results is not of a primitive type. Accepted primitive types include 'int', 'char', 'boolean', 'long', 'short', 'byte', 'float', 'double");
                        }
                    }
                }
            }
        }

        //TODO: Reminder: Do you need private constructors???
        //Handle binary numbers
        public static final class BinaryUtils{

            private BinaryUtils(){

            }

            public static void convertDecimalToBinary(int num){
                ArrayList<Integer> remainderList = new ArrayList<>();
                int remainder; int quotient;
                do {
                    remainder = num % 2;
                    quotient = num / 2;
                    remainderList.add(remainder);
                }
                while (quotient != 1);
                //Extract the information from the remainderList

            }

            public static void convertBinaryToDecimal(Binary binaryNum){
                int internal = binaryNum.getBinaryNum();

            }
        }
    }

    @ParameterTypes(input = { String.class, String.class }, index = 0) //apply to first ELasticFunction
    @ParameterTypes(input = { String.class, int.class }, output = double.class, index = 1)
    @ParameterTypes(input = { String.class, int.class, double.class }, output = float.class, index = 3)
    public static void Test() throws NoSuchMethodException{
        ElasticFunction elasticFunc = arr -> {
            //Number of parameters supplied by input must equate to number of elements in


            return null;
        };


        elasticFunc.apply("Test", "string2", "this stupid thing");

    }

    //Function<P1, P2, R> only accepts a total of 2 parameters
    @FunctionalInterface
    interface ElasticFunction {
        //all annotations are interfaces! T now becomes a sub interface???
        Object apply(String methodName, Object... a) throws NoSuchMethodException;
    }

    public static class FunctionExtra implements ElasticFunction{
        @Override
        public Object apply(String methodName, Object... a) throws NoSuchMethodException {
            //find the method return the annotation.
            Function<java.lang.reflect.Method, Object[]> res = x -> {
                Class<?>[] classTypes = ((ParameterTypes) x.getAnnotation(ParameterTypes.class)).input();
                Class<?> output = ((ParameterTypes) x.getAnnotation(ParameterTypes.class)).output();
                return new Object[]{ classTypes, output };
            };
            // if a[i] not an instanceof Class<?>[i] then throw an exception
            java.lang.reflect.Method m = Tools.class.getMethod(methodName);
            for (int i = 0; i < a.length; i++){ //a contains a list of parameters... by right idk

            }
        }
    }
}

//isolated independent datatype class
final class Binary extends Object implements java.io.Serializable{
    private int binaryNum;

    //Constructor to store a binary value data IDK
    public Binary(int binaryNum) throws InvalidBinaryNumberFormat{
        this.binaryNum = this.checkFormat(binaryNum);
    }

    public void setBinaryNum(int binaryNum){
        this.binaryNum = binaryNum;
    }

    public int getBinaryNum(){
        return this.binaryNum;
    }

    @Override
    public String toString(){
        return "" + this.binaryNum;
    }

    public Binary plus(Binary num) throws NotAnArrayException, ContentsNotPrimitiveException{
        //new Binary(101011).add(new Binary(101110))
        // technically it will be this.binaryNum + num
        char[] c = Integer.toString(num.getBinaryNum()).toCharArray();
        char[] c1 = Integer.toString(this.binaryNum).toCharArray();
        Character[] cOut = Tools.Converter.ReferenceTypeConverter.simplifiedToComplexArray(c);
        Character[] c1Out = Tools.Converter.ReferenceTypeConverter.simplifiedToComplexArray(c1);
        String result = new String();
        int tracker = 1;
        do {
            //'1' + '1' = '10'  last digit becomes '0', '1' shifted to before '0'
            if (Tools.ArrayUtilsCustom.getElementAt(cOut.length - tracker).in(cOut) == '1' && Tools.ArrayUtilsCustom.getElementAt(c1Out.length - tracker).in(c1Out) == '1') {
                //if both characters are equal to '1'
                result += '0';
            } else {// '0' + '0' = '0', still 0 as always
                if (Tools.ArrayUtilsCustom.getElementAt(cOut.length - tracker).in(cOut) == '0' && Tools.ArrayUtilsCustom.getElementAt(c1Out.length - tracker).in(c1Out) == '0') {
                    result += '0';
                }
                else { // '1' + '0' = '1', will give you '1'
                    result += '1';
                }
            }
            tracker++; //Add one to the tracker and repeat the whole process again.
        }
        while (cOut.length - tracker >= 0);
        String outcome = new Tools.StringAddOn(result).reverse();
        return null;
    }

    public Binary minus(Binary num) throws NotAnArrayException, ContentsNotPrimitiveException{
        //new Binary(1011).minus(new Binary(1011111))
        //technically it will be this.binaryNum - num
        char[] c = Integer.toString(num.getBinaryNum()).toCharArray();
        char[] c1 = Integer.toString(this.binaryNum).toCharArray();
        Character[] cOut = Tools.Converter.ReferenceTypeConverter.simplifiedToComplexArray(c);
        Character[] c1Out = Tools.Converter.ReferenceTypeConverter.simplifiedToComplexArray(c1);
        String result = new String();
        int tracker = 1;
        do {
            if ((Tools.ArrayUtilsCustom.getElementAt(cOut.length - tracker).in(cOut) == '1' && Tools.ArrayUtilsCustom.getElementAt(c1Out.length - tracker).in(c1Out) == '1')) {
                //if both characters are equal to '1'

            } else {
                if (Tools.ArrayUtilsCustom.getElementAt(cOut.length - tracker).in(cOut) == '0' && Tools.ArrayUtilsCustom.getElementAt(c1Out.length - tracker).in(c1Out) == '0') {

                }
                else {

                }
            }
            tracker++; //Add one to the tracker and repeat the whole process again.
        }
        while (cOut.length - tracker >= 0);
        String outcome = new Tools.StringAddOn(result).reverse();
        return null;
    }

    public Binary times(Binary num) throws NotAnArrayException, ContentsNotPrimitiveException{
        //new Binary(1011).multiply(new Binary(1010111))
        //technically it will be this.binaryNum * num
        char[] c = Integer.toString(num.getBinaryNum()).toCharArray();
        char[] c1 = Integer.toString(this.binaryNum).toCharArray();
        Character[] cOut = Tools.Converter.ReferenceTypeConverter.simplifiedToComplexArray(c);
        Character[] c1Out = Tools.Converter.ReferenceTypeConverter.simplifiedToComplexArray(c1);
        String result = new String();
        int tracker = 1;
        do {
            if ((Tools.ArrayUtilsCustom.getElementAt(cOut.length - tracker).in(cOut) == '1' && Tools.ArrayUtilsCustom.getElementAt(c1Out.length - tracker).in(c1Out) == '1')) {
                //if both characters are equal to '1'

            } else {
                if (Tools.ArrayUtilsCustom.getElementAt(cOut.length - tracker).in(cOut) == '0' && Tools.ArrayUtilsCustom.getElementAt(c1Out.length - tracker).in(c1Out) == '0') {

                }
                else {

                }
            }
            tracker++; //Add one to the tracker and repeat the whole process again.
        }
        while (cOut.length - tracker >= 0);
        String outcome = new Tools.StringAddOn(result).reverse();
        return null;
    }

    public Binary divide(Binary num) throws NotAnArrayException, ContentsNotPrimitiveException{
        //new Binary(1011).divide(new Binary(1010111))
        //technically it will be this.binaryNum / num
        char[] c = Integer.toString(num.getBinaryNum()).toCharArray();
        char[] c1 = Integer.toString(this.binaryNum).toCharArray();
        Character[] cOut = Tools.Converter.ReferenceTypeConverter.simplifiedToComplexArray(c);
        Character[] c1Out = Tools.Converter.ReferenceTypeConverter.simplifiedToComplexArray(c1);
        String result = new String();
        int tracker = 1;
        do {
            if ((Tools.ArrayUtilsCustom.getElementAt(cOut.length - tracker).in(cOut) == '1' && Tools.ArrayUtilsCustom.getElementAt(c1Out.length - tracker).in(c1Out) == '1')) {
                //if both characters are equal to '1'

            } else {
                if (Tools.ArrayUtilsCustom.getElementAt(cOut.length - tracker).in(cOut) == '0' && Tools.ArrayUtilsCustom.getElementAt(c1Out.length - tracker).in(c1Out) == '0') {

                }
                else {

                }
            }
            tracker++; //Add one to the tracker and repeat the whole process again.
        }
        while (cOut.length - tracker >= 0);
        return null;
    }

    private static int checkFormat(int binaryNum) throws InvalidBinaryNumberFormat{
        char[] tempArr = Integer.toString(binaryNum).toCharArray();
        for (char item : tempArr){
            //  c == '1' || '0'   haiya...
            if (!(item == '1' || item == '0')){
                throw new InvalidBinaryNumberFormat("Binary number is not represented in the correct format");
            }
        }
        return binaryNum;
    }
}

