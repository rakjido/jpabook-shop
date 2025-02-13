package io.rooftop.jpashop.repository;

//package io.rooftop.jpashop.repository;
//
//import io.rooftop.jpashop.domain.Member;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityManagerFactory;
//import javax.persistence.PersistenceContext;
//import javax.persistence.PersistenceUnit;
//import java.util.List;
//
//@Repository
//public class MemberRepository {
//
////    @PersistenceUnit
////    EntityManagerFactory emf;
//
//    @PersistenceContext
//    EntityManager em;
//
//    public Long save(Member member) {
//        em.persist(member);
//        return member.getId();
//    }
//
//    public Member findOne(Long id) {
//        return em.find(Member.class, id);
//    }
//
//    public List<Member> findAll() {
//
//        return em.createQuery("select m from Member m", Member.class).getResultList();
//    }
//
//    public List<Member> findByName(String name) {
//        return em.createQuery("select m from Member m where m.name =:name", Member.class)
//                .setParameter("name", name)
//                .getResultList();
//    }
//}

import io.rooftop.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByName(String name);
}