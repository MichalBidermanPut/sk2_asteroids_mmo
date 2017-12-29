/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_engine;

/**
 *
 * @author szmii
 */
public interface IServer {
    void open();
    void close();
    void broadcast();//wysyla dane
    void updateEngine();//pobiera dane i wruca do silnika
}
