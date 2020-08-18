//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.06.09 at 11:07:05 AM CST 
//


package com.rfs.fe.v43.nc;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;


/**
 * <p>Java class for DescuentoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DescuentoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MontoDescuento" type="{https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/notaCreditoElectronica}DecimalDineroType"/>
 *         &lt;element name="NaturalezaDescuento">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="80"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DescuentoType", namespace = "https://cdn.comprobanteselectronicos.go.cr/xml-schemas/v4.3/notaCreditoElectronica", propOrder = {
    "montoDescuento",
    "naturalezaDescuento"
})
public class DescuentoType {

    @XmlElement(name = "MontoDescuento", required = true)
    protected BigDecimal montoDescuento;
    @XmlElement(name = "NaturalezaDescuento", required = true)
    protected String naturalezaDescuento;

    /**
     * Gets the value of the montoDescuento property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMontoDescuento() {
        return montoDescuento;
    }

    /**
     * Sets the value of the montoDescuento property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMontoDescuento(BigDecimal value) {
        this.montoDescuento = value;
    }

    /**
     * Gets the value of the naturalezaDescuento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaturalezaDescuento() {
        return naturalezaDescuento;
    }

    /**
     * Sets the value of the naturalezaDescuento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaturalezaDescuento(String value) {
        this.naturalezaDescuento = value;
    }

}
