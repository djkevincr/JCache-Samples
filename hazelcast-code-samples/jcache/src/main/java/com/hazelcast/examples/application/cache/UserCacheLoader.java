package com.hazelcast.examples.application.cache;

import com.hazelcast.examples.application.dao.UserDao;
import com.hazelcast.examples.application.dao.UserDaoImpl;
import com.hazelcast.examples.application.model.User;
import com.hazelcast.examples.application.staticClass;

import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserCacheLoader implements CacheLoader<Integer, User>, Serializable {

    private  UserDao userDao;

   public UserCacheLoader(UserDao userDao) {
        // store the dao instance created externally
        //this.userDao = userDao;
    }

    public UserCacheLoader( ) {
        // store the dao instance created externally
        this.userDao = staticClass.getInctance();
    }

    public void setDao(UserDao dao){
        this.userDao = dao;
    }

    @Override
    public User load(Integer key) throws CacheLoaderException {
        // just call through into the dao
        User user=null;
        try {
            user = userDao.findUserById(key);
        } catch (CacheLoaderException ex){
            System.out.print(this.hashCode()+" "+getUserDaoKeys()+" not "+key);
            throw ex;
        }
        return user;
    }

    @Override
    public Map<Integer, User> loadAll(Iterable<? extends Integer> keys) throws CacheLoaderException {
        // create the resulting map
        Map<Integer, User> loaded = new HashMap<Integer, User>();
        // for every key in the given set of keys
        for (Integer key : keys) {
            // try to retrieve the user
            User user = userDao.findUserById(key);
            // if user is not found do not add the key to the result set
            if (user != null) {
                loaded.put(key, user);
            }
        }
        return loaded;
    }

    private String getUserDaoKeys(){
        String log ="";
        for (Integer integer : userDao.allUserIds()){
            log = log.concat(String.valueOf(integer));
            log = log.concat("  -  ");
        }
        return log;
    }
}
