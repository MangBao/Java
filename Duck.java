
public abstract class Duck {
    private String Swim;
    private String Display;

    public Duck(){
        this.Swim = this.getDuckSwim();
        this.Display = this.getDuckDisplay();
    }
    public Duck(String Swim, String Display) {
        this.Swim = Swim;
        this.Display = Display;
    }
    public String setSwim() {
        return Swim;
    }
    public String setDisplay() {
        return Display;
    }
    public String perfomQuack(){
        return Display;
    }
    public abstract String getDuckSwim();
    public abstract String getDuckDisplay();
}

