package SM3Generator;

public enum Types {
    String("text"),
    Date("date"),
    Numeric("numeric");

    private final String text;

    Types(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }
}
