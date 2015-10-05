package mule;

public class Store {

    private int food, energy, ore, mule;

    public Store() {
        this("beginner");
    }
    public Store(String difficulty) {
        if(difficulty == "beginner") {
            food = 16;
            energy = 16;
            ore = 0;
            mule = 25;
        } else if(difficulty == "standard" || difficulty == "tournament") {
            food = 8;
            energy = 8;
            ore = 8;
            mule = 14;
        } else {
            //don't let this happen
        }
    }

    public int getFood() { return food; }

    public int getEnergy() { return energy; }

    public int getOre() {return ore; }

    public int getMule() { return mule; }

    public void setFood(int f) { food = f; }

    public void setEnergy(int e) { energy = e; }

    public void setOre(int o) { ore = o;}

    public void setMule(int m) { mule = m; }

    public boolean canPurchase(String item) {
        switch(item) {
            case "food": return food > 0;
            case "ore": return ore > 0;
            case "energy": return energy > 0;
            default: return false;
        }
    }

}
