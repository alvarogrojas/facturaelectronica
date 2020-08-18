package com.rfs.repository;

//@Service
public class ReciboRepositoryImpl implements   ReciboRepositoryCustom {
//    @PersistenceContext
//    private EntityManager entityManager;
//    @Override
//    public List<ReciboDTO> getRecibosFiltrados(Integer encargadoId, Integer clienteId, String estado,
//                                               Date fechaDesde, Date fechaHasta,
//                                               Integer pageNumber, Integer pageSize) {
//        Session session = entityManager.unwrap(Session.class);
//        Query query = session.createQuery(getQueryCustom(session,encargadoId, clienteId, estado,
//                fechaDesde, fechaHasta));
//        if (pageNumber!=null && pageSize!=null) {
//            query.setFirstResult(pageSize * pageNumber);
//            query.setMaxResults(pageSize);
//        }
//        initQueryForRecibosFilter(encargadoId, clienteId, estado, fechaDesde, fechaHasta, query);
//
//        return query.list();
//    }
//
//    private String getQueryCustom(Session session, Integer encargadoId, Integer clienteId, String estado, Date fechaDesde, Date fechaHasta) {
//        String result = "select new com.rfs.dtos.ReciboDTO(r.id, r.recibo,r.aduana.nombre,r.consignatario, r.dua,r.encargado.nombre,r.corelacionId,r.estado, r.tipo.nombre,r.cliente.nombre,r.fecha, previoExamen, aforoFisico, r.permisos, r.pedimentados) from Recibo r ";
//        boolean andRequired = false;
//        if ((encargadoId!=null && encargadoId!=0) || (clienteId!=null && clienteId!=0) ||
//                (!Strings.isNullOrEmpty(estado) && !estado.equals("TODOS")) || (fechaDesde!=null ) || (fechaHasta!=null )) {
//            result = result + "where ";
//            if (encargadoId!=null && encargadoId!=0) {
//                result = result + " r.encargado.id=:encargadoId";
//                andRequired = true;
//            }
//
//            if (clienteId!=null && clienteId!=0) {
//                if (andRequired) {
//                    result = result + " and ";
//                } else {
//                    andRequired = true;
//                }
//                result = result + " r.cliente.id=:clienteId";
//
//            }
//
//            if (!Strings.isNullOrEmpty(estado) && !estado.equals("TODOS")) {
//                if (andRequired) {
//                    result = result + " and ";
//                } else {
//                    andRequired = true;
//                }
//                result = result + " r.estado=:estado";
//            }
//
//            if (fechaDesde!=null ) {
//                if (andRequired) {
//                    result = result + " and ";
//                } else {
//                    andRequired = true;
//                }
//                result = result + " r.fecha>=:fechaDesde";
//            }
//
//            if (fechaHasta!=null ) {
//                if (andRequired) {
//                    result = result + " and ";
//                }
//                result = result + " r.fecha<=:fechaHasta";
//            }
//        }
//        return result;
//    }
//
//    @Override
//    public Long countRecibosFiltrados(Integer encargadoId, Integer clienteId, String estado, Date fechaDesde, Date fechaHasta, Integer pageNumber, Integer pageSize) {
//        Session session = entityManager.unwrap(Session.class);
//        Criteria criteria = session.createCriteria(Recibo.class);
//
//
//        initCriteriaForRecibosFilter(encargadoId, clienteId, estado, fechaDesde, fechaHasta, criteria);
//
//        criteria.setProjection(Projections.rowCount());
//
//        return (Long) criteria.uniqueResult();
//
//    }
//
//    private void initCriteriaForRecibosFilter(Integer encargadoId, Integer clienteId, String estado, Date fechaDesde, Date fechaHasta, Criteria criteria) {
//        if (encargadoId!=null && encargadoId!=0) {
//            criteria.add(Restrictions.eq("encargado.id", encargadoId));
//        }
//        if (clienteId!=null && clienteId!=0) {
//            criteria.add(Restrictions.eq("cliente.id", clienteId));
//        }
//        if (!Strings.isNullOrEmpty(estado) && !estado.equals("TODOS")) {
//            criteria.add(Restrictions.eq("estado", estado));
//        }
//
//        if (fechaDesde!=null ) {
//            criteria.add(Restrictions.ge("fecha", fechaDesde));
//        }
//
//        if (fechaHasta!=null ) {
//            criteria.add(Restrictions.le("fecha", fechaHasta));
//        }
//    }
//
//    private void initQueryForRecibosFilter(Integer encargadoId, Integer clienteId, String estado, Date fechaDesde, Date fechaHasta, Query criteria) {
//        if (encargadoId!=null && encargadoId!=0) {
//            criteria.setParameter("encargadoId", encargadoId);
//        }
//        if (clienteId!=null && clienteId!=0) {
//            criteria.setParameter("clienteId", clienteId);
//        }
//        if (!Strings.isNullOrEmpty(estado) && !estado.equals("TODOS")) {
//            criteria.setParameter("estado", estado);
//        }
//
//        if (fechaDesde!=null ) {
//            criteria.setParameter("fechaDesde", fechaDesde);
//        }
//
//        if (fechaHasta!=null ) {
//            criteria.setParameter("fechaHasta", fechaHasta);
//        }
//    }


}
