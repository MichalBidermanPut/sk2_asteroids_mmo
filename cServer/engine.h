#ifndef ENGINE_H_INCLUDED
#define ENGINE_H_INCLUDED

#include "element.h"
#include <set>
using std::set;

class Engine{
private:
    set<Asteroid> asteroids;
    set<Craft> crafts;
    set<Bullet> bullets;
    void turn();
    void generate_asteroids();
    void update();
public:
    void run();

    void addAsteroid(Asteroid a);
    void addCraft(Craft c);
    void addBullet(Bullet b);
    void delAsteroid(Asteroid a);
    void delCraft(Craft c);
    void delBullet(Bullet b);
};


#endif // ENGINE_H_INCLUDED
