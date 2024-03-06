package com.closure13k.aaronfmpt1.persistence;

import com.closure13k.aaronfmpt1.logic.employee.Employee;
import com.closure13k.aaronfmpt1.persistence.exceptions.NonexistentEntityException;
import com.closure13k.aaronfmpt1.persistence.exceptions.PreexistingEntityException;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class EmployeeJpaController implements Serializable {

    public EmployeeJpaController() {
        this.emf = Persistence.createEntityManagerFactory("EmpleadosPU");
    }

    public EmployeeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Employee employee) throws PreexistingEntityException, RollbackException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Employee employee) throws Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            employee = em.merge(employee);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.isEmpty()) {
                int id = employee.getId();
                if (findEmployee(id) == null) {
                    throw new NonexistentEntityException("The employee with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Employee employee;
            try {
                employee = em.getReference(Employee.class, id);
                employee.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The employee with id " + id + " no longer exists.", enfe);
            }
            em.remove(employee);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Employee> findEmployeeEntities() {
        return findEmployeeEntities(true, -1, -1);
    }

    public List<Employee> findEmployeeEntities(int maxResults, int firstResult) {
        return findEmployeeEntities(false, maxResults, firstResult);
    }

    private List<Employee> findEmployeeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Employee.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Employee> findActiveEmployeeEntities() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Employee.findAllActiveEmployees");
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Employee> findActiveEmployeeEntitiesByRole(String role) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Employee.findAllByRole");
            q.setParameter("role", role);
            return q.getResultList();
        } finally {
            em.close();
        }
    }


    public Employee findEmployee(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Employee.class, id);
        } finally {
            em.close();
        }
    }

    public Employee findEmployee(String nif) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Employee.findByNif");
            q.setParameter("nif", nif);
            return (Employee) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public Employee findInactiveEmployee(String nif) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Employee.findInactiveByNif");
            q.setParameter("nif", nif);
            return (Employee) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public List<Employee> findEmployeesByListOfNifs(List<String> employeeNifs) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Employee.findByListOfNifs", Employee.class);
            q.setParameter("nifList", employeeNifs);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public int getEmployeeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Employee> rt = cq.from(Employee.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}

