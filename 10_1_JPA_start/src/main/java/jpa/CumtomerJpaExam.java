package jpa;

import entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class CumtomerJpaExam {
    public static void main(String[] args) {
        // EntityManager를 얻기 위한 Factory 설정
        // "customer-exam"은 persistence unit name
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("customer-exam");

        // EntityManager
        EntityManager em = emf.createEntityManager();

        // Transaction을 시작
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(Customer.sample());
        tx.commit();

        em.close();
        emf.close();
    }
}
