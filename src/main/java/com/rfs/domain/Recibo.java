package com.rfs.domain;

/**
 * Created by alvaro on 10/17/17.
 */
//@Entity
public class Recibo {
//    @Id
//    private Integer id;
//
//     private String recibo;
//     private Date fecha;
//
//    @OneToOne
//    @JoinColumn(name="aduana_id", referencedColumnName="id")
//    private Aduana aduana;
//
//
//    @OneToOne
//    @JoinColumn(name="tipo_id", referencedColumnName="id")
//    private TipoTramite tipo;
//
//     private String consignatario;
//     private String proveedor;
//     private String bl;
//     private String factura;
//     private String dua;
//     private String observaciones;
//     private String estado;
//
//
//    @OneToOne
//    @JoinColumn(name="encargado_id", referencedColumnName="id")
//    private Usuario encargado;
//
////     private Integer encargadoId;
//     private Integer corelacionId;
//     private Date fechaFin;
//     private Integer estaFacturado;
//     private Date fechaDua;
//     private Integer ultimoCambioId;
//
//     private Date fechaUltimoCambio;
//
//     private String ticaEstado;
//
//     private Short previoExamen, aforoFisico, permisos, pedimentados;
//
//    @OneToOne
//     @JoinColumn(name="cliente_id", referencedColumnName="id")
//     private Cliente cliente;
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getRecibo() {
//        return recibo;
//    }
//
//    public void setRecibo(String recibo) {
//        this.recibo = recibo;
//    }
//
//    public Date getFecha() {
//        return fecha;
//    }
//
//    public void setFecha(Date fecha) {
//        this.fecha = fecha;
//    }
//
////    public Integer getAduanaId() {
////        return aduanaId;
////    }
////
////    public void setAduanaId(Integer aduanaId) {
////        this.aduanaId = aduanaId;
////    }
//
//    public TipoTramite getTipo() {
//        return tipo;
//    }
//
//    public void setTipo(TipoTramite tipo) {
//        this.tipo = tipo;
//    }
//
//    public Cliente getCliente() {
//        return cliente;
//    }
//
//    public void setCliente(Cliente c) {
//        this.cliente = c;
//    }
//
//    public String getConsignatario() {
//        return consignatario;
//    }
//
//    public void setConsignatario(String consignatario) {
//        this.consignatario = consignatario;
//    }
//
//    public String getProveedor() {
//        return proveedor;
//    }
//
//    public void setProveedor(String proveedor) {
//        this.proveedor = proveedor;
//    }
//
//    public String getBl() {
//        return bl;
//    }
//
//    public void setBl(String bl) {
//        this.bl = bl;
//    }
//
//    public String getFactura() {
//        return factura;
//    }
//
//    public void setFactura(String factura) {
//        this.factura = factura;
//    }
//
//    public String getDua() {
//        return dua;
//    }
//
//    public void setDua(String dua) {
//        this.dua = dua;
//    }
//
//    public String getObservaciones() {
//        return observaciones;
//    }
//
//    public void setObservaciones(String observaciones) {
//        this.observaciones = observaciones;
//    }
//
//    public String getEstado() {
//        return estado;
//    }
//
//    public void setEstado(String estado) {
//        this.estado = estado;
//    }
//
////    public Integer getEncargadoId() {
////        return encargadoId;
////    }
////
////    public void setEncargadoId(Integer encargadoId) {
////        this.encargadoId = encargadoId;
////    }
//
//    public Integer getCorelacionId() {
//        return corelacionId;
//    }
//
//    public void setCorelacionId(Integer corelacionId) {
//        this.corelacionId = corelacionId;
//    }
//
//    public Date getFechaFin() {
//        return fechaFin;
//    }
//
//    public void setFechaFin(Date fechaFin) {
//        this.fechaFin = fechaFin;
//    }
//
//    public Integer getEstaFacturado() {
//        return estaFacturado;
//    }
//
//    public void setEstaFacturado(Integer estaFacturado) {
//        this.estaFacturado = estaFacturado;
//    }
//
//    public Date getFechaDua() {
//        return fechaDua;
//    }
//
//    public void setFechaDua(Date fechaDua) {
//        this.fechaDua = fechaDua;
//    }
//
//    public Integer getUltimoCambioId() {
//        return ultimoCambioId;
//    }
//
//    public void setUltimoCambioId(Integer ultimoCambioId) {
//        this.ultimoCambioId = ultimoCambioId;
//    }
//
//    public Date getFechaUltimoCambio() {
//        return fechaUltimoCambio;
//    }
//
//    public void setFechaUltimoCambio(Date fechaUltimoCambio) {
//        this.fechaUltimoCambio = fechaUltimoCambio;
//    }
//
//    public Aduana getAduana() {
//        return aduana;
//    }
//
//    public void setAduana(Aduana aduana) {
//        this.aduana = aduana;
//    }
//
//    public Usuario getEncargado() {
//        return encargado;
//    }
//
//    public void setEncargado(Usuario encargado) {
//        this.encargado = encargado;
//    }
//
//    public String getTicaEstado() {
//        return ticaEstado;
//    }
//
//    public void setTicaEstado(String ticaEstado) {
//        this.ticaEstado = ticaEstado;
//    }
//
//    public Short getPrevioExamen() {
//        return previoExamen;
//    }
//
//    public void setPrevioExamen(Short previoExamen) {
//        this.previoExamen = previoExamen;
//    }
//
//    public Short getAforoFisico() {
//        return aforoFisico;
//    }
//
//    public void setAforoFisico(Short aforoFisico) {
//        this.aforoFisico = aforoFisico;
//    }
//
//    public Short getPermisos() {
//        return permisos;
//    }
//
//    public void setPermisos(Short permisos) {
//        this.permisos = permisos;
//    }
//
//    public Short getPedimentados() {
//        return pedimentados;
//    }
//
//    public void setPedimentados(Short pedimentados) {
//        this.pedimentados = pedimentados;
//    }
//
//    //    public List<Factura> getFacturas() {
////        return facturas;
////    }
////
////    public void setFacturas(List<Factura> facturas) {
////        this.facturas = facturas;
////    }
//
//    public String toString() {
//        return (id!=null)?
//            id.toString() :
//                "";
//    }
}
