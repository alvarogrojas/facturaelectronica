package com.rfs.repository;


import com.rfs.dtos.FacturaRegistroDTO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Service
public class FacturaRepositoryImpl implements   FacturaRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;


    public List<FacturaRegistroDTO> getFacturaFiltrados(Date fechaInicio, Date fechaFinal, Integer enviadas, Integer empresaId,String estado) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery(getQueryCustomForRecord(null,fechaInicio, fechaFinal, enviadas, empresaId,estado));

        initQueryForFacturaFilter(null,fechaInicio, fechaFinal, enviadas, empresaId, estado, query);
        return query.list();
    }

    public List<FacturaRegistroDTO> getFacturaFiltrados(Integer facturaId, Date fechaInicio, Date fechaFinal, Integer empresaId) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery(getQueryCustomForRecord(facturaId, fechaInicio, fechaFinal, null, empresaId,null));

        initQueryForFacturaFilter(facturaId, fechaInicio, fechaFinal, null, empresaId, null, query);
        return query.list();
    }

    @Override
    public Long countFacturaFiltrados(Integer facturaId, Date fechaInicio, Date fechaFinal,Integer empresaId) {
//        Session session = entityManager.unwrap(Session.class);
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery(getCountForFacturasFilter(facturaId, fechaInicio, fechaFinal, empresaId));

        initQueryForTicaFilter(facturaId, fechaInicio, fechaFinal, empresaId, query);


        return (Long) query.uniqueResult();
    }




    private void initQueryForFacturaFilter(Integer clienteId, String estado, String estadoPago, Date fechaInicio, Date fechaFinal, Integer enviadas, Integer empresaId, Query criteria) {


        if (fechaInicio!=null ) {
            criteria.setParameter("fechaDesde", fechaInicio);
        }

        if (fechaFinal!=null ) {
            criteria.setParameter("fechaHasta", fechaFinal);
        }

        if (clienteId!=null && clienteId!=0) {
            criteria.setParameter("clienteId", clienteId);
        }

        if (empresaId!=null && empresaId!=0) {
            criteria.setParameter("empresaId", empresaId);
        }

       // if (estado!=null ) {
        if (!StringUtils.isEmpty(estado) ) {
            criteria.setParameter("estado", estado);
        }

        if (!StringUtils.isEmpty(estadoPago) ) {
            criteria.setParameter("estadoPago", estadoPago);
        }

        if (enviadas!=null && enviadas!=-1) {
            criteria.setParameter("enviadas", enviadas);
        }
    }

    private void initQueryForFacturaFilter(Integer facturaId, Date fechaInicio, Date fechaFinal, Integer enviadas, Integer empresaId, String estado,Query criteria) {


        if (fechaInicio!=null ) {
            criteria.setParameter("fechaDesde", fechaInicio);
        }

        if (fechaFinal!=null ) {
            criteria.setParameter("fechaHasta", fechaFinal);
        }

        if (enviadas!=null && enviadas!=-1) {
            criteria.setParameter("enviadas", enviadas);
        }

        if (estado!=null && !estado.equals("")) {
            criteria.setParameter("estado", estado);
        }

        if (facturaId!=null && facturaId>0) {
            criteria.setParameter("facturaId", facturaId);
        }

        if (empresaId!=null && empresaId!=0) {

            criteria.setParameter("empresaId", empresaId);
        }

    }

    private String getQueryCustomForRecord(Integer facturaId, Date fechaDesde, Date fechaHasta, Integer enviadas, Integer empresaId, String estado) {
        String result = " select new com.rfs.dtos.FacturaRegistroDTO(f) from Factura f where ";
        boolean andRequired = false;
//        if (fechaDesde!=null || fechaHasta!=null) {

            if (fechaDesde!=null ) {
                if (andRequired) {
                    result = result + " and ";
                }
                andRequired = true;
                result = result + " date(f.fechaFacturacion)>=:fechaDesde";
            }

            if (fechaHasta!=null ) {
                if (andRequired) {
                    result = result + " and ";
                } else {
                    andRequired = true;
                }
                result = result + " date(f.fechaFacturacion)<=:fechaHasta";
            }

            if (facturaId!=null && facturaId!=0) {
                if (andRequired) {
                    result = result + " and ";
                } else {
                    andRequired = true;
                }
                result = result + " f.facturaIdentity.facturaId=:facturaId";
            }
            if (enviadas!=null && enviadas!=-1) {
                if (andRequired) {
                    result = result + " and ";
                } else {
                    andRequired = true;
                }
                result = result + " f.enviadaHacienda=:enviadas";
            }

            if (estado!=null && !estado.equals("")) {
                if (andRequired) {
                    result = result + " and ";
                } else {
                    andRequired = true;
                }
                result = result + " f.estado=:estado";
            }

            if (empresaId!=null && empresaId!=0) {
                if (andRequired) {
                    result = result + " and ";
                } else {
                    andRequired = true;
                }
                result = result + " f.facturaIdentity.empresaId=:empresaId";
            }
       // }
        result = result + " order by f.fechaFacturacion asc";
        return result;
    }


    private String getQueryCustom(Integer reciboId, Integer facturaId, Integer clienteId, Date fechaDesde, Date fechaHasta) {
        String result = "select new com.rfs.dtos.FacturaReciboDTO(f.facturaIdentity.facturaId, 0,f.fechaFacturacion,f.encargado.nombre, f.cliente.nombre, f.enviadaHacienda) from Factura f ";
        boolean andRequired = true;
        if ((reciboId!=null && reciboId!=0) || (facturaId!=null && facturaId!=0) || (clienteId!=null && clienteId!=0) ||
                (fechaDesde!=null ) || (fechaHasta!=null )) {
            //result = result + "where ";
            if (reciboId!=null && reciboId!=0) {
                if (andRequired) {
                    result = result + " and ";
                }
                result = result + " f.reciboId=:reciboId";
            }

            if (facturaId!=null && facturaId!=0) {
                if (andRequired) {
                    result = result + " and ";
                }
                result = result + " f.facturaIdentity.facturaId=:facturaId";
            }

            if (clienteId!=null && clienteId!=0) {
                if (andRequired) {
                    result = result + " and ";
                }
                result = result + " r.cliente.id=:clienteId";

            }

            if (fechaDesde!=null ) {
                if (andRequired) {
                    result = result + " and ";
                }
                result = result + " date(f.fechaFacturacion)>=:fechaDesde";
            }

            if (fechaHasta!=null ) {
                if (andRequired) {
                    result = result + " and ";
                }
                result = result + " date(f.fechaFacturacion)<=:fechaHasta";
            }
        }
        result = result + " order by f.facturaIdentity.facturaId desc";
        return result;
    }

    @Override
    public Long countFacturas(Integer clienteId, String estado, String estadoPago, Date fechaInicio, Date fechaFinal, Integer enviadas, Integer empresaId) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery(getCountForFacturas(clienteId, estado, estadoPago, fechaInicio, fechaFinal,enviadas, empresaId));

        initQueryForFacturas(clienteId, estado,estadoPago, fechaInicio, fechaFinal, enviadas, empresaId, query);


        return (Long) query.uniqueResult();
    }

    private String getQueryCustomForRecord(Integer clienteId, String estado, String estadoPago, Date fechaDesde, Date fechaHasta, Integer enviadas, Integer empresaId) {
        String result = " select new com.rfs.dtos.FacturaRegistroDTO(f) from Factura f where  ";
        boolean andRequired = false;
        if (fechaDesde!=null || fechaHasta!=null) {

            if (fechaDesde!=null ) {
                if (andRequired) {
                    result = result + " and ";
                }
                andRequired = true;
                result = result + " date(f.fechaFacturacion)>=:fechaDesde";
            }

            if (fechaHasta!=null ) {
                if (andRequired) {
                    result = result + " and ";
                } else {
                    andRequired = true;
                }
                result = result + " date(f.fechaFacturacion)<=:fechaHasta";
            }
            if (clienteId!=null && clienteId!=0) {
                if (andRequired) {
                    result = result + " and ";
                } else {
                    andRequired = true;
                }
                result = result + " f.cliente.id=:clienteId";
            }

            if (empresaId!=null && empresaId!=0) {
                if (andRequired) {
                    result = result + " and ";
                } else {
                    andRequired = true;
                }
                result = result + " f.facturaIdentity.empresaId=:empresaId";
            }

            if (!StringUtils.isEmpty(estado)) {
                if (andRequired) {
                    result = result + " and ";
                } else {
                    andRequired = true;
                }
                result = result + " f.estado=:estado";
            }

            if (!StringUtils.isEmpty(estadoPago)) {
                if (andRequired) {
                    result = result + " and ";
                } else {
                    andRequired = true;
                }
                result = result + " f.estadoPago=:estadoPago";
            }


            if (enviadas!=null && enviadas!=-1) {
                if (andRequired) {
                    result = result + " and ";
                }
                result = result + " f.enviadaHacienda=:enviadas";
            }
        }
        result = result + " order by f.fechaFacturacion asc";
        return result;
    }

    private String getSumQueryCustomForRecord(Integer clienteId, String estado, String estadoPago,Date fechaDesde, Date fechaHasta, Integer enviadas, Integer empresaId) {
        String result = " select sum(f.total) from Factura f where  ";
        boolean andRequired = false;
        if (fechaDesde!=null || fechaHasta!=null) {

            if (fechaDesde!=null ) {
                if (andRequired) {
                    result = result + " and ";
                }
                andRequired = true;
                result = result + " date(f.fechaFacturacion)>=:fechaDesde";
            }

            if (fechaHasta!=null ) {
                if (andRequired) {
                    result = result + " and ";
                } else {
                    andRequired = true;
                }
                result = result + " date(f.fechaFacturacion)<=:fechaHasta";
            }
            if (clienteId!=null && clienteId!=0) {
                if (andRequired) {
                    result = result + " and ";
                } else {
                    andRequired = true;
                }
                result = result + " f.cliente.id=:clienteId";
            }

            if (empresaId!=null && empresaId!=0) {
                if (andRequired) {
                    result = result + " and ";
                }  else {
                    andRequired = true;
                }
                result = result + " f.facturaIdentity.empresaId=:empresaId";

            }

            if (!StringUtils.isEmpty(estado)) {
                if (andRequired) {
                    result = result + " and ";
                } else {
                    andRequired = true;
                }
                result = result + " f.estado=:estado";
            }

            if (!StringUtils.isEmpty(estadoPago)) {
                if (andRequired) {
                    result = result + " and ";
                } else {
                    andRequired = true;
                }
                result = result + " f.estadoPago=:estadoPago";
            }


            if (enviadas!=null && enviadas!=-1) {
                if (andRequired) {
                    result = result + " and ";
                }
                result = result + " f.enviadaHacienda=:enviadas";
            }
        }

        return result;
    }

    @Override
    public List<FacturaRegistroDTO> getFacturas(Integer clienteId, String estado, String estadoPago, Date fechaInicio, Date fechaFinal, Integer pageNumber, Integer pageSize, Integer enviadas, Integer empresaId) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery(getQueryCustomForRecord(clienteId, estado, estadoPago, fechaInicio, fechaFinal, enviadas,empresaId));
        initQueryForFacturaFilter(clienteId, estado, estadoPago, fechaInicio, fechaFinal, enviadas, empresaId, query);
        return query.list();
    }

    @Override
    public Double sumTotalesFacturas(Integer clienteId, String estado, String estadoPago, Date fechaInicio, Date fechaFinal, Integer pageNumber, Integer pageSize, Integer enviadas, Integer empresaId) {
        Session session = entityManager.unwrap(Session.class);
        Query query = session.createQuery(getSumQueryCustomForRecord(clienteId, estado, estadoPago, fechaInicio, fechaFinal, enviadas, empresaId));

        initQueryForFacturaFilter(clienteId, estado, estadoPago, fechaInicio, fechaFinal, enviadas, empresaId, query);
        return (Double)query.uniqueResult();
    }


    private void initQueryForTicaFilter(Integer facturaId, Date fechaInicio, Date fechaFinal, Integer empresaId,  Query criteria) {

        if (empresaId!=null && empresaId!=0) {
            criteria.setParameter("empresaId", empresaId);
        }

        if (facturaId!=null && facturaId!=0) {
            criteria.setParameter("facturaId", facturaId);
        }


        if (fechaInicio!=null ) {
            criteria.setParameter("fechaDesde", fechaInicio);
        }

        if (fechaFinal!=null ) {
            criteria.setParameter("fechaHasta", fechaFinal);
        }
    }

    private String getCountForFacturasFilter( Integer facturaId,  Date fechaDesde, Date fechaHasta,Integer empresaId) {


        String result = "select count(f) from Factura f where ";
        boolean andRequired = false;
//        if  ((facturaId!=null && facturaId!=0) || (empresaId!=null && empresaId!=0) ||
//                (fechaDesde!=null ) || (fechaHasta!=null )) {
            //result = result + "where ";


            if (facturaId!=null && facturaId!=0) {
                if (andRequired) {
                    result = result + " and ";
                }
                andRequired =true;

                result = result + " f.facturaIdentity.facturaId=:facturaId";
            }

            if (empresaId!=null && empresaId!=0) {
                if (andRequired) {
                    result = result + " and ";
                }
                andRequired =true;

                result = result + " f.facturaIdentity.empresaId=:empresaId";
            }


            if (fechaDesde!=null ) {
                if (andRequired) {
                    result = result + " and ";
                }
                andRequired =true;

                result = result + " date(f.fechaFacturacion)>=:fechaDesde";
            }

            if (fechaHasta!=null ) {
                if (andRequired) {
                    result = result + " and ";
                }
                result = result + " date(f.fechaFacturacion)<=:fechaHasta";
            }
       // }
        return result;
    }


    private String getCountForFacturas(Integer clienteId, String estado, String estadoPago, Date fechaDesde, Date fechaHasta, Integer enviadas, Integer empresaId) {


        String result = "select count(f.facturaIdentity.facturaId) from Factura f  where  ";
        boolean andRequired = false;
        if (!StringUtils.isEmpty(estado) || (clienteId!=null && clienteId!=0) ||
                (fechaDesde!=null ) || (fechaHasta!=null ) || !StringUtils.isEmpty(estadoPago) || (empresaId!=null && empresaId!=0) || (enviadas!=null && enviadas!=0)) {
            //result = result + "where ";
            if (!StringUtils.isEmpty(estado)) {

                result = result + " f.estado=:estado";
                andRequired = true;
            }

            if (!StringUtils.isEmpty(estadoPago)) {

                if (andRequired) {
                    result = result + " and ";
                }  else {
                    andRequired = true;
                }
                result = result + " f.estadoPago=:estadoPago";
            }


            if (clienteId!=null && clienteId!=0) {
                if (andRequired) {
                    result = result + " and ";
                }  else {
                    andRequired = true;
                }
                result = result + " f.cliente.id=:clienteId";

            }
            if (empresaId!=null && empresaId!=0) {
                if (andRequired) {
                    result = result + " and ";
                }  else {
                    andRequired = true;
                }
                result = result + " f.facturaIdentity.empresaId=:empresaId";

            }

            if (fechaDesde!=null ) {
                if (andRequired) {
                    result = result + " and ";
                } else {
                    andRequired = true;
                }
                result = result + " date(f.fechaFacturacion)>=:fechaDesde";
            }

            if (fechaHasta!=null ) {
                if (andRequired) {
                    result = result + " and ";
                }
                result = result + " date(f.fechaFacturacion)<=:fechaHasta";
            }

            if (enviadas!=null && enviadas!=-1) {
                if (andRequired) {
                    result = result + " and ";
                }  else {
                    andRequired = true;
                }
                result = result + " f.enviadaHacienda=:enviadas";

            }
        }
        return result;
    }

    private void initQueryForFacturas(Integer clienteId, String estado, String estadoPago, Date fechaInicio, Date fechaFinal, Integer enviadas, Integer empresaId, Query criteria) {

        if (!StringUtils.isEmpty(estado)) {

            criteria.setParameter("estado", estado);
        }

        if (!StringUtils.isEmpty(estadoPago)) {

            criteria.setParameter("estadoPago", estadoPago);
        }


        if (clienteId!=null && clienteId!=0) {

            criteria.setParameter("clienteId", clienteId);
        }

        if (empresaId!=null && empresaId!=0) {

            criteria.setParameter("empresaId", empresaId);
        }

        if (fechaInicio!=null ) {
            criteria.setParameter("fechaDesde", fechaInicio);
        }

        if (fechaFinal!=null ) {
            criteria.setParameter("fechaHasta", fechaFinal);
        }

        if (enviadas!=null && enviadas!=-1) {
            criteria.setParameter("enviadas", enviadas);
        }
    }


}
