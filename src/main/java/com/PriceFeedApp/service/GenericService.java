package com.PriceFeedApp.service;

import com.PriceFeedApp.dao.GenericDAO;

public interface GenericService<T, ID> {

    abstract GenericDAO<T, ID> getDAO();

    T getById(Integer id) ;

}