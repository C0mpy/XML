//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.05.28 at 07:34:31 PM CEST 
//


package model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element ref="{www.tim7.org}alineja" maxOccurs="unbounded" minOccurs="2"/>
 *         &lt;/sequence>
 *         &lt;element ref="{www.tim7.org}sadrzaj"/>
 *       &lt;/choice>
 *       &lt;attribute name="broj" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "alineja",
    "sadrzaj"
})
@XmlRootElement(name = "podtacka")
public class Podtacka {

    protected List<Alineja> alineja;
    protected String sadrzaj;
    @XmlAttribute(name = "broj")
    protected Integer broj;

    /**
     * Gets the value of the alineja property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the alineja property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAlineja().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Alineja }
     * 
     * 
     */
    public List<Alineja> getAlineja() {
        if (alineja == null) {
            alineja = new ArrayList<Alineja>();
        }
        return this.alineja;
    }

    /**
     * Gets the value of the sadrzaj property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSadrzaj() {
        return sadrzaj;
    }

    /**
     * Sets the value of the sadrzaj property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSadrzaj(String value) {
        this.sadrzaj = value;
    }

    /**
     * Gets the value of the broj property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getBroj() {
        return broj;
    }

    /**
     * Sets the value of the broj property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setBroj(Integer value) {
        this.broj = value;
    }

}
