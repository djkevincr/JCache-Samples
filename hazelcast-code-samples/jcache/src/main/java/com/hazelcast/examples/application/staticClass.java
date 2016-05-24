package com.hazelcast.examples.application;

import com.hazelcast.examples.application.dao.UserDao;

/**
 * Created by djkevincr on 3/22/16.
 */
public class staticClass {
    static UserDao dao2;

   public static void setInctance(UserDao dao){
       dao2= dao;
   }

    public static UserDao getInctance(){
        return dao2;
    }

}
