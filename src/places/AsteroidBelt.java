package places;

import interfaces.Steppable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AsteroidBelt implements Steppable {
    private static AsteroidBelt instance;
    private final List<Asteroid> asteroids;

    public static AsteroidBelt getInstance() {
        if(instance == null)
            instance = new AsteroidBelt();

        return instance;
    }

    private AsteroidBelt() {
        this.asteroids = new ArrayList<>();
    }

    @Override
    public void Step() {

    }

    public void AsteroidExploded(Asteroid asteroid) {
        this.asteroids.remove(asteroid);
    }

    private List<Integer> RandomAsteroids(){
        List<Integer> indexes = new ArrayList<Integer>();
        for (int i = 0; i < asteroids.size(); ++i) {
            indexes.add(i);
        }

        Collections.shuffle(list);
        int num = (int) (asteroids.size() * 0.1);
        for(int i = 0; i < asteroids.size() - num; ++i){
            indexes.remove(i);
        }
        return indexes;
    }

    private void NearSun() {
        var indexes = this.RandomAsteroids();

        for(Integer idx : indexes){
            asteroids.get(idx).NearSun();
        }
    }

    private void SolarFlare() {
        var indexes = this.RandomAsteroids();

        for(Integer idx : indexes){
            asteroids.get(idx).SolarFlare();
        }
    }
}
