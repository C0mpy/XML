<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:pred="www.assembly.gov.rs/acts/">

    <xsl:template match="pred:act">
        <rdf:RDF xml:base="www.assembly.gov.rs/">
            <rdf:Description rdf:about="acts/{@id}">
                <pred:sessionId><xsl:value-of select="@sessionId"/></pred:sessionId>
                <pred:status><xsl:value-of select="@status"/></pred:status>
                <pred:title><xsl:value-of select="@title"/></pred:title>
                <pred:country><xsl:value-of select="@country"/></pred:country>
                <pred:state><xsl:value-of select="@state"/></pred:state>
                <pred:city><xsl:value-of select="@city"/></pred:city>
                <pred:broughtBy><xsl:value-of select="@broughtBy"/></pred:broughtBy>
                <pred:issueNumber><xsl:value-of select="@issueNumber"/></pred:issueNumber>
                <pred:date><xsl:value-of select="@date"/></pred:date>
                <pred:mayor><xsl:value-of select="@mayor"/></pred:mayor>
            </rdf:Description>
        </rdf:RDF>
    </xsl:template>
    
</xsl:stylesheet>