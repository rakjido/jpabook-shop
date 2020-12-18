package io.rooftop.jpashop.repository;

import io.rooftop.jpashop.domain.Item.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemRepository {

    @PersistenceContext
    EntityManager em;

    public Long save(Item item) {
        em.persist(item);
        return item.getId();
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

    public List<Item> findByName(String name) {
        return em.createQuery("select i from Item i where i.name =:name", Item.class)
                .setParameter("name", name)
                .getResultList();
    }

}
