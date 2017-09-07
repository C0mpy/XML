<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
                xmlns:pred="www.assembly.gov.rs/amendments/">

    <xsl:template match="/pred:amendment">
        <rdf:RDF xml:base="www.assembly.gov.rs/">
            <rdf:Description rdf:about="amendments/{@id}">
                <pred:actId><xsl:value-of select="@actId"/></pred:actId>
                <pred:status><xsl:value-of select="@status"/></pred:status>
            </rdf:Description>
        </rdf:RDF>
    </xsl:template>

</xsl:stylesheet>