package com.website.website.dao;

public interface IGenericDao {
    Object save(Object o, String collectionName);
    Object save(Object o);
    boolean delete(Object o);
    boolean delete(Object o, String collectionName);
}
