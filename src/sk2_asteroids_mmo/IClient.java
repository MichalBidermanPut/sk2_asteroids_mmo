/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sk2_asteroids_mmo;

import java.util.List;

/**
 *
 * @author szmii
 */
public interface IClient {
    void update();      ///actualizes information in internal variables
    List<Interactable> getObjects();///returns list of every object in game
    void sendPosition(double x, double y);
    boolean login(String username, String password);///true if logged in correctly
}
