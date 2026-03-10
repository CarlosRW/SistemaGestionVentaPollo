package com.ventapollo.service.impl;

import com.ventapollo.domain.Item;
import com.ventapollo.service.ItemService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService {
    @Override
    public List<Item> gets() { return listaItems; }

    @Override
    public void save(Item item) {
        boolean existe = false;
        for (Item i : listaItems) {
            if (i.getId().equals(item.getId())) {
                i.setCantidad(i.getCantidad() + 1);
                existe = true;
                break;
            }
        }
        if (!existe) {
            item.setCantidad(1);
            listaItems.add(item);
        }
    }

    @Override
    public void delete(Item item) {
        listaItems.removeIf(i -> i.getId().equals(item.getId()));
    }

    @Override
    public Item get(Item item) {
        for (Item i : listaItems) {
            if (i.getId().equals(item.getId())) return i;
        }
        return null;
    }

    @Override
    public void update(Item item) {
        for (Item i : listaItems) {
            if (i.getId().equals(item.getId())) {
                i.setCantidad(item.getCantidad());
                break;
            }
        }
    }
}