public class MallardDuck extends Duck implements InterfaceFly, quackBehavior{
    
    public MallardDuck(){

    }
    @Override
    public String getDuckSwim() {
        return "Boi";
    }

    @Override
    public String getDuckDisplay() {
        return "Display";
    }

    @Override
    public String quack() {
        return "FLY";
    }

    @Override
    public String flyWithWings() {
        return "QUACK";
    }

    @Override
    public String squeak() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String muteQuack() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String flyNoWay() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
