package com.example.foo.spaceapp.particle;

import java.util.Random;

/**
 * Created by foo on 12/18/16.
 */

public interface ParticleInitializer {
    void initParticle(Particle p, Random r);
}
