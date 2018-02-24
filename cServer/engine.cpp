#include "engine.h"
#include <set>

void Engine::run(){
    while(true)turn();
}

void Engine::turn(){
    update();
    for(Asteroid a: asteroids){
        for(Craft c: crafts){
            if(c.shape.colides(a.shape)){
                c.die();
                crafts.erase(c);
                asteroids.erase(a);
            }
        }
        for(Bullet b: bullets){
            if(b.shape.colides(a.shape)){
                bullets.erase(b);
                asteroids.erase(a);
            }
        }
    }
    for(Bullet b: bullets){
        for(Craft c: crafts){
            if(c.shape.colides(b.shape)){
                c.die();
                crafts.erase(c);
                bullets.erase(b);
            }
        }
    }
}
