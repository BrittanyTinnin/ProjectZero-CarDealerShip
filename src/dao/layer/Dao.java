package dao.layer;


import model.layer.User;

import java.util.List;

public interface Dao<T> {

    public List<T> getData();
    public boolean storeData(List<T> users);
    public T getUser(int userId);
    public void updateUser(T user);
    public void deleteUser(T user);
}
