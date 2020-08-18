package com.rfs.repository;

public interface ReciboRepository {
        //extends CrudRepository<Recibo, Long>,ReciboRepositoryCustom {

//    public Integer countByEncargadoIdAndEstado(Integer encargadoId, String estado);
//
//    @Query(value = "select count(r.id) from Recibo r, Cliente c,TipoTramite t where r.encargado.id=?1 and r.estado=?2 and c.id=r.cliente.id and t.id=r.tipo.id and (r.recibo like %?3% or c.nombre like %?3% or r.consignatario like %?3% or r.proveedor like %?3% or r.bl like %?3% or t.nombre like %?3%)")
//    public Integer getCountByEncargadoIdAndEstadoWithFilter(Integer encargadoId, String estado, String filter);
//
//    @Query(value = "select count(r.id) from Recibo r where r.estado=?1 and (r.dua is not null and r.dua!='')")
//    public Integer getCountByEstadoConDua(String estado);
//
//    @Query(value = "select count(r.id) from Recibo r where r.fecha>=current_date")
//    public Integer getCountRecibosHoy();
//
//    @Query(value = "select count(r.id) from Recibo r, Cliente c,TipoTramite t where r.estado=?1 and c.id=r.cliente.id and t.id=r.tipo.id and (r.recibo like %?2% or c.nombre like %?2% or r.consignatario like %?2% or r.proveedor like %?2% or r.bl like %?2% or t.nombre like %?2%)")
//    public Integer getCountByEstadoWithFilter(String estado, String filter);
//
//    public Integer countByEstado(String estado);
//
//    @Query(value = "select new com.rfs.dtos.EncargadoDTO(u.id, u.nombre,r.estado, count(r)) from Recibo r, Usuario u where r.estado='PENDIENTE' and r.encargado.id=u.id group by r.encargado.id order by COUNT(r)  desc")
//    public List<EncargadoDTO> findEncargadosCount();
//
//    @Query(value = "select new com.rfs.dtos.ReciboDTO(r.id, r.recibo,r.aduana.nombre,r.consignatario, r.dua,r.encargado.nombre,r.corelacionId,r.estado, r.tipo.nombre,r.cliente.nombre,r.fecha) from Recibo r where r.fecha>=current_date order by r.fecha  desc")
//    public List<ReciboDTO>  findRecibosHoy();
//
//    public Recibo findTopByOrderByIdDesc();
//
//    public List<Recibo> findByClienteIdAndEstado(Integer clienteId, String estado);
//
//    public List<Recibo> findByClienteId(Integer clienteId);
//
//    public List<Recibo> findByCorelacionId(Integer corelacionId);
//
////    public List<Recibo> findByEncargadoIdAndEstadoOrderByFechaAsc(Integer encargadoId, String estado);
//
//    @Query(value = "select new com.rfs.dtos.PendientesFacturarDTO(r.id, r.recibo,r.cliente.nombre, r.dua, u.nombre,r.fecha,r.ticaEstado,r.corelacionId) from Recibo r, Usuario u where r.estado='PENDIENTE' and (r.dua is not null and r.dua!='') and r.encargado.id=u.id")
//    public List<PendientesFacturarDTO> findPendientesFacturar();
//
//    @Query(value = "select new com.rfs.dtos.PendientesFacturarDTO(r.id, r.recibo,r.cliente.nombre, r.dua, u.nombre,r.fecha,r.ticaEstado,r.corelacionId) from Recibo r, Usuario u where r.estado='PENDIENTE' and (r.dua is not null and r.dua!='') and r.ticaEstado='EN PROCESO DE AFORO' and r.encargado.id=u.id")
//    public List<PendientesFacturarDTO> findPendientesFacturarEnAforo();
//
//    @Query(value = "select new com.rfs.dtos.PendientesFacturarDTO(r.id, r.recibo,r.cliente.nombre, r.dua, u.nombre,r.fecha,r.ticaEstado,r.corelacionId) from Recibo r, Usuario u where r.estado='PENDIENTE' and (r.dua is not null and r.dua!='') and r.ticaEstado='FACTURAR INMEDIATO' and r.encargado.id=u.id")
//    public List<PendientesFacturarDTO> findPendientesFacturarEnInmediata();
//
//    @Query(value = "select new com.rfs.dtos.PendientesFacturarDTO(r.id, r.recibo,r.cliente.nombre, r.dua, u.nombre,r.fecha,r.ticaEstado,r.corelacionId) from Recibo r, Usuario u where r.estado='PENDIENTE' and (r.dua is not null and r.dua!='') and r.ticaEstado='DUA ANTICIPADO' and r.encargado.id=u.id")
//    public List<PendientesFacturarDTO> findPendientesFacturarDuaAnticipado();
//
//    @Query(value = "select new com.rfs.dtos.PendienteDTO(r.id, r.recibo,r.cliente.nombre, r.observaciones,date(r.fechaUltimoCambio)=current_date,datediff(now(),r.fechaUltimoCambio)) from Recibo r where r.encargado.id=?1 and r.estado=?2 order by r.fechaUltimoCambio")
//    public List<PendienteDTO> findPendientesByFechaAsc(Integer encargadoId, String estado);
//
//    @Query(value = "select new com.rfs.dtos.ReciboDTO(r.id, r.recibo,r.consignatario, r.proveedor,r.bl,r.estado,t.nombre,c.nombre,r.fecha,datediff(now(),r.fecha),r.corelacionId) from Recibo r, Cliente c,TipoTramite t where r.encargado.id=?1 and r.estado=?2 and c.id=r.cliente.id and t.id=r.tipo.id order by r.fecha")
//    public List<ReciboDTO> findByEncargadoIdAndEstadoOrderByFechaAsc1(Integer encargadoId, String estado, Pageable pageable);
//
//    @Query(value = "select new com.rfs.dtos.ReciboDTO(r.id, r.recibo,r.consignatario, r.proveedor,r.bl,r.estado,t.nombre,c.nombre,r.fecha,datediff(now(),r.fecha),r.corelacionId) from Recibo r, Cliente c,TipoTramite t where r.encargado.id=?1 and r.estado=?2 and c.id=r.cliente.id and t.id=r.tipo.id and (r.recibo like %?3% or c.nombre like %?3% or r.consignatario like %?3% or r.proveedor like %?3% or r.bl like %?3% or t.nombre like %?3%) order by r.fecha")
//    public List<ReciboDTO> findByEncargadoIdAndEstadoOrderWithFilterByFechaAsc(Integer encargadoId, String estado, String filter, Pageable pageable);
//
//
//    public Recibo findById(Integer id);
//
//    @Query(value = "select new com.rfs.dtos.ReciboDTO(r.id, r.recibo,r.consignatario, r.proveedor,r.bl,r.estado,t.nombre,c.nombre,r.fecha,datediff(now(),r.fecha),r.corelacionId) from Recibo r, Cliente c,TipoTramite t where r.estado=?1 and c.id=r.cliente.id and t.id=r.tipo.id order by r.fecha")
//    public List<ReciboDTO> findByEstadoOrderByFechaAsc(String estado, Pageable pageable);
//
//    //ADMIN
//    @Query(value = "select new com.rfs.dtos.FacturaDataDTO(r.cliente.id,r.cliente.nombre,r.cliente.telefono, r.cliente.direccion,r.cliente.contacto1,r.cliente.tieneCredito,r.cliente.diasCredito,r.tipo.nombre, r.aduana.nombre, r.bl, r.proveedor, r.dua,r.previoExamen,r.aforoFisico, r.permisos, r.cliente.contacto1Correo, r.cliente.contacto2Correo, r.cliente.contacto3Correo, r.cliente.contacto4Correo, 0) from Recibo r where r.id=?1 ")
//    public FacturaDataDTO findByIdToFactura(Integer recibo);
//
//    @Query(value = "select new com.rfs.dtos.ReciboDTO(r.id, r.recibo,r.consignatario, r.proveedor,r.bl,r.estado,t.nombre,c.nombre,r.fecha,datediff(now(),r.fecha),r.corelacionId) from Recibo r, Cliente c,TipoTramite t where r.estado=?1 and c.id=r.cliente.id and t.id=r.tipo.id and (r.recibo like %?2% or c.nombre like %?2% or r.consignatario like %?2% or r.proveedor like %?2% or r.bl like %?2% or t.nombre like %?2%) order by r.fecha")
//    public List<ReciboDTO> findByEstadoOrderWithFilterByFechaAsc(String estado, String filter, Pageable pageable);
//
//    @Query(value = "select new com.rfs.dtos.ReciboIdDTO(r.id, r.recibo) from Recibo r where r.estado=?1 order by r.fecha")
//    public List<ReciboIdDTO> findByEstadoOrderByFechaDesc(String estado);
//
//    //@Query(value = "select new com.rfs.dtos.ReciboPendientesAnualDTO(Year(r.fecha), Count(case when month(r.fecha)=12 then id end) As Dic,   Count(case when month(r.fecha)=11 then id end) As Nov,   Count(case when month(r.fecha)= 10 then id end) As Oct,   Count(case when month(r.fecha)= 9 then id end) As Sept,   Count(case when month(r.fecha)= 8 then id end) As Agos,   Count(case when month(r.fecha)= 7 then id end) As Jul,   Count(case when month(r.fecha)= 6 then id end) As Jun,   Count(case when month(r.fecha)= 5 then id end) As May,   Count(case when month(r.fecha)= 4 then id end) As Abr,   Count(case when month(r.fecha)= 3 then id end) As Mar,   Count(case when month(r.fecha)= 2 then id end) As Feb,   Count(case when month(r.fecha)= 1 then id end) As Ene) from Recibo r where r.estado='PENDIENTE' and r.fecha <= now() ")
//    @Query(value = "select new com.rfs.dtos.ReciboPendientesAnualDTO(Year(r.fecha) as ano," +
//                                                                        "Count(case when month(r.fecha)=12 then id end) as dic," +
//                                                                        "Count(case when month(r.fecha)=11 then id end) as nov," +
//                                                                        "Count(case when month(r.fecha)=10 then id end) as oct, " +
//                                                                        "Count(case when month(r.fecha)=9 then id end) as sept, " +
//                                                                        "Count(case when month(r.fecha)=8 then id end) as ago, " +
//                                                                        "Count(case when month(r.fecha)=7 then id end) as jul," +
//                                                                        "Count(case when month(r.fecha)=6 then id end) as jun, " +
//                                                                        "Count(case when month(r.fecha)=5 then id end) as may," +
//                                                                        "Count(case when month(r.fecha)=4 then id end) as abr," +
//                                                                        "Count(case when month(r.fecha)=3 then id end) as mar," +
//                                                                        "Count(case when month(r.fecha)=2 then id end) as feb, " +
//                                                                        "Count(case when month(r.fecha)=1 then id end) as ene) from Recibo r where r.estado='PENDIENTE' and Year(r.fecha) = (select Year(max(r2.fecha)) as ano1 from Recibo r2) and r.fecha <= now() ")
//    public List<ReciboPendientesAnualDTO> findPendientesPorMes();
//
//    @Query(value = "select new com.rfs.dtos.ReciboFacturacionDTO(r.id, r.recibo,r.corelacionId) from Recibo r where r.id=?1 order by r.fecha")
//    public ReciboFacturacionDTO findReciboByReciboId(Integer reciboId);
//
//    @Query(value = "select new com.rfs.dtos.TramiteEncargadoDTO(count(r.id), r.encargado.nombre, r.cliente.nombre, r.tipo.nombre,r.aduana.nombre) from Recibo r where r.encargado.id=?1 and r.fecha>=?2 and r.fecha<=?3 group by r.tipo.id,r.cliente.id order by r.encargado.nombre desc")
//    public List<TramiteEncargadoDTO> findTramitesByEncargados(Integer encargadoId, Date fechaInicio, Date fechaFin,Pageable p);
//
//    @Query(value = "select new com.rfs.dtos.TramiteEncargadoDTO(count(r.id), r.encargado.nombre, r.cliente.nombre, r.tipo.nombre,r.aduana.nombre) from Recibo r where r.encargado.id=?1 and r.fecha>=?2 and r.fecha<=?3 group by r.tipo.id,r.cliente.id order by r.encargado.nombre desc")
//    public List<TramiteEncargadoDTO> findTramitesByEncargados(Integer encargadoId, Date fechaInicio, Date fechaFin);
//
//    @Query(value = "select new com.rfs.dtos.TramiteEncargadoDTO(count(r.id), r.encargado.nombre, r.cliente.nombre, r.tipo.nombre,r.aduana.nombre) from Recibo r where r.cliente.id=?1 and r.fecha>=?2 and r.fecha<=?3 group by r.encargado.id,r.tipo.id order by r.encargado.nombre desc")
//    public List<TramiteEncargadoDTO> findTramitesByEncargadosClientes(Integer clienteId, Date fechaInicio, Date fechaFin);
//
//    @Query(value = "select new com.rfs.dtos.TramiteEncargadoDTO(count(r.id), r.encargado.nombre, r.cliente.nombre, r.tipo.nombre,r.aduana.nombre) from Recibo r where r.cliente.id=?1 and r.fecha>=?2 and r.fecha<=?3 group by r.encargado.id,r.tipo.id order by r.encargado.nombre desc")
//    public List<TramiteEncargadoDTO> findTramitesByEncargadosClientes(Integer clienteId, Date fechaInicio, Date fechaFin, Pageable pageable);
//
//    @Query(value = "select new com.rfs.dtos.TramiteEncargadoDTO(count(r.id), r.encargado.nombre, r.cliente.nombre, r.tipo.nombre,r.aduana.nombre) from Recibo r where r.encargado.id=?1 and r.fecha>=?2 and r.fecha<=?3 and r.cliente.id=?4 group by r.tipo.id order by r.encargado.nombre desc")
//    public List<TramiteEncargadoDTO> findTramitesByEncargados(Integer encargadoId, Date fechaInicio, Date fechaFin, Integer clienteId);
//
//    @Query(value = "select new com.rfs.dtos.TramiteEncargadoDTO(count(r.id), r.encargado.nombre, r.cliente.nombre, r.tipo.nombre,r.aduana.nombre) from Recibo r where r.encargado.id=?1 and r.fecha>=?2 and r.fecha<=?3 and r.cliente.id=?4 group by r.tipo.id order by r.encargado.nombre desc")
//    public List<TramiteEncargadoDTO> findTramitesByEncargados(Integer encargadoId, Date fechaInicio, Date fechaFin, Integer clienteId, Pageable pageable);
//
//    @Query(value = "select new com.rfs.dtos.TramiteEncargadoDTO(count(r.id), r.encargado.nombre, r.cliente.nombre, r.tipo.nombre,r.aduana.nombre) from Recibo r where r.fecha>=?1 and r.fecha<=?2 group by r.encargado.id,r.tipo.id, r.cliente.id order by r.encargado.nombre desc")
//    public List<TramiteEncargadoDTO> findTramitesByEncargados(Date fechaInicio, Date fechaFin);
//
//    @Query(value = "select new com.rfs.dtos.TramiteEncargadoDTO(count(r.id), r.encargado.nombre, r.cliente.nombre, r.tipo.nombre,r.aduana.nombre) from Recibo r where r.fecha>=?1 and r.fecha<=?2 group by r.encargado.id,r.tipo.id, r.cliente.id order by r.encargado.nombre desc")
//    public List<TramiteEncargadoDTO> findTramitesByEncargados(Date fechaInicio, Date fechaFin, Pageable pageable);

}
