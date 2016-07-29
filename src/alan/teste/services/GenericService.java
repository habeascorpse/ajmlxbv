/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.services;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

/**
 *
 *
 * @author alan
 * @param <T>
 * @since 0.4
 *
 * Classe genérica que implementa funções para os modelos
 *
 */
public abstract class GenericService<T> {

    /**
     * @author alan Intancia do PersistenceTransaction
     */
    private final Class<T> type;

    /**
     *
     * @param type
     * @param connection EnumConnection
     */
    protected GenericService(Class<T> type) {
        this.type = type;
    }

    /**
     * Método que retorna a Entidade pelo ID
     *
     * @param id - id da entidade
     * @return T - Retorna a Entidade
     * @author alan
     */
    public T getByID(Long id) {

        try {
            T t = getEntityManager().find(type, id);
            return t;

        } catch (NoResultException ex) {
            return null;
        }

    }

    /**
     * Método que retorna uma Lista de todas Entidades
     *
     * @return List<T> - Retorna uma lista de Entidade
     *
     */
    public List<T> getAll() {
        try {

            List lista = getEntityManager()
                    .createQuery("select a from " + type.getName() + " a")
                    .getResultList();

            return lista;
        } catch (NoResultException ex) {
            return new ArrayList<T>();
        }

    }

    /**
     * Método que implementa persiste uma entidade
     *
     * @param obj Entidade
     * @return Retorna um Enum de Retorno EReturn
     *
     */
    public T save(T obj) {

        getEntityManager().persist(obj);
        return obj;
    }

    /**
     * Método que implementa persiste uma lista de entidade
     *
     * @param objs
     * @return Retorna um Enum de Retorno EReturn
     */
    public void save(List<T> objs) {

        for (T obj : objs) {
            getEntityManager().persist(obj);
        }
    }

    /**
     * Método que implementa o merge em uma entidade
     *
     * @param obj
     * @return Retorna um Enum de Retorno EReturn
     */
    public T merge(T obj) {

        return getEntityManager().merge(obj);

    }

    /**
     * Método que implementa o merge em uma lista de entidades
     *
     * @param objs
     * @return Retorna um Enum de Retorno EReturn
     */
    public List<T> merge(List<T> objs) {

        List<T> secondL = new ArrayList();

        objs.stream().forEach((obj) -> {
            secondL.add(getEntityManager().merge(obj));
        });

        return secondL;
    }

    /**
     * Método que remove uma entidade
     *
     * @param obj
     * @return Retorna um Enum de Retorno EReturn
     */
    public void remove(T obj) {

        obj = getEntityManager().merge(obj);
        getEntityManager().remove(obj);

    }

    /**
     * Método que remove uma lista de entidades
     *
     * @param objs
     * @return Retorna um Enum de Retorno EReturn
     */
    public void remove(List<T> objs) {


            for (T obj : objs) {
                obj = getEntityManager().merge(obj);
                getEntityManager().remove(obj);
            }

    }

    protected abstract EntityManager getEntityManager();

}
