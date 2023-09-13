package com.game.repository;

import com.game.entity.Player;


import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Optional;


@Repository(value = "db")
public class PlayerRepositoryDB implements IPlayerRepository {
    private final SessionFactory sessionFactory;


    public PlayerRepositoryDB() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Player.class)
                .buildSessionFactory();
    }


    @Override
    public List<Player> getAll(int pageNumber, int pageSize) {
        String nativeSql = "SELECT * FROM rpg.player";
        try (Session session = sessionFactory.openSession()) {
            NativeQuery<Player> query = session.createNativeQuery(nativeSql, Player.class);
            query.setFirstResult(pageNumber * pageSize);
            query.setMaxResults(pageSize);
            return query.list();
        }
    }

    @Override
    public int getAllCount() {
       try(Session session = sessionFactory.openSession()) {
           Query<Long> query= session.createNamedQuery("getCount",Long.class);
           return Math.toIntExact(query.getSingleResult());
       }
    }

    @Override
    public Player save(Player player) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(player);
            session.getTransaction().commit();
            return player;
        }
    }

    @Override
    public Player update(Player player) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(player);
            session.getTransaction().commit();
            return player;
        }
    }

    @Override
    public Optional<Player> findById(long id) {
        String hql = "from Player where id = :inputId ";
        try (Session session = sessionFactory.openSession()) {
            Query<Player> query = session.createQuery(hql, Player.class);
            query.setParameter("inputId", id);
            return   Optional.of(query.getSingleResult());
        }
    }

    @Override
    public void delete(Player player) {
        try ( Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.remove(player);
            session.getTransaction().commit();
        }
    }

    @PreDestroy
    public void beforeStop() {
        sessionFactory.close();
    }
}