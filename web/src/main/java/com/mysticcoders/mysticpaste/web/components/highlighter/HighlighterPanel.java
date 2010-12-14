package com.mysticcoders.mysticpaste.web.components.highlighter;

import com.mysticcoders.mysticpaste.model.LanguageSyntax;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.PackageResourceReference;

import java.util.ArrayList;
import java.util.List;

/**
 * HighlighterTextAreaPanel
 * <p/>
 * Created by: Andrew Lombardi
 * Copyright 2006 Mystic Coders, LLC
 */
public class HighlighterPanel extends Panel {
    private static final long serialVersionUID = 1L;


    private static List<LanguageSyntax> types
            = new ArrayList<LanguageSyntax>();

    static {
        types.add(new LanguageSyntax("as3", "Applescript", "shBrushAS3.js"));
        types.add(new LanguageSyntax("bash", "Bash", "shBrushBash.js"));
        types.add(new LanguageSyntax("csharp", "C#", "shBrushCSharp.js"));
        types.add(new LanguageSyntax("cpp", "C / C++", "shBrushCpp.js"));
        types.add(new LanguageSyntax("css", "CSS", "shBrushCss.js"));
        types.add(new LanguageSyntax("delphi", "Delphi", "shBrushDelphi.js"));
        types.add(new LanguageSyntax("diff", "Diff", "shBrushDiff.js"));
        types.add(new LanguageSyntax("groovy", "Groovy", "shBrushGroovy.js"));
        types.add(new LanguageSyntax("java", "Java", "shBrushJava.js"));
        types.add(new LanguageSyntax("js", "JavaScript", "shBrushJScript.js"));
        types.add(new LanguageSyntax("javafx", "JavaFX", "shBrushJavaFX.js"));
        types.add(new LanguageSyntax("perl", "Perl", "shBrushPerl.js"));
        types.add(new LanguageSyntax("php", "PHP", "shBrushPhp.js"));
        types.add(new LanguageSyntax("text", "Plain Text", "shBrushPlain.js"));
        types.add(new LanguageSyntax("powershell", "PowerShell", "shBrushPowerShell.js"));
        types.add(new LanguageSyntax("python", "Python", "shBrushPython.js"));
        types.add(new LanguageSyntax("ruby", "Ruby", "shBrushRuby.js"));
        types.add(new LanguageSyntax("scala", "Scala", "shBrushScala.js"));
        types.add(new LanguageSyntax("sql", "SQL", "shBrushSql.js"));
        types.add(new LanguageSyntax("vb", "VB / VB.NET", "shBrushVb.js"));
        types.add(new LanguageSyntax("xml", "XML / XHTML", "shBrushXml.js"));
    }


    public static List<LanguageSyntax> getLanguageSyntaxList() {
        return types;
    }

    public static LanguageSyntax getLanguageSyntax(String language) {
        for (LanguageSyntax syntax : types) {
            if (syntax.getLanguage().equals(language)) {
                return syntax;
            }
        }

        return null;
    }

    public static String getLanguageScript(String language) {
        for (LanguageSyntax syntax : types) {
            if (syntax.getLanguage().equals(language)) {
                return syntax.getScript();
            }
        }

        return null;
    }

    public HighlighterPanel(String id, IModel model) {
        this(id, model, null);
    }

    public HighlighterPanel(String id, IModel model, String language) {
        this(id, model, language, null);
    }

    public void renderHead(IHeaderResponse response) {
        response.renderCSSReference(new PackageResourceReference(HighlighterPanel.class, "shCore.css"));
        response.renderCSSReference(new PackageResourceReference(HighlighterPanel.class, "shThemeDefault.css"));
    }

    private String language;

    public HighlighterPanel(String id, IModel model, String localLanguage, String highlightLines) {
        super(id);

        this.language = localLanguage;

        Label codePanel = new Label("code", model);
        add(codePanel);

        WebMarkupContainer highlighterCoreContainer = new WebMarkupContainer("highlighterCore");
        highlighterCoreContainer.add(new Behavior() {
          public void renderHead(IHeaderResponse response) {
            response.renderJavascriptReference(new PackageResourceReference(HighlighterPanel.class,
              "shCore.js"));
          }
        });
        add( highlighterCoreContainer );

        if (language == null || getLanguageScript(language) == null) language = "text";


        WebMarkupContainer highlighterLanguageContainer = new WebMarkupContainer("highlighterLanguage");
        highlighterLanguageContainer.add(new Behavior() {
            public void renderHead(IHeaderResponse response) {
                response.renderJavascriptReference(new PackageResourceReference(HighlighterPanel.class, getLanguageScript(language)));
            }
        });


        StringBuffer brushConfig = new StringBuffer("brush: ");
        brushConfig.append(language);
        brushConfig.append("; toolbar: false");
        if (highlightLines != null)
            brushConfig.append("; highlight: [").append(highlightLines).append("]");

        codePanel.add(new AttributeModifier("class", true, new Model<String>(brushConfig.toString())));

    }
}