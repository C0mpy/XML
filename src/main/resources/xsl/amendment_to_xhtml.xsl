<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:a="www.assembly.gov.rs/amendments/">

    <xsl:template match="/">
        <html>
        	<head>
        		<title>Assembly Amendments</title>
        	</head>
        	<body>
        		<table>
        			<tr>
        				<th>Id</th>
        				<th>actId</th>
        				<th>status</th>
        			</tr>
        			<xsl:for-each select="a:amendment">
        				<tr>
        					<td><xsl:value-of select="@id"/></td>
        					<td><xsl:value-of select="@actId"/></td>
        					<td><xsl:value-of select="@status"/></td>
        				</tr>
        			</xsl:for-each>
        		</table>
        	</body>
        </html>
    </xsl:template>
    
</xsl:stylesheet>