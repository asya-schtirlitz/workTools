package SM3Generator;


import static SM3Generator.Constants.*;

public class Field {
        String name;
        String template;
        private static final String templateText = "<xsl:choose>\n" +
                "\t<xsl:when test=\"NameSpaceFieldName\">\n" +
                "\t\t<xsl:apply-templates select=\"NameSpaceFieldName\"/>\n" + // mode="mandatory_ReplaceableText"
                "\t</xsl:when>\n" +
                "\t<xsl:otherwise>\n" +
                "\t\t<xsl:call-template name=\"default_ReplaceableText\">\n" +
                "\t\t\t<xsl:with-param name=\"ReplaceableText_tag\">FieldName</xsl:with-param>\n" +
                "\t\t</xsl:call-template>\n" +
                "\t</xsl:otherwise>\n" +
                "</xsl:choose>\n";
        private static final String templateArrayText = "<xsl:choose>\n" +
                "\t<xsl:when test=\"NameSpaceFieldName\">\n" +
                "\t\t\t<xsl:apply-templates select=\"NameSpaceFieldName\"/>\n" +
                "\t</xsl:when>\n" +
                "\t<xsl:otherwise>\n" +
                "\t\t<xsl:call-template name=\"default_ReplaceableText_array\">\n" +
                "\t\t\t<xsl:with-param name=\"array_tag\">FieldName</xsl:with-param>\n" +
                "\t\t</xsl:call-template>\n" +
                "\t</xsl:otherwise>\n" +
                "</xsl:choose>\n";

        public Field(String fieldNname, String type, String nameSpace, boolean isArray) {
            this.name = fieldNname;
            this.template = "Error type";
            if (isArray) {
                this.template = this.templateArrayText;
            } else {
                this.template = this.templateText;
            }

            for (Types t : Types.values()) {
                if (type.equals(t.name())) {
                    this.template = template.replaceAll(REPLACEABLE_TEXT, t.getText());
                }
            }

            this.template = this.template.replaceAll(REPLACEABLE_NAMESPACE, nameSpace);
        }

}
