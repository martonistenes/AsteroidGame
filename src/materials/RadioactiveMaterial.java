package materials;

import Skeleton.Skeleton;
import places.Asteroid;

public abstract class RadioactiveMaterial extends Material {
    @Override
    public void OnNearSun(Asteroid asteroid) {
        Skeleton.getInstance().tabIncrement();
        Skeleton.getInstance().Print(this,"OnNearSun()");
        Skeleton.getInstance().tabDecrement();
        asteroid.Explosion();
    }
}
