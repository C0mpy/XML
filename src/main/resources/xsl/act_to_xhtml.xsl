<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:a="www.assembly.gov.rs/acts/">

    <xsl:template match="/">
        <html>
        	<head>
        		<title>Assembly Acts</title>
        	</head>
        	<body>
        		<table>
        			<tr>
        				<th>#</th>
        				<th>Title</th>
        				<th>Country</th>
        				<th>City</th>
        				<th>Date</th>
        			</tr>
        			<xsl:for-each select="a:act">
        				<tr>
        					<td><xsl:value-of select="position()"/></td>
        					<td><xsl:value-of select="@title"/></td>
        					<td><xsl:value-of select="@country"/></td>
        					<td><xsl:value-of select="@city"/></td>
        					<td><xsl:value-of select="@date"/></td>
        				</tr>
        			</xsl:for-each>
        		</table>
        	</body>
        </html>
    </xsl:template>
    
</xsl:stylesheet>