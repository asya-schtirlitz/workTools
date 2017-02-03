package SM3Generator;


public enum Stages {
    PRH("prh",14),
    PRB("prb",16),
    PRV("prv",18),
    POV("pov",20),
    SLOLP("slolp",24);

    private final String name;
    private final int column;

    Stages(String name, int column){
        this.name = name;
        this.column = column;
    }

    public String getName(){
        return name;
    }

    public int getColumn() {
        return column;
    }
}
