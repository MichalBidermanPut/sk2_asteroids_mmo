#ifndef ELEMENT_H_INCLUDED
#define ELEMENT_H_INCLUDED

class Owner{
    int id;
};

class Rectangle{
public:
    Rectangle(int in_x,int in_y,int in_lenx,int in_leny);
    Rectangle();
    int x,y,lenx, leny;
    int getX(){return x;}
    int getY(){return y;}
    int getLenX(){return lenx;}
    int getLenY(){return leny;}
    bool colides(Rectangle other);
};

class Element{
public:
    Rectangle shape;
    int getX(){return shape.getX();}
    int getY(){return shape.getY();}
    int getOwnerId()const;
    bool operator<(const Element e)const {return this->getOwnerId()<e.getOwnerId();}///This is used for searching in set
    bool operator>(const Element e)const {return this->getOwnerId()>e.getOwnerId();}
    bool operator==(const Element e)const {return this->getOwnerId()==e.getOwnerId();}
};

class Asteroid: public Element{
public:
    Asteroid();
};

class Craft: public Element{
public:
    Owner*owner;
    void die();
};

class Bullet: public Element{
public:
    Craft*craft;
    int x0,y0;
    long long bitrhTime;
};

#endif // ELEMENT_H_INCLUDED
