/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game_engine;

import java.util.List;

/**
 *
 * @author szmii
 */
public interface IEngine {
    void execute(); 
    /*
    void execute():
    sprawdza czy wystepuje kolizja oraz wykonuje efekt kolizji
    */
    void addElement(Interactable element);
    void addElements(List<Interactable> element);
    void deleteElement(int element_ID);
    void deleteElements(List<Integer> IDs);
    void updateElement(Interactable element);/*
    updateElement: Porownoje ID elementu, i podstawia dane
    */
    List<Interactable> getElements();
    
    /*
    modyfikacja elementow twozy kolejke zmian 
    gdy zachodzi 'update();' zmiany zostaja zastosowane do wewnetrznch zmiennych
    */
    
}
