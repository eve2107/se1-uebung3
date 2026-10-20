package org.hbrs.se1.ws26.exercises.uebung3.persistence;

import java.io.*;
import java.util.List;

public class PersistenceStrategyStream<E> implements PersistenceStrategy<E> {

    // URL of file, in which the objects are stored
    private String location = "objects.ser";

    // Backdoor method used only for testing purposes, if the location should be changed in a Unit-Test
    // Example: Location is a directory (Streams do not like directories, so try this out ;-)!
    public void setLocation(String location) {
        this.location = location;
    }



    @Override
    public void save(List<E> member) throws PersistenceException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(location))) {
            oos.writeObject(member);
        } catch (IOException e) {
            throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable, "Fehler beim Speichern der Daten");
        }
    }
    @Override
    @SuppressWarnings("unchecked")
    public List<E> load() throws PersistenceException  {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(location))) {
                Object obj = ois.readObject();
                if (obj instanceof List) {
                    return (List<E>) obj;
                }
                throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,"Falsches Format in der Datei");
            } catch (IOException | ClassNotFoundException e) {
                throw new PersistenceException(PersistenceException.ExceptionType.ConnectionNotAvailable,"Fehler beim Laden der Daten");
            }
        }
    }
