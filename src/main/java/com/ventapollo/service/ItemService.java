package com.ventapollo.service;

import com.ventapollo.domain.Item;
import java.util.ArrayList;
import java.util.List;

public interface ItemService {
    
    
    List<Item> listaItems = new ArrayList<>();

    
    public List<Item> gets();

    
    public void save(Item item);

    
    public void delete(Item item);

    
    public Item get(Item item);

    
    public void update(Item item);

    
    public void checkout();
}