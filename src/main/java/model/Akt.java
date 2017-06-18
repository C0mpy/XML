//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.06.18 at 02:58:01 PM CEST 
//


package model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{www.tim7.org/akt}preambula"/>
 *         &lt;choice>
 *           &lt;sequence>
 *             &lt;element ref="{www.tim7.org/akt}deo" maxOccurs="unbounded" minOccurs="2"/>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element ref="{www.tim7.org/akt}clan" maxOccurs="20" minOccurs="2"/>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element ref="{www.tim7.org/akt}glava" maxOccurs="unbounded"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *       &lt;/sequence>
 *       &lt;attribute name="naslov" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="status" use="required" type="{www.tim7.org/akt}status" />
 *       &lt;attribute name="drzava" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="pokrajina" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="grad" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="donosilac" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="broj" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="datum" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="gradonacelnik" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "preambula",
    "deo",
    "clan",
    "glava"
})
@XmlRootElement(name = "akt")
public class Akt {

    @XmlElement(required = true)
    protected Object preambula;
    protected List<Deo> deo;
    protected List<Clan> clan;
    protected List<Glava> glava;
    @XmlAttribute(name = "naslov", required = true)
    protected String naslov;
    @XmlAttribute(name = "status", required = true)
    protected String status;
    @XmlAttribute(name = "drzava")
    protected String drzava;
    @XmlAttribute(name = "pokrajina")
    protected String pokrajina;
    @XmlAttribute(name = "grad")
    protected String grad;
    @XmlAttribute(name = "donosilac")
    protected String donosilac;
    @XmlAttribute(name = "broj")
    protected String broj;
    @XmlAttribute(name = "datum")
    protected String datum;
    @XmlAttribute(name = "gradonacelnik")
    protected String gradonacelnik;

    /**
     * Gets the value of the preambula property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getPreambula() {
        return preambula;
    }

    /**
     * Sets the value of the preambula property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setPreambula(Object value) {
        this.preambula = value;
    }

    /**
     * Gets the value of the deo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Deo }
     * 
     * 
     */
    public List<Deo> getDeo() {
        if (deo == null) {
            deo = new ArrayList<Deo>();
        }
        return this.deo;
    }

    /**
     * Gets the value of the clan property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clan property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClan().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Clan }
     * 
     * 
     */
    public List<Clan> getClan() {
        if (clan == null) {
            clan = new ArrayList<Clan>();
        }
        return this.clan;
    }

    /**
     * Gets the value of the glava property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the glava property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getGlava().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Glava }
     * 
     * 
     */
    public List<Glava> getGlava() {
        if (glava == null) {
            glava = new ArrayList<Glava>();
        }
        return this.glava;
    }

    /**
     * Gets the value of the naslov property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNaslov() {
        return naslov;
    }

    /**
     * Sets the value of the naslov property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNaslov(String value) {
        this.naslov = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the drzava property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDrzava() {
        return drzava;
    }

    /**
     * Sets the value of the drzava property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDrzava(String value) {
        this.drzava = value;
    }

    /**
     * Gets the value of the pokrajina property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPokrajina() {
        return pokrajina;
    }

    /**
     * Sets the value of the pokrajina property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPokrajina(String value) {
        this.pokrajina = value;
    }

    /**
     * Gets the value of the grad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrad() {
        return grad;
    }

    /**
     * Sets the value of the grad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrad(String value) {
        this.grad = value;
    }

    /**
     * Gets the value of the donosilac property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDonosilac() {
        return donosilac;
    }

    /**
     * Sets the value of the donosilac property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDonosilac(String value) {
        this.donosilac = value;
    }

    /**
     * Gets the value of the broj property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBroj() {
        return broj;
    }

    /**
     * Sets the value of the broj property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBroj(String value) {
        this.broj = value;
    }

    /**
     * Gets the value of the datum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatum() {
        return datum;
    }

    /**
     * Sets the value of the datum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatum(String value) {
        this.datum = value;
    }

    /**
     * Gets the value of the gradonacelnik property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGradonacelnik() {
        return gradonacelnik;
    }

    /**
     * Sets the value of the gradonacelnik property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGradonacelnik(String value) {
        this.gradonacelnik = value;
    }

}
