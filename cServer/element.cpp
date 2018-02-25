#include "element.h"
#include "StaticConsts.h"
#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

Rectangle::Rectangle(){
    return;
}
Rectangle::Rectangle(int in_x,int in_y,int in_lenx,int in_leny){
    x=in_x;
    y=in_y;
    lenx=in_lenx;
    leny=in_leny;
}

int Element::getOwnerId()const {
    NOT_IMPLEMENTED;
    return 0;
}

void Craft::die() {
    NOT_IMPLEMENTED;
}

bool Rectangle::colides(Rectangle other){
    return
        abs(this->getX() - other.getX()) * 2 >= this->getLenX() + other.getLenX()
        and
        abs(this->getY() - other.getY()) * 2 >= this->getLenY() + other.getLenY();

}

Asteroid::Asteroid(){
    int tmpsize=rand()%ASTER_MX_SIZE;
    shape=Rectangle(
                    rand()%WIDTH,
                    rand()%HEIGHT,
                    tmpsize,
                    tmpsize
                    );
}
