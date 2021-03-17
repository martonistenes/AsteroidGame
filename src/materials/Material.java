package materials;

import places.Asteroid;

public abstract class Material {
    public void onNearSun(Asteroid asteroid) {

    }

    public abstract void count(MaterialCounter counter);

    public abstract boolean compatibleWith(Material material);
}
