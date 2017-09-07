//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.09.07 at 02:54:13 AM CEST 
//


package app.jaxb_model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the model package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Content_QNAME = new QName("www.assembly.gov.rs/acts/", "content");
    private final static QName _DataReference_QNAME = new QName("www.assembly.gov.rs/acts/", "reference");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: model
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Clause }
     * 
     */
    public Clause createClause() {
        return new Clause();
    }

    /**
     * Create an instance of {@link Subclause }
     * 
     */
    public Subclause createSubclause() {
        return new Subclause();
    }

    /**
     * Create an instance of {@link Indent }
     * 
     */
    public Indent createIndent() {
        return new Indent();
    }

    /**
     * Create an instance of {@link Data }
     * 
     */
    public Data createData() {
        return new Data();
    }

    /**
     * Create an instance of {@link Chapter }
     * 
     */
    public Chapter createChapter() {
        return new Chapter();
    }

    /**
     * Create an instance of {@link Section }
     * 
     */
    public Section createSection() {
        return new Section();
    }

    /**
     * Create an instance of {@link Subsection }
     * 
     */
    public Subsection createSubsection() {
        return new Subsection();
    }

    /**
     * Create an instance of {@link Article }
     * 
     */
    public Article createArticle() {
        return new Article();
    }

    /**
     * Create an instance of {@link Paragraph }
     * 
     */
    public Paragraph createParagraph() {
        return new Paragraph();
    }

    /**
     * Create an instance of {@link Act }
     * 
     */
    public Act createAct() {
        return new Act();
    }

    /**
     * Create an instance of {@link Part }
     * 
     */
    public Part createPart() {
        return new Part();
    }

    /**
     * Create an instance of {@link Amendment }
     * 
     */
    public Amendment createAmendment() {
        return new Amendment();
    }

    /**
     * Create an instance of {@link Target }
     * 
     */
    public Target createTarget() {
        return new Target();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Data }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "www.assembly.gov.rs/acts/", name = "content")
    public JAXBElement<Data> createContent(Data value) {
        return new JAXBElement<Data>(_Content_QNAME, Data.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "www.assembly.gov.rs/acts/", name = "reference", scope = Data.class)
    @XmlIDREF
    public JAXBElement<Object> createDataReference(Object value) {
        return new JAXBElement<Object>(_DataReference_QNAME, Object.class, Data.class, value);
    }

}